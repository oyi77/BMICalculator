import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class HistoryApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("History");

        TextArea historyArea = new TextArea();
        historyArea.setEditable(false);

        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(event -> {
            try (Socket socket = new Socket("localhost", 1234);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println("getHistory");
                String response = in.readLine();

                // Update the history area with the received history
                historyArea.setText(response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(historyArea, refreshButton);
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
