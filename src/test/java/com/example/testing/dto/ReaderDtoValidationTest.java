package com.example.testing.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ReaderDtoValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void shouldThrowException_whenFirstNameExceedsMaxSize() {

        //given
        Long id = 1L;
        String firstName = "A".repeat(256);
        String lastName = "Smith";
        String cardNumber = "111111";

        //when
        Set<ConstraintViolation<ReaderDto>> violations = validator
                .validate(new ReaderDto(id, firstName, lastName, cardNumber));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 0 and 255", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_whenFirstNameIsBlank() {
        //given
        Long id = 1L;
        String firstName = "";
        String lastName = "Smith";
        String cardNumber = "11111";

        //when
        Set<ConstraintViolation<ReaderDto>> violations
                = validator.validate(new ReaderDto(id, firstName, lastName, cardNumber));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_whenLastNameExceedsMaxSize() {

        //given
        Long id = 1L;
        String firstName = "Tom";
        String lastName = "A".repeat(256);
        String cardNumber = "111111";

        //when
        Set<ConstraintViolation<ReaderDto>> violations = validator
                .validate(new ReaderDto(id, firstName, lastName, cardNumber));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 0 and 255", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_whenLastNameIsBlank() {

        //given
        Long id = 1L;
        String firstName = "Tom";
        String lastName = "";
        String cardNumber = "111111";

        //when
        Set<ConstraintViolation<ReaderDto>> violations = validator
                .validate(new ReaderDto(id, firstName, lastName, cardNumber));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_whenCardNumberExceedsMaxSize() {

        //given
        Long id = 1L;
        String firstName = "Tom";
        String lastName = "Armani";
        String cardNumber = "1".repeat(16);

        //when
        Set<ConstraintViolation<ReaderDto>> violations = validator
                .validate(new ReaderDto(id, firstName, lastName, cardNumber));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 5 and 15", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_whenCardNumberLengthIsBelowMinSize() {

        //given
        Long id = 1L;
        String firstName = "Tom";
        String lastName = "Armani";
        String cardNumber = "1".repeat(4);

        //when
        Set<ConstraintViolation<ReaderDto>> violations = validator
                .validate(new ReaderDto(id, firstName, lastName, cardNumber));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 5 and 15", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_whenCardNumberIsBlank() {

        //given
        Long id = 1L;
        String firstName = "Tom";
        String lastName = "Armani";
        String cardNumber = "";

        //when
        Set<ConstraintViolation<ReaderDto>> violations = validator
                .validate(new ReaderDto(id, firstName, lastName, cardNumber));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 5 and 15", violations.iterator().next().getMessage());
    }

}
