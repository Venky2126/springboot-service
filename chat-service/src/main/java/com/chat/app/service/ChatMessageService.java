package com.chat.app.service;

import org.springframework.stereotype.Service;

import com.chat.app.model.ChatMessage;
import com.chat.app.repository.ChatMessageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

	private final ChatMessageRepository chatMessageRepository;

	public void save(ChatMessage chatMessage) {
		chatMessageRepository.save(chatMessage);
	}

	public void updateMessageStatusToSeen(Long messageId) {
		ChatMessage message = chatMessageRepository.findById(messageId)
				.orElseThrow(() -> new RuntimeException("Message not found"));
		message.setStatus("SEEN");
		chatMessageRepository.save(message);
	}
}