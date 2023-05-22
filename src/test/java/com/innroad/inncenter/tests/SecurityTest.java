package com.innroad.inncenter.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.endpoints.SecurityEndPoint;
import com.innroad.inncenter.serviceobject.SecurityObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class SecurityTest extends TestCore {
	private WebDriver driver = null;
	
	SecurityObjects securityObject = new SecurityObjects();
	SecurityEndPoint securityEndPoint= new SecurityEndPoint();
	CreateSecurityRequestBody body= new CreateSecurityRequestBody();
	HashMap<String, String> session= new HashMap<String, String>();
	HashMap<String, String> allOvertoken= new HashMap<String, String>();
	HashMap<String, String> gettoggletoken= new HashMap<String, String>();
	HashMap<String, String> getMasterFolioData= new HashMap<String, String>();
	HashMap<String, Response> bookingData= new HashMap<String, Response>();
	public static String test_name = null;
	
	ArrayList<String> testSteps = new ArrayList<>();
	String [] splitSession=null;
	String seassion=null, authToken=null;
	HashMap<String, String>  getPropertyTimeZone= new HashMap<String, String>();
	HashMap<String, Response> getMasterFolioResponse= new HashMap<String, Response>();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, securityexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	private  HashMap<String, String>  getSessionUsingLoginAPI(String ClientId,String UserName, String Password) {
		 HashMap<String, String>  loginApi=new  HashMap<String, String> ();
		 loginApi=securityObject.postRequestsForLogin(securityEndPoint.loginApi, body.loginJson(ClientId, UserName, Password));		
		return loginApi;
	}
	
	private String  authTokenUsinggetEntitlementAndSecurityAccessMapping(String iRegistrationNo) throws ParseException {
		String token=null;
		allOvertoken=securityObject.getEntitlementAndSecurityAccessMapping(securityEndPoint.entitlementApi, body.entitlementAndSecurityJson(iRegistrationNo, ""), splitSession[0]);
		token=allOvertoken.get("token");
		testSteps.add("EntitlementAndSecurityAccessMapping API :- " +securityEndPoint.entitlementApi);
		testSteps.add("Entitlement API to get authtoken---" + token);
		app_logs.info("Entitlement API to get authtoken---" + token);
		return token;
	}
	
	private String getToggle(String clientId, String session) {
		String sessionWithCoockie=null;
		gettoggletoken=securityObject.getToggleToken(securityEndPoint.genrateToggleTokenEndPoint(clientId),session);
		testSteps.add("Toggle API :- " +securityEndPoint.genrateToggleTokenEndPoint(clientId));
		testSteps.add("Toggle API to get Toggle---" + gettoggletoken.get("ir.pc.toggle"));
		app_logs.info("To get Toggle" +gettoggletoken.get("ir.pc.toggle"));
		testSteps.add("==============Make Coockie combination of Session and Toggle=============");
		app_logs.info("==============Make Coockie combination of Session and Toggle=============");
		getMasterFolioResponse= new HashMap<String, Response>();
		sessionWithCoockie=session+";"+securityProperties.getProperty("ToggleLabel")+gettoggletoken.get("ir.pc.toggle")+";"+securityProperties.getProperty("Gat");
		testSteps.add("Cookie is---" + sessionWithCoockie);
		app_logs.info("Cookie is---" + sessionWithCoockie);
		return sessionWithCoockie;
	}
	
	private HashMap<String, String> getpropertyTimeZone(String token, String session, String iRegistrationNo) {
		HashMap<String, String> getPropertyTimeZone= new HashMap<String, String>();
		Response response=securityObject.postRequestusingauthAndCoockie(securityEndPoint.gtPropertyTimeZoneAPI, token, session, body.entitlementAndSecurityJson(iRegistrationNo, ""));
		   getPropertyTimeZone=securityObject.getPropertyTimeZone(response);
			testSteps.add("Property is---" + getPropertyTimeZone);
			app_logs.info("Property is---" + getPropertyTimeZone);
			return getPropertyTimeZone;
	}
	@Test(dataProvider = "getData")
	public void verifyResponseofBookingAPI(String ClientId,String UserName, String Password, String iRegistrationNo, String cardNumber, String nameOnCard,
			String InvalidClientID) throws ParseException {
		String testName=null;
		 String test_description = null;
		String  test_catagory = null;
		testName= "verifyResponseofBookingAPI";
		test_description= "verifyResponseofBookingAPI";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		test_catagory = "Security";		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
	/*	HashMap<String, String>  loginApi=getSessionUsingLoginAPI(InvalidClientID,UserName,Password);
		splitSession= loginApi.get("Session").split(";");
		String session=splitSession[0];
		testSteps.add("Login API :- " +securityEndPoint.loginApi);
		testSteps.add("Get Session :- " + session);
		app_logs.info("Login API to get session--" + session);	
		String clientId=loginApi.get("ClientID");
		String authToken=authTokenUsinggetEntitlementAndSecurityAccessMapping(iRegistrationNo);
		String toggle= getToggle(clientId,session);
		String property=getpropertyTimeZone(authToken,session,iRegistrationNo).get("PropertyId");
		List<Header> headerList = new LinkedList<Header>();
		Header clientID = new Header("clientid",clientId);
		Header propertyID = new Header("propertyid",property);		
		Header authorization = new Header("authorization",authToken);
		Header Cookie = new Header("Cookie",toggle);
		headerList.add(authorization);
		headerList.add(Cookie);
		headerList.add(clientID);
		headerList.add(propertyID);	*/	
			String property="2835";
		List<Header> headerList = new LinkedList<Header>();
		Headers headersgetbookingApi = new Headers(headerList);
		//To get booking data of reservation
		bookingData=securityObject.getMethodWitoutBody(securityEndPoint.genratebookingEndPoint(property,"20261813"), headersgetbookingApi);
		app_logs.info(bookingData.get("Body").asString());
		Utility.verifyEquals(String.valueOf(bookingData.get("Body").getStatusCode()), "500", testSteps); 
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		//HashMap<String, String> contactData= securityObject.getbillingAddressfromJasonObject(bookingData.get("Body"));
		}catch (Exception e) {
			Utility.catchException(driver, e, "Booking API", "Booking", "Booking", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Booking API", "Booking", "Booking", testName, test_description,
					test_catagory, testSteps);
		}
	}
	
	@Test(dataProvider = "getData")
	public void verifyGetMasterFolioResponse(String ClientId,String UserName, String Password, String iRegistrationNo, String cardNumber, String nameOnCard,
			String InvalidClientID) {
		String testName=null;
		 String test_description = null;
			String  test_catagory = null;
		testName= "verifyGetMasterFolioResponse";
		test_description= "verifyGetMasterFolioResponse";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		test_catagory = "Security";		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
		HashMap<String, String>  loginApi=getSessionUsingLoginAPI(InvalidClientID,UserName,Password);
		splitSession= loginApi.get("Session").split(";");
		String session=splitSession[0];
		testSteps.add("Login API :- " +securityEndPoint.loginApi);
		testSteps.add("Get Session :- " + session);
		app_logs.info("Login API to get session--" + session);	
		String authToken=authTokenUsinggetEntitlementAndSecurityAccessMapping(iRegistrationNo);
		List<Header> headerList = new LinkedList<Header>();
		Header authorization = new Header("authorization",authToken);
		Header Cookie = new Header("Cookie",session);
		headerList.clear();
		headerList.add(authorization);
		headerList.add(Cookie);
		Headers headers = new Headers(headerList);
		//To getFolio Data of Reservation
		getMasterFolioResponse=securityObject.getMethodWitoutBody(securityEndPoint.genrateMasterFolioEndPoint("20261813"),headers);	
		testSteps.add("Folio API:- " + securityEndPoint.genrateMasterFolioEndPoint("20261813"));
		app_logs.info("\"Folio API:- " + securityEndPoint.genrateMasterFolioEndPoint("20261813"));
		testSteps.add("Folio Data of Reservation:- " +getMasterFolioResponse.get("Body").asString());
		app_logs.info("Folio Data of Reservation:- " +getMasterFolioResponse.get("Body").asString());
		testSteps.add("Folio Data of Reservation:- " +getMasterFolioResponse.get("Body").getStatusCode());
		app_logs.info("Folio Data of Reservation:- " +getMasterFolioResponse.get("Body").getStatusCode());
		//getMasterFolioData=securityObject.getDataMasterFolioApi(getMasterFolioResponse.get("Body"));
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Get Master Folio Response API", "MasterFolio", "MasterFolio", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Get Master Folio Response API", "MasterFolio", "MasterFolio", testName, test_description,
					test_catagory, testSteps);
		}
	}
	@Test(dataProvider = "getData")
	public void verifyResponseOfPaymentMethod(String ClientId,String UserName, String Password, String iRegistrationNo, String cardNumber, String nameOnCard,
			String InvalidClientID) throws Exception {	
		 String testName=null;
		 String test_description = null;
			String  test_catagory = null;
		 testName= "verifyResponseOfPaymentMethod";
		 test_description= "verifyResponseOfPaymentMethod";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		test_catagory = "Security";		
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}			
			HashMap<String, String>  loginApi=getSessionUsingLoginAPI(ClientId,UserName,Password);
			splitSession= loginApi.get("Session").split(";");
			String session=splitSession[0];
			testSteps.add("Login API :- " +securityEndPoint.loginApi);
			testSteps.add("Get Session :- " + session);
			app_logs.info("Login API to get session--" + session);	
			String clientId=loginApi.get("ClientID");
			String authToken=authTokenUsinggetEntitlementAndSecurityAccessMapping(iRegistrationNo);
			String toggle= getToggle(clientId,session);
			String property=getpropertyTimeZone(authToken,session,iRegistrationNo).get("PropertyId");			
			List<Header> headerList = new LinkedList<Header>();			
			Header authorization = new Header("authorization",authToken);
			Header Cookie = new Header("Cookie",toggle);
			Header clientID = new Header("clientid",clientId);
			Header propertyID = new Header("propertyid",property);		
			headerList.add(authorization);
			headerList.add(Cookie);
			headerList.add(clientID);
			headerList.add(propertyID);		
			Headers headersgetbookingApi = new Headers(headerList);
			//To get booking data of reservation
			bookingData=securityObject.getMethodWitoutBody(securityEndPoint.genratebookingEndPoint(property,"20267980"), headersgetbookingApi);
			app_logs.info(bookingData.get("Body").asString());
			HashMap<String, String> contactData= securityObject.getbillingAddressfromJasonObject(bookingData.get("Body"));
			headerList.clear();
			headerList.add(authorization);
			headerList.add(Cookie);
			Headers headers = new Headers(headerList);
			//To getFolio Data of Reservation
			getMasterFolioResponse=securityObject.getMethodWitoutBody(securityEndPoint.genrateMasterFolioEndPoint("20261813"),headers);	
			testSteps.add("Folio Data of Reservation:- " +getMasterFolioResponse.get("Body").asString());
			app_logs.info("Folio Data of Reservation:- " +getMasterFolioResponse.get("Body").asString());
			getMasterFolioData=securityObject.getDataMasterFolioApi(getMasterFolioResponse.get("Body"));			
			String yearDate=Utility.getFutureMonthAndYearForMasterCard();
			String fourDight= cardNumber.substring(cardNumber.length() - 4);
			testSteps.add("==============Add Payment Method to Reservation By another Client=============");
			app_logs.info("==============Add Payment Method to Reservation By another Client=============");
			//To add paymentMethod
			String paymentMethod= body.createPaymentMethodJson(getMasterFolioData.get("folioId"), securityProperties.getProperty("paymentMethodType"),
					getMasterFolioData.get("referenceTypeId"), contactData.get("billingAddress"), getMasterFolioData.get("ledgerAccountId"), nameOnCard, yearDate, cardNumber, fourDight);
			
			HashMap<String, String>  loginAPI=getSessionUsingLoginAPI(InvalidClientID,UserName,Password);
			String [] splitSession=null;
			splitSession= loginAPI.get("Session").split(";");
			String session1=splitSession[0];
			testSteps.add("Login API :- " +securityEndPoint.loginApi);
			testSteps.add("Get Session :- " + session1);
			app_logs.info("Login API to get session--" + session1);	
			String authToken1=authTokenUsinggetEntitlementAndSecurityAccessMapping(iRegistrationNo);
			
			Response response=securityObject.createPaymentMethod(authToken1, session1, securityEndPoint.paymentMethodCreateAPI, paymentMethod);		
			testSteps.add("Payment Method Data added Successfully:- " +response.asString());
			app_logs.info("Payment Method Data added Successfully:- " +response.asString());
			testSteps.add("Payment Method Data added Successfully:- " +response.statusCode());
			app_logs.info("Payment Method Data added Successfully:- " +response.statusCode());
		RetryFailedTestCases.count = Utility.reset_count;
		Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Payment Method", "Payment Method", "Payment Method", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Payment Method", "Payment Method", "Payment Method", testName, test_description,
					test_catagory, testSteps);
		}
		
	}
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("Security_Login", securityexcel);

	}

	/*@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.close();
		}*/
}
