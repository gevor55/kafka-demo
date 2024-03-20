package com.gevorg.demo.kafka.service;

import com.gevorg.demo.kafka.dto.BookDto;
import com.gevorg.demo.kafka.entity.Book;
import com.gevorg.demo.kafka.mapper.BookMapper;
import com.gevorg.demo.kafka.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceimpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(bookMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookDto save(BookDto bookDto) {
        Book newBook = bookRepository.save(bookMapper.dtoToEntity(bookDto));

        return bookMapper.entityToDto(newBook);
    }

    @Override
    public Optional<BookDto> findByTitleAndAuthor(String title, String author) {

        return bookRepository.findByTitleAndAuthor(title, author)
                .map(bookMapper::entityToDto);
    }
}
