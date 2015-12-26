package server.service;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by lanhnguyen on 12/22/2015.
 */
public abstract class AbstractConnectionHandler implements Runnable {
    protected Socket connnection;
    public void run() {

    }

    public void write(String line){

    }

    public void response(OutputStream out,byte[] msg){

    }
}
