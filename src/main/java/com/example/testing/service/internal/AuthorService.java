package com.example.testing.service.internal;

import com.example.testing.dto.AuthorDto;
import com.example.testing.mapper.AuthorMapper;
import com.example.testing.repository.AuthorRepository;
import com.example.testing.service.AuthorUseCases;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorService implements AuthorUseCases {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public Collection<AuthorDto> getAuthors() {
        final var authors = authorRepository.findAll();
        return authors.stream()
                .map(authorMapper::mapAuthorToAuthorDto)
                .toList();
    }

    @Override
    public AuthorDto getAuthor(@NonNull Long id) {
        final var author = authorRepository.findById(id).orElseThrow();
        return authorMapper.mapAuthorToAuthorDto(author);
    }

    @Override
    public void createAuthor(@NonNull @Valid AuthorDto authorDto) {
        final var author = authorMapper.mapAuthorDtoToAuthor(authorDto);
        authorRepository.save(author);
    }

    @Override
    public void updateAuthor(@NonNull Long id, @NonNull @Valid AuthorDto authorDto) {
        final var author = authorRepository.findById(id).orElseThrow();
        authorMapper.updateAuthorFromAuthorDto(author, authorDto);
        authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(@NonNull Long id) {
        authorRepository.deleteById(id);
    }

}
