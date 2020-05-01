import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTest {

    Tile tile;
    Player player;

    @Before
    public void setUp(){
        tile = new Tile(TileType.GRASS);

        player = new Player( new Map(10));
    }

    @After
    public void tearDown(){
        tile = null;
        player = null;
    }

    @Test
    public void getType(){
        TileType type = tile.getType();
        assertEquals(TileType.GRASS, type);
    }



    @Test
    public void getUncovered(){
        assertFalse(tile.getUncovered(player).contains(tile));
    }
}
