package Servers;

import KeyValueStore.KeyValueStore;
import PAXOS.Acceptor;
import PAXOS.Learner;
import PAXOS.Proposer;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

//TODO: WORKS, NOT ABOUT INSTATTION OF CONSTRUVTOR AND LEARNER

public class Server extends KeyValueStore implements Runnable, IServer{
    /**
     * Constructor for the computations implementation.
     *
     * @throws RemoteException throws a remote exception.
     */

    private String serverName;
    private String localhost;
    private String key = "";
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

    private Acceptor acceptor;
    private String msg = "";
    private Learner learner;

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

        this.acceptor = new Acceptor();
        this.learner = new Learner();
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

        this.acceptor = new Acceptor();
        this.learner = new Learner();
    }

    @Override
    public String startGetRequest(String key) throws RemoteException, InterruptedException {
        setKeyStore(key);

//        Server server = new Server(serverName, localhost, key, "get", serverId, acceptorPorts, acceptorServerNames,
//                portNumber);
//        Thread T1 = new Thread(server);
//        T1.start();
//        T1.join();
//        String val = server.getMessage();
        String val = getKey(key);
        return val;
    }

    @Override
    public void startPutRequest(String key, String msg) throws RemoteException {
//        Server server = new Server(serverName, localhost, key, "put", serverId);
//        new Thread(server).start();
        LOGGER.info("Mesage: " + msg);
        LOGGER.info("Key: " + key);
        LOGGER.info("CASE PUT REQUEST HIT");
        acceptorsPortsList = Arrays.asList(acceptorPorts.split("\\s*,\\s*"));
        acceptorsServerNamesList = Arrays.asList(acceptorServerNames.split("\\s*,\\s*"));
        // propose
        setMessage(msg);
        setKeyStore(key);
        System.out.println("This message: " + this.msg);
        System.out.println("This key: " + this.key);
        proposer.propose(acceptorsPortsList, localhost, acceptorsServerNamesList, serverName, portNumber, key,
                msg);

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
    public Boolean accept(List<IServer> acceptorNodes, float proposalId, int sizeAcceptors) throws RemoteException {
        return acceptor.checkAcceptance(acceptorNodes, proposalId, sizeAcceptors);
    }

    @Override
    public float getHighestProposalId() throws RemoteException {
        return highestProposerId;
    }

    @Override
    public void learn(String hostName, List<String> acceptorPortsList, List<String> serverNames, String key,
                      String msg) throws RemoteException {
        learner.learnPaxos(hostName, acceptorPortsList, serverNames, key, msg);
    }

    @Override
    public void putToKvStore(String key, String msg) throws RemoteException {
        System.out.println("putToKvStore: " + key);
        System.out.println("putToKVStore: " + msg);
        putKey(key, msg);
    }

    @Override
    public void setMessage(String value) throws RemoteException {
        this.msg = value;
    }

    @Override
    public void setKeyStore(String key) throws RemoteException {
        this.key = key;
    }

    @Override
    public void startDeleteRequest(String key) throws RemoteException {
        //TODO: TOMMOROW WILL NEED TO CHECK FOR PUT OR DELETE IN PROPOSE LEARNER SECTION
        // THEN ADD CODE FOR NEW LEARNER METHOD FOR DELETE
        deleteKey(key);
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
                    System.out.println("CASE GET: " + this.key);
                    System.out.println("CASE GET 2: " + key);
                    returnMsg = getKey(this.key);
                    LOGGER.info("Request GET Successful");
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
