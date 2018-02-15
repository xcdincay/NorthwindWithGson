package com.ittr.services.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class Connection {

	 public HttpResponse getResponse(String url, DefaultHttpClient httpClient) throws IOException {
	        try {

	            HttpGet httpGet = new HttpGet(url);
	            httpGet.addHeader("accept", "application/json");
	            HttpResponse response = httpClient.execute(httpGet);
	            return response;
	        } catch (IOException e) {
	            throw e;
	        }
	    }

	    public String readData(HttpResponse response) throws Exception {
	        BufferedReader reader = null;
	        try {
	            reader = new BufferedReader(new InputStreamReader(
	                    response.getEntity().getContent()));
	            StringBuffer data = new StringBuffer();
	            char[] dataLength = new char[1024];
	            int read;
	            while (((read = reader.read(dataLength)) != -1)) {
	                data.append(dataLength, 0, read);
	            }
	            return data.toString();
	        } finally {
	            if (reader != null)
	                reader.close();
	        }
	    }
}
