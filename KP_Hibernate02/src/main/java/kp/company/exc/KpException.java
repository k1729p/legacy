package kp.company.exc;

/**
 * Class KpException.
 */
public class KpException extends Exception {
	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 */
	public KpException() {

		super();
	}

	/**
	 * Constructor with message parameter.
	 * 
	 * @param message the message
	 */
	public KpException(String message) {

		super(message);
	}

}
