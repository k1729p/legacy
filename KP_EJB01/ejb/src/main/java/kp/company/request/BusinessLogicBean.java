package kp.company.request;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import kp.company.entity.Department;
import kp.company.entity.Employee;
import kp.company.entity.Title;

/**
 * Class BusinessLogicBean.
 * 
 */
@Stateful
@Remote(BusinessLogic.class)
public class BusinessLogicBean implements BusinessLogic, Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger
			.getLogger(BusinessLogicBean.class.getName());

	@PersistenceContext(unitName = "kp_ejb01")
	private EntityManager entityManager = null;

	/**
	 * Retrieves all <code>Department</code>s from the data store.
	 * 
	 * @return the list of departments
	 */
	@SuppressWarnings("unchecked")
	public List<Department> loadDepartments() {

		List<Department> departments = null;
		String ql = "select department from Department department "
				+ "order by department.name asc";
		Query query = entityManager.createQuery(ql);
		departments = (List<Department>) query.getResultList();

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadDepartments():");
		}
		return departments;
	}

	/**
	 * Retrieves <code>Department</code> from the data store.
	 * 
	 * @return the department
	 */
	public Department loadDepartment(Long departmentId) {

		Department department = null;
		String ql = "select department from Department department "
				+ "where department.id=:departmentId";
		Query query = entityManager.createQuery(ql);
		query.setParameter("departmentId", departmentId);
		department = (Department) query.getSingleResult();

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadDepartment():");
		}
		return department;
	}

	/**
	 * Retrieves all <code>Employee</code>s from the data store.
	 * 
	 * @param departmentId
	 *            departmentId
	 * @return the list of employees
	 */
	@SuppressWarnings("unchecked")
	public List<Employee> loadEmployees(Long departmentId) {

		List<Employee> employees = null;
		String ql = "select employee from Employee employee "
				+ "where employee.department.id=:departmentId "
				+ "order by employee.lastName asc";
		Query query = entityManager.createQuery(ql);
		query.setParameter("departmentId", departmentId);
		employees = (List<Employee>) query.getResultList();

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadEmployees():");
		}
		return employees;
	}

	/**
	 * Retrieves <code>Employee</code> from the data store.
	 * 
	 * @param departmentId
	 *            departmentId
	 * @param employeeId
	 *            employeeId
	 * @return employee
	 */
	public Employee loadEmployee(Long departmentId, Long employeeId) {

		Employee employee = null;
		String ql = "select employee from Employee employee "
				+ "where employee.department.id=:departmentId "
				+ "and employee.id=:employeeId";
		Query query = entityManager.createQuery(ql);
		query.setParameter("departmentId", departmentId);
		query.setParameter("employeeId", employeeId);
		employee = (Employee) query.getSingleResult();

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadEmployee():");
		}
		return employee;
	}

	/**
	 * Retrieves all <code>Title</code>s from the data store.
	 * 
	 * @return the list of titles
	 */
	@SuppressWarnings("unchecked")
	public List<Title> loadTitles() {

		List<Title> titles = null;
		String ql = "select title from Title title "
				+ "order by title.name asc";
		Query query = entityManager.createQuery(ql);
		titles = (List<Title>) query.getResultList();

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadTitles():");
		}
		return titles;
	}

	/**
	 * Retrieves <code>Title</code> from the data store.
	 * 
	 * @param titleId
	 *            titleId
	 * @return the title
	 */
	public Title loadTitle(Long titleId) {

		Title title = null;
		String ql = "select title from Title title "
				+ "where title.id=:titleId";
		Query query = entityManager.createQuery(ql);
		query.setParameter("titleId", titleId);
		title = (Title) query.getSingleResult();

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("loadTitle():");
		}
		return title;
	}

	/**
	 * Stores a <code>Department</code> to the data store.
	 * 
	 * @param department
	 *            department
	 * @return merged department
	 */
	public Department storeDepartment(Department department) {

		Department mergedDepartment = null;
		mergedDepartment = entityManager.merge(department);

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("storeDepartment():");
		}
		return mergedDepartment;
	}

	/**
	 * Stores an <code>Employee</code> to the data store.
	 * 
	 * @param employee
	 *            employee
	 * @return merged employee
	 */
	public Employee storeEmployee(Employee employee) {

		return storeEmployee(null, employee);
	}

	/**
	 * Stores an <code>Employee</code> to the data store.
	 * 
	 * @param department
	 *            department
	 * @param employee
	 *            employee
	 * @return merged employee
	 */
	public Employee storeEmployee(Department department, Employee employee) {

		Employee mergedEmployee = null;
		if (department != null) {
			employee.setDepartment(department);
		}
		mergedEmployee = entityManager.merge(employee);
		if (department != null) {
			department.addEmployee(mergedEmployee);
			entityManager.merge(department);
		}
		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("storeEmployee():");
		}
		return mergedEmployee;
	}

	/**
	 * Deletes a <code>Department</code> from the data store.
	 * 
	 * @param id
	 *            id
	 */
	public void deleteDepartment(Long id) {

		Department department = entityManager.find(Department.class, id);
		entityManager.remove(department);

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("deleteDepartment():");
		}
	}

	/**
	 * Deletes an <code>Employee</code> from the data store.
	 * 
	 * @param id
	 *            id
	 */
	public void deleteEmployee(Long id) {

		Employee employee = entityManager.find(Employee.class, id);
		Department department = employee.getDepartment();
		department.removeEmployee(employee);
		entityManager.merge(department);
		entityManager.remove(employee);

		if (logger.isLoggable(Level.CONFIG)) {
			logger.config("deleteEmployee():");
		}
	}
}
