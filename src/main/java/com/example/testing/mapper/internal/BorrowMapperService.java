package com.example.testing.mapper.internal;

import com.example.testing.dto.BorrowDto;
import com.example.testing.mapper.BookMapper;
import com.example.testing.mapper.BorrowMapper;
import com.example.testing.mapper.ReaderMapper;
import com.example.testing.model.Borrow;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class BorrowMapperService implements BorrowMapper {

    private final BookMapper bookMapper;
    private final ReaderMapper readerMapper;

    @Override
    public BorrowDto mapBorrowToBorrowDto(@NonNull Borrow borrow) {
        final var bookDto = bookMapper.mapBookToBookDto(borrow.getBook());
        final var readerDto = readerMapper.mapReaderToReaderDto(borrow.getReader());
        return new BorrowDto(borrow.getId(), borrow.getBorrowDate(), borrow.getReturnDate(), bookDto, readerDto);
    }

    @Override
    public Borrow mapBorrowDtoToBorrow(@NonNull BorrowDto borrowDto) {
        final var book = bookMapper.mapBookDtoToBook(borrowDto.book());
        final var reader = readerMapper.mapReaderDtoToReader(borrowDto.reader());
        return new Borrow(book, reader);
    }

}
