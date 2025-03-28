package com.chat.app.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.chat.app.model.ChatMessage;
import com.chat.app.request.ChatMessageRequest;
import com.chat.app.service.ChatMessageService;
import com.chat.app.service.EmailService;
import com.chat.app.service.EmailTemplate;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequiredArgsConstructor
public class ChatController {

    private final EmailService emailService;
    private final ChatMessageService chatMessageService;

    // Store session IDs of active users
    private static final Set<String> activeSessions = ConcurrentHashMap.newKeySet();

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessageRequest sendMessage(@Payload ChatMessageRequest messageRequest, SimpMessageHeaderAccessor headerAccessor) {
        log.info("Inside ChatController sendMessage");

        // Retrieve user session attributes
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes != null) {
            String sender = (String) sessionAttributes.get("email");
            String name = (String) sessionAttributes.get("name");
            String sessionId = headerAccessor.getSessionId();
            messageRequest.setSender(sender);
            messageRequest.setName(name);

            // Ensure message is only sent if another session is active
            if (activeSessions.size() > 1) {
                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setMessageType(messageRequest.getMessageType());
                chatMessage.setContent(messageRequest.getContent());
                chatMessage.setName(messageRequest.getName());
                chatMessage.setSender(messageRequest.getSender());
                chatMessage.setStatus("SENT");
                chatMessageService.save(chatMessage);

                return messageRequest;
            } else {
                log.info("No other active sessions. Message not sent.");
                return null;
            }
        }

        return null;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessageRequest addUser(@Payload ChatMessageRequest messageRequest,
                                      SimpMessageHeaderAccessor headerAccessor) throws MessagingException {
        String sender = messageRequest.getSender();
        String name = messageRequest.getName();
        log.info("Inside ChatController addUser");

        // Generate the Template to send Chat
        EmailTemplate template = new EmailTemplate("SendOtp.html");
        Map<String, String> replacements = new HashMap<>();
        replacements.put("email", sender);
        replacements.put("name", name);
        String message = template.getTemplate(replacements);

        log.info("Chat Notification to Mail: " + sender);
        // Send Email
        emailService.sendChatMessage(sender, message);

        // Add sender to web socket session
        if (messageRequest.getContent() == null) {
            messageRequest.setContent(messageRequest.getSender());
        }

        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        if (sessionAttributes != null) {
            sessionAttributes.put("email", messageRequest.getSender());
            sessionAttributes.put("name", name); // Add name to session attributes
            activeSessions.add(headerAccessor.getSessionId()); // Add session ID to active sessions
        }

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessageType(messageRequest.getMessageType());
        chatMessage.setContent(messageRequest.getContent());
        chatMessage.setName(messageRequest.getName());
        chatMessage.setSender(messageRequest.getSender());
        chatMessage.setStatus("JOINED");
        chatMessageService.save(chatMessage);

        return messageRequest;
    }

    @MessageMapping("/chat.seenMessage")
    @SendTo("/topic/seen")
    public void seenMessage(@Payload Map<String, Object> payload) {
        Long messageId = Long.valueOf(payload.get("messageId").toString());
        String sessionId = payload.get("sessionId").toString();
        chatMessageService.updateMessageStatusToSeen(messageId, sessionId);
    }
}