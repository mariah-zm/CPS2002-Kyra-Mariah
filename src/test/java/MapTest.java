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

    @Test
    public void isLegal_Correct(){
        int x = 5;
        int y = 5;
        assertTrue(map.isLegal(x, y));
    }

    @Test
    public void isLegal_Incorrect(){
        int x = 26;
        int y = -1;
        assertFalse(map.isLegal(x,y));
    }

    //testing number of water tiles
    @Test
    public void numberOfWaterTiles(){
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.WATER)).count();
        int expected = grid.size() - (int) (grid.size()*0.80);
        assertEquals(expected, occurrences);
    }

    //testing that tiles surrounding treasure tile are not all water tiles
    public void reachable(){
        int x=0, y=0; //coordinates of treasure tile

        //finding Treasure tile
        outerLoop:
        for(int i=0; i < map.getSize(); i++){
            for(int j=0; j < map.getSize(); j++){
                if(map.getTile(i, j).getType() == TileType.TREASURE){
                    x=i;
                    y=j;
                    break outerLoop;
                }
            }
        }

        List<Tile> surroundingTiles = new ArrayList<>();

        //creating a list of the surrounding tiles
        if(map.isLegal(x+1,y)) surroundingTiles.add(map.getTile(x+1,y));
        if(map.isLegal(x-1,y)) surroundingTiles.add(map.getTile(x-1,y));
        if(map.isLegal(x,y+1)) surroundingTiles.add(map.getTile(x,y+1));
        if(map.isLegal(x,y-1)) surroundingTiles.add(map.getTile(x,y-1));

        //asserting that at least one is a Grass Tile
        boolean notAllWater = surroundingTiles.stream().anyMatch(tile -> tile.getType().equals(TileType.GRASS));
        assertTrue(notAllWater);
    }
}