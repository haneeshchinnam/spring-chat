package com.example.practice.serviceImpl;

import com.example.practice.exception.UserNotFound;
import com.example.practice.modal.Message;
import com.example.practice.modal.Status;
import com.example.practice.modal.User;
import com.example.practice.payload.ChatDto;
import com.example.practice.repository.ChatRepository;
import com.example.practice.repository.UserRepository;
import com.example.practice.service.ChatService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatServiceImpl.class);
    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public ChatDto createMessage(ChatDto chatDto) {
        if (chatDto == null) {
            throw new IllegalArgumentException("ChatDto cannot be null");
        }

        try {
            Message message = chatDtoToEntity(chatDto);
            if (message.getSender() == null || message.getReceiver() == null) {
                throw new IllegalStateException("Sender or Receiver cannot be null");
            }

            Message message1 = chatRepository.save(message);
            return entityToMessage(message1);
        } catch (UserNotFound | IllegalArgumentException | IllegalStateException e) {
            log.error(String.valueOf(e));
            throw new UserNotFound(e.getMessage());
        }
    }

    @Override
    public List<ChatDto> getAllMessages() {
        List<Message> messages = chatRepository.findAll();
        return messages.stream().map((this::entityToMessage)).collect(Collectors.toList());
    }

    @Override
    public List<ChatDto> getMessages(Long senderId, Long receiverId) {
        List<Message> messages = chatRepository.getMessagesByReceiverSenderId(senderId, receiverId);
        return messages.stream().map((this::entityToMessage)).collect(Collectors.toList());
    }

    private Message chatDtoToEntity(ChatDto chatDto) {
        if (chatDto == null) {
            throw new IllegalArgumentException("ChatDto cannot be null");
        }

        if (chatDto.getMessage() == null || chatDto.getDate() == null ||
                chatDto.getReceiver() == null || chatDto.getSender() == null) {
            throw new IllegalArgumentException("ChatDto contains null values");
        }

        Message message = new Message();
        message.setMessage(chatDto.getMessage());
        message.setDate(chatDto.getDate());

        User receiver = userRepository.findUserById(chatDto.getReceiver())
                .orElseThrow(() -> new UserNotFound(String.format("Receiver Id %d user does not exist", chatDto.getReceiver())));
        message.setReceiverName(receiver);

        User sender = userRepository.findUserById(chatDto.getSender())
                .orElseThrow(() -> new UserNotFound(String.format("Sender Id %d user does not exist", chatDto.getSender())));
        message.setSenderName(sender);
        return message;
    }

    private ChatDto entityToMessage(Message message) {
        ChatDto chatDto = new ChatDto();
        chatDto.setDate(message.getDate());
        chatDto.setMessage(message.getMessage());
        chatDto.setId(message.getId());
        chatDto.setStatus(message.getStatus());
        chatDto.setSender(message.getSender().getId());
        chatDto.setReceiver(message.getReceiver().getId());
        return chatDto;
    }
}
