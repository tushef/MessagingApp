/**
 * @author Fatjon Tushe, Anatasiia Dudrina, Dinghao Shi
 */

package com.example.messagingapp.Backend;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private static final int PORT = 5555;
    private static final String ERROR_MESSAGE = "Sorry, something went wrong";
    private boolean isCompleted = false;

    /**
     * Starting the Server and managing incoming connections
     */
    public void startServer(){
        try{
            serverSocket = new ServerSocket(PORT);
            ExecutorService threadsPool = Executors.newCachedThreadPool();
            while(!isCompleted){
                Socket clientSocket = serverSocket.accept();
                ClientHandler thr = new ClientHandler(clientSocket);
                threadsPool.execute(thr);
            }
        } catch(IOException e){
            terminate();
        }
    }

    /**
     * Terminates the server and clients.
     */
    public void terminate(){
        try{
            isCompleted = true;
            if(serverSocket!=null){
                serverSocket.close();
            } //if server terminates, clients should be terminated as well
        }catch(IOException e){
            System.out.println(ERROR_MESSAGE);
        }
    }

    public static void main(String[] args){
        Server tcpServer = new Server();
        tcpServer.startServer();
    }
}