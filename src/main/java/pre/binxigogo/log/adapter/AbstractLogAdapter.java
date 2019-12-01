package pre.binxigogo.log.adapter;

import pre.binxigogo.log.Logger;

/**
 * 抽象的日志适配器，所有具体日志适配器都要继续该适配器
 * 
 * @author wangguobin
 *
 */
public abstract class AbstractLogAdapter implements Logger {
	private String className;

	public AbstractLogAdapter(String className) {
		this.className = className;
	}

	public String getClassName() {
		return className;
	}
}