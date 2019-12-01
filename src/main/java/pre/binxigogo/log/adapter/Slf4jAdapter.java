package pre.binxigogo.log.adapter;

/**
 * Slf4j日志适配器，日志文件配置方式与直接使用Slf4j一致
 * 
 * @author wangguobin
 *
 */
public class Slf4jAdapter extends AbstractLogAdapter {
	private org.slf4j.Logger logger;

	public Slf4jAdapter(String className) {
		super(className);
		this.logger = org.slf4j.LoggerFactory.getLogger(className);
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
