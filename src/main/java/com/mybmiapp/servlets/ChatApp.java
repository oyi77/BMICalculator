import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Chat");

        TextArea chatArea = new TextArea();
        chatArea.setEditable(false);

        Label messageLabel = new Label("Message:");
        TextField messageField = new TextField();

        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            String message = messageField.getText();

            try (Socket socket = new Socket("localhost", 1234);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println(message);
                String response = in.readLine();

                // TODO: Update the chat area with the received message

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(chatArea, messageLabel, messageField, sendButton);
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
