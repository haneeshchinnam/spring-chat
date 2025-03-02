package com.example.practice.service;

import com.example.practice.modal.Message;
import com.example.practice.payload.ChatDto;

import java.util.List;

public interface ChatService {
    List<ChatDto> getAllMessages();

    List<ChatDto> getMessages(Long senderId, Long receiverId);

    ChatDto createMessage(ChatDto chatDto);
}
