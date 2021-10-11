package com.example.demo.services;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author : christiaan.griffioen
 * @since :  15-6-2021, di
 **/
class CalculatorTest {

    //under test
    Calculator calculator = new Calculator();

    @Test
    void itShouldCalculateIq(){
        //given
        int age = 23;
        int score = 9;

        //when
        int result = calculator.calculateIq(age, score);

        //then
        assertThat(result).isEqualTo(32);
    }

}