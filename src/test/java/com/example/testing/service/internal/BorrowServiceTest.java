package com.example.testing.service.internal;

import com.example.testing.dto.AuthorDto;
import com.example.testing.dto.BookDto;
import com.example.testing.dto.BorrowDto;
import com.example.testing.dto.ReaderDto;
import com.example.testing.mapper.BookMapper;
import com.example.testing.mapper.BorrowMapper;
import com.example.testing.mapper.ReaderMapper;
import com.example.testing.model.Author;
import com.example.testing.model.Book;
import com.example.testing.model.Borrow;
import com.example.testing.model.Reader;
import com.example.testing.repository.BorrowRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BorrowServiceTest {

    @Mock
    private BorrowRepository borrowRepository;
    @Mock
    private BorrowMapper borrowMapper;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private ReaderMapper readerMapper;
    @InjectMocks
    private BorrowService borrowService;


    private final Author author = new Author("Adam", "Mickiewicz");
    private final Book book = new Book("Pan Tadeusz", "9780244226459", List.of(author));
    private final Reader reader = new Reader("Adam", "Mocker", "12345");

    private final AuthorDto authorDto = new AuthorDto(1L, "Adam", "Mickiewicz");
    private final BookDto bookDto = new BookDto(2L, "Pan Tadeusz", "9780244226459", List.of(authorDto));
    private final ReaderDto readerDto = new ReaderDto(3L, "Adam", "Mocker", "12345");


    @Test
    void getBorrows_shouldReturnBorrows_whenBorrowsExist() {
        //given
        Author author2 = new Author("Henryk", "Sienkiewicz");
        Book book2 = new Book("Latarnik", "9780244226451", List.of(author2));
        Reader reader2 = new Reader("Jan", "Kowalski", "54321");

        List<Borrow> borrowsList = Arrays.asList(
                new Borrow(book, reader),
                new Borrow(book2, reader2)
        );
        when(borrowRepository.findAll()).thenReturn(borrowsList);
        when(readerMapper.mapReaderToReaderDto(any(Reader.class)))
                .thenAnswer(invocation -> {
                    Reader reader = invocation.getArgument(0);
                    return new ReaderDto(
                            reader.getId(),
                            reader.getFirstName(),
                            reader.getLastName(),
                            reader.getCardNumber()
                    );
                });
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
        when(borrowMapper.mapBorrowToBorrowDto(any(Borrow.class)))
                .thenAnswer(invocation -> {
                    Borrow borrow = invocation.getArgument(0);
                    return new BorrowDto(
                            borrow.getId(),
                            borrow.getBorrowDate(),
                            borrow.getReturnDate(),
                            bookMapper.mapBookToBookDto(borrow.getBook()),
                            readerMapper.mapReaderToReaderDto(borrow.getReader())
                    );
                });

        //when
        Collection<BorrowDto> borrowDtoCollection = borrowService.getBorrows();

        //then
        List<BorrowDto> borrowDtos = borrowDtoCollection.stream().toList();

        assertEquals(2, borrowDtoCollection.size());

        assertEquals(borrowsList.get(0).getBorrowDate(), borrowDtos.get(0).borrowDate());
        assertEquals(borrowsList.get(0).getReturnDate(), borrowDtos.get(0).returnDate());
        Book bookBeingAsserted1 = borrowsList.get(0).getBook();
        BookDto bookDtoBeingAsserted1 = borrowDtos.get(0).book();
        assertEquals(bookBeingAsserted1.getId(), bookDtoBeingAsserted1.id());
        assertEquals(bookBeingAsserted1.getTitle(), bookDtoBeingAsserted1.title());
        assertEquals(bookBeingAsserted1.getIsbn(), bookDtoBeingAsserted1.isbn());

        assertEquals(borrowsList.get(1).getBorrowDate(), borrowDtos.get(1).borrowDate());
        assertEquals(borrowsList.get(1).getReturnDate(), borrowDtos.get(1).returnDate());
        Book bookBeingAsserted2 = borrowsList.get(1).getBook();
        BookDto bookDtoBeingAsserted2 = borrowDtos.get(1).book();
        assertEquals(bookBeingAsserted2.getId(), bookDtoBeingAsserted2.id());
        assertEquals(bookBeingAsserted2.getTitle(), bookDtoBeingAsserted2.title());
        assertEquals(bookBeingAsserted2.getIsbn(), bookDtoBeingAsserted2.isbn());
    }

    @Test
    void getBorrow_shouldReturnBorrow_whenBorrowExists() {
        //given
        LocalDateTime borrowDate = LocalDateTime.now();
        LocalDateTime returnDate = LocalDateTime.now();
        Borrow borrow = new Borrow(book, reader);
        when(borrowRepository.findById(1L)).thenReturn(Optional.of(borrow));
        when(borrowMapper.mapBorrowToBorrowDto(borrow)).thenReturn(new BorrowDto(
                1L, borrowDate, returnDate,
                bookDto, readerDto
        ));

        //when
        BorrowDto borrowDto = borrowService.getBorrow(1L);

        //then
        assertEquals(1L, borrowDto.id());
        assertEquals(borrowDate, borrowDto.borrowDate());
        assertEquals(returnDate, borrowDto.returnDate());
        assertEquals(bookDto, borrowDto.book());
        assertEquals(readerDto, borrowDto.reader());

        verify(borrowRepository).findById(1L);
        verify(borrowMapper).mapBorrowToBorrowDto(borrow);
    }

    @Test
    void getBorrow_shouldThrowException_whenIdIsNull() {
        assertThrows(NullPointerException.class, () -> borrowService.getBorrow(null));
    }

    @Test
    void getBorrow_shouldThrowException_whenBorrowDoesNotExist() {
        assertThrows(NoSuchElementException.class, () -> borrowService.getBorrow(1L));
    }

    @Test
    void borrowBook_shouldCorrectlyBorrowBook_whenDataIsNotNull() {
        //given
        BorrowDto borrowDto = new BorrowDto(1L, LocalDateTime.now(), LocalDateTime.now(), bookDto, readerDto);

        //when
        borrowService.borrowBook(borrowDto);

        //then
        assertThat(borrowRepository.findById(1L)).isNotNull();
        verify(borrowMapper).mapBorrowDtoToBorrow(borrowDto);
        verify(borrowRepository).save(borrowMapper.mapBorrowDtoToBorrow(borrowDto));
    }

    @Test
    void borrowBook_shouldThrowException_whenReaderDtoIsNull() {
        assertThrows(NullPointerException.class, () -> borrowService.borrowBook(null));
    }

    @Test
    void returnBook_shouldCorrectlyReturnBook_whenBorrowExists() {
        //given
        BorrowDto borrowDto = new BorrowDto(1L, LocalDateTime.now(), LocalDateTime.now(), bookDto, readerDto);
        Borrow borrow = new Borrow(book, reader);
        when(borrowRepository.findById(1L)).thenReturn(Optional.of(borrow));

        //when
        borrowService.returnBook(1L);

        //then
        verify(borrowRepository).findById(1L);
        verify(borrowRepository).save(borrow);
    }

    @Test
    void returnBook_shouldThrowException_whenBookDoesNotExist() {
        assertThrows(NoSuchElementException.class, () -> borrowService.returnBook(10L));
    }

    @Test
    void returnBook_shouldThrowException_whenIdIsNull() {
        assertThrows(NullPointerException.class, () -> borrowService.returnBook(null));
    }

    @Test
    void shouldCorrectly_deleteBorrow() {
        //given
        Long id = 1L;
        Borrow borrow = new Borrow(book, reader);
        borrowRepository.save(borrow);

        //when
        borrowService.deleteBorrow(id);
        Optional<Borrow> actualBorrow = borrowRepository.findById(id);

        //then
        assertThat(actualBorrow).isEmpty();
        verify(borrowRepository).deleteById(id);
    }

    @Test
    void deleteBorrow_shouldThrowException_whenIdIsNull() {
        assertThrows(NullPointerException.class, () -> borrowService.deleteBorrow(null));
    }

}
