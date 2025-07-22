package com.ikkikki.spring_basic.main;

import com.ikkikki.spring_basic.domain.Member;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MemberMain {
  public static void main(String[] args) {
    ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("xml/bean-config.xml");
    // xml 가져올거고 classpath 기준으로 탐색하겠다.
    Member m = context.getBean("member", Member.class);
    System.out.println(m);
  }
}
