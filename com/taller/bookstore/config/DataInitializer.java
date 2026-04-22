package com.taller.bookstore.config;

import com.taller.bookstore.entity.*;
import com.taller.bookstore.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.HashSet;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedData(UserRepository userRepository,
                               AuthorRepository authorRepository,
                               CategoryRepository categoryRepository,
                               BookRepository bookRepository,
                               PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByEmail("admin@bookstore.com")) {
                userRepository.save(User.builder()
                        .fullName("Admin Bookstore")
                        .email("admin@bookstore.com")
                        .password(passwordEncoder.encode("Admin123*"))
                        .role(Role.ROLE_ADMIN)
                        .build());
            }

            if (!userRepository.existsByEmail("user@bookstore.com")) {
                userRepository.save(User.builder()
                        .fullName("Usuario Demo")
                        .email("user@bookstore.com")
                        .password(passwordEncoder.encode("User1234*"))
                        .role(Role.ROLE_USER)
                        .build());
            }

            if (authorRepository.count() == 0 && categoryRepository.count() == 0 && bookRepository.count() == 0) {
                Author author1 = authorRepository.save(Author.builder()
                        .name("Gabriel García Márquez")
                        .biography("Autor colombiano y referente del realismo mágico.")
                        .email("ggm@demo.com")
                        .phone("3000000001")
                        .build());

                Author author2 = authorRepository.save(Author.builder()
                        .name("George Orwell")
                        .biography("Autor británico reconocido por sus novelas políticas y distópicas.")
                        .email("orwell@demo.com")
                        .phone("3000000002")
                        .build());

                Category fiction = categoryRepository.save(Category.builder().name("Ficción").description("Narrativa general").build());
                Category classic = categoryRepository.save(Category.builder().name("Clásico").description("Obras clásicas").build());
                Category dystopia = categoryRepository.save(Category.builder().name("Distopía").description("Ficción distópica").build());

                bookRepository.save(Book.builder()
                        .title("Cien años de soledad")
                        .isbn("9780307474728")
                        .price(new BigDecimal("49.90"))
                        .stock(25)
                        .description("Novela emblemática de la literatura latinoamericana.")
                        .author(author1)
                        .categories(new HashSet<>(java.util.Set.of(fiction, classic)))
                        .build());

                bookRepository.save(Book.builder()
                        .title("1984")
                        .isbn("9780451524935")
                        .price(new BigDecimal("39.90"))
                        .stock(30)
                        .description("Novela distópica sobre vigilancia y poder.")
                        .author(author2)
                        .categories(new HashSet<>(java.util.Set.of(classic, dystopia)))
                        .build());
            }
        };
    }
}
