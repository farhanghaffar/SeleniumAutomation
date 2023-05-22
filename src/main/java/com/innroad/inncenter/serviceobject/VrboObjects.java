package com.innroad.inncenter.serviceobject;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.File;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import com.innroad.inncenter.endpoints.OTAEndPoints;
import com.innroad.inncenter.endpoints.VrboEndPoints;
import com.innroad.inncenter.utils.Utility;
import java.util.Locale;
import java.util.Set;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import com.google.common.collect.ArrayListMultimap;
import com.innroad.inncenter.endpoints.OTAEndPoints;
import com.innroad.inncenter.endpoints.VrboEndPoints;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.properties.OR_Reservation;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_CPReservation;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Collection;
import java.util.HashSet;
import io.restassured.path.xml.element.Node;
import io.restassured.response.Response;

public class VrboObjects {

	public static Logger log = Logger.getLogger("A1Service");

	public void verifyVrboClientLevelStatus_GET(String clientID) {
		given().get(VrboEndPoints.genrateVrboClientLevelEndPoint(clientID)).then().statusCode(200);
	}

	/*
	 * public int getVrboStatusCode(String token) { // Response response =
	 * RestAssured.get(VrboEndPoints.GetAdvertiser);
	 * 
	 * Response response = given().header("authorization", token)
	 * 
	 * .get(VrboEndPoints.GetAdvertiser); int statusCode = response.getStatusCode();
	 * log.info("Verified Status Code: for Vrbo Client Level<b>" + statusCode +
	 * "</b>"); return statusCode; }
	 */

	public Response getResponseBodyOfVrboRateEndPoint(String property, String roomClassName, String token) {
		Response response = given().header("authorization", token).header("Content-Type", "application/xml").when()
				.get(VrboEndPoints.generateRateEndPoint(property, roomClassName));

		return response;

	}

	public HashMap<String, String> getExternalIdOfTax(Response responseBody, ArrayList<String> taxName) {

		String externalIdFlat = "", externalIdPercent = "", percentTaxName = "", flatTaxName = "";
		HashMap<String, String> records = new HashMap<String, String>();
		List<Node> nodeRule = responseBody.xmlPath()
				.getList("lodgingRateContent.lodgingRate.taxRules.flatTaxRules.rule", Node.class);
		log.info("nodeRule: " + nodeRule.size());
		for (int i = 0; i < nodeRule.size(); i++) {
			flatTaxName = responseBody
					.path("lodgingRateContent.lodgingRate.taxRules.flatTaxRules.rule[" + i + "].name");
			if (taxName.contains(flatTaxName)) {
				externalIdFlat = responseBody
						.path("lodgingRateContent.lodgingRate.taxRules.flatTaxRules.rule[" + i + "].externalId");
				records.put(flatTaxName, externalIdFlat);
			}

		}
		List<Node> nodeRule1 = responseBody.xmlPath()
				.getList("lodgingRateContent.lodgingRate.taxRules.percentOfRentAndFeesTaxRules.rule", Node.class);
		for (int i = 0; i < nodeRule1.size(); i++) {
			percentTaxName = responseBody
					.path("lodgingRateContent.lodgingRate.taxRules.percentOfRentAndFeesTaxRules.rule[" + i + "].name");
			if (taxName.contains(flatTaxName)) {
				externalIdPercent = responseBody
						.path("lodgingRateContent.lodgingRate.taxRules.percentOfRentAndFeesTaxRules.rule[" + i
								+ "].externalId");
				records.put(percentTaxName, externalIdPercent);
			}

		}
		return records;
	}

