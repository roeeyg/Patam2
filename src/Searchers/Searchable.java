package Searchers;

import Images.State;

import java.util.List;

public interface Searchable<T> {

	State<T> getInitState();
	boolean isGoal(State<T> state);
	List<State<T>> getAllStates(State<T> state);

}