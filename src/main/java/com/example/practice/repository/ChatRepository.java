package com.example.practice.repository;

import com.example.practice.modal.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Message, Long> {

    @Query(value = "SELECT * FROM messages m WHERE m.sender_id = :senderId AND m.receiver_id = :receiverId or m.sender_id = :receiverId AND m.receiver_id = :senderId",
            nativeQuery = true)
    List<Message> getMessagesByReceiverSenderId(@Param("senderId") Long senderId,
                                                @Param("receiverId") Long receiverId);
}
