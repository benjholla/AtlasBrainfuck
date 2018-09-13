package brainfuck.ast;

import brainfuck.SourceCorrespondence;

public abstract class Command extends ASTNode {

	public Command(SourceCorrespondence sc) {
		super(sc);
	}

}
