package com.example.testing.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthorTest {

    @Test
    void shouldCorrectly_createAuthor() {
        //given
        String firstName = "Adam";
        String lastName = "Smith";

        //when
        Author author = new Author(firstName, lastName);

        //then
        assertNotNull(author);
        assertNull(author.getId());
        assertEquals(firstName, author.getFirstName());
        assertEquals(lastName, author.getLastName());
    }
}