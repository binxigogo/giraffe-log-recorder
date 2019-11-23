package pre.binxigogo.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 忽略方法参数注解
 * </p>
 * 在@{@link Log}所在方法参数中增加，增加后，将不在日志记录中记录该参数
 * 
 * @author wangguobin
 *
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreParam {

}
