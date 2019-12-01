package pre.binxigogo.log.format;

import pre.binxigogo.log.UserPicker;

/**
 * 抽象日志消息格式化器
 * 
 * @author wangguobin
 *
 */
public abstract class AbstractLogMessageFormatter implements LogMessageFormatter {
	private UserPicker userPicker;

	public AbstractLogMessageFormatter(UserPicker userPicker) {
		this.userPicker = userPicker;
	}

	public UserPicker getUserPicker() {
		return userPicker;
	}
}
