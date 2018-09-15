package com.benjholla.brainfuck.ast;

import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;

public abstract class Instruction extends ASTNode {

	public static enum Type {
		INCREMENT("+"), 
		DECREMENT("-"),
		MOVE_RIGHT(">"),
		MOVE_LEFT("<"),
		READ(","),
		WRITE("."),
		LOOP("[");
		
		private String name;
		
		private Type(String name) {
			this.name = name;
		}
		
		public String toString() {
			return name;
		}
	}
	
	public abstract Type getType();
	
	public Instruction(ParserSourceCorrespondence sc) {
		super(sc);
	}

}
