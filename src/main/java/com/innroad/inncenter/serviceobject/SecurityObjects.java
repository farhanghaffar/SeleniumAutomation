package com.innroad.inncenter.serviceobject;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;

import com.innroad.inncenter.endpoints.VrboEndPoints;
import com.innroad.inncenter.utils.Utility;

import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.http.Headers;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;


public class SecurityObjects {
	
	public static Logger log = Logger.getLogger("SecurityObjects");
	
	
	public Response postRequestusingauthAndCoockie( String uri, String authorization, String coockie,String requestBody) {
		Response response = given()
				.header("Content-Type", "application/json").header("authorization", authorization).header("Cookie", coockie).body(requestBody).when().post(uri);
	/*	Response response = given()
				.headers(headers).body(requestBody).when().post(uri);*/
		return response;
	}
	
	public HashMap<String, String> postRequestsForLogin( String uri, String requestBody) {
		HashMap<String, String> data = new HashMap<String, String>();

		Response response = given()
				.header("Content-Type", "application/json").body(requestBody).when().post(uri);

		JSONObject root = new JSONObject(response.asString());
		JSONArray jsonArray = root.getJSONArray("ObjList");		
		JSONObject firstArray = jsonArray.getJSONObject(0);
		int clientID= firstArray.getInt("ClientID");
		Cookies coockie= response.getDetailedCookies();
		data.put("Session", coockie.get("ASP.NET_SessionId").toString());
		data.put("ClientID", String.valueOf(clientID));
		log.info(response.asString());
		return data;
	}
	
	
	public HashMap<String, String> getToggleToken(String uri,String session){
		HashMap<String, String> data = new HashMap<String, String>();
		Response response =given().header("Cookie", session).header("Accept", "application/json, text/javascript, */*; q=0.01").when().get(uri);		
		log.info(response.statusCode());
		log.info(response.asString());
		log.info(response.jsonPath().getString("token"));
		data.put("ir.pc.toggle", response.jsonPath().getString("token"));
		return data;
		
	}
	public HashMap<String, String> getEntitlementAndSecurityAccessMapping(String uri, String requestBody, String session) throws ParseException {
		HashMap<String, String> data = new HashMap<String, String>();

		Response response = given()
				.header("Content-Type", "application/json").header("Cookie", session).body(requestBody).when().post(uri);
			
		log.info(response.asString());
		JSONObject root = new JSONObject(response.asString());
		JSONArray jsonArray = root.getJSONArray("ObjList");		
		JSONObject firstArray = jsonArray.getJSONObject(0);
		String token= firstArray.getString("AuthToken");
		log.info(token);
		data.put("token", token);	
		return data;
	}
	

	public HashMap<String, Response> getMethodWitoutBody(String uri, Headers headers) {
		HashMap<String, Response> data = new HashMap<String, Response>();
		
		Response response =given().headers(headers).when().get(uri);
		data.put("Body", response);		
		log.info(response.statusCode());
		return data;
	}
	public void getMethodWithBody(String token, String session, String uri,String requestBody) {
		Response response = given().header("Authorization", token)
				.header("Content-Type", "application/json").header("Cookie", session).body(requestBody).when().get(uri)
				.then()
				.contentType(ContentType.JSON).extract().response();;
		log.info(response.asString());		
	}
	
	public Response createPaymentMethod(String token, String session, String uri,String requestBody) {
		Response response = given().header("authorization", token)
				.header("Content-Type", "application/json").header("Cookie", session).body(requestBody).when().post(uri);
		log.info(response.asString());	
		log.info(response.getStatusCode());
		return response;	
		
	}
	
	
	public  HashMap<String, String> getbillingAddressfromJasonObject(Response response) {
		
		HashMap<String, String> data = new HashMap<String, String>();
		JSONObject root = new JSONObject(response.asString());
		JSONObject contact=root.getJSONObject("booking").getJSONObject("primaryRoom").getJSONObject("contact");
		if(Utility.validateString(contact.getString("address1"))) {
			data.put("address1", contact.getString("address1"));
		}
		if(Utility.validateString(contact.getString("address2"))) {
			data.put("address2", contact.getString("address2"));
		}
		if(Utility.validateString(contact.getString("address3"))) {
			data.put("address3", contact.getString("address3"));
		}
		if(Utility.validateString(String.valueOf(contact.getInt("territoryId")))) {
			data.put("territoryId", String.valueOf(contact.getInt("territoryId")));
		}
		data.put("city", contact.getString("city"));
		data.put("postalCode", contact.getString("postalCode"));
		data.put("countryId", String.valueOf(contact.getInt("countryId")));
		JSONObject billingAddress = new JSONObject(data);
		log.info(billingAddress);
		data.put("billingAddress", billingAddress.toString());
		return data;
	}
	public HashMap<String, String> getDataMasterFolioApi(Response response){
		HashMap<String, String> data = new HashMap<String, String>();
		JSONObject root = new JSONObject(response.asString());
		JSONArray jsonArray = root.getJSONArray("folios");		
		JSONObject firstArray = jsonArray.getJSONObject(0).getJSONArray("items").getJSONObject(0);
		data.put("ledgerAccountId", String.valueOf(firstArray.getInt("ledgerAccountId")));
		data.put("folioId", String.valueOf(firstArray.getInt("folioId")));
		data.put("referenceTypeId", String.valueOf(firstArray.getInt("referenceTypeId")));
		log.info(data);
		return data;
	}
	
	public HashMap<String, String>  getPropertyTimeZone(Response response) {
		HashMap<String, String> data = new HashMap<String, String>();
		JSONObject root = new JSONObject(response.asString());
			JSONArray jsonArray = root.getJSONArray("ObjList");		
			JSONObject firstArray = jsonArray.getJSONArray(0).getJSONObject(1);
		data.put("PropertyId", String.valueOf(firstArray.getInt("PropertyId")));
		return data;
		
	}
}
