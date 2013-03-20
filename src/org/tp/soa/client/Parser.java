package org.tp.soa.client;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.tp.soa.client.flickr.FlickrApi;
import org.tp.soa.client.openstreetmap.MapView;
import org.tp.soa.client.openstreetmap.OpenStreetMapApi;

import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.UniformInterfaceException;

public class Parser 
{
	private FlickrApi flickr;
	private OpenStreetMapApi osm;
	private MapView map;
	
	public Parser(){
		String cleFlickr = "528ae2c27f6bc67b1376016e2d259bf1";
		String secretFlickr = "b087e446e6b2ac7e";
		try {
			this.flickr = new FlickrApi(cleFlickr, secretFlickr);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		this.osm = new OpenStreetMapApi();
		this.map = new MapView();
	}
	
	public static void main(String[] args)
	{
		Parser p = new Parser();
		ArrayList<Offre> offres = p.recupererOffres();
		for(Offre o:offres){
			System.out.println("====================");
			System.out.println("description : "+o.getDescription());
			System.out.println("ville : "+o.getVille());
			System.out.println("====================");
		}
	}
	
	public MapView getMap(){
		return this.map;
	}
	
	public ArrayList<Offre> recupererOffres()
	{
		Document document = null;
		Element racine;
		
		ArrayList<Offre> offres = new ArrayList<Offre>();
		
		//On crée une instance de SAXBuilder
		SAXBuilder sxb = new SAXBuilder();
		try
		{
			//On crée un nouveau document JDOM avec en argument le fichier XML
			//Le parsing est terminé ;)
			document = sxb.build(new File("Offres.xml"));
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		racine = document.getRootElement();
		List<Element> domOffres = racine.getChildren("status");
		Iterator<Element> i = domOffres.iterator();
		
		String ville = "";
		String descr = "";
		while(i.hasNext())
		{
			Element courant = i.next();
			if(courant.getChild("place")!=null && courant.getChild("place").getChild("name")!=null)
				ville = courant.getChild("place").getChild("name").getText();
			descr = courant.getChild("text").getText();
			
			Offre offre;
			try {
				offre = new Offre(this.flickr.getOneFromTags(ville), descr, ville, osm.getMoreInformations(ville));
				if(offre.getLatitude() != 0.0 && offre.getLongitude() != 0.0){
					this.map.addMarker(offre.getLatitude(), offre.getLongitude());
				}
				offres.add(offre);
			} catch (UniformInterfaceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClientHandlerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return offres;
	}

}

