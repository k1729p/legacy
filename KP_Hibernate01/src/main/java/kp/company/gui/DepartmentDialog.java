package kp.company.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import kp.company.BusinessLogic;
import kp.company.Constants;
import kp.company.domain.Department;
import kp.company.exc.KpException;

/**
 * Class DepartmentDialog.
 */
public class DepartmentDialog {
	private static final Logger logger = Logger.getLogger(DepartmentDialog.class.getName());

	protected Shell shell;
	private boolean flagOK;
	private Department department;

	/**
	 * Constructor with parameters.
	 * 
	 * @param parentShell the parent shell
	 * @param department  the department
	 */
	public DepartmentDialog(Shell parentShell, Department department) {

		this.shell = new Shell(parentShell, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		this.department = department;
		this.flagOK = false;
	}

	/**
	 * Opens the dialog.
	 * 
	 * @return the flag OK
	 */
	public boolean open() {

		shell.setLayout(new GridLayout());
		shell.setText("Department");

		createDataFields();
		createControlButtons();

		shell.pack();
		shell.open();
		Display display = shell.getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("open(): flagOK[" + flagOK + "]");
		}
		return flagOK;
	}

	/**
	 * Creates data fields.
	 */
	private void createDataFields() {

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		addNameField(composite);
	}

	/**
	 * Creates control buttons.
	 */
	private void createControlButtons() {

		Composite composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		composite.setLayout(layout);

		Button okButton = new Button(composite, SWT.PUSH);

		okButton.setLayoutData(Constants.Layout.BUTTON.getValue());
		okButton.setText("OK");
		okButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				saveOrUpdateDepartment();
			}
		});
		Button cancelButton = new Button(composite, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.setLayoutData(Constants.Layout.BUTTON.getValue());
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		shell.setDefaultButton(okButton);
	}

	/**
	 * Saves new or updates existing department.
	 */
	private void saveOrUpdateDepartment() {

		if (!verifyDepartment()) {
			flagOK = false;
			shell.close();
			if (logger.isLoggable(Level.CONFIG)) {
				logger.config("saveOrUpdateDepartment(): flagOK[" + flagOK + "]");
			}
			return;
		}
		try {
			BusinessLogic businessLogic = new BusinessLogic();
			businessLogic.saveOrUpdateDepartment(department);
			flagOK = true;
		} catch (KpException ex) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR);
			messageBox.setText("Error");
			messageBox.setMessage("Error with \"" + department.getName() + "\" department editing");
			messageBox.open();
			logger.severe("saveOrUpdateDepartment(): KpException");
		}
		shell.close();
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("saveOrUpdateDepartment(): flagOK[" + flagOK + "]");
		}
	}

	/**
	 * Verify department.
	 */
	private boolean verifyDepartment() {

		String name = department.getName();
		return (name != null && !"".equals(name.trim()));
	}

	/**
	 * Adds name field.
	 * 
	 * @param composite the composite
	 * @param labelText the label text
	 */
	private void addNameField(Composite composite) {

		Label label = new Label(composite, SWT.RIGHT);
		label.setText("Name");
		GridData gridData = new GridData();
		gridData.widthHint = Constants.TEXT_WIDTH;

		Text text = new Text(composite, SWT.BORDER);
		text.setLayoutData(gridData);
		String name = department.getName();
		if (name != null && !"".equals(name.trim())) {
			text.setText(name);
		}
		addNameFieldListener(text);
	}

	/**
	 * Adds name field listener.
	 * 
	 * @param text the text
	 */
	private void addNameFieldListener(final Text text) {

		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String name = text.getText();
				if (name != null && !"".equals(name.trim())) {
					department.setName(name);
				}
			}
		});
	}

}
