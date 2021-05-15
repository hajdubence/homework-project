package SlidingPuzzle.model;

//import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    static Stream<Position> positionProvider() {
        return Stream.of(new Position(0, 0),
                new Position(0, 2),
                new Position(3, 0),
                new Position(1, 1),
                new Position(-1, -1));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void moveTo(Position pos) {
        assertEquals(new Position(pos.row()-1,pos.col()), new Position(pos.row(),pos.col()).moveTo(Direction.UP));
        assertEquals(new Position(pos.row()+1,pos.col()), new Position(pos.row(),pos.col()).moveTo(Direction.DOWN));
        assertEquals(new Position(pos.row(),pos.col()-1), new Position(pos.row(),pos.col()).moveTo(Direction.LEFT));
        assertEquals(new Position(pos.row(),pos.col()+1), new Position(pos.row(),pos.col()).moveTo(Direction.RIGHT));
    }

    @ParameterizedTest
    @MethodSource("positionProvider")
    void testToString(Position pos) {
        assertEquals("(" + pos.row() + "," + pos.col() + ")", pos.toString());
    }

}