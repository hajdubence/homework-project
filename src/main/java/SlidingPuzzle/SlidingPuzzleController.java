package SlidingPuzzle;

import javafx.beans.binding.ObjectBinding;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import SlidingPuzzle.modell.*;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SlidingPuzzleController {

    private SlidingPuzzleModel model = new SlidingPuzzleModel();

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
    private void initialize() {
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
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        //TODO
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
                        super.bind(model.numberProperty(new Position(col, row)));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.numberProperty(new Position(col, row)).get()) {
                            case 0 -> Color.TRANSPARENT;
                            default -> Color.web("0x00b000");
                        };
                    }
                }
        );
        circle.strokeProperty().bind(
                new ObjectBinding<Paint>() {
                    {
                        super.bind(model.numberProperty(new Position(col, row)));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.numberProperty(new Position(col, row)).get()) {
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
                        super.bind(model.numberProperty(new Position(col, row)));
                    }
                    @Override
                    protected Paint computeValue() {
                        return switch (model.numberProperty(new Position(col, row)).get()) {
                            case 0 -> Color.TRANSPARENT;
                            default -> Color.WHITE;
                        };
                    }
                }
        );
        text.textProperty().bind(
                new ObjectBinding<String>() {
                    {
                        super.bind(model.numberProperty(new Position(col, row)));
                    }
                    @Override
                    protected String computeValue() {
                        return model.numberProperty(new Position(col, row)).get().toString();
                    }
                }
        );
        return text;
    }

}
