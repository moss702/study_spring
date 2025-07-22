package com.ikkikki.spring_basic.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class LoggingInvocationHandler implements InvocationHandler {
  private Object target; // proxy의 대상이 될 녀석

  public LoggingInvocationHandler(Object target) { // 생성자
    this.target = target;
  }

  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    System.out.println("[로그] 호출 전 :: " + method.getName());
    Object o = method.invoke(target, args); // 실제로 할 일 | 메서드 대리 호출 * args: 오브젝트 배열
    System.out.println("[로그] 호출 후 :: " + method.getName());
    return o;
  }
}
