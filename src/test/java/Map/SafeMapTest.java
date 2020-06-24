package Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SafeMapTest {

    Map map = SafeMap.getInstance();
    List<Tile> grid;

    @Before
    public void setUp(){
        map.setSize(25, 5);
        map.generate();

        grid = new ArrayList<>();
        for(Tile[] row : map.getGrid()){
            Collections.addAll(grid, row);
        }
    }

    @After
    public void tearDown(){
        map = null;
        grid = null;
    }

    //testing number of grass tiles
    @Test
    public void numberOfGrassTiles(){
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.GRASS)).count();
        int minimum = (int) (grid.size()*0.90)-1;
        assertTrue(minimum <= occurrences);
    }

    //testing number of water tiles
    @Test
    public void numberOfWaterTiles(){
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.WATER)).count();
        int maximum = grid.size() - (int) (grid.size()*0.90);
        assertTrue(maximum >= occurrences);
    }

}
