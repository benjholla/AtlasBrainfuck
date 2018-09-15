// Generated from Brainfuck.g4 by ANTLR 4.7.1

package com.benjholla.brainfuck.parser;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BrainfuckParser}.
 *
 * @param <T>
 *            The return type of the visit operation. Use {@link Void} for
 *            operations with no return type.
 */
public interface BrainfuckVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BrainfuckParser#program}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitProgram(BrainfuckParser.ProgramContext ctx);

	/**
	 * Visit a parse tree produced by {@link BrainfuckParser#instruction_list}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitInstruction_list(BrainfuckParser.Instruction_listContext ctx);

	/**
	 * Visit a parse tree produced by {@link BrainfuckParser#instruction}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitInstruction(BrainfuckParser.InstructionContext ctx);

	/**
	 * Visit a parse tree produced by {@link BrainfuckParser#loop}.
	 * 
	 * @param ctx
	 *            the parse tree
	 * @return the visitor result
	 */
	T visitLoop(BrainfuckParser.LoopContext ctx);
}