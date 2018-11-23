package Solver;

import Images.MyNextPosition;
import Images.State;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class MySolution implements Serializable {
    private ArrayList<MyNextPosition> steps = new ArrayList<>();

    public MySolution(Solution solution) {
        //Move to MySolution
        List<State> states = solution.getStateList();

        List<MyNextPosition> justSteps = new ArrayList<>();
        State<?> current = states.get(states.size() - 1);
        while(current.getPreviousPosition() != null) {
            justSteps.add((MyNextPosition) current.getNextPosition());
            current = current.getPreviousPosition();
        }

        Map<Point, Long> collect = justSteps.stream()
                .filter(Objects::nonNull)
                .collect(Collectors.groupingBy(MyNextPosition::getPoint, Collectors.counting()));
        for (Point point : collect.keySet()) {
            steps.add(new MyNextPosition(point, collect.get(point).intValue()%4));
        }
    }

    public ArrayList<MyNextPosition> getNextPositions() {
        return steps;
    }

    public void printSteps(){
        for (MyNextPosition step:steps) {
            System.out.println(step);
        }
    }
}