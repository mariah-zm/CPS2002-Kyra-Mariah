import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTest {

    Tile tile;

    @Before
    public void setUp(){
        tile = new Tile(TileType.GRASS);
    }

    @After
    public void tearDown(){
        tile = null;
    }

    @Test
    public void getType(){
        TileType type = tile.getType();
        assertEquals(TileType.GRASS, type);
    }

    @Test
    public void setUncovered(){
        tile.setUncovered();
        assertTrue(tile.getUncovered());
    }

    @Test
    public void getUncovered(){
        assertFalse(tile.getUncovered());
    }
}
