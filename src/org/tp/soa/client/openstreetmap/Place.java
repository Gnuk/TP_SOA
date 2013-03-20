package org.tp.soa.client.openstreetmap;

public class Place {
	
	private String searchPlace;
	private double longitude;
	private double latitude;
	private String place;
	private String urlImage;

	public Place(String searchPlace){
		this.searchPlace = searchPlace;
		this.place = null;
	}

	public String getSearchPlace() {
		return searchPlace;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getUrlImage() {
		return urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}
	
	

}
