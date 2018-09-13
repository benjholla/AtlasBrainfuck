grammar Brainfuck;

@header{
	package brainfuck.parser; 
	
	import brainfuck.*;
	import brainfuck.ast.*;
	import java.util.List;
	import java.io.File;
}

@members{
	private File file;
	
	public void setFile(File file){
		this.file = file;
	}
	
	public File getFile(){
		return file;
	}
}

program returns [Program prog]
   : commands=command_list EOF
   {
   	  SourceCorrespondence sc;
   	  if($commands.list.isEmpty()){
   	     sc = new SourceCorrespondence(file, 0, 0, 0);
   	  } else {
   	     SourceCorrespondence firstCommandSC = $commands.list.get(0).getSourceCorrespondence();
   	     sc = new SourceCorrespondence(file, firstCommandSC.getLine(), firstCommandSC.getOffset(), _ctx.getStart().getText().length()); 
   	  }
      $prog = new Program(sc, $commands.list);
   }
   ;

command_list returns [ArrayList<Command> list]
   @init
   {
      $list = new ArrayList<Command>();
   }
   : (c = command {$list.add($c.value);})*
   ;

command returns [Command value] 
   : l=loop
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new LoopCommand(sc, $l.list);
   }
   | ti=TAPE_INCREMENT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new IncrementCommand(sc);
   }
   | td=TAPE_DECREMENT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new DecrementCommand(sc);
   }
   | tl=TAPE_LEFT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new MoveLeftCommand(sc);
   }
   | tr=TAPE_RIGHT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new MoveRightCommand(sc);
   }
   | i=INPUT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new InputCommand(sc);
   }
   | o=OUTPUT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new OutputCommand(sc);
   }
   ;
         
loop returns [ArrayList<Command> list]
   : '[' commands=command_list ']'
   {
      $list = $commands.list;
   }
   ;

TAPE_INCREMENT : '+';
TAPE_DECREMENT : '-';
TAPE_LEFT      : '<';
TAPE_RIGHT     : '>';
INPUT          : ',';
OUTPUT         : '.';
WHITESPACE : . -> skip;