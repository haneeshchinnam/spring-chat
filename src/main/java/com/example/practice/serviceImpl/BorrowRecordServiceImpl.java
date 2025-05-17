package com.example.practice.serviceImpl;

import com.example.practice.exception.UserNotFound;
import com.example.practice.modal.Book;
import com.example.practice.modal.BorrowRecord;
import com.example.practice.modal.User;
import com.example.practice.payload.BorrowRecordDto;
import com.example.practice.repository.BookRepository;
import com.example.practice.repository.BorrowRecordRepository;
import com.example.practice.repository.UserRepository;
import com.example.practice.service.BookService;
import com.example.practice.service.BorrowRecordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowRecordServiceImpl implements BorrowRecordService {

    @Autowired
    BorrowRecordRepository borrowRecordRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;


    @Override
    @Transactional
    public List<BorrowRecordDto> getBorrowRecordsByUser(Long id) {
        try {
            List<BorrowRecord> borrowRecords = borrowRecordRepository.getBorrowRecordsByUserId(id);
//            System.out.println(borrowRecords);
            return borrowRecords.stream().map((item) -> {
                User user = item.getUser();
                return borrowRecordEntityToDto(BorrowRecord.builder().borrowDate(item.getBorrowDate()).returnDate(item.getReturnDate()).user(user).book(item.getBook()).id(item.getId()).build());
            }).toList();
        } catch (Exception e) {
            throw new UserNotFound(e.getMessage());
        }
    }

    @Override
    public BorrowRecordDto addBorrowRecordForUser(BorrowRecordDto borrowRecordDto) {
        try {
            System.out.println(borrowRecordDto.getUserId()+" "+borrowRecordDto.getBookId());
            BorrowRecord borrowRecord = borrowRecordDtoToEntity(borrowRecordDto);
            BorrowRecord savedRecord = borrowRecordRepository.save(borrowRecord);
            return borrowRecordEntityToDto(savedRecord);
        } catch (Exception e) {
            throw new UserNotFound(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<BorrowRecordDto> getBorrowRecords() {
        return borrowRecordRepository.getBorrowRecordList().stream().map(this::borrowRecordEntityToDto).toList();
    }

    public BorrowRecordDto borrowRecordEntityToDto(BorrowRecord borrowRecord) {
        BorrowRecordDto borrowRecordDto = new BorrowRecordDto();
        borrowRecordDto.setId(borrowRecord.getId());
        borrowRecordDto.setBorrowDate(borrowRecord.getBorrowDate());
        borrowRecordDto.setReturnDate(borrowRecord.getReturnDate());
        borrowRecordDto.setBookId(borrowRecord.getBook().getId());
        borrowRecordDto.setUserId(borrowRecord.getUser().getId());
        return borrowRecordDto;
    }

    public BorrowRecord borrowRecordDtoToEntity(BorrowRecordDto borrowRecordDto) {
        BorrowRecord borrowRecord = new BorrowRecord();
        borrowRecord.setBorrowDate(borrowRecordDto.getBorrowDate());
        borrowRecord.setReturnDate(borrowRecordDto.getReturnDate());
        User user = userRepository.findUserById(borrowRecordDto.getUserId()).orElseThrow(() -> new UserNotFound(String.format("User Id %d user does not exist", borrowRecordDto.getUserId())));
        Book book = bookRepository.findById(borrowRecordDto.getBookId()).orElseThrow(() -> new UserNotFound(String.format("Book Id %d book does not exist", borrowRecordDto.getBookId())));
        borrowRecord.setUser(user);
        borrowRecord.setBook(book);
        return borrowRecord;
    }
}
