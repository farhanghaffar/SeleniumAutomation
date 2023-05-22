package com.innroad.inncenter.endpoints;

public class SecurityEndPoint {
	
	
	public static String innroadUri = "https://app.qainnroad.com";
	public static String pmsinnroadUri = "https://pms-inncenter-api.qainnroad.com";
	public static String loginApi= innroadUri+"/innRoadServices/Login";
	public static String entitlementApi= innroadUri+"/Api/nextgen/GetEntitlementAndSecurityAccessMapping";
	public static String getMasterFolioAPI= innroadUri+"/folio/api/v2/booking/";
	public static String paymentMethodCreateAPI= innroadUri+"/folio/api/v2/paymentmethod.new";
	public static String toggleTokenAPI= innroadUri+"/InnRoad.Rest.Api/api/v1/client/";
/*	public static String bookingAPI= innroadUri+"/reservations/api/Booking/";*/
	public static String gtPropertyTimeZoneAPI= pmsinnroadUri+"/api/v1/TimeZone/GetPropertyTimeZone";
	public static String bookingAPI= innroadUri+"/reservations/api/Booking/property/";
	
	public static String genrateMasterFolioEndPoint(String reservationNo) {
		String test=getMasterFolioAPI + reservationNo + "/getMasterFolio";
		System.out.println(test);
		return test;
	}
	
	public static String genrateToggleTokenEndPoint(String clientID) {
		String test=toggleTokenAPI + clientID + "/toggle/token";
		System.out.println(test);
		return test;
	}
	
	public static String genratebookingEndPoint(String property, String reservationNo) {
		String test=bookingAPI + property+"/"+reservationNo;
		System.out.println(test);
		return test;
	}
	
	/*public static String genratebookingEndPoint(String reservationNo) {
		String test=bookingAPI + reservationNo;
		System.out.println(test);
		return test;
	}*/

}
