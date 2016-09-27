package com.omega;


import com.omega.service.DetectionService;
import spark.Request;
import spark.Response;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static spark.Spark.*;

/**
 * Created by NaveenJetty on 9/19/2016.
 */
public class Api {
    static DetectionService detectionService = new DetectionService();

    public static void main(String[] args) {
        port(5678);

        get("/kmlfile",(req,res)-> getFile(req,res));
    }

    public static Object getFile(Request request, Response response){
//        response.type("application/xml");
        response.raw().setContentType("application/xml");
        String file_name=null;
        try {
            file_name = detectionService.getkml();
            if (file_name.equals("error"))
                return null;
        }catch (FileNotFoundException e){
            halt(405,"server error");
        }

//            response.header("Content-Disposition", "attachment;filename="+file_name);
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
