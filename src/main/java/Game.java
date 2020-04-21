import java.util.Scanner;

public class Game {

    private int turns;
    private Player[] players;
    private Map map;

    //validating user input for number of players
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

    //validating user input for map size
    public boolean setMapSize(int size, int playerCount) {
        final int MAX = 50;
        //setting minimum number of map size according to amount of players
        final int MIN = playerCount <= 4 ? 5 : 8;

        //validating given size
        if (MIN > size) {
            System.out.println("Given map size is too small.");
            return false;
        } else if (MAX < size) {
            System.out.println("Given map size is too big.");
            return false;
        }
        return true;
    }

    public static void main(String[] args){
        //starting the game
        Game game = new Game();

        try{
            //variables/scanner object for receiving user input
            boolean inputAccepted;
            Scanner scanner = new Scanner(System.in);

            //variables for user input for map size and number of players
            int size = 0, playerCount = 0;

            //validating number of players
            do {
                System.out.println("Enter number of players: ");
                if (scanner.hasNextInt()) {
                    playerCount = scanner.nextInt();
                    inputAccepted = game.setNumPlayers(playerCount);
                } else {
                    scanner.nextLine();
                    System.out.println("Not an integer!");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            //initialising players array
            game.players = new Player[playerCount];

            //validating map size
            do {
                System.out.println("Enter map size: ");
                if (scanner.hasNextInt()) {
                    size = scanner.nextInt();
                    inputAccepted = game.setMapSize(size, playerCount);
                } else {
                    scanner.nextLine();
                    System.out.println("Not an integer!");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            //initialising the map
            game.map = new Map(size);
        }catch (Exception e){
            System.exit(1);
        }
        System.exit(0);

    }
}
