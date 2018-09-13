package brainfuck.ast;

import brainfuck.SourceCorrespondence;

public class DecrementCommand extends Command {

	public DecrementCommand(SourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return "-";
	}
	
}
