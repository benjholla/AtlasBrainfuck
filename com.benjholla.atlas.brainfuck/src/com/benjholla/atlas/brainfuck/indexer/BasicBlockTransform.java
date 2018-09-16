package com.benjholla.atlas.brainfuck.indexer;

import java.util.ArrayList;
import java.util.List;

import com.benjholla.atlas.brainfuck.common.XCSG;
import com.benjholla.atlas.brainfuck.preferences.BrainfuckPreferences;
import com.ensoftcorp.atlas.core.db.graph.Edge;
import com.ensoftcorp.atlas.core.db.graph.Graph;
import com.ensoftcorp.atlas.core.db.graph.GraphElement.NodeDirection;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.graph.UncheckedGraph;
import com.ensoftcorp.atlas.core.db.set.AtlasHashSet;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.index.common.SourceCorrespondence;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.script.CommonQueries;

/**
 * Converts the Atlas CFG to a CFG with Basic Blocks
 * 
 * The code in a basic block has: 
 * - One entry point, meaning no code within it is the destination of a jump
 *   instruction anywhere in the program. 
 * - One exit point, meaning only the last instruction can cause the program
 *   to begin executing code in a different basic block.
 * 
 * More formally, a sequence of instructions forms a basic block if: 
 * - The instruction in each position dominates, or always executes before, all
 *   those in later positions. 
 * - No other instruction executes between two instructions in the sequence.
 * 
 * The algorithm for generating basic blocks from a listing of code is simple:
 * the analyser scans over the code, marking block boundaries, which are
 * instructions which may either begin or end a block because they either
 * transfer control or accept control from another point. Then, the listing is
 * simply "cut" at each of these points, and basic blocks remain.
 * 
 * Reference: https://en.wikipedia.org/wiki/Basic_block
 * 
 * Control flow blocks that are tagged with or contain a node tagged with the
 * constructor defined Atlas tags may be treated as "cut points".
 * 
 * Note that by some definitions unconditional jumps to labels not targeted by
 * other jumps may be included within a basic block, but currently this
 * implementation treats all jumps as cut points.
 * 
 * @author Ben Holland
 */
public class BasicBlockTransform implements ProgramGraphTransform {
	
	private String[] cutPointTags = new String[] {
		XCSG.ControlFlowCondition
	};

	public BasicBlockTransform() {}
	
	@Override
	public Q transform(Q cfg) {
		// handling base cases
		if(CommonQueries.isEmpty(cfg)) {
			return Common.empty();
		} else if(cfg.eval().nodes().size() == 1) {
			return cfg;
		}
		
		// cut the cfg at the block boundaries
		Q cutPoints = cfg.nodes(cutPointTags);
		Q connectedCutPoints = cutPoints.induce(cfg);
		
//		// debug
//		Highlighter h = new Highlighter();
//		h.highlight(cutPoints, Color.RED);
//		DisplayUtils.show(cfg, h, true, "Cut Points");
		
		// cut cfg into basic blocks
		Q cutCFG = cfg.difference(cutPoints);
		List<BasicBlock> basicBlocks = new ArrayList<BasicBlock>();
		for(Node root : new AtlasHashSet<Node>(cutCFG.roots().eval().nodes())) {
			basicBlocks.add(new BasicBlock(cutCFG.forward(Common.toQ(root)).eval()));
		}
		
		// keep track of a set of all the inter basic block edges
		AtlasSet<Edge> basicBlockConnections = new AtlasHashSet<Edge>();
		basicBlockConnections.addAll(connectedCutPoints.eval().edges());
		
		// connect the basic blocks
		for(BasicBlock basicBlock : basicBlocks) {
			Node root = Common.toQ(basicBlock.getBasicBlocks()).roots().eval().nodes().one();
			Node leaf = Common.toQ(basicBlock.getBasicBlocks()).leaves().eval().nodes().one();
			// connect the incoming entry edges
			for(Edge incomingEdge : cfg.reverseStep(Common.toQ(basicBlock.getEntry())).eval().edges()) {
				basicBlockConnections.add(getOrCreateBasicBlockBoundaryControlFlowEdge(incomingEdge.from(), root, incomingEdge.getAttr(XCSG.conditionValue)));
			}
			// connect the outgoing exit edges
			for(Edge outgoingEdge : cfg.forwardStep(Common.toQ(basicBlock.getExit())).eval().edges()) {
				basicBlockConnections.add(getOrCreateBasicBlockBoundaryControlFlowEdge(leaf, outgoingEdge.to(), outgoingEdge.getAttr(XCSG.conditionValue)));
			}
		}
		
		return Common.toQ(basicBlockConnections);
	}
	
