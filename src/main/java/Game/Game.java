package Game;

import Direction.Direction;
import HTML.HTMLGenerator;
import Map.*;
import Map.Map;
import Player.Player;
import Player.PlayerStatus;
import Team.*;
import edu.emory.mathcs.backport.java.util.Collections;

import java.io.*;
import java.util.*;

public class Game {

    public Player [] players;
    public int numOfTeams =0;
    public List<Integer> winners = new ArrayList<>();
    public static Map map;

    HTMLGenerator generator = new HTMLGenerator();
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

    //creating the players - passing a new object of Map.Map
    public void createPlayers(){
        for(int i =0; i<players.length; ++i){
            players[i] = new Player(map, i+1);
        }
    }

    //Creating Teams
    public List<Team> teamFormation(){
        //Randomly reordering players
        Collections.shuffle(Arrays.asList(players));
        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < numOfTeams; i++) {
            teams.add(new Team(i));
        }

        //Assigning players to teams
        int j = 0;
        for (Player player : players) {
            player.addToTeam(teams.get(j));
            j = (j + 1) % teams.size();
        }

        displayTeams(teams);
        return teams;
    }

    //generating the html files for each player
    public void generateHTML() throws IOException {
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
        StringBuilder listOfWinners = new StringBuilder(" Player " + winners.get(0));

        for (int i = 1; i < winners.size(); i++) {
            if (i + 1 == winners.size()) {
                listOfWinners.append(" and Player ").append(winners.get(i));
            } else {
                listOfWinners.append(", Player ").append(winners.get(i));
            }
        }
        return "GAME OVER!\nCongratulations " + listOfWinners + ", you win the game!";
    }

    //Returns winning team

    public boolean acceptNumTeams(){
        Scanner scanner = new Scanner(System.in);
        boolean inputAccepted;
        do {
            System.out.println("Enter number of teams:");
            if (scanner.hasNextInt()) {
                numOfTeams = scanner.nextInt();
                inputAccepted = true;
            } else {
                scanner.nextLine();
                System.out.println("Not an integer!");
                inputAccepted = false;
            }
            if(numOfTeams <2){
                System.out.println("Number of teams must be at least 2.");
                inputAccepted = false;
            }
        }while (!inputAccepted);
        return true;
    }

    public void displayTeams(List<Team> teams) {
        for (int i = 0; i < teams.size(); i++) {
            System.out.println("****************");
            System.out.println("Team " + (i+1));
           // List players = teams.get(i).getObservers();
            for (Object player : teams.get(i).getObservers())
                System.out.println("Player " + ((Player) player).getID());
        }
        System.out.println("* * * * * * * * * * * *");
    }

    public void collabGameLoop()throws IOException {
        Scanner scanner = new Scanner(System.in);
        //variables to control movement logic
        Direction move;
        String moveInput;
        //variables to control when game ends
        boolean isGameWon = false;
        boolean inputAccepted;

        List<Team> teams = teamFormation();
        do {
         for(Team team : teams) {
             Player currentPlayer = (Player) team.currentPlayer();
             do {
                 System.out.println("Team " + team.getTeamNum() + ": Player " + currentPlayer.getID());
                 System.out.println("Enter direction (U,D,R,L):");
                 moveInput = scanner.next();
                 //getting corresponding direction
                 move = Direction.getDirection(moveInput.charAt(0));

                 //direction validation
                 if (move == null) {
                     System.out.println("Please enter a valid direction.");
                     inputAccepted = false;
                 } else {
                     if (currentPlayer.move(move)) {
                         inputAccepted = true;
                         //if treasure tile is found by the player, game ends
                         if (currentPlayer.getStatus() == PlayerStatus.WINS) {
                             if (!isGameWon) isGameWon = true;
                             winners.add(currentPlayer.getID());
                         }
                         if (currentPlayer.getStatus() == PlayerStatus.DEAD) {
                             currentPlayer.setPosition(currentPlayer.getInitial());
                         }
                     } else {
                         inputAccepted = false;
                     }
                 }

             } while (!inputAccepted);
             team.setNextPlayer();
         } generateHTML();
        } while (!isGameWon);
    }

    public void soloGameLoop() throws IOException {
        Scanner scanner = new Scanner(System.in);
        //variables to control movement logic
        Direction move;
        String moveInput;
        //variables to control when game ends
        boolean isGameWon = false;
        boolean inputAccepted;

        do {
            //getting players' moves
            for (int i = 0; i < players.length; ++i) {
                System.out.println("\n*\t*\t*\t*\t*\t*\t*\t*\n");
                System.out.println("Player " + (i + 1));
                do {
                    System.out.println("Enter direction (U,D,R,L):");
                    moveInput = scanner.next();
                    //getting corresponding direction
                    move = Direction.getDirection(moveInput.charAt(0));

                    //direction validation
                    if (move == null) {
                        System.out.println("Please enter a valid direction.");
                        inputAccepted = false;
                    } else {
                        if (players[i].move(move)) {
                            inputAccepted = true;

                            //if treasure tile is found by the player, game ends
                            if (players[i].getStatus() == PlayerStatus.WINS) {
                                if (!isGameWon) isGameWon = true;
                                winners.add(i + 1);
                            }
                            if (players[i].getStatus() == PlayerStatus.DEAD) {
                                players[i].setPosition(players[i].getInitial());
                            }
                        } else {
                            inputAccepted = false;
                        }
                    }
                } while (!inputAccepted);
            }
            generateHTML();
        } while (!isGameWon);
    }

    public static void main(String[] args){
        //starting the game
        Game game = new Game();

        Scanner scanner = new Scanner(System.in);
        MapMode mode;
        boolean solo = false;

        try{
            //Variables for user input for map size and number of players
            boolean inputAccepted;
            int size, playerCount = 0;

            do {
                System.out.println("Enter game mode:\n(1) Solo or \n(2) Collaborative");
                if (scanner.hasNextInt()) {
                    int choice = scanner.nextInt();
                    if(choice == 1 || choice == 2){
                        if(choice == 1){
                            solo = true;
                        }else {
                           game.acceptNumTeams();
                           solo=false;
                        }
                        inputAccepted = true;
                    }else {
                        scanner.nextLine();
                        System.out.println("Invalid input.");
                        inputAccepted = false;
                    }
                } else {
                    scanner.nextLine();
                    System.out.println("Invalid input.");
                    inputAccepted = false;
                }
            }while (!inputAccepted);

            //validating number of players
            do {
                System.out.println("Enter number of players: ");
                if (scanner.hasNextInt()) {
                    playerCount = scanner.nextInt();
                    inputAccepted = game.setNumPlayers(playerCount);
                    if (playerCount < game.numOfTeams) {
                        System.out.println("Game cannot have more teams than players.");
                        inputAccepted = false;
                    }
                }else {
                    scanner.nextLine();
                    System.out.println("Not an integer!");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            //validating map type
            int mapType;
            do {
                System.out.println("Enter map type: (1)Safe or (2)Hazardous");
                if (scanner.hasNextInt()) {
                    mapType = scanner.nextInt();
                    if(mapType == 1 || mapType == 2){
                        if(mapType ==1){
                            mode = MapMode.SAFE;
                        }else {
                            mode = MapMode.HAZARDOUS;
                        }
                        //initialising the map with selected mode
                        map = MapFactory.getMap(mode);
                        inputAccepted = true;
                    }else {
                        scanner.nextLine();
                        System.out.println("Integer out of range!");
                        inputAccepted = false;
                    }
                } else {
                    scanner.nextLine();
                    System.out.println("Not an integer!");
                    inputAccepted = false;
                }
            }while (!inputAccepted);

            //validating map size
            do {
                System.out.println("Enter map size: ");
                if (scanner.hasNextInt()) {
                    size = scanner.nextInt();
                    inputAccepted = map.setSize(size, playerCount);
                } else {
                    scanner.nextLine();
                    System.out.println("Not an integer!");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            //generating the map with tiles according to the map mode
            map.generate();

            //initialising players array
            game.createPlayers();
            game.generateHTML();

            System.out.println("\n\nLaunching Game...\n");

            //opening html files for all players
            for(int f=0; f<game.htmlFiles.length; f++){
                game.openHTML(game.htmlFiles[f].getPath());
            }

            if(!solo) {
                game.collabGameLoop();
            }else {
                game.soloGameLoop();
            }

            System.out.println(game.listOfWinners());

        }catch (Exception e){
            System.exit(1);
        }
        System.exit(0);
    }
}
