package com.example.testing.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record BorrowDto(
        Long id,
        LocalDateTime borrowDate,
        LocalDateTime returnDate,
        @NotNull @Valid BookDto book,
        @NotNull @Valid ReaderDto reader) {

}