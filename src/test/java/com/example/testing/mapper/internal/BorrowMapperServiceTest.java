package com.example.testing.mapper.internal;

import com.example.testing.dto.AuthorDto;
import com.example.testing.dto.BookDto;
import com.example.testing.dto.BorrowDto;
import com.example.testing.dto.ReaderDto;
import com.example.testing.model.Author;
import com.example.testing.model.Book;
import com.example.testing.model.Borrow;
import com.example.testing.model.Reader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class BorrowMapperServiceTest {

    @Autowired
    private BorrowMapperService borrowMapperService;

    @Test
    void mapBorrowToBorrowDto_shouldCorrectlyMap_whenBorrowIsNotNull() {
        //given
        Author author = new Author("Adam", "Mickiewicz");
        Book book = new Book("Pan Tadeusz", "9780244226459", List.of(author));
        Reader reader = new Reader("Adam", "Mocker", "12345");

        Borrow borrow = new Borrow(book, reader);

        //when
        BorrowDto borrowDto = borrowMapperService.mapBorrowToBorrowDto(borrow);

        //then
        BookDto bookDto = borrowDto.book();
        assertEquals(book.getId(), bookDto.id());
        assertEquals(book.getTitle(), bookDto.title());
        assertEquals(book.getIsbn(), bookDto.isbn());

        ReaderDto readerDto = borrowDto.reader();
        assertEquals(reader.getFirstName(), readerDto.firstName());
        assertEquals(reader.getLastName(), readerDto.lastName());
        assertEquals(reader.getCardNumber(), readerDto.cardNumber());
    }

    @Test
    void mapBorrowToBorrowDto_shouldThrowException_whenBorrowIsNull() {
        assertThrows(NullPointerException.class, () -> borrowMapperService.mapBorrowToBorrowDto(null));
    }

    @Test
    void mapBorrowDtoToBorrow_shouldCorrectlyMap_whenBorrowDtoIsNotNull() {
        //given
        AuthorDto authorDto = new AuthorDto(1L, "Adam", "Mickiewicz");
        BookDto bookDto = new BookDto(2L, "Pan Tadeusz", "9780244226459", List.of(authorDto));
        ReaderDto readerDto = new ReaderDto(3L, "Adam", "Mocker", "12345");
        LocalDateTime borrowDate = LocalDateTime.of(2023, 1, 1, 12, 0, 0);
        LocalDateTime returnDate = LocalDateTime.of(2023, 1, 2, 12, 0, 0);

        BorrowDto borrowDto = new BorrowDto(1L, borrowDate, returnDate, bookDto, readerDto);

        //when
        Borrow borrow = borrowMapperService.mapBorrowDtoToBorrow(borrowDto);

        //then
        Book book = borrow.getBook();
        assertEquals(bookDto.id(), book.getId());
        assertEquals(bookDto.title(), book.getTitle());
        assertEquals(bookDto.isbn(), book.getIsbn());

        Reader reader = borrow.getReader();
        assertEquals(readerDto.firstName(), reader.getFirstName());
        assertEquals(readerDto.lastName(), reader.getLastName());
        assertEquals(readerDto.cardNumber(), reader.getCardNumber());

        assertEquals(borrowDate, borrow.getBorrowDate());
        assertEquals(returnDate, borrow.getReturnDate());
    }

    @Test
    void mapBorrowDtoToBorrow_shouldThrowException_whenBorrowDtoIsNull() {
        assertThrows(NullPointerException.class, () -> borrowMapperService.mapBorrowDtoToBorrow(null));
    }

}
