package com.omega.storm.clustering;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;

import de.micromata.opengis.kml.v_2_2_0.Kml;

public class ClusterService {
	public static void getkml() throws FileNotFoundException{
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
kml.marshal(new File("cluster.kml"));


	}
	
}
