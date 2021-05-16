package SlidingPuzzle.results;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


/**
 *  This class holds the result of one game.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result implements Comparable<Result> {

    private String name;
    private int moves;

    @Override
    public int compareTo(Result otherResult){
        return Integer.compare(moves, otherResult.moves);
    }

}
