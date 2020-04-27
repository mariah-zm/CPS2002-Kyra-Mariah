import java.util.Random;

public class Map {

    private int size;
    private Tile[][] grid;

    //class constructor
    public Map(int size) {
        this.size = size;
        this.grid = new Tile[size][size];
        generate();
    }

    //getter for grid
    public Tile[][] getGrid() {
        return grid;
    }

    //getter for size
    public int getSize() {
        return size;
    }

    //filling the grid elements with tile types
    private void generate() {
        //Setting the Treasure tile
        Random rnd = new Random();
        int treasureX = rnd.nextInt(this.size);
        int treasureY = rnd.nextInt(this.size);

        this.grid[treasureX][treasureY] = new Tile(TileType.TREASURE);

        //Setting number of grass tiles
        int numOfGrassTiles = (int) ((size * size) * 0.80) - 1;

        //Setting the Grass tiles
        setGrass(treasureX, treasureY, numOfGrassTiles);

        //Setting the remaining empty tiles as Water tiles
        setWater();
    }

    //setting a number of tile types as grass
    //includes path validation
    private void setGrass(int tX, int tY, int num) {
        //variable to store position of tiles to be set as grass
        int x = tX;
        int y = tY;
        //variables to store temporary position for validation
        int tempX = x;
        int tempY = y;

        boolean tileSet;

        for (int i = 1; i <= num; i++) {
            tileSet = false;
            do {
                //randomly choosing a nearby tile
                Direction direction = Direction.randomDirection();
                switch (direction) {
                    case UP:
                        tempY -= 1;
                        break;
                    case DOWN:
                        tempY += 1;
                        break;
                    case LEFT:
                        tempX -= 1;
                        break;
                    case RIGHT:
                        tempX += 1;
                        break;
                    default:
                        throw new IndexOutOfBoundsException();
                }

                //checking whether the new position is within map bounds
                if (isLegal(tempX, tempY)) {
                    //checking if randomly chosen coordinate happens to be the treasure tile or already assigned grass tile
                    if(grid[tempX][tempY] == null){
                        x = tempX;
                        y = tempY;
                        grid[x][y] = new Tile(TileType.GRASS);
                        tileSet = true;
                    }
                } else {
                    tempX = x;
                    tempY = y;
                }
            } while (!tileSet);
        }
    }

    //setting the remaining tiles as water tiles
    private void setWater() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[x][y] == null) {
                    grid[x][y] = new Tile(TileType.WATER);
                }
            }
        }
    }

    //returns tile
    public Tile getTile(Position p){
        return grid[p.getX()][p.getY()];
    }

    //checking whether given coordinates are within map boundaries
    public boolean isLegal(int x, int y){
        return x >= 0 && x < size && y >= 0 && y < size;
    }

}
