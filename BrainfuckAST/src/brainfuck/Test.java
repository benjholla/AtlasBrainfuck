package brainfuck;

import java.io.File;
import java.io.FileInputStream;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import brainfuck.ast.Program;
import brainfuck.parser.BrainfuckLexer;
import brainfuck.parser.BrainfuckParser;

public class Test {

	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\Ben\\Desktop\\fib.b");
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
	}
	
}
