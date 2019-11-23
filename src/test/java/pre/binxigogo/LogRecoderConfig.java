package pre.binxigogo;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import pre.binxigogo.log.Logger;
import pre.binxigogo.log.adapter.Slf4jAdapter;
import pre.binxigogo.log.annotation.EnableLogRecord;
import pre.binxigogo.log.format.DefaultLogMessageFormatter;
import pre.binxigogo.log.format.LogMessageFormatter;
import pre.binxigogo.module.user.User;
import pre.binxigogo.module.user.UserController;

@Configuration
// 增加统一日志记录启动注解
@EnableLogRecord
@Import(value = { UserController.class })
public class LogRecoderConfig {
	//配置logger适配器
	@Bean
	public Logger logger() {
		return new Slf4jAdapter(LoggerFactory.getLogger(LogRecoderConfig.class));
	}

	//配置LogMessageFormatter格式化器
	@Bean
	public LogMessageFormatter logMessageFormatter() {
		// 指定UserPicker，可以根据实际情况得到用户，例如：你可以通过自定义UserPicker实现类得到当前登录用户信息
		return new DefaultLogMessageFormatter(() -> "admin");
	}

	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				LogRecoderConfig.class);
		UserController userController = applicationContext.getBean(UserController.class);
		long start = System.currentTimeMillis();
		userController.add(new User("张三", 20), null);
		userController.get();
		userController.toAdd("abcdefg");
		userController.exception("987654321");
		System.out.println("用时：" + (System.currentTimeMillis() - start));
		applicationContext.close();
	}
}
