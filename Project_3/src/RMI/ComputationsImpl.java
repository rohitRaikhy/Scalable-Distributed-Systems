/**
 * Implementation of the IComputations interface. This is the logic for the CRUD operations on the
 * key value store.
 */

package RMI;

import java.rmi.RemoteException;
import java.util.Map;

public class ComputationsImpl extends java.rmi.server.UnicastRemoteObject implements IComputations {

    /**
     * Constructor for the computations implementation.
     * @throws java.rmi.RemoteException throws a remote exception.
     */
    public ComputationsImpl() throws java.rmi.RemoteException {
        super();
    }

    @Override
    public void putRequest(String key, String value, Map<String, String> hashmap) throws RemoteException {
        hashmap.put(key, value);
        System.out.println("SUCCESS PUT: " + key);
    }

    @Override
    public String getRequest(String key, Map<String, String> hashmap) throws RemoteException {
        if(hashmap.containsKey(key)) {
            System.out.println("SUCCESS GET: " + hashmap.get(key));
            return hashmap.get(key);
        } else {
            throw new IllegalArgumentException("Key is not in the right format.");
        }
    }

    @Override
    public void deleteKeyRequest(String key, Map<String, String> hashmap) throws RemoteException {
        hashmap.remove(key);
        System.out.println("SUCCESS DELETE REQUEST");
    }
}
