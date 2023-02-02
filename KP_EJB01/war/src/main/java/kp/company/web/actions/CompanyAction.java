package kp.company.web.actions;

import java.util.logging.Logger;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Class CompanyAction
 *
 */
public class CompanyAction extends ActionSupport {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(CompanyAction.class.getName());

	/**
	 * Executes action.
	 * 
	 * @return result the result
	 */
	public String execute() throws Exception {

		logger.info("execute():");
		return SUCCESS;
	}
}
