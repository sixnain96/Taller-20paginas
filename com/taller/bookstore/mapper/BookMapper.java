package com.taller.bookstore.mapper;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.entity.Book;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final CategoryMapper categoryMapper;

    public BookMapper(AuthorMapper authorMapper, CategoryMapper categoryMapper) {
        this.authorMapper = authorMapper;
        this.categoryMapper = categoryMapper;
    }

    public Book toEntity(BookRequest request) {
        return Book.builder()
                .title(request.getTitle())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .stock(request.getStock())
                .description(request.getDescription())
                .build();
    }

    public void updateEntity(Book book, BookRequest request) {
        book.setTitle(request.getTitle());
        book.setIsbn(request.getIsbn());
        book.setPrice(request.getPrice());
        book.setStock(request.getStock());
        book.setDescription(request.getDescription());
    }

    public BookResponse toResponse(Book book) {
        return BookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .stock(book.getStock())
                .description(book.getDescription())
                .author(authorMapper.toResponse(book.getAuthor()))
                .categories(book.getCategories().stream()
                        .map(categoryMapper::toResponse)
                        .collect(Collectors.toList()))
                .build();
    }
}
