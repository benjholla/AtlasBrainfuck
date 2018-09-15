// Generated from Brainfuck.g4 by ANTLR 4.7.1

	package com.benjholla.brainfuck.parser; 
	
	import com.benjholla.brainfuck.parser.support.*;
	import com.benjholla.brainfuck.ast.*;
	import java.util.List;
	import java.io.File;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link BrainfuckParser}.
 */
public interface BrainfuckListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link BrainfuckParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(BrainfuckParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(BrainfuckParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuckParser#instruction_list}.
	 * @param ctx the parse tree
	 */
	void enterInstruction_list(BrainfuckParser.Instruction_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#instruction_list}.
	 * @param ctx the parse tree
	 */
	void exitInstruction_list(BrainfuckParser.Instruction_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuckParser#instruction}.
	 * @param ctx the parse tree
	 */
	void enterInstruction(BrainfuckParser.InstructionContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#instruction}.
	 * @param ctx the parse tree
	 */
	void exitInstruction(BrainfuckParser.InstructionContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuckParser#loop}.
	 * @param ctx the parse tree
	 */
	void enterLoop(BrainfuckParser.LoopContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#loop}.
	 * @param ctx the parse tree
	 */
	void exitLoop(BrainfuckParser.LoopContext ctx);
}