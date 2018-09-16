package com.benjholla.atlas.brainfuck.ui;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import com.ensoftcorp.atlas.core.db.graph.GraphElement;
import com.ensoftcorp.atlas.core.highlight.Highlighter;
import com.ensoftcorp.atlas.core.markup.IMarkup;
import com.ensoftcorp.atlas.core.markup.Markup;
import com.ensoftcorp.atlas.core.markup.MarkupFromH;
import com.ensoftcorp.atlas.core.query.Q;
import com.ensoftcorp.atlas.core.script.Common;
import com.ensoftcorp.atlas.core.script.CommonQueries;
import com.ensoftcorp.atlas.core.xcsg.XCSG;
import com.ensoftcorp.atlas.ui.viewer.graph.DisplayUtil;

/**
 * A set of helper utilities for some common display related methods
 * 
 * @author Ben Holland
 */
public class DisplayUtils {

	private DisplayUtils(){}
	
	private final static long LARGE_GRAPH_WARNING = 100;
	
	/**
	 * Returns a shell for the given display
	 * @param display
	 * @return
	 */
	private static Shell getShell(Display display){
		Shell shell = display.getActiveShell();
		if (shell == null) {
			shell = new Shell(display);
		}
		return shell;
	}
	
	/**
	 * Returns the active display
	 * @return
	 */
	private static Display getDisplay(){
		Display display = Display.getCurrent();
		if(display == null){
			display = Display.getDefault();
		}
		return display;
	}
	
	
	/**
	 * Opens a display prompt alerting the user of the error 
	 * 
	 * @param message the message to display
	 */
	public static void showError(final String message) {
		final Display display = getDisplay();
		display.syncExec(new Runnable(){
			@Override
			public void run() {
				MessageBox mb = new MessageBox(getShell(display), SWT.ICON_ERROR | SWT.OK);
				mb.setText("Alert");
				mb.setMessage(message);
				mb.open();
			}
		});
	}
	
	/**
	 * Opens a display prompt alerting the user of the error and offers the
	 * ability to copy a stack trace to the clipboard
	 * 
	 * @param t the throwable to grab stack trace from
	 * @param message the message to display
	 */
	public static void showError(final Throwable t, final String message) {
		final Display display = getDisplay();
		display.syncExec(new Runnable(){
			@Override
			public void run() {
				MessageBox mb = new MessageBox(getShell(display), SWT.ICON_ERROR | SWT.NO | SWT.YES);
				mb.setText("Alert");
				StringWriter errors = new StringWriter();
				t.printStackTrace(new PrintWriter(errors));
				String stackTrace = errors.toString();
				mb.setMessage(message + "\n\nWould you like to copy the stack trace?");
				int response = mb.open();
				if (response == SWT.YES) {
					StringSelection stringSelection = new StringSelection(stackTrace);
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(stringSelection, stringSelection);
				}
			}
		});
	}
	
	/**
	 * Opens a display prompt showing a message
	 * @param message
	 */
	public static void showMessage(final String message){
		final Display display = getDisplay();
		display.syncExec(new Runnable(){
			@Override
			public void run() {
				MessageBox mb = new MessageBox(getShell(display), SWT.ICON_INFORMATION | SWT.OK);
				mb.setText("Message");
				mb.setMessage(message);
				mb.open();
			}
		});
	}
	
	/**
	 * A show method for a single graph element
	 * Defaults to extending and no highlighting
	 * @param ge The GraphElement to show
	 * @param title A title to indicate the graph content
	 */
	public static void show(final GraphElement ge, final String title) {
		show(Common.toQ(ge), title);
	}
	
	/**
	 * A show method for a single graph element
	 * @param ge The GraphElement to show
	 * @param h An optional highlighter, set to null otherwise
	 * @param extend A boolean to define if the graph should be extended (typical use is true)
	 * @param title A title to indicate the graph content
	 */
	public static void show(final GraphElement ge, final Highlighter h, final boolean extend, final String title) {
		show(Common.toQ(ge), h, extend, title);
	}
	
	/**
	 * Shows a graph inside Atlas
	 * Defaults to extending and no highlighting
	 * @param ge The GraphElement to show
	 * @param title A title to indicate the graph content
	 */
	public static void show(final Q q, final String title) {
		Markup m = null;
		show(q, m, true, title);
	}
	
	/**
	 * Shows a graph inside Atlas
	 * 
	 * @param q The query to show
	 * @param h An optional highlighter, set to null otherwise
	 * @param extend A boolean to define if the graph should be extended (typical use is true)
	 * @param title A title to indicate the graph content
	 */
	public static void show(final Q q, final boolean extend, final String title) {
		Markup m = null;
		show(q, m, extend, title);
	}
	
	/**
	 * Shows a graph inside Atlas
	 * 
	 * @param q The query to show
	 * @param highlighter An optional highlighter, set to null otherwise
	 * @param extend A boolean to define if the graph should be extended (typical use is true)
	 * @param title A title to indicate the graph content
	 */
	public static void show(final Q q, final Highlighter highlighter, final boolean extend, final String title) {
		IMarkup m = null;
		if(highlighter != null){
			m = new MarkupFromH(highlighter);
		}
		show(q, m, extend, title);
	}
	
	/**
	 * Shows a graph inside Atlas
	 * 
	 * @param q The query to show
	 * @param h An optional highlighter, set to null otherwise
	 * @param extend A boolean to define if the graph should be extended (typical use is true)
	 * @param title A title to indicate the graph content
	 */
	public static void show(final Q q, final IMarkup markup, final boolean extend, final String title) {
		final Display display = getDisplay();
		display.syncExec(new Runnable(){
			@Override
			public void run() {
				try {
					long graphSize = CommonQueries.nodeSize(q);
					boolean showGraph = false;
					if (graphSize > LARGE_GRAPH_WARNING) {
						MessageBox mb = new MessageBox(getShell(display), SWT.ICON_QUESTION | SWT.YES | SWT.NO);
						mb.setText("Warning");
						mb.setMessage("The graph you are about to display has " + graphSize + " nodes.  " 
								+ "Displaying large graphs may cause Eclipse to become unresponsive." 
								+ "\n\nDo you want to continue?");
						int response = mb.open();
						if (response == SWT.YES) {
							showGraph = true; // user says let's do it!!
						}
					} else {
						// graph is small enough to display
						showGraph = true;
					}
					if (showGraph) {
						Q extended = Common.universe().edgesTaggedWithAny(XCSG.Contains).reverse(q).union(q);
						Q displayExpr = extend ? extended : q;
						DisplayUtil.displayGraph((markup != null ? markup : new Markup()), displayExpr.eval(), title);
					}
				} catch (Exception e){
					DisplayUtils.showError(e, "Could not display graph.");
				}
			}
		});
	}
	
}
