package Map;

public enum TileType {
    GRASS("grass"),
    WATER("water"),
    TREASURE("treasure");

    private String key;

    TileType(String fffb40) {
        key = fffb40;
    }

    //returns the corresponding key which will be used for html code generation
    public String getKey(){
        return key;
    }
}