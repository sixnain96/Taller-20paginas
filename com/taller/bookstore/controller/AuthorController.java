package com.taller.bookstore.controller;

import com.taller.bookstore.config.ApiResponseBuilder;
import com.taller.bookstore.dto.request.AuthorRequest;
import com.taller.bookstore.dto.response.ApiResponse;
import com.taller.bookstore.dto.response.AuthorResponse;
import com.taller.bookstore.dto.response.BookResponse;
import com.taller.bookstore.service.AuthorService;
import com.taller.bookstore.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final BookService bookService;
    private final ApiResponseBuilder responseBuilder;

    public AuthorController(AuthorService authorService, BookService bookService, ApiResponseBuilder responseBuilder) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.responseBuilder = responseBuilder;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AuthorResponse>>> getAll() {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Autores obtenidos correctamente", authorService.getAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Autor obtenido correctamente", authorService.getById(id)));
    }

    @GetMapping("/{id}/books")
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooksByAuthor(@PathVariable Long id) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Libros del autor obtenidos correctamente", bookService.getByAuthor(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<AuthorResponse>> create(@Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(responseBuilder.success(HttpStatus.CREATED, "Autor creado correctamente", authorService.create(request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AuthorResponse>> update(@PathVariable Long id, @Valid @RequestBody AuthorRequest request) {
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Autor actualizado correctamente", authorService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        authorService.delete(id);
        return ResponseEntity.ok(responseBuilder.success(HttpStatus.OK, "Autor eliminado correctamente", null));
    }
}
