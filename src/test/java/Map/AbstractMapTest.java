package Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class AbstractMapTest {

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
    @Test
    public void getSize(){
        assertEquals(25, map.getSize());
    }

    //testing setter - not valid
    @Test
    public void setSize_TooSmall() {
        boolean result = map.setSize(4, 4);
        assertFalse(result);
    }

    //testing setter - not valid
    @Test
    public void setSize_TooBig() {
        boolean result = map.setSize(60, 8);
        assertFalse(result);
    }

    //testing setter - valid
    @Test
    public void setSize_Correct() {
        boolean result = map.setSize(30, 6);
        assertTrue(result);
    }

    //testing that the correct number of tiles is generated
    @Test
    public void gridSize() {
        map.setSize(25, 5);
        map.generate();

        int size = map.getGrid().length * map.getGrid()[0].length;

        assertEquals(25*25, size);
    }

    //testing that map is generated and no elements remain empty
    @Test
    public void gridNotNull(){
        boolean containsNull = (grid.contains(null)) && (map.getGrid() != null);
        assertFalse(containsNull);
    }
    @Test
    public void treasureTile(){
        int occurrences = (int) grid.stream().filter(tile -> tile.getType().equals(TileType.TREASURE)).count();
        assertEquals(1, occurrences);
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

        //asserting that at least one is a Grass Map.Tile
        boolean notAllWater = surroundingTiles.stream().anyMatch(tile -> tile.getType().equals(TileType.GRASS));
        assertTrue(notAllWater);
    }
}