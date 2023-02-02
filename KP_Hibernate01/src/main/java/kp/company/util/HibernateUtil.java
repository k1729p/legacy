package kp.company.util;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Class HibernateUtil.
 */
public class HibernateUtil {

	private static final Logger logger = Logger.getLogger(HibernateUtil.class.getName());

	private static Configuration configuration;
	private static final SessionFactory sessionFactory = buildSessionFactory();

	/**
	 * Create the SessionFactory from hibernate.cfg.xml.
	 * 
	 * @return the session factory
	 */
	private static SessionFactory buildSessionFactory() {

		SessionFactory sessionFactory = null;
		try {
			configuration = new Configuration();
			configuration.configure();
			sessionFactory = configuration.buildSessionFactory();
		} catch (Throwable ex) {
			logger.severe("buildSessionFactory(): exception[" + ex.getMessage() + "]");
			throw new ExceptionInInitializerError(ex);
		}
		if (logger.isLoggable(Level.INFO)) {
			logger.info("buildSessionFactory():");
		}
		return sessionFactory;
	}

	/**
	 * Gets Configuration.
	 * 
	 * @return the configuration
	 */
	public static Configuration getConfiguration() {

		return configuration;
	}

	/**
	 * Opens session.
	 * 
	 * @return the session
	 */
	public static Session openSession() {

		Session session = sessionFactory.openSession();
		if (logger.isLoggable(Level.FINE)) {
			logger.fine("openSession():");
		}
		return session;
	}
}