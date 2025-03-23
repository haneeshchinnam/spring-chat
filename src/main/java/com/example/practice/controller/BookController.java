package com.example.practice.controller;

import com.example.practice.payload.BookDto;
import com.example.practice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("book")
public class BookController {

    @Autowired
    BookService bookService;

    @GetMapping("/")
    ResponseEntity<List<BookDto>> getAllBooks() {
        return ResponseEntity.ok(bookService.getBooks());
    }

    @GetMapping("/{bookId}")
    ResponseEntity<BookDto> getBook(@PathVariable("bookId") Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PostMapping("/")
    ResponseEntity<String> createBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookService.createBook(bookDto), HttpStatus.CREATED);
    }
}
