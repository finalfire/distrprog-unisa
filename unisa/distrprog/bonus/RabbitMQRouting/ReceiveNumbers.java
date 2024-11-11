package unisa.distrprog.bonus.RabbitMQRouting;

import com.rabbitmq.client.*;

public class ReceiveNumbers {
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();
        channel.exchangeDeclare(EmitNumbers.EXCHANGE, "direct");

        String key = args[0];
        String queueName = channel.queueDeclare().getQueue();
        channel.queueBind(queueName, EmitNumbers.EXCHANGE, key);

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
