package server.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private int listenerPort;
    private String nameNode;
    private String hdfsUser;
    private String path;
    private long fileSize;

    public Server(int listenerPort, String nameNode, String hdfsUser, String path, long fileSize) {
        this.listenerPort = listenerPort;
        this.nameNode = nameNode;
        this.hdfsUser = hdfsUser;
        this.path = path;
        this.fileSize = fileSize;
    }


    public void acceptConnection() {
        Socket clientSocket = null;

        try {
            ServerSocket serverSocket = new ServerSocket(listenerPort, 1000);
            System.out.println("Server is started");
            while (true){
                clientSocket = serverSocket.accept();
                new Thread(new ConnectionHandler(clientSocket, nameNode, hdfsUser, fileSize, path)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
