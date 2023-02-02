package kp.company.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import kp.company.BusinessLogic;
import kp.company.domain.Department;
import kp.company.domain.Employee;
import kp.company.domain.Title;
import kp.company.exc.KpException;
import kp.company.util.LoggingConfiguration;

/**
 * Company backing bean, that is backed to xhtml pages.
 * 
 */
@ManagedBean
@SessionScoped
public class CompanyBackingBean implements Serializable {
	private static final Logger logger = Logger.getLogger(CompanyBackingBean.class.getName());

	private static final long serialVersionUID = 1L;

	private BusinessLogic businessLogic = null;
	private Department department = null;
	private Employee employee = null;
	private Long selectedTitleId = null;

	/**
	 * Default empty constructor.
	 */
	public CompanyBackingBean() {

		LoggingConfiguration.setLogging();	
		businessLogic = new BusinessLogic();
		if (logger.isLoggable(Level.INFO)) {
			logger.info("CompanyBackingBean():");
		}
	}

	/**
	 * Sets department.
	 * 
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(Department department) {

		this.department = department;
	}

	/**
	 * Gets department.
	 * 
	 * @return the department
	 */
	public Department getDepartment() {

		return department;
	}

	/**
	 * Sets employee.
	 * 
	 * @param employee
	 *            the employee to set
	 */
	public void setEmployee(Employee employee) {

		this.employee = employee;
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
	 * Sets selected title id.
	 * 
	 * @param selectedTitleId
	 *            the selected title id to set
	 */
	public void setSelectedTitleId(Long selectedTitleId) {

		this.selectedTitleId = selectedTitleId;
	}

	/**
	 * Gets selected title id.
	 * 
	 * @return the titleSelectItem
	 */
	public Long getSelectedTitleId() {

		return selectedTitleId;
	}

	/**
	 * Gets departments.
	 * 
	 * @return list of departments
	 * @throws KpException
	 *             the exception
	 */
	public List<Department> getDepartments() throws KpException {

		List<Department> departments = businessLogic.loadDepartments();
		if (logger.isLoggable(Level.INFO)) {
			logger.info("getDepartments():");
		}
		return departments;
	}

	/**
	 * Gets employees.
	 * 
	 * @return list of departments
	 * @throws KpException
	 *             the exception
	 */
	public List<Employee> getEmployees() throws KpException {

		if (department == null) {
			logger.severe("getEmployees(): department == null");
			return null;
		}
		List<Employee> employees = businessLogic.loadEmployees(department.getId());
		if (logger.isLoggable(Level.INFO)) {
			logger.info("getEmployees():");
		}
		return employees;
	}

	/**
	 * Gets title select items.
	 * 
	 * @return list of title select items
	 * @throws KpException
	 *             the exception
	 */
	public List<SelectItem> getTitleSelectItems() throws KpException {

		List<Title> titles = businessLogic.loadTitles();
		List<SelectItem> titleSelectItem = new ArrayList<SelectItem>();
		for (Title t : titles) {
			SelectItem selectItem = new SelectItem(t.getId(), t.getName());
			titleSelectItem.add(selectItem);
		}
		if (employee != null && employee.getTitle() != null) {
			selectedTitleId = employee.getTitle().getId();
		} else {
			selectedTitleId = 0L;
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("getTitleSelectItems():");
		}
		return titleSelectItem;
	}

	/**
	 * List departments. Wrapper for "kp:columnCommand" tag.
	 * 
	 * @return outcome the outcome
	 */
	public String listDepartments() {

		return "listDepartments";
	}

	/**
	 * Lists employees. Wrapper for "kp:columnCommand" tag.
	 * 
	 * @return outcome the outcome
	 */
	public String listEmployees() {

		return "listEmployees";
	}

	/**
	 * Edits department. Wrapper for "kp:columnCommand" tag.
	 * 
	 * @return outcome the outcome
	 */
	public String editDepartment() {

		return "editDepartment";
	}

	/**
	 * Confirms deleting department. Wrapper for "kp:columnCommand" tag.
	 * 
	 * @return outcome the outcome
	 */
	public String confirmDeleteDepartment() {

		return "confirmDeleteDepartment";
	}

	/**
	 * Edits employee. Wrapper for "kp:columnCommand" tag.
	 * 
	 * @return outcome the outcome
	 */
	public String editEmployee() {

		return "editEmployee";
	}

	/**
	 * Confirms deleting department. Wrapper for "kp:columnCommand" tag.
	 * 
	 * @return outcome the outcome
	 */
	public String confirmDeleteEmployee() {

		return "confirmDeleteEmployee";
	}

	/**
	 * Prepares adding new department.
	 * 
	 * @return outcome the outcome
	 */
	public String prepareAddDepartment() {

		department = new Department();
		String outcome = "editDepartment";
		if (logger.isLoggable(Level.INFO)) {
			logger.info("prepareAddDepartment(): outcome[" + outcome + "]");
		}
		return outcome;
	}

	/**
	 * Updates department.
	 * 
	 * @return outcome the outcome
	 * @throws KpException
	 *             the exception
	 */
	public String updateDepartment() throws KpException {

		businessLogic.storeDepartment(department);
		String outcome = "listDepartments";
		if (logger.isLoggable(Level.INFO)) {
			logger.info("updateDepartment(): outcome[" + outcome + "]");
		}
		return outcome;
	}

	/**
	 * Deletes department.
	 * 
	 * @return outcome the outcome
	 * @throws KpException
	 *             the exception
	 */
	public String deleteDepartment() throws KpException {

		businessLogic.deleteDepartment(department.getId());
		String outcome = "listDepartments";
		if (logger.isLoggable(Level.INFO)) {
			logger.info("deleteDepartment(): outcome[" + outcome + "]");
		}
		return outcome;
	}

	/**
	 * Prepares adding new employee.
	 * 
	 * @return the employee
	 */
	public String prepareAddEmployee() {

		employee = new Employee();
		if (department != null) {
			employee.setDepartment(department);
		}
		String outcome = "editEmployee";
		if (logger.isLoggable(Level.INFO)) {
			logger.info("prepareAddDepartment(): outcome[" + outcome + "]");
		}
		return outcome;
	}

	/**
	 * Updates employee.
	 * 
	 * @return outcome the outcome
	 * @throws KpException
	 *             the exception
	 */
	public String updateEmployee() throws KpException {

		List<Title> titles = businessLogic.loadTitles();
		for (Title title : titles) {
			if (selectedTitleId.equals(title.getId())) {
				employee.setTitle(title);
			}
		}
		businessLogic.storeEmployee(employee);
		String outcome = "listEmployees";
		if (logger.isLoggable(Level.INFO)) {
			logger.info("updateEmployee(): outcome[" + outcome + "]");
		}
		return outcome;
	}

	/**
	 * Deletes employee.
	 * 
	 * @return outcome the outcome
	 * @throws KpException
	 *             the exception
	 */
	public String deleteEmployee() throws KpException {

		businessLogic.deleteEmployee(employee.getId());
		String outcome = "listEmployees";
		if (logger.isLoggable(Level.INFO)) {
			logger.info("deleteEmployee(): outcome[" + outcome + "]");
		}
		return outcome;
	}

}