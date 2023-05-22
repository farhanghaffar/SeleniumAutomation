package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.javascript.host.Map;
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
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.serviceobject.VrboObjects;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.Elements_AdvanceGroups;
import io.restassured.response.Response;
import java.lang.System;
import java.text.ParseException;

public class VerifyUnitAvailabilityContentApi extends TestCore {

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
	public void verifyUnitAvailabilityContent(String caseType, String reservationType, String delim,
			String roomClassName, String ratePlanName, String accountType, String checkInDate, String checkOutDate) throws Exception {
		if (delim.equalsIgnoreCase("|")) {
			Utility.DELIM = "\\" + delim;

		} else {
			Utility.DELIM = delim;
		}

		app_logs.info("DELIM : " + delim);
		app_logs.info("DELIM : " + Utility.DELIM);
		testName = "VerifyVrboUnitAvailabilityContent-" + caseType;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		String defaultDateFormat = "MM/dd/yyyy";
		String dateFormatForRateGrid = "MMM/d/yyyy";
		String calendarTodayDate = "";
		String salutation = "Mr.";
		String guestFirstName = endPointName + randomString;
		String guestLastName = "Res" + randomString;
		String guestName = guestFirstName + " " + guestLastName;
		String phoneNumber = "8790321567";
		String alternativePhone = "8790321567";
		String email = "innroadautomation@innroad.com";

		accountType = "Corporate/Member Accounts";
		String account = "";
		if (accountType.contains("Corp")) {
			account = "CorporateAccount";
		} else if (accountType.contains("Unit")) {
			account = "UnitOwner";
		} else if (accountType.contains("Travel")) {
			account = "TravelAgent";
		}
		account = account + randomString;
		String accountNumber = Utility.GenerateRandomString15Digit();
		String address1 = "test1";
		String address2 = "test2";
		String address3 = "test3";
		String address4 = "test4";
		String city = "New york";
		String state = "Alaska";
		String country = "United States";
		String paymentType = "MC";
		String cardNumber = "5454545454545454";
		String nameOnCard = "testUser";
		String cardExpDate = "12/23";
		String marketSegment = "GDS";
		String referral = "Other";
		String postalCode = "12345";
		String isGuesProfile = "No";
		String groupFirstName = "Group" + randomString;
		String groupLastName = "Res" + randomString;
		String blockName = "BlueBook" + randomString;
		String roomPerNight = "1";
		String updatedBlockedCount = "0";
		String roomBlockCount = "1";
		String accountName = groupFirstName + groupLastName;
		String baseAmount = "100";
		String isAssign = "";
		
		String timeZone = "";
		String roomNumber = "";
		String reservationNumber = "";
		String reservationStatus = "";
		String maxAdult = "1";
		String maxPerson = "0";
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

		if (checkInDate.isEmpty() || checkInDate.equalsIgnoreCase("") || checkOutDate.isEmpty()
				|| checkOutDate.equalsIgnoreCase("")) {
			checkInDate = Utility.getCurrentDate(defaultDateFormat, timeZone);
			checkOutDate = Utility.getNextDate(1, defaultDateFormat);
			app_logs.info("checkInDate : " + checkInDate);
			app_logs.info("checkOutDate : " + checkOutDate);
		}

		int getDaysbetweenCurrentAndCheckInDate = Utility
				.getNumberofDays(Utility.getCurrentDate(defaultDateFormat, timeZone), checkInDate, defaultDateFormat);
		int getDaysbetweenDate = Utility.getNumberofDays(checkInDate, checkOutDate, defaultDateFormat);
		getDates = Utility.returnDatesInBetweenDateRange(checkInDate, checkOutDate, defaultDateFormat);
		app_logs.info("getDates : " + getDates);

		if (caseType.equalsIgnoreCase("VerifyRoomAvailabilityInEndpointForSingle")
				||caseType.equalsIgnoreCase("VerifyRoomAvailabilityInEndpointForForSingleUnassigned")
				||caseType.equalsIgnoreCase("VerifyRoomAvailabilityInEndpointForForGroup")
				||caseType.equalsIgnoreCase("VerifyRoomAvailabilityInEndpointForGroupBlock")
				||caseType.equalsIgnoreCase("VerifyRoomAvailabilityInEndpointForAccount")
				|| caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
			ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
			ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();
			String beforeReservedCount = null;
			String beforeAvailableRooms = null;

			try {
				navigation.Inventory(driver);
				testSteps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");
				navigation.Rates_Grid(driver);
				testSteps.add("Navigate Rates Grid");
				app_logs.info("Navigate Rates Grid");

				try {
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickOnAvailability(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception f) {

					navigation.Rates_Grid(driver);
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickOnAvailability(driver);
					testSteps.addAll(getTestSteps);
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
				for (String getDate : getDates) {

					calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
					app_logs.info(calendarTodayDate);
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickCalendar(driver);
					testSteps.addAll(getTestSteps);

					ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, testSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid,
							timeZone, getTestSteps);
					testSteps.addAll(getTestSteps);

					int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

					ratesGrid.expandRoomClass(driver, roomClassName, testSteps);
					beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
					app_logs.info("Before Reserved Count : " + beforeReservedCount);
					beforeReservedCountList.add(Integer.parseInt(beforeReservedCount));
					beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
					app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
					beforeAvailableRoomsCountList.add(Integer.parseInt(beforeAvailableRooms));

					ratesGrid.reduceRoomClass(driver, roomClassName, testSteps);

				}
				testSteps.add("Before Reserved Count : " + beforeReservedCountList);
				testSteps.add("Before Available Rooms : " + beforeAvailableRoomsCountList);

				navigation.reservationBackward3(driver);
				testSteps.add("Navigate to Reservations");
				app_logs.info("Navigate to Reservations");

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to get reserved and available room count", testName, testDescription, testCategory,
						testSteps);

			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to get reserved and available room count", testName, testDescription, testCategory,
						testSteps);

			}

			String isDayAvailableBeforeRes = null;
			String beginDateBeforeRes = null;
			String endDateBeforeRes = null;
			ArrayList<String> isDayAvailableBeforeResList = new ArrayList<>();
			ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();
			ArrayList<String> changeOverSignBeforeResList = new ArrayList<>();
			ArrayList<Integer> minStayValueBeforeResList = new ArrayList<>();

			try {
				app_logs.info("===== Getting EndPoint Response =====".toUpperCase());
				testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

				vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps);
				response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

				String resp = vrboObject.getAttributesValue(response, "unitAvailabilityContent.listingExternalId");

				beginDateBeforeRes = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.dateRange.beginDate");
				app_logs.info("unitAvailabilityContent.unitAvailability.dateRange.beginDate : " + beginDateBeforeRes);

				endDateBeforeRes = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.dateRange.endDate");
				app_logs.info("unitAvailabilityContent.unitAvailability.dateRange.endDate : " + endDateBeforeRes);

				app_logs.info("===== VERIFY BEGIN DATE IN RESPONSE IS CURRENT DATE =====".toUpperCase());
				testSteps.add("<b> ===== VERIFY BEGIN DATE IN RESPONSE IS CURRENT DATE ===== </b>".toUpperCase());

				String getCurrentDate = Utility.getNextDate(0, "yyyy-MM-dd", timeZone);
				app_logs.info("Expeted Begin Date : " + getCurrentDate);
				testSteps.add("Expeted Begin Date : " + getCurrentDate);
				app_logs.info("Found : " + beginDateBeforeRes);
				testSteps.add("Found : " + beginDateBeforeRes);

				assertEquals(beginDateBeforeRes, getCurrentDate, "Failed : begind date in response didn't match");
				app_logs.info("Verified begin date in response is current date");
				testSteps.add("Verified begin date in response is current date");

				app_logs.info(
						"===== VERIFY end date in response is two years ahead of current date =====".toUpperCase());
				testSteps.add("<b> ===== VERIFY end date in response is two years ahead of current date ===== </b>"
						.toUpperCase());

				String getEndDate = Utility.getNextDate(730, "yyyy-MM-dd", timeZone);
				app_logs.info("Expeted End Date : " + getEndDate);
				testSteps.add("Expeted End Date : " + getEndDate);
				app_logs.info("Found : " + endDateBeforeRes);
				testSteps.add("Found : " + endDateBeforeRes);

				assertEquals(endDateBeforeRes, getEndDate, "Failed : end date in response didn't match");

				app_logs.info("Verified end date in response is two years ahead of current date");
				testSteps.add("Verified end date in response is two years ahead of current date");

				isDayAvailableBeforeRes = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability");
				app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability : "
						+ isDayAvailableBeforeRes);

