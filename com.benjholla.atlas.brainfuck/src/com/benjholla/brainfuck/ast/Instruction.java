package com.benjholla.brainfuck.ast;

import org.eclipse.core.runtime.SubMonitor;

import com.benjholla.atlas.brainfuck.frontend.XCSG;
import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;
import com.ensoftcorp.atlas.core.db.graph.Edge;
import com.ensoftcorp.atlas.core.db.graph.EditableGraph;
import com.ensoftcorp.atlas.core.db.graph.Node;

public abstract class Instruction extends ASTNode {

	public static enum Type {
		INCREMENT("+"), 
		DECREMENT("-"),
		MOVE_RIGHT(">"),
		MOVE_LEFT("<"),
		READ(","),
		WRITE("."),
		LOOP("[");
		
		private String name;
		
		private Type(String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
	}
	
	public abstract Type getType();
	
	public Instruction(ParserSourceCorrespondence sc) {
		super(sc);
	}
	
	@Override
	public Node index(EditableGraph graph, Node containerNode, SubMonitor monitor) {
		// create the instruction node
		Node instructionNode = graph.createNode();
		instructionNode.tag(XCSG.ControlFlow_Node);
		instructionNode.putAttr(XCSG.name, getType().toString());
		
		// make the container node contain the loop header
		Edge containsEdge = graph.createEdge(containerNode, instructionNode);
		containsEdge.tag(XCSG.Contains);
		
		return instructionNode;
	}

}
