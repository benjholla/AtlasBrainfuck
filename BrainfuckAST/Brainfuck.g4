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
      SourceCorrespondence sc = new SourceCorrespondence(file, 0, 0, 0);
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
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getCharPositionInLine(), (1 + $l.list.size() + 1));
      $value = new LoopCommand(sc, $l.list);
   }
   | ti=TAPE_INCREMENT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getCharPositionInLine(), 1);
      $value = new IncrementCommand(sc);
   }
   | td=TAPE_DECREMENT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getCharPositionInLine(), 1);
      $value = new DecrementCommand(sc);
   }
   | tl=TAPE_LEFT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getCharPositionInLine(), 1);
      $value = new MoveLeftCommand(sc);
   }
   | tr=TAPE_RIGHT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getCharPositionInLine(), 1);
      $value = new MoveRightCommand(sc);
   }
   | i=INPUT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getCharPositionInLine(), 1);
      $value = new InputCommand(sc);
   }
   | o=OUTPUT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getCharPositionInLine(), 1);
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