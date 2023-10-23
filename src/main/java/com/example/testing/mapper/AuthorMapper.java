package com.example.testing.mapper;

import com.example.testing.dto.AuthorDto;
import com.example.testing.model.Author;
import org.springframework.lang.NonNull;

public interface AuthorMapper {

    AuthorDto mapAuthorToAuthorDto(@NonNull Author author);

    Author mapAuthorDtoToAuthor(@NonNull AuthorDto authorDto);

    void updateAuthorFromAuthorDto(@NonNull Author author, @NonNull AuthorDto authorDto);

}
