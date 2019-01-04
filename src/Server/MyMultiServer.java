package Server;

import java.util.concurrent.*;
import java.net.*;
import java.io.*;

public class MyMultiServer
{
    private int port;
    private ServerSocket serverSocket;
    private volatile boolean stop;
    ThreadPoolExecutor threadPool;
    
    public MyMultiServer(int port, int threadsNum) {
        this.port = port;
        this.threadPool = new ThreadPoolExecutor(threadsNum, threadsNum, 10, TimeUnit.SECONDS, new PriorityBlockingQueue<>());
    }
    
    private void startServer(final ClientHandler ch) throws IOException {
        (this.serverSocket = new ServerSocket(this.port)).setSoTimeout(5000);
        System.out.println("Server connected - waiting for client");
        while (!this.stop) {
            try {
                Socket aClient = this.serverSocket.accept();
                int matrixSize=aClient.getInputStream().available();
                
                PriorityQRunnable priorityQueue = new PriorityQRunnable(matrixSize) {
                	@Override
					public void run() {
						try {
			                System.out.println("handle client");
			                ch.handleClient(aClient.getInputStream(),aClient.getOutputStream());
			                aClient.close();
			            } catch (IOException e) {
							e.printStackTrace();
						}
                	}
                };
                
                threadPool.execute(priorityQueue); 
            }

          
        
                
            catch (SocketTimeoutException ex) {}
        }
        System.out.println("Finish handling last clients");
        this.threadPool.shutdown();
        this.serverSocket.close();
    }
    
    public void stop() {
        this.stop = true;
    }
    
    public void start(final ClientHandler ch) {
        new Thread(() -> {
            try {
                this.startServer(ch);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
