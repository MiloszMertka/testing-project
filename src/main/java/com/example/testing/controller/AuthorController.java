package com.example.testing.controller;

import com.example.testing.dto.AuthorDto;
import com.example.testing.service.AuthorUseCases;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorController {

    private final AuthorUseCases authorUseCases;

    @GetMapping
    public Collection<AuthorDto> getAuthors() {
        return authorUseCases.getAuthors();
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthor(@PathVariable Long id) {
        return authorUseCases.getAuthor(id);
    }

    @PostMapping
    public void createAuthor(@RequestBody AuthorDto authorDto) {
        authorUseCases.createAuthor(authorDto);
    }

    @PatchMapping("/{id}")
    public void updateAuthor(@PathVariable Long id, @RequestBody AuthorDto authorDto) {
        authorUseCases.updateAuthor(id, authorDto);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable Long id) {
        authorUseCases.deleteAuthor(id);
    }

}
