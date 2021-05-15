package SlidingPuzzle.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SlidingPuzzleModelTest {

    SlidingPuzzleModel model;

    @BeforeEach
    void init() {
        model = new SlidingPuzzleModel();
    }

    @Test
    void isSquare() {
        assertTrue(model.isSquare(new Position(1 , 2)));
        assertFalse(model.isSquare(new Position(2 , 1)));
    }

    @Test
    void isEmpty() {
        assertFalse(model.isEmpty(new Position(0 , 0)));
        assertFalse(model.isEmpty(new Position(1 , 1)));
        assertTrue(model.isEmpty(new Position(1 , 0)));
    }

    @Test
    void isEndSState() {
        assertFalse(model.isEndState());
    }

    @Test
    void move() {
        assertThrows(IllegalArgumentException.class, () -> model.move(new Position(0 , 0) , Direction.DOWN));
        assertThrows(IllegalArgumentException.class, () -> model.move(new Position(0 , 0) , Direction.UP));
        assertThrows(IllegalArgumentException.class, () -> model.move(new Position(1 , 1) , Direction.DOWN));
        assertThrows(IllegalArgumentException.class, () -> model.move(new Position(1 , 0) , Direction.RIGHT));
        assertDoesNotThrow(() -> model.move(new Position(1 , 1),Direction.LEFT));
    }

    @Test
    void getValidMoves() {
        assertEquals(EnumSet.of(Direction.LEFT), model.getValidMoves(new Position(1 , 1)));
        assertEquals(EnumSet.noneOf(Direction.class), model.getValidMoves(new Position(1 , 2)));
    }

    @Test
    void canBeMoved() {
        List<Position> expected = new ArrayList<Position>();
        expected.add(new Position(1,1));
        expected.add(new Position(1,3));
        expected.add(new Position(1,5));
        expected.add(new Position(1,7));
        assertEquals(expected, model.canBeMoved());
    }
}