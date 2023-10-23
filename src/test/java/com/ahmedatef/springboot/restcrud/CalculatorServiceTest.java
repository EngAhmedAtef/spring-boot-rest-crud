package com.ahmedatef.springboot.restcrud;

import com.ahmedatef.springboot.restcrud.service.CalculatorService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class CalculatorServiceTest {

    private final CalculatorService service = new CalculatorService();

    @Test
    public void CalculatorService_Add_ReturnsCorrectAnswer() {
        int answer = 4;
        int expected = service.add(2, 2);

        Assertions.assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_Subtract_ReturnsCorrectAnswer() {
        int answer = 2;
        int expected = service.subtract(5, 3);

        Assertions.assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_Multiply_ReturnsCorrectAnswer() {
        int answer = 15;
        int expected = service.multiply(5, 3);

        Assertions.assertThat(answer == expected).isTrue();
    }

    @Test
    public void CalculatorService_Divide_ReturnsCorrectAnswer() {
        int answer = 5;
        int expected = service.divide(10, 2);

        Assertions.assertThat(answer == expected).isTrue();
    }

}