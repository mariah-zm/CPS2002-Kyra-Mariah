package Game;

import Map.SafeMap;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class GameTest {

    Game game;

    @Before
    public void setUp(){
        this.game = new Game();
        game.map = SafeMap.getInstance();
        game.map.setSize(10, 5);
        game.map.generate();
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
    public void setNumTeams_TooBig(){
        game.setNumPlayers(2);
        assertFalse(game.setNumTeams(4));
    }

    @Test
    public void setNumTeams_TooSmall(){
        game.setNumPlayers(2);
        assertFalse(game.setNumTeams(1));
    }

    @Test
    public void setNumTeams_Correct(){
        game.setNumPlayers(4);
        assertTrue(game.setNumTeams(2));
    }

    @Test
    public void setNumTeams_ArraySize(){
        game.setNumPlayers(4);
        game.setNumTeams(2);

        assertEquals(2, game.teams.length);
    }

    @Test
    public void createPlayersTest() {
        game.setNumPlayers(2);
        game.createPlayers();
        int result = game.players.length;
        assertEquals(2, result);
    }

    @Test
    public void createTeams_ObserversListNotEmpty(){
        game.setNumPlayers(6);
        game.createPlayers();
        game.setNumTeams(2);
        game.createTeams();

        assertFalse(game.teams[0].getObservers().isEmpty());
    }

    @Test
    public void createTeams_numOfPlayers(){
        game.setNumPlayers(6);
        game.createPlayers();
        game.setNumTeams(2);
        game.createTeams();

        assertEquals(game.teams[0].getObservers().size(), game.teams[1].getObservers().size());
    }

    @Test
    public void displayTeams(){
        game.setNumPlayers(6);
        game.createPlayers();
        game.setNumTeams(2);
        game.createTeams();

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        game.displayTeams();
    }

    //Testing singleton pattern
    @Test
    public void sameMapInstanceTest(){
        game.setNumPlayers(2);
        game.createPlayers();

        assertEquals(game.players[0].getMap().hashCode(), game.players[1].getMap().hashCode());
    }

    @Test
    public void listOfWinners_Player_1(){
        game.winners.add("Player 1");

        String result = game.listOfWinners();
        assertTrue(result.contains("Player 1"));
    }

    @Test
    public void listOfWinners_Player_2(){
        game.winners.add("Player 1");
        game.winners.add("Player 2");

        String output = "Player 1 and Player 2";
        String result = game.listOfWinners();
        assertTrue(result.contains(output));
    }

    @Test
    public void listOfWinners_Player_3(){
        game.winners.add("Player 1");
        game.winners.add("Player 2");
        game.winners.add("Player 3");

        String output = "Player 1, Player 2 and Player 3";
        String result = game.listOfWinners();
        assertTrue(result.contains(output));
    }

    @Test
    public void listOfWinners_Team_1(){
        game.winners.add("Team 1");

        String result = game.listOfWinners();
        assertTrue(result.contains("Team 1"));
    }

    @Test
    public void listOfWinners_Team_2(){
        game.winners.add("Team 1");
        game.winners.add("Team 2");

        String output = "Team 1 and Team 2";
        String result = game.listOfWinners();
        assertTrue(result.contains(output));
    }

    @Test
    public void listOfWinners_Team_3(){
        game.winners.add("Team 1");
        game.winners.add("Team 2");
        game.winners.add("Team 3");

        String output = "Team 1, Team 2 and Team 3";
        String result = game.listOfWinners();
        assertTrue(result.contains(output));
    }

}