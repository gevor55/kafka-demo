package com.gevorg.demo.kafka.controller;

import com.gevorg.demo.kafka.dto.BookDto;
import com.gevorg.demo.kafka.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DemoKafkaController {

    private final BookService bookService;

    @GetMapping()
    public ResponseEntity<List<BookDto>> findAll() {
        return ResponseEntity.ok(bookService.findAll());
    }
}
