package Team;

import Map.Tile;

import java.util.ArrayList;
import java.util.List;

//Subject class for Observer DP
public class Subject {

    //List of observers registered with subject
    protected List<Observer> observers = new ArrayList<>();
    //The last tile discovered by team, i.e. the state
    protected Tile lastExplored;

    //Getter for current position
    public Tile getSubjectLastExplored() {
        return lastExplored;
    }

    //Notifies all Observers when a new tile is discovered
    public void setSubjectLastExplored(Tile newTile) {
        this.lastExplored = newTile;
        this.notifyObservers();
    }

    //Adds a new Observer to Subject
    public void registerObserver(Observer observer) {
        if(!this.observers.contains(observer)){
            this.observers.add(observer);
        }
    }

    //Updates all Observers
    private void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    //Returns list of observers registered with Subject
    public List<Observer> getObservers() {
        return new ArrayList<>(this.observers);
    }

}