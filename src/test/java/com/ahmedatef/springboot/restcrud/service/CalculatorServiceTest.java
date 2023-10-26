package com.ahmedatef.springboot.restcrud.service;

import com.ahmedatef.springboot.restcrud.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceTest {

    private CalculatorService service;

    @BeforeEach
    public void initializeService() { service = new CalculatorService(); }

    @Test
    public void CalculatorService_Add_ReturnsCorrectAnswer() {
        assertEquals(4, service.add(2, 2));
    }

    @Test
    public void CalculatorService_AddToZero_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> service.add(5, 0));
    }

    @Test
    public void CalculatorService_Subtract_ReturnsCorrectAnswer() {
        assertEquals(2, service.subtract(5, 3));
    }

    @Test
    public void CalculatorService_SubtractResultsInNegative_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> service.subtract(5, 6));
    }

    @Test
    public void CalculatorService_Multiply_ReturnsCorrectAnswer() {
        assertEquals(15, service.multiply(5, 3));
    }

    @Test
    public void CalculatorService_MultiplyByZero_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> service.add(5, 0));
    }

    @Test
    public void CalculatorService_Divide_ReturnsCorrectAnswer() {
        assertEquals(5, service.divide(10, 2));
    }

    @Test
    public void CalculatorService_DivideByZero_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> service.divide(5, 0));
    }

}