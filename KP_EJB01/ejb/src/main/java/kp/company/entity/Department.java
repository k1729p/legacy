package kp.company.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Simple JavaBean business object representing a department.
 */
@Entity
@Table(name = "DEPARTMENTS")
public class Department implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Set<Employee> employees = new HashSet<Employee>();

	/**
	 * Constructor.
	 */
	public Department() {
	}

	/**
	 * Gets id.
	 * 
	 * @return id
	 */
	@Id @GeneratedValue
	@Column(name = "id")
	public Long getId() {

		return id;
	}

	/**
	 * Sets id.
	 * 
	 * @param id
	 *            id
	 */
	public void setId(Long id) {

		this.id = id;
	}

	/**
	 * Gets name.
	 * 
	 * @return name
	 */
	@Column(name = "name")
	public String getName() {

		return this.name;
	}

	/**
	 * Sets name.
	 * 
	 * @param name
	 *            name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Gets employees.
	 * 
	 * @return list of employees
	 */
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "department")
	public Set<Employee> getEmployees() {

		return employees;
	}

	/**
	 * Sets employees.
	 * 
	 * @param employees
	 *            employees
	 */
	public void setEmployees(Set<Employee> employees) {

		this.employees = employees;
	}

	/**
	 * Adds employee.
	 * 
	 * @param employee
	 *            employee
	 */
	public void addEmployee(Employee employee) {

		employees.add(employee);
	}

	/**
	 * Removes employee.
	 * 
	 * @param employee
	 *            employee
	 */
	public void removeEmployee(Employee employee) {

		employees.remove(employee);
	}

}