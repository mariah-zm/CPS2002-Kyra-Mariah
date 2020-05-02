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

    //test for type getter
    @Test
    public void getType(){
        TileType type = tile.getType();
        assertEquals(TileType.GRASS, type);
    }

    //test for uncovered setter
    @Test
    public void setUncovered(){
        tile.setUncovered();
        assertTrue(tile.getUncovered());
    }

    //test for uncovered getter
    @Test
    public void getUncovered(){
        assertFalse(tile.getUncovered());
    }

    //test for html getter
    @Test
    public void getHTML(){
        String html = tile.getHtml();
        assertEquals("<td><div class=\"grass\">", html);
    }

}
