package kp.company.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Class LoggingFormatter.
 */
public class LoggingFormatter extends Formatter {

	private static String EOL = System.getProperty("line.separator");
	private static final boolean SHOW_CLASS = true;
	private static final boolean SHOW_METHOD = false;

	/**
	 * Formats log message.
	 * 
	 * @param record
	 *            record
	 * @return formated message
	 */
	public String format(LogRecord record) {

		StringBuffer logMsgBuf = new StringBuffer();
		logMsgBuf.append(record.getLevel().toString().substring(0, 3));
		logMsgBuf.append(" ");
		logMsgBuf.append(record.getThreadID());
		logMsgBuf.append(" ");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM HH:mm:ss.SSS");
		logMsgBuf.append(sdf.format(new Date(record.getMillis())));
		logMsgBuf.append(" ");
		if (SHOW_CLASS) {
			String className = record.getSourceClassName();
			// className = className.substring(className.lastIndexOf('.') + 1);
			logMsgBuf.append(className);
			logMsgBuf.append(".");
			if (SHOW_METHOD) {
				logMsgBuf.append(record.getSourceMethodName());
				logMsgBuf.append("(): ");
			}
		}
		logMsgBuf.append(super.formatMessage(record)).append(EOL);

		Throwable thr1 = record.getThrown();
		if (thr1 != null) {
			// going to the bottom of the stack
			Throwable thr2 = thr1.getCause();
			while (thr2 != null) {
				thr1 = thr2;
				thr2 = thr2.getCause();
			}
			logMsgBuf.append("   ");
			logMsgBuf.append(thr1.toString()).append(EOL);
			StackTraceElement[] stTrElem = thr1.getStackTrace();
			for (int i = 0; i < stTrElem.length; i++) {
				logMsgBuf.append("      ");
				logMsgBuf.append(stTrElem[i].toString()).append(EOL);
			}
		}
		return logMsgBuf.toString();
	}
}
