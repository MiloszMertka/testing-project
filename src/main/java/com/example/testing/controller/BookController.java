package com.example.testing.controller;

import com.example.testing.dto.BookDto;
import com.example.testing.service.BookUseCases;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class BookController {

    private final BookUseCases bookUseCases;

    @GetMapping
    public Collection<BookDto> getBooks() {
        return bookUseCases.getBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBook(@PathVariable Long id) {
        return bookUseCases.getBook(id);
    }

    @PostMapping
    public void createBook(@RequestBody BookDto bookDto) {
        bookUseCases.createBook(bookDto);
    }

    @PatchMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        bookUseCases.updateBook(id, bookDto);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookUseCases.deleteBook(id);
    }

}
