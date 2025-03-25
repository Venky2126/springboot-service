package com.chat.app.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.chat.app.request.ChatMessageRequest;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatController {

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessageRequest sendMessage(@Payload ChatMessageRequest messageRequest) {

		log.info("Inside ChatController sendMessage");

		return messageRequest;
	}

	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessageRequest addUser(@Payload ChatMessageRequest messageRequest,
			SimpMessageHeaderAccessor headerAccessor) {

		log.info("Inside ChatController addUser");

		// Add username in web socket session
		if (messageRequest.getContent() == null) {
			messageRequest.setContent(messageRequest.getSender());
		}
		headerAccessor.getSessionAttributes().put("username", messageRequest.getSender());

		return messageRequest;
	}

}
