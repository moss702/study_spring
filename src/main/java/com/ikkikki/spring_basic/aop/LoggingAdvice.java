package com.ikkikki.spring_basic.aop;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

@Component
public class LoggingAdvice implements MethodInterceptor {
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    System.out.println("[로그] 호출 전 :: " + invocation.getMethod().getName());
    Object o = invocation.proceed(); // 해당 메서드를 진행시키면 와야할녀석. 여기서 o는 void
    System.out.println("[로그] 호출 후 :: " + invocation.getMethod().getName());
    return o;
  }
}
