package Searchers;

import Images.MyState;
import Images.State;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PipeSolver implements Searchable<char[][]> {
    private MyState initialState;
    private int col, row, goalcol, goalrow, startcol, startrow;

    public PipeSolver(MyState initialState, int row, int col) {
        this.initialState = initialState;
        this.row = row;
        this.col = col;
        findStartAndGoal();
    }
    
    private void findStartAndGoal() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (initialState.getState()[i][j] == 's') {
                    startrow = i;
                    startcol = j;
                } else if (initialState.getState()[i][j] == 'g') {
                    goalcol = i;
                    goalrow = j;
                }
            }
        }
    }

    @Override
    public State<char[][]> getInitialState() {
        return initialState;
    }
/*
    @Override
	public boolean isGoal(State<char[][]> problem) {
		int i = 0;
		int j = 0;
		
		try {
				if((problem.getState()[i+1][j] == '7') || (problem.getState()[i+1][j] == 'J') || (problem.getState()[i+1][j] == '-'))
				{
					if(isGoal(problem.getState(), i+1, j, 1))
						return true;
				}
				if((problem.getState()[i][j+1] == '|') || (problem.getState()[i][j+1] == 'L') || (problem.getState()[i][j+1] == 'J'))
				{
					if(isGoal(problem.getState(), i, j+1, -2))
						return true;
				}
				if((problem.getState()[i-1][j] == 'L') || (problem.getState()[i-1][j] == 'F') || (problem.getState()[i-1][j] == '-'))
				{
					if(isGoal(problem.getState(), i-1, j, -1))
						return true;
				}
				if((problem.getState()[i][j-1] == '|') || (problem.getState()[i][j-1] == 'F') || (problem.getState()[i][j-1] == '7'))
				{
					if(isGoal(problem.getState(), i, j-1, 2))
						return true;
				}
				return false;
			}
		
			//return isGoal(problem.getState(), i, j, 3);
		 catch (ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

    public boolean isGoal(char[][] Matrix , int i , int j , int direction) throws ArrayIndexOutOfBoundsException
	{
		try {
			if(Matrix[i][j] == 'g')
				return true;
			if(direction == 1)
			{
				if(Matrix[i][j] == '-')
					return isGoal(Matrix, i+1, j, 1);
				else if (Matrix[i][j] == '7')
					return isGoal(Matrix, i, j+1, -2);
				else if ((Matrix[i][j] == 'J'))
					return isGoal(Matrix, i, j-1, 2);
				else
					return false;
				
			}
			else if(direction == 2)
			{
				if(Matrix[i][j] == '|')
					return isGoal(Matrix, i, j-1, 2);
				else if (Matrix[i][j] == '7')
					return isGoal(Matrix, i-1, j, -1);
				else if ((Matrix[i][j] == 'F'))
					return isGoal(Matrix, i+1, j, 1);
				else
					return false;
				
			}
			else if(direction == -1)
			{
				if(Matrix[i][j] == '-')
					return isGoal(Matrix, i-1, j, -1);
				else if (Matrix[i][j] == 'L')
					return isGoal(Matrix, i, j-1, 2);
				else if ((Matrix[i][j] == 'F'))
					return isGoal(Matrix, i, j+1, -2);
				else
					return false;
				
			}
			else if(direction == -2)
			{
				if(Matrix[i][j] == '|')
					return isGoal(Matrix, i, j+1, -2);
				else if (Matrix[i][j] == 'L')
					return isGoal(Matrix, i+1, j, 1);
				else if ((Matrix[i][j] == 'J'))
					return isGoal(Matrix, i-1, j, -1);
				else
					return false;
				
			}
	}
		catch(ArrayIndexOutOfBoundsException e)
		{
			return false;
		}
		
		return false;
	}
    
    */
    
  
    @Override
    public boolean isGoal(State<char[][]> state) {
        
        boolean result = false;
        char[][] level = state.getState();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (level[i][j] == 's') {
                    if (isValidIndex(i + 1, j)
                            && isMatch('s', level[i + 1][j], Direction.Down)) {
                        result = isGoal(i + 1, j, level, Direction.Down);
                        if (result) return true;
                    }
                    if (isValidIndex(i, j + 1)
                            && isMatch('s', level[i][j + 1], Direction.Right)) {
                        result = isGoal(i, j + 1, level, Direction.Right);
                        if (result) return true;
                    }
                    if (isValidIndex(i - 1, j)
                            && isMatch('s', level[i - 1][j], Direction.Up)) {
                        result = isGoal(i - 1, j, level, Direction.Up);
                        if (result) return true;
                    }
                    if (isValidIndex(i, j - 1)
                            && isMatch('s', level[i][j - 1], Direction.Left)) {
                        result = isGoal(i, j - 1, level, Direction.Left);
                        if (result) return true;
                    }
                }
            }
        }
        return false;
    }


    private boolean isGoal(int row, int col, char[][] level, Direction fromDirection) {
        if (level[row][col] == 'g') {
            return true;
        } else if (!isValidIndex(row, col)) {
            return false;
        } else if (isValidIndex(row + 1, col)
                && fromDirection != Direction.Up
                && isMatch(level[row][col], level[row + 1][col], Direction.Down)) {
            return isGoal(row + 1, col, level, Direction.Down);
        } else if (isValidIndex(row, col + 1)
                && fromDirection != Direction.Left
                && isMatch(level[row][col], level[row][col + 1], Direction.Right)) {
            return isGoal(row, col + 1, level, Direction.Right);
        } else if (isValidIndex(row - 1, col)
                && fromDirection != Direction.Down
                && isMatch(level[row][col], level[row - 1][col], Direction.Up)) {
            return isGoal(row - 1, col, level, Direction.Up);
        } else if (isValidIndex(row, col - 1)
                && fromDirection != Direction.Right
                && isMatch(level[row][col], level[row][col - 1], Direction.Left)) {
            return isGoal(row, col - 1, level, Direction.Left);
        }
        return false;
    }

    private boolean isMatch(char from, char to, Direction direction) {
        switch (direction) {
            case Up:
                switch (from) {
                    case '|':
                    case 'L':
                    case 'J':
                    case 's':
                        return to == '|' || to == 'F' || to == '7' || to == 'g';
                    default:
                        return false;
                }
            case Down:
                switch (from) {
                    case '|':
                    case '7':
                    case 'F':
                    case 's':
                        return to == 'L' || to == 'J' || to == '|' || to == 'g';
                    default:
                        return false;
                }
            case Left:
                switch (from) {
                    case '7':
                    case '-':
                    case 'J':
                    case 's':
                        return to == 'L' || to == '-' || to == 'F' || to == 'g';
                    default:
                        return false;
                }
            case Right:
                switch (from) {
                    case '-':
                    case 'L':
                    case 'F':
                    case 's':
                        return to == '-' || to == 'J' || to == '7' || to == 'g';
                    default:
                        return false;
                }
        }

        return false;
    }

    private boolean isValidIndex(int row, int col) {
        return row >= 0 && row < row && col >= 0 && col < col;
    }

    private char[][] convertStringToChar(String levelString) {
        char[][] level = new char[row][col];
        for (int i = 0; i < row; i++) {
            level[i] = new char[col];
            level[i] = levelString.substring(i * col, (i * col) + col).toCharArray();
        }
        return level;
    }

    @Override
    public List<State<char[][]>> getPotentialStates(State<char[][]> s) {
        ArrayList<State<char[][]>> possibleStates = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                char[][] newState = new char[row][];

                for (int l = 0; l < row; l++) {
                    newState[l] = s.getState()[l].clone();
                }

                newState[i][j] = getNextChar(newState[i][j]);
                possibleStates.add(new MyState(newState, row));
            }
        }

        return possibleStates;
    }

    @Override
    public double grade(State state) {
        return heuristicGrade(state);
    }

    public double heuristicGrade(State<char[][]> state) {
        return manhattanGrade(state.getState(), startrow, startcol, Direction.Start);
    }

    public static char getNextChar(char c) {
        switch (c) {
            case '|':
                return '-';
            case '-':
                return '|';
            case 'L':
                return 'F';
            case 'F':
                return '7';
            case '7':
                return 'J';
            case 'J':
                return 'L';
            case 's':
                return 's';
            case 'g':
                return 'g';
            default:
                return ' ';
        }
    }

    private double manhattanGrade(char[][] board, int row, int col, Direction c) {
        int moveRight = col + 1;
        int moveLeft = col - 1;
        int moveUp = row - 1;
        int moveDown = row + 1;
        if (!isValidIndex(row, col))
            return 0;
        if (board[row][col] == 'g')
            return 1;
        if (board[row][col] == ' ')
            return 0;
        if (board[row][col] == 'g' && c != Direction.Start)
            return 0;
        switch (c) {
            case Start:
                return minOfValues(manhattanGrade(board, moveUp, col, Direction.Up),
                        manhattanGrade(board, moveDown, col, Direction.Down),
                        manhattanGrade(board, row, moveRight, Direction.Right),
                        manhattanGrade(board, row, moveLeft, Direction.Left));

            case Up:
                if (board[row][col] == '|')
                    return manhattanGrade(board, moveUp, col, Direction.Up) ;
                else if (board[row][col] == 'F')
                    return manhattanGrade(board, row, moveRight, Direction.Right) ;
                else if (board[row][col] == '7')
                    return manhattanGrade(board, row, moveLeft, Direction.Left) ;
                else
                    return Math.abs(Point.distance(row, col, goalcol, goalrow));
            case Down:
                if (board[row][col] == '|')
                    return manhattanGrade(board, moveDown, col, Direction.Down) ;
                else if (board[row][col] == 'L')
                    return manhattanGrade(board, row, moveRight, Direction.Right) ;
                else if (board[row][col] == 'J')
                    return manhattanGrade(board, row, moveLeft, Direction.Left) ;
                else
                    return Math.abs(Point.distance(row, col, goalcol, goalrow));
            case Left:
                if (board[row][col] == '-')
                    return manhattanGrade(board, row, moveLeft, Direction.Left) ;
                else if (board[row][col] == 'L')
                    return manhattanGrade(board, moveUp, col, Direction.Up);
                else if (board[row][col] == 'F')
                    return manhattanGrade(board, moveDown, col, Direction.Down) ;
                else
                    return Math.abs(Point.distance(row, col, goalcol, goalrow));
            case Right:
                if (board[row][col] == '-')
                    return manhattanGrade(board, row, moveRight, Direction.Right);
                else if (board[row][col] == '7')
                    return manhattanGrade(board, moveDown, col, Direction.Down);
                else if (board[row][col] == 'J')
                    return manhattanGrade(board, moveUp, col, Direction.Up) ;
                else
                    return Math.abs(Point.distance(row, col, goalcol, goalrow));
            default:
                return 0;
        }


    }

    public static double minOfValues(Double... values) {
        return Collections.min(Arrays.asList(values));
    }

    enum Direction {
        Up, Down, Left, Right, Start, End;
    }
}