	private class BasicBlock {
		private AtlasSet<Edge> basicBlockNodes = new AtlasHashSet<Edge>();
		private AtlasSet<Edge> basicBlockEdges = new AtlasHashSet<Edge>(); 
		private Node entry;
		private Node exit;
		
		/**
		 * Constructs a new node which has the names of each of the nodes delimited by
		 * two new lines
		 * 
		 * @param statements
		 */
		public BasicBlock(Graph statements) {
			if(statements.roots().size() != 1) {
				throw new RuntimeException("Basic block must have exactly one entry.");
			} else {
				this.entry = (Node) statements.roots().one();
			}
			
			if(statements.leaves().size() != 1) {
				throw new RuntimeException("Basic block must have exactly one exit.");
			} else {
				this.exit = (Node) statements.leaves().one();
			}
			
			// if the basic block consists of a single node
			// then there is nothing to do
			
			if(entry.equals(exit)) {
				basicBlockNodes.add(entry);
			} else {
				// create the basic block
				Node basicBlock = Graph.U.createNode();
				basicBlockNodes.add(basicBlock);

				int blockSize = 1;
				Node cur = entry;
				
				// add a container if the original entry had a container
				Node parent = Common.toQ(cur).parent().eval().nodes().one();
				if(parent != null) {
					Edge containsEdge = Graph.U.createEdge(parent, basicBlock);
					containsEdge.tag(XCSG.Contains);
				}
				
				StringBuilder blockName = new StringBuilder();
				String statement = cur.getAttr(XCSG.name).toString();
				blockName.append(statement);
				
				// set the source correspondence to be the start of the entry statement's source correspondence
				// up until the end of the exit statement's source correspondence
				SourceCorrespondence entrySC = (SourceCorrespondence) cur.getAttr(XCSG.sourceCorrespondence);
				SourceCorrespondence exitSC = (SourceCorrespondence) cur.getAttr(XCSG.sourceCorrespondence);
				
				combineSourceCorrespondence(basicBlock, entrySC, exitSC);
				basicBlock.putAttr(XCSG.name, blockName.toString());
				
				while(!cur.equals(exit)) {
					AtlasSet<Edge> outEdges = statements.edges(cur, NodeDirection.OUT);
					if(outEdges.isEmpty()) {
						throw new RuntimeException("A basic block must be connected.");
					} else if(outEdges.size() > 1) {
						throw new RuntimeException("A basic block node may not have more than one successor.");
					} else {
						cur = outEdges.one().to();
						statement = cur.getAttr(XCSG.name).toString();
						exitSC = (SourceCorrespondence) cur.getAttr(XCSG.sourceCorrespondence);
						if(BrainfuckPreferences.isLimitMaxBasicBlockInstructionsEnabled() && (blockSize++ <= BrainfuckPreferences.getMaxBasicBlockInstructions())) {
							blockName.append(statement);
							combineSourceCorrespondence(basicBlock, entrySC, exitSC);
							basicBlock.putAttr(XCSG.name, blockName.toString());
						} else {
							Node previousBasicBlock = basicBlock;
							basicBlock = Graph.U.createNode();
							// add a container if the original entry had a container
							parent = Common.toQ(cur).parent().eval().nodes().one();
							if(parent != null) {
								Edge containsEdge = Graph.U.createEdge(parent, basicBlock);
								containsEdge.tag(XCSG.Contains);
							}
							basicBlockNodes.add(basicBlock);
							basicBlockEdges.add(getOrCreateBasicBlockBoundaryControlFlowEdge(previousBasicBlock, basicBlock, null));
							
							blockSize = 1;
							blockName = new StringBuilder();
							blockName.append(statement);
							basicBlock.putAttr(XCSG.name, blockName.toString());
							entrySC = (SourceCorrespondence) cur.getAttr(XCSG.sourceCorrespondence);
							exitSC = (SourceCorrespondence) cur.getAttr(XCSG.sourceCorrespondence);
							combineSourceCorrespondence(basicBlock, entrySC, exitSC);
						}
					}
				}
				
				// purge the old control flow edges
				AtlasSet<Edge> edgesToPurge = new AtlasHashSet<Edge>(statements.edges());
				while(!edgesToPurge.isEmpty()) {
					Edge edgeToPurge = edgesToPurge.one();
					Graph.U.delete(edgeToPurge);
					edgesToPurge.remove(edgeToPurge);
				}
				
				// purge the old control flow nodes
				AtlasSet<Node> nodesToPurge = new AtlasHashSet<Node>(statements.nodes());
				while(!nodesToPurge.isEmpty()) {
					Node nodeToPurge = nodesToPurge.one();
					Graph.U.delete(nodeToPurge);
					nodesToPurge.remove(nodeToPurge);
				}
			}
		}

