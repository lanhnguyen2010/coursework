package server.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConnectionHandler extends AbstractConnectionHandler {
    public static final String END_CHAR = "\003";
    public static final String RESPONSE_MESSAGE = "Successfull";
    private HDFSWriter hdfsWriter;
    private String hdfsPath;

    public ConnectionHandler(Socket aConnnection, HDFSWriter hdfsWriter, String hdfsPath) {
        this.connnection = aConnnection;
        this.hdfsWriter = hdfsWriter;
        this.hdfsPath = hdfsPath;
    }

    @Override
    public void run() {
        System.out.println("Receive message from " + connnection.getInetAddress().getHostAddress()
                + " port: " + connnection.getPort());
        try {
            int num =1;
            InputStream in = connnection.getInputStream();
            OutputStream out = connnection.getOutputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter(Pattern.compile(END_CHAR));
            while (scanner.hasNext()){
                write(scanner.next() + "\n");
                response(out, (num +END_CHAR).getBytes());
                num ++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                hdfsWriter.close();
                connnection.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void write(String line) {
        hdfsWriter.write(hdfsPath, line);
    }

    @Override
    public void response(OutputStream out, byte[] msg) {
        try {
            out.write(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
