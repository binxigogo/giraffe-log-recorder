package pre.binxigogo.log.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

/**
 * 统一日志记录启用注解，处理标记@{@link Log}方法上的日志注解。@{@link Log} 方法所在类必须是一个Spring组件类，
 * 支持Spring采用各种方式的组件，包括不限于@{@link Component}、@{@link Repository}、@{@link Service}、@{@link Controller}、、@{@link Bean}等。
 * 示例如下：
 * 
 * <pre>
 * &#064;Configuration
 * // 增加统一日志记录启动注解
 * &#064;EnableLogRecord
 * &#064;Import(value = { UserController.class })
 * public class LogRecoderConfig {
 * 	// 配置logger适配器
 * 	&#064;Bean
 * 	public Logger logger() {
 * 		return new Slf4jAdapter(LoggerFactory.getLogger(LogRecoderConfig.class));
 * 	}
 * 
 * 	// 配置LogMessageFormatter格式化器
 * 	&#064;Bean
 * 	public LogMessageFormatter logMessageFormatter() {
 * 		// 指定UserPicker，可以根据实际情况得到用户，例如：你可以通过自定义UserPicker实现类得到当前登录用户信息
 * 		return new DefaultLogMessageFormatter(() -> "admin");
 * 	}
 * 
 * 	public static void main(String[] args) {
 * 		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
 * 				LogRecoderConfig.class);
 * 		UserController userController = applicationContext.getBean(UserController.class);
 * 		userController.add(new User("张三", 20));
 * 		applicationContext.close();
 * 	}
 * }
 * </pre>
 * 
 * 统一日志记录增加方式如下：
 * 
 * <pre>
 * public class UserController {
 * 	&#064;Log(code = "10001")
 * 	public boolean add(User user) {
 * 		System.out.println("增加用户");
 * 		return true;
 * 	}
 * }
 * </pre>
 * 
 * @author wangguobin
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@EnableAspectJAutoProxy
@Import(value = LogRecordRegistrar.class)
public @interface EnableLogRecord {
}
