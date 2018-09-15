package com.benjholla.brainfuck.ast;

public class ReadInputInstruction extends Instruction {

	public ReadInputInstruction(SourceCorrespondence sc) {
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
