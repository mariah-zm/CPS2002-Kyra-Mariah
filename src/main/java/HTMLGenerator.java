public class HTMLGenerator {

    //contains html for page header, background, instruction bar, and the map
    public String headerHTML(int i) {
        return "<html><head><style>" +
                "body {background-image: url(\"https://media.giphy.com/media/VwXcgwGIPyiiY/giphy.gif\");" +
                "background-color: #cccccc;" +
                "font-family: \"Lato\", sans-serif;}" +

                ".sidebar {" +
                "height: 100%;" +
                "width: 0; position: fixed; z-index: 1; top: 0; left: 0; background-color: #111; overflow-x: hidden; transition: 0.5s; padding-top: 60px; }" +
                ".sidebar a { padding: 8px 8px 8px 32px; text-decoration: none; font-size: 5px; color: #818181; display: block; transition: 0.3s; }" +

                ".sidebar a:hover { color: #f1f1f1; }" +

                ".sidebar .closebtn { position: absolute;top: 0; right: 25px;font-size: 25px; margin-left: 50px;}" +

                ".openbtn { font-size: 16px;cursor: pointer;background-color: #111;color: white; padding: 10px 15px;border: none; }" +

                ".openbtn:hover {background-color: #444;}" +
                "#main {transition: margin-left .5s;padding: 16px;}" +

                ".header {\n" +
                "  padding: 20px;\n" +
                "  text-align: center;\n" +
                "  color: white;\n" +
                "  font-size: 16px;\n" +
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
                "<div id=\"mySidebar\" class=\"sidebar\">" +
                "<a href=\"javascript:void(0)\" class=\"closebtn\" onclick=\"closeNav()\">Close" +
                "<p>1/ Move Up(U), Down(D), Left(L), or Right(R).</p>" +
                "<p>2/ The treasure is hidden, it's your job to find it.</p>" +
                "<p>3/ Mind the water tiles, or else it's back to square one!</p></a>" +
                "</div>" +
                "<div id=\"main\">" + "<button class=\"openbtn\" onclick=\"openNav()\"> >Instructions</button> </div> <script>" +
                "function openNav() {document.getElementById(\"mySidebar\").style.width = \"250px\";" +
                "document.getElementById(\"main\").style.marginLeft = \"250px\"; }" +
                "function closeNav() { document.getElementById(\"mySidebar\").style.width = \"0\"; document.getElementById(\"main\").style.marginLeft= \"0\"; } </script>" +
                "<table style=\"border:1px solid black;margin-left:auto;margin-right:auto;\">\n" +
                "<div class=\"header\">\n" +
                "  <h1>FIND THE HIDDEN TREASURE</h1>\n" +
                "<h2>Player " + i + "</h2>\n<h3>Reload the page to see your updated moves<h3>";
    }

    public String gridHTML(Player player){
        Map map = player.getMap();
        int mapSize = map.getSize();

        StringBuilder html = new StringBuilder();

        //will store current parsed tile
        Tile current;

        for (int j = 0; j < mapSize; j++) {
            html.append("<tr>");
            for (int k = 0; k < mapSize; k++) {
                current = map.getTile(j, k);

                /*if the tile is discovered then we get corresponding html code
                 *otherwise, tile is greyed out*/
                if (player.getDiscoveredTiles().contains(current)) {
                    html.append(current.getHtml());
                } else {
                    html.append("<td><div class=\"square\">");
                }

                /*if the tile coordinates correspond to the player's current position
                 *the cell will contain an icon to indicate the player's position*/
                if (j == player.getCurrent().getX() && k == player.getCurrent().getY()) {
                    html.append("<p>");
                    //if the tile is a treasure tile, an icon will be added to the cell
                    if (current.getType() == TileType.TREASURE) {
                            html.append("&#128176;");
                    }
                    html.append("&#127939;");
                    html.append("</p>");
                }

                /*if the tile is a treasure tile, an icon will be added to the cell
                if (current.getType() == TileType.TREASURE) {
                    if (player.getDiscoveredTiles().contains(current)) {
                        html.append("<p>&#128176;</p>");
                    }
                }*/

                html.append("</div>");
                html.append("</td>");
            }
            html.append("</tr>");
        }
        html.append("</table></body></html>");
        return (html.toString());
    }

    //message to be displayed on top of the grid according to the player's status
    public String moveMessage(PlayerStatus status){
        switch(status){
            case SAFE:
                //message displayed when the player discovers grass tile
                return "<h2>Press U, D, L or R to move.</h2></div>";

            case WINS:
                //message displayed when the player discovers treasure tile
                return "<h2>WELL DONE, YOU FOUND THE TREASURE!</h2></div>";

            case DEAD:
                //message displayed when the player discovers water tile
                return "<h2>YIKES! You discovered a water tile. Start again from your initial position.</h2></div>";

            default:
                return "</div>";
        }
    }
}

