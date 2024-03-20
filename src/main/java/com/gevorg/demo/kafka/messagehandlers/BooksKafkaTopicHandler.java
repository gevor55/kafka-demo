package com.gevorg.demo.kafka.messagehandlers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gevorg.demo.kafka.dto.BookDto;
import com.gevorg.demo.kafka.mapper.BookMapper;
import com.gevorg.demo.kafka.service.BookService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
@Component
@Slf4j
public class BooksKafkaTopicHandler {

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "${spring.kafka.topic.book.name}",
            containerFactory = "bookDtoConcurrentKafkaListenerContainerFactory")
    @Transactional
    public void processBookMessage(String message) throws JsonProcessingException {

        //Assuming that message contains only one book
        BookDto messageBook = objectMapper.readValue(message, BookDto.class);

        if (messageBook == null) {
            System.err.println("Book is null");
            return;
        }
        Optional<BookDto> optionalStoredBookDto = bookService.findByTitleAndAuthor(messageBook.getTitle(),
                messageBook.getAuthor());

        if (optionalStoredBookDto.isPresent()) {
            BookDto bookDto = optionalStoredBookDto.get();
            log.warn("Book with title {} and author {} already has been stored", bookDto.getTitle(), bookDto.getAuthor());
            return;
        }
        bookService.save(messageBook);
        log.info("Book with title {} and author {} has been saved", messageBook.getTitle(), messageBook.getAuthor());
    }
}