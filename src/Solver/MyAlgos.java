package Solver;

import Images.State;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class MyAlgos implements Solution, Serializable {
    private List<State<?>> stateList;
    private State<?> initialState, goalState;

    public MyAlgos(List<State<?>> stateList, State initialState, State goalState) {
        this.stateList = stateList;
        this.initialState = initialState;
        this.goalState = goalState;
    }

    @Override
    public State<?> getInitialState() {
        return initialState;
    }

    @Override
    public State<?> getGoalState() {
        return goalState;
    }

    @Override
    public List<State<?>> getStateList() {
        return stateList;
    }

    @Override
    public void reverse() {
        Collections.reverse(stateList);
    }


}