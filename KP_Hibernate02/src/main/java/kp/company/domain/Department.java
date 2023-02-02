package kp.company.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/*
 * In a bidirectional relationship the owner is responsible for the association column(s) update.
 * To declare a side as not responsible for the relationship, the attribute "mappedBy" is used.
 * "mappedBy" refers to the property name of the association on the owner side.
 */
/**
 * Simple JavaBean business object representing a department.
 * 
 */
@Entity
@Table(name = "DEPARTMENTS")
public class Department {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Employee> employees = new HashSet<Employee>();

	/**
	 * Constructor.
	 */
	public Department() {
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
	 * Gets id.
	 * 
	 * @return id
	 */
	public Long getId() {

		return id;
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
	 * Gets name.
	 * 
	 * @return name
	 */
	public String getName() {

		return this.name;
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