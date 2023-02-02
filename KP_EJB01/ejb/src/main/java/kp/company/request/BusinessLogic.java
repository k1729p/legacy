package kp.company.request;

import java.util.List;

import javax.ejb.Remote;

import kp.company.entity.Department;
import kp.company.entity.Employee;
import kp.company.entity.Title;

/**
 * Interface BusinessLogic. This session bean provide a client's view of the
 * application's business logic.
 * 
 */
@Remote
public interface BusinessLogic {
	/**
	 * Retrieves all <code>Department</code>s from the data store.
	 * 
	 * @return the list of departments
	 */
	List<Department> loadDepartments();

	/**
	 * Retrieves <code>Department</code> from the data store.
	 * 
	 * @param departmentId
	 *            departmentId
	 * @return department
	 */
	Department loadDepartment(Long departmentId);

	/**
	 * Retrieves all <code>Employee</code>s from the data store.
	 * 
	 * @param departmentId
	 *            departmentId
	 * @return the list of employees
	 */
	List<Employee> loadEmployees(Long departmentId);

	/**
	 * Retrieves <code>Employee</code> from the data store.
	 * 
	 * @param departmentId
	 *            departmentId
	 * @param employeeId
	 *            employeeId
	 * @return employee
	 */
	Employee loadEmployee(Long departmentId, Long employeeId);

	/**
	 * Retrieves all <code>Title</code>s from the data store.
	 * 
	 * @return the list of titles
	 */
	List<Title> loadTitles();

	/**
	 * Retrieves <code>Title</code> from the data store.
	 * 
	 * @param titleId
	 *            titleId
	 * @return title
	 */
	Title loadTitle(Long titleId);

	/**
	 * Stores a <code>Department</code> to the data store.
	 * 
	 * @param department
	 *            department
	 * @return merged department
	 */
	Department storeDepartment(Department department);

	/**
	 * Stores an <code>Employee</code> to the data store.
	 * 
	 * @param employee
	 *            employee
	 * @return merged employee
	 */
	Employee storeEmployee(Employee employee);

	/**
	 * Stores an <code>Employee</code> to the data store.
	 * 
	 * @param department
	 *            department
	 * @param employee
	 *            employee
	 * @return merged employee
	 */
	Employee storeEmployee(Department department, Employee employee);

	/**
	 * Deletes a <code>Department</code> from the data store.
	 * 
	 * @param id
	 *            id
	 */
	void deleteDepartment(Long id);

	/**
	 * Deletes an <code>Employee</code> from the data store.
	 * 
	 * @param id
	 *            id
	 */
	void deleteEmployee(Long id);
}