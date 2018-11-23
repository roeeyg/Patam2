package Searchers;

import Images.State;
import Solver.Solution;

import java.util.HashSet;
import java.util.List;

public class BFS extends GeneralSearcher {
    @Override
    <T> Solution searchAlgorithm(Searchable<T>  s) {
        addToOpenList(s.getInitialState());
        HashSet<State<T>> closedSet = new HashSet<>();

        while (!openList.isEmpty()) {
            State<T> state = popOpenList();
            closedSet.add(state);

            if (s.isGoal(state)) {
                return backtraceSolution(state, s.getInitialState());
            }

            List<State<T>> PotentialStates = s.getPotentialStates(state);

            for (State<T> possibleState: PotentialStates) {
                if (!closedSet.contains(possibleState) && !openList.contains(possibleState)) {
                    possibleState.setPreviousPosition(state);
                    possibleState.setCost(state.getCost() + 1);
                    addToOpenList(possibleState);
                }
            }

        }

        return null;
    }
}