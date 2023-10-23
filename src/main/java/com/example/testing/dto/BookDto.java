package com.example.testing.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.Collection;

public record BookDto(
        Long id,
        @NotBlank @Size(max = 255) String title,
        @NotBlank @Size(min = 10, max = 13) @Pattern(regexp = "^(?=(?:\\D*\\d){10}(?:(?:\\D*\\d){3})?$)[\\d-]+$") String isbn,
        @NotNull @Size(min = 1) Collection<@NotNull @Valid AuthorDto> authors) {

}