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


    public Server(Integer cordPortNumber, String serverName) throws RemoteException {
        this.cordPortNumber = cordPortNumber;
        this.serverName = serverName;
       // this.localhost = localhost;
    }

    public Server(String request, String key, String msg, Integer coordPortNumber, String serverName
                  ) throws RemoteException {
        super();
        this.request = request;
        this.key = key;
        this.msg = msg;
        this.cordPortNumber = coordPortNumber;
        this.serverName = serverName;
      //  this.localhost = localhost;
    }

    // This requests a get method, does not need to talk to the coordinator
    @Override
    public String startGetRequestThread(String key, Integer coordPortNumber) throws RemoteException, InterruptedException {
        Server server = new Server("GET", key, "EMPTY", coordPortNumber, serverName);
        Thread T1 = new Thread(server);
        T1.start();
        T1.join();
        String val = server.getMessage();
        return val;
    }

    //TODO: Needs to talk to the coordinator to perform put. If global commit msg comes back, coordinator
    // TODO: will perform put.
    @Override
    public void startPutRequest(String key, String value, Integer coordPortNumber) throws RemoteException {

        Server server = new Server("PUT", key, value, coordPortNumber, serverName);
        //TODO: IF WORKS ADD PRINT STATEMENT FOR SUCCESS
        new Thread(server).start();

    }

    //TODO: Needs to talk to the coordinator to perform delete
    @Override
    public void startDeleteRequest(String key, Integer coordPortNumber) throws RemoteException {
        Server server = new Server("DELETE", key, "EMPTY", coordPortNumber, serverName);
        //TODO: IF WORKS ADD PRINT STATEMENT FOR SUCCESS
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

    // TODO: need a roll back if failed
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
                   // putRequest(this.key, this.msg, map);
                    //TODO: Call the coordinator for global message commit or abort. e.g coordintor.phase1()
                    //TODO: Get coordinator registry, talk to cooridaintor through regirstry
                    try {
                        //TODO: local host is hardcoded, might not work with docker, port is also hardcoded for the
                        //TODO: coordinator need to change this by adding to the client so client knows port of cooridinator
                       Registry registry = LocateRegistry.getRegistry("localhost", cordPortNumber);
                      //  Registry registry = LocateRegistry.getRegistry(localhost, cordPortNumber);
                        ICoordinator coordinator = (ICoordinator) registry.lookup("Coordinator");
//                        System.out.println("KEY: "  + this.key);
//                        System.out.println("MSG: "  + this.msg);
                        LOGGER.info("hit");
                      coordinator.phaseOne("localhost", serverName, "PUT", this.key, this.msg);
                       // coordinator.phaseOne(localhost, serverName, "PUT", this.key, this.msg);
                    } catch (NotBoundException e) {
                        System.out.println("Not bound");
                    }
                    break;
                case "delete":
                   // deleteKeyRequest(this.key, map);
                    try {
                        Registry registry = LocateRegistry.getRegistry("localhost", cordPortNumber);
                        //Registry registry = LocateRegistry.getRegistry(localhost, cordPortNumber);
                        ICoordinator coordinator = (ICoordinator) registry.lookup("Coordinator");
                        returnMsg = getRequest(this.key, map);
                        coordinator.phaseOne("localhost", serverName, "DELETE", this.key, returnMsg);
                        //coordinator.phaseOne(localhost, serverName, "DELETE", this.key, returnMsg);
                    } catch (NotBoundException e) {
                        System.out.println("Not bound");
                    }
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
}
