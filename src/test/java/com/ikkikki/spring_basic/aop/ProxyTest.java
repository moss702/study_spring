package com.ikkikki.spring_basic.aop;

import com.ikkikki.spring_basic.aop.advice.MyBeforeAdvice;
import com.ikkikki.spring_basic.service.FirstService;
import com.ikkikki.spring_basic.service.SecondService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.aop.Advice;
import org.junit.jupiter.api.Test;
import org.springframework.aop.*;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

@Slf4j
@SpringBootTest
public class ProxyTest {
  @Autowired
  private BoardService boardService;
  @Autowired
  private LoggingAdvice advice;
  @Autowired
  private MyBeforeAdvice beforeAdvice;
  @Autowired
  private AfterReturningAdvice returningAdvice;
  @Autowired
  private ThrowsAdvice throwsAdvice;

  @Autowired
  private Pointcut pointcut;

  @Autowired
  private BoardService proxy;
  @Autowired
  private FirstService firstService;
  @Autowired
  private SecondService secondService;

  @PostConstruct
  public void init() {
    Advice[] advices = new Advice[]{returningAdvice, throwsAdvice};
    // 정상 결과 returningAdvice | 예외 결과 throwsAdvice
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);

    for(Advice a : advices) {
      proxyFactory.addAdvice(a);
    }
    proxy = (BoardService) proxyFactory.getProxy();
  }

  @Test
  public void testExist() {
    log.info("{}", boardService);
  }

  @Test
  public void testWrite() {
    boardService.write("원본 객체의 제목", "내용");

    // 프록시 팩토리 객체 * Bean의 형태로
    // ProxyFactory proxyFactory = new ProxyFactory(boardService);
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);

    proxyFactory.addAdvice(advice);
    BoardService proxy = (BoardService) proxyFactory.getProxy();

    proxy.write("프록시 객체의 제목", "내용");
  }

  @Test
  public void testBefore() {
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);
    proxyFactory.addAdvice(beforeAdvice);
    proxyFactory.addAdvice(advice);
    BoardService proxy = (BoardService) proxyFactory.getProxy();

    System.out.println("======================= 글쓰기");
    proxy.write("비포 어드바이스 객체의 제목", "내용");
    System.out.println("======================= 글조회");
    proxy.get(3L);
  }

  @Test
  public void testAfterReturn() {
    try{
      System.out.println("======================= 글삭제");
      proxy.remove(1L);
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Test
  public void testAdvisor() {
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);

    PointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(pointcut, beforeAdvice);
    proxyFactory.addAdvisor(pointcutAdvisor); // 어드바이저 등록

    proxy = (BoardService) proxyFactory.getProxy();

    proxy.write("제목", "내용");
    proxy.get(3L);
    proxy.remove(4L);
  }

  @Test
  public void testFirstTwo() {
//    ProxyFactory pf1 = new ProxyFactory();
//    pf1.setTarget(firstService);
//
//    ProxyFactory pf2 = new ProxyFactory();
//    pf2.setTarget(secondService);
//
//    PointcutAdvisor pointcutAdvisor = new DefaultPointcutAdvisor(pointcut, beforeAdvice);
//    pf1.addAdvisor(pointcutAdvisor);
//    pf2.addAdvisor(pointcutAdvisor);
//
//    firstService = (FirstService) pf1.getProxy();
//    firstService.one();
//    firstService.two();
//
//    secondService = (SecondService) pf2.getProxy();
//    secondService.one();
//    secondService.two();

    MethodBeforeAdvice beforeAdvice = (method, args, target) -> System.out.println("익명 출력");

    DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new StaticMethodMatcherPointcut() {
      @Override
      public boolean matches(Method method, Class<?> targetClass) {
        return method.getName().equals("two") && targetClass == FirstService.class;
      }
    }, beforeAdvice);
    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(firstService);
    proxyFactory.addAdvisor(advisor);

    ProxyFactory proxyFactory2 = new ProxyFactory();
    proxyFactory2.setTarget(secondService);
    proxyFactory2.addAdvisor(advisor);

    FirstService proxy = (FirstService) proxyFactory.getProxy();
    proxy.one();
    proxy.two();

    SecondService proxy2 = (SecondService) proxyFactory2.getProxy();
    proxy2.one();
    proxy2.two();
  }

  @Test
  public void testAspectj() {
    AspectJExpressionPointcut pc =  new AspectJExpressionPointcut();
    pc.setExpression("execution(void *.write*(..))"); // 모든 메서드의 실행을 다 가로채오겠다.
    //"execution(*반환타입 *클래스명.*메서드명 (..))")

    // 포인트컷을 쓰기위해 어드바이저 정의
    DefaultPointcutAdvisor advisor = new  DefaultPointcutAdvisor(pc, beforeAdvice);

    ProxyFactory proxyFactory = new ProxyFactory();
    proxyFactory.setTarget(boardService);

    proxyFactory.addAdvisor(advisor);

    BoardService proxy = (BoardService) proxyFactory.getProxy();
    proxy.write("title", "content");
    proxy.get(3L);


  }


}
