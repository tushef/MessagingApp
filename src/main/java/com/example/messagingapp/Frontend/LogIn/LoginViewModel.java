package com.example.messagingapp.Frontend.LogIn;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginViewModel implements Initializable {

    Stage stage = new Stage();
    @FXML private Button startButton;
    @FXML private TextField usernameInput;
    @FXML private Label errorMessage;
    GuestModel guest = new GuestModel();

    /**
     * Handles event of clicking login button.
     * @param event
     */
    public void handleLoginClick(ActionEvent event){
        //ToDo: Send the Username to the Guest Model
        if (!usernameInput.getText().trim().equals("")) {
            String requestedUsername = usernameInput.getText();
            this.guest.setRequestedUsername(requestedUsername);
            if (this.guest.logInWasSuccesful()) {
                System.out.println("Your login was successful");
                guest.assignFinalUsername();
                try {
                    switchToChatScene();
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }
            } else {
                alertError();
            }
        }
    }

    /**
     * Shows error, when Login failed.
     */
    public void alertError(){
        usernameInput.setStyle("-fx-border-color: red");
        usernameInput.clear();
        errorMessage.setText("This username already exists \n Please pick another");
        controlStartButton();

    }

    /**
     * Disables login button if username field is empty or filled with spaces.
     */
    public void controlStartButton(){
        String userNickname = usernameInput.getText();
        boolean isDisabled = userNickname.isEmpty() || userNickname.trim().isEmpty();
        startButton.setDisable(isDisabled);
    }

    /**
     * Switches from login scene to the chat scene, once login is successful.
     * @throws Exception
     */
    public void switchToChatScene() throws Exception {
        Parent root = FXMLLoader.load
                (Objects.requireNonNull(getClass().
                        getResource("/com/example/messagingapp/Chat/Chat.fxml")));
        stage = (Stage) startButton.getScene().getWindow();
        stage.setTitle("Chat");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    /**
     * Disables login button at first opening.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setDisable(true);
    }

}