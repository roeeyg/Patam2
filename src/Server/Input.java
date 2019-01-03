package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

class InputFromUserReader {
    int numRows = 0;
    int numCol = 0;
    String output;

    void readFromUser(InputStream inputStream) throws IOException {
        BufferedReader inFClient = new BufferedReader(new InputStreamReader(inputStream));
        //Getting level from user
        String line;
        StringBuilder builder = new StringBuilder();
        while (!(line = inFClient.readLine()).equals("done")) {
            builder.append(line);
            numRows++;
            numCol = line.length();
//            System.out.println(line);
        }
        output = builder.toString();
    }
}