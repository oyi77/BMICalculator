import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LogoutApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Logout");

        Button logoutButton = new Button("Logout");
        logoutButton.setOnAction(event -> {
            // TODO: Logout the current user and navigate to the login scene
        });

        VBox vbox = new VBox(logoutButton);
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
