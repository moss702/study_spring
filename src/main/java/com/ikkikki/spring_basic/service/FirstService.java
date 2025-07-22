package com.ikkikki.spring_basic.service;

import org.springframework.stereotype.Service;

@Service
public class FirstService {
  public void one() {
    System.out.println("First.one()");
  }
  public void two() {
    System.out.println("First.two()");
  }
}
