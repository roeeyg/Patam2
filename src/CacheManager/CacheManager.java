package CacheManager;

import Solver.Solution;

// CacheManager saves and loads a game to cache
public interface CacheManager {
    void save(String problem, Solution solution);
    Solution load(String problem);
}