package com.example.testing.mapper;

import com.example.testing.dto.BookDto;
import com.example.testing.model.Book;
import org.springframework.lang.NonNull;

public interface BookMapper {

    BookDto mapBookToBookDto(@NonNull Book book);

    Book mapBookDtoToBook(@NonNull BookDto bookDto);

    void updateBookFromBookDto(@NonNull Book book, @NonNull BookDto bookDto);

}
