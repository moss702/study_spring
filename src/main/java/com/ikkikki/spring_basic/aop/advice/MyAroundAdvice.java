package com.ikkikki.spring_basic.aop.advice;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

@Component
public class MyAroundAdvice implements MethodInterceptor {
  //invoke
  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    System.out.println("[로그] 호출 전 ::" + invocation.getMethod().getName());;
    Object o = invocation.proceed();  // 여기서 Object는 void
    System.out.println("[로그] 호출 후 ::" + invocation.getMethod().getName());
    return o;
  }
}

