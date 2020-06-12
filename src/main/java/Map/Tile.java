package Map;

import Position.Position;

public class Tile {

    private TileType type;
    Position position;
    private String html;

    public Tile(TileType type, Position position){
        this.type = type;
        this.position = position;
        this.html = "<td><div class=\""+ type.getKey()+ "\">";
    }

    //getter for type
    public TileType getType() {
        return this.type;
    }

    //getter for html
    public String getHtml(){
        return html;
    }

    //getter for position
    public Position getPosition(){
        return position;
    }

}