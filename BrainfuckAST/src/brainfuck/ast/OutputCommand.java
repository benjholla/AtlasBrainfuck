package brainfuck.ast;

import brainfuck.SourceCorrespondence;

public class OutputCommand extends Command {

	public OutputCommand(SourceCorrespondence sc) {
		super(sc);
	}

	public String toString(){
		return ".";
	}
	
}
