package com.innroad.inncenter.tests;

import static org.testng.Assert.assertTrue;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.endpoints.DBCon;
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

public class VerifyUnitAvailabilityContentApiForMrb extends TestCore {
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
	public void verifyUnitAvailabilityContentApiForMrb(String caseType, String reservationType, String delim,
			String roomClassName, String ratePlanName, String checkInDate, String checkOutDate) throws Exception {
		if (delim.equalsIgnoreCase("|")) {
			Utility.DELIM = "\\" + delim;

		} else {
			Utility.DELIM = delim;
		}

		DBCon dbCon=new DBCon();
		Connection con = dbCon.getCon();
		app_logs.info("DELIM : " + delim);
		app_logs.info("DELIM : " + Utility.DELIM);
		testName = "VerifyVrboUnitAvailabilityContent-" + caseType;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		String defaultDateFormat = "MM/dd/yyyy";
		String salutation = "Mr.";
		String guestFirstName = endPointName + randomString;
		String guestLastName = "Res" + randomString;
		String phoneNumber = "8790321567";
		String alternativePhone = "8790321567";
		String email = "innroadautomation@innroad.com";

		String address1 = "test1";
		String address2 = "test2";
		String address3 = "test3";
		String city = "New york";
		String state = "Alaska";
		String country = "United States";
		String paymentType = "MC";
		String cardNumber = "5454545454545454";
		String nameOnCard = "testUser";
		String cardExpDate = "12/23";
		String referral = "Other";
		String postalCode = "12345";
		String isGuesProfile = "No";
		String mrbRatePlan = ratePlanName + "|" + ratePlanName;
		String mrbRoomClassName = roomClassName.split(Utility.DELIM) + "|" + roomClassName;
		String mrbSalutation = salutation + "|" + salutation;
		String mrbFirstName = guestFirstName + "|" + guestFirstName + randomString;
		String mrbLastName = guestLastName + "|" + guestLastName + randomString;
		String mrbPhoneNumber = phoneNumber + "|" + phoneNumber;
		String isSplitRes = "No";
		String mrbMaxAdult = "";
		String mrbMaxPerson = "";
		String checkInArr[] = checkInDate.split(Utility.DELIM);
		String checkOutArr[] = checkOutDate.split(Utility.DELIM);
		String timeZone = "";
		String mrbCheckin = "";
		String mrbCheckOut = "";
		String reservationNumber = "";
		String reservationStatus = "";
		String maxAdult = "";
		String maxPerson = "";
		String roomClassAbbreviation = "";
		int getDaysbetweenCurrentAndCheckInDate = Utility
				.getNumberofDays(Utility.getCurrentDate(defaultDateFormat, timeZone), checkInDate, defaultDateFormat);
		int getDaysbetweenDate = Utility.getNumberofDays(checkInDate, checkOutDate, defaultDateFormat);
		ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();
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
			
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to logged into app", testName,
					testDescription, testCategory, testSteps);

		} catch (Error e) {
			
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
		  app_logs.info(propertyId);
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
		  app_logs.info(roomClassId);
		  navigation.reservationBackward3(driver);
		  testSteps.add("Back to reservation");
		  

		  } catch (Exception e) {  if (Utility.reTry.get(testName)
		  == Utility.count) { RetryFailedTestCases.count = Utility.reset_count;
		  Utility.AddTest_IntoReport(testName, testDescription, testCategory,
		  testSteps); Utility.updateReport(e,
		  "Failed to get propertyId, timeZone and roomclassId", testName,
		  "getPreconditions", driver);
		  
		  } else { assertTrue(false); } } catch (Error e) { 
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
			
			Utility.catchException(driver, e, testDescription, testCategory, "Failed to get ratePlanId", testName,
					testDescription, testCategory, testSteps);
		} catch (Error e) {
			
			Utility.catchError(driver, e, testDescription, testCategory, "Failed to get ratePlanId", testName,
					testDescription, testCategory, testSteps);
		}

		  
		
		roomClassAbbreviation = roomClassName;

		maxAdult = "1";
		maxPerson = "0";
		mrbMaxAdult = maxAdult + "|" + maxAdult;
		mrbMaxPerson = maxPerson + "|" + maxPerson;
		timeZone = "America/New_York";
		int getChangeInCount = 0;
	
		//db for qa
		int dbAdvertiserId = 0;
		int lastRecordIdBeforeAddingNewRes = 0;
		
		getUnitAvailabilityContentApi = VrboEndPoints.getUnitAvailabilityContentApi(propertyId, roomClassId,
				ratePlanId);
		app_logs.info("Endpoint Name: " + endPointName);
		app_logs.info("unitAvailabilityContent Url : " + getUnitAvailabilityContentApi);
		
		if (caseType.equalsIgnoreCase("VerifyRoomAvailabilityInEndpointForMRB")
				|| caseType.equalsIgnoreCase("VerifyAvailabilityForQuoteReservation")) {
			
			ArrayList<Integer> availabilityCountList = new ArrayList<>();
			HashMap<Integer, ArrayList<Integer>> mapBeforeRes = new HashMap<>();
			ArrayList<String> checkInDatesList = new ArrayList<>();
			ArrayList<String> checkOutDatesList = new ArrayList<>();

			if (reservationType.equalsIgnoreCase("MRB")) {
				if (checkInDate.isEmpty() || checkInDate.equalsIgnoreCase("") || checkOutDate.isEmpty()
						|| checkOutDate.equalsIgnoreCase("")) {
					checkInDate = Utility.getCurrentDate(defaultDateFormat, timeZone) + Utility.DELIM
							+ Utility.getNextDate(1, defaultDateFormat);
					checkOutDate = Utility.getNextDate(2, defaultDateFormat) + Utility.DELIM
							+ Utility.getNextDate(3, defaultDateFormat);
				}
				app_logs.info("mrbCheckInDate : " + checkInDate);
				app_logs.info("mrbCheckOutDate : " + checkOutDate);

				app_logs.info("checkInDate.split(Utility.DELIM) : " + checkInDate.split(Utility.DELIM).length);
				app_logs.info("First Index : " + checkInDate.split(Utility.DELIM)[0] + " : "
						+ checkOutDate.split(Utility.DELIM)[0]);
				app_logs.info("Second Index : " + checkInDate.split(Utility.DELIM)[1] + " : "
						+ checkOutDate.split(Utility.DELIM)[1]);

				mrbCheckin = "";
				mrbCheckOut = "";
				for (String s : checkInDate.split(Utility.DELIM)) {
					if (mrbCheckin.isEmpty() || mrbCheckin.equalsIgnoreCase(null)) {
						mrbCheckin = ESTTimeZone.reformatDate(s, defaultDateFormat, "dd/MM/yyyy");
					} else {
						mrbCheckin = mrbCheckin + "|" + ESTTimeZone.reformatDate(s, defaultDateFormat, "dd/MM/yyyy");
					}
				}

				for (String s : checkOutDate.split(Utility.DELIM)) {
					if (mrbCheckOut.isEmpty() || mrbCheckOut.equalsIgnoreCase(null)) {
						mrbCheckOut = ESTTimeZone.reformatDate(s, defaultDateFormat, "dd/MM/yyyy");
					} else {
						mrbCheckOut = mrbCheckOut + "|" + ESTTimeZone.reformatDate(s, defaultDateFormat, "dd/MM/yyyy");
					}
				}
				app_logs.info("mrbCheckInDate : " + mrbCheckin);
				app_logs.info("mrbCheckOutDate : " + mrbCheckOut);
				for (String s : checkInArr) {
					checkInDatesList.add(s);
				}
				for (String s : checkOutArr) {
					checkOutDatesList.add(s);
				}

				try {
					app_logs.info("===== Getting last Entry form database =====".toUpperCase());
					testSteps.add("<b> ===== Getting last Entry form database ===== </b>".toUpperCase());
					con=dbCon.openConnection();
					dbAdvertiserId=dbCon.getAdvertiserIdFromDb(con, advertiserId);
					app_logs.info("Advertiser Id in database : " + dbAdvertiserId);
					testSteps.add("Advertiser Id in database : " + dbAdvertiserId);
					lastRecordIdBeforeAddingNewRes=dbCon.getLastRecordFromDb(con, dbAdvertiserId);
					dbCon.closeConnection();
					app_logs.info("Last record Id in database : " + lastRecordIdBeforeAddingNewRes);
					testSteps.add("Last record Id in database : " + lastRecordIdBeforeAddingNewRes);
					
					app_logs.info("===== Getting EndPoint Response =====".toUpperCase());
					testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

					for (int i = 0; i < checkInArr.length; i++) {
						int getDaysBetweenCurrentAndCheckIn = Utility.getNumberofDays(
								Utility.getCurrentDate(defaultDateFormat, timeZone), checkInArr[i], defaultDateFormat);
						int getDaysBetweenDates = Utility.getNumberofDays(checkInArr[i], checkOutArr[i],
								defaultDateFormat);
						app_logs.info("getDaysBetweenCurrentAndCheckIn : " + getDaysBetweenCurrentAndCheckIn);
						app_logs.info("getDaysBetweenDates : " + getDaysBetweenDates);

						vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
								statusCode200, testSteps);
						response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

						String resp = vrboObject.getAttributesValue(response,
								"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
						app_logs.info(
								"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
										+ resp);
						ArrayList<Integer> getAvailabilityCount = new ArrayList<>();
						String mrbArr[] = resp.split(",");
						app_logs.info("getAvailabilityCount : " + mrbArr.length);
						for (int j = 0; j < mrbArr.length; j++) {
							if (j >= getDaysbetweenCurrentAndCheckInDate) {
								getAvailabilityCount.add(Integer.parseInt(mrbArr[j]));
							}
						}

						app_logs.info("getAvailabilityCount : " + getAvailabilityCount);
						availabilityCountList.clear();
						if (getDaysBetweenDates > 0) {
							for (int j = 0; j < getDaysBetweenDates; j++) {
								availabilityCountList.add(getAvailabilityCount.get(j));
							}
						}
						app_logs.info("availabilityCountBeforeResListFirstDateRange : " + availabilityCountList);

						mapBeforeRes.put(i, availabilityCountList);

					}
					app_logs.info("checkInArr length : " + checkInArr.length);
					app_logs.info("Final mapBeforeRes : " + mapBeforeRes);
				} catch (Exception e) {
					
					Utility.catchException(driver, e, testDescription, testCategory,
							"Failed to get unit availability content response before creating new res", testName,
							testDescription, testCategory, testSteps);
				} catch (Error e) {
					
					Utility.catchError(driver, e, testDescription, testCategory,
							"Failed to get unit availability content response before creating new res", testName,
							testDescription, testCategory, testSteps);
				}

				try {
					testSteps.add("<b> ===== CREATING 'MRB' RESERVATION ===== </b>");
					app_logs.info("<b> ===== CREATING 'MRB' RESERVATION ===== </b>");
					mrbRatePlan = "";
					mrbRoomClassName = "";
					mrbSalutation = "";
					mrbFirstName = "";
					mrbLastName = "";
					mrbPhoneNumber = "";
					app_logs.info("mrbCheckin.split(\"|\") Length : " + checkInDate.split(Utility.DELIM).length);

					for (int i = 0; i < mrbCheckin.split(Utility.DELIM).length; i++) {
						if (mrbSalutation != "" || mrbFirstName != "" || mrbLastName != "" || mrbPhoneNumber != "") {
							mrbSalutation = mrbSalutation + "|" + salutation;
							mrbFirstName = mrbFirstName + "|" + guestFirstName;
							mrbLastName = mrbLastName + "|" + guestLastName;
							mrbPhoneNumber = mrbPhoneNumber + "|" + phoneNumber;
						} else {
							mrbSalutation = salutation;
							mrbFirstName = guestFirstName;
							mrbLastName = guestLastName;
							mrbPhoneNumber = phoneNumber;
						}

					}
					app_logs.info(mrbSalutation);
					app_logs.info(salutation);
					mrbRoomClassName = roomClassName;
					mrbRatePlan = ratePlanName;

					reservationPage.click_NewReservation(driver, testSteps);
					reservationPage.select_Dates(driver, testSteps, mrbCheckin, mrbCheckOut, mrbMaxAdult, mrbMaxPerson,
							mrbRatePlan, "", isSplitRes);

					if (isSplitRes.equalsIgnoreCase("Yes")) {
						getTestSteps.clear();
						reservationPage.enter_Adults(driver, getTestSteps, maxAdult);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						reservationPage.enter_Children(driver, getTestSteps, maxPerson);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						reservationPage.select_Rateplan(driver, getTestSteps, ratePlanName, "");
						testSteps.addAll(getTestSteps);

					}

					reservationPage.clickOnFindRooms(driver, testSteps);
					reservationPage.select_MRBRooms(driver, testSteps, mrbRoomClassName, "Yes", "");
					try {
						reservationPage.clickNext(driver, testSteps);

					} catch (Exception e) {
						reservationPage.clickSelectRoom(driver,
								mrbRoomClassName.split("\\|")[mrbRoomClassName.split("\\|").length - 1], testSteps);
						reservationPage.clickNext(driver, testSteps);

					}

					getTestSteps.clear();
					Wait.wait5Second();
					reservationPage.enter_MRB_MailingAddress(driver, testSteps, mrbSalutation, mrbFirstName,
							mrbLastName, mrbPhoneNumber, alternativePhone, email, "", "", address1, address2,
							address3, city, country, state, postalCode, isGuesProfile, "", isSplitRes, getTestSteps);

					reservationPage.SelectReferral(driver, referral);
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

					reservationPage.closeReservationTab(driver);
					testSteps.add("Closed opened reservation");
					app_logs.info("Closed opened reservation");

				} catch (Exception e) {
					
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create mrb reservation", testName, "MrbReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create mrb reservation", testName, "MrbReservation", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			}
			
			try {
				app_logs.info("===== Getting last Entry from database and verify new record has been entered successfully =====".toUpperCase());
				testSteps.add("<b> ===== Getting last Entry from database and verify new record has been entered successfully ===== </b>".toUpperCase());
				con=dbCon.openConnection();
				int lastRecordIdAfterCreatingRes = dbCon.getLastRecordFromDb(con, dbAdvertiserId);
				app_logs.info("Last record Id in database : " + lastRecordIdAfterCreatingRes+1);
				testSteps.add("Last record Id in database : " + lastRecordIdAfterCreatingRes);
				
				Utility.verifyNotEqual(lastRecordIdAfterCreatingRes+1, lastRecordIdBeforeAddingNewRes, testSteps);
				dbCon.closeConnection();
			} catch (Exception e) {
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify database has new record entered", testName, testDescription, testCategory,
						testSteps);

			} catch (Error e) {
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify database has new record entered", testName, testDescription, testCategory,
						testSteps);

			}

			
			
				
			getChangeInCount = mrbCheckin.split("\\|").length;
			app_logs.info(getChangeInCount);

			try {
				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CREATING RESERVATION========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CREATING RESERVATION ========");

				unitAvailable.verifyRoomAvaibiltyForMRB(driver, testSteps, checkInArr, defaultDateFormat, timeZone,
						vrboToken, endPointName, getUnitAvailabilityContentApi, checkOutArr, statusCode200,
						getDaysbetweenCurrentAndCheckInDate,  checkInDatesList, checkOutDatesList,
						mapBeforeRes, getChangeInCount,false);

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

				testSteps.add(
						"===== " + "Changing roomclass and verify availability in rategrid and endpoint".toUpperCase()
								+ " =====");
				app_logs.info(
						"===== " + "Changing roomclass and verify availability in rategrid and endpoint".toUpperCase()
								+ " =====");

				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
				testSteps.add("Searched and opened reservation with number : " + reservationNumber);
				app_logs.info("Searched and opened reservation with number : " + reservationNumber);

				app_logs.info("===== "
						+ "Verifying room class updation with 'No Rate Change' checked for reservation".toUpperCase()
						+ " =====");
				testSteps.add("===== "
						+ "Verifying room class updation with 'No Rate Change' checked for reservations".toUpperCase()
						+ " =====");

				getTestSteps.clear();
				getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 1, 2);
				testSteps.addAll(getTestSteps);

				reservationPage.clickStayInfoEditOptions(driver, testSteps, 2);

				getTestSteps.clear();
				getTestSteps = reservationPage.clickFindRooms(driver);
				testSteps.addAll(getTestSteps);

				String getRoomClass = reservationPage.getRoomClass(driver, roomClassName);
				app_logs.info(getRoomClass);

				reservationPage.select_RoomWhileChnageDates(driver, testSteps, getRoomClass, "Yes", "");

				// add select method to do
				getTestSteps.clear();
				getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.editMRBReservationChangeStayDetails(driver, 2, 3);
				testSteps.addAll(getTestSteps);

				reservationPage.clickStayInfoEditOptions(driver, testSteps, 2);

				getTestSteps.clear();
				getTestSteps = reservationPage.clickFindRooms(driver);
				testSteps.addAll(getTestSteps);

				getRoomClass = reservationPage.getRoomClass(driver, roomClassName);
				app_logs.info(getRoomClass);
				reservationPage.select_RoomWhileChnageDates(driver, testSteps, getRoomClass, "Yes", "");

				// add select method to do
				getTestSteps.clear();
				getTestSteps = reservationPage.clickSaveAfterEditStayInfoInMRB(driver);
				testSteps.addAll(getTestSteps);
				unitAvailable.verifyRoomAvaibiltyForMRB(driver, testSteps, checkInArr, defaultDateFormat, timeZone,
						vrboToken, endPointName, getUnitAvailabilityContentApi, checkOutArr, statusCode200,
						getDaysbetweenCurrentAndCheckInDate,checkInDatesList, checkOutDatesList,
						mapBeforeRes, getChangeInCount,false);

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");

			} catch (Exception e) {
				
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify availability after changing roomclass", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				
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
				getChangeInCount = 0;

			} catch (Exception e) {
				
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify availability after making reservation no show", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify availability after making reservation no show", testName, testDescription,
						testCategory, testSteps);
			}
			try {

				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CANCELLING RESERVATION========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CANCELLING RESERVATION ========");

				unitAvailable.verifyRoomAvaibiltyForMRB(driver, testSteps, checkInArr, defaultDateFormat, timeZone,
						vrboToken, endPointName, getUnitAvailabilityContentApi, checkOutArr, statusCode200,
						getDaysbetweenCurrentAndCheckInDate, checkInDatesList, checkOutDatesList,
						mapBeforeRes, getChangeInCount,false);

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
				getChangeInCount = mrbCheckin.split("\\|").length;
			} catch (Exception e) {
				
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify response after roll back reservation", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify response after roll back reservation", testName, testDescription,
						testCategory, testSteps);
			}

			try {

				app_logs.info("========= VERIFY'" + roomClassName + "' AFTER ROLL BACK BUTTON========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "'AFTER ROLL BACK BUTTON========");

				unitAvailable.verifyRoomAvaibiltyForMRB(driver, testSteps, checkInArr, defaultDateFormat, timeZone,
						vrboToken, endPointName, getUnitAvailabilityContentApi, checkOutArr, statusCode200,
						getDaysbetweenCurrentAndCheckInDate, checkInDatesList, checkOutDatesList,
						mapBeforeRes, getChangeInCount,false);

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
				getChangeInCount = 0;

			} catch (Exception e) {
				
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content post request", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content post request", testName, testDescription,
						testCategory, testSteps);
			}
			try {
				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CHANGING RESERVATION STATUS NOSHOW========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' AFTER CHANGING RESERVATION STATUS NOSHOW ========");

				unitAvailable.verifyRoomAvaibiltyForMRB(driver, testSteps, checkInArr, defaultDateFormat, timeZone,
						vrboToken, endPointName, getUnitAvailabilityContentApi, checkOutArr, statusCode200,
						getDaysbetweenCurrentAndCheckInDate,  checkInDatesList, checkOutDatesList,
						mapBeforeRes, getChangeInCount,false);

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
				getChangeInCount = mrbCheckin.split("\\|").length;
			} catch (Exception e) {
				
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify response after roll back reservation", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify response after roll back reservation", testName, testDescription,
						testCategory, testSteps);
			}
			try {

				app_logs.info("========= VERIFY'" + roomClassName + "' AFTER NOSHOW ROLL BACK BUTTON========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "'AFTER NOSHOW ROLL BACK BUTTON========");

				unitAvailable.verifyRoomAvaibiltyForMRB(driver, testSteps, checkInArr, defaultDateFormat, timeZone,
						vrboToken, endPointName, getUnitAvailabilityContentApi, checkOutArr, statusCode200,
						getDaysbetweenCurrentAndCheckInDate,checkInDatesList, checkOutDatesList,
						mapBeforeRes, getChangeInCount,false);

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
				if (Utility.compareDates(Utility.getCurrentDate(defaultDateFormat, timeZone),
						checkInDate.split(Utility.DELIM)[0], defaultDateFormat) == 0) {
					ArrayList<Integer> availabilityCountAfterResList = new ArrayList<>();
					testSteps.add(
							"===== " + "Making CheckIn and verify availability in rategrid and endpoint".toUpperCase()
									+ " =====");
					app_logs.info(
							"===== " + "Making CheckIn and verify availability in rategrid and endpoint".toUpperCase()
									+ " =====");

					reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
					testSteps.add("Searched and opened reservation with number : " + reservationNumber);
					app_logs.info("Searched and opened reservation with number : " + reservationNumber);
					Wait.wait5Second();
						testSteps.add("<b>==========Start Check In==========</b>");
						reservationPage.clickCheckInAllButton(driver, testSteps);
						reservationPage.generatGuestReportToggle(driver, testSteps, config.getProperty("flagOff"));
						reservationPage.checkInProcess(driver, testSteps);
						testSteps.add("<b>==========Start Verifying Check Out All Button ==========</b>");
						Wait.wait5Second();
						reservationPage.verifyCheckOutAllButton(driver, testSteps);
						testSteps.add("<b>==========Start Verifying IN-HOUSE Status==========</b>");
						reservationPage.verifyReservationStatusStatus(driver, testSteps, config.getProperty("inHouse"));
						getChangeInCount = mrbCheckin.split("\\|").length;
						app_logs.info(
								"Verified availability count changed for specific daterange available in response after early checkOut mrb reservation");
						testSteps.add(
								"Verified availability count changed for specific daterange available in response after early checkOut mrb reservation");

						try {
							app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '"
									+ roomClassName + "' AFTER CHANGING RESERVATION CHECKINN========");
							testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '"
									+ roomClassName + "'  AFTER CHANGING RESERVATION CHECKINN ========");

							unitAvailable.verifyRoomAvaibiltyForMRB(driver, testSteps, checkInArr, defaultDateFormat,
									timeZone, vrboToken, endPointName, getUnitAvailabilityContentApi, checkOutArr,
									statusCode200, getDaysbetweenCurrentAndCheckInDate,
									checkInDatesList, checkOutDatesList, mapBeforeRes, getChangeInCount, false);

						} catch (Exception e) {
							Utility.catchException(driver, e, testDescription, testCategory,
									"Failed to verify available rooms in rate grid", testName, testDescription,
									testCategory, testSteps);

						} catch (Error e) {
							Utility.catchError(driver, e, testDescription, testCategory,
									"Failed to verify available rooms in rate grid", testName, testDescription,
									testCategory, testSteps);

						}
						
						try {
							testSteps.add("Check Out reservation");
							app_logs.info("Check Out  reservation");
							reservationPage.clickCheckOutButton(driver, testSteps);
							reservationPage.generatGuestReportToggle(driver, testSteps, "No");
							reservationPage.proceedToCheckOutPayment(driver, testSteps);

							try {
								getTestSteps.clear();
								getTestSteps = homePage.clickPayButton(driver);
								testSteps.addAll(getTestSteps);

							} catch (Exception e) {
								app_logs.info("");
							}
							homePage.clickCloseCheckoutSuccessfullPopup(driver);
							String loading = "(//div[@class='ir-loader-in'])";
							Wait.explicit_wait_absenceofelement(loading, driver);
							reservationPage.closeReservationTab(driver);
							testSteps.add("Closed opened reservation");
							app_logs.info("Closed opened reservation");
							getChangeInCount =0;
						} catch (Exception e) {
							
							Utility.catchException(driver, e, testDescription, testCategory,
									"Failed to checkout", testName,
									testDescription, testCategory, testSteps);
						} catch (Error e) {
							
							Utility.catchError(driver, e, testDescription, testCategory,
									"Failed to checkout", testName,
									testDescription, testCategory, testSteps);
						}
						
						try {
							app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '"
									+ roomClassName + "' AFTER Checkout========");
							testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '"
									+ roomClassName + "' AFTER Checkout ========");

							unitAvailable.verifyRoomAvaibiltyForMRB(driver, testSteps, checkInArr, defaultDateFormat,
									timeZone, vrboToken, endPointName, getUnitAvailabilityContentApi, checkOutArr,
									statusCode200, getDaysbetweenCurrentAndCheckInDate,
									checkInDatesList, checkOutDatesList, mapBeforeRes, getChangeInCount,true);

						} catch (Exception e) {
							Utility.catchException(driver, e, testDescription, testCategory,
									"Failed to verify available rooms in rate grid", testName, testDescription,
									testCategory, testSteps);

						} catch (Error e) {
							Utility.catchError(driver, e, testDescription, testCategory,
									"Failed to verify available rooms in rate grid", testName, testDescription,
									testCategory, testSteps);

						}

				}
			} catch (Exception e) {
				
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content after early checkout", testName, testDescription,
						testCategory, testSteps);
			} catch (Error e) {
				
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content after early checkout", testName, testDescription,
						testCategory, testSteps);
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
		return Utility.getData("VerifyUnitAvailabilityApiForMRB", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}
}
