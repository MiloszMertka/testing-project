package com.example.testing.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ReaderDto(
        Long id,
        @NotBlank(message = "must not be blank")
        @Size(max = 255, message = "size must be between 0 and 255")
        String firstName,
        @NotBlank(message = "must not be blank")
        @Size(max = 255, message = "size must be between 0 and 255")
        String lastName,
        @Size(min = 5, max = 15, message = "size must be between 5 and 15")
        String cardNumber) {

}