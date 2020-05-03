import java.util.ArrayList;
import java.util.Random;

public class Player {

    private Position initial; //will store the randomly generated initial position
    private Position current; //the player's position that will change throughout the game
    private Map map; //a copy of the generated map from the player's perspective
    private PlayerStatus status;

    //class constructor
    public Player(Map map) {
        this.map = map;
        this.initial = setInitial();
        this.current = new Position(this.initial.getX(),this.initial.getY()); //this will start off as initial
        this.status = PlayerStatus.SAFE;
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

        map.getTile(x,y).setUncovered();
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

        //uncover discovered tile
        map.getTile(X,Y).setUncovered();

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
}
