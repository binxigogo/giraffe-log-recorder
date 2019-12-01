package pre.binxigogo.log.adapter;

public interface LogAdapterClassAware {
	void setLogAdapter(Class<? extends AbstractLogAdapter> logAdapterClass);
}