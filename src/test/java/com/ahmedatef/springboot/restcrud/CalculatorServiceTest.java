package com.ahmedatef.springboot.restcrud;

import com.ahmedatef.springboot.restcrud.service.CalculatorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceTest {

    private CalculatorService service;

    @BeforeEach
    public void initializeService() { service = new CalculatorService(); }

    @Test
    public void CalculatorService_Add_ReturnsCorrectAnswer() {
        int answer = 4;
        int expected = service.add(2, 2);

        assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_AddToZero_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> service.add(5, 0));
    }

    @Test
    public void CalculatorService_Subtract_ReturnsCorrectAnswer() {
        int answer = 2;
        int expected = service.subtract(5, 3);

        assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_SubtractResultsInNegative_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> service.subtract(5, 6));
    }

    @Test
    public void CalculatorService_Multiply_ReturnsCorrectAnswer() {
        int answer = 15;
        int expected = service.multiply(5, 3);

        assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_MultiplyByZero_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> service.add(5, 0));
    }

    @Test
    public void CalculatorService_Divide_ReturnsCorrectAnswer() {
        int answer = 5;
        int expected = service.divide(10, 2);

        assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_DivideByZero_ThrowsArithmeticException() {
        assertThrows(ArithmeticException.class, () -> service.divide(5, 0));
    }

}