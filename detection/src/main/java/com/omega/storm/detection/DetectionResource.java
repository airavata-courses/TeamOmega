package com.omega.storm.detection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("DetectionResource")
public class DetectionResource {

	@GET
	@Produces("application/xml")

	@Path("/get")
	 public Response output() throws FileNotFoundException {
//        Client client = ClientBuilder.newClient();
//        final InputStream responseStream = client.target("http://localhost:8080/detection/webapi/...").request()
//                .get(InputStream.class);
		DetectionService.getkml();
		File file=new File("Newkml.kml");
		
        ResponseBuilder response = Response.ok(file);
       
		response.header("Content-Disposition", "attachment;filename=detectionOutput.kml");
		
        
        
        return response.build();
    }
	
}
