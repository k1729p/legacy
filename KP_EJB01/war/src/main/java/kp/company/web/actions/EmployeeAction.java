package kp.company.web.actions;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.dispatcher.Parameter;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import kp.company.entity.Department;
import kp.company.entity.Employee;
import kp.company.entity.Title;
import kp.company.request.BusinessLogic;

/**
 * Class EmployeeAction.
 */
public class EmployeeAction extends ActionSupport {

	private static final Logger logger = Logger.getLogger(EmployeeAction.class.getName());

	private static final long serialVersionUID = 1L;
	private BusinessLogic businessLogic = null;

	private Long departmentId = null;
	private String departmentName = null;
	private Employee employee = null;
	private String employeeId = null;
	private Long titleId = null;

	/**
	 * Constructor.
	 * 
	 */
	public EmployeeAction() {

		ServletContext servletContext = ServletActionContext.getServletContext();
		businessLogic = (BusinessLogic) servletContext.getAttribute("businessLogic");
	}

	/**
	 * List action.
	 * 
	 * @return result
	 */
	public String list() {

		loadDepartmentIdAndName();
		logger.info("list():");
		return SUCCESS;
	}

	/**
	 * Edit action.
	 * 
	 * @return result
	 */
	public String edit() {

		loadDepartmentIdAndName();
		loadEmployee();
		logger.info("edit():");
		return INPUT;
	}

	/**
	 * Save action.
	 * 
	 * @return result
	 */
	public String save() {

		Department department = businessLogic.loadDepartment(departmentId);
		employee.setDepartment(department);
		departmentName = department.getName();

		Title title = businessLogic.loadTitle(titleId);
		employee.setTitle(title);

		businessLogic.storeEmployee(employee);
		logger.info("save():");
		return SUCCESS;
	}

	/**
	 * Delete action.
	 * 
	 * @return result
	 */
	public String delete() {

		loadDepartmentIdAndName();
		loadEmployee();

		businessLogic.deleteEmployee(employee.getId());
		logger.info("delete():");
		return SUCCESS;
	}

	/**
	 * Sets department id.
	 * 
	 * @param departmentId
	 *            the department id to set
	 */
	public void setDepartmentId(Long departmentId) {

		this.departmentId = departmentId;
	}

	/**
	 * Gets department id.
	 * 
	 * @return the department id
	 */
	public Long getDepartmentId() {

		return departmentId;
	}

	/**
	 * Sets department name.
	 * 
	 * @param departmentName
	 *            the department name to set
	 */
	public void setDepartmentName(String departmentName) {

		this.departmentName = departmentName;
	}

	/**
	 * Gets department name.
	 * 
	 * @return the department name
	 */
	public String getDepartmentName() {

		return departmentName;
	}

	/**
	 * Gets employees.
	 * 
	 * @return the employees
	 */
	public List<Employee> getEmployees() {

		List<Employee> employees = businessLogic.loadEmployees(departmentId);
		logger.info("getEmployees():");
		return employees;
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
	 * Sets employee.
	 * 
	 * @param employee
	 *            the employee to set
	 */
	public void setEmployee(Employee employee) {

		this.employee = employee;
		logger.info("setEmployee():");
	}

	/**
	 * @return the employeeId
	 */
	public String getEmployeeId() {
		return employeeId;
	}

	/**
	 * @param employeeId
	 *            the employeeId to set
	 */
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	/**
	 * Gets titles.
	 * 
	 * @return the titles
	 */
	public List<Title> getTitles() {

		List<Title> titles = businessLogic.loadTitles();
		logger.info("getTitles():");
		return titles;
	}

	/**
	 * Gets title id.
	 * 
	 * @return the title id
	 */
	public Long getTitleId() {
		return titleId;
	}

	/**
	 * Sets title id.
	 * 
	 * @param titleId
	 *            the titleId to set
	 */
	public void setTitleId(Long titleId) {
		this.titleId = titleId;
	}

	/**
	 * Loads department id and name.
	 * 
	 */
	private void loadDepartmentIdAndName() {

		ActionContext context = ActionContext.getContext();
		HttpParameters parameters = context.getParameters();
		Parameter idParam = parameters.get("departmentId");

		departmentId = null;
		if (Objects.nonNull(idParam)) {
			String[] idArray = idParam.getMultipleValues();
			if (idArray.length > 0 && Objects.nonNull(idArray[0]) && !idArray[0].trim().isEmpty()) {
				departmentId = Long.valueOf(idArray[0]);
			}
		}
		if (Objects.isNull(departmentId)) {
			logger.severe("loadDepartmentIdAndName(): Empty 'departmentId' parameter!");
			throw new RuntimeException("Empty 'departmentId' parameter!");
		}
		Department department = businessLogic.loadDepartment(departmentId);
		departmentName = department.getName();
	}

	/**
	 * Loads employee.
	 * 
	 */
	private void loadEmployee() {

		ActionContext context = ActionContext.getContext();
		HttpParameters parameters = context.getParameters();
		Parameter idParam = parameters.get("employeeId");

		Long employeeId = null;
		if (Objects.nonNull(idParam)) {
			String[] idArray = idParam.getMultipleValues();
			if (idArray.length > 0 && Objects.nonNull(idArray[0]) && !idArray[0].trim().isEmpty()) {
				employeeId = Long.valueOf(idArray[0]);
			}
		}
		if (Objects.nonNull(employeeId)) {
			employee = businessLogic.loadEmployee(departmentId, employeeId);
		} else {
			employee = new Employee();
		}
	}
}
