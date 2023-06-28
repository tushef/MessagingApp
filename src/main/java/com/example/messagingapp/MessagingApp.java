package com.example.messagingapp;

import com.example.messagingapp.Frontend.LogIn.LoginViewModel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class MessagingApp extends Application {

    private Scene scene;

    @Override
    public void start(Stage mainStage){
        try {
            URL fxmlLocation = Objects.requireNonNull(getClass().getResource("/com/example/messagingapp/LogIn/WelcomeWindow.fxml"));
            FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
            scene = new Scene(fxmlLoader.load(), 600, 400);
            mainStage.show();
            scene.getStylesheets().add(LoginViewModel.class.getResource("/com/example/messagingapp/LogIn/WelcomeWindow.css").toExternalForm());
            mainStage.setScene(scene);
            mainStage.setResizable(false);
            mainStage.setOnCloseRequest(windowEvent -> {
                Platform.exit();
                System.exit(0);
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Starts the app.
     * @param args
     */
    public static void main (String[]args){
        launch(args);
    }
}
