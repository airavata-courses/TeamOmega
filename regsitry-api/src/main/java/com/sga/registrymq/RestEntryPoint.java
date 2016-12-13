package com.sga.registrymq;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


import javax.print.Doc;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by navee on 12/10/2016.
 */

@Path("/")
public class RestEntryPoint {
    private static MongoClient mongo = Database.getInstance().mongoClient;
    @GET
    @Path("/")
    public Response root(){
        String result = "<h1>Registry API is running</h1>";
        return Response.status(200).entity(result).build();
    }

    @GET
    @Path("/{username}")
    public Response sayHello(@PathParam("username") String username){
        String output = "Jersey says hello to :" + username;
        return Response.status(200).entity(output).build();
    }

    @GET
    @Path("/userprocesses/{username}")
    public Response getProcessesbyUser(@PathParam("username") String username){
        MongoCollection collection = mongo.getDatabase("testOmega").getCollection("ProcessMaster");
//        FindIterable myDoc = collection.find(eq("username",username));
        List<Document> documents = (List<Document>) collection.find(eq("username",username)).into(new ArrayList<Document>());
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        for(Document doc:documents){
            jsonArray.add(doc);
        }
        result.put("processes",jsonArray);
        System.out.println(documents);
        return Response.status(200).entity(result.toJSONString()).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

    @GET
    @Path("/getprocessinfo/{process_id}")
    public Response getProcessInfo(@PathParam("process_id") long process_id){
        MongoCollection collection = mongo.getDatabase("testOmega").getCollection("ProcessDetail");
        List<Document> documents = (List<Document>) collection.find(eq("process_id",process_id)).into(new ArrayList<Document>());
        JSONArray jsonArray = new JSONArray();
        JSONObject result = new JSONObject();
        for(Document doc: documents) jsonArray.add(doc);
        result.put("process",jsonArray);
        return Response.status(200).entity(result.toJSONString()).type(MediaType.APPLICATION_JSON_TYPE).build();
    }

}
