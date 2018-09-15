package com.benjholla.atlas.brainfuck.common;

/**
 * A wrapper and extension for relevant XCSG tag and attribute constants
 * 
 * @author Ben Holland
 */
public class XCSG {

	public static class Language {
		public static final String Brainfuck = "XCSG.Language.Brainfuck";
	}
	
	public static class Brainfuck {
		private static boolean isTagHierarchyInitialized = false;
		
		// nodes
		public static final String IncrementOperator = "XCSG.Brainfuck.IncrementOperator";
		public static final String DecrementOperator = "XCSG.Brainfuck.DecrementOperator";
		public static final String MoveLeftOperator = "XCSG.Brainfuck.MoveLeftOperator";
		public static final String MoveRightOperator = "XCSG.Brainfuck.MoveRightOperator";
		public static final String ReadInputOperator = "XCSG.Brainfuck.ReadInputOperator";
		public static final String WriteOutputOperator = "XCSG.Brainfuck.WriteOutputOperator";

		public static void registerSchema(){
			if(!isTagHierarchyInitialized){
				isTagHierarchyInitialized = true;
				com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(IncrementOperator, com.ensoftcorp.atlas.core.xcsg.XCSG.Operator);
				com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(DecrementOperator, com.ensoftcorp.atlas.core.xcsg.XCSG.Operator);
				com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(MoveLeftOperator, com.ensoftcorp.atlas.core.xcsg.XCSG.Operator);
				com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(MoveRightOperator, com.ensoftcorp.atlas.core.xcsg.XCSG.Operator);
				com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(ReadInputOperator, com.ensoftcorp.atlas.core.xcsg.XCSG.Operator);
				com.ensoftcorp.atlas.core.xcsg.XCSG.HIERARCHY.registerTag(WriteOutputOperator, com.ensoftcorp.atlas.core.xcsg.XCSG.Operator);
			}
		}
	}
	
	// nodes
	public static final String Project = com.ensoftcorp.atlas.core.xcsg.XCSG.Project;
	public static final String Namespace = com.ensoftcorp.atlas.core.xcsg.XCSG.Namespace;
	public static final String ImplictFunction = com.ensoftcorp.atlas.core.xcsg.XCSG.Function;
	public static final String ControlFlow_Node = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlow_Node;
	public static final String ControlFlowRoot = com.ensoftcorp.atlas.core.xcsg.XCSG.controlFlowRoot;
	public static final String ControlFlowExit = com.ensoftcorp.atlas.core.xcsg.XCSG.controlFlowExitPoint;
	public static final String ControlFlowCondition = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlowCondition;
	public static final String Loop = com.ensoftcorp.atlas.core.xcsg.XCSG.Loop;
	public static final String Operator = com.ensoftcorp.atlas.core.xcsg.XCSG.Operator;
	
	// edges
	public static final String Contains = com.ensoftcorp.atlas.core.xcsg.XCSG.Contains;
	public static final String ControlFlow_Edge = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlow_Edge;
	public static final String ControlFlowBackEdge = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlowBackEdge;
	public static final String LoopChild = com.ensoftcorp.atlas.core.xcsg.XCSG.LoopChild;
	
	// attributes
	public static String name = com.ensoftcorp.atlas.core.xcsg.XCSG.name;
	public static final String conditionValue = com.ensoftcorp.atlas.core.xcsg.XCSG.conditionValue;
	public static final String sourceCorrespondence = com.ensoftcorp.atlas.core.xcsg.XCSG.sourceCorrespondence;
	
}
