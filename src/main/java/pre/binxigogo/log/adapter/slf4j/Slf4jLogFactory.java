package pre.binxigogo.log.adapter.slf4j;

import org.slf4j.LoggerFactory;

import pre.binxigogo.log.LogFactory;
import pre.binxigogo.log.Logger;

public class Slf4jLogFactory implements LogFactory {

	@Override
	public Logger getLogger(String className) {
		return new Slf4jLogger(LoggerFactory.getLogger(className));
	}

	@Override
	public Logger getLogger(Class<?> clazz) {
		return new Slf4jLogger(LoggerFactory.getLogger(clazz));
	}
	
}
