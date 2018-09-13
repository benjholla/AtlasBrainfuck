package brainfuck.ast;

import java.util.List;

import brainfuck.SourceCorrespondence;

public class LoopCommand extends Command {

	private List<Command> commands;

	public LoopCommand(SourceCorrespondence sc, List<Command> commands) {
		super(sc);
		this.commands = commands;
	}
	
	public List<Command> getCommands(){
		return commands;
	}

	@Override
	public String toString() {
		StringBuilder cmds = new StringBuilder();
		for(Command command : commands) {
			if(command instanceof LoopCommand) {
				cmds.append(" ");
			}
			cmds.append(command.toString());
		}
		return "Loop: [" + cmds + "]";
	}
}
