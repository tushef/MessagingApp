package com.example.messagingapp.Frontend;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Messaging {
    private static final int PORT = 5555;
    private static final String LOCALHOST = "localhost";
    private PrintWriter output;
    private BufferedReader input;
    private Socket clientSocket;
    private String username;
    public static Messaging instance = new Messaging();
    public Messaging() {
        try {
            clientSocket = new Socket(LOCALHOST, PORT);
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        }
        catch (IOException e){
            System.out.println(e.getMessage());
            terminate();
        }
    }

    public boolean isClosed(){return this.clientSocket.isClosed();}

    public static Messaging getInstance() {
        return instance;
    }


    public void setUsername(String username){this.username = username;}

    /**
     * Sends a message to the client Stream.
     * @param message
     */
    public void sendMessage(String message) {
        output.println(message);
        if(message.equals("bye")  && username != null){
            Platform.exit();
            System.exit(0);
        }
    }

    /**
     * It listens for new messages
     * and it returns non-null ones
     * @return received message
     */
    public String receivedMessage() {
        String message;
        while(clientSocket.isConnected()){
            try{
                //System.out.println("Listening");
                if((message=input.readLine()) != null) {
                    System.out.println(message);
                    return message;
                }

            }catch (IOException e){
                terminate();
                return "Error: "+e.getMessage();
            }
        }
        return "Not Connected";
    }

    /**
     * Terminates the Messaging.
     */
    public void terminate(){
        try{
            if (output != null) {
                output.close();
            }
            if (input != null) {
                input.close();
            }
            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
