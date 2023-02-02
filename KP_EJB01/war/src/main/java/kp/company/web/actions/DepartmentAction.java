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
import kp.company.request.BusinessLogic;

/**
 * Class DepartmentAction.
 */
public class DepartmentAction extends ActionSupport {

	private static final Logger logger = Logger
			.getLogger(DepartmentAction.class.getName());

	private static final long serialVersionUID = 1L;
	private BusinessLogic businessLogic = null;
	private Department department = null;
	private String departmentId = null;

	/**
	 * Constructor.
	 * 
	 */
	public DepartmentAction() {

		ServletContext servletContext = ServletActionContext
				.getServletContext();
		businessLogic = (BusinessLogic) servletContext
				.getAttribute("businessLogic");
	}

	/**
	 * List action.
	 * 
	 * @return result
	 */
	public String list() {

		logger.info("list():");
		return SUCCESS;
	}

	/**
	 * Edit action.
	 * 
	 * @return result
	 */
	public String edit() {

		loadDepartment();
		logger.info("edit():");
		return INPUT;
	}

	/**
	 * Save action.
	 * 
	 * @return result
	 */
	public String save() {

		businessLogic.storeDepartment(department);
		logger.info("save():");
		return SUCCESS;
	}

	/**
	 * Delete action.
	 * 
	 * @return result
	 */
	public String delete() {

		loadDepartment();
		businessLogic.deleteDepartment(department.getId());
		logger.info("delete():");
		return SUCCESS;
	}

	/**
	 * Gets departments.
	 * 
	 * @return the departments
	 */
	public List<Department> getDepartments() {

		List<Department> departments = businessLogic.loadDepartments();
		logger.info("getDepartments():");
		return departments;
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
	 * Sets department.
	 * 
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(Department department) {

		this.department = department;
		logger.info("setDepartment():");
	}

	/**
	 * 
	 * @return the departmentId
	 */
	public String getDepartmentId() {
		return departmentId;
	}

	/**
	 * 
	 * @param departmentId the departmentId to set
	 */
	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	/**
	 * Loads department.
	 * 
	 */
	private void loadDepartment() {

		ActionContext context = ActionContext.getContext();
		HttpParameters parameters = context.getParameters();
		Parameter idParam = parameters.get("departmentId");

		Long departmentId = null;
		if (Objects.nonNull(idParam)) {
			String[] idArray = idParam.getMultipleValues();
			if (idArray.length > 0 && Objects.nonNull(idArray[0]) && !idArray[0].trim().isEmpty()) {
				departmentId = Long.valueOf(idArray[0]);
			}
		}
		if (Objects.nonNull(departmentId)) {
			department = businessLogic.loadDepartment(departmentId);
		} else {
			department = new Department();
		}
	}
}
