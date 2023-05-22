package com.innroad.inncenter.endpoints;
import java.io.File;


import com.innroad.inncenter.utils.Utility;

public class VrboEndPoints {
	
//	public static String vrboUri = "https://ota-api.qa3innroad.com";
//	public static String vrboUri = "https://ota-public-api.qa3innroad.com";
	public static String vrboUri = "https://ota-public-api.qainnroad.com";
	//public static String vrbouri1 = "https://ota-public-api.qa3innroad.com";

	static String[] urlArray= Utility.envURL.split("app.");
	static String url=urlArray[1].replace("/", "").trim();
	
	//public static String vrboUri = "https://ota-public-api."+url;

	public static String clientListingUrl= vrboUri+"/api/v1/homeaway/contentindex/clients/";
	
	public static String GetAdvertiser = vrboUri+"/api/v1/homeaway/contentindex/advertisers";

	public static String GetRates = vrboUri+ "/api/v1/homeaway/content/properties/";

	public static String bookingRequest = vrboUri+"/api/v1/homeaway/booking/create";
	
	public static String bookingUpdate = vrboUri + "/api/v1/homeaway/bookingcontent/clients/";
			//https://ota-public-api.qainnroad.com/api/v1/homeaway/bookingcontent/clients/3111/bookings/18507457
	
	public static String bookingUpdatedIndex = vrboUri + "/api/v1/homeaway/bookingcontentindex/clients/";
	
	public static String bookingcontentindexURL = vrboUri + "/api/v1/homeaway/bookingcontent/clients/";
			

	
	public static String genrateVrboClientLevelEndPoint(String clientID) {
		String test=clientListingUrl + clientID + "/listing";
		System.out.println(test);
		return clientListingUrl + clientID + "/listing";
	}
	
	public static String getUnitAvailabilityContentApi(String propertyId, String roomClassId, String ratePlanId) {
		return vrboUri + "/api/v1/homeaway/content/properties/"+ propertyId +"/roomclasses/"+ roomClassId +"/rateplans/"+ ratePlanId +"/unitavailability";		
	}
	
	public static String getRatePlanApi(String propertyId, String roomClassId) {
		return vrboUri + "/api/v1/homeaway/content/properties/"+ propertyId +"/roomclasses/"+ roomClassId +"/lodgingrate";
	}
	
		public static String generateRateEndPoint(String property, String roomclass) {
		String rateEndPoint=GetRates +property+"/roomclasses/"+ roomclass+"/lodgingrate";
		System.out.println(rateEndPoint);
		return rateEndPoint;
	}
	public String generateVrboBookingUpdateEndPoint(String clientId, String confirmationNum) {
		String bookingUpdateEndPoint = bookingUpdate + clientId + "/bookings/"+ confirmationNum;
		System.out.println(bookingUpdateEndPoint);
		return bookingUpdateEndPoint;
		
	}
	
	public static String generateVrboBusdIndexEndPoint(String clientId) {
		String bookingupdateEndpoint = bookingUpdatedIndex + clientId + "/bookings";
		System.out.println(bookingupdateEndpoint);
		return bookingupdateEndpoint;
	}
	public static String getbusUrl(String clientId) {
		String bookingcontentindex = bookingcontentindexURL + clientId + "/bookings/";
		return bookingcontentindex;
		
	}
	
}
