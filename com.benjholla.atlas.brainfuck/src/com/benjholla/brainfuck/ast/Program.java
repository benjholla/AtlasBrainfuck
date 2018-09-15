package com.benjholla.brainfuck.ast;

import java.util.List;

import org.eclipse.core.runtime.SubMonitor;

import com.benjholla.atlas.brainfuck.common.SourceCorrespondence;
import com.benjholla.atlas.brainfuck.frontend.XCSG;
import com.ensoftcorp.atlas.core.db.graph.Edge;
import com.ensoftcorp.atlas.core.db.graph.EditableGraph;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;

public class Program extends ASTNode {
	private List<Instruction> instructions;

	public Program(SourceCorrespondence sc, List<Instruction> instructions) {
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
		Node previousInstructionNode = null;
		for(int i=0; i<instructions.size(); i++) {
			Instruction instruction = instructions.get(i);
			
			// create the instruction node
			Node instructionNode = instruction.index(graph, containerNode, monitor);
			
			// make the container node contain the instruction
			Edge containsEdge = graph.createEdge(containerNode, instructionNode);
			containsEdge.tag(XCSG.Contains);
			
			// tag the root/exit nodes appropriately
			if(i==0) {
				instructionNode.tag(XCSG.ControlFlowRoot);
			} else if(i==(instructions.size()-1)) {
				instructionNode.tag(XCSG.ControlFlowExit);
			}
			
			// create the control flow edge relationship if this isn't the first instruction
			if(previousInstructionNode != null) {
				Edge controlFlowEdge = graph.createEdge(previousInstructionNode, instructionNode);
				controlFlowEdge.tag(XCSG.ControlFlow_Edge);
				if(previousInstructionNode.taggedWith(XCSG.Loop)) {
					Node header = previousInstructionNode;
					controlFlowEdge.putAttr(XCSG.conditionValue, true); // any non-zero value
					
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
		
		// return the last instruction node
		return previousInstructionNode;
	}
	
}
