package com.example.testing.mapper.internal;

import com.example.testing.dto.ReaderDto;
import com.example.testing.model.Reader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
public class ReaderMapperServiceTest {

    @Autowired
    private ReaderMapperService readerMapperService;

    @Test
    void mapReaderToReaderDto_shouldCorrectlyMap_whenReaderIsNotNull() {

        //given
        Reader reader = new Reader("John", "Mock", "12345");

        //when
        ReaderDto readerDto = readerMapperService.mapReaderToReaderDto(reader);

        //then
        assertEquals(reader.getFirstName(), readerDto.firstName());
        assertEquals(reader.getLastName(), readerDto.lastName());
        assertEquals(reader.getCardNumber(), readerDto.cardNumber());
    }

    @Test
    void mapReaderToReaderDto_shouldThrowException_whenReaderIsNull() {
        assertThrows(NullPointerException.class, () -> readerMapperService.mapReaderToReaderDto(null));
    }

    @Test
    void mapReaderDtoToReader_shouldCorrectlyMap_whenReaderDtoIsNotNull() {

        //given
        ReaderDto readerDto = new ReaderDto(1L, "John", "Mock", "12345");

        //when
        Reader reader = readerMapperService.mapReaderDtoToReader(readerDto);

        //then
        assertEquals(reader.getFirstName(), readerDto.firstName());
        assertEquals(reader.getLastName(), readerDto.lastName());
        assertEquals(reader.getCardNumber(), readerDto.cardNumber());
    }

    @Test
    void mapReaderDtoToReader_shouldThrowException_whenReaderDtoIsNull() {
        assertThrows(NullPointerException.class, () -> readerMapperService.mapReaderDtoToReader(null));
    }

    @Test
    void updateReaderFromReaderDto_shouldCorrectlyUpdate_whenDataIsNotNull() {

        //given
        Reader reader = new Reader("John", "Alibaba", "33333");
        ReaderDto readerDto = new ReaderDto(1L, "Petter", "Tuba", "45555");

        //when
        readerMapperService.updateReaderFromReaderDto(reader, readerDto);

        //then
        assertEquals(reader.getFirstName(), readerDto.firstName());
        assertEquals(reader.getLastName(), readerDto.lastName());
        assertEquals(reader.getCardNumber(), readerDto.cardNumber());
    }

    @Test
    void updateReaderFromReaderDto_shouldThrowException_whenReaderIsNull() {

        //given
        ReaderDto readerDto = new ReaderDto(1L, "Petter", "Tuba", "45555");

        //then
        assertThrows(
                NullPointerException.class, () -> readerMapperService.updateReaderFromReaderDto(null, readerDto)
        );
    }

    @Test
    void updateReaderFromReaderDto_shouldThrowException_whenReaderDtoIsNull() {

        //given
        Reader reader = new Reader("John", "Alibaba", "33333");

        //then
        assertThrows(
                NullPointerException.class, () -> readerMapperService.updateReaderFromReaderDto(reader, null)
        );
    }
}
