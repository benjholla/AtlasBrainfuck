package brainfuck.ast;

import brainfuck.SourceCorrespondence;

public class MoveRightCommand extends Command {

	public MoveRightCommand(SourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return ">";
	}
	
}
