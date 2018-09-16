package com.benjholla.atlas.brainfuck.indexer;

import com.ensoftcorp.atlas.core.query.Q;

public interface ProgramGraphTransform {

	public Q transform(Q graph);
	
}
