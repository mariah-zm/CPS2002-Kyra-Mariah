package HTML;

import Map.Map;
import Map.SafeMap;
import Map.Tile;
import Map.TileType;
import Player.Player;
import Player.PlayerStatus;
import Player.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class HTMLGeneratorTest {

    HTMLGenerator generator;

    @Before
    public void setUp(){
        generator = new HTMLGenerator();
    }

    @After
    public void tearDown(){
        generator = null;
    }

    //Ensuring headerHTML is returned and not null
    @Test
    public void headerHTML_NotNullOrEmpty(){
        String html = generator.headerHTML("Player 1");
        assertNotNull(html);
        assertFalse(html.isEmpty());
    }

    //Checking that the player number is displayed
    @Test
    public void headerHTML_ContainsPlayerNumber(){
        String playerNumber = "Player 1";
        String html = generator.headerHTML(playerNumber);
        assertTrue(html.contains(playerNumber));
    }

    //Ensuring gridHTML is returned and not null
    @Test
    public void gridHTML_NotNullOrEmpty(){
        Map map = SafeMap.getInstance();
        map.setSize(10, 2);
        map.generate();

        Player player = new Player(map,1);
        String html = generator.gridHTML(player);
        assertNotNull(html);
        assertFalse(html.isEmpty());
    }

    @Test
    public void gridHTML_TreasureIcon(){
        Map map = SafeMap.getInstance();
        map.setSize(5,2);
        map.generate();

        List<Tile> grid = new ArrayList<Tile>();
        for(Tile[] row : map.getGrid()){
            Collections.addAll(grid, row);
        }

        Position treasurePosition = new Position(0,0);

        for(Tile tile : grid){
            if(tile.getType() == TileType.TREASURE){
                treasurePosition = tile.getPosition();
            }
        }

        Player player = new Player(map,1);
        player.setPosition(treasurePosition);

        String html = generator.gridHTML(player);

        assertTrue(html.contains("&#128176;"));
    }

    @Test
    public void moveMessage_GRASS(){
        PlayerStatus status = PlayerStatus.SAFE;
        String message = "<h2>Press U, D, L or R to move.</h2></div>";
        assertEquals(message, generator.moveMessage(status));
    }

    @Test
    public void moveMessage_WATER(){
        PlayerStatus status = PlayerStatus.DEAD;
        String message = "<h2>YIKES! You discovered a water tile. Start again from your initial position.</h2></div>";
        assertEquals(message, generator.moveMessage(status));
    }

    @Test
    public void moveMessage_TREASURE(){
        PlayerStatus status = PlayerStatus.WINS;
        String message = "<h2>WELL DONE, YOU FOUND THE TREASURE!</h2></div>";
        assertEquals(message, generator.moveMessage(status));
    }

    //All other methods are tested in Game.GameTest
}