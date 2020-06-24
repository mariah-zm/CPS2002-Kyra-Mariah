package Game;

import Player.Direction;
import Map.MapFactory;
import Map.MapMode;
import Player.Player;
import Player.PlayerStatus;
import Team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayGame {

    //Method to pick game mode - Solo or Teams
    public static boolean gameModeMenu(){
        Scanner scanner = new Scanner(System.in);
        boolean inputAccepted, mode = false;
        int userInput;

        do {
            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt();

                switch (userInput) {
                    case 1:
                        inputAccepted = true;
                        break;
                    case 2:
                        mode = true;
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

        return mode;
    }

    //Method to pick map type - Hazardous or Safe
    public static MapMode mapSetUp(){
        Scanner scanner = new Scanner(System.in);
        boolean inputAccepted;
        int userInput;
        MapMode mode = null;

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

        return mode;
    }

    //Game loop in solo mode
    public static List<String> soloMode(Player[] players) {
        Scanner scanner = new Scanner(System.in);

        List<String> winningPlayers = new ArrayList<>();

        //Variables to control movement logic
        Direction move;
        String moveInput;
        boolean inputAccepted;

        //Getting players' moves
        for (Player player : players) {
            System.out.println(player.getPlayerID() + "'s turn:");

            do {
                System.out.println("Enter direction (U,D,R,L):");
                moveInput = scanner.next();
                //Getting corresponding direction
                move = Direction.getDirection(moveInput.charAt(0));

                //Direction validation
                if (move == null) {
                    System.out.println("Please enter a valid direction.");
                    inputAccepted = false;
                } else {
                    if (player.move(move)) {
                        inputAccepted = true;

                        //if treasure tile is found by the player, game ends
                        if (player.getStatus() == PlayerStatus.WINS) {
                            winningPlayers.add(player.getPlayerID());
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
        return winningPlayers;
    }

    //Displays Teams and Players in them
    public static void displayTeams(Team[] teams) {
        String id;
        for (Team team : teams) {
            System.out.println(team.getTeamID());
            for (Object player : team.getObservers()) {
                id = ((Player) player).getPlayerID();
                System.out.println(id.substring(id.lastIndexOf("-")));
            }
        }
    }

    //Game loop in team mode
    public static List<String> collabMode(Team[] teams) {
        Scanner scanner = new Scanner(System.in);

        List<String> winningTeams = new ArrayList<>();

        //variables to control movement logic
        Direction move;
        String moveInput, id, currentPlayerID;
        boolean inputAccepted;
        Player currentPlayer;

        for (Team team : teams) {
            System.out.println(team.getTeamID() + " is playing");
            for (Object player : team.getObservers()) {
                currentPlayer = ((Player) player);
                id = currentPlayer.getPlayerID();
                currentPlayerID = id.substring(id.lastIndexOf("-")+1);
                System.out.println(currentPlayerID + "'s turn:");

                do {
                    System.out.println("Enter direction (U,D,R,L):");
                    moveInput = scanner.next();
                    //Getting corresponding direction
                    move = Direction.getDirection(moveInput.charAt(0));

                    //Direction validation
                    if (move == null) {
                        System.out.println("Please enter a valid direction.");
                        inputAccepted = false;
                    } else {
                        if (currentPlayer.move(move)) {
                            inputAccepted = true;

                            //if treasure tile is found by the player, game ends
                            if (currentPlayer.getStatus() == PlayerStatus.WINS) {
                                if(!winningTeams.contains(team.getTeamID())) winningTeams.add(team.getTeamID());
                            }
                            if (currentPlayer.getStatus() == PlayerStatus.DEAD) {
                                currentPlayer.setPosition(currentPlayer.getInitial());
                            }
                        } else {
                            inputAccepted = false;
                        }
                    }
                } while (!inputAccepted);
            }
        }
        return winningTeams;
    }

    public static void main(String[] args) {
        //starting the game
        Game game = new Game();
        List<String> winners;

        Scanner scanner = new Scanner(System.in);

        boolean inputAccepted, teamMode;
        MapMode mapMode;
        //variable for menu input
        int userInput;

        System.out.println("WELCOME TO HIDDEN TREASURE\n\n* * * * * * * * * * *\n");

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

            //Picking game mode
            System.out.println("How would you like to play? \n(1) Solo \n(2) Collaborative");
            teamMode = gameModeMenu();

            //Validating number of teams
            if (teamMode) {
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

            mapMode = mapSetUp();

            //Getting map Singleton according to map mode
            game.map = MapFactory.getMap(mapMode);
            //Generating the map with tiles according to the map mode
            game.map.generate();

            //initialising players array
            game.createPlayers();
            game.generateHTML();

            System.out.println("\n\nLaunching Game...\n");

            //Generating teams
            if (teamMode) {
                game.createTeams();
                displayTeams(game.teams);
            }

            do {
                System.out.println("\n*\t*\t*\t*\t*\t*\t*\t*\n");

                if (teamMode) {
                    winners = collabMode(game.teams);
                } else {
                    winners = soloMode(game.players);
                }

                game.generateHTML();
            } while (winners.isEmpty());

            System.out.println(game.listOfWinners(winners));

        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}