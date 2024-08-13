package jp.co.mgsystems.yuricollection.gootscatalog.commons;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.After;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Component
@Aspect
@Slf4j
public class EventLogFilter {
    
    @Before("execution(* jp.co.mgsystems.yuricollection.gootscatalog..*Controller.*(..))")
    public void beforeLog(JoinPoint joinPoint) {
        // Controllerのメソッド呼び出し前処理として実行される
        log.info(String.format("%s START", joinPoint.toShortString()));
    }

    @After("execution(* jp.co.mgsystems.yuricollection.gootscatalog..*Controller.*(..))")
    public void afterLog(JoinPoint joinPoint) {
        // Controllerのメソッド呼び出し前処理として実行される
        log.info(String.format("%s END", joinPoint.toShortString()));
    }
}
