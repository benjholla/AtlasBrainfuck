package com.benjholla.brainfuck.ast;

import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;

public class MoveLeftInstruction extends Instruction {

	public MoveLeftInstruction(ParserSourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return "<";
	}
	
	@Override
	public Type getType() {
		return Type.MOVE_LEFT;
	}
	
}
