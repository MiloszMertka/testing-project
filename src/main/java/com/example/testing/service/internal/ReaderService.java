package com.example.testing.service.internal;

import com.example.testing.dto.ReaderDto;
import com.example.testing.mapper.ReaderMapper;
import com.example.testing.repository.ReaderRepository;
import com.example.testing.service.ReaderUseCases;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ReaderService implements ReaderUseCases {

    private final ReaderRepository readerRepository;
    private final ReaderMapper readerMapper;

    @Override
    public Collection<ReaderDto> getReaders() {
        final var readers = readerRepository.findAll();
        return readers.stream()
                .map(readerMapper::mapReaderToReaderDto)
                .toList();
    }

    @Override
    public ReaderDto getReader(@NonNull Long id) {
        final var reader = readerRepository.findById(id).orElseThrow();
        return readerMapper.mapReaderToReaderDto(reader);
    }

    @Override
    public void createReader(@NonNull @Valid ReaderDto readerDto) {
        final var reader = readerMapper.mapReaderDtoToReader(readerDto);
        readerRepository.save(reader);
    }

    @Override
    public void updateReader(@NonNull Long id, @NonNull @Valid ReaderDto readerDto) {
        final var reader = readerRepository.findById(id).orElseThrow();
        readerMapper.updateReaderFromReaderDto(reader, readerDto);
        readerRepository.save(reader);
    }

    @Override
    public void deleteReader(@NonNull Long id) {
        readerRepository.deleteById(id);
    }

}
