package kp.company.gui;

import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
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
import kp.company.exc.KpException;

/**
 * Class DepartmentsViewer.
 */
public class DepartmentsViewer {

	private static final Logger logger = Logger.getLogger(DepartmentsViewer.class.getName());

	private Shell shell = null;
	private Table departmentsTable = null;
	private EmployeesViewer employeesViewer = null;

	/**
	 * Constructor with one parameter.
	 * 
	 * @param shell the shell
	 */
	public DepartmentsViewer(Shell shell) {

		this.shell = shell;
	}

	/**
	 * Creates departments table.
	 * 
	 * @param composite       composite
	 * @param employeesViewer employees viewer
	 * @throws KpException the exception
	 */
	protected void create(Composite composite, EmployeesViewer employeesViewer) throws KpException {

		this.employeesViewer = employeesViewer;

		Group departmentsGroup = new Group(composite, SWT.NONE);
		departmentsGroup.setLayout(new GridLayout());
		departmentsGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		departmentsGroup.setText("Departments");

		departmentsTable = new Table(departmentsGroup, SWT.SINGLE | SWT.BORDER | SWT.FULL_SELECTION);
		departmentsTable.setLayout(new GridLayout(1, false));
		departmentsTable.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		departmentsTable.setHeaderVisible(true);
		departmentsTable.setLinesVisible(true);
		departmentsTable.setMenu(createPopUpMenu(shell));
		departmentsTable.setVisible(true);

		departmentsTable.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				TableItem[] items = departmentsTable.getSelection();
				if (items.length > 0) {
					editDepartment(items[0]);
				}
			}

			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = departmentsTable.getSelection();
				if (items.length > 0) {
					showDepartmentEmployees(items[0]);
				}
			}
		});
		departmentsTable.addTraverseListener(new TraverseListener() {
			public void keyTraversed(TraverseEvent ev) {
				TableItem[] items = departmentsTable.getSelection();
				if (items.length > 0) {
					showDepartmentEmployees(items[0]);
				}
			}
		});

		String[] columnNames = new String[] { "Name" };
		for (int i = 0; i < columnNames.length; i++) {
			TableColumn column = new TableColumn(departmentsTable, SWT.NONE);
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
		loadDepartments();
		if (logger.isLoggable(Level.INFO)) {
			logger.info("create():");
		}
	}

	/**
	 * Selects department from the top of the departments table.
	 */
	public void selectTopDepartment() {

		TableItem[] items = departmentsTable.getItems();
		if (items.length > 0) {
			departmentsTable.select(0);
			showDepartmentEmployees(items[0]);
		}
	}

	/**
	 * Creates departments pop-up menu.
	 * 
	 * @param shell the shell
	 */
	private Menu createPopUpMenu(Shell shell) {

		Menu popUpMenu = new Menu(shell, SWT.POP_UP);
		popUpMenu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent ev) {
				Menu menu = (Menu) ev.widget;
				MenuItem[] items = menu.getItems();
				if (departmentsTable.getSelectionCount() > 0) {
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
		item.setText("Edit Department");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = departmentsTable.getSelection();
				if (items.length > 0) {
					editDepartment(items[0]);
				}
			}
		});

		// Delete
		item = new MenuItem(popUpMenu, SWT.PUSH);
		item.setText("Delete Department");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				TableItem[] items = departmentsTable.getSelection();
				if (items.length > 0) {
					deleteDepartment(items[0]);
				}
			}
		});

		new MenuItem(popUpMenu, SWT.SEPARATOR);

		// Add
		item = new MenuItem(popUpMenu, SWT.PUSH);
		item.setText("Add Department");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				addDepartment();
			}
		});
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("createPopUpMenu():");
		}
		return popUpMenu;
	}

	/**
	 * Adds new department.
	 * 
	 */
	private void addDepartment() {

		departmentsTable.setSortColumn(null);
		Department department = new Department();
		DepartmentDialog dialog = new DepartmentDialog(shell, department);
		if (!dialog.open()) {
			if (logger.isLoggable(Level.CONFIG)) {
				logger.config("addDepartment(): cancel");
			}
			return;
		}
		TableItem item = new TableItem(departmentsTable, SWT.NONE);
		String name = department.getName();
		String[] rowContent = new String[] { name };
		item.setText(rowContent);
		item.setData(department);
		departmentsTable.select(departmentsTable.indexOf(item));
		showDepartmentEmployees(item);

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("addDepartment(): name[" + name + "]");
		}
	}

	/**
	 * Edits department.
	 * 
	 * @param item the item
	 */
	private void editDepartment(TableItem item) {

		departmentsTable.setSortColumn(null);
		Department department = (Department) item.getData();
		DepartmentDialog dialog = new DepartmentDialog(shell, department);
		if (!dialog.open()) {
			if (logger.isLoggable(Level.CONFIG)) {
				logger.config("editDepartment(): cancel");
			}
			return;
		}
		String name = department.getName();
		String[] rowContent = new String[] { name };
		item.setText(rowContent);
		item.setData(department);

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("editDepartment(): name[" + name + "]");
		}
	}

	/**
	 * Deletes department.
	 * 
	 * @param item the item
	 */
	private void deleteDepartment(TableItem item) {

		departmentsTable.setSortColumn(null);
		// ask user if they want to delete selected department
		MessageBox messageBox = new MessageBox(shell, SWT.ICON_WARNING | SWT.YES | SWT.NO | SWT.CANCEL);
		messageBox.setText("Delete Department");
		messageBox.setMessage("Delete selected department?");
		int choice = messageBox.open();
		if (choice != SWT.YES) {
			if (logger.isLoggable(Level.CONFIG)) {
				logger.config("deleteDepartment(): cancel");
			}
			return;
		}
		Department department = (Department) item.getData();
		String name = department.getName();
		try {
			BusinessLogic businessLogic = new BusinessLogic();
			businessLogic.deleteDepartment(department);
		} catch (KpException ex) {
			messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR);
			messageBox.setText("Error");
			messageBox.setMessage("Error with \"" + department.getName() + "\" department deleting");
			messageBox.open();
			logger.severe("deleteDepartment(): KpException");
			return;
		}
		item.dispose();
		employeesViewer.removeDepartmentEmployees();

		if (departmentsTable.getItemCount() > 0) {
			TableItem[] items = departmentsTable.getItems();
			int index = departmentsTable.getTopIndex();
			departmentsTable.select(index);
			showDepartmentEmployees(items[index]);
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("deleteDepartment(): name[" + name + "]");
		}
	}

	/**
	 * Loads departments.
	 */
	public void loadDepartments() {

		departmentsTable.removeAll();
		departmentsTable.setSortColumn(null);
		List<Department> departments = null;
		try {
			BusinessLogic businessLogic = new BusinessLogic();
			departments = businessLogic.loadDepartments();
		} catch (KpException ex) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR);
			messageBox.setText("Error");
			messageBox.setMessage("Error with department loading");
			messageBox.open();
			logger.severe("loadDepartments(): KpException");
		}
		for (Department department : departments) {
			String name = department.getName();
			TableItem item = new TableItem(departmentsTable, SWT.NONE);

			String[] rowContent = new String[] { name };
			item.setText(rowContent);
			item.setData(department);
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadDepartments(): ");
		}
	}

	/**
	 * Shows department employees.
	 * 
	 * @param item the item
	 */
	private void showDepartmentEmployees(TableItem item) {

		Department department = (Department) item.getData();
		employeesViewer.showDepartmentEmployees(department);
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("showDepartmentEmployees(): name[" + department.getName() + "]");
		}
	}

	/**
	 * Sorts table.
	 * 
	 * @param columnIndex the column index
	 */
	private void sortTable(final int columnIndex) {

		if (departmentsTable.getItemCount() <= 0) {
			if (logger.isLoggable(Level.FINE)) {
				logger.fine("sortTable():");
			}
			return;
		}
		TableColumn sortColumn = departmentsTable.getSortColumn();
		TableColumn selectedColumn = departmentsTable.getColumn(columnIndex);
		int dir = departmentsTable.getSortDirection();
		if (sortColumn == selectedColumn) {
			dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
		} else {
			departmentsTable.setSortColumn(selectedColumn);
			dir = SWT.UP;
		}
		departmentsTable.setSortDirection(dir);
		final boolean sortDirectionDown = dir == SWT.DOWN;

		Comparator<Department> comparator = new Comparator<Department>() {

			public int compare(Department dep1, Department dep2) {
				StringBuilder key1 = new StringBuilder();
				StringBuilder key2 = new StringBuilder();
				switch (columnIndex) {
				default:
					key1.append(dep1.getName());
					key2.append(dep2.getName());
				}
				key1.append(dep1.getId());
				key2.append(dep2.getId());
				int result = key1.toString().compareTo(key2.toString());
				result = sortDirectionDown ? -result : result;
				return result;
			}
		};
		TreeSet<Department> departmentsSorted = new TreeSet<Department>(comparator);
		TableItem[] items = departmentsTable.getItems();
		for (TableItem item : items) {
			Department department = (Department) item.getData();
			departmentsSorted.add(department);
		}
		// update data displayed in table
		departmentsTable.removeAll();
		employeesViewer.removeDepartmentEmployees();
		for (Department department : departmentsSorted) {
			TableItem item = new TableItem(departmentsTable, SWT.NONE);
			String[] rowContent = new String[] { department.getName() };
			item.setText(rowContent);
			item.setData(department);
		}
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("sortTable():");
		}
	}
}
