package kp.company.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Simple JavaBean business object representing a job title. The 'equals()' and
 * 'hashCode()' methods are overridden because instances of subclasses are in
 * Sets.
 */
@Entity
@Table(name = "TITLES")
public class Title {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	/**
	 * Constructor.
	 */
	public Title() {
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
		if (!(other instanceof Title)) {
			return false;
		}
		final Title title = (Title) other;
		if (title.getId() == null || !title.getId().equals(this.getId())) {
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
