package com.example.testing.mapper.internal;

import com.example.testing.dto.AuthorDto;
import com.example.testing.dto.BookDto;
import com.example.testing.mapper.AuthorMapper;
import com.example.testing.model.Author;
import com.example.testing.model.Book;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class BookMapperServiceTest {

    @Mock
    private AuthorMapper authorMapper;
    @InjectMocks
    private BookMapperService bookMapperService;

    private final Author author = new Author("Adam", "Mickiewicz");
    private final AuthorDto authorDto = new AuthorDto(1L, "Adam", "Mickiewicz");

    @Test
    void shouldMapBookToBookDto() {
        //given
        Book book =  new Book("Pan Matuesz", "9780244226459", List.of(author));
        //when
        BookDto bookDto = bookMapperService.mapBookToBookDto(book);
        //then
        assertEquals(book.getId(), bookDto.id());
        assertEquals(book.getTitle(), bookDto.title());
        assertEquals(book.getIsbn(), bookDto.isbn());
    }

    @Test
    void shouldMapBookDtoToBook() {
        //given
        BookDto bookDto = new BookDto(1L, "Pan Matuesz", "9780244226459", List.of(authorDto));
        when(authorMapper.mapAuthorDtoToAuthor(authorDto)).thenReturn(author);
        //when
        Book book = bookMapperService.mapBookDtoToBook(bookDto);
        //then
        assertEquals(bookDto.title(), book.getTitle());
        assertEquals(bookDto.isbn(), book.getIsbn());
    }

    @Test
    void shouldUpdateBookFromBookDto(){
        //given
        List<Author> authors = new ArrayList<>();
        authors.add(author);
        List<AuthorDto> authorDtos = new ArrayList<>();
        authorDtos.add(authorDto);
        BookDto bookDto = new BookDto(1L, "Pan Krzysztof", "9780244226459", authorDtos);
        Book book = new Book("Pan Tadeusz", "9780244226458", authors);
        when(authorMapper.mapAuthorDtoToAuthor(any(AuthorDto.class))).thenReturn(author);
        //when
        bookMapperService.updateBookFromBookDto(book, bookDto);
        //then
        assertEquals(bookDto.title(), book.getTitle());
        assertEquals(bookDto.isbn(), book.getIsbn());
    }
}