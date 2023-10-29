package com.example.testing.mapper.internal;

import com.example.testing.dto.BookDto;
import com.example.testing.mapper.AuthorMapper;
import com.example.testing.mapper.BookMapper;
import com.example.testing.model.Book;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class BookMapperService implements BookMapper {

    private final AuthorMapper authorMapper;

    @Override
    public BookDto mapBookToBookDto(@NonNull Book book) {
        final var authorDtos = book.getAuthors().stream().map(authorMapper::mapAuthorToAuthorDto).toList();
        return new BookDto(book.getId(), book.getTitle(), book.getIsbn(), authorDtos);
    }

    @Override
    public Book mapBookDtoToBook(@NonNull BookDto bookDto) {
        final var authors = bookDto.authors().stream().map(authorMapper::mapAuthorDtoToAuthor).toList();
        return new Book(bookDto.title(), bookDto.isbn(), authors);
    }

    @Override
    public void updateBookFromBookDto(@NonNull Book book, @NonNull BookDto bookDto) {
        final var authors = bookDto.authors().stream().map(authorMapper::mapAuthorDtoToAuthor).toList();
        book.setTitle(bookDto.title());
        book.setIsbn(bookDto.isbn());
        book.getAuthors().clear();
        book.getAuthors().addAll(authors);
    }

}
