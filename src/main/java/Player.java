import java.util.Random;

public class Player {

    private Position initial; //will store the randomly generated initial position
    private Position current; //the player's position that will change throughout the game
    private Map map; //a copy of the generated map from the player's perspective
    public int ID;


    //class constructor
    public Player( Map map) {
        this.map = map;
        this.initial = setInitial();
        this.current = this.initial; //this will start off as initial
    }



    //setting random initial position
    public Position setInitial() {

       Random rand = new Random();
        int x, y;
        //generating a random position
        x = rand.nextInt(map.getSize());
        y = rand.nextInt(map.getSize());
        //validating that the randomly generated position is a Grass tile

        if((map.getTile(x, y).getType() != TileType.GRASS)){
            setInitial();
        }
        //return once valid
        this.map.getTile(x,y).setUncovered();
        return new Position(x,y);

    }
    //checking if new coordinates are in map boundary
    public boolean setPosition(Position p) {
        int x = p.getX();
        int y = p.getY();

        if (x > 0 && x < map.getSize() && y > 0 && y < map.getSize()) {
            //if legal move, set new position
            this.current.setX(x);
            this.current.setY(y);
            this.map.getTile(x,y).setUncovered();
            return true;
        }
        return false;
    }

    public boolean move(Direction direction) {
        //temporary variables to validate move
        int X = this.current.getX();
        int Y = this.current.getY();

        //setting new coordinates accordingly
        switch (direction) {
            case UP:
                Y += 1; //y-coordinate moves up by 1
                break;
            case DOWN:
                Y -= 1; //y-coordinate moves down by 1
                break;
            case RIGHT:
                X += 1; //x-coordinate moves right by 1
                break;
            case LEFT:
                X -= 1; //x-coordinate moves left by 1
                break;
            default:
                //in the case of an invalid token the player does not move
                return false;
        }

        //validating move - checking if legal
        if (!setPosition(new Position(X, Y))) { //size will be obtained from map itself
            System.out.println("Illegal move.");
            return false;
        }else {
            return true;
        }
    }


    //getter for current position
    public Position getCurrent(){
        return this.current;
    }

    //getter for player's map
    public Map getMap(){
        return this.map;
    }
}
