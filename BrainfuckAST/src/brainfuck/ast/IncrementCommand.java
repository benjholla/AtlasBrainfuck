package brainfuck.ast;

import brainfuck.SourceCorrespondence;

public class IncrementCommand extends Command {

	public IncrementCommand(SourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return "+";
	}
	
}
