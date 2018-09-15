package brainfuck.regression;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.junit.jupiter.api.Test;

import brainfuck.ast.Program;
import brainfuck.interpreter.BrainfuckInterpreter;
import brainfuck.parser.BrainfuckLexer;
import brainfuck.parser.BrainfuckParser;

class TestInterpreter {

	@Test
	void testHelloWorld() throws Exception {
		File source = new File("examples" + File.separator + "hello.b");
		FileInputStream fis = new FileInputStream(source);
		ANTLRInputStream input = new ANTLRInputStream(fis);
		BrainfuckLexer lexer = new BrainfuckLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		BrainfuckParser parser = new BrainfuckParser(tokens);
		parser.setFile(source);
		Program program = parser.program().prog;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BrainfuckInterpreter.execute(program, null, output);
		String result = new String(output.toByteArray(), "UTF-8").trim();
		if(!result.equals("Hello World!")) {
			fail("Incorrect output");
		}
	}
	
	@Test
	void testFibonacciSequence() throws Exception {
		File source = new File("examples" + File.separator + "fib-small.b");
		FileInputStream fis = new FileInputStream(source);
		ANTLRInputStream input = new ANTLRInputStream(fis);
		BrainfuckLexer lexer = new BrainfuckLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		BrainfuckParser parser = new BrainfuckParser(tokens);
		parser.setFile(source);
		Program program = parser.program().prog;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BrainfuckInterpreter.execute(program, null, output);
		String result = new String(output.toByteArray(), "UTF-8").trim();
		if(!result.equals("1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89")) {
			fail("Incorrect output");
		}
	}
	
	@Test
	void testQuine() throws Exception {
		File source = new File("examples" + File.separator + "quine.b");
		FileInputStream fis = new FileInputStream(source);
		ANTLRInputStream input = new ANTLRInputStream(fis);
		BrainfuckLexer lexer = new BrainfuckLexer(input);
		CommonTokenStream tokens = new CommonTokenStream(lexer);
		BrainfuckParser parser = new BrainfuckParser(tokens);
		parser.setFile(source);
		Program program = parser.program().prog;
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		BrainfuckInterpreter.execute(program, null, output);
		String result = new String(output.toByteArray(), Charset.forName("UTF-8")).trim();
		if(!result.equals(readFile(source, Charset.forName("UTF-8")).trim())) {
			fail("Incorrect output");
		}
	}
	
	private static String readFile(File file, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(file.toPath());
		return new String(encoded, encoding);
	}

}
