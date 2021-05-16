package SlidingPuzzle.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/menu.fxml"));
        stage.setTitle("Sliding Puzzle");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }
}
