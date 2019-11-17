package pre.binxigogo.log.format;

import pre.binxigogo.log.annotation.Log;

/**
 * 日志格式化器，内置实现 {@link DefaultLogMessageFormatter}，可以根据实际情况实现自己的日志消息格式化器。
 * <p>
 * 注意事项：仅能使用一个日志格式化器，如果存在多个时，会查找是否包含有<code>logMessageFormatter</code>名称的<code>bean</code>，如果不存在相同名称<code>bean</code>时，会抛出 No qualifying bean of type 'pre.binxigogo.log.format.LogMessageFormatter' available: expected single matching bean but found x: ...
 * </p>
 * 示例（实现不限于）如下：
 * <pre>
 * 	&#064;Bean
 * 	public LogMessageFormatter logMessageFormatter() {
 * 		// 指定UserPicker，可以根据实际情况得到用户，例如：你可以通过自定义UserPicker实现类得到当前登录用户信息
 * 		return new DefaultLogMessageFormatter(() -> "admin");
 * 	}
 * </pre>
 * @author wangguobin
 *
 */
public interface LogMessageFormatter {
	String format(Log log, String className, String methodName, String[] parameterNames, Object[] args, Object result);
}
