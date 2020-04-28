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
        return type;
    }

    //setter for uncovered
   public void setUncovered() {
       this.uncovered = true;
   }

   public void setCovered(){
        this.uncovered= false;

   }

    //getter for uncovered
    public boolean getUncovered(){
        return uncovered;
    }

    //getter for html
    public String getHtml(){
        return html;
    }

}