package kp.company.domain;

/**
 * Simple JavaBean domain object with an id property. Used as a base class for
 * objects needing this property. The 'equals()' and 'hashCode()' methods are
 * overridden because instances of subclasses are in Sets.
 */
public class BaseEntity {

	private Long id;

	/**
	 * Constructor.
	 */
	public BaseEntity() {
	}

	/**
	 * Sets id.
	 * 
	 * @param id the id
	 */
	protected void setId(Long id) {

		this.id = id;
	}

	/**
	 * Gets id.
	 * 
	 * @return the id
	 */
	public Long getId() {

		return id;
	}

	/**
	 * Indicates whether some other object is "equal to" this one.
	 * 
	 * @param other the other
	 * @return the result
	 */
	public boolean equals(Object other) {

		if (this == other) {
			return true;
		}
		if (!(other instanceof BaseEntity)) {
			return false;
		}
		final BaseEntity baseEntity = (BaseEntity) other;
		if (baseEntity.getId() == null || !baseEntity.getId().equals(this.getId())) {
			return false;
		}
		return true;
	}

	/**
	 * Returns a hash code value for the object.
	 * 
	 * @return the hash code
	 */
	public int hashCode() {

		int result = 0;
		if (this.getId() != null) {
			result = 29 * this.getId().intValue();
		}
		return result;
	}

}
