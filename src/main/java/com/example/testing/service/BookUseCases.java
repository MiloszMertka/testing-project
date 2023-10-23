package com.example.testing.service;

import com.example.testing.dto.BookDto;

import java.util.Collection;

public interface BookUseCases {

    Collection<BookDto> getBooks();

    BookDto getBook(Long id);

    void createBook(BookDto bookDto);

    void updateBook(Long id, BookDto bookDto);

    void deleteBook(Long id);

}
