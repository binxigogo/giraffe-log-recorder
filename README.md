一款基于Spring和Aspects的日志管理工具，支持方法执行前、执行后和异常三种日志点统一格式日志输出，可以扩展适合自己项目的日志格式化方式和输出方式，编写本工具的目的是为了简化日志代码的编写量，提高对日志记录的可扩展行、可维护性。
使用方式

1、建立配置类

    @Configuration
    @EnableLogRecord
    public class LogRecoderConfig{
      //配置logger适配器
      @Bean
      public Logger logger() {
        return new Slf4jAdapter(LoggerFactory.getLogger(LogRecoderConfig.class));
      }
      //配置LogMessageFormatter格式化器
      @Bean
      public LogMessageFormatter logMessageFormatter() {
        // 指定UserPicker，可以根据实际情况得到用户，例如：你可以通过自定义UserPicker实现类得到当前登录用户信息
        return new DefaultLogMessageFormatter(() -> "admin");
      }
    }

在配置类中首先需要在类上增加@EnableLogRecord开启日志记录；
增加Logger适配器，开发人员可以跟据自身需要增加不同日志框架的适配器，增加方式如下：

    public class XXXAdapter implements Logger {
	    private xxx.zzz.Logger logger;

        public XXXAdapter(xxx.zzz.Logger logger) {
            this.logger = logger;
        }

        public void trace(String msg) {
            logger.trace(msg);
        }

        public void trace(String msg, Throwable t) {
            logger.trace(msg, t);
        }

        public void debug(String msg) {
            logger.debug(msg);
        }

        public void debug(String msg, Throwable t) {
            logger.debug(msg, t);
        }

        public void info(String msg) {
            logger.info(msg);
        }

        public void info(String msg, Throwable t) {
            logger.info(msg, t);
        }

        public void warn(String msg) {
            logger.warn(msg);
        }

        public void warn(String msg, Throwable t) {
            logger.warn(msg, t);
        }

        public void error(String msg) {
            logger.error(msg);
        }

        public void error(String msg, Throwable t) {
            logger.error(msg, t);
        }
    }

增加日志化格式器，giraffe-log-recoder实现了一个默认的格式化器DefaultLogMessageFormatter，开发人员可以定制自己的日志格式化器，自定义方式如下：
   
    public class MyLogMessageFormatter extends AbstractLogMessageFormatter {
        public DefaultLogMessageFormatter(UserPicker userPicker) {
            super(userPicker);
        }

        public String format(Log log, String className, String methodName, String[] parameterNames, Object[] args,
                Object result) {
            // 实现自己的日志格式
            return "格式化后日志字符串";
        }
    }
    
2、增加日志注解
在需要记录日志方法上增加@Log注解，需要注意的是需要记录日志的类必须是Spring容器管理的bean。
    
    public class UserController {
        @Log(code = "10001")
        public boolean add(User user, @IgnoreParam Model model) {
            System.out.println("增加用户");
            return true;
        }

        @Log(code = "10002", points = { @LogPoint(level = Level.INFO, position = LogPointPosition.AFTER) })
        public String get() {
            System.out.println("-----get-----");
            return "hello world!";
        }

        @Log(code = "10003", points = { @LogPoint(level = Level.INFO, position = LogPointPosition.BEFORE) })
        public void toAdd(String id) {
            System.out.println("----toAdd----");
        }

        @Log(code = "10004", points = { @LogPoint(level = Level.INFO, position = LogPointPosition.BEFORE),
                @LogPoint(level = Level.WARN, position = LogPointPosition.EXCEPTION) })
        public void exception(String id) {
            throw new RuntimeException("抛出异常了");
        }
    }

实现上述两步后，需要增加依赖日志框架的配置文件，例如：Log4j的log4j.properties
注解说明：
你可以去源码中的pre.binxigogo.log.annotation包查看每个注解的详细说明。
