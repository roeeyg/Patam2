package Searchers;

import Images.State;

import java.util.List;

public interface Searchable<T> {
    State<T> getInitialState();

    boolean isGoal(State<T> state);

    List<State<T>> getPotentialStates(State<T> s);

    double grade(State<T> state);
}