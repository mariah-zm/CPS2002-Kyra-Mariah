package Player;

import Map.Map;
import Map.Tile;
import Map.TileType;
import Team.Observer;
import Team.Team;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//Concrete Observer of Observer DP
public class Player extends Observer {

    private Position initial; //Will store the randomly generated initial position
    private Position current; //The player's position that will change throughout the game
    private Map map; //Holds a reference to the singleton instance
    private PlayerStatus status; //Player's status depends on last discovered tile
    private List<Tile> discoveredTiles; //A list of tiles discovered by Player
    private String playerID;

    //Class constructor
    public Player(Map map, int ID) {
        this.map = map;
        this.initial = setInitial();
        this.current = new Position(this.initial.getX(), this.initial.getY()); //This will start off as initial
        this.status = PlayerStatus.SAFE;
        setPlayerID("Player " + ID);

        this.discoveredTiles = new ArrayList<>();
        addDiscoveredTile(map.getTile(initial.getX(), initial.getY()));
    }

    //Setting a valid random initial position
    public Position setInitial() {
        Random rand = new Random();

        int x, y;
        //Validating that the randomly generated position is a Grass tile
        do {  //Generating a random position
            x = rand.nextInt(map.getSize());
            y = rand.nextInt(map.getSize());
        } while (map.getTile(x, y).getType() != TileType.GRASS);

        //Return once valid
        return new Position(x, y);
    }

    //Checking if new coordinates are in map boundary
    public boolean setPosition(Position p) {
        int x = p.getX();
        int y = p.getY();

        if (x >= 0 && x < map.getSize() && y >= 0 && y < map.getSize()) {
            //If legal move, set new position
            this.current.setX(x);
            this.current.setY(y);
            return true;
        }
        return false;
    }

    //Assigning Player to Team
    public void addToTeam(Team team) {
        this.subject = team;
        this.subject.registerObserver(this);
        setPlayerID(team.getTeamID() + " - " + this.playerID);
    }

    public boolean move(Direction direction) {
        //Temporary variables to validate move
        int X = current.getX();
        int Y = current.getY();

        //Setting new coordinates accordingly
        switch (direction) {
            case UP:
                X -= 1; //x-coordinate moves up by 1
                break;
            case DOWN:
                X += 1; //x-coordinate moves down by 1
                break;
            case RIGHT:
                Y += 1; //y-coordinate moves right by 1
                break;
            case LEFT:
                Y -= 1; //y-coordinate moves left by 1
                break;
        }

        //Validating move - checking if legal
        if (!setPosition(new Position(X, Y))) { //size will be obtained from map itself
            System.out.println("Illegal move.");
            return false;
        }

        //Adds discovered tile
        addDiscoveredTile(map.getTile(X, Y));

        //Setting status according to discovered tile type
        setStatus(map.getTile(current.getX(), current.getY()).getType());
        return true;
    }

    //Adds discovered tiles to list only if new
    public void addDiscoveredTile(Tile tile) {
        if (!discoveredTiles.contains(tile)) {
            discoveredTiles.add(tile);
        }
    }

    //Getter for the list of tiles the player visited
    public List<Tile> getDiscoveredTiles() {
        return discoveredTiles;
    }

    //Getter for initial position
    public Position getInitial() {
        return initial;
    }

    //Getter for current position
    public Position getCurrent() {
        return current;
    }

    //Getter for Player's map
    public Map getMap() {
        return map;
    }

    //Getter for Player's status
    public PlayerStatus getStatus() {
        return status;
    }

    //Setter for Player ID
    public void setPlayerID(String ID){
        this.playerID = ID;
    }

    //Getter for Player ID
    public String getPlayerID() {
        return playerID;
    }

    //Setter for status
    public void setStatus(TileType type) {
        status = PlayerStatus.getStatus(type);
    }

    //Updating discoveredTiles list by adding tiles discovered by other Players
    @Override
    public void update() {
        Tile discoveredTile = this.subject.getSubjectLastExplored();
        addDiscoveredTile(discoveredTile);
    }

}