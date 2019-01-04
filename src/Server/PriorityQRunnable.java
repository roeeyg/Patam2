package Server;


public abstract class PriorityQRunnable implements Comparable<PriorityQRunnable>, Runnable {
    private int puzzlePriority;

    @Override
    public int compareTo(PriorityQRunnable priorityRunnable) {
        return this.puzzlePriority - priorityRunnable.puzzlePriority;
    }

    PriorityQRunnable(int puzzlePriority) {
        this.puzzlePriority = puzzlePriority;
    }

    public int getPuzzlePriority() {
        return puzzlePriority;
    }
}