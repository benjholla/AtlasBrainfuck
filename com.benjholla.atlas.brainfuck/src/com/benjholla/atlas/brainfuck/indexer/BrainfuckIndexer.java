package com.benjholla.atlas.brainfuck.indexer;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

import com.benjholla.atlas.brainfuck.common.XCSG;
import com.benjholla.atlas.brainfuck.log.Log;
import com.benjholla.brainfuck.ast.Program;
import com.benjholla.brainfuck.parser.support.ParserSourceCorrespondence;
import com.benjholla.eclipse.brainfuck.projects.BrainfuckNature;
import com.benjholla.eclipse.brainfuck.projects.BrainfuckProject;
import com.ensoftcorp.atlas.core.db.graph.Edge;
import com.ensoftcorp.atlas.core.db.graph.EditableGraph;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.index.common.SourceCorrespondence;
import com.ensoftcorp.atlas.core.indexing.IMappingSettings;
import com.ensoftcorp.atlas.core.indexing.Parser;
import com.ensoftcorp.atlas.core.indexing.Visitor;
import com.ensoftcorp.atlas.core.indexing.providers.LanguageIndexingProvider;
import com.ensoftcorp.atlas.core.indexing.providers.SimpleIndexingStage;
import com.ensoftcorp.atlas.core.script.Common;

public class BrainfuckIndexer implements com.ensoftcorp.atlas.core.indexing.providers.LanguageIndexingProviderFactory<BrainfuckProject, BrainfuckAST> {

	private static void index(BrainfuckAST ast, EditableGraph graph, Node projectNode, SubMonitor monitor) throws Exception {
		Log.info("Indexing: " + ast.getBrainfuckProject().getProject().getName());
		
		// index the program
		for(Program program : ast.getASTForest()) {
			File sourceFile = program.getParserSourceCorrespondence().getSource();
			String sourceFileName = sourceFile.getName();
			monitor.subTask("Processing: " + sourceFileName);
			
			// create a namespace (defined by the source file)
			Node namespaceNode = graph.createNode();
			namespaceNode.tag(XCSG.Namespace);
			namespaceNode.putAttr(XCSG.name, sourceFileName);
			ParserSourceCorrespondence psc = program.getParserSourceCorrespondence();
			SourceCorrespondence namespaceSC = new SourceCorrespondence(WorkspaceUtils.getFile(psc.getSource()), psc.getOffset(), psc.getLength(), psc.getStartLine(), psc.getEndLine());
			namespaceNode.putAttr(XCSG.sourceCorrespondence, namespaceSC);
			
			// make the project contain the namespace
			Edge containsEdge = graph.createEdge(projectNode, namespaceNode);
			containsEdge.tag(XCSG.Contains);
			
			// brainfuck has no real concept of functions, but will create a single implict main function
			// as another container level inside the namespace to allow smart views and common queries to 
			// operate cleanly out of the box
			Node implicitFunctionNode = graph.createNode();
			implicitFunctionNode.tag(XCSG.Brainfuck.ImplictFunction);
			if(sourceFileName.contains(".")) {
				sourceFileName = sourceFileName.substring(0, sourceFileName.lastIndexOf("."));
			}
			implicitFunctionNode.putAttr(XCSG.name, sourceFileName);
			namespaceNode.putAttr(XCSG.sourceCorrespondence, namespaceSC);
			
			// make the namespace contain the implicit function
			containsEdge = graph.createEdge(namespaceNode, implicitFunctionNode);
			containsEdge.tag(XCSG.Contains);
			
			// index the contents of the namespace
			program.index(graph, implicitFunctionNode, monitor);
		}
		
//		// TODO: optionally coalesce basic blocks for readability
//		if(BrainfuckPreferences.isCoalescingBasicBlocksEnabled()) {
//			for(Node function : new AtlasHashSet<Node>(Common.toQ(graph).nodes(XCSG.Brainfuck.ImplictFunction).eval().nodes())) {
//				BasicBlockTransform basicBlock = new BasicBlockTransform();
//				basicBlock.transform(CommonQueries.cfg(Common.toQ(function)));
//			}
//		}
	}

	@Override
	public boolean canIndexProject(IProject project) throws CoreException {
		return project.isAccessible() && project.isNatureEnabled(BrainfuckNature.NATURE_ID);
	}

	@Override
	public long countIndexFiles() {
		// Note: it is unclear what this should do in Atlas APIs
		// so just returning 0 here
		return 0;
	}

	@Override
	public LanguageIndexingProvider<BrainfuckProject, BrainfuckAST> getProvider(Collection<IMappingSettings> settings){

		LanguageIndexingProvider<BrainfuckProject, BrainfuckAST> provider = new LanguageIndexingProvider<BrainfuckProject, BrainfuckAST>(){

			@Override
			public SimpleIndexingStage[] getPreVisitationProcessors() {
				return new SimpleIndexingStage[]{};
			}

			@Override
			@SuppressWarnings("unchecked")
			public Visitor<BrainfuckAST>[] getVisitors() {
				Visitor<BrainfuckAST> visitor = new Visitor<BrainfuckAST>(){

					@Override
					public void visit(BrainfuckAST ast, EditableGraph graph, SubMonitor submonitor) {
						try {
							index(ast, graph, getOrCreateNodeForCompilationUnit(ast.getBrainfuckProject(), graph), submonitor);
						} catch (Exception e) {
							Log.error("Indexing Brainfuck Program Failed", e);
						}
					}

					@Override
					public String displayName() {
						return "Brainfuck Program Indexer";
					}
					
				};
				
				return new Visitor[]{ visitor };
			}

			@Override
			public SimpleIndexingStage[] getPostVisitationProcessors() {
				return new SimpleIndexingStage[]{};
			}

			@Override
			public String indexingDisplayName() {
				return "Brainfuck Indexer";
			}

			@Override
			public String tag() {
				// a tag to be applied to every element created by this language indexing provider
				return XCSG.Language.Brainfuck;
			}

			@Override
			public Parser<BrainfuckProject, BrainfuckAST> getParser() {
				Parser<BrainfuckProject, BrainfuckAST> parser = new Parser<BrainfuckProject, BrainfuckAST>(){
					@Override
					public BrainfuckAST parse(BrainfuckProject project) throws Exception {
						Log.info("Parsing: " + project.getProject().getName());
						return new BrainfuckAST(project);
					}
				};
				return parser;
			}

			@Override
			public boolean supportsMultithreadedParsing() {
				return false;
			}

			@Override
			public Collection<BrainfuckProject> findCompilationUnits(IProgressMonitor monitor) throws CoreException {
				LinkedList<BrainfuckProject> brainfuckProjects = new LinkedList<BrainfuckProject>();
				for(IProject project : com.ensoftcorp.atlas.core.index.ProjectPropertiesUtil.getAllEnabledProjects()){
					if(canIndexProject(project)){
						brainfuckProjects.add(new BrainfuckProject(project));
					}
				}
				return brainfuckProjects;
			}

			@Override
			public Node getOrCreateNodeForCompilationUnit(BrainfuckProject project, EditableGraph graph) {
				Node compilationUnit = Common.toQ(graph).nodes(XCSG.Project).selectNode(XCSG.name, project.getProject().getName()).eval().nodes().one();
				if(compilationUnit != null){
					return compilationUnit;
				} else {
					compilationUnit = graph.createNode();
					compilationUnit.tag(XCSG.Project);
					compilationUnit.putAttr(XCSG.name, project.getProject().getName());
					return compilationUnit;
				}
			}
			
		};
		
		return provider;
	}
	
}
