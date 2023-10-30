package com.example.testing.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BorrowDto(
        Long id,
        LocalDateTime borrowDate,
        LocalDateTime returnDate,
        @NotNull(message = "must not be null")
        @Valid
        BookDto book,
        @NotNull(message = "must not be null")
        @Valid
        ReaderDto reader) {

}