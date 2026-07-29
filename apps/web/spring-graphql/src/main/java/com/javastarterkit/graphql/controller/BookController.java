package com.javastarterkit.graphql.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.javastarterkit.graphql.entity.Book;

@Controller
public class BookController {

    private final List<Book> books = new ArrayList<>();
    private long nextId = 1;

    public BookController() {
        // Initialize with sample data
        books.add(new Book(nextId++, "Spring Boot in Action", "Craig Walls"));
        books.add(new Book(nextId++, "Spring Framework", "Rod Johnson"));
    }

    @QueryMapping
    public List<Book> books() {
        return new ArrayList<>(books);
    }

    @QueryMapping
    public Book book(@Argument Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @MutationMapping
    public Book createBook(@Argument String title, @Argument String author) {
        Book book = new Book(nextId++, title, author);
        books.add(book);
        return book;
    }

    @MutationMapping
    public Book updateBook(@Argument Long id, @Argument String title, @Argument String author) {
        Book book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
        
        if (book != null) {
            if (title != null) book.setTitle(title);
            if (author != null) book.setAuthor(author);
        }
        return book;
    }

    @MutationMapping
    public boolean deleteBook(@Argument Long id) {
        return books.removeIf(book -> book.getId().equals(id));
    }
}