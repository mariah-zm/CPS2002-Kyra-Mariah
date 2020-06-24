package Player;

public class Position {

    private int x; //x-coordinate
    private int y; //y-coordinate

    //class constructor
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //setter for x-coordinate
    public void setX(int x){
        this.x = x;
    }

    //setter for y-coordinate
    public void setY(int y){
        this.y = y;
    }

    //getter for x-coordinate
    public int getX(){
        return x;
    }

    //getter for y-coordinate
    public int getY(){
        return y;
    }

}

