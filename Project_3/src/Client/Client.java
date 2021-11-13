/**
 * This is the client program of the key value store. It allows for the following CRUD operations; put, delete, get.
 */

package Client;
import Server.IServer;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class Client {


    private static String hostName;
    private static Integer portNumber;
    private static String msg;
    private final static Logger LOGGER = Logger.getLogger(Client.class.getName());
    private static String serverName;
    private static Integer coordPortNumber;

    /**
     * Driver program of the client process.
     *
     * @param args arguments to be entered by the user. {Hostname, port_number, server_name, coordinator port number}.
     * @throws IOException throws exception.
     */
    public static void main(String[] args) throws IOException {

        FileHandler fh = new FileHandler("myLogRmiClient.txt");
        fh.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        if (args.length < 2) {
            System.exit(1);
        }
        try {
            hostName = args[0].toString();
            portNumber = Integer.parseInt(args[1]);
            serverName = args[2].toString();
            coordPortNumber = Integer.parseInt(args[3]);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        try {
            Registry registry = LocateRegistry.getRegistry(hostName, portNumber);
            IServer server = (IServer) registry.lookup(serverName);
            while (true) {
                System.out.println("Enter a request (GET, PUT, DELETE): ");
                Scanner in = new Scanner(System.in);
                String request = in.nextLine();
                request = request.toLowerCase();
                System.out.println("Request sent to the server: " + request);
                LOGGER.info("Request sent to the server: " + request);
                System.out.println("Enter key: ");
                String key = in.nextLine();
                if (request.equals("put")) {
                    System.out.println("Enter message: ");
                    msg = in.nextLine();
                }
                switch (request) {
                    case "get":
                        String value = server.startGetRequestThread(key, coordPortNumber);
                        System.out.println("Request get: " + value);
                        LOGGER.info("Request get: " + value);
                        break;
                    case "put":
                        server.startPutRequest(key, msg, coordPortNumber);
                        System.out.println("Request put: " + msg);
                        LOGGER.info("Request put: " + msg);
                        break;
                    case "delete":
                        server.startDeleteRequest(key, coordPortNumber);
                        System.out.println("Request delete: " + key);
                        LOGGER.info("Request delete: " + key);
                        break;
                }
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Arguments for commands not correct (GET, PUT, DELETE)");
        } catch (NotBoundException e) {
            System.out.println("Not Bound");
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
    }
}
