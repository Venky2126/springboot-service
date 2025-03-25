package com.chat.app.request;

import lombok.Data;

@Data
public class ChatMessageRequest {

	private MessageType messageType;
	private String content;
	private String sender;

	public enum MessageType {
		CHAT, JOIN, LEAVE
	}

	public MessageType getType() {
		return messageType;
	}

}
