package SlidingPuzzle.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DirectionTest {

    @Test
    void of() {
        assertThrows(IllegalArgumentException.class, () -> Direction.of(1, 1));
        assertThrows(IllegalArgumentException.class, () -> Direction.of(0, 0));
        assertEquals(Direction.UP, Direction.of(-1, 0));
        assertEquals(Direction.DOWN, Direction.of(1, 0));
        assertEquals(Direction.LEFT, Direction.of(0, -1));
        assertEquals(Direction.RIGHT, Direction.of(0, 1));
    }

}