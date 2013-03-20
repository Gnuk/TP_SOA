package org.tp.soa.client;

public class Offre {
	private String urlImage;
	private String description;
	private String ville;
	
	
	
	public Offre(String urlImage, String description, String ville) {
		super();
		this.urlImage = urlImage;
		this.description = description;
		this.ville = ville;
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
}
