package Position;

import Position.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class PositionTest {

    Position test_position;

    @Before
    public void setUp() throws Exception {
        test_position = new Position(1,1);
    }

    @After
    public void tearDown() throws Exception {
        test_position = null;
    }

    @Test
    public void getX_test() {
       int x_coordinate = test_position.getX();
       assertEquals(1,x_coordinate);
    }

    @Test
    public void getY_test() {
        int y_coordinate = test_position.getY();
        assertEquals(1,y_coordinate);
    }
    @Test
    public void setX_test() {
        test_position.setX(2); //should change the x_coordinate
        //using the previously tested function
        assertEquals(2, test_position.getX());
    }

    @Test
    public void setY_test() {
        test_position.setY(2); //should change the x_coordinate
        //using the previously tested function
        assertEquals(2, test_position.getY());
    }
}
