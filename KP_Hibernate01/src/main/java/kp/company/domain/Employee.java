package kp.company.domain;

/**
 * Simple JavaBean domain object representing an employee.
 */
public class Employee extends Person {

	private Department department;
	private Title title;

	/**
	 * Constructor.
	 */
	public Employee() {
	}

	/**
	 * Sets department.
	 * 
	 * @param department the department
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

		return this.department;
	}

	/**
	 * Sets title.
	 * 
	 * @param title the title
	 */
	public void setTitle(Title title) {

		this.title = title;
	}

	/**
	 * Gets title.
	 * 
	 * @return the title
	 */
	public Title getTitle() {

		return this.title;
	}

}
