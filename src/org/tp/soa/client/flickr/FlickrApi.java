package org.tp.soa.client.flickr;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import sun.net.www.protocol.http.HttpURLConnection;

public class FlickrApi {
	
	String cle;
	String secret;
	URL wsdlURL;
	
	public FlickrApi(String cle, String secret) throws MalformedURLException{
		this.cle = cle;
		this.secret = secret;
		this.wsdlURL = new URL("http://api.flickr.com/services/soap/");
	}
	
	/**
	 * Enveloppe de requête Soap
	 * @param tags les tags demandés
	 * @return
	 */
	private String getSoapXMLRequestTag(String tags){
		String request =
			"<s:Envelope " +
			"xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" " +
			"xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" " +
			"xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"> " +
				"<s:Body> "  +
					"<x:FlickrRequest xmlns:x=\"urn:flickr\"> " +
						"<method>flickr.photos.search</method> " +
						"<api_key>"+this.cle+"</api_key>" +
						"<secret>"+this.secret+"</secret>" +
						"<tags>" + tags + "</tags>" +
						"<per_page>1</per_page>" +
					"</x:FlickrRequest> " +
				"</s:Body> " +
			"</s:Envelope> ";
		return request;
	}
	
	/**
	 * Récupère une image à partir de tags
	 * @param tags Les tags recherchés
	 * @return L'url de l'image
	 */
	public String getOneFromTags(String tags){
		String request = getSoapXMLRequestTag(tags);
		return getReponse(request);
	}
	
	/**
	 * Transforme une enveloppe de réponse Soap Flickr en URL flickr
	 * @param stringXml L'enveloppe de réponse Soap
	 * @return L'url de l'image
	 * @throws JDOMException
	 * @throws IOException
	 */
	private String toFlickrUrl(String stringXml) throws JDOMException, IOException{
		
		//Première passe pour récupérer le XML contenu dans l'enveloppe
		InputStream inEnv = new ByteArrayInputStream(stringXml.getBytes());
		SAXBuilder sxbEnv = new SAXBuilder();
		Document docEnv = sxbEnv.build(inEnv);
    	Element racineEnv = docEnv.getRootElement();
    	
    	// Seconde passe pour exploiter le XML de l'enveloppe
    	InputStream inXML = new ByteArrayInputStream(racineEnv.getValue().getBytes());
		SAXBuilder sxbXML = new SAXBuilder();
		Document docXML = sxbXML.build(inXML);
    	Element racine = docXML.getRootElement();
    	Element photo = racine.getChild("photo");
    	
    	
    	// Reconstitution de L'url
    	return "http://farm"+photo.getAttributeValue("farm")+".staticflickr.com/"+photo.getAttributeValue("server")+"/"+photo.getAttributeValue("id")+"_"+photo.getAttributeValue("secret")+".jpg";
	}
	
	/**
	 * Récupère une url à partir d'une requête
	 * @param request
	 * @return
	 */
	private String getReponse(String request){
		 // Envelope SOAP
		  String response = "";
		 
		  try 
		  {
			   // Connexion
			   HttpURLConnection connection = (HttpURLConnection) this.wsdlURL.openConnection();
			   connection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			   connection.setRequestMethod("POST");
			   connection.setDoOutput(true);
			   connection.setDoInput(true);
			   
			   // Requête + récupération de la réponse
			   OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
			   osw.write(request);
			   osw.flush();
			   osw.close();
			   InputStream is;
			   if (connection.getResponseCode() >= 400) 
			   {
				   is = connection.getErrorStream();
			   } 
			   else 
			   {
				   is = connection.getInputStream();
			   }
			   InputStreamReader isr = new InputStreamReader(is);
			   BufferedReader br = new java.io.BufferedReader(isr);
			   String line = null;
			   while ((line = br.readLine()) != null) 
			   {
				   response = response.concat(line);
			   }
			   return this.toFlickrUrl(response);
		  } 
		  catch (Exception e) 
		  {
			   return null;
		  }
	}
}
