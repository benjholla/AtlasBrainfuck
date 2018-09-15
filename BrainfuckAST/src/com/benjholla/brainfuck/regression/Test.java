package com.benjholla.brainfuck.regression;

import java.io.File;

import com.benjholla.brainfuck.interpreter.BrainfuckInterpreter;

public class Test {

	public static void main(String[] args) throws Exception {
		
		
//		String code = "+++++++++++>+>>>>++++++++++++++++++++++++++++++++++++++++++++>++++++++++++++++++++++" +
//                "++++++++++<<<<<<[>[>>>>>>+>+<<<<<<<-]>>>>>>>[<<<<<<<+>>>>>>>-]<[>++++++++++[-<-[>>+>" +
//                "+<<<-]>>>[<<<+>>>-]+<[>[-]<[-]]>[<<[>>>+<<<-]>>[-]]<<]>>>[>>+>+<<<-]>>>[<<<+>>>-]+<[" +
//                ">[-]<[-]]>[<<+>>[-]]<<<<<<<]>>>>>[++++++++++++++++++++++++++++++++++++++++++++++++.[" +
//                "-]]++++++++++<[->-<]>++++++++++++++++++++++++++++++++++++++++++++++++.[-]<<<<<<<<<<<" +
//                "<[>>>+>+<<<<-]>>>>[<<<<+>>>>-]<-[>>.>.<<<[-]]<<[>>+>+<<<-]>>>[<<<+>>>-]<<[<+>-]>[<+>" +
//                "-]<<<-]";
		
//		String code = "++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>.";
//		InputStream codeStream = new ByteArrayInputStream(code.getBytes());
//		ANTLRInputStream input = new ANTLRInputStream(codeStream);
		
		File file = new File("examples" + File.separator + "fib-small.b");
		BrainfuckInterpreter.execute(file, System.in, System.out/*, System.err*/);
		
//		System.out.println(program.getSourceCorrespondence().toString());
//		for(Instruction command : program.getInstructions()) {
//			System.out.println(command.toString() + " " + command.getSourceCorrespondence().toString());
//		}
		
//		System.out.println(program.toString());
//		System.out.println(program.getSourceCorrespondence().toString());
	}
	
}
