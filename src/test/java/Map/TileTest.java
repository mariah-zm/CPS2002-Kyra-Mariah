package Map;

import Map.Tile;
import Map.TileType;
import Position.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTest {

    Tile tile;

    @Before
    public void setUp(){
        tile = new Tile(TileType.GRASS, new Position(1, 0));
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

    //test for html getter
    @Test
    public void getHTML(){
        String html = tile.getHtml();
        assertEquals("<td><div class=\"grass\">", html);
    }

    //test for position getter
    @Test
    public void getPosition(){
        int x = 1;
        int y = 0;

        assertEquals(x, tile.getPosition().getX());
        assertEquals(y, tile.getPosition().getY());
    }

}
