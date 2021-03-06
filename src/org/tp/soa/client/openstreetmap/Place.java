package org.tp.soa.client.openstreetmap;

public class Place {
	
	private String searchPlace;
	private double longitude;
	private double latitude;
	private String place;

	/**
	 * Stock des informations sur un lieu
	 * @param searchPlace Le lieu recherché
	 */
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
	
	

}
