package com.example.testing.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
public class AuthorDtoTest {

    @Autowired
    private Validator validator;

    @Test
    void shouldCorrectly_createAuthorDto() {
        //given
        Long id = 1L;
        String firstName = "Micheal";
        String lastName = "Costner";

        //when
        AuthorDto authorDto = new AuthorDto(id, firstName, lastName);

        //then
        assertEquals(id, authorDto.id());
        assertEquals(firstName, authorDto.firstName());
        assertEquals(lastName, authorDto.lastName());
    }

    @Test
    void shouldThrowException_whenFirstNameExceedsMaxSize() {
        //given
        Long id = 1L;
        String firstName = "A".repeat(256);
        String lastName = "Smith";

        //when
        Set<ConstraintViolation<AuthorDto>> violations = validator.validate(new AuthorDto(id, firstName, lastName));

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

        //when
        Set<ConstraintViolation<AuthorDto>> violations = validator.validate(new AuthorDto(id, firstName, lastName));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_whenLastNameExceedsMaxSize() {
        //given
        Long id = 1L;
        String firstName = "Adam";
        String lastName = "S".repeat(256);

        //when
        Set<ConstraintViolation<AuthorDto>> violations = validator.validate(new AuthorDto(id, firstName, lastName));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 0 and 255", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_whenLastNameIsBlank() {
        //given
        Long id = 1L;
        String firstName = "Adam";
        String lastName = "";

        //when
        Set<ConstraintViolation<AuthorDto>> violations = validator.validate(new AuthorDto(id, firstName, lastName));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }
}