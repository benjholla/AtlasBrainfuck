package com.benjholla.brainfuck.ast;

import java.util.List;

import org.eclipse.core.runtime.SubMonitor;

import com.benjholla.atlas.brainfuck.common.XCSG;
import com.benjholla.atlas.brainfuck.indexer.WorkspaceUtils;
import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;
import com.ensoftcorp.atlas.core.db.graph.Edge;
import com.ensoftcorp.atlas.core.db.graph.EditableGraph;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.index.common.SourceCorrespondence;

public class LoopInstruction extends Instruction {

	private List<Instruction> instructions;

	public LoopInstruction(ParserSourceCorrespondence sc, List<Instruction> instructions) {
		super(sc);
		this.instructions = instructions;
	}
	
	public List<Instruction> getInstructions(){
		return instructions;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(Instruction instruction : instructions) {
			if(instruction instanceof LoopInstruction) {
				result.append(" ");
			}
			result.append(instruction.toString());
		}
		return "Loop: [" + result + "]";
	}
	
	@Override
	public Type getType() {
		return Type.LOOP;
	}
	
	@Override
	public Node index(EditableGraph graph, Node containerNode, SubMonitor monitor) {
		// create the loop header node
		Node loopHeader = graph.createNode();
		loopHeader.tag(XCSG.ControlFlow_Node);
		loopHeader.putAttr(XCSG.name, getType().toString());
		loopHeader.tag(XCSG.ControlFlowCondition);
		loopHeader.tag(XCSG.Loop);
		
		// set the loop headers node's source correspondence
		// selecting a loop header will highlight the entire loop
		loopHeader.putAttr(XCSG.sourceCorrespondence, new SourceCorrespondence(WorkspaceUtils.getFile(psc.getSource()), psc.getOffset(), psc.getLength(), psc.getStartLine(), psc.getEndLine()));
		
		// make the container node contain the loop header
		Edge containsEdge = graph.createEdge(containerNode, loopHeader);
		containsEdge.tag(XCSG.Contains);
		
		Node previousInstructionNode = null;
		for(int i=0; i<instructions.size(); i++) {
			Instruction instruction = instructions.get(i);
			
			// create the instruction
			Node instructionNode = instruction.index(graph, containerNode, monitor);
			
			// create loop child edge
			Edge loopChildEdge = graph.createEdge(loopHeader, instructionNode);
			loopChildEdge.tag(XCSG.LoopChild);
			
			// tag the cfg root/exit nodes appropriately
			if(i == 0) {
				Edge loopEntryEdge = graph.createEdge(loopHeader, instructionNode);
				loopEntryEdge.tag(XCSG.ControlFlow_Edge);
				loopEntryEdge.putAttr(XCSG.conditionValue, true); // any non-zero value
			}
			
			// create the control flow edge relationship if this isn't the first instruction
			if(previousInstructionNode != null) {
				Edge controlFlowEdge = graph.createEdge(previousInstructionNode, instructionNode);
				controlFlowEdge.tag(XCSG.ControlFlow_Edge);
				if(previousInstructionNode.taggedWith(XCSG.Loop)) {
					Node header = previousInstructionNode;
					controlFlowEdge.putAttr(XCSG.conditionValue, false); // 0 value
					
					// link the footer up to the next instruction
					AtlasSet<Edge> backEdges = graph.edges().taggedWithAny(XCSG.ControlFlowBackEdge);
					for(Edge backEdge : backEdges) {
						if(backEdge.to().equals(header)) {
							Node footer = backEdge.from();
							controlFlowEdge = graph.createEdge(footer, instructionNode);
							controlFlowEdge.tag(XCSG.ControlFlow_Edge);
							controlFlowEdge.putAttr(XCSG.conditionValue, false); // 0 value
							break;
						}
					}
				}
			}
			
			// update the previous instruction
			previousInstructionNode = instructionNode;
		}
		
		// create the loop footer node
		Node loopFooter = graph.createNode();
		loopFooter.tag(XCSG.ControlFlow_Node);
		loopFooter.tag(XCSG.ControlFlowCondition);
		loopFooter.putAttr(XCSG.name, "]");
		
		// set the loop headers node's source correspondence
		// selecting a loop footer will highlight just the loop footer
		loopFooter.putAttr(XCSG.sourceCorrespondence, new SourceCorrespondence(WorkspaceUtils.getFile(psc.getSource()), (psc.getOffset()+psc.getLength()-1), 1, psc.getEndLine(), psc.getEndLine()));
		
		// make the container node contain the loop header
		containsEdge = graph.createEdge(containerNode, loopFooter);
		containsEdge.tag(XCSG.Contains);
		
		// create exit edge
		Edge loopFooterEdge = graph.createEdge(previousInstructionNode, loopFooter);
		loopFooterEdge.tag(XCSG.ControlFlow_Edge);
		
		// create loop back edge
		Edge loopBackEdge = graph.createEdge(loopFooter, loopHeader);
		loopBackEdge.tag(XCSG.ControlFlow_Edge);
		loopBackEdge.tag(XCSG.ControlFlowBackEdge);
		loopBackEdge.putAttr(XCSG.conditionValue, true); // any non-zero value
		
		return loopHeader;
	}
	
}
