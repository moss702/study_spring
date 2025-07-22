package com.ikkikki.spring_basic.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
  @Before("execution(* com..*.get(..))") // 모든 위치에 있는, 메서드 이름이 get인 녀석
  public void before(JoinPoint joinPoint) {
    System.out.println(joinPoint.getSignature().getName() + "before 처리됨");
  }

  @Around("execution(* com..*.get(Long))")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
    System.out.println("around's before");
    Object ret = joinPoint.proceed();
    System.out.println("around's after");
    return ret;
  }

  @After("bean(boardServiceImpl)")
  public void after(JoinPoint joinPoint) {
    System.out.println("after finally");
  }

  @AfterReturning("args(String, String) && execution(* *..BoardServiceImpl.*(..))")
  public void afterReturn(JoinPoint joinPoint) {
    System.out.println("정상 종료 afterReturning");
  }
}
