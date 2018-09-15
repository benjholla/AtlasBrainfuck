package com.benjholla.brainfuck.ast;
import com.benjholla.atlas.brainfuck.common.SourceCorrespondence;

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
