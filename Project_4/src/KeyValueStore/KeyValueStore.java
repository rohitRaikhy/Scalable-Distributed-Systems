/**
 * Implementation of the key value store interface.
 */

package KeyValueStore;

import java.rmi.RemoteException;
import java.util.HashMap;

public class KeyValueStore extends java.rmi.server.UnicastRemoteObject implements IKeyValueStore{

    private HashMap<String, String> keyVs = new HashMap<>();

    /**
     * Constructor for key value store.
     *
     * @throws RemoteException throws a remote exception.
     */
    public KeyValueStore() throws RemoteException {
        super();
    }

    /**
     * Retrieves the value from the key value store.
     *
     * @param key String key.
     * @return String value.
     * @throws RemoteException throws remote exception.
     */
    public String getKey(String key) throws RemoteException {
        if(keyVs.containsKey(key)) {
            System.out.println("SUCCESS GET: " + keyVs.get(key));
            return keyVs.get(key);
        } else {
            throw new IllegalArgumentException("Key is not in the right format. This is the key: " + key);
        }
    }

    /**
     * Puts value into the key value store.
     *
     * @param key String key.
     * @param value String value.
     */
    public void putKey(String key, String value) throws RemoteException{
        keyVs.put(key, value);
        System.out.println("SUCCESS PUT: " + key);
        System.out.println("PUT KEY METHOD: "  +keyVs.get(key));
    }

    /**
     * Deletes key from the key value store.
     *
     * @param key String key.
     */
    public void deleteKey(String key) throws RemoteException {
        keyVs.remove(key);
        System.out.println("SUCCESS DELETE REQUEST: " + keyVs.get(key));
    }
}
