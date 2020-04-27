import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class Game {

    private int turns;
    public Player[] players;
    private Map map;
    private File[] htmlFiles;
    private BufferedWriter[] bw; //will allow us to write to files



    public boolean setNumPlayers(int playercount) {
        int minPlayers = 2;
        int maxPlayers = 8;
        players = new Player[playercount];

        //validating given number of players [2 < n < 8]
        if (players.length < minPlayers || players.length > maxPlayers) {
            System.out.println("Only between 2 and 8 players are accepted.");
            return false;
        }

        return true;
    }

    public boolean setMapSize(int size) {
        final int MAX = 50;
        //setting minimum number of map size according to amount of players
        final int MIN = players.length <= 4 ? 5 : 8;

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

    public void generateHTMLFiles() throws IOException {
        //variables for loops
        int i, j, k;

        //contains the html code to be written to file
        StringBuilder html = new StringBuilder();

        //this method will only execute on the initial generation
        if (htmlFiles == null) {
            htmlFiles = new File[players.length];
            bw = new BufferedWriter[players.length];
            for (i = 0; i < htmlFiles.length; i++) {
                //creating the file for the player
                htmlFiles[i] = new File("C:\\map_player_" + (i + 1) + ".html");
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
                    if (players[i].getMap().getTile(j, k).getUncovered()) {
                        html.append(players[i].getMap().getTile(j, k).getHtml());
                    } else {
                        html.append("<td bgcolour=#808080>");
                    }
                    /* if the tile coordinates correspond to the player's current position
                    the cell will contain an '*' to indicate the player's position
                     */
                    if (players[i].getCurrent() == new Position(j, k)) {
                        html.append("*");
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
