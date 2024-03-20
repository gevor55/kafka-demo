package com.gevorg.demo.kafka.service;

import com.gevorg.demo.kafka.dto.BookDto;
import com.gevorg.demo.kafka.entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> findAll();

    BookDto save(BookDto book);

    Optional<BookDto> findByTitleAndAuthor(String title, String author);
}
