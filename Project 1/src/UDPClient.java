import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

public class UDPClient {

    private static InetAddress serverHost;
    private static int serverPortNumber;
    private final static Logger LOGGER = Logger.getLogger(UDPClient.class.getName());

    /**
     * Main method to execute client program.
     *
     * @param args arguments from user input terminal.
     * @throws IOException exception thrown.
     */
    public static void main(String args[]) throws IOException {
        DatagramSocket dgSocket = null;
        FileHandler fh = new FileHandler("myLogUdpClient.txt");
        fh.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        if (args.length < 3) {
            LOGGER.info("UDP client arguments not in the order: test message, server host name, port number.");
            System.exit(1);
        }
        try {
            dgSocket = new DatagramSocket();

            byte[] bytes = args[0].getBytes();
            serverHost = InetAddress.getByName(args[1]);
            serverPortNumber = Integer.valueOf(args[2]).intValue();
            DatagramPacket dgRequest = new DatagramPacket(bytes, args[0].length(), serverHost,
                    serverPortNumber);
            dgSocket.send(dgRequest);
            byte[] byteBuffer = new byte[1000];
            DatagramPacket dgResponse = new DatagramPacket(
                    byteBuffer, byteBuffer.length);
            dgSocket.receive(dgResponse);
            LOGGER.info("Datagram Response: " + new String(dgResponse.getData()));
        }
        catch (SocketException e) {
            LOGGER.info("Socket Exception.");
        } catch (IOException e) {
            LOGGER.info("IO Exception.");
        }

        while (true) {
            if (args.length < 3) {
                LOGGER.info("Client arguments incorrect input: test message, server host name, port number");
                System.exit(1);
            }
            System.out.println("Enter a request: ");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            dgSocket.setSoTimeout(1000 * 3);
            try {
                byte[] byteBuffer = new byte[100];
                DatagramPacket dgRequests = new DatagramPacket(s.getBytes("UTF-8"), s.length(), serverHost,
                        serverPortNumber);
                dgSocket.send(dgRequests);
                System.out.println("Request sent to server: " + s);
                LOGGER.info("Request send to server " + s);
                DatagramPacket dgResponse = new DatagramPacket(
                        byteBuffer, byteBuffer.length);
                dgSocket.receive(dgResponse);
                LOGGER.info("Datagram Response: " + new String(dgResponse.getData()));
            } catch (InterruptedIOException iioe) {
                LOGGER.info("Network timeout");
            }
        }
    }
}
