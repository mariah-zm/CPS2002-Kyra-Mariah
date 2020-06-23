package Map;

import Direction.Direction;
import Player.Position;

public abstract class Map {

    protected int size = 0;
    protected Tile[][] grid;
    //map singleton
    protected static Map map = null;

    //setter with validation
    public boolean setSize(int size, int players) {
        final int MAX = 50;
        //setting minimum number of map size according to amount of players
        final int MIN = players <= 4 ? 5 : 8;

        //validating given size
        if (MIN > size) {
            System.out.println("Given map size is too small.");
            return false;
        } else if (MAX < size) {
            System.out.println("Given map size is too big.");
            return false;
        }

        this.size = size;
        return true;
    }

    //getter for size
    public int getSize() {
        return size;
    }

    //getter for grid
    public Tile[][] getGrid() {
        return grid;
    }

    //setting a number of tile types as grass
    //includes path validation
    public void setGrass(int tX, int tY, int num) {
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
                        tempX -= 1;
                        break;
                    case DOWN:
                        tempX += 1;
                        break;
                    case LEFT:
                        tempY -= 1;
                        break;
                    case RIGHT:
                        tempY += 1;
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
                        grid[x][y] = new Tile(TileType.GRASS, new Position(x, y));
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
    public void setWater() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (grid[x][y] == null) {
                    grid[x][y] = new Tile(TileType.WATER, new Position(x, y));
                }
            }
        }
    }

    //returns tile
    public Tile getTile(int x, int y){
        return grid[x][y];
    }

    //checking whether given coordinates are within map boundaries
    public boolean isLegal(int x, int y){
        return x >= 0 && x < size && y >= 0 && y < size;
    }

    public abstract void generate();
}