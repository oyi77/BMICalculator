package com.mybmiapp.services;

import com.mybmiapp.models.ChatMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatService {
    private Map<Integer, List<ChatMessage>> chatMessagesByUserId;

    public ChatService() {
        this.chatMessagesByUserId = new HashMap<>();
    }

    public void addChatMessage(ChatMessage chatMessage) {
        int senderId = chatMessage.getSenderId();
        int receiverId = chatMessage.getReceiverId();

        chatMessagesByUserId.computeIfAbsent(senderId, k -> new ArrayList<>()).add(chatMessage);
        chatMessagesByUserId.computeIfAbsent(receiverId, k -> new ArrayList<>()).add(chatMessage);
    }

    public List<ChatMessage> getChatMessagesByUserId(int userId) {
        return chatMessagesByUserId.getOrDefault(userId, new ArrayList<>());
    }

    public void blockUser(int blockerId, int blockedId) {
        List<ChatMessage> blockedMessages = chatMessagesByUserId.getOrDefault(blockedId, new ArrayList<>());
        chatMessagesByUserId.put(blockedId, new ArrayList<>());

        List<ChatMessage> updatedMessages = new ArrayList<>();
        for (ChatMessage message : blockedMessages) {
            if (message.getSenderId() != blockerId) {
                updatedMessages.add(message);
            }
        }
        chatMessagesByUserId.put(blockerId, updatedMessages);
    }
}
