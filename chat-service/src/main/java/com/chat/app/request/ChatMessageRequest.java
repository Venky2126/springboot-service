package com.chat.app.request;

import lombok.Data;

@Data
public class ChatMessageRequest {
    private String sender;
    private String content;
    private MessageType messageType; // Use the enum for messageType
    private String name;

    // Enum for message types
    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE
    }
}