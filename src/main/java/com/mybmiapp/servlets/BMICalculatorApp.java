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

public class BMICalculatorApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("BMI Calculator");

        Label weightLabel = new Label("Weight (kg):");
        TextField weightField = new TextField();

        Label heightLabel = new Label("Height (m):");
        TextField heightField = new TextField();

        Button calculateButton = new Button("Calculate BMI");
        calculateButton.setOnAction(event -> {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());

            try (Socket socket = new Socket("localhost", 1234);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println(weight + "," + height);
                String response = in.readLine();

                // TODO: Show the calculated BMI

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        VBox vbox = new VBox(weightLabel, weightField, heightLabel, heightField, calculateButton);
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