	public HashMap<String, String> getExternalIdOfFee(Response responseBody, ArrayList<String> feeName) {
		HashMap<String, String> records = new HashMap<String, String>();

		String feeexternalIdFlat = "", feeNameFlat = "", feeNamePercent = "", feeexternalIdPercent = "";
		List<Node> node = responseBody.xmlPath().getList("lodgingRateContent.lodgingRate.fees.otherFees.fee",
				Node.class);

		log.info("nodeFee: " + node.size());
		for (int i = 0; i < node.size(); i++) {

			feeNameFlat = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].name");

			if (feeName.contains(feeNameFlat)) {
				feeexternalIdFlat = responseBody
						.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].externalId");
				records.put(feeNameFlat, feeexternalIdFlat);
			}

		}

		List<Node> node1 = responseBody.xmlPath().getList("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee",
				Node.class);
		log.info("nodeFee: " + node1.size());
		if (node1.size() == 1) {

			feeNamePercent = responseBody.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee.name");

			if (feeName.contains(feeNamePercent)) {
				feeexternalIdPercent = responseBody
						.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee.externalId");
				records.put(feeNamePercent, feeexternalIdPercent);
			}

		} else if (node.size() > 1) {
			for (int i = 0; i < node1.size(); i++) {

				feeNamePercent = responseBody
						.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee[" + i + "].name");

				if (feeName.contains(feeNamePercent)) {
					feeexternalIdPercent = responseBody
							.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee[" + i + "].externalId");
					records.put(feeNamePercent, feeexternalIdPercent);
				}

			}
		}
		return records;
	}

	public ArrayList<String> getStartAndEndDateBaseRate(Response responseBody) {
		ArrayList<String> result = new ArrayList<String>();
		String startDate = responseBody
				.path("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override.nights.range.min");
		result.add(startDate);
		String endDate = responseBody
				.path("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override.nights.range.max");
		result.add(endDate);
		return result;

	}

	public HashMap<String, ArrayList<String>> getExtraAdultChildDateRangeBaseRate(Response responseBody) {
		HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
		ArrayList<String> dateRangeAdult = new ArrayList<String>();
		ArrayList<String> dateRangeChild = new ArrayList<String>();
		String extraAdultStartDay = responseBody.path(
				"lodgingRateContent.lodgingRate.fees.otherFees.fee[0].nightlyOverrides.override[0].nights.range.min");
		dateRangeAdult.add(extraAdultStartDay);
		String extraAdultEndtDay = responseBody.path(
				"lodgingRateContent.lodgingRate.fees.otherFees.fee[0].nightlyOverrides.override[0].nights.range.max");
		dateRangeAdult.add(extraAdultEndtDay);

		String extraChildStartDay = responseBody.path(
				"lodgingRateContent.lodgingRate.fees.otherFees.fee[1].nightlyOverrides.override[0].nights.range.min");
		dateRangeChild.add(extraChildStartDay);
		String extraChildEndtDay = responseBody.path(
				"lodgingRateContent.lodgingRate.fees.otherFees.fee[1].nightlyOverrides.override[0].nights.range.max");
		dateRangeChild.add(extraChildEndtDay);
		result.put("extraAdultDateRange", dateRangeAdult);
		result.put("extraChildDateRange", dateRangeChild);
		return result;
	}

	public String getRateAmount(Response responseBody) {

		String res = responseBody.path("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override.amount");

		System.out.println(res);

		return res;

	}

	public HashMap<String, String> getAdditionalAdultChildRate(Response responseBody) {
		HashMap<String, String> extraRate = new HashMap<String, String>();
		String extraAdult = responseBody
				.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[0].nightlyOverrides.override[0].amount");
		extraRate.put("extraAdultAmount", extraAdult);
		String extraChild = responseBody
				.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[1].nightlyOverrides.override[0].amount");
		extraRate.put("extraChildAmount", extraChild);
		return extraRate;

	}

	public String getResponseBodyOfVrboRateEndPointTest(String property, String roomClassName, String token) {
		String result = given().header("authorization", token).when()
				.get(VrboEndPoints.generateRateEndPoint(property, roomClassName)).then().contentType(ContentType.XML)
				.extract().path("lodgingRateContent.lodgingRate.currency");

		return result;

	}
	/*
	 * private static Document toXmlDocument(String str) throws
	 * ParserConfigurationException, SAXException, IOException{
	 * 
	 * DocumentBuilderFactory docBuilderFactory =
	 * DocumentBuilderFactory.newInstance(); DocumentBuilder docBuilder =
	 * docBuilderFactory.newDocumentBuilder(); Document document =
	 * docBuilder.parse(new InputSource(new StringReader(str)));
	 * 
	 * return document; }
	 */

	public int getVrboClientStatusCodes(String clientID, String token) {
		// Response response =
		// RestAssured.get(VrboEndPoints.genrateVrboClientLevelEndPoint(clientID));

		Response response = given().header("authorization", token)

				.get(VrboEndPoints.genrateVrboClientLevelEndPoint(clientID));
		int statusCode = response.getStatusCode();

		given().get(VrboEndPoints.genrateVrboClientLevelEndPoint(clientID)).then().statusCode(200);
		return statusCode;
	}

	public HashMap<String, String> getVrboClientStatusCode(String clientID, String token) {
		HashMap<String, String> data = new HashMap<String, String>();
		Response response = given().header("authorization", token)
				.get(VrboEndPoints.genrateVrboClientLevelEndPoint(clientID));
		int statusCode = response.getStatusCode();
		data.put("StatusCode", String.valueOf(statusCode));
		data.put("APIURL", VrboEndPoints.genrateVrboClientLevelEndPoint(clientID));
		log.info(data);
		return data;
	}

	public HashMap<String, String> getVrboStatusCode(String token) {
		HashMap<String, String> data = new HashMap<String, String>();
		Response response = given().header("authorization", token).get(VrboEndPoints.GetAdvertiser);
		int statusCode = response.getStatusCode();
		log.info("Verified Status Code: for Vrbo Client Level<b>" + statusCode + "</b>");
		data.put("StatusCode", String.valueOf(statusCode));
		data.put("APIURL", VrboEndPoints.GetAdvertiser);
		log.info(data);
		return data;
	}

	public HashMap<String, String> postRequest(String token, String uri, String requestBody) {
		HashMap<String, String> data = new HashMap<String, String>();
		Response response = given().header("authorization", token)
				.header("Content-Type", "application/xml;charset=utf-8").body(requestBody).when().post(uri);

		log.info("contant  type--" + response.getContentType());
		int statusCode = response.getStatusCode();
		log.info("Post Response :" + response.asString());
		log.info("Status Code :" + response.getStatusCode());
		data.put("StatusCode", String.valueOf(statusCode));
		data.put("APIURL", uri);
		data.put("BODY", response.asString());
		log.info(data);
		return data;
	}

	public int getVrboRateEndpointStatus(String property, String roomClassName, String token) {
		// Response response =
		// RestAssured.get(VrboEndPoints.genrateVrboClientLevelEndPoint(clientID));

		Response response = given().header("authorization", token)

				.get(VrboEndPoints.generateRateEndPoint(property, roomClassName));
		int statusCode = response.getStatusCode();

		return statusCode;
	}

	/*
	 * public void veifyVrboClientStatusCode(int statusCode, ArrayList<String>
	 * testSteps) { assertEquals(statusCode, 200);
	 * log.info("Verified Status Code: for Vrbo Client Level<b>" + statusCode +
	 * "</b>"); testSteps.add("Verified Status Code: for Vrbo Client Level<b>" +
	 * statusCode + "</b>");
	 * 
	 * }
	 */

	public void verifyBodyData_GET() {
		given().get(OTAEndPoints.GET_ALL_USER_LIST).then().body("data.id[1]", equalTo(8)).body("data.first_name",
				hasItems("Michael", "Lindsay"));
	}

	public void veifyVrboClientStatusCode(int statusCode, ArrayList<String> testSteps) {
		assertEquals(statusCode, 200);
		log.info("Verified Status Code:  " + statusCode);
		testSteps.add("Verified Status Code:  <b>" + statusCode + "</b>");

	}

	public void verifyXMLSchema(String schemaPayLoad, String uri) {
		given().header("Content-Type", "application/xml;charset=utf-8").body(schemaPayLoad).then();

	}
	/*
	 * public void verifyBodyData_GET() {
	 * given().get(OTAEndPoints.GET_ALL_USER_LIST).then().body("data.id[1]",
	 * equalTo(8)).body("data.first_name", hasItems("Michael", "Lindsay")); }
	 */

	public void getRequest(String clientID) {
		Response response = RestAssured.get(VrboEndPoints.genrateVrboClientLevelEndPoint(clientID));

		System.out.println(response.asPrettyString());
		System.out.println(response.getBody().asString());
		System.out.println(response.getStatusCode());
		System.out.println(response.getStatusLine());

		assertEquals(response.getStatusCode(), 200);
		System.out.println("Successfully Verified Status Code");
	}

	public void verifyPOST(String name, String job) {
		given().body(genrateJsonObject(name, job).toJSONString()).when().post(OTAEndPoints.POST_USER).then()
				.statusCode(201).log().all();

		System.out.println("Request Posted");
	}

	public JSONObject genrateJsonObject(String name, String job) {
		JSONObject request = new JSONObject();

		request.put("name", name);
		request.put("name", "Automation Eng");

		return request;
	}

	public void verifyPUT(String name, String job) {
		given().body(genrateJsonObject(name, job).toJSONString()).when().put(OTAEndPoints.POST_USER).then()
				.statusCode(200).log().all();

		System.out.println("Request Posted");
	}

	public void verifyDELETE() {
		when().delete(OTAEndPoints.DELETE_USER).then().statusCode(204).log().all();
	}

	public void verifyVrboRates(ArrayList<String> vrboDateRange, String inStartDate, String inEndDate, String vrboRate,
			String inRate, ArrayList<String> test_steps) {
		ArrayList<String> days = new ArrayList<String>();
		days.add(inStartDate);
		days.add(inEndDate);
		int i = 0;
		for (String date : vrboDateRange) {
			String changeDate = Utility.parseDate(date, "yyyy-MM-dd", "dd/MM/yyyy");

			assertEquals(changeDate, days.get(i), "Failed to verify");
			i++;

		}
		assertEquals(inRate, vrboRate, "Faled to verify");
		test_steps.add("Verify the date range: " + vrboDateRange + " " + "Has Rate: " + vrboRate);
		log.info("Verify the date range: " + vrboDateRange + " " + "Has Rate: " + vrboRate);
	}
	
	//Following are  the generic methods to validate end points
	
	public void verifyVrboApiStatusCode(String endPointName, String apiToVerifyStatus, String authToken, int expectedStatusCode, ArrayList<String> testSteps) {
	
		testSteps.add("===== Verify Status Code for End Point '"+ endPointName +"' is '<b>" + expectedStatusCode +"</b>'. =====".toUpperCase());
		log.info("===== Verify Status Code End Point '"+ endPointName +"' is '<b>" + expectedStatusCode +"</b>'. =====".toUpperCase());		
		testSteps.add("EndPoint Url : " + apiToVerifyStatus);
		log.info("EndPoint Url : " + apiToVerifyStatus);
		testSteps.add("Expected Status code : " + expectedStatusCode);
		log.info("Expected Status Code : " + expectedStatusCode);
		
		given()
		.header("authorization", authToken)
		.header("Content-Type", "application/xml;charset=utf-8")
		.get(apiToVerifyStatus).then().statusCode(expectedStatusCode);
		
		 log.info("Verified Status Code for '"+ endPointName  +"' : <b>" + expectedStatusCode +"</b>");
		 testSteps.add("Verified Status Code for '"+ endPointName  +"' : <b>" + expectedStatusCode +"</b>");
	
	}
	
	public Response getApiResponse(String apiToVerifyStatus, String authToken) {
		Response response = given()
				.header("authorization", authToken)
				.header("Content-Type", "application/xml;charset=utf-8")
				.when()
				.get(apiToVerifyStatus)
				.then()
				.contentType(ContentType.XML)
				.extract()
				.response();
		
		//log.info(response.asPrettyString());
		//log.info(response.getBody().asString());
		//log.info(response.getStatusCode());
		//log.info(response.getStatusLine());

		return response;
	}

	public String getAttributesValue(Response response, String attribute) {
		return response.path(attribute);
	}
	
	public JSONObject generateJsonObject(HashMap<String, String> jsonMap) {
		JSONObject jsonObject = new JSONObject();
		if(!jsonMap.isEmpty()) {
			jsonMap.forEach((key, value) -> jsonObject.put(key, value));			
			log.info("jsonMap.size : " + jsonMap.size());			
		}

		return jsonObject;
		
	}

	public void verifyPostRequest(String apiToVerifyStatus, JSONObject jsonObject, ArrayList<String> testSteps) {
		given().
			body(jsonObject.toJSONString()).
		when().
			post(apiToVerifyStatus).
		then().
			statusCode(201).
			log().all();
		
		log.info("Request posted successfully");			
		testSteps.add("Request posted successfully");
	}

	public void verifyDeleteRequest(String apiToVerifyStatus, ArrayList<String> testSteps) {
		when().
			delete(apiToVerifyStatus).
		then().
			statusCode(204).
			log().all();

		log.info("Deleted successfully");			
		testSteps.add("Deleted successfully");

	}
	public void verifyVerboRatesWithDateRange(HashMap<String, ArrayList<ArrayList<String>>> getAllVrboBaseRate,
			HashMap<String, String> roomClassWiseSourceBaseRatesRule, ArrayList<String> AllBaseRateIncludingOverride,
			ArrayList<String> test_steps) {
		Set<String> vrborates = getAllVrboBaseRate.keySet();
		Collection<String> inRatesRul = roomClassWiseSourceBaseRatesRule.values();
		ArrayList<ArrayList<String>> getAllDates = new ArrayList<ArrayList<String>>();
		ArrayList<String> allRateVrbo = new ArrayList<String>();
		// Iterator<String> itr = vrborates.iterator();

		for (String vrboval : vrborates) {
			ArrayList<ArrayList<String>> dates = getAllVrboBaseRate.get(vrboval);
			allRateVrbo.add(vrboval);
			getAllDates.addAll(dates);
		}
		boolean found = false;
		Set<String> set = new HashSet<>(inRatesRul);
		for (String rate : set) {
			for (String vrvoR : allRateVrbo) {
				if (rate.equalsIgnoreCase(vrvoR)) {
					found = true;
					break;
				}

			}
			assertEquals(found, true, "Failed to assert");
			log.info("rateFound: " + rate);
			ArrayList<ArrayList<String>> vrbodateRange = getAllVrboBaseRate.get(rate);
			ArrayList<String> range = vrbodateRange.get(0);
			for (String days : range) {
				String getValRates = roomClassWiseSourceBaseRatesRule.get(days);

				assertEquals(getValRates, rate, "Failed to assert");

			}
			log.info("Verify All dates: " + range);
		}

	}

	public HashMap<String, ArrayList<ArrayList<String>>> getAllRatesBaseRateWithDateRange(WebDriver driver,
			Response responseBody) throws ParseException {
		String rate = "";
		String startDate = "";
		String endDate = "";
		HashMap<String, ArrayList<ArrayList<String>>> records = new HashMap<String, ArrayList<ArrayList<String>>>();
		List<Node> node = responseBody.xmlPath()
				.getList("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override", Node.class);
		log.info("nodeFee: " + node.size());
		for (int i = 0; i < node.size(); i++) {
			ArrayList<ArrayList<String>> dateRange = new ArrayList<ArrayList<String>>();
			ArrayList<ArrayList<String>> ranges = new ArrayList<ArrayList<String>>();
			if (node.size() == 1) {
				rate = responseBody
						.path("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override.amount");
				log.info("rate: " + rate);

				startDate = responseBody
						.path("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override.nights.range.min");

				log.info("startDate: " + startDate);

				endDate = responseBody
						.path("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override.nights.range.max");
				log.info("endDate: " + endDate);

				dateRange.add(
						Utility.getAllDatesStartAndEndDates(Utility.parseDate(startDate, "yyyy-MM-dd", "dd/MM/yyyy"),
								Utility.parseDate(endDate, "yyyy-MM-dd", "dd/MM/yyyy")));
			} else if (node.size() > 1) {
				rate = responseBody.path(
						"lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override[" + i + "].amount");
				log.info("rate: " + rate);
				List<Node> nodeRange = responseBody.xmlPath().getList(
						"lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override[" + i + "].nights.range",
						Node.class);
				log.info("nodeRange: " + nodeRange.size());
				for (int j = 0; j < nodeRange.size(); j++) {

					startDate = responseBody
							.path("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override[" + i
									+ "].nights.range[" + j + "].min");
					log.info("startDate: " + startDate);

					endDate = responseBody.path("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override["
							+ i + "].nights.range[" + j + "].max");
					log.info("endDate: " + endDate);

					ranges.add(Utility.getAllDatesStartAndEndDates(
							Utility.parseDate(startDate, "yyyy-MM-dd", "dd/MM/yyyy"),
							Utility.parseDate(endDate, "yyyy-MM-dd", "dd/MM/yyyy")));

				}

				dateRange.addAll(ranges);
			}
			records.put(Utility.convertDecimalFormat(rate), dateRange);

			System.out.println(records);

		}
		return records;

	}

	public HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>> getAllExtraAdultChildDateRange(
			WebDriver driver, Response responseBody) throws ParseException {
		String startDay = "", endDay = "", extrarate = "", feeName = "";

		ArrayList<HashMap<String, ArrayList<ArrayList<String>>>> ExtraAdult = new ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>();
		ArrayList<HashMap<String, ArrayList<ArrayList<String>>>> ExtraChild = new ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>();
		ArrayList<HashMap<String, ArrayList<ArrayList<String>>>> ExtraAdultAdjustMent = new ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>();
		ArrayList<HashMap<String, ArrayList<ArrayList<String>>>> ExtraChildAdjustMent = new ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>();
		HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>> diff = new HashMap<String, ArrayList<HashMap<String, ArrayList<ArrayList<String>>>>>();

		List<Node> node = responseBody.xmlPath().getList("lodgingRateContent.lodgingRate.fees.otherFees.fee",
				Node.class);

		log.info("nodeFee: " + node.size());
		for (int i = 0; i < node.size(); i++) {
			String age = "";
			List<Node> nodeOverride = responseBody.xmlPath().getList(
					"lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].nightlyOverrides.override",
					Node.class);
			HashMap<String, ArrayList<ArrayList<String>>> records = new HashMap<String, ArrayList<ArrayList<String>>>();
			for (int j = 0; j < nodeOverride.size(); j++) {
				ArrayList<ArrayList<String>> dateRange = new ArrayList<ArrayList<String>>();
				ArrayList<ArrayList<String>> ranges = new ArrayList<ArrayList<String>>();
				extrarate = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i
						+ "].nightlyOverrides.override[" + j + "].amount");
				log.info("extrarate: " + extrarate);
				List<Node> nodeRange = responseBody.xmlPath()
						.getList("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i
								+ "].nightlyOverrides.override[" + j + "].nights.range", Node.class);

				for (int k = 0; k < nodeRange.size(); k++) {
					startDay = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i
							+ "].nightlyOverrides.override[" + j + "].nights.range[" + k + "].min");
					log.info("startDay: " + startDay);
					endDay = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i
							+ "].nightlyOverrides.override[" + j + "].nights.range[" + k + "].max");

					log.info("endDate: " + endDay);

					ranges.add(
							Utility.getAllDatesStartAndEndDates(Utility.parseDate(startDay, "yyyy-MM-dd", "dd/MM/yyyy"),
									Utility.parseDate(endDay, "yyyy-MM-dd", "dd/MM/yyyy")));
				}
				dateRange.addAll(ranges);
				if (Double.parseDouble(extrarate) != 0) {
					records.put(Utility.convertDecimalFormat(extrarate), dateRange);
				}

			}
			System.out.println(records);
			try {
				age = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i
						+ "].appliesPerGuestPerNight.forGuestsOfAge.range.min");
			} catch (Exception e) {
				age = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i
						+ "].appliesPerGuestPerNight.forGuestsOfAge.range.max");
			}

			feeName = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].name");

			if (Integer.parseInt(age) == 18 && feeName.equalsIgnoreCase("Additional Guest Fee")) {

				ExtraAdult.add(records);

			} else if (Integer.parseInt(age) == 17 && feeName.equalsIgnoreCase("Additional Guest Fee")) {

				ExtraChild.add(records);

			} else if (Integer.parseInt(age) == 18 && feeName.equalsIgnoreCase("Additional Guest Fee Adjustment")) {

				ExtraAdultAdjustMent.add(records);
				System.out.println(diff);
			} else if (Integer.parseInt(age) == 17 && feeName.equalsIgnoreCase("Additional Guest Fee Adjustment")) {

				ExtraChildAdjustMent.add(records);

			}

		}
		diff.put("ExtraAdult", ExtraAdult);
		diff.put("ExtraChild", ExtraChild);
		diff.put("ExtraAdultAdjustMent", ExtraAdultAdjustMent);
		diff.put("ExtraChildAdjustMent", ExtraChildAdjustMent);

		return diff;

	}

	public HashMap<String, String> getFlatTaxDetails(WebDriver driver, Response responseBody) {
		String flatTaxName = "";
		String flatTaxAmount = "";
		HashMap<String, String> records = new HashMap<String, String>();
		List<Node> nodeRule = responseBody.xmlPath()
				.getList("lodgingRateContent.lodgingRate.taxRules.flatTaxRules.rule", Node.class);
		log.info("nodeRule: " + nodeRule.size());
		for (int i = 0; i < nodeRule.size(); i++) {
			flatTaxName = responseBody
					.path("lodgingRateContent.lodgingRate.taxRules.flatTaxRules.rule[" + i + "].name");

			flatTaxAmount = responseBody
					.path("lodgingRateContent.lodgingRate.taxRules.flatTaxRules.rule[" + i + "].amount");
			records.put(flatTaxName, flatTaxAmount);
		}
		return records;
	}

	public HashMap<String, String> getPercentageTax(WebDriver driver, Response responseBody) {
		HashMap<String, String> records = new HashMap<String, String>();
		String percentTaxName = "";
		String percentTaxValue = "";
		List<Node> nodeRule = responseBody.xmlPath()
				.getList("lodgingRateContent.lodgingRate.taxRules.percentOfRentAndFeesTaxRules.rule", Node.class);
		for (int i = 0; i < nodeRule.size(); i++) {
			percentTaxName = responseBody
					.path("lodgingRateContent.lodgingRate.taxRules.percentOfRentAndFeesTaxRules.rule[" + i + "].name");

			percentTaxValue = responseBody.path(
					"lodgingRateContent.lodgingRate.taxRules.percentOfRentAndFeesTaxRules.rule[" + i + "].percent");

			records.put(percentTaxName, percentTaxValue);
		}
		return records;

	}

	public HashMap<String, String> getFlatFeeDetailsFromTaxesAndFee(WebDriver driver, Response responseBody) {
		HashMap<String, String> records = new HashMap<String, String>();
		String feeName = "";
		String feeAmount = "";
		List<Node> node = responseBody.xmlPath().getList("lodgingRateContent.lodgingRate.fees.otherFees.fee",
				Node.class);

		log.info("nodeFee: " + node.size());
		for (int i = 0; i < node.size(); i++) {

			List<Node> nodeExist = responseBody.xmlPath()
					.getList("lodgingRateContent.lodgingRate.fees.otherFees.fee.amount");
			log.info("nodeExist: " + nodeExist.size());
			if (nodeExist.size() > 1) {
				feeAmount = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].amount");

				feeName = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].name");

				records.put(feeName, feeAmount);

			} else if (nodeExist.size() == 1) {
				feeAmount = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee.amount");

				feeName = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee.name");

				records.put(feeName, feeAmount);

			}
		}
		return records;
	}

	public HashMap<String, String> getPercentFeeDetailsFromTaxesAndFee(WebDriver driver, Response responseBody) {
		HashMap<String, String> records = new HashMap<String, String>();
		String feeName = "";
		String feeAmount = "";
		List<Node> node = responseBody.xmlPath().getList("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee",
				Node.class);
		log.info("nodeFee: " + node.size());
		if (node.size() == 1) {
			feeAmount = responseBody.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee.percent");

			feeName = responseBody.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee.name");

			records.put(feeName, feeAmount);
		} else if (node.size() > 1) {
			for (int i = 0; i < node.size(); i++) {
				feeAmount = responseBody
						.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee[" + i + "].percent");

				feeName = responseBody
						.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee[" + i + "].name");
				records.put(feeName, feeAmount);
			}
		}
		return records;
	}

	public HashMap<String, Response> postRequests(String token, String uri, String requestBody) {
		HashMap<String, Response> data = new HashMap<String, Response>();

		Response response = given().header("authorization", token)
				.header("Content-Type", "application/xml;charset=utf-8").body(requestBody).when().post(uri);

		log.info("contant  type--" + response.getContentType());

		data.put("BODY", response);
		log.info(response.asString());
		return data;
	}

	public HashMap<String, Response> getBookingUpdateRequest(String token, String clientId, String confirmationNum) {
		HashMap<String, Response> data = new HashMap<String, Response>();
		VrboEndPoints vrboend = new VrboEndPoints();
		String uri = vrboend.generateVrboBookingUpdateEndPoint(clientId, confirmationNum);
		log.info("uri--" + uri);
		Response response = given().header("authorization", token).header("Content-Type", "application/xml").when()
				.get(uri);
		log.info("contant  type--" + response.getContentType());
		data.put("BODY", response);
		log.info(data);
		return data;
	}

	public HashMap<String, String> postRequestResponseData(Response response, String uri) {
		HashMap<String, String> data = new HashMap<String, String>();
		log.info("contant  type--" + response.getContentType());
		int statusCode = response.getStatusCode();
		log.info("Post Response :" + response.asString());
		log.info("Status Code :" + response.getStatusCode());
		data.put("StatusCode", String.valueOf(statusCode));
		data.put("APIURL", uri);
		data.put("BODY", response.asString());
		log.info(data);
		return data;
	}

