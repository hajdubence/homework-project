package SlidingPuzzle.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class SlidingPuzzleModel {

    private List<Square> table = new ArrayList<Square>();

    private final ObjectProperty<Integer> moves = new SimpleObjectProperty<>();

    public SlidingPuzzleModel() {
        table.add(new Square(new Position(0,3),0));
        table.add(new Square(new Position(0,5),0));
        table.add(new Square(new Position(0,7),0));
        table.add(new Square(new Position(1,0),0));

        for (int i = 2; i < 10; i++) {
            table.add(new Square(new Position(1,i-1),i));
        }
        table.add(new Square(new Position(1,9),1));
        moves.set(0);
    }


    public ObjectProperty<Integer> getNumberProperty(Position position) {
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getPosition().equals(position)) {
                return table.get(i).getNumberProperty();
            }
        }
        throw new IllegalArgumentException();
    }
    public ObjectProperty<Integer> getScoreProperty() {
        return moves;
    }

    public boolean isSquare(Position position) {
        for (int i = 0; i < table.size(); i++) {
            if(table.get(i).getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEmpty(Position position){
        for (int i = 0; i < table.size(); i++) {
            if(table.get(i).getPosition().equals(position)) {
                return table.get(i).getNumber() == 0;
            }
        }
        return false;
    }

    public void move(Position position, Direction direction) {
        int from = -1, to = -1;

        for (int i = 0; i < table.size(); i++) {
            if(table.get(i).getPosition().equals(position)) {
                from = i;
            }
            if (table.get(i).getPosition().equals(position.moveTo(direction))) {
                to = i;
            }
        }

        if (from==-1 || to == -1 || table.get(to).getNumber()!=0) {
            throw new IllegalArgumentException();
        } else {
            table.get(to).getNumberProperty().set(table.get(from).getNumber());
            table.get(from).getNumberProperty().set(0);
            moves.set(moves.get() + 1);
        }
    }

    public Set<Direction> getValidMoves(Position position) {
        EnumSet<Direction> validMoves = EnumSet.noneOf(Direction.class);
        for (var direction : Direction.values()) {
            if (isEmpty(position.moveTo(direction))) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    public List<Position> canBeMoved() {
        List<Position> movable = new ArrayList<Position>();
        for (int i = 0; i < table.size(); i++) {
            if (table.get(i).getNumber()!=0 && !getValidMoves(table.get(i).getPosition()).isEmpty()) {
                movable.add(table.get(i).getPosition());
            }
        }
        return movable;
    }

}
