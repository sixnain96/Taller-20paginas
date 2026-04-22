package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.dto.response.PageResponse;
import com.taller.bookstore.entity.Author;
import com.taller.bookstore.entity.Book;
import com.taller.bookstore.entity.Category;
import com.taller.bookstore.exception.custom.DuplicateResourceException;
import com.taller.bookstore.exception.custom.ResourceNotFoundException;
import com.taller.bookstore.mapper.BookMapper;
import com.taller.bookstore.repository.AuthorRepository;
import com.taller.bookstore.repository.BookRepository;
import com.taller.bookstore.repository.CategoryRepository;
import com.taller.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final BookMapper bookMapper;

    public BookServiceImpl(BookRepository bookRepository,
                           AuthorRepository authorRepository,
                           CategoryRepository categoryRepository,
                           BookMapper bookMapper) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
        this.bookMapper = bookMapper;
    }

    @Override
    @Transactional
    public BookResponse create(BookRequest request) {
        if (bookRepository.existsByIsbn(request.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN " + request.getIsbn() + " already exists");
        }
        Book book = bookMapper.toEntity(request);
        applyRelations(book, request);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    @Transactional
    public BookResponse update(Long id, BookRequest request) {
        Book book = findEntity(id);
        if (bookRepository.existsByIsbnAndIdNot(request.getIsbn(), id)) {
            throw new DuplicateResourceException("Book with ISBN " + request.getIsbn() + " already exists");
        }
        bookMapper.updateEntity(book, request);
        applyRelations(book, request);
        return bookMapper.toResponse(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Book book = findEntity(id);
        bookRepository.delete(book);
    }

    @Override
    public BookResponse getById(Long id) {
        return bookMapper.toResponse(findEntity(id));
    }

    @Override
    public PageResponse<BookResponse> getAll(Long authorId, Long categoryId, int page, int size) {
        Page<Book> result;
        PageRequest pageable = PageRequest.of(page, size);

        if (authorId != null && categoryId != null) {
            result = bookRepository.findByAuthorIdAndCategoriesId(authorId, categoryId, pageable);
        } else if (authorId != null) {
            result = bookRepository.findByAuthorId(authorId, pageable);
        } else if (categoryId != null) {
            result = bookRepository.findByCategoriesId(categoryId, pageable);
        } else {
            result = bookRepository.findAll(pageable);
        }

        return PageResponse.<BookResponse>builder()
                .content(result.getContent().stream().map(bookMapper::toResponse).toList())
                .page(result.getNumber())
                .size(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .first(result.isFirst())
                .last(result.isLast())
                .build();
    }

    @Override
    public List<BookResponse> getByAuthor(Long authorId) {
        authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + authorId + " not found"));
        return bookRepository.findAllByAuthorId(authorId).stream().map(bookMapper::toResponse).toList();
    }

    @Override
    public List<BookResponse> getByCategory(Long categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + categoryId + " not found"));
        return bookRepository.findAllByCategoriesId(categoryId).stream().map(bookMapper::toResponse).toList();
    }

    private Book findEntity(Long id) {
        return bookRepository.findWithDetailsById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book with id " + id + " not found"));
    }

    private void applyRelations(Book book, BookRequest request) {
        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + request.getAuthorId() + " not found"));

        Set<Category> categories = new HashSet<>(categoryRepository.findAllById(request.getCategoryIds()));
        if (categories.size() != request.getCategoryIds().size()) {
            throw new ResourceNotFoundException("One or more categories were not found");
        }

        book.setAuthor(author);
        book.setCategories(categories);
    }
}
