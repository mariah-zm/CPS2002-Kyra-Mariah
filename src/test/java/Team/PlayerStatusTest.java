package Team;

import Map.TileType;
import Team.PlayerStatus;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerStatusTest {

    @Test
    public void getStatus_GRASS(){
        assertEquals(PlayerStatus.SAFE, PlayerStatus.getStatus(TileType.GRASS));
    }

    @Test
    public void getStatus_WATER(){
        assertEquals(PlayerStatus.DEAD, PlayerStatus.getStatus(TileType.WATER));
    }

    @Test
    public void getStatus_TREASURE(){
        assertEquals(PlayerStatus.WINS, PlayerStatus.getStatus(TileType.TREASURE));
    }

    @Test
    public void getStatus_NULL(){
        assertEquals(null, PlayerStatus.getStatus(null));
    }

}
