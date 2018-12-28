package Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = new File("src/View/MainWindow.fxml").toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Pipes Game");
        primaryStage.setScene(new Scene(root, 1000, 600));
        primaryStage.show();
    }
}