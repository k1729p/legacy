package kp.company.gui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import kp.company.BusinessLogic;
import kp.company.domain.Title;
import kp.company.exc.KpException;

/**
 * Class SummaryViewer.
 */
public class SummaryViewer {

	private static final Logger logger = Logger.getLogger(SummaryViewer.class.getName());

	private Shell shell = null;
	private Tree companyTree = null;
	private Color[] background = null;

	/**
	 * Constructor with one parameter.
	 * 
	 * @param shell the shell
	 */
	public SummaryViewer(Shell shell) {

		this.shell = shell;
		this.background = new Color[] { new Color(shell.getDisplay(), 208, 208, 255),
				new Color(shell.getDisplay(), 208, 255, 208), new Color(shell.getDisplay(), 255, 208, 208) };
	}

	/**
	 * Creates departments list.
	 * 
	 * @param composite the composite
	 * @throws KpException the exception
	 */
	protected void create(Composite composite) throws KpException {

		Group companyGroup = new Group(composite, SWT.NONE);
		companyGroup.setLayout(new GridLayout());
		companyGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		companyGroup.setText("Company");

		companyTree = new Tree(companyGroup, SWT.SINGLE | SWT.BORDER);
		companyTree.setLayout(new GridLayout(1, false));
		companyTree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		loadCompanySummary();
		if (logger.isLoggable(Level.INFO)) {
			logger.info("create():");
		}
	}

	/**
	 * Loads company summary.
	 */
	public void loadCompanySummary() {

		companyTree.removeAll();
		BusinessLogic businessLogic = new BusinessLogic();
		HashMap<String, Color> titleColors = new HashMap<String, Color>();
		try {
			List<Title> titles = businessLogic.loadTitles();
			for (int i = 0; i < titles.size(); i++) {
				String titleKey = titles.get(i).getName().substring(0, 4);
				titleColors.put(titleKey, background[i % 3]);
			}
			Map<String, List<String>> summaryMap = businessLogic.loadSummary();
			StringBuilder buf = new StringBuilder();
			buf.append(summaryMap.size());
			buf.append(" department");
			if (summaryMap.size() != 1) {
				buf.append("s");
			}
			TreeItem compItem = new TreeItem(companyTree, SWT.NONE);
			compItem.setText(buf.toString());
			for (String departmentName : summaryMap.keySet()) {
				TreeItem depItem = new TreeItem(compItem, SWT.NONE);
				depItem.setText(departmentName);
				for (String empCount : summaryMap.get(departmentName)) {
					TreeItem empItem = new TreeItem(depItem, SWT.NONE);
					empItem.setText(empCount);
					int index = empCount.indexOf(" ") + 1;
					String titleKey = empCount.substring(index, index + 4);
					empItem.setBackground(titleColors.get(titleKey));
				}
				depItem.setExpanded(true);
			}
			compItem.setExpanded(true);
		} catch (KpException ex) {
			MessageBox messageBox = new MessageBox(shell, SWT.APPLICATION_MODAL | SWT.ICON_ERROR);
			messageBox.setText("Error");
			messageBox.setMessage("Error with department loading");
			messageBox.open();
			logger.severe("loadDepartmentsSummary(): KpException");
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadDepartmentsSummary(): ");
		}
	}
}
