package com.example.testing.mapper.internal;

import com.example.testing.dto.AuthorDto;
import com.example.testing.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthorMapperServiceTest {

    @Autowired
    private AuthorMapperService authorMapperService;

    @Test
    void shouldCorrectly_mapAuthorToAuthorDto_whenAuthorIsNotNull() {
        //given
        Author author = new Author("Adam", "Smith");

        //when
        AuthorDto authorDto = authorMapperService.mapAuthorToAuthorDto(author);

        //then
        assertEquals(author.getId(), authorDto.id());
        assertEquals(author.getFirstName(), authorDto.firstName());
        assertEquals(author.getLastName(), authorDto.lastName());
    }

    @Test
    void mapAuthorToAuthorDto_shouldThrowException_whenAuthorIsNull() {
        assertThrows(NullPointerException.class, () -> authorMapperService.mapAuthorToAuthorDto(null));
    }

    @Test
    void shouldCorrectly_mapAuthorDtoToAuthor_whenAuthorDtoIsNotNull() {
        //given
        AuthorDto authorDto = new AuthorDto(1L, "Adam", "Smith");

        //when
        Author author = authorMapperService.mapAuthorDtoToAuthor(authorDto);

        //then
        assertEquals(authorDto.firstName(), author.getFirstName());
        assertEquals(authorDto.lastName(), author.getLastName());
    }

    @Test
    void mapAuthorDtoToAuthor_shouldThrowException_whenAuthorDtoIsNull() {
        assertThrows(NullPointerException.class, () -> authorMapperService.mapAuthorDtoToAuthor(null));
    }

    @Test
    void shouldCorrectly_updateAuthorFromAuthorDto_WhenAuthorAndAuthorDtoIsNotNull() {
        //given
        Author author = new Author("Adam", "Smith");
        AuthorDto authorDto = new AuthorDto(1L, "Kevin", "Scott");

        //when
        authorMapperService.updateAuthorFromAuthorDto(author, authorDto);

        //then
        assertEquals(authorDto.firstName(), author.getFirstName());
        assertEquals(authorDto.lastName(), author.getLastName());
    }

    @Test
    void updateAuthorFromAuthorDto_shouldThrowException_whenAuthorDtoIsNull() {
        //given
        Author author = new Author("Adam", "Smith");

        //then
        assertThrows(NullPointerException.class, () -> authorMapperService
                .updateAuthorFromAuthorDto(author, null));
    }

    @Test
    void updateAuthorFromAuthorDto_shouldThrowException_whenAuthorIsNull() {
        //given
        AuthorDto authorDto = new AuthorDto(1L, "Adam", "Smith");

        //then
        assertThrows(NullPointerException.class, () -> authorMapperService
                .updateAuthorFromAuthorDto(null, authorDto));
    }

    @Test
    void updateAuthorFromAuthorDto_shouldThrowException_whenAuthorAndAuthorDtoAreNull() {
        assertThrows(NullPointerException.class, () -> authorMapperService
                .updateAuthorFromAuthorDto(null, null));
    }
}