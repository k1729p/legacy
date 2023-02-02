package kp.company.util;

import java.io.IOException;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

/**
 * Class LoggingConfiguration.
 */
public class LoggingConfiguration {

	private static final Logger logger = Logger
			.getLogger(LoggingConfiguration.class.getName());
	private static LogManager logManager = null;

	private static String LOGGING_PROPS_FILE = "/logging.properties";

	/**
	 * Sets logging.
	 * 
	 */
	public static void setLogging() {

		if (logManager != null) {
			// logging is ready
			return;
		}
		logManager = LogManager.getLogManager();
		try {
			logManager.readConfiguration(LoggingConfiguration.class
					.getResource(LOGGING_PROPS_FILE).openStream());
		} catch (IOException ex) {
			System.out.println("Exception[" + ex.getMessage() + "]");
			System.exit(1);
		}
		Logger parentLogger = logger.getParent();
		Handler[] handlers = parentLogger.getHandlers();
		while (handlers.length == 0) {
			parentLogger = parentLogger.getParent();
			if (parentLogger == null) {
				System.out.println("Logging problem: handlers not found!");
				return;
			}
			handlers = parentLogger.getHandlers();
		}
		for (Handler handler : handlers) {
			handler.setFormatter(new LoggingFormatter());
		}
		/*
		 * Check logging.
		 */
		if (!"".equals("")) {
			logger.finest("setLogging(): logging with level finest");
			logger.fine("setLogging(): logging with level fine");
			logger.config("setLogging(): logging with level config");
			logger.info("setLogging(): logging with level info");
			logger.warning("setLogging(): logging with level warning");
			logger.severe("setLogging(): logging with level severe");
		}
	}
}
