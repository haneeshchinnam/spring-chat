package com.example.practice.service;

import com.example.practice.payload.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getBooks();

    BookDto getBook(Long id);

    String createBook(BookDto bookDto);
}
