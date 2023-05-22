package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaxesAndFee;
import com.innroad.inncenter.pageobjects.UnitAvailabilityContent;
import com.innroad.inncenter.pageobjects.Vrbo;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import io.restassured.response.Response;
import java.lang.System;
import java.text.ParseException;

public class VerifyUnitAvailabilityForInboundReservation extends TestCore {

	public static String testName = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> getDates = null;

	private WebDriver driver = null;

	HashMap<String, String> data = new HashMap<String, String>();
	CreateBookingRequestXML bookingRequest = new CreateBookingRequestXML();
	HashMap<String, Response> responses = new HashMap<String, Response>();
	RoomStatus roomStatus = new RoomStatus();
	TaxesAndFee taxFee = new TaxesAndFee();
	TaskList tasklist = new TaskList();
	Admin admin = new Admin();
	Vrbo vrbo = new Vrbo();

	CPReservationPage reservationPage = new CPReservationPage();
	Navigation navigation = new Navigation();
	RatesGrid ratesGrid = new RatesGrid();
	NightlyRate nightlyRate = new NightlyRate();
	NewRoomClassesV2 newRoomClassV2 = new NewRoomClassesV2();
	ReservationSearch reservationSearch = new ReservationSearch();
	Account CreateTA = new Account();
	ReservationHomePage homePage = new ReservationHomePage();
	Tapechart tapeChart = new Tapechart();
	Rate rate = new Rate();
	Groups group = new Groups();
	AdvGroups advanceGroup = new AdvGroups();
	VrboObjects vrboObject = new VrboObjects();
	Folio folio = new Folio();
	DerivedRate derivedRate = new DerivedRate();
	UnitAvailabilityContent unitAvailable = new UnitAvailabilityContent();
	String endPointName = "unitAvailabilityContent";
	String getUnitAvailabilityContentApi = null;
	Response response = null;

	String advertiserId = null, currency = null;
	String propertyId = "";
	String roomClassId = "";
	String ratePlanId = "";
	String randomString = Utility.GenerateRandomNumber();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	 
	@Test(dataProvider = "getData", groups = "Vrbo")
	public void verifyUnitAvailabilityForInboundReservation(String caseType, String ratePlanName, String beginDate, String noOfNights, 
			String originationDate, String roomClassName, String channel, String message, String title, String firstName, String lastName, String email, String phoneNo, String address1, String address3, String address4, String country, String adult, String child, String externalId, String feeType, String chargeName, String amount, String productID, String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, 
			String cardCode, String cardType, String subject, String detail) throws Exception {
		
		app_logs.info("DELIM : " + Utility.DELIM);
		testName = "VerifyUnitAvailabilityForInboundReservation-" + caseType;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		String defaultDateFormat = "MM/dd/yyyy";
		String dateFormatForRateGrid = "MMM/d/yyyy";

		String timeZone = "";
		String roomClassAbbreviation = "";
		System.out.println(vrboToken);
		
		try {
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
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to logged into app", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to logged into app", testName,
					testDescription, testCategory, testSteps);

		}

		
		  try { testSteps.add( "<b>" +
		  "===== GET TIMEZONE, RATEPLANID, ROOMCLASSID AND PROPERTYID ====="
		  .toUpperCase() + "</b>"); app_logs.
		  info("===== GET TIMEZONE, RATEPLANID, ROOMCLASSID AND PROPERTYID ====="
		  .toUpperCase());
		  
		  testSteps.add("<b>===== GETTING PROPERTY NAME ===== </b>");
		  
		  String property = homePage.getReportsProperty(driver, testSteps);
		  testSteps.add("<b>Property Name : </b>" + property);
		  app_logs.info("Property Name : " + property);
		  
		  navigation.admin(driver); navigation.clickonClientinfo(driver);
		  testSteps.add("<b>===== Getting Currency =====</b>");
		  app_logs.info("===== Getting Currency====="); admin.clickOnClient(driver,
		  property); testSteps.add("Open Property : " + property);
		  app_logs.info("Open Property : " + property);
		  
		  admin.clickClientOptions(driver, testSteps); currency =
		  admin.getDefaultCurrency(driver); testSteps.add("Default Currency : " +
		  currency); app_logs.info("Default Currency : " + currency);
		  
		  navigation.navigateToSetupfromRoomMaintenance(driver);
		  testSteps.add("Click Setup"); app_logs.info("Click Setup");
		  
		  navigation.clickVrboSetup(driver); testSteps.add("Click Vrbo Setup");
		  app_logs.info("Click Vrbo Setup");
		  
		  testSteps.add("<b>===== Getting Advertisement ID =====</b>");
		  app_logs.info("===== Getting Advertisement ID=====");
		  
		  advertiserId = vrbo.getVrboAdvertisementID(driver);
		  testSteps.add("Advertisement ID : " + advertiserId);
		  app_logs.info("Advertisement ID " + advertiserId);
		  
		  testSteps.add("<b>===== GETTING PROPERTY ID =====</b>");
		  
		  navigation.Properties(driver); testSteps.add("Navigat Properties");
		  
		  propertyId = navigation.getPropertyId(driver, property);
		  testSteps.add("<b>Property Id : </b>" + propertyId);
		  
		  testSteps.add("<b>===== GETTING PROPERTY TIME ZONE =====</b>");
		  
		  navigation.open_Property(driver, testSteps, property);
		  testSteps.add("Open Property : " + property);
		  
		  navigation.click_PropertyOptions(driver, testSteps); String navTimeZone =
		  navigation.get_Property_TimeZone(driver); app_logs.info("Time Zone " +
		  navTimeZone);
		  
		  testSteps.add("Property Time Zone " + navTimeZone); timeZone =
		  Utility.getTimeZone(navTimeZone);
		  testSteps.add("<b>Reformatted Time Zone : </b>" + timeZone);
		  
		  testSteps.add("===== GETTING ROOM CLASS ID =====</b>");
		  
		  navigation.roomClass(driver); testSteps.add("Navigate Room Class");
		  
		  HashMap<String, String> getMap = newRoomClassV2.getRoomClassDetails(driver,
		  roomClassName, testSteps); testSteps.add("Getting RoomClass Details '" +
		  getMap + "'"); roomClassAbbreviation = getMap.get("roomClassAbbreviation");
		  roomClassId = getMap.get("roomClassId"); 

		  navigation.reservationBackward3(driver);
		  testSteps.add("Back to reservation");
		  

		  } catch (Exception e) { e.printStackTrace(); if (Utility.reTry.get(testName)
		  == Utility.count) { RetryFailedTestCases.count = Utility.reset_count;
		  Utility.AddTest_IntoReport(testName, testDescription, testCategory,
		  testSteps); Utility.updateReport(e,
		  "Failed to get propertyId, timeZone and roomclassId", testName,
		  "getPreconditions", driver);
		  
		  } else { assertTrue(false); } } catch (Error e) { e.printStackTrace();
		  if (Utility.reTry.get(testName) == Utility.count) {
		  RetryFailedTestCases.count = Utility.reset_count;
		  Utility.AddTest_IntoReport(testName, testDescription, testCategory,
		  testSteps); Utility.updateReport(e,
		  "Failed to get propertyId, timeZone and roomclassId", testName,
		  "getPreconditions", driver); } else { assertTrue(false); } }

