package com.chat.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.chat.app.request.ChatMessageRequest;
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

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessageRequest sendMessage(@Payload ChatMessageRequest messageRequest) {
		log.info("Inside ChatController sendMessage");
		return messageRequest;
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
		}

		return messageRequest;
	}
}