package com.example.testing.controller;

import com.example.testing.dto.AuthorDto;
import com.example.testing.dto.BookDto;
import com.example.testing.service.BookUseCases;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class BookControllerTest {
    @Mock
    private BookUseCases bookUseCases;
    @InjectMocks
    private BookController bookController;

    private final AuthorDto authorDto = new AuthorDto(5L, "Kazimierz", "Wielki");

    @Test
    void shouldCorrectly_GetBooks_WhenBooksExists() {
        //given
        List<BookDto> books  = List.of(
                new BookDto(2L, "Pan Mateusz", "9780244226458", List.of(authorDto))
        );
        when(bookUseCases.getBooks())
                .thenReturn(books);
        //when
        List<BookDto> books_got = bookController.getBooks().stream().toList();
        //then
        assertEquals(books.get(0), books_got.get(0));
    }

    @Test
    void shouldCorrectly_GetBook_WhenBookExists() {
        //given
        BookDto book = new BookDto(2L, "Pan Mateusz", "9780244226458", List.of(authorDto));
        when(bookUseCases.getBook(2L))
                .thenReturn(book);
        //when
        BookDto book_got = bookController.getBook(2L);
        //then
        assertEquals(book, book_got);
    }

    @Test
    void shouldCorrectly_CreateBook_WhenBookIsValid() {
        //given
        BookDto book = new BookDto(2L, "Pan Mateusz", "9780244226458", List.of(authorDto));
        //when
        bookController.createBook(book);
        //then
        verify(bookUseCases).createBook(book);
    }

    @Test
    void shouldCorrectly_UpdateBook_WhenBookIsValid() {
        //given
        Long id = 2L;
        BookDto bookDto = new BookDto(id, "Pan Mateusz", "9780244226458", List.of(authorDto));
        //when
        bookController.updateBook(id, bookDto);
        //then
        verify(bookUseCases).updateBook(id, bookDto);
    }
}