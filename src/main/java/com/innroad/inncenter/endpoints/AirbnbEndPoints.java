package com.innroad.inncenter.endpoints;

public class AirbnbEndPoints {
	public static String airbnbUri = "https://ota-public-api.qainnroad.com";
	public static String contentBookingUri = "http://be-core-api.qainnroad.com";
	
	
	public static String clientListingUrl= airbnbUri+"/api/v1/airbnb/webhook/notify";
	public static String checkAvailability = airbnbUri + "/api/v1/airbnb/webhook/reservation?";
	public static String makeReservation = airbnbUri + "/api/v1/airbnb/webhook/reservation";
	public static String updateReservation = airbnbUri + "/api/v1/airbnb/webhook/reservation/";
	public static String ReservationContent = contentBookingUri + "/api/v1/booking.getexternal";

	
	
	public String getCheckAvailabilityEndPoint(String listingId, String startDate, String night) {
		
		String endpoint = checkAvailability + "listing_id="+listingId+"&guest_id=&confirmation_code=&start_date="+startDate+"&nights="+night+"&number_of_guests=&listing_base_price=&listingid_str="+listingId+"";
		System.out.println("check availability endpoint + "+endpoint);
		return endpoint;
		
	}
	
	public String updateReservationEndpoint(String confirmation) {
		
		String endpoint = updateReservation + confirmation;
		return endpoint;
		
	}
}
