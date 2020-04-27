import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
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
        generator = new HTMLGenerator();
        map1 = new Map(10);
        game = new Game();
        player1 = new Player(map1);
        game.players.add(player1);

    }

    @After
    public void tearDown() throws Exception {
        generator = null;
        game.players =null;
        game = null;
        map1= null;
        player1 = null;

    }


/*    @Test //checking that the files are being generated
    public void HTML_FileTest() throws IOException {

        generator.generateHTMLFiles(player1, game);
        assertNotNull(generator.htmlFiles);
    }
*/

    @Test //checking that the files have the correct name
    public void HTML_FileNameTest() throws IOException {

        generator.generateHTMLFiles(player1);
        assertTrue(new File("C:\\Users\\kyra_\\OneDrive\\Desktop\\CPS2002\\src\\generated_HTML\\map_player_1.html").exists());

    }

    @Test //checking that the previous positions are uncovered on the grid
    public void HTML_uncoveredTilesTest() throws IOException {
        player1.setPosition(player1.initial);
        generator.generateHTMLFiles(player1);

        //the player has only visited the initial tile
        //therefore there should only be one green tile uncovered
        String grass_code = "<td><div class=\"grass\">";
        String file_content = new String ( Files.readAllBytes( Paths.get("C:\\Users\\kyra_\\OneDrive\\Desktop\\CPS2002\\src\\generated_HTML\\map_player_1.html") ) );
        assertTrue(file_content.contains(grass_code));
        int count = StringUtils.countMatches(file_content, grass_code);
        assertEquals(1,count);

    }

    @Test
    //current tile should contain a person symbol
    public void HTML_currentPositionTest() throws IOException{
        player1.setPosition(player1.initial);
        generator.generateHTMLFiles(player1);
        String currentPositionMark = "<p>&#127939;</p>";
        String file_content = new String ( Files.readAllBytes( Paths.get("C:\\Users\\kyra_\\OneDrive\\Desktop\\CPS2002\\src\\generated_HTML\\map_player_1.html") ) );
        assertTrue(file_content.contains(currentPositionMark));


    }
}