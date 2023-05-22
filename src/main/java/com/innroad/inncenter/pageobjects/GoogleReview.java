package com.innroad.inncenter.pageobjects;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.JsonObject;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.specification.RequestSpecification;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import modelclass.*;

public class GoogleReview  {

	public Response response = null;
	private static ObjectReader reader;
    private static ObjectWriter writer;

	public static void main(String[] args) {
		GoogleReview googleReview = new GoogleReview();
		googleReview.GoogleToChallenge(googleReview.GetGoogleReview("", googleReview.getListOfMerchant("automate")));
		

	}

	public HashMap<String, List<Review>> setupWithToken(String basePath) {
		
		HashMap<String, List<Review>> listOfGoogle = new HashMap<>();
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		Request request = new Request.Builder().url("https://app.getdandy.com/gateway/" + basePath).get()
				.addHeader("Authorization", "Bearer 5317757393053b89b079159a2e0265aa3bf59378da0c377a6fb7eda7").build();
		try {
			response = client.newCall(request).execute();
			// System.out.println(response.code());
			if (response.code() == 200) {
				// System.out.println(response.body().string());
				JSONObject responseValue = new JSONObject(response.body().string());

				JSONArray jsonArray = responseValue.getJSONArray("listMerchants");
				
				for (int i = 0; i < jsonArray.length(); i++) {
					System.out.println("i:"+1);
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String companyName = jsonObject.getString("companyName");
					System.out.println("Company name: "+companyName);
					 request = new Request.Builder()
							  .url("https://app.getdandy.com/gateway/reviews/list?merchantName="+companyName+", MD&skip=0&take=1000000")
							  .get()
							  .addHeader("Authorization", "Bearer 5317757393053b89b079159a2e0265aa3bf59378da0c377a6fb7eda7")
							  .build();
							Response response = client.newCall(request).execute();
							
							if (response.code() == 200) {
								Response response1 = response;
								 //System.out.println(response1.body().string());
								
								List<Review> listOfReviews = 	Converter.fromJsonString(response.body().string());
								listOfGoogle.put(companyName, listOfReviews);
																
							}
				}

			} else {
				// api failed
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return listOfGoogle;
	}

	
	public Response getListOfMerchant(String basePath) {
		
		HashMap<String, List<Review>> listOfGoogle = new HashMap<>();
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		RequestBody body = RequestBody.create(mediaType, "");
		Request request = new Request.Builder().url("https://app.getdandy.com/gateway/" + basePath).get()
				.addHeader("Authorization", "Bearer 5317757393053b89b079159a2e0265aa3bf59378da0c377a6fb7eda7").build();
		try {
			response = client.newCall(request).execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	
	public HashMap<String, List<Review>> GetGoogleReview(String basePath, Response response) {
		
		HashMap<String, List<Review>> listOfGoogle = new HashMap<>();
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		MediaType mediaType = MediaType.parse("text/plain");
		try {
			if (response.code() == 200) {
				JSONObject responseValue = new JSONObject(response.body().string());

				JSONArray jsonArray = responseValue.getJSONArray("listMerchants");
				
				for (int i = 0; i < jsonArray.length() && i<10; i++) {
					System.out.println("i:"+i);
					JSONObject jsonObject = jsonArray.getJSONObject(i);
					String companyName = jsonObject.getString("companyName");
					Request request = new Request.Builder()
							  .url("https://app.getdandy.com/gateway/reviews/list?merchantName="+companyName+"&skip=0&take=1000000")
							  .get()
							  .addHeader("Authorization", "Bearer 5317757393053b89b079159a2e0265aa3bf59378da0c377a6fb7eda7")
							  .build();
							Response Googleresponse = client.newCall(request).execute();
							
							if (response.code() == 200) {
								String body = Googleresponse.body().string();
								List<Review> listOfReviews = 	Converter.fromJsonString(body.toString());
								listOfGoogle.put(companyName, listOfReviews);
								
																
							}
				}

			} else {
				// api failed
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
		return listOfGoogle;
	}
	
	public void GoogleToChallenge(HashMap<String, List<Review>> GetGoogleReview, WebDriver driver) {
		
		for (Entry<String, List<Review>> entry : GetGoogleReview.entrySet()) {
			System.out.println("Key: "+entry.getKey());
			System.out.println("Value: "+entry.getValue());
			driver.get("");
			for (Review getReview : entry.getValue()) {
				
				System.out.println(getReview.getLocation().getPostalCode());
			}
			
			
			
			
		}
		
	}


}
