/**
 * Driver program for the coordinator application.
 */

package Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class StartCoordinator {

    /**
     * Main program of coordinator application.
     *
     * @param args user input arguments {port number of coordinator, server ports of all servers}
     * @throws RemoteException throws remote exception.
     */
    public static void main(String[] args) throws RemoteException {
        if(args.length < 1) {
            System.exit(1);
        }
        try {
            int portNumber = Integer.parseInt(args[0]);
            String serverPorts = args[1].toString();
            Coordinator coord = new Coordinator(serverPorts);
            Registry registry = LocateRegistry.createRegistry(portNumber);
            registry.bind("Coordinator", coord);
            System.out.println("SUCCESS CONNECTED TO COORDINATOR");
        } catch (AlreadyBoundException e) {
            System.out.println("Coordinator is already bound to registry");
        } catch (IllegalArgumentException e) {
            System.out.println("Arguments not in right format.");
        } catch (RemoteException e) {
            System.out.println("Remote exception.");
        }
    }
}
