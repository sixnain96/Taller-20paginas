package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.AuthorRequest;
import com.taller.bookstore.dto.response.AuthorResponse;
import com.taller.bookstore.entity.Author;
import com.taller.bookstore.exception.custom.AuthorHasBooksException;
import com.taller.bookstore.exception.custom.ResourceNotFoundException;
import com.taller.bookstore.mapper.AuthorMapper;
import com.taller.bookstore.repository.AuthorRepository;
import com.taller.bookstore.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorServiceImpl(AuthorRepository authorRepository, AuthorMapper authorMapper) {
        this.authorRepository = authorRepository;
        this.authorMapper = authorMapper;
    }

    @Override
    @Transactional
    public AuthorResponse create(AuthorRequest request) {
        Author author = authorMapper.toEntity(request);
        return authorMapper.toResponse(authorRepository.save(author));
    }

    @Override
    @Transactional
    public AuthorResponse update(Long id, AuthorRequest request) {
        Author author = findEntity(id);
        authorMapper.updateEntity(author, request);
        return authorMapper.toResponse(authorRepository.save(author));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Author author = findEntity(id);
        if (!author.getBooks().isEmpty()) {
            throw new AuthorHasBooksException("Author with id " + id + " has associated books and cannot be deleted");
        }
        authorRepository.delete(author);
    }

    @Override
    public AuthorResponse getById(Long id) {
        return authorMapper.toResponse(findEntity(id));
    }

    @Override
    public List<AuthorResponse> getAll() {
        return authorRepository.findAll().stream().map(authorMapper::toResponse).toList();
    }

    private Author findEntity(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author with id " + id + " not found"));
    }
}
