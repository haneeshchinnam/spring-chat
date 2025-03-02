package com.example.practice.controller;

import com.example.practice.modal.Message;
import com.example.practice.payload.ChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/topic/chatroom")
    public ChatDto sendMessage(@Payload ChatDto message) {
        System.out.println("Message "+ message.toString());
        return message;
    }

}
