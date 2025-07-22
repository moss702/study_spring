package com.ikkikki.spring_basic.aop.pointcut;

import com.ikkikki.spring_basic.service.FirstService;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class MySimplePointcut extends StaticMethodMatcherPointcut {
  @Override
  public boolean matches(Method method, Class<?> targetClass) {
    System.out.println(method.getDeclaringClass());

    // 매개변수 개수 1개, 리턴타입이 void
    return method.getReturnType() == void.class && method.getParameterCount() == 1;
//    return method.getName().equals("two") && // 메서드는 원형이 없어서 이름을 가져와서 해야하지만
//            targetClass == FirstService.class; // 클래스는 원형이 있음. 이렇게 가져와서 비교해야 함


  }
}
