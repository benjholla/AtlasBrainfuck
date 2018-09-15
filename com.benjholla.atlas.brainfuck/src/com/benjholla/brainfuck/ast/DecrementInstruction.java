package com.benjholla.brainfuck.ast;

import com.benjholla.atlas.brainfuck.common.SourceCorrespondence;

public class DecrementInstruction extends Instruction {

	public DecrementInstruction(SourceCorrespondence sc) {
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
