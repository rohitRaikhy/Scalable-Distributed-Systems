package PAXOS;

import KeyValueStore.KeyValueStore;
import Servers.IServer;
//import Servers.Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
//import java.util.logging.Logger;

public class Learner extends KeyValueStore {


    public Learner() throws RemoteException {
        super();
    }

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
