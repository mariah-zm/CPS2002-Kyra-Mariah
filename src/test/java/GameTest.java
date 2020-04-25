import org.apache.commons.lang.StringUtils;
import org.codehaus.plexus.util.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        game.generateHTMLFiles(map);
        assertNotNull(game.htmlFiles);
    }


        @Test //checking that the files have the correct name
    public void HTML_FileNameTest() throws IOException {

        game.generateHTMLFiles(map);
        assertTrue(new File("C:\\Users\\kyra_\\OneDrive\\Desktop\\CPS2002\\src\\generated_HTML\\map_player_1.html").exists());
       assertTrue(new File("C:\\Users\\kyra_\\OneDrive\\Desktop\\CPS2002\\src\\generated_HTML\\map_player_2.html").exists());

    }

    @Test //checking that the previous positions are uncovered on the grid
    public void HTML_uncoveredTilesTest() throws IOException {
        game.generateHTMLFiles(map);
        //the player has only visited the initial tile
        //therefore there should only be one green tile uncovered
        String green_hexcode = "#67E240";
        String file_content = new String ( Files.readAllBytes( Paths.get("C:\\Users\\kyra_\\OneDrive\\Desktop\\CPS2002\\src\\generated_HTML\\map_player_1.html") ) );
        assertTrue(file_content.contains(green_hexcode));
       // int count = StringUtils.countMatches(String.valueOf(game.htmlFiles[0]), green_hexcode);
       // assertEquals(1,count);

    }

}