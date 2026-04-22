package com.taller.bookstore.service.impl;

import com.taller.bookstore.dto.request.CategoryRequest;
import com.taller.bookstore.dto.response.CategoryResponse;
import com.taller.bookstore.entity.Category;
import com.taller.bookstore.exception.custom.DuplicateResourceException;
import com.taller.bookstore.exception.custom.ResourceNotFoundException;
import com.taller.bookstore.mapper.CategoryMapper;
import com.taller.bookstore.repository.CategoryRepository;
import com.taller.bookstore.service.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    @Transactional
    public CategoryResponse create(CategoryRequest request) {
        validateDuplicateName(request.getName(), null);
        Category category = categoryMapper.toEntity(request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse update(Long id, CategoryRequest request) {
        Category category = findEntity(id);
        validateDuplicateName(request.getName(), id);
        categoryMapper.updateEntity(category, request);
        return categoryMapper.toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Category category = findEntity(id);
        category.getBooks().forEach(book -> book.getCategories().remove(category));
        categoryRepository.delete(category);
    }

    @Override
    public CategoryResponse getById(Long id) {
        return categoryMapper.toResponse(findEntity(id));
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAll().stream().map(categoryMapper::toResponse).toList();
    }

    private Category findEntity(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category with id " + id + " not found"));
    }

    private void validateDuplicateName(String name, Long excludeId) {
        categoryRepository.findByNameIgnoreCase(name).ifPresent(existing -> {
            if (excludeId == null || !existing.getId().equals(excludeId)) {
                throw new DuplicateResourceException("Category with name " + name + " already exists");
            }
        });
    }
}
