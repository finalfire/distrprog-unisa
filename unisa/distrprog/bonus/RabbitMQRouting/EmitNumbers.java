package unisa.distrprog.bonus.RabbitMQRouting;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.ByteBuffer;
import java.util.Random;

public class EmitNumbers {
    public static final String EXCHANGE = "numbers";

    public static void main(String[] args) throws Exception {
        Random rand = new Random();

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        try (Connection conn = factory.newConnection();
             Channel channel = conn.createChannel()) {
            channel.exchangeDeclare(EXCHANGE, "direct");

            int number = rand.nextInt(100);
            String queue = number % 2 == 0 ? "even" : "odd";
            channel.basicPublish(EXCHANGE, queue, null, String.valueOf(number).getBytes());
            System.out.println(" [x] Sent '" + number + "'");
        }
    }
}
