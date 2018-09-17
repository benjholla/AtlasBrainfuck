package com.benjholla.brainfuck.ast;

import java.util.List;

import org.eclipse.core.runtime.SubMonitor;

import com.benjholla.brainfuck.atlas.common.XCSG;
import com.benjholla.brainfuck.atlas.preferences.BrainfuckPreferences;
import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;
import com.ensoftcorp.atlas.core.db.graph.Edge;
import com.ensoftcorp.atlas.core.db.graph.EditableGraph;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.index.common.SourceCorrespondence;

public class Program extends ASTNode {
	private List<Instruction> instructions;

	public Program(ParserSourceCorrespondence sc, List<Instruction> instructions) {
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
		return "Program: {" + result + "}";
	}
	
	@Override
	public Node index(EditableGraph graph, Node containerNode, SubMonitor monitor) {
		int blockSize = 0;
		Node previousInstructionNode = null;
		for(int i=0; i<instructions.size(); i++) {
			Instruction instruction = instructions.get(i);
			Node instructionNode;
			blockSize++;
			
			boolean coalesce = false;
			if(previousInstructionNode != null && !(previousInstructionNode.taggedWith(XCSG.ControlFlowCondition)) && !(instruction instanceof LoopInstruction)) {
				if(BrainfuckPreferences.isCoalescingBasicBlocksEnabled()) {
					if(BrainfuckPreferences.isLimitMaxBasicBlockInstructionsEnabled()) {
						if(BrainfuckPreferences.isIncludeWhitespaceBasicBlockDelimitersEnabled()) {
							// limited length, whitespace considered
							if(blockSize <= BrainfuckPreferences.getMaxBasicBlockInstructions()) {
								SourceCorrespondence previousSC = (SourceCorrespondence) previousInstructionNode.getAttr(XCSG.sourceCorrespondence);
								int prevOffsetEnd = previousSC.offset + previousSC.length;
								int nextOffsetEnd = instruction.getParserSourceCorrespondence().getOffset() + instruction.getParserSourceCorrespondence().getLength();
								if(nextOffsetEnd - prevOffsetEnd == 1) {
									coalesce = true;
								}
							}
						} else {
							// limited length, whitespace ignored
							if(blockSize <= BrainfuckPreferences.getMaxBasicBlockInstructions()) {
								coalesce = true;
							}
						}
					} else {
						if(BrainfuckPreferences.isIncludeWhitespaceBasicBlockDelimitersEnabled()) {
							// unlimited length, whitespace considered
							SourceCorrespondence previousSC = (SourceCorrespondence) previousInstructionNode.getAttr(XCSG.sourceCorrespondence);
							int prevOffsetEnd = previousSC.offset + previousSC.length;
							int nextOffsetEnd = instruction.getParserSourceCorrespondence().getOffset() + instruction.getParserSourceCorrespondence().getLength();
							if(nextOffsetEnd - prevOffsetEnd == 1) {
								coalesce = true;
							}
						} else {
							// unlimited length, whitespace ignored
							coalesce = true;
						}
					}
				}
			}
			
			if(coalesce) {
				if(previousInstructionNode.taggedWith(XCSG.Brainfuck.Instruction)) {
					previousInstructionNode.tags().remove(XCSG.Brainfuck.Instruction);
					previousInstructionNode.tags().remove(XCSG.Brainfuck.IncrementInstruction);
					previousInstructionNode.tags().remove(XCSG.Brainfuck.DecrementInstruction);
					previousInstructionNode.tags().remove(XCSG.Brainfuck.MoveLeftInstruction);
					previousInstructionNode.tags().remove(XCSG.Brainfuck.MoveRightInstruction);
					previousInstructionNode.tags().remove(XCSG.Brainfuck.ReadInputInstruction);
					previousInstructionNode.tags().remove(XCSG.Brainfuck.WriteOutputInstruction);
					previousInstructionNode.tag(XCSG.Brainfuck.Instructions);
				}
				String name = previousInstructionNode.getAttr(XCSG.name).toString();
				previousInstructionNode.putAttr(XCSG.name, name + instruction.getType().toString());
				SourceCorrespondence previousInstructionSC = ((SourceCorrespondence) previousInstructionNode.getAttr(XCSG.sourceCorrespondence));
				SourceCorrespondence sc = new SourceCorrespondence(previousInstructionSC.sourceFile, 
																   previousInstructionSC.offset, 
																   ((instruction.getParserSourceCorrespondence().getOffset()+instruction.getParserSourceCorrespondence().getLength())-previousInstructionSC.offset), 
																   previousInstructionSC.startLine, 
																   instruction.getParserSourceCorrespondence().getEndLine());
				previousInstructionNode.putAttr(XCSG.sourceCorrespondence, sc);
				instructionNode = previousInstructionNode;
			} else {
				blockSize = 1;
				
				// create the instruction node
				instructionNode = instruction.index(graph, containerNode, monitor);
				
				// make the container node contain the instruction
				Edge containsEdge = graph.createEdge(containerNode, instructionNode);
				containsEdge.tag(XCSG.Contains);
				
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
			
			// tag the root/exit nodes appropriately
			if(i==0) {
				instructionNode.tag(XCSG.ControlFlowRoot);
			} else if(i==(instructions.size()-1)) {
				instructionNode.tag(XCSG.ControlFlowExit);
			}
		}
		
		// return the last instruction node
		return previousInstructionNode;
	}
	
}
