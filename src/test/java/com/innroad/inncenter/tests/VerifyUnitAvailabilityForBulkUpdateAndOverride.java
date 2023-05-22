package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
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
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationHomePage;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
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

public class VerifyUnitAvailabilityForBulkUpdateAndOverride extends TestCore {
	
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> getDates = null;
	public static String testName = "";
	public static String testDescription = "";
	public static String testCategory = "";

	private WebDriver driver = null;
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
	ReservationHomePage homePage = new ReservationHomePage();
	Rate rate = new Rate();
	VrboObjects vrboObject = new VrboObjects();
	UnitAvailabilityContent unitAvailable = new UnitAvailabilityContent();
	String endPointName = "unitAvailabilityContent";
	String getUnitAvailabilityContentApi = null;
	Response response = null;

	String advertiserId = null, currency = null;
	String propertyId = null;
	String roomClassId = null;
	String ratePlanId = null;
	String randomString = Utility.GenerateRandomNumber();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, otaexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Vrbo")
	public void verifyUnitAvailabilityForBulkUpdateAndOverride(String caseType, String roomClassName,
			String ratePlanName, String checkInDate, String checkOutDate) throws Exception {
		testName = "VerifyUnitAvailabilityForBulkUpdateAndOverride-" + caseType;
		Utility.DELIM=",";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String defaultDateFormat = "MM/dd/yyyy";
		String dateFormatForRateGrid = "MMM/d/yyyy";
		String calendarTodayDate = "";
		String salutation = "Mr.";

		String firstName = "VerifyAvailability" + randomString;
		String lastName = "Res" + randomString;
		String paymentType = "MC";
		String cardNumber = "5454545454545454";
		String nameOnCard = firstName + "";
		String cardExpDate = "12/23";
		String referral = "Other";
		String isAssign = "";

		String timeZone = "";
		String roomNumber = "";
		String reservationNumber = "";
		String maxAdult = "";
		String maxPerson = "";
		String roomClassAbbreviation = "";
		System.out.println(vrboToken);
		String vrboSource = "Vrbo";
		String option = "Availability";
		String isOccupancy = "no";
		String occupancyType = "greater";
		String occupancyValue = "8";
		String action = "Blackout";
		String isMon = "yes";
		String isTue = "yes";
		String isWed = "yes";
		String isThu = "yes";
		String isFri = "yes";
		String isSat = "yes";
		String isSun = "yes";
		HashMap<String, String> preAvailibilityStatus = new HashMap<String, String>();
		HashMap<String, String> postAvailabilityStatus = new HashMap<String, String>();
		HashMap<String, String> AvailabilityChange = new HashMap<String, String>();

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
			  app_logs.info("===== Getting Currency=====");
			  admin.clickOnClient(driver,property); testSteps.add("Open Property : " + property);
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

		if (checkInDate.isEmpty() || checkInDate.equalsIgnoreCase("") || checkOutDate.isEmpty()
				|| checkOutDate.equalsIgnoreCase("")) {
			checkInDate = Utility.getCurrentDate(defaultDateFormat, timeZone);
			checkOutDate = Utility.getNextDate(1, defaultDateFormat);
			app_logs.info("checkInDate : " + checkInDate);
			app_logs.info("checkOutDate : " + checkOutDate);
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
		String startDate = ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "dd/MM/yyyy");
		String endDate = ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "dd/MM/yyyy");
		int getDaysbetweenCurrentAndCheckInDate = Utility
				.getNumberofDays(Utility.getCurrentDate(defaultDateFormat, timeZone), checkInDate, defaultDateFormat);
		int getDaysbetweenDate = Utility.getNumberofDays(checkInDate, checkOutDate, defaultDateFormat);
		getDates = Utility.returnDatesInBetweenDateRange(checkInDate, checkOutDate, defaultDateFormat);
		app_logs.info("getDates : " + getDates);
		

		if (caseType.equalsIgnoreCase("VerifyAvailabilityWithAndWithoutBlackOut")) {
			ArrayList<Integer> availabilityCountBeforeChangingStatusList = new ArrayList<>();
			ArrayList<String> isDayAvailableBeforeChangingStatusList = new ArrayList<>();
			ArrayList<String> isDayAvailableAfterChangingStatusList = new ArrayList<>();
			ArrayList<Integer> availabilityCountAfterChangingStatusList = new ArrayList<>();

			try {
				testSteps.add(
						"<b>" + "===== Verify availability after black out a date from bulk update =====".toUpperCase()
								+ "</b>");
				app_logs.info("===== Verify availability after black out a date from bulk update =====".toUpperCase());

				vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps);

				app_logs.info("===== Getting EndPoint Response =====".toUpperCase());
				testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

				response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
				app_logs.info("response  : " + response);
				String resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability");
				app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability : "
						+ resp);
				String getCount = "";
				getCount = resp.substring(getDaysbetweenCurrentAndCheckInDate);
				getCount = resp.substring(0, getDaysbetweenDate);

