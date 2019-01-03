package Model;


import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import Searchers.PipeSolver;

public class PGModel {
    private static final String TIME_PREFIX = "seconds:";
    private static final String STEPS_PREFIX = "steps:";

    private ScheduledExecutorService executor;

    private Socket serverSocket;
    public IntegerProperty stepsNum = new SimpleIntegerProperty();
    public IntegerProperty secondsPassed = new SimpleIntegerProperty();
    public ListProperty<char[]> gameBoard = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    public BooleanProperty isGoal = new SimpleBooleanProperty();

    private char[][] mazeData = {
            {'s', 'L', 'F'},
            {'F', '-', 'F'},
            {'|', '7', 'g'}
    };

    public char[][] getMazeData() {
        return gameBoard.toArray(new char[gameBoard.size()][]);
    }

    public PGModel() {
        gameBoard.addAll(mazeData);
    }

    public boolean isGameStarted() {
        return executor != null;
    }

    public void start() {
        setTimer();
    }

    public void stop() {
        if (executor != null) {
            executor.shutdown();
        }
        executor = null;
    }

    private void setTimer() {
        Runnable helloRunnable = () -> Platform.runLater(() -> secondsPassed.setValue(secondsPassed.get() + 1));

        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(helloRunnable, 0, 1, TimeUnit.SECONDS);
    }

    public void connect(String ip, String port) throws IOException {
        int portNum = Integer.parseInt(port);
        this.serverSocket = new Socket(ip, portNum);
        System.out.println("Connected to server");
    }

    public void disconnect() throws IOException {
        if (this.serverSocket != null) {
            this.serverSocket.close();
        }
    }

    public void movePipe(int row, int col) {
        char current = mazeData[row][col];
        if (current != 's' && current != 'g' && current != ' ') {
            stepsNum.set(stepsNum.get() + 1);
        }
        mazeData[row][col] = PipeSolver.getNextChar(mazeData[row][col]);
        this.gameBoard.set(col, this.gameBoard.get(col));
    }

    public void solve() throws IOException, InterruptedException {
        if (this.serverSocket != null) {
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(this.serverSocket.getOutputStream());

            for (char[] line : this.gameBoard.get()) {
                outToServer.println(line);
                System.out.println(line);
                outToServer.flush();
            }

            outToServer.println("done");
            outToServer.flush();
            System.out.println("flushed to server");
            String line;
            Boolean hasSteps = true;
            while (!(line = inFromServer.readLine()).equals("done")) {
                System.out.println("**"  + line);
                String[] steps = line.split(",");
                int row = Integer.parseInt(steps[0]);
                int col = Integer.parseInt(steps[1]);
                int step = Integer.parseInt(steps[2]);

                for (int i = 1; i <= step; i++) {
                    Platform.runLater(() -> movePipe(row, col));
                    Thread.sleep(150);
                }
                hasSteps = false;
            }
            isGoal.setValue(hasSteps);
            System.out.println("done");
        }
    }

    public void saveCurrentGame(File file) {
        try {
            PrintWriter outFile = new PrintWriter(file);
            for (char[] aGameBoard : this.gameBoard) {
                outFile.println(new String(aGameBoard));
            }

            outFile.println(TIME_PREFIX + secondsPassed.get());
            outFile.println(STEPS_PREFIX + stepsNum.get());

            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadGame(File file) throws IOException {
        List<char[]> mazeBuilder = new ArrayList<>();
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith(TIME_PREFIX)) {
                int seconds = Integer.parseInt(line.split(":")[1]);
                secondsPassed.set(seconds);
            } else if (line.startsWith(STEPS_PREFIX)) {
                int steps = Integer.parseInt(line.split(":")[1]);
                stepsNum.set(steps);
            } else {
                mazeBuilder.add(line.toCharArray());
            }
        }
        this.gameBoard.setAll(mazeBuilder.toArray(new char[mazeBuilder.size()][]));
        reader.close();
    }

}