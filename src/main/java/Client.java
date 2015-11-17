import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * ======================================================================================
 * Client.java
 * ======================================================================================
 * <p>
 * Definitions for class:
 * - Client
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

public class Client {
    public static void main(String argv[]) throws Exception {
        String sentence;
        String modifiedSentence;
        final File folder = new File("D:/input");
        Socket clientSocket = new Socket("localhost", 6789);
        for (File file : folder.listFiles()){
            BufferedReader inFromUser = new BufferedReader(new FileReader(file));
            DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            while ((sentence = inFromUser.readLine()) != null) {
                System.out.println(sentence);
                outToServer.writeBytes(sentence + '\n');
                //modifiedSentence = inFromServer.readLine();
                //System.out.println("FROM SERVER: " + modifiedSentence);
            }

        }



        clientSocket.close();
    }
}
