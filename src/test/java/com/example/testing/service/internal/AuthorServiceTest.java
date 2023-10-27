package com.example.testing.service.internal;

import com.example.testing.dto.AuthorDto;
import com.example.testing.mapper.AuthorMapper;
import com.example.testing.model.Author;
import com.example.testing.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;

    @Test
    void shouldCorrectly_getAuthors() {
        //given
        List<Author> authorsList = Arrays.asList(new Author("Adam", "Smith"), new Author("Paul", "Henks"));
        when(authorRepository.findAll()).thenReturn(authorsList);
        when(authorMapper.mapAuthorToAuthorDto(any(Author.class)))
                .thenAnswer(invocation -> {
                    Author author = invocation.getArgument(0);
                    return new AuthorDto(author.getId(), author.getFirstName(), author.getLastName());
                });

        //when
        Collection<AuthorDto> authorDtoCollection = authorService.getAuthors();

        //then
        assertEquals(2, authorDtoCollection.size()); // Assuming there are 2 authors in the list
        List<AuthorDto> authorDtoList = new ArrayList<>(authorDtoCollection);

        assertEquals("Adam", authorDtoList.get(0).firstName());
        assertEquals("Smith", authorDtoList.get(0).lastName());

        assertEquals("Paul", authorDtoList.get(1).firstName());
        assertEquals("Henks", authorDtoList.get(1).lastName());

        verify(authorRepository).findAll();
        verify(authorMapper, times(2)).mapAuthorToAuthorDto(any(Author.class));

    }

    @Test
    void getAuthor_shouldReturnAuthor_whenAuthorDoesExist() {
        //given
        Long id = 1L;
        String firstName = "Adam";
        String lastName = "Smith";
        Author author = new Author(firstName, lastName);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorMapper.mapAuthorToAuthorDto(author)).thenReturn(new AuthorDto(id, firstName, lastName));

        //when
        AuthorDto authorDto = authorService.getAuthor(id);

        //then
        assertEquals(1L, authorDto.id());
        assertEquals(firstName, authorDto.firstName());
        assertEquals(lastName, authorDto.lastName());
        verify(authorRepository).findById(id);
        verify(authorMapper).mapAuthorToAuthorDto(author);
    }

    @Test
    void getAuthor_shouldThrowException_whenIdIsNull() {
        assertThrows(NullPointerException.class, () -> authorService.getAuthor(null));
    }

    @Test
    void getAuthor_shouldThrowException_whenAuthorDoesNotExists() {
        assertThrows(NoSuchElementException.class, () -> authorService.getAuthor(1L));
    }

    @Test
    void shouldCorrectly_createAuthor() {
        //given
        Long id = 1L;
        AuthorDto authorDto = new AuthorDto(1L, "Adam", "Smith");

        //when
        authorService.createAuthor(authorDto);

        //then
        assertThat(authorRepository.findById(id)).isNotNull();
        verify(authorMapper).mapAuthorDtoToAuthor(authorDto);
        verify(authorRepository).save(authorMapper.mapAuthorDtoToAuthor(authorDto));
    }

    @Test
    void createAuthor_shouldThrowException_whenAuthorDtoIsNull() {
        assertThrows(NullPointerException.class, () -> authorService.createAuthor(null));
    }

    @Test
    void shouldCorrectly_updateAuthor() {
        //given
        Long id = 1L;
        AuthorDto authorDto = new AuthorDto(1L, "Paul", "Kevin");
        Author author = new Author("Adam", "Smith");
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        //when
        authorService.updateAuthor(id, authorDto);

        //then
        verify(authorRepository).findById(id);
        verify(authorMapper).updateAuthorFromAuthorDto(author, authorDto);
        verify(authorRepository).save(author);
    }

    @Test
    void updateAuthor_shouldThrowException_whenAuthorDtoIsNull() {
        //then
        assertThrows(NullPointerException.class, () -> authorService.updateAuthor(1L, null));
    }

    @Test
    void updateAuthor_shouldThrowException_whenIdIsNull() {
        //given
        AuthorDto authorDto = new AuthorDto(1L, "UpdatedFirstName", "UpdatedLastName");

        // then
        assertThrows(NullPointerException.class, () -> authorService.updateAuthor(null, authorDto));
    }

    @Test
    void shouldCorrectly_deleteAuthor() {
        //given
        Long id = 1L;
        Author author = new Author("Adam", "Smith");
        authorRepository.save(author);

        //when
        authorService.deleteAuthor(id);
        Optional<Author> actualAuthor = authorRepository.findById(id);

        //then
        assertThat(actualAuthor).isEmpty();
        verify(authorRepository).deleteById(id);
    }

    @Test
    void deleteAuthor_shouldThrowException_whenIdIsNull() {
        assertThrows(NullPointerException.class, () -> authorService.deleteAuthor(null));
    }
}