package Searchers;

import Images.State;
import Solver.MyAlgos;
import Solver.Solution;

import java.util.ArrayList;
import java.util.PriorityQueue;

abstract class GeneralSearcher implements Searcher {
    PriorityQueue<State<?>> openList = new PriorityQueue<>();

    private int evaluatedNodes = 0;

    abstract <T> Solution searchAlgorithm(Searchable<T> s);

    void addNode() {
        evaluatedNodes++;
    }

    @Override
    public Solution search(Searchable s) {
        long startTime = System.currentTimeMillis();
//        System.out.println("searchAlgorithm started");

        Solution solution = searchAlgorithm(s);

        long stopTime = System.currentTimeMillis();
        double elapsedTime = stopTime - startTime;
//        System.out.println("searchAlgorithm total seconds: " + elapsedTime / 1000);
//        System.out.println("searchAlgorithm total evaluated nodes: " + evaluatedNodes);
        return solution;
    }

    <T> State<T> popOpenList() {
        addNode();
        return (State<T>) openList.poll();
    }

    void addToOpenList(State<?> state) {
        openList.add(state);
    }

    Solution backtraceSolution(State<?> state, State<?> initialState) {
        ArrayList<State<?>> list = new ArrayList<>();
        State<?> n = state;
        list.add(n);
        while (n.getPreviousPosition() != null) {
            list.add(n.getPreviousPosition());
            n = n.getPreviousPosition();
        }
        Solution solution = new MyAlgos(list, initialState, state);
        solution.reverse();
        return solution;
    }

}