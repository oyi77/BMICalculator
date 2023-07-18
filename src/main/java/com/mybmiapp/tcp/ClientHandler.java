package com.mybmiapp.tcp;

import com.mybmiapp.services.ChatService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final ChatService chatService;

    public ClientHandler(Socket clientSocket, ChatService chatService) {
        this.clientSocket = clientSocket;
        this.chatService = chatService;
    }

    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(clientSocket.getOutputStream(), true);

            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                // Process client input and respond accordingly
                // Example: chatService.addChatMessage(message);
                // Example: writer.println(response);
            }

            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
