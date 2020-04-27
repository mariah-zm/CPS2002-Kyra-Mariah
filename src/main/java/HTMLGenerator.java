import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HTMLGenerator {

  //  public File htmlFiles = null;

    //public BufferedWriter bw = null;//will allow us to write to files
    public int playercounter = 1;

    public void generateHTMLFiles(Player player) throws IOException {
        //variables for loops
        int i;
        int j, k;
        File htmlFiles = null;
        BufferedWriter bw= null;
        StringBuilder html=null;

        //contains the html code to be written to file

        //this method will only execute on the initial generation
      /*  if (htmlFiles == null) {
            htmlFiles = new File[game.players.size()];
            bw = new BufferedWriter[game.players.size()];
            for (i = 0; i < game.players.size(); i++) {*/
                //creating the file for the player
         htmlFiles = new File("C:\\Users\\kyra_\\OneDrive\\Desktop\\CPS2002\\src\\generated_HTML\\map_player_" + (playercounter) + ".html");
         bw = new BufferedWriter(new FileWriter(htmlFiles));
        playercounter++;

                //loop for each player
                // for (i = 0; i < htmlFiles.length; i++) {
                html = new StringBuilder();
                html.append("<html><head>" +
                        "<body style=\"background-color: #28444B;\">"+
                        "<style>\n" +
                        ".header {\n" +
                                "  padding: 60px;\n" +
                                "  text-align: center;\n" +
                                "  background: #28444B;\n" +
                                "  color: white;\n" +
                                "  font-size: 30px;\n" +
                                "  font-family: Arial;\n" +
                                "\n" +
                                "}"+
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
                        "</style></head></body>"+
                        "<table style=\"border:1px solid black;margin-left:auto;margin-right:auto;\">\n" +
                        "<div class=\"header\">\n" +
                                "  <h1>FIND THE HIDDEN TREASURE</h1>\n" +
                                "</div>");

                //nested-loop to parse through each tile in grid and writing html code accordingly
                for (j = 0; j < player.getMap().getSize(); j++) {
                    html.append("<tr>");
                    for (k = 0; k < player.getMap().getSize(); k++) {
                    /*if the tile is uncovered then we get corresponding html code
                    otherwise, tile is greyed out
                     */
                        if (player.getMap().getTile(j, k).getUncovered()) {
                            html.append(player.getMap().getTile(j, k).getHtml());
                        } else {
                            html.append("<td><div class=\"square\">");
                        }
                    /* if the tile coordinates correspond to the player's current position
                    the cell will contain a person to indicate the player's position
                     */
                    if (j == player.getCurrent().getX() && k ==player.getCurrent().getY()) {
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

                //writing the code to the corresponding file
                bw.write(html.toString());
                bw.close();
            }
        }



