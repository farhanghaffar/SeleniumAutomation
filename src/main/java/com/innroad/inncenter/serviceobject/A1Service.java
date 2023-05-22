package com.innroad.inncenter.serviceobject;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.*;
import com.innroad.inncenter.endpoints.OTAEndPoints;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class A1Service {

	public static Logger log = Logger.getLogger("A1Service");

	public void verifyStatus_GET() {
        given().get(OTAEndPoints.GET_ALL_USER_LIST).then().statusCode(200);
    }
	
	public void verifyBodyData_GET() {
		given().get(OTAEndPoints.GET_ALL_USER_LIST).then().body("data.id[1]", equalTo(8)).body("data.first_name",
				hasItems("Michael", "Lindsay"));
	}

	public void getRequest() {
		Response response = RestAssured.get(OTAEndPoints.GET_ALL_USER_LIST);

		System.out.println(response.asPrettyString());
		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusCode());
		System.out.println(response.getStatusLine());

		assertEquals(response.getStatusCode(), 200);
		System.out.println("Successfully Verified Status Code");
	}
	

	public void verifyPOST(String name, String job) {

		given().
			body(genrateJsonObject(name, job).toJSONString()).
		when().
			post(OTAEndPoints.POST_USER).
		then().
			statusCode(201).
			log().all();
		
		System.out.println("Request Posted");
	}
	
	public JSONObject genrateJsonObject(String name, String job) {
		JSONObject request = new JSONObject();
		
		request.put("name", name );
		request.put("name", "Automation Eng");
		
		return request;
	}
	
	public void verifyPUT(String name, String job) {
		given().
			body(genrateJsonObject(name, job).toJSONString()).
		when().
			put(OTAEndPoints.POST_USER).
		then().
			statusCode(200).
			log().all();
		
		System.out.println("Request Posted");
	}
	
	public void verifyDELETE() {
		when().
			delete(OTAEndPoints.DELETE_USER).
		then().
			statusCode(204).
			log().all();
	}
}
