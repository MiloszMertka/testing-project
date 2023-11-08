package com.example.testing.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@SpringBootTest
@ActiveProfiles("test")
class BookDtoValidationTest {

    private final AuthorDto authorDto = new AuthorDto(5L, "Kazimierz", "Wielki");
    @Autowired
    private Validator validator;

    @Test
    void shouldThrowException_WhenTitleExceedsLimitedCapacity() {
        //given
        Long id = 1L;
        String title = "D".repeat(256);
        String isbn = "98-3-6-148410";
        Set<AuthorDto> authorsDto = Set.of(authorDto);
        //when
        Set<ConstraintViolation<BookDto>> violations = validator.validate(new BookDto(id, title, isbn, authorsDto));
        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 0 and 255", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_WhenTitleIsBlank() {
        //given
        Long id = 1L;
        String title = "";
        String isbn = "98-3-6-148410";
        Set<AuthorDto> authorsDto = Set.of(authorDto);
        //when
        Set<ConstraintViolation<BookDto>> violations = validator.validate(new BookDto(id, title, isbn, authorsDto));
        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_WhenISBNisIncorrect() {
        //given
        Long id = 1L;
        String title = "Sieci Komputerowe";
        String isbn = "98-3-6-14f410";
        Set<AuthorDto> authorsDto = Set.of(authorDto);
        //when
        Set<ConstraintViolation<BookDto>> violations = validator.validate(new BookDto(id, title, isbn, authorsDto));
        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must match \"^[\\d-]+$\"", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_WhenISBNisToShort() {
        //given
        Long id = 1L;
        String title = "Sieci Komputerowe";
        String isbn = "978-3-1-0";
        Set<AuthorDto> authorsDto = Set.of(authorDto);
        //when
        Set<ConstraintViolation<BookDto>> violations = validator.validate(new BookDto(id, title, isbn, authorsDto));
        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 10 and 13", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_WhenISBNisToLong() {
        //given
        Long id = 1L;
        String title = "Sieci Komputerowe";
        String isbn = "978-3-16-1484101232-0";
        Set<AuthorDto> authorsDto = Set.of(authorDto);
        //when
        Set<ConstraintViolation<BookDto>> violations = validator.validate(new BookDto(id, title, isbn, authorsDto));
        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 10 and 13", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_WhenISBNisBlank() {
        //given
        Long id = 1L;
        String title = "Sieci Komputerowe";
        String isbn = null;
        Set<AuthorDto> authorsDto = Set.of(authorDto);
        //when
        Set<ConstraintViolation<BookDto>> violations = validator.validate(new BookDto(id, title, isbn, authorsDto));
        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be blank", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_WhenNoAuthorGiven() {
        //given
        Long id = 1L;
        String title = "Sieci Komputerowe";
        String isbn = "98-3-6-148410";
        Set<AuthorDto> authorsDto = Set.of();
        //when
        Set<ConstraintViolation<BookDto>> violations = validator.validate(new BookDto(id, title, isbn, authorsDto));
        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 1 and 2147483647", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_WhenAuthorIsNull() {
        //given
        Long id = 1L;
        String title = "Sieci Komputerowe";
        String isbn = "98-3-6-148410";
        Set<AuthorDto> authorsDto = null;
        //when
        Set<ConstraintViolation<BookDto>> violations = validator.validate(new BookDto(id, title, isbn, authorsDto));
        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

}