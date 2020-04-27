import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {

    Game game;
    Map map;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        map = new Map(25);

    }

    @After
    public void tearDown() throws Exception {
        game = null;
    }

    @Test
    public void setNumPlayers_LessThan2() {
        boolean result = game.setNumPlayers(1);
        assertFalse(result);
    }

    @Test
    public void setNumPlayers_MoreThan8() {
        boolean result = game.setNumPlayers(10);
        assertFalse(result);
    }
    @Test
    public void setNumPlayers() {
        boolean result = game.setNumPlayers(5);
        assertTrue(result);
    }
    @Test
    public void setMapSize_TooSmall() {
        game.setNumPlayers(4);
        boolean result = game.setMapSize(4);
        assertFalse(result);
    }

    @Test
    public void setMapSize_TooBig() {
        game.setNumPlayers(8);
        boolean result = game.setMapSize(60);
        assertFalse(result);
    }

    @Test
    public void setMapSize() {
        game.setNumPlayers(6);
        boolean result = game.setMapSize(30);
        assertTrue(result);
    }

}