package com.example.testing.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class BorrowDtoValidationTest {

    @Autowired
    private Validator validator;

    @Test
    void shouldThrowException_whenBookIsBlank() {
        //given
        Long id = 1L;
        LocalDateTime borrowDate = LocalDateTime.now();
        LocalDateTime returnDate = LocalDateTime.now();
        ReaderDto reader = new ReaderDto(1000L, "TestName", "TestLastName", "12345");

        //when
        Set<ConstraintViolation<BorrowDto>> violations = validator
                .validate(new BorrowDto(id, borrowDate, returnDate, null, reader));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

    @Test
    void shouldThrowException_whenReaderIsBlank() {
        //given
        Long id = 1L;
        LocalDateTime borrowDate = LocalDateTime.now();
        LocalDateTime returnDate = LocalDateTime.now();
        BookDto book = new BookDto(10L, "Test Title", "", List.of(new AuthorDto(100L, "", "")));

        //when
        Set<ConstraintViolation<BorrowDto>> violations = validator
                .validate(new BorrowDto(id, borrowDate, returnDate, book, null));

        //then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("must not be null", violations.iterator().next().getMessage());
    }

}
