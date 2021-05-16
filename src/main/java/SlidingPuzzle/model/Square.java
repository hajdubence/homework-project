package SlidingPuzzle.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * This class represents a square in the board.
 */
public class Square {

    private final Position position;
    private final ObjectProperty<Integer> number = new SimpleObjectProperty<>();

    public Square(Position position, int number) {
        this.position = position;
        this.number.set(number);
    }

    /**
     * Returns the position of the square.
     *
     * @return the position of the square
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Returns 0 if the square is empty, otherwise the number of the piece.
     *
     * @return 0 if the square is empty, otherwise the number of the piece
     */
    public Integer getNumber() {
        return number.get();
    }

    /**
     * Returns an ObjectProperty with a value of 0 if the square is empty,
     * otherwise the number of the piece.
     *
     * @return an ObjectProperty with a value of 0 if the square is empty,
     * otherwise the number of the piece
     */
    public ObjectProperty<Integer> getNumberProperty() {
        return number;
    }

}
