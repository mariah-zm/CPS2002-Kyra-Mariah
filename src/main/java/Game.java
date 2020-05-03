import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    public Player [] players;
    public List<Integer> winners = new ArrayList<>();
    public Map map;

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

        players = new Player[playerCount];
        return true;
    }

    //validating user input for map size
    public boolean setMapSize(int size) {
        final int MAX = 50;
        //setting minimum number of map size according to amount of players
        final int MIN = players.length <= 4 ? 5 : 8;

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

    //creating the players - passing a new object of Map
    public void createPlayers(){
        for(int i =0; i<players.length; ++i){
            Map newMap = new Map(map.getGrid());
            players[i] = new Player(newMap);
        }
    }

    //generating the html files for each player
    public void generateHTML() throws IOException {
        generator = new HTMLGenerator();
        //gets the user directory
        final String dir = System.getProperty("user.dir");
        String path = dir + "\\generated_HTML";

        StringBuilder temp = new StringBuilder();

        //creating the folder where the HTML files will be stored
        File folder = new File(path);
        folder.mkdir();

        htmlFiles = new File[players.length];
        bw = new BufferedWriter[players.length];

        for (int i = 0; i < players.length; i++) {
            //creating the file for the player
            htmlFiles[i] = new File(path + "\\map_player_" + (i+1) + ".html");
            bw[i] = new BufferedWriter(new FileWriter(htmlFiles[i]));

            temp.append(generator.headerHTML(i+1));
            temp.append(generator.moveMessage(players[i].getStatus()));
            temp.append(generator.gridHTML(players[i]));

            bw[i].write(temp.toString());
            bw[i].close();
            //resetting StringBuilder
            temp.setLength(0);
        }
    }

    //opens the html files
    public void openHTML(String path){
        try{
            Process process = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path);
            process.waitFor();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    //printing the list of winner
    public String listOfWinners() {
        StringBuilder listOfWinners = new StringBuilder("Player " + winners.get(0));

        for (int i = 1; i < winners.size(); i++) {
            if (i + 1 == winners.size()) {
                listOfWinners.append(" and Player ").append(winners.get(i));
            } else {
                listOfWinners.append(", Player ").append(winners.get(i));
            }
        }
        return "GAME OVER!\nCongratulations " + listOfWinners + ", you win the game!";
    }

    public static void main(String[] args){
        //starting the game
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        //variables to control movement logic
        Direction move;
        String moveInput;
        //variables to control when game ends
        boolean isGameWon = false;

        try{
            //variables for user input for map size and number of players
            boolean inputAccepted;
            int size = 0, playerCount;

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

            //initialising players array
            game.createPlayers();
            game.generateHTML();

            System.out.println("\n\nLaunching Game...\n");

            //opening html files for all players
            for(int f=0; f<game.htmlFiles.length; f++){
                game.openHTML(game.htmlFiles[f].getPath());
            }

            do {
                //getting players' moves
                for (int i = 0; i < game.players.length; ++i) {
                    System.out.println("\n*\t*\t*\t*\t*\t*\t*\t*\n");
                    System.out.println("Player " + (i + 1));
                    do {
                        System.out.println("Enter direction (U,D,R,L):");
                        moveInput = scanner.next();
                        //getting corresponding direction
                        move = Direction.getDirection(moveInput.charAt(0));

                        //direction validation
                        if(move == null){
                            System.out.println("Please enter a valid direction.");
                            inputAccepted = false;
                        }
                        else{
                            if(game.players[i].move(move)){
                                inputAccepted = true;

                                //if treasure tile is found by the player, game ends
                                if(game.players[i].getStatus() == PlayerStatus.WINS){
                                    if (!isGameWon) isGameWon = true;
                                    game.winners.add(i+1);
                                }
                                if(game.players[i].getStatus() == PlayerStatus.DEAD){
                                    game.players[i].setPosition(game.players[i].getInitial());
                                }
                            }
                            else{
                                inputAccepted = false;
                            }
                        }
                    }while(!inputAccepted);
                }
                game.generateHTML();
            }while(!isGameWon);

            System.out.println(game.listOfWinners());

        }catch (Exception e){
            System.exit(1);
        }
        System.exit(0);
    }
}
