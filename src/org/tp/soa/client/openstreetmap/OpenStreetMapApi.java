package org.tp.soa.client.openstreetmap;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

/**
 * API OpenStreetMap
 * @author Anthony Rey
 * @author Yohann Berthon
 * @since 18/03/2013
 */
public class OpenStreetMapApi {
	
	private String url;
	private Client client;
	private String apiUrl;
	private String options;
	private String lieu;
	
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
	 * Récupère un lieu à partir d'un XML d'openstreetmap
	 * @param stringXml
	 * @return L'objet Place du lieu
	 * @throws JDOMException
	 * @throws IOException
	 */
	private Place toPlace(String stringXml) throws JDOMException, IOException{
		InputStream in = new ByteArrayInputStream(stringXml.getBytes());
		SAXBuilder sxb = new SAXBuilder();
		Document document = sxb.build(in);
    	Element racine = document.getRootElement();
    	List<Element> listPlaces = racine.getChildren("place");
    	Iterator<Element> i = listPlaces.iterator();
    	Place place = new Place(this.lieu);
    	if(i.hasNext()){
    		Element courant = i.next();
    		place.setPlace(courant.getAttribute("display_name").getValue());
    		place.setLongitude(courant.getAttribute("lon").getDoubleValue());
    		place.setLatitude(courant.getAttribute("lat").getDoubleValue());
    	}
    	return place;
	}
	
	/**
	 * Récupération des informations pour un lieu donné
	 * @param version Version de l'API
	 * @param user Utilisateur demandé
	 * @return L'objet Place contenant les informations spplémentaires
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws ClientHandlerException 
	 * @throws UniformInterfaceException 
	 */
	public Place getMoreInformations(String lieu) throws UniformInterfaceException, ClientHandlerException, JDOMException, IOException {
		this.lieu = lieu;
		String composedLieu = this.lieu.replaceAll("\\s+", "+");
		WebResource userTimeline = this.client.resource(getBaseURI("&q="+composedLieu));
		
		// Attention, TEXT_XML ne fonctionne pas pour une raison inconnue
		
		return this.toPlace(userTimeline.accept(MediaType.TEXT_HTML).get(String.class));
	}
}
