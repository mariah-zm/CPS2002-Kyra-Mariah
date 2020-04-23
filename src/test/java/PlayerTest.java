import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player;
    Map map = new Map(25);
    Position test_position;

    @Before
    public void setUp() {
        player = new Player(map);
        test_position = new Position(1,1);
    }

    @After
    public void tearDown()
    {
        player = null;
    }

    @Test
    public void setInitialTest() {
        Position start = player.setInitial();
        int x = start.getX();
        int y = start.getY();
        TileType result = player.getMap().getTile(x,y).getType();
        assertEquals(TileType.GRASS, result);
    }

    @Test
    public void moveUp() {

        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.UP);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x, new_x);
        assertEquals(y + 1, new_y);

    }

    @Test
    public void moveDown() {

        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.DOWN);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x, new_x);
        assertEquals(y - 1, new_y);
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

        player.setPosition(test_position);
        int actual_x = player.getCurrent().getX();
        int actual_y = player.getCurrent().getY();
        assertEquals(1, actual_x);
        assertEquals(1,actual_y);
    }


    @Test
    public void getCurrent(){
        player.setPosition(test_position);
        Position current= player.getCurrent();
        int x =current.getX();
        int y = current.getY();
        assertEquals(x, test_position.getX());
        assertEquals(y, test_position.getY());

    }

    @Test
    public void getMap(){

    }

}