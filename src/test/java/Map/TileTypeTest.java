package Map;

import Map.TileType;
import org.junit.Test;
import static org.junit.Assert.*;

public class TileTypeTest {

    @Test
    public void getKey_GRASS() {
        assertEquals("grass", TileType.GRASS.getKey());
    }

    @Test
    public void getKey_WATER() {
        assertEquals("water", TileType.WATER.getKey());
    }

    @Test
    public void getKey_TREASURE() {
        assertEquals("treasure", TileType.TREASURE.getKey());
    }

}
