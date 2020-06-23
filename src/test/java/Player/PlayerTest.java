package Player;

import Direction.Direction;
import Map.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {

    Player player;
    Map map = SafeMap.getInstance();
    Position test_position;

    @Before
    public void setUp() {
        map.setSize(10, 5);
        map.generate();
        player = new Player(map, 1);
        test_position = new Position(2,2);
    }

    @After
    public void tearDown()
    {
        player = null;
        map = null;
        test_position = null;
    }

    //ensuring the initial position is set to a Grass tile
    @Test
    public void setInitial() {
        Position start = player.getInitial();
        TileType result = player.getMap().getTile(start.getX(),start.getY()).getType();
        assertEquals(TileType.GRASS, result);
    }

    @Test
    public void move_Up() {
        player.setPosition(test_position);
        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.UP);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x-1, new_x);
        assertEquals(y, new_y);
    }

    @Test
    public void move_Down() {
        player.setPosition(test_position);
        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.DOWN);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x+1, new_x);
        assertEquals(y, new_y);
    }

    @Test
    public void move_Right() {
        player.setPosition(test_position);
        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.RIGHT);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x, new_x);
        assertEquals(y+1, new_y);
    }

    @Test
    public void move_Left() {
        player.setPosition(test_position);
        int x = player.getCurrent().getX();
        int y = player.getCurrent().getY();
        player.move(Direction.LEFT);
        int new_x = player.getCurrent().getX();
        int new_y = player.getCurrent().getY();

        assertEquals(x, new_x);
        assertEquals(y-1, new_y);
    }

    @Test
    public void move_LegalMove(){
        player.setPosition(test_position);
        boolean result = player.move(Direction.RIGHT);

        assertTrue(result);
    }

    @Test
    public void move_IllegalMove(){
        player.setPosition(new Position(0,0));
        boolean result = player.move(Direction.LEFT);

        assertFalse(result);
    }

    @Test
    public void setPosition(){
        player.setPosition(test_position);

        assertEquals(2, player.getCurrent().getX());
        assertEquals(2, player.getCurrent().getY());
    }

    @Test
    public void setPosition_InMapBounds(){
        boolean result = player.setPosition(test_position);

        assertTrue(result);
    }

    @Test
    public void setPosition_OutOfMapBounds(){
        boolean result = player.setPosition(new Position(-1, -1));

        assertFalse(result);
    }

    @Test
    public void getCurrent(){
        player.setPosition(test_position);
        Position current = player.getCurrent();

        assertEquals(2, current.getX());
        assertEquals(2, current.getY());
    }

    @Test
    public void getMap(){
        assertEquals(map, player.getMap());
    }

    @Test
    public void setStatus_getStatus_GRASS(){
        player.setStatus(TileType.GRASS);
        assertEquals(PlayerStatus.SAFE, player.getStatus());
    }

    @Test
    public void setStatus_getStatus_WATER(){
        player.setStatus(TileType.WATER);
        assertEquals(PlayerStatus.DEAD, player.getStatus());
    }

    @Test
    public void setStatus_getStatus_TREASURE(){
        player.setStatus(TileType.TREASURE);
        assertEquals(PlayerStatus.WINS, player.getStatus());
    }

    @Test
    public void getDiscoveredTiles_ContainsInitial(){
        int x = player.getInitial().getX();
        int y = player.getInitial().getY();
        Tile tile = map.getTile(x, y);
        assertTrue(player.getDiscoveredTiles().contains(tile));
    }

}