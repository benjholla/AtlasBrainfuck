package com.benjholla.eclipse.brainfuck.projects;

import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import com.benjholla.eclipse.brainfuck.log.Log;

public class BrainfuckBuilder extends IncrementalProjectBuilder {
	
	public static final String BUILDER_ID = "com.benjholla.brainfuck.builder";

	private static final boolean DEBUG = false;
	
	@SuppressWarnings("rawtypes")
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor) throws CoreException {
		// no incremental build support, just full build everytime
		fullBuild(monitor);
		return null;
	}
	
	protected void clean(IProgressMonitor monitor) throws CoreException {
		if(DEBUG) Log.info("Clean: " + getBrainfuckProject().getProject().getName());
	}
	
	protected void fullBuild(final IProgressMonitor monitor) throws CoreException {
		if(DEBUG) Log.info("Build: " + getBrainfuckProject().getProject().getName());
	}
	
	/**
	 * Returns the Brainfuck project to build or clean, if the project is invalid returns null
	 * @return
	 */
	private IProject getBrainfuckProject(){
		IProject project = getProject();
		try {
			if(project.isOpen() && project.exists() && project.hasNature(BrainfuckNature.NATURE_ID)){
				return project;
			}
		} catch (CoreException e) {}
		return null;
	}
	
}