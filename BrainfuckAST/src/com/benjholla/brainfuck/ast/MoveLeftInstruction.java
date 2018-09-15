package com.benjholla.brainfuck.ast;

public class MoveLeftInstruction extends Instruction {

	public MoveLeftInstruction(SourceCorrespondence sc) {
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
