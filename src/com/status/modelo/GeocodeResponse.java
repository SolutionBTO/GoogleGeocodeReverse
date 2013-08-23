/**
 * 
 */
package com.status.modelo;

/**
 * classe modelo, Java Bean guarda os valores do request ao serviço google map
 * @author Roberto Silva
 * criado em: 13/08/2013
 */
public class GeocodeResponse {
	
    private String status;
    private double lat;
    private double lng;
    
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the lat
	 */
	public double getLat() {
		return lat;
	}
	/**
	 * @param lat the lat to set
	 */
	public void setLat(double lat) {
		this.lat = lat;
	}
	/**
	 * @return the lng
	 */
	public double getLng() {
		return lng;
	}
	/**
	 * @param lng the lng to set
	 */
	public void setLng(double lng) {
		this.lng = lng;
	}
    
    
}
