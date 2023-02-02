package kp.company.domain;

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
public class Employee {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@ManyToOne
	@JoinColumn(name = "department_id")
	private Department department;

	@ManyToOne
	@JoinColumn(name = "title_id")
	private Title title;

	/**
	 * Constructor.
	 */
	public Employee() {
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
	 * Sets first name.
	 * 
	 * @param firstName
	 *            first name
	 */
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	/**
	 * Gets first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {

		return this.firstName;
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
	 * Gets last name.
	 * 
	 * @return last name
	 */
	public String getLastName() {

		return this.lastName;
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
	 * Gets department.
	 * 
	 * @return department
	 */
	public Department getDepartment() {

		return this.department;
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
	 * Gets title.
	 * 
	 * @return title title
	 */
	public Title getTitle() {

		return this.title;
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
