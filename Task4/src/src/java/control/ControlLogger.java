package src.java.control;

import org.apache.log4j.Logger;

public class ControlLogger {
	private static Logger logger = Logger.getLogger(ControlLogger.class);
	
	public ControlLogger() {
	}
	
	public void makeLog(Object object) {
		logger.debug(object);
		logger.info(object);
		logger.warn(object);
		logger.error(object);
		logger.fatal(object);
	}
}
