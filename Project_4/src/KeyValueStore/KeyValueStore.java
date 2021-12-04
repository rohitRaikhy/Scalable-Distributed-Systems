package KeyValueStore;

//TODO: Make a key value store, use

import java.rmi.RemoteException;
import java.util.HashMap;

public class KeyValueStore extends java.rmi.server.UnicastRemoteObject implements IKeyValueStore{

    private HashMap<String, String> keyVs = new HashMap<>();

    public KeyValueStore() throws RemoteException {
        super();
    }

    /**
     *
     * @param key
     * @return
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
     *
     * @param key
     * @param value
     */
    public void putKey(String key, String value) throws RemoteException{
        keyVs.put(key, value);
        System.out.println("SUCCESS PUT: " + key);
        System.out.println("PUT KEY METHOD: "  +keyVs.get(key));
    }

    /**
     *
     * @param key
     */
    public void deleteKey(String key) throws RemoteException {
        keyVs.remove(key);
        System.out.println("SUCCESS DELETE REQUEST: " + keyVs.get(key));
    }
}
