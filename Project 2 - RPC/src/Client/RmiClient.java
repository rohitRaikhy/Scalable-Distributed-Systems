package Client;

import RMI.RmiInterface;
import Server.RmiServer;
import Server.RmiServerInterface;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class RmiClient {

    private static String hostName;
    private static Integer portNumber;
    private static String msg;
    private final static Logger LOGGER = Logger.getLogger(RmiClient.class.getName());

    public static void initialEntry(RmiServerInterface server) throws RemoteException, InterruptedException {
        for(int i = 0; i <= 5; i++) {
            server.startPutRequest(Integer.toString(i), "output: " + i);
            LOGGER.info("Put " + i + " into hashmap");
            String value = server.startGetRequestThread(Integer.toString(i));
            LOGGER.info("Request get: " + value);
            server.startDeleteRequest(Integer.toString(i));
            LOGGER.info("Request delete: " + i);
        }
    }

    public static void main(String[] args) throws IOException {

        FileHandler fh = new FileHandler("myLogRmiClient.txt");
        fh.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        if (args.length < 2) {
            System.exit(1);
        }
        // Get the host name and port number from the client
        try {
            hostName = args[0].toString();
            portNumber = Integer.parseInt(args[1]);
        } catch (IllegalArgumentException e ) {
            e.printStackTrace();
        }

        // get the request type, key, data from the user

        try {
            Registry registry = LocateRegistry.getRegistry(hostName, portNumber);
            RmiServerInterface server = (RmiServerInterface) registry.lookup("RPCServer");

            initialEntry(server);

            // set up while loop here
            while(true) {
                System.out.println("Enter a request (GET, PUT, DELETE): ");
                Scanner in = new Scanner(System.in);
                String request = in.nextLine();
                request = request.toLowerCase();
                System.out.println("Request sent to the server: " + request);
                LOGGER.info("Request sent to the server: " + request);
                System.out.println("Enter key: ");
                //Enter the key
                String key = in.nextLine();

                // message value if needed
                if (request.equals("put")) {
                    System.out.println("Enter message: ");
                    msg = in.nextLine();
                }

         switch (request) {
                    case "get":
                        String value = server.startGetRequestThread(key);
                        System.out.println("Request get: " + value);
                        LOGGER.info("Request get: " + value);
                        break;
                    case "put":
                        server.startPutRequest(key, msg);
                        System.out.println("Request put: " + msg);
                        LOGGER.info("Request put: " + msg);
                        break;
                    case "delete":
                        server.startDeleteRequest(key);
                        System.out.println("Request delete: " + key);
                        LOGGER.info("Request delete: " + key);
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Arguments for commands not correct (GET, PUT, DELETE)");
            LOGGER.info("Exception raised.");
        } catch (NotBoundException e) {
            LOGGER.info("Exception raised.");
        } catch (RemoteException e) {
            LOGGER.info("Exception raised.");
        } catch (InterruptedException e) {
            LOGGER.info("Exception raised.");
        }
    }
}
