import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int turns;
    public ArrayList<Player> players = new ArrayList<Player>();
   // private Map map;
    public File[] htmlFiles=null;

    public BufferedWriter[] bw=null;//will allow us to write to files




    public boolean setNumPlayers(int playercount) {
        int minPlayers = 2;
        int maxPlayers = 8;

        //validating given number of players [2 < n < 8]
        if (playercount < minPlayers || playercount > maxPlayers) {
            System.out.println("Only between 2 and 8 players are accepted.");
            return false;
        }

        return true;
    }

    public boolean setMapSize(int size) {
        final int MAX = 50;
        //setting minimum number of map size according to amount of players
        final int MIN = players.size() <= 4 ? 5 : 8;

        //validating given size
        if (MIN >= size) {
            System.out.println("Given map size is too small.");
            return false;
        } else if (MAX <= size) {
            System.out.println("Given map size is too big.");
            return false;
        }
        return true;
    }

    public void generateHTMLFiles(Map map) throws IOException {
        //variables for loops
        int i, j, k;


        //contains the html code to be written to file
        StringBuilder html = new StringBuilder();

        //this method will only execute on the initial generation
       if (htmlFiles == null) {
           htmlFiles = new File[players.size()];
           bw=  new BufferedWriter[players.size()];
        for (i = 0; i < htmlFiles.length; i++) {
                //creating the file for the player
                htmlFiles[i] = new File("C:\\Users\\kyra_\\OneDrive\\Desktop\\CPS2002\\src\\generated_HTML\\map_player_" + (i + 1) + ".html");
                bw[i] = new BufferedWriter(new FileWriter(htmlFiles[i]));
            }
      }

        //loop for each player
        for (i = 0; i < htmlFiles.length; i++) {
            html.append("<html><head><title>table</title><style>" +
                    "table{ border-collapse: collapse; width: 50%; } " +
                    "td { border: 2 px solid black; padding: 15 px; }" +
                    "</style></head><body><table>");

            //nested-loop to parse through each tile in grid and writing html code accordingly
            for (j = 0; j < map.getSize(); j++) {
                html.append("<tr>");
                for (k = 0; k < map.getSize(); k++) {
                    /*if the tile is uncovered then we get corresponding html code
                    otherwise, tile is greyed out
                     */
                    if (players.get(i).getMap().getTile(j, k).getUncovered()) {
                        html.append(players.get(i).getMap().getTile(j, k).getHtml());
                    } else {
                        html.append("<td bgcolour=#808080>");
                    }
                    /* if the tile coordinates correspond to the player's current position
                    the cell will contain a smiley to indicate the player's position
                     */

                    if( j==players.get(i).getCurrent().getX() && k==players.get(i).getCurrent().getY() ){
                        html.append("</p>&#9786;</p>");

                    }

                  html.append("</td>");
                }
                html.append("</tr>");
            }

            html.append("</table></body></html>");

            //writing the code to the corresponding file
            bw[i].write(html.toString());
            bw[i].close();
        }
    }
}
