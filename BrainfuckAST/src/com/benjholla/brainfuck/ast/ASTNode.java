package com.benjholla.brainfuck.ast;

public abstract class ASTNode {
	
	/**
	 * The corresponding source code
	 */
	protected SourceCorrespondence sc;
	
	/**
	 * Builds a new tree node
	 *
	 * @param lineNumber
	 *            the line in the source file from which this node came.
	 */
	protected ASTNode(SourceCorrespondence sc) {
		this.sc = sc;
	}
	
	/**
	 * Sets the values of this node object to the values of a given node.
	 *
	 * @param other
	 *            the other node
	 * @return this node
	 */
	public ASTNode set(ASTNode other) {
		this.sc = other.sc;
		return this;
	}

	/**
	 * Returns the corresponding source code this ASTNode represents
	 *
	 * @return the line number
	 */
	public SourceCorrespondence getSourceCorrespondence() {
		return sc;
	}
	
	public String toString(){
		return "ASTNode " + this.getClass().getSimpleName() + " [" + sc.toString() + "]";
	}

}