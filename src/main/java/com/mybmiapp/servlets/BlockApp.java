import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class BlockApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Block User");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Button blockButton = new Button("Block");
        blockButton.setOnAction(event -> {
            String email = emailField.getText();

            try (Socket socket = new Socket("localhost", 1234);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println("block," + email);
                String response = in.readLine();

                // TODO: Show a message based on the response

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(emailLabel, emailField, blockButton);
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
