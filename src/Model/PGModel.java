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
    public ListProperty<char[]> pipeMaze = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));
    public BooleanProperty isGoal = new SimpleBooleanProperty();

    //Starting maze
    private char[][] mazeData = {
            {'F', 's', 'F'},
            {'F', '-', 'L'},
            {'g', '7', '7'}
    };

    public char[][] getMazeData() {
        return pipeMaze.toArray(new char[pipeMaze.size()][]);
    }
    
    public char[][] ToArray()
    {    	
    	int col = this.pipeMaze.getSize();
    	int row = this.pipeMaze.getValue().get(0).length;

    	char[][] array = new char[col][row];

    	for(int i = 0; i < col; i++)
    		for(int j =0; j < row; j++)
    		{
    			array[i][j] = this.pipeMaze.get(i)[j];
    		}

    	return array;
    }

    public boolean isGameStarted() {
        return executor != null;
    }

    public void start() {
        setTimer();
    }

    public PGModel() {
        pipeMaze.addAll(mazeData);
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
    
    public boolean isDone() throws IOException, InterruptedException {
		if (serverSocket != null) {
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
			PrintWriter outToServer = new PrintWriter(this.serverSocket.getOutputStream());

			for (char[] line : this.pipeMaze.get()) {
				outToServer.println(line);
				outToServer.flush();
			}

			outToServer.println("done");
			outToServer.flush();

			String line;
			
			if ((line = inFromServer.readLine()).equals("done")) 
				return true;
		}
		return false;

	}

    public void connect(String ip, String port) throws IOException {
        int portNum = Integer.parseInt(port);
        this.serverSocket = new Socket(ip, portNum);
        System.out.println("Connected to server");
    }

    public void movePipe(int row, int col) {
        char current = mazeData[row][col];
        if (current != 's' && current != 'g' && current != ' ') {
            stepsNum.set(stepsNum.get() + 1);
        }
        mazeData[row][col] = PipeSolver.getNextClick(mazeData[row][col]);
        this.pipeMaze.set(col, this.pipeMaze.get(col));
    }
    
    public void disconnect() throws IOException {
        if (this.serverSocket != null) {
            this.serverSocket.close();
        }
    }

    public void solve() throws IOException, InterruptedException {
        if (this.serverSocket != null) {
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(this.serverSocket.getOutputStream());

            for (char[] line : this.pipeMaze.get()) {
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
            for (char[] apipeMaze : this.pipeMaze) {
                outFile.println(new String(apipeMaze));
            }

            outFile.println(TIME_PREFIX + secondsPassed.get());
            outFile.println(STEPS_PREFIX + stepsNum.get());

            outFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	public void loadGame(String fileName) {
		List<char[]> PGBoardBuilder = new ArrayList<char[]>();
		BufferedReader reader;
		try {

			reader = new BufferedReader(new FileReader(fileName));
			String line;
			while ((line = reader.readLine()) != null)
				PGBoardBuilder.add(line.toCharArray());
			this.pipeMaze.setAll(PGBoardBuilder.toArray(new char[PGBoardBuilder.size()][]));
			this.mazeData=this.pipeMaze.toArray(new char [this.pipeMaze.size()][]);
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
