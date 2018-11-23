package Searchers;

import Images.State;
import Solver.Solution;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HillClimbing extends GeneralSearcher {
    @Override
    <T> Solution searchAlgorithm(Searchable<T> s) {
        State<T> curState = s.getInitialState();
        State<T> bestNextState = null;

        while (true) {
            List<State<T>> PotentialStates = new ArrayList<>(s.getPotentialStates(curState));
            for (State<T> possibleState : PotentialStates) {
                possibleState.setPreviousPosition(curState);
            }
            double grade = Double.MAX_VALUE;
            if (Math.random() < 0.5) {
                for (State<T> possibleState : PotentialStates) {
                    addNode();
                    double g = s.grade(possibleState);
                    if (g < grade) {
                        bestNextState = possibleState;
                        grade = g;
                    }
                }

                if (bestNextState == null) {
                    bestNextState = curState;
                }

                if (s.isGoal(bestNextState)) {
                    return backtraceSolution(bestNextState, s.getInitialState());
                }

                if (s.grade(curState) > s.grade(bestNextState)) {
                    curState = bestNextState;
                }
            } else {
                if (PotentialStates.isEmpty()) {
                    break;
                }

                Random rnd = new Random();
                int rndInd = rnd.nextInt(PotentialStates.size());
                curState = PotentialStates.get(rndInd);
            }
        }

        return null;
    }
}