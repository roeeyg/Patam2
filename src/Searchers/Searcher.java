package Searchers;

import Solver.Solution;

public interface Searcher {
    Solution search(Searchable s);
}