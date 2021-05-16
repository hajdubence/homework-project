package SlidingPuzzle.model;
/**
 * This class represents a position in a board.
 */
public record Position(int row, int col) {

    /**
     * Returns the position that in the direction specified.
     *
     * @param direction the direction where you want to move
     * @return the position that in the direction specified
     */
    public Position moveTo(Direction direction) {
        return new Position(row + direction.getRowChange(), col + direction.getColChange());
    }

    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}