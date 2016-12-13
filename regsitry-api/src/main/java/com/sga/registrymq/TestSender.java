package com.sga.registrymq;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

/**
 * Created by navee on 12/10/2016.
 */
public class TestSender {
    private final static String QUEUE_NAME = "hello";
    public static void main(String[] args) throws java.io.IOException, TimeoutException {
        String message;
        ArrayList<String> messages = new ArrayList<String>();
        message = "{\"room\":  \"xyz@xyz.com-org-us-7257ed775cad24ca\",\"status\":\"0\",\"msg\" : \"DataIngestor is processing the request number 12346478915\",\"req_loc\":\"SEATTLE\",\"req_time\":\"20:40\",\"req_date\":\"10/05/2016\"}";
        messages.add(message);
        message = "{\"room\":  \"xyz@xyz.com-org-us-7257ed775cad24ca\",\"status\":\"1\",\"msg\" : \"DataIngestor has completed the request number 12346478915\"}";
        messages.add(message);
        message = "{\"room\":  \"xyz@xyz.com-org-us-7257ed775cad24ca\",\"status\":\"0\",\"msg\" : \"StormDetection is processing the request number 12346478915\"}";
        messages.add(message);
        message = "{\"room\":  \"xyz@xyz.com-org-us-7257ed775cad24ca\",\"status\":\"1\",\"msg\" : \"StormDetection has completed the request number 12346478915\"}";
        messages.add(message);
        message = "{\"room\":  \"xyz@xyz.com-org-us-7257ed775cad24ca\",\"status\":\"0\",\"msg\" : \"StormClustering is processing the request number 12346478915\"}";
        messages.add(message);
        message = "{\"room\":  \"xyz@xyz.com-org-us-7257ed775cad24ca\",\"status\":\"1\",\"msg\" : \"StormClustering has completed the request number 12346478915\"}";
        messages.add(message);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("52.53.188.79");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        for (String m: messages) {
            channel.basicPublish("", QUEUE_NAME, null, m.getBytes());
        }
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    }
}
