package Game;

import HTML.HTMLGenerator;
import Map.Map;
import Player.Player;
import Team.Team;
import edu.emory.mathcs.backport.java.util.Collections;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class Game {

    public Player[] players;
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
    public String listOfWinners(List<String> winners) {
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

}
