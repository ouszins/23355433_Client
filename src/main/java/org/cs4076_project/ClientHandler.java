package org.cs4076_project;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientRequest;
            while ((clientRequest = in.readLine()) != null) {
                String response = RequestProcessor.checkRequest(clientRequest);
                out.println(response);
            }
        } catch (IOException e) {
            System.out.println("Client connection error: " + e.getMessage());
        }finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
            } catch (IOException ex) {
                System.out.println("Error closing: " + ex.getMessage());
            }
        }
    }
}