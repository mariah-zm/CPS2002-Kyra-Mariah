package Game;

import Map.MapFactory;
import Map.MapMode;

import java.util.Scanner;

public class GameLoop {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        boolean inputAccepted;
        Game game = null;
        MapMode mode = null;
        int size, playerCount = 0;

        System.out.println("WELCOME TO HIDDEN TREASURE\n* * * * * * * * * * *\n");

        try {
            do {
                System.out.println("Enter game mode: \n(1) Solo \n(2) Collaborative");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();

                    switch (choice) {
                        case 1:
                            game = new SoloGame();
                            inputAccepted = true;
                            break;

                        case 2:
                            game = new CollabGame();
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

            //Validating number of players
            do {
                System.out.println("Enter number of players: ");
                if (scanner.hasNextInt()) {
                    playerCount = scanner.nextInt();
                    inputAccepted = game.setNumPlayers(playerCount);
                } else {
                    scanner.nextLine();
                    System.out.println("\nInvalid input!\n\n");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            //Validating map size
            do {
                System.out.println("Enter map size: ");
                if (scanner.hasNextInt()) {
                    size = scanner.nextInt();
                    inputAccepted = game.map.setSize(size, playerCount);
                } else {
                    scanner.nextLine();
                    System.out.println("Not an integer!");
                    inputAccepted = false;
                }
            } while (!inputAccepted);

            do {
                System.out.println("Enter map type: \n(1) Safe \n(2) Hazardous");

                if (scanner.hasNextInt()) {
                    choice = scanner.nextInt();

                    switch (choice) {
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

            game.map = MapFactory.getMap(mode);


            //Generating the map with tiles according to the map mode
            game.map.generate();


        } catch (Exception e) {
            System.exit(1);
        }
        System.exit(0);
    }
}
