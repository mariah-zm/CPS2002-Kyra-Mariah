package Team;

import Direction.Direction;
import Position.Position;
import Map.*;
import Game.Game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Player extends Observer {

    private Position initial; //will store the randomly generated initial position
    private Position current; //the player's position that will change throughout the game
    private Map map; //holds a reference to the singleton instance
    private PlayerStatus status;
    private List<Tile> discoveredTiles;

    //class constructor
    public Player(Map map) {
        this.map = map;
        this.initial = setInitial();
        this.current = new Position(this.initial.getX(),this.initial.getY()); //this will start off as initial
        this.status = PlayerStatus.SAFE;

        this.discoveredTiles = new ArrayList<>();
        discoveredTiles.add(map.getTile(initial.getX(), initial.getY()));
    }

    //setting random initial position
    public Position setInitial() {
        Random rand = new Random();

        int x, y;
        //validating that the randomly generated position is a Grass tile
        do {  //generating a random position
            x = rand.nextInt(map.getSize());
            y = rand.nextInt(map.getSize());
        }while(map.getTile(x,y).getType() != TileType.GRASS);

        //return once valid
        return new Position(x, y);
    }

    //checking if new coordinates are in map boundary
    public boolean setPosition(Position p) {
        int x = p.getX();
        int y = p.getY();

        if (x >= 0 && x < map.getSize() && y >= 0 && y < map.getSize()) {
            //if legal move, set new position
            this.current.setX(x);
            this.current.setY(y);
            return true;
        }
        return false;
    }

    public void addToTeam(Team team){
        //assigning this team as this player's subject
        this.subject = team;
        observers.add(this);
    }


    public boolean move(Direction direction) {
        //temporary variables to validate move
        int X = current.getX();
        int Y = current.getY();

        //setting new coordinates accordingly
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
            default:
                //in the case of an invalid token the player does not move
                return false;
        }

        //validating move - checking if legal
        if (!setPosition(new Position(X, Y))){ //size will be obtained from map itself
            System.out.println("Illegal move.");
            return false;
        }

        //adds discovered tile
        discoveredTiles.add(map.getTile(X, Y));

        //setting status according to discovered tile type
        setStatus(map.getTile(current.getX(),current.getY()).getType());
        return true;
    }

    //getter for initial position
    public Position getInitial(){
        return initial;
    }

    //getter for current position
    public Position getCurrent(){
        return current;
    }

    //getter for player's map
    public Map getMap(){
        return map;
    }

    //getter for player's status
    public PlayerStatus getStatus(){
        return status;
    }

    //setter for status
    public void setStatus(TileType type) {
        status = PlayerStatus.getStatus(type);
    }

    //getter for the list of tiles the player visited
    public List<Tile> getDiscoveredTiles() {
        return discoveredTiles;
    }

    @Override
    public void update() {
        //exposing tiles for all players in a certain team
        Map map = Game.map;
        Position discoveredPos = this.subject.getSubjectPosition();
        Tile discoveredTile = map.getTile(discoveredPos.getX(),discoveredPos.getY());
        this.discoveredTiles.add(discoveredTile);
    }


}
