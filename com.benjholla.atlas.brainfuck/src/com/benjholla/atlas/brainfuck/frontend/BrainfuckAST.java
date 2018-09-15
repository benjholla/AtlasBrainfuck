package com.benjholla.atlas.brainfuck.frontend;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import com.benjholla.atlas.brainfuck.log.Log;
import com.benjholla.brainfuck.ast.Program;
import com.benjholla.brainfuck.parser.BrainfuckLexer;
import com.benjholla.brainfuck.parser.BrainfuckParser;
import com.benjholla.eclipse.brainfuck.projects.BrainfuckProject;

public class BrainfuckAST {

	private BrainfuckProject project;
	private List<Program> astForest = new ArrayList<Program>();
	
	public BrainfuckAST(BrainfuckProject project){
		this.project = project;
		for(File sourceFile : project.getSourceFiles()) {
			try {
				FileInputStream fis = new FileInputStream(sourceFile);
				CharStream input = CharStreams.fromStream(fis);
				BrainfuckLexer lexer = new BrainfuckLexer(input);
				CommonTokenStream tokens = new CommonTokenStream(lexer);
				BrainfuckParser parser = new BrainfuckParser(tokens);
				parser.setFile(sourceFile);
				Program program = parser.program().prog;
				astForest.add(program);
			} catch (Throwable t) {
				Log.error("Error parsing source file: " + sourceFile.getName(), t);
			}
		}
	}
	
	public BrainfuckProject getBrainfuckProject() {
		return project;
	}

	public List<Program> getASTForest() {
		return astForest;
	}
	
}
