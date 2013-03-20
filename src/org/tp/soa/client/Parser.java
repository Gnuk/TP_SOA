package org.tp.soa.client;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class Parser 
{
	
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
			offres.add(new Offre("", descr, ville));
		}		
		return offres;
	}

}

