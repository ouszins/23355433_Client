package org.cs4076_project;

import java.io.*;
import java.net.*;

public class Server_23369205 {
    
    private static final int port = 1234;
    

    public static void main(String[] args) {
        ServerSocket socket = null;
        
        try {
            socket = new ServerSocket(port);
            System.out.println("Server is waiting for connection from client");

                while(true){
                Socket clientSocket = socket.accept();
                System.out.println("Client connected from " + clientSocket.getInetAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
                }
                
                
            
        } 
        catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
        
       
    }    
  }

    
