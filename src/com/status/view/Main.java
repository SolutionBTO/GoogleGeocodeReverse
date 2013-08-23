/**
 * 
 */
package com.status.view;

import com.google.gson.JsonObject;
import com.status.controle.GeocodeReverse;
import com.status.modelo.GeocodeResponse;

/**
 * @author Roberto Silva
 * criado em: 13/08/2013
 */
public class Main {

	/** 
     * Distance in kilometers between two geo points. 
     * 
     * Example: 
     * 
     * double lat1 = -34.87001758635342; 
     * double lon1 = -56.16755962371826; 
     * double lat2 = -34.88487484011935; 
     * double lon2 = -56.1661434173584; 
     * 
     * double distance = distFrom(lat1, lon1, lat2, lon2); 
     * 
     * @param lat1 
     * @param lng1 
     * @param lat2 
     * @param lng2 
     * @return 
     */  
    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {  
        //double earthRadius = 3958.75;//miles  
        double earthRadius = 6371;//kilometers  
        double dLat = Math.toRadians(lat2 - lat1);  
        double dLng = Math.toRadians(lng2 - lng1);  
        double sindLat = Math.sin(dLat / 2);  
        double sindLng = Math.sin(dLng / 2);  
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)  
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));  
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));  
        double dist = earthRadius * c;  
  
        return dist;  
    } 
	
	
	/**
	 * metodo principal onde é executado a aplicação
	 * @param args
	 */
	public static void main(String[] args) {
		
		String local1="Av Turucuvi,100 São Paulo-SP";
		String local2="Av Agua Fria,1500 São Paulo-SP";
		
		GeocodeReverse geocodeReverse=new GeocodeReverse();
		
		JsonObject jsonObject=geocodeReverse.getJSON_LocationInfo(local1);
		GeocodeResponse response=geocodeReverse.getCordenadas(jsonObject);
		double lat1=response.getLat();
		double lng1=response.getLng();
		
		System.out.println(local1+", lat="+lat1+"; lng="+lng1);
		
		System.out.println();
		jsonObject=geocodeReverse.getJSON_LocationInfo(local2);
		response=geocodeReverse.getCordenadas(jsonObject);
		double lat2=response.getLat();;
		double lng2=response.getLng();
		
		System.out.println(local2+", lat="+lat2+"; lng="+lng2);
		
		System.out.println();
		
		System.out.println("A distância linear entre os ponto é: "+distFrom(lat1, lng1, lat2, lng2));
	}

}
