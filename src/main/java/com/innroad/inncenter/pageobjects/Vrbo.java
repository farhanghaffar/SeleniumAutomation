package com.innroad.inncenter.pageobjects;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.log4j.Logger;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.json.simple.parser.ParseException;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonschema.examples.Utils;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.innroad.inncenter.webelements.Elements_Vrbo;

import bsh.org.objectweb.asm.Type;
import groovy.json.JsonBuilder;


public class Vrbo {

	
	public static Logger accountsLogger = Logger.getLogger("Vrbo");
	
	public String getVrboAdvertisementID(WebDriver driver) {
		String advertisementID=null;
		Elements_Vrbo elements= new Elements_Vrbo(driver);
		advertisementID=elements.vrboAdvertiserId.getText().trim();
		return advertisementID;
		
	}
	
	public String getPropertyId(WebDriver driver,String propertyName) {
        String prop = "(//a[text()='"+propertyName+"']/following::td)[5]";
        String getPropertyId = driver.findElement(By.xpath(prop)).getText();
        return getPropertyId;
       
    }
	
	
  RestHighLevelClient client(String Url, int port, String host) {
        RestClientBuilder builder = RestClient.builder(
                new HttpHost(Url,port,host))
                .setRequestConfigCallback(
                        new RestClientBuilder.RequestConfigCallback() {
                            public RequestConfig.Builder customizeRequestConfig1(
                                    RequestConfig.Builder requestConfigBuilder) {
                                return requestConfigBuilder
                                        .setConnectTimeout(5000)
                                        .setSocketTimeout(60000);
                            }

							@Override
							public RequestConfig.Builder customizeRequestConfig(RequestConfig.Builder builder) {
							    return builder.setSocketTimeout(80000); // try to prevent SocketTimeoutException
							}
							
                        });

//		SearchRequest searchRequest = new SearchRequest();
//	    searchRequest.indices("level: Info AND tag: "+"innRoad.Services.Ota.External.HomeAway.CalendarApi" +" "+"AND context.QueueItemId"+": "+eventId);
	    SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//	    searchSourceBuilder.query(QueryBuilders.matchAllQuery());
//	    searchRequest.source(searchSourceBuilder);
	    Map<String, Object> map=null;
	     
	    try {
//	        SearchResponse searchResponse = null;
//	        searchResponse =client.search(searchRequest, RequestOptions.DEFAULT);
	  
//	            SearchHit[] searchHit = searchResponse.getHits().getHits();
//	            for (SearchHit hit : searchHit) {
//	                map = hit.getSourceAsMap();
//	                  System.out.println("map:"+Arrays.toString(map.entrySet().toArray()));
//	                    
//	
//	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
        return new RestHighLevelClient(builder);
    }
	public String getConnectionKibana(String url, int port, String host,String eventId) throws IOException {
		RestHighLevelClient client = client(url,port,host);	
		SearchRequest searchRequest = new SearchRequest();
		String stringpath= "level: Info AND tag: "+"innRoad.Services.Ota.External.HomeAway.CalendarApi" +" "+"AND context.QueueItemId"+": "+eventId;
	    SearchSourceBuilder builder = new SearchSourceBuilder()
	    		  .postFilter(QueryBuilders.queryStringQuery(stringpath));
	    		searchRequest.searchType(SearchType.DFS_QUERY_THEN_FETCH);
	    		searchRequest.source(builder);
	    		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
	    		String strResponse=response.toString();
	    		System.out.println(strResponse);
				return strResponse;

	}
	
	public String getParseJason(String responseJason) throws ParseException, JSONException {
        String result = "";
        String jsonString = responseJason;
        Object obj = new JSONParser().parse(jsonString);
        JSONObject obj1 = (JSONObject) obj;
        Map address = ((Map) obj1.get("hits"));
        Iterator<Map.Entry> itr1 = address.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            if (pair.getKey().equals("hits")) {
                JSONArray ss = new JSONArray();
                ss = (JSONArray) pair.getValue();
                System.out.println("ss: " + ss.toString());
                Iterator<JSONObject> iterator = ss.iterator();
                while (iterator.hasNext()) {
                    Object ob = iterator.next();
                    System.out.println("Debug: " + ob.toString());

 

                    Object obj3 = new JSONParser().parse(ob.toString());
                    JSONObject obj5 = (JSONObject) obj3;
                    Map address2 = ((Map) obj5.get("_source"));
                    Iterator<Map.Entry> itr2 = address2.entrySet().iterator();
                    while (itr2.hasNext()) {
                        Map.Entry pair2 = itr2.next();
                        if (pair2.getKey().equals("message")) {
                            result = pair2.getValue().toString();
                        }
                    }
                }

 

            }

 

        }
        return result;
    }
}
