package org.cs4076_project;
import java.io.*;
import java.net.*;

public class TCPClient_23355433 {
    private static InetAddress IP;
    private static final int PORT = 1234;
    BufferedReader in;
    PrintWriter out;
    private Socket link;

    public void run() {
        try {
            IP = InetAddress.getLocalHost();
            link = new Socket(IP, PORT);
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            out = new PrintWriter(link.getOutputStream(), true);
        } catch (IOException e) {
            System.out.println("Unable to establish socket connection");
        }
    }
    
    public void close() {
        try {
            if (link != null) {
                link.close();
            }
        } catch (IOException e) {
            System.out.println("Unable to disconnect!");
        }
    }

        
    public String send(String message) {
        String response = "";
        out.println(message);
        try{
            response = in.readLine();
        } catch (IOException e) {
            return ("No response from server received");
        }
        return response;
    }
}
