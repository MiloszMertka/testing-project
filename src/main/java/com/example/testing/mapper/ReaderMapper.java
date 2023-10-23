package com.example.testing.mapper;

import com.example.testing.dto.ReaderDto;
import com.example.testing.model.Reader;
import org.springframework.lang.NonNull;

public interface ReaderMapper {

    ReaderDto mapReaderToReaderDto(@NonNull Reader reader);

    Reader mapReaderDtoToReader(@NonNull ReaderDto readerDto);

    void updateReaderFromReaderDto(@NonNull Reader reader, @NonNull ReaderDto readerDto);

}
