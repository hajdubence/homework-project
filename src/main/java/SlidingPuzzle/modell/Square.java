package SlidingPuzzle.modell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Square {

    private final Position position;
    private final ObjectProperty<Integer> number = new SimpleObjectProperty<>();

    public Square(Position position, int number) {
        this.position = position;
        this.number.set(number);
    }

    public Position getPosition() {
        return position;
    }

    public Integer getNumber() {
        return number.get();
    }

    public ObjectProperty<Integer> getNumberProperty() {
        return number;
    }

}
