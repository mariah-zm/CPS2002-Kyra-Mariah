package Team;

public abstract class Observer extends Subject{
    public Subject subject = null;
    public abstract void update();

}
