package Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class HazardMapTest {
    Map map = HazardMap.getInstance();
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
        //number of grass tiles should be in this range
        int minimum = (int) (grid.size()*0.65)-1;
        int maximum = (int) (grid.size()*0.75)-1;
        assertTrue(minimum<= occurrences && occurrences <= maximum);
    }

    //testing number of water tiles
    @Test
    public void numberOfWaterTiles(){
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.WATER)).count();
        //number of water tiles should be in this range
        int minimum = grid.size() - (int) (grid.size()*0.75);
        int maximum = grid.size() - (int) (grid.size()*0.65);
        assertTrue(minimum<= occurrences && maximum>= occurrences);
    }



}