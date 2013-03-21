package org.tp.soa.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.tp.soa.client.twitter.TwitterApi;

public class Main {
	
	/**
	 * Lancement du client
	 * @param args
	 */
	public static void main(String[] args) {

		Parser p = new Parser();
		ArrayList<Offre> stages = new ArrayList<Offre>();
		stages = p.recupererOffres("remixjobs");
		for(int i=0; i<stages.size(); i++){
			System.out.println(stages.get(i).toString());
		}
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
