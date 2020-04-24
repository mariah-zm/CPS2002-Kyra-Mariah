import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import static org.junit.Assert.*;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class GameTest {

    Game game;

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Rule
    public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    @Before
    public void setUp(){
        game = new Game();
    }

    @After
    public void tearDown(){
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
    public void main_InputsFoundAndCorrect(){
        systemInMock.provideLines("2", "5");
        exit.expectSystemExitWithStatus(0);
        game.main(null);
    }

    @Test
    public void main_InputsNotImmediatelyCorrect(){
        systemInMock.provideLines("incorrect", "2", "incorrect", "5");
        exit.expectSystemExitWithStatus(0);
        game.main(null);
    }

    @Test
    public void main_noInputFound(){
        exit.expectSystemExitWithStatus(1);
        game.main(null);
    }
}