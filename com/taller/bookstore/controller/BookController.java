package com.taller.bookstore.controller;

import com.taller.bookstore.config.ApiResponseBuilder;
import com.taller.bookstore.dto.request.BookRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.dto.response.PageResponse;
import com.taller.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final ApiResponseBuilder responseBuilder;

    public BookController(BookService bookService, ApiResponseBuilder responseBuilder) {
        this.bookService = bookService;
        this.responseBuilder = responseBuilder;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<BookResponse>>> getAll(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Libros obtenidos correctamente", bookService.getAll(authorId, categoryId, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Libro obtenido correctamente", bookService.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<BookResponse>> create(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBuilder.success(HttpStatus.CREATED, "Libro creado correctamente", bookService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> update(@PathVariable Long id, @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Libro actualizado correctamente", bookService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        bookService.delete(id);
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Libro eliminado correctamente", null));
    }
}
