package org.tp.soa.client.flickr;

import javax.xml.ws.WebServiceRef;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.tp.soa.client.openstreetmap.Place;

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
	
	private String getSoapXMLRequestTag(String tag){
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
						"<tags>" + tag + "</tags>" +
						"<per_page>1</per_page>" +
					"</x:FlickrRequest> " +
				"</s:Body> " +
			"</s:Envelope> ";
		return request;
	}
	
	public String getOneFromTags(String tags){
		String request = getSoapXMLRequestTag(tags);
		return getReponse(request);
	}
	
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
