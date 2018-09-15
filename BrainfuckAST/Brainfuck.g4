grammar Brainfuck;

@header{
	package com.benjholla.brainfuck.parser; 
	
	import com.benjholla.brainfuck.parser.support.*;
	import com.benjholla.brainfuck.ast.*;
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
   : instructions=instruction_list end=EOF
   {
   	  ParserSourceCorrespondence sc;
   	  if($instructions.list.isEmpty()){
   	     sc = new ParserSourceCorrespondence(file, 0, 0, 0);
   	  } else {
   	     ParserSourceCorrespondence firstInstructionSC = $instructions.list.get(0).getSourceCorrespondence();
   	     long eof = $end.getStartIndex();
   	     sc = new ParserSourceCorrespondence(file, firstInstructionSC.getLine(), firstInstructionSC.getOffset(), (eof-firstInstructionSC.getOffset())); 
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
      $value = $l.value;
   }
   | tape_increment=TAPE_INCREMENT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $tape_increment.getLine(), $tape_increment.getStartIndex(), $tape_increment.getText().length());
      $value = new IncrementInstruction(sc);
   }
   | tape_decrement=TAPE_DECREMENT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $tape_decrement.getLine(), $tape_decrement.getStartIndex(), $tape_decrement.getText().length());
      $value = new DecrementInstruction(sc);
   }
   | tape_left=TAPE_LEFT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $tape_left.getLine(), $tape_left.getStartIndex(), $tape_left.getText().length());
      $value = new MoveLeftInstruction(sc);
   }
   | tape_right=TAPE_RIGHT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $tape_right.getLine(), $tape_right.getStartIndex(), $tape_right.getText().length());
      $value = new MoveRightInstruction(sc);
   }
   | input=INPUT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $input.getLine(), $input.getStartIndex(), $input.getText().length());
      $value = new ReadInputInstruction(sc);
   }
   | output=OUTPUT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $output.getLine(), $output.getStartIndex(), $output.getText().length());
      $value = new WriteOutputInstruction(sc);
   }
   ;
         
loop returns [LoopInstruction value]
   : lbrace='[' instructions=instruction_list rbrace=']'
   {
   	  long length = $rbrace.getStartIndex() - $lbrace.getStartIndex();
   	  ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $lbrace.getLine(), $lbrace.getStartIndex(), length);
      $value = new LoopInstruction(sc, $instructions.list);
   }
   ;

TAPE_INCREMENT : '+';
TAPE_DECREMENT : '-';
TAPE_LEFT      : '<';
TAPE_RIGHT     : '>';
INPUT          : ',';
OUTPUT         : '.';
WHITESPACE : . -> skip;