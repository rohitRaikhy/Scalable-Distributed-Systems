package Servers;

import KeyValueStore.KeyValueStore;
import PAXOS.Proposer;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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
    private Proposer proposer;
    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
    private String serverId;
    private String acceptorPorts;
    private List<String> acceptorsPortsList;
    private List<String> acceptorsServerNamesList;
    private String acceptorServerNames;
    private float highestProposerId = 0;
    private Integer portNumber;

    public Server(String serverName, String localhost, String serverId, String acceptorPorts, String acceptorServerNames,
                  Integer portNumber)
            throws RemoteException {
        this.serverName = serverName;
        this.localhost = localhost;
        this.proposer = new Proposer(serverId);
        this.serverId = serverId;
        this.acceptorPorts = acceptorPorts;
        this.acceptorServerNames = acceptorServerNames;
        this.portNumber = portNumber;
    }

    public Server(String serverName, String localhost, String key, String request, String serverId,
                  String acceptorPorts, String acceptorServerNames, Integer portNumber)
            throws RemoteException {
        super();
        this.serverName = serverName;
        this.localhost = localhost;
        this.key= key;
        this.request = request;
        this.proposer = new Proposer(serverId);
        this.serverId = serverId;
        this.acceptorPorts = acceptorPorts;
        this.acceptorServerNames = acceptorServerNames;
        this.portNumber = portNumber;
    }

    @Override
    public String startGetRequest(String key) throws RemoteException, InterruptedException {
        Server server = new Server(serverName, localhost, key, "get", serverId, acceptorPorts, acceptorServerNames,
                portNumber);
        Thread T1 = new Thread(server);
        T1.start();
        T1.join();
        String val = server.getMessage();
        return val;
    }

    @Override
    public void startPutRequest(String key, String msg) throws RemoteException {
//        Server server = new Server(serverName, localhost, key, "put", serverId);
//        new Thread(server).start();

        LOGGER.info("CASE PUT REQUEST HIT");
        acceptorsPortsList = Arrays.asList(acceptorPorts.split("\\s*,\\s*"));
        acceptorsServerNamesList = Arrays.asList(acceptorServerNames.split("\\s*,\\s*"));
        // propose
        proposer.propose(acceptorsPortsList, localhost, acceptorsServerNamesList, serverName, portNumber);
        // accept

        //learn: Make action to servers for request PUT
    }

    @Override
    public float prepareProposal(float proposerId) throws RemoteException {
        if(proposerId > highestProposerId) {
            highestProposerId = proposerId;
            return proposerId;
        } else {
            return highestProposerId;
        }
    }

    @Override
    public void makePromise(float proposerId) throws RemoteException {

    }

    @Override
    public void restart() throws RemoteException {
        highestProposerId = 0;
    }

    @Override
    public void run() {
        execute();
    }

    public void execute() {
        try {
            switch (this.request.toLowerCase()) {
                case "get":
                    System.out.println("HIT GET CASE");
                    returnMsg = getKey(this.key);
                    break;
                case "put":
                    //TODO: BUILD OUT PHASE ONE, CLIENT SENT REQUEST TO PROPOSER THIS SERVER.
                    // NEED TO SET UP PROPOSER
                    LOGGER.info("CASE PUT REQUEST HIT");
//                    proposer.propose();
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
