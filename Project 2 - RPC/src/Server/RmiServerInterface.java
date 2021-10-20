package Server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerInterface extends Remote {

    /**
     * Starts a thread for get request.
     *
     * @param key String key.
     * @return String value.
     * @throws RemoteException throws exception.
     */
    public String startGetRequestThread(String key) throws RemoteException, InterruptedException;

    /**
     * Starts a thread for put request.
     *
     * @param key String key.
     * @param value String value.
     * @throws RemoteException throws exception.
     */
    public void startPutRequest(String key, String value) throws RemoteException;

    /**
     * Starts a thread for delete request.
     *
     * @param key String key.
     * @throws RemoteException throws exception.
     */
    public void startDeleteRequest(String key) throws RemoteException;

}
