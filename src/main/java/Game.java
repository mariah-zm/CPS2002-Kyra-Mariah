public class Game {

    private int turns;
    public Player[] players;
    private Map map;

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
}
