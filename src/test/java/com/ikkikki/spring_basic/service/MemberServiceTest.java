package com.ikkikki.spring_basic.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class MemberServiceTest {
  @Autowired
  @Qualifier("mem1")
  private MemberService memberService;

  @Test
  public void testExist() {
    log.info("{}", memberService);
  }


}
