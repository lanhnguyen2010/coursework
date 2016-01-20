package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;


public class Client {
    public static final String END_CHAR = "\003";


    public static void main(String[] args) throws IOException, InterruptedException {
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        String filePath = args[2];
        Socket socket = new Socket(ip, port);
        System.out.println("Connect to server");

        try {
            final File folder = new File(filePath);
            for (File file : folder.listFiles()) {

                if (file.getName().endsWith("read")){
                    continue;
                }
                System.out.println("Sending data in file :" + file.getName());
                OutputStream out = socket.getOutputStream();
                InputStream in = socket.getInputStream();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    out.write((line + END_CHAR).getBytes());
                    readResponse(in);
                }
                reader.close();
                file.renameTo(new File(file.getPath() + "_read"));
                System.out.println("Finished sending file : "+ file.getName());
            }
            socket.close();
        } catch (SocketException e) {
            throw new SocketException("Cannot connect to Listener");
        } finally {
            socket.close();
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
            if (intch == 3) {
                break;
            }

        }
        System.out.println(response);
    }
}
