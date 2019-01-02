package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import AppProperties.*;
import Model.PGModel;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {


    private MyModelView ModelView = new MyModelView();
	
	char[][] mazeData = {
            {'s', 'F', 'F', '-', 'J',},
            {'F', '7', '7', '7', '7',},
            {'L', '7', 'F', '7', '7',},
            {'7', '-', '-', '7', 'g'}

    };

    @FXML
    private PipeBoard PipeBoard;

    private NakedObjectDisplayer nakedObjectDisplayer = new NakedObjectDisplayer();
    private ServerConfig serverConfigObject = new ServerConfig();
    private ThemeConfig themeConfigObject = new ThemeConfig();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeTheme();
        PipeBoard.setMazeData(mazeData);
        
		 ModelView.pipeBoard.addEventFilter(MouseEvent.MOUSE_CLICKED, (e)->{
			 int col = (int) (e.getX() / ModelView.pipeBoard.getColdWidth());
		     int row = (int) (e.getY() / ModelView.pipeBoard.getRowHeight());
		     ModelView.PGModel.movePipe(row, col);
		     ModelView.pipeBoard.redraw();
		     //check if goal
			 });

    }

    public void start() throws IOException, InterruptedException {

        
    	System.out.println(ModelView.isGoal());
    }

    public void openFile() throws IOException {
        System.out.println("openFile");
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze file");
        fc.setInitialDirectory(new File("./resources"));
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Xml files", "xml"));
        File chosen = fc.showOpenDialog(null);
        if (chosen != null) {
        	ModelView.loadGame(chosen.getPath());
        	ModelView.pipeBoard.redrawMaze();
        }
        
    }

    public void serverConfig() {
        nakedObjectDisplayer.display(this.serverConfigObject);
    }

    public void themeConfig() {
        nakedObjectDisplayer.displayComboBox(this.themeConfigObject, isChanged -> {
            System.out.println("Is theme changed: " + isChanged);
            if (isChanged) {
                changeTheme();
            }
            return null;
        });
    }

    private void changeTheme() {
        Themes themeType = themeConfigObject.getSelectedTheme();
        PipeBoard.setAnglePipeImage(themeType.getAnglePipe());
        PipeBoard.setRegularPipeImage(themeType.getRegularPipe());
        PipeBoard.setBackgroundImageProperty(themeType.getBackgroundImage());
        PipeBoard.setStartImage(themeType.getStartImage());
        PipeBoard.setGoalImage(themeType.getEndImage());
        PipeBoard.initImages();
        PipeBoard.redraw();
    }

}