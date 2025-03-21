package contactSite;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionSynchronization;

@Component
@Aspect
public class TimeTraceAop {

    private static final Logger logger = LoggerFactory.getLogger(TimeTraceAop.class);

    @Around("execution(* contactSite..*Service.*(..))")
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        Object result = joinPoint.proceed();

        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    long endTime = System.currentTimeMillis();
                    long executionTime = endTime - startTime;

                    logger.info("\n====================================\n" +
                                    "✅ [{}] 실행 완료 (트랜잭션 포함): {} ms\n" +
                                    "====================================",
                            joinPoint.getSignature(), executionTime);
                }
            });
        } else {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            logger.info("\n====================================\n" +
                            "✅ [{}] 실행 완료: {} ms\n" +
                            "====================================",
                    joinPoint.getSignature(), executionTime);
        }

        return result;
    }
}
