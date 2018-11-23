package Images;


import java.io.Serializable;
import java.util.Arrays;

public class MyState implements State, Serializable {
    private int hashCode;
	private double cost;
	private char[][] state;
    private State<char[][]> PreviousPosition;
    private double grade;
    private int row;

    @Override
    public char[][] getState() {
        return state;
    }
    
    public MyState(char[][] state, int row) {
        this.state = state;
        this.row = row;
        this.hashCode = hashCode();
    }

    @Override
    public State getPreviousPosition() {
        return PreviousPosition;
    }

    @Override
    public void setPreviousPosition(State n) {
        PreviousPosition = n;
    }

    @Override
    public void setCost(double cost) {
        this.cost = cost;
    }
    
    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public void printState() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < state[i].length; j++) {
                System.out.print(state[i][j]);
            }
            System.out.println();
        }
    }
    
    @Override
    public MyNextPosition getNextPosition() {
        if (PreviousPosition == null) {
            return new MyNextPosition();
        }
        // Scan matrix and detect the changed cell
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < state[i].length; j++) {
                if (state[i][j] != PreviousPosition.getState()[i][j]) {
                    return new MyNextPosition(i, j, 1);
                }
            }

        }
        return null;
    }

    @Override
    public double getGrade() {
        return grade;
    }

    @Override
    public void setGrade(double grade) {
        this.grade = grade;
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(cost, ((State) o).getCost());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof MyState) {
            return this.hashCode == ((MyState) o).hashCode;
        }
        return false;
        
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }
}