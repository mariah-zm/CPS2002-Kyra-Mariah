import java.util.ArrayList;

public class Tile {

    private TileType type;
    private boolean uncovered;
    private String html;


    public Tile(TileType type){
        this.type = type;
        this.uncovered = false;
        this.html = "<td><div class=\""+ type.getHexCode()+ "\">";
    }

    //getter for type
    public TileType getType() {
        return this.type;
    }

    //setter for uncovered
    public void setUncovered(){
        this.uncovered = true;
    }

    //getter for uncovered
    public boolean getUncovered(){
        return this.uncovered;
    }

    //getter for html
    public String getHtml(){
        return html;
    }

}