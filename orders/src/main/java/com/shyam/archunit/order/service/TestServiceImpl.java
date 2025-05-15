package com.shyam.archunit.order.service;

import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl {

  public int add(int i, int j) {
    return i+j;
  }
}
