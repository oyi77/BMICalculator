import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

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
            // TODO: Send BMI calculation request to server
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
