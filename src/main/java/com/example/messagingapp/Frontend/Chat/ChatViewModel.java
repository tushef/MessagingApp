/**
 * @author Florian Maurer, Fatjon Tushe, Anastasiia Dudrina, Dominik Ramaj
 * API between GUI and Model
 */

package com.example.messagingapp.Frontend.Chat;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.ResourceBundle;

public class ChatViewModel implements Initializable {

    @FXML private TextField userInput;
    @FXML private VBox chatBox;
    @FXML private Button sendButton;
    private ChatModel chatModel;

    /**
     * Initializes basic settings once the chat scene is opened: initializes chat model, disables send button at first opening of the chat scene, starts listeting for incoming messages.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chatModel = new ChatModel();
        controlSendButton();
        userInput.textProperty().bindBidirectional(chatModel.messageProperty());
        chatModel.listenForMessages(chatBox);
    }

    /**
     * Handles sending messages in the chat: allowes to send messages in case if it contains non-empty characters
     * @param event
     */
    public void handleSubmitClick(ActionEvent event){
        if (!userInput.getText().trim().equals("")) {
            chatModel.sendMessage();
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_RIGHT);
            hBox.setPadding(new Insets(5, 5, 5, 10));

            Text text = new Text(userInput.getText());
            TextFlow textFlow = new TextFlow(text);

            textFlow.setStyle(
                    "-fx-color: rgb(255, 255, 255);" +
                            "-fx-background-color: rgb(43, 179, 252);" +
                            "-fx-background-radius: 20px;");

            textFlow.setPadding(new Insets(5, 10, 5, 10));
            text.setFill(Color.color(0.934, 0.925, 0.996));

            hBox.getChildren().add(textFlow);
            chatBox.getChildren().add(hBox);
            userInput.clear();
        }
    }

    /**
     * Disables send button in case if the text field is empty or contains only spaces
     */
    public void controlSendButton(){
            String message = userInput.getText();
            boolean isDisabled = message.isEmpty() || message.trim().isEmpty();
            sendButton.setDisable(isDisabled);
    }

    /**
     * Handles output of a single received message.
     * @param receivedMessage
     * @param vbox
     */
    public static void outputReceivedMessage(String receivedMessage, VBox vbox){
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        Text text = new Text(receivedMessage);
        TextFlow textFlow = new TextFlow(text);

        textFlow.setStyle(
                "-fx-background-color: rgb(82, 82, 82);" +
                        "-fx-background-radius: 20px;");
        text.setFill(Color.rgb(43, 179, 252));

        textFlow.setPadding(new Insets(5, 10, 5, 10));
        hBox.getChildren().add(textFlow);

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().add(hBox);
            }
        });
    }
}