package brainfuck.ast;

import brainfuck.SourceCorrespondence;

public class MoveLeftCommand extends Command {

	public MoveLeftCommand(SourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return "<";
	}
	
}
