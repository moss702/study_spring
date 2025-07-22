package com.ikkikki.spring_basic.aop;


public interface BoardService {
  void write(String title, String Content);
  Object get(Long bno);
  void modify(String title, String Content);
  void remove(Long bno);
  //void list(Long bno);
}
