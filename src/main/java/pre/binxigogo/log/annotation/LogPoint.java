package pre.binxigogo.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志插入点，支持<code>LogPointPosition.BEFORE</code>, <code>LogPointPosition.AFTER</code>, <code>LogPointPosition.EXCEPTION</code>。
 * <table border="1">
 * 	<tr><th>插入点位置</th><th>说明</th></tr>
 *  <tr><td>LogPointPosition.BEFORE</td><td>在执行方法之前</td></tr>
 *  <tr><td>LogPointPosition.AFTER</td><td>在执行方法之后，如果方法跑出异常，则不记录该日志</td></tr>
 *  <tr><td>LogPointPosition.EXCEPTION</td><td>抛出异常时记录</td></tr>
 * <table>
 * 日志级别包括：TRACE, DEBUG, INFO, WARN, ERROR
 * @author wangguobin
 *
 */
@Documented
@Target(ElementType.LOCAL_VARIABLE)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPoint {
	LogPointPosition position();

	Level level() default Level.INFO;
}
