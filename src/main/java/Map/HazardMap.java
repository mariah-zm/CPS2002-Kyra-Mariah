package Map;

import Player.Position;

import java.util.Random;

public class HazardMap extends Map {
    private static HazardMap map;
    private HazardMap() {}

    public static Map getInstance() {
        if (map == null)
            map = new HazardMap();
        return map;
    }

    //filling the grid elements with tile types
    public void generate() {
        this.grid = new Tile[size][size];

        //Setting the Treasure tile
        Random rnd = new Random();
        int treasureX = rnd.nextInt(this.size);
        int treasureY = rnd.nextInt(this.size);

        this.grid[treasureX][treasureY] = new Tile(TileType.TREASURE, new Position(treasureX, treasureY));

        //Setting number of grass tiles
        double percentage = Math.random() * (0.75 - 0.65) + 0.65;
        int numOfGrassTiles = (int) ((size * size) * percentage) - 1;

        //Setting the Grass tiles
        setGrass(treasureX, treasureY, numOfGrassTiles);

        //Setting the remaining empty tiles as Water tiles
        setWater();
    }
}
