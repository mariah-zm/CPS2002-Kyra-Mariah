import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//to be used for random generation of path in map generation
public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    private static final List<Direction> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    //picks a random direction
    public static Direction randomDirection()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
