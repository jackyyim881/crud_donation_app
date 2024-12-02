package com.donation.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.donation.models.data.ChatMessage;

@Controller
@RequestMapping("/chat")
public class ChatWebController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        chatMessage.setTime(LocalDateTime.now().format(timeFormatter));
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(ChatMessage chatMessage) {
        chatMessage.setTime(LocalDateTime.now().format(timeFormatter));
        chatMessage.setType(ChatMessage.MessageType.JOIN);
        return chatMessage;
    }

    @MessageMapping("/chat.removeUser")
    @SendTo("/topic/public")
    public ChatMessage removeUser(ChatMessage chatMessage) {
        chatMessage.setTime(LocalDateTime.now().format(timeFormatter));
        chatMessage.setType(ChatMessage.MessageType.LEAVE);
        return chatMessage;
    }
}
