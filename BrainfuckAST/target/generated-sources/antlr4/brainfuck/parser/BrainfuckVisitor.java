// Generated from Brainfuck.g4 by ANTLR 4.7.1

	package brainfuck.parser; 
	
	import brainfuck.*;
	import brainfuck.ast.*;
	import java.util.List;
	import java.io.File;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link BrainfuckParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface BrainfuckVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link BrainfuckParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(BrainfuckParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link BrainfuckParser#command_list}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand_list(BrainfuckParser.Command_listContext ctx);
	/**
	 * Visit a parse tree produced by {@link BrainfuckParser#command}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCommand(BrainfuckParser.CommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link BrainfuckParser#loop}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoop(BrainfuckParser.LoopContext ctx);
}