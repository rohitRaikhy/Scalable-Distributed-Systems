package KeyValueStore;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface IKeyValueStore extends Remote {

    public String getKey(String key) throws RemoteException;
    public void putKey(String key, String value) throws RemoteException;
    public void deleteKey(String key) throws RemoteException;
}
