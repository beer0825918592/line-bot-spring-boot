package com.iphayao.linebot;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriBuilder;







//throws ClientProtocolException, IOException
import com.sun.jersey.core.util.MultivaluedMapImpl;
import com.sun.jersey.json.impl.provider.entity.JSONRootElementProvider;


import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import org.apache.http.client.ClientProtocolException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.api.representation.Form;









public class Callrest {
    public void getCV(String Userid)throws ClientProtocolException, IOException {
    	 // Create a Logger 
        Logger logger 
            = Logger.getLogger( 
            		Callrest.class.getName()); 

       // "http://localhost:8082/rest/prsorderservice/v1/Post"
    	try {
    		System.out.println("Call Rest : Satrt ");
//    		  final String POST_PARAMS = "{\n" + "\"userId\": 101,\r\n" +
//    			        "    \"id\": 101,\r\n" +
//    			        "    \"title\": \"Test Title\",\r\n" +
//    			        "    \"body\": \"Test Body\"" + "\n}";
    		 //URL url = new URL("http://localhost:8082/rest/prsorderservice/v1/Post");
    		// https://petstore.swagger.io/v2/pet/findByStatus?status=available
    		
    		 URL obj = new URL("http://localhost:8082/rest/prsorderservice/v1/test");
    		 
    		// URL obj = new URL("http://localhost:8082/rest/prsorderservice/v1/Post");
    		    HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
    		    postConnection.setRequestMethod("POST");
    		  
    		   postConnection.setRequestProperty("Content-Type", "application/json");
    		   postConnection.setDoOutput(true);
    		   postConnection.setConnectTimeout(10000);
    		   // postConnection.setRequestProperty("OrderID","1");
    		   //////////////////////////////
//    		 URL obj = new URL("https://petstore.swagger.io/v2/pet/findByStatus?status=available");
//    		 HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
//    		 postConnection.setRequestMethod("GET");
//    		 postConnection.setRequestProperty("Content-Type", "application/json");
//    		 postConnection.setDoOutput(true);
//    		 postConnection.setConnectTimeout(10000);
    		   
    		   System.out.println("Call Rest : postConnection "+postConnection.getResponseCode());

    			if (postConnection.getResponseCode()!=200&&postConnection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
    				throw new RuntimeException("Failed : HTTP error code : "
    					+ postConnection.getResponseCode());
    			}

    			BufferedReader br = new BufferedReader(new InputStreamReader(
    					(postConnection.getInputStream())));

    			String output;
    			System.out.println("Output from Server .... \n");
    			while ((output = br.readLine()) != null) {
    				System.out.println(output);
    			}

    			postConnection.disconnect();
    		   
    		   
    		   
    		   
    		   
    		   //////////////////////////
    		   // postConnection.connect();
//    		    System.out.println("Call Rest : OS ");
//    		    OutputStream os = postConnection.getOutputStream();
//    		    os.write(POST_PARAMS.getBytes());
//    		    os.flush();
//    		    os.close();
//    		    int responseCode = postConnection.getResponseCode();
//    		    System.out.println("Call Rest : responseCode ");
//    		    System.out.println("POST Response Code :  " + responseCode);
//    		    System.out.println("POST Response Message : " + postConnection.getResponseMessage());
//    		    if (responseCode == HttpURLConnection.HTTP_CREATED) { //success
//    		        BufferedReader in = new BufferedReader(new InputStreamReader(
//    		            postConnection.getInputStream()));
//    		        String inputLine;
//    		        StringBuffer response = new StringBuffer();
//    		        while ((inputLine = in .readLine()) != null) {
//    		            response.append(inputLine);
//    		        } in .close();
//    		        // print result
//    		        System.out.println(response.toString());
//    		    } else {
//    		        System.out.println("POST NOT WORKED");
//    		    }
//       	  
       	  
    	}catch(Exception e) {
    		
    		System.err.println("Error "+e);
    		
    		
    	}
    	
    	//return "x";
    	
    } 
    	
    
}
