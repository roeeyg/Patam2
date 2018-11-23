package Solver;

import Images.State;

import java.util.List;

public interface Solution {
    List getStateList();

    void reverse();

    State<?> getInitialState();

    State<?> getGoalState();

}