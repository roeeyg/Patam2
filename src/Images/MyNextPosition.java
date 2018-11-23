package Images;

import java.awt.*;

public class MyNextPosition extends NextPosition {
    private Point point;
    private int NumOfClicks;

    public MyNextPosition(int row, int col, int NumOfClicks) {
        this(new Point(row, col), NumOfClicks);
    }

    public MyNextPosition(Point point, int NumOfClicks) {
        this.point = point;
        this.NumOfClicks = NumOfClicks;
    }

    public MyNextPosition() {
        this(new Point(), 0);
    }

    public int getNumOfClicks() {
        return NumOfClicks;
    }
    
    public Point getPoint() {
        return point;
    }

    public void setNumOfClicks(int NumOfClicks) {
        this.NumOfClicks = NumOfClicks;
    }

    @Override
    public String NextPositionToString() {
        StringBuilder stringbuilder = new StringBuilder();
        stringbuilder.append(point.x);
        stringbuilder.append(",");
        stringbuilder.append(point.y);
        stringbuilder.append(",");
        stringbuilder.append(NumOfClicks);
        return stringbuilder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return point == ((MyNextPosition) obj).point;
    }
}