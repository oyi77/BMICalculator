package com.mybmiapp.tcp;

import com.mybmiapp.services.ChatService;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer {
    private final int port;
    private final ExecutorService executorService;
    private final ChatService chatService;

    public TCPServer(int port) {
        this.port = port;
        this.executorService = Executors.newFixedThreadPool(10); // Adjust the pool size as needed
        this.chatService = new ChatService(); // You can pass other required services here
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started. Listening on port " + port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket, chatService);
                executorService.execute(clientHandler);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        executorService.shutdown();
    }
}
