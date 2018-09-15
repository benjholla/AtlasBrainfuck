package com.benjholla.eclipse.brainfuck.ui;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;

import com.benjholla.eclipse.brainfuck.log.Log;
import com.benjholla.eclipse.brainfuck.projects.BrainfuckProject;

public class NewProjectWizard extends Wizard implements INewWizard {

	private NewProjectPage page;

	public NewProjectWizard(String startRuntimePath) {
		page = new NewProjectPage("Create Brainfuck Project");
		String projectName = new File(startRuntimePath).getName();
		projectName = projectName.substring(0, projectName.lastIndexOf('.'));
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
		if (project.exists()) {
			// find a project name that doesn't collide
			int i = 2;
			while (project.exists()) {
				i++;
				project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName + "_" + i);
			}
			projectName = projectName + "_" + i;
		}
		page.setInitialProjectName(projectName);
		this.setWindowTitle("Create Brainfuck Project");
	}

	public NewProjectWizard() {
		page = new NewProjectPage("Create Brainfuck Project");
		this.setWindowTitle("Create Brainfuck Project");
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	@Override
	public void addPages() {
		this.addPage(page);
	}

	@Override
	public boolean performFinish() {
		final String projectName = page.getProjectName();
		final IPath projectLocation = page.getLocationPath();

		IRunnableWithProgress j = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) {
				IStatus result = null;
				try {
					result = createProject(projectName, projectLocation, monitor);
				} catch (Throwable t) {
					String message = "Could not create Brainfuck project. " + t.getMessage();
					UIJob uiJob = new WizardErrorDialog("Error creating Brainfuck project...", message, projectName);
					uiJob.schedule();
					Log.error(message, t);
				} finally {
					monitor.done();
				}
				if (result != null && result.equals(Status.CANCEL_STATUS)) {
					IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
					BrainfuckProject.deleteProject(project);
				}
			}
		};

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);

		try {
			dialog.run(true, true, j);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	private static IStatus createProject(String projectName, IPath projectPath, IProgressMonitor monitor) throws IOException, CoreException {
		IProject project = null;
		
		try {
			monitor.beginTask("Create Brainfuck Project", 2);
			
			// create the empty eclipse project
			monitor.setTaskName("Creating Eclipse project...");

			// check the project does not already exist
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			if(project.exists()){
				Log.warning("Project " + projectName + " already exists.");
				return Status.CANCEL_STATUS;
			} else {
				monitor.worked(1);
			}
			
			// create a new proejct
			project = BrainfuckProject.createProject(projectName, projectPath, monitor);
			monitor.worked(1);
			
			return Status.OK_STATUS;
		} finally {
			if (project != null && project.exists()){
				project.refreshLocal(IResource.DEPTH_INFINITE, new NullProgressMonitor());
			}
			monitor.done();
		}
	}

}