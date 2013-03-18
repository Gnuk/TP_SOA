package org.tp.soa.client;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.tp.soa.client.openstreetmap.MapView;
import org.tp.soa.client.openstreetmap.OpenStreetMapApi;
import org.tp.soa.client.twitter.TwitterApi;

public class Main {
	
	/**
	 * Lancement du client
	 * @param args
	 */
	public static void main(String[] args) {
		TwitterApi twitter = new TwitterApi();
		String xmlTwitter = twitter.getUserTimeline("1", "remixjobs");
		System.out.println(xmlTwitter);
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
		OpenStreetMapApi osm = new OpenStreetMapApi();
		System.out.println(osm.getMoreInformations("Paris"));

	    MapView map = new MapView();
	    map.addMarker(48.8565056, 2.3521334);
	    map.setVisible(true);
	}
}
