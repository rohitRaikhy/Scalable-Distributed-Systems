/**
 * Interface for the CRUD operations on key value store.
 */

package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IComputations extends Remote {

    /**
     *
     * @param key String key.
     * @param value String value.
     * @param hashmap hashmap to store data.
     * @throws RemoteException throws exception.
     */
    public void putRequest(String key, String value, Map<String, String> hashmap)
            throws RemoteException;

    /**
     *
     * @param key String key.
     * @param hashmap hashmap to store data.
     * @return String key.
     * @throws RemoteException throws exception.
     */
    public String getRequest(String key, Map<String, String> hashmap) throws RemoteException;

    /**
     *
     * @param key String key.
     * @param hashmap hashmap to store data.
     * @throws RemoteException throws exception.
     */
    public void deleteKeyRequest(String key, Map<String, String> hashmap) throws RemoteException;
}
