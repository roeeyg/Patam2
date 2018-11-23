package Images;

import java.io.Serializable;

public interface State<T> extends Comparable<State<T>>, Serializable {
    T getState();

    NextPosition getNextPosition();

    double getCost();

    void setCost(double cost);

    void printState();

    double getGrade();
    
    void setPreviousPosition(State<T> n);

    State<T> getPreviousPosition();

    void setGrade(double grade);
}