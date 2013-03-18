package org.tp.soa.client.openstreetmap;

import java.net.URI;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * API OpenStreetMap
 * @author Anthony REY <anthony.rey@mailoo.org>
 * @since 18/03/2013
 */
public class OpenStreetMapApi {
	
	private String url;
	private Client client;
	private String apiUrl;
	private String options;
	
	/**
	 * Informations générales de l'API REST OpenStreetMap
	 */
	public OpenStreetMapApi(){
		// Url de l'API
		this.apiUrl = "http://nominatim.openstreetmap.org/search";
		this.options = "?format=xml&limit=1";
		this.url = this.apiUrl+this.options;
		
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
	private URI getBaseURI(String method){
		return UriBuilder.fromUri(this.url+method).build();
	}
	
	/**
	 * Récupération des informations pour un lieu donné
	 * @param version Version de l'API
	 * @param user Utilisateur demandé
	 * @return String Le XML demandé
	 */
	public String getMoreInformations(String lieu) {
		lieu = lieu.replaceAll("\\s+", "+");
		WebResource userTimeline = this.client.resource(getBaseURI("&q="+lieu));
		return userTimeline.accept(MediaType.TEXT_HTML).get(String.class);
	}
}
