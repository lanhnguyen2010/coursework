import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private int listenerPort;

    public Server(int listenerPort) {
        this.listenerPort = listenerPort;
    }


    public void acceptConnection() {
        try {
            ServerSocket serverSocket = new ServerSocket(listenerPort, 1000);
            System.out.println("Server is started");
            Socket clientSocket = null;
            while (true){
                clientSocket = serverSocket.accept();
                new Thread(new ConnectionHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
