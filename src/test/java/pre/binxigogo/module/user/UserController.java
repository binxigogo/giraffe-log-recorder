package pre.binxigogo.module.user;

import org.springframework.ui.Model;

import pre.binxigogo.log.annotation.IgnoreParam;
import pre.binxigogo.log.annotation.Level;
import pre.binxigogo.log.annotation.Log;
import pre.binxigogo.log.annotation.LogPoint;
import pre.binxigogo.log.annotation.LogPointPosition;

public class UserController {
	@Log(code = "10001")
	public boolean add(User user, @IgnoreParam Model model) {
		System.out.println("增加用户");
		return true;
	}

	@Log(code = "10002", points = { @LogPoint(level = Level.INFO, position = LogPointPosition.AFTER) })
	public String get() {
		System.out.println("-----get-----");
		return "hello world!";
	}

	@Log(code = "10003", points = { @LogPoint(level = Level.INFO, position = LogPointPosition.BEFORE) })
	public void toAdd(String id) {
		System.out.println("----toAdd----");
	}

	@Log(code = "10004", points = { @LogPoint(level = Level.INFO, position = LogPointPosition.BEFORE),
			@LogPoint(level = Level.WARN, position = LogPointPosition.EXCEPTION) })
	public void exception(String id) {
		throw new RuntimeException("抛出异常了");
	}
}
