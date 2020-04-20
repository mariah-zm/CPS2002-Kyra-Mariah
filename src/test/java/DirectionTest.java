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
}