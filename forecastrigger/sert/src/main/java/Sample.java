/**
 * Created by vkalvaku on 12/11/16.
 */
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP;

import org.json.simple.JSONObject;


import java.io.IOException;



class Rabbitq {

    public Channel send_channel;
    public Channel receive_channel;
    public Channel status_channel;
    public Connection connection;

    public Rabbitq() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("52.15.165.201");
            connection = factory.newConnection();
            receive_channel = connection.createChannel();
            receive_channel.basicQos(1);
            receive_channel.queueDeclare("ForecastTrigger", true, false, false, null);
            send_channel = connection.createChannel();
            status_channel = connection.createChannel();
            send_channel.queueDeclare("Response", true, false, false, null);
            status_channel.queueDeclare("Status", true, false, false, null);

        }
        catch ( Exception e){
            System.out.println(e);
        }

    }

}


public class Sample {
    private final static String QUEUE_NAME = "ForecastTrigger";

    public static void main(String[] argv) throws Exception {

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                JsonElement jelement = new JsonParser().parse(message);
                JsonObject jobject = jelement.getAsJsonObject();
                System.out.println(" [x] Received '" + message + "'");
                System.out.println(jobject.get("msg").toString());
                try {

                    //sleep 5 seconds
                    Thread.sleep(25000);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Woke up");
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }
}

