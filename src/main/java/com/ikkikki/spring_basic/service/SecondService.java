package com.ikkikki.spring_basic.service;

import org.springframework.stereotype.Service;

@Service
public class SecondService {
  public void one() {
    System.out.println("Second.one()");
  }
  public void two() {
    System.out.println("Second.two()");
  }
}
