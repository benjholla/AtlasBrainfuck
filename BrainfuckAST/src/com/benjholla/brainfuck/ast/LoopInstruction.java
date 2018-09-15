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
		StringBuilder cmds = new StringBuilder();
		for(Instruction instruction : instructions) {
			if(instruction instanceof LoopInstruction) {
				cmds.append(" ");
			}
			cmds.append(instruction.toString());
		}
		return "Loop: [" + cmds + "]";
	}
	
	@Override
	public Type getType() {
		return Type.LOOP;
	}
}