				for (char c : getCount.toCharArray()) {
					isDayAvailableBeforeChangingStatusList.add(String.valueOf(c));
				}

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
				availabilityCountBeforeChangingStatusList.clear();
				if (getDaysbetweenDate > 0) {
					for (int j = 0; j < getDaysbetweenDate; j++) {
						availabilityCountBeforeChangingStatusList.add(getAvailabilityCount.get(j));
					}
				}

				app_logs.info("availabilityCountBeforeRes : " + availabilityCountBeforeChangingStatusList);
				testSteps.add("availabilityCountBeforeRes : " + availabilityCountBeforeChangingStatusList);

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to get availability from response", testName, testDescription, testCategory, testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory, "Failed to get availability from response",
						testName, testDescription, testCategory, testSteps);
			}

			try {
				testSteps.add("==========Navigate to Rates Grid=========");
				app_logs.info("==========Navigate to Rates Grid==========");

				navigation.Inventory(driver);
				testSteps.add("Navigate Setup");
				navigation.Rates_Grid(driver);
				ratesGrid.searchRatePlan(driver, ratePlanName);
				testSteps.add("Navigate Rates Grid");
				app_logs.info("Navigate Rates Grid");
				ratesGrid.clickOnAvailabilityTab(driver);

				testSteps.add("==========Before Bulk Update=========");
				preAvailibilityStatus = ratesGrid.getAvailableBlockoutOfRoomClasses(driver, testSteps, checkInDate,
						checkOutDate, roomClassName, vrboSource);
				app_logs.info("=========================Before Bulk Update=======================================");
				app_logs.info("##################################################################################");
				app_logs.info(preAvailibilityStatus);
				app_logs.info("##################################################################################");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("===============Bulk Update=========");
				ratesGrid.clickOnBulkUpdate(driver);
				ratesGrid.selectBulkUpdateOption(driver, "Availability");
				AvailabilityChange = ratesGrid.bulkUpdateAvailabilityChange(driver, testSteps, startDate, endDate,
						roomClassName, isOccupancy, occupancyType, occupancyValue, vrboSource, action, isMon, isTue,
						isWed, isThu, isFri, isSat, isSun);
				app_logs.info("===============================Bulk Update========================================");
				app_logs.info("##################################################################################");
				app_logs.info(AvailabilityChange);
				app_logs.info("##################################################################################");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to black out through bulk update", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to black out through bulk update", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("==========After Bulk Update=========");
				postAvailabilityStatus = ratesGrid.getAvailableBlockoutOfRoomClasses(driver, testSteps, startDate,
						endDate, roomClassName, vrboSource);
				app_logs.info("==============================After Bulk Update===================================");
				app_logs.info("##################################################################################");
				app_logs.info(postAvailabilityStatus);
				app_logs.info("##################################################################################");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("=====================Validation check=======================");
				app_logs.info("=====================Validation check=======================");
				ArrayList<String> checkedDays = new ArrayList<>();
				checkedDays = ratesGrid.getCheckedDays(driver, isMon, isTue, isWed, isThu, isFri, isSat, isSun);
				ArrayList<String> filteredDays = new ArrayList<>();
				Set<String> keys = preAvailibilityStatus.keySet();
				for (String key : keys) {
					System.out.println(key);
					System.out.println(checkedDays);
					for (int j = 0; j <= checkedDays.size() - 1; j++) {
						if (key.contains(checkedDays.get(j))) {
							filteredDays.add(key);

							app_logs.info("Filtered days are " + filteredDays);

						}

					}

				}
				testSteps.add("Filtered days are " + filteredDays);
				app_logs.info("Filtered days are " + filteredDays);

				for (int k = 0; k <= filteredDays.size() - 1; k++) {
					if (preAvailibilityStatus.get(filteredDays.get(k)).equalsIgnoreCase("available")) {
						try {
							System.out.println(preAvailibilityStatus.get(filteredDays.get(k)));
							Assert.assertEquals(postAvailabilityStatus.get(filteredDays.get(k)), "blocked",
									"Failed messaage");
							app_logs.info(filteredDays.get(k) + " is changed from available to blocked");
							testSteps.add(filteredDays.get(k) + " is changed from available to blocked");
						} catch (AssertionError e) {
							app_logs.info(filteredDays.get(k) + " is available previously also");
							testSteps.add(filteredDays.get(k) + " is available previously also");
						}

					} else

					{
						System.out.println(preAvailibilityStatus.get(filteredDays.get(k)));
						try {
							Assert.assertEquals(postAvailabilityStatus.get(filteredDays.get(k)), "available",
									"Failed messaage");
							app_logs.info(filteredDays.get(k) + " is changed from blocked to available");
							testSteps.add(filteredDays.get(k) + " is changed from blocked to available");
						} catch (AssertionError e) {
							app_logs.info(filteredDays.get(k) + " is blocked previously also");
							testSteps.add(filteredDays.get(k) + " is blocked previously also");
						}

					}
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add(
						"<b>" + "===== Verify availability after changing available to blackout in end point ===== "
								.toUpperCase() + "</b>");
				app_logs.info("===== Verify availability after changing available to blackout in end point ===== "
						.toUpperCase());

				vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps);

				app_logs.info("===== Getting EndPoint Response =====".toUpperCase());
				testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

				response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
				app_logs.info("response  : " + response);

				String resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability");
				app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability : "
						+ resp);
				String getCount = "";
				getCount = resp.substring(getDaysbetweenCurrentAndCheckInDate);
				getCount = resp.substring(0, getDaysbetweenDate);

				for (char c : getCount.toCharArray()) {
					isDayAvailableAfterChangingStatusList.add(String.valueOf(c));
				}

				app_logs.info(
						"===== Verify day is blackout in response after making room blackout =====".toUpperCase());
				testSteps.add("<b>"
						+ " ===== Verify day is blackout in response after making room blackout ===== ".toUpperCase()
						+ "</b>");

				app_logs.info("Expeted Day availability : " + "N");
				testSteps.add("Expeted Day availability : " + "N");

				for (int i = 0; i < isDayAvailableAfterChangingStatusList.size(); i++) {

					app_logs.info("Found : " + isDayAvailableAfterChangingStatusList.get(i));
					testSteps.add("Found : " + isDayAvailableAfterChangingStatusList.get(i));
					assertEquals(isDayAvailableAfterChangingStatusList.get(i), "N",
							"Failed : day availability in response didn't match");
				}

				app_logs.info("Verified day is blackout in response after making room blackout");
				testSteps.add("Verified day is blackout in response after making room blackout");

				resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
				app_logs.info(
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
								+ resp);

				app_logs.info("===== VERIFY availability count is zero in response after making day blackout ====="
						.toUpperCase());
				testSteps.add(
						"<b>" + "===== VERIFY availability count is zero in response after making day blackout ===== "
								.toUpperCase() + "</b>");
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
				availabilityCountAfterChangingStatusList.clear();
				if (getDaysbetweenDate > 0) {
					for (int j = 0; j < getDaysbetweenDate; j++) {
						availabilityCountAfterChangingStatusList.add(getAvailabilityCount.get(j));
					}
				}

				for (int i = 0; i < availabilityCountAfterChangingStatusList.size(); i++) {
					app_logs.info(
							"Previous Room availability count : " + availabilityCountBeforeChangingStatusList.get(i));
					testSteps.add(
							"Previous Room availability count : " + availabilityCountBeforeChangingStatusList.get(i));
					int countAfterChangingStatus = availabilityCountAfterChangingStatusList.get(i);
					app_logs.info("Current Room availbility count : " + countAfterChangingStatus);
					testSteps.add("Current Room availbility count : " + countAfterChangingStatus);
					assertEquals(countAfterChangingStatus, 0,
							"Failed : Availability count in response didn't decrease");
				}

				app_logs.info("Verified availability count is zero in response after making day blackout");
				testSteps.add("Verified availability count is zero in response after making day blackout");

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content response after making dates blackout", testName,
						testDescription, testCategory, testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content response after making dates blackout", testName,
						testDescription, testCategory, testSteps);
			}

			action = "Available";
			preAvailibilityStatus.clear();
			AvailabilityChange.clear();
			postAvailabilityStatus.clear();

			try {
				testSteps.add(
						"<b>" + "===== Verify availability after changing blackout to avilable ===== ".toUpperCase()
								+ "</b>");
				app_logs.info("===== Verify availability after changing blackout to avilable ===== ".toUpperCase());

				navigation.InventoryV2(driver);
				testSteps.add("Navigate Setup");
				navigation.Rates_Grid(driver);
				ratesGrid.searchRatePlan(driver, ratePlanName);
				testSteps.add("Navigate Rates Grid");
				app_logs.info("Navigate Rates Grid");
				ratesGrid.clickOnAvailabilityTab(driver);

				testSteps.add("==========Before Bulk Update=========");
				preAvailibilityStatus = ratesGrid.getAvailableBlockoutOfRoomClasses(driver, testSteps, checkInDate,
						checkOutDate, roomClassName, vrboSource);
				app_logs.info("=========================Before Bulk Update=======================================");
				app_logs.info("##################################################################################");
				app_logs.info(preAvailibilityStatus);
				app_logs.info("##################################################################################");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid and get pre availability status", testName,
							"Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid and get pre availability status", testName,
							"Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("===============Bulk Update=========");
				ratesGrid.clickOnBulkUpdate(driver);
				ratesGrid.selectBulkUpdateOption(driver, "Availability");
				AvailabilityChange = ratesGrid.bulkUpdateAvailabilityChange(driver, testSteps, startDate, endDate,
						roomClassName, isOccupancy, occupancyType, occupancyValue, vrboSource, action, isMon, isTue,
						isWed, isThu, isFri, isSat, isSun);
				app_logs.info("===============================Bulk Update========================================");
				app_logs.info("##################################################################################");
				app_logs.info(AvailabilityChange);
				app_logs.info("##################################################################################");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to black out through bulk update", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to black out through bulk update", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("==========After Bulk Update=========");
				postAvailabilityStatus = ratesGrid.getAvailableBlockoutOfRoomClasses(driver, testSteps, startDate,
						endDate, roomClassName, vrboSource);
				app_logs.info("==============================After Bulk Update===================================");
				app_logs.info("##################################################################################");
				app_logs.info(postAvailabilityStatus);
				app_logs.info("##################################################################################");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid and get post availability status", testName,
							"Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid and get post availability status", testName,
							"Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("=====================Validation check=======================");
				app_logs.info("=====================Validation check=======================");
				ArrayList<String> checkedDays = new ArrayList<>();
				checkedDays = ratesGrid.getCheckedDays(driver, isMon, isTue, isWed, isThu, isFri, isSat, isSun);
				ArrayList<String> filteredDays = new ArrayList<>();
				Set<String> keys = preAvailibilityStatus.keySet();
				for (String key : keys) {
					System.out.println(key);
					System.out.println(checkedDays);
					for (int j = 0; j <= checkedDays.size() - 1; j++) {
						if (key.contains(checkedDays.get(j))) {
							filteredDays.add(key);
							app_logs.info("Filtered days are " + filteredDays);

						}

					}

				}
				testSteps.add("Filtered days are " + filteredDays);
				app_logs.info("Filtered days are " + filteredDays);

				for (int k = 0; k <= filteredDays.size() - 1; k++) {
					System.out.println(preAvailibilityStatus.get(filteredDays.get(k)));
					if (preAvailibilityStatus.get(filteredDays.get(k)).equalsIgnoreCase("available")) {
						try {
							Assert.assertEquals(postAvailabilityStatus.get(filteredDays.get(k)), "blocked",
									"Failed messaage");
							app_logs.info(filteredDays.get(k) + " is changed from available to blocked");
							testSteps.add(filteredDays.get(k) + " is changed from available to blocked");
						} catch (AssertionError e) {
							app_logs.info(filteredDays.get(k) + " is available previously also");
							testSteps.add(filteredDays.get(k) + " is available previously also");
						}

					} else

					{
						System.out.println(preAvailibilityStatus.get(filteredDays.get(k)));
						try {
							Assert.assertEquals(postAvailabilityStatus.get(filteredDays.get(k)), "available",
									"Failed messaage");
							app_logs.info(filteredDays.get(k) + " is changed from blocked to available");
							testSteps.add(filteredDays.get(k) + " is changed from blocked to available");
						} catch (AssertionError e) {
							app_logs.info(filteredDays.get(k) + " is blocked previously also");
							testSteps.add(filteredDays.get(k) + " is blocked previously also");
						}

					}
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify availability status", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify availability status", testName, "Navigation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			isDayAvailableAfterChangingStatusList.clear();
			availabilityCountAfterChangingStatusList.clear();
			try {

				testSteps
						.add("<b>" + "===== Verify availability in response after changing blackout to available ===== "
								.toUpperCase() + "</b>");
				app_logs.info("===== Verify availability in response after changing blackout to available ===== "
						.toUpperCase());
				vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps);

				app_logs.info("===== Getting EndPoint Response =====".toUpperCase());
				testSteps.add("<b> ===== Getting EndPoint Response ===== </b>".toUpperCase());

				response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);
				app_logs.info("response  : " + response);
				String resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability");
				app_logs.info("unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availability : "
						+ resp);
				String getCount = "";
				getCount = resp.substring(getDaysbetweenCurrentAndCheckInDate);
				getCount = resp.substring(0, getDaysbetweenDate);

				for (char c : getCount.toCharArray()) {
					isDayAvailableAfterChangingStatusList.add(String.valueOf(c));
				}

				app_logs.info(
						"===== Verify day is available in response after making date available =====".toUpperCase());
				testSteps.add("<b>"
						+ " ===== Verify day is available in response after making date available ===== ".toUpperCase()
						+ "</b>");

				app_logs.info("Expeted Day availability : " + "Y");
				testSteps.add("Expeted Day availability : " + "Y");

				for (int i = 0; i < isDayAvailableAfterChangingStatusList.size(); i++) {

					app_logs.info("Found : " + isDayAvailableAfterChangingStatusList.get(i));
					testSteps.add("Found : " + isDayAvailableAfterChangingStatusList.get(i));
					assertEquals(isDayAvailableAfterChangingStatusList.get(i), "Y",
							"Failed : day availability in response didn't match");
				}

				app_logs.info("Verified day is available in response after making date available");
				testSteps.add("Verified day is available in response after making date available");

				resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
				app_logs.info(
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
								+ resp);

				app_logs.info("===== Veirfy availability count is updated in response after making day available ====="
						.toUpperCase());
				testSteps.add("<b>"
						+ "===== Veirfy availability count is updated in response after making day available ===== "
								.toUpperCase()
						+ "</b>");
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
				availabilityCountAfterChangingStatusList.clear();
				if (getDaysbetweenDate > 0) {
					for (int j = 0; j < getDaysbetweenDate; j++) {
						availabilityCountAfterChangingStatusList.add(getAvailabilityCount.get(j));
					}
				}

				for (int i = 0; i < availabilityCountAfterChangingStatusList.size(); i++) {
					int beforeCountRes = availabilityCountBeforeChangingStatusList.get(i);
					app_logs.info("Previous Room availability count : " + beforeCountRes);
					testSteps.add("Previous Room availability count : " + beforeCountRes);
					int countAfterChangingStatus = availabilityCountAfterChangingStatusList.get(i);
					app_logs.info("Current Room availbility count : " + countAfterChangingStatus);
					testSteps.add("Current Room availbility count : " + countAfterChangingStatus);
					assertEquals(countAfterChangingStatus, beforeCountRes,
							"Failed : Availability count in response didn't decrease");
				}

				app_logs.info("Verified availability count is updated in response after making day available");
				testSteps.add("Verified availability count is updated in response after making day available");
			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content response after making date availble from bulk update",
						testName, testDescription, testCategory, testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify unit availability content response after making date availble from bulk update",
						testName, testDescription, testCategory, testSteps);
			}

		}

		if (caseType.equalsIgnoreCase("VerifyBulkUpdateMinStayRule")
				|| caseType.equalsIgnoreCase("VerifyBulkUpdateNoCheckInRule")
				|| caseType.equalsIgnoreCase("VerifyBulkUpdateNoCheckOutRule")
				|| caseType.equalsIgnoreCase("VerifyBulkUpdateNoCheckInCheckOutRule")

				|| caseType.equalsIgnoreCase("VerifyMinStayRuleByOverride")
				|| caseType.equalsIgnoreCase("VerifyNoCheckInRuleByOverride")
				|| caseType.equalsIgnoreCase("VerifyNoCheckOutRuleByOverride")
				|| caseType.equalsIgnoreCase("VerifyNoCheckInNoCheckOutRuleByOverride")

				|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveMinStayRule")
				|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveNoCheckInRule")
				|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveNoCheckOutRule")
				|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveNoCheckInCheckOutRule")

				|| caseType.equalsIgnoreCase("VerifyRemoveMinStayRuleByOverride")
				|| caseType.equalsIgnoreCase("VerifyRemoveNoCheckInRuleByOverride")
				|| caseType.equalsIgnoreCase("VerifyRemoveNoCheckOutRuleByOverride")
				|| caseType.equalsIgnoreCase("VerifyRemoveNoCheckInNoCheckOutRuleByOverride")) {

			startDate = ESTTimeZone.reformatDate(checkInDate, defaultDateFormat, "MM/dd/yyyy");
			endDate = ESTTimeZone.reformatDate(checkOutDate, defaultDateFormat, "MM/dd/yyyy");
			String rulesToUpdate = "";
			String minStayRuleValue = "3";
			String overriedType = "";

			if (caseType.contains("Override") || caseType.contains("override")) {
				overriedType = "override";
			} else if (caseType.contains("Bulk") || caseType.contains("bulk")) {
				overriedType = "bulkUpdate";
			}

			if (caseType.equalsIgnoreCase("VerifyBulkUpdateMinStayRule")
					|| caseType.equalsIgnoreCase("VerifyMinStayRuleByOverride")

					|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveMinStayRule")
					|| caseType.equalsIgnoreCase("VerifyRemoveMinStayRuleByOverride")) {
				rulesToUpdate = "Min stay";
			} else if (caseType.equalsIgnoreCase("VerifyBulkUpdateNoCheckInRule")
					|| caseType.equalsIgnoreCase("VerifyNoCheckInRuleByOverride")
					|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveNoCheckInRule")
					|| caseType.equalsIgnoreCase("VerifyRemoveNoCheckInRuleByOverride")) {
				rulesToUpdate = "No Check In";
			} else if (caseType.equalsIgnoreCase("VerifyBulkUpdateNoCheckOutRule")
					|| caseType.equalsIgnoreCase("VerifyNoCheckOutRuleByOverride")
					|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveNoCheckOutRule")
					|| caseType.equalsIgnoreCase("VerifyRemoveNoCheckOutRuleByOverride")) {
				rulesToUpdate = "No Check Out";
			} else if (caseType.equalsIgnoreCase("VerifyBulkUpdateNoCheckInCheckOutRule")
					|| caseType.equalsIgnoreCase("VerifyNoCheckInNoCheckOutRuleByOverride")
					|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveNoCheckInCheckOutRule")
					|| caseType.equalsIgnoreCase("VerifyRemoveNoCheckInNoCheckOutRuleByOverride")) {
				rulesToUpdate = "No Check In,No Check Out";
			}

			try {

				testSteps.add("<b> ========== APPLYING RULES ========== </b>");
				app_logs.info("========== APPLYING RULES ==========");
				Wait.wait60Second();
				unitAvailable.verifyRulesOverriedThroughBulkUpdate(driver, testSteps, endPointName,
						getUnitAvailabilityContentApi, vrboToken, statusCode200, ratePlanName, roomClassName, startDate,
						endDate, "MM/dd/yyyy", timeZone, rulesToUpdate, minStayRuleValue, vrboSource, option,
						isOccupancy, occupancyType, occupancyValue, action, isMon, isTue, isWed, isThu, isFri, isSat,
						isSun, overriedType, "Yes");

				if (caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveMinStayRule")
						|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveNoCheckInRule")
						|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveNoCheckOutRule")
						|| caseType.equalsIgnoreCase("VerifyBulkUpdateRemoveNoCheckInCheckOutRule")

						|| caseType.equalsIgnoreCase("VerifyRemoveMinStayRuleByOverride")
						|| caseType.equalsIgnoreCase("VerifyRemoveNoCheckInRuleByOverride")
						|| caseType.equalsIgnoreCase("VerifyRemoveNoCheckOutRuleByOverride")
						|| caseType.equalsIgnoreCase("VerifyRemoveNoCheckInNoCheckOutRuleByOverride")) {

					minStayRuleValue = "";
					testSteps.add("<b> ========== REMOVING RULES ========== </b>");
					app_logs.info("========== REMOVING RULES ==========");

					unitAvailable.verifyRulesOverriedThroughBulkUpdate(driver, testSteps, endPointName,
							getUnitAvailabilityContentApi, vrboToken, statusCode200, ratePlanName, roomClassName,
							startDate, endDate, "MM/dd/yyyy", timeZone, rulesToUpdate, minStayRuleValue, vrboSource,
							option, isOccupancy, occupancyType, occupancyValue, action, isMon, isTue, isWed, isThu,
							isFri, isSat, isSun, overriedType, "No");

				}

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify min stay override rule from bulk update in end point",
							testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify min stay override rule from bulk update in end point",
							testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		if (caseType.equalsIgnoreCase("VerifyInActiveRoomClassAndRatePlan")) {

			try {
				unitAvailable.verifyInactiveRoomClassAndRatePlan(driver, endPointName, getUnitAvailabilityContentApi,
						vrboToken, statusCode200, testSteps, roomClassName, ratePlanName);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify inactive room class and rate plan", testName, "RatesV2",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify inactive room class and rate plan", testName, "RatesV2",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (caseType.equalsIgnoreCase("VerifyOutOfOrderRoom")) {

			try {

				String outOforderSubject = "Verifying Availability" + randomString;
				unitAvailable.verifyRoomMaintenance(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, outOforderSubject, checkInDate, checkOutDate, defaultDateFormat, timeZone, "",
						roomClassName, "Verify room count in end point", testSteps);
			} catch (Exception e) {
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify room out of order in end point", testName, testDescription, testCategory,
						testSteps);
			} catch (Error e) {
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify room out of order in end point", testName, testDescription, testCategory,
						testSteps);
			}
		}

		if (caseType.equalsIgnoreCase("VerifyHighestAppliedMinStayRule")) {

			try {

				unitAvailable.verifyMinStayRuleRatePlan(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps, ratePlanName, roomClassName, 4, 2, checkInDate, checkOutDate,
						defaultDateFormat, timeZone, true, true, "yes");

				unitAvailable.verifyMinStayRuleRatePlan(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps, ratePlanName, roomClassName, 1, 1, checkInDate, checkOutDate,
						defaultDateFormat, timeZone, false, false, "no");

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify min stay rule value in  end point", testName, testDescription, testCategory,
						testSteps);
			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify min stay rule value in  end point", testName, testDescription, testCategory,
						testSteps);
			}
		}

		if (caseType.equalsIgnoreCase("VerifyVirtualRoomAvailabilityInEndpoint")) {
			ArrayList<Integer> beforeReservedCountList = new ArrayList<>();
			ArrayList<Integer> beforeAvailableRoomsCountList = new ArrayList<>();
			ArrayList<Integer> beforeVirtualClassReserveCountList = new ArrayList<>();
			ArrayList<Integer> beforeVirtualClassAvailableCountList = new ArrayList<>();
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
					calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
					app_logs.info(calendarTodayDate);
					ratesGrid.clickCalendar(driver);

					ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);
					ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone,
							getTestSteps);

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
				app_logs.info("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF VIRTUAL ROOM CLASS '" + roomClassName
						+ "' BEFORE CREATING RESERVATION ========");
				testSteps.add("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF VIRTUAL ROOM CLASS '" + roomClassName
						+ "' BEFORE CREATING RESERVATION ========");
				for (int i = 0; i < getDates.size() - 1; i++) {
					String getDate = getDates.get(i);
					calendarTodayDate = ESTTimeZone.reformatDate(getDate, defaultDateFormat, dateFormatForRateGrid);
					app_logs.info(calendarTodayDate);
					ratesGrid.clickCalendar(driver);

					ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormatForRateGrid, getTestSteps);

					ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormatForRateGrid, timeZone,
							getTestSteps);

					int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormatForRateGrid);

					ratesGrid.expandRoomClass(driver, roomClassName, getTestSteps);
					beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
					app_logs.info("Before Reserved Count : " + beforeReservedCount);
					beforeVirtualClassReserveCountList.add(Integer.parseInt(beforeReservedCount));
					beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
					app_logs.info("Before Available Rooms : " + beforeAvailableRooms);
					beforeVirtualClassAvailableCountList.add(Integer.parseInt(beforeAvailableRooms));

					ratesGrid.reduceRoomClass(driver, roomClassName, getTestSteps);

				}
				testSteps.add("Before Reserved Count : " + beforeVirtualClassReserveCountList);
				testSteps.add("Before Available Rooms : " + beforeVirtualClassAvailableCountList);

				navigation.reservationBackward3(driver);
				testSteps.add("Navigate to Reservations");
				app_logs.info("Navigate to Reservations");

			} catch (Exception e) {
				e.printStackTrace();
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to get reserved and available room count of virtual roomclass", testName,
						testDescription, testCategory, testSteps);

			} catch (Error e) {
				e.printStackTrace();
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to get reserved and available room count of virtual roomclass", testName,
						testDescription, testCategory, testSteps);

			}

			ArrayList<Integer> availabilityCountBeforeResList = new ArrayList<>();
			ArrayList<Integer> availabilityCountForVirtualRoomClassBeforeResList = new ArrayList<>();
			String getUnitAvailabilityContentApiForVirtualRoomClass = VrboEndPoints
					.getUnitAvailabilityContentApi(propertyId, roomClassId, ratePlanId);
			app_logs.info("getUnitAvailabilityContentApiForVirtualRoomClass Url : "
					+ getUnitAvailabilityContentApiForVirtualRoomClass);

			try {
				app_logs.info("===== Getting EndPoint Response for room class '" + roomClassName
						+ "' from which virtual class constitue =====".toUpperCase());
				testSteps.add("<b> ===== Getting EndPoint Response for room class '" + roomClassName
						+ "' from which virtual class constitue ===== </b>".toUpperCase());

				vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps);
				response = vrboObject.getApiResponse(getUnitAvailabilityContentApi, vrboToken);

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
				if (getDaysbetweenDate > 0) {
					for (int j = 0; j < getDaysbetweenDate; j++) {
						availabilityCountBeforeResList.add(getAvailabilityCount.get(j));
					}
				}
				app_logs.info("availabilityCountBeforeRes : " + availabilityCountBeforeResList);
				testSteps.add("availabilityCountBeforeRes : " + availabilityCountBeforeResList);

				app_logs.info("===== Getting EndPoint Response for virtual room class '" + roomClassName
						+ "' =====".toUpperCase());
				testSteps.add("<b> ===== Getting EndPoint Response for virtual room class '" + roomClassName
						+ "' ===== </b>".toUpperCase());

				vrboObject.verifyVrboApiStatusCode(endPointName, getUnitAvailabilityContentApiForVirtualRoomClass,
						vrboToken, statusCode200, testSteps);
				response = vrboObject.getApiResponse(getUnitAvailabilityContentApiForVirtualRoomClass, vrboToken);

				resp = vrboObject.getAttributesValue(response,
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount");
				app_logs.info(
						"unitAvailabilityContent.unitAvailability.unitAvailabilityConfiguration.availableUnitCount : "
								+ resp);
				getAvailabilityCount.clear();
				mrbArr = resp.split(",");
				app_logs.info("getAvailabilityCount : " + mrbArr.length);
				for (int i = 0; i < mrbArr.length; i++) {
					if (i >= getDaysbetweenCurrentAndCheckInDate) {
						getAvailabilityCount.add(Integer.parseInt(mrbArr[i]));
					}
				}

				app_logs.info("getAvailabilityCount : " + getAvailabilityCount);
				if (getDaysbetweenDate > 0) {
					for (int j = 0; j < getDaysbetweenDate; j++) {
						availabilityCountForVirtualRoomClassBeforeResList.add(getAvailabilityCount.get(j));
					}
				}
				app_logs.info("availabilityCountBeforeRes : " + availabilityCountForVirtualRoomClassBeforeResList);
				testSteps.add("availabilityCountBeforeRes : " + availabilityCountForVirtualRoomClassBeforeResList);

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
				testSteps.add("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");
				app_logs.info("<b> ===== CREATING 'SINGLE' RESERVATION ===== </b>");

				reservationPage.click_NewReservation(driver, testSteps);

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
				getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, firstName, lastName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
				testSteps.addAll(getTestSteps);

				reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, cardNumber, nameOnCard,
						cardExpDate);

				reservationPage.SelectReferral(driver, referral);

				reservationPage.clickBookNow(driver, testSteps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				app_logs.info("reservationNumber : " + reservationNumber);
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
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "CreateReservation", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			int getChangeInCount = 1;
			// verify
			try {

				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF VIRTUAL ROOM CLASS '"
						+ roomClassName + "' AFTER CREATING RESERVATION========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF VIRTUAL ROOM CLASS '"
						+ roomClassName + "' AFTER CREATING RESERVATION ========");

				unitAvailable.verifyRoomAvailability(driver, endPointName,
						getUnitAvailabilityContentApiForVirtualRoomClass, vrboToken, statusCode200, testSteps, getDates,
						defaultDateFormat, dateFormatForRateGrid, timeZone, roomClassName, ratePlanName,
						beforeVirtualClassReserveCountList, beforeVirtualClassAvailableCountList,
						availabilityCountForVirtualRoomClassBeforeResList, getDaysbetweenCurrentAndCheckInDate,
						getDaysbetweenDate, getChangeInCount);

				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE AFTER CREATING RESERVATION========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
						+ "' FROM WHICH VIRTUAL CLASS CONSTITUTE AFTER CREATING RESERVATION ========");

				unitAvailable.verifyRoomAvailability(driver, endPointName, getUnitAvailabilityContentApi, vrboToken,
						statusCode200, testSteps, getDates, defaultDateFormat, dateFormatForRateGrid, timeZone,
						roomClassName, ratePlanName, beforeReservedCountList, beforeAvailableRoomsCountList,
						availabilityCountBeforeResList, getDaysbetweenCurrentAndCheckInDate, getDaysbetweenDate,
						getChangeInCount);

			} catch (Exception e) {
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid and end point", testName, testDescription,
						testCategory, testSteps);

			} catch (Error e) {
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid and end point", testName, testDescription,
						testCategory, testSteps);

			}
			getChangeInCount = 0;
			try {
				testSteps.add("===== "
						+ "Changing reservation room to unassigned and verify availability in rategrid and endpoint for virtual roomclass"
								.toUpperCase()
						+ " =====");
				app_logs.info("===== "
						+ "Changing reservation room to unassigned and verify availability in rategrid and endpoint for virtual roomclass"
								.toUpperCase()
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
				reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, "No", "");

				testSteps.addAll(reservationPage.clickSaveAfterEditStayInfo(driver));

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");

				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF VIRTUAL ROOM CLASS '"
						+ roomClassName + "' AFTER CANCELLING RESERVATION========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF VIRTUAL ROOM CLASS '"
						+ roomClassName + "' AFTER CANCELLING RESERVATION ========");

				unitAvailable.verifyRoomAvailability(driver, endPointName,
						getUnitAvailabilityContentApiForVirtualRoomClass, vrboToken, statusCode200, testSteps, getDates,
						defaultDateFormat, dateFormatForRateGrid, timeZone, roomClassName, ratePlanName,
						beforeVirtualClassReserveCountList, beforeVirtualClassAvailableCountList,
						availabilityCountForVirtualRoomClassBeforeResList, getDaysbetweenCurrentAndCheckInDate,
						getDaysbetweenDate, getChangeInCount);

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

				reservationPage.select_RoomWhileChnageDates(driver, testSteps, roomClassName, "Yes", "");

				testSteps.addAll(reservationPage.clickSaveAfterEditStayInfo(driver));

				reservationPage.closeReservationTab(driver);
				testSteps.add("Closed opened reservation");
				app_logs.info("Closed opened reservation");

			} catch (Exception e) {
				Utility.catchException(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid and end point", testName, testDescription,
						testCategory, testSteps);

			} catch (Error e) {
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid and end point", testName, testDescription,
						testCategory, testSteps);

			}

			try {

				testSteps.add("===== "
						+ "Changing reservation status to cancel and verify availability in rategrid and endpoint for virtual roomclass"
								.toUpperCase()
						+ " =====");
				app_logs.info("===== "
						+ "Changing reservation status to cancel and verify availability in rategrid and endpoint for virtual roomclass"
								.toUpperCase()
						+ " =====");

				reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
				testSteps.add("Searched and opened reservation with number : " + reservationNumber);
				app_logs.info("Searched and opened reservation with number : " + reservationNumber);

				reservationPage.change_ReservationStatus(driver, testSteps, "Cancel");

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
				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF VIRTUAL ROOM CLASS '"
						+ roomClassName + "' AFTER CANCELLING RESERVATION========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF VIRTUAL ROOM CLASS '"
						+ roomClassName + "' AFTER CANCELLING RESERVATION ========");

				unitAvailable.verifyRoomAvailability(driver, endPointName,
						getUnitAvailabilityContentApiForVirtualRoomClass, vrboToken, statusCode200, testSteps, getDates,
						defaultDateFormat, dateFormatForRateGrid, timeZone, roomClassName, ratePlanName,
						beforeVirtualClassReserveCountList, beforeVirtualClassAvailableCountList,
						availabilityCountForVirtualRoomClassBeforeResList, getDaysbetweenCurrentAndCheckInDate,
						getDaysbetweenDate, getChangeInCount);

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
						"Failed to verify available rooms in rate grid and endpoint after cancelling reservation",
						testName, testDescription, testCategory, testSteps);

			} catch (Error e) {
				Utility.catchError(driver, e, testDescription, testCategory,
						"Failed to verify available rooms in rate grid and endpoint after cancelling reservation",
						testName, testDescription, testCategory, testSteps);

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
		return Utility.getData("AvailabilityApiForBulkUpdate", otaexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}
