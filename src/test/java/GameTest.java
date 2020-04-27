import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GameTest {

    Game game;
    Map map1,map2;
    Player player1;
    Player player2;

    @Before
    public void setUp() throws Exception {
        map1 = new Map(4);
        map2 = map1;
        game = new Game();
        player1 = new Player(map1);
        game.players.add(player1);
        player2 = new Player(map2);
        game.players.add(player2);

    }

    @After
    public void tearDown() throws Exception {
     //   game.htmlFiles = null;
        game.players =null;
        game = null;
        map1= null;
        map2=null;
        player1 = null;
       player2 = null;

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