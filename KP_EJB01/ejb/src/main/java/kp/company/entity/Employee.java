package kp.company.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Simple JavaBean domain object representing an employee. The 'equals()' and
 * 'hashCode()' methods are overridden because instances of subclasses are in
 * Sets.
 */
@Entity
@Table(name = "EMPLOYEES")
public class Employee implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String firstName;
	private String lastName;
	private Department department;
	private Title title;

	/**
	 * Constructor.
	 */
	public Employee() {
	}

	/**
	 * Gets id.
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
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
	 * Gets first name.
	 * 
	 * @return first name
	 */
	@Column(name = "first_name")
	public String getFirstName() {

		return this.firstName;
	}

	/**
	 * Sets first name.
	 * 
	 * @param firstName
	 *            first name
	 */
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	/**
	 * Gets last name.
	 * 
	 * @return last name
	 */
	@Column(name = "last_name")
	public String getLastName() {

		return this.lastName;
	}

	/**
	 * Sets last name.
	 * 
	 * @param lastName
	 *            last name
	 */
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}

	/**
	 * Gets department.
	 * 
	 * @return department
	 */
	@ManyToOne
	@JoinColumn(name = "department_id")
	public Department getDepartment() {

		return this.department;
	}

	/**
	 * Sets department.
	 * 
	 * @param department
	 *            department
	 */
	public void setDepartment(Department department) {

		this.department = department;
	}

	/**
	 * Gets title.
	 * 
	 * @return title title
	 */
	@ManyToOne
	@JoinColumn(name = "title_id")
	public Title getTitle() {

		return this.title;
	}

	/**
	 * Sets title.
	 * 
	 * @param title
	 *            title
	 */
	public void setTitle(Title title) {

		this.title = title;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param other
	 *            other object
	 * @return result the result
	 */
	public boolean equals(Object other) {

		if (this == other) {
			return true;
		}
		if (!(other instanceof Employee)) {
			return false;
		}
		final Employee employee = (Employee) other;
		if (employee.getId() == null || !employee.getId().equals(this.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return hashCode
	 */
	public int hashCode() {

		int result = 0;
		if (this.getId() != null) {
			result = 29 * this.getId().intValue();
		}
		return result;
	}

}
