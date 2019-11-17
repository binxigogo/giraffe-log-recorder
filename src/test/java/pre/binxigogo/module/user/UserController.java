package pre.binxigogo.module.user;

import pre.binxigogo.log.annotation.Log;

public class UserController {
	@Log(code = "10001")
	public boolean add(User user) {
		System.out.println("增加用户");
		return true;
	}
}
