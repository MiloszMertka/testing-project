package com.example.testing.service;

import com.example.testing.dto.AuthorDto;

import java.util.Collection;

public interface AuthorUseCases {

    Collection<AuthorDto> getAuthors();

    AuthorDto getAuthor(Long id);

    void createAuthor(AuthorDto authorDto);

    void updateAuthor(Long id, AuthorDto authorDto);

    void deleteAuthor(Long id);

}
