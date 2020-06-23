package Team;

//Observer class for Observer DP
public abstract class Observer extends Subject{

    public Subject subject = null;

    public abstract void update();

}