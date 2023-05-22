package com.innroad.inncenter.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.endpoints.VrboEndPoints;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyDistributiontakenout extends TestCore{

	
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	private WebDriver driver = null;
	Navigation navigation = new Navigation();
	Vrbo vrbo = new Vrbo();
	ReservationHomePage homePage = new ReservationHomePage();
	Admin admin = new Admin();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	CreateBookingRequestXML bookingRequest = new CreateBookingRequestXML();
	HashMap<String, String> response = new HashMap<String, String>();
	VrboObjects vrboObject = new VrboObjects();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "Vrbo")
	public void verifyDistributiontakenout(String propertyName,String beginDate, String noOfNights, String originationDate,
			String roomClassName, String channel, String message, String title, String firstName, String lastName,
			String email, String phoneNo, String address1, String address3, String address4, String country,
			String adult, String child, String externalId, String feeType, String chargeName, String amount,
			String productID, String paymentType, String postalCode, String cvv, String nameONCard, String cardNo,
			String cardCode, String cardType){
		String testName= null,startDate = null, endDate = null,  expiryDate = null,
				error = null,  classId = null, currency = null, advertiserId = null,
				startDate1 = null, endDate1 = null, property=null;
		try {
			testName = "Verify Distribution taken out";
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			ArrayList<String> checkInDates = new ArrayList<>();
			ArrayList<String> oDates = new ArrayList<>();
			if (!(Utility.validateInput(beginDate))) {
				startDate1 = Utility.getCurrentDate("dd/MM/yyyy");
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy");
				} else {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(Integer.parseInt(noOfNights)),
							"MM/dd/yyyy", "dd/MM/yyyy");
				}
			} else {
				checkInDates = Utility.splitInputData(beginDate);
				startDate1 = checkInDates.get(0);
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.addDays(startDate1, 1);
				} else {

					endDate1 = Utility.addDays(startDate1, Integer.parseInt(noOfNights));
				}
			}
			startDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "YYYY-MM-dd");
			endDate = Utility.parseDate(endDate1, "dd/MM/yyyy", "YYYY-MM-dd");

			if (!(Utility.validateInput(originationDate))) {
				originationDate = Utility.parseDate(startDate1, "dd/MM/yyyy", "yyyy-MM-dd'T'HH:mm:ss");
			} else {
				oDates = Utility.splitInputData(originationDate);
				originationDate = Utility.parseDate(oDates.get(0), "dd/MM/yyyy", "yyyy-MM-dd'T'HH:mm:ss");
			}

			originationDate = originationDate + "+00:00";
			app_logs.info(startDate);
			app_logs.info(endDate);
			app_logs.info(originationDate);
			
			driver = getDriver();
			loginOTA(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			
			
			testSteps.add("<b>===== Getting Property =====</b>");
			app_logs.info("===== Getting Property=====");
			property=homePage.getReportsProperty(driver, testSteps);
			testSteps.add("<b>Property Name : </b>" + property);
			app_logs.info("Property Name : " + property);
			
			testSteps.add("<b>===== Selecting Property =====</b>");
			app_logs.info("===== Selecting Property=====");
			navigation.selectProperty(driver, propertyName);
			Wait.waitforPageLoad(50, driver);
			testSteps.add("<b>Select Property  : </b>" + propertyName);
			app_logs.info("Select Property  : " + propertyName);
			
			

			navigation.admin(driver);
			navigation.clickonClientinfo(driver);
			testSteps.add("<b>===== Getting Currency =====</b>");
			app_logs.info("===== Getting Currency=====");
			admin.clickOnClient(driver, property);
			testSteps.add("Open Property : " + property);
			app_logs.info("Open Property : " + property);

			admin.clickClientOptions(driver, testSteps);
			currency = admin.getDefaultCurrency(driver);
			testSteps.add("Default Currency : " + currency);
			app_logs.info("Default Currency : " + currency);

			navigation.navigateToSetupfromRoomMaintenance(driver);
			testSteps.add("Click Setup");
			app_logs.info("Click Setup");
			navigation.clickVrboSetup(driver);
			testSteps.add("Click Vrbo Setup");
			app_logs.info("Click Vrbo Setup");

			testSteps.add("<b>===== Getting Advertisement ID =====</b>");
			app_logs.info("===== Getting Advertisement ID=====");

			advertiserId = vrbo.getVrboAdvertisementID(driver);
			testSteps.add("Advertisement ID : " + advertiserId);
			app_logs.info("Advertisement ID " + advertiserId);

			testSteps.add("<b>===== Getting Room Class ID =====</b>");
			app_logs.info("===== Getting Room Class ID=====");

			navigation.roomClass(driver);
			HashMap<String, String> roomClassIds = new HashMap<String, String>();
			roomClassIds = roomClass.getRoomClassDetails(driver, roomClassName, testSteps);
			classId = roomClassIds.get("roomClassId");
			testSteps.add("Room Class ID : " + classId);
			app_logs.info("Room Class ID " + classId);
			
			expiryDate=Utility.getFutureMonthAndYearForMasterCard();
			File requestBody=bookingRequest.createBasicBookingRequest(testName,advertiserId, classId, channel,message, title, firstName, lastName, email, phoneNo, address1, 
					address3, address4, country, adult, child, startDate, endDate, originationDate, externalId, feeType, chargeName, amount, productID,
					startDate, paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath,currency);
				
		String myRequest = bookingRequest.getFileContent(requestBody);
		app_logs.info("Request--"+myRequest);	
		response=vrboObject.postRequest(vrvoproperties.getProperty("BookingRequestToken"), VrboEndPoints.bookingRequest, myRequest);
		if(response.get("StatusCode").equals("200")) {
			testSteps.add("API End Point-" + response.get("APIURL"));
			testSteps.add("Requested Body-" +  "<a href='"+requestBody+"' target='_blank'>"
					+ "Click here to open : URL</a><br>");
			vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
		    File responseBody=bookingRequest.getResponseXmlFile(response.get("BODY"),schemaFilesPath,testName,"bookingResponse");
		    testSteps.add("Response Body-" +  "<a href='"+responseBody+"' target='_blank'>"
					+ "Click here to open : URL</a><br>");		
		    error=bookingRequest.getValueOfXmlTags(response.get("BODY"), "errorType");
			app_logs.info("Error Is -" + error);
			String errorType="SERVER_ERROR";
			Utility.verifyText(error.toLowerCase().trim(), errorType.toLowerCase().trim(), "Verify Error", testSteps, app_logs);
		}
		else {
			app_logs.info("Failed Get Status Code: " + response.get("StatusCode") +" Unable to Verify Card Declined as Merchant Services not available");
			testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") +" Unable to Verify Card Declined as Merchant Services not available </b>");
		}
		
		//navigation.Reservation_Backward_4(driver);
		navigation.cpReservation_Backward(driver);
		navigation.selectProperty(driver, property);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Distribution taken out", testName,
					test_description, test_catagory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Distribution taken out", testName,
					test_description, test_catagory, testSteps);
		}
		
	
	}
	
	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("Distributiontakenout", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
