package com.benjholla.brainfuck.ast;

import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;

public class DecrementInstruction extends Instruction {

	public DecrementInstruction(ParserSourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return "-";
	}

	@Override
	public Type getType() {
		return Type.DECREMENT;
	}
	
}
