package com.benjholla.eclipse.brainfuck.ui;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.UIJob;
import org.osgi.framework.Bundle;

import com.benjholla.eclipse.brainfuck.Activator;
import com.benjholla.eclipse.brainfuck.log.Log;
import com.benjholla.eclipse.brainfuck.projects.BrainfuckProject;

public class WizardErrorDialog extends UIJob {

	private String message, projectName;
	
	public WizardErrorDialog(String name, String errorMessage, String projectName) {
		super(name);
		this.message = errorMessage;
		this.projectName = projectName;
	}

	@Override
	public IStatus runInUIThread(IProgressMonitor monitor) {
		Path iconPath = new Path("icons" + File.separator + "brainfuck.gif");
		Bundle bundle = Platform.getBundle(Activator.PLUGIN_ID);
		Image icon = null;
		try {
			icon = new Image(PlatformUI.getWorkbench().getDisplay(), FileLocator.find(bundle, iconPath, null).openStream());
		} catch (IOException e) {
			Log.error("Brainfuck.gif icon is missing.", e);
		};
		MessageDialog dialog = new MessageDialog(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), 
												"Could Not Create Brainfuck Project", 
												icon, 
												message, 
												MessageDialog.ERROR,
												new String[] { "Delete Project", "Cancel" }, 
												0);
		int response = dialog.open();

		IStatus status = Status.OK_STATUS;
		if (response == 0) {
			IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject(projectName);
			status = BrainfuckProject.deleteProject(project);
		}
		
		if (icon != null){
			icon.dispose();
		}
		
		return status;
	}
	
}