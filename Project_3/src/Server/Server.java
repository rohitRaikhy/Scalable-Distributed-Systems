package Server;

import RMI.ComputationsImpl;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.logging.Logger;

public class Server extends ComputationsImpl implements Runnable, IServer{

    private String request;
    private String key;
    private String msg;
    private static HashMap<String, String> map = new HashMap<>();
    private volatile String returnMsg;
    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
    private Coordinator coordinator;
    private Integer cordPortNumber;
    private String serverName;
    private String localhost;


    public Server(Integer cordPortNumber, String serverName, String localhost) throws RemoteException {
        this.cordPortNumber = cordPortNumber;
        this.serverName = serverName;
        this.localhost = localhost;
    }

    public Server(String request, String key, String msg, Integer coordPortNumber, String serverName,
                 String localhost ) throws RemoteException {
        super();
        this.request = request;
        this.key = key;
        this.msg = msg;
        this.cordPortNumber = coordPortNumber;
        this.serverName = serverName;
        this.localhost = localhost;
    }

    @Override
    public String startGetRequestThread(String key, Integer coordPortNumber) throws RemoteException, InterruptedException {
        Server server = new Server("GET", key, "EMPTY", coordPortNumber, serverName, localhost);
        Thread T1 = new Thread(server);
        T1.start();
        T1.join();
        String val = server.getMessage();
        return val;
    }

    @Override
    public void startPutRequest(String key, String value, Integer coordPortNumber) throws RemoteException {

        Server server = new Server("PUT", key, value, coordPortNumber, serverName, localhost);
        new Thread(server).start();

    }

    @Override
    public void startDeleteRequest(String key, Integer coordPortNumber) throws RemoteException {
        Server server = new Server("DELETE", key, "EMPTY", coordPortNumber, serverName, localhost);
        new Thread(server).start();
    }

    @Override
    public String commitPutRequest(String key, String value) throws RemoteException {
        try {
            putRequest(key, value, map);
            return "OK";
        }catch (RemoteException e) {
            System.out.println("Remote Exception");
            return "ABORT";
        }
    }

    @Override
    public void rollBackPutRequest(String key) throws RemoteException {
        try {
            deleteKeyRequest(key, map);
        } catch (RemoteException e) {
            System.out.println("Remote Exception: rollBackPut");
        }
    }

    @Override
    public void executePutRequest(String key, String msg) throws RemoteException {
        try {
            putRequest(key, msg, map);
        } catch (RemoteException e) {
            System.out.println("Remote Exception: executePut");
        }
    }

    @Override
    public void rollBackDeleteRequest(String key, String msg) throws RemoteException {
        try {
            putRequest(key, msg, map);
        } catch (RemoteException e) {
            System.out.println("Remote Exception: rollBackDelete");
        }
    }

    @Override
    public String commitDeleteRequest(String key) throws RemoteException {
        try {
            deleteKeyRequest(key, map);
            return "OK";
        }catch (RemoteException e) {
            System.out.println("Remote Exception");
            return "ABORT";
        }
    }

    @Override
    public void run() {
        execute();
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
                    try {
                        Registry registry = LocateRegistry.getRegistry(localhost, cordPortNumber);
                        ICoordinator coordinator = (ICoordinator) registry.lookup("Coordinator");
                        LOGGER.info("hit");
                      coordinator.phaseOne(localhost, serverName, "PUT", this.key, this.msg);
                    } catch (NotBoundException e) {
                        System.out.println("Not bound");
                    }
                    break;
                case "delete":
                    try {
                        Registry registry = LocateRegistry.getRegistry(localhost, cordPortNumber);
                        ICoordinator coordinator = (ICoordinator) registry.lookup("Coordinator");
                        returnMsg = getRequest(this.key, map);
                        coordinator.phaseOne(localhost, serverName, "DELETE", this.key, returnMsg);
                    } catch (NotBoundException e) {
                        System.out.println("Not bound");
                    }
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
