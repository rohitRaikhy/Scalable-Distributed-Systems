/**
 * Class for receiving messages through Rabbitmq messsage queue system.
 *  Rabbitmq server needs to be downloaded and installed on local machine.
 *  Rabbitmq packages needs to be installed, can be installed using intellij through
 *  project structure, imports, searching rabbitmq message queue.
 *  jar files included in src file are needed to run the program in terminal.
 */

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

public class Receive {

    private final static String QUEUE_NAME = "hello_world";

    /**
     * Main function to start the program of receive message queue with rabbitmq.
     *
     * @param args arguments that can be entered by user.
     * @throws Exception throws exception if connection cannot be established.
     */
    public static void main(String[] args) throws Exception {
        ConnectionFactory fact = new ConnectionFactory();
        fact.setHost("localhost");
        Connection con = fact.newConnection();
        Channel channel = con.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (tag, delivery) -> {
            String msg = new String(delivery.getBody(), "UTF-8");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, tag -> { });
    }
}
