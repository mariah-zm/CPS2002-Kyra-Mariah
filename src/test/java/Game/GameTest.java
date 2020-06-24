package Game;

import Game.Game;
import Map.SafeMap;
import Player.Player;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GameTest {

    Game game;

    @Before
    public void setUp(){
        this.game = new Game();
        Game.map = SafeMap.getInstance();
        Game.map.setSize(10, 5);
        Game.map.generate();
    }

    @After
    public void tearDown(){
        this.game = null;
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

    //checking that the files are generated
    @Test
    public void HTML_FileTest() throws IOException {
        game.setNumPlayers(2);
        game.createPlayers();
        game.generateHTML();
        assertNotNull(game.htmlFiles);
    }

    //checking that the files have the correct name
    @Test
    public void HTML_FileNameTest() throws IOException {
        game.setNumPlayers(2);
        game.createPlayers();
        game.generateHTML();
        assertTrue((String.valueOf(Paths.get(game.htmlFiles[0].getAbsolutePath()))).contains("map_player_1.html"));
        assertTrue((String.valueOf(Paths.get(game.htmlFiles[1].getAbsolutePath()))).contains("map_player_2.html"));
    }

    //checking that the previous positions are uncovered on the grid for each individual player
    @Test
    public void HTML_uncoveredTilesTest_moreThanOnePlayer() throws IOException {
        game.setNumPlayers(2);
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
        game.createPlayers();
        game.generateHTML();
        String currentPositionMark = "<p>&#127939;</p>";
        String file_content = new String ( Files.readAllBytes(Paths.get(game.htmlFiles[0].getAbsolutePath())));
        assertTrue(file_content.contains(currentPositionMark));
    }

    //Grid should not display water or treasure tiles at this point
    @Test
    public void HTML_noTreasure() throws IOException {
        game.setNumPlayers(2);
        game.createPlayers();
        game.generateHTML();
        String treasureMark = "<p>&#FFFB40;</p>";
        String waterMark = "<p>&#2FA6F1;</p>";
        String file_content = new String( Files.readAllBytes(Paths.get(game.htmlFiles[0].getAbsolutePath())));
        assertFalse(file_content.contains(treasureMark));
        assertFalse(file_content.contains(waterMark));
    }

    //Testing that the files are opened
    @Test
    public void openHTML_FileOpened(){
        boolean isAsserted;

        try {
            game.setNumPlayers(2);
            game.createPlayers();
            game.generateHTML();

            String path1 = game.htmlFiles[0].getAbsolutePath();
            String path2 = game.htmlFiles[1].getAbsolutePath();

            game.openHTML(path1);
            game.openHTML(path2);

            isAsserted = true;
        }
        catch(Exception ex){
            isAsserted = false;
        }

        assertTrue(isAsserted);
    }

    @Test
    public void createPlayersTest() {
        game.setNumPlayers(2);
        game.createPlayers();
        int result = game.players.length;
        assertEquals(2, result);
    }

    //Testing singleton pattern
    @Test
    public void sameMapInstanceTest(){
        game.setNumPlayers(2);
        game.createPlayers();

        assertEquals(game.players[0].getMap().hashCode(), game.players[1].getMap().hashCode());
    }

    //Testing that the correct list of winners is returned - 1 winner
    @Test
    public void listOfWinners_1(){
        Player player = new Player(Game.map, 1);

        game.winners.add(player);

        String result = game.listOfWinners();
        assertTrue(result.contains(player.getPlayerID()));
    }

    //Testing that the correct list of winners is returned - 2 winners
    @Test
    public void listOfWinners_2(){
        Player player1 = new Player(Game.map, 1);
        Player player2 = new Player(Game.map, 2);

        game.winners.add(player1);
        game.winners.add(player2);

        String output = player1.getPlayerID() + " and " + player2.getPlayerID();
        String result = game.listOfWinners();
        assertTrue(result.contains(output));
    }

    //Testing that the correct list of winners is returned - 3 winners
    @Test
    public void listOfWinners_3(){
        Player player1 = new Player(Game.map, 1);
        Player player2 = new Player(Game.map, 2);
        Player player3 = new Player(Game.map, 3);

        game.winners.add(player1);
        game.winners.add(player2);
        game.winners.add(player3);

        String output = player1.getPlayerID() + ", " + player2.getPlayerID() + " and " + player3.getPlayerID();
        String result = game.listOfWinners();
        assertTrue(result.contains(output));
    }
}