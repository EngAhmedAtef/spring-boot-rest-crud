package com.ahmedatef.springboot.restcrud.service;

import org.springframework.stereotype.Service;

@Service
public class CalculatorService {

    public int add(int x, int y) {
        if (x == 0 || y == 0)
            throw new ArithmeticException("Cannot add to zero.");
        return x + y;
    }

    public int subtract(int x, int y) {
        if (y > x)
            throw new ArithmeticException("Subtraction will result in a negative number.");
        return x - y;
    }

    public int multiply(int x, int y) {
        if (x == 0 || y == 0)
            throw new ArithmeticException("Cannot multiply by zero.");
        return x * y;
    }

    public int divide(int x, int y) {
        if (y == 0)
            throw new ArithmeticException("Cannot divide by zero.");
        return x / y;
    }

}