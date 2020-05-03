
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class HTMLGeneratorTest {

    HTMLGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new HTMLGenerator();
    }

    @After
    public void tearDown() throws Exception {
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
        Player player = new Player(new Map(5));
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
    public void moveMessage_WATER()throws IOException{
        PlayerStatus status = PlayerStatus.DEAD;
        String message = "<h2>YIKES! You discovered a water tile. Start again from your initial position.</h2></div>";
        assertEquals(message, generator.moveMessage(status));
    }

    @Test
    public void moveMessage_TREASURE()throws IOException{
        PlayerStatus status = PlayerStatus.WINS;
        String message = "<h2>WELL DONE, YOU FOUND THE TREASURE!</h2></div>";
        assertEquals(message, generator.moveMessage(status));
    }

    //all other methods are tested in GameTest
}