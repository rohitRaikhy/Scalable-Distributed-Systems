package Servers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

    public String startGetRequest(String key) throws RemoteException, InterruptedException;

}
