package com.benjholla.brainfuck.ast;

public class MoveRightInstruction extends Instruction {

	public MoveRightInstruction(SourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return ">";
	}
	
	@Override
	public Type getType() {
		return Type.MOVE_RIGHT;
	}
	
}
