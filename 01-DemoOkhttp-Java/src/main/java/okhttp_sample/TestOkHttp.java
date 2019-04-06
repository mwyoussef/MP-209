package okhttp_sample;

import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TestOkHttp {

	public static void main(String[] args) throws IOException, ParseException {
		// TODO Auto-generated method stub
		
		OkHttpClient client = new OkHttpClient();
		
		String token = "" ;


		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\n\t\"grant_type\":\"password\",\n\t\"username\": \"etudiant@isi.utm.tn\",\n\t\"scope\": \"*\", \n\t\"client_id\": \"SJGZDWXOPLJZLBDQGACCAGAVWSHORHJK\", \n\t\"client_secret\": \"6734914665b5258c7a2eb01077382585\" ,\n\t\t\"password\": \"pm2019\"\n\n}");
		Request request = new Request.Builder()
		  .url("http://isiforge.tn/isi/oauth2/token")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .build();

		Response response = client.newCall(request).execute();
		
		if (response.isSuccessful() ) {
			
			String resp =  response.body().string() ;
			System.out.println(  "reponse as a string : " + resp);			
			
			
			JSONParser parser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) parser.parse( resp );
			
	        System.out.println("Full jsonObject :" + jsonObject);
	        System.out.println("access_token :" + jsonObject.get("access_token") );
	        System.out.println("refresh_token :" + jsonObject.get("refresh_token") );
	        System.out.println("expires_in :" + jsonObject.get("expires_in") );
	        System.out.println("token_type :" + jsonObject.get("token_type") );
	        
	        token = (String) jsonObject.get("access_token");
	        
			
		}
		else {
			System.out.println("reponse KO");
		}
		
		
		System.out.println("--------------------------------------");
		
		
		Request request2 = new Request.Builder()
				  .url("http://process.isiforge.tn/api/1.0/isi/case/start-cases")
				  .get()
				  .addHeader("Authorization", "Bearer " + token )
				  .build();

		Response response2 = client.newCall(request2).execute();
				
		if (response2.isSuccessful() ) {
			
			String resp2 =  response2.body().string() ;
			System.out.println(  "reponse2 as a string : " + resp2);			
			
			JSONParser parser = new JSONParser();
			
			
			JSONArray array = (JSONArray) parser.parse( resp2 );
			
	        System.out.println("Full array :" + array);
	        
	     // looping through All Contacts
            for (int i = 0; i < array.size(); i++) {
            	
            	System.out.println("----- Processus N" + i );
            	
            	JSONObject processJSONObject = (JSONObject) array.get(i) ;
            	
            	System.out.println("pro_uid :" + processJSONObject.get("pro_uid") );
            	System.out.println("pro_title :" + processJSONObject.get("pro_title") );
            	System.out.println("tas_uid :" + processJSONObject.get("tas_uid") );
    	        
            	
            	
            }
	        
	        
	        
	        
			
		}
		else {
			System.out.println("reponse 2 KO");
		}
				
		
		
		
		
		

	}

}
