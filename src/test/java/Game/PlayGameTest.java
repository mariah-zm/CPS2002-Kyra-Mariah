package Game;

import Team.Team;
import Map.Map;
import Map.SafeMap;
import Player.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class PlayGameTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp(){
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void tearDown(){
        System.setOut(originalOut);
    }

    @Test
    public void displayTeams(){
        Team[] teams = new Team[2];
        teams[0] = new Team(1);
        teams[1] = new Team(2);

        Map map = SafeMap.getInstance();
        map.setSize(10, 2);
        map.generate();

        Player player1 = new Player(map,1);
        Player player2 = new Player(map,2);
        Player player3 = new Player(map,3);
        Player player4 = new Player(map,4);

        player1.addToTeam(teams[0]);
        player2.addToTeam(teams[0]);
        player3.addToTeam(teams[1]);
        player4.addToTeam(teams[1]);

        String output = "Team 1- Player 1- Player 2Team 2- Player 3- Player 4";
        PlayGame.displayTeams(teams);
        String content = outContent.toString().replaceAll("\\r\\n", "");
        String content2 = content.replaceAll("\\n","");
        //ignoring new line character since this might cause a false failure
        assertTrue(content2.contains(output));
    }
}