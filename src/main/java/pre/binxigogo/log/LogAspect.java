package pre.binxigogo.log;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import pre.binxigogo.log.annotation.Log;
import pre.binxigogo.log.annotation.LogPoint;
import pre.binxigogo.log.annotation.LogPointPosition;
import pre.binxigogo.log.format.LogMessageFormatter;

@Aspect
public class LogAspect {
	private Logger logger;
	private LogMessageFormatter logMessageFormatter;

	public LogAspect(Logger logger, LogMessageFormatter logMessageFormatter) {
		this.logger = logger;
		this.logMessageFormatter = logMessageFormatter;
	}

	@Pointcut(value = "@annotation(pre.binxigogo.log.annotation.Log)")
	public void pointCut() {
	}

	@Around("pointCut()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = signature.getMethod().getName();
		Log log = getLog(signature.getMethod());
		Object[] args = joinPoint.getArgs();
		String[] parameterNames = signature.getParameterNames();
		String className = joinPoint.getTarget().getClass().getName();
		beforeLog(log, className, methodName, parameterNames, args);
		Object result = null;
		try {
			result = joinPoint.proceed();
			afterLog(log, className, methodName, parameterNames, args, result);
		} catch (Throwable t) {
			exceptionLog(log, className, methodName, parameterNames, args, t);
			throw t;
		}
		return result;
	}

	private void beforeLog(Log log, String className, String methodName, String[] parameterNames, Object[] args) {
		log(LogPointPosition.BEFORE, log, className, methodName, parameterNames, args, null, null);
	}

	private void afterLog(Log log, String className, String methodName, String[] parameterNames, Object[] args,
			Object result) {
		log(LogPointPosition.AFTER, log, className, methodName, parameterNames, args, result, null);
	}

	private void exceptionLog(Log log, String className, String methodName, String[] parameterNames, Object[] args,
			Throwable t) {
		log(LogPointPosition.EXCEPTION, log, className, methodName, parameterNames, args, null, t);
	}

	private void log(LogPointPosition logPointPosition, Log log, String className, String methodName,
			String[] parameterNames, Object[] args, Object result, Throwable t) {
		LogPoint logPoint = containPoint(log.points(), logPointPosition);
		if (logPoint != null) {
			format(log, logPoint, className, methodName, parameterNames, args, result, t);
		}
	}

	private void format(Log log, LogPoint logPoint, String className, String methodName, String[] parameterNames, Object[] args,
			Object result, Throwable t) {
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
		Annotation[] declaredAnnotations = method.getDeclaredAnnotations();
		System.out.println("get annotation");
		for (Annotation annotation : declaredAnnotations) {
			System.out.println(annotation.getClass().getName());
		}
		return method.getAnnotation(Log.class);
	}
}
