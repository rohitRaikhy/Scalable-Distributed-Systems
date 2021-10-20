package Server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerStart {

    public static void main(String[] args) throws RemoteException {
        if(args.length < 1) {
            System.exit(1);
        }

        try {
            int portNumber = Integer.parseInt(args[0]);
            RmiServer server = new RmiServer();
            Registry registry = LocateRegistry.createRegistry(portNumber);
            registry.bind("RPCServer", server);
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
