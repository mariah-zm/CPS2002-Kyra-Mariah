import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private int turns;
    public ArrayList<Player> players = new ArrayList<Player>();
    private Map map;
    HTMLGenerator generator;
    public File[] htmlFiles = null;
    public BufferedWriter[] bw = null;

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

    public boolean setMapSize(int size) {
        final int MAX = 50;
        //setting minimum number of map size according to amount of players
        final int MIN = players.size() <= 4 ? 5 : 8;

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

    public void createPlayers(int playercount, Map map){

        for(int i =0; i< playercount; ++i){
            Player player = new Player(map);
            players.add(player);
        }
    }

    public void generateHTML() throws IOException {
        generator = new HTMLGenerator();

        File file = new File("src\\generated_HTML");
        String path = file.getAbsolutePath();
        for (int i = 0; i < players.size(); i++) {

            htmlFiles = new File[players.size()];
            bw = new BufferedWriter[players.size()];

            for (i = 0; i < players.size(); i++) {
                //creating the file for the player
                htmlFiles[i] = new File(path +"\\map_player_" + (i + 1) + ".html");
                bw[i] = new BufferedWriter(new FileWriter(htmlFiles[i]));
                StringBuilder temp = new StringBuilder();
                temp.append(generator.headerHTML(i + 1));
                temp.append(generator.winnerMessageHTML(players.get(i)));
                temp.append(generator.gridHTML(players.get(i)));

                bw[i].write(temp.toString());
                bw[i].close();
                temp = null;
            }
        }
    }

    public static void main(String[] args){
        //starting the game
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        try{
            //variables for user input for map size and number of players
            boolean inputAccepted;
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


            //validating map size
            do {
                System.out.println("Enter map size: ");
                if (scanner.hasNextInt()) {
                    size = scanner.nextInt();
                    inputAccepted = game.setMapSize(size);
                } else {
                    scanner.nextLine();
                    System.out.println("Not an integer!");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            //initialising the map
            game.map = new Map(size);
            game.createPlayers(playerCount, game.map);
            game.generateHTML();

        }catch (Exception e){
            System.exit(1);
        }
        System.exit(0);
    }
}
