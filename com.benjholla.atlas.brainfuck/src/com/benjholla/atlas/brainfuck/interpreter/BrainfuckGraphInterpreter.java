package com.benjholla.atlas.brainfuck.interpreter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import com.benjholla.atlas.brainfuck.common.XCSG;
import com.ensoftcorp.atlas.core.db.graph.Edge;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.script.CommonQueries;

public class BrainfuckGraphInterpreter {

	/**
	 * Executes a brainfuck program with an empty input, returning the output as a string
	 * 
	 * @param program
	 * @param input
	 * @param output
	 * 
	 * @throws IOException
	 */
	public static String execute(Q program) throws IOException {
		return execute(program, "");
	}
	
	/**
	 * Executes a brainfuck program with the given input and returning the output as as string
	 * 
	 * @param program
	 * @param input
	 * @param output
	 * 
	 * @throws IOException
	 */
	public static String execute(Q program, String input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		execute(program, new ByteArrayInputStream(input.getBytes()), output, null);
		return new String(output.toByteArray(), "UTF-8");
	}
	
	/**
	 * Executes a brainfuck program with the given input, output, and debug streams
	 * 
	 * @param program
	 * @param input
	 * @param output
	 * 
	 * @throws IOException
	 */
	public static void execute(Q program, InputStream input, OutputStream output) throws IOException {
		execute(program, input, output, null);
	}
	
	/**
	 * Executes a brainfuck program with the given input, output, and debug streams
	 * 
	 * @param program
	 * @param input
	 * @param output
	 * @param debug
	 * 
	 * @throws IOException
	 */
	public static void execute(Q program, InputStream input, OutputStream output, OutputStream debug) throws IOException {
		AtlasSet<Node> nodes = program.eval().nodes();
		if(!nodes.isEmpty()) {
			execute(nodes.one(), input, output, debug);
		} else {
			throw new IllegalArgumentException("No program nodes were specified");
		}
	}
	
	/**
	 * Executes a brainfuck program with an empty input, returning the output as a string
	 * 
	 * @param program
	 * @param input
	 * @param output
	 * 
	 * @throws IOException
	 */
	public static String execute(Node program) throws IOException {
		return execute(program);
	}
	
	/**
	 * Executes a brainfuck program with the given input, output, and debug streams
	 * 
	 * @param program
	 * @param input
	 * @param output
	 * 
	 * @throws IOException
	 */
	public static String execute(Node program, String input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		execute(program, new ByteArrayInputStream(input.getBytes()), output, null);
		return new String(output.toByteArray(), "UTF-8");
	}
	
	/**
	 * Executes a brainfuck program with the given input, output, and debug streams
	 * 
	 * @param program
	 * @param input
	 * @param output
	 * 
	 * @throws IOException
	 */
	public static void execute(Node program, InputStream input, OutputStream output) throws IOException {
		execute(program, input, output, null);
	}
	
