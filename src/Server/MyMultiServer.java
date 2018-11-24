package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyMultiServer extends MyServer {

    private ServerSocket serverSocket;
    private int port;
    private boolean stop = false;
    ThreadPoolExecutor tpexec;
	
    public MyMultiServer(int port, int num) {
		this.port = port;
		this.tpexec = new ThreadPoolExecutor(1, num, 10, TimeUnit.SECONDS,
    			new PriorityBlockingQueue<Runnable>());
	}
	
    @Override
    public void stop() {
        stop = true;
    }
    
	private void startServer(ClientHandler clientHandler) throws IOException {
		
		   serverSocket = new ServerSocket(port);
	        serverSocket.setSoTimeout(5000);
	        System.out.println("Server started - waiting");


			while (!stop) {
				try {
					// Waiting for a client
					Socket aClient = serverSocket.accept();
					System.out.println("client connected");
					int priority = 0;
					do {
						priority = aClient.getInputStream().available();
					} while (priority == 0);

					System.out.println("Client priority: " + priority);
					tpexec.execute(new PriorityQRunnable(priority) {
						@Override
						public void run() {
							try {
				                System.out.println("handle client");
				                clientHandler.handleClient(aClient.getInputStream(), aClient.getOutputStream());
				                aClient.close();
				                System.out.println("client disconnected");
				            } catch (SocketTimeoutException e) {
				            	e.printStackTrace();
				            } catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				} catch (IOException e) {

				}
			}

	        serverSocket.close();
	        System.out.println("Finish handling last clients");
	        tpexec.shutdown();
	  
	        try {
				tpexec.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

	        System.out.println("Done");
	    }
	
		public static void main(String[] args) throws Exception {
			System.out.println("**** Server Side ****");

//        Server s = new MyServer(Integer.parseInt(args[0]));//Take the port from the args
			Server s = new MyMultiServer(6400, 5);//Take the port from the args
			s.start(new MyClientHandler());
			System.in.read();
			s.stop();
			System.out.println("Closed server");
		}
	}
