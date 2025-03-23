package com.example.practice.service;

import com.example.practice.payload.BorrowRecordDto;

import java.util.List;

public interface BorrowRecordService {
    List<BorrowRecordDto> getBorrowRecordsByUser(Long id);

    BorrowRecordDto addBorrowRecordForUser(BorrowRecordDto borrowRecordDto);
}
