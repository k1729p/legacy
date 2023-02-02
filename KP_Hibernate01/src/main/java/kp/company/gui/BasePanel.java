package kp.company.gui;

import java.util.logging.Level;
import java.util.logging.Logger;

import kp.company.BusinessLogic;
import kp.company.exc.KpException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

/**
 * Class BasePanel.
 */
public class BasePanel {

	private static final Logger logger = Logger.getLogger(BasePanel.class
			.getName());
	private Shell shell = null;
	private SummaryViewer summaryViewer = null;
	private DetailsSash detailsSash = null;
	private HelpViewer helpViewer = null;
	private Color[] background = null;

	/**
	 * Opens shell.
	 * 
	 * @param display the display
	 * @return the shell
	 */
	public Shell open(Display display) {

		background = new Color[] { new Color(display, 192, 255, 192),
				new Color(display, 192, 192, 255),
				new Color(display, 255, 192, 192) };
		shell = new Shell(display);
		shell.setText("KP Hibernate 01");

		shell.setLayout(new FillLayout());
		shell.addShellListener(new ShellAdapter() {
			public void shellClosed(ShellEvent e) {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("");
				}
			}
		});
		shell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (logger.isLoggable(Level.FINE)) {
					logger.fine("open():");
				}
			}
		});
		createTabs();
		createMenuBar();

		shell.pack();
		shell.setLocation(250, 50);
		shell.setSize(600, 400);
		shell.open();
		return shell;
	}

	/**
	 * Creates tab folder page.
	 */
	private void createTabs() {

		String[] tabText = { "Summary", "Details" };
		String[] tooltipText = tabText;
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		Composite[] tabFolderPage = new Composite[2];

		for (int i = 0; i < tabFolderPage.length; i++) {
			tabFolderPage[i] = new Composite(tabFolder, SWT.NONE);
			tabFolderPage[i].setLayout(new GridLayout(1, false));
			tabFolderPage[i].setLayoutData(new GridData(SWT.FILL, SWT.FILL,
					true, true));
			tabFolderPage[i].setBackground(background[i]);

			TabItem tabItem = new TabItem(tabFolder, SWT.NONE);
			tabItem.setText(tabText[i]);
			tabItem.setToolTipText(tooltipText[i]);
			tabItem.setControl(tabFolderPage[i]);
		}
		tabFolder.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				reloadDataFromDatabase();
			}
		});
		summaryViewer = new SummaryViewer(shell);
		detailsSash = new DetailsSash(shell);
		try {
			summaryViewer.create(tabFolderPage[0]);
			detailsSash.create(tabFolderPage[1]);
		} catch (KpException ex) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL
					| SWT.ICON_ERROR);
			messageBox.setText("KpException in create.");
			messageBox.setMessage("KpException[" + ex.getMessage() + "]");
			messageBox.open();
			logger.log(Level.SEVERE, "createTabs(): Exception["
					+ ex.getMessage() + "]", ex);
			System.exit(1);
		}
	}

	/**
	 * Creates menu bar.
	 */
	private void createMenuBar() {

		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		createSettingsMenu(menuBar);
		createHelpMenu(menuBar);

		if (logger.isLoggable(Level.FINE)) {
			logger.fine("createMenuBar():");
		}
	}

	/**
	 * Creates settings menu items.
	 * 
	 * @param menuBar the menu bar
	 */
	private void createSettingsMenu(Menu menuBar) {

		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);

		MenuItem subItem = null;
		item.setText("Settings");
		Menu menu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(menu);

		// menu item "Join Departments"
		subItem = new MenuItem(menu, SWT.NONE);
		subItem.setText("Join Departments");
		subItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				joinDepartments();
			}
		});

		new MenuItem(menu, SWT.SEPARATOR);

		// menu item "Exit"
		subItem = new MenuItem(menu, SWT.NONE);
		subItem.setText("Exit");
		subItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("createSettingsMenu():");
		}
	}

	/**
	 * Joins two departments.
	 */
	private void joinDepartments() {

		boolean result = false;
		try {
			BusinessLogic businessLogic = new BusinessLogic();
			result = businessLogic.joinDepartments();
			reloadDataFromDatabase();
		} catch (KpException ex) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL
					| SWT.ICON_ERROR);
			messageBox.setText("Error");
			messageBox.setMessage("Error with joining departments");
			messageBox.open();
			logger.severe("joinDepartments(): KpException");
		}
		MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL
				| SWT.ICON_INFORMATION);
		messageBox.setText("Join Departments");
		if (result) {
			messageBox.setMessage("Departments joining ended with success.");
		} else {
			messageBox.setMessage("Departments joining ended with failure.");
		}
		messageBox.open();
		if (logger.isLoggable(Level.INFO)) {
			logger.info("joinDepartments():");
		}
	}

	/**
	 * Reloads data from database.
	 */
	private void reloadDataFromDatabase() {

		summaryViewer.loadCompanySummary();
		detailsSash.reloadDepartments();
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("reloadDataFromDatabase():");
		}
	}

	/**
	 * Creates menu items for help menu.
	 * 
	 * @param menuBar the menu bar
	 */
	private void createHelpMenu(Menu menuBar) {

		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		MenuItem subItem = null;
		item.setText("Help");
		Menu menu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(menu);

		subItem = new MenuItem(menu, SWT.NONE);
		subItem.setText("About");
		subItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MessageBox dialog = new MessageBox(shell, SWT.APPLICATION_MODAL);
				dialog.setText("About");
				dialog.setMessage("KP Hibernate Example");
				dialog.open();
			}
		});
		helpViewer = new HelpViewer(shell);
		new MenuItem(menu, SWT.SEPARATOR);
		subItem = new MenuItem(menu, SWT.NONE);
		subItem.setText("Help");
		subItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				helpViewer.showHelp();
			}
		});
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("createHelpMenu():");
		}
	}
}
