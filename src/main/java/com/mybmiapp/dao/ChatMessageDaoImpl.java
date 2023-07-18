package com.mybmiapp.dao;

import com.mybmiapp.models.ChatMessage;
import com.mybmiapp.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChatMessageDaoImpl implements ChatMessageDao {

    private Connection connection;

    // Constructor to initialize the connection (you may use a connection pool in production)
    public ChatMessageDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean addChatMessage(ChatMessage chatMessage) {
        String query = "INSERT INTO chat_messages (sender_id, receiver_id, message, email, timestamp) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, chatMessage.getSenderId());
            pstmt.setInt(2, chatMessage.getReceiverId());
            pstmt.setString(3, chatMessage.getMessage());
            pstmt.setString(4, chatMessage.getEmail());
            pstmt.setTimestamp(5, java.sql.Timestamp.valueOf(chatMessage.getTimestamp())); // Convert LocalDateTime to Timestamp

            // Execute the insert query
            int rowsInserted = pstmt.executeUpdate();

            // Return true if the operation is successful (1 row inserted), false otherwise
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // An error occurred, return false
        }
    }

    @Override
    public List<ChatMessage> getChatMessages(int id, int receiverId) {
        ResultSet resultSet = null;
        List<ChatMessage> chatMessages = new ArrayList<>();

        String query = "SELECT * FROM chat_messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY timestamp";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            pstmt.setInt(2, receiverId);
            pstmt.setInt(3, receiverId);
            pstmt.setInt(4, id);
            resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int messageId = resultSet.getInt("id");
                String messageText = resultSet.getString("message");
                int senderId = resultSet.getInt("sender_id");
                int recvId = resultSet.getInt("receiver_id");
                String email = resultSet.getString("email");

                LocalDateTime timestamp = resultSet.getTimestamp("timestamp").toLocalDateTime();

                ChatMessage chatMessage = new ChatMessage(messageId, senderId, recvId, messageText, email, timestamp);
                chatMessages.add(chatMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeResultSet(resultSet);
        }

        return chatMessages;
    }

    @Override
    public List<ChatMessage> getChatMessagesByReceiverId(int receiverId) {
        List<ChatMessage> chatMessages = new ArrayList<>();
        String query = "SELECT * FROM chat_messages WHERE receiver_id = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, receiverId);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int senderId = resultSet.getInt("sender_id");
                String message = resultSet.getString("message");
                LocalDateTime timestamp = resultSet.getTimestamp("timestamp").toLocalDateTime();
                String email = resultSet.getString("email"); // Retrieve the email from the database

                ChatMessage chatMessage = new ChatMessage(id, senderId, receiverId, message, email, timestamp);
                chatMessage.setEmail(email); // Set the email in the ChatMessage object
                chatMessages.add(chatMessage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatMessages;
    }

}
