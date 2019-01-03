package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MyMultiServer implements Server {
    private int port;
    private boolean stop = false;
    private ThreadPoolExecutor threadPoolExecutor;
    private int clientsCount = 0;

    MyMultiServer(int port, int threadsNumber) {
        this.port = port;
        this.threadPoolExecutor = new ThreadPoolExecutor(threadsNumber, threadsNumber, 10, TimeUnit.SECONDS,
                new PriorityBlockingQueue<>());
    }

    @Override
    public void start(ClientHandler clientHandler) {
        new Thread(() -> {
            try {
                startServer(clientHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void startServer(ClientHandler clientHandler) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        serverSocket.setSoTimeout(5000);
        System.out.println("Server connected - waiting for client");

        while (!stop) {
            try {
                Socket aClient = serverSocket.accept();
                clientsCount++;
                System.out.println("*** client number " + clientsCount + " is connected ***");
                InputFromUserReader inputFromUserReader = new InputFromUserReader();
                inputFromUserReader.readFromUser(aClient.getInputStream());

                PriorityQRunnable priorityRunnable = new PriorityQRunnable(inputFromUserReader.numRows * inputFromUserReader.numCol) {
                    @Override
                    public void run() {
                        try {
                            clientHandler.handleClient(inputFromUserReader.numRows, inputFromUserReader.numCol, inputFromUserReader.output, aClient.getOutputStream());
                            aClient.close();
                          //  System.out.println("*** Finished with a client with priority " + this.getPuzzlePriority());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };

                threadPoolExecutor.execute(priorityRunnable);

            } catch (SocketTimeoutException e) {
                if(threadPoolExecutor.getActiveCount() == 0){
                    System.out.println("No  more tasks");
                    this.stop();
                }
            }
        }
        threadPoolExecutor.shutdown();
        serverSocket.close();
    }

    @Override
    public void stop() {
        stop = true;
    }
}