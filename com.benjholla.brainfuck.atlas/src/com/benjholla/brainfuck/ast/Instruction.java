package com.benjholla.brainfuck.ast;

import org.eclipse.core.runtime.SubMonitor;

import com.benjholla.brainfuck.atlas.common.XCSG;
import com.benjholla.brainfuck.atlas.indexer.WorkspaceUtils;
import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;
import com.ensoftcorp.atlas.core.db.graph.Edge;
import com.ensoftcorp.atlas.core.db.graph.EditableGraph;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.index.common.SourceCorrespondence;

public abstract class Instruction extends ASTNode {

	public static enum Type {
		INCREMENT('+'), 
		DECREMENT('-'),
		MOVE_RIGHT('>'),
		MOVE_LEFT('<'),
		READ(','),
		WRITE('.'),
		LOOP('[');
		
		private char name;
		
		private Type(char name) {
			this.name = name;
		}
		
		public char getName() {
			return name;
		}
		
		public String toString() {
			return "" + name;
		}
	}
	
	public abstract Type getType();
	
	public Instruction(ParserSourceCorrespondence sc) {
		super(sc);
	}
	
	@Override
	public Node index(EditableGraph graph, Node containerNode, SubMonitor monitor) {
		// create the instruction node
		Node instructionNode = graph.createNode();
		instructionNode.putAttr(XCSG.name, getType().toString());
		
		// set the instruction node's source correspondence
		SourceCorrespondence sc = new SourceCorrespondence(WorkspaceUtils.getFile(psc.getSource()), psc.getOffset(), psc.getLength(), psc.getStartLine(), psc.getEndLine());
		instructionNode.putAttr(XCSG.sourceCorrespondence, sc);
		
		// make the container node contain the loop header
		Edge containsEdge = graph.createEdge(containerNode, instructionNode);
		containsEdge.tag(XCSG.Contains);
		
		return instructionNode;
	}

}
