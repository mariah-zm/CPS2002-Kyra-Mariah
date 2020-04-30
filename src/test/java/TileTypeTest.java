import org.junit.Test;
import static org.junit.Assert.*;

public class TileTypeTest {

    @Test
    public void getHexCode() {
        assertEquals("grass", TileType.GRASS.getHexCode());
        assertEquals("water", TileType.WATER.getHexCode());
        assertEquals("treasure", TileType.TREASURE.getHexCode());
    }

}
