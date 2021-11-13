package Server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

public class Coordinator extends java.rmi.server.UnicastRemoteObject implements ICoordinator {

    private List<String> items;
    private String portServers;
    private String REQUEST;
    private String deleteMsgTemp;
    private final static Logger LOGGER = Logger.getLogger(Server.class.getName());

    public Coordinator() throws RemoteException {
        super();
    }

    public Coordinator(String portServers) throws RemoteException {
        super();
        this.portServers = portServers;
    }

    @Override
    public String phaseOne(String hostName, String serverName, String request, String key,
    String msg) throws RemoteException, NotBoundException {

        ArrayList<IServer> servers = new ArrayList<IServer>();
        ArrayList<String> serverNames = new ArrayList<String>();
        ArrayList<String> globalCommits = new ArrayList<String>();

        REQUEST = request;
        items = Arrays.asList(portServers.split("\\s*,\\s*"));
        LOGGER.info(items.toString());
        serverNames.add("server_one");
        serverNames.add("server_two");
        serverNames.add("server_three");
        serverNames.add("server_four");
        serverNames.add("server_five");
        System.out.println("Host name: " + hostName);
        System.out.println("serverName: " + serverName);
        for (int i = 0; i < 5; i++) {
            try {
                System.out.println("server port number " + items.get(i));
                Registry registry = LocateRegistry.getRegistry(hostName, Integer.parseInt(items.get(i)));
                IServer server = (IServer) registry.lookup(serverNames.get(i));
                LOGGER.info("hit");
                servers.add(server);
            } catch (NotBoundException e) {
                System.out.println("Failed to acquire servers");
                return "ABORT";
            } catch (RemoteException e) {
                System.out.println("There is a server down");
                return "ABORT";
            }

        }
        for (IServer server : servers) {

            if (request.toLowerCase().equals("put")) {
                String response = server.commitPutRequest(key, msg);
                globalCommits.add(response);
            }

            if (request.toLowerCase().equals("delete")) {
                String response = server.commitDeleteRequest(key);
                globalCommits.add(response);
            }
        }
        // go to phase two
        for (String response : globalCommits) {
            if (response.equals("ABORT")) {
                for (IServer server : servers) {
                    if (request.toLowerCase().equals("put")) {
                        server.rollBackPutRequest(key);
                    }
                    else if (request.toLowerCase().equals("delete")) {
                        server.rollBackDeleteRequest(key, msg);
                    }
                }
                System.out.println("Cannot commit to servers, phase one GLOBAL ABORT");
                return "ABORT";
            }
        }
        System.out.println("Global Commit");

        if(request.toLowerCase().equals("delete")) {
            System.out.println("All delete requests successful");
        } else if (request.toLowerCase().equals("put")) {
            System.out.println("All Put requests successful");
        }
        return "Global Commit";
    }
}
