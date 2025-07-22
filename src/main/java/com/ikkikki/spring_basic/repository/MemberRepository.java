package com.ikkikki.spring_basic.repository;

import com.ikkikki.spring_basic.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
                                // 내가 정의하게 될 Entity의 타입


}
