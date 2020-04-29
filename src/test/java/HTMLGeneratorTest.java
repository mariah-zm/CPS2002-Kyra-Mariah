
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class HTMLGeneratorTest {


    Game game;
    Map map1;
    Player player1;
    HTMLGenerator generator;


    @Before
    public void setUp() throws Exception {
        map1 = new Map(10);
        game = new Game();
        player1 = new Player(map1);
        game.players.add(player1);
        generator = new HTMLGenerator();




    }

    @After
    public void tearDown() throws Exception {
        generator = null;
        game.players =null;
        game = null;
        map1= null;
        player1 = null;

    }

    @Test
    public void winnerMessageTest()throws IOException{
        player1.status = PlayerStatus.WINS;
        game.generateHTML();
        String winnerMessage = "<h2>WINNER! WINNER! WINNER!</h2>";
        String file_content = new String ( Files.readAllBytes( Paths.get(Paths.get("").toAbsolutePath().toString()+"map_player_1.html") ) );
        assertTrue(file_content.contains(winnerMessage));
    }

    //all other methods are tested in GameTest
}