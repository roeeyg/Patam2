package Images;

import java.io.Serializable;

public abstract class NextPosition implements Serializable {
    @Override
    public String toString()
    {
        return NextPositionToString();
    }
    public abstract String NextPositionToString();
}