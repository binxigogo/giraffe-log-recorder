package pre.binxigogo.log.format;

import java.text.MessageFormat;
import java.util.Optional;

import pre.binxigogo.log.UserPicker;
import pre.binxigogo.log.annotation.Log;

public class DefaultLogMessageFormatter extends AbstractLogMessageFormatter {
	public DefaultLogMessageFormatter(UserPicker userPicker) {
		super(userPicker);
	}

	public String format(Log log, String className, String methodName, String[] parameterNames, Object[] args,
			Object result) {
		Optional<Object> resultOpt = Optional.ofNullable(result);
		return MessageFormat.format("{0} {1} [{2}] [{3}]", log.code(), getUserPicker().getUser(),
				wrapParameter(parameterNames, args), resultOpt.orElse(""));
	}

	private String wrapParameter(String[] parameterNames, Object[] args) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < parameterNames.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(parameterNames[i]).append("=").append(args[i] == null ? "" : args[i]);
		}
		return sb.toString();
	}
}
