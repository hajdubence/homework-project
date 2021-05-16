package SlidingPuzzle.javafx.controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

import SlidingPuzzle.results.Result;
import SlidingPuzzle.results.Results;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

public class MenuController {

    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @FXML
    private GridPane leaderboard;

    @FXML
    private Text warning;

    @FXML
    private TextField nameField;

    @FXML
    private void initialize() throws IOException{
        createLeaderboard();
    }

    private void createLeaderboard() throws IOException {
        try {
            FileReader reader = new FileReader("results.json");
            reader.close();
        } catch (FileNotFoundException e) {
            try {
                Logger.debug("Creating results file.");
                FileWriter writer = new FileWriter("results.json");
                writer.write(objectMapper.writeValueAsString(new Results()));
                writer.close();
            } catch (IOException e2) {
                Logger.error("Can't create results file.");
            }
        }
        Results results = objectMapper.readValue(new FileReader("results.json"), Results.class);
        Collections.sort(results.getList());
        for (int i = 0; i < 10 && i < results.getList().size() ; i++) {
            Result result = results.getList().get(i);
            Text record = new Text(result.getName() + " (" + result.getMoves() + ")");
            leaderboard.add(record, 1, i);
            Logger.debug("Added {} to the leaderboard.", record.getText());
        }
    }

    @FXML
    private void start(ActionEvent event) throws IOException {
        if (nameField.getText()=="") {
            Logger.info("Tried to start the game whitout entering a name");
            warning.setVisible(true);
        } else {
            Logger.info("Name entered: {}", nameField.getText());
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/puzzle.fxml"));
            Parent root = fxmlLoader.load();
            SlidingPuzzleController controller = fxmlLoader.<SlidingPuzzleController>getController();
            controller.setName(nameField.getText());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }



}
