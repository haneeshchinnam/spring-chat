package com.example.practice.repository;

import com.example.practice.modal.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    @Query("SELECT br FROM BorrowRecord br JOIN FETCH br.user WHERE br.user.id = :id")
    List<BorrowRecord> getBorrowRecordsByUserId(@Param("id") Long id);

    @Query(value = "SELECT * FROM borrow_record", nativeQuery = true)
    List<BorrowRecord> getBorrowRecordList();
}
