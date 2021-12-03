package Servers;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServer extends Remote {

    public String startGetRequest(String key) throws RemoteException, InterruptedException;
    public void startPutRequest(String key, String msg) throws RemoteException;
    public float prepareProposal(float proposerId) throws RemoteException;
    public void makePromise(float proposerId) throws RemoteException;
    public void restart() throws RemoteException;
}
