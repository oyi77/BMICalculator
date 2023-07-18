import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Login");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            try (Socket socket = new Socket("localhost", 1234);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println(email + "," + password);
                String response = in.readLine();

                if (response.equals("success")) {
                    // Navigate to the next scene
                    BMICalculatorApp bmiCalculatorApp = new BMICalculatorApp();
                    bmiCalculatorApp.start(new Stage());
                    primaryStage.close();
                } else {
                    // Show error message
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Login failed. Please try again.");
                    alert.showAndWait();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(emailLabel, emailField, passwordLabel, passwordField, loginButton);
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
