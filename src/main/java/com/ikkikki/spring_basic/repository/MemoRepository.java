package com.ikkikki.spring_basic.repository;

import com.ikkikki.spring_basic.domain.Memo;
import com.ikkikki.spring_basic.domain.dto.MemoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MemoRepository extends JpaRepository<Memo, Long> {
  void deleteByMemoText(String memoText);
  List<Memo> findByMemoText(String memoText);

  // 정렬 , 페이징
  List<Memo> findByMnoBetweenOrderByMnoDesc(Long mno1, Long mno2);
  Page<Memo> findByMnoBetween(Long mno1, Long mno2, Pageable pageable);

  long countBymno(Long mno);
  List<Memo> findByMnoOrMemoText(Long mno, String memoText);

//  List<Memo> getListDesc(); // 사전에 정의되지않은 메서드는 실행하다가 터짐!!

//  @Query ("select * from tbl_memo order by mno desc") // * 아스트리크 허용 안함!
//  List<Memo> getListDesc();

  @Query ("select m from Memo m order by m.mno desc limit 10") // * 대신 별칭 m 부여. 의미는 *과 동일
  List<Memo> getListDesc();

  //nativeQuery? JPQL이 아니라 실제 SQL문을 쓸수있게함. *도, 실제 테이블명도 사용가능. 하지만 DB의 영향을 많이 받는다.
  @Query (value = "select  * from tbl_memo order by mno desc limit 10", nativeQuery = true)
  List<Memo> getListDesc2();

  @Transactional
  @Modifying
  @Query("update Memo m set m.memoText = :memoText where m.mno = :mno")
  int updateMemoText(@Param("mno") Long mno, @Param("memoText") String memoText);

  @Transactional
  @Modifying
  @Query("update Memo m set m.memoText = :#{#memo.memoText} where m.mno = :#{#memo.mno}")
  int updateMemoText2(@Param("memo") Memo memo);

  @Transactional
  @Modifying
  @Query("update Memo m set m.memoText = ?2 where m.mno = ?1")
  int updateMemoText3(Long mno, String memoText);

  @Query(value = "select m.mno, m.memoText, CURRENT_DATE AS n from Memo m where m.mno > :mno",
          countQuery = "select count(m) from Memo m where m.mno > :mno")
  Page<Object[]> getListWithQueryObject(Long mno, Pageable pageable);

  @Query(value = "select m.mno AS mno, m.memoText AS memoText, CURRENT_DATE AS n from Memo m where m.mno > :mno",
          countQuery = "select count(m) from Memo m where m.mno > :mno")
  Page<MemoDTO> getListWithQueryProjection(Long mno, Pageable pageable);

}
