package Team;

import Position.Position;

import java.util.ArrayList;
import java.util.List;

//Implementing Observer design pattern as per standard design
public class Subject {

    protected List<Observer> observers = new ArrayList<>();
    protected Position position;

    public Position getSubjectPosition(){
        return position;
    }


    public void notifyAllObservers(){
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void setSubjectPosition( Position position){
        this.position = position;
        this.notifyAllObservers();
    }
    public List<Observer> getObservers() {
        return new ArrayList<>(this.observers);
    }
}
