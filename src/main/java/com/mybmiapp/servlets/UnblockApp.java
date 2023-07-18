import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UnblockApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Unblock User");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Button unblockButton = new Button("Unblock");
        unblockButton.setOnAction(event -> {
            String email = emailField.getText();

            try (Socket socket = new Socket("localhost", 1234);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println("unblock," + email);
                String response = in.readLine();

                // Show a message based on the response
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Unblock User");
                alert.setHeaderText(null);
                alert.setContentText(response);
                alert.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(emailLabel, emailField, unblockButton);
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(10);

        Scene scene = new Scene(vbox, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
