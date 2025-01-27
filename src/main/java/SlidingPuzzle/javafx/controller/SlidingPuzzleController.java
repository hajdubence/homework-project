package SlidingPuzzle.javafx.controller;

import SlidingPuzzle.results.Result;
import SlidingPuzzle.results.Results;
import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import SlidingPuzzle.model.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.tinylog.Logger;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SlidingPuzzleController {

    private SlidingPuzzleModel model = new SlidingPuzzleModel();

    private SelectionPhase selectionPhase = SelectionPhase.SELECT_FROM;

    private List<Position> selectablePositions = new ArrayList<>();

    private Position selected;

    private String name;

    private ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    private enum SelectionPhase {
        SELECT_FROM,
        SELECT_TO;

        public SelectionPhase alter() {
            return switch (this) {
                case SELECT_FROM -> SELECT_TO;
                case SELECT_TO -> SELECT_FROM;
            };
        }
    }

    @FXML
    private GridPane board;

    @FXML
    private StackPane score;

    @FXML
    private void initialize() {
        Logger.info("Creating board.");
        for (int i = 0; i < board.getRowCount(); i++) {
            for (int j = 0; j < board.getColumnCount(); j++) {
                if (model.isSquare(new Position(i,j))) {
                    var square = createSquare();

                    var circle = createCircle(i,j);
                    square.getChildren().add(circle);

                    var text = createText(i,j);
                    square.getChildren().add(text);

                    board.add(square, j, i);
                }
            }
        }
        createCounter();

        setSelectablePositions();
        showSelectablePositions();
    }

    private StackPane createSquare() {
        var square = new StackPane();
        square.getStyleClass().add("square");
        square.setOnMouseClicked(this::handleMouseClick);
        return square;
    }

    private Circle createCircle(int col, int row) {
        var circle = new Circle(40);
        circle.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.getNumberProperty(new Position(col, row)));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.getNumberProperty(new Position(col, row)).get()) {
                            case 0 -> Color.TRANSPARENT;
                            default -> Color.web("0x00b000");
                        };
                    }
                }
        );
        circle.strokeProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.getNumberProperty(new Position(col, row)));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.getNumberProperty(new Position(col, row)).get()) {
                            case 0 -> Color.TRANSPARENT;
                            default -> Color.BLACK;
                        };
                    }
                }
        );
        return circle;
    }

    private Text createText(int col, int row) {
        var text = new Text();
        text.setFont(Font.font("Serif",32));
        text.fillProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.getNumberProperty(new Position(col, row)));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.getNumberProperty(new Position(col, row)).get()) {
                            case 0 -> Color.TRANSPARENT;
                            default -> Color.WHITE;
                        };
                    }
                }
        );
        text.textProperty().bind(
                new ObjectBinding<String>() {
                    {
                        super.bind(model.getNumberProperty(new Position(col, row)));
                    }
                    @Override
                    protected String computeValue() {
                        return model.getNumberProperty(new Position(col, row)).get().toString();
                    }
                }
        );
        return text;
    }

    private void createCounter() {
        var text = new Text();
        text.setFont(Font.font("Serif",24));
        text.textProperty().bind(
                new ObjectBinding<String>() {
                    {
                        super.bind(model.getScoreProperty());
                    }
                    @Override
                    protected String computeValue() {
                        return "Moves: " + model.getScoreProperty().get().toString();
                    }
                }
        );
        score.getChildren().add(text);
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        var square = (StackPane) event.getSource();
        var row = GridPane.getRowIndex(square);
        var col = GridPane.getColumnIndex(square);
        var position = new Position(row, col);
        Logger.debug("Click on square {}", position);
        handleClickOnSquare(position);
        checkGameEnd(event);
    }

    private void handleClickOnSquare(Position position) {
        switch (selectionPhase) {
            case SELECT_FROM -> {
                if (selectablePositions.contains(position)) {
                    selectPosition(position);
                    alterSelectionPhase();
                }
            }
            case SELECT_TO -> {
                if (selectablePositions.contains(position)) {
                    var direction = Direction.of(position.row() - selected.row(), position.col() - selected.col());
                    Logger.debug("Moving piece {} {}", selected, direction);
                    model.move(selected, direction);
                    deselectSelectedPosition();
                    alterSelectionPhase();
                }
            }
        }
    }

    private void alterSelectionPhase() {
        selectionPhase = selectionPhase.alter();
        hideSelectablePositions();
        setSelectablePositions();
        showSelectablePositions();
    }

    private void selectPosition(Position position) {
        Logger.debug("Selecting {} position", position);
        selected = position;
        showSelectedPosition();
    }

    private void showSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().add("selected");
    }

    private void deselectSelectedPosition() {
        hideSelectedPosition();
        selected = null;
    }

    private void hideSelectedPosition() {
        var square = getSquare(selected);
        square.getStyleClass().remove("selected");
    }

    private void setSelectablePositions() {
        Logger.debug("Setting selectable positions");
        selectablePositions.clear();
        switch (selectionPhase) {
            case SELECT_FROM -> selectablePositions.addAll(model.canBeMoved());
            case SELECT_TO -> {
                for (var direction : model.getValidMoves(selected)) {
                    selectablePositions.add(selected.moveTo(direction));
                }
            }
        }
    }

    private void showSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().add("selectable");
        }
    }

    private void hideSelectablePositions() {
        for (var selectablePosition : selectablePositions) {
            var square = getSquare(selectablePosition);
            square.getStyleClass().remove("selectable");
        }
    }

    private StackPane getSquare(Position position) {
        for (var child : board.getChildren()) {
            if (GridPane.getRowIndex(child) == position.row() && GridPane.getColumnIndex(child) == position.col()) {
                return (StackPane) child;
            }
        }
        throw new AssertionError();
    }

    private void checkGameEnd(MouseEvent event) {
        if (model.isEndState()) {
            Logger.info("The puzzle is solved.");
            try {
                saveResult();
            } catch (IOException e) {
                Logger.error("An error occurred while savaging the result");
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/ending.fxml"));
            } catch (IOException e) {
                Logger.error(e);
            }
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    private void saveResult() throws IOException {
        Results results = objectMapper.readValue(new FileReader("results.json"), Results.class);
        results.getList().add(new Result(name,model.getMoves()));
        FileWriter writer = new FileWriter("results.json");
        writer.write(objectMapper.writeValueAsString(results));
        writer.close();
    }

    public void setName(String name) {
        Logger.info("Setting name to {}", name);
        this.name = name;
    }
}
