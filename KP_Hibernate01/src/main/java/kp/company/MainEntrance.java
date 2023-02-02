package kp.company;

import java.util.logging.Level;
import java.util.logging.Logger;

import kp.company.gui.BasePanel;
import kp.company.util.Configuration;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * Class MainEntrance.
 */
public class MainEntrance {

	private static final Logger logger = Logger.getLogger(MainEntrance.class.getName());

	/**
	 * Main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		Configuration.setLogging();

		Display display = new Display();
		BasePanel ap = new BasePanel();
		Shell shell = ap.open(display);

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		if (logger.isLoggable(Level.INFO)) {
			logger.info("main():");
		}
	}

}
