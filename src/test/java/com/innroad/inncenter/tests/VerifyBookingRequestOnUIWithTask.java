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
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyBookingRequestOnUIWithTask extends TestCore{
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	private WebDriver driver = null;
	VrboObjects vrboObject= new VrboObjects();
	ReservationSearch rsvSearch= new ReservationSearch();
	CPReservationPage reservation = new CPReservationPage();	
	HashMap<String, String> data= new HashMap<String, String>();
	CreateBookingRequestXML bookingRequest= new CreateBookingRequestXML();
	HashMap<String, String> response= new HashMap<String, String>();
	Navigation navigation= new Navigation();
	RoomStatus roomStatus = new RoomStatus();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	TaskList tasklist = new TaskList();
	NewRoomClassesV2 roomClass = new NewRoomClassesV2();
	ReservationHomePage homePage = new ReservationHomePage();
	Admin admin = new Admin();
	Vrbo vrbo = new Vrbo();
	ArrayList<String> datesRangeList = new ArrayList<String>();
	
	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "Vrbo") 
	public void bookingRequest(String beginDate, String noOfNights, String originationDate,String className, String channel, String message,String title,String firstName, 
			String lastName,String email,String phoneNo,String address1,String address3, String address4, 
			String country,String adult, String child,String  externalId, String feeType, String chargeName,
			String amount, String productID, String paymentType,String postalCode,String cvv, 
			String  nameONCard, String cardNo, String cardCode, String cardType, String taskFors,String taskCategories,String taskTypes,
			String taskDetail, String taskRemark,String taskAssignees, String status, String state, String exCountry) {
		String	testName=null,startDate=null, endDate=null,  confirmationNo=null,expiryDate=null,
				startDate1=null, endDate1=null, advertiserId=null,classId=null,currency=null;
		data.clear();
		try {
			testName = "Verify Booking Request on UI with Task";
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");
			
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			
			driver = getDriver();
			loginOTA(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
			testSteps.add("<b>===== Getting Property =====</b>");
			app_logs.info("===== Getting Property=====");
			String property = homePage.getReportsProperty(driver, testSteps);
			testSteps.add("<b>Property Name : </b>" + property);
			app_logs.info("Property Name : " + property);

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
			roomClassIds = roomClass.getRoomClassDetails(driver, className, testSteps);
			classId = roomClassIds.get("roomClassId");
			testSteps.add("Room Class ID : " + classId);
			app_logs.info("Room Class ID " + classId);
			
			
			
			testSteps.add("====================Verify API End Point======================");
			ArrayList<String> checkInDates = new ArrayList<>();
			ArrayList<String> oDates = new ArrayList<>();
			if (!(Utility.validateInput(beginDate))) {
				startDate1 = Utility.getCurrentDate("dd/MM/yyyy");
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy");
				} else {
					endDate1 = Utility.parseDate(Utility.getDatePast_FutureDate(Integer.parseInt(noOfNights)),
							"MM/dd/yyyy", "dd/MM/yyyy");
				}
			} else {
				checkInDates = Utility.splitInputData(beginDate);
				startDate1 = checkInDates.get(0);
				if (!Utility.validateString(noOfNights)) {
					endDate1 = Utility.addDays(startDate1, 2);
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
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(startDate1, endDate1);
			 expiryDate=Utility.getFutureMonthAndYearForMasterCard();
			 File requestBody=bookingRequest.createBasicBookingRequest(testName,advertiserId, classId, channel,message, title, firstName, lastName, email, phoneNo, address1, 
						address3, address4, country, adult, child, startDate, endDate, originationDate, externalId, feeType, chargeName, amount, productID,
						startDate, paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath,currency);
			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--"+myRequest);
			
			response=vrboObject.postRequest(vrvoproperties.getProperty("BookingRequestToken"), VrboEndPoints.bookingRequest, myRequest);
			if(!response.get("BODY").contains("PROPERTY_NOT_AVAILABLE")&&!response.get("BODY").contains("SERVER_ERROR")) {
			testSteps.add("API End Point-" + response.get("APIURL"));
			testSteps.add("Requested Body-" +  "<a href='"+requestBody+"' target='_blank'>"
					+ "Click here to open : URL</a><br>");
			vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
    		 File responseBody=bookingRequest.getResponseXmlFile(response.get("BODY"),schemaFilesPath,testName,"bookingResponse");				 
			 testSteps.add("Response Body-" +  "<a href='"+responseBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");
			confirmationNo=bookingRequest.getValueOfXmlTags(response.get("BODY"), "externalId");
			testSteps.add("Confirmation No -" + confirmationNo);
			app_logs.info("Confirmation No -" + confirmationNo);
			}else if(response.get("StatusCode").equals("200")) {
				testSteps.add("API End Point-" + response.get("APIURL"));
				testSteps.add("Requested Body-" +  "<a href='"+requestBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
			    File responseBody=bookingRequest.getResponseXmlFile(response.get("BODY"),schemaFilesPath,testName,"bookingResponse");
			    testSteps.add("Response Body-" +  "<a href='"+responseBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				
			}
			else {
				app_logs.info("Failed Get Status Code: <b>" + response.get("StatusCode") +"</b>");
				testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") +"</b>");
			}
			
			
			
			
		}catch (Exception e) {
			Utility.catchExceptionOTA(e, test_description, test_catagory, "Booking Request", testName,
					test_description, test_catagory, testSteps);
			
		} catch (Error e) {
			Utility.catchErrorOTA(e, test_description, test_catagory, "Booking Request", testName,
					test_description, test_catagory, testSteps);			
		}
		try {
			testSteps.add("====================Verify Reservation ON UI======================");
			if(!response.get("BODY").contains("PROPERTY_NOT_AVAILABLE")&&!response.get("BODY").contains("SERVER_ERROR")) {
			//navigation.Reservation_Backward_4(driver);
				navigation.cpReservation_Backward(driver);
			rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
			amount=Utility.convertDecimalFormat(amount);
			app_logs.info(amount);
			
			testSteps.add("====================Verify Stay Info ON Reservation======================");
			reservation.verify_StayInfo(driver, testSteps, startDate1, endDate1, adult, child, className, amount);
			testSteps.add("====================Verify Guest Info ON Reservation======================");
			reservation.validateGuestInfo(driver, testSteps, title, firstName, lastName, phoneNo, "", email, exCountry, "", address1, "", "", state, address3, postalCode);
			String fourDigit=Utility.getCardNumberHidden(cardNo);
			app_logs.info(fourDigit);
			testSteps.add("====================Verify Payment Method ON Reservation======================");
			reservation.VerifyPaymentMethod(driver, testSteps, "MC", fourDigit, nameONCard, expiryDate);
			testSteps.add("====================Verify Marketing Info ON Reservation======================");
			reservation.verifyMarketingInfo(driver, "GDS", "GDS", "Vrbo", confirmationNo, testSteps);
			testSteps.add("====================Verify Notes ON Reservation======================");
			String description="Guest is bringing "+child+" pets.";
			reservation.verifyNotes(driver, "", "",description, testSteps);
			reservation.verifyNotes(driver, "", "", message, testSteps);
			testSteps.add("====================Verify Folio Line Item ON Reservation======================");
			reservation.click_Folio(driver, testSteps);
			String desc="Name: "+nameONCard;
			app_logs.info(desc);
			reservation.verifyFolioLineItem(driver, startDate1, "MC", desc, amount, testSteps);
			int days = datesRangeList.size();
			String oneDayRoomCharge = reservation.perDayRoomChargeIncludingExtraAdultChild("",
					"",amount, "", "", days);
			app_logs.info("Room Charge amount:- " +oneDayRoomCharge);
			reservation.includeTaxesinLineItems(driver, testSteps, false);
			for(int i=0;i<days;i++) {
				testSteps.add("====================Verify Room Charges on Folio Screen for Date "
						+ datesRangeList.get(i) + "======================");
				reservation.verifyFolioLineItem(driver, datesRangeList.get(i), chargeName, "HomeAway Rate",
						oneDayRoomCharge, testSteps);
			}
			app_logs.info("Verified Room Charge Amount");	
			testSteps.add("====================Verify History ON Reservation======================");
			reservation.click_History(driver, testSteps);
			reservation.verify_ReservationInHistoryTab(driver, testSteps, confirmationNo);
			}else {
				testSteps.add("Unable to Verify response Data on UI Due to - " + response.get("BODY"));
			}
			//reservation.closeReservationTab(driver);
			rsvSearch.closeReservation(driver);
			
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory,"Booking Request Verified on UI", testName, test_description, test_catagory, testSteps);
			
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Booking Request  Verified on UI", testName,
					test_description, test_catagory, testSteps);			
		}
		
		
		try {
			testSteps.add("====================Verify Task ON Reservation======================");
			if(!response.get("BODY").contains("PROPERTY_NOT_AVAILABLE")&&!response.get("BODY").contains("SERVER_ERROR")) {
				testSteps.add("====================Create Task======================");
			navigation.navigateGuestservice(driver);
			app_logs.info("Navigate To Guest Services");
			testSteps.add("Navigate To Guest Services ");
			roomStatus.verifyRoomStatusTabEnabled(driver, testSteps);
			roomStatus.verifiedGuestServicesDataLoadedOrNot(driver, testSteps);
			navigation.Nav_TaskList(driver);
			app_logs.info("Navigate To Task_List");
			testSteps.add("Navigate To Task List");
			getTest_Steps.clear();
			getTest_Steps = tasklist.clickAddTaskButton(driver);
			getTest_Steps = tasklist.CreateNewTask(driver, taskFors, taskCategories, taskTypes, taskDetail, taskRemark,
					taskAssignees, confirmationNo, getTest_Steps);
			testSteps.addAll(getTest_Steps);
			testSteps.add("Successfully Added Task with " +taskFors);
			app_logs.info("Successfully Added  Task with " +taskFors);	
			testSteps.add("====================Verify Task  on Reservation======================");
			navigation.Reservation_Backward_2(driver);
			app_logs.info("Navigate back to Reservation");
			testSteps.add("Navigate back to Reservation");
			rsvSearch.basicSearch_WithResNumber(driver, confirmationNo);
			app_logs.info("Successfully Searched & Opened Reservation " + confirmationNo);
			testSteps.add("Successfully Searched &Opened Reservation " + confirmationNo);
			getTest_Steps.clear();
			getTest_Steps = reservation.VerifyCreatedTask(driver, taskTypes, taskDetail, status, getTest_Steps);
			testSteps.addAll(getTest_Steps);
			}
			else {
				testSteps.add("Unable to Verify Task on Reservation Due to - " + response.get("BODY"));
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory,"Booking Request Verified Task On UI", testName, test_description, test_catagory, testSteps);
			
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Booking Request  Verified Task on UI", testName,
					test_description, test_catagory, testSteps);			
		}
	}



	
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("BookingRequest", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
