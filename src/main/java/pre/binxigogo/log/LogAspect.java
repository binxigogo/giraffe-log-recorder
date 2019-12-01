package pre.binxigogo.log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import pre.binxigogo.log.adapter.LogAdaptManager;
import pre.binxigogo.log.annotation.IgnoreParam;
import pre.binxigogo.log.annotation.Log;
import pre.binxigogo.log.annotation.LogPoint;
import pre.binxigogo.log.annotation.LogPointPosition;
import pre.binxigogo.log.format.LogMessageFormatter;

@Aspect
public class LogAspect {
	private static final Map<String, Logger> classLoggers = new ConcurrentHashMap<>();
	private LogAdaptManager logAdaptManager;
	private LogMessageFormatter logMessageFormatter;

	public LogAspect(LogAdaptManager logAdaptManager, LogMessageFormatter logMessageFormatter) {
		this.logMessageFormatter = logMessageFormatter;
		this.logAdaptManager = logAdaptManager;
	}

	@Pointcut(value = "@annotation(pre.binxigogo.log.annotation.Log)")
	public void pointCut() {
	}

	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		Log log = getLog(method);
		Object[] args = joinPoint.getArgs();
		String[] parameterNames = signature.getParameterNames();
		String className = joinPoint.getTarget().getClass().getName();
		Logger logger = getLogger(className);
		Set<Integer> ignoreMethodParameterPos = getIgnoreMethodParameterPos(method);
		String methodName = method.getName();
		// 方法执行之前记录日志
		beforeLog(log, logger, className, methodName, parameterNames, args, ignoreMethodParameterPos);
		Object result = null;
		try {
			result = joinPoint.proceed();
			// 方法执行之后记录日志
			afterLog(log, logger, className, methodName, parameterNames, args, result, ignoreMethodParameterPos);
		} catch (Throwable t) {
			// 方法抛出异常记录日志
			exceptionLog(log, logger, className, methodName, parameterNames, args, t, ignoreMethodParameterPos);
			throw t;
		}
		return result;
	}

	private Set<Integer> getIgnoreMethodParameterPos(Method method) {
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		Set<Integer> ignoreMethodParameterPos = new HashSet<>();
		for (int i = 0; i < parameterAnnotations.length; i++) {
			if (isContainAnnotation(parameterAnnotations[i], IgnoreParam.class)) {
				ignoreMethodParameterPos.add(i);
				break;
			}
		}
		return ignoreMethodParameterPos;
	}

	private Logger getLogger(String className) {
		Logger logger = classLoggers.get(className);
		if (logger == null) {
			logger = logAdaptManager.getLogger(className);
			classLoggers.put(className, logger);
		}
		return logger;
	}

	private boolean isContainAnnotation(Annotation[] annotations, Class<?> existAnnotation) {
		for (Annotation annotation : annotations) {
			if (annotation.annotationType().getName().equals(existAnnotation.getName())) {
				return true;
			}
		}
		return false;
	}

	private void beforeLog(Log log, Logger logger, String className, String methodName, String[] parameterNames,
			Object[] args, Set<Integer> ignoreMethodParameterPos) {
		log(LogPointPosition.BEFORE, log, logger, className, methodName, parameterNames, args, null, null,
				ignoreMethodParameterPos);
	}

	private void afterLog(Log log, Logger logger, String className, String methodName, String[] parameterNames,
			Object[] args, Object result, Set<Integer> ignoreMethodParameterPos) {
		log(LogPointPosition.AFTER, log, logger, className, methodName, parameterNames, args, result, null,
				ignoreMethodParameterPos);
	}

	private void exceptionLog(Log log, Logger logger, String className, String methodName, String[] parameterNames,
			Object[] args, Throwable t, Set<Integer> ignoreMethodParameterPos) {
		log(LogPointPosition.EXCEPTION, log, logger, className, methodName, parameterNames, args, null, t,
				ignoreMethodParameterPos);
	}

	private void log(LogPointPosition logPointPosition, Log log, Logger logger, String className, String methodName,
			String[] parameterNames, Object[] args, Object result, Throwable t, Set<Integer> ignoreMethodParameterPos) {
		LogPoint logPoint = containPoint(log.points(), logPointPosition);
		if (logPoint != null) {
			format(log, logger, logPoint, className, methodName, parameterNames, args, result, t,
					ignoreMethodParameterPos);
		}
	}

	private void format(Log log, Logger logger, LogPoint logPoint, String className, String methodName,
			String[] parameterNames, Object[] args, Object result, Throwable t, Set<Integer> ignoreMethodParameterPos) {
		// 如果有需要忽略的方法参数，重新组装参数名称和参数值数组
		if (!ignoreMethodParameterPos.isEmpty()) {
			int noneIgnoreParamNum = parameterNames.length - ignoreMethodParameterPos.size();
			if (noneIgnoreParamNum > 0) {
				List<String> parameterNameList = new ArrayList<>(noneIgnoreParamNum);
				List<Object> argsList = new ArrayList<Object>(noneIgnoreParamNum);
				for (int i = 0; i < parameterNames.length; i++) {
					if (!ignoreMethodParameterPos.contains(i)) {
						parameterNameList.add(parameterNames[i]);
						argsList.add(args[i]);
					}
				}
				parameterNames = parameterNameList.toArray(new String[noneIgnoreParamNum]);
				args = argsList.toArray(new Object[noneIgnoreParamNum]);
			} else {
				parameterNames = new String[] {};
				args = new Object[] {};
			}

		}
		String msg = logMessageFormatter.format(log, className, methodName, parameterNames, args, result);
		switch (logPoint.level()) {
		case INFO:
			logger.info(msg, t);
			break;
		case DEBUG:
			logger.debug(msg, t);
			break;
		case WARN:
			logger.warn(msg, t);
			break;
		case ERROR:
			logger.error(msg, t);
			break;
		case TRACE:
			logger.trace(msg, t);
			break;
		}
	}

	private LogPoint containPoint(LogPoint[] points, LogPointPosition pointPosition) {
		for (LogPoint point : points) {
			if (point.position() == pointPosition) {
				return point;
			}
		}
		return null;
	}

	private Log getLog(Method method) {
		return method.getAnnotation(Log.class);
	}
}
