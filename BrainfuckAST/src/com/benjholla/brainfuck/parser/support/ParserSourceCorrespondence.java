package com.benjholla.brainfuck.parser.support;

import java.io.File;

public class ParserSourceCorrespondence {

	private File source = null;
	private int line = 0;
	private long offset = 0;
	private long length = 0;

	public ParserSourceCorrespondence(File source, int line, long offset, long length) {
		this.source = source;
		this.line = line;
		this.offset = offset;
		this.length = length;
	}

	public File getSource() {
		return source;
	}

	public int getLine() {
		return line;
	}

	public long getOffset() {
		return offset;
	}

	public long getLength() {
		return length;
	}

	@Override
	public String toString() {
		return "SourceCorrespondence [source=" + (source != null ? source.getName() : "null") + ", line=" + line + ", offset=" + offset + ", length=" + length + "]";
	}
	
}
