package View;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.function.Function;

import Images.RowCol;

public class PipeBoard extends Canvas {
    private static final String ANGLE_PIPE_DEFAULT = "./resources/GrayCorner.png";
    private static final String REGULAR_PIPE_DEFAULT = "./resources/Gray.png";
    private static final String BACKGROUND_DEFAULT = "./resources/GrayBackGround.jpg";
    private static final String GOAL_DEFAULT = "./resources/GraySE.png";
    private static final String START_DEFAULT = "./resources/GraySE.png";

    private char[][] mazeData;

    private StringProperty regularPipeImage, anglePipeImage, backgroundImage, startImage, goalImage;
    private double colWidth;
    private double rowHeight;

    private Image imagePipeVertical, imagePipeHorizontal, imagePipe0Rotation, imagePipe90Rotation,
            imagePipe180Rotation, imagePipe270Rotation, imageBackground, imageStart, imageGoal;


    public PipeBoard() {
        regularPipeImage = new SimpleStringProperty();
        anglePipeImage = new SimpleStringProperty();
        backgroundImage = new SimpleStringProperty();
        startImage = new SimpleStringProperty();
        goalImage = new SimpleStringProperty();
    }


    void init(char[][] mazeData, Function<RowCol, Void> listener) {
        this.mazeData = mazeData;
        initImages();
        redrawMaze();
        addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            int col = (int) (event.getX() / colWidth);
            int row = (int) (event.getY() / rowHeight);
            listener.apply(new RowCol(row, col));
        });
    }

    public void setMazeData(char[][] mazeData) {
        this.mazeData = mazeData;
        redrawMaze();
    }

    public StringProperty regularPipeImageProperty() {
        return regularPipeImage;
    }

    public void setRegularPipeImage(String regularPipeImage) {
        this.regularPipeImage.set(regularPipeImage);
    }

    /**
     * Returns the straight pipe image the user of the view set.
     * If no image was set - a default will be used.
     */
    public String getRegularPipeImage() {
        if (regularPipeImage.get() == null || regularPipeImage.get().isEmpty()) {
            return REGULAR_PIPE_DEFAULT;
        }
        return regularPipeImage.get();
    }

    public StringProperty anglePipeImageProperty() {
        return anglePipeImage;
    }

    public void setAnglePipeImage(String anglePipeImage) {
        this.anglePipeImage.set(anglePipeImage);
    }

    /**
     * Returns the angled pipe image the user of the view set.
     * If no image was set - a default will be used.
     */
    public String getAnglePipeImage() {
        if (anglePipeImage.get() == null || anglePipeImage.get().isEmpty()) {
            return ANGLE_PIPE_DEFAULT;
        }
        return anglePipeImage.get();
    }

    /**
     * Returns the background image the user of the view set.
     * If no image was set - a default will be used.
     */
    public String getBackgroundImage() {
        if (backgroundImage.get() == null || backgroundImage.get().isEmpty()) {
            return BACKGROUND_DEFAULT;
        }
        return backgroundImage.get();
    }

    public StringProperty backgroundImagePropertyProperty() {
        return backgroundImage;
    }

    public void setBackgroundImageProperty(String backgroundImageProperty) {
        this.backgroundImage.set(backgroundImageProperty);
    }

    public String getStartImage() {
        if (startImage.get() == null || startImage.get().isEmpty()) {
            return START_DEFAULT;
        }
        return startImage.get();
    }

    public StringProperty startImageProperty() {
        return startImage;
    }

    public void setStartImage(String startImage) {
        this.startImage.set(startImage);
    }

    public void setGoalImage(String goalImage) {
        this.goalImage.set(goalImage);
    }

    public String getGoalImage() {
        if (goalImage.get() == null || goalImage.get().isEmpty()) {
            return GOAL_DEFAULT;
        }
        return goalImage.get();
    }

    public StringProperty goalImageProperty() {
        return goalImage;
    }

    private void redrawMaze() {
        if (mazeData == null) {
            return;
        }
        double width = getWidth();
        double height = getHeight();
        colWidth = width / mazeData[0].length;
        rowHeight = height / mazeData.length;
        redraw();
    }

    void initImages() {
        Image regularPipe = null;
        Image anglePipe = null;
        try {
            regularPipe = new Image(new FileInputStream(getRegularPipeImage()));
            anglePipe = new Image(new FileInputStream(getAnglePipeImage()));
            imageBackground = new Image(new FileInputStream(getBackgroundImage()));
            imageStart = new Image(new FileInputStream((getStartImage())));
            imageGoal = new Image(new FileInputStream(getGoalImage()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        int rotateImage = 0;
        //'F'
        ImageView iv = new ImageView(anglePipe);
        iv.setRotate(rotateImage);
        imagePipe0Rotation = iv.snapshot(params, null);
        //'7'
        rotateImage = 90;
        iv.setRotate(rotateImage);
        imagePipe90Rotation = iv.snapshot(params, null);
        //'J'
        rotateImage = 180;
        iv.setRotate(rotateImage);
        imagePipe180Rotation = iv.snapshot(params, null);
        //'L'
        rotateImage = 270;
        iv.setRotate(rotateImage);
        imagePipe270Rotation = iv.snapshot(params, null);

        //'|'
        rotateImage = 0;
        iv = new ImageView(regularPipe);
        iv.setRotate(rotateImage);
        imagePipeHorizontal = iv.snapshot(params, null);
        //'-'
        rotateImage = 90;
        iv.setRotate(rotateImage);
        imagePipeVertical = iv.snapshot(params, null);
    }

    void redraw() {
        if (mazeData != null) {
            GraphicsContext graphicsContext = getGraphicsContext2D();
            graphicsContext.drawImage(imageBackground, 0, 0, getWidth(), getHeight());

            for (int i = 0; i < mazeData.length; i++) {
                for (int j = 0; j < mazeData[i].length; j++) {
                    Image image = null;
                    switch (mazeData[i][j]) {
                        case '|':
                            image = imagePipeVertical;
                            break;
                        case '-':
                            image = imagePipeHorizontal;
                            break;
                        case 'F':
                            image = imagePipe90Rotation;
                            break;
                        case '7':
                            image = imagePipe180Rotation;
                            break;
                        case 'J':
                            image = imagePipe270Rotation;
                            break;
                        case 'L':
                            image = imagePipe0Rotation;
                            break;
                        case ' ':
                            break;
                        case 'g':
                            image = imageGoal;
                            break;
                        case 's':
                            image = imageStart;
                            break;
                    }
                    graphicsContext.drawImage(image, j * colWidth, i * rowHeight, colWidth, rowHeight);
                }
            }
        }
    }

    @Override
    public void resize(double width, double height) {
        super.setWidth(width);
        super.setHeight(height);
        redrawMaze();
    }

    @Override
    public boolean isResizable() {
        return true;
    }

    @Override
    public double minHeight(double width) {
        return 64;
    }

    @Override
    public double maxHeight(double width) {
        return 1000;
    }

    @Override
    public double prefHeight(double width) {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height) {
        return 0;
    }

    @Override
    public double maxWidth(double height) {
        return 10000;
    }
}