import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class HTMLGenerator {

    public String headerHTML(int i) {
        StringBuilder html = new StringBuilder();
        html.append("<html><head>" +
                "<style>\n" +
                 "body {\n" +
                " background-image: url(\"https://media.giphy.com/media/VwXcgwGIPyiiY/giphy.gif\");\n" +
                " background-color: #cccccc;\n" +
                "}"+
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
                    if (player.getMap().getTile(j, k).getUncovered()) {
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

