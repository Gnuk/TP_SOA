package org.tp.soa.client;

import org.tp.soa.client.openstreetmap.Place;

public class Offre {
	private String urlImage;
	private String description;
	private String ville;
	private Place place;
	
	
	
	public Offre(String urlImage, String description, String ville) {
		super();
		this.urlImage = urlImage;
		this.description = description;
		this.ville = ville;
	}
	public Offre(String urlImage, String description, String ville, Place place) {
		super();
		this.urlImage = urlImage;
		this.description = description;
		this.ville = ville;
		this.place = place;
	}

	public String getUrlImage() {
		return urlImage;
	}
	
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getVille() {
		return ville;
	}
	
	public void setVille(String ville) {
		this.ville = ville;
	}

	public double getLatitude() {
		return this.place.getLatitude();
	}

	public double getLongitude() {
		return this.place.getLongitude();
	}
	
	public Place getPlace(){
		return this.place;
	}
	
	public String getAddress(){
		return this.place.getPlace();
	}
}
