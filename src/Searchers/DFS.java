package Searchers;

import Images.State;
import Solver.Solution;

import java.util.HashSet;
import java.util.List;
import java.util.Stack;

public class DFS extends GeneralSearcher {
    @Override
    <T> Solution searchAlgorithm(Searchable<T>  s) {
        Stack<State<T>> stack = new Stack<>();
        HashSet<State> closedSet = new HashSet<>();

        stack.add(s.getInitialState());

        while (!stack.isEmpty()) {
            State<T> state = stack.pop();
            addNode();
            closedSet.add(state);

            if (s.isGoal(state)) {
                return backtraceSolution(state, s.getInitialState());
            }

            List<State<T>> PotentialStates = s.getPotentialStates(state);

            for (State<T> possibleState : PotentialStates) {
                if (!closedSet.contains(possibleState) && !stack.contains(possibleState)) {
                    possibleState.setPreviousPosition(state);
                    possibleState.setCost(state.getCost() + 1);
                    stack.add(possibleState);
                }
            }
        }

        return null;
    }
}