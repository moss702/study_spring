package com.ikkikki.spring_basic.aop;

import org.springframework.aop.framework.ProxyFactory;

import java.lang.reflect.Proxy;

public class App {
  public static void main(String[] args) {
    //
    BoardService target = new BoardServiceImpl();
    System.out.println("target의 결과물==============");
    target.write("제목", "내용");

    // 어라운드 형태로 어드바이스가 들어감
    // 새로운 프록시 생성. 인자 3개 필요(클래스로더, 클래스배열, 핸들러)
    BoardService proxy = (BoardService) Proxy.newProxyInstance(
            BoardService.class.getClassLoader(),
            new Class[] {BoardService.class},
            new LoggingInvocationHandler(target)
    );
    System.out.println("target의 결과물==============");
    proxy.write("title", "content");


  }
}