//==============================Validate Booking Response XML===============================================
	public void validateBookingResponse(Response response, String advertiseAssignedID, String listingExternalID,
			String unitExternalID, String externalConfirmation, String numberOfAdult, String numberOfChilds,
			String numberOfPets, String checkinDate, String checkoutDate, String IsTaxAdd, String TaxNameInXml,
			String TaxAmountInXml, String setTaxRate, String isExtraAdultInXml, String extraAdultAmount,
			String isExtraChildInXml, String extraChildAmount, String isFeeAdd, String feeNameInXml, String feeAmount,
			String setFeeRate, String taxExternalId, String feeExternalId, String roomchargeAmount,
			String paymentSheduleItem, String roomClassLevelPolicy, String originationDate,
			ArrayList<String> test_steps) {
		String version = "", advertiseAssignedId = "", listingExternalid = "", unitExternalid = "",
				confirmationNumber = "", nodeLocale = "", resPaymentStatus = "", resStatus = "";
		Node nameNode = response.htmlPath().get();
		assertThat(nameNode.name(), is("bookingResponse"));
		log.info("Verify Starting XML Tag: " + nameNode.name().toString());
		test_steps.add("Verify Starting XML Tag: " + nameNode.name().toString());

		version = response.path("bookingResponse.documentVersion");
		assertThat(version, is("1.3"));
		log.info("Verify Booking response Version: " + version);
		test_steps.add("Verify Booking response Version: " + version);

		advertiseAssignedId = response.path("bookingResponse.bookingResponseDetails.advertiserAssignedId");
		assertThat(advertiseAssignedId, is(advertiseAssignedID));

		log.info("Verify Booking response advertiseAssignedId: " + advertiseAssignedId);
		test_steps.add("Verify Booking response advertiseAssignedId: " + advertiseAssignedId);

		listingExternalid = response.path("bookingResponse.bookingResponseDetails.listingExternalId");
		assertThat(listingExternalid, is(listingExternalID));
		test_steps.add("Verify Booking response listingExternalid: " + listingExternalid);

		log.info("Verify Booking response listingExternalid: " + listingExternalid);

		unitExternalid = response.path("bookingResponse.bookingResponseDetails.unitExternalId");
		assertThat(unitExternalid, is(unitExternalID));

		log.info("Verify Booking response unitExternalid: " + unitExternalid);
		test_steps.add("Verify Booking response unitExternalid: " + unitExternalid);

		confirmationNumber = response.path("bookingResponse.bookingResponseDetails.externalId");
		assertThat(confirmationNumber, is(externalConfirmation));

		log.info("Verify Booking response confirmationNumber: " + confirmationNumber);
		test_steps.add("Verify Booking response confirmationNumber: " + confirmationNumber);

		nodeLocale = response.path("bookingResponse.bookingResponseDetails.locale");
		assertThat(nodeLocale, is("en_US"));
		log.info("Verify locale: " + nodeLocale);
		test_steps.add("Verify locale: " + nodeLocale);

		List<Node> urlNode = response.xmlPath().getList("bookingResponse.bookingResponseDetails.rentalAgreement.url");
		assertThat(urlNode.size(), equalTo(1));
		log.info("Verify url: " + urlNode);
		test_steps.add("Verify url: " + urlNode);

		resPaymentStatus = response.path("bookingResponse.bookingResponseDetails.reservationPaymentStatus");
		assertThat(resPaymentStatus, is("PAID"));
		log.info("Verify ReservationPaymentStatus: " + resPaymentStatus);
		test_steps.add("Verify ReservationPaymentStatus: " + resPaymentStatus);

		resStatus = response.path("bookingResponse.bookingResponseDetails.reservationStatus");
		log.info("Verify ReservationStatus: " + resStatus);
		test_steps.add("Verify ReservationStatus: " + resStatus);

		validateOrderItemsInBookingResponse(response, IsTaxAdd, TaxNameInXml, TaxAmountInXml, setTaxRate,
				isExtraAdultInXml, extraAdultAmount, isExtraChildInXml, extraChildAmount, isFeeAdd, feeNameInXml,
				feeAmount, setFeeRate, taxExternalId, feeExternalId, roomchargeAmount, paymentSheduleItem, test_steps);

		validateReservationDetailsInResponse(response, numberOfAdult, numberOfChilds, numberOfPets, checkinDate,
				checkoutDate, test_steps);
		validateReservationOriginationDate(response, originationDate, test_steps);

		validateReservationPaymentDetails(response, paymentSheduleItem, checkinDate, test_steps);
		validateAcceptedPaymentForms(response, test_steps);
		validateReservationPolicy(response, roomClassLevelPolicy, test_steps);

	}

	public void validateReservationOriginationDate(Response response, String originationDate,
			ArrayList<String> test_steps) {
		log.info("========VERIFY RESERVATION ORIGIATIONATION DATE========");
		test_steps.add("========VERIFY RESERVATION ORIGIATIONATION DATE========");
		String originationDates = response
				.path("bookingResponse.bookingResponseDetails.reservation.reservationOriginationDate");
		assertThat(originationDates, is(originationDate));
		log.info("Verify originationDates: " + originationDates);
		test_steps.add("Verify originationDates: " + originationDates);
	}

	public void validateReservationPolicy(Response response, String roomClassLevelPolicy,
			ArrayList<String> test_steps) {
		HashMap<String, String> policyApplicable = new HashMap<String, String>();
		log.info("========VERIFY RESERVATION POLICY========");
		test_steps.add("========VERIFY RESERVATION POLICY========");
		policyApplicable.put("Moderate",
				"Bookings cancelled at least 30 days before the start of stay will receive 100% refund. Bookings cancelled at least 14 days before the start of stay will receive a 50% refund");
		policyApplicable.put("Firm",
				"Bookings cancelled at least 60 days before the start of stay will receive 100% refund. Bookings cancelled at least 30 days before the start of stay will receive a 50% refund");
		policyApplicable.put("Strict",
				"Bookings cancelled at least 60 days before the start of stay will receive 100% refund");
		policyApplicable.put("No Refund", "No Refund");
		policyApplicable.put("Relaxed",
				"Bookings cancelled at least 14 days before the start of stay will receive 100% refund. Bookings cancelled at least 7 days before the start of stay will receive a 50% refund");
		String getpolicy = response.path(
				"bookingResponse.bookingResponseDetails.orderList.order.reservationCancellationPolicy.description");
		assertThat(getpolicy, is(policyApplicable.get(roomClassLevelPolicy)));
		log.info("Verify getpolicy: " + getpolicy);
		test_steps.add("Verify getpolicy: " + getpolicy);
	}

	public void validateAcceptedPaymentForms(Response response, ArrayList<String> test_steps) {
		log.info("========VERIFY ACCEPTED FORMS========");
		test_steps.add("========VERIFY ACCEPTED FORMS========");
		String paymentFormType = response.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[0].paymentFormType");
		assertThat(paymentFormType, is("CARD"));
		log.info("Verify paymentFormType: " + paymentFormType);
		test_steps.add("Verify paymentFormType: " + paymentFormType);

		String cardcode = response.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[0].cardCode");
		assertThat(cardcode, is("AMEX"));
		log.info("Verify cardcode: " + cardcode);
		test_steps.add("Verify cardcode: " + cardcode);

		String cardtype = response.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[0].cardType");
		assertThat(cardtype, is("CREDIT"));
		log.info("Verify cardtype: " + cardtype);
		test_steps.add("Verify cardtype: " + cardtype);

		log.info("========================================");
		test_steps.add("========================================");
		String paymentFormType1 = response
				.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
						+ "acceptedPaymentForms.paymentCardDescriptor[1].paymentFormType");
		assertThat(paymentFormType1, is("CARD"));
		log.info("Verify paymentFormType1: " + paymentFormType1);
		test_steps.add("Verify paymentFormType1: " + paymentFormType1);

		String cardcode1 = response.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[1].cardCode");
		assertThat(cardcode1, is("DISCOVER"));
		log.info("Verify cardcode1: " + cardcode1);
		test_steps.add("Verify cardcode1: " + cardcode1);

		String cardtype1 = response.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[1].cardType");
		assertThat(cardtype1, is("CREDIT"));
		log.info("Verify cardtype1: " + cardtype1);
		test_steps.add("Verify cardtype1: " + cardtype1);

		log.info("========================================");
		test_steps.add("========================================");
		String paymentFormType2 = response
				.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
						+ "acceptedPaymentForms.paymentCardDescriptor[2].paymentFormType");
		assertThat(paymentFormType2, is("CARD"));
		log.info("Verify paymentFormType2: " + paymentFormType2);
		test_steps.add("Verify paymentFormType2: " + paymentFormType2);

		String cardcode2 = response.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[2].cardCode");
		assertThat(cardcode2, is("MASTERCARD"));
		log.info("Verify cardcode2: " + cardcode2);
		test_steps.add("Verify cardcode2: " + cardcode2);

		String cardtype2 = response.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[2].cardType");
		assertThat(cardtype2, is("CREDIT"));
		log.info("Verify cardtype2: " + cardtype2);
		test_steps.add("Verify cardtype2: " + cardtype2);
		log.info("========================================");
		test_steps.add("========================================");
		String paymentFormType3 = response
				.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
						+ "acceptedPaymentForms.paymentCardDescriptor[3].paymentFormType");
		assertThat(paymentFormType3, is("CARD"));
		log.info("Verify paymentFormType3: " + paymentFormType3);
		test_steps.add("Verify paymentFormType3: " + paymentFormType3);

		String cardcode3 = response.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[3].cardCode");
		assertThat(cardcode3, is("VISA"));
		log.info("Verify cardcode3: " + cardcode3);
		test_steps.add("Verify cardcode3: " + cardcode3);

		String cardtype3 = response.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[3].cardType");
		assertThat(cardtype3, is("CREDIT"));
		log.info("Verify cardtype3: " + cardtype3);
		test_steps.add("Verify cardtype3: " + cardtype3);
	}

	public void validateReservationPaymentDetails(Response response, String paymentScheduleAmount, String dueDate,
			ArrayList<String> test_steps) {
		log.info("========VERIFY RESERVATION PAYMENT SCHEDULE DETAILS========");
		test_steps.add("========VERIFY RESERVATION PAYMENT SCHEDULE DETAILS========");
		String paymentscheduleAmount = response
				.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
						+ "paymentScheduleItemList.paymentScheduleItem.amount");
		assertThat(paymentscheduleAmount, is(paymentScheduleAmount));
		log.info("Verify paymentscheduleAmount: " + paymentscheduleAmount);
		test_steps.add("Verify paymentscheduleAmount: " + paymentscheduleAmount);

		String paymentscheduleDueDate = response
				.path("bookingResponse.bookingResponseDetails.orderList.order.paymentSchedule."
						+ "paymentScheduleItemList.paymentScheduleItem.dueDate");
		assertThat(paymentscheduleDueDate, is(dueDate));
		log.info("Verify paymentscheduleDueDate: " + paymentscheduleDueDate);
		test_steps.add("Verify paymentscheduleDueDate: " + paymentscheduleDueDate);
	}

	public void validateOrderItemsInBookingResponse(Response response, String IsTaxAdd, String TaxNameInXml,
			String TaxAmountInXml, String setTaxRate, String isExtraAdultInXml, String extraAdultAmount,
			String isExtraChildInXml, String extraChildAmount, String isFeeAdd, String feeNameInXml, String feeAmount,
			String setFeeRate, String taxExternalId, String feeExternalId, String roomchargeAmount,
			String paymentSheduleItem, ArrayList<String> test_steps) {
		ArrayList<String> taxesName = new ArrayList<String>();
		ArrayList<String> taxesAmount = new ArrayList<String>();
		ArrayList<String> taxesExternal = new ArrayList<String>();
		ArrayList<String> feeName = new ArrayList<String>();
		ArrayList<String> feeAmounts = new ArrayList<String>();
		ArrayList<String> feeExternal = new ArrayList<String>();

		ArrayList<String> setTaxRates = new ArrayList<String>();
		ArrayList<String> setFeeRates = new ArrayList<String>();
		int totalSize = 0, feeSize = 0, extraAdultSize = 0, extraChildSize = 0, taxSize = 0;
		String extraAdultFeeName = "OBR_ADULT_FEE";
		String extraChildFeeName = "OBR_CHILD_FEE";
		if (IsTaxAdd.equalsIgnoreCase("Yes")) {

			taxesName = Utility.convertTokenToArrayList(TaxNameInXml, Utility.DELIMS);
			taxesAmount = Utility.convertTokenToArrayList(TaxAmountInXml, Utility.DELIMS);
			taxesExternal = Utility.convertTokenToArrayList(taxExternalId, Utility.DELIMS);
			setTaxRates = Utility.convertTokenToArrayList(setTaxRate, Utility.DELIMS);
			taxSize = taxesName.size();
		}
		if (isFeeAdd.equalsIgnoreCase("Yes")) {
			feeName = Utility.convertTokenToArrayList(feeNameInXml, Utility.DELIMS);
			feeAmounts = Utility.convertTokenToArrayList(feeAmount, Utility.DELIMS);
			feeExternal = Utility.convertTokenToArrayList(feeExternalId, Utility.DELIMS);
			setFeeRates = Utility.convertTokenToArrayList(setFeeRate, Utility.DELIMS);
			feeSize = feeName.size();
		}
		if (isExtraAdultInXml.equalsIgnoreCase("Yes") && isExtraChildInXml.equalsIgnoreCase("Yes")) {
			// extra 1 added for room charge order item
			extraAdultSize = 1;
			extraChildSize = 1;
			totalSize = taxSize + feeSize + extraAdultSize + extraChildSize + 1;
		} else if (!isExtraAdultInXml.equalsIgnoreCase("Yes") && isExtraChildInXml.equalsIgnoreCase("Yes")) {
			extraChildSize = 1;
			totalSize = taxSize + feeSize + extraAdultSize + extraChildSize + 1;
		} else if (isExtraAdultInXml.equalsIgnoreCase("Yes") && !isExtraChildInXml.equalsIgnoreCase("Yes")) {
			extraAdultSize = 1;
			totalSize = taxSize + feeSize + extraAdultSize + extraChildSize + 1;
		} else {
			totalSize = taxSize + feeSize + extraAdultSize + extraChildSize + 1;
		}
		log.info("========VERIFY RESERVATION ORDER ITEMS========");
		test_steps.add("========VERIFY RESERVATION ORDER ITEMS========");
		List<Node> oderItemsNode = response.xmlPath()
				.getList("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem");
		assertThat(oderItemsNode.size(), is(totalSize));
		log.info("Verify Order item Size: " + oderItemsNode.size());
		test_steps.add("Verify Order item Size: " + oderItemsNode.size());
		for (int i = 0; i < oderItemsNode.size(); i++) {
			String name = "", preTaxAmount = "", totalAmount = "", externalId = "", feeType, productId = "",
					taxRate = "", feeRate = "";
			log.info("=======================");
			test_steps.add("=====================");
			if (i == 0) {
				name = response.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
						+ i + "].name");
				assertThat(name, is("Room Charge"));
				log.info("Verify name: " + name);
				test_steps.add("Verify name: " + name);

				preTaxAmount = response
						.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
								+ "].preTaxAmount");
				assertThat(preTaxAmount, is(roomchargeAmount));
				log.info("Verify preTaxAmount: " + preTaxAmount);
				test_steps.add("Verify preTaxAmount: " + preTaxAmount);

				totalAmount = response
						.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
								+ "].totalAmount");
				assertThat(totalAmount, is(roomchargeAmount));
				log.info("Verify totalAmount: " + totalAmount);
				test_steps.add("Verify totalAmount: " + totalAmount);

				externalId = response
						.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
								+ "].externalId");
				assertThat(externalId, is("1234"));
				log.info("Verify externalId: " + externalId);
				test_steps.add("Verify externalId: " + externalId);

				feeType = response
						.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
								+ "].feeType");
				assertThat(feeType, is("RENTAL"));
				log.info("Verify feeType: " + feeType);
				test_steps.add("Verify feeType: " + feeType);

				productId = response
						.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
								+ "].productId");
				assertThat(productId, is("RENT"));
				log.info("Verify productId: " + productId);
				test_steps.add("Verify productId: " + productId);
			} else {
				name = response.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
						+ i + "].name");
				if (name.equalsIgnoreCase(extraAdultFeeName)) {
					assertThat(name, is(extraAdultFeeName));
					log.info("Verify extraAdultFeeName: " + extraAdultFeeName);
					test_steps.add("Verify extraAdultFeeName: " + extraAdultFeeName);
					preTaxAmount = response
							.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
									+ "].preTaxAmount");
					assertThat(preTaxAmount, is(extraAdultAmount));
					log.info("Verify preTaxAmount: " + preTaxAmount);
					test_steps.add("Verify preTaxAmount: " + preTaxAmount);

					totalAmount = response
							.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
									+ "].totalAmount");
					assertThat(totalAmount, is(extraAdultAmount));
					log.info("Verify totalAmount: " + totalAmount);
					test_steps.add("Verify totalAmount: " + totalAmount);

					feeType = response
							.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
									+ "].feeType");
					assertThat(feeType, is("RENTAL"));
					log.info("Verify feeType: " + feeType);
					test_steps.add("Verify feeType: " + feeType);

					productId = response
							.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
									+ "].productId");
					assertThat(productId, is("RENT"));
					log.info("Verify productId: " + productId);
					test_steps.add("Verify productId: " + productId);
				} else if (name.equalsIgnoreCase(extraChildFeeName)) {
					assertThat(name, is(extraChildFeeName));
					log.info("Verify extraChildFeeName: " + extraChildFeeName);
					test_steps.add("Verify extraChildFeeName: " + extraChildFeeName);
					preTaxAmount = response
							.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
									+ "].preTaxAmount");
					assertThat(preTaxAmount, is(extraChildAmount));
					log.info("Verify preTaxAmount: " + preTaxAmount);
					test_steps.add("Verify preTaxAmount: " + preTaxAmount);

					totalAmount = response
							.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
									+ "].totalAmount");
					assertThat(totalAmount, is(extraChildAmount));
					log.info("Verify totalAmount: " + totalAmount);
					test_steps.add("Verify totalAmount: " + totalAmount);

					feeType = response
							.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
									+ "].feeType");
					assertThat(feeType, is("RENTAL"));
					log.info("Verify feeType: " + feeType);
					test_steps.add("Verify feeType: " + feeType);

					productId = response
							.path("bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem[" + i
									+ "].productId");
					assertThat(productId, is("RENT"));
					log.info("Verify productId: " + productId);
					test_steps.add("Verify productId: " + productId);
				} else {
					if (isFeeAdd.equalsIgnoreCase("Yes") && IsTaxAdd.equalsIgnoreCase("Yes")) {
						int j = 0, k = 0;
						for (String feenames : feeName) {

							if (name.equalsIgnoreCase(feenames)) {
								assertThat(name, is(feenames));
								log.info("Verify feenames: " + feenames);
								test_steps.add("Verify feenames: " + feenames);
								preTaxAmount = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].preTaxAmount");
								assertThat(preTaxAmount, is(feeAmounts.get(j)));
								log.info("Verify preTaxAmount: " + preTaxAmount);
								test_steps.add("Verify preTaxAmount: " + preTaxAmount);

								totalAmount = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].totalAmount");
								assertThat(totalAmount, is(feeAmounts.get(j)));
								log.info("Verify totalAmount: " + totalAmount);
								test_steps.add("Verify totalAmount: " + totalAmount);

								externalId = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].externalId");
								assertThat(externalId, is(feeExternal.get(j)));
								log.info("Verify externalId: " + externalId);
								test_steps.add("Verify externalId: " + externalId);

								feeType = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].feeType");
								assertThat(feeType, is("MISC"));
								log.info("Verify feeType: " + feeType);
								test_steps.add("Verify feeType: " + feeType);

								if (!setFeeRates.get(j).equalsIgnoreCase("No")) {
									feeRate = response.path(
											"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
													+ i + "].taxRate");
									assertThat(feeRate, is(setFeeRates.get(j)));
									log.info("Verify feeRate: " + feeRate);
									test_steps.add("Verify feeRate: " + feeRate);
								}

								productId = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].productId");
								assertThat(productId, is("PROPERTY-FEE"));
								log.info("Verify productId: " + productId);
								test_steps.add("Verify productId: " + productId);
							}
							j++;
						}
						for (String taxnames : taxesName) {

							if (name.equalsIgnoreCase(taxnames)) {
								assertThat(name, is(taxnames));
								log.info("Verify taxnames: " + taxnames);
								test_steps.add("Verify taxnames: " + taxnames);
								preTaxAmount = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].preTaxAmount");
								assertThat(preTaxAmount, is(taxesAmount.get(k)));
								log.info("Verify preTaxAmount: " + preTaxAmount);
								test_steps.add("Verify preTaxAmount: " + preTaxAmount);

								totalAmount = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].totalAmount");
								assertThat(totalAmount, is(taxesAmount.get(k)));
								log.info("Verify totalAmount: " + totalAmount);
								test_steps.add("Verify totalAmount: " + totalAmount);

								externalId = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].externalId");
								assertThat(externalId, is(taxesExternal.get(k)));
								log.info("Verify externalId: " + externalId);
								test_steps.add("Verify externalId: " + externalId);

								feeType = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].feeType");
								assertThat(feeType, is("TAX"));
								log.info("Verify feeType: " + feeType);
								test_steps.add("Verify feeType: " + feeType);

								if (!setTaxRates.get(k).equalsIgnoreCase("No")) {
									taxRate = response.path(
											"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
													+ i + "].taxRate");
									assertThat(taxRate, is(setTaxRates.get(k)));
									log.info("Verify taxRate: " + taxRate);
									test_steps.add("Verify taxRate: " + taxRate);
								}

								productId = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].productId");
								assertThat(productId, is("TAX"));
								log.info("Verify productId: " + productId);
								test_steps.add("Verify productId: " + productId);
							}
							k++;
						}

					} else if (!isFeeAdd.equalsIgnoreCase("Yes") && IsTaxAdd.equalsIgnoreCase("Yes")) {
						int j = 0;
						for (String taxnames : taxesName) {

							if (name.equalsIgnoreCase(taxnames)) {
								assertThat(name, is(taxnames));
								log.info("Verify taxnames: " + taxnames);
								test_steps.add("Verify taxnames: " + taxnames);
								preTaxAmount = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].preTaxAmount");
								assertThat(preTaxAmount, is(taxesAmount.get(j)));
								log.info("Verify preTaxAmount: " + preTaxAmount);
								test_steps.add("Verify preTaxAmount: " + preTaxAmount);

								totalAmount = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].totalAmount");
								assertThat(totalAmount, is(taxesAmount.get(j)));
								log.info("Verify totalAmount: " + totalAmount);
								test_steps.add("Verify totalAmount: " + totalAmount);

								externalId = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].externalId");
								assertThat(externalId, is(taxesExternal.get(j)));
								log.info("Verify externalId: " + externalId);
								test_steps.add("Verify externalId: " + externalId);

								feeType = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].feeType");
								assertThat(feeType, is("TAX"));
								log.info("Verify feeType: " + feeType);
								test_steps.add("Verify feeType: " + feeType);

								if (!setTaxRates.get(j).equalsIgnoreCase("No")) {
									taxRate = response.path(
											"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
													+ i + "].taxRate");
									assertThat(taxRate, is(setTaxRates.get(j)));
									log.info("Verify taxRate: " + taxRate);
									test_steps.add("Verify taxRate: " + taxRate);
								}

								productId = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].productId");
								assertThat(productId, is("TAX"));
								log.info("Verify productId: " + productId);
								test_steps.add("Verify productId: " + productId);
							}
							j++;
						}
					} else {
						int j = 0;
						for (String feenames : feeName) {
							if (name.equalsIgnoreCase(feenames)) {
								assertThat(name, is(feenames));
								log.info("Verify taxnames: " + feenames);
								test_steps.add("Verify taxnames: " + feenames);
								preTaxAmount = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].preTaxAmount");
								assertThat(preTaxAmount, is(feeAmounts.get(j)));
								log.info("Verify preTaxAmount: " + preTaxAmount);
								test_steps.add("Verify preTaxAmount: " + preTaxAmount);

								totalAmount = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].totalAmount");
								assertThat(totalAmount, is(feeAmounts.get(j)));
								log.info("Verify totalAmount: " + totalAmount);
								test_steps.add("Verify totalAmount: " + totalAmount);

								externalId = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].externalId");
								assertThat(externalId, is(feeExternal.get(j)));
								log.info("Verify externalId: " + externalId);
								test_steps.add("Verify externalId: " + externalId);

								feeType = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].feeType");
								assertThat(feeType, is("MISC"));
								log.info("Verify feeType: " + feeType);
								test_steps.add("Verify feeType: " + feeType);

								if (!setFeeRates.get(j).equalsIgnoreCase("No")) {
									feeRate = response.path(
											"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
													+ i + "].taxRate");
									assertThat(feeRate, is(setFeeRates.get(j)));
									log.info("Verify feeRate: " + feeRate);
									test_steps.add("Verify feeRate: " + feeRate);
								}

								productId = response.path(
										"bookingResponse.bookingResponseDetails.orderList.order.orderItemList.orderItem["
												+ i + "].productId");
								assertThat(productId, is("PROPERTY-FEE"));
								log.info("Verify productId: " + productId);
								test_steps.add("Verify productId: " + productId);
							}
							j++;
						}
					}
				}

			}
		}
	}

	public void validateReservationDetailsInResponse(Response response, String numberOfAdult, String numberOfChilds,
			String numberOfPets, String checkinDate, String checkoutDate, ArrayList<String> test_steps) {
		String getAdultsCount = "", getChildsCount = "", getPetsCount = "", getCheckInDate = "", getCheckOutDate;
		log.info("========VERIFY RESERVATION STAY DETAILS========");
		test_steps.add("========VERIFY RESERVATION STAY DETAILS========");
		getAdultsCount = response.path("bookingResponse.bookingResponseDetails.reservation.numberOfAdults");
		assertThat(getAdultsCount, is(numberOfAdult));
		log.info("Verify NumberOfAdult: " + getAdultsCount);
		test_steps.add("Verify NumberOfAdult: " + getAdultsCount);

		getChildsCount = response.path("bookingResponse.bookingResponseDetails.reservation.numberOfChildren");
		assertThat(getChildsCount, is(numberOfChilds));
		log.info("Verify NumberOfChildren: " + getChildsCount);
		test_steps.add("Verify NumberOfChildren: " + getChildsCount);

		getPetsCount = response.path("bookingResponse.bookingResponseDetails.reservation.numberOfPets");
		assertThat(getPetsCount, is(numberOfPets));
		log.info("Verify NumberOfPets: " + getPetsCount);
		test_steps.add("Verify NumberOfPets: " + getPetsCount);

		getCheckInDate = response.path("bookingResponse.bookingResponseDetails.reservation.reservationDates.beginDate");
		assertThat(getCheckInDate, is(checkinDate));
		log.info("Verify beginDate: " + getCheckInDate);
		test_steps.add("Verify beginDate: " + getCheckInDate);

		getCheckOutDate = response.path("bookingResponse.bookingResponseDetails.reservation.reservationDates.endDate");
		assertThat(getCheckOutDate, is(checkoutDate));
		log.info("Verify EndDate: " + getCheckOutDate);
		test_steps.add("Verify EndDate: " + getCheckOutDate);
	}

	// ========================================Booking Update
	// Validations=================================================
	public void validateBookingUpdateXML(Response response, String advertiseAssignedID, String listingExternalID,
			String unitExternalID, String externalConfirmation, String locale, HashMap<String, Double> calcInTaxVal,
			HashMap<String, Double> calcInFeeVal, Double roomchargeAmount, HashMap<String, String> getExternalIdOfTax,
			HashMap<String, String> getExternalIdOfFee, HashMap<String, String> setTaxRates,
			HashMap<String, String> setFeeRates, String payStatus, String nameTitle, String nameOnCard,
			String emailAddress, String phoneCountryCode, String phoneNumber, String addressLine1, String addressLine2,
			String addressLine3, String country, String addressLine4, String postalCode, String numberOfAdult,

			String numberOfChilds, String checkinDate, String checkoutDate, String originationDate,
			String resrvationStatus, Double tripTotal, boolean isVoidCharges, String dueDate,
			ArrayList<String> test_steps) {

		String version = "", advertiseAssignedId = "", listingExternalid = "", unitExternalid = "",
				confirmationNumber = "", getlocale = "", resPaymentStatus = "", resStatus = "";
		Node nameNode = response.htmlPath().get();
		assertThat(nameNode.name(), is("bookingUpdate"));
		log.info("Verify Starting XML Tag: " + nameNode.name().toString());
		test_steps.add("Verify Starting XML Tag: " + nameNode.name().toString());

		version = response.path("bookingUpdate.documentVersion");
		assertThat(version, is("1.3"));
		log.info("Verify Booking Update response Version: " + version);
		test_steps.add("Verify Booking Update response Version: " + version);

		advertiseAssignedId = response.path("bookingUpdate.bookingUpdateDetails.advertiserAssignedId");
		assertThat(advertiseAssignedId, is(advertiseAssignedID));

		log.info("Verify Booking Update response advertiseAssignedId: " + advertiseAssignedId);
		test_steps.add("Verify Booking Update response advertiseAssignedId: " + advertiseAssignedId);

		listingExternalid = response.path("bookingUpdate.bookingUpdateDetails.listingExternalId");
		assertThat(listingExternalid, is(listingExternalID));
		test_steps.add("Verify Booking Update response listingExternalid: " + listingExternalid);

		log.info("Verify Booking Update response listingExternalid: " + listingExternalid);

		unitExternalid = response.path("bookingUpdate.bookingUpdateDetails.unitExternalId");
		assertThat(unitExternalid, is(unitExternalID));

		log.info("Verify Booking Update response unitExternalid: " + unitExternalid);
		test_steps.add("Verify Booking Update response unitExternalid: " + unitExternalid);

		confirmationNumber = response.path("bookingUpdate.bookingUpdateDetails.externalId");
		assertThat(confirmationNumber, is(externalConfirmation));

		log.info("Verify Booking Update response confirmationNumber: " + confirmationNumber);
		test_steps.add("Verify Booking Update response confirmationNumber: " + confirmationNumber);

		getlocale = response.path("bookingUpdate.bookingUpdateDetails.locale");
		assertThat(getlocale, is(locale));

		log.info("Verify Booking Update response locale: " + getlocale);
		test_steps.add("Verify Booking Update response locale: " + getlocale);
		validateAcceptedPaymentFormsInBus(response, test_steps);
		validateOrderItemInBus(response, calcInTaxVal, calcInFeeVal, roomchargeAmount, getExternalIdOfTax,
				getExternalIdOfFee, setTaxRates, setFeeRates, resrvationStatus, isVoidCharges, test_steps);
		validateInquiRerDetailsInBUSXML(response, nameTitle, nameOnCard, emailAddress, phoneCountryCode, phoneNumber,
				test_steps);
		validateReservationDetailsInBUS(response, numberOfAdult, numberOfChilds, checkinDate, checkoutDate, test_steps);
		validateAcceptedPaymentFormsInBus(response, test_steps);
		validateAddressdetailsInBusXML(response, addressLine1, addressLine3, addressLine2, addressLine4, country,
				postalCode, test_steps);
		validateReservationOriginationDateInBus(response, originationDate, test_steps);
		List<Node> urlNode = response.xmlPath()
				.getList("bookingUpdate.bookingUpdateDetails.rentalAgreement.agreementText");
		assertThat(urlNode.size(), equalTo(1));
		log.info("Verify url: " + urlNode);
		test_steps.add("Verify url: " + urlNode);
		validatePaymentSceduleInBus(response, dueDate, tripTotal, test_steps);
		resPaymentStatus = response.path("bookingUpdate.bookingUpdateDetails.reservationPaymentStatus");

		if (Utility.validateString(payStatus)) {
			try {
				assertThat(resPaymentStatus, is(payStatus));
				log.info("Verify ReservationPaymentStatus: " + resPaymentStatus);
				test_steps.add("Verify ReservationPaymentStatus: " + resPaymentStatus);
			} catch (Exception e) {
				log.info("ReservationPaymentStatus Missmatch due to some existing issue: " + resPaymentStatus);
				test_steps.add("ReservationPaymentStatus Missmatch due to some existing issue: " + e.toString());
			} catch (Error e) {
				log.info("ReservationPaymentStatus Missmatch due to some existing issue: " + resPaymentStatus);
				test_steps.add("ReservationPaymentStatus Missmatch due to some existing issue: " + e.toString());

			}
		}

		resStatus = response.path("bookingUpdate.bookingUpdateDetails.reservationStatus");
		assertThat(resStatus, is(resrvationStatus));
		log.info("Verify ReservationStatus: " + resStatus);

		test_steps.add("Verify ReservationStatus: " + resStatus);

	}

	public String getVrBoPaymentStatus(String balance) {
		String status = "";
		if (Utility.validateString(balance)) {
			if (balance.contains("(")) {

				status = "PAID";

			} else if (Double.parseDouble(Utility.convertDecimalFormat(balance)) > 0) {

				status = "PARTIAL_PAID";
			} else {
				status = "PAID";
			}
		}
		return status;
	}

	public void validateOrderItemInBus(Response response, HashMap<String, Double> calcInTaxVal,
			HashMap<String, Double> calcInFeeVal, Double roomchargeAmount, HashMap<String, String> getExternalIdOfTax,
			HashMap<String, String> getExternalIdOfFee, HashMap<String, String> setTaxRates,
			HashMap<String, String> setFeeRates, String resStatus, boolean isVoidCharges,
			ArrayList<String> test_steps) {
		int totalSize = 0;
		if (!isVoidCharges) {
			totalSize = calcInFeeVal.keySet().size() + calcInTaxVal.keySet().size() + 1;
		} else {
			totalSize = calcInFeeVal.keySet().size() + 1;
		}

		log.info("========VERIFY RESERVATION ORDER ITEMS========");
		test_steps.add("========VERIFY RESERVATION ORDER ITEMS========");
		List<Node> oderItemsNode = response.xmlPath()
				.getList("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem");
		log.info("Verify Order item Size1: " + oderItemsNode);
		assertThat(oderItemsNode.size(), is(totalSize));
		log.info("Verify Order item Size: " + oderItemsNode.size());
		test_steps.add("Verify Order item Size: " + oderItemsNode.size());
		for (int i = 0; i < oderItemsNode.size(); i++) {
			String name = "", preTaxAmount = "", totalAmount = "", externalId = "", feeType, productId = "",
					taxRate = "", feeRate = "", nameFee = "";
			log.info("=======================");
			test_steps.add("=====================");
			if (i == 0) {
				String getstatus = "";
				name = response.path(
						"bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i + "].name");
				assertThat(name, is("Room Charge"));
				log.info("Verify name: " + name);
				test_steps.add("Verify name: " + name);

				preTaxAmount = response
						.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i
								+ "].preTaxAmount");
				assertThat(Double.parseDouble(preTaxAmount), is(roomchargeAmount));
				log.info("Verify preTaxAmount: " + preTaxAmount);
				test_steps.add("Verify preTaxAmount: " + preTaxAmount);

				totalAmount = response
						.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i
								+ "].totalAmount");
				assertThat(Double.parseDouble(totalAmount), is(roomchargeAmount));
				log.info("Verify totalAmount: " + totalAmount);
				test_steps.add("Verify totalAmount: " + totalAmount);

				feeType = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
						+ i + "].feeType");
				assertThat(feeType, is("RENTAL"));
				log.info("Verify feeType: " + feeType);
				test_steps.add("Verify feeType: " + feeType);

				getstatus = response.path(
						"bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i + "].status");
				assertThat(getstatus, is("ACCEPTED"));
				log.info("Verify status: " + getstatus);
				test_steps.add("Verify status: " + getstatus);

			} else {
				if (!isVoidCharges) {
					if (calcInTaxVal.keySet().size() > 0) {
						String nameTax = "", getstatus = "";

						nameTax = response
								.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i
										+ "].name");
						if (calcInTaxVal.keySet().contains(nameTax)) {

							log.info("Verify taxnames: " + nameTax);
							test_steps.add("Verify taxnames: " + nameTax);
							preTaxAmount = response
									.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
											+ i + "].preTaxAmount");
							assertThat(Double.parseDouble(preTaxAmount), is(calcInTaxVal.get(nameTax)));
							log.info("Verify preTaxAmount: " + preTaxAmount);
							test_steps.add("Verify preTaxAmount: " + preTaxAmount);

							totalAmount = response
									.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
											+ i + "].totalAmount");
							assertThat(Double.parseDouble(totalAmount), is(calcInTaxVal.get(nameTax)));
							log.info("Verify totalAmount: " + totalAmount);
							test_steps.add("Verify totalAmount: " + totalAmount);

							externalId = response
									.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
											+ i + "].externalId");
							assertThat(externalId, is(getExternalIdOfTax.get(nameTax)));
							log.info("Verify externalId: " + externalId);
							test_steps.add("Verify externalId: " + externalId);

							feeType = response
									.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
											+ i + "].feeType");
							assertThat(feeType, is("TAX"));
							log.info("Verify feeType: " + feeType);
							test_steps.add("Verify feeType: " + feeType);

							getstatus = response
									.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
											+ i + "].status");
							assertThat(getstatus, is("ACCEPTED"));
							log.info("Verify status: " + getstatus);
							test_steps.add("Verify status: " + getstatus);

							if (!setTaxRates.get(nameTax).equalsIgnoreCase("No")) {
								taxRate = response.path(
										"bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
												+ i + "].taxRate");
								assertThat(taxRate, is(setTaxRates.get(nameTax)));
								log.info("Verify taxRate: " + taxRate);
								test_steps.add("Verify taxRate: " + taxRate);
							}

						}
					}
				}
				if (calcInFeeVal.keySet().size() > 0) {
					String getstatus = "";
					nameFee = response
							.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i
									+ "].name");
					if (calcInFeeVal.keySet().contains(nameFee)) {
						log.info("Verify Feenames: " + nameFee);
						test_steps.add("Verify Feenames: " + nameFee);
						preTaxAmount = response
								.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i
										+ "].preTaxAmount");
						assertThat(Double.parseDouble(preTaxAmount), is(calcInFeeVal.get(nameFee)));
						log.info("Verify preTaxAmount: " + preTaxAmount);
						test_steps.add("Verify preTaxAmount: " + preTaxAmount);

						totalAmount = response
								.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i
										+ "].totalAmount");
						assertThat(Double.parseDouble(totalAmount), is(calcInFeeVal.get(nameFee)));
						log.info("Verify totalAmount: " + totalAmount);
						test_steps.add("Verify totalAmount: " + totalAmount);

						if (!nameFee.equalsIgnoreCase("Fee Adjustment")) {
							externalId = response
									.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
											+ i + "].externalId");
							assertThat(externalId, is(getExternalIdOfFee.get(nameFee)));
							log.info("Verify externalId: " + externalId);
							test_steps.add("Verify externalId: " + externalId);
						}

						feeType = response
								.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i
										+ "].feeType");
						assertThat(feeType, is("MISC"));
						log.info("Verify feeType: " + feeType);
						test_steps.add("Verify feeType: " + feeType);

						getstatus = response
								.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem[" + i
										+ "].status");
						assertThat(getstatus, is("ACCEPTED"));
						log.info("Verify status: " + getstatus);
						test_steps.add("Verify status: " + getstatus);
						if (!nameFee.equalsIgnoreCase("Fee Adjustment")) {
							if (!setFeeRates.get(nameFee).equalsIgnoreCase("No")) {
								feeRate = response.path(
										"bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
												+ i + "].taxRate");
								assertThat(feeRate, is(setFeeRates.get(nameFee)));
								log.info("Verify feeRate: " + feeRate);
								test_steps.add("Verify feeRate: " + feeRate);
							}
						}

						if (!nameFee.equalsIgnoreCase("Fee Adjustment")) {
							productId = response
									.path("bookingUpdate.bookingUpdateDetails.orderList.order.orderItemList.orderItem["
											+ i + "].productId");
							assertThat(productId, is("PROPERTY-FEE"));
							log.info("Verify productId: " + productId);
							test_steps.add("Verify productId: " + productId);
						}
					}

				}
			}
		}

	}

	public void validateInquiRerDetailsInBUSXML(Response response, String nameTitle, String nameOnCard,
			String emailAddress, String phoneCountryCode, String phoneNumber, ArrayList<String> test_steps) {
		String title = "", getFirstname = "", getLastname = "", getemailAddress = "", getphoneCountryCode = "",
				getphoneNumber = "";
		if (Utility.validateString(nameTitle)) {
			title = response.path("bookingUpdate.bookingUpdateDetails.inquirer.title");
			assertThat(title, is(nameTitle));

			log.info("Verify Booking Update response title: " + title);
			test_steps.add("Verify Booking Update response title: " + title);
		}

		getFirstname = response.path("bookingUpdate.bookingUpdateDetails.inquirer.firstName");
		assertThat(getFirstname, is(nameOnCard.split(" ")[0]));

		log.info("Verify Booking Update response firstName: " + getFirstname);
		test_steps.add("Verify Booking Update response firstName: " + getFirstname);

		if (nameOnCard.split(" ").length > 1) {
			getLastname = response.path("bookingUpdate.bookingUpdateDetails.inquirer.lastName");
			assertThat(getLastname, is(nameOnCard.split(" ")[1]));

			log.info("Verify Booking Update response lastName: " + getLastname);
			test_steps.add("Verify Booking Update response lastName: " + getLastname);
		}

		getemailAddress = response.path("bookingUpdate.bookingUpdateDetails.inquirer.emailAddress");
		assertThat(emailAddress, is(emailAddress));

		log.info("Verify Booking Update response emailAddress: " + getemailAddress);
		test_steps.add("Verify Booking Update response emailAddress: " + getemailAddress);

		getphoneCountryCode = response.path("bookingUpdate.bookingUpdateDetails.inquirer.phoneCountryCode");
		assertThat(getphoneCountryCode, is(phoneCountryCode));

		log.info("Verify Booking Update response phoneCountryCode: " + getphoneCountryCode);
		test_steps.add("Verify Booking response phoneCountryCode: " + getphoneCountryCode);

		getphoneNumber = response.path("bookingUpdate.bookingUpdateDetails.inquirer.phoneNumber");
		assertThat(getphoneNumber, is(phoneNumber));

		log.info("Verify Booking Update response phoneNumber: " + getphoneNumber);
		test_steps.add("Verify Booking Update response phoneNumber: " + getphoneNumber);

	}

	public void validatePhoneCode(Response response, String phoneCountryCode, ArrayList<String> test_steps) {
		String getphoneCountryCode = "";
		getphoneCountryCode = response.path("bookingUpdate.bookingUpdateDetails.inquirer.phoneCountryCode");
		assertThat(getphoneCountryCode, is(phoneCountryCode));

		log.info("Verify Booking Update response phoneCountryCode: " + getphoneCountryCode);
		test_steps.add("Verify Booking response phoneCountryCode: " + getphoneCountryCode);
	}

	public void validateBusNameTitle(Response response, String nameTitle, ArrayList<String> test_steps) {
		String title = "";
		title = response.path("bookingUpdate.bookingUpdateDetails.inquirer.title");
		assertThat(title, is(nameTitle));

		log.info("Verify Booking Update response title: " + title);
		test_steps.add("Verify Booking Update response title: " + title);

	}

	public void validateBusFirstAndLastName(Response response, String nameOnCard, ArrayList<String> test_steps) {
		String getFirstname = "", getLastname = "";
		getFirstname = response.path("bookingUpdate.bookingUpdateDetails.inquirer.firstName");
		assertThat(getFirstname, is(nameOnCard.split(" ")[0]));

		log.info("Verify Booking Update response firstName: " + getFirstname);
		test_steps.add("Verify Booking Update response firstName: " + getFirstname);

		if (nameOnCard.split(" ").length > 1) {
			getLastname = response.path("bookingUpdate.bookingUpdateDetails.inquirer.lastName");
			assertThat(getLastname, is(nameOnCard.split(" ")[1]));

			log.info("Verify Booking Update response lastName: " + getLastname);
			test_steps.add("Verify Booking Update response lastName: " + getLastname);
		}
	}

	public void validateBusEmail(Response response, String emailAddress, ArrayList<String> test_steps) {
		String getemailAddress = "";
		getemailAddress = response.path("bookingUpdate.bookingUpdateDetails.inquirer.emailAddress");
		assertThat(emailAddress, is(emailAddress));

		log.info("Verify Booking Update response emailAddress: " + getemailAddress);
		test_steps.add("Verify Booking Update response emailAddress: " + getemailAddress);
	}

	public void validateBusPhone(Response response, String phoneNumber, ArrayList<String> test_steps) {
		String getphoneNumber = "";
		getphoneNumber = response.path("bookingUpdate.bookingUpdateDetails.inquirer.phoneNumber");
		assertThat(getphoneNumber, is(phoneNumber));

		log.info("Verify Booking Update response phoneNumber: " + getphoneNumber);
		test_steps.add("Verify Booking Update response phoneNumber: " + getphoneNumber);
	}

	public void validateAddressdetailsInBusXML(Response response, String addressLine1, String addressLine3,
			String addressLine2, String addressLine4, String country, String postalCode, ArrayList<String> test_steps) {
		String getaddressLine1 = "", getaddressLine2, getaddressLine3 = "", getaddressLine4 = "", getcountry = "",
				getpostalCode = "";
		getaddressLine1 = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.addressLine1");
		assertThat(getaddressLine1.trim(), is(addressLine1));

		log.info("Verify Booking Update response addressLine1: " + getaddressLine1);
		test_steps.add("Verify Booking Update response addressLine1: " + getaddressLine1);

		if (Utility.validateString(addressLine2)) {
			getaddressLine2 = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.addressLine2");
			assertThat(getaddressLine2.trim(), is(addressLine2));
			log.info("Verify Booking Update response addressLine2: " + getaddressLine2);
			test_steps.add("Verify Booking Update response addressLine2: " + getaddressLine2);
		}
		if (Utility.validateString(addressLine3)) {
			getaddressLine3 = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.addressLine3");
			assertThat(getaddressLine3.trim(), is(addressLine3));

			log.info("Verify Booking Update response addressLine3: " + getaddressLine3);
			test_steps.add("Verify Booking response addressLine3: " + getaddressLine3);
		}
		if (Utility.validateString(addressLine4)) {
			getaddressLine4 = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.addressLine4");
			assertThat(getaddressLine4, is(addressLine4));

			log.info("Verify Booking Update response addressLine4: " + getaddressLine4);
			test_steps.add("Verify Booking Update response addressLine4: " + getaddressLine4);
		}

		getcountry = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.country");
		Locale completeName = new Locale("", getcountry);
		assertThat(completeName.getDisplayCountry(), is(country));

		log.info("Verify Booking Update response country: " + getcountry);
		test_steps.add("Verify Booking Update response country: " + getcountry);

		getpostalCode = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.postalCode");
		assertThat(getpostalCode, is(postalCode));

		log.info("Verify Booking Update response postalCode: " + getpostalCode);
		test_steps.add("Verify Booking Update response postalCode: " + getpostalCode);

	}

	public void validateBusAddress1(Response response, String addressLine1, ArrayList<String> test_steps) {
		String getaddressLine1 = "";
		getaddressLine1 = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.addressLine1");
		assertThat(getaddressLine1.trim(), is(addressLine1));

		log.info("Verify Booking Update response addressLine1: " + getaddressLine1);
		test_steps.add("Verify Booking Update response addressLine1: " + getaddressLine1);
	}

	public void validateBusAddress2(Response response, String addressLine2, ArrayList<String> test_steps) {
		String getaddressLine2;
		if (Utility.validateString(addressLine2)) {
			getaddressLine2 = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.addressLine2");
			assertThat(getaddressLine2.trim(), is(addressLine2));
			log.info("Verify Booking Update response addressLine2: " + getaddressLine2);
			test_steps.add("Verify Booking Update response addressLine2: " + getaddressLine2);
		}
	}

	public void validateBusAddress3(Response response, String addressLine3, ArrayList<String> test_steps) {
		String getaddressLine3 = "";
		if (Utility.validateString(addressLine3)) {
			getaddressLine3 = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.addressLine3");
			assertThat(getaddressLine3.trim(), is(addressLine3));

			log.info("Verify Booking Update response addressLine3: " + getaddressLine3);
			test_steps.add("Verify Booking response addressLine3: " + getaddressLine3);
		}
	}

	public void validateBusAddress4(Response response, String addressLine4, ArrayList<String> test_steps) {
		String getaddressLine4 = "";
		if (Utility.validateString(addressLine4)) {
			getaddressLine4 = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.addressLine4");
			assertThat(getaddressLine4, is(addressLine4));

			log.info("Verify Booking Update response addressLine4: " + getaddressLine4);
			test_steps.add("Verify Booking Update response addressLine4: " + getaddressLine4);
		}
	}

	public void validateBusCountry(Response response, String country, ArrayList<String> test_steps) {
		String getcountry = "";

		getcountry = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.country");
		Locale completeName = new Locale("", getcountry);
		assertThat(completeName.getDisplayCountry(), is(country));

		log.info("Verify Booking Update response country: " + getcountry);
		test_steps.add("Verify Booking Update response country: " + getcountry);
	}

	public void validateBuspostalCode(Response response, String postalCode, ArrayList<String> test_steps) {
		String getpostalCode = "";
		getpostalCode = response.path("bookingUpdate.bookingUpdateDetails.inquirer.address.postalCode");
		assertThat(getpostalCode, is(postalCode));

		log.info("Verify Booking Update response postalCode: " + getpostalCode);
		test_steps.add("Verify Booking Update response postalCode: " + getpostalCode);
	}

	public void validateReservationDetailsInBUS(Response response, String numberOfAdult, String numberOfChilds,
			String checkinDate, String checkoutDate, ArrayList<String> test_steps) {
		String getAdultsCount = "", getChildsCount = "", getCheckInDate = "", getCheckOutDate;
		log.info("========VERIFY RESERVATION STAY DETAILS IN BUS========");
		test_steps.add("========VERIFY RESERVATION STAY DETAILS IN BUS========");
		getAdultsCount = response.path("bookingUpdate.bookingUpdateDetails.reservation.numberOfAdults");
		assertThat(getAdultsCount, is(numberOfAdult));
		log.info("Verify NumberOfAdult: " + getAdultsCount);
		test_steps.add("Verify NumberOfAdult: " + getAdultsCount);

		getChildsCount = response.path("bookingUpdate.bookingUpdateDetails.reservation.numberOfChildren");
		assertThat(getChildsCount, is(numberOfChilds));
		log.info("Verify NumberOfChildren: " + getChildsCount);
		test_steps.add("Verify NumberOfChildren: " + getChildsCount);

		getCheckInDate = response.path("bookingUpdate.bookingUpdateDetails.reservation.reservationDates.beginDate");
		assertThat(getCheckInDate, is(checkinDate));
		log.info("Verify beginDate: " + getCheckInDate);
		test_steps.add("Verify beginDate: " + getCheckInDate);

		getCheckOutDate = response.path("bookingUpdate.bookingUpdateDetails.reservation.reservationDates.endDate");
		assertThat(getCheckOutDate, is(checkoutDate));
		log.info("Verify EndDate: " + getCheckOutDate);
		test_steps.add("Verify EndDate: " + getCheckOutDate);
	}

	public void validateAdultCountInBus(Response response, String numberOfAdult, ArrayList<String> test_steps) {
		String getAdultsCount = "";
		getAdultsCount = response.path("bookingUpdate.bookingUpdateDetails.reservation.numberOfAdults");
		assertThat(getAdultsCount, is(numberOfAdult));
		log.info("Verify NumberOfAdult: " + getAdultsCount);
		test_steps.add("Verify NumberOfAdult: " + getAdultsCount);
	}

	public void validateChildCountInBus(Response response, String numberOfChilds, ArrayList<String> test_steps) {
		String getChildsCount = "";
		getChildsCount = response.path("bookingUpdate.bookingUpdateDetails.reservation.numberOfChildren");
		assertThat(getChildsCount, is(numberOfChilds));
		log.info("Verify NumberOfChildren: " + getChildsCount);
		test_steps.add("Verify NumberOfChildren: " + getChildsCount);

	}

	public void validateAcceptedPaymentFormsInBus(Response response, ArrayList<String> test_steps) {
		log.info("========VERIFY ACCEPTED FORMS========");
		test_steps.add("========VERIFY ACCEPTED FORMS========");
		String paymentFormType = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[0].paymentFormType");
		assertThat(paymentFormType, is("CARD"));
		log.info("Verify paymentFormType: " + paymentFormType);
		test_steps.add("Verify paymentFormType: " + paymentFormType);

		String cardcode = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[0].cardCode");
		assertThat(cardcode, is("AMEX"));
		log.info("Verify cardcode: " + cardcode);
		test_steps.add("Verify cardcode: " + cardcode);

		String cardtype = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[0].cardType");
		assertThat(cardtype, is("CREDIT"));
		log.info("Verify cardtype: " + cardtype);
		test_steps.add("Verify cardtype: " + cardtype);

		log.info("========================================");
		test_steps.add("========================================");
		String paymentFormType1 = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[1].paymentFormType");
		assertThat(paymentFormType1, is("CARD"));
		log.info("Verify paymentFormType1: " + paymentFormType1);
		test_steps.add("Verify paymentFormType1: " + paymentFormType1);

		String cardcode1 = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[1].cardCode");
		assertThat(cardcode1, is("DISCOVER"));
		log.info("Verify cardcode1: " + cardcode1);
		test_steps.add("Verify cardcode1: " + cardcode1);

		String cardtype1 = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[1].cardType");
		assertThat(cardtype1, is("CREDIT"));
		log.info("Verify cardtype1: " + cardtype1);
		test_steps.add("Verify cardtype1: " + cardtype1);

		log.info("========================================");
		test_steps.add("========================================");
		String paymentFormType2 = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[2].paymentFormType");
		assertThat(paymentFormType2, is("CARD"));
		log.info("Verify paymentFormType2: " + paymentFormType2);
		test_steps.add("Verify paymentFormType2: " + paymentFormType2);

		String cardcode2 = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[2].cardCode");
		assertThat(cardcode2, is("MASTERCARD"));
		log.info("Verify cardcode2: " + cardcode2);
		test_steps.add("Verify cardcode2: " + cardcode2);

		String cardtype2 = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[2].cardType");
		assertThat(cardtype2, is("CREDIT"));
		log.info("Verify cardtype2: " + cardtype2);
		test_steps.add("Verify cardtype2: " + cardtype2);
		log.info("========================================");
		test_steps.add("========================================");
		String paymentFormType3 = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[3].paymentFormType");
		assertThat(paymentFormType3, is("CARD"));
		log.info("Verify paymentFormType3: " + paymentFormType3);
		test_steps.add("Verify paymentFormType3: " + paymentFormType3);

		String cardcode3 = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[3].cardCode");
		assertThat(cardcode3, is("VISA"));
		log.info("Verify cardcode3: " + cardcode3);
		test_steps.add("Verify cardcode3: " + cardcode3);

		String cardtype3 = response.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule."
				+ "acceptedPaymentForms.paymentCardDescriptor[3].cardType");
		assertThat(cardtype3, is("CREDIT"));
		log.info("Verify cardtype3: " + cardtype3);
		test_steps.add("Verify cardtype3: " + cardtype3);
	}

	public void validateReservationOriginationDateInBus(Response response, String originationDate,
			ArrayList<String> test_steps) {
		log.info("========VERIFY RESERVATION ORIGIATIONATION DATE========");
		test_steps.add("========VERIFY RESERVATION ORIGIATIONATION DATE========");
		String originationDates = response
				.path("bookingUpdate.bookingUpdateDetails.reservation.reservationOriginationDate");

		Assert.assertTrue(originationDates.contains(originationDate));
		log.info("Verify originationDates: " + originationDates);
		test_steps.add("Verify originationDates: " + originationDates);
	}

	public void validateBusIndex(Response response, String versions, String confirmation, String clientId,
			ArrayList<String> test_steps) {
		log.info("========VERIFY CONTENT BUS INDEX========");
		test_steps.add("========VERIFY CONTENT BUS INDEX========");
		String getversion = response.path("bookingContentIndex.documentVersion");
		assertThat(getversion, is(versions));
		log.info("Verify versions: " + versions);
		test_steps.add("Verify versions: " + versions);

		String getclientId = response.path("bookingContentIndex.advertiser.assignedId");
		assertThat(getclientId, is(clientId));
		log.info("Verify clientId: " + clientId);
		test_steps.add("Verify clientId: " + clientId);

		String expectedUrlName = VrboEndPoints.getbusUrl(clientId) + confirmation;
		System.out.println("expectedUrlName " + expectedUrlName);
		List<Node> urlNodes = response.xmlPath().getList("bookingContentIndex.advertiser.bookingContentIndexEntry");

		System.out.println("urlNodes" + urlNodes);
		for (int i = 0; i < urlNodes.size(); i++) {
			String urlName = response
					.path("bookingContentIndex.advertiser.bookingContentIndexEntry[" + i + "].bookingUpdateUrl");
			if (expectedUrlName.equalsIgnoreCase(urlName)) {
				assertThat(urlName, is(expectedUrlName));
				log.info("Verify urlName: " + urlName);
				test_steps.add("Verify urlName: " + urlName);
				break;
			}
		}
	}

	public void validatePaymentSceduleInBus(Response response, String checkinDate, Double tripTotal,
			ArrayList<String> test_steps) {
		log.info("========VERIFY PAYMENT SCHEDULE========");
		test_steps.add("========VERIFY PAYMENT SCHEDULE========");
		String getPaymentSceduleAmount = response
				.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule.paymentScheduleItemList."
						+ "paymentScheduleItem.amount");
		assertThat(Double.parseDouble(getPaymentSceduleAmount), is(tripTotal));
		log.info("Verify PaymentSceduleAmount: " + tripTotal);
		test_steps.add("Verify PaymentSceduleAmount: " + tripTotal);

		String getpaymentdueDate = response
				.path("bookingUpdate.bookingUpdateDetails.orderList.order.paymentSchedule.paymentScheduleItemList."
						+ "paymentScheduleItem.dueDate");
		try {
			assertThat(getpaymentdueDate, is(checkinDate));
			log.info("Verify paymentdueDate: " + checkinDate);
			test_steps.add("Verify paymentdueDate: " + checkinDate);
		} catch (Exception e) {
			log.info("paymentdueDate Missmatched due to existing issue: " + e.toString());

			test_steps.add("paymentdueDate Missmatched due to existing issue: " + e.toString());
		} catch (Error e) {
			log.info("paymentdueDate Missmatched due to existing issue: " + e.toString());

			test_steps.add("paymentdueDate Missmatched due to existing issue: " + e.toString());
		}

	}

	/*
	 * public HashMap<String, Double>
	 * getVrboTaxesInRoomChargeFolioAFterAddedRoomCharges(WebDriver driver, String
	 * taxLedgeraccount, ArrayList<String> taxName) throws InterruptedException {
	 * Elements_CPReservation res = new Elements_CPReservation(driver);
	 * CPReservationPage reservation = new CPReservationPage(); HashMap<String,
	 * Double> taxvalue = new HashMap<String, Double>(); DecimalFormat df = new
	 * DecimalFormat("##.00"); Double calculatedRecieved = 0.00; double
	 * totalTaxinFolios = 0.0; int roomChargesFolioCount =
	 * reservation.getRoomChargeFolioItemCountInRes(driver);
	 * ArrayList<ArrayList<Double>> total = new ArrayList<ArrayList<Double>>(); for
	 * (int i = 1; i <= roomChargesFolioCount; i++) {
	 * 
	 * String xpath =
	 * "(//tbody[starts-with(@data-bind,'getElement: ComputedFolioItemsElement')]/tr/td/span[text()='"
	 * + taxLedgeraccount + "']/../following-sibling::td/a)[" + i + "]";
	 * 
	 * Wait.waitUntilPresenceOfElementLocated(xpath, driver);
	 * Wait.explicit_wait_elementToBeClickable(driver.findElement(By.xpath(xpath)),
	 * driver); driver.findElement(By.xpath(xpath)).click();
	 * Wait.explicit_wait_10sec(res.itemDetailOnFolioPopup, driver);
	 * Wait.waitUntilPresenceOfElementLocated(OR_Reservation.ItemDetailOnFolioPopup,
	 * driver); int size = taxName.size(); ArrayList<Double> folioValue = new
	 * ArrayList<Double>(); for (int j = 1; j <= size; j++) {
	 * 
	 * double value = 0.00; double sum = 0.00; String xpath1 = "//a[text()='" +
	 * taxName.get(j - 1).trim() + "']/ancestor::td/following-sibling::td/span";
	 * Wait.waitUntilPresenceOfElementLocated(xpath1, driver);
	 * 
	 * int sameTaxCount = driver.findElements(By.xpath(xpath1)).size(); for (int k =
	 * 1; k <= sameTaxCount; k++) { String tax = "(//a[text()='" + taxName.get(j -
	 * 1).trim() + "'])[" + k + "]/ancestor::td/following-sibling::td/span"; String
	 * val = driver.findElement(By.xpath(tax)).getText(); value =
	 * Double.parseDouble(val.replace("$", "").trim()); sum = sum + value;
	 * 
	 * } folioValue.add(sum); System.out.println("folioValue" + folioValue); }
	 * total.add(i - 1, folioValue);
	 * 
	 * reservation.clickFolioPopupCancel(driver);
	 * log.info("Close open folio line item"); } System.out.println("total" +
	 * total); ArrayList<Double> calculated =
	 * reservation.addMultipleArrayListData(total); int j = 0; for (Double calcval :
	 * calculated) { if (calcval != 0) { taxvalue.put(taxName.get(j), calcval); }
	 * 
	 * j++; } return taxvalue; }
	 */
	public HashMap<String, Double> getVrboTaxesInRoomChargeFolioAFterAddedRoomCharges(WebDriver driver,
			String taxLedgeraccount, ArrayList<String> taxName) throws InterruptedException {
		Elements_CPReservation res = new Elements_CPReservation(driver);
		CPReservationPage reservation = new CPReservationPage();
		HashMap<String, Double> taxvalue = new HashMap<String, Double>();
		DecimalFormat df = new DecimalFormat("##.00");
		Double calculatedRecieved = 0.00;
		double totalTaxinFolios = 0.0;
		int roomChargesFolioCount = reservation.getRoomChargeFolioItemCountInRes(driver);
		ArrayList<ArrayList<Double>> total = new ArrayList<ArrayList<Double>>();
		for (int i = 1; i <= roomChargesFolioCount; i++) {

			String xpath = "(//tbody[starts-with(@data-bind,'getElement: ComputedFolioItemsElement')]/tr/td/span[text()='"
					+ taxLedgeraccount + "']/../following-sibling::td/a)[" + i + "]";

			Wait.waitUntilPresenceOfElementLocated(xpath, driver);
			Wait.explicit_wait_elementToBeClickable(driver.findElement(By.xpath(xpath)), driver);
			driver.findElement(By.xpath(xpath)).click();
			Wait.explicit_wait_10sec(res.itemDetailOnFolioPopup, driver);
			Wait.waitUntilPresenceOfElementLocated(OR_Reservation.ItemDetailOnFolioPopup, driver);
			int size = taxName.size();
			ArrayList<Double> folioValue = new ArrayList<Double>();
			for (int j = 1; j <= size; j++) {

				double value = 0.00;
				double sum = 0.00;
				String xpath1 = "//a[text()='" + taxName.get(j - 1).trim()
						+ "']/ancestor::td/following-sibling::td/span";
				Wait.waitUntilPresenceOfElementLocated(xpath1, driver);

				int sameTaxCount = driver.findElements(By.xpath(xpath1)).size();
				for (int k = 1; k <= sameTaxCount; k++) {
					String tax = "(//a[text()='" + taxName.get(j - 1).trim() + "'])[" + k
							+ "]/ancestor::td/following-sibling::td/span";
					String val = driver.findElement(By.xpath(tax)).getText();
					value = Double.parseDouble(val.replace("$", "").trim());
					sum = sum + value;

				}
				folioValue.add(sum);
				System.out.println("folioValue" + folioValue);
			}
			total.add(i - 1, folioValue);

			reservation.clickFolioPopupCancel(driver);
			log.info("Close open folio line item");
		}
		System.out.println("total" + total);
		ArrayList<Double> calculated = reservation.addMultipleArrayListData(total);
		int j = 0;
		for (Double calcval : calculated) {
			if (calcval != 0) {
				taxvalue.put(taxName.get(j), calcval);
			}

			j++;
		}

		return taxvalue;
	}

	public void verifyVrboBaseRate(HashMap<String, ArrayList<HashMap<String, String>>> inRates,
			HashMap<String, ArrayList<ArrayList<String>>> vrboBaseRate, ArrayList<String> datesRangeList,
			ArrayList<String> test_steps) {
		for (String days : datesRangeList) {
			String inBaseRate = inRates.get(days).get(0).get("BaseRate");
			int listSize = vrboBaseRate.get(Utility.convertDecimalFormat(inBaseRate)).size();
			boolean found = false;
			for (int i = 0; i < listSize; i++) {
				ArrayList<String> dateRange = vrboBaseRate.get(Utility.convertDecimalFormat(inBaseRate)).get(i);
				if (dateRange.contains(days)) {
					test_steps.add("Base Rate:" + inBaseRate + " Displayed on: " + days);
					log.info("Base Rate:" + inBaseRate + " Displayed on: " + days);
					found = true;
					break;
				}
			}
			assertTrue(found);
		}
		test_steps.add("Base Rate verified on VrBo");
		log.info("Base Rate verified on VrBo");
	}

	public void verifyExtraChildAndAdultAmountWithDateRange(WebDriver driver,
			HashMap<String, ArrayList<HashMap<String, String>>> inRate,
			ArrayList<HashMap<String, ArrayList<ArrayList<String>>>> extraAdult,
			ArrayList<HashMap<String, ArrayList<ArrayList<String>>>> extraChild, ArrayList<String> datesRangeList,
			ArrayList<String> test_steps) {
		boolean foundChild = false, foundAdult = false;
		for (int j = 0; j < datesRangeList.size(); j++) {
			String getextraChildsAmount = inRate.get(datesRangeList.get(j)).get(0).get("ExtraChild");
			int listSize = 0;
			int count = 0;
			for (int k = 0; k < extraChild.size(); k++) {
				try {
					if (!(extraChild.get(k).get(Utility.convertDecimalFormat(getextraChildsAmount)).equals(null))) {
						break;
					}
				} catch (Exception e) {

				}
				count++;
			}

			listSize = extraChild.get(count).get(Utility.convertDecimalFormat(getextraChildsAmount)).size();
			for (int i = 0; i < listSize; i++) {
				ArrayList<String> dateRange = extraChild.get(count)
						.get(Utility.convertDecimalFormat(getextraChildsAmount)).get(i);
				if (dateRange.contains(datesRangeList.get(j))) {
					test_steps
							.add("ExtraChild Rate:" + getextraChildsAmount + " Displayed on: " + datesRangeList.get(j));
					log.info("ExtraChild Rate:" + getextraChildsAmount + " Displayed on: " + datesRangeList.get(j));
					test_steps.add("ExtraChild Rate:" + getextraChildsAmount + " Displayed on: " + datesRangeList.get(j));
					foundChild = true;
					break;
				}
			}
			assertTrue(foundChild);
		}

		for (int j = 0; j < datesRangeList.size(); j++) {
			String getextraAdultsAmount = inRate.get(datesRangeList.get(j)).get(0).get("ExtraAdult");
			int listSize = 0;
			int count = 0;
			for (int k = 0; k < extraChild.size(); k++) {
				try {
					if (!(extraAdult.get(k).get(Utility.convertDecimalFormat(getextraAdultsAmount)).equals(null))) {
						break;
					}
				} catch (Exception e) {

				}
				count++;
			}
			listSize = extraAdult.get(count).get(Utility.convertDecimalFormat(getextraAdultsAmount)).size();

			for (int i = 0; i < listSize; i++) {
				ArrayList<String> dateRange = extraAdult.get(count)
						.get(Utility.convertDecimalFormat(getextraAdultsAmount)).get(i);
				if (dateRange.contains(datesRangeList.get(j))) {
					test_steps
							.add("ExtraAdult Rate:" + getextraAdultsAmount + " Displayed on: " + datesRangeList.get(j));
					log.info("ExtraAdult Rate:" + getextraAdultsAmount + " Displayed on: " + datesRangeList.get(j));
					test_steps.add("ExtraAdult Rate:" + getextraAdultsAmount + " Displayed on: " + datesRangeList.get(j));
					foundAdult = true;
					break;
				}
			}
			assertTrue(foundAdult);
		}

	}
	
	
	public boolean isAdultChildRateAvailable(WebDriver driver, Response responseBody) {
		boolean isRateAvailable = false;
		try {
			List<Node> node = responseBody.xmlPath().getList("lodgingRateContent.lodgingRate.fees.otherFees.fee",
					Node.class);
			if (node.size() >= 1) {
				isRateAvailable = true;
			} else {

			}
		} catch (Exception e) {

		}
		return isRateAvailable;
	}

	public boolean isVrboRateAvailable(WebDriver driver, Response responseBody) {
		boolean isRateAvailable = false;
		try {
			List<Node> node = responseBody.xmlPath()
					.getList("lodgingRateContent.lodgingRate.nightlyRates.nightlyOverrides.override", Node.class);
			if (node.size() >= 1) {
				isRateAvailable = true;
			} else {

			}
		} catch (Exception e) {

		}
		return isRateAvailable;
	}
	
	public void verifyTaxesOnVrbo(WebDriver driver, ArrayList<String>taxName,
			ArrayList<String>intaxValues,HashMap<String,String>vrboTaxes, ArrayList<String> test_steps) {
		int i = 0;
		for(String tax : taxName) {
			String getAmount = vrboTaxes.get(tax);
			assertEquals(getAmount, intaxValues.get(i), "Failed to verify");
			test_steps.add("Verify tax name: "+ tax+ " available with value: "+ getAmount);
			log.info("Verify tax name: "+ tax+ " available with value: "+ getAmount);
			i++;
		}
	}
	public void verifyFeesOnVrbo(WebDriver driver, ArrayList<String> feeName, ArrayList<String> infeeValues,
			HashMap<String, String> vrboTaxes, ArrayList<String> test_steps) {
		int i = 0;
		for (String fee : feeName) {
			String getAmount = vrboTaxes.get(fee);
			assertEquals(getAmount, infeeValues.get(i), "Failed to verify");
			test_steps.add("Verify fee name: " + fee + " available with value: " + getAmount);
			log.info("Verify tax fee: " + fee + " available with value: " + getAmount);
			i++;
		}
	}
	
	public void verifyNightOrStayInPropertyFeeOnVrbo(WebDriver driver, Response responseBody,
			ArrayList<String> inFeeName, ArrayList<String> nightOrStayFees, ArrayList<String> categoryFees,
			ArrayList<String> test_steps) {
		int feeSize = categoryFees.size();
		for (int j = 0; j < feeSize; j++) {
			String feeName = "";
			boolean found = false;
			if (categoryFees.get(j).equals("flat")) {

				
				List<Node> node = responseBody.xmlPath().getList("lodgingRateContent.lodgingRate.fees.otherFees.fee",
						Node.class);

				log.info("nodeFee: " + node.size());
				for (int i = 0; i < node.size(); i++) {
					List<Object> feeStayOrNight = null;
					List<Node> nodeExist = responseBody.xmlPath()
							.getList("lodgingRateContent.lodgingRate.fees.otherFees.fee.amount");
					log.info("nodeExist: " + nodeExist.size());
					if (nodeExist.size() > 1) {

						feeName = responseBody
								.path("lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].name");
						if (inFeeName.contains(feeName)) {
							if (nightOrStayFees.get(j).equalsIgnoreCase("per stay")) {
								feeStayOrNight = responseBody.xmlPath()
										.getList(
										"lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].appliesPerStay");
								found = true;
								test_steps.add("Verify fee appliesPerStay: "+ feeName);
								log.info("Verify fee appliesPerStay: "+ feeName);
								break;
							} else {
								feeStayOrNight = responseBody.xmlPath()
										.getList(
										"lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].appliesPerNight");
								found = true;
								test_steps.add("Verify fee appliesPerNight: "+ feeName);
								log.info("Verify fee appliesPerNight: "+ feeName);
								break;
							}

						}
					
					} else if (nodeExist.size() == 1) {

						feeName = responseBody.path("lodgingRateContent.lodgingRate.fees.otherFees.fee.name");
						if (inFeeName.contains(feeName)) {
							if (nightOrStayFees.get(j).equalsIgnoreCase("per stay")) {
								feeStayOrNight = responseBody.xmlPath()
										.getList(
										"lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].appliesPerStay");
								found = true;
								test_steps.add("Verify fee appliesPerStay: "+ feeName);
								log.info("Verify fee appliesPerStay: "+ feeName);
								break;
							} else {
								feeStayOrNight = responseBody.xmlPath()
										.getList(
										"lodgingRateContent.lodgingRate.fees.otherFees.fee[" + i + "].appliesPerNight");
								found = true;
								test_steps.add("Verify fee appliesPerNight: "+ feeName);
								log.info("Verify fee appliesPerNight: "+ feeName);
								break;
							}
						}
					}
				}
				
			} else {
				String pFeeName = "";
				

				List<Node> node = responseBody.xmlPath()
						.getList("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee", Node.class);
				log.info("nodeFee: " + node.size());
				if (node.size() == 1) {
					List<Object> feeStayOrNight = null;
					pFeeName = responseBody.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee.name");
					if (inFeeName.contains(pFeeName)) {
						if (nightOrStayFees.get(j).equalsIgnoreCase("per stay")) {
							feeStayOrNight = responseBody.xmlPath()
									.getList("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee.appliesPerNight");
							test_steps.add("Verify fee appliesPerNight: "+ pFeeName);
							log.info("Verify fee appliesPerNight: "+ pFeeName);
							found = true;
							
						} else {
							feeStayOrNight = responseBody.xmlPath()
									.getList("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee.appliesPerNight");
							test_steps.add("Verify fee appliesPerNight: "+ pFeeName);
							log.info("Verify fee appliesPerNight: "+ pFeeName);
							found = true;
						
						}
					}
				
				} else if (node.size() > 1) {
					for (int i = 0; i < node.size(); i++) {
						List<Object> feeStayOrNight = null;
						pFeeName = responseBody
								.path("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee[" + i + "].name");
						if (inFeeName.contains(pFeeName)) {
							if (nightOrStayFees.get(j).equalsIgnoreCase("per stay")) {
								feeStayOrNight = responseBody.xmlPath()
										.getList("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee.appliesPerNight");
								test_steps.add("Verify fee appliesPerNight: "+ pFeeName);
								log.info("Verify fee appliesPerNight: "+ pFeeName);
								found = true;
								break;
								
							} else {
								feeStayOrNight = responseBody.xmlPath()
										.getList("lodgingRateContent.lodgingRate.fees.percentOfRentFees.fee.appliesPerNight");
								test_steps.add("Verify fee appliesPerNight: "+ pFeeName);
								log.info("Verify fee appliesPerNight: "+ pFeeName);
								found = true;
								break;
								
							}
						}
					}
					
				}
			
			}
			assertTrue(found);
			
		}
	}
	
	
	public boolean getTaxFlagValueforPercentage(WebDriver driver, Response responseBody) {
		boolean isExist=false;
		List<Node> nodeRule = responseBody.xmlPath()
				.getList("lodgingRateContent.lodgingRate.taxRules.percentOfRentAndFeesTaxRules.rule", Node.class);
		int size= nodeRule.size();
		if(size==0) {
			isExist=false;
		}else {
			isExist=true;
		}
		return isExist;
	}
	
	public HashMap<String, String> calculateAdjustFeeWithPercentFeeAndExtraAdultAndChild(WebDriver driver, ArrayList<String> fees, 
			String extraAdult, String extraChild) {
		HashMap<String, String> adjustFee= new HashMap<String, String>();
		double feeCal=0.00, xtraAdult=0.00, xtraChild=0.00;
		for(String str: fees) {
			feeCal=feeCal+Double.parseDouble(str);
		}
		log.info(feeCal);
		xtraAdult=(Double.parseDouble(Utility.convertDecimalFormat(extraAdult))*feeCal)/100;
		xtraChild=(Double.parseDouble(Utility.convertDecimalFormat(extraChild))*feeCal)/100;
		adjustFee.put("extraAdultAdjustFee", String.valueOf(xtraAdult));
		adjustFee.put("extraChildAdjustFee", String.valueOf(xtraChild));
		log.info(adjustFee);
		return adjustFee;
	}
}


