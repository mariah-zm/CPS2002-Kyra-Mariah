package Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

//to be used for random generation of path in map generation
public enum Direction {
    UP('U'),
    DOWN('D'),
    RIGHT('R'),
    LEFT('L');

    private static final List<Direction> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    private char direction;

    Direction(char direction){
        this.direction = direction;
    }

    //picks a random direction
    public static Direction randomDirection()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    //returns corresponding Player.Direction value
    public static Direction getDirection(char direction) {
        for (Direction dir : values()) {
            if (dir.direction == direction){
                return dir;
            }
        }
        return null;
    }
}
