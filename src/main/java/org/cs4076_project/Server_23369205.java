package org.cs4076_project;

import java.io.*;
import java.net.*;

public class Server_23369205 {

    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is running and waiting for connections...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());


                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        }
        catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }

    }
}







