package SlidingPuzzle;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Results {
    private ArrayList<Result> list = new ArrayList<>();
}
