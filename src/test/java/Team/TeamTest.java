package Team;

import Map.Tile;
import Map.TileType;
import Player.Player;
import Player.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class TeamTest {

    Team team;
    Player[] players;

    @Before
    public void setUp() {
        team = new Team(1);
        players = new Player[2];
        players[0] = Mockito.mock(Player.class);
        players[1] = Mockito.mock(Player.class);
        team.registerObserver(players[0]);
        team.registerObserver(players[1]);
    }

    @After
    public void tearDown() {
        team = null;
        players = null;
    }

    @Test
    public void setSubjectLastExplored_TileUpdated() {
        int newX = 1;
        int newY = 1;

        Position tilePosition = new Position(newX, newY);
        Tile newTile = new Tile(TileType.GRASS, tilePosition);
        team.setSubjectLastExplored(newTile);

        assertEquals(newTile, team.getSubjectLastExplored());
    }

    @Test
    public void registerObserver() {
        int size = team.getObservers().size();

        assertEquals(2, size);
    }

    @Test
    public void registerObserver_NoDuplicates() {
        team.registerObserver(players[0]);

        int size = team.getObservers().size();

        assertEquals(2, size);
    }

    @Test
    public void setNextPlayer(){
        team.setNextPlayer();

        assertEquals(players[1], team.currentPlayer());
    }

    @Test
    public void setNextPlayer_Resets(){
        team.setNextPlayer();
        team.setNextPlayer();

        assertEquals(players[0], team.currentPlayer());
    }

    @Test
    public void currentPlayer(){
        assertEquals(players[0], team.currentPlayer());
    }

}
