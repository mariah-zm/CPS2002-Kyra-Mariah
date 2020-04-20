public enum TileType {
    GRASS("#67E240"),
    WATER("#2FA6F1"),
    TREASURE("#FFFB40");

    private String hexCode;

    TileType(String fffb40) {
        hexCode = fffb40;
    }

    //returns the corresponding hexadecimal colour number which will be used for html
    public String getHexCode(){
        return hexCode;
    }
}