package View;

import javafx.beans.property.*;
import Model.PGModel;

public class MyModelView {
    private BooleanProperty isGoal;
    private IntegerProperty numberOfSteps;
    private ListProperty<char[]> gameBoard;
    private PGModel pipesModel;

    public MyModelView(PGModel pipesModel){
        this.pipesModel = pipesModel;

        this.isGoal = new SimpleBooleanProperty();
        this.isGoal.bind(this.pipesModel.isGoal);

        this.numberOfSteps = new SimpleIntegerProperty();
        this.numberOfSteps.bind(this.pipesModel.numberOfSteps);

        this.gameBoard = new SimpleListProperty<>();
        this.gameBoard.bind(this.pipesModel.gameBoard);


    }
}