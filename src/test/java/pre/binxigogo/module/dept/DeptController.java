package pre.binxigogo.module.dept;

import pre.binxigogo.log.annotation.Log;

public class DeptController {
	@Log(code = "10011")
	public String add(String deptName, String phone) {
		return "添加成功";
	}
}
