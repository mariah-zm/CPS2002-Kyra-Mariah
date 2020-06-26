package Map;

import org.junit.Test;

import static org.junit.Assert.*;

public class MapFactoryTest {
    @Test
    public void getSafeMap() {
        MapMode mode = MapMode.SAFE;
        Map map = MapFactory.getMap(mode);
        assertTrue(map instanceof SafeMap);
    }
    @Test
    public void getHazardMap() {
        MapMode mode = MapMode.HAZARDOUS;
        Map map = MapFactory.getMap(mode);
        assertTrue(map instanceof HazardMap);
    }
}