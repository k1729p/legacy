package kp.company.domain;

/**
 * Simple JavaBean domain object representing an person.
 */
public class Person extends BaseEntity {

	private String firstName;
	private String lastName;

	/**
	 * Constructor.
	 */
	public Person() {
	}

	/**
	 * Gets first name.
	 * 
	 * @return the first name
	 */
	public String getFirstName() {

		return this.firstName;
	}

	/**
	 * Sets first name.
	 * 
	 * @param firstName the first name
	 */
	public void setFirstName(String firstName) {

		this.firstName = firstName;
	}

	/**
	 * Gets last name.
	 * 
	 * @return the last name
	 */
	public String getLastName() {

		return this.lastName;
	}

	/**
	 * Sets last name.
	 * 
	 * @param lastName the last name
	 */
	public void setLastName(String lastName) {

		this.lastName = lastName;
	}
}