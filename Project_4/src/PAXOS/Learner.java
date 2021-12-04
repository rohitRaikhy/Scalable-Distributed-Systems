/**
 * Learner phase of the PAXOS algorithm.
 */

package PAXOS;

import KeyValueStore.KeyValueStore;
import Servers.IServer;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Learner extends KeyValueStore {
    /**
     * Constructor of learner class.
     *
     * @throws RemoteException
     */
    public Learner() throws RemoteException {
        super();
    }

    /**
     * Learn phase of the paxos algorithm, commits to key value store operations.
     *
     * @param hostName String hostname.
     * @param acceptorPortsList List of acceptor ports.
     * @param serverNames List of server names of acceptors.
     * @param key String key.
     * @param msg String message.
     */
    public void learnPaxos(String hostName, List<String> acceptorPortsList, List<String> serverNames, String key,
                           String msg) {
        for (int i = 0; i < acceptorPortsList.size(); i++) {
            try {
                Registry registry = LocateRegistry.getRegistry(hostName, Integer.parseInt(acceptorPortsList.get(i)));
                IServer server = (IServer) registry.lookup(serverNames.get(i));
                server.putToKvStore(key, msg);
            } catch (NotBoundException e) {
                continue;
            } catch (RemoteException e) {
                continue;
            }
        }
    }
}
