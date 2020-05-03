import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GameTest {

    Game game;
    Map map;

    //@Mock private Runtime mockRuntime;

    @Before
    public void setUp(){
        this.game = new Game();
        this.map = new Map(10);
    }

    @After
    public void tearDown(){
        this.game = null;
        this.map = null;
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
    public void setNumPlayers_Correct() {
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
    public void setMapSize_Correct() {
        game.setNumPlayers(6);
        boolean result = game.setMapSize(30);
        assertTrue(result);
    }

    //checking that the files are generated
    @Test
    public void HTML_FileTest() throws IOException {
        game.setNumPlayers(2);
        game.map = new Map(5);
        game.createPlayers();
        game.generateHTML();
        assertNotNull(game.htmlFiles);
    }

    //checking that the files have the correct name
    @Test
    public void HTML_FileNameTest() throws IOException {
        game.setNumPlayers(2);
        game.map = new Map(5);
        game.createPlayers();
        game.generateHTML();
        assertTrue((String.valueOf(Paths.get(game.htmlFiles[0].getAbsolutePath()))).contains("map_player_1.html"));
        assertTrue((String.valueOf(Paths.get(game.htmlFiles[1].getAbsolutePath()))).contains("map_player_2.html"));
    }

    //checking that the previous positions are uncovered on the grid for each individual player
    @Test
    public void HTML_uncoveredTilesTest_moreThanOnePlayer() throws IOException {
        game.setNumPlayers(2);
        game.map = new Map(5);
        game.createPlayers();
        game.generateHTML();
        //the player has only visited the initial tile
        //therefore there should only be one green tile uncovered
        String grass_code = "<td><div class=\"grass\">";

        String file_content = new String(Files.readAllBytes(Paths.get(game.htmlFiles[0].getAbsolutePath())));
        int count = StringUtils.countMatches(file_content, grass_code);
        assertEquals(1,count);

        String file_content_player2 = new String( Files.readAllBytes(Paths.get(game.htmlFiles[1].getAbsolutePath())));
        int count2 = StringUtils.countMatches(file_content_player2, grass_code);
        assertEquals(1,count2);
    }

    //current tile should contain a person symbol
    @Test
    public void HTML_currentPositionTest() throws IOException{
        game.setNumPlayers(2);
        game.map = new Map(5);
        game.createPlayers();
        game.generateHTML();
        String currentPositionMark = "<p>&#127939;</p>";
        String file_content = new String ( Files.readAllBytes(Paths.get(game.htmlFiles[0].getAbsolutePath())));
        assertTrue(file_content.contains(currentPositionMark));
    }

    //grid should not display water or treasure tiles at this point
    @Test
    public void HTML_noTreasure() throws IOException {
        game.setNumPlayers(2);
        game.map = new Map(5);
        game.createPlayers();
        game.generateHTML();
        String treasureMark = "<p>&#FFFB40;</p>";
        String waterMark = "<p>&#2FA6F1;</p>";
        String file_content = new String( Files.readAllBytes(Paths.get(game.htmlFiles[0].getAbsolutePath())));
        assertFalse(file_content.contains(treasureMark));
        assertFalse(file_content.contains(waterMark));
    }

    /*
    @Test
    public void openHTML_FileOpened() throws IOException {
        game.setNumPlayers(2);
        game.map = new Map(5);
        game.createPlayers();
        game.generateHTML();

        String path = game.htmlFiles[0].getAbsolutePath();

        game.openHTML(path);
        when(Runtime.getRuntime()).thenReturn(mockRuntime);
        verify(mockRuntime.exec(path));
    }*/

    @Test
    public void createPlayersTest() {
        game.setNumPlayers(2);
        game.map = new Map(5);
        game.createPlayers();
        int result = game.players.length;
        assertEquals(2, result);
    }

    //testing that the correct list of winners is returned - 1 winner
    @Test
    public void listOfWinners_1(){
        game.winners.add(1);

        String result = game.listOfWinners();
        assertTrue(result.contains("Player 1"));
    }

    //testing that the correct list of winners is returned - 2 winners
    @Test
    public void listOfWinners_2(){
        game.winners.add(1);
        game.winners.add(2);

        String result = game.listOfWinners();
        assertTrue(result.contains("Player 1 and Player 2"));
    }

    //testing that the correct list of winners is returned - 3 winners
    @Test
    public void listOfWinners_3(){
        game.winners.add(1);
        game.winners.add(2);
        game.winners.add(3);

        String result = game.listOfWinners();
        assertTrue(result.contains("Player 1, Player 2 and Player 3"));
    }
}