package SlidingPuzzle;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
