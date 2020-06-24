package Game;

import Direction.Direction;
import HTML.HTMLGenerator;
import Map.Map;
import Map.MapFactory;
import Map.MapMode;
import Player.Player;
import Player.PlayerStatus;
import Team.Team;
import edu.emory.mathcs.backport.java.util.Collections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

    public Player[] players;
    public List<String> winners = new ArrayList<>();
    public Map map;
    public Team[] teams;

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
    public void createPlayers() {
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player(map, i + 1);
        }
    }

    public boolean setNumTeams(int teamCount) {
        int max = players.length;

        if (max < teamCount) {
            System.out.println("You cannot have more teams than players!\nMaximum number of teams is " + max);
            return false;
        }

        if (teamCount < 2) {
            System.out.println("Number of teams should be at least 2.");
            return false;
        }

        teams = new Team[teamCount];
        return true;
    }

    //Creating Teams
    public void createTeams() {
        //Randomly reordering players
        Collections.shuffle(Arrays.asList(players));
        for (int i = 0; i < teams.length; i++) {
            teams[i] = new Team(i + 1);
        }

        //Assigning players to teams
        int j = 0;
        for (Player player : players) {
            player.addToTeam(teams[j]);
            j = (j + 1) % teams.length;
        }
    }

    //Displays Teams and Players in them
    public void displayTeams() {
        String id;
        for (Team team : teams) {
            System.out.println(team.getTeamID());
            for (Object player : team.getObservers()) {
                id = ((Player) player).getPlayerID();
                System.out.println(id.substring(id.lastIndexOf("-")));
            }
        }
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
            htmlFiles[i] = new File(path + "\\map_player_" + (i + 1) + ".html");
            bw[i] = new BufferedWriter(new FileWriter(htmlFiles[i]));

            temp.append(generator.headerHTML(players[i].getPlayerID()));
            temp.append(generator.moveMessage(players[i].getStatus()));
            temp.append(generator.gridHTML(players[i]));

            bw[i].write(temp.toString());
            bw[i].close();
            //resetting StringBuilder
            temp.setLength(0);
        }
    }

    //opens the html files
    public void openHTML(String path) {
        try {
            Process process = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + path);
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //printing the list of winner
    public String listOfWinners() {
        StringBuilder listOfWinners = new StringBuilder(winners.get(0));

        for (int i = 1; i < winners.size(); i++) {
            if (i + 1 == winners.size()) {
                listOfWinners.append(" and ").append(winners.get(i));
            } else {
                listOfWinners.append(", ").append(winners.get(i));
            }
        }
        return "GAME OVER!\nCongratulations " + listOfWinners + ", you win the game!";
    }

    public static void main(String[] args) {
        //starting the game
        Game game = new Game();

        Scanner scanner = new Scanner(System.in);
        boolean inputAccepted, teamMode = false;
        MapMode mode = null;
        //variable for menu input
        int userInput;

        //variables to control movement logic
        Direction move;
        String moveInput;
        //variables to control when game ends
        boolean isGameWon = false;

        System.out.println("WELCOME TO HIDDEN TREASURE\n* * * * * * * * * * *\n");

        try {
            //Validating number of players
            do {
                System.out.println("Enter number of players: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();
                    inputAccepted = game.setNumPlayers(userInput);
                } else {
                    scanner.nextLine();
                    System.out.println("\nInvalid input!\n\n");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            do {
                System.out.println("How would you like to play? \n(1) Solo \n(2) Collaborative");

                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            inputAccepted = true;
                            break;

                        case 2:
                            teamMode = true;
                            inputAccepted = true;
                            break;

                        default:
                            System.out.println("\nInvalid input!\n\n");
                            inputAccepted = false;
                    }
                } else {
                    scanner.nextLine();
                    System.out.println("\nInvalid input!\n\n");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            if (teamMode) {
                //Validating map size
                do {
                    System.out.println("Enter number of teams: ");
                    if (scanner.hasNextInt()) {
                        userInput = scanner.nextInt();
                        inputAccepted = game.setNumTeams(userInput);
                    } else {
                        scanner.nextLine();
                        System.out.println("\nInvalid input!\n\n");
                        inputAccepted = false;
                    }
                } while (!inputAccepted);
            }

            //Validating map size
            do {
                System.out.println("Enter map size: ");
                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();
                    inputAccepted = game.map.setSize(userInput, game.players.length);
                } else {
                    scanner.nextLine();
                    System.out.println("\nInvalid input!\n\n");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            do {
                System.out.println("Enter map type: \n(1) Safe \n(2) Hazardous");

                if (scanner.hasNextInt()) {
                    userInput = scanner.nextInt();

                    switch (userInput) {
                        case 1:
                            mode = MapMode.SAFE;
                            inputAccepted = true;
                            break;
                        case 2:
                            mode = MapMode.HAZARDOUS;
                            inputAccepted = true;
                            break;
                        default:
                            System.out.println("\nInvalid input!\n\n");
                            inputAccepted = false;
                    }
                } else {
                    scanner.nextLine();
                    System.out.println("\nInvalid input!\n\n");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            //Getting map Singleton according to map mode
            game.map = MapFactory.getMap(mode);
            //Generating the map with tiles according to the map mode
            game.map.generate();

            //initialising players array
            game.createPlayers();
            game.generateHTML();

            System.out.println("\n\nLaunching Game...\n");

            //Generating teams
            if (teamMode) {
                game.createTeams();
                game.displayTeams();
            }

            do {
                //getting players' moves
                for (Player player : game.players) {
                    System.out.println("\n*\t*\t*\t*\t*\t*\t*\t*\n");
                    System.out.println(player.getPlayerID());
                    if (teamMode) {
                    }
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
                            if (player.move(move)) {
                                inputAccepted = true;
                                if (teamMode) {
                                          int x = player.getCurrent().getX();
                                          int y = player.getCurrent().getY();
                                          player.subject.setSubjectLastExplored(player.getMap().getTile(x,y));
                                }

                                //if treasure tile is found by the player, game ends
                                if (player.getStatus() == PlayerStatus.WINS) {
                                    if (!isGameWon) isGameWon = true;
                                    if (teamMode) {

                                    } else {
                                        game.winners.add(player.getPlayerID());
                                    }
                                }
                                if (player.getStatus() == PlayerStatus.DEAD) {
                                    player.setPosition(player.getInitial());
                                }
                            } else {
                                inputAccepted = false;
                            }
                        }
                    } while (!inputAccepted);
                }
                game.generateHTML();
            } while (!isGameWon);


        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
