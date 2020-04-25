public enum TileType {
    GRASS("grass"),
    WATER("water"),
    TREASURE("treasure");

    private String hexCode;

    TileType(String fffb40) {
        hexCode = fffb40;
    }

    //returns the corresponding hexadecimal colour number which will be used for html
    public String getHexCode(){
        return hexCode;
    }
}