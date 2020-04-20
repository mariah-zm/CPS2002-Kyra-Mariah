import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class MapTest{

    Map map;
    List<Tile> grid;

    @Before
    public void setUp() throws Exception {
        map = new Map(25);

        grid = new ArrayList<>();
        for(Tile[] row : map.getGrid()){
            for(Tile tile : row){
                grid.add(tile);
            }
        }
    }

    @After
    public void tearDown() throws Exception {
        map = null;
        grid = null;
    }

    //test for size getter
    @Test
    public void getSize(){
        assertEquals(25, map.getSize());
    }

    //testing that map is generated and not null
    @Test
    public void gridNotNull(){
        boolean containsNull = (grid.contains(null));
        assertFalse(containsNull);
    }

    //testing grid size
    @Test
    public void gridSize(){
        assertEquals(25*25, grid.size());
    }

    //testing that the map contains one and only one treasure tile
    @Test
    public void treasureTile(){
        /*boolean containsTile = grid.stream().anyMatch(tile -> tile.getType().equals(TileType.TREASURE));
        assertTrue(containsTile);*/
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.TREASURE)).count();
        assertEquals(1, occurrences);
    }

    @Test
    public void generate() { //testing that map generation creates the required amount of tiles

    }

    @Test
    public void numberOfGrassTiles(){
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.GRASS)).count();
        int expected = (int) (grid.size()*0.80)-1;
        assertEquals(expected, occurrences);
    }

    @Test
    public void numberOfWaterTiles(){
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.WATER)).count();
        int expected = grid.size() - (int) (grid.size()*0.80);
        assertEquals(expected, occurrences);
    }
}
