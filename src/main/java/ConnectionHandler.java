import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ConnectionHandler extends AbstractConnectionHandler {
    public static final String END_CHAR = "\003";
    public static final String RESPONSE_MESSAGE = "Successfull";

    public ConnectionHandler(Socket aConnnection) {
        this.connnection = aConnnection;
    }

    @Override
    public void run() {
        System.out.println("Receive message from " + connnection.getInetAddress().getHostAddress()
                + " port: " + connnection.getPort());
        try {
            InputStream in = connnection.getInputStream();
            OutputStream out = connnection.getOutputStream();
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter(Pattern.compile("\003"));
            while (scanner.hasNext()){
                write(scanner.next());
                response(out, (RESPONSE_MESSAGE +END_CHAR).getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(String line) {
        System.out.println(line);
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
