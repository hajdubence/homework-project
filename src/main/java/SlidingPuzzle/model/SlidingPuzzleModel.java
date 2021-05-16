package SlidingPuzzle.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import org.tinylog.Logger;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * Class representing the state of the puzzle.
 */
public class SlidingPuzzleModel {

    /**
     * The squares of the board.
     */
    private List<Square> board = new ArrayList<Square>();

    /**
     * The counter of the number of moves.
     */
    private final ObjectProperty<Integer> moves = new SimpleObjectProperty<>();

    /**
     * This constructor creates the beginning state.
     */
    public SlidingPuzzleModel() {
        board.add(new Square(new Position(0,3),0));
        board.add(new Square(new Position(0,5),0));
        board.add(new Square(new Position(0,7),0));
        board.add(new Square(new Position(1,0),0));

        for (int i = 2; i < 10; i++) {
            board.add(new Square(new Position(1,i-1),i));
        }
        board.add(new Square(new Position(1,9),1));
        moves.set(0);
    }

    /**
     * Returns an ObjectProperty af square on the position specified.
     * The value is 0 if square square is empty, otherwise the number of the piece.
     *
     * @param position the position of the square
     * @return an ObjectProperty af square on the position specified where
     * the value is 0 if square square is empty, otherwise the number of the piece
     * @throws IllegalArgumentException if the square is not on the board
     */
    public ObjectProperty<Integer> getNumberProperty(Position position) {
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).getPosition().equals(position)) {
                return board.get(i).getNumberProperty();
            }
        }
        throw new IllegalArgumentException();
    }

    public ObjectProperty<Integer> getScoreProperty() {
        return moves;
    }

    /**
     * Returns if a square is on the board.
     *
     * @param position the position of the square
     * @return {@code true} if it is on the board, {@code false} otherwise
     */
    public boolean isSquare(Position position) {
        for (int i = 0; i < board.size(); i++) {
            if(board.get(i).getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of moves made so far.
     *
     * @return the number of moves made so far
     */
    public int getMoves() {
        return moves.get().intValue();
    }

    /**
     * Returns if a square is empty.
     *
     * @param position the position of the square
     * @return {@code true} if it is empty, {@code false} otherwise
     */
    public boolean isEmpty(Position position){
        for (int i = 0; i < board.size(); i++) {
            if(board.get(i).getPosition().equals(position)) {
                return board.get(i).getNumber() == 0;
            }
        }
        return false;
    }

    /**
     * {@return {@code true} if the puzzle is solved, {@code false} otherwise}
     */
    public boolean isEndState() {
        for (int i = 0; i < 9; i++) {
            if (getNumberProperty(new Position(1,i)).get().intValue() != i+1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Moves a piece in the direction specified
     *
     * @param position the position of a piece
     * @param direction the direction of the move
     * @throws IllegalArgumentException if the move is invalid
     */
    public void move(Position position, Direction direction) {
        int from = -1, to = -1;

        for (int i = 0; i < board.size(); i++) {
            if(board.get(i).getPosition().equals(position)) {
                from = i;
            }
            if (board.get(i).getPosition().equals(position.moveTo(direction))) {
                to = i;
            }
        }

        if (from==-1 || to == -1 || board.get(to).getNumber()!=0) {
            throw new IllegalArgumentException();
        } else {
            Logger.debug("Moving from {} to {}", board.get(from).getPosition(), board.get(to).getPosition());
            board.get(to).getNumberProperty().set(board.get(from).getNumber());
            board.get(from).getNumberProperty().set(0);
            moves.set(moves.get() + 1);
        }
    }

    /**
     * Returns a set of directions where you can move from a position.
     *
     * @param position the position of a piece
     * @return a set of directions where you can move from a position
     */
    public Set<Direction> getValidMoves(Position position) {
        EnumSet<Direction> validMoves = EnumSet.noneOf(Direction.class);
        for (var direction : Direction.values()) {
            if (isEmpty(position.moveTo(direction))) {
                validMoves.add(direction);
            }
        }
        return validMoves;
    }

    /**
     * Returns a list of positions where are the pieces that can be moved.
     *
     * @return a list of positions where are the pieces that can be moved
     */
    public List<Position> canBeMoved() {
        List<Position> movable = new ArrayList<Position>();
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i).getNumber()!=0 && !getValidMoves(board.get(i).getPosition()).isEmpty()) {
                movable.add(board.get(i).getPosition());
            }
        }
        return movable;
    }

}
