import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private int turns;
    public ArrayList<Player> players = new ArrayList<Player>();
    private Map map;
    // public File[] htmlFiles=null;

    //public BufferedWriter[] bw=null;//will allow us to write to files


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
}