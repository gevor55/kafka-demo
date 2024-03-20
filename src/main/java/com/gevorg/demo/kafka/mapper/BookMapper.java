package com.gevorg.demo.kafka.mapper;

import com.gevorg.demo.kafka.dto.BookDto;
import com.gevorg.demo.kafka.entity.Book;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class BookMapper {

    public Book dtoToEntity(BookDto dto) {
        return Book.builder()
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .build();
    }

    public BookDto entityToDto(Book book) {
        return BookDto.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
