package pre.binxigogo.log.adapter;

import pre.binxigogo.log.Logger;

/**
 * 日志适配管理器
 * 
 * @author wangguobin
 *
 */
public class LogAdaptManager implements LogAdapterClassAware {
	private Class<? extends AbstractLogAdapter> logAdapterClass;

	@Override
	public void setLogAdapter(Class<? extends AbstractLogAdapter> logAdapterClass) {
		this.logAdapterClass = logAdapterClass;
	}

	public Class<? extends AbstractLogAdapter> getLogAdapterClass() {
		return logAdapterClass;
	}

	/**
	 * 得到
	 * @param className
	 * @return
	 */
	public Logger getLogger(String className) {
		try {
			return logAdapterClass.getConstructor(String.class).newInstance(className);
		} catch (Exception e) {
			// 不对异常处理
		}
		return null;
	}
}
