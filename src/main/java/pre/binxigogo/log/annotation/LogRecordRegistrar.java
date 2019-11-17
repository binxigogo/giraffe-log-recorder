package pre.binxigogo.log.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import pre.binxigogo.log.LogAspect;

/**
 * 日志记录注册器
 * 
 * @author wangguobin
 *
 */
public class LogRecordRegistrar implements ImportBeanDefinitionRegistrar {

	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
		RootBeanDefinition logAspectBeanDef = new RootBeanDefinition(LogAspect.class);
		registry.registerBeanDefinition("logAspect", logAspectBeanDef);
	}

}
