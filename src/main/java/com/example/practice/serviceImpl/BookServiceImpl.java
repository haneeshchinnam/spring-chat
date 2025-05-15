package com.example.practice.serviceImpl;

import com.example.practice.exception.UserNotFound;
import com.example.practice.modal.Book;
import com.example.practice.payload.BookDto;
import com.example.practice.repository.BookRepository;
import com.example.practice.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookRepository bookRepository;

    @Override
    public List<BookDto> getBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::bookEntityToDto).toList();
    }

    @Override
    public BookDto getBook(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if(book.isPresent()) {
            return bookEntityToDto(book.get());
        } else {
            throw new UserNotFound("Book not found");
        }
    }

    @Override
    public String createBook(BookDto bookDto) {
        Book book = bookDtoToEntity(bookDto);
        try {
            bookRepository.save(book);
            return "Book Created";
        } catch (Exception e) {
            throw new UserNotFound(e.getMessage());
        }
    }

    public BookDto bookEntityToDto(Book book) {
        BookDto bookDto = BookDto.builder().title(book.getTitle()).author(book.getAuthor()).build();
        bookDto.setAuthor(book.getAuthor());
        bookDto.setTitle(book.getTitle());
        bookDto.setId(book.getId());
        return bookDto;
    }

    public Book bookDtoToEntity(BookDto bookDto) {
        Book book = new Book();
        book.setAuthor(bookDto.getAuthor());
        book.setTitle(bookDto.getTitle());
        book.setBorrowRecords(List.of());
        return book;
    }
}
