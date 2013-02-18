package org.tp.soa.client;

public class Offre {
	private String urlImage;
	private String nom;
	private String description;
	private String ville;
	
	
	
	public Offre(String urlImage, String nom, String description, String ville) {
		super();
		this.urlImage = urlImage;
		this.nom = nom;
		this.description = description;
		this.ville = ville;
	}

	public String getUrlImage() {
		return urlImage;
	}
	
	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	public String getNom() {
		return nom;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
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
}
