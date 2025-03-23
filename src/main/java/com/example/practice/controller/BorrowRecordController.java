package com.example.practice.controller;

import com.example.practice.payload.BorrowRecordDto;
import com.example.practice.service.BorrowRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("borrow-record")
public class BorrowRecordController {
    @Autowired
    BorrowRecordService borrowRecordService;

    @GetMapping("/{id}")
    ResponseEntity<List<BorrowRecordDto>> getBorrowRecordsByUserId(@PathVariable("id") Long id) {
        return ResponseEntity.ok(borrowRecordService.getBorrowRecordsByUser(id));
    }

    @PostMapping("/")
    ResponseEntity<BorrowRecordDto> addBorrowRecordForUser(@RequestBody BorrowRecordDto borrowRecordDto) {
        return new ResponseEntity<>(borrowRecordService.addBorrowRecordForUser(borrowRecordDto), HttpStatus.CREATED);
    }
}
