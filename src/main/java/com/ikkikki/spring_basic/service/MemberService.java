package com.ikkikki.spring_basic.service;

import com.ikkikki.spring_basic.domain.Member;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface MemberService {
  void resister(Member member);

}
