package com.ikkikki.spring_basic.repository;

import com.ikkikki.spring_basic.domain.Memo;
import com.ikkikki.spring_basic.domain.dto.MemoDTO;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

@Slf4j
@SpringBootTest
public class MemoRepositoryTest {

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private MemoRepository memoRepository;

  @Transactional
  @Rollback(false)
  @Test
  public void testEntityManager() {
    log.info("{}", entityManager);
    entityManager.persist(new Memo());
  }

//  @Transactional
//  @Rollback(false)
  @Test
  public void testEntityManager2(){
//    Memo memo = memoRepository.findById(2L).orElseThrow(RuntimeException::new);
//    memo.setMemoText("Hello world");
    //jpa는 dirty checking을 통해 값 변경을 감지한다
    Memo memo = entityManager.find(Memo.class, 3L); // 영속 객체
    memo.setMemoText("안녕세상아");
  }
  @Transactional
  @Rollback(false)
  @Test
  public void testEntityManager3() {
    Memo memo = new Memo();
    memo.setMno(3L);
    memo.setMemoText("비영속");
    entityManager.merge(memo);
  }

  @Test
  public void testExist() {
    log.info("testExist: {}", memoRepository);
  }

  @Test
  public void testInsertDummies(){ // 100개 인서트
    IntStream.rangeClosed(1, 100).forEach(i ->{
      Memo memo = Memo.builder().memoText("Sample..." + i).build();
      memoRepository.save(memo);
    });
  }

  @Test
  public void testInsert() { // 1개 인서트
    Memo memo = Memo.builder().memoText("메모 인서트 테스트").build();
    memoRepository.save(memo);
  }

  @Test
  public void testfindById() { // 1개 조회
    Memo memo = memoRepository.findById(1L).orElseThrow(()-> new RuntimeException("해당 번호의 메모가 없습니다."));
    log.info("testFindById: {}", memo);
  }

  @Test
  public void testSelect(){ // *교재 60p
    Long mno = 100L;
    Optional<Memo> result = memoRepository.findById(mno);
    System.out.println("====================");
    if(result.isPresent()){
      Memo memo = result.get();
      System.out.println(memo);
    }
  }

  @Test
  public void testPageDefault(){ // 페이징 처리. (1페이지 10개)
    Pageable pageable = PageRequest.of(1, 10);
    Page<Memo> result = memoRepository.findAll(pageable);
    System.out.println(result);

    System.out.println("===================================");
    System.out.println("Total Pages : " + result.getTotalPages());    // 총 페이지
    System.out.println("Total Count : " + result.getTotalElements()); // 전체 갯수
    System.out.println("Page Number : " + result.getNumber());        // 현재 페이지 넘버
    System.out.println("Page Size : " + result.getSize());            // 페이지당 데이터 갯수
    System.out.println("has next page? : " + result.hasNext());       // 다음 페이지
    System.out.println("first page? : " + result.isFirst());          // 시작 페이지(0)인가 여부
    System.out.println("===================================");
    long totalElements = result.getTotalElements();
    int totalPages = result.getTotalPages();

    log.info("totalElements: {}", totalElements);
    log.info("totalPages: {}", totalPages);

    result.getContent().forEach(System.out::println);
  }

  @Test
  public void testQueryMethod() {
    memoRepository.findByMnoBetweenOrderByMnoDesc(70L,80L).forEach(m -> log.info("{}", m));
  }

  @Test
  public void testQueryMethod2() {
    Page<Memo> memos = memoRepository.findByMnoBetween(10L, 50L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")));
    memos.forEach(m -> log.info("{}", m));
  }

  // memo의 총 갯수를 로깅하는. 이름 조합을 이용해서. groupBy
  @Test
  public void testQueryMethod3() {
    log.info("{}", memoRepository.countBymno(10L));
  }

  @Test
  public void testQueryMethod4(){
    // mno가 특정 long이거나, memoText가 특정 문자열일때 query method
    log.info("{}",memoRepository.findByMnoOrMemoText(1000L, "비영속"));
  }

  @Test
  public void testQueryMethod5() {
   // log.info("{}", memoRepository.getListDesc());
    log.info("{}", memoRepository.getListDesc2());
  }


  @Test
  public void testSort(){
    Sort sort = Sort.by(Sort.Direction.DESC, "mno");
    PageRequest pageRequest = PageRequest.of(0, 5, sort);
    Page<Memo> result = memoRepository.findAll(pageRequest);
    result.forEach(r -> log.info("{}", r));
  }

  @Test
  public void testListWithQueryObject(){
    Page<Object[]> objects = memoRepository.getListWithQueryObject(
            0L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")));
    objects.forEach(r -> {
      for(Object o : r) {
        log.info("{}", o);
      }
    });
  }

  @Test
  public void testListWithQueryProjection() {
    Page<MemoDTO> dtos = memoRepository.getListWithQueryProjection(
            0L, PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno")));
    dtos.forEach(r -> log.info("{} {} {}", r.getMno(), r.getMemoText(), r.getN()));
  }

  @Test
  public void testUpdateMemoText2() {
    memoRepository.updateMemoText(5L, "변경내용2");
  }
  @Test
  public void testUpdateMemoText3() {
    memoRepository.updateMemoText2(Memo.builder().mno(5L).memoText("변경내용2").build());
  }

  @Test
  public void testUpdateMemoText4() {
    memoRepository.updateMemoText3(6L, "순서찾기로 param 생략");
  }

  @Test
  public void testUpdate(){
    Memo memo = Memo.builder().mno(100L).memoText("Update Text").build();
    System.out.println(memoRepository.save(memo));
  }

  @Test
  public void testDelete() { // 1개 삭제
    Memo memo = entityManager.find(Memo.class, 2L);
    memoRepository.delete(memo);
  }

}