				isDayAvailableBeforeRes = isDayAvailableBeforeRes.substring(getDaysbetweenCurrentAndCheckInDate);
				isDayAvailableBeforeRes = isDayAvailableBeforeRes.substring(0, getDaysbetweenDate);
				for (char c : isDayAvailableBeforeRes.toCharArray()) {
					isDayAvailableBeforeResList.add(String.valueOf(c));
				}
				app_logs.info("isDayAvailableBeforeRes : " + isDayAvailableBeforeResList);
				testSteps.add("isDayAvailableBeforeRes : " + isDayAvailableBeforeResList);

				resp = vrboObject.getAttributesValue(response,
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
				if (getDaysbetweenDate > 0) {
					for (int j = 0; j < getDaysbetweenDate; j++) {
						availabilityCountBeforeResList.add(getAvailabilityCount.get(j));
					}
				}
				app_logs.info("availabilityCountBeforeRes : " + availabilityCountBeforeResList);
				testSteps.add("availabilityCountBeforeRes : " + availabilityCountBeforeResList);

				resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.changeOver");
				app_logs.info(
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.changeOver : " + resp);

				resp = resp.substring(getDaysbetweenCurrentAndCheckInDate);
				resp = resp.substring(0, getDaysbetweenDate);

				for (char c : resp.toCharArray()) {
					changeOverSignBeforeResList.add(String.valueOf(c));
				}

				app_logs.info("changeOverSignBeforeRes : " + changeOverSignBeforeResList);
				testSteps.add("changeOverSignBeforeRes : " + changeOverSignBeforeResList);

				resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.minStay");
				app_logs.info(
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.minStay : " + resp);
				String getCount = "";
				for (String s : resp.split(",")) {
					getCount = getCount + s;
				}

				getCount = getCount.substring(getDaysbetweenCurrentAndCheckInDate);
				getCount = getCount.substring(0, getDaysbetweenDate);

				for (char c : getCount.toCharArray()) {
					minStayValueBeforeResList.add(Integer.parseInt(String.valueOf(c)));
				}

				app_logs.info("minStayValueBeforeRes : " + minStayValueBeforeResList);
				testSteps.add("minStayValueBeforeRes : " + minStayValueBeforeResList);

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

			if (reservationType.equalsIgnoreCase("tapeChart")) {

				try {

					navigation.TapeChart(driver);
					testSteps.add("Navigate to tape chart");
					app_logs.info("Navigate to tape chart");

					reservationPage.selectcheckInAndCheckoutInTapeCharts(driver, checkInDate, checkOutDate, timeZone,
							testSteps);
					tapeChart.enterAdult(driver, maxAdult, testSteps);
					tapeChart.selectRatePlan(driver, ratePlanName, testSteps);
					tapeChart.clickSearchButton(driver, testSteps);

					app_logs.info("==========SELECT ROOM==========");
					testSteps.add("==========SELECT ROOM==========");
					tapeChart.clickAvailableSlot(driver, roomClassAbbreviation);
					testSteps.add("Click available room of Room Class '" + roomClassAbbreviation + "'");
					app_logs.info("Click on available room of Room Class '" + roomClassAbbreviation + "'");
					Wait.wait10Second();
					testSteps.add("New Reservation page is opened");
					app_logs.info("New Reservation Page is Opened");

					String room = reservationPage.getRoomSelectedFromTapeChart(driver, testSteps);
					Assert.assertTrue(room.contains(roomClassName), "Failed: Room is not selected");
					testSteps.add("Verified Room Class is '" + roomClassName + "'");

					getTestSteps.clear();
					getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
							guestLastName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
					testSteps.addAll(getTestSteps);

					reservationPage.SelectReferral(driver, referral);

					if (caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
						reservationPage.click_Quote(driver, testSteps);
					} else {
						reservationPage.clickBookNow(driver, testSteps);
					}

					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("reservationNumber : " + reservationNumber);

					reservationStatus = reservationPage.get_ReservationStatus(driver, testSteps);

					reservationPage.clickCloseReservationSavePopup(driver, testSteps);

					roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");

					String selectedRoomClass = reservationPage.getRoomClassResDetail(driver);
					testSteps.add("Selected RoomClass : " + selectedRoomClass);
					app_logs.info("Selected RoomClass : " + selectedRoomClass);

					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create reservation form tapechart", testName,
								"CreatetapeChartRes", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create reservation form tapechart", testName,
								"CreatetapeChartRes", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

			}
			
			if (reservationType.equalsIgnoreCase("Single") || reservationType.equalsIgnoreCase("SingleUnassigned")
					|| reservationType.equalsIgnoreCase("account")) {

				try {
					testSteps.add("<b> ===== CREATING '" + reservationType + "' RESERVATION ===== </b>");
					app_logs.info("<b> ===== CREATING '" + reservationType + "' RESERVATION ===== </b>");

					if (reservationType.equalsIgnoreCase("account")) {

						CreateTA.clickNewReservationFromAccount(driver, testSteps, accountType, account, marketSegment,
								referral, guestFirstName, guestLastName, phoneNumber, alternativePhone, address1,
								address2, address3, email, city, state, postalCode);
					} else if (reservationType.equalsIgnoreCase("Single")
							|| reservationType.equalsIgnoreCase("SingleUnassigned")) {
						reservationPage.click_NewReservation(driver, testSteps);
					}

					getTestSteps.clear();
					getTestSteps = reservationPage.checkInDate(driver, checkInDate);
					testSteps.addAll(getTestSteps);
					app_logs.info(getTestSteps);
					getTestSteps.clear();
					getTestSteps = reservationPage.checkOutDate(driver, checkOutDate);
					testSteps.addAll(getTestSteps);

					reservationPage.enter_Adults(driver, testSteps, maxAdult);
					reservationPage.enter_Children(driver, testSteps, maxPerson);
					reservationPage.select_Rateplan(driver, testSteps, ratePlanName, "");
					reservationPage.clickOnFindRooms(driver, testSteps);
					if (reservationType.equalsIgnoreCase("SingleUnassigned")) {
						isAssign = "No";
						reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
					} else if (reservationType.equalsIgnoreCase("Single")
							|| reservationType.equalsIgnoreCase("Account")) {
						isAssign = "Yes";
						if (reservationType.equalsIgnoreCase("Account")) {
							reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, account);
						} else if (reservationType.equalsIgnoreCase("Single")) {
							reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
						}

					}
					reservationPage.clickNext(driver, testSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
							guestLastName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
					testSteps.addAll(getTestSteps);

					reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard,
							cardExpDate);

					reservationPage.SelectReferral(driver, referral);

					if (caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
						reservationPage.click_Quote(driver, testSteps);
					} else {
						reservationPage.clickBookNow(driver, testSteps);
					}
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("reservationNumber : " + reservationNumber);

					reservationStatus = reservationPage.get_ReservationStatus(driver, testSteps);

					if (caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
						reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
					}

					reservationPage.clickCloseReservationSavePopup(driver, testSteps);

					roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");

					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed reservation tab");
					app_logs.info("Closed reservation tab");

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			}
			
			if (reservationType.equalsIgnoreCase("Group") || reservationType.equalsIgnoreCase("GroupBlock")) {

				// Click New Account and Enter Account Name
				try {
					app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
					testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");

					navigation.secondaryGroupsManu(driver);
					testSteps.add("Navigate Groups");
					app_logs.info(" Navigate Groups");

					getTestSteps.clear();
					getTestSteps = group.enterrGroupName(driver, accountName);
					testSteps.addAll(getTestSteps);

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to click new account and enter account name", testName,
								"EnterAccountName", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Enter Account Number
				try {
					accountNumber = Utility.GenerateRandomString15Digit();
					getTestSteps.clear();
					getTestSteps = group.enterAccountNo(driver, accountNumber);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Enter Account Attributes
				try {
					getTestSteps.clear();
					getTestSteps = group.selectAccountAttributes(driver, marketSegment, referral);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Enter Account Mailing Address
				try {
					getTestSteps.clear();
					getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, phoneNumber,
							address1, city, state, country, postalCode);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
								"EnterAccountMailingAddress", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
								"EnterAccountMailingAddress", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Check Mailing Info CheckBox
				try {
					getTestSteps.clear();
					getTestSteps = group.Billinginfo(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName,
								"CheckMailingInfoCheckBox", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName,
								"CheckMailingInfoCheckBox", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Save Account
				try {
					getTestSteps.clear();
					getTestSteps = group.clickOnSave(driver);
					testSteps.addAll(getTestSteps);

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Save  Account", testName, "SaveAccount", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Save Account", testName, "SaveAccount", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// Navigate to Reservation and check count
				try {
					app_logs.info("==========GET RESERVATION COUNT==========");
					testSteps.add("==========GET RESERVATION COUNT==========");
					getTestSteps.clear();
					group.clickOnGroupsReservationTab(driver);
					testSteps.addAll(getTestSteps);

					String initialResCount = group.getReservationCount(driver);
					testSteps.add("Initial Reservation Count : " + initialResCount);
					Utility.app_logs.info("Initial Reservation Count : " + initialResCount);

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}

				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				if (reservationType.equalsIgnoreCase("GroupBlock")) {

					try {
						app_logs.info("==========CREATE NEW BLOCK==========");
						testSteps.add("==========CREATE NEW BLOCK==========");

						getTestSteps.clear();
						getTestSteps = group.navigateRoomBlock(driver);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = group.ClickNewBlock(driver);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = group.EnterBlockName(driver, blockName);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = group.ClickOkay_CreateNewBlock(driver);
						testSteps.addAll(getTestSteps);

						app_logs.info("==========SEARCH ROOMS==========");
						testSteps.add("==========SEARCH ROOMS==========");

						getTestSteps.clear();
						getTestSteps = group.SelectArrivalDepartureDates(driver,
								ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/MM/yyyy"),
								ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "dd/MM/yyyy"));
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = group.SelectRatePlan(driver, ratePlanName);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = group.SelectAdults(driver, maxAdult);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = group.SelectChilds(driver, maxPerson);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = group.EnterNights(driver, roomPerNight);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = group.ClickSearchGroup(driver);
						testSteps.addAll(getTestSteps);

						advanceGroup.updatedAutomaticallyAssignedRooms(driver, updatedBlockedCount);
						advanceGroup.BlockRoomForSelectedRoomclass(driver, roomBlockCount, roomClassName);

						advanceGroup.ClickCreateBlock(driver, getTestSteps);

						app_logs.info("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
						testSteps.add("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
						String RoomBlocked = group.getRoomBlockedInRoomBlockDetatil(driver);
						Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
						testSteps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
						assertEquals(RoomBlocked, roomPerNight, "Failed Room Blocked Not Matched");

						String totalRoomNight = group.getTotalRoomNightsInRoomBlockDetail(driver);
						Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
						testSteps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);

						String expectedRevenueDetail = group.getExpectedRevenueInRoomBlockDetail(driver);
						Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
						testSteps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);

					} catch (Exception e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group",
									driver);
						} else {
							Assert.assertTrue(false);
						}

					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group",
									driver);
						} else {
							Assert.assertTrue(false);
						}
					}

					String beforeAvailableRoom = null;
					String beforePickupValue = null;
					try {
						String beforeBookIconClass = null;
						app_logs.info("==========ROOMING LIST DETAILS==========");
						testSteps.add("==========ROOMING LIST DETAILS==========");
						Utility.app_logs.info("Book Room From RoomClass : " + roomClassName);
						testSteps.add("Book Room From RoomClass : " + roomClassName);

						beforePickupValue = group.getPickUpValue(driver, roomClassName);
						Utility.app_logs.info("Pickup Value : " + beforePickupValue);
						testSteps.add("Pickup Value : " + beforePickupValue);

						beforeAvailableRoom = group.getAvailableRooms(driver, roomClassName);
						Utility.app_logs.info("Available Rooms : " + beforeAvailableRoom);
						testSteps.add("Available Rooms : " + beforeAvailableRoom);

						beforeBookIconClass = group.getBookIconClass(driver, roomClassName);
						Utility.app_logs.info("BookIcon Class : " + beforeBookIconClass);
						testSteps.add("BookIcon Class : " + beforeBookIconClass);

						String blockedCount = group.getBlocked(driver, roomClassName);
						Utility.app_logs.info("Blocked Count  : " + blockedCount);
						testSteps.add("Blocked Count  : " + blockedCount);

					} catch (Exception e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
						} else {
							Assert.assertTrue(false);
						}

					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
						} else {
							Assert.assertTrue(false);
						}
					}

					// verify
					try {
						app_logs.info("===== VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
								+ "' AFTER CREATING GROUP BLOCK =====");
						testSteps.add("===== VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
								+ "' AFTER CREATING GROUP BLOCK =====");

						navigation.Reservation_Backward(driver);
						testSteps.add("Navigate back to reservation");
						app_logs.info("Navigate back to reservation");

						unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi,
								vrboToken, statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid,
								timeZone, roomClassName, ratePlanName, beforeReservedCountList,
								beforeAvailableRoomsCountList, availabilityCountBeforeResList,
								getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 1);

					} catch (Exception e) {
						Utility.catchException(driver, e, testDescription, testCategory,
								"Failed to verify available rooms in rate grid", testName, testDescription,
								testCategory, testSteps);
					} catch (Error e) {
						Utility.catchError(driver, e, testDescription, testCategory,
								"Failed to verify available rooms in rate grid", testName, testDescription,
								testCategory, testSteps);
					}

					// Create Reservation
					try {
						app_logs.info("==========ENTER RESERVATION DETAILS CREATED FROM <b>GROUP BLOCK</b>==========");
						testSteps.add("==========ENTER RESERVATION DETAILS CREATED FROM <b>GROUP BLOCK</b>==========");

						navigation.secondaryGroupsManu(driver);
						testSteps.add("Navigate Groups");
						app_logs.info(" Navigate Groups");

						group.Search_Account(driver, accountName, accountNumber);
						app_logs.info("Opened group : " + accountNumber);
						testSteps.add("Opened group : " + accountNumber);

						getTestSteps.clear();
						getTestSteps = group.navigateRoomBlock(driver);
						testSteps.addAll(getTestSteps);

						group.bookIconClick(driver, roomClassName);
						Utility.app_logs.info("Click <b>Book Icon</b> of Room Class  : " + roomClassName);
						testSteps.add("Click <b>Book Icon</b> of Room Class  : " + roomClassName);

						String roomnumber = reservationPage.getRoomNumber(driver, getTestSteps);
						Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
						testSteps.add("Verified Room Number is Unassigned");

						getTestSteps.clear();
						getTestSteps = reservationPage.verifyAssociatedAccount_ResHeader(driver, accountName);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean("flase"));
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.enter_GuestName(driver, getTestSteps, salutation, guestFirstName,
								guestLastName);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = reservationPage.selectReferral(driver, referral);
						testSteps.addAll(getTestSteps);
						
						reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard,
								cardExpDate);

						if (caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
							reservationPage.click_Quote(driver, testSteps);
						} else {
							reservationPage.clickBookNow(driver, testSteps);
						}

						reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
						app_logs.info("reservationNumber : " + reservationNumber);

						reservationStatus = reservationPage.get_ReservationStatus(driver, testSteps);

						if (caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
							reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
						}
						reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);

						testSteps.add("Successfully Associated Account to  Reservation");
						app_logs.info("Successfully Associated Account to Reservation");
						getTestSteps.clear();
						getTestSteps = reservationPage.verifyAssociatedAccount(driver, accountName);
						testSteps.addAll(getTestSteps);

						reservationPage.closeReservationTab(driver);
						testSteps.add("Closed opened tab");
						app_logs.info("Closed opened tab");

					} catch (Exception e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Associate Account to Reservation", testName,
									"Reservation", driver);
						} else {
							Assert.assertTrue(false);
						}
					} catch (Error e) {
						if (Utility.reTry.get(testName) == Utility.count) {
							RetryFailedTestCases.count = Utility.reset_count;
							Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
							Utility.updateReport(e, "Failed to Associate Account to Reservation", testName,
									"Reservation", driver);
						} else {
							Assert.assertTrue(false);
						}
					}

				} else {
				
					testSteps.add("<b>" + "===== Create Group Reservation From new Reservation button =====".toUpperCase()
									+ "</b>");
					app_logs.info("===== Create Group Reservation From new Reservation button =====".toUpperCase());

					group.click_GroupNewReservation(driver, testSteps);
					getTestSteps.clear();
					getTestSteps = reservationPage.checkInDate(driver, checkInDate);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.checkOutDate(driver, checkOutDate);
					testSteps.addAll(getTestSteps);

					reservationPage.enter_Adults(driver, testSteps, maxAdult);
					reservationPage.enter_Children(driver, testSteps, maxPerson);
					reservationPage.select_Rateplan(driver, testSteps, ratePlanName, "");
					reservationPage.clickOnFindRooms(driver, testSteps);
					isAssign = "Yes";

					reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
					reservationPage.clickNext(driver, testSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
							guestLastName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
					testSteps.addAll(getTestSteps);

					reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard,
							cardExpDate);

					if (caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
						reservationPage.click_Quote(driver, testSteps);
					} else {
						reservationPage.clickBookNow(driver, testSteps);
					}
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("reservationNumber : " + reservationNumber);

					reservationStatus = reservationPage.get_ReservationStatus(driver, testSteps);

					if (caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
						reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
					}

					reservationPage.clickCloseReservationSavePopup(driver, testSteps);

					roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");

					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed reservation tab");
					app_logs.info("Closed reservation tab");

				}

			}

			if(caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
				try {
					app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
							+ "' REMAINS SAME AFTER CREATING QUOTE RESERVATION========");
					testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
							+ "' REMAINS SAME AFTER CREATING QUOTE RESERVATION ========");
					
					
						unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
								statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
								roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
								availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 0);
					
				}
					catch (Exception e) {
					Utility.catchException(driver, e, testDescription, testCategory,
							"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
							testSteps);

				} catch (Error e) {
					Utility.catchError(driver, e, testDescription, testCategory,
							"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
							testSteps);

				}

			}else {
				
			int getChangeInCount = 0;
			if (!reservationType.isEmpty() && !reservationType.equalsIgnoreCase("null")) {
				getChangeInCount = 1;
			}
			try {
				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CREATING RESERVATION========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CREATING RESERVATION ========");
				
					unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
							statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
							roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
							availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, getChangeInCount);
				
			}
				catch (Exception e) {
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
						testSteps);

			} catch (Error e) {
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
						testSteps);

			}
			try {

				testSteps.add(
						"===== " + "Changing roomclass and verify availability in rategrid and endpoint".toUpperCase()
								+ " =====");
				app_logs.info(
						"===== " + "Changing roomclass and verify availability in rategrid and endpoint".toUpperCase()
								+ " =====");

				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
				testSteps.add("Searched and opened reservation with number : " + reservationNumber);
				app_logs.info("Searched and opened reservation with number : " + reservationNumber);
				reservationPage.ClickEditStayInfo(driver, testSteps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

				reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
				try {
					testSteps.addAll(reservationPage.clickFindRooms(driver));

				} catch (Exception e) {
					app_logs.info("In catch");
					testSteps.add(e.toString());
				}

				String getRoomClass = reservationPage.getRoomClass(driver, roomClassName);
				app_logs.info(getRoomClass);
				if (reservationType.equalsIgnoreCase("Account")) {
					reservationPage.select_RoomWhileChnageDates(driver, testSteps, getRoomClass, "Yes", account);
				} else {
					reservationPage.select_RoomWhileChnageDates(driver, testSteps, getRoomClass, "Yes", "");
				}

				testSteps.addAll(reservationPage.clickSaveAfterEditStayInfo(driver));
				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");
		
				unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
						roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
						availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 0);

				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
				testSteps.add("Searched and opened reservation with number : " + reservationNumber);
				app_logs.info("Searched and opened reservation with number : " + reservationNumber);
				reservationPage.ClickEditStayInfo(driver, testSteps);
				reservationPage.ClickStayInfo_ChangeDetails(driver, testSteps);

				reservationPage.clickStayInfoEditOptions(driver, testSteps, 3);
				try {
					testSteps.addAll(reservationPage.clickFindRooms(driver));

				} catch (Exception e) {
					app_logs.info("In catch");
					testSteps.add(e.toString());
				}

				if (reservationType.equalsIgnoreCase("Account")) {
					reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, "Yes", account);
				} else {
					reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, "Yes", "");
				}

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");
			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify availability after changing roomclass", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify availability after changing roomclass", testName, testDescription,
						testCategory, testSteps);
			}
			try {
	
					testSteps.add("===== "
							+ "Changing reservation status to cancel and verify availability in rategrid and endpoint"
									.toUpperCase()
							+ " =====");
					app_logs.info("===== "
							+ "Changing reservation status to cancel and verify availability in rategrid and endpoint"
									.toUpperCase()
							+ " =====");
	
					reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
					testSteps.add("Searched and opened reservation with number : " + reservationNumber);
					app_logs.info("Searched and opened reservation with number : " + reservationNumber);
	
					reservationPage.changeReservationStatus(driver, testSteps, "Cancel");
	
					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");
	
				} catch (Exception e) {
					e.printStackTrace();
					Utility.catchException(driver, e, testDescription, testCategory,
							"Failed to verify availability after making reservation no show", testName, testDescription,
							testCategory, testSteps);
				} catch (Error e) {
					e.printStackTrace();
					Utility.catchError(driver, e, testDescription, testCategory,
							"Failed to verify availability after making reservation no show", testName, testDescription,
							testCategory, testSteps);
				}
			
			// verify
			try {

				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CANCELLING RESERVATION========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CANCELLING RESERVATION ========");

				unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
						roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
						availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 0);
				
			} catch (Exception e) {
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid and endpoint after cancelling reservation",
						testName, testDescription, testCategory, testSteps);

			} catch (Error e) {
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid and endpoint after cancelling reservation",
						testName, testDescription, testCategory, testSteps);

			}
			
			try {

				testSteps.add("<b> ===== "
						+ "Roll back reservation and verify availability in rategrid and endpoint".toUpperCase()
						+ " =====</b>");
				app_logs.info("===== "
						+ "Roll back reservation and verify availability in rategrid and endpoint".toUpperCase()
						+ " =====");

				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
				testSteps.add("Searched and opened reservation with number : " + reservationNumber);
				app_logs.info("Searched and opened reservation with number : " + reservationNumber);

				testSteps.add("==========Start Verifying Roll Back Button ==========");
				reservationPage.verifyRollBackButton(driver, testSteps);
				reservationPage.clickRollBackButton(driver, testSteps);
				reservationPage.clickYesButtonRollBackMsg(driver, testSteps);

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify response after roll back reservation", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify response after roll back reservation", testName, testDescription,
						testCategory, testSteps);
			}
			
			try {

				app_logs.info("========= VERIFY'" + roomClassName + "' AFTER ROLL BACK BUTTON========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "'AFTER ROLL BACK BUTTON========");

				unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
						roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
						availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 1);
		
			} catch (Exception e) {
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid and endpoint after cancelling reservation",
						testName, testDescription, testCategory, testSteps);

			} catch (Error e) {
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid and endpoint after cancelling reservation",
						testName, testDescription, testCategory, testSteps);

			}
			

			try {

				testSteps.add("===== "
						+ "Changing reservation status to noshow and verify availability in rategrid and endpoint"
								.toUpperCase()
						+ " =====");
				app_logs.info("===== "
						+ "Changing reservation status to noshow and verify availability in rategrid and endpoint"
								.toUpperCase()
						+ " =====");

				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
				testSteps.add("Searched and opened reservation with number : " + reservationNumber);
				app_logs.info("Searched and opened reservation with number : " + reservationNumber);

				reservationPage.noShowReservation(driver, "Void");

				reservationPage.closeReservationTab(driver);
				testSteps.add("Close open Reservation");
				app_logs.info("Close open Reservation");

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content post request", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content post request", testName, testDescription,
						testCategory, testSteps);
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
			try {

				testSteps.add("<b> ===== "
						+ "Roll back reservation and verify availability in rategrid and endpoint".toUpperCase()
						+ " =====</b>");
				app_logs.info("===== "
						+ "Roll back reservation and verify availability in rategrid and endpoint".toUpperCase()
						+ " =====");

				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
				testSteps.add("Searched and opened reservation with number : " + reservationNumber);
				app_logs.info("Searched and opened reservation with number : " + reservationNumber);

				testSteps.add("==========Start Verifying Roll Back Button ==========");
				reservationPage.verifyRollBackButton(driver, testSteps);
				reservationPage.clickRollBackButton(driver, testSteps);
				reservationPage.clickYesButtonRollBackMsg(driver, testSteps);

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify response after roll back reservation", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify response after roll back reservation", testName, testDescription,
						testCategory, testSteps);
			}
						
		try {
				if (Utility.compareDates(Utility.getCurrentDate(defaultDateFormat, timeZone),
						checkInDate.split(Utility.DELIM)[0], defaultDateFormat) == 0) {
					ArrayList<Integer> availabilityCountAfterResList = new ArrayList<>();
					testSteps.add("===== "
							+ "Making early checkout and verify availability in rategrid and endpoint".toUpperCase()
							+ " =====");
					app_logs.info("===== "
							+ "Making early checkout and verify availability in rategrid and endpoint".toUpperCase()
							+ " =====");

					reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
					testSteps.add("Searched and opened reservation with number : " + reservationNumber);
					app_logs.info("Searched and opened reservation with number : " + reservationNumber);
					Wait.wait5Second();
				


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

				//	testSteps.add("<b>==========Start Verifying Roll Back Button ==========</b>");
			//		reservationPage.verifyRollBackButton(driver, testSteps);
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

					app_logs.info("getDates.size() - 1 : " + getDates.size());
					for (int i = 0; i < getDates.size() - 1; i++) {
						int afterCountRes = availabilityCountAfterResList.get(i);
						int beforeCountRes = availabilityCountBeforeResList.get(i);
						app_logs.info("Previous Room availability count : " + beforeCountRes);
						testSteps.add("Previous Room availability count : " + beforeCountRes);
						app_logs.info("Current Room availbility count : " + afterCountRes);
						testSteps.add("Current Room availbility count : " + afterCountRes);
						
							assertEquals(afterCountRes, beforeCountRes - 1,
									"Failed : Availability count in response didn't changed");
						
					}

				testSteps.add("==========Start Verifying Roll Back Button ==========");
				reservationPage.verifyRollBackButton(driver, testSteps);
				reservationPage.clickRollBackButton(driver, testSteps);
				reservationPage.clickYesButtonRollBackMsg(driver, testSteps);

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");

			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to bulk cancel new reservation", testName,
						"BulkCancellationReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to bulk cancel new reservation", testName,
						"BulkCancellationReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
			try {

				testSteps.add("<b>==== "
						+ "Bulk Cancel reservation and verify availability in rategrid and endpoint".toUpperCase()
						+ " =====</b>");
				app_logs.info("==== "
						+ "Bulk Cancel reservation and verify availability in rategrid and endpoint".toUpperCase()
						+ " =====");

				getTestSteps.clear();
				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, false);
				testSteps.add("Successfully Search with Multiple Reservation Numbers");
				app_logs.info("Successfully Search with Multiple Reservation Numbers");

				getTestSteps.clear();
				getTestSteps = reservationSearch.bulkActionCancellation(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add("===== " + "VERIFICATION OF RESERVATIONS STATUS AFTER BULK CANCELATION".toUpperCase()
						+ " =====");
				getTestSteps.clear();
				getTestSteps = reservationPage.verifyReservationStatus(driver, reservationNumber, "Cancelled");
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to bulk cancel new reservation", testName,
							"BulkCancellationReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to bulk cancel new reservation", testName,
							"BulkCancellationReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

				// verify
				try {
					app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
							+ "' AFTER BULK CANCELLING RESERVATION========");
					testSteps.add("<b>========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
							+ "' AFTER BULK CANCELLING RESERVATION ========</b>");

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

				try {

					testSteps.add("Roll back reservation");
					app_logs.info("Roll back reservation");

					reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
					testSteps.add("Searched and opened reservation with number : " + reservationNumber);
					app_logs.info("Searched and opened reservation with number : " + reservationNumber);

					testSteps.add("==========Start Verifying Roll Back Button ==========");
					reservationPage.verifyRollBackButton(driver, testSteps);
					reservationPage.clickRollBackButton(driver, testSteps);
					reservationPage.clickYesButtonRollBackMsg(driver, testSteps);

					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");

				} catch (Exception e) {
					e.printStackTrace();
					Utility.catchException(driver, e, testDescription, testCategory, "Failed to roll back reservation",
							testName, testDescription, testCategory, testSteps);
				} catch (Error e) {
					e.printStackTrace();
					Utility.catchError(driver, e, testDescription, testCategory, "Failed to roll back reservation",
							testName, testDescription, testCategory, testSteps);
				}

				try {

					testSteps.add("<b>==== "
							+ "Bulk NoShow reservation and verify availability in rategrid and endpoint".toUpperCase()
							+ " =====</b>");
					app_logs.info("==== "
							+ "Bulk NoShow reservation and verify availability in rategrid and endpoint".toUpperCase()
							+ " =====");

					getTestSteps.clear();
					getTestSteps = reservationSearch.multipleSearchReservationNumber(driver, reservationNumber);
					testSteps.addAll(getTestSteps);

					testSteps.add("Successfully Search with Multiple Reservation Numbers");
					app_logs.info("Successfully Search with Multiple Reservation Numbers");

					getTestSteps.clear();
					getTestSteps = reservationPage.selectAllSearchedReservationCheckBox(driver);
					testSteps.addAll(getTestSteps);
					reservationPage.selectAllSearchedReservationCheckBox(driver);
					getTestSteps.clear();
					getTestSteps = reservationSearch.makeReservationBulkNoShow(driver);
					testSteps.addAll(getTestSteps);

					testSteps.add("===== " + "VERIFICATION OF RESERVATIONS STATUS AFTER BULK No-Show" + " =====");

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyReservationStatus(driver, reservationNumber, "No-Show");
					testSteps.addAll(getTestSteps);

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to bulk noshow new reservation", testName, "BulkNoshowReservation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to bulk noshow new reservation", testName, "BulkNoshowReservation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// verify
				try {
					app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
							+ "' AFTER MAKE RESERVATION BULK NO SHOW========");
					testSteps.add("<b> ========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
							+ "' AFTER MAKE RESERVATION BULK NO SHOW ======== </b>");
						unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
								statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
								roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
								availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 0);
	
					
					} catch (Exception e) {
					Utility.catchException(driver, e, testDescription, testCategory,
							"Failed to verify available rooms in response after noshow", testName, testDescription,
							testCategory, testSteps);
				} catch (Error e) {
					Utility.catchError(driver, e, testDescription, testCategory,
							"Failed to verify available rooms in response after noshow", testName, testDescription,
							testCategory, testSteps);
				}

//				try {
//
//					reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
//					testSteps.add("Searched and opened reservation with number : " + reservationNumber);
//					app_logs.info("Searched and opened reservation with number : " + reservationNumber);
//
//					testSteps.add("==========Start Verifying Roll Back Button ==========");
//					reservationPage.verifyRollBackButton(driver, testSteps);
//					reservationPage.clickRollBackButton(driver, testSteps);
//					reservationPage.clickYesButtonRollBackMsg(driver, testSteps);
//
//					reservationPage.closeReservationTab(driver);
//					testSteps.add("Closed opened reservation");
//					app_logs.info("Closed opened reservation");
//
//				} catch (Exception e) {
//					e.printStackTrace();
//					Utility.catchException(driver, e, testDescription, testCategory, "Failed to roll back reservation",
//							testName, testDescription, testCategory, testSteps);
//				} catch (Error e) {
//					e.printStackTrace();
//					Utility.catchError(driver, e, testDescription, testCategory, "Failed to roll back reservation",
//							testName, testDescription, testCategory, testSteps);
//				}

				try {

					testSteps.add("<b>==== " + "Bulk Delete reservation".toUpperCase() + " =====</b>");
					app_logs.info("==== " + "Bulk Delete reservation".toUpperCase() + " =====");

					reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
					reservationPage.clickFolio(driver, testSteps);
					homePage.cancelPaymentInFolio(driver, testSteps, paymentType, "Void Payment", "Cancel");

					getTestSteps.clear();
					getTestSteps = folio.voidAllLineItem(driver, "Void to bulk delete");
					testSteps.addAll(getTestSteps);

					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");

					Utility.refreshPage(driver);
					Wait.waitforPageLoad(30, driver);

					getTestSteps.clear();
					getTestSteps = reservationSearch.multipleSearchReservationNumber(driver, reservationNumber);
					testSteps.addAll(getTestSteps);

					testSteps.add("Successfully Search with Multiple Reservation Numbers");
					app_logs.info("Successfully Search with Multiple Reservation Numbers");

					getTestSteps.clear();
					getTestSteps = reservationPage.selectAllSearchedReservationCheckBox(driver);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationSearch.deleteResevation(driver);
					testSteps.addAll(getTestSteps);

					testSteps.add("===== "
							+ "VERIFICATION OF RESERVATION HAS BEEN DELETED AFTER BULK DELETION".toUpperCase() + " =====");

					getTestSteps.clear();
					getTestSteps = reservationSearch.verifyBasicSearchWithReservationNumber(driver, reservationNumber,
							true);
					testSteps.addAll(getTestSteps);

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to bulk Deleted new reservation", testName,
								"BulkDeletedReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to bulk Deleted new reservation", testName,
								"BulkDeletedReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// verify
				try {
					app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
							+ "' AFTER BULK DELETING RESERVATION========");
					testSteps.add("<b>========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
							+ "' AFTER BULK DELETING RESERVATION ========</b>");
					
						unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
								statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
								roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
								availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 0);
	
				} catch (Exception e) {
					Utility.catchException(driver, e, testDescription, testCategory,
							"Failed to verify available rooms in response after bulk deleting reservation", testName,
							testDescription, testCategory, testSteps);

				} catch (Error e) {
					Utility.catchError(driver, e, testDescription, testCategory,
							"Failed to verify available rooms in response after bulk deleting reservation", testName,
							testDescription, testCategory, testSteps);

				}
		}

		}

		if (caseType.equalsIgnoreCase("VerifyAvailabilityForRoomListingReservation")) {

			String expectedRevenue = null;
			String arrivelDate = Utility.getCurrentDate("MMM d, yyyy");
			String departDate = Utility.GetNextDate(1, "MMM d, yyyy");
			String getRoomNumber = null;
			ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
			ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();
			ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();

			try {
				navigation.Inventory(driver);
				testSteps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");

				navigation.Rates_Grid(driver);
				testSteps.add("Navigate Rates Grid");
				app_logs.info("Navigate Rates Grid");

				try {
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickOnAvailability(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception f) {

					navigation.Rates_Grid(driver);
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickOnAvailability(driver);
					testSteps.addAll(getTestSteps);
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
				String beforeReservedCount = "";
				String beforeAvailableRooms = "";

				navigation.Inventory(driver, testSteps);
				testSteps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");

				navigation.Rates_Grid(driver);
				testSteps.add("Navigate Rates Grid");
				app_logs.info("Navigate Rates Grid");

				for (String getDate : getDates) {

					calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
					app_logs.info(calendarTodayDate);
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickCalendar(driver);
					testSteps.addAll(getTestSteps);

					ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, testSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid,
							timeZone, getTestSteps);
					testSteps.addAll(getTestSteps);

					int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

					ratesGrid.expandRoomClass(driver, roomClassName, testSteps);
					beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
					testSteps.add("Before Reserved Count : " + beforeReservedCount);
					app_logs.info("Before Reserved Count : " + beforeReservedCount);
					beforeReservedCountList.add(Integer.parseInt(beforeReservedCount));
					beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
					testSteps.add("Before Available Rooms : " + beforeAvailableRooms);
					app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
					beforeAvailableRoomsCountList.add(Integer.parseInt(beforeAvailableRooms));

					ratesGrid.reduceRoomClass(driver, roomClassName, testSteps);

				}
				navigation.reservationBackward3(driver);
				testSteps.add("Navigate to Reservations");
				app_logs.info("Navigate to Reservations");

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to get reserved and available room count", testName, testDescription, testCategory,
						testSteps);

			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to get reserved and available room count", testName, testDescription, testCategory,
						testSteps);

			}

			try {
				app_logs.info("===== Getting EndPoint Response =====".toUpperCase());
				testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

				vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps);
				response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

				String resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
				app_logs.info(
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
								+ resp);
				testSteps.add(
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
								+ resp);
				String getCount = "";
				for (String s : resp.split(",")) {
					getCount = getCount + s;
				}

				getCount = getCount.substring(getDaysbetweenCurrentAndCheckInDate);
				getCount = getCount.substring(0, getDaysbetweenDate);

				for (char c : getCount.toCharArray()) {
					availabilityCountBeforeResList.add(Integer.parseInt(String.valueOf(c)));
				}

				app_logs.info("availabilityCountBeforeRes : " + availabilityCountBeforeResList);
				testSteps.add("availabilityCountBeforeRes : " + availabilityCountBeforeResList);

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

			// Click New Account and Enter Account Name
			try {
				app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
				testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");

				navigation.secondaryGroupsManu(driver);
				testSteps.add("Navigate Groups");
				app_logs.info(" Navigate Groups");

				getTestSteps.clear();
				getTestSteps = group.enterrGroupName(driver, accountName);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click new account and enter account name", testName,
							"EnterAccountName", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Enter Account Number
			try {
				accountNumber = Utility.GenerateRandomString15Digit();
				getTestSteps.clear();
				getTestSteps = group.enterAccountNo(driver, accountNumber);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Enter Account Attributes
			try {
				getTestSteps.clear();
				getTestSteps = group.selectAccountAttributes(driver, marketSegment, referral);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Enter Account Mailing Address
			try {
				getTestSteps.clear();
				getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, phoneNumber, address1,
						city, state, country, postalCode);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
							"EnterAccountMailingAddress", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
							"EnterAccountMailingAddress", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Check Mailing Info CheckBox
			try {
				getTestSteps.clear();
				getTestSteps = group.Billinginfo(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName,
							"CheckMailingInfoCheckBox", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName,
							"CheckMailingInfoCheckBox", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Save Account
			try {
				getTestSteps.clear();
				getTestSteps = group.clickOnSave(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Save  Account", testName, "SaveAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Save Account", testName, "SaveAccount", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Navigate to Reservation and check count
			try {
				app_logs.info("==========GET RESERVATION COUNT==========");
				testSteps.add("==========GET RESERVATION COUNT==========");
				getTestSteps.clear();
				group.clickOnGroupsReservationTab(driver);
				testSteps.addAll(getTestSteps);

				String initialResCount = group.getReservationCount(driver);
				testSteps.add("Initial Reservation Count : " + initialResCount);
				Utility.app_logs.info("Initial Reservation Count : " + initialResCount);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("==========CREATE NEW BLOCK==========");
				testSteps.add("<b>==========CREATE NEW BLOCK==========</b>");

				getTestSteps.clear();
				getTestSteps = group.navigateRoomBlock(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickNewBlock(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.EnterBlockName(driver, blockName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickOkay_CreateNewBlock(driver);
				testSteps.addAll(getTestSteps);

				app_logs.info("==========SEARCH ROOMS==========");
				testSteps.add("==========SEARCH ROOMS==========");

				getTestSteps.clear();
				getTestSteps = group.SelectArrivalDepartureDates(driver, Utility.getCurrentDate("dd/MM/yyyy"),
						Utility.GetNextDate(1, "dd/MM/yyyy"));
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.SelectRatePlan(driver, ratePlanName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.SelectAdults(driver, maxAdult);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.SelectChilds(driver, maxPerson);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.EnterNights(driver, roomPerNight);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.ClickSearchGroup(driver);
				testSteps.addAll(getTestSteps);

				advanceGroup.updatedAutomaticallyAssignedRooms(driver, updatedBlockedCount);
				advanceGroup.BlockRoomForSelectedRoomclass(driver, roomBlockCount, roomClassName);

				getTestSteps.clear();
				getTestSteps = advanceGroup.ClickCreateBlock(driver, getTestSteps);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add("==========CREATE A NEW RESERVATION FORM ROOMING LIST==========");
				app_logs.info("==========CREATE A NEW RESERVATION FORM ROOMING LIST==========");

				getTestSteps.clear();
				getTestSteps = group.clickOnRoomingListButton(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.enterReservationContentIntoRoomListingPopup(driver, guestFirstName, guestLastName,
						baseAmount, roomClassName);
				testSteps.addAll(getTestSteps);

				getRoomNumber = group.getSelecteRoomNumber(driver);

				getTestSteps.clear();
				getTestSteps = group.clickOnBillingInfoIcon(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.enterBillingInfoInRoomListing(driver, salutation, paymentType, cardNumber,
						cardExpDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = group.clickOnPickupButton(driver);
				testSteps.addAll(getTestSteps);

				reservationNumber = group.getReservationNumberfromRoomingListSummary(driver, guestName);
				testSteps.add("Reservation #: " + reservationNumber);

				group.getGuestNamefromRoomingListSummary(driver, reservationNumber, true);
				testSteps.add("Click on guest name in rooming list - pick up summary");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create a reservation form rooming list", testName, "Group",
							driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create a reservation form rooming list", testName, "Group",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			String groupName = "";
			try {

				app_logs.info("==========VERIFY RESERVATION DETAILS POPUP==========");
				testSteps.add("==========VERIFY RESERVATION DETAILS POPUP==========");
				Elements_AdvanceGroups elementsAdvance = new Elements_AdvanceGroups(driver);

				String getGuestName = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.GuestNameInReservationDetails, OR.GuestNameInReservationDetails);
				testSteps.add("Expected guest name: " + guestName);
				testSteps.add("Found: " + getGuestName);
				assertEquals(getGuestName, guestName, "Failed: guest name is mismatching in reservation details popup");
				testSteps.add("Verified guest name");
				String getGroupName = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.GroupNameInReservationDetails, OR.GroupNameInReservationDetails);
				testSteps.add("Expected group name: " + groupName);
				testSteps.add("Found: " + getGroupName);
				assertEquals(getGroupName, groupName, "Failed: group name is mismatching in reservation details popup");
				testSteps.add("Verified group name");

				String confirmationNumber = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.ConfirmationNumberInReservationDetails,
						OR.ConfirmationNumberInReservationDetails);
				testSteps.add("Expected confirmation number: " + guestName);
				testSteps.add("Found: " + confirmationNumber);
				assertEquals(confirmationNumber, reservationNumber,
						"Failed: reservation number is mismatching in reservation details popup");
				testSteps.add("Verified reservation number");

				String expectedTripTotal = "$ " + baseAmount + ".00";
				String tripTotal = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.TripTotalInReservationDetails, OR.TripTotalInReservationDetails);
				testSteps.add("Expected trip total: " + expectedTripTotal);
				testSteps.add("Found: " + tripTotal);
				assertEquals(tripTotal, expectedTripTotal,
						"Failed: trip total is mismatching in reservation details popup");
				testSteps.add("Verified trip total");

				String expectedBalance = folio.MinseTwoValue(folio.splitString(tripTotal), baseAmount);
				String balance = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.BalanceInReservationDetails, OR.BalanceInReservationDetails);
				testSteps.add("Expected balance: $ " + expectedBalance);
				testSteps.add("Found: " + balance);
				assertEquals(balance, "$ " + expectedBalance,
						"Failed: balance is mismatching in reservation details popup");
				testSteps.add("Verified balance");

				String getRatePlan = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.RatePlanInReservationDetails, OR.RatePlanInReservationDetails);
				testSteps.add("Expected rate plan: Promo Code");
				testSteps.add("Found: " + getRatePlan);
				assertEquals(getRatePlan, "Promo Code",
						"Failed: rate plan is mismatching in reservation details popup");
				testSteps.add("Verified rate plan");

				String getPromoCode = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.PromoInReservationDetails, OR.PromoInReservationDetails);
				testSteps.add("Expected promo code: GBR");
				testSteps.add("Found: " + getPromoCode);
				assertTrue(getPromoCode.contains("GBR"),
						"Failed: rate plan is mismatching in reservation details popup");
				testSteps.add("Verified promo code");

				String getArrivelDate = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.CheckinDateInReservtionDetails, OR.CheckinDateInReservtionDetails);
				testSteps.add("Expected check-in date: " + arrivelDate);
				testSteps.add("Found: " + getArrivelDate);
				assertEquals(getArrivelDate, arrivelDate,
						"Failed: check-in date is mismatching in reservation details popup");
				testSteps.add("Verified check-in date");

				String getDepartDate = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.CheckoutDateInResrvationDetails, OR.CheckoutDateInResrvationDetails);
				testSteps.add("Expected check-out date: " + departDate);
				testSteps.add("Found: " + getDepartDate);
				assertEquals(getDepartDate, departDate,
						"Failed: check-out date is mismatching in reservation details popup");
				testSteps.add("Verified check-out date");

				String getRoomClass = group.getReservationDetailsfromPopup(driver,
						elementsAdvance.RoomClassInResrvationDetails, OR.RoomClassInResrvationDetails);
				testSteps.add("Expected room class: " + roomClassName);
				testSteps.add("Found: " + getRoomClass);
				assertEquals(getRoomClass, roomClassName,
						"Failed: room class is mismatching in reservation details popup");
				testSteps.add("Verified room class");

				roomNumber = group.getReservationDetailsfromPopup(driver, elementsAdvance.RoomNumberInResrvationDetails,
						OR.RoomNumberInResrvationDetails);
				testSteps.add("Expected room number: " + getRoomNumber);
				testSteps.add("Found: " + roomNumber);
				assertEquals(roomNumber, getRoomNumber,
						"Failed: room number is mismatching in reservation details popup");
				testSteps.add("Verified room number");

				group.verifyStartCheckInButton(driver);
				testSteps.add("Verified start check-in button is dispaying on reservation edtails popup");

				group.clickOnCloseReservationDetailsPopup(driver);
				testSteps.add("Click on close reservation details popup");

				group.clickonClosePickedupSummary(driver);
				testSteps.add("Click on close pickedup details summary popup");

				group.clickonCloseRoomingListPopup(driver);
				testSteps.add("Click on close rooming list popup");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify reservatin details in reservation details popup",
							testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify reservatin details in reservation details popup",
							testName, "Group", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// verify
			try {
				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CREATING RESERVATION FROM ROOMING LIST========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CREATING RESERVATION FROM ROOMING LIST  ========");

				unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
						roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
						availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 1);
			} catch (Exception e) {
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid after creating reservation from room listing",
						testName, testDescription, testCategory, testSteps);

			} catch (Error e) {
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid after creating reservation from room listing",
						testName, testDescription, testCategory, testSteps);

			}

		}

		if (caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservatuionWithInvalidCardNumber")) {
			ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
			ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();
			String beforeReservedCount = null;
			String beforeAvailableRooms = null;
			cardNumber = "1451541555112345";
			String takePaymentType = "Capture";
			try {
				navigation.Inventory(driver);
				testSteps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");
				navigation.Rates_Grid(driver);
				testSteps.add("Navigate Rates Grid");
				app_logs.info("Navigate Rates Grid");

				try {
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickOnAvailability(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception f) {

					navigation.Rates_Grid(driver);
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickOnAvailability(driver);
					testSteps.addAll(getTestSteps);
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
				for (String getDate : getDates) {

					calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
					app_logs.info(calendarTodayDate);
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickCalendar(driver);
					testSteps.addAll(getTestSteps);

					ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, testSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid,
							timeZone, getTestSteps);
					testSteps.addAll(getTestSteps);

					int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

					ratesGrid.expandRoomClass(driver, roomClassName, testSteps);
					beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
					app_logs.info("Before Reserved Count : " + beforeReservedCount);
					beforeReservedCountList.add(Integer.parseInt(beforeReservedCount));
					beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
					app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
					beforeAvailableRoomsCountList.add(Integer.parseInt(beforeAvailableRooms));
					ratesGrid.reduceRoomClass(driver, roomClassName, testSteps);

				}
				testSteps.add("Before Reserved Count : " + beforeReservedCountList);
				testSteps.add("Before Available Rooms : " + beforeAvailableRoomsCountList);

				navigation.reservationBackward3(driver);
				testSteps.add("Navigate to Reservations");
				app_logs.info("Navigate to Reservations");

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to get reserved and available room count", testName, testDescription, testCategory,
						testSteps);

			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to get reserved and available room count", testName, testDescription, testCategory,
						testSteps);

			}

			String isDayAvailableBeforeRes = null;
			String beginDateBeforeRes = null;
			String endDateBeforeRes = null;
			ArrayList<String> isDayAvailableBeforeResList = new ArrayList<>();
			ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();

			try {
				app_logs.info("===== Getting EndPoint Response =====".toUpperCase());
				testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

				vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps);
				response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

				String resp = vrboObject.getAttributesValue(response, "unitAvailabilityContent.listingExternalId");

				beginDateBeforeRes = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.dateRange.beginDate");
				app_logs.info("unitAvailabilityContent.unitAvailability.dateRange.beginDate : " + beginDateBeforeRes);

				endDateBeforeRes = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.dateRange.endDate");
				app_logs.info("unitAvailabilityContent.unitAvailability.dateRange.endDate : " + endDateBeforeRes);

				app_logs.info("===== VERIFY BEGIN DATE IN RESPONSE IS CURRENT DATE =====".toUpperCase());
				testSteps.add("<b> ===== VERIFY BEGIN DATE IN RESPONSE IS CURRENT DATE ===== </b>".toUpperCase());

				String getCurrentDate = Utility.getNextDate(0, "yyyy-MM-dd", timeZone);
				app_logs.info("Expeted Begin Date : " + getCurrentDate);
				testSteps.add("Expeted Begin Date : " + getCurrentDate);
				app_logs.info("Found : " + beginDateBeforeRes);
				testSteps.add("Found : " + beginDateBeforeRes);

				assertEquals(beginDateBeforeRes, getCurrentDate, "Failed : begind date in response didn't match");
				app_logs.info("Verified begin date in response is current date");
				testSteps.add("Verified begin date in response is current date");

				app_logs.info(
						"===== VERIFY end date in response is two years ahead of current date =====".toUpperCase());
				testSteps.add("<b> ===== VERIFY end date in response is two years ahead of current date ===== </b>"
						.toUpperCase());

				String getEndDate = Utility.getNextDate(730, "yyyy-MM-dd", timeZone);
				app_logs.info("Expeted End Date : " + getEndDate);
				testSteps.add("Expeted End Date : " + getEndDate);
				app_logs.info("Found : " + endDateBeforeRes);
				testSteps.add("Found : " + endDateBeforeRes);

				assertEquals(endDateBeforeRes, getEndDate, "Failed : end date in response didn't match");

				app_logs.info("Verified end date in response is two years ahead of current date");
				testSteps.add("Verified end date in response is two years ahead of current date");

				isDayAvailableBeforeRes = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability");
				app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability : "
						+ isDayAvailableBeforeRes);

				isDayAvailableBeforeRes = isDayAvailableBeforeRes.substring(getDaysbetweenCurrentAndCheckInDate);
				isDayAvailableBeforeRes = isDayAvailableBeforeRes.substring(0, getDaysbetweenDate);
				for (char c : isDayAvailableBeforeRes.toCharArray()) {
					isDayAvailableBeforeResList.add(String.valueOf(c));
				}
				app_logs.info("isDayAvailableBeforeRes : " + isDayAvailableBeforeResList);
				testSteps.add("isDayAvailableBeforeRes : " + isDayAvailableBeforeResList);

				resp = vrboObject.getAttributesValue(response,
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
						"Failed to verify unit availability content response", testName, testDescription, testCategory,
						testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content response", testName, testDescription, testCategory,
						testSteps);
			}
			
				try {
					testSteps.add("<b> ===== CREATING '" + reservationType + "' RESERVATION ===== </b>");
					app_logs.info("<b> ===== CREATING '" + reservationType + "' RESERVATION ===== </b>");


					getTestSteps.clear();
					getTestSteps = reservationPage.checkInDate(driver, checkInDate);
					testSteps.addAll(getTestSteps);
					app_logs.info(getTestSteps);
					getTestSteps.clear();
					getTestSteps = reservationPage.checkOutDate(driver, checkOutDate);
					testSteps.addAll(getTestSteps);

					reservationPage.enter_Adults(driver, testSteps, maxAdult);
					reservationPage.enter_Children(driver, testSteps, maxPerson);
					reservationPage.select_Rateplan(driver, testSteps, ratePlanName, "");
					reservationPage.clickOnFindRooms(driver, testSteps);
					isAssign = "No";
					reservationPage.select_Room(driver, testSteps, roomClassName, isAssign, "");
					reservationPage.clickNext(driver, testSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
							guestLastName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
					testSteps.addAll(getTestSteps);

					reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard,
							cardExpDate);

					reservationPage.SelectReferral(driver, referral);

					reservationPage.click_Quote(driver, testSteps);
					
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					app_logs.info("reservationNumber : " + reservationNumber);

					reservationStatus = reservationPage.get_ReservationStatus(driver, testSteps);
					reservationPage.verify_QuoteStauts(driver, testSteps, reservationStatus);
					
					reservationPage.clickCloseReservationSavePopup(driver, testSteps);

					roomNumber = reservationPage.get_RoomNumber(driver, testSteps, "Yes");

					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed reservation tab");
					app_logs.info("Closed reservation tab");

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}
				
				try {
					reservationSearch.basicSearch_WithResNumber(driver, reservationNumber, true);
					Wait.wait5Second();
					reservationPage.clickFolio(driver, testSteps);
					Wait.wait5Second();
					reservationPage.verifyTransactionDeclinedMessageInTakePaymentPopup(driver);
					
					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");
				}
				catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify decline transcation message for quote reservation", testName, "TransactionDeclineMessage", driver);
					} else {
					Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();						
					if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify decline transcation message for quote reservation", testName, "TransactionDeclineMessage", driver);
					} else {
					Assert.assertTrue(false);
					}
				}
				
				try {
				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CREATING QUOTE RESERVATION WITH INVALID CARD NUMBER ========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CREATING QUOTE RESERVATION WITH INVALID CARD NUMBER ========");

					unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
							statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
							roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
							availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate, 0);
				}
					catch (Exception e) {
					Utility.catchException(driver, e, testDescription, testCategory,
							"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
							testSteps);

				} catch (Error e) {
					Utility.catchError(driver, e, testDescription, testCategory,
							"Failed to verify available rooms in rate grid", testName, testDescription, testCategory,
							testSteps);

				}

		}
		
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);

		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to add steps into report",
					testName, testDescription, testCategory, testSteps);

		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to add steps into report", testName,
					testDescription, testCategory, testSteps);

		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("vrboUnitAvailabilityContent", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
