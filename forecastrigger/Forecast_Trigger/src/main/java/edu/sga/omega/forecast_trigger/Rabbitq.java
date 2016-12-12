package edu.sga.omega.forecast_trigger;

import com.google.gson.JsonObject;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by vkalvaku on 12/11/16.
 */
public class Rabbitq {

    public Channel send_channel;
    public Channel receive_channel;
    public Channel status_channel;
    public Connection connection;

    public Rabbitq(Boolean ifMain) {
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));

            String ip = in.readLine(); //you get the IP as a String
            System.out.println(ip);

            ip = "52.15.165.201";

            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(ip);
            connection = factory.newConnection();
            if (ifMain) {
                receive_channel = connection.createChannel();
                receive_channel.basicQos(1);
                receive_channel.queueDeclare("ForecastTrigger", true, false, false, null);
            } else {
                send_channel = connection.createChannel();
                send_channel.queueDeclare("response", true, false, false, null);
            }
            status_channel = connection.createChannel();
            status_channel.queueDeclare("status", true, false, false, null);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void sendStatus(String room, String req_no, Boolean status) {


//        Map<String, Object> data = new HashMap<String, Object>();
//        data.put( "name", "dvsfa" );
//        data.put( "age", 32 );
//        data.put( "city", room );
//        Gson gson = new Gson();
//        String json = gson.toJson(data);


        JsonObject msg = new JsonObject();
        if (status) {
            msg.addProperty("msg", "Forecast Trigger is processing req # " + req_no);
            msg.addProperty("status", "0");
        } else {
            msg.addProperty("msg", "Forecast Trigger has completed processing req # " + req_no);
            msg.addProperty("status", "1");
        }
        msg.addProperty("room", "" + room);
        String message = msg.toString();
        System.out.println(message);

        try {
            status_channel.basicPublish("", "status", null, message.getBytes());
        } catch (Exception e) {
            System.out.println("Cannot publish status msg" + e);
        }


    }

    public void sendResponse(String room, String req_no, String url) {

        JsonObject msg = new JsonObject();
        msg.addProperty("msg", "Forecast Trigger has completed processing req # " + req_no);
        msg.addProperty("room", room);
        msg.addProperty("url", url);
        String message = msg.toString();
        System.out.println(message + room);
        try {
            send_channel.basicPublish("", "response", null, message.getBytes());
        } catch (Exception e) {
            System.out.println("Cannot publish response msg" + e);
        }


    }
}
