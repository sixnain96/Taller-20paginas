package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.AuthorRequest;
import com.taller.bookstore.dto.response.AuthorResponse;

import java.util.List;

public interface AuthorService {
    AuthorResponse create(AuthorRequest request);
    AuthorResponse update(Long id, AuthorRequest request);
    void delete(Long id);
    AuthorResponse getById(Long id);
    List<AuthorResponse> getAll();
}
