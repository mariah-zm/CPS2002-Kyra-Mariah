package Team;

import Player.Position;

import java.util.ArrayList;
import java.util.List;

//Subject class for Observer DP
public class Subject {

    //List of observers registered with subject
    protected List<Observer> observers = new ArrayList<>();
    //The current position of the Subject, i.e. the state
    protected Position position;

    //Getter for current position
    public Position getSubjectPosition(){
        return position;
    }

    //Adds a new Observer to Subject
    public void registerObserver(Observer observer){
        this.observers.add(observer);
    }

    //Updates all Observers
    public void notifyObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }

    //Returns list of observers registered with Subject
    public List<Observer> getObservers() {
        return new ArrayList<>(this.observers);
    }

}