		private void combineSourceCorrespondence(Node basicBlock, SourceCorrespondence entrySC,
				SourceCorrespondence exitSC) {
			if(entrySC != null && exitSC != null) {
				// source file, offset, length, start line, end line
				SourceCorrespondence sc = new SourceCorrespondence(entrySC.sourceFile, 
																   entrySC.offset, 
																   ((exitSC.offset+exitSC.length)-entrySC.offset), 
																   entrySC.startLine, 
																   exitSC.endLine);
				basicBlock.putAttr(XCSG.sourceCorrespondence, sc);
			} else if(entrySC != null) {
				basicBlock.putAttr(XCSG.sourceCorrespondence, entrySC);
			} else if(exitSC != null) {
				basicBlock.putAttr(XCSG.sourceCorrespondence, exitSC);
			}
		}

		public Node getEntry() {
			return entry;
		}

		public Node getExit() {
			return exit;
		}

		public Graph getBasicBlocks() {
			return new UncheckedGraph(basicBlockNodes, basicBlockEdges);
		}
	}
	
	/**
	 * Creates a basic block boundary control flow edge if it does not already exist between(predecessor,successor) for the given conditionValue.
	 * 
	 * @param predecessor
	 * @param successor
	 * @param conditionValue - The edge condition value or null if edge is unconditional
	 * @return
	 */
	private Edge getOrCreateBasicBlockBoundaryControlFlowEdge(Node predecessor, Node successor, Object conditionValue) {
		Q betweenEdges = Common.universe().edges(XCSG.ControlFlow_Edge).betweenStep(Common.toQ(predecessor), Common.toQ(successor));
		
		// if the edge is a condition edge then we should filter the between edges to the correct conditional edge type
		// or create a new one if one does not exist already
		Edge basicBlockBoundaryControlFlowEdge;
		if(conditionValue != null) {
			basicBlockBoundaryControlFlowEdge = betweenEdges.selectEdge(XCSG.conditionValue, conditionValue).eval().edges().one();
		} else {
			basicBlockBoundaryControlFlowEdge = betweenEdges.eval().edges().one();
		}
		
		// if the edge doesn't exist already then create it
		if(basicBlockBoundaryControlFlowEdge == null) {
			basicBlockBoundaryControlFlowEdge = Graph.U.createEdge(predecessor, successor);
			basicBlockBoundaryControlFlowEdge.tag(XCSG.ControlFlow_Edge);
			if(conditionValue != null) {
				basicBlockBoundaryControlFlowEdge.putAttr(XCSG.conditionValue, conditionValue);
			}
		}
		return basicBlockBoundaryControlFlowEdge;
	}

}
