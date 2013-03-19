package org.tp.soa.client.flickr;

import javax.xml.ws.WebServiceRef;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.transform.Source;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;

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
	
	private String getSoapXMLRequestLatLon(double lat, double lon){
		String request =
			"<s:Envelope " +
			"xmlns:s=\"http://www.w3.org/2003/05/soap-envelope\" " +
			"xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" " +
			"xmlns:xsd=\"http://www.w3.org/1999/XMLSchema\"> " +
				"<s:Body> "  +
					"<x:FlickrRequest xmlns:x=\"urn:flickr\"> " +
						"<method>flickr.photos.search</method> " +
						"<name>value</name> " +
						"<api_key>"+this.cle+"</api_key>" +
						"<secret>"+this.secret+"</secret>" +
						"<lat>" + lat + " </lat>" +
						"<lon>" + lon + "</lon>" +
						"<accuracy>16</accuracy>" +
						"<per_page>1</per_page>" +
					"</x:FlickrRequest> " +
				"</s:Body> " +
			"</s:Envelope> ";
		return request;
	}
	
	public String getImagesFromLatLon(double lat, double lon){
		 // Envelope SOAP
		  String request = getSoapXMLRequestLatLon(lat, lon);
		  
		  String response = "";
		 
		  try 
		  {
			   String soapXml = request;
			   
			   // Définir la connexion
			   HttpURLConnection conn = (HttpURLConnection) this.wsdlURL.openConnection();
			   conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			   conn.setRequestProperty("SOAPAction", "");
			   conn.setRequestProperty("Content-Length", "200000");
			   conn.setRequestProperty("Host", "127.0.0.1");
			   conn.setRequestMethod("POST");
			   conn.setDoOutput(true);
			   conn.setDoInput(true);
			   
			   // Requête
			   OutputStream os = conn.getOutputStream();
			   OutputStreamWriter osw = new OutputStreamWriter(os);
			   osw.write(soapXml);
			   osw.flush();
			   osw.close();
			   InputStream is;
			   if (conn.getResponseCode() >= 400) 
			   {
				   is = conn.getErrorStream();
			   } 
			   else 
			   {
				   is = conn.getInputStream();
			   }
		   
			   // Reponse
			   InputStreamReader isr = new InputStreamReader(is);
			   BufferedReader br = new java.io.BufferedReader(isr);
			   String line = null;
			   while ((line = br.readLine()) != null) 
			   {
			    response = response.concat(line);
			   }
		  } 
		  catch (Exception e) 
		  {
			   e.printStackTrace();
		  }
		  return response;
	}
}
