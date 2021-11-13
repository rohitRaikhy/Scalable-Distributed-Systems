/**
 * Driver program for the server application.
 */

package Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartServer {

    /**
     * Main program for the server application.
     *
     * @param args user arguments {port number of server, server name, coordintor port number}
     * @throws RemoteException
     */
    public static void main(String[] args) throws RemoteException {
        if(args.length < 1) {
            System.exit(1);
        }
        try {
            int portNumber = Integer.parseInt(args[0]);
            String serverName = args[1].toString();
            int cordPortNumber = Integer.parseInt(args[2]);
            //String localhost = args[3].toString();

            Server server = new Server(cordPortNumber, serverName);
            Registry registry = LocateRegistry.createRegistry(portNumber);
            registry.bind(serverName, server);
            System.out.println("SUCCESS CONNECTED TO SERVER");
        } catch (AlreadyBoundException e) {
            System.out.println("Bounds exception");
        } catch (IllegalArgumentException e) {
            System.out.println("Arguments not in right format.");
        } catch (RemoteException e) {
            System.out.println("Remote exception.");
        }
    }
}
