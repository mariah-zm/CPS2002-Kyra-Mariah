package Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SafeMapFactoryTest {
    public SafeMapFactory creator;
    public Map map;

    @Before
    public void setUp() {
        creator = new SafeMapFactory();
        map = creator.getMap();
    }

    @After
    public void tearDown() {
        creator = null;
        map = null;
    }

    @Test
    public void getMapInstance() {
        //verifying that the creator makes the right type of map
        assertTrue(map instanceof SafeMap);
    }
}