	/**
	 * Executes a brainfuck program with the given input, output, and debug streams
	 * 
	 * @param program
	 * @param input
	 * @param output
	 * @param debug
	 * 
	 * @throws IOException
	 */
	public static void execute(Node program, InputStream input, OutputStream output, OutputStream debug) throws IOException {
		if(program.taggedWith(XCSG.Project)) {
			AtlasSet<Node> namespaces = Common.toQ(program).children().eval().nodes();
			if(namespaces.isEmpty()) {
				throw new IllegalArgumentException("Project does not contain any Brainfuck programs to execute.");
			} else if(namespaces.size() == 1) {
				program = namespaces.one();
			} else {
				throw new IllegalArgumentException("Project contains multiple brainfuck projects, please select a specific Brainfuck program.");
			}
		}
		
		if(program.taggedWith(XCSG.Namespace)) {
			AtlasSet<Node> implicitFunctions = Common.toQ(program).eval().nodes();
			if(implicitFunctions.isEmpty()) {
				throw new IllegalArgumentException("Project namespace (" + program.getAttr(XCSG.name) + ") is missing an implicit function.");
			} else if(implicitFunctions.size() > 1) {
				throw new IllegalArgumentException("Project namespace (" + program.getAttr(XCSG.name) + ") has multiple implicit functions.");
			} else {
				program = implicitFunctions.one();
			}
		}
		
		if(program.taggedWith(XCSG.ImplictFunction)) {
			AtlasSet<Node> controlFlowRoots = Common.toQ(program).eval().nodes();
			if(controlFlowRoots.isEmpty()) {
				throw new IllegalArgumentException("Implict function (" + program.getAttr(XCSG.name) + ") is missing a control flow roots.");
			} else if(controlFlowRoots.size() > 1) {
				throw new IllegalArgumentException("Implict function (" + program.getAttr(XCSG.name) + ") contains multiple control flow roots.");
			} else {
				program = controlFlowRoots.one();
			}
		}
		
		if(!program.taggedWith(XCSG.ControlFlow_Node)) {
			throw new IllegalArgumentException("Invalid program node. Please provide a Brainfuck project, namespace, "
					+ "implicit function, or control flow node (root or other control flow node to being program "
					+ "execution at a given program point)");
		} else {
			// execute the program at the given program point
			Node nextInstruction = program;
			
			// initialize the tape to 1 cell with value 0
			// memory is unbounded in this implementation
			ArrayList<Byte> memory = new ArrayList<Byte>();
			memory.add((byte)0x00);
			
			// initialize the memory pointer (index into memory)
			int mp = 0;
			
			// execute instructions until there are no more to execute
			Q cfg = Common.toQ(CommonQueries.cfg(Common.toQ(nextInstruction).parent()).eval());
			Q loopChildEdges = Common.toQ(cfg.retainNodes().induce(Common.universe().edges(XCSG.LoopChild)).retainEdges().eval());
			do {
				if(nextInstruction.taggedWith(XCSG.Operator)) {
					if(nextInstruction.taggedWith(XCSG.Brainfuck.IncrementOperator)) {
						byte incrementValue = memory.remove(mp);
						memory.add(mp, ++incrementValue);
					} else if(nextInstruction.taggedWith(XCSG.Brainfuck.DecrementOperator)) {
						byte decrementValue = memory.remove(mp);
						memory.add(mp, --decrementValue);
					} else if(nextInstruction.taggedWith(XCSG.Brainfuck.MoveLeftOperator)) {
						mp = (mp>0) ? mp-1 : 0;
					} else if(nextInstruction.taggedWith(XCSG.Brainfuck.MoveRightOperator)) {
						mp++;
						if(mp == memory.size()){
							// we have reached the end of the tape, grow by one cell
							memory.add((byte)0x00);
						}
					} else if(nextInstruction.taggedWith(XCSG.Brainfuck.ReadInputOperator)) {
						memory.remove(mp);
						byte[] bytes = new byte[1];
						if(input != null) {
							input.read(bytes);
						}
						memory.add(mp, bytes[0]);
					} else if(nextInstruction.taggedWith(XCSG.Brainfuck.WriteOutputOperator)) {
						output.write(new byte[] {memory.get(mp)});
					} else {
						throw new IllegalArgumentException("Error: Control flow operator [" + nextInstruction.address().toAddressString() + "] was an unknown operator type.");
					}
					
					AtlasSet<Node> successors = cfg.successors(Common.toQ(nextInstruction)).eval().nodes();
					if(successors.size() > 1) {
						throw new IllegalArgumentException("Error: Control flow operator node [" + nextInstruction.address().toAddressString() + "] unexpectedly had multiple successors.");
					} else {
						nextInstruction = successors.one();
					}
				} else {
					Q conditionalEdges = cfg.forwardStep(Common.toQ(nextInstruction));
					if (memory.get(mp) == 0) {
						Edge falseEdge = conditionalEdges.selectEdge(XCSG.conditionValue, false, "false").eval().edges().one();
						if(falseEdge != null) {
							nextInstruction = falseEdge.to();
						} else {
							if (!CommonQueries.isEmpty(loopChildEdges.predecessors(Common.toQ(nextInstruction)).nodes(XCSG.ControlFlowExit))) {
								// only a problem if the loop is the last statement
								throw new IllegalArgumentException("Error: Control flow loop false edge could not be found for [" + nextInstruction.address().toAddressString() + "].");
							} else {
								nextInstruction = null;
							}
						}
					} else {
						Edge trueEdge = conditionalEdges.selectEdge(XCSG.conditionValue, true, "true").eval().edges().one();
						if(trueEdge != null) {
							nextInstruction = trueEdge.to();
						} else {
							throw new IllegalArgumentException("Error: Control flow loop true edge could not be found for [" + nextInstruction.address().toAddressString()  + "].");
						}
					}
				}
			} while(nextInstruction != null);
		}
	}
	
}
