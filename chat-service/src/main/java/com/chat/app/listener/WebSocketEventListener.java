package com.chat.app.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.chat.app.request.ChatMessageRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class WebSocketEventListener {

	@Autowired
	private SimpMessageSendingOperations messagingTemplate;

	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

		String username = (String) headerAccessor.getSessionAttributes().get("username");
		if (username != null) {
			log.info("User Disconnected : " + username);

			ChatMessageRequest messageRequest = new ChatMessageRequest();
			messageRequest.setMessageType(ChatMessageRequest.MessageType.LEAVE);
			messageRequest.setSender(username);

			messagingTemplate.convertAndSend("/topic/public", messageRequest);
		}
	}
}
