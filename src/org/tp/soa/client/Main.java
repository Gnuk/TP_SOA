package org.tp.soa.client;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.tp.soa.client.openstreetmap.MapView;
import org.tp.soa.client.openstreetmap.OpenStreetMapApi;
import org.tp.soa.client.openstreetmap.Place;
import org.tp.soa.client.twitter.TwitterApi;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

public class Main {
	
	/**
	 * Lancement du client
	 * @param args
	 */
	public static void main(String[] args) {
		TwitterApi twitter = new TwitterApi();
		String xmlTwitter = twitter.getUserTimeline("1", "remixjobs");
		//System.out.println(xmlTwitter);
		try {
			FileWriter fw = new FileWriter("Offres.xml");
			BufferedWriter output = new BufferedWriter(fw);
			output.write(xmlTwitter,0,xmlTwitter.length());
			output.flush();
			output.close ();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		MapView map = new MapView();
		
		OpenStreetMapApi osm = new OpenStreetMapApi();
		try {
			Place place = osm.getMoreInformations("Annecy");
			System.out.println("Recherche : " + place.getSearchPlace());
			System.out.println("Lieu : " + place.getPlace());
			System.out.println("LonLat (" + place.getLongitude() + ", " +place.getLatitude()+")");
			map.addMarker(place.getLatitude(), place.getLongitude());
			
		} catch (UniformInterfaceException | ClientHandlerException
				| JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.setVisible(true);
	}
}
