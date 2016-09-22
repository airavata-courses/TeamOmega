package com.omega.storm.clustering;

import java.io.File;
import java.io.FileNotFoundException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.glassfish.jersey.media.multipart.FormDataParam;

@Path("/ClusterResource")
public class ClusterResource {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") File file) throws FileNotFoundException{
		ClusterService.getkml();
		File file1=new File("file.kml");
		
        ResponseBuilder response = Response.ok(file);
       
		response.header("Content-Disposition", "attachment;filename=detectionOutput.kml");
		
        
        
        return response.build();
				
	        
	   }
}