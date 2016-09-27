package com.omega;


import com.omega.service.ClusteringService;
import spark.Request;
import spark.Response;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static spark.Spark.*;

public class Api {
    static ClusteringService clusteringService = new ClusteringService();

    public static void main(String[] args) {
        port(5789);

        get("/kmlfile",(req,res)-> getFile(req,res));
    }

    public static Object getFile(Request request, Response response){
        //Get file from the parameters
        String file_path = request.queryParams("path");
        System.out.println("file path from parameter"+file_path);

        response.raw().setContentType("application/xml");
        String file_name=null;
        try {
            file_name = clusteringService.getkml();
            if (file_name.equals("error"))
                return null;
        }catch (FileNotFoundException e){
            halt(405,"server error");
        }
        response.raw().setHeader("Content-Disposition","attachment; filename=output.zip");
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(response.raw().getOutputStream()));
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file_name)))
        {
            ZipEntry zipEntry = new ZipEntry(file_name);

            zipOutputStream.putNextEntry(zipEntry);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = bufferedInputStream.read(buffer)) > 0) {
                zipOutputStream.write(buffer,0,len);
            }

        } catch (Exception e) {
            e.printStackTrace();
            halt(405,"server error");
        }
        return response.raw();
    }
}
