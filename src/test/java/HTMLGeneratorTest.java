
import Map.Map;
import Map.SafeMap;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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

    //ensuring headerHTML is returned and not null
    @Test
    public void headerHTML_NotNullOrEmpty(){
        String html = generator.headerHTML(1);
        assertNotNull(html);
        assertFalse(html.isEmpty());
    }

    //checking that the player number is displayed
    @Test
    public void headerHTML_ContainsPlayerNumber(){
        String html = generator.headerHTML(1);
        String playerNumber = "Player 1";
        assertTrue(html.contains(playerNumber));
    }

    //ensuring gridHTML is returned and not null
    @Test
    public void gridHTML_NotNullOrEmpty(){
        Map map = SafeMap.getInstance();
        map.setSize(10, 2);
        map.generate();

        Player player = new Player(map);
        String html = generator.gridHTML(player);
        assertNotNull(html);
        assertFalse(html.isEmpty());
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

    //all other methods are tested in GameTest
}