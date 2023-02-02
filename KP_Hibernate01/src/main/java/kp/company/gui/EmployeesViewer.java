package kp.company.gui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import kp.company.BusinessLogic;
import kp.company.Constants;
import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import kp.company.exc.KpException;

/**
 * Class EmployeesViewer.
 */
public class EmployeesViewer {

	private static final Logger logger = Logger.getLogger(EmployeesViewer.class.getName());

	private Shell shell = null;
	private Table employeesTable = null;
	private Department department = null;
	private Color[] background = null;
	private HashMap<String, Color> titleColors = new HashMap<String, Color>();

	/**
	 * Constructor with one parameter.
	 * 
	 * @param shell the shell
	 */
	public EmployeesViewer(Shell shell) {

		this.shell = shell;
		this.background = new Color[] { new Color(shell.getDisplay(), 208, 208, 255),
				new Color(shell.getDisplay(), 208, 255, 208), new Color(shell.getDisplay(), 255, 208, 208) };
	}

	/**
	 * Creates employees table.
	 * 
	 * @param composite the composite
	 * @throws KpException the exception
	 */
	protected void create(Composite composite) throws KpException {

		Group employeesGroup = new Group(composite, SWT.NONE);
		employeesGroup.setLayout(new GridLayout());
		employeesGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		employeesGroup.setText("Employees");

		employeesTable = new Table(employeesGroup, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		employeesTable.setLayout(new GridLayout(1, false));
		employeesTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		employeesTable.setHeaderVisible(true);
		employeesTable.setLinesVisible(true);
		employeesTable.setMenu(createPopUpMenu(shell));

		employeesTable.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				TableItem[] items = employeesTable.getSelection();
				if (items.length > 0) {
					editEmployee(items[0]);
				}
			}
		});
		String[] columnNames = new String[] { "First Name", "Last Name", "Title" };
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn column = new TableColumn(employeesTable, SWT.NONE);
			column.setText(columnNames[i]);
			column.setWidth(Constants.ColumnWidth.NORMAL.getValue());
			column.setResizable(true);
			final int columnIndex = i;
			column.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					sortTable(columnIndex);
				}
			});
		}
		BusinessLogic businessLogic = new BusinessLogic();
		List<Title> titles = businessLogic.loadTitles();
		for (int i = 0; i < titles.size(); i++) {
			titleColors.put(titles.get(i).getName(), background[i % 3]);
		}
		if (logger.isLoggable(Level.INFO)) {
			logger.info("create():");
		}
	}

	/**
	 * Creates groups pop-up menu.
	 * 
	 * @param shell the shell
	 * @return the popup menu
	 */
	private Menu createPopUpMenu(Shell shell) {

		Menu popUpMenu = new Menu(shell, SWT.POP_UP);
		popUpMenu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent ev) {
				Menu menu = (Menu) ev.widget;
				MenuItem[] items = menu.getItems();
				if (employeesTable.getSelectionCount() > 0) {
					items[0].setEnabled(true); // edit
					items[1].setEnabled(true); // delete
				} else {
					items[0].setEnabled(false); // edit
					items[1].setEnabled(false); // delete
				}
			}
		});

		// Edit
		MenuItem item = new MenuItem(popUpMenu, SWT.PUSH);
		item.setText("Edit Employee");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = employeesTable.getSelection();
				if (items.length > 0)
					editEmployee(items[0]);
			}
		});

		// Delete
		item = new MenuItem(popUpMenu, SWT.PUSH);
		item.setText("Delete Employee");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = employeesTable.getSelection();
				if (items.length > 0)
					deleteEmployee(items[0]);
			}
		});

		new MenuItem(popUpMenu, SWT.SEPARATOR);

		// Add
		item = new MenuItem(popUpMenu, SWT.PUSH);
		item.setText("Add Employee");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addEmployee();
			}
		});
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("");
		}
		return popUpMenu;
	}

	/**
	 * Adds new employee.
	 * 
	 */
	private void addEmployee() {

		employeesTable.setSortColumn(null);
		if (department == null) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_INFORMATION);
			messageBox.setText("Adding employee");
			messageBox.setMessage("First add department then employee.");
			messageBox.open();
			if (logger.isLoggable(Level.CONFIG)) {
				logger.config("addEmployee(): no department");
			}
			return;
		}
		EmployeeDialog dialog = new EmployeeDialog(shell, department);
		if (!dialog.open()) {
			if (logger.isLoggable(Level.CONFIG)) {
				logger.config("addEmployee(): cancel");
			}
			return;
		}
		Employee employee = dialog.getEmployee();
		String firstName = employee.getFirstName();
		String lastName = employee.getLastName();
		String titleName = employee.getTitle().getName();

		TableItem item = new TableItem(employeesTable, SWT.NONE);
		String[] rowContent = new String[] { firstName, lastName, titleName };
		item.setText(rowContent);
		item.setData(employee);
		employeesTable.select(employeesTable.indexOf(item));

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("addEmployee(): firstName[" + firstName + "], lastName[" + lastName + "]");
		}
	}

	/**
	 * Edits employee.
	 * 
	 * @param item the item
	 */
	private void editEmployee(TableItem item) {

		employeesTable.setSortColumn(null);
		EmployeeDialog dialog = new EmployeeDialog(shell, item);
		if (!dialog.open()) {
			if (logger.isLoggable(Level.CONFIG)) {
				logger.config("editEmployee(): cancel");
			}
			return;
		}
		Employee employee = dialog.getEmployee();
		String firstName = employee.getFirstName();
		String lastName = employee.getLastName();
		String titleName = employee.getTitle().getName();

		String[] rowContent = new String[] { firstName, lastName, titleName };
		item.setText(rowContent);
		item.setData(employee);

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("editEmployee(): firstName[" + firstName + "], lastName[" + lastName + "]");
		}
	}

	/**
	 * Deletes employee.
	 * 
	 * @param item the item
	 */
	private void deleteEmployee(TableItem item) {

		employeesTable.setSortColumn(null);
		// ask user if they want to delete selected employee
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL);
		messageBox.setText("Delete Employee");
		messageBox.setMessage("Delete selected employee?");
		int choice = messageBox.open();
		if (choice != SWT.YES) {
			if (logger.isLoggable(Level.CONFIG)) {
				logger.config("deleteEmployee(): cancel");
			}
			return;
		}
		Employee employee = (Employee) item.getData();

		String employeeName = employee.getFirstName() + " " + employee.getLastName();
		try {
			BusinessLogic businessLogic = new BusinessLogic();
			businessLogic.deleteEmployee(department, employee.getId());
		} catch (KpException ex) {
			messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR);
			messageBox.setText("Error");
			messageBox.setMessage("Error with \"" + employeeName + "\" employee deleting");
			messageBox.open();
			logger.severe("deleteEmployee(): KpException");
			return;
		}
		item.dispose();

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("deleteEmployee(): name[" + employeeName + "]");
		}
	}

	/**
	 * Loads only employee names for department.
	 * 
	 * @param department the department
	 */
	protected void showDepartmentEmployees(Department department) {

		this.department = department;
		employeesTable.removeAll();
		employeesTable.setSortColumn(null);

		List<Employee> employees = null;
		try {
			BusinessLogic businessLogic = new BusinessLogic();
			employees = businessLogic.loadEmployees(department);
		} catch (KpException ex) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR);
			messageBox.setText("Error");
			messageBox.setMessage("Error with employee loading");
			messageBox.open();
			logger.severe("showDepartmentEmployees(): KpException");
			return;
		}
		for (Employee employee : employees) {
			TableItem item = new TableItem(employeesTable, SWT.NONE);
			String[] rowContent = new String[] { employee.getFirstName(), employee.getLastName(),
					employee.getTitle().getName() };
			item.setText(rowContent);
			item.setData(employee);
			item.setBackground(2, titleColors.get(employee.getTitle().getName()));
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("showDepartmentEmployees():");
		}
	}

	/**
	 * Removes employee names for department.
	 */
	protected void removeDepartmentEmployees() {

		this.department = null;
		employeesTable.removeAll();
		employeesTable.setSortColumn(null);
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("removeDepartmentEmployees():");
		}
	}

	/**
	 * Sorts table.
	 * 
	 * @param columnIndex the column index
	 */
	private void sortTable(final int columnIndex) {

		if (employeesTable.getItemCount() <= 0) {
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("sortTable():");
			}
			return;
		}
		TableColumn sortColumn = employeesTable.getSortColumn();
		TableColumn selectedColumn = employeesTable.getColumn(columnIndex);
		int dir = employeesTable.getSortDirection();
		if (sortColumn == selectedColumn) {
			dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
		} else {
			employeesTable.setSortColumn(selectedColumn);
			dir = SWT.UP;
		}
		employeesTable.setSortDirection(dir);
		final boolean sortDirectionDown = dir == SWT.DOWN;

		Comparator<Employee> comparator = new Comparator<Employee>() {

			public int compare(Employee emp1, Employee emp2) {
				StringBuilder key1 = new StringBuilder();
				StringBuilder key2 = new StringBuilder();
				switch (columnIndex) {
				case 0:
					key1.append(emp1.getFirstName());
					key2.append(emp2.getFirstName());
				case 1:
					key1.append(emp1.getLastName());
					key2.append(emp2.getLastName());
				default:
					key1.append(emp1.getTitle().getName());
					key2.append(emp2.getTitle().getName());
				}
				key1.append(emp1.getId());
				key2.append(emp2.getId());
				int result = key1.toString().compareTo(key2.toString());
				result = sortDirectionDown ? -result : result;
				return result;
			}
		};
		TreeSet<Employee> employeesSorted = new TreeSet<Employee>(comparator);
		TableItem[] items = employeesTable.getItems();
		for (TableItem item : items) {
			Employee employee = (Employee) item.getData();
			employeesSorted.add(employee);
		}
		// update data displayed in table
		employeesTable.removeAll();
		for (Employee employee : employeesSorted) {
			String titleName = employee.getTitle().getName();
			TableItem item = new TableItem(employeesTable, SWT.NONE);
			String[] rowContent = new String[] { employee.getFirstName(), employee.getLastName(), titleName };
			item.setText(rowContent);
			item.setData(employee);
			item.setBackground(2, titleColors.get(titleName));
		}
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("sortTable():");
		}
	}
}
