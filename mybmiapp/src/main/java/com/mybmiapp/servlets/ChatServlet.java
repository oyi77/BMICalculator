package com.mybmiapp.servlets;

import com.mybmiapp.dao.BlockedUserDao;
import com.mybmiapp.dao.BlockedUserDaoImpl;
import com.mybmiapp.dao.ChatMessageDao;
import com.mybmiapp.dao.ChatMessageDaoImpl;
import com.mybmiapp.dao.UserDao;
import com.mybmiapp.dao.UserDaoImpl;
import com.mybmiapp.models.ChatMessage;
import com.mybmiapp.models.User;
import com.mybmiapp.util.DatabaseConnection;
import com.mybmiapp.util.UserSessionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@WebServlet("/chat")
public class ChatServlet extends HttpServlet {

    private final ChatMessageDao chatMessageDao;
    private final UserDao userDao;
    private final BlockedUserDao blockUserDao;
    private final Connection dm;

    public ChatServlet() throws SQLException {
        this.dm = DatabaseConnection.getConnection();
        chatMessageDao = new ChatMessageDaoImpl(dm);
        userDao = new UserDaoImpl(dm);
        blockUserDao = new BlockedUserDaoImpl(dm);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = UserSessionUtil.getCurrentUser(request.getSession());

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        List<User> users = userDao.getAllUsers(currentUser.getId());
        request.setAttribute("users", users);

        // Get the list of all users except the current user
        List<User> blockedUsers = blockUserDao.getBlockedUsers(currentUser.getId());
        request.setAttribute("users", users);

        String receiverIdParam = request.getParameter("receiverId");
        if (receiverIdParam != null && !receiverIdParam.isEmpty()) {
            int receiverId = Integer.parseInt(receiverIdParam);
            User receiver = userDao.getUserById(receiverId);

            // Check if the receiver is not blocked by the current user
            boolean isBlocked = blockUserDao.isBlocked(currentUser.getId(), receiverId);
            if (!isBlocked) {
                request.setAttribute("receiverId", receiverId);
                request.setAttribute("email", receiver.getEmail());

                // Retrieve chat messages with the selected receiver
                List<ChatMessage> chatMessages = chatMessageDao.getChatMessages(currentUser.getId(), receiverId);
                request.setAttribute("chatMessages", chatMessages);
            }
        }

        request.getRequestDispatcher("chat.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User currentUser = UserSessionUtil.getCurrentUser(request.getSession());

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String receiverIdParam = request.getParameter("receiverId");
        String message = request.getParameter("message");

        if (receiverIdParam != null && !receiverIdParam.isEmpty() && message != null && !message.isEmpty()) {
            int receiverId = Integer.parseInt(receiverIdParam);
            currentUser = userDao.getUserById(currentUser.getId());
            LocalDateTime timestamp = LocalDateTime.now();
            ChatMessage chatMessage = new ChatMessage(0, currentUser.getId(), receiverId, message,currentUser.getEmail(), timestamp);
            chatMessageDao.addChatMessage(chatMessage);
        }

        response.sendRedirect("chat");
    }
}

