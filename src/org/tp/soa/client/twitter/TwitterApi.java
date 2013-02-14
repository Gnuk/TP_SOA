package org.tp.soa.client.twitter;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class TwitterApi {
	
	private String url;
	private Client client;
	
	/**
	 * Informations générales de l'API REST twitter
	 */
	public TwitterApi(){
		// Url de l'API
		this.url = "https://api.twitter.com";
		
		// Appel du client REST
		ClientConfig config = new DefaultClientConfig();
		this.client = Client.create(config);
	}
	
	/**
	 * Récupère une URI pour REST
	 * @param version Version de l'API
	 * @param method Méthode REST utilisée
	 * @return URI
	 */
	private URI getBaseURI(String version, String method){
		return UriBuilder.fromUri(this.url+"/"+version+method).build();
	}
	
	/**
	 * Récupération de la Timeline pour un utilisateur
	 * @param version Version de l'API
	 * @param user Utilisateur demandé
	 * @return String Le XML demandé
	 */
	public String getUserTimeline(String version, String user) {
		WebResource userTimeline = this.client.resource(getBaseURI(version, "/statuses/user_timeline.xml?screen_name="+user));
		return userTimeline.accept(MediaType.TEXT_XML).get(String.class);
	}
}
