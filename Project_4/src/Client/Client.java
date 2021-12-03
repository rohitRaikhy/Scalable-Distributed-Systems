package Client;

import Servers.IServer;

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
     * @param args arguments to be entered by the user.
     *             {Hostname, port_number, server_name}.
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
                        //TODO: This does not need PAXOS
                        String req = server.startGetRequest(key);
                        System.out.println("Request get: " + req);
                        LOGGER.info("Request get: " + req);
                        break;
                    case "put":
                        //TODO: Need to talk to the proposer for PAXOS Consensus.
                        server.startPutRequest(key, msg);
                       break;
                    case "delete":
                        //TODO: Need to talk to the proposer for PAXOS Consensus.
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
