package RMI;

import java.rmi.RemoteException;
import java.util.Map;

public class RmiInterfaceImpl extends java.rmi.server.UnicastRemoteObject implements RmiInterface {

    /**
     * Constructor of java RMI object.
     *
     * @throws java.rmi.RemoteException
     */
    public RmiInterfaceImpl() throws java.rmi.RemoteException {
        super();
    }

    @Override
    public synchronized void putRequest(String key, String value, Map<String, String> hashmap) throws RemoteException {
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
    public synchronized void deleteKeyRequest(String key, Map<String, String> hashmap) throws RemoteException {
        hashmap.remove(key);
        System.out.println("SUCCESS DELETE REQUEST");
    }
}
