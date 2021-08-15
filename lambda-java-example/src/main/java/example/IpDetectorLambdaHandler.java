package example;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.net.InetAddress; 

import com.amazonaws.services.lambda.runtime.RequestStreamHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class IpDetectorLambdaHandler implements RequestStreamHandler {

	JSONParser parser = new JSONParser();
	
//	@Override
	public void handleRequest(InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
		LambdaLogger logger = context.getLogger();
		logger.log("Loading Java Lambda handler of ProxyWithStream");
	       
	    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	    JSONObject responseJson = new JSONObject();
	    String responseCode = "200";
	    JSONObject event = null;
	    String yourIpStr = null;
	    String xForwardedFor = null;
	    String origin = null;
	    String remoteAddress = null;
	    String postfix;
	    int index = 0;
	   
	   try {
	       event = (JSONObject)parser.parse(reader);
	       if(event.get("headers") != null) {
	    	   JSONObject headers = (JSONObject)event.get("headers"); 
		       if(headers.get("X-Forwarded-For") != null) {
		    	   String ipList = (String)headers.get("X-Forwarded-For");
		    	   xForwardedFor = ipList;
		    	   String [] ipArray = ipList.split(",");
		    	   if(ipArray != null && ipArray.length > 0) {
		    		   yourIpStr = ipArray[0]; 
		    	   }
		       }
		       if(headers.get("origin") != null) {
		    	   origin = (String)headers.get("origin");
		    	   String temp = "";
		    	   
		    	   if(origin.toLowerCase().contains("https")) {
		    		   temp = origin.substring(8);
		    		   origin = temp;
		    		   postfix = ":443";
		    	   } else {
		    		   temp = origin.substring(7);
		    		   origin = temp;
		    		   postfix = ":80";
		    	   }
		    	   InetAddress[] addresses = InetAddress.getAllByName(origin);
		    	   for(int i = 0; i < addresses.length; i++) {
		    		   remoteAddress = addresses[i].getHostAddress() + postfix;
		    		   if(i != addresses.length - 1) {
		    			   remoteAddress = remoteAddress + ", ";
		    		   }
		    	   }
		       }
	       }
	   } catch(Exception pex) {
		   responseJson.put("statusCode", "400");
		   responseJson.put("exception", pex);
	   }
	   JSONObject responseBody = new JSONObject();
	   responseBody.put("input", event.toJSONString());
	   responseBody.put("yourIp", yourIpStr);
	   responseBody.put("xForwardedFor", xForwardedFor);
	   responseBody.put("origin", origin);
	   responseBody.put("remoteAddress", remoteAddress);
	   responseBody.put("index", index);
	   
	   JSONObject headerJson = new JSONObject();
	   headerJson.put("x-custom-header", "Authorization");
	   headerJson.put("Access-Control-Allow-Origin", "*");
	   headerJson.put("Access-Control-Allow-Methods", "GET, OPTIONS");
	   headerJson.put("Access-Control-Allow-Headers", "Content-Type,X-Amz-Date,Authorization,X-Api-Key,X-Amz-Security-Token");
	
	   responseJson.put("isBase64Encoded", false);
	   responseJson.put("statusCode", responseCode);
	   responseJson.put("headers", headerJson);
	   responseJson.put("body", responseBody.toString());  
	
	   OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
	   writer.write(responseJson.toJSONString());  
	   writer.close();
//	   logger.log("response: " + responseJson.toJSONString());
   }	   
}
