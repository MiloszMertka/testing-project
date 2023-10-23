package com.example.testing.mapper.internal;

import com.example.testing.dto.ReaderDto;
import com.example.testing.mapper.ReaderMapper;
import com.example.testing.model.Reader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ReaderMapperService implements ReaderMapper {

    @Override
    public ReaderDto mapReaderToReaderDto(@NonNull Reader reader) {
        return new ReaderDto(reader.getId(), reader.getFirstName(), reader.getLastName(), reader.getCardNumber());
    }

    @Override
    public Reader mapReaderDtoToReader(@NonNull ReaderDto readerDto) {
        return new Reader(readerDto.firstName(), readerDto.lastName(), readerDto.cardNumber());
    }

    @Override
    public void updateReaderFromReaderDto(@NonNull Reader reader, @NonNull ReaderDto readerDto) {
        reader.setFirstName(readerDto.firstName());
        reader.setLastName(readerDto.lastName());
        reader.setCardNumber(readerDto.cardNumber());
    }

}
