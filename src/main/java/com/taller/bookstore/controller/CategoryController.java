package com.taller.bookstore.controller;

import com.taller.bookstore.config.ApiResponseBuilder;
import com.taller.bookstore.dto.request.CategoryRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.dto.response.CategoryResponse;
import com.taller.bookstore.service.BookService;
import com.taller.bookstore.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final BookService bookService;
    private final ApiResponseBuilder responseBuilder;

    public CategoryController(CategoryService categoryService, BookService bookService, ApiResponseBuilder responseBuilder) {
        this.categoryService = categoryService;
        this.bookService = bookService;
        this.responseBuilder = responseBuilder;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAll() {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Categorías obtenidas correctamente", categoryService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Categoría obtenida correctamente", categoryService.getById(id)));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksByCategory(@PathVariable Long id) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Libros de la categoría obtenidos correctamente", bookService.getByCategory(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBuilder.success(HttpStatus.CREATED, "Categoría creada correctamente", categoryService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(@PathVariable Long id, @Valid @RequestBody CategoryRequest request) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Categoría actualizada correctamente", categoryService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Categoría eliminada correctamente", null));
    }
}
