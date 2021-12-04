/**
 * Interface of the Server.
 */
package Servers;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IServer extends Remote {

    /**
     * Starts the get request.
     *
     * @param key String key.
     * @return String value.
     * @throws RemoteException throws remote exception.
     * @throws InterruptedException throws interrupted exception.
     */
    public String startGetRequest(String key) throws RemoteException, InterruptedException;

    /**
     * Starts the put request.
     *
     * @param key String key.
     * @param msg String msg.
     * @throws RemoteException throws remote exception.
     */
    public void startPutRequest(String key, String msg) throws RemoteException;

    /**
     * Prepare proposal stage of PAXOS.
     *
     * @param proposerId float proposerId.
     * @return float proposerId.
     * @throws RemoteException throws remote exception.
     */
    public float prepareProposal(float proposerId) throws RemoteException;

    /**
     * Promise stage of PAXOS.
     *
     * @param proposerId float proposerId.
     * @throws RemoteException throws remote exception.
     */
    public void makePromise(float proposerId) throws RemoteException;

    /**
     * Restarts the PAXOS algorithm.
     *
     * @throws RemoteException throws a remote exception.
     */
    public void restart() throws RemoteException;

    /**
     * Accept phase 2 of PAXOS.
     *
     * @param acceptorNodes list of acceptor nodes.
     * @param proposalId float proposalId.
     * @param sizeAcceptors Integer size of acceptor list.
     * @return boolean.
     * @throws RemoteException throws a remote exception.
     */
    public Boolean accept(List<IServer> acceptorNodes, float proposalId, int sizeAcceptors) throws RemoteException;

    /**
     * Gets the highest proposal number of PAXOS.
     *
     * @return float highest proposal number.
     * @throws RemoteException throws a remote exception.
     */
    public float getHighestProposalId() throws RemoteException;

    /**
     * Learn phase of PAXOS stage three.
     *
     * @param hostName String hostname.
     * @param acceptorPortsList List of acceptor nodes.
     * @param serverNames List of server names.
     * @param key String key.
     * @param msg String msg.
     * @throws RemoteException throws a remote exception.
     */
    public void learn(String hostName, List<String> acceptorPortsList, List<String> serverNames, String key,
                      String msg) throws RemoteException;

    /**
     * Put to key value store.
     *
     * @param key String key.
     * @param msg String msg.
     * @throws RemoteException throws a remote exception.
     */
    public void putToKvStore(String key, String msg) throws RemoteException;

    /**
     * Sets message.
     * @param value String value.
     * @throws RemoteException throws a remote exception.
     */
    public void setMessage(String value) throws RemoteException;

    /**
     * Sets key.
     *
     * @param key String key.
     * @throws RemoteException throws a remote exception.
     */
    public void setKeyStore(String key) throws RemoteException;

    /**
     * Starts the delete request.
     *
     * @param key String key.
     * @throws RemoteException throws a remote exception.
     */
    public void startDeleteRequest(String key) throws RemoteException;
}
