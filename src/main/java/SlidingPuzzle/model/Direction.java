package SlidingPuzzle.model;

/**
 * Enum representing the four cardinal directions.
 */
public enum Direction {

    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1);

    private final int rowChange;
    private final int colChange;

    Direction(int rowChange, int colChange) {
        this.rowChange = rowChange;
        this.colChange = colChange;
    }

    /**
     * Returns the change in the row index when moving a step in this
     * direction.
     *
     * @return the change in the row index when moving a step in this
     * direction
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * Returns the change in the column index when moving a step in this
     * direction.
     *
     * @return the change in the column index when moving a step in this
     * direction
     */
    public int getColChange() {
        return colChange;
    }

    /**
     * Returns the direction that corresponds to the changes in the row index
     * and the column index specified.
     *
     * @param rowChange the change in the row index
     * @param colChange the change in the column index
     * @return the direction that corresponds to the changes in the row index
     * and the column index specified
     */
    public static Direction of(int rowChange, int colChange) {
        for (var direction : values()) {
            if (direction.rowChange == rowChange && direction.colChange == colChange) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

}
