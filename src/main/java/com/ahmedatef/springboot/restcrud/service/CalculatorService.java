package com.ahmedatef.springboot.restcrud.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public int add(int x, int y) { return x + y; }

    public int subtract(int x, int y) { return x - y; }

    public int multiply(int x, int y) { return x * y; }

    public int divide(int x, int y) { return x / y; }

}