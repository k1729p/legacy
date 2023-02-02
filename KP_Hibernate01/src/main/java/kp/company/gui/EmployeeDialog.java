package kp.company.gui;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import kp.company.BusinessLogic;
import kp.company.Constants;
import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import kp.company.exc.KpException;

/**
 * Class EmployeeDialog.
 */
public class EmployeeDialog {

	private static final Logger logger = Logger.getLogger(EmployeeDialog.class.getName());

	protected Shell shell;
	private boolean flagOK;
	private boolean flagAddEmployee;
	private Employee employee = null;
	private Department department = null;
	private Combo titleCombo = null;

	/**
	 * Constructor with parameters.
	 * 
	 * @param parentShell the parent shell
	 * @param department  the department
	 */
	public EmployeeDialog(Shell parentShell, Department department) {

		this.shell = new Shell(parentShell, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		this.employee = new Employee();
		this.department = department;
		this.flagAddEmployee = true;
		this.flagOK = false;
	}

	/**
	 * Constructor with parameters.
	 * 
	 * @param parentShell the parent shell
	 * @param item        the item
	 */
	public EmployeeDialog(Shell parentShell, TableItem item) {

		this.shell = new Shell(parentShell, SWT.DIALOG_TRIM | SWT.PRIMARY_MODAL);
		this.employee = (Employee) item.getData();
		this.flagAddEmployee = false;
		this.flagOK = false;
	}

	/**
	 * Gets employee.
	 * 
	 * @return the employee
	 */
	public Employee getEmployee() {

		return employee;
	}

	/**
	 * Opens the dialog.
	 * 
	 * @return the flag OK
	 */
	public boolean open() {

		shell.setLayout(new GridLayout());
		shell.setText("Employee");

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

		addFirstNameField(composite);
		addLastNameField(composite);
		addTitleCombo(composite);
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
				saveOrUpdateEmployee();
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
	 * Save or update employee.
	 */
	private void saveOrUpdateEmployee() {

		if (!verifyEmployee()) {
			flagOK = false;
			shell.close();
			if (logger.isLoggable(Level.CONFIG)) {
				logger.config("saveOrUpdateEmployee(): flagOK[" + flagOK + "]");
			}
			return;
		}
		BusinessLogic businessLogic = new BusinessLogic();
		try {
			if (flagAddEmployee) {
				businessLogic.saveEmployee(department, employee);
			} else {
				businessLogic.updateEmployee(employee);
			}
			flagOK = true;
		} catch (KpException ex) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR);
			messageBox.setText("Error");
			messageBox.setMessage("Error with \"" + employee.getLastName() + "\" employee editing");
			messageBox.open();
			logger.severe("saveOrUpdateEmployee(): KpException");
		}
		shell.close();
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("saveOrUpdateEmployee(): flagOK[" + flagOK + "]");
		}
	}

	/**
	 * Verify employee.
	 */
	private boolean verifyEmployee() {

		String fName = employee.getFirstName();
		String lName = employee.getLastName();
		return (fName != null && !"".equals(fName.trim()) && lName != null && !"".equals(lName.trim()));
	}

	/**
	 * Adds first name field.
	 * 
	 * @param composite the composite
	 * @param labelText the label text
	 */
	private void addFirstNameField(Composite composite) {

		Label label = new Label(composite, SWT.RIGHT);
		label.setText("First Name");
		GridData gridData = new GridData();
		gridData.widthHint = Constants.TEXT_WIDTH;

		Text text = new Text(composite, SWT.BORDER);
		text.setLayoutData(gridData);
		String firstName = employee.getFirstName();
		if (firstName != null && !"".equals(firstName.trim())) {
			text.setText(firstName);
		}
		addFirstNameFieldListener(text);
	}

	/**
	 * Adds last name field listener.
	 * 
	 * @param text the text
	 */
	private void addFirstNameFieldListener(final Text text) {

		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String firstName = text.getText();
				if (firstName != null && !"".equals(firstName.trim())) {
					employee.setFirstName(firstName);
				}
			}
		});
	}

	/**
	 * Adds last name field.
	 * 
	 * @param composite the composite
	 * @param labelText the label text
	 */
	private void addLastNameField(Composite composite) {

		Label label = new Label(composite, SWT.RIGHT);
		label.setText("Last Name");
		GridData gridData = new GridData();
		gridData.widthHint = Constants.TEXT_WIDTH;

		Text text = new Text(composite, SWT.BORDER);
		text.setLayoutData(gridData);
		String lastName = employee.getLastName();
		if (lastName != null && !"".equals(lastName.trim())) {
			text.setText(lastName);
		}
		addLastNameFieldListener(text);
	}

	/**
	 * Adds last name field listener.
	 * 
	 * @param text the text
	 */
	private void addLastNameFieldListener(final Text text) {

		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				String lastName = text.getText();
				if (lastName != null && !"".equals(lastName.trim())) {
					employee.setLastName(lastName);
				}
			}
		});
	}

	/**
	 * Adds title combo.
	 * 
	 * @param composite the composite
	 */
	private void addTitleCombo(Composite composite) {

		Label label = new Label(composite, SWT.RIGHT);
		label.setText("Title");
		BusinessLogic businessLogic = new BusinessLogic();
		List<Title> titles = null;
		try {
			titles = businessLogic.loadTitles();
		} catch (KpException ex) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR);
			messageBox.setText("Error");
			messageBox.setMessage("Error with loading employee titles");
			messageBox.open();
			logger.severe("addTitleCombo(): KpException");
			return;
		}
		Map<String, Title> titlesMap = new TreeMap<String, Title>();
		for (Title title : titles) {
			titlesMap.put(title.getName(), title);
		}

		GridData gridData = new GridData();
		gridData.widthHint = Constants.COMBO_WIDTH;
		titleCombo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		titleCombo.setData(titlesMap);
		titleCombo.setLayoutData(gridData);
		titleCombo.setItems(titlesMap.keySet().toArray(new String[0]));

		Title title = employee.getTitle();
		if (title == null || !titlesMap.containsKey(title.getName())) {
			title = titles.get(0);
			employee.setTitle(title);
		}
		titleCombo.setText(title.getName());
		addTitleComboListener(titleCombo);
	}

	/**
	 * Adds title combo listener.
	 * 
	 * @param combo the combo
	 */
	private void addTitleComboListener(final Combo combo) {

		combo.addModifyListener(new ModifyListener() {
			@SuppressWarnings("unchecked")
			public void modifyText(ModifyEvent e) {
				Map<String, Title> titlesMap = (Map<String, Title>) combo.getData();
				Title title = titlesMap.get(combo.getText());
				employee.setTitle(title);
			}
		});
	}

}
