package Servers;

import KeyValueStore.KeyValueStore;
import java.rmi.RemoteException;

//TODO: When making the servers, in the command line need:
// 1. what acceptors can speak to one another with ports
// 2. All learners need to speak to each other with ports
// 3. Proposers need to know what acceptors they can speak to

public class Server extends KeyValueStore implements Runnable, IServer{
    /**
     * Constructor for the computations implementation.
     *
     * @throws RemoteException throws a remote exception.
     */

    private String serverName;
    private String localhost;
    private String key;
    private String request;
    private volatile String returnMsg;

    public Server(String serverName, String localhost) throws RemoteException {
        this.serverName = serverName;
        this.localhost = localhost;
    }

    public Server(String serverName, String localhost, String key, String request) throws RemoteException {
        super();
        this.serverName = serverName;
        this.localhost = localhost;
        this.key= key;
        this.request = request;
    }

    @Override
    public String startGetRequest(String key) throws RemoteException, InterruptedException {
        Server server = new Server(serverName, localhost, key, "get");
        Thread T1 = new Thread(server);
        T1.start();
        T1.join();
        String val = server.getMessage();
        return val;
    }

    @Override
    public void run() {
        execute();
    }

    public void execute() {
        try {
            switch (this.request.toLowerCase()) {
                case "get":
                    returnMsg = getKey(this.key);
                    break;
                case "put":
                    break;
                case "delete":
                    break;
                default:
                    throw new IllegalArgumentException("Cannot perform request");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return returnMsg;
    }
}
