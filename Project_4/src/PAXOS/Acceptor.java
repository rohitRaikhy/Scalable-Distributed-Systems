/**
 * Acceptor phase of the PAXOS algorithm.
 */
package PAXOS;

import KeyValueStore.KeyValueStore;
import Servers.IServer;
import java.rmi.RemoteException;
import java.util.List;

public class Acceptor extends KeyValueStore {

    /**
     * Constructor for the acceptor class.
     *
     * @throws RemoteException throws a remote exception.
     */
    public Acceptor() throws RemoteException {
        super();
    }

    /**
     * Checks the acceptance of value in acceptor node.
     *
     * @param acceptorNodes list of acceptor nodes.
     * @param proposalId float proposalId.
     * @param sizeAcceptors Integer size of acceptor list.
     * @return Boolean.
     * @throws RemoteException throws a remote exception.
     */
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
