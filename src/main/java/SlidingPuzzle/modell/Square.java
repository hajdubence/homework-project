package SlidingPuzzle.modell;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Square {
    Position position;
    ObjectProperty<Integer> number = new SimpleObjectProperty<Integer>();

    public Square(Position position, int number) {
        this.position = position;
        this.number.set(number);
    }
}
