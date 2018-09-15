package brainfuck.regression;

import java.io.File;
import java.io.FileInputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import brainfuck.ast.Program;
import brainfuck.interpreter.BrainfuckInterpreter;
import brainfuck.parser.BrainfuckLexer;
import brainfuck.parser.BrainfuckParser;

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
		FileInputStream fis = new FileInputStream(file);
		ANTLRInputStream input = new ANTLRInputStream(fis);
		
		BrainfuckLexer lexer = new BrainfuckLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		BrainfuckParser parser = new BrainfuckParser(tokens);
		parser.setFile(file);
//		parser.removeErrorListeners();
//		parser.addErrorListener(new ParserError(file.getName()));
		Program program = parser.program().prog;
		System.out.println(program.toString());
		BrainfuckInterpreter.execute(program, System.in, System.out/*, System.err*/);
		
//		System.out.println(program.getSourceCorrespondence().toString());
//		for(Instruction command : program.getInstructions()) {
//			System.out.println(command.toString() + " " + command.getSourceCorrespondence().toString());
//		}
		
//		System.out.println(program.toString());
	}
	
}