		try {
			testSteps.add("<b>===== GETTING RATE PLAN ID =====</b>");

			String getRatePlanApi = VrboEndPoints.getRatePlanApi(propertyId, roomClassId);
			vrboObject.verifyVrboApiStatusCode("Rate Plan Id ", getRatePlanApi, vrboToken, statusCode200, testSteps);
			response = vrboObject.getApiResponse(getRatePlanApi, vrboToken);
			ratePlanId = vrboObject.getAttributesValue(response, "lodgingRateContent.lodgingRate.externalId");
			app_logs.info("lodgingRateContent.lodgingRate.externalId : " + ratePlanId);
			testSteps.add("<b>Rate Plan Id : </b>" + ratePlanId);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to get ratePlanId", testName,
					testDescription, testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to get ratePlanId", testName,
					testDescription, testCategory, testSteps);
		}


		getUnitAvailabilityContentApi = VrboEndPoints.getUnitAvailabilityContentApi(propertyId, roomClassId,
				ratePlanId);
		app_logs.info("Endpoint Name: " + endPointName);
		app_logs.info("unitAvailabilityContent Url : " + getUnitAvailabilityContentApi);


		try {
			if (caseType.equalsIgnoreCase("VerifyAvailabiltyForVrboReservation")) {
				verifyAvailablityVrboReservation(roomClassId, advertiserId, currency,
						defaultDateFormat, dateFormatForRateGrid, timeZone, ratePlanName,  beginDate,  noOfNights, 
						originationDate,  roomClassName,  channel,  message,  title,  firstName,  lastName,  email,  phoneNo,  address1,  address3,  address4,  country,  adult,  child,  externalId,  feeType,  chargeName,  
						amount,  productID,  paymentType,  postalCode,  cvv,  nameONCard,  cardNo, 
						cardCode,  cardType,  subject,  detail);
			} else if (caseType.equalsIgnoreCase("VerifyAvailabiltyForVrboReservationVirtualRoomClass")) {
				verifyAvailablityVrboReservation(roomClassId, advertiserId,
						currency, defaultDateFormat, dateFormatForRateGrid, timeZone, ratePlanName,  beginDate,  noOfNights, 
						originationDate,  roomClassName,  channel,  message,  title,  firstName,  lastName,  email,  phoneNo,  address1,  address3,  address4,  country,  adult,  child,  externalId,  feeType,  chargeName,
						amount,  productID,  paymentType,  postalCode,  cvv,  nameONCard,  cardNo, 
						cardCode,  cardType,  subject,  detail);
			} else if (caseType.equalsIgnoreCase("VerifyAvailablityForCancelVrboReservation")) {
				verifyAvailablityCancelVrboReservation(roomClassId, advertiserId, currency,
						defaultDateFormat, dateFormatForRateGrid, timeZone,
						ratePlanName,  beginDate,  noOfNights, 
						originationDate,  roomClassName,  channel,  message,  title,  firstName,  lastName,  email,  phoneNo,  address1,  address3,  address4,  country,  adult,  child,  externalId,  feeType,  chargeName,
						amount,  productID,  paymentType,  postalCode,  cvv,  nameONCard,  cardNo, 
						cardCode,  cardType,  subject,  detail);
			} else if (caseType.equalsIgnoreCase("VerifyAvailablityForNoshowVrboReservation")) {
				verifyAvailablityNoshowVrboReservation(roomClassId, advertiserId, currency,
						defaultDateFormat, dateFormatForRateGrid, timeZone,
						ratePlanName,  beginDate,  noOfNights, 
						originationDate,  roomClassName,  channel,  message,  title,  firstName,  lastName,  email,  phoneNo,  address1,  address3,  address4,  country,  adult,  child,  externalId,  feeType,  chargeName,
						amount,  productID,  paymentType,  postalCode,  cvv,  nameONCard,  cardNo, 
						cardCode,  cardType,  subject,  detail);
			} else if (caseType.equalsIgnoreCase("VerifyAvailablityForCheckoutVrboReservation")) {
				verifyAvailablityCheckoutVrboReservation(roomClassId, advertiserId,
						currency, defaultDateFormat, dateFormatForRateGrid, timeZone,
						ratePlanName,  beginDate,  noOfNights, 
						originationDate,  roomClassName,  channel,  message,  title,  firstName,  lastName,  email,  phoneNo,  address1,  address3,  address4,  country,  adult,  child,  externalId,  feeType,  chargeName,
						amount,  productID,  paymentType,  postalCode,  cvv,  nameONCard,  cardNo, 
						cardCode,  cardType,  subject,  detail);
			} else if (caseType.equalsIgnoreCase("VerifyAvailablityForChangeStayDatesVrboReservation")) {
				verifyAvailablityChangeStayDatesVrboReservation(roomClassId, advertiserId,
						currency, defaultDateFormat, dateFormatForRateGrid, timeZone,
						ratePlanName,  beginDate,  noOfNights, 
						originationDate,  roomClassName,  channel,  message,  title,  firstName,  lastName,  email,  phoneNo,  address1,  address3,  address4,  country,  adult,  child,  externalId,  feeType,  chargeName,
						amount,  productID,  paymentType,  postalCode,  cvv,  nameONCard,  cardNo, 
						cardCode,  cardType,  subject,  detail);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify unit availability content delete request", testName, testDescription,
					testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify unit availability content delete request", testName, testDescription,
					testCategory, testSteps);
		}

		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to add steps into report",
					testName, testDescription, testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to add steps into report", testName,
					testDescription, testCategory, testSteps);
		}

	}

	public void verifyAvailablityVrboReservation(String classId,
			String advertiserId, String currency, String defaultDateFormat, String dateFormatForRateGrid,
			String timeZone,String ratePlanName, String beginDate, String noOfNights, 
			String originationDate, String roomClassName, String channel, String message, String title, String firstName, String lastName, String email, String phoneNo, String address1, String address3, String address4, String country, String adult, String child, String externalId, String feeType, String chargeName, String amount, String productID, String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, 
			String cardCode, String cardType, String subject, String detail
			) throws NumberFormatException, ParseException, Exception {

		String startDate = null, endDate = null, expiryDate = null, startDate1 = null, endDate1 = null;
		String confirmationNo = "", error;

		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> oDates = new ArrayList<>();
		ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
		ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();
		String beforeReservedCount = null;
		String beforeAvailableRooms = null;
		int getDaysbetweenCurrentAndCheckInDate = 0;
		int getDaysbetweenDate = 0;
		try {

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

			getDaysbetweenCurrentAndCheckInDate = Utility.getNumberofDays(
					Utility.getCurrentDate(defaultDateFormat, timeZone),
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			getDaysbetweenDate = Utility.getNumberofDays(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysbetweenCurrentAndCheckInDate);
			app_logs.info("getDaysbetweenDate : " + getDaysbetweenDate);

			getDates = Utility.returnDatesInBetweenDateRange(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDates : " + getDates.size());
			app_logs.info("getDates : " + getDates);

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

			expiryDate = Utility.getFutureMonthAndYearForMasterCard();

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		}

		try {
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			try {
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			} catch (Exception f) {
				navigation.Rates_Grid(driver);
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			}

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);

		}

		// verify
		try {
			app_logs.info("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE BEFORE CREATING RESERVATION ========");
			testSteps.add("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE BEFORE CREATING RESERVATION ========");
			for (int i = 0; i < getDates.size() - 1; i++) {
				String getDate = getDates.get(i);
				String calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
				app_logs.info(calendarTodayDate);
				ratesGrid.clickCalendar(driver);

				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);
				ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone, getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

				ratesGrid.expandRoomClass(driver, roomClassName, getTestSteps);
				beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				app_logs.info("Before Reserved Count : " + beforeReservedCount);
				beforeReservedCountList.add(Integer.parseInt(beforeReservedCount));
				beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
				beforeAvailableRoomsCountList.add(Integer.parseInt(beforeAvailableRooms));

				ratesGrid.reduceRoomClass(driver, roomClassName, getTestSteps);

			}
			testSteps.add("Before Reserved Count : " + beforeReservedCountList);
			testSteps.add("Before Available Rooms : " + beforeAvailableRoomsCountList);

			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);

		}

		ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();

		try {
			app_logs.info("===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue =====".toUpperCase());
			testSteps.add("<b> ===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue ===== </b>".toUpperCase());

			vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode200,
					testSteps);
			response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

			String resp = vrboObject.getAttributesValue(response,
					"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
			app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
					+ resp);
			ArrayList<Integer> getAvailabilityCount = new ArrayList<>();
			getAvailabilityCount.clear();
			String mrbArr[] = resp.split(",");
			app_logs.info("getAvailabilityCount : " + mrbArr.length);
			for (int i = 0; i < mrbArr.length; i++) {
				if (i >= getDaysbetweenCurrentAndCheckInDate) {
					getAvailabilityCount.add(Integer.parseInt(mrbArr[i]));
				}
			}

			app_logs.info("getAvailabilityCount : " + getAvailabilityCount);
			if (getDaysbetweenDate > 0) {
				for (int j = 0; j < getDaysbetweenDate; j++) {
					availabilityCountBeforeResList.add(getAvailabilityCount.get(j));
				}
			}
			app_logs.info("availabilityCountBeforeRes : " + availabilityCountBeforeResList);
			testSteps.add("availabilityCountBeforeRes : " + availabilityCountBeforeResList);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		}

		try {
			File requestBody = bookingRequest.createBasicBookingRequest(testName, advertiserId, classId, channel, message, title,
					firstName, lastName, email, phoneNo, address1, address3, address4, country, adult, child, startDate,
					endDate, originationDate, externalId, feeType, chargeName, amount, productID, startDate,
					paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath,
					currency);
			
				String myRequest = bookingRequest.getFileContent(requestBody);
				app_logs.info("Request--"+myRequest);	
				HashMap<String, String> response=vrboObject.postRequest(vrvoproperties.getProperty("BookingRequestToken"), VrboEndPoints.bookingRequest, myRequest);
				if(!response.get("BODY").contains("SERVER_ERROR") && response.get("StatusCode").equals("200")) {
					testSteps.add("API End Point-" + response.get("APIURL"));
					testSteps.add("Requested Body-" +  "<a href='"+requestBody+"' target='_blank'>"
							+ "Click here to open : URL</a><br>");
					vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
				    File responseBody=bookingRequest.getResponseXmlFile(response.get("BODY"),schemaFilesPath,testName,"bookingResponse");
				    testSteps.add("Response Body-" +  "<a href='"+responseBody+"' target='_blank'>"
							+ "Click here to open : URL</a><br>");		
				    error=bookingRequest.getValueOfXmlTags(response.get("BODY"), "errorType");
					app_logs.info("Error Is -" + error);
					String errorType="PROPERTY_NOT_AVAILABLE";
					Utility.verifyText(error.toLowerCase().trim(), errorType.toLowerCase().trim(), "Verify Error", testSteps, app_logs);
				}
				else {
					app_logs.info("Failed Get Status Code: <b>" + response.get("StatusCode") +" Unable to Verify Request with Blacked Out </b>");
					testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") +"Unable to Verify Request with Blacked Out </b>");
				}

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to create vrbo reservation",
					testName, testDescription, testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to create vrbo reservation", testName,
					testDescription, testCategory, testSteps);
		}
		int getChangeInCount = 1;
		// verify
		try {

			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION ========");

			unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
					roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
					availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate,
					getChangeInCount);

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		}
	}

	public void verifyAvailablityCancelVrboReservation(String classId,
			String advertiserId, String currency, String defaultDateFormat, String dateFormatForRateGrid,
			String timeZone,
			String ratePlanName, String beginDate, String noOfNights, 
			String originationDate, String roomClassName, String channel, String message, String title, String firstName, String lastName, String email, String phoneNo, String address1, String address3, String address4, String country, String adult, String child, String externalId, String feeType, String chargeName, String amount, String productID, String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, 
			String cardCode, String cardType, String subject, String detail) throws NumberFormatException, ParseException, Exception {

		String startDate = null, endDate = null, expiryDate = null, startDate1 = null, endDate1 = null, error = null;
		String confirmationNo = "";

		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> oDates = new ArrayList<>();

		ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
		ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();
		String beforeReservedCount = null;
		String beforeAvailableRooms = null;
		int getDaysbetweenCurrentAndCheckInDate = 0;
		int getDaysbetweenDate = 0;
		try {

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

			getDaysbetweenCurrentAndCheckInDate = Utility.getNumberofDays(
					Utility.getCurrentDate(defaultDateFormat, timeZone),
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			getDaysbetweenDate = Utility.getNumberofDays(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysbetweenCurrentAndCheckInDate);
			app_logs.info("getDaysbetweenDate : " + getDaysbetweenDate);

			getDates.clear();
			getDates = Utility.returnDatesInBetweenDateRange(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDates : " + getDates.size());
			app_logs.info("getDates : " + getDates);

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

			expiryDate = Utility.getFutureMonthAndYearForMasterCard();

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		}

		try {
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			try {
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			} catch (Exception f) {

				navigation.Rates_Grid(driver);
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			}

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);

		}

		// verify
		try {
			app_logs.info("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE BEFORE CREATING RESERVATION ========");
			testSteps.add("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE BEFORE CREATING RESERVATION ========");
			for (int i = 0; i < getDates.size() - 1; i++) {
				String getDate = getDates.get(i);
				String calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
				app_logs.info(calendarTodayDate);
				ratesGrid.clickCalendar(driver);

				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);
				ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone, getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

				ratesGrid.expandRoomClass(driver, roomClassName, getTestSteps);
				beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				app_logs.info("Before Reserved Count : " + beforeReservedCount);
				beforeReservedCountList.add(Integer.parseInt(beforeReservedCount));
				beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
				beforeAvailableRoomsCountList.add(Integer.parseInt(beforeAvailableRooms));

				ratesGrid.reduceRoomClass(driver, roomClassName, getTestSteps);

			}
			testSteps.add("Before Reserved Count : " + beforeReservedCountList);
			testSteps.add("Before Available Rooms : " + beforeAvailableRoomsCountList);

			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);

		}

		ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();

		try {
			app_logs.info("===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue =====".toUpperCase());
			testSteps.add("<b> ===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue ===== </b>".toUpperCase());

			vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode200,
					testSteps);
			response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

			String resp = vrboObject.getAttributesValue(response,
					"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
			app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
					+ resp);
			ArrayList<Integer> getAvailabilityCount = new ArrayList<>();
			getAvailabilityCount.clear();
			String mrbArr[] = resp.split(",");
			app_logs.info("getAvailabilityCount : " + mrbArr.length);
			for (int i = 0; i < mrbArr.length; i++) {
				if (i >= getDaysbetweenCurrentAndCheckInDate) {
					getAvailabilityCount.add(Integer.parseInt(mrbArr[i]));
				}
			}

			app_logs.info("getAvailabilityCount : " + getAvailabilityCount);
			if (getDaysbetweenDate > 0) {
				for (int j = 0; j < getDaysbetweenDate; j++) {
					availabilityCountBeforeResList.add(getAvailabilityCount.get(j));
				}
			}
			app_logs.info("availabilityCountBeforeRes : " + availabilityCountBeforeResList);
			testSteps.add("availabilityCountBeforeRes : " + availabilityCountBeforeResList);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		}

		try {
			File requestBody = bookingRequest.createBasicBookingRequest(testName, advertiserId, classId, channel, message, title,
					firstName, lastName, email, phoneNo, address1, address3, address4, country, adult, child, startDate,
					endDate, originationDate, externalId, feeType, chargeName, amount, productID, startDate,
					paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath,
					currency);
/*
			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--" + myRequest);
			HashMap<String, String> response = new HashMap<String, String>();
			app_logs.info("BookingRequestToken : " + BookingRequestToken);
			response = vrboObject.postRequest(BookingRequestToken, VrboEndPoints.bookingRequest, myRequest);

			if (!response.get("BODY").contains("PROPERTY_NOT_AVAILABLE")
					&& !response.get("BODY").contains("SERVER_ERROR")) {
				testSteps.add("API End Point-" + response.get("APIURL"));
				testSteps.add("Requested Body-" + "<a href='" + requestBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
				File responseBody = bookingRequest.getResponseXmlFile(response.get("BODY"), schemaFilesPath, testName);
				testSteps.add("Response Body-" + "<a href='" + responseBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				confirmationNo = bookingRequest.getValueOfXmlTags(response.get("BODY"), "externalId");
				testSteps.add("Confirmation No -" + confirmationNo);
				app_logs.info("Confirmation No -" + confirmationNo);
			} else if (response.get("StatusCode").equals("200")) {
				testSteps.add("API End Point-" + response.get("APIURL"));
				testSteps.add("Requested Body-" + "<a href='" + requestBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
				File responseBody = bookingRequest.getResponseXmlFile(response.get("BODY"), schemaFilesPath, testName);
				testSteps.add("Response Body-" + "<a href='" + responseBody + "' target='_blank'>"
						+ "Click here to open : URL</a><br>");

			} else {
				app_logs.info("Failed Get Status Code: <b>" + response.get("StatusCode") + "</b>");
				testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") + "</b>");
			}
*/

			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--"+myRequest);	
			HashMap<String, String> response=vrboObject.postRequest(vrvoproperties.getProperty("BookingRequestToken"), VrboEndPoints.bookingRequest, myRequest);
			if(!response.get("BODY").contains("SERVER_ERROR") && response.get("StatusCode").equals("200")) {
				testSteps.add("API End Point-" + response.get("APIURL"));
				testSteps.add("Requested Body-" +  "<a href='"+requestBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
			    File responseBody=bookingRequest.getResponseXmlFile(response.get("BODY"),schemaFilesPath,testName,"bookingResponse");
			    testSteps.add("Response Body-" +  "<a href='"+responseBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");		
			    error=bookingRequest.getValueOfXmlTags(response.get("BODY"), "errorType");
				app_logs.info("Error Is -" + error);
				String errorType="PROPERTY_NOT_AVAILABLE";
				Utility.verifyText(error.toLowerCase().trim(), errorType.toLowerCase().trim(), "Verify Error", testSteps, app_logs);
			}
			else {
				app_logs.info("Failed Get Status Code: <b>" + response.get("StatusCode") +" Unable to Verify Request with Blacked Out </b>");
				testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") +"Unable to Verify Request with Blacked Out </b>");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to create vrbo reservation",
					testName, testDescription, testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to create vrbo reservation", testName,
					testDescription, testCategory, testSteps);
		}
		int getChangeInCount = 1;
		// verify
		try {

			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION ========");

			unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
					roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
					availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate,
					getChangeInCount);

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		}

		try {

			testSteps.add("===== "
					+ "Changing reservation status to cancel and verify availability in rategrid and endpoint for roomclass"
							.toUpperCase()
					+ " =====");
			app_logs.info("===== "
					+ "Changing reservation status to cancel and verify availability in rategrid and endpoint for roomclass"
							.toUpperCase()
					+ " =====");

			reservationSearch.basicSearchWithResNumber(driver, confirmationNo, true);
			testSteps.add("Searched and opened reservation with number : " + confirmationNo);
			app_logs.info("Searched and opened reservation with number : " + confirmationNo);

			reservationPage.changeReservationStatus(driver, testSteps, "Cancel");

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify availability after cancelling reservation", testName, testDescription,
					testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify availability after cancelling reservation", testName, testDescription,
					testCategory, testSteps);
		}

		getChangeInCount = 0;
		// verify
		try {
			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CANCELLING RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CANCELLING RESERVATION ========");

			unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
					roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
					availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate,
					getChangeInCount);

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);
		}

		try {

			testSteps.add("<b> ===== "
					+ "Roll back reservation and verify availability in rategrid and endpoint".toUpperCase()
					+ " =====</b>");
			app_logs.info(
					"===== " + "Roll back reservation and verify availability in rategrid and endpoint".toUpperCase()
							+ " =====");

			reservationSearch.basicSearchWithResNumber(driver, confirmationNo, true);
			testSteps.add("Searched and opened reservation with number : " + confirmationNo);
			app_logs.info("Searched and opened reservation with number : " + confirmationNo);

			testSteps.add("==========Start Verifying Roll Back Button ==========");
			reservationPage.verifyRollBackButton(driver, testSteps);
			reservationPage.clickRollBackButton(driver, testSteps);
			reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
			Wait.wait10Second();
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify response after roll back reservation", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify response after roll back reservation", testName, testDescription, testCategory,
					testSteps);
		}

		try {

			unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
					roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
					availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 1);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify unit availability content response", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify unit availability content response", testName, testDescription, testCategory,
					testSteps);
		}
	}

	public void verifyAvailablityNoshowVrboReservation(String classId,
			String advertiserId, String currency, String defaultDateFormat, String dateFormatForRateGrid,
			String timeZone,
			String ratePlanName, String beginDate, String noOfNights, 
			String originationDate, String roomClassName, String channel, String message, String title, String firstName, String lastName, String email, String phoneNo, String address1, String address3, String address4, String country, String adult, String child, String externalId, String feeType, String chargeName, String amount, String productID, String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, 
			String cardCode, String cardType, String subject, String detail) throws NumberFormatException, ParseException, Exception {

		String startDate = null, endDate = null, expiryDate = null, startDate1 = null, endDate1 = null, error = null;
		String confirmationNo = "";

		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> oDates = new ArrayList<>();

		ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
		ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();
		String beforeReservedCount = null;
		String beforeAvailableRooms = null;
		int getDaysbetweenCurrentAndCheckInDate = 0;
		int getDaysbetweenDate = 0;
		try {

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

			getDaysbetweenCurrentAndCheckInDate = Utility.getNumberofDays(
					Utility.getCurrentDate(defaultDateFormat, timeZone),
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			getDaysbetweenDate = Utility.getNumberofDays(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysbetweenCurrentAndCheckInDate);
			app_logs.info("getDaysbetweenDate : " + getDaysbetweenDate);

			getDates.clear();
			getDates = Utility.returnDatesInBetweenDateRange(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDates : " + getDates.size());
			app_logs.info("getDates : " + getDates);

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

			expiryDate = Utility.getFutureMonthAndYearForMasterCard();

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		}

		try {
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			try {
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			} catch (Exception f) {

				navigation.Rates_Grid(driver);
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			}

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);
		}

		// verify
		try {
			app_logs.info("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE BEFORE CREATING RESERVATION ========");
			testSteps.add("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE BEFORE CREATING RESERVATION ========");
			for (int i = 0; i < getDates.size() - 1; i++) {
				String getDate = getDates.get(i);
				String calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
				app_logs.info(calendarTodayDate);
				ratesGrid.clickCalendar(driver);

				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);
				ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone, getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

				ratesGrid.expandRoomClass(driver, roomClassName, getTestSteps);
				beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				app_logs.info("Before Reserved Count : " + beforeReservedCount);
				beforeReservedCountList.add(Integer.parseInt(beforeReservedCount));
				beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
				beforeAvailableRoomsCountList.add(Integer.parseInt(beforeAvailableRooms));

				ratesGrid.reduceRoomClass(driver, roomClassName, getTestSteps);

			}
			testSteps.add("Before Reserved Count : " + beforeReservedCountList);
			testSteps.add("Before Available Rooms : " + beforeAvailableRoomsCountList);

			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);

		}

		ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();

		try {
			app_logs.info("===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue =====".toUpperCase());
			testSteps.add("<b> ===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue ===== </b>".toUpperCase());

			vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode200,
					testSteps);
			response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

			String resp = vrboObject.getAttributesValue(response,
					"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
			app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
					+ resp);
			ArrayList<Integer> getAvailabilityCount = new ArrayList<>();
			getAvailabilityCount.clear();
			String mrbArr[] = resp.split(",");
			app_logs.info("getAvailabilityCount : " + mrbArr.length);
			for (int i = 0; i < mrbArr.length; i++) {
				if (i >= getDaysbetweenCurrentAndCheckInDate) {
					getAvailabilityCount.add(Integer.parseInt(mrbArr[i]));
				}
			}

			app_logs.info("getAvailabilityCount : " + getAvailabilityCount);
			if (getDaysbetweenDate > 0) {
				for (int j = 0; j < getDaysbetweenDate; j++) {
					availabilityCountBeforeResList.add(getAvailabilityCount.get(j));
				}
			}
			app_logs.info("availabilityCountBeforeRes : " + availabilityCountBeforeResList);
			testSteps.add("availabilityCountBeforeRes : " + availabilityCountBeforeResList);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		}

		try {
			File requestBody = bookingRequest.createBasicBookingRequest(testName, advertiserId, classId, channel, message, title,
					firstName, lastName, email, phoneNo, address1, address3, address4, country, adult, child, startDate,
					endDate, originationDate, externalId, feeType, chargeName, amount, productID, startDate,
					paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath,
					currency);


			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--"+myRequest);	
			HashMap<String, String> response=vrboObject.postRequest(vrvoproperties.getProperty("BookingRequestToken"), VrboEndPoints.bookingRequest, myRequest);
			if(!response.get("BODY").contains("SERVER_ERROR") && response.get("StatusCode").equals("200")) {
				testSteps.add("API End Point-" + response.get("APIURL"));
				testSteps.add("Requested Body-" +  "<a href='"+requestBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
			    File responseBody=bookingRequest.getResponseXmlFile(response.get("BODY"),schemaFilesPath,testName,"bookingResponse");
			    testSteps.add("Response Body-" +  "<a href='"+responseBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");		
			    error=bookingRequest.getValueOfXmlTags(response.get("BODY"), "errorType");
				app_logs.info("Error Is -" + error);
				String errorType="PROPERTY_NOT_AVAILABLE";
				Utility.verifyText(error.toLowerCase().trim(), errorType.toLowerCase().trim(), "Verify Error", testSteps, app_logs);
			}
			else {
				app_logs.info("Failed Get Status Code: <b>" + response.get("StatusCode") +" Unable to Verify Request with Blacked Out </b>");
				testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") +"Unable to Verify Request with Blacked Out </b>");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to create vrbo reservation",
					testName, testDescription, testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to create vrbo reservation", testName,
					testDescription, testCategory, testSteps);
		}
		int getChangeInCount = 1;
		// verify
		try {

			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION ========");

			unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
					roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
					availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate,
					getChangeInCount);

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		}

		try {

			testSteps.add(
					"===== " + "Changing reservation status to noshow and verify availability in rategrid and endpoint"
							.toUpperCase() + " =====");
			app_logs.info(
					"===== " + "Changing reservation status to noshow and verify availability in rategrid and endpoint"
							.toUpperCase() + " =====");

			reservationSearch.basicSearchWithResNumber(driver, confirmationNo, true);
			testSteps.add("Searched and opened reservation with number : " + confirmationNo);
			app_logs.info("Searched and opened reservation with number : " + confirmationNo);

			reservationPage.noShowReservation(driver, "Void");

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close open Reservation");
			app_logs.info("Close open Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify unit availability content post request", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify unit availability content post request", testName, testDescription, testCategory,
					testSteps);
		}

		// verify
		try {
			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CHANGING RESERVATION STATUS NOSHOW========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CHANGING RESERVATION STATUS NOSHOW ========");

			unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
					roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
					availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 0);

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
					testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
					testSteps);

		}

	}

	public void verifyAvailablityCheckoutVrboReservation(String classId,
			String advertiserId, String currency, String defaultDateFormat, String dateFormatForRateGrid,
			String timeZone,
			String ratePlanName, String beginDate, String noOfNights, 
			String originationDate, String roomClassName, String channel, String message, String title, String firstName, String lastName, String email, String phoneNo, String address1, String address3, String address4, String country, String adult, String child, String externalId, String feeType, String chargeName, String amount, String productID, String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, 
			String cardCode, String cardType, String subject, String detail) throws NumberFormatException, ParseException, Exception {

		String startDate = null, endDate = null, expiryDate = null, startDate1 = null, endDate1 = null, error = null;
		String confirmationNo = "";

		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> oDates = new ArrayList<>();

		ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
		ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();
		String beforeReservedCount = null;
		String beforeAvailableRooms = null;
		int getDaysbetweenCurrentAndCheckInDate = 0;
		int getDaysbetweenDate = 0;
		try {

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



			getDaysbetweenCurrentAndCheckInDate = Utility.getNumberofDays(
					Utility.getCurrentDate(defaultDateFormat, timeZone),
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			getDaysbetweenDate = Utility.getNumberofDays(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysbetweenCurrentAndCheckInDate);
			app_logs.info("getDaysbetweenDate : " + getDaysbetweenDate);

			getDates.clear();
			getDates = Utility.returnDatesInBetweenDateRange(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDates : " + getDates.size());
			app_logs.info("getDates : " + getDates);

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

			expiryDate = Utility.getFutureMonthAndYearForMasterCard();

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		}

		try {
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			try {
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			} catch (Exception f) {

				navigation.Rates_Grid(driver);
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			}

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);

		}

		// verify
		try {
			app_logs.info("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE BEFORE CREATING RESERVATION ========");
			testSteps.add("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE BEFORE CREATING RESERVATION ========");
			for (int i = 0; i < getDates.size() - 1; i++) {
				String getDate = getDates.get(i);
				String calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
				app_logs.info(calendarTodayDate);
				ratesGrid.clickCalendar(driver);

				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);
				ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone, getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

				ratesGrid.expandRoomClass(driver, roomClassName, getTestSteps);
				beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				app_logs.info("Before Reserved Count : " + beforeReservedCount);
				beforeReservedCountList.add(Integer.parseInt(beforeReservedCount));
				beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
				beforeAvailableRoomsCountList.add(Integer.parseInt(beforeAvailableRooms));

				ratesGrid.reduceRoomClass(driver, roomClassName, getTestSteps);

			}
			testSteps.add("Before Reserved Count : " + beforeReservedCountList);
			testSteps.add("Before Available Rooms : " + beforeAvailableRoomsCountList);

			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);

		}

		ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();

		try {
			app_logs.info("===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue =====".toUpperCase());
			testSteps.add("<b> ===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue ===== </b>".toUpperCase());

			vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode200,
					testSteps);
			response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

			String resp = vrboObject.getAttributesValue(response,
					"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
			app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
					+ resp);
			ArrayList<Integer> getAvailabilityCount = new ArrayList<>();
			getAvailabilityCount.clear();
			String mrbArr[] = resp.split(",");
			app_logs.info("getAvailabilityCount : " + mrbArr.length);
			for (int i = 0; i < mrbArr.length; i++) {
				if (i >= getDaysbetweenCurrentAndCheckInDate) {
					getAvailabilityCount.add(Integer.parseInt(mrbArr[i]));
				}
			}

			app_logs.info("getAvailabilityCount : " + getAvailabilityCount);
			if (getDaysbetweenDate > 0) {
				for (int j = 0; j < getDaysbetweenDate; j++) {
					availabilityCountBeforeResList.add(getAvailabilityCount.get(j));
				}
			}
			app_logs.info("availabilityCountBeforeRes : " + availabilityCountBeforeResList);
			testSteps.add("availabilityCountBeforeRes : " + availabilityCountBeforeResList);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		}

		try {
			File requestBody = bookingRequest.createBasicBookingRequest(testName, advertiserId, classId, channel, message, title,
					firstName, lastName, email, phoneNo, address1, address3, address4, country, adult, child, startDate,
					endDate, originationDate, externalId, feeType, chargeName, amount, productID, startDate,
					paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath,
					currency);


			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--"+myRequest);	
			HashMap<String, String> response=vrboObject.postRequest(vrvoproperties.getProperty("BookingRequestToken"), VrboEndPoints.bookingRequest, myRequest);
			if(!response.get("BODY").contains("SERVER_ERROR") && response.get("StatusCode").equals("200")) {
				testSteps.add("API End Point-" + response.get("APIURL"));
				testSteps.add("Requested Body-" +  "<a href='"+requestBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
			    File responseBody=bookingRequest.getResponseXmlFile(response.get("BODY"),schemaFilesPath,testName,"bookingResponse");
			    testSteps.add("Response Body-" +  "<a href='"+responseBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");		
			    error=bookingRequest.getValueOfXmlTags(response.get("BODY"), "errorType");
				app_logs.info("Error Is -" + error);
				String errorType="PROPERTY_NOT_AVAILABLE";
				Utility.verifyText(error.toLowerCase().trim(), errorType.toLowerCase().trim(), "Verify Error", testSteps, app_logs);
			}
			else {
				app_logs.info("Failed Get Status Code: <b>" + response.get("StatusCode") +" Unable to Verify Request with Blacked Out </b>");
				testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") +"Unable to Verify Request with Blacked Out </b>");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to create vrbo reservation",
					testName, testDescription, testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to create vrbo reservation", testName,
					testDescription, testCategory, testSteps);
		}
		int getChangeInCount = 1;
		// verify
		try {

			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION ========");

			unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
					roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
					availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate,
					getChangeInCount);

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		}

		try {
			if (Utility.compareDates(Utility.getCurrentDate(defaultDateFormat, timeZone),
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat).split(Utility.DELIM)[0],
					defaultDateFormat) == 0) {
				ArrayList<Integer> availabilityCountAfterResList = new ArrayList<>();
				testSteps.add("===== "
						+ "Making early checkout and verify availability in rategrid and endpoint".toUpperCase()
						+ " =====");
				app_logs.info("===== "
						+ "Making early checkout and verify availability in rategrid and endpoint".toUpperCase()
						+ " =====");

				reservationSearch.basicSearchWithResNumber(driver, confirmationNo, true);
				testSteps.add("Searched and opened reservation with number : " + confirmationNo);
				app_logs.info("Searched and opened reservation with number : " + confirmationNo);

				testSteps.add("==========Starting Roll Back ==========");
				reservationPage.verifyRollBackButton(driver, testSteps);
				reservationPage.clickRollBackButton(driver, testSteps);
				reservationPage.clickYesButtonRollBackMsg(driver, testSteps);

				reservationPage.Click_CheckInButton(driver, testSteps);
				reservationPage.disableGenerateGuestReportToggle(driver, testSteps);
				getTestSteps.clear();
				getTestSteps = homePage.clickConfirmChekInButton(driver);
				testSteps.addAll(getTestSteps);

				try {
					getTestSteps.clear();
					getTestSteps = homePage.clickPayButton(driver);
					testSteps.addAll(getTestSteps);

				} catch (Exception e) {
				}

				try {
					getTestSteps.clear();
					getTestSteps = homePage.clickCloseCheckinSuccessfullPopup(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {

				}
				testSteps.add("Reservation checked in");
				app_logs.info("Reservation checked in");

				testSteps.add("===== " + "Checking out reservation".toUpperCase() + " =====");
				app_logs.info("===== " + "Checking out reservation".toUpperCase() + " =====");

				reservationPage.clickCheckOutButton(driver, testSteps);
				reservationPage.generatGuestReportToggle(driver, testSteps, "No");
				reservationPage.proceedToCheckOutPayment(driver, testSteps);
				try {
					getTestSteps.clear();
					getTestSteps = homePage.clickPayButton(driver);
					testSteps.addAll(getTestSteps);

				} catch (Exception e) {

				}
				try {

					getTestSteps.clear();
					getTestSteps = homePage.clickCloseCheckoutSuccessfullPopup(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {

				}

				testSteps.add("Reservation checked out");
				app_logs.info("Reservation checked out");
				String loading = "(//div[@class='ir-loader-in'])";
				Wait.explicit_wait_absenceofelement(loading, driver);

				testSteps.add("<b>==========Start Verifying Roll Back Button ==========</b>");
				reservationPage.verifyRollBackButton(driver, testSteps);
				testSteps.add("<b>==========Start Verifying DEPARTED Status==========</b>");
				reservationPage.verifyReservationStatusStatus(driver, testSteps, "DEPARTED");

				availabilityCountAfterResList.clear();

				app_logs.info("===== Getting EndPoint Response =====".toUpperCase());
				testSteps.add("<b>" + " ===== Getting EndPoint Response ===== ".toUpperCase() + "</b>");

				vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps);
				Response response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

				String resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
				app_logs.info(
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
								+ resp);
				ArrayList<Integer> getAvailabilityCount = new ArrayList<>();
				getAvailabilityCount.clear();
				String mrbArr[] = resp.split(",");
				app_logs.info("getAvailabilityCount : " + mrbArr.length);
				for (int i = 0; i < mrbArr.length; i++) {
					if (i >= getDaysbetweenCurrentAndCheckInDate) {
						getAvailabilityCount.add(Integer.parseInt(mrbArr[i]));
					}
				}

				app_logs.info("getAvailabilityCount : " + getAvailabilityCount);
				availabilityCountAfterResList.clear();
				if (getDaysbetweenDate > 0) {
					for (int j = 0; j < getDaysbetweenDate; j++) {
						availabilityCountAfterResList.add(getAvailabilityCount.get(j));
					}
				}
				app_logs.info(
						"===== Verify availability count changed for specific daterange in response after Early CheckOut ====="
								.toUpperCase());
				testSteps.add("<b>"
						+ "===== VERIFY availability count changed for specific daterange in response after Early CheckOut ====="
								.toUpperCase()
						+ "</b>");

				for (int i = 0; i < getDates.size(); i++) {
					int afterCountRes = availabilityCountAfterResList.get(i);
					int beforeCountRes = availabilityCountBeforeResList.get(i);
					app_logs.info("Previous Room availability count : " + beforeCountRes);
					testSteps.add("Previous Room availability count : " + beforeCountRes);
					app_logs.info("Current Room availbility count : " + afterCountRes);
					testSteps.add("Current Room availbility count : " + afterCountRes);
					if (i == 0) {
						assertEquals(afterCountRes, beforeCountRes - 1,
								"Failed : Availability count in response didn't changed");
					} else {
						assertEquals(afterCountRes, beforeCountRes,
								"Failed : Availability count in response didn't changed");

					}

				}

				app_logs.info(
						"Verified availability count changed for specific daterange available in response after early checkOut");
				testSteps.add(
						"Verified availability count changed for specific daterange available in response after early checkOut");

			}

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify unit availability content after early checkout", testName, testDescription,
					testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify unit availability content after early checkout", testName, testDescription,
					testCategory, testSteps);
		}

		try {

			reservationSearch.basicSearchWithResNumber(driver, confirmationNo, true);
			testSteps.add("Searched and opened reservation with number : " + confirmationNo);
			app_logs.info("Searched and opened reservation with number : " + confirmationNo);

			testSteps.add("==========Start Verifying Roll Back Button ==========");
			reservationPage.verifyRollBackButton(driver, testSteps);
			reservationPage.clickRollBackButton(driver, testSteps);
			reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
			Wait.wait10Second();
			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify response after roll back reservation", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify response after roll back reservation", testName, testDescription, testCategory,
					testSteps);
		}
	}

	public void verifyAvailablityChangeStayDatesVrboReservation(String classId, String advertiserId, String currency, String defaultDateFormat,
			String dateFormatForRateGrid, String timeZone,
			String ratePlanName, String beginDate, String noOfNights, 
			String originationDate, String roomClassName, String channel, String message, String title, String firstName, String lastName, String email, String phoneNo, String address1, String address3, String address4, String country, String adult, String child, String externalId, String feeType, String chargeName, String amount, String productID, String paymentType, String postalCode, String cvv, String nameONCard, String cardNo, 
			String cardCode, String cardType, String subject, String detail) throws NumberFormatException, ParseException, Exception {

		String startDate = null, endDate = null, expiryDate = null, startDate1 = null, endDate1 = null, error = null;
		String confirmationNo = "";
		HashMap<String, String> commonData = Utility.getExcelData(testDataPath + File.separator + "OTATestData.xlsx",
				"BROutOfOrder");
		app_logs.info("commonData : " + commonData);
		System.out.println();
		String extendCheckOutDate = "";
		int getDaysBetweenExtendedDates = 0;

		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> oDates = new ArrayList<>();
		ArrayList<String> getExtendedDates = new ArrayList<>();

		ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
		ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();

		ArrayList<Integer> beforeReservedCountForExtendedDatesList = new ArrayList<>();
		ArrayList<Integer> beforeAvailableRoomsCountForExtendedDatesList = new ArrayList<>();
		ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();
		ArrayList<Integer> availabilityCountBeforeResForExtendedDatesList = new ArrayList<>();

		String beforeReservedCount = null;
		String beforeAvailableRooms = null;
		int getDaysbetweenCurrentAndCheckInDate = 0;
		int getDaysbetweenDate = 0;
		try {

			app_logs.info("beginDate : " + beginDate);
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

			extendCheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(endDate1, "dd/MM/yyyy", defaultDateFormat,
					1, timeZone);

			getDaysBetweenExtendedDates = Utility.getNumberofDays(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat), extendCheckOutDate,
					defaultDateFormat);
			app_logs.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysBetweenExtendedDates);
			app_logs.info("getDaysbetweenDate : " + getDaysbetweenDate);

			getExtendedDates.clear();
			getExtendedDates = Utility.returnDatesInBetweenDateRange(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat), extendCheckOutDate,
					defaultDateFormat);
			app_logs.info("getExtendedDates : " + getExtendedDates.size());
			app_logs.info("getExtendedDates : " + getExtendedDates);

			getDaysbetweenCurrentAndCheckInDate = Utility.getNumberofDays(
					Utility.getCurrentDate(defaultDateFormat, timeZone),
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			getDaysbetweenDate = Utility.getNumberofDays(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDaysbetweenCurrentAndCheckInDate : " + getDaysbetweenCurrentAndCheckInDate);
			app_logs.info("getDaysbetweenDate : " + getDaysbetweenDate);

			getDates.clear();
			getDates = Utility.returnDatesInBetweenDateRange(
					ESTTimeZone.reformatDate(startDate1, "dd/MM/yyyy", defaultDateFormat),
					ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat), defaultDateFormat);
			app_logs.info("getDates : " + getDates.size());
			app_logs.info("getDates : " + getDates);

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

			expiryDate = Utility.getFutureMonthAndYearForMasterCard();

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to get dates", testName,
					testDescription, testCategory, testSteps);

		}

		try {
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			try {
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			} catch (Exception f) {

				navigation.Rates_Grid(driver);
				testSteps.addAll(ratesGrid.clickOnAvailability(driver));
			}

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);

		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to Navigate Rates Grid and get current date", testName, testDescription, testCategory,
					testSteps);

		}
		// verify
		try {
			app_logs.info("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' BEFORE CREATING RESERVATION ========");
			testSteps.add("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' BEFORE CREATING RESERVATION ========");

			for (int i = 0; i < getDates.size() - 1; i++) {
				String getDate = getDates.get(i);
				String calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
				app_logs.info(calendarTodayDate);
				ratesGrid.clickCalendar(driver);

				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);
				ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone, getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

				ratesGrid.expandRoomClass(driver, roomClassName, getTestSteps);
				beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				app_logs.info("Before Reserved Count : " + beforeReservedCount);
				beforeReservedCountList.add(Integer.parseInt(beforeReservedCount));
				beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
				beforeAvailableRoomsCountList.add(Integer.parseInt(beforeAvailableRooms));

				ratesGrid.reduceRoomClass(driver, roomClassName, getTestSteps);

			}
			testSteps.add("Before Reserved Count : " + beforeReservedCountList);
			testSteps.add("Before Available Rooms : " + beforeAvailableRoomsCountList);

			for (int i = 0; i < getExtendedDates.size() - 1; i++) {
				String getDate = getExtendedDates.get(i);
				String calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
				app_logs.info(calendarTodayDate);
				ratesGrid.clickCalendar(driver);

				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);
				ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone, getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

				ratesGrid.expandRoomClass(driver, roomClassName, getTestSteps);
				beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				app_logs.info("Before Reserved Count : " + beforeReservedCount);
				beforeReservedCountForExtendedDatesList.add(Integer.parseInt(beforeReservedCount));
				beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
				beforeAvailableRoomsCountForExtendedDatesList.add(Integer.parseInt(beforeAvailableRooms));

				ratesGrid.reduceRoomClass(driver, roomClassName, getTestSteps);

			}
			testSteps
					.add("Before Reserved Count For Extended Dates Range : " + beforeReservedCountForExtendedDatesList);
			testSteps.add("Before Available Rooms For Extended Dates Range : "
					+ beforeAvailableRoomsCountForExtendedDatesList);

			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get reserved and available room count of roomclass from which virtual class constitue",
					testName, testDescription, testCategory, testSteps);
		}

		try {
			app_logs.info("===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue =====".toUpperCase());
			testSteps.add("<b> ===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue ===== </b>".toUpperCase());

			vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode200,
					testSteps);
			response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

			String resp = vrboObject.getAttributesValue(response,
					"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
			app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
					+ resp);
			ArrayList<Integer> getAvailabilityCount = new ArrayList<>();
			getAvailabilityCount.clear();
			String mrbArr[] = resp.split(",");
			app_logs.info("getAvailabilityCount : " + mrbArr.length);
			for (int i = 0; i < mrbArr.length; i++) {
				if (i >= getDaysbetweenCurrentAndCheckInDate) {
					getAvailabilityCount.add(Integer.parseInt(mrbArr[i]));
				}
			}

			app_logs.info("getAvailabilityCount : " + getAvailabilityCount);
			if (getDaysbetweenDate > 0) {
				for (int j = 0; j < getDaysbetweenDate; j++) {
					availabilityCountBeforeResList.add(getAvailabilityCount.get(j));
				}
			}
			app_logs.info("availabilityCountBeforeRes : " + availabilityCountBeforeResList);
			testSteps.add("availabilityCountBeforeRes : " + availabilityCountBeforeResList);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		}

		try {
			app_logs.info("===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue =====".toUpperCase());
			testSteps.add("<b> ===== Getting EndPoint Response for room class '" + roomClassName
					+ "' from which virtual class constitue ===== </b>".toUpperCase());

			vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode200,
					testSteps);
			response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

			String resp = vrboObject.getAttributesValue(response,
					"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
			app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
					+ resp);
			ArrayList<Integer> getAvailabilityCount = new ArrayList<>();
			getAvailabilityCount.clear();
			String mrbArr[] = resp.split(",");
			app_logs.info("getAvailabilityCount : " + mrbArr.length);
			for (int i = 0; i < mrbArr.length; i++) {
				if (i >= getDaysbetweenCurrentAndCheckInDate) {
					getAvailabilityCount.add(Integer.parseInt(mrbArr[i]));
				}
			}

			app_logs.info("getAvailabilityCount : " + getAvailabilityCount);
			if (getDaysBetweenExtendedDates > 0) {
				for (int j = 0; j < getDaysBetweenExtendedDates; j++) {
					availabilityCountBeforeResForExtendedDatesList.add(getAvailabilityCount.get(j));
				}
			}
			app_logs.info("availabilityCountBeforeResForExtendedDatesList : "
					+ availabilityCountBeforeResForExtendedDatesList);
			testSteps.add("availabilityCountBeforeResForExtendedDatesList : "
					+ availabilityCountBeforeResForExtendedDatesList);

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to get unit availability content response", testName, testDescription, testCategory,
					testSteps);
		}

		try {
			File requestBody = bookingRequest.createBasicBookingRequest(testName, advertiserId, classId, channel, message, title,
					firstName, lastName, email, phoneNo, address1, address3, address4, country, adult, child, startDate,
					endDate, originationDate, externalId, feeType, chargeName, amount, productID, startDate,
					paymentType, postalCode, cvv, expiryDate, nameONCard, cardNo, cardCode, cardType, schemaFilesPath,
					currency);


			String myRequest = bookingRequest.getFileContent(requestBody);
			app_logs.info("Request--"+myRequest);	
			HashMap<String, String> response=vrboObject.postRequest(vrvoproperties.getProperty("BookingRequestToken"), VrboEndPoints.bookingRequest, myRequest);
			if(!response.get("BODY").contains("SERVER_ERROR") && response.get("StatusCode").equals("200")) {
				testSteps.add("API End Point-" + response.get("APIURL"));
				testSteps.add("Requested Body-" +  "<a href='"+requestBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");
				vrboObject.veifyVrboClientStatusCode(Integer.parseInt(response.get("StatusCode")), testSteps);
			    File responseBody=bookingRequest.getResponseXmlFile(response.get("BODY"),schemaFilesPath,testName,"bookingResponse");
			    testSteps.add("Response Body-" +  "<a href='"+responseBody+"' target='_blank'>"
						+ "Click here to open : URL</a><br>");		
			    error=bookingRequest.getValueOfXmlTags(response.get("BODY"), "errorType");
				app_logs.info("Error Is -" + error);
				String errorType="PROPERTY_NOT_AVAILABLE";
				Utility.verifyText(error.toLowerCase().trim(), errorType.toLowerCase().trim(), "Verify Error", testSteps, app_logs);
			}
			else {
				app_logs.info("Failed Get Status Code: <b>" + response.get("StatusCode") +" Unable to Verify Request with Blacked Out </b>");
				testSteps.add("Failed Get Status Code: <b>" + response.get("StatusCode") +"Unable to Verify Request with Blacked Out </b>");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to create vrbo reservation",
					testName, testDescription, testCategory, testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to create vrbo reservation", testName,
					testDescription, testCategory, testSteps);
		}
		int getChangeInCount = 1;
		// verify
		try {

			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING VRBO RESERVATION ========");

			unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
					roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
					availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate,
					getChangeInCount);

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid and endpoint after cancelling reservation", testName,
					testDescription, testCategory, testSteps);

		}

		try {
			reservationSearch.basicSearchWithResNumber(driver, confirmationNo, true);
			testSteps.add("Searched and opened reservation with number : " + confirmationNo);
			app_logs.info("Searched and opened reservation with number : " + confirmationNo);

			reservationPage.ChangeGuestInfoLastName(driver, testSteps, lastName);
			app_logs.info("===== "
					+ "Verifying stay dates updation with 'No Rate Change' checked for reservation".toUpperCase()
					+ " =====");
			testSteps.add("===== "
					+ "Verifying stay dates updation with 'No Rate Change' checked for reservations".toUpperCase()
					+ " =====");

			Wait.wait10Second();
			reservationPage.ClickEditStayInfo(driver, testSteps);

			Wait.wait10Second();
			reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

			try {
				reservationPage.clickYesButtonRollBackMsg(driver, testSteps);

			} catch (Exception e) {
				e.printStackTrace();
			}
			app_logs.info(getTestSteps);
			app_logs.info(extendCheckOutDate);
			reservationPage.checkOutDate(driver, extendCheckOutDate);

			reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
			
			testSteps.addAll(reservationPage.clickFindRooms(driver));
			reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, "Yes", "");
			reservationPage.clickYesButtonRoomWhileChnageDatesMsg(driver, testSteps);
			testSteps.addAll(reservationPage.clickSaveAfterEditStayInfo(driver));

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify availability after changing roomclass", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify availability after changing roomclass", testName, testDescription, testCategory,
					testSteps);
		}

		try {
			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER EXTENDING DATE========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER EXTENDING DATE ========");

			unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
					statusCode200, testSteps, getExtendedDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
					roomClassName, ratePlanName, beforeReservedCountForExtendedDatesList,
					beforeAvailableRoomsCountForExtendedDatesList, availabilityCountBeforeResForExtendedDatesList,
					getDaysbetweenCurrentAndCheckInDate, getDaysBetweenExtendedDates, 1);

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
					testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
					testSteps);

		}

		try {

			reservationSearch.basicSearchWithResNumber(driver, confirmationNo, true);
			testSteps.add("Searched and opened reservation with number : " + confirmationNo);
			app_logs.info("Searched and opened reservation with number : " + confirmationNo);

			app_logs.info("===== "
					+ "Verifying stay dates updation with 'No Rate Change' checked for reservation".toUpperCase()
					+ " =====");
			testSteps.add("===== "
					+ "Verifying stay dates updation with 'No Rate Change' checked for reservations".toUpperCase()
					+ " =====");

			Wait.wait10Second();
			reservationPage.ClickEditStayInfo(driver, testSteps);
			Wait.wait10Second();
			reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

			try {
				reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
			} catch (Exception e) {
			}
			reservationPage.checkOutDate(driver, ESTTimeZone.reformatDate(endDate1, "dd/MM/yyyy", defaultDateFormat));

			reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
			try {
				testSteps.addAll(reservationPage.clickFindRooms(driver));

			} catch (Exception e) {
				app_logs.info("In catch");
				testSteps.add(e.toString());
			}

			reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, "Yes", "");

			try {
				reservationPage.clickYesButtonRoomWhileChnageDatesMsg(driver, testSteps);
			} catch (Exception e) {

			}

			testSteps.addAll(reservationPage.clickSaveAfterEditStayInfo(driver));

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");

		} catch (Exception e) {
			e.printStackTrace();
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify availability after changing roomclass", testName, testDescription, testCategory,
					testSteps);
		} catch (Error e) {
			e.printStackTrace();
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify availability after changing roomclass", testName, testDescription, testCategory,
					testSteps);
		}

		try {
			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER REDUCING DATE========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER REDUCING DATE ========");

			String afterReservedCount = "";
			String afterAvailableRooms = "";
			int changeInCount = 1;

			app_logs.info("========== VERIFY IN AVAILABILITY TAB ==========".toUpperCase());
			testSteps.add("========== VERIFY IN AVAILABILITY TAB ==========".toUpperCase());

			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");

			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");
			try {
				ratesGrid.clickOnAvailability(driver);
			} catch (Exception f) {

				navigation.Rates_Grid(driver);
				ratesGrid.clickOnAvailability(driver);
			}
			testSteps.add("beforeReservedCountForExtendedDatesList : " + beforeReservedCountForExtendedDatesList);
			testSteps.add("beforeAvailableRoomsCountForExtendedDatesList : " + beforeAvailableRoomsCountForExtendedDatesList);

			for (int i = 0; i < getExtendedDates.size() - 1; i++) {
				String calendarTodayDate = ESTTimeZone.reformatDate(getExtendedDates.get(i), defaultDateFormat,
						dateFormatForRateGrid);

				ratesGrid.clickCalendar(driver);

				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);

				getTestSteps.clear();
				ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone, getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

				getTestSteps.clear();
				ratesGrid.expandRoomClass(driver, roomClassName, getTestSteps);

				int tempReserved = beforeReservedCountForExtendedDatesList.get(i);
				afterReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");

				int afterReservedcountdata= Integer.parseInt(afterReservedCount);
				
				if (i == beforeReservedCountForExtendedDatesList.size()|| tempReserved == afterReservedcountdata) {
					changeInCount = 0;
				}
				
				tempReserved = tempReserved + changeInCount;
				
				testSteps.add("Before Reserved Count : " + tempReserved);
				app_logs.info("Before Reserved Count : " + tempReserved);
				
				testSteps.add("After Reserved Count : " + afterReservedCount);
				app_logs.info("After Reserved Count : " + afterReservedCount);

				
				  try { 
					  assertEquals(afterReservedCount, Integer.toString(tempReserved), "Failed : Reserved count is not increased after creating reservation");
				  
				  }catch (Exception e) 
				  { 
					  e.printStackTrace(); 
				  }
				 
				changeInCount = 1;

				tempReserved = beforeAvailableRoomsCountForExtendedDatesList.get(i);
				afterAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				
				int afterAvailableRoomstdata= Integer.parseInt(afterAvailableRooms);
				
				if (i == beforeAvailableRoomsCountForExtendedDatesList.size() || tempReserved == afterAvailableRoomstdata) {
					changeInCount = 0;
				}	
				if (tempReserved != 0) {
					tempReserved = tempReserved - changeInCount;
				}
				testSteps.add("Before Available Count : " + tempReserved);
				app_logs.info("Before Available Count : " + tempReserved);
				
				testSteps.add("After Available Rooms : " + afterAvailableRooms);
				app_logs.info("After Available Rooms : " + afterAvailableRooms);

				try { 
					  assertEquals(afterAvailableRooms, Integer.toString(tempReserved), "Failed : Reserved count is not increased after creating reservation");
				  
				  }catch (Exception e) 
				  { 
					  e.printStackTrace(); 
				  }
				ratesGrid.reduceRoomClass(driver, roomClassName, getTestSteps);

			}
			if (changeInCount > 0) {
				app_logs.info("Verified availability count decreased for specific date range in rategrid");
				testSteps.add("Verified availability count decreased for specific date range in rategrid");
			} else {
				app_logs.info("Verified availability count increased for specific date range in rategrid");
				testSteps.add("Verified availability count increased for specific date range in rategrid");

			}

			ArrayList<Integer> availabilityCountAfterResList = new ArrayList<>();

			app_logs.info("===== Getting EndPoint Response =====".toUpperCase());
			testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

			vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken, statusCode200,
					testSteps);
			Response response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

			String resp = vrboObject.getAttributesValue(response,
					"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
			app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
					+ resp);
			ArrayList<Integer> tempResponse = new ArrayList<>();
			tempResponse.clear();
			String tempArr[] = resp.split(",");
			app_logs.info("tempResponse : " + tempArr.length);
			for (int i = 0; i < tempArr.length; i++) {
				if (i >= getDaysbetweenCurrentAndCheckInDate) {
					tempResponse.add(Integer.parseInt(tempArr[i]));
				}
			}
			app_logs.info("tempResponse : " + tempResponse);
			availabilityCountAfterResList.clear();
			if (getDaysBetweenExtendedDates > 0) {
				for (int j = 0; j < getDaysBetweenExtendedDates; j++) {
					availabilityCountAfterResList.add(tempResponse.get(j));
				}
			}

			if (changeInCount > 0) {
				app_logs.info("===== Verify availability count decreased for specific daterange in response ====="
						.toUpperCase());
				testSteps.add("<b>" + "===== VERIFY availability count decreased for specific daterange in response ====="
								.toUpperCase() + "</b>");
			} else {
				app_logs.info("===== Verify availability count increased for specific daterange in response ====="
						.toUpperCase());
				testSteps.add("<b>" + "===== VERIFY availability count increased for specific daterange in response ====="
								.toUpperCase() + "</b>");

			}
			app_logs.info("availabilityCountBeforeResList : " + availabilityCountBeforeResForExtendedDatesList);
			app_logs.info("availabilityCountAfterResList : " + availabilityCountAfterResList);

			changeInCount = 1;
			for (int i = 0; i < availabilityCountBeforeResForExtendedDatesList.size(); i++) {
				int tempTwo = availabilityCountAfterResList.get(i);
				int tempOne = availabilityCountBeforeResForExtendedDatesList.get(i);

				if (i == availabilityCountBeforeResForExtendedDatesList.size()||tempOne==tempTwo) {
					changeInCount = 0;
				}
				if (tempOne != 0) {
					tempOne = tempOne - changeInCount;
				}
				app_logs.info("Previous Room availability count : " + tempOne);
				testSteps.add("Previous Room availability count : " + tempOne);
				app_logs.info("Current Room availbility count : " + tempTwo);
				testSteps.add("Current Room availbility count : " + tempTwo);
				
				try {
     			assertEquals(tempTwo, tempOne, "Failed : Availability count in response didn't decrease");
				}catch (Exception e) {
					e.printStackTrace();
				}

			}
			if (changeInCount > 0) {
				app_logs.info("Verified availability count decreased for specific daterange available in response");
				testSteps.add("Verified availability count decreased for specific daterange available in response");

			} else {
				app_logs.info("Verified availability count increased for specific daterange available in response");
				testSteps.add("Verified availability count increased for specific daterange available in response");

			}

			try {
				navigation.reservationBackward3(driver);
				app_logs.info("Navigate to Reservations");

			} catch (Exception e) {
				app_logs.info(e.toString());
			}

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
					testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory,
					"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
					testSteps);

		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("AvailabilityForInboundRes", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
