package com.example.messagingapp.Backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private PrintWriter output;
    private BufferedReader input;
    private String username;
    private static final ArrayList<ClientHandler> existingConnections = new ArrayList<>();

    public ClientHandler(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            output = new PrintWriter(clientSocket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Starts Server thread, contains the logic about handling connected users in the chat and players, manages commands that are sent into the chat
     */
    @Override
    public void run() {
        try {
            String msg = receivedMessage();
            while (!usernameIsAccepted(msg)) {
                msg = receivedMessage();
            }
            sendMessage("Server: Welcome " + username);
            broadcast(username + " has entered the chat!");
            //listening for messages from the Client
            while ((msg = receivedMessage()) != null && !clientSocket.isClosed()) {
                if ((msg.equals("bye"))) {
                    broadcast(this.username+" has left the chat");
                    this.terminate();
                } else if (msg.startsWith("@")) {
                    //splittedMessage[0].substring(1) takes the username and removes the @
                    String[] splittedMessage = msg.split(" ", 2);
                    String username = splittedMessage[0].substring(1);
                    sendPrivateMessage(username, splittedMessage[1]);
                } else {
                    broadcast(username + ":" + msg);
                }
            }
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
            terminate();
        }
    }

    /**
     * sends a message to the client Stream
     *
     * @param message
     */
    public void sendMessage(String message) {
        output.println(message);
    }

    /**
     * Private Messaging
     * Sends a message to a specific user
     *
     * @param receiverUsername
     * @param message
     */
    private void sendPrivateMessage(String receiverUsername, String message) {
        for (ClientHandler tst : existingConnections) {
            if (tst.username.equals(receiverUsername) && !(tst.username.equals(this.username))) {
                tst.sendMessage(this.username + "(Privat): " + message);
            }
        }
    }

    /**
     * It listens for new messages
     * and it returns non-null ones
     *
     * @return received message
     */
    public String receivedMessage() {
        while (!clientSocket.isClosed()) {
            try {
                String message;
                if ((message = input.readLine()) != null) {
                    //System.out.println(message);
                    return message;
                }
            } catch (IOException e) {
                terminate();
                return "Error: " + e.getMessage();
            }
        }
        return "Client is not connected";
    }

    /**
     * it checks whether it can accept a certain Username
     * if yes than it assigns the username to the ServerThread
     * adds the ServerThread to existingConnections
     * and returns true
     *
     * @param requestedUsername
     * @return whether the username is accepted or not
     */
    public boolean usernameIsAccepted(String requestedUsername) {
        for (ClientHandler tst : existingConnections) {
            if (tst.username.equalsIgnoreCase(requestedUsername) && !requestedUsername.trim().isEmpty()) {
                this.sendMessage("This user already exists.");
                return false;
            }
        }
        this.username = requestedUsername;
        existingConnections.add(this);
        this.sendMessage("Username Accepted");
        return true;
    }

    /**
     * It broadcasts the message to every other client except the one this Thread handles and is responsible for
     * @param message
     */
    public void broadcast(String message){
        for (ClientHandler tst : existingConnections) {
            if (!tst.username.equals(this.username)) {
                tst.sendMessage(message);
            }
        }
    }

    /**
     * removes the ServerThread from existing connections
     */
    public void removeTCPServerThread() {
        if (existingConnections != null)
            existingConnections.remove(this);
    }

    /**
     * Terminates the ServerThread
     */
    public void terminate() {
        try {
            input.close();
            output.close();
            if (!clientSocket.isClosed()) {
                clientSocket.close();
            }
            removeTCPServerThread();
        } catch (IOException e) {
            System.out.println("Error " + e.getMessage());
        }
    }
}