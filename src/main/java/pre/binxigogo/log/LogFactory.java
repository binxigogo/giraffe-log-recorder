package pre.binxigogo.log;

public interface LogFactory {
	public Logger getLogger(String className);

	public Logger getLogger(Class<?> clazz);
}
