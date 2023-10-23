package com.example.testing.mapper.internal;

import com.example.testing.dto.AuthorDto;
import com.example.testing.mapper.AuthorMapper;
import com.example.testing.model.Author;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class AuthorMapperService implements AuthorMapper {

    @Override
    public AuthorDto mapAuthorToAuthorDto(@NonNull Author author) {
        return new AuthorDto(author.getId(), author.getFirstName(), author.getLastName());
    }

    @Override
    public Author mapAuthorDtoToAuthor(@NonNull AuthorDto authorDto) {
        return new Author(authorDto.firstName(), authorDto.lastName());
    }

    @Override
    public void updateAuthorFromAuthorDto(@NonNull Author author, @NonNull AuthorDto authorDto) {
        author.setFirstName(authorDto.firstName());
        author.setLastName(authorDto.lastName());
    }

}
