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
      long startPos = _ctx.getStart().getCharPositionInLine();
      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
      $value = new LoopCommand(sc, $l.list);
   }
   | ti=TAPE_INCREMENT
   {
      long startPos = _ctx.getStart().getCharPositionInLine();
      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
      $value = new IncrementCommand(sc);
   }
   | td=TAPE_DECREMENT
   {
      long startPos = _ctx.getStart().getCharPositionInLine();
      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
      $value = new DecrementCommand(sc);
   }
   | tl=TAPE_LEFT
   {
      long startPos = _ctx.getStart().getCharPositionInLine();
      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
      $value = new MoveLeftCommand(sc);
   }
   | tr=TAPE_RIGHT
   {
      long startPos = _ctx.getStart().getCharPositionInLine();
      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
      $value = new MoveRightCommand(sc);
   }
   | i=INPUT
   {
      long startPos = _ctx.getStart().getCharPositionInLine();
      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
      $value = new InputCommand(sc);
   }
   | o=OUTPUT
   {
      long startPos = _ctx.getStart().getCharPositionInLine();
      long stopPos = _ctx.getStop() != null ? _ctx.getStop().getCharPositionInLine() : startPos + 1;
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), startPos, (stopPos-startPos));
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