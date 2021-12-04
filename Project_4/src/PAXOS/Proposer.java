/**
 * Proposer stage of the PAXOS algorithm.
 */

package PAXOS;

import KeyValueStore.KeyValueStore;
import Servers.IServer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Proposer extends KeyValueStore {

    private Integer N = 1;
    private float proposerId;
    private String serverId;
    private final static Logger LOGGER = Logger.getLogger(Proposer.class.getName());
    private ArrayList<IServer> acceptorNodes = new ArrayList<>();
    private float highestProposerId;
    private float result;
    private Integer count = 0;
    private String serverName;
    private Integer portNumber;
    private IServer serverProposer;

    /**
     * Constructor of proposer class.
     *
     * @param serverId
     * @throws RemoteException
     */
    public Proposer(String serverId) throws RemoteException {
        super();
        this.serverId = serverId;
    }

    /**
     * Popose stage of the paxos algorithm.
     *
     * @param acceptorPortsList List of acceptors the proposer can speak to.
     * @param hostName String hostname.
     * @param acceptorServerNames List of acceptor server names the proposer can speak to.
     * @param serverName String server name.
     * @param portNumber Integer port number.
     * @param key String key.
     * @param msg String msg.
     * @throws RemoteException throws remote exception.
     */
    public void propose(List<String> acceptorPortsList, String hostName, List<String> acceptorServerNames,
                        String serverName, Integer portNumber, String key, String msg)
            throws RemoteException {
        // 1st stage prepare message to acceptors
        proposerId = Float.parseFloat(serverId + "." + N.toString());
        LOGGER.info("PROPOSER ID: " + proposerId);
        N ++;
        try {
            Registry registryProposer = LocateRegistry.getRegistry(hostName, portNumber);
            serverProposer = (IServer) registryProposer.lookup(serverName);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
        //Phase 1 end NEED SEND THE PROPESER ID TO ALL ACCEPTORS
        for (int i = 0; i < acceptorPortsList.size(); i++) {
            // get the stubs from the registry for all available acceptor nodes
            try {
                Registry registry = LocateRegistry.getRegistry(hostName, Integer.parseInt(acceptorPortsList.get(i)));
                IServer server = (IServer) registry.lookup(acceptorServerNames.get(i));
                LOGGER.info("HIT");
                acceptorNodes.add(server);
                LOGGER.info("ACCEPTOR NODES PHASE 1: " + acceptorServerNames.get(i));
            } catch(NotBoundException e) {
                LOGGER.info("Never bounds to registry");
                continue;
            } catch (RemoteException e) {
                LOGGER.info("Never bounds to registry");
                continue;
            }
        }
        for(IServer acceptorNode: acceptorNodes) {
            try {
                result = acceptorNode.prepareProposal(proposerId);
                if(result == proposerId) {
                    count ++;
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if(count >= acceptorPortsList.size() / 2) {
            // majority is reached
            LOGGER.info("Quoram reached! Phase 1 SUCCESSFUL");
            if(serverProposer.accept(acceptorNodes, proposerId, acceptorPortsList.size())) {
                serverProposer.learn(hostName, acceptorPortsList, acceptorServerNames, key, msg);
                LOGGER.info("PHASE 2 COMPLETE, MAJORITY OF ACCEPTORS APPROVE FOR PHASE 3.");
            } else {
                LOGGER.info("Failed to reach majority! Phase 1 FAILED");
            }

        } else {
            // Restart PAXOS on the proposer node.
            serverProposer.restart();
            LOGGER.info("Failed to reach majority! Phase 1 FAILED");
        }
        count = 0;
        LOGGER.info("Result: " + result);
    }
}
