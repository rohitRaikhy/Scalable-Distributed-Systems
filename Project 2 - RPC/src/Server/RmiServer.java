package Server;

import RMI.RmiInterface;
import RMI.RmiInterfaceImpl;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.logging.Logger;

public class RmiServer extends RmiInterfaceImpl implements Runnable, RmiServerInterface {

    private String request;
    private String key;
    private String msg;
    private static HashMap<String, String> map = new HashMap<>();
    private volatile String returnMsg;
    private final static Logger LOGGER = Logger.getLogger(RmiServer.class.getName());


    /**
     * Default constructor.
     *
     * @throws RemoteException throws exception.
     */
    public RmiServer() throws RemoteException{
    }

    /**
     * Constructor of java RMI object.
     *
     * @throws RemoteException
     */
    public RmiServer(String request, String key, String msg) throws RemoteException {
        super();
        this.request = request;
        this.key = key;
        this.msg = msg;
    }

    @Override
    public void run() {
        execute();
    }


    /**
     * Starts thread for get request.
     *
     * @param key String key.
     * @return String value in hashmap.
     * @throws RemoteException throws exception.
     */
    @Override
    public String startGetRequestThread(String key) throws RemoteException, InterruptedException {
        RmiServer server = new RmiServer("GET", key, "EMPTY");
        // TODO: add a return statement here for getting value
        //TODO: IF WORKS ADD PRINT STATEMENT FOR SUCCESS
        Thread T1 = new Thread(server);
        T1.start();
        T1.join();
        String val = server.getMessage();
        return val;
    }

    /**
     * Starts thread for put request.
     *
     * @param key String key.
     * @param value String value.
     * @throws RemoteException throws exception.
     */
    @Override
    public void startPutRequest(String key, String value) throws RemoteException {
        RmiServer server = new RmiServer("PUT", key, value);
        //TODO: IF WORKS ADD PRINT STATEMENT FOR SUCCESS
        new Thread(server).start();
    }

    /**
     * Starts thread for delete request.
     *
     * @param key String key.
     * @throws RemoteException throws exception.
     */
    @Override
    public void startDeleteRequest(String key) throws RemoteException {
        RmiServer server = new RmiServer("DELETE", key, "EMPTY");
        //TODO: IF WORKS ADD PRINT STATEMENT FOR SUCCESS
        new Thread(server).start();
    }

    /**
     * Executes program for threads.
     */
    public void execute() {

        try {
            switch (this.request.toLowerCase()) {
                case "get":
                    returnMsg = getRequest(this.key, map);
                    break;
                case "put":
                    putRequest(this.key, this.msg, map);
                    break;
                case "delete":
                    deleteKeyRequest(this.key, map);
                    break;
                default:
                    throw new IllegalArgumentException("Cannot perform request");
            }
        } catch (RemoteException e) {
            System.out.println("Remote Exception");
        }
    }

    public String getMessage() {
        return returnMsg;
    }

    public static void main(String[] args) {

    }
}
