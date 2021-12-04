package PAXOS;

import KeyValueStore.KeyValueStore;
import Servers.IServer;

import java.rmi.RemoteException;
import java.util.List;

public class Acceptor extends KeyValueStore {

    public Acceptor() throws RemoteException {
        super();
    }

    public Boolean checkAcceptance(List<IServer> acceptorNodes, float proposalId, int sizeAcceptors) throws RemoteException {
        int count = 0;
        for(IServer acceptor : acceptorNodes) {
            if(acceptor.getHighestProposalId() <= proposalId) {
                count ++;
            }
        }
        if(count >= sizeAcceptors / 2) {
            return true;
        } else{
            return false;
        }
    }
}
