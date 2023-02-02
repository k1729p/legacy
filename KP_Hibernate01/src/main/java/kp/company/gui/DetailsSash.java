package kp.company.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import kp.company.exc.KpException;

/**
 * Class DetailsSash.
 */
public class DetailsSash {

	private static final Logger logger = Logger.getLogger(DetailsSash.class.getName());

	private Shell shell = null;
	private SashForm horizSash = null;
	private DepartmentsViewer departmentsViewer = null;
	private EmployeesViewer employeesViewer = null;

	/**
	 * Constructor with one parameter.
	 * 
	 * @param shell the shell
	 */
	public DetailsSash(Shell shell) {
		this.shell = shell;
	}

	/**
	 * Creates groups sash.
	 * 
	 * @param composite the composite
	 * @throws KpException the exception
	 */
	public void create(Composite composite) throws KpException {

		employeesViewer = new EmployeesViewer(shell);
		departmentsViewer = new DepartmentsViewer(shell);

		horizSash = new SashForm(composite, SWT.HORIZONTAL);
		horizSash.setLayout(new GridLayout());
		horizSash.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		departmentsViewer.create(horizSash, employeesViewer);
		employeesViewer.create(horizSash);
		departmentsViewer.selectTopDepartment();

		horizSash.setWeights(new int[] { 33, 66 });

		if (logger.isLoggable(Level.FINE)) {
			logger.fine("create():");
		}
	}

	/**
	 * Reloads departments. Wrapper method.
	 */
	public void reloadDepartments() {

		departmentsViewer.loadDepartments();
		departmentsViewer.selectTopDepartment();
	}

}
