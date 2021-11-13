/**
 * Interface for the server.
 */

package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {
    /**
     * Starts a thread for get request.
     *
     * @param key String key.
     * @return String value.
     * @throws RemoteException throws exception.
     */
    public String startGetRequestThread(String key, Integer coordPortNumber) throws RemoteException, InterruptedException;

    /**
     * Starts a thread for put request.
     *
     * @param key String key.
     * @param value String value.
     * @throws RemoteException throws exception.
     */
    public void startPutRequest(String key, String value, Integer coordPortNumber) throws RemoteException;

    /**
     * Starts a thread for delete request.
     *
     * @param key String key.
     * @throws RemoteException throws exception.
     */
    public void startDeleteRequest(String key, Integer coordPortNumber) throws RemoteException;

    /**
     * Checks to see if put request will work with server for coordindator.
     *
     * @param key String key to be added to hashmap.
     * @param value String value to be added to hashmap.
     * @return String OK or Abort.
     * @throws RemoteException throws Remote Exception.
     */
    public String commitPutRequest(String key, String value) throws RemoteException;

    /**
     * Rolls back put request if abort message of 2pc.
     *
     * @param key String key.
     * @throws RemoteException throws remote exception.
     */
    public void rollBackPutRequest(String key) throws RemoteException;

    /**
     * Executes the put request.
     *
     * @param key String key.
     * @param value String message.
     * @throws RemoteException throws remote exception.
     */
    public void executePutRequest(String key, String value) throws RemoteException;

    /**
     * Rolls back delete request if abort message of 2pc.
     *
     * @param key String key.
     * @param msg String message.
     * @throws RemoteException throws remote exception.
     */
    public void rollBackDeleteRequest(String key, String msg) throws RemoteException;

    /**
     * commit message for 2pc protocol delete request.
     *
     * @param key String key.
     * @return String OK or ABORT.
     * @throws RemoteException throws remote exception.
     */
    public String commitDeleteRequest(String key) throws RemoteException;
}
