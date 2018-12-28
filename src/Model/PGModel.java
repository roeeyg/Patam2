package Model;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class PGModel {

    private Socket serverSocket;
    public BooleanProperty isGoal;
    public IntegerProperty numberOfSteps;
    public ListProperty<char[]> gameBoard;


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

    public void solve() throws IOException, InterruptedException {
        if (this.serverSocket != null) {
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(this.serverSocket.getInputStream()));
            PrintWriter outToServer = new PrintWriter(this.serverSocket.getOutputStream());

            for (char[] line : this.gameBoard.get()) {
                outToServer.println(line);
                outToServer.flush();
            }

            outToServer.println("done");
            outToServer.flush();

            String line;
            while (!(line = inFromServer.readLine()).equals("done")) {
                String[] steps = line.split(",");
                int row = Integer.parseInt(steps[0]);
                int col = Integer.parseInt(steps[1]);
                int step = Integer.parseInt(steps[2]);

                for (int i = 1; i <= step; i++) {
                    Platform.runLater(()-> movePipe(row, col));
                    Thread.sleep(50);
                }
            }
        }
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

}