package com.taller.bookstore.repository;

import com.taller.bookstore.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Override
    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByAuthorId(Long authorId, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByCategoriesId(Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    Page<Book> findByAuthorIdAndCategoriesId(Long authorId, Long categoryId, Pageable pageable);

    @EntityGraph(attributePaths = {"author", "categories"})
    List<Book> findAllByAuthorId(Long authorId);

    @EntityGraph(attributePaths = {"author", "categories"})
    List<Book> findAllByCategoriesId(Long categoryId);

    @EntityGraph(attributePaths = {"author", "categories"})
    @Query("select b from Book b where b.id = :id")
    Optional<Book> findWithDetailsById(Long id);

    boolean existsByIsbn(String isbn);
    boolean existsByIsbnAndIdNot(String isbn, Long id);
}
