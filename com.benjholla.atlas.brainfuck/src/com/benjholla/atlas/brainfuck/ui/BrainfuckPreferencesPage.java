package com.benjholla.atlas.brainfuck.ui;

import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.IntegerFieldEditor;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

import com.benjholla.atlas.brainfuck.Activator;
import com.benjholla.atlas.brainfuck.preferences.BrainfuckPreferences;

public class BrainfuckPreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	private static final String COALESCING_BASIC_BLOCKS_DESCRIPTION = "Enable coalescing basic blocks";
	private static final String INCLUDE_WHITESPACE_BASIC_BLOCK_DELIMITERS_DESCRIPTION = "Include whitespace (non-instruction characters) as basic block delimiteres";
	private static final String LIMIT_MAX_BASIC_BLOCK_INSTRUCTIONS_DESCRIPTION = "Limit max instructions per basic block";
	private static final String MAX_BASIC_BLOCK_INSTRUCTIONS_DESCRIPTION = "Max instructions per basic block";
	
	private static boolean changeListenerAdded = false;
	
	public BrainfuckPreferencesPage() {
		super(GRID);
	}

	@Override
	public void init(IWorkbench workbench) {
		IPreferenceStore preferences = Activator.getDefault().getPreferenceStore();
		setPreferenceStore(preferences);
		setDescription("Configure preferences for the Atlas for Brainfuck plugin.");
		
		// use to update cached values if user edits a preference
		if(!changeListenerAdded){
			getPreferenceStore().addPropertyChangeListener(new IPropertyChangeListener() {
				@Override
				public void propertyChange(org.eclipse.jface.util.PropertyChangeEvent event) {
					BrainfuckPreferences.loadPreferences();
				}
			});
			changeListenerAdded = true;
		}
	}

	@Override
	protected void createFieldEditors() {
		addField(new BooleanFieldEditor(BrainfuckPreferences.COALESCING_BASIC_BLOCKS, "&" + COALESCING_BASIC_BLOCKS_DESCRIPTION, getFieldEditorParent()));
		addField(new BooleanFieldEditor(BrainfuckPreferences.INCLUDE_WHITESPACE_BASIC_BLOCK_DELIMITERS, "&" + INCLUDE_WHITESPACE_BASIC_BLOCK_DELIMITERS_DESCRIPTION, getFieldEditorParent()));
		addField(new BooleanFieldEditor(BrainfuckPreferences.LIMIT_MAX_BASIC_BLOCK_INSTRUCTIONS, "&" + LIMIT_MAX_BASIC_BLOCK_INSTRUCTIONS_DESCRIPTION, getFieldEditorParent()));
		addField(new IntegerFieldEditor(BrainfuckPreferences.MAX_BASIC_BLOCK_INSTRUCTIONS, "&" + MAX_BASIC_BLOCK_INSTRUCTIONS_DESCRIPTION, getFieldEditorParent()));
	}
}
