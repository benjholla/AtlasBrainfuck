package brainfuck.ast;

import brainfuck.SourceCorrespondence;

public class InputCommand extends Command {

	public InputCommand(SourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return ",";
	}
	
}
