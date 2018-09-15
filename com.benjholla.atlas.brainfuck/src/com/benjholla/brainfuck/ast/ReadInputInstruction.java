package com.benjholla.brainfuck.ast;

import org.eclipse.core.runtime.SubMonitor;

import com.benjholla.atlas.brainfuck.common.XCSG;
import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;
import com.ensoftcorp.atlas.core.db.graph.EditableGraph;
import com.ensoftcorp.atlas.core.db.graph.Node;

public class ReadInputInstruction extends Instruction {

	public ReadInputInstruction(ParserSourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return ",";
	}
	
	@Override
	public Type getType() {
		return Type.READ;
	}
	
	@Override
	public Node index(EditableGraph graph, Node containerNode, SubMonitor monitor) {
		// create the instruction node
		Node instructionNode = super.index(graph, containerNode, monitor);
		instructionNode.tag(XCSG.Brainfuck.ReadInputOperator);
		return instructionNode;
	}
	
}