package kp.company.domain;

/**
 * Simple JavaBean domain object adds a name property to <code>BaseEntity</code>
 * . Used as a base class for objects needing these properties.
 */
public class NamedEntity extends BaseEntity {

	private String name;

	/**
	 * Constructor.
	 */
	public NamedEntity() {
	}

	/**
	 * Sets name.
	 * 
	 * @param name the name
	 */
	public void setName(String name) {

		this.name = name;
	}

	/**
	 * Gets name.
	 * 
	 * @return the name
	 */
	public String getName() {

		return this.name;
	}
}
