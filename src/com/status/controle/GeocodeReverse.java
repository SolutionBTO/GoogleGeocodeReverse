package com.status.controle;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.status.modelo.GeocodeResponse;

/**
 * classe de controle, faz chamada HTTP ao servidor GOOGLE e trata o JSON de resposta
 * @author Roberto Silva
 * criado em: 23/08/2013
 */
public class GeocodeReverse {
    
	/**
     * consulta o servidor web da google para obter Latitude e Longitude atraves do parametro enviado 
     * @param lugar_destino
     * @return
     */
	public  JsonObject getJSON_LocationInfo(String lugar_destino) {
		final int HTTP_TIMEOUT = 10000;
    //exemplo:
	//http://maps.googleapis.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA&sensor=false	
		lugar_destino=lugar_destino.replaceAll(" ", "%20");
		
		HttpGet httpGet = new HttpGet(
				"http://maps.google.com/maps/api/geocode/json?" +
				"address=" + lugar_destino+
				"&sensor=false");
		HttpClient client = new DefaultHttpClient();
		
		//especificando o tempo da solicitação
		HttpParams httpParamns = client.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParamns, HTTP_TIMEOUT);
		HttpConnectionParams.setSoTimeout(httpParamns, HTTP_TIMEOUT);
		//ConnManagerParams.setTimeout(httpParamns, HTTP_TIMEOUT);
		
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			//solicitação
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			//liberar conexão 
			client.getConnectionManager().shutdown();
		}

		JsonObject jsonObject = new JsonObject();
		JsonParser jsonParser=new JsonParser();
		try {
			jsonObject = (JsonObject)jsonParser.parse(stringBuilder.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jsonObject;
	}

	/**
	 * Faz uma conversão de JSONObject p/ GeocodeResponse - Bean
	 * @param jsonObject
	 * @return {@link GeocodeResponse} - JavaBean
	 */
	public GeocodeResponse getCordenadas(JsonObject jsonObject) {
		GeocodeResponse response=new GeocodeResponse();
		try {
			response.setStatus(jsonObject.get("status").getAsString());
			
			if(!response.getStatus().equals("OK")){	
				throw new Exception("Não foi possível localizar o destinatário.");
			}
			
			response.setLat(((JsonArray)jsonObject.get("results")).get(0).
					getAsJsonObject().get("geometry").
					getAsJsonObject().get("location").
					getAsJsonObject().get("lat").getAsDouble());
			
			response.setLng( ((JsonArray)jsonObject.get("results")).get(0).
					getAsJsonObject().get("geometry").
					getAsJsonObject().get("location").
					getAsJsonObject().get("lng").getAsDouble());
					

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}
	
}
