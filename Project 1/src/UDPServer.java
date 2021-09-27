import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class UDPServer {

    private static int socketNumber;
    private final static Logger LOGGER = Logger.getLogger(UDPServer.class.getName());

    /**
     * Main function to run the Server UDP program.
     *
     * @param args arguments to be entered by the user in terminal.
     * @throws IOException exception.
     */
    public static void main(String args[]) throws IOException {
        DatagramSocket dgSocket = null;
        String requestType = null;

        FileHandler fh = new FileHandler("myLogUdpServer.txt");
        fh.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        if (args.length <= 0) {
            System.out.println("Please pass the port number for UDPServer");
            LOGGER.info("UDP Server port number incorrect.");
            System.exit(1);
        }

        Map<String, String> hashMap = new HashMap<String, String>();

        try {
            socketNumber = Integer.valueOf(args[0]).intValue();
            dgSocket = new DatagramSocket(socketNumber);
            byte[] byteBuffer = new byte[1000];
            try {
                initialEntry(dgSocket, hashMap);
            } catch (IOException e){
                System.out.println("Socket Exception");
                LOGGER.info("Initial 5 requests failed.");
            }
            while (true) {
                DatagramPacket dgRequest = new DatagramPacket(
                byteBuffer, byteBuffer.length);
                dgSocket.receive(dgRequest);

                String output = new String(dgRequest.getData());
                try {
                    requestType = output.substring(0, output.indexOf("("));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Wrong input");
                    LOGGER.info("Input type is incorrect from user.");
                    requestType = "";
                }

                if(requestType.equals("put")) {
                    String key = output.substring(output.indexOf("("), output.indexOf(",")).replace(
                            "(", "");
                    String value = output.substring(output.indexOf(","), output.indexOf(")")).
                            replace(")", "").replace(",", "");
                    try {
                        putRequest(key, value, hashMap);
                        String putResult = "SUCCESS PUT REQUEST";
                        DatagramPacket dgResponse = new DatagramPacket(
                                putResult.getBytes(), putResult.length(),
                                dgRequest.getAddress(), dgRequest.getPort());
                        dgSocket.send(dgResponse);
                        LOGGER.info("PUT REQUEST SUCCESS");
                    } catch (IllegalArgumentException e) {
                        String putResult = "FAILURE PUT REQUEST.";
                        DatagramPacket dgResponse = new DatagramPacket(
                                putResult.getBytes(), putResult.length(),
                                dgRequest.getAddress(), dgRequest.getPort());
                        dgSocket.send(dgResponse);
                        System.out.println("Key or value are incorrect format");
                        LOGGER.info("PUT REQUEST FAILED.");
                    }
                }

                else if(requestType.equals("get")) {
                    String key = output.substring(output.indexOf("("), output.indexOf(")")).replace(
                            "(", "").replace(")", "");
                    try {
                        String result = getRequest(key, hashMap);
                        String getResult = "SUCCESS GET REQUEST: " + result;
                        DatagramPacket dgResponse = new DatagramPacket(
                                getResult.getBytes(), getResult.length(),
                                dgRequest.getAddress(), dgRequest.getPort());
                        dgSocket.send(dgResponse);
                        LOGGER.info("GET REQUEST SUCCESS.");
                    } catch (IllegalArgumentException e) {
                        String putResult = "FAILURE GET REQUEST";
                        DatagramPacket dgResponse = new DatagramPacket(
                                putResult.getBytes(), putResult.length(),
                                dgRequest.getAddress(), dgRequest.getPort());
                        dgSocket.send(dgResponse);
                        System.out.println("Key is incorrect format");
                        LOGGER.info("GET REQUEST FAILED.");
                    }
                } else if(requestType.equals("delete")) {
                    String key = output.substring(output.indexOf("("), output.indexOf(")")).replace(
                            "(", "").replace(")", "");
                    try {
                        deleteKey(key, hashMap);
                        String deleteResult = "SUCCESS DELETE REQUEST";
                        DatagramPacket dgResponse = new DatagramPacket(
                                deleteResult.getBytes(), deleteResult.length(),
                                dgRequest.getAddress(), dgRequest.getPort());
                        dgSocket.send(dgResponse);
                        LOGGER.info("DELETE REQUEST SUCCESS.");
                    } catch (IllegalArgumentException e) {
                        String deleteResult = "FAILURE DELETE REQUEST";
                        DatagramPacket dgResponse = new DatagramPacket(
                                deleteResult.getBytes(), deleteResult.length(),
                                dgRequest.getAddress(), dgRequest.getPort());
                        dgSocket.send(dgResponse);
                        System.out.println("Delete result incorrect format");
                        LOGGER.info("DELETE REQUEST FAILED.");
                    }

                }

            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dgSocket != null) {
                dgSocket.close();
            }
        }
    }

    /**
     * Operation for request of putting key, value in hashmap.
     *
     * @param key String key to be put in hashmap.
     * @param value String value to be put in hashmap.
     * @param hashMap hashmap of values String, String for storing requests.
     */
    private static void putRequest(String key, String value, Map<String, String> hashMap){
        hashMap.put(key, value);
    }

    /**
     * Operation for request of getting element from hashmap.
     *
     * @param key the key to be retrieved.
     * @param hashMap hashmap of values String, String for storing requests.
     * @return String value from hashmap.
     * @throws IllegalArgumentException error when arguments not entered correctly.
     */
    private static String getRequest(String key, Map<String, String> hashMap) throws IllegalArgumentException{
        if (hashMap.containsKey(key) ) {
            return hashMap.get(key);
        } else {
            throw new IllegalArgumentException("Key not right format.");
        }
    }

    /**
     * Operation for request of deleting from hashmap.
     *
     * @param key String the key to be deleted.
     * @param hashMap hashmap of values String, String for storing requests.
     */
    private static void deleteKey(String key, Map<String, String> hashMap){
        hashMap.remove(key);
    }

    /**
     * Adds 5 put, get, delete requests to the hash map on start of server execution.
     *
     * @param dgSocket Socket on server side.
     * @param hashMap hashmap of values String, String for storing requests.
     * @throws IOException Error  case.
     */
    private static void initialEntry(DatagramSocket dgSocket,
                                     Map<String, String> hashMap) throws IOException {

        byte [] byteBuffer = new byte[1000];
        DatagramPacket dgRequest = new DatagramPacket(
                byteBuffer, byteBuffer.length);
        dgSocket.receive(dgRequest);

        for (int i = 0; i <= 5; i++) {
            putRequest(Integer.toString(i), "output", hashMap);
            System.out.println("Put " + i + " into hashmap.");
            String val = getRequest(Integer.toString(i), hashMap);
            System.out.println("Received " + val + " from hashmap.");
            deleteKey(Integer.toString(i), hashMap);
            System.out.println("Deleted key " + i);
        }
        String sendString = "SUCCESS 5 PUT, 5 GET, 5 DELETE REQUESTs";

        byte[] sendData = sendString.getBytes("UTF-8");

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                dgRequest.getAddress(), dgRequest.getPort());
        dgSocket.send(sendPacket);
    }
}
