package com.ikkikki.spring_basic.aop.advice;

import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;

@Component
public class MyThrowAdvice implements ThrowsAdvice {
  public void afterThrowing(Throwable throwable) throws Throwable {
    System.out.println("im afterThrow");
  }
}
