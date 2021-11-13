package Server;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICoordinator extends Remote {

    /**
     * Phase one of the 2pc protocol.
     *
     * @param hostName String hostname.
     * @param serverName String servername.
     * @param request String request type {PUT, GET, DELETE}.
     * @param key String key.
     * @param msg String message.
     * @return String {OK, ABORT}
     * @throws RemoteException throws remote exception.
     * @throws NotBoundException throws not bound exception.
     */
    public String phaseOne(String hostName, String serverName, String request,
                           String key, String msg) throws RemoteException, NotBoundException;

}
