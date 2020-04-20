public class Tile {

    private TileType type;
    private boolean uncovered;

    public Tile(TileType type){
        this.type = type;
        this.uncovered = false;
    }

    //getter for type
    public TileType getType() {
        return type;
    }

    //setter for uncovered
    public void setUncovered(){
        uncovered = true;
    }

    //getter for uncovered
    public boolean getUncovered(){
        return uncovered;
    }
}