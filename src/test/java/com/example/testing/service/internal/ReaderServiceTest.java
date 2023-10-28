package com.example.testing.service.internal;

import com.example.testing.dto.ReaderDto;
import com.example.testing.mapper.ReaderMapper;
import com.example.testing.model.Reader;
import com.example.testing.repository.ReaderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReaderServiceTest {

    @Mock
    private ReaderRepository readerRepository;
    @Mock
    private ReaderMapper readerMapper;

    @InjectMocks
    private ReaderService readerService;

    @Test
    void getReaders_shouldReturnReaders_whenReadersExist() {

        //given
        List<Reader> readerList = Arrays.asList(
                new Reader("Janusz", "Kowalski", "12345"),
                new Reader("Jan", "Tamer", "32345")
        );
        when(readerRepository.findAll()).thenReturn(readerList);
        when(readerMapper.mapReaderToReaderDto(any(Reader.class)))
                .thenAnswer(invocation -> {
                    Reader reader = invocation.getArgument(0);
                    return new ReaderDto(
                            reader.getId(),
                            reader.getFirstName(),
                            reader.getLastName(),
                            reader.getCardNumber()
                    );
                });

        //when
        Collection<ReaderDto> readerDtoCollection = readerService.getReaders();

        //then
        List<ReaderDto> readerDtos = readerDtoCollection.stream().toList();

        assertEquals(2, readerDtoCollection.size());
        assertEquals(readerList.get(0).getFirstName(), readerDtos.get(0).firstName());
        assertEquals(readerList.get(0).getLastName(), readerDtos.get(0).lastName());
        assertEquals(readerList.get(0).getCardNumber(), readerDtos.get(0).cardNumber());
        assertEquals(readerList.get(1).getFirstName(), readerDtos.get(1).firstName());
        assertEquals(readerList.get(1).getLastName(), readerDtos.get(1).lastName());
        assertEquals(readerList.get(1).getCardNumber(), readerDtos.get(1).cardNumber());
    }

    @Test
    void getReader_shouldReturnReader_whenReaderExists() {

        //given
        Reader reader = new Reader("Han", "Matejko", "12445");
        when(readerRepository.findById(1L)).thenReturn(Optional.of(reader));
        when(readerMapper.mapReaderToReaderDto(reader)).thenReturn(new ReaderDto(
                1L,
                "Han",
                "Matejko",
                "12445"
        ));

        //when
        ReaderDto readerDto = readerService.getReader(1L);

        //then
        assertEquals(1L, readerDto.id());
        assertEquals("Han", readerDto.firstName());
        assertEquals("Matejko", readerDto.lastName());
        assertEquals("12445", readerDto.cardNumber());
        verify(readerRepository).findById(1L);
        verify(readerMapper).mapReaderToReaderDto(reader);
    }

    @Test
    void getReader_shouldThrowException_whenIdIsNull() {
        assertThrows(NullPointerException.class, () -> readerService.getReader(null));
    }

    @Test
    void getReader_shouldThrowException_whenReaderDoesNotExists() {
        assertThrows(NoSuchElementException.class, () -> readerService.getReader(1L));
    }

    @Test
    void createReader_shouldCorrectlyCreateReader_whenDataIsNotNull() {

        //given
        ReaderDto readerDto = new ReaderDto(1L, "Tom", "Solo", "12365");

        //when
        readerService.createReader(readerDto);

        //then
        assertThat(readerRepository.findById(1L)).isNotNull();
        verify(readerMapper).mapReaderDtoToReader(readerDto);
        verify(readerRepository).save(readerMapper.mapReaderDtoToReader(readerDto));
    }

    @Test
    void createReader_shouldThrowException_whenReaderDtoIsNull() {
        assertThrows(NullPointerException.class, () -> readerService.createReader(null));
    }

    @Test
    void updateReader_shouldCorrectlyUpdateReader_whenDataIsNotNull() {

        //given
        Long id = 1L;
        ReaderDto readerDto = new ReaderDto(id, "Paul", "Kevin", "99995");
        Reader reader = new Reader("Adam", "Smith", "55555");
        when(readerRepository.findById(id)).thenReturn(Optional.of(reader));

        //when
        readerService.updateReader(id, readerDto);

        //then
        verify(readerRepository).findById(id);
        verify(readerMapper).updateReaderFromReaderDto(reader, readerDto);
        verify(readerRepository).save(reader);
    }

    @Test
    void updateReader_shouldThrowException_whenReaderDtoIsNull() {
        //then
        assertThrows(NullPointerException.class, () -> readerService.updateReader(1L, null));
    }

    @Test
    void updateAuthor_shouldThrowException_whenIdIsNull() {
        //given
        ReaderDto readerDto = new ReaderDto(1L, "UpdatedFirstName", "UpdatedLastName", "12675");

        // then
        assertThrows(NullPointerException.class, () -> readerService.updateReader(null, readerDto));
    }

    @Test
    void shouldCorrectly_deleteAuthor() {
        //given
        Long id = 1L;
        Reader reader = new Reader("Adam", "Smith", "11115");
        readerRepository.save(reader);

        //when
        readerService.deleteReader(id);
        Optional<Reader> actualReader = readerRepository.findById(id);

        //then
        assertThat(actualReader).isEmpty();
        verify(readerRepository).deleteById(id);
    }

    @Test
    void deleteAuthor_shouldThrowException_whenIdIsNull() {
        assertThrows(NullPointerException.class, () -> readerService.deleteReader(null));
    }
}
