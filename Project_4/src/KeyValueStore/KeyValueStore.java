package KeyValueStore;

//TODO: Make a key value store, use

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;

public class KeyValueStore extends java.rmi.server.UnicastRemoteObject implements IKeyValueStore{

    private HashMap<String, String> keyValueStore = new HashMap<>();

    public KeyValueStore() throws RemoteException {
        super();
    }

    /**
     *
     * @param key
     * @return
     */
    public String getKey(String key) throws RemoteException {
        if(keyValueStore.containsKey(key)) {
            System.out.println("SUCCESS GET: " + keyValueStore.get(key));
            return keyValueStore.get(key);
        } else {
            throw new IllegalArgumentException("Key is not in the right format.");
        }
    }

    /**
     *
     * @param key
     * @param value
     */
    public void putKey(String key, String value) throws RemoteException{
        keyValueStore.put(key, value);
        System.out.println("SUCCESS PUT: " + key);
    }

    /**
     *
     * @param key
     */
    public void deleteKey(String key) throws RemoteException {
        keyValueStore.remove(key);
        System.out.println("SUCCESS DELETE REQUEST");
    }
}
