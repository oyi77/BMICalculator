package com.mybmiapp.dao;

import com.mybmiapp.models.ChatMessage;

import java.util.List;

public interface ChatMessageDao {
    boolean addChatMessage(ChatMessage chatMessage);
    List<ChatMessage> getChatMessagesByReceiverId(int receiverId);
    public List<ChatMessage> getChatMessages(int id, int receiverId);
}
