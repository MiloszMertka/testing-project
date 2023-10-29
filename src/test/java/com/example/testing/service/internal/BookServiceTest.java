package com.example.testing.service.internal;

import com.example.testing.dto.AuthorDto;
import com.example.testing.dto.BookDto;
import com.example.testing.mapper.BookMapper;
import com.example.testing.model.Author;
import com.example.testing.model.Book;
import com.example.testing.repository.BookRepository;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
class BookServiceTest {
    @Autowired
    private Validator validator;

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    private final Author author1 = new Author("Adam", "Mickiewicz");
    private final Author author2 = new Author("Juliusz", "Słowacki");
    private final AuthorDto authorDto1 = new AuthorDto(2L,"Adam", "Mickiewicz");
    private final AuthorDto authorDto2 = new AuthorDto(3L,"Juliusz", "Słowacki");

    @Test
    void shouldCorrectly_GetBooks_WhenBooksExists() {
        //given
        List<Book> books = Arrays.asList(
                new Book("Pan Mateusz", "9780244226459", List.of(author1)),
                new Book("Pan Tadeusz", "9780244226458", List.of(author2))
        );
        when(bookRepository.findAll())
                .thenReturn(books);
        when(bookMapper.mapBookToBookDto(any(Book.class)))
                .thenAnswer(invocation -> {
                    Book book = invocation.getArgument(0);
                    return new BookDto(
                            book.getId(),
                            book.getTitle(),
                            book.getIsbn(),
                            book.getAuthors().stream()
                                    .map(author -> new AuthorDto(
                                            author.getId(),
                                            author.getFirstName(),
                                            author.getLastName()
                                    )).toList());});
        //when
        List<BookDto> result = bookService.getBooks().stream().toList();
        //then
        assertEquals(2, result.size());
        assertEquals(books.get(0).getTitle(), result.get(0).title());
        assertEquals(books.get(0).getIsbn(), result.get(0).isbn());
        assertEquals(books.get(0).getAuthors().get(0).getFirstName(),
                result.get(0).authors().stream().toList().get(0).firstName());
        assertEquals(books.get(0).getAuthors().get(0).getLastName(),
                result.get(0).authors().stream().toList().get(0).lastName());
        assertEquals(books.get(1).getTitle(), result.get(1).title());
        assertEquals(books.get(1).getIsbn(), result.get(1).isbn());
        assertEquals(books.get(1).getAuthors().get(0).getFirstName(),
                result.get(1).authors().stream().toList().get(0).firstName());
        assertEquals(books.get(1).getAuthors().get(0).getLastName(),
                result.get(1).authors().stream().toList().get(0).lastName());
    }

    void shouldCorrectly_GetBook_WhenBookExists() {
        //given
        Book book = new Book("Pan Mateusz", "9780244226459", List.of(author1));
        when(bookRepository.findById(1L))
                .thenReturn(Optional.of(book));
        when(bookMapper.mapBookToBookDto(any(Book.class)))
                .thenReturn(new BookDto(1L, "Pan Matuesz", "9780244226459", List.of(authorDto1)));
        //when
        BookDto result = bookService.getBook(1L);
        //then
        assertEquals("Pan Matuesz", result.title());
        assertEquals("9780244226459", result.isbn());
        assertEquals("Adam", result.authors().stream().toList().get(0).firstName());
    }

    @Test
    void shouldThrow_WhenBook_DoesNotExists() {
        //given
        Long id = 1L;
        when(bookRepository.findById(id))
                .thenReturn(java.util.Optional.empty());
        //when
        assertThrows(NoSuchElementException.class, () -> bookService.getBook(id));
    }

    @Test
    void shouldCorrectly_CreateBook() {
        //given
        BookDto bookDto = new BookDto(1L, "Pan Matuesz", "9780244226459", List.of(authorDto1));
        Book book = new Book("Pan Mateusz", "9780244226459", List.of(author1));
        when(bookMapper.mapBookDtoToBook(any(BookDto.class)))
                .thenReturn(book);
        //when
        bookService.createBook(bookDto);
        //then
        assertEquals("Pan Mateusz", book.getTitle());
        assertEquals("9780244226459", book.getIsbn());
        assertEquals("Adam", book.getAuthors().get(0).getFirstName());
        verify(bookRepository).save(book);
    }

    @Test
    void shouldCorrectly_UpdateBook() {
        //given
        Long id = 1L;
        BookDto bookDto = new BookDto(id, "Pan Matuesz", "9780244226459", List.of(authorDto1));
        Book book = new Book("Pan Krzysztof", "9780244226459", List.of(author2));
        when(bookRepository.findById(1L))
                .thenReturn(java.util.Optional.of(book));
        //when
        bookService.updateBook(id, bookDto);
        //then
        verify(bookMapper).updateBookFromBookDto(book,bookDto);
        verify(bookRepository).save(book);
    }

    @Test
    void shouldCorrectly_DeleteBook() {
        //given
        Long id = 1L;
        Book book = new Book("Pan Matuesz", "9780244226459", List.of(author1));
        bookRepository.save(book);
        //when
        bookService.deleteBook(id);
        Optional<Book> res = bookRepository.findById(id);
        //then
        assertTrue(res.isEmpty());
        verify(bookRepository).deleteById(id);
    }

    @Test
    void shouldThrow_WhenGettingNonExistingID(){
        assertThrows(NoSuchElementException.class,
                () -> bookService.getBook(1L));
    }

    @Test
    void shouldThrow_WhenCreatingWithNullBookDto(){
        BookDto bookDto = null;
        assertThrows(NullPointerException.class,
                () -> bookService.createBook(bookDto));
    }

    @Test
    void shouldThrow_WhenUpdatingNonExistingID(){
        BookDto bookDto = new BookDto(1L, "Pan Matuesz", "9780244226459", List.of(authorDto1));
        assertThrows(NullPointerException.class,
                () -> bookService.updateBook(null, bookDto));
    }

    @Test
    void shouldThrow_WhenUpdatingWithNullBookDto(){
        BookDto bookDto = null;
        assertThrows(NullPointerException.class,
                () -> bookService.updateBook(1L, bookDto));
    }

    @Test
    void shouldThrow_WhenDeletingNonExistingID() {
        Long id = null;
        assertThrows(NullPointerException.class,
                () -> bookService.deleteBook(id));
    }
}