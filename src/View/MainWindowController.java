package pipes_client_app.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import View.dialogs.NakedObjectDisplayer;
import pipes_client_app.dialogs.ServerConfigObject;
import pipes_client_app.dialogs.ThemeConfigObject;
import pipes_client_app.dialogs.ThemeType;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    char[][] mazeData = {
            {'s', 'L', 'F', '-', 'J', '7', '7', '7', '7'},
            {'7', '7', '7', '7', '7', '7', '7', '7', '7'},
            {'7', '7', '7', '7', '7', '7', '7', '7', '7'},
            {'7', '7', '7', '7', '|', '7', '7', '7', '7'},
            {'7', '7', '7', '7', 'L', '7', '7', '7', '7'},
            {'7', '7', '7', '7', '7', '7', '7', '7', '7'},
            {'7', '7', '7', '7', '|', '7', '7', '7', '7'},
            {'7', '7', '-', '-', '-', '-', '-', '-', 'g'},
    };

    @FXML
    private PipesGrid pipesGrid;

    private NakedObjectDisplayer nakedObjectDisplayer = new NakedObjectDisplayer();
    private ServerConfigObject serverConfigObject = new ServerConfigObject();
    private ThemeConfigObject themeConfigObject = new ThemeConfigObject();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        changeTheme();
        pipesGrid.setMazeData(mazeData);

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
        ThemeType themeType = themeConfigObject.getSelectedTheme();
        pipesGrid.setAnglePipeImage(themeType.getAnglePipe());
        pipesGrid.setRegularPipeImage(themeType.getRegularPipe());
        pipesGrid.setBackgroundImageProperty(themeType.getBackgroundImage());
        pipesGrid.setStartImage(themeType.getStartImage());
        pipesGrid.setGoalImage(themeType.getEndImage());
        pipesGrid.initImages();
        pipesGrid.redraw();
    }

}