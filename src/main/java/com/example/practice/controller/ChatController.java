package com.example.practice.controller;

import com.example.practice.payload.ChatDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Map;

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

    // Send a message to a chatroom
    @MessageMapping("/chatroom/{roomId}")
    public void sendToChatRoom(@DestinationVariable String roomId, @Payload ChatDto message,
                               @Header("simpSessionAttributes") Map<String, Object> sessionAttributes) {
        String username = (String) sessionAttributes.get("username");

        if (username == null) {
            throw new RuntimeException("Unauthorized access");
        }

        System.out.println("User " + username + " sent message to room: " + roomId);
        simpMessagingTemplate.convertAndSend("/topic/chatroom/" + roomId, message);
    }

    // Send a private message to a user
    @MessageMapping("/private-message/{recipient}")
    public void sendPrivateMessage(@DestinationVariable String recipient, @Payload ChatDto message,
                                   @Header("simpSessionAttributes") Map<String, Object> sessionAttributes) {
        String username = (String) sessionAttributes.get("username");

        if (username == null) {
            throw new RuntimeException("Unauthorized access");
        }

        System.out.println("User " + username + " sent a private message to: " + recipient);
        simpMessagingTemplate.convertAndSendToUser(recipient, "/queue/messages", message);
    }

}
