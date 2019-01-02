package View;

import javafx.beans.property.*;

import java.io.IOException;

import Model.PGModel;
import Searchers.PipeSolver;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.scene.input.MouseEvent;

public class MyModelView {
    //private BooleanProperty isGoal = new SimpleBooleanProperty(false);
    //private IntegerProperty numberOfSteps;
    //private ListProperty<char[]> gameBoard;
    public PGModel PGModel = new PGModel();
    public PipeBoard pipeBoard = new PipeBoard();

    public MyModelView(){
    }

        //this.isGoal.bind(pipesModel.isGoal);

        

        //this.numberOfSteps = new SimpleIntegerProperty();
        //this.numberOfSteps.bind(this.pipesModel.numberOfSteps);

        //this.gameBoard = new SimpleListProperty<>();
        //this.gameBoard.bind(this.pipesModel.gameBoard);


    public void loadGame(String name) throws IOException
    {
    	this.PGModel.loadGame(name);
    	pipeBoard.setMazeData(this.PGModel.ToArray());
    }
    
    
    
    public boolean isGoal() throws IOException, InterruptedException
    {
    	return PGModel.isSolution();
    }
    
}