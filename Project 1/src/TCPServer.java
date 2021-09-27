import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.*;

public class TCPServer {

    private static int socketNumber;
    private final static Logger LOGGER = Logger.getLogger(TCPServer.class.getName());

    /**
     * Main program to execute the TCP server.
     *
     * @param args arguments inputs from user terminal.
     * @throws IOException throws exception.
     */
    public static void main(String[] args) throws IOException {
        DatagramSocket dgSocket = null;
        String requestType = null;
        String key = null;
        Boolean flag = Boolean.TRUE;
        String value = null;

        FileHandler fh = new FileHandler("myLogTcpServer.txt");
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
            ServerSocket s = new ServerSocket(socketNumber);
            Socket s1 = s.accept();
            try {
                initialEntry(s1, hashMap);
                LOGGER.info("Initial 5 requests SUCCESS.");
            } catch (IOException e) {
                System.out.println("Socket Exception");
                LOGGER.info("Initial 5 requests FAILED.");
            }
            while(true) {
                InputStream s1In = s1.getInputStream();
                DataInputStream dis = new DataInputStream(s1In);
                String output = new String(dis.readUTF());
                try {
                    requestType = output.substring(0, output.indexOf("("));
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Wrong input");
                    LOGGER.info("Input type is incorrect from user.");
                    requestType = "";
                }

                if(requestType.equals("put")) {
                    try {
                        key = output.substring(output.indexOf("("), output.indexOf(",")).replace(
                                "(", "");
                        value = output.substring(output.indexOf(","), output.indexOf(")")).
                                replace(")", "").replace(",", "");
                        flag = Boolean.TRUE;
                    } catch(IndexOutOfBoundsException e) {
                        LOGGER.info("Key format incorrect.");
                        DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                        out.writeUTF("Key format incorrect");
                        flag = Boolean.FALSE;
                    }
                    if(flag != Boolean.FALSE) {
                        try {
                            putRequest(key, value, hashMap);
                            String putResult = "SUCCESS PUT REQUEST";
                            DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                            out.writeUTF(putResult);
                            LOGGER.info("PUT REQUEST SUCCESS");
                        } catch (IllegalArgumentException e) {
                            String putResult = "FAILURE PUT REQUEST.";
                            DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                            out.writeUTF(putResult);
                            System.out.println("Key or value are incorrect format");
                            LOGGER.info("PUT REQUEST FAILED.");
                        }
                    }
                }

                else if(requestType.equals("get")) {
                    try {
                        key = output.substring(output.indexOf("("), output.indexOf(")")).replace(
                                "(", "").replace(")", "");
                        flag = Boolean.TRUE;
                    } catch (IndexOutOfBoundsException e) {
                        LOGGER.info("Key format incorrect.");
                        DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                        out.writeUTF("Key format incorrect");
                        flag = Boolean.FALSE;
                    }
                    if(flag != Boolean.FALSE) {
                        try {
                            String result = getRequest(key, hashMap);
                            String getResult = "SUCCESS GET REQUEST: " + result;
                            DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                            out.writeUTF(getResult);
                            LOGGER.info("GET REQUEST SUCCESS.");
                        } catch (IllegalArgumentException e) {
                            String getResult = "FAILURE GET REQUEST";
                            DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                            out.writeUTF(getResult);
                            System.out.println("Key is incorrect format");
                            LOGGER.info("GET REQUEST FAILED.");
                        }
                    }
                }
                else if(requestType.equals("delete")) {
                    try {
                        key = output.substring(output.indexOf("("), output.indexOf(")")).replace(
                                "(", "").replace(")", "");
                        flag = Boolean.TRUE;
                    } catch(IndexOutOfBoundsException e) {
                        LOGGER.info("Key format incorrect.");
                        DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                        out.writeUTF("Key format incorrect");
                        flag = Boolean.FALSE;
                    }
                    if(flag != Boolean.FALSE) {
                        try {
                            deleteKey(key, hashMap);
                            String deleteResult = "SUCCESS DELETE REQUEST";
                            DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                            out.writeUTF(deleteResult);
                            LOGGER.info("DELETE REQUEST SUCCESS.");
                        } catch (IllegalArgumentException e) {
                            String deleteResult = "FAILURE DELETE REQUEST";
                            DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                            out.writeUTF(deleteResult);
                            System.out.println("Delete result incorrect format");
                            LOGGER.info("DELETE REQUEST FAILED.");
                        }
                    }

                }
            }



        } catch (SocketException e) {
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
     * @param dgSocket socket for TCP server.
     * @param hashMap hashmap to store data for TCP client requests.
     * @throws IOException throws IOException.
     */
    private static void initialEntry(Socket dgSocket, Map<String, String> hashMap) throws IOException {
//        byte [] byteBuffer = new byte[1000];
//        DatagramPacket dgRequest = new DatagramPacket(
//                byteBuffer, byteBuffer.length);
//        dgSocket.receive(dgRequest);

        for (int i = 0; i <= 5; i++) {
            putRequest(Integer.toString(i), "output", hashMap);
            System.out.println("Put " + i + " into hashmap.");
            String val = getRequest(Integer.toString(i), hashMap);
            System.out.println("Received " + val + " from hashmap.");
            deleteKey(Integer.toString(i), hashMap);
            System.out.println("Deleted key " + i);
        }
        String sendString = "SUCCESS 5 PUT, 5 GET, 5 DELETE REQUESTs";
        try {
            DataOutputStream out = new DataOutputStream(dgSocket.getOutputStream());
            out.writeUTF(sendString);
        } catch (IOException e) {
            System.out.println("IO Exception found.");
        }

//        byte[] sendData = sendString.getBytes("UTF-8");
//
//        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
//                dgRequest.getAddress(), dgRequest.getPort());
//        dgSocket.send(sendPacket);
    }
}
