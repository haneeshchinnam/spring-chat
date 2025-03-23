package com.example.practice.repository;

import com.example.practice.modal.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    @Query(value = "SELECT * FROM borrow_record b WHERE b.user_id = :id", nativeQuery = true)
    List<BorrowRecord> getBorrowRecordsByUserId(@Param("id") Long id);
}
