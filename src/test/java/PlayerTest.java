import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player;
    Map map = new Map();

    @Before
    public void setUp() {
        player = new Player(map);
    }

    @After
    public void tearDown()
    {
        player = null;
    }

    @Test
    public void setInitial() {
    }

    @Test
    public void moveUp() {

        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.UP);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x, new_x);
        assertEquals(y - 1, new_y);

    }

    @Test
    public void moveDown() {

        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.DOWN);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x, new_x);
        assertEquals(y + 1, new_y);
    }

    @Test
    public void moveRight() {

        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.RIGHT);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x + 1, new_x);
        assertEquals(y, new_y);

    }

    @Test
    public void moveLeft() {

        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.LEFT);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x - 1, new_x);
        assertEquals(y, new_y);
    }

    @Test
    public void setPosition(){

    }

    @Test
    public void getStatus(){

    }

    @Test
    public void getCurrent(){

    }

    @Test
    public void getMap(){

    }

}