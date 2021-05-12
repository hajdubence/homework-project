package SlidingPuzzle.modell;

import javafx.beans.property.ObjectProperty;

import java.util.ArrayList;
import java.util.List;

public class SlidingPuzzleModel {

    private List<Square> table = new ArrayList<Square>();

    public SlidingPuzzleModel() {
        table.add(new Square(new Position(0,3),0));
        table.add(new Square(new Position(0,5),0));
        table.add(new Square(new Position(0,7),0));
        table.add(new Square(new Position(1,0),0));

        for (int i = 2; i < 10; i++) {
            table.add(new Square(new Position(1,i-1),i));
        }
        table.add(new Square(new Position(1,9),1));
    }

    public boolean isSquare(Position position) {
        for (int i = 0; i < table.size(); i++) {
            if(table.get(i).position.equals(position)) {
                return true;
            }
        }
        return false;
    }

    public ObjectProperty<Integer> numberProperty(Position position) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).position.equals(position)) {
                return table.get(i).number;
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean isEmpty(Position position){
        for (int i = 0; i < table.size(); i++) {
            if(table.get(i).position.equals(position)) {
                if (table.get(i).number.get() == 0) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

}
