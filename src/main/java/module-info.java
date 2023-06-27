module com.example.messagingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires moshi;
    requires javafx.media;

    exports com.example.messagingapp.Frontend;
    opens com.example.messagingapp.Frontend to javafx.fxml, javafx.controls;

    exports com.example.messagingapp.Frontend.Chat;
    opens com.example.messagingapp.Frontend.Chat to javafx.fxml, javafx.controls;

    exports com.example.messagingapp.Frontend.LogIn;
    opens com.example.messagingapp.Frontend.LogIn to javafx.fxml, javafx.controls;

    exports com.example.messagingapp;
    opens com.example.messagingapp to javafx.controls, javafx.fxml;
}