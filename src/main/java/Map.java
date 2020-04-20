import java.util.Random;

public class Map {

    private int size;
    private Tile[][] grid;

    //class constructor
    public Map(int size){
        this.size = size;
        this.grid = new Tile[size][size];
        generate();
    }

    //filling the grid elements with tile types
    private void generate(){

    }

}
