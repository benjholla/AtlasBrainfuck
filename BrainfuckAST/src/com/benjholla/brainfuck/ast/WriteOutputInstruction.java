package com.benjholla.brainfuck.ast;

public class WriteOutputInstruction extends Instruction {

	public WriteOutputInstruction(SourceCorrespondence sc) {
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
