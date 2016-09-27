package com.omega.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class ClusteringService {


    public static String getkml() throws FileNotFoundException{
        float minX = 50.0f;
        float maxX = 100.0f;

        Random rand = new Random();

        float finalX = rand.nextFloat() * (maxX - minX) + minX;

        final Kml kml = new Kml();
        kml.createAndSetPlacemark().withName("Location").withOpen(Boolean.TRUE).createAndSetPoint()
                .addToCoordinates(-0.126236, finalX);
// marshals to console
        kml.marshal();
// marshals into file
        String file_name = "NewKml.kml";
        boolean success=kml.marshal(new File(file_name));

        if (success)
            return file_name;
        else
            return "error";
    }
}
