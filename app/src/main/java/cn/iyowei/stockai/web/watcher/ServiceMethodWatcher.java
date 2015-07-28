package cn.iyowei.stockai.web.watcher;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 每个service方法执行时间监控类
 *
 * @author Vick.liu (vicklin123@gmail.com)
 */
public class ServiceMethodWatcher {

    protected static Logger logger = LoggerFactory.getLogger(ServiceMethodWatcher.class);

    /**
     * 监控service的方法的执行时间
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        String className = pjp.getTarget().getClass().getName();
        String methodName = pjp.getSignature().getName();
        String targetPoint = "===>[" + className + "#" + methodName + "]";
        long startTime = System.currentTimeMillis();
        Object result = pjp.proceed(pjp.getArgs());
        long endTime = System.currentTimeMillis();
        long gap = endTime - startTime;
        System.out.println(targetPoint + " runs:" + gap + "ms");
        long threshold = 500;
        if (gap > threshold) {
            logger.info(targetPoint + " runs:" + gap + "ms，超过阀值" + threshold + "ms");
            System.err.println(targetPoint + " runs:" + gap + "ms，超过阀值" + threshold + "ms");
        }
        return result;
    }
}
