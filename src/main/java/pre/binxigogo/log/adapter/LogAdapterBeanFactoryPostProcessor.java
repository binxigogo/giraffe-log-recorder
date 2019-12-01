package pre.binxigogo.log.adapter;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.AnnotationUtils;

import pre.binxigogo.log.annotation.EnableLogRecord;

/**
 * 给LogAdaptManager装配日志适配器类
 * 
 * @author wangguobin
 *
 */
public class LogAdapterBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		String[] enableLogRecordBeanNames = beanFactory.getBeanNamesForAnnotation(EnableLogRecord.class);
		Set<Class<? extends AbstractLogAdapter>> logAdapterClassSet = new HashSet<>();
		// 查找EnableLogRecord注解类的AbstractLogAdapter
		for (String enableLogRecordBeanName : enableLogRecordBeanNames) {
			EnableLogRecord enableLogRecord = AnnotationUtils
					.findAnnotation(beanFactory.getBean(enableLogRecordBeanName).getClass(), EnableLogRecord.class);
			logAdapterClassSet.add(enableLogRecord.logAdapter());
		}
		if (logAdapterClassSet.size() != 1) {
			throw new BeansException("日志适配器类只能有一个，实际：" + logAdapterClassSet.size()) {
				private static final long serialVersionUID = 1L;
			};
		}
		// 给日志适配管理器设置日志适配器类
		Class<? extends AbstractLogAdapter> logAdapterClass = logAdapterClassSet.iterator().next();
		beanFactory.getBeansOfType(LogAdapterClassAware.class).values()
				.forEach(aware -> aware.setLogAdapter(logAdapterClass));
	}
}
