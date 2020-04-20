import org.junit.Test;
import static org.junit.Assert.*;

public class TileTypeTest {

    @Test
    public void getHexCode() {
        assertEquals("#67E240", TileType.GRASS.getHexCode());
        assertEquals("#2FA6F1", TileType.WATER.getHexCode());
        assertEquals("#FFFB40", TileType.TREASURE.getHexCode());
    }

}
