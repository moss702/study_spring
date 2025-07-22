package com.ikkikki.spring_basic.service;

import com.ikkikki.spring_basic.domain.Member;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@ToString
@Service("mem1")
@RequiredArgsConstructor //필요한 애들만 주입. * final 또는 @NonNull
public class MemberServiceImpl implements MemberService {

  @Override
  public void resister(Member member) {

  }
}
