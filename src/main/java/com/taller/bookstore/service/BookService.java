package com.taller.bookstore.service;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.dto.response.PageResponse;

import java.util.List;

public interface BookService {
    BookResponse create(BookRequest request);
    BookResponse update(Long id, BookRequest request);
    void delete(Long id);
    BookResponse getById(Long id);
    PageResponse<BookResponse> getAll(Long authorId, Long categoryId, int page, int size);
    List<BookResponse> getByAuthor(Long authorId);
    List<BookResponse> getByCategory(Long categoryId);
}
