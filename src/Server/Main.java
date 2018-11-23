package Server;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("**** Server Side ****");

//        Server s = new MyServer(Integer.parseInt(args[0]));//Take the port from the args
        Server s = new MyServer(6400);//Take the port from the args
        ClientHandler ch = new MyClientHandler();
        s.start(ch);
        System.in.read();
        s.stop();
        System.out.println("Closed server");
    }
}