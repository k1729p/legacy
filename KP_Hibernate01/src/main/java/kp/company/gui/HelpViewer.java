package kp.company.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * Class HelpViewer
 */
public class HelpViewer {

	private static final Logger logger = Logger.getLogger(HelpViewer.class.getName());
	private Shell shell;

	private static final String HELP_TXT_01 = "\"Summary\" tab shows tree with departments.\n\n"
			+ "\"Details\" tab shows table with departments and\n"
			+ "table with employees of the selected department.\n\n"
			+ "On both tables right mouse button shows pop up menu for\n"
			+ "adding/editing/deleting selected department or employee.\n\n";

	/**
	 * Constructor with one parameter.
	 * 
	 * @param shell the shell
	 */
	public HelpViewer(Shell shell) {

		this.shell = shell;
	}

	/**
	 * Shows help.
	 */
	public void showHelp() {

		Shell dialog = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

		dialog.setLayout(new GridLayout());
		Text text = new Text(dialog, SWT.MULTI | SWT.WRAP);
		text.setText(HELP_TXT_01);
		Font initialFont = text.getFont();
		FontData[] fontData = initialFont.getFontData();
		for (int i = 0; i < fontData.length; i++) {
			fontData[i].setHeight(12);
			fontData[i].setStyle(SWT.BOLD);
		}
		Font newFont = new Font(shell.getDisplay(), fontData);
		text.setFont(newFont);
		dialog.pack();
		dialog.open();
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("");
		}
	}
}
