import java.io.*;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class TCPClient {

    private final static Logger LOGGER = Logger.getLogger(TCPClient.class.getName());

    /**
     * Main program executes TCP client.
     *
     * @param args argument inputs from user terminal.
     * @throws IOException throws IOException.
     */
    public static void main(String[] args) throws IOException {

        Socket s1 = null;
        FileHandler fh = new FileHandler("myLogTCPClient.txt");
        fh.setLevel(Level.INFO);
        LOGGER.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();
        fh.setFormatter(formatter);

        if (args.length < 2) {
            LOGGER.info("UDP client arguments not in the order: server host name, port number.");
            System.exit(1);
        }
        try {
            s1 = new Socket(args[0], Integer.parseInt(args[1]));
            InputStream s1In = s1.getInputStream();
            DataInputStream dis = new DataInputStream(s1In);
            String st = new String(dis.readUTF());
            //System.out.println(st);
            LOGGER.info(st);
        } catch (SocketException e) {
            LOGGER.info("Socket Exception.");
        } catch (IOException e) {
            LOGGER.info("IO Exception.");
        }

        while(true) {
            System.out.println("Enter a request: ");
            Scanner in = new Scanner(System.in);
            String s = in.nextLine();
            s1.setSoTimeout(1000 * 3);
            System.out.println("Request sent to the server: " + s);
            try {
                DataOutputStream out = new DataOutputStream(s1.getOutputStream());
                out.writeUTF(s);
                InputStream s1In = s1.getInputStream();
                DataInputStream dis = new DataInputStream(s1In);
                String st = new String(dis.readUTF());
                LOGGER.info("Socket response: " + st);
            } catch (InterruptedIOException iioe) {
                LOGGER.info("Network timeout");
            }
        }
        }
    }
