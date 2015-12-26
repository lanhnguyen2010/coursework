package server.service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private int listenerPort;
    private String nameNode;
    private String hdfsUser;
    private String path;

    public Server(int listenerPort, String nameNode, String hdfsUser, String path) {
        this.listenerPort = listenerPort;
        this.nameNode = nameNode;
        this.hdfsUser = hdfsUser;
        this.path = path;
    }


    public void acceptConnection() {
        HDFSWriter hdfsWriter = null;
        try {
            ServerSocket serverSocket = new ServerSocket(listenerPort, 1000);
            System.out.println("Server is started");
            Socket clientSocket = null;
            hdfsWriter = new HDFSWriter(nameNode, hdfsUser, path);
            while (true){
                clientSocket = serverSocket.accept();
                new Thread(new ConnectionHandler(clientSocket, hdfsWriter, path)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (hdfsWriter != null) {
                hdfsWriter.close();
            }
        }

    }
}
