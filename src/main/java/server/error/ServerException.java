package server.error;

/**
 * Created by lanhnguyen on 12/23/2015.
 */
public class ServerException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ServerException(String message){
        super(message);
    }

    public ServerException(String message, Throwable e){
        super(message, e);
    }

}
