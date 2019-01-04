package View;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import Model.PGModel;
import AppProperties.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import AppProperties.Themes;

public class MainWindowController implements Initializable {
    private MediaPlayer mediaPlayer;
    private PGModel PGModel;

    @FXML
    private PipeBoard PipeBoard;
    @FXML
    private Label stepsLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label serverLabel;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    @FXML
    private Button solveButton;
    @FXML
    private Button checkIfDoneButton;


    private NakedObjectDisplayer nakedObjectDisplayer = new NakedObjectDisplayer();
    private ServerConfig serverConfigObject = new ServerConfig();
    private ThemeConfig ThemeConfig = new ThemeConfig();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        PGModel = new PGModel();

        changeTheme();

        PipeBoard.init(PGModel.getMazeData(), rowCol -> {
            if (PGModel.isGameStarted()) {
                PGModel.movePipe(rowCol.getRow(), rowCol.getCol());
                PipeBoard.setMazeData(PGModel.getMazeData());
                PipeBoard.redraw();
            } else {
                nakedObjectDisplayer.display(new Dialog("Error!", "Please click on Start to begin playing", "Ok"));
            }
            return null;
        });

        PGModel.gameBoard.addListener((observable, oldValue, newValue) -> PipeBoard.setMazeData(PGModel.gameBoard.toArray(new char[PGModel.gameBoard.size()][])));
        PGModel.stepsNum.addListener((observable, oldValue, newValue) -> this.stepsLabel.setText("Steps taken: " + Integer.toString(PGModel.stepsNum.get())));
        PGModel.secondsPassed.addListener((observable, oldValue, newValue) -> this.timeLabel.setText("Seconds passed: " + Integer.toString(PGModel.secondsPassed.get())));
        PGModel.isGoal.addListener((observable, oldValue, newValue) -> {
            if (newValue)
                Platform.runLater(() -> nakedObjectDisplayer.display(new Dialog("YAY!!!", "You solved the game!", "Woohoo!")));
        });
    }
   
    public void isDone() throws IOException, InterruptedException
    {
    	PGModel.connect(serverConfigObject.getIP(), serverConfigObject.getPort());
    	
    	if(this.PGModel.isDone())
    		nakedObjectDisplayer.display(new Dialog("Congratulation!!!", "You solved the game" , "Done"));
    	else
    		nakedObjectDisplayer.display(new Dialog("Sorry", "You did not solve the game" , "Try again"));
    	PGModel.disconnect();
    }

    public void start() {
        PGModel.start();
        toggleButtons(true);
    }

    public void stop() {
        PGModel.stop();
        toggleButtons(false);
    }

    public void solve() {
        stop();
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    System.out.println("Solve clicked " + serverConfigObject.getIP() + " " + serverConfigObject.getPort());
                    Platform.runLater(() -> serverLabel.setText("Server Status: Connecting to " + serverConfigObject.getIP() + ":" + serverConfigObject.getPort()));
                    PGModel.connect(serverConfigObject.getIP(), serverConfigObject.getPort());
                    Platform.runLater(() -> serverLabel.setText("Server Status: Waiting for solution"));
                    PGModel.solve();
                    PGModel.disconnect();
                    Platform.runLater(() -> serverLabel.setText("Server Status: Disconnected"));
                } catch (IOException e) {
                    Platform.runLater(() -> nakedObjectDisplayer.display(new Dialog("Oops...", "Couldn't connect to the server, please try again later", "Ok")));
                    Platform.runLater(() -> serverLabel.setText("Server Status: Couldn't connect to the server"));
                    e.printStackTrace();
                }
                return null;
            }
        };
        new Thread(task).start();
    }

    private void toggleButtons(boolean startDisabled) {
        startButton.setDisable(startDisabled);
        stopButton.setDisable(!startDisabled);
        solveButton.setDisable(!startDisabled);
    }

    public void openFile() {
        System.out.println("openFile");
        FileChooser fc = new FileChooser();
        fc.setTitle("Load maze file");
        fc.setInitialDirectory(new File("./resources"));
        fc.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File chosen = fc.showOpenDialog(null);
        if (chosen != null) {
            System.out.println("Chose: " + chosen.getName());
            try {
                PGModel.loadGame(chosen);
                PipeBoard.setMazeData(PGModel.ToArray());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void saveFile() {
        stop();
        System.out.println("saveFile");
        FileChooser fc = new FileChooser();
        fc.setTitle("Choose the folder to save the current game");
        FileChooser.ExtensionFilter txtExtensionFilter = new FileChooser.ExtensionFilter("Text Files", "*.txt");
        fc.getExtensionFilters().add(txtExtensionFilter);
        fc.setSelectedExtensionFilter(txtExtensionFilter);

        File selectedFile = fc.showSaveDialog(null);

        if (selectedFile == null) {
            return;
        }
        PGModel.saveCurrentGame(selectedFile);
    }

    public void serverConfig() {
        nakedObjectDisplayer.display(this.serverConfigObject);
    }

    public void themeConfig() {
        nakedObjectDisplayer.display(this.ThemeConfig, isChanged -> {
            System.out.println("Is theme changed: " + isChanged);
            if (isChanged) {
                changeTheme();
            }
            return null;
        });
    }

    private void changeTheme() {
        Themes Themes = ThemeConfig.getSelectedTheme();
        PipeBoard.setAnglePipeImage(Themes.getAnglePipe());
        PipeBoard.setRegularPipeImage(Themes.getRegularPipe());
        PipeBoard.setBackgroundImageProperty(Themes.getBackgroundImage());
        PipeBoard.setStartImage(Themes.getStartImage());
        PipeBoard.setGoalImage(Themes.getEndImage());
        PipeBoard.initImages();
        PipeBoard.redraw();
        //playMusic(Themes.getMusic());
    }

    private void playMusic(String musicFile) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media hit = new Media(new File(musicFile).toURI().toString());
        mediaPlayer = new MediaPlayer(hit);
        mediaPlayer.play();
    }


}