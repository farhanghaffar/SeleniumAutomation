package com.innroad.inncenter.pageobjects;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.annotations.Test;

import com.innroad.inncenter.endpoints.AirbnbEndPoints;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class AirbnbObjects {

	public static Logger log = Logger.getLogger("A1Service");

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

	public void airbnbReservationActions(String action, String confirmation, String startDate, String endDate,
			String fname, String lname, String listingID, String night, String email, String adultCount,
			String childCount, String phNum, String baseprice, String payoutamount, String token) {
		AirbnbEndPoints airbnbEnd = new AirbnbEndPoints();
		JSONObject requestParams = new JSONObject();
		HashMap<String, String> act = new HashMap<String, String>();
		if (action.equalsIgnoreCase("create")) {
			act.put(action, "reservation_acceptance_confirmation");
		} else if (action.equalsIgnoreCase("cancel")) {
			act.put(action, "reservation_cancellation_confirmation");
		} else if (action.equalsIgnoreCase("payment")) {
			act.put(action, "payout_notification");
		} else if (action.equalsIgnoreCase("modify")) {
			act.put(action, "reservation_alteration_confirmation");
		}
		Map<String, Object> resObj = new HashMap<String, Object>();
		Map<String, String> guestDetails = new HashMap<String, String>();
		JSONArray guestph = new JSONArray();
		requestParams.put("action", act.get(action));
		requestParams.put("reservation", resObj);
		resObj.put("guest_details", guestDetails);
		resObj.put("guest_phone_numbers", guestph);

		// ResDetails

		resObj.put("cancellation_policy_category", "super_strict_30"); // Cast
		resObj.put("confirmation_code", confirmation);
		resObj.put("end_date", endDate);
		resObj.put("expected_payout_amount_accurate", payoutamount);

		// guestDetails
		resObj.put("guest_email", email); // Cast
		resObj.put("guest_first_name", fname);
		//resObj.put("guest_id", "1603061");// 1603069
		resObj.put("guest_id", "1603069");// 1603061
		resObj.put("guest_last_name", lname);

		guestDetails.put("localized_description", "1 guest"); // Cast
		guestDetails.put("number_of_adults", adultCount);
		guestDetails.put("number_of_children", childCount);
		guestDetails.put("number_of_infants", "0");

		// guestph
		guestph.add(phNum);

		resObj.put("guest_preferred_locale", "en");
		resObj.put("host_currency", "USD");
		/*resObj.put("host_id", "38831");// 38836
*/		resObj.put("host_id", "38836");// 38836
		resObj.put("listing_base_price_accurate", baseprice);
		resObj.put("listing_cancellation_host_fee_accurate", "0.00");
		resObj.put("listing_cancellation_payout_accurate", "0.00");
		resObj.put("listing_cleaning_fee_accurate", "0.00");
		resObj.put("listing_host_fee_accurate", "0");

		resObj.put("listing_id", listingID);
		resObj.put("nights", night);
		resObj.put("number_of_guests", "1");
		resObj.put("occupancy_tax_amount_paid_to_host_accurate", "0.00");
		resObj.put("special_offer_id", null);
		resObj.put("start_date", startDate);

		resObj.put("status_type", "accept");
		resObj.put("thread_id", "435023593");
		resObj.put("total_paid_amount_accurate", payoutamount);

		System.out.println(requestParams);
		RestAssured.given().header("authorization", token).header("Accept-Language", "application/json")
				.contentType(ContentType.JSON).body(requestParams).when().post(airbnbEnd.clientListingUrl).then()
				.assertThat().statusCode(200).log().all();

	}

	public Response checkAvailability(String listingId, String startDate, String night, String token) {
		AirbnbEndPoints airbnbEnd = new AirbnbEndPoints();
		Response response = given().headers("authorization", token).accept("application/json")
				.get(airbnbEnd.getCheckAvailabilityEndPoint(listingId, startDate, night));
		return response;
	}

	public Response makeReservationCheck(String listingId, String adultCount, String childCount, String night,
			String startDate, String confirmation, String token) {
		AirbnbEndPoints airbnbEnd = new AirbnbEndPoints();
		JSONObject requestParams = new JSONObject();
		Response response;
		// Map<String, Object>resObj = new HashMap<String, Object>();
		Map<String, Object> guest_details = new HashMap<String, Object>();
		requestParams.put("guest_details", guest_details);
		requestParams.put("confirmation_code", confirmation);
		requestParams.put("listing_id", listingId);
		requestParams.put("listing_base_price", "");
		requestParams.put("nights", night);
		requestParams.put("number_of_guests", "");
		requestParams.put("start_date", startDate);
		requestParams.put("guest_id", "");

		guest_details.put("localized_description", "");
		guest_details.put("number_of_adults", adultCount);
		guest_details.put("number_of_children", childCount);
		guest_details.put("number_of_infants", "");

		requestParams.put("listingid_str", listingId);
		System.out.println(requestParams);
		response = RestAssured.given().header("authorization", token).header("Accept-Language", "application/json")
				.contentType(ContentType.JSON).body(requestParams).when().post(airbnbEnd.makeReservation);

		return response;

	}

	public Response updateReservationCheck(String listingId, String adultCount, String childCount, String night,
			String startDate, String confirmation, String token) {
		AirbnbEndPoints airbnbEnd = new AirbnbEndPoints();
		JSONObject requestParams = new JSONObject();
		Response response;
		// Map<String, Object>resObj = new HashMap<String, Object>();
		Map<String, Object> guest_details = new HashMap<String, Object>();
		requestParams.put("guest_details", guest_details);
		requestParams.put("confirmation_code", confirmation);
		requestParams.put("listing_id", listingId);
		requestParams.put("listing_base_price", "");
		requestParams.put("nights", night);
		requestParams.put("number_of_guests", "");
		requestParams.put("start_date", startDate);
		requestParams.put("guest_id", "");

		guest_details.put("localized_description", "");
		guest_details.put("number_of_adults", adultCount);
		guest_details.put("number_of_children", childCount);
		guest_details.put("number_of_infants", "");

		requestParams.put("listingid_str", listingId);
		response = RestAssured.given().header("authorization", token).header("Accept-Language", "application/json")
				.contentType(ContentType.JSON).body(requestParams).when()
				.put(airbnbEnd.updateReservationEndpoint(confirmation));
		return response;

	}

	public void verifyUpdateReservationCheck(String listingId, String adultCount, String childCount, String night,
			String startDate, String confirmation, String token, String expResult, ArrayList<String>test_steps) {
		try {
			AirbnbEndPoints airbnbEnd = new AirbnbEndPoints();
			JSONObject requestParams = new JSONObject();
			Response response = null;
			Map<String, Object> guest_details = new HashMap<String, Object>();
			requestParams.put("guest_details", guest_details);
			requestParams.put("confirmation_code", confirmation);
			requestParams.put("listing_id", listingId);
			requestParams.put("listing_base_price", "");
			requestParams.put("nights", night);
			requestParams.put("number_of_guests", "");
			requestParams.put("start_date", startDate);
			requestParams.put("guest_id", "");

			guest_details.put("localized_description", "");
			guest_details.put("number_of_adults", adultCount);
			guest_details.put("number_of_children", childCount);
			guest_details.put("number_of_infants", "");

			requestParams.put("listingid_str", listingId);
			response = RestAssured.given().header("authorization", token).header("Accept-Language", "application/json")
					.contentType(ContentType.JSON).body(requestParams).when()
					.put(airbnbEnd.updateReservationEndpoint(confirmation));
			 String result = response.path("ReservationResponse.Ok");
			
		      assertEquals(result.trim(), expResult);
		      test_steps.add("Verify the update endpoint returns: "+ result);

		      log.info("Verify the update endpoint returns: "+ result);

		} catch (Exception e) {

		}

	}

	public JSONObject contentPayLoad(String extconfirmation) {
		JSONObject requestParam = new JSONObject();
		requestParam.put("externalConfirmationCode", extconfirmation);
		return requestParam;

	}

	public String postReservationContent(String extconfirmation) {
		JSONObject requestParam = contentPayLoad(extconfirmation);
		System.out.println(requestParam.toString());
		Response response = given().body(requestParam).post(AirbnbEndPoints.ReservationContent);
		System.out.println(response.body().asString());
		String oo = response.body().asString();
		return oo;
	}

	public boolean isReservationExistForModification(String extconfirmation) throws ParseException {
		boolean isResExist = false;
		try {
		String response = postReservationContent(extconfirmation);
		System.out.println(response.toString());
		Object obj = new JSONParser().parse(response);
		JSONObject jobj = (JSONObject) obj;

		
			Map map = ((Map) jobj.get("reservationInfo"));
			Iterator<Map.Entry> itr = map.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry pair = itr.next();
				if (pair.getKey().equals("confirmationNumber")) {
					System.out.println("resConf# " + pair.getValue());
					isResExist = true;
				}
			}
		} catch (Exception e) {
		} catch (Error e) {
		}
		return isResExist;
	}
	public void airbnbReservationWithTax(String action, String confirmation, String startDate, String endDate,
			String fname, String lname, String listingID, String night, String email, String adultCount,
			String childCount, String phNum, String baseprice, String payoutamount, String token,ArrayList<String>taxAmount,
			ArrayList<String>taxName, ArrayList<String>taxType, String currency) {
		AirbnbEndPoints airbnbEnd = new AirbnbEndPoints();
		JSONObject requestParams = new JSONObject();
		HashMap<String, String> act = new HashMap<String, String>();
		if (action.equalsIgnoreCase("create")) {
			act.put(action, "reservation_acceptance_confirmation");
		} else if (action.equalsIgnoreCase("cancel")) {
			act.put(action, "reservation_cancellation_confirmation");
		} else if (action.equalsIgnoreCase("payment")) {
			act.put(action, "payout_notification");
		} else if (action.equalsIgnoreCase("modify")) {
			act.put(action, "reservation_alteration_confirmation");
		}
		Map<String, String> taxVal = null;
		JSONArray taxes = new JSONArray();
		
		Map<String, Object> resObj = new HashMap<String, Object>();
		Map<String, String> guestDetails = new HashMap<String, String>();
		JSONArray guestph = new JSONArray();
		requestParams.put("action", act.get(action));
		requestParams.put("reservation", resObj);
		resObj.put("guest_details", guestDetails);
		resObj.put("guest_phone_numbers", guestph);
		resObj.put("transient_occupancy_tax_details", taxes);
		
		 for(int i =0; i< taxName.size(); i++) {
			    taxVal = new HashMap<String, String>();
			    taxVal.put("amount_native", taxAmount.get(i));
			    taxVal.put("native_currency", currency);
			    taxVal.put("tax_type", taxType.get(i));
			    taxVal.put("name", taxName.get(i));
			    taxVal.put("amount_usd", taxAmount.get(i));
			    taxes.add(taxVal);
			    
			    }

		// ResDetails

		resObj.put("cancellation_policy_category", "super_strict_30"); // Cast
		resObj.put("confirmation_code", confirmation);
		resObj.put("end_date", endDate);
		resObj.put("expected_payout_amount_accurate", payoutamount);

		// guestDetails
		resObj.put("guest_email", email); // Cast
		resObj.put("guest_first_name", fname);
		resObj.put("guest_id", "1603069");// 1603069 1603061
		resObj.put("guest_last_name", lname);

		guestDetails.put("localized_description", "1 guest"); // Cast
		guestDetails.put("number_of_adults", adultCount);
		guestDetails.put("number_of_children", childCount);
		guestDetails.put("number_of_infants", "0");

		// guestph
		guestph.add(phNum);

		resObj.put("guest_preferred_locale", "en");
		resObj.put("host_currency", "USD");
		resObj.put("host_id", "38836");// 38836 38831
		resObj.put("listing_base_price_accurate", baseprice);
		resObj.put("listing_cancellation_host_fee_accurate", "0.00");
		resObj.put("listing_cancellation_payout_accurate", "0.00");
		resObj.put("listing_cleaning_fee_accurate", "0.00");
		resObj.put("listing_host_fee_accurate", "0");
		
		/*//----Additional Add
		resObj.put("pass_through_tax_expected_amount_accurate", "0.00");
		resObj.put("pass_through_tax_amount_paid_to_host_accurate", "0.00");
		resObj.put("airbnb_collected_tax_amount_accurate", "0.00");
		resObj.put("host_fee_base_accurate", "0.00");
		resObj.put("host_fee_vat_accurate", "0.00");
		resObj.put("expected_payout_amount_before_taxes_accurate", payoutamount);
		
		//----Additional Add
		*/
		
		resObj.put("listing_id", listingID);
		resObj.put("listing_id_str", listingID);
		resObj.put("nights", night);
		resObj.put("number_of_guests", "1");
		resObj.put("occupancy_tax_amount_paid_to_host_accurate", "0.00");
		resObj.put("special_offer_id", null);
		resObj.put("start_date", startDate);

		resObj.put("status_type", "accept");
		resObj.put("thread_id", "435023593");
		resObj.put("total_paid_amount_accurate", payoutamount);

		System.out.println(requestParams);
		RestAssured.given().header("authorization", token).header("Accept-Language", "application/json")
				.contentType(ContentType.JSON).body(requestParams).when().post(airbnbEnd.clientListingUrl).then()
				.assertThat().statusCode(200).log().all();

	}
}
