import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {

    Game game;
    Player player1;
    Player player2;

    @Before
    public void setUp() throws Exception {
        game = new Game();
    }

    @After
    public void tearDown() throws Exception {
        game = null;
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
        boolean result = game.setMapSize(4,4);
        assertFalse(result);
    }

    @Test
    public void setMapSize_TooBig() {
        boolean result = game.setMapSize(60,4);
        assertFalse(result);
    }

    @Test
    public void setMapSize() {
        boolean result = game.setMapSize(30,6);
        assertTrue(result);
    }

    @Test
    public void main(){
        String [] args = {};
    }

}