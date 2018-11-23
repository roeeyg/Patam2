package Searchers;

import Searchers.*;

import Images.State;
import Solver.Solution;

import java.util.HashSet;
import java.util.List;

public class BestFirstSearch extends GeneralSearcher {

    @Override
    <T> Solution searchAlgorithm(Searchable<T>  s) {
        addToOpenList(s.getInitialState());
        HashSet<State> closedSet = new HashSet<>();

        while (!openList.isEmpty()) {
            State<T> state = popOpenList();
            closedSet.add(state);

            if (s.isGoal(state)) {
                return backtraceSolution(state, s.getInitialState());
            }

            List<State<T>> PotentialStates = s.getPotentialStates(state);

            for (State<T> possibleState : PotentialStates) {
                if (!closedSet.contains(possibleState)) {
                    possibleState.setPreviousPosition(state);
                    possibleState.setCost(state.getCost() + 1);
                    if (!openList.contains(possibleState)) {
                        addToOpenList(possibleState);
                    } else if (openList.removeIf(e -> e.equals(possibleState) && e.getCost() > possibleState.getCost())) {
                        addToOpenList(possibleState);
                    }
                }
            }
        }

        return null;
    }


}