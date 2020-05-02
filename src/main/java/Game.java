import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Player [] players;
    public Map map;
    HTMLGenerator generator;
    public File[] htmlFiles = null;
    public BufferedWriter[] bw = null;
    public static boolean won = false;

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

    //setter for players array
    public void setPlayers(){
        for(int i =0; i<players.length; ++i){
            Map newMap = new Map(map.getGrid());
            players[i] = new Player(newMap);
        }
    }

    //getter for players array
    public Player[] getPlayers(){
        return this.players;
    }

    public int findWinner(){
        int winner =0;
        for(int i =0; i< players.length; i++){
            if (players[i].getStatus()==PlayerStatus.WINS){
                winner = i+1;
            }
        }
        return winner;
    }

    public void generateHTML() throws IOException {
        generator = new HTMLGenerator();
        String path = "src\\generated_HTML";
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
            temp.setLength(0);
        }
    }

    //opens the html files
    public void openHTML(String path){
        try{
            Process process = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path);
            process.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void main(String[] args){
        //starting the game
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        //variables to control movement logic
        Direction move;
        String moveInput;

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
            //initialising players array
            game.setPlayers();
            game.generateHTML();

            System.out.println("Launching Game...");

            //opening html files for all players
            for(int f=0; f<game.htmlFiles.length; f++){
                game.openHTML(game.htmlFiles[f].getPath());
            }

            do {
                for (int i = 0; i < game.players.length; ++i) {
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
                            game.players[i].move(move);
                            inputAccepted = true;
                        }
                    }while(!inputAccepted);
                }
                game.generateHTML();
                //condition for winning game
            }while(!won);


            System.out.println("\n\n\t\tGAME OVER!");
            System.out.println("\n\n\t\tPLAYER "+ game.findWinner()+" WINS!");

        }catch (Exception e){
            System.exit(1);
        }
        System.exit(0);
    }
}
