package kp.company.web;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kp.company.request.BusinessLogic;

/**
 * Class ContextListener.
 */
public class ContextListener implements ServletContextListener {

	private static final Logger logger = Logger.getLogger(ContextListener.class
			.getName());

	@EJB
	private BusinessLogic businessLogic;

	/**
	 * Constructor.
	 */
	public ContextListener() {

		logger.info("ContextListener():");
	}

	/**
	 * Context is initialized.
	 * 
	 * @param event
	 *            the event
	 */
	public void contextInitialized(ServletContextEvent event) {

		ServletContext servletContext = event.getServletContext();
		servletContext.setAttribute("businessLogic", businessLogic);
		logger.info("contextInitialized():");
	}

	/**
	 * Context is destroyed.
	 * 
	 * @param event
	 *            the event
	 */
	public void contextDestroyed(ServletContextEvent event) {

		ServletContext servletContext = event.getServletContext();
		servletContext.removeAttribute("businessLogic");
		logger.info("contextDestroyed():");
	}

}
