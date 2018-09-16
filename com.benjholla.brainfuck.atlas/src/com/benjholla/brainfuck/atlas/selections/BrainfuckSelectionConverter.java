package com.benjholla.brainfuck.atlas.selections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;

import com.benjholla.brainfuck.atlas.common.XCSG;
import com.benjholla.brainfuck.atlas.indexer.BrainfuckIndexer;
import com.ensoftcorp.atlas.core.db.graph.Node;
import com.ensoftcorp.atlas.core.db.set.AtlasHashSet;
import com.ensoftcorp.atlas.core.db.set.AtlasSet;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.ui.selection.ISelectionContext;
import com.ensoftcorp.atlas.ui.selection.converter.ISelectionConverter;
import com.ensoftcorp.atlas.ui.selection.event.AbstractSelectionEvent;
import com.ensoftcorp.atlas.ui.selection.event.IAtlasSelectionEvent;

@SuppressWarnings("rawtypes")
public class BrainfuckSelectionConverter implements ISelectionConverter {

	@Override
	public boolean canConvertSelection(ISelectionContext selectionContext) {
		if (!(selectionContext.getSelection() instanceof IStructuredSelection)) {
			return false;
		}
		Iterator iterator = ((IStructuredSelection) selectionContext.getSelection()).iterator();
		while (iterator.hasNext()) {
			Object selection = iterator.next();
			return isBrainfuckProject(selection) || isBrainfuckFile(selection);
		}
		return false;
	}

	@Override
	public IAtlasSelectionEvent convertSelection(ISelectionContext selectionContext) {
		if (!canConvertSelection(selectionContext)) {
			throw new IllegalArgumentException("argument 'selection' must be of type IStructuredSelection, and must contain at least one Brainfuck file");
		}
		IStructuredSelection structuredSelection = (IStructuredSelection) selectionContext.getSelection();
		Iterator localIterator = structuredSelection.iterator();
		ArrayList<IProject> projects = new ArrayList<IProject>();
		ArrayList<IFile> files = new ArrayList<IFile>();
		while (localIterator.hasNext()) {
			Object selection = localIterator.next();
			if(isBrainfuckProject(selection)) {
				IProject project = (IProject) selection;
				projects.add(project);
			} else if(isBrainfuckFile(selection)) {
				IFile file = (IFile) selection;
				files.add(file);
			}
		}
		if(!projects.isEmpty()) {
			return new BrainfuckProjectSelectionEvent(selectionContext.getWorkbenchPart(), selectionContext.getSelection(), projects);
		} else {
			return new BrainfuckFileSelectionEvent(selectionContext.getWorkbenchPart(), selectionContext.getSelection(), files);
		}
	}

	private boolean isBrainfuckProject(Object project) {
		if ((project instanceof IProject)) {
			try {
				return BrainfuckIndexer.isIndexableBrainfuckProject((IProject) project);
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
	
	private boolean isBrainfuckFile(Object object) {
		if ((object instanceof IFile)) {
			IFile file = (IFile) object;
			return "bf".equals(file.getFileExtension());
		}
		return false;
	}

	private static final class BrainfuckProjectSelectionEvent extends AbstractSelectionEvent {

		private Collection<IProject> projects;
		
		private BrainfuckProjectSelectionEvent(IWorkbenchPart workbenchPart, ISelection selection, Collection<IProject> projects) {
			super(workbenchPart, selection);
			this.projects = projects;
		}

		protected Q calculateApproximateSelection() {
			AtlasSet<Node> result = new AtlasHashSet<Node>();
			for(IProject project : projects) {
				String projectName = project.getName();
				Q proj = Common.universe().nodes(XCSG.Project).selectNode(XCSG.name, projectName);
				result.addAll(proj.eval().nodes());
			}
			return Common.toQ(result);
		}
		
	}
	
	private static final class BrainfuckFileSelectionEvent extends AbstractSelectionEvent {

		Collection<IFile> files;
		
		private BrainfuckFileSelectionEvent(IWorkbenchPart workbenchPart, ISelection selection, Collection<IFile> files) {
			super(workbenchPart, selection);
			this.files = files;
		}

		protected Q calculateApproximateSelection() {
			AtlasSet<Node> result = new AtlasHashSet<Node>();
			for(IFile file : files) {
				String projectName = file.getProject().getName();
				Q project = Common.universe().nodes(XCSG.Project).selectNode(XCSG.name, projectName);
				Q namespaces = project.children().nodes(XCSG.Namespace).selectNode(XCSG.name, file.getName());
				result.addAll(namespaces.eval().nodes());
				result.addAll(namespaces.children().nodes(XCSG.Brainfuck.ImplictFunction).eval().nodes());
			}
			return Common.toQ(result);
		}
		
	}

}
