package com.benjholla.atlas.brainfuck.common;

import org.eclipse.ui.IStartup;

import com.benjholla.atlas.brainfuck.log.Log;

/**
 * A wrapper and extension for relevant XCSG tag and attribute constants
 * 
 * @author Ben Holland
 */
public class XCSG implements IStartup {

	public static class Language {
		public static final String Brainfuck = "XCSG.Language.Brainfuck";
	}
	
	public static class Brainfuck {
		// nodes
		public static final String ImplictFunction = "XCSG.Brainfuck.ImplictFunction";
		public static final String LoopFooter = "XCSG.Brainfuck.LoopFooter";
		public static final String Instruction = "XCSG.Brainfuck.Instruction";
		public static final String Instructions = "XCSG.Brainfuck.Instructions"; // just used for coalesced basic blocks
		public static final String IncrementInstruction = "XCSG.Brainfuck.IncrementInstruction";
		public static final String DecrementInstruction = "XCSG.Brainfuck.DecrementInstruction";
		public static final String MoveLeftInstruction = "XCSG.Brainfuck.MoveLeftInstruction";
		public static final String MoveRightInstruction = "XCSG.Brainfuck.MoveRightInstruction";
		public static final String ReadInputInstruction = "XCSG.Brainfuck.ReadInputInstruction";
		public static final String WriteOutputInstruction = "XCSG.Brainfuck.WriteOutputInstruction";
	}
	
	// nodes
	public static final String Project = com.ensoftcorp.atlas.core.xcsg.XCSG.Project;
	public static final String Namespace = com.ensoftcorp.atlas.core.xcsg.XCSG.Namespace;
	public static final String Function = com.ensoftcorp.atlas.core.xcsg.XCSG.Function;
	public static final String ControlFlow_Node = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlow_Node;
	public static final String ControlFlowRoot = com.ensoftcorp.atlas.core.xcsg.XCSG.controlFlowRoot;
	public static final String ControlFlowExit = com.ensoftcorp.atlas.core.xcsg.XCSG.controlFlowExitPoint;
	public static final String ControlFlowCondition = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlowCondition;
	public static final String Loop = com.ensoftcorp.atlas.core.xcsg.XCSG.Loop;
	
	// edges
	public static final String Contains = com.ensoftcorp.atlas.core.xcsg.XCSG.Contains;
	public static final String ControlFlow_Edge = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlow_Edge;
	public static final String ControlFlowBackEdge = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlowBackEdge;
	public static final String LoopChild = com.ensoftcorp.atlas.core.xcsg.XCSG.LoopChild;
	
	// attributes
	public static String name = com.ensoftcorp.atlas.core.xcsg.XCSG.name;
	public static final String conditionValue = com.ensoftcorp.atlas.core.xcsg.XCSG.conditionValue;
	public static final String sourceCorrespondence = com.ensoftcorp.atlas.core.xcsg.XCSG.sourceCorrespondence;
	
	@Override
	public void earlyStartup() {
		try {
			registerSchema();
		} catch (Exception e){
			Log.error("Unable to build tag hierarchy.", e);
		}
	}
	
	private static boolean isTagHierarchyInitialized = false;
	
	public static void registerSchema(){
		if(!isTagHierarchyInitialized){
			isTagHierarchyInitialized = true;
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.ImplictFunction, XCSG.Function);
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.Instruction, XCSG.ControlFlow_Node);
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.Instructions, XCSG.ControlFlow_Node);
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.IncrementInstruction, XCSG.Brainfuck.Instruction);
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.DecrementInstruction, XCSG.Brainfuck.Instruction);
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.MoveLeftInstruction, XCSG.Brainfuck.Instruction);
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.MoveRightInstruction, XCSG.Brainfuck.Instruction);
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.ReadInputInstruction, XCSG.Brainfuck.Instruction);
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.WriteOutputInstruction, XCSG.Brainfuck.Instruction);
			com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(XCSG.Brainfuck.LoopFooter, XCSG.ControlFlowCondition);
		}
	}
	
}
