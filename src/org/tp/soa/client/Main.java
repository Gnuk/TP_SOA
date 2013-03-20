package org.tp.soa.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.List;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.tp.soa.client.twitter.TwitterApi;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

import org.tp.soa.client.openstreetmap.*;
import org.tp.soa.client.flickr.*;

public class Main {
	
	/**
	 * Lancement du client
	 * @param args
	 */
	public static void main(String[] args) {
		
		String cleFlickr = "528ae2c27f6bc67b1376016e2d259bf1";
		String secretFlickr = "b087e446e6b2ac7e";
		
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

		Parser p = new Parser();
		ArrayList<Offre> stages = new ArrayList<Offre>();
		stages = p.recupererOffres();
		System.out.println(stages.size());
		System.out.println("Enregistrement des informations dans la base de donnée Google App Engine : \n");
		try{
			for (int i = 0; i < stages.size(); ++i){
				URI uri = new URI(
				        "http", 
				        "www.app-stages.appspot.com", 
				        "/tp_soa_server",
				        "description="+stages.get(i).getDescription()+"&ville="+stages.get(i).getVille(),
				        null);
				String request = uri.toASCIIString();
				HttpGet get = new HttpGet(request);
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse response = httpClient.execute(get);
				
				BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	
				System.out.println(br.readLine());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

			
		
		p.getMap().setVisible(true);
	}
}
