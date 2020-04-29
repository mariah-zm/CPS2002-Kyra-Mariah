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
    HTMLGenerator generator;

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
       game.htmlFiles = null;
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

    @Test //checking that the files are generated
    public void HTML_FileTest() throws IOException {

        game.generateHTML();
        assertNotNull(game.htmlFiles);
    }

    @Test //checking that the files have the correct name
    public void HTML_FileNameTest() throws IOException {

        game.generateHTML();
        assertTrue(new File(Paths.get("").toAbsolutePath().toString()+"map_player_1.html").exists());
        assertTrue(new File(Paths.get("").toAbsolutePath().toString()+"map_player_2.html").exists());


    }

    @Test //checking that the previous positions are uncovered on the grid for each individual player
    public void HTML_uncoveredTilesTest_moreThanOnePlayer() throws IOException {

        game.generateHTML();
        //the player has only visited the initial tile
        //therefore there should only be one green tile uncovered
        String grass_code = "<td><div class=\"grass\">";

        String file_content = new String( Files.readAllBytes(Paths.get(game.htmlFiles[0].getAbsolutePath())));
        assertTrue(file_content.contains(grass_code));
        int count = StringUtils.countMatches(file_content, grass_code);
        assertEquals(1,count);

        String file_content_player2 = new String( Files.readAllBytes(Paths.get(game.htmlFiles[1].getAbsolutePath())));
        assertTrue(file_content_player2.contains(grass_code));
        int count2 = StringUtils.countMatches(file_content_player2, grass_code);
        assertEquals(1,count2);

    }


    @Test
    //current tile should contain a person symbol
    public void HTML_currentPositionTest() throws IOException{
        game.generateHTML();
        String currentPositionMark = "<p>&#127939;</p>";
        String file_content = new String ( Files.readAllBytes(Paths.get(game.htmlFiles[0].getAbsolutePath())));
        assertTrue(file_content.contains(currentPositionMark));


    }

    @Test
    //grid should not display water or treasure tiles at this point

    public void HTML_noTreasure() throws IOException {
        game.generateHTML();
     String treasureMark = "<p>&#FFFB40;</p>";
     String waterMark = "<p>&#2FA6F1;</p>";
     String file_content = new String( Files.readAllBytes(Paths.get(game.htmlFiles[0].getAbsolutePath())));
     assertFalse(file_content.contains(treasureMark));
     assertFalse(file_content.contains(waterMark));
 }



}