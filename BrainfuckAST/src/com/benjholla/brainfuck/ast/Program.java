package com.benjholla.brainfuck.ast;

import java.util.List;

import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;

public class Program extends ASTNode {
	private List<Instruction> instructions;

	public Program(ParserSourceCorrespondence sc, List<Instruction> instructions) {
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
		return "Program: {" + result + "}";
	}
	
}
