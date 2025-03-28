package com.chat.app.request;

import lombok.Data;

@Data
public class ChatMessageRequest {
    private String sender;
    private String content;
    private String messageType;
    private String name;
}