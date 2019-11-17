package pre.binxigogo.log;

/**
 * 用户拾取器，用于日志记录时得到操作用户信息
 * 
 * @author wangguobin
 *
 */
@FunctionalInterface
public interface UserPicker {
	String getUser();
}