package com.benjholla.brainfuck.ast;

import java.util.List;

public class LoopInstruction extends Instruction {

	private List<Instruction> instructions;

	public LoopInstruction(SourceCorrespondence sc, List<Instruction> instructions) {
		super(sc);
		this.instructions = instructions;
	}
	
	public List<Instruction> getInstructions(){
		return instructions;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for(Instruction instruction : instructions) {
			if(instruction instanceof LoopInstruction) {
				result.append(" ");
			}
			result.append(instruction.toString());
		}
		return "Loop: [" + result + "]";
	}
	
	@Override
	public Type getType() {
		return Type.LOOP;
	}
}
