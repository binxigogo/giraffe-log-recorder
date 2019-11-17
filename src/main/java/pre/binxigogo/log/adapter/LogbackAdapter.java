package pre.binxigogo.log.adapter;

import pre.binxigogo.log.Logger;

/**
 * Logback日志适配器，日志文件配置方式与直接使用Logback一致
 * 
 * @author wangguobin
 *
 */
public class LogbackAdapter implements Logger {
	private ch.qos.logback.classic.Logger logger;

	public LogbackAdapter(ch.qos.logback.classic.Logger logger) {
		this.logger = logger;
	}

	public void trace(String msg) {
		logger.trace(msg);
	}

	public void trace(String msg, Throwable t) {
		logger.trace(msg, t);
	}

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void debug(String msg, Throwable t) {
		logger.debug(msg, t);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void info(String msg, Throwable t) {
		logger.info(msg, t);
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String msg, Throwable t) {
		logger.warn(msg, t);
	}

	public void error(String msg) {
		logger.error(msg);
	}

	public void error(String msg, Throwable t) {
		logger.error(msg, t);
	}
}
