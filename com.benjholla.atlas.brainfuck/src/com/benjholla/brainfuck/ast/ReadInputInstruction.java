package com.benjholla.brainfuck.ast;

import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;

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
	
}
