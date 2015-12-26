package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;
import server.service.ConnectionHandler;


public class Client {

    public static void main(String[] args) throws IOException, InterruptedException {
        String ip = "192.168.56.101";
        int port = 6789;
        String filePath = "D:\\LLPSender\\LLPSender\\hl7";
        Socket socket = new Socket(ip, port);
        System.out.println("Connect to server");

        try {
            final File folder = new File(filePath);
            for (File file : folder.listFiles()) {
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    out.write((line + ConnectionHandler.END_CHAR).getBytes());
                    readResponse(in);
                }
            }
            socket.close();
        } catch (SocketException e) {
            throw new SocketException("Cannot connect to Listener");
        }


    }

    private static void readResponse(InputStream in) throws IOException, InterruptedException {
        StringBuilder response = new StringBuilder();
        int attempts = 0;
        while (in.available() == 0) {
            attempts++;
            Thread.sleep(10L);
            if (attempts == 300) {
                throw new SocketException();
            }

        }

        int intch;
        while ((intch = in.read()) != -1) {
            response.append(Character.toString((char) intch));
            if (intch == 3){
                break;
            }

        }
        System.out.println(response);
    }
}
