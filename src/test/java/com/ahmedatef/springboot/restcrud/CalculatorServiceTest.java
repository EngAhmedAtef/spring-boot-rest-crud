package com.ahmedatef.springboot.restcrud;

import com.ahmedatef.springboot.restcrud.service.CalculatorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {

    @Test
    public void ArithmeticOperationsService_Add_ReturnsCorrectAnswer() {

        CalculatorService service = new CalculatorService();

        int answer = 4;
        int expected = service.add(2, 2);

        Assertions.assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_Subtract_ReturnsCorrectAnswer() {

        CalculatorService service = new CalculatorService();

        int answer = 2;
        int expected = service.subtract(5, 3);

        Assertions.assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_Multiply_ReturnsCorrectAnswer() {

        CalculatorService service = new CalculatorService();

        int answer = 15;
        int expected = service.multiply(5, 3);

        Assertions.assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_Divide_ReturnsCorrectAnswer() {

        CalculatorService service = new CalculatorService();

        int answer = 5;
        int expected = service.divide(10, 2);

        Assertions.assertThat(answer == expected).isTrue();
    }

}