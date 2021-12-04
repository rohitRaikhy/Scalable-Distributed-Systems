package Servers;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

//TODO: BROKE ON THE LEARNERS PART REMOTE SERVER EXCEPTION, FORGOT TO THROW REMOTE EXCEPTION IN LEARN METHOD, WORKED
// AFTER

public interface IServer extends Remote {

    public String startGetRequest(String key) throws RemoteException, InterruptedException;
    public void startPutRequest(String key, String msg) throws RemoteException;
    public float prepareProposal(float proposerId) throws RemoteException;
    public void makePromise(float proposerId) throws RemoteException;
    public void restart() throws RemoteException;

    public Boolean accept(List<IServer> acceptorNodes, float proposalId, int sizeAcceptors) throws RemoteException;
    public float getHighestProposalId() throws RemoteException;

    public void learn(String hostName, List<String> acceptorPortsList, List<String> serverNames, String key,
                      String msg) throws RemoteException;
    public void putToKvStore(String key, String msg) throws RemoteException;
    public void setMessage(String value) throws RemoteException;
    public void setKeyStore(String key) throws RemoteException;

    public void startDeleteRequest(String key) throws RemoteException;
}
