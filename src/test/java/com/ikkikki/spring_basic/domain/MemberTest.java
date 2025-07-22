package com.ikkikki.spring_basic.domain;

import com.ikkikki.spring_basic.config.AppConfig;
import com.ikkikki.spring_basic.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@Slf4j
@SpringBootTest
//@ContextConfiguration(locations = "classpath:xml/bean-config.xml") //xml로 정의된걸 가져옴
@ContextConfiguration(classes = {AppConfig.class}) //class로 정의된걸 가져옴
public class MemberTest {
  @Autowired
  Member member;

  @Test
  public void testExist() {
    log.info("{}", member);
  }
}
