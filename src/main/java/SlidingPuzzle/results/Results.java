package SlidingPuzzle.results;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

/**
 *  A list of {@link Result} entries.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Results {
    private ArrayList<Result> list = new ArrayList<>();
}
