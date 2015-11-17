import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ======================================================================================
 * Server.java
 * ======================================================================================
 * <p>
 * Definitions for class:
 * - Server
 * <p>
 * ======================================================================================
 * Modification History:
 * ======================================================================================
 * <p>
 * Date:			11/17/2015
 * Original development
 *
 * @author lanhnguyen
 * @version 1.0
 *          Description:  	Output Segment Interfaces
 *          Design Pattern(s): No
 *          <p>
 *          ======================================================================================
 *          Copyright 2015, Sandlot Solutions. All rights reserved.
 *          ======================================================================================
 **/

public class Server {
    public static void main(String argv[]) throws Exception {
        String clientSentence;
        String capitalizedSentence;
        ServerSocket welcomeSocket = new ServerSocket(6789);
        File fout = new File("D:/output/output.txt");
        FileOutputStream fos = new FileOutputStream(fout);


        while (true) {
            System.out.println("1");
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
            while ((clientSentence = inFromClient.readLine())!=null) {
                bw.write(clientSentence);
                bw.newLine();

                System.out.println("Received: " + clientSentence);
                capitalizedSentence = clientSentence.toUpperCase() + '\n';
                outToClient.write(capitalizedSentence.getBytes());
            }
            System.out.println(connectionSocket.isConnected());
            bw.close();
        }
    }
}
