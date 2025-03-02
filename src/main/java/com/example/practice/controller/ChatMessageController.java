package com.example.practice.controller;

import com.example.practice.modal.Message;
import com.example.practice.payload.ChatDto;
import com.example.practice.payload.ChatPayload;
import com.example.practice.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chat")
public class ChatMessageController {

    @Autowired
    private ChatService chatService;

    @GetMapping("/sender/{senderId}/receiver/{receiverId}")
    public ResponseEntity<List<ChatDto>> getPrivateMessages(@PathVariable("senderId") Long senderId, @PathVariable("receiverId") Long receiverId) {
        return ResponseEntity.ok(chatService.getMessages(senderId, receiverId));
    }

    @PostMapping("/")
    public ResponseEntity<List<ChatDto>> getMessages(@RequestBody ChatPayload chatPayload) {
        return ResponseEntity.ok(chatService.getAllMessages());
    }

    @PostMapping("/create")
    public ResponseEntity<ChatDto> createMessage(@RequestBody ChatDto chatDto) {
        ChatDto chat = chatService.createMessage(chatDto);
        return new ResponseEntity<>(chat, HttpStatus.CREATED);
    }
}
