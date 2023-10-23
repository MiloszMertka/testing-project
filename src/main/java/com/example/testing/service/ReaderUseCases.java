package com.example.testing.service;

import com.example.testing.dto.ReaderDto;

import java.util.Collection;

public interface ReaderUseCases {

    Collection<ReaderDto> getReaders();

    ReaderDto getReader(Long id);

    void createReader(ReaderDto readerDto);

    void updateReader(Long id, ReaderDto readerDto);

    void deleteReader(Long id);

}
