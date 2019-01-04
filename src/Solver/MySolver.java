package Solver;

import Searchers.*;
import Images.MyState;

public class MySolver implements Solver {

    @Override
    public Solution solve(char[][] level, int rows, int cols) {
    	PipeSolver pipegame = new PipeSolver(new MyState(level, rows), rows, cols);
        Solution solution = new BestFirstSearch().searchAlgorithm(pipegame);
//        Solution solution = new BFS<String>().search(pipegame);
//        Solution solution = new DFS().search(pipegame);
//        Solution solution = new HillClimbing().search(pipegame);
        return solution;
    }	
}