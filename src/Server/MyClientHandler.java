package Server;

import CacheManager.CacheManager;
import CacheManager.MyCacheManager;
import Images.MyNextPosition;
import Solver.MySolver;
import Solver.MySolution;
import Solver.Solution;
import Solver.Solver;

import java.io.*;

public class MyClientHandler implements ClientHandler {
    private CacheManager cacheManager = new MyCacheManager();
    private Solver solver = new MySolver();

    @Override
    public void handleClient(InputStream inFromClient, OutputStream outToClient) {
        int row = 0;
        int col = 0;
        PrintWriter outTC = new PrintWriter(outToClient);
        BufferedReader inFClient = new BufferedReader(new InputStreamReader(inFromClient));
        try {
            //Getting level from user
            String line;
            StringBuilder builder = new StringBuilder();
            while (!(line = inFClient.readLine()).equals("done")) {
                builder.append(line);
                row++;
                col = line.length();
//                System.out.println(line);
            }

            String level = builder.toString();
            //Getting solution from the cache manager
            Solution solution = cacheManager.load(level);
            if (solution == null) {
                //solver
                solution = solver.solve(convertStringToChar(level, row, col), row, col);
                cacheManager.save(level, solution);
            }

            MySolution MySolution = new MySolution(solution);

//            System.out.println("Printing Solution steps: ");
//            MySolution.printSteps();

            for (MyNextPosition step : MySolution.getNextPositions()) {
                outTC.println(step.toString());
            }

            outTC.println("done");

//            System.out.println("done");
            outTC.flush();
            inFClient.close();
            outTC.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private char[][] convertStringToChar(String levelString, int rowNum, int colNum) {
        char[][] level = new char[rowNum][colNum];
        for (int i = 0; i < rowNum; i++) {
            level[i] = new char[colNum];
            level[i] = levelString.substring(i * colNum, (i * colNum) + colNum).toCharArray();
        }
        return level;
    }
}