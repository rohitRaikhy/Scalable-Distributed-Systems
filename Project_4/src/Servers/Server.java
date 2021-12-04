package Servers;

import KeyValueStore.KeyValueStore;
import PAXOS.Acceptor;
import PAXOS.Learner;
import PAXOS.Proposer;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

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

    /**
     * Constructor for server class.
     *
     * @param serverName String server name.
     * @param localhost String localhost.
     * @param serverId String server id.
     * @param acceptorPorts List of acceptor ports.
     * @param acceptorServerNames List of server names.
     * @param portNumber Integer port number.
     * @throws RemoteException throws a remote exception.
     */
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

    /**
     * Constructor for server class.
     *
     * @param serverName String server name.
     * @param localhost String localhost.
     * @param key String key.
     * @param request String request.
     * @param serverId String server id.
     * @param acceptorPorts List of acceptor ports.
     * @param acceptorServerNames Lost of server names.
     * @param portNumber Integer port numbers.
     * @throws RemoteException throws a remote exception.
     */
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
        String val = getKey(key);
        return val;
    }

    @Override
    public void startPutRequest(String key, String msg) throws RemoteException {
        LOGGER.info("Mesage: " + msg);
        LOGGER.info("Key: " + key);
        LOGGER.info("CASE PUT REQUEST HIT");
        acceptorsPortsList = Arrays.asList(acceptorPorts.split("\\s*,\\s*"));
        acceptorsServerNamesList = Arrays.asList(acceptorServerNames.split("\\s*,\\s*"));
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
                    LOGGER.info("CASE PUT REQUEST HIT");
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

    /**
     *  Gets message.
     *
     * @return String value.
     */
    public String getMessage() {
        return returnMsg;
    }
}
