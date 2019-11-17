package pre.binxigogo.log.format;

import pre.binxigogo.log.UserPicker;

public abstract class AbstractLogMessageFormatter implements LogMessageFormatter {
	private UserPicker userPicker;

	public AbstractLogMessageFormatter(UserPicker userPicker) {
		this.userPicker = userPicker;
	}

	public UserPicker getUserPicker() {
		return userPicker;
	}
}
