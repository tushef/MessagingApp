/**
 * @author Florian Maurer, Fatjon Tushe, Dominik Ramaj
 */

package com.example.messagingapp.Frontend.Chat;

import com.example.messagingapp.Frontend.Messaging;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.VBox;

public class ChatModel {
    private StringProperty message = new SimpleStringProperty("");

    public void sendMessage(){
        Messaging.instance.sendMessage(message.getValue());
    }

    /**
     * Outputs incoming messages in the chat window
     * @param vbox
     */
    public void listenForMessages(VBox vbox){
        new Thread(() -> {
            while(!Messaging.instance.isClosed()){
               ChatViewModel.outputReceivedMessage(Messaging.instance.receivedMessage(), vbox);
            }
        }).start();
    }
    public StringProperty messageProperty() {
        return message;
    }
}