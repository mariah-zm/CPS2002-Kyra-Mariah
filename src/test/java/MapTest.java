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

    //testing that the correct number of tiles is generated
    @Test
    public void gridSize(){
        assertEquals(25*25, grid.size());
    }

    //testing that map is generated and no elements remain empty
    @Test
    public void gridNotNull(){
        boolean containsNull = (grid.contains(null)) && (map.getGrid() != null);
        assertFalse(containsNull);
    }

    //testing that the map contains one and only one treasure tile
    @Test
    public void treasureTile(){
        /*boolean containsTile = grid.stream().anyMatch(tile -> tile.getType().equals(TileType.TREASURE));
        assertTrue(containsTile);*/
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.TREASURE)).count();
        assertEquals(1, occurrences);
    }

    //testing number of grass tiles
    @Test
    public void numberOfGrassTiles(){
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.GRASS)).count();
        int expected = (int) (grid.size()*0.80)-1;
        assertEquals(expected, occurrences);
    }

    //testing number of water tiles
    @Test
    public void numberOfWaterTiles(){
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.WATER)).count();
        int expected = grid.size() - (int) (grid.size()*0.80);
        assertEquals(expected, occurrences);
    }

    //testing that tiles surrounding treasure tile are not all water tiles
    @Test
    public void reachable(){
        int x=0, y=0; //coordinates of treasure tile

        outerLoop:
        for(; x < map.getSize(); x++){
            for(; y < map.getSize(); y++){
                if(map.getTile(x, y).getType() == TileType.TREASURE){
                    break outerLoop;
                }
            }
        }

        List<Tile> surroundingTiles = new ArrayList<>();

        for(int i=-1; i <= 1; i++){
            for(int j=-1; j <= 1; j++){
                if(map.isLegal(x+i, y+j)){
                    surroundingTiles.add(map.getTile(x+i,y+j));
                }
            }
        }

        boolean notAllWater = surroundingTiles.stream().anyMatch(tile -> tile.getType().equals(TileType.GRASS));
        assertTrue(notAllWater);
    }
}