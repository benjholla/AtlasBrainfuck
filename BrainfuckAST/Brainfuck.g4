grammar Brainfuck;

@header{
	package brainfuck.parser; 
	
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
   : instructions=instruction_list EOF
   {
   	  SourceCorrespondence sc;
   	  if($instructions.list.isEmpty()){
   	     sc = new SourceCorrespondence(file, 0, 0, 0);
   	  } else {
   	     SourceCorrespondence firstInstructionSC = $instructions.list.get(0).getSourceCorrespondence();
   	     sc = new SourceCorrespondence(file, firstInstructionSC.getLine(), firstInstructionSC.getOffset(), _ctx.getStart().getText().length()); 
   	  }
      $prog = new Program(sc, $instructions.list);
   }
   ;

instruction_list returns [ArrayList<Instruction> list]
   @init
   {
      $list = new ArrayList<Instruction>();
   }
   : (c = instruction {$list.add($c.value);})*
   ;

instruction returns [Instruction value] 
   : l=loop
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new LoopInstruction(sc, $l.list);
   }
   | ti=TAPE_INCREMENT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new IncrementInstruction(sc);
   }
   | td=TAPE_DECREMENT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new DecrementInstruction(sc);
   }
   | tl=TAPE_LEFT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new MoveLeftInstruction(sc);
   }
   | tr=TAPE_RIGHT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new MoveRightInstruction(sc);
   }
   | i=INPUT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new ReadInputInstruction(sc);
   }
   | o=OUTPUT
   {
      SourceCorrespondence sc = new SourceCorrespondence(file, _ctx.getStart().getLine(), _ctx.getStart().getStartIndex(), _ctx.getStart().getText().length());
      $value = new WriteOutputInstruction(sc);
   }
   ;
         
loop returns [ArrayList<Instruction> list]
   : '[' instructions=instruction_list ']'
   {
      $list = $instructions.list;
   }
   ;

TAPE_INCREMENT : '+';
TAPE_DECREMENT : '-';
TAPE_LEFT      : '<';
TAPE_RIGHT     : '>';
INPUT          : ',';
OUTPUT         : '.';
WHITESPACE : . -> skip;