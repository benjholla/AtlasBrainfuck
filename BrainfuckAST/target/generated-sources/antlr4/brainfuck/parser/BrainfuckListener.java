// Generated from Brainfuck.g4 by ANTLR 4.7.1

	package brainfuck.parser; 
	
	import brainfuck.*;
	import brainfuck.ast.*;
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
	 * Enter a parse tree produced by {@link BrainfuckParser#command_list}.
	 * @param ctx the parse tree
	 */
	void enterCommand_list(BrainfuckParser.Command_listContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#command_list}.
	 * @param ctx the parse tree
	 */
	void exitCommand_list(BrainfuckParser.Command_listContext ctx);
	/**
	 * Enter a parse tree produced by {@link BrainfuckParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(BrainfuckParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link BrainfuckParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(BrainfuckParser.CommandContext ctx);
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