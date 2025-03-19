package contactSite;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TimeTraceAop {

    private static final Logger logger = LoggerFactory.getLogger(TimeTraceAop.class);

    @Around("execution(* contactSite..*Service.*(..))")  // 모든 Service 메서드 실행 시간 측정
    public Object trackTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        try {
            return joinPoint.proceed();
        } finally {
            long endTime = System.currentTimeMillis();
            long executionTime = endTime - startTime;

            logger.info("\n====================================\n" +
                            "✅ [{}] 실행 완료: {} ms\n" +
                            "====================================",
                    joinPoint.getSignature(), executionTime);
        }
    }
}
