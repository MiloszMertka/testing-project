package com.example.testing.service.internal;

import com.example.testing.dto.BookDto;
import com.example.testing.mapper.BookMapper;
import com.example.testing.repository.BookRepository;
import com.example.testing.service.BookUseCases;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class BookService implements BookUseCases {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Collection<BookDto> getBooks() {
        final var books = bookRepository.findAll();
        return books.stream()
                .map(bookMapper::mapBookToBookDto)
                .toList();
    }

    @Override
    public BookDto getBook(@NonNull Long id) {
        final var book = bookRepository.findById(id).orElseThrow();
        return bookMapper.mapBookToBookDto(book);
    }

    @Override
    public void createBook(@NonNull @Valid BookDto bookDto) {
        final var book = bookMapper.mapBookDtoToBook(bookDto);
        bookRepository.save(book);
    }

    @Override
    public void updateBook(@NonNull Long id, @NonNull @Valid BookDto bookDto) {
        final var book = bookRepository.findById(id).orElseThrow();
        bookMapper.updateBookFromBookDto(book, bookDto);
        bookRepository.save(book);
    }

    @Override
    public void deleteBook(@NonNull Long id) {
        bookRepository.deleteById(id);
    }

}
