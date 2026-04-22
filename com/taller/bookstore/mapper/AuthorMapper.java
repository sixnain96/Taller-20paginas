package com.taller.bookstore.mapper;

import com.taller.bookstore.dto.request.AuthorRequest;
import com.taller.bookstore.dto.response.AuthorResponse;
import com.taller.bookstore.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

    public Author toEntity(AuthorRequest request) {
        return Author.builder()
                .name(request.getName())
                .biography(request.getBiography())
                .email(request.getEmail())
                .phone(request.getPhone())
                .build();
    }

    public void updateEntity(Author author, AuthorRequest request) {
        author.setName(request.getName());
        author.setBiography(request.getBiography());
        author.setEmail(request.getEmail());
        author.setPhone(request.getPhone());
    }

    public AuthorResponse toResponse(Author author) {
        return AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .biography(author.getBiography())
                .email(author.getEmail())
                .phone(author.getPhone())
                .build();
    }
}
