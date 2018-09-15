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
   : instructions=instruction_list eof=EOF
   {
   	  ParserSourceCorrespondence sc;
   	  if($instructions.list.isEmpty()){
   	     sc = new ParserSourceCorrespondence(file, 0, 0, 0, 0);
   	  } else {
   	     ParserSourceCorrespondence firstInstructionSC = $instructions.list.get(0).getParserSourceCorrespondence();
   	     sc = new ParserSourceCorrespondence(file, firstInstructionSC.getOffset(), ((int) $eof.getStartIndex()- (int) firstInstructionSC.getOffset()), firstInstructionSC.getStartLine(), $eof.getLine()); 
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
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $tape_increment.getStartIndex(), $tape_increment.getText().length(), $tape_increment.getLine(), $tape_increment.getLine());
      $value = new IncrementInstruction(sc);
   }
   | tape_decrement=TAPE_DECREMENT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $tape_decrement.getStartIndex(), $tape_decrement.getText().length(), $tape_decrement.getLine(), $tape_decrement.getLine());
      $value = new DecrementInstruction(sc);
   }
   | tape_left=TAPE_LEFT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $tape_left.getStartIndex(), $tape_left.getText().length(), $tape_left.getLine(), $tape_left.getLine());
      $value = new MoveLeftInstruction(sc);
   }
   | tape_right=TAPE_RIGHT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $tape_right.getStartIndex(), $tape_right.getText().length(), $tape_right.getLine(), $tape_right.getLine());
      $value = new MoveRightInstruction(sc);
   }
   | input=INPUT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $input.getStartIndex(), $input.getText().length(), $input.getLine(), $input.getLine());
      $value = new ReadInputInstruction(sc);
   }
   | output=OUTPUT
   {
      ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $output.getStartIndex(), $output.getText().length(), $output.getLine(), $output.getLine());
      $value = new WriteOutputInstruction(sc);
   }
   ;
         
loop returns [LoopInstruction value]
   : lbrace='[' instructions=instruction_list rbrace=']'
   {
   	  int length = $rbrace.getStartIndex() - $lbrace.getStartIndex();
   	  ParserSourceCorrespondence sc = new ParserSourceCorrespondence(file, $lbrace.getStartIndex(), length, $lbrace.getLine(), $rbrace.getLine());
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