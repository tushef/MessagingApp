/**
 * @author Fatjon Tushe, Dominik Ramaj
 */
package com.example.messagingapp.Frontend.LogIn;

import com.example.messagingapp.Frontend.Messaging;

public class GuestModel {
    private String requestedUsername;

    /**
     * it sends a username to the Server in order to get accepted
     * @return the status of the request
     */
    public boolean logInWasSuccesful(){
        Messaging.instance.sendMessage(requestedUsername);
        System.out.println("Sent the username, now waiting");
        String message = Messaging.instance.receivedMessage();
        System.out.println(message);
        return message.equals("Username Accepted");
    }

    /**
     * The final method which is called from the ViewModel
     * in order to assign the accepted username
     */
    public void assignFinalUsername(){
        Messaging.instance.setUsername(this.requestedUsername);
    }

    public void setRequestedUsername(String requestedUsername) {
        this.requestedUsername = requestedUsername;
    }
}
