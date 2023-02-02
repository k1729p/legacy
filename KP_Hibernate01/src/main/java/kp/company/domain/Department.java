package kp.company.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Simple JavaBean business object representing a department.
 */
public class Department extends NamedEntity {

	private Set<Employee> employees = new HashSet<Employee>();

	/**
	 * Constructor.
	 */
	public Department() {
	}

	/**
	 * Sets employees.
	 * 
	 * @param employees employees
	 */
	public void setEmployees(Set<Employee> employees) {

		this.employees = employees;
	}

	/**
	 * Gets employees.
	 * 
	 * @return list of employees
	 */
	public Set<Employee> getEmployees() {

		return employees;
	}

	/**
	 * Adds employee.
	 * 
	 * @param employee employee
	 */
	public void addEmployee(Employee employee) {

		employees.add(employee);
		employee.setDepartment(this);
	}

	/**
	 * Removes employee.
	 * 
	 * @param employee employee
	 */
	public void removeEmployee(Employee employee) {

		employees.remove(employee);
		employee.setDepartment(null);
	}

	/**
	 * Removes employee.
	 * 
	 * @param employeeId employee id
	 * @return the removed employee
	 */
	public Employee removeEmployee(Long employeeId) {

		Employee removedEmployee = null;
		for (Employee employee : employees) {
			if (employee.getId().longValue() == employeeId.longValue()) {
				removedEmployee = employee;
				break;
			}
		}
		if (removedEmployee != null) {
			removeEmployee(removedEmployee);
		}
		return removedEmployee;
	}

}