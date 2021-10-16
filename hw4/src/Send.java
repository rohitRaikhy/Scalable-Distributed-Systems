/**
 * Class for sending messages through Rabbitmq messsage queue system.
 *  Rabbitmq server needs to be downloaded and installed on local machine.
 *  Rabbitmq packages needs to be installed, can be installed using intellij through
 *  project structure, imports, searching rabbitmq message queue.
 *  jar files included in src file are needed to run the program in terminal.
 */

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
    private final static String QUEUE_NAME = "hello_world";

    /**
     * Main function of send message queue.
     *
     * @param args arguments that can be entered by user.
     * @throws Exception throws an exception if new connection cannot be established.
     */
    public static void main(String[] args) throws Exception{
        ConnectionFactory fact = new ConnectionFactory();
        fact.setHost("localhost");
        try(Connection con = fact.newConnection();
        Channel channel = con.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String msg = "Hello World!";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println(" [x] Sent " + msg);
        }
    }
}
