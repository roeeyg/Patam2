package Model;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PGModel {

    private Socket serverSocket;
    public BooleanProperty isGoal = new SimpleBooleanProperty();
    public IntegerProperty numberOfSteps;
    public SimpleListProperty<char[]> gameBoard = new SimpleListProperty<>(FXCollections.observableArrayList(new ArrayList<>()));


    public void connect(String ip, String port) throws IOException {
        int portNum = Integer.parseInt(port);
        this.serverSocket = new Socket(ip, portNum);
        System.out.println("Connected to server");
    }

    public void disconnect() throws IOException{
        if (this.serverSocket != null) {
            this.serverSocket.close();
        }
    }
    
    public void setMazeData(char[][] array)
    {
    	this.gameBoard.setAll(array);
    }
    
    public char[][] ToArray()
    {    	
    	int col = this.gameBoard.getSize();
    	int row = this.gameBoard.getValue().get(0).length;
    	
    	char[][] array = new char[col][row];
    	
    	for(int i = 0; i < col; i++)
    		for(int j =0; j < row; j++)
    		{
    			array[i][j] = this.gameBoard.get(i)[j];
    		}
    	
    	return array;
    }

    public void movePipe(int row, int col) {
        switch (this.gameBoard.get(col)[row]) {
            case 'L':
                this.gameBoard.get(col)[row] = 'F';
                numberOfSteps.set(numberOfSteps.get() + 1);
                break;
            case 'F':
                this.gameBoard.get(col)[row] = '7';
                numberOfSteps.set(numberOfSteps.get() + 1);
                break;
            case '7':
                this.gameBoard.get(col)[row] = 'J';
                numberOfSteps.set(numberOfSteps.get() + 1);
                break;
            case 'J':
                this.gameBoard.get(col)[row] = 'L';
                numberOfSteps.set(numberOfSteps.get() + 1);
                break;
            case '-':
                this.gameBoard.get(col)[row] = '|';
                numberOfSteps.set(numberOfSteps.get() + 1);
                break;
            case '|':
                this.gameBoard.get(col)[row] = '-';
                numberOfSteps.set(numberOfSteps.get() + 1);
                break;
            case 's':
                this.gameBoard.get(col)[row] = 's';
                break;
            case 'g':
                this.gameBoard.get(col)[row] = 'g';
                break;
            default:
                this.gameBoard.get(col)[row] = ' ';
                break;
        }
        //notify the bind
        this.gameBoard.set(col, this.gameBoard.get(col));
    }

    public void saveGame(File file) throws IOException{
            PrintWriter out = new PrintWriter(file);
        for (char[] aGameBoard : this.gameBoard) {
            out.println(new String(aGameBoard));
        }
            out.close();
    }

    public void loadGame(String fileName) throws IOException {
        List<char[]> mazeBuilder = new ArrayList<>();
        BufferedReader reader;
        reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            mazeBuilder.add(line.toCharArray());
        }
        this.gameBoard.setAll(mazeBuilder.toArray(new char[mazeBuilder.size()][]));
        reader.close();
    }
    
    
	public boolean isSolution() throws IOException, InterruptedException {
		if (serverSocket != null) {
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
			PrintWriter outToServer = new PrintWriter(this.serverSocket.getOutputStream());

			for (char[] line : this.gameBoard.get()) {
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
}