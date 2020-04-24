import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class GameTest {

    Game game;
    Map map;
    Player player1;
    Player player2;

    @Before
    public void setUp() throws Exception {
        map = new Map(25);
        game = new Game();
        player1 = new Player(map);
        game.players.add(player1);
        player2 = new Player(map);
        game.players.add(player2);

    }

    @After
    public void tearDown() throws Exception {
        game.htmlFiles = null;
        game.players =null;
        game = null;
        map= null;
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

    @Test //checking that the files are being generated
    public void HTML_FileTest() throws IOException {

        game.generateHTMLFiles();
        assertNotNull(game.htmlFiles);
    }


        @Test //checking that the files have the correct name
    public void HTML_FileNameTest() throws IOException {

        game.generateHTMLFiles();
       // File file = new File("C:\\map_player_1.html");
        assertTrue(new File("C:\\map_player_1.html").exists());

    }

    @Test //checking that the current position is marker with an asterisk
    public void HTML_currentPositionTest() throws IOException {
        Position test_position = new Position(10,10);
        player1.setPosition(test_position);

        game.generateHTMLFiles();


    }

}