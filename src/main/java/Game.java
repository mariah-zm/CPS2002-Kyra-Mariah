import java.io.BufferedWriter;
import java.io.File;

public class Game {

    private int turns;
    private Player[] players;
    private Map map;


    public boolean setNumPlayers(int playerCount) {
        int minPlayers = 2;
        int maxPlayers = 8;

        //validating given number of players [2 < n < 8]
        if (playerCount < minPlayers || playerCount > maxPlayers) {
            System.out.println("Only between 2 and 8 players are accepted.");
            return false;
        }

        return true;
    }

    public boolean setMapSize(int size, int playercount) {
        final int MAX = 50;
        //setting minimum number of map size according to amount of players
        final int MIN = playercount <= 4 ? 5 : 8;

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
