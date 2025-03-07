package org.cs4076_project;
import java.io.*;
import java.net.*;

public class TCPClient_23355433 {
    private static InetAddress IP;
    private static final int PORT = 1234;
    BufferedReader in;
    PrintWriter out;

    String run() {

        Socket link = null;
        try
        {
            IP = InetAddress.getLocalHost();
        }
        catch(UnknownHostException e)
        {
            System.out.println("IP of local machine not found!");
            System.exit(1);
        }

        try
        {
            link = new Socket(IP,PORT);
            in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            out = new PrintWriter(link.getOutputStream(),true);
            BufferedReader userEntry =new BufferedReader(new InputStreamReader(System.in));

        }
        catch(IOException e)
        {
            return ("Unable to establish socket to server");
        }
        finally
        {
            try
            {
                System.out.println("\n* Closing connection... *");
                assert link != null;
                link.close();
            }catch(IOException e)
            {
                System.out.println("Unable to disconnect/close!");
                System.exit(1);
            }
        }
        return "OK";
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
