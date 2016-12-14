package edu.sga.omega.forecast_trigger; /**
 * Created by vkalvaku on 12/11/16.
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;


public class Forecast {

    public static void main(String[] argv) throws Exception {


        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        final AuroraThrift thriftapi = new AuroraThrift();

        final Rabbitq rabq = new Rabbitq(true);
        Consumer consumer = new DefaultConsumer(rabq.receive_channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                JsonElement jelement = new JsonParser().parse(message);
                JsonObject jobject = jelement.getAsJsonObject();
                String room = jobject.get("room").toString();
                room = room.replaceAll("^\"|\"$", "");
                String req_no = jobject.get("req_no").toString();


                System.out.println(" [x] Received '" + jobject.toString() + "'");
            try {

                //sleep 5 seconds
//                Thread.sleep(25000);
              if (thriftapi.createJob(req_no,room)){
                  rabq.sendStatus(room, req_no, true);
                  rabq.receive_channel.basicAck(envelope.getDeliveryTag(), false);
              }else{
                  rabq.sendStatus(room, req_no, false);
                  rabq.receive_channel.basicNack(envelope.getDeliveryTag(), false,true);
              }

            } catch (Exception e) {
                e.printStackTrace();
            }
                System.out.println("Woke up");


            }
        };
        rabq.receive_channel.basicConsume("ForecastTrigger", false, consumer);
    }
}

