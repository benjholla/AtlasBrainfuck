package com.benjholla.brainfuck.ast;

import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;

public class WriteOutputInstruction extends Instruction {

	public WriteOutputInstruction(ParserSourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return ".";
	}
	
	@Override
	public Type getType() {
		return Type.WRITE;
	}
	
}
