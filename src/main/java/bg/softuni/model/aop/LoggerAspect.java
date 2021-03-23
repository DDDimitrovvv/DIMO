package bg.softuni.model.aop;

import bg.softuni.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {

    private final LogService logService;

    public LoggerAspect(LogService logService) {
        this.logService = logService;
    }

    @Pointcut("execution(* bg.softuni.web.ProductController.details(..))")
    public void productDetailsPointcut(){}

    @After("productDetailsPointcut()")
    public void afterAdvice(JoinPoint joinPoint) throws Exception {
        Object[] args = joinPoint.getArgs();
        Long productId = (Long) args[1];
        String actionPage = joinPoint.getSignature().getName();

        logService.createLog(actionPage, productId);
    }

}
