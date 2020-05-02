import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HTMLGenerator {

    public String headerHTML(int i) {
        //contains html for page header, background, instuction bar, and the map
        StringBuilder html = new StringBuilder();
        html.append("<html><head> <style>"+
               "body {background-image: url(\"https://media.giphy.com/media/VwXcgwGIPyiiY/giphy.gif\");"+
         "background-color: #cccccc;"+
            "font-family: \"Lato\", sans-serif;}"+

                 ".sidebar {"+
            "height: 100%;"+
            "width: 0; position: fixed; z-index: 1; top: 0; left: 0; background-color: #111; overflow-x: hidden; transition: 0.5s; padding-top: 60px; }"+
".sidebar a { padding: 8px 8px 8px 32px; text-decoration: none; font-size: 5px; color: #818181; display: block; transition: 0.3s; }"+

".sidebar a:hover { color: #f1f1f1; }"+

".sidebar .closebtn { position: absolute;top: 0; right: 25px;font-size: 25px; margin-left: 50px;}"+

".openbtn { font-size: 25px;cursor: pointer;background-color: #111;color: white; padding: 10px 15px;border: none; }"+

".openbtn:hover {background-color: #444;}"+
"#main {transition: margin-left .5s;padding: 16px;}"+

                 ".header {\n" +
                 "  padding: 60px;\n" +
                 "  text-align: center;\n" +
                 "  color: white;\n" +
                 "  font-size: 30px;\n" +
                 "  font-family: Arial;\n" +
                 "\n" +
                 "}" +
                 ".square {\n" +
                 "  height: 50px;\n" +
                 "  width: 50px;\n" +
                 "  background-color: #E2D6D4;\n" +
                 "}" + ".grass {\n" +
                 "  height: 50px;\n" +
                 "  width: 50px;\n" +
                 "  background-color: #67E240;\n" +
                 "}" + ".water {\n" +
                 "  height: 50px;\n" +
                 "  width: 50px;\n" +
                 "  background-color: #2FA6F1;\n" +
                 "}" + ".treasure {\n" +
                 "  height: 50px;\n" +
                 "  width: 50px;\n" +
                 "  background-color: #FFFB40;\n" +
                 "}" +
                 "</style></head></body>" +
                 "<div id=\"mySidebar\" class=\"sidebar\">"+
  "<a href=\"javascript:void(0)\" class=\"closebtn\" onclick=\"closeNav()\">Close"+
 "<p>1/ Move Up(U), Down(D), Left(L), or Right(R).</p>"+
  "<p>2/ The treasure is hidden, it's your job to find it.</p>"+
                "<p>3/ Mind the water tiles, or else it's back to square one!</p></a>"+
                "</div>"+
"<div id=\"main\">"+ "<button class=\"openbtn\" onclick=\"openNav()\"> >Instructions</button> </div> <script>"+
          "function openNav() {document.getElementById(\"mySidebar\").style.width = \"250px\";"+
            "document.getElementById(\"main\").style.marginLeft = \"250px\"; }"+
        "function closeNav() { document.getElementById(\"mySidebar\").style.width = \"0\"; document.getElementById(\"main\").style.marginLeft= \"0\"; } </script>"+
                "<table style=\"border:1px solid black;margin-left:auto;margin-right:auto;\">\n" +
                "<div class=\"header\">\n" +
                "  <h1>FIND THE HIDDEN TREASURE</h1>\n" +
                "<h2>Player "+ i +"</h2>");

        return html.toString();
    }

    public String gridHTML(Player player) throws IOException {
        Map map = player.getMap();
        ArrayList<Tile> visited = player.visitedTiles;

        StringBuilder html = new StringBuilder();
        Tile[][] grid = map.getGrid();

        for (int j = 0; j < map.getSize(); j++) {
            html.append("<tr>");
            for (int k = 0; k < map.getSize(); k++) {
                //get current tile in loop
                Tile current = grid[j][k];

                /*if the tile is uncovered then we get corresponding html code
                    otherwise, tile is greyed out
                     */
                if (visited.contains(current)) {
                    html.append(current.getHtml());
                } else {
                    html.append("<td><div class=\"square\">");
                }
                    /* if the tile coordinates correspond to the player's current position
                    the cell will contain a person to indicate the player's position
                     */
                if (j == player.getCurrent().getX() && k == player.getCurrent().getY()) {
                    html.append("<p>&#127939;</p>");
                }
                if (player.getMap().getTile(j, k).getType() == TileType.TREASURE) {
                    if (player.visitedTiles.contains(current)) {
                        html.append("<p>&#128176;</p>");
                    }
                }

                    html.append("</div>");

                    html.append("</td>");
                }
                html.append("</tr>");

            }

            html.append("</table></body></html>");
            return (html.toString());
        }


    public String winnerMessageHTML(Player player){
        String winnerMessage =  "<h2>WINNER! WINNER! WINNER!</h2></div>)";
        if(player.status==PlayerStatus.WINS){
            return winnerMessage;
        }else{
            return "</div>";
        }
    }



}

