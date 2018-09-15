package com.benjholla.atlas.brainfuck.frontend;

/**
 * A wrapper and extension for relevant XCSG tag and attribute constants
 * 
 * @author Ben Holland
 */
public class XCSG {

	public static class Language {
		public static final String Brainfuck = "XCSG.Language.Brainfuck";
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
	
	// edges
	public static final String Contains = com.ensoftcorp.atlas.core.xcsg.XCSG.Contains;
	public static final String ControlFlow_Edge = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlow_Edge;
	public static final String ControlFlowBackEdge = com.ensoftcorp.atlas.core.xcsg.XCSG.ControlFlowBackEdge;
	public static final String LoopChild = com.ensoftcorp.atlas.core.xcsg.XCSG.LoopChild;
	
	// attributes
	public static String name = com.ensoftcorp.atlas.core.xcsg.XCSG.name;
	public static final String conditionValue = com.ensoftcorp.atlas.core.xcsg.XCSG.conditionValue;
	
}
