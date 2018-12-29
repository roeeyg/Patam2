package View;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import AppProperties.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    char[][] mazeData = {
            {'s', 'F', 'F', '-', 'J', '7', '7', '7', '7'},
            {'F', '7', '7', '7', '7', '|', '7', 'F', 'L'},
            {'L', '7', 'F', '7', '7', '7', '7', '7', '|'},
            {'7', '-', '-', '7', '|', '7', '7', '7', '7'},
            {'L', '7', '7', '7', 'L', 'F', '7', '|', '7'},
            {'|', '7', '|', '7', '7', '7', '7', '7', '7'},
            {'-', 'L', 'F', '7', '|', 'F', 'L', '7', '-'},
            {'7', 'F', '-', '-', '-', '-', '-', '-', 'g'},
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

    }

    public void start() {
        System.out.println("start");
    }

    public void openFile() {
        System.out.println("openFile");
        FileChooser fc = new FileChooser();
        fc.setTitle("Open maze file");
        fc.setInitialDirectory(new File("./resources"));
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Xml files", "xml"));
        File chosen = fc.showOpenDialog(null);
        if (chosen != null) {
            System.out.println("Chose: " + chosen.getName());
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