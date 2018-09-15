package com.benjholla.brainfuck.ast;

import com.benjholla.atlas.brainfuck.common.SourceCorrespondence;

public class IncrementInstruction extends Instruction {

	public IncrementInstruction(SourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return "+";
	}
	
	@Override
	public Type getType() {
		return Type.INCREMENT;
	}
	
}
