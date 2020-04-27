import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class DirectionTest {

    List<Direction> values = Arrays.asList(Direction.values());

    @Before
    public void setUp(){
        values = Arrays.asList(Direction.values());
    }

    @After
    public void tearDown(){
        values = null;
    }

    @Test
    public void getRandomDirection(){
        assertTrue(values.contains(Direction.randomDirection()));
    }

    @Test
    public void getDirection_UP(){
        assertEquals(Direction.UP, Direction.getDirection('U'));
    }

    @Test
    public void getDirection_DOWN(){
        assertEquals(Direction.DOWN, Direction.getDirection('D'));
    }

    @Test
    public void getDirection_RIGHT(){
        assertEquals(Direction.UP, Direction.getDirection('R'));
    }

    @Test
    public void getDirection_LEFT(){
        assertEquals(Direction.UP, Direction.getDirection('L'));
    }
}