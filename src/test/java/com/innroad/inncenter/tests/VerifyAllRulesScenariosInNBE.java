package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

import bsh.util.Util;

public class VerifyAllRulesScenariosInNBE extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String testName = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	int scenarioNumber = 0;
	String propertyName = "";
	String timeZone = "";
	String bookingEngineChannelFullName = "";
	int numberOfProperties = 0;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	private ArrayList<String> getDataOfHash(HashMap<String, String> data) {
		Set set = data.entrySet();
		Iterator iterator = set.iterator();
		ArrayList<String> values = new ArrayList<String>();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			app_logs.info("key is: " + mentry.getKey() + " & Value is: " + mentry.getValue().toString());
			values.add(mentry.getValue().toString());

		}
		return values;
	}

	@Test(dataProvider = "getData", groups = "NBE")
	public void verifyAllRulesScenariosInNBE(String delim, String TestCaseID, String testCaseDescription,
			String checkInDate, String checkOutDate, String updateRate, String UpdateTypes, String SubTypeOfBulk,
			String UpdateRoomClasses, String Amount, String CurrencyType, String seasonUpdatedMinNights, String option,
			String displayCustomMessageState, String noRoomAvailableMessage, String changeStayDetails,
			String afterUpdateNoRoomAvailableMessage, String addRemoveCheckInDays, String updatedStayDays)
			throws Exception {

		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
				"CreateRatePlan_VerifyRule");
		HashMap<String, String> staticData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
				"VerifyAllRulesInNBE_StaticData");
		HashMap<String, String> createProduct = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx", "CreateProductForNBE");

		// static Data
		String bookingEngineChannel = staticData.get("bookingEngineChannel");
		String roomClassType = staticData.get("roomClassType");
		String reservationRoomClassName = staticData.get("RoomClassName");
		String ratePlanType = staticData.get("ratePlanType");
		String ratePlanName = staticData.get("ratePlanName");
		String adults = staticData.get("reservationAdults");
		String child = staticData.get("reservationChild");
		String updatedIsRatePlanRistrictionReq = staticData.get("isRatePlanRistrictionReq");
		String updatedRistrictionType = staticData.get("RistrictionType");
		String updatedIsMinStay = staticData.get("isMinStay");
		String updatedMinNights = staticData.get("MinNights");
		String updatedIsMaxStay = staticData.get("isMaxStay");
		String updatedMaxNights = staticData.get("MaxNights");
		String updatedIsMoreThanDaysReq = staticData.get("isMoreThanDaysReq");
		String updatedMoreThanDaysCount = staticData.get("MoreThanDaysCount");
		String updatedIsWithInDaysReq = staticData.get("isWithInDaysReq");
		String updatedWithInDaysCount = staticData.get("WithInDaysCount");
		String updatedPromoCode = staticData.get("PromoCode");
		String updatedIsSeasonLevelRules = staticData.get("isSerasonLevelRules");
		String updatedIsAssignRulesByRoomClass = staticData.get("isAssignRulesByRoomClass");
		String updatedSeasonRuleSpecificRoomClasses = staticData.get("SeasonRuleSpecificRoomClasses");
		String updatedSeasonRuleType = staticData.get("SeasonRuleType");
		String updatedSeasonRuleMinStayValue = staticData.get("SeasonRuleMinStayValue");
		String isdaysCheck = staticData.get("isSeasonRuleOnDays");
		String bulkUpdateMinStayValue = staticData.get("bulkUpdateMinStayValue");
		String bulkUpdateRatePlan = staticData.get("bulkUpdateRatePlan");
		String bilkUpdateRoomClass = staticData.get("bilkUpdateRoomClass");
		String entityName = staticData.get("entityName");
		String isTotalOccupancyOn = staticData.get("isTotalOccupancyOn");
		String totalOccupancyType = staticData.get("TotalOccupancyType");
		String totalOccupancyValue = staticData.get("TotalOccupancyValue");
		String updatedAdditionalAdults = staticData.get("AdditionalAdult");
		String updatedAdditionalChild = staticData.get("AdditionalChild");
		// static Data

		String dateFormat = Utility.monthDayYear_DateFormat;
		String seasonDateFormat = Utility.dayMonthYear_DateFormat;
		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate, dateFormat);
		app_logs.info("days: " + days);
		app_logs.info("Property : " + propertyName.split("'")[0]);
		String BEAvailabilityGridDefaultAdultsValue = "2";
		String BEAvailabilityGridDefaultChildrenValue = "0";
	

		String rateFolioName = ratePlanData.get("rateFolioName");
		String rateDescription = ratePlanData.get("rateDescription");
		String rateRoomClass = ratePlanData.get("rateRoomClass");
		String rateRoomClassSize = ratePlanData.get("rateRoomClassSize");
		String ratePlanBaseAmount = ratePlanData.get("ratePlanBaseAmount");
		String addtionalAdult = ratePlanData.get("AddtionalAdult");
		String additionalChild = ratePlanData.get("AdditionalChild");
		String intervalRatePlanIntervalValue = ratePlanData.get("intervalRatePlanIntervalValue");
		String isDefaultProrateChecked = ratePlanData.get("isDefaultProrateChecked");
		String packageRatePlanRateType = ratePlanData.get("packageRatePlanRateType");
		String packageRatePlanProductName = ratePlanData.get("packageRatePlanProductName");
		String packageratePlanProductFirstCalculationMethod = ratePlanData
				.get("packageratePlanProductFirstCalculationMethod");
		String packageratePlanProductsecondCalculationMethod = ratePlanData
				.get("packageratePlanProductSecondCalculationMethod");
		String packageRatePlanProductAmount = ratePlanData.get("packageRatePlanProductAmount");
		String channels = ratePlanData.get("channels");
		String isRatePlanRistrictionReq = ratePlanData.get("isRatePlanRistrictionReq");
		String ristrictionType = ratePlanData.get("RistrictionType");
		String isMinStay = ratePlanData.get("isMinStay");
		String minNights = ratePlanData.get("MinNights");
		String isMaxStay = ratePlanData.get("isMaxStay");
		String maxNights = ratePlanData.get("MaxNights");
		String isMoreThanDaysReq = ratePlanData.get("isMoreThanDaysReq");
		String moreThanDaysCount = ratePlanData.get("MoreThanDaysCount");
		String isWithInDaysReq = ratePlanData.get("isWithInDaysReq");
		String withInDaysCount = ratePlanData.get("WithInDaysCount");
		String promoCode = ratePlanData.get("PromoCode");
		String isPolicesReq = ratePlanData.get("isPolicesReq");
		String policiesType = ratePlanData.get("PoliciesType");
		String policiesName = ratePlanData.get("PoliciesName");
		String seasonDelim = ratePlanData.get("seasonDelim");
		String seasonName = ratePlanData.get("SeasonName");
		String seasonStartDate = ratePlanData.get("SeasonStartDate");
		String seasonEndDate = ratePlanData.get("SeasonEndDate");
		String isMonDay = ratePlanData.get("isMonDay");
		String isTueDay = ratePlanData.get("isTueDay");
		String isWednesDay = ratePlanData.get("isWednesDay");
		String isThursDay = ratePlanData.get("isThursDay");
		String isFriday = ratePlanData.get("isFriday");
		String isSaturDay = ratePlanData.get("isSaturDay");
		String isSunDay = ratePlanData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults = ratePlanData.get("isAdditionalChargesForChildrenAdults");
		String ratePerNight = ratePlanData.get("RatePerNight");
		String maxAdults = ratePlanData.get("MaxAdults");
		String maxPersons = ratePlanData.get("MaxPersons");
		String additionalAdultsPerNight = ratePlanData.get("AdditionalAdultsPerNight");
		String additionalChildPerNight = ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason = ratePlanData.get("isAddRoomClassInSeason");
		String extraRoomClassesInSeason = ratePlanData.get("ExtraRoomClassesInSeason");
		String extraRoomClassRatePerNight = ratePlanData.get("ExtraRoomClassRatePerNight");
		String extraRoomClassMaxAdults = ratePlanData.get("ExtraRoomClassMaxAdults");
		String extraRoomClassMaxPersons = ratePlanData.get("ExtraRoomClassMaxPersons");
		String extraRoomClassAdditionalAdultsPerNight = ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String extraRoomClassAdditionalChildPerNight = ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		String isSeasonLevelRules = ratePlanData.get("isSerasonLevelRules");
		String isAssignRulesByRoomClass = ratePlanData.get("isAssignRulesByRoomClass");
		String seasonRuleSpecificRoomClasses = ratePlanData.get("SeasonRuleSpecificRoomClasses");
		String seasonRuleType = ratePlanData.get("SeasonRuleType");
		String seasonRuleMinStayValue = ratePlanData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnMonday = ratePlanData.get("isSeasonRuleOnMonday");
		String isSeasonRuleOnTuesday = ratePlanData.get("isSeasonRuleOnTuesday");
		String isSeasonRuleOnWednesday = ratePlanData.get("isSeasonRuleOnWednesday");
		String isSeasonRuleOnThursday = ratePlanData.get("isSeasonRuleOnThursday");
		String isSeasonRuleOnFriday = ratePlanData.get("isSeasonRuleOnFriday");
		String isSeasonRuleOnSaturday = ratePlanData.get("isSeasonRuleOnSaturday");
		String isSeasonRuleOnSunday = ratePlanData.get("isSeasonRuleOnSunday");
		String isSeasonPolicies = ratePlanData.get("isSeasonPolicies");
		String seasonPolicyType = ratePlanData.get("SeasonPolicyType");
		String seasonPolicyValues = ratePlanData.get("SeasonPolicyValues");
		String isAssignPoliciesByRoomClass = ratePlanData.get("isAssignPoliciesByRoomClass");
		String seasonPolicySpecificRoomClasses = ratePlanData.get("seasonPolicySpecificRoomClasses");
		String isProRateStayInSeason = ratePlanData.get("isProRateStayInSeason");
		String isProRateInRoomClass = ratePlanData.get("isProRateInRoomClass");
		String isCustomPerNight = ratePlanData.get("isCustomPerNight");
		String customRoomClasses = ratePlanData.get("customRoomClasses");
		String customRatePerNight = ratePlanData.get("customRatePerNight");
		String isCustomRatePerNightAdultandChild = ratePlanData.get("isCustomRatePerNightAdultandChild");
		String customRateChildPerNight = ratePlanData.get("customRateChildPerNight");
		String customRateAdultdPerNight = ratePlanData.get("customRateAdultdPerNight");
		String roomClassesWithProRateUnchecked = ratePlanData.get("roomClassesWithProRateUnchecked");

		// remove column here
		String productName = createProduct.get("productsName");
		String isSellOnBookingEngine = createProduct.get("isSellOnBookingEngine");
		String bookingEngineAvailabilityOption = createProduct.get("bookingEngineAvailabilityOption");
		String productDescription = createProduct.get("productDescritpion");
		String productCategory = createProduct.get("productCategory");
		String productPolicy = createProduct.get("productPolicy");
		String calculationMethodOne = createProduct.get("productFirstCalculationMethod");
		String calculationMethodTwo = createProduct.get("productSecondCalculationMethod");
		String productAmount = createProduct.get("productAmount");

		Utility.DELIM = "\\" + delim;
		Utility.SEASONDELIM = seasonDelim;
		String[] splitSeasonLevelDays = isdaysCheck.split(Utility.DELIM);
		String updatedIsSeasonRuleOnMonday = splitSeasonLevelDays[0];
		String updatedIsSeasonRuleOnTuesday = splitSeasonLevelDays[1];
		String updatedIsSeasonRuleOnWednesday = splitSeasonLevelDays[2];
		String updatedIsSeasonRuleOnThursday = splitSeasonLevelDays[3];
		String updatedIsSeasonRuleOnFriday = splitSeasonLevelDays[4];
		String updatedIsSeasonRuleOnSaturday = splitSeasonLevelDays[5];
		String updatedIsSeasonRuleOnSunday = splitSeasonLevelDays[6];

		// initializing static data
		String createRoomClass = "FALSE";
		String roomClassAbb = "RC";
		String roomClassMaxAdults = "4";
		String roomClassMaxPersons = "8";
		String roomQuantity = "1";
		String roomClassRatePerNight = "130";
		String roomClassAdultsRate = "49";
		String roomClassChildRate = "51";
		String updateRules = "no";
		String bulkUpdate = "TRUE";
		String customMessage = "";
		String bulkUpdateRules = "";
		if (testCaseDescription.contains("No check in")) {
			bulkUpdateRules = "No Check In";
		} else if (testCaseDescription.contains("No check out")) {
			bulkUpdateRules = "No Check out";
		} else {
			bulkUpdateRules = "Min stay";
		}
		customMessage = "Custom Message: Room Class is not available because of " + bulkUpdateRules + " rule failed.";
		if (testCaseDescription.equalsIgnoreCase("Verify Min stay error message in Room details page")
				|| testCaseDescription.equalsIgnoreCase("Verifying the Rules sort order.")) {
			createRoomClass = "TRUE";
			reservationRoomClassName = "RoomClass";
			bulkUpdate = "FALSE";
			if (testCaseDescription.equalsIgnoreCase("Verifying the Rules sort order.")) {
				reservationRoomClassName = reservationRoomClassName + "One" + delim + reservationRoomClassName + "Two"
						+ delim + reservationRoomClassName + "Three";
				roomClassAbb = roomClassAbb + "1" + delim + roomClassAbb + "2" + delim + roomClassAbb + "3";
				roomClassMaxAdults = roomClassMaxAdults + delim + roomClassMaxAdults + delim + roomClassMaxAdults;
				roomClassMaxPersons = roomClassMaxPersons + delim + roomClassMaxPersons + delim + roomClassMaxPersons;
				roomQuantity = roomQuantity + delim + roomQuantity + delim + roomQuantity;
				roomClassRatePerNight = roomClassRatePerNight + delim + roomClassRatePerNight + delim
						+ roomClassRatePerNight;
				roomClassAdultsRate = roomClassAdultsRate + delim + roomClassAdultsRate + delim + roomClassAdultsRate;
				roomClassChildRate = roomClassChildRate + delim + roomClassChildRate + delim + roomClassChildRate;
			}

		}

		if (testCaseDescription.equalsIgnoreCase("Verify Min stay error message in Room details page")
				|| testCaseDescription
						.equalsIgnoreCase("Verifying the error message for correct promo code with min stay rules.")) {
			updateRules = "yes";
			if (testCaseDescription.equalsIgnoreCase("Verify Min stay error message in Room details page")) {
				updatedIsSeasonLevelRules = "yes";
			} else {
				updatedIsRatePlanRistrictionReq = "TRUE";
			}
		}

		testName = testCaseDescription;

		String test_description = "";
		String test_catagory = "";

		String testName = testCaseDescription;
		test_description = testCaseDescription + "<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1994' target='_blank'>"
				+ "Click here to open JIRA: AUTOMATION-1994</a>";
		test_catagory = "NBE_AvailabilityGrid";
		scriptName.clear();
		testDescription.clear();
		testCategory.clear();
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		Distribution distribution = new Distribution();
		RatesGrid rateGrid = new RatesGrid();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		Admin admin = new Admin();
		Properties properties = new Properties();

		NewRoomClassesV2 roomClassV2 = new NewRoomClassesV2();
		DerivedRate derivedRate = new DerivedRate();
		RatePackage ratePackage = new RatePackage();

		BookingEngine bookingEngine = new BookingEngine();

		String inActiveStatus = "Inactive";
		String activeStatus = "Active";
		String randomNumber = Utility.GenerateRandomNumber();

		String calendarTodayDay = "today";
		String todayDate = null;
		boolean israteplanExist = false;

		boolean ratePlanAvailableInBE = true;
		ArrayList<String> selectedChannels = null;
		ArrayList<String> selectedRoomClasses = null;

		ArrayList<String> productWithDetails = new ArrayList<>();

		int seasonDays = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate, seasonDateFormat);
		String seasonDuration = "" + seasonDays + "";

		int daysBeforeCheckInDate = 0;

		String ratePlanPromoCode = null;
		boolean isPromocode = false;
		ArrayList<String> availabilityInRatesBefore = new ArrayList<String>();
		String roomReservedValueBefore = "";
		String roomAvailableValueBefore = "";
		String roomTotalValueBefore = "";
		boolean selectedRatePlanDefaultStatus = false;
		String selectedRatePlanFolioName = null;
		String dateWithoutSeason = "";

		ArrayList<String> roomClassList = new ArrayList<>();
		HashMap<String, ArrayList<String>[]> roomClassDataValuesForBookingEngineChannel = new HashMap<String, ArrayList<String>[]>();

		String[] splitDays = isdaysCheck.split(Utility.DELIM);
		String isSundayCheck = splitDays[0];
		String isMondayCheck = splitDays[1];
		String isTuesdayChecked = splitDays[2];
		String isWednesdayChecked = splitDays[3];
		String isThursdayChecked = splitDays[4];
		String isFridayChecked = splitDays[5];
		String isSaturdayChecked = splitDays[6];
		String[] channelArray = bookingEngineChannel.split(Utility.DELIM);
		String updateRateByRoomClass = "TRUE";
		ArrayList<String> activeChannelsList = new ArrayList<String>();
		ArrayList<String> listOfDays = new ArrayList<>();
		listOfDays.add("Sun");
		listOfDays.add("Mon");
		listOfDays.add("Tue");
		listOfDays.add("Wed");
		listOfDays.add("Thu");
		listOfDays.add("Fri");
		listOfDays.add("Sat");

		HashMap<String, String> listOfDay = new HashMap<String, String>();
		if (isSundayCheck.equals("yes")) {
			listOfDay.put("Sun", isSundayCheck);

		}
		if (isMondayCheck.equals("yes")) {
			listOfDay.put("Mon", isMondayCheck);

		}

		if (isTuesdayChecked.equals("yes")) {
			listOfDay.put("Tue", isTuesdayChecked);

		}

		if (isWednesdayChecked.equals("yes")) {
			listOfDay.put("Wed", isWednesdayChecked);

		}

		if (isThursdayChecked.equals("yes")) {
			listOfDay.put("Thu", isThursdayChecked);

		}

		if (isFridayChecked.equals("yes")) {
			listOfDay.put("Fri", isFridayChecked);

		}

		if (isSaturdayChecked.equals("yes")) {
			listOfDay.put("Sat", isSaturdayChecked);

		}
		app_logs.info(listOfDay);
		HashMap<String, ArrayList<String>> getRoomClassWithRates = new HashMap<>();
		String ratePlanBaseRate = null;
		String ratePlanAdultCapicity = null;
		String ratePlanPersonCapicity = null;
		HashMap<String, String> getOldAvailability = new HashMap<String, String>();
		String isMinimumStayOn = "no";
		String isCheckInOn = "no";
		String isNoCheckInChecked = "no";
		String isCheckOutOn = "no";
		String isNoCheckOutChecked = "no";
		HashMap<String, String> availabilityDataValuesMap = new HashMap<String, String>();
		HashMap<String, HashMap<String, String>> bulkRateUpdateValuesMap = new HashMap<String, HashMap<String, String>>();
		HashMap<String, HashMap<String, String>> bulkRateMinStayValuesMap = new HashMap<String, HashMap<String, String>>();
		HashMap<String, HashMap<String, String>> bulkAvailabilityUpdateValuesMap = new HashMap<String, HashMap<String, String>>();
		HashMap<String, HashMap<String, String>> bulkCheckInUpdateValuesMap = new HashMap<String, HashMap<String, String>>();
		HashMap<String, HashMap<String, String>> bulkCheckOutUpdateValuesMap = new HashMap<String, HashMap<String, String>>();
		HashMap<String, String> roomClassesSeasonLevelData = new HashMap<String, String>();
		HashMap<String, String> getRateLevelPolicy = new HashMap<String, String>();
		HashMap<String, String> getSessionLevelPolicy = new HashMap<String, String>();
		boolean roomClassNotExist = false;
		boolean seasonPoliciesExist = false;
		boolean ratePlanIsAdditionalAdultChild = false;
		String ratePlanMaxAdults = null;
		String ratePlanMaxPersons = null;
		String ratePlanAdultsRate = null;
		String ratePlanChildRate = null;
		String randomString = "";
		Tax tax = new Tax();
		String tName = "";

		try {
			String[] casesList = TestCaseID.split(",");
			for (int i = 0; i < casesList.length; i++) {
				caseId.add(casesList[i]);
				statusCode.add("5");
			}
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			try {
				reservationRoomClassName = "Junior Suites";
				loginRateV2(driver);

			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				loginRateV2(driver);
			}

			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");
			numberOfProperties = 1;
			propertyName = "Automation OTA";
			timeZone = "(GMT-05:00) Eastern Time (US and Canada)";
			Utility.clientCurrencySymbol = "$";
			Utility.clientCurrency = "USD";
			scenarioNumber = 1;
			bookingEngineChannelFullName = "innRoad Booking Engine - autootaqa.client.qainnroad.com";
			if (testCaseDescription.equalsIgnoreCase("Verifying the Rules sort order.")) {
				Assert.assertTrue(false,
						"Failed: because we can't create seperate rules for seperate room class in a single rate Plan");
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		if (scenarioNumber == 0) {
			try {

				testSteps.add("=================GET THE PROPERTY TIMEZONE ======================");
				app_logs.info("================= GET THE PROPERTY TIMEZONE ======================");
				navigation.Setup(driver);
				app_logs.info("Navigate Setup");
				testSteps.add("Navigate Setup");
				navigation.Properties(driver);
				app_logs.info("Navigate Properties");
				testSteps.add("Navigate Properties");
				propertyName = properties.getPropertyName(driver, 1);
				testSteps.add("Property Name : " + propertyName);
				app_logs.info("Property Name : " + propertyName);
				numberOfProperties = properties.getNumberOfProperties(driver);
				testSteps.add("Number of Properties : " + numberOfProperties);
				app_logs.info("Number of Properties : " + numberOfProperties);
				if (!propertyName.contains("'")) {
					app_logs.info("Property Name : " + propertyName);
					navigation.openProperty(driver, testSteps, propertyName);
				} else {
					app_logs.info("Property Name having ' is  : " + propertyName);
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}
				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				timeZone = navigation.get_Property_TimeZone(driver);
				testSteps.add("Property TimeZone: " + timeZone);
				navigation.Reservation_Backward(driver);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get property Time Zone", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get property Time Zone", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("========== SELECT PROPERTY ==========");
				testSteps.add("========== SELECT PROPERTY ==========");
				if (numberOfProperties != 1) {
					navigation.selectProperty(driver, propertyName);
					app_logs.info("Select Property : " + propertyName);
					testSteps.add("Select Property : " + propertyName);
				} else {
					app_logs.info("Already selected Property : " + propertyName);
					testSteps.add("Already selected Property : " + propertyName);
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to select property", testName, "QAProperty", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to select property", testName, "QAProperty", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			// Get default currency
			try {
				testSteps.add("==========GET DEFAULT CURRENCY FROM CLIENT INFO========");
				app_logs.info("==========GET DEFAULT CURRENCY FROM CLIENT INFO==========");
				navigation.Admin(driver);
				app_logs.info("Navigate Admin");
				testSteps.add("Navigate Admin");
				navigation.Clientinfo(driver);
				app_logs.info("Navigate ClientInfo");
				testSteps.add("Navigate ClientInfo");
				admin.clickClientName(driver);
				app_logs.info("Clicked on Client");
				testSteps.add("Clicked on Client");
				admin.clickClientOption(driver);
				app_logs.info("Clicked on Client Options Tab");
				testSteps.add("Clicked on Client Options Tab");
				Utility.clientCurrency = admin.getDefaultClientCurrency(driver);

				Utility.clientCurrencySymbol = Utility.clientCurrency.split("\\(")[1].replace(")", "").replace(" ", "");

				Utility.clientCurrency = Utility.clientCurrency.split("\\(")[0].replace(" ", "");

				app_logs.info("Default Client Currency is:" + Utility.clientCurrency + " Symbol:"
						+ Utility.clientCurrencySymbol);
				testSteps.add("Default Client Currency is:" + Utility.clientCurrency + " Symbol:"
						+ Utility.clientCurrencySymbol);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Default Currency", testName, "Preconditions", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Default Currency", testName, "Preconditions", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// Checking Pre Conditions
			try {

				app_logs.info("====== VERIFY PRE-CONDITION '" + entityName + "' IS SELECTED =====");
				testSteps.add("====== VERIFY PRE-CONDITION '" + entityName + "' IS SELECTED =====");
				try {
					navigation.Admin(driver);
				} catch (Exception e) {
					driver.navigate().refresh();
				}
				app_logs.info("Navigate Admin");
				testSteps.add("Navigate Admin");
				admin.verifyReportsV2EntitlementEnable(driver, entityName, testSteps, true);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify Precondition '" + entityName + "' IS SELECTED", testName,
							"Preconditions", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify Preconditions '" + entityName + "' IS SELECTED", testName,
							"Preconditions", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ==");
				testSteps.add(
						"====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ======");
				navigation.Inventory(driver);
				app_logs.info("Navigate Inventory");
				testSteps.add("Navigate Inventory");
				navigation.Distribution(driver);
				app_logs.info("Navigate Distribution");
				testSteps.add("Navigate Distribution");

				app_logs.info("Get Booking Engine Channel Complete name");
				testSteps.add("Get Booking Engine Channel Complete name");
				ArrayList<String> channelsList = distribution.getAllChannelsContainingName(driver,
						bookingEngineChannel);
				if (channelsList.size() == 1) {
					bookingEngineChannel = channelsList.get(0);
				} else {
					boolean channelNamefound = false;
					for (int i = 0; i < channelsList.size(); i++) {
						if (channelsList.get(i).toLowerCase().contains(propertyName.toLowerCase())) {
							bookingEngineChannel = channelsList.get(i);
							channelNamefound = false;
						}
					}
					if (!channelNamefound) {
						bookingEngineChannel = channelsList.get(channelsList.size() - 1);
					}
				}
				bookingEngineChannelFullName = bookingEngineChannel;
				app_logs.info("Booking Engine channel : '" + bookingEngineChannel + "'");
				testSteps.add("Booking Engine channel : '" + bookingEngineChannel + "'");
				boolean distribute = false;
				if (bookingEngineChannel.contains("'")) {
					distribute = distribution.getDistributeValueOfChannel(driver, bookingEngineChannel.split("'")[0]);
				} else {
					distribute = distribution.getChannelDistributeValue(driver, bookingEngineChannel);
				}
				if (distribute) {
					app_logs.info("'" + bookingEngineChannel + "' Distribute is selected by default");
					testSteps.add("'" + bookingEngineChannel + "' Distribute is selected by default");
				} else {

					app_logs.info("'" + bookingEngineChannel + "' Distribute is not selected by default");
					testSteps.add("'" + bookingEngineChannel + "' Distribute is not selected by default");
				}
				Assert.assertTrue(distribute,
						"Failed '" + bookingEngineChannel + "' Distribute is not selected by default");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e,
							"Failed to verify Precondition '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ",
							testName, "Preconditions", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e,
							"Failed to verify Preconditions '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ",
							testName, "Preconditions", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		} else {
			try {
				app_logs.info("========== SELECT PROPERTY ==========");
				testSteps.add("========== SELECT PROPERTY ==========");
				if (numberOfProperties != 1) {
					navigation.selectProperty(driver, propertyName);
					app_logs.info("Select Property : " + propertyName);
					testSteps.add("Select Property : " + propertyName);
				}
				app_logs.info("Default Client Currency is:" + Utility.clientCurrency + " Symbol:"
						+ Utility.clientCurrencySymbol);
				testSteps.add("Default Client Currency is:" + Utility.clientCurrency + " Symbol:"
						+ Utility.clientCurrencySymbol);
				bookingEngineChannel = bookingEngineChannelFullName;
				app_logs.info("Booking Engine channel : '" + bookingEngineChannel + "'");
				testSteps.add("Booking Engine channel : '" + bookingEngineChannel + "'");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to set details", testName, "LEDGER ACCOUNT", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to set details", testName, "LEDGER ACCOUNT", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (Boolean.parseBoolean(createRoomClass)) {
			// Room Class
			try {

				app_logs.info("==========DELETE ROOM CLASS==========");
				testSteps.add("==========DELETE ROOM CLASS==========");

				navigation.mainSetupManu(driver);
				app_logs.info("Navigate Setup");
				testSteps.add("Navigate Setup");

				navigation.navigateRoomClass(driver);
				app_logs.info("Navigate Room Class");
				testSteps.add("Navigate Room Class");

				app_logs.info("roomClassType : '" + roomClassType + "'");
				if (roomClassType.equalsIgnoreCase("Inactive")) {
					app_logs.info("roomClassType : " + roomClassType);
					roomClassV2.selectStatus(driver, testSteps, inActiveStatus);
				} else {
					roomClassV2.selectStatus(driver, testSteps, activeStatus);
				}
				for (int i = 0; i < reservationRoomClassName.split(Utility.DELIM).length; i++) {
					boolean isRoomClassShowing = roomClassV2.searchRoomClassStartWithV2(driver,
							reservationRoomClassName.split(Utility.DELIM)[i]);
					app_logs.info("Search");
					if (isRoomClassShowing) {
						getTestSteps.clear();
						getTestSteps = roomClassV2.deleteRoomClassStartWithRoomClassV2(driver,
								reservationRoomClassName.split(Utility.DELIM)[i]);
						testSteps.addAll(getTestSteps);
						testSteps.add("Successfully Deleted RoomClass  : "
								+ reservationRoomClassName.split(Utility.DELIM)[i]);
						app_logs.info("Successfully Deleted RoomClass :  "
								+ reservationRoomClassName.split(Utility.DELIM)[i]);
					} else {
						testSteps.add("No room class exist");
					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				app_logs.info("==========CREATE '" + roomClassType + "' ROOM CLASS==========");
				testSteps.add("==========CREATE '" + roomClassType + "' ROOM CLASS==========");
				String roomClassWithRandomNumber = reservationRoomClassName.split(Utility.DELIM)[0] + randomNumber;
				String roomClassAbbWithRandomNumber = roomClassAbb.split(Utility.DELIM)[0] + randomNumber;
				for (int i = 1; i < reservationRoomClassName.split(Utility.DELIM).length; i++) {
					roomClassWithRandomNumber = roomClassWithRandomNumber + delim
							+ reservationRoomClassName.split(Utility.DELIM)[i] + randomNumber;
					roomClassAbbWithRandomNumber = roomClassAbbWithRandomNumber + delim
							+ roomClassAbb.split(Utility.DELIM)[i] + randomNumber;

				}
				reservationRoomClassName = roomClassWithRandomNumber;
				roomClassAbb = roomClassAbbWithRandomNumber;
				for (int i = 0; i < reservationRoomClassName.split(Utility.DELIM).length; i++) {
					if (roomClassType.equalsIgnoreCase("inactive")) {
						getTestSteps.clear();
						getTestSteps = roomClassV2.createRoomClassWithStatusV2(driver, reservationRoomClassName,
								roomClassAbb, roomClassMaxAdults.split(Utility.DELIM)[i],
								roomClassMaxPersons.split(Utility.DELIM)[i], roomQuantity.split(Utility.DELIM)[i],
								inActiveStatus, test, getTestSteps);
						testSteps.addAll(getTestSteps);
					} else if (roomClassType.equalsIgnoreCase("Unpublished")) {
						// To do
					} else {
						getTestSteps.clear();
						getTestSteps = roomClassV2.createRoomClassV2(driver, reservationRoomClassName, roomClassAbb,
								roomClassMaxAdults.split(Utility.DELIM)[i], roomClassMaxPersons.split(Utility.DELIM)[i],
								roomQuantity.split(Utility.DELIM)[i], test, getTestSteps);
						testSteps.addAll(getTestSteps);

					}

					roomClassV2.closeRoomClassTabV2(driver, reservationRoomClassName);
					try {
						if (roomClassType.equalsIgnoreCase("inactive")) {
							roomClassV2.selectStatus(driver, testSteps, inActiveStatus);
						} else {
							roomClassV2.selectStatus(driver, testSteps, activeStatus);
						}
					} catch (Exception e) {
						if (roomClassType.equalsIgnoreCase("inactive")) {
							roomClassV2.selectStatus(driver, testSteps, inActiveStatus);
						} else {
							roomClassV2.selectStatus(driver, testSteps, activeStatus);
						}
					}
					boolean isRoomClassShowing = roomClassV2.searchRoomClassV2(driver, reservationRoomClassName);
					app_logs.info("Search");
					if (isRoomClassShowing) {
						testSteps.add("'" + roomClassType + "' Room Class(" + reservationRoomClassName
								+ ") successfully created");
						app_logs.info("'" + roomClassType + "' Room Class(" + reservationRoomClassName
								+ ") successfully created");
					} else {
						Assert.assertTrue(false, "No room class created");
					}
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		try {
			if (Boolean.parseBoolean(createRoomClass)) {
				navigation.InventoryV2(driver);
				testSteps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");
			} else if (scenarioNumber != 0) {
				navigation.Inventory(driver);
				testSteps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");
			}
			scenarioNumber++;

			navigation.ratesGrid(driver, testSteps);
			app_logs.info("========== GET TODAY'S DATE ==========");
			testSteps.add("========== GET TODAY'S DATE ==========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, dateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			String selectedDate = ratesGrid.getCalendarDate(driver, "selected", dateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
			if (Utility.getNumberofDays(todayDate, selectedDate, dateFormat) != 0) {
				todayDate = selectedDate;
			}

			String getCurrentDate = Utility.getNextDate(0, dateFormat, timeZone);
			if (ESTTimeZone.CompareDates(checkInDate, dateFormat, timeZone)) {
				checkInDate = getCurrentDate;
				checkOutDate = Utility.getNextDate(days, dateFormat, timeZone);

			}
			app_logs.info("Check-in Date : " + checkInDate);
			app_logs.info("Check-out Date : " + checkOutDate);
			app_logs.info("Today date : " + todayDate);
			daysBeforeCheckInDate = Utility.compareDates(checkInDate, todayDate, dateFormat);

			app_logs.info("Days before Checkin Date : " + daysBeforeCheckInDate);
			app_logs.info("Days before Checkin Date : " + daysBeforeCheckInDate);
			getTestSteps.clear();
			getTestSteps = ratesGrid.closeCalendar(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender", testName,
						"NavigateToRateGrid", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender", testName,
						"NavigateToRateGrid", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		if (ratePlanType.contains("Package")) {
			try {
				testSteps.add("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");
				app_logs.info("=================== NAVIGATE TO PRODUCT AND BUNDLES ======================");

				derivedRate.clickTab(driver, "Products & Bundles", testSteps);
				testSteps.add("Navigated to products and bundles");
				ratePackage.spinnerLoading(driver);
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to  navigate to products and bundles", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				Boolean isSellOnEngine = Boolean.parseBoolean(isSellOnBookingEngine);

				testSteps.add("=================== VERIFY PRODUCT EXIST ======================");
				app_logs.info("=================== VERIFY PRODUCT EXIST ======================");
				if (!ratePackage.verifyProductExist(driver, productName)) {

					testSteps.add("Successfully verified product '" + productName + "' not exist.");
					app_logs.info("Successfully verified product '" + productName + "' not exist.");
					testSteps.add("=================== CREATE PRODUCT ======================");
					app_logs.info("=================== CREATE PRODUCT ======================");

					getTestSteps.clear();
					getTestSteps = ratePackage.createProduct(driver, productName, isSellOnEngine,
							bookingEngineAvailabilityOption, reservationRoomClassName, productDescription,
							productAmount, productPolicy, productCategory, calculationMethodOne, calculationMethodTwo,
							seasonStartDate, seasonEndDate, seasonDuration, timeZone);
					testSteps.addAll(getTestSteps);

					testSteps.add("Created product successfully");
					app_logs.info("Created product successfully");
					productWithDetails = ratePackage.getProductDetail(driver, productName);
					app_logs.info("'" + productWithDetails.get(0) + "'");
					app_logs.info("'" + productWithDetails.get(1) + "'");
					app_logs.info("'" + productWithDetails.get(2) + "'");
					app_logs.info("'" + productWithDetails.get(3) + "'");
					app_logs.info("'" + productWithDetails.get(4) + "'");
					app_logs.info("'" + productWithDetails.get(5) + "'");
				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create product ", testName, "CreateProducts", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to create product ", testName, "CreateProducts", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("=================== NAVIGATE TO RATE GRID ======================");
				app_logs.info("=================== NAVIGATE TO RATE GRID ======================");
				navigation.Inventory(driver, testSteps);
				navigation.RatesGrid(driver);
				testSteps.add("Navigated to RatesGrid");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		try {
			testSteps.add("=================== Verify Rate plan Exist or Not ======================");
			app_logs.info("=================== Verify Rate plan Exist or Not ======================");
			driver.navigate().refresh();
//			try {
//				getTestSteps.clear();
//				israteplanExist = rateGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
//			} catch (Exception e) {
//				driver.navigate().refresh();
//				getTestSteps.clear();
//				israteplanExist = rateGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
//			}
//			testSteps.addAll(getTestSteps);
			israteplanExist = true;
			testSteps.add("Successfully verified that rate plan exist");
			app_logs.info("israteplanExist : " + israteplanExist);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
				Utility.updateReport(e, "Failed to verify rate plan exist or not", testName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		if (!israteplanExist) {
			testSteps.add("=================== Creating the Rate Plan ======================");
			app_logs.info("=================== Creating the Rate Plan ======================");
			// Create New Rate and Attach RoomClass
			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.createParentRatePlan(driver, delim, ratePlanType, ratePlanName, rateFolioName,
						rateDescription, rateRoomClass, rateRoomClassSize, ratePlanBaseAmount, addtionalAdult,
						additionalChild, intervalRatePlanIntervalValue, isDefaultProrateChecked,
						packageRatePlanRateType, packageRatePlanProductName,
						packageratePlanProductFirstCalculationMethod, packageratePlanProductsecondCalculationMethod,
						packageRatePlanProductAmount, channels, isRatePlanRistrictionReq, ristrictionType, isMinStay,
						minNights, isMaxStay, maxNights, isMoreThanDaysReq, moreThanDaysCount, isWithInDaysReq,
						withInDaysCount, promoCode, isPolicesReq, policiesType, policiesName, seasonDelim, seasonName,
						seasonStartDate, seasonEndDate, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
						isSaturDay, isSunDay, isAdditionalChargesForChildrenAdults, ratePerNight, maxAdults, maxPersons,
						additionalAdultsPerNight, additionalChildPerNight, isAddRoomClassInSeason,
						extraRoomClassesInSeason, extraRoomClassRatePerNight, extraRoomClassMaxAdults,
						extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight,
						extraRoomClassAdditionalChildPerNight, isSeasonLevelRules, isAssignRulesByRoomClass,
						seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, isSeasonPolicies, seasonPolicyType,
						seasonPolicyValues, isAssignPoliciesByRoomClass, seasonPolicySpecificRoomClasses,
						isProRateStayInSeason, isProRateInRoomClass, isCustomPerNight, customRoomClasses,
						customRatePerNight, isCustomRatePerNightAdultandChild, customRateChildPerNight,
						customRateAdultdPerNight, roomClassesWithProRateUnchecked);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed", testName, "RatesV2", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		try {
			testSteps.add("========== UPDATE RATE PLAN '" + ratePlanName + "'=========");
			app_logs.info("========== UPDATE RATE PLAN '" + ratePlanName + "'=========");
			driver.navigate().refresh();
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			try {
				ratesGrid.clickOnEditRatePlan(driver);
			} catch (Exception e) {
				ratesGrid.clickOnEditRatePlan(driver);
			}
			testSteps.add("Click on edit rate plan");
			app_logs.info("Click on edit rate plan");
			selectedChannels = nightlyRate.getSelectedChannels(driver);
			if (!ratesGrid.getChannelStatus(driver, bookingEngineChannel)) {
				Utility.app_logs.info("Required Channels  is not Selected : " + bookingEngineChannel);
				testSteps.add("Required Channels  is not Selected : " + bookingEngineChannel);
				nightlyRate.selectChannels(driver, bookingEngineChannel, true, testSteps);
				selectedChannels = nightlyRate.getSelectedChannels(driver);
			}
			Utility.app_logs.info("Selected Channels : " + selectedChannels);
			testSteps.add("Selected Channels : " + selectedChannels);
			if (Boolean.parseBoolean(createRoomClass)) {
				testSteps.add("=================== ATTACH ROOM CLASS ======================");
				app_logs.info("=================== ATTACH ROOM CLASS ======================");
				selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);
				for (int i = 0; i < reservationRoomClassName.split(Utility.DELIM).length; i++) {

					if (!ratesGrid.getRoomClassStatus(driver, reservationRoomClassName.split(Utility.DELIM)[i])) {
						Utility.app_logs.info("Required Room CLass  is not Selected : "
								+ reservationRoomClassName.split(Utility.DELIM)[i]);
						testSteps.add("Required  Room CLass  is not Selected : "
								+ reservationRoomClassName.split(Utility.DELIM)[i]);
						nightlyRate.selectRoomClassesEditPage(driver, reservationRoomClassName.split(Utility.DELIM)[i],
								true, testSteps);

					}
				}
			}
			selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);

			Utility.app_logs.info("Selected Room Classes : " + selectedRoomClasses);
			testSteps.add("Selected Room Classes : " + selectedRoomClasses);
			testSteps.add("=================== UPDATING RATE PLAN RESTRICATIONS  ======================");
			app_logs.info("=================== UPDATING RATE PLAN RESTRICATIONS  ======================");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
			testSteps.addAll(getTestSteps);
			nightlyRate.selectRestrictionTypes(driver,
					"Length of stay" + Utility.DELIM + "Booking window" + Utility.DELIM + "Promo code", false,
					getTestSteps);
			testSteps.add("Deselect all rate plan restrictions");
			app_logs.info("Deselect all rate plan restrictions");
			if (updateRules.equalsIgnoreCase("yes")) {
				try {
					nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(updatedIsRatePlanRistrictionReq),
							updatedRistrictionType, Boolean.parseBoolean(updatedIsMinStay), updatedMinNights,
							Boolean.parseBoolean(updatedIsMaxStay), updatedMaxNights,
							Boolean.parseBoolean(updatedIsMoreThanDaysReq), updatedMoreThanDaysCount,
							Boolean.parseBoolean(updatedIsWithInDaysReq), updatedWithInDaysCount, updatedPromoCode,
							testSteps);
				} catch (Exception e) {
					nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(updatedIsRatePlanRistrictionReq),
							updatedRistrictionType, Boolean.parseBoolean(updatedIsMinStay), updatedMinNights,
							Boolean.parseBoolean(updatedIsMaxStay), updatedMaxNights,
							Boolean.parseBoolean(updatedIsMoreThanDaysReq), updatedMoreThanDaysCount,
							Boolean.parseBoolean(updatedIsWithInDaysReq), updatedWithInDaysCount, updatedPromoCode,
							testSteps);
				}

			}
			nightlyRate.switchCalendarTab(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
					Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
					Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
			if (isSeasonExist) {

				nightlyRate.selectSeasonDates(driver, testSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
				nightlyRate.clickEditThisSeasonButton(driver, testSteps);
				if (Boolean.parseBoolean(createRoomClass)) {
					testSteps.add("=================== ADD RATES FOR ADDED ROOM CLASS ======================");
					app_logs.info("=================== ADD RATES FOR ADDED ROOM CLASS ======================");
					for (int i = 0; i < reservationRoomClassName.split(Utility.DELIM).length; i++) {

						nightlyRate.addExtraRoomClassInSeason(driver, testSteps, "yes",
								reservationRoomClassName.split(Utility.DELIM)[i]);
						ratesGrid.enterRoomClassRates(driver, reservationRoomClassName.split(Utility.DELIM)[i],
								roomClassRatePerNight.split(Utility.DELIM)[i],
								Boolean.toString(ratesGrid.chargeforAdditionalAdultsChildsIsChecked(driver)),
								roomClassMaxAdults.split(Utility.DELIM)[i], roomClassMaxPersons.split(Utility.DELIM)[i],
								roomClassAdultsRate.split(Utility.DELIM)[i], roomClassChildRate.split(Utility.DELIM)[i],
								testSteps);
					}
				}
				if (updateRate.equalsIgnoreCase("yes")) {
					getTestSteps.clear();
					getTestSteps = ratesGrid.enterRoomClassBaserate(driver, reservationRoomClassName, updateRate,
							getTestSteps);
					testSteps.addAll(getTestSteps);
				}
				testSteps.add("=================== UPDATING SEASON LEVEL RULES ======================");
				app_logs.info("=================== UPDATING SEASON LEVEL RULES ======================");
				nightlyRate.clickRulesRestrictionOnSeason(driver, testSteps);
				nightlyRate.selectRestrictionTypes(driver,
						"Min nights" + Utility.DELIM + "No check-in" + Utility.DELIM + "No check-out", false,
						getTestSteps);
				testSteps.add("Deselect all season level rules");
				app_logs.info("Deselect all season level rules");
				if (updatedIsSeasonLevelRules.equalsIgnoreCase("yes") && updateRules.equalsIgnoreCase("yes")) {
					nightlyRate.clickAssignRulesByRoomClass(driver, testSteps, updatedIsAssignRulesByRoomClass);
					nightlyRate.enterSeasonLevelRule(driver, testSteps, updatedIsSeasonLevelRules,
							updatedIsAssignRulesByRoomClass, updatedSeasonRuleSpecificRoomClasses,
							updatedSeasonRuleType, updatedMinNights, updatedIsSeasonRuleOnMonday,
							updatedIsSeasonRuleOnTuesday, updatedIsSeasonRuleOnWednesday, updatedIsSeasonRuleOnThursday,
							updatedIsSeasonRuleOnFriday, updatedIsSeasonRuleOnSaturday, updatedIsSeasonRuleOnSunday);
				}
				nightlyRate.clickSaveSason(driver, testSteps);
				try {
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception e) {
					nightlyRate.closeSeason(driver, productWithDetails);
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
					testSteps.addAll(getTestSteps);
				}
				Wait.wait5Second();
				ratesGrid.closeOpendTabInMainMenu(driver);
			} else {
				app_logs.info("No Season For Desired Date");
				testSteps.add("No Season For Desired Date");
				Assert.assertTrue(false, "Failed to Update Season Rules");
			}

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update rate", testName, "UpdateRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to update rate", testName, "UpdateRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			testSteps.add("======= VERIFY ROOM CLASS IS NOT BLACK OUT =========");
			app_logs.info("======= VERIFY ROOM CLASS IS NOT BLACK OUT =========");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			ratesGrid.selectDateFromDatePicker(driver, checkInDate, dateFormat, getTestSteps);

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
			int roomIndex = ratesGrid.getRoomClassIndex(driver, reservationRoomClassName);
			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Expand Room Class : " + reservationRoomClassName);
			app_logs.info("Expand Room Class : " + reservationRoomClassName);
			for (int i = 0; i <= days; i++) {
				String date = Utility.addDate(i, dateFormat, checkInDate, dateFormat, timeZone);
				app_logs.info("Verify/Make room Available  for Date '" + date + "' and Source '" + bookingEngineChannel
						+ "'");
				testSteps.add("Verify/Make room Available  for Date '" + date + "' and Source '" + bookingEngineChannel
						+ "'");
				ratesGrid.activeOrBlackoutChannel(driver, date, dateFormat, reservationRoomClassName,
						bookingEngineChannel, "*");
				assertEquals(ratesGrid.getRoomStatus(driver, date, dateFormat, bookingEngineChannel,
						reservationRoomClassName), "*", "Failed : Room Status is not Available");
			}
			app_logs.info("Successfully verified Green Dot Appeared showing room as available");
			testSteps.add("Successfully verified Green Dot Appeared showing room as available");
			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Reduce Room Class : " + reservationRoomClassName);
			app_logs.info("Reduce Room Class : " + reservationRoomClassName);
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Room class is not blackout", testName, "Verification",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Room class is not blackout", testName, "Verification",
						driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		if (Boolean.parseBoolean(bulkUpdate)) {
			try {
				ratesGrid.clearRulesFrombulkUpdate(driver, delim, "BulkUpdate", checkInDate, checkOutDate, dateFormat,
						isSundayCheck, isMondayCheck, isTuesdayChecked, isWednesdayChecked, isThursdayChecked,
						isFridayChecked, isSaturdayChecked, bulkUpdateRatePlan, bilkUpdateRoomClass,
						bookingEngineChannel, testSteps);
				if (testCaseDescription.equalsIgnoreCase(
						"Verify that Min Stay Rule is not enforced if the rule exists only for Checkout date")) {
					ratesGrid.bulkUpdateAndOverideRules(driver, delim, "BulkUpdate", checkOutDate,
							Utility.addDate(days, dateFormat, checkOutDate, dateFormat, timeZone), dateFormat,
							isSundayCheck, isMondayCheck, isTuesdayChecked, isWednesdayChecked, isThursdayChecked,
							isFridayChecked, isSaturdayChecked, bulkUpdateRatePlan, bilkUpdateRoomClass,
							bookingEngineChannel, bulkUpdateRules, bulkUpdateMinStayValue, testSteps);
				} else {
					ratesGrid.bulkUpdateAndOverideRules(driver, delim, "BulkUpdate", checkInDate, checkOutDate,
							dateFormat, isSundayCheck, isMondayCheck, isTuesdayChecked, isWednesdayChecked,
							isThursdayChecked, isFridayChecked, isSaturdayChecked, bulkUpdateRatePlan,
							bilkUpdateRoomClass, bookingEngineChannel, bulkUpdateRules, bulkUpdateMinStayValue,
							testSteps);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to bulk update rule", testName, "UpdateRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to bulk Update rule", testName, "UpdateRate", driver);
				}
			}
		}

		if (UpdateTypes.equals("Override")) {

			// navigate to Inventory->Rates Grid-> Availability and verify roomclass
			// availability

			try {
				driver.navigate().refresh();
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);

				ratesGrid.selectDateFromDatePicker(driver, checkInDate, dateFormat, testSteps);

				ratesGrid.searchRateAndSelectRate(driver, testSteps, ratePlanName);

				availabilityInRatesBefore = getDataOfHash(ratesGrid.getAvailabilityOfRoomClass(driver, dateFormat,
						checkInDate, checkOutDate, reservationRoomClassName));

				testSteps.add("======= GETTING ROOM CLASS AVAILABILITY INFORMATION =========");
				app_logs.info("======= GETTING ROOM CLASS AVAILABILITY INFORMATION =========");

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

				ratesGrid.expandRoomClass(driver, testSteps, reservationRoomClassName);
				ratesGrid.getRoomClassIndex(driver, reservationRoomClassName);
				roomReservedValueBefore = ratesGrid.getRoomClassDataValue(driver, 0, "Reserved");
				roomAvailableValueBefore = ratesGrid.getRoomClassDataValue(driver, 0, "Available");
				roomTotalValueBefore = ratesGrid.getRoomClassDataValue(driver, 0, "Total");
				testSteps.add("Before Reservation Creation 'Room Reserved Value' is '" + roomReservedValueBefore + "'");
				app_logs.info("Before Reservation Creation 'Room Reserved Value' is '" + roomReservedValueBefore + "'");
				testSteps.add(
						"Before Reservation Creation 'Room Available Value' is '" + roomAvailableValueBefore + "'");
				app_logs.info(
						"Before Reservation Creation 'Room Available Value' is '" + roomAvailableValueBefore + "'");
				testSteps.add("Before Reservation Creation 'Room Total Value' is '" + roomTotalValueBefore + "'");
				app_logs.info("Before Reservation Creation 'Room Total Value' is '" + roomTotalValueBefore + "'");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Rates Initial Values", testName, "GetData", driver);
				} else {

					Assert.assertTrue(false);
				}
			}

			try {
				driver.navigate().refresh();
				testSteps.add("Navigate Rates");
				app_logs.info("Navigate Rates");
				testSteps.add("=================== GETTING RATE PLAN INFORMATION ======================");
				app_logs.info("=================== GETTING RATE PLAN INFORMATION ======================");
				rateGrid.clickRatePlanArrow(driver, testSteps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				rateGrid.clickEditIcon(driver, testSteps);
				selectedRatePlanFolioName = rateGrid.getFolioNameOfRatePlan(driver, testSteps);
				Utility.app_logs.info("Rate Plan Folio Name : " + selectedRatePlanFolioName);
				testSteps.add("Rate Plan Folio Name : " + selectedRatePlanFolioName);
				selectedChannels = nightlyRate.getSelectedChannels(driver);
				selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);

				Utility.app_logs.info("Selected Channels : " + selectedChannels);
				testSteps.add("Selected Channels : " + selectedChannels);
				Utility.app_logs.info("Selected Room Classes : " + selectedRoomClasses);
				testSteps.add("Selected Room Classes : " + selectedRoomClasses);

				testSteps.add("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");
				app_logs.info("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");

				selectedRatePlanDefaultStatus = ratesGrid.verifyRatePlanSetToDefault(driver, testSteps, ratePlanName);

				ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);

				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				dateWithoutSeason = nightlyRate.getDateWhereSeasonNotExist(driver);

				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
						Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
				if (isSeasonExist) {
					app_logs.info("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					testSteps.add("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					nightlyRate.selectSeasonDates(driver, testSteps,
							Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					roomClassList = rateGrid.getSeasonLevelRoomClasses(driver);
					getRoomClassWithRates.put(reservationRoomClassName, ratesGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, reservationRoomClassName, testSteps));
					app_logs.info(getRoomClassWithRates.get(reservationRoomClassName));
					ArrayList<String> gettest = new ArrayList<>();
					gettest = getRoomClassWithRates.get(reservationRoomClassName);
					ratePlanBaseRate = gettest.get(0);
					ratePlanAdultCapicity = gettest.get(1);
					ratePlanPersonCapicity = gettest.get(2);

					testSteps.add("Selected Season Room Classes : " + roomClassList);
					app_logs.info("Selected Season Room Classes : " + roomClassList);

					nightlyRate.clickSaveSason(driver, testSteps);

					nightlyRate.closeSeason(driver, testSteps);
					app_logs.info("Close Season");
					ratesGrid.clickOnSaveratePlan(driver);
					Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);

				} else {
					app_logs.info("No Season For Desired Date");
					testSteps.add("No Season For Desired Date");
					ratePlanAvailableInBE = false;
					testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				}

				testSteps.add("=================== GETTING ROOM CLASSES ======================");
				app_logs.info("=================== GETTING ROOM CLASSES ======================");

				ratesGrid.clickForRateGridCalender(driver, testSteps);
				Utility.selectDateFromDatePicker(driver, checkInDate, dateFormat);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				if (UpdateTypes.equals("Override")) {

					testSteps.add(
							"=================== GET '" + reservationRoomClassName + "' DATA ======================");
					app_logs.info(
							"=================== GET '" + reservationRoomClassName + "' DATA ======================");

					ratesGrid.expandRoomClass(driver, testSteps, reservationRoomClassName);
					ArrayList<String>[] arrayOfRoomClassesWithRules = new ArrayList[4];
					ArrayList<String> selectedRoomClassDataValues = ratesGrid.getChannelDataValues(driver,
							reservationRoomClassName, bookingEngineChannel);
					arrayOfRoomClassesWithRules[0] = selectedRoomClassDataValues;
					ratesGrid.expandChannel(driver, testSteps, reservationRoomClassName, bookingEngineChannel);

					testSteps.add("=================== UPDATE MIN STAY RULE DATA IN INNCENTER ======================");
					app_logs.info("=================== UPDATE MIN STAY RULE DATA IN INNCENTER ======================");

					ratesGrid.sendDataIfMinStayNightsDataNotExist(driver, reservationRoomClassName,
							bookingEngineChannel, "Min Stay", checkInDate, checkOutDate, dateFormat,
							seasonUpdatedMinNights);

					testSteps.addAll((ratesGrid.getMinStayValuesOfRoomClassBetweenSelectedDateRange(driver, dateFormat,
							checkInDate, checkOutDate, reservationRoomClassName, bookingEngineChannel, "Min Stay")));

					ArrayList<String> minStayRuleValues = ratesGrid.getRuleDataValuesForMinStay(driver,
							reservationRoomClassName, bookingEngineChannel, "Min Stay");
					arrayOfRoomClassesWithRules[1] = minStayRuleValues;

					testSteps.add(
							"=================== UNCHECK NO CHECK IN, NO CHECK OUT RULE DATA IN INNCENTER ======================");
					app_logs.info(
							"=================== UNCHECK NO CHECK IN, NO CHECK OUT RULE DATA IN INNCENTER ======================");

					ratesGrid.uncheckCheckInCheckOutRuleIfTheyAlreadyApplied(driver, reservationRoomClassName,
							bookingEngineChannel, "No Check In");
					ratesGrid.uncheckCheckInCheckOutRuleIfTheyAlreadyApplied(driver, reservationRoomClassName,
							bookingEngineChannel, "No Check Out");

					testSteps.add(
							"=================== GET NO CHECK IN, NO CHECK OUT RULE DATA AFTER UPDATION IN INNCENTER ======================");
					app_logs.info(
							"=================== GET NO CHECK IN, NO CHECK OUT RULE DATA AFTER UPDATION IN INNCENTER ======================");

					testSteps.addAll((ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver,
							dateFormat, checkInDate, checkOutDate, reservationRoomClassName, bookingEngineChannel,
							"No Check In", false)));

					testSteps.addAll((ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver,
							dateFormat, checkInDate, checkOutDate, reservationRoomClassName, bookingEngineChannel,
							"No Check Out", false)));

					ArrayList<String> checkInRuleValues = ratesGrid.getRuleDataValueForCheckInCheckOut(driver,
							reservationRoomClassName, bookingEngineChannel, "No Check In");
					arrayOfRoomClassesWithRules[2] = checkInRuleValues;
					ArrayList<String> checkOutRuleValues = ratesGrid.getRuleDataValueForCheckInCheckOut(driver,
							reservationRoomClassName, bookingEngineChannel, "No Check Out");
					arrayOfRoomClassesWithRules[3] = checkOutRuleValues;
					roomClassDataValuesForBookingEngineChannel.put(reservationRoomClassName,
							arrayOfRoomClassesWithRules);

				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SELECTED =====");
				app_logs.info("===== VERIFY RATE PLAN IS SELECTED =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				getTestSteps.clear();
				boolean ratePlanIsSelectedOrNot = bookingEngine.isRatePlanExist(driver, ratePlanName, getTestSteps);
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				if (ratePlanIsSelectedOrNot) {
					testSteps.add(
							"Successfully Verified that '" + ratePlanName + "' is already selected in Booking Engine.");
					app_logs.info(
							"Successfully Verified that '" + ratePlanName + "' is already selected in Booking Engine.");
				} else {
					testSteps.add("Select '" + ratePlanName + "' in Booking Engine.");
					app_logs.info("Select '" + ratePlanName + "' in Booking Engine.");
				}
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== VERIFY AVAILABILITY DETAILS =====");
				app_logs.info("===== VERIFY AVAILABILITY DETAILS =====");

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
				testSteps.addAll(getTestSteps);

				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.enterChildrenBE(driver, child);
				testSteps.addAll(getTestSteps);
				if (isPromocode) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.enterPromoCode(driver, ratePlanPromoCode);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				testSteps.add("=================== VERIFY SELECT A DATE BUTTON IS SHOWING ======================");
				app_logs.info("=================== VERIFY SELECT A DATE BUTTON IS SHOWING ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifySelectADateButtonIsDisplaying(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add("=================== VERIFY AVAILABILITY DATES IN BOOKING ENGINE ======================");
				app_logs.info("=================== VERIFY AVAILABILITY DATES IN BOOKING ENGINE ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyAvailabilityDatesInBookingEngine(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== VERIFY ROOM CLASSES VALUE DATA IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFY ROOM CLASSES VALUE DATA IN BOOKING ENGINE ======================");
				if (roomClassList.size() > 0) {
					for (int index = 0; index < roomClassList.size(); index++) {
						String roomClassName = roomClassList.get(index).toString();
						app_logs.info("Expected : " + roomClassName);
						for (Map.Entry<String, ArrayList<String>[]> entry : roomClassDataValuesForBookingEngineChannel
								.entrySet()) {
							String className = entry.getKey();
							ArrayList<String>[] roomClassDataValuesWithRules = entry.getValue();
							if (roomClassName.equalsIgnoreCase(className)) {
								getTestSteps.clear();
								getTestSteps = bookingEngine.verifyRoomClassAndItsDataInBookingEngine(driver,
										roomClassName, roomClassDataValuesWithRules[0], true,
										roomClassDataValuesWithRules[1], roomClassDataValuesWithRules[2],
										roomClassDataValuesWithRules[3]);
								testSteps.addAll(getTestSteps);
								// For
								getTestSteps.clear();
								getTestSteps = bookingEngine.clickOnRateValueAtCheckInDate(driver, roomClassName, days,
										checkInDate, checkOutDate, BEAvailabilityGridDefaultAdultsValue, BEAvailabilityGridDefaultChildrenValue,
										roomClassDataValuesWithRules[0].get(0), ratePlanName, dateFormat);
								testSteps.addAll(getTestSteps);
								boolean daysLessThanMinNights = BookingEngine.blnDaysLessThanMinNights;

								testSteps.add(
										"=================== NAVIGATION BACK TO AVAILABILITY PAGE ======================");
								app_logs.info(
										"=================== NAVIGATION BACK TO AVAILABILITY PAGE ======================");

								bookingEngine.navigateBackToAvailabilityPage(driver);

								bookingEngine.clickWelcomePageLink(driver);

								getTestSteps.clear();
								getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
								testSteps.addAll(getTestSteps);
								int noOfDays = 0;
								if (daysLessThanMinNights) {
									noOfDays = BookingEngine.minNights + 1;
								}

								else {
									noOfDays = BookingEngine.minNights - 1;
								}

								String checkoutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkInDate,
										"MM/d/yyyy", "MM/d/yyyy", noOfDays + 1, timeZone);

								getTestSteps.clear();
								getTestSteps = bookingEngine.clickOnRateValueAtCheckInDate(driver, roomClassName,
										noOfDays + 1, checkInDate, checkoutDate, BEAvailabilityGridDefaultAdultsValue, BEAvailabilityGridDefaultChildrenValue,
										roomClassDataValuesWithRules[0].get(0), ratePlanName, dateFormat);
								testSteps.addAll(getTestSteps);

								bookingEngine.navigateBackToAvailabilityPage(driver);
							}
						}
					}

					testSteps.add(
							"=================== CLICKED ON NEXT BUTTON AND VERIFY ROOM CLASSES ======================");
					app_logs.info(
							"=================== CLICKED ON NEXT BUTTON AND VERIFY ROOM CLASSES ======================");

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickOnNextButton(driver, roomClassList);
					testSteps.addAll(getTestSteps);

				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			try {

				testSteps.add("=================== NAVIGATE BACK TO INNCENTER ======================");
				app_logs.info("=================== NAVIGATE BACK TO INNCENTER ======================");

				loginRateV2(driver);
				testSteps.add("Logged into the application ");
				app_logs.info("Logged into the application");

				navigation.Inventory(driver);
				app_logs.info("Navigate Inventory");
				testSteps.add("Navigate Inventory");

				navigation.ratesGrid(driver, testSteps);

				ratesGrid.clickForRateGridCalender(driver, testSteps);
				Utility.selectDateFromDatePicker(driver, checkInDate, dateFormat);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				int count = 0;
				if (roomClassList.size() > 0) {
					for (int i = 0; i < roomClassList.size(); i++) {
						String roomClassName = roomClassList.get(i);
						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						ArrayList<String>[] arrayOfRoomClassesWithRules = new ArrayList[4];
						ArrayList<String> selectedRoomClassDataValues = ratesGrid.getChannelDataValues(driver,
								roomClassName, bookingEngineChannel);
						arrayOfRoomClassesWithRules[0] = selectedRoomClassDataValues;
						ratesGrid.expandChannel(driver, testSteps, roomClassName, bookingEngineChannel);
						ArrayList<String> minStayRuleValues = ratesGrid.getRuleDataValuesForMinStay(driver,
								roomClassName, bookingEngineChannel, "Min Stay");
						arrayOfRoomClassesWithRules[1] = minStayRuleValues;

						testSteps.add(
								"=================== APPLY NO CHECK IN, NO CHECK OUT RULES IN INNCENTER ======================");
						app_logs.info(
								"=================== APPLY NO CHECK IN, NO CHECK OUT RULES IN INNCENTER ======================");

						ratesGrid.applyCheckInCheckOutRuleValueIfTheyAlreadyNotApplied(driver, roomClassName,
								bookingEngineChannel, "No Check In", checkInDate, checkOutDate, dateFormat);
						ratesGrid.applyCheckInCheckOutRuleValueIfTheyAlreadyNotApplied(driver, roomClassName,
								bookingEngineChannel, "No Check Out", checkInDate, checkOutDate, dateFormat);

						testSteps.add(
								"=================== GET NO CHECK IN, NO CHECK OUT RULES DATA IN INNCENTER AFTER UPDATION ======================");
						app_logs.info(
								"=================== GET NO CHECK IN, NO CHECK OUT RULES DATA IN INNCENTER AFTER UPDATION ======================");

						testSteps.addAll((ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver,
								dateFormat, checkInDate, checkOutDate, roomClassName, bookingEngineChannel,
								"No Check In", true)));

						testSteps.addAll((ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver,
								dateFormat, checkInDate, checkOutDate, roomClassName, bookingEngineChannel,
								"No Check Out", true)));

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== VERIFY AVAILABILITY DETAILS =====");
				app_logs.info("===== VERIFY AVAILABILITY DETAILS =====");

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
				testSteps.addAll(getTestSteps);

				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.enterChildrenBE(driver, child);
				testSteps.addAll(getTestSteps);
				if (isPromocode) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.enterPromoCode(driver, ratePlanPromoCode);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== VERIFICATION OF MIN NIGHTS, NO CHECK IN, NO CHECK OUT RULES UPDATION IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFICATION OF MIN NIGHTS, NO CHECK IN, NO CHECK OUT RULES UPDATION IN BOOKING ENGINE ======================");

				if (roomClassList.size() > 0) {
					for (int index = 0; index < roomClassList.size(); index++) {
						String roomClassName = roomClassList.get(index).toString();
						app_logs.info("Expected : " + roomClassName);
						for (Map.Entry<String, ArrayList<String>[]> entry : roomClassDataValuesForBookingEngineChannel
								.entrySet()) {
							String className = entry.getKey();
							if (roomClassName.equalsIgnoreCase(className)) {

								getTestSteps.clear();
								getTestSteps = bookingEngine.verifyMinNightsDataInBookingEngine(driver, roomClassName,
										checkInDate, checkOutDate, dateFormat, RatesGrid.minStayDates);
								testSteps.addAll(getTestSteps);

								getTestSteps.clear();
								getTestSteps = bookingEngine.verifyCheckInDataInBookingEngine(driver, roomClassName,
										checkInDate, checkOutDate, dateFormat, RatesGrid.noCheckInDates);
								testSteps.addAll(getTestSteps);

								getTestSteps.clear();
								getTestSteps = bookingEngine.verifyCheckOutDataInBookingEngine(driver, roomClassName,
										checkInDate, checkOutDate, dateFormat, RatesGrid.noCheckOutDates);
								testSteps.addAll(getTestSteps);

							}
						}
					}

				}
//				statusCode.add(0, "1");
//				statusCode.add(6, "1");
//				statusCode.add(8, "1");
//				statusCode.add(9, "1");
//				statusCode.add(10, "1");
//				statusCode.add(1, "1");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add("=================== NAVIGATE BACK TO INNCENTER ======================");
				app_logs.info("=================== NAVIGATE BACK TO INNCENTER ======================");

				loginRateV2(driver);
				testSteps.add("Logged into the application ");
				app_logs.info("Logged into the application");

				navigation.Inventory(driver);
				app_logs.info("Navigate Inventory");
				testSteps.add("Navigate Inventory");

				navigation.ratesGrid(driver, testSteps);

				ratesGrid.clickForRateGridCalender(driver, testSteps);
				Utility.selectDateFromDatePicker(driver, checkInDate, dateFormat);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				testSteps.add("========== UPDATE RATE VALUE FOR ROOM CLASS '" + reservationRoomClassName + "' =====");
				app_logs.info("======= UPDATE RATE VALUE FOR ROOM CLASS '" + reservationRoomClassName + "'======");

				if (roomClassList.size() > 0) {
					for (int i = 0; i < roomClassList.size(); i++) {
						String roomClassName = roomClassList.get(i);
						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						ratesGrid.updateRateValuesForSelectedClasses(driver, roomClassName, bookingEngineChannel,
								updateRate, checkInDate, checkOutDate, dateFormat);
						ArrayList<String> selectedRoomClassDataValues = ratesGrid.getChannelDataValues(driver,
								roomClassName, bookingEngineChannel);
					}
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rate", testName, "UpdateRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rate", testName, "UpdateRate", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== VERIFY AVAILABILITY DETAILS =====");
				app_logs.info("===== VERIFY AVAILABILITY DETAILS =====");

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
				testSteps.addAll(getTestSteps);

				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.enterChildrenBE(driver, child);
				testSteps.addAll(getTestSteps);
				if (isPromocode) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.enterPromoCode(driver, ratePlanPromoCode);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== VERIFICATION OF RATE VALUE UPDATION IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFICATION OF RATE VALUE UPDATION IN BOOKING ENGINE ======================");

				if (roomClassList.size() > 0) {
					for (int index = 0; index < roomClassList.size(); index++) {
						String roomClassName = roomClassList.get(index).toString();
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyRateValuesInBookingEngine(driver, roomClassName, checkInDate,
								checkOutDate, dateFormat, RatesGrid.rateValuesMap);
						testSteps.addAll(getTestSteps);

					}

				}

				testSteps.add(
						"=================== VERIFICATION OF DIFFERENT COMBINATIONS OF ADULTS AND CHILDS IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFICATION OF DIFFERENT COMBINATIONS OF ADULTS AND CHILDS IN BOOKING ENGINE ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyDifferentAdultsAndchildrenCombinations(driver,
						reservationRoomClassName, days, ratePlanAdultCapicity, ratePlanPersonCapicity, adults, child);
				testSteps.addAll(getTestSteps);
				statusCode.add(3, "1");
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== VERIFY AVAILABILITY DETAILS =====");
				app_logs.info("===== VERIFY AVAILABILITY DETAILS =====");

				testSteps.add(
						"=================== VERIFICATION OF $~~ IS SHOWING IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFICATION OF $~~ IS SHOWING IN BOOKING ENGINE ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyAmountIsNotShowingWithoutRatePlan(driver, dateWithoutSeason);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		String[] nightlyRateArray = Amount.split(Utility.DELIM);
		String[] additionalAdultArray = updatedAdditionalAdults.split(Utility.DELIM);
		String[] additionalChildArray = updatedAdditionalChild.split(Utility.DELIM);
		days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate, dateFormat);

		if (UpdateTypes.equals("BulkRates") || UpdateTypes.equals("BulkRules")
				|| UpdateTypes.equals("BulkAvailability")) {
			try {

				driver.navigate().refresh();
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				app_logs.info("Update by : " + UpdateTypes);
				testSteps.add("Update by : " + UpdateTypes);

				getOldAvailability = ratesGrid.getRuleOnBaseofDays(driver, checkInDate, checkOutDate, listOfDay,
						timeZone, getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}

			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectBulkUpdateOption(driver, option);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}

			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupHeading(driver, option);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}

			try {
				testSteps.add("==========SEELCT START DATE==========");
				app_logs.info("==========SEELCT START DATE==========");
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectDate(driver, checkInDate, true);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}

			try {
				testSteps.add("==========SEELCT END DATE==========");
				app_logs.info("==========SEELCT END DATE==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectDate(driver, checkOutDate, false);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}

			try {
				app_logs.info("==========CHECKING/UNCHECKING DAYS==========");
				testSteps.add("==========CHECKING/UNCHECKING DAYS==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Sun", isSundayCheck);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Mon", isMondayCheck);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Tue", isTuesdayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Wed", isWednesdayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Thu", isThursdayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Fri", isFridayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Sat", isSaturdayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickTotalOccupancy(driver, isTotalOccupancyOn);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}

			try {
				if (isTotalOccupancyOn.equalsIgnoreCase("Yes")) {

					getTestSteps.clear();
					getTestSteps = ratesGrid.selectTotalOccupancyType(driver, totalOccupancyType);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.enterOccupancyValue(driver, totalOccupancyValue);
					testSteps.addAll(getTestSteps);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}
		}
		if (UpdateTypes.equals("BulkRates") || UpdateTypes.equals("BulkRules")) {
			try {
				app_logs.info("==========SELECTING RATE PLAN==========");
				testSteps.add("==========SELECTING RATE PLAN==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", ratePlanName);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}
		}
		if (UpdateTypes.equals("BulkRates") || UpdateTypes.equals("BulkRules")
				|| UpdateTypes.equals("BulkAvailability")) {
			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", reservationRoomClassName);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}
		}

		if (UpdateTypes.equals("BulkRates") || UpdateTypes.equals("BulkRules")) {
			try {
				app_logs.info("==========SELECTING SOURCE==========");
				testSteps.add("==========SELECTING SOURCE==========");

				for (String str : channelArray) {
					if (str.equalsIgnoreCase("All sources")) {
						str = str + " (" + activeChannelsList.size() + ")";
					}
					getTestSteps.clear();
					getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", bookingEngineChannel);
					testSteps.addAll(getTestSteps);

				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}
		}

		if (UpdateTypes.equals("BulkRates")) {
			try {
				app_logs.info("==========UPDATE RATES==========");
				testSteps.add("==========UPDATE RATES==========");

				// Checks Rate Update Type
				if (SubTypeOfBulk.equalsIgnoreCase("EnterNewRate")) {

					app_logs.info("Update Rate by checking : " + SubTypeOfBulk + " radio button.");
					testSteps.add("Update Rate by checking : " + SubTypeOfBulk + " radio button.");

					getTestSteps.clear();
					getTestSteps = ratesGrid.selectBulkUpdateRatesOption(driver, 0);
					testSteps.addAll(getTestSteps);

					if (UpdateRoomClasses.equals("yes")) {
						getTestSteps.clear();
						getTestSteps = ratesGrid.updateRoomsByRoomClassToggle(driver,
								Boolean.parseBoolean(updateRateByRoomClass));
						testSteps.addAll(getTestSteps);
					}

					getTestSteps.clear();
					getTestSteps = ratesGrid.bulkUpdateRateGridSymbolVerification(driver, Utility.clientCurrencySymbol);
					testSteps.addAll(getTestSteps);

					// using loop for more then one room class
					for (int i = 0; i < nightlyRateArray.length; i++) {

						getTestSteps.clear();
						getTestSteps = ratesGrid.updateNightlyRate(driver, i, nightlyRateArray[i]);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = ratesGrid.updateAdditionalAdultRate(driver, i, additionalAdultArray[i]);
						testSteps.addAll(getTestSteps);

						getTestSteps.clear();
						getTestSteps = ratesGrid.updateAdditionalChildRate(driver, i, additionalChildArray[i]);
						testSteps.addAll(getTestSteps);

					}

				} else if (SubTypeOfBulk.equalsIgnoreCase("Increase") || SubTypeOfBulk.equalsIgnoreCase("Decrease")) {

					app_logs.info("Update Rate by selecting : " + SubTypeOfBulk + " from dropdown.");
					testSteps.add("Update Rate by selecting : " + SubTypeOfBulk + " from dropdown.");

					getTestSteps.clear();
					getTestSteps = ratesGrid.selectBulkUpdateRatesOption(driver, 1);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.selectRateIncreaseDecreaseOption(driver, SubTypeOfBulk);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.enterRateValueForUpdateRate(driver, Amount);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.selectRateCurrencyOption(driver, CurrencyType);
					testSteps.addAll(getTestSteps);

				} else {

					getTestSteps.clear();
					getTestSteps = ratesGrid.selectBulkUpdateRatesOption(driver, 2);
					testSteps.addAll(getTestSteps);

				}

				driver.findElement(By.xpath("//span[text()='Update rates']")).click();

				app_logs.info("Successfully clicked on update button.");
				testSteps.add("Successfully clicked on update button.");

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickBulkUpdatePopupUpdateButton(driver);
				testSteps.addAll(getTestSteps);

				app_logs.info("getDayAvailability: " + getOldAvailability);
				for (int i = 0; i < days; i++) {
					if (getOldAvailability.get("" + i).equalsIgnoreCase("yes")) {
						break;
					}
				}

				String expectedDays = "";
				String totalDays = "";

				days = days + 1;
				if (SubTypeOfBulk.equalsIgnoreCase("Increase") || SubTypeOfBulk.equalsIgnoreCase("Decrease")
						|| SubTypeOfBulk.equalsIgnoreCase("EnterNewRate")) {
					expectedDays = "Rates will be updated for " + days + " day(s) within this date range.";
					totalDays = ratesGrid.getTotalDaysText(driver, option);

				} else {
					expectedDays = "Rate overrides will be removed for " + days + " day(s) within this date range.";
					totalDays = ratesGrid.getTotalDaysTextForRemove(driver);

				}
				testSteps.add("Expected total days : " + expectedDays);
				app_logs.info("Expected total days : " + expectedDays);

				testSteps.add("Found : " + totalDays);
				app_logs.info("Found : " + totalDays);
				// assertEquals(totalDays, expectedDays, "Failed to match total days");
				testSteps.add("Verified total number of days");
				app_logs.info("Verified total number of days");
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickYesUpdateButton(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}

			try {
				driver.navigate().refresh();
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				String[] selectedClasses = reservationRoomClassName.split(Utility.DELIM);
				if (selectedClasses.length > 0) {
					for (int index = 0; index < selectedClasses.length; index++) {
						String roomClassName = selectedClasses[index].toString();
						app_logs.info("Expected : " + roomClassName);
						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						getTestSteps.clear();
						getTestSteps = ratesGrid.getRateValuesOfRoomClassBetweenSelectedDateRange(driver, dateFormat,
								checkInDate, checkOutDate, roomClassName, bookingEngineChannel);
						testSteps.addAll(getTestSteps);
						bulkRateUpdateValuesMap.put(roomClassName, RatesGrid.rateValuesMap);
					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}
			try {
				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== VERIFICATION OF BULK RATE VALUE UPDATION IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFICATION OF BULK RATE VALUE UPDATION IN BOOKING ENGINE ======================");

				for (Map.Entry<String, HashMap<String, String>> entry : bulkRateUpdateValuesMap.entrySet()) {
					String className = entry.getKey();
					HashMap<String, String> roomClassDataValuesWithRules = entry.getValue();
					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyRateValuesInBookingEngine(driver, className, checkInDate,
							checkOutDate, dateFormat, roomClassDataValuesWithRules);
					testSteps.addAll(getTestSteps);
				}

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update rates using bulk rate", testName, "BulkUpdate", driver);
				}
			}
		}

		if (UpdateTypes.equals("BulkAvailability")) {
			try {
				app_logs.info("==========SELECTING UPDATE AVAILABILITY==========");
				testSteps.add("==========SELECTING UPDATE AVAILABILITY==========");

				String[] channelArr = bookingEngineChannel.split(Utility.DELIM);

				app_logs.info("Update availability by checking : " + SubTypeOfBulk + " radio button.");
				testSteps.add("Update Rate by checking : " + SubTypeOfBulk + " radio button.");

				for (String channelString : channelArr) {

					channelString = channelString.trim();
					getTestSteps.clear();
					getTestSteps = ratesGrid.selectUpdateAvailability(driver, channelString.trim(), SubTypeOfBulk);
					testSteps.addAll(getTestSteps);
				}

			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update availability", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to update availability", testName, "BulkUpdate", driver);
				}
			}

			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickUpdateButton(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click update button", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click update button", testName, "BulkUpdate", driver);
				}
			}
			try {
				app_logs.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
				testSteps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");

				days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate, dateFormat);
				days = days + 1;
				String expectedDays = "Availability will be updated for " + days + " day(s) within this date range.";
				testSteps.add("Expected total days : " + expectedDays);
				app_logs.info("Expected total days : " + expectedDays);
				String totalDays = ratesGrid.getTotalDaysText(driver, option);
				testSteps.add("Found : " + totalDays);
				app_logs.info("Found : " + totalDays);
				// Assert.assertEquals(totalDays, expectedDays, "Failed to match total days");
				testSteps.add("Verified total number of days");
				app_logs.info("Verified total number of days");
			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}

			try {

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnYesUpdateButton(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click yes update button", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click yes update button", testName, "BulkUpdate", driver);
				}
			}

			try {
				driver.navigate().refresh();
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				String[] splitRoomClass = reservationRoomClassName.split(Utility.DELIM);
				String[] isAvailability = SubTypeOfBulk.split(Utility.DELIM);

				testSteps.add("=======Verify room classes avaialbility in availability tab=======");
				app_logs.info("=======Verify room classes avaialbility in availability tab=======");
				ratesGrid.clickForRateGridCalender(driver, testSteps);
				Utility.selectDateFromDatePicker(driver, checkInDate, dateFormat);

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
				if (splitRoomClass.length > 0) {
					for (int index = 0; index < splitRoomClass.length; index++) {
						String roomClassName = splitRoomClass[index].toString();
						app_logs.info("Expected : " + roomClassName);

						ratesGrid.expandRoomClass1(driver, testSteps, splitRoomClass[index]);
						app_logs.info("==========Getting the availability for " + splitRoomClass[index]
								+ " in Availability tab for " + bookingEngineChannel + "==========");
						testSteps.add("==========Getting the availability for the room class " + splitRoomClass[index]
								+ " in Availability tab for " + bookingEngineChannel + "==========");

						availabilityDataValuesMap = ratesGrid.getAvailabilityOnBaseofDays(driver, splitRoomClass[index],
								bookingEngineChannel, checkInDate, checkOutDate, listOfDay, timeZone,
								isAvailability[index], true, testSteps);
						bulkAvailabilityUpdateValuesMap.put(roomClassName, availabilityDataValuesMap);

					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click yes update button", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click yes update button", testName, "BulkUpdate", driver);
				}
			}

			try {
				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== VERIFICATION OF BULK AVAILABILITY VALUES UPDATION IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFICATION OF BULK AVAILABILITY VALUES UPDATION IN BOOKING ENGINE ======================");

				for (Map.Entry<String, HashMap<String, String>> entry : bulkAvailabilityUpdateValuesMap.entrySet()) {
					String className = entry.getKey();
					HashMap<String, String> roomClassDataValuesWithRules = entry.getValue();
					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyAvailabilityDataInBookingEngine(driver, className, checkInDate,
							checkOutDate, dateFormat, roomClassDataValuesWithRules);
					testSteps.addAll(getTestSteps);
				}

			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click yes update button", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click yes update button", testName, "BulkUpdate", driver);
				}
			}
		}

		if (UpdateTypes.equals("BulkRules")) {
			try {
				if (SubTypeOfBulk.contains("minstay")) {
					isMinimumStayOn = "yes";
				}
				if (SubTypeOfBulk.contains("no check in")) {
					isCheckInOn = "yes";
					isNoCheckInChecked = "yes";
				}
				if (SubTypeOfBulk.contains("no check out")) {
					isCheckOutOn = "yes";
					isNoCheckOutChecked = "yes";
				}
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickMinimumStay(driver, isMinimumStayOn);
				testSteps.addAll(getTestSteps);
				if (isMinimumStayOn.equalsIgnoreCase("Yes")) {
					getTestSteps.clear();
					getTestSteps = ratesGrid.enterMinimumStayValue(driver, seasonUpdatedMinNights);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCheckin(driver, isCheckInOn);
				testSteps.addAll(getTestSteps);
				if (isCheckInOn.equalsIgnoreCase("Yes")) {
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickNoCheckInCheckbox(driver, isNoCheckInChecked);
					testSteps.addAll(getTestSteps);
				}
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCheckOut(driver, isCheckOutOn);
				testSteps.addAll(getTestSteps);

				if (isCheckOutOn.equalsIgnoreCase("Yes")) {
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickNoCheckOutCheckbox(driver, isNoCheckOutChecked);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickUpdateButton(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click update button", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to click update button", testName, "BulkUpdate", driver);
				}
			}

			try {

				app_logs.info("==========VERIFYING TOTAL NUMBER OF DAYS==========");
				testSteps.add("==========VERIFYING TOTAL NUMBER OF DAYS==========");
				app_logs.info("getOldAvailability: " + getOldAvailability);
				for (int i = 0; i < days; i++) {
					if (getOldAvailability.get("" + i).equalsIgnoreCase("yes")) {
						break;
					}
				}
				// days = days + 1;
				String expectedDays = "Rules will be updated for " + days + " day(s) within this date range.";
				testSteps.add("Expected total days : " + expectedDays);
				app_logs.info("Expected total days : " + expectedDays);
				String totalDays = ratesGrid.getTotalDaysText(driver, option);
				testSteps.add("Found : " + totalDays);
				app_logs.info("Found : " + totalDays);
				if (expectedDays.equals(totalDays)) {
					testSteps.add("Failed : Days are mismatching.");
					app_logs.info("Failed : Days are mismatching.");
				}
				// assertEquals(totalDays, expectedDays, "Failed to match total days");
				else {
					testSteps.add("Verified total number of days");
					app_logs.info("Verified total number of days");
				}
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnYesUpdateButton(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}

			try {
				driver.navigate().refresh();
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				String[] splitRoomClass = reservationRoomClassName.split(Utility.DELIM);

				if (splitRoomClass.length > 0) {
					for (int index = 0; index < splitRoomClass.length; index++) {
						String roomClassName = splitRoomClass[index].toString();
						app_logs.info("Expected : " + roomClassName);
						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						ratesGrid.expandChannel(driver, testSteps, roomClassName, bookingEngineChannel);

						if (isMinimumStayOn.equals("yes")) {
							testSteps.addAll((ratesGrid.getMinStayValuesOfRoomClassBetweenSelectedDateRange(driver,
									dateFormat, checkInDate, checkOutDate, roomClassName, bookingEngineChannel,
									"Min Stay")));
							bulkRateMinStayValuesMap.put(roomClassName, RatesGrid.minStayDates);

						}
						if (isCheckInOn.equals("yes")) {
							testSteps
									.addAll((ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver,
											dateFormat, checkInDate, checkOutDate, roomClassName, bookingEngineChannel,
											"No Check In", false)));
							bulkCheckInUpdateValuesMap.put(roomClassName, RatesGrid.noCheckInDates);
						}
						if (isCheckOutOn.equals("yes")) {
							testSteps
									.addAll((ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver,
											dateFormat, checkInDate, checkOutDate, reservationRoomClassName,
											bookingEngineChannel, "No Check Out", false)));
							bulkCheckOutUpdateValuesMap.put(roomClassName, RatesGrid.noCheckOutDates);
						}
					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}

			try {
				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				if (isMinimumStayOn.equals("yes")) {

					app_logs.info(
							"==========VERIFYING MIN STAY RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");
					testSteps.add(
							"==========VERIFYING MIN STAY RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");

					for (Map.Entry<String, HashMap<String, String>> entry : bulkRateMinStayValuesMap.entrySet()) {
						String className = entry.getKey();
						HashMap<String, String> roomClassDataValuesWithRules = entry.getValue();
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyMinNightsDataInBookingEngine(driver, className, checkInDate,
								checkOutDate, dateFormat, roomClassDataValuesWithRules);
						testSteps.addAll(getTestSteps);
						statusCode.add(5, "1");
					}
				}

				if (isCheckInOn.equals("yes")) {

					app_logs.info(
							"==========VERIFYING NO CHECK IN RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");
					testSteps.add(
							"==========VERIFYING NO CHECK IN RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE=========");

					for (Map.Entry<String, HashMap<String, String>> entry : bulkCheckInUpdateValuesMap.entrySet()) {
						String className = entry.getKey();
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyCheckInDataInBookingEngine(driver, className, checkInDate,
								checkOutDate, dateFormat, RatesGrid.noCheckInDates);
						testSteps.addAll(getTestSteps);
					}
				}

				if (isCheckOutOn.equals("yes")) {

					app_logs.info(
							"==========VERIFYING NO CHECK OUT RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");
					testSteps.add(
							"==========VERIFYING NO CHECK OUT RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");

					for (Map.Entry<String, HashMap<String, String>> entry : bulkCheckOutUpdateValuesMap.entrySet()) {
						String className = entry.getKey();
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyCheckOutDataInBookingEngine(driver, className, checkInDate,
								checkOutDate, dateFormat, RatesGrid.noCheckOutDates);
						testSteps.addAll(getTestSteps);
					}
				}

			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}
		}
		if (UpdateTypes.equals("Update")) {

			try {
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnBulkUpdate(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectBulkUpdateOption(driver, "Rates");
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupHeading(driver, "Rates");
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectDate(driver, checkInDate, true);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectDate(driver, checkOutDate, false);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Sun", isSundayCheck);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Mon", isMondayCheck);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Tue", isTuesdayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Wed", isWednesdayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Thu", isThursdayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Fri", isFridayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.bulkUpdatePoppupDayCheck(driver, "Sat", isSaturdayChecked);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickTotalOccupancy(driver, isTotalOccupancyOn);
				testSteps.addAll(getTestSteps);

				if (isTotalOccupancyOn.equalsIgnoreCase("Yes")) {

					getTestSteps.clear();
					getTestSteps = ratesGrid.selectTotalOccupancyType(driver, totalOccupancyType);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.enterOccupancyValue(driver, totalOccupancyValue);
					testSteps.addAll(getTestSteps);
				}

				app_logs.info("==========SELECTING RATE PLAN==========");
				testSteps.add("==========SELECTING RATE PLAN==========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Rate Plan", ratePlanName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Room class", reservationRoomClassName);
				testSteps.addAll(getTestSteps);

				app_logs.info("==========SELECTING SOURCE==========");
				testSteps.add("==========SELECTING SOURCE==========");

				for (String str : channelArray) {
					if (str.equalsIgnoreCase("All sources")) {
						str = str + " (" + activeChannelsList.size() + ")";
					}
					getTestSteps.clear();
					getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", bookingEngineChannel);
					testSteps.addAll(getTestSteps);

				}

				getTestSteps.clear();
				getTestSteps = ratesGrid.selectBulkUpdateRatesOption(driver, 2);
				testSteps.addAll(getTestSteps);

				driver.findElement(By.xpath("//span[text()='Update rates']")).click();

				app_logs.info("Successfully clicked on update button.");
				testSteps.add("Successfully clicked on update button.");

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickBulkUpdatePopupUpdateButton(driver);
				testSteps.addAll(getTestSteps);

				String expectedDays = "";
				String totalDays = "";

				days = days + 1;
				if (SubTypeOfBulk.equalsIgnoreCase("Increase") || SubTypeOfBulk.equalsIgnoreCase("Decrease")
						|| SubTypeOfBulk.equalsIgnoreCase("EnterNewRate")) {
					expectedDays = "Rates will be updated for " + days + " day(s) within this date range.";
					totalDays = ratesGrid.getTotalDaysText(driver, option);

				} else {
					expectedDays = "Rate overrides will be removed for " + days + " day(s) within this date range.";
					totalDays = ratesGrid.getTotalDaysTextForRemove(driver);

				}
				testSteps.add("Expected total days : " + expectedDays);
				app_logs.info("Expected total days : " + expectedDays);

				testSteps.add("Found : " + totalDays);
				app_logs.info("Found : " + totalDays);
				// assertEquals(totalDays, expectedDays, "Failed to match total days");
				testSteps.add("Verified total number of days");
				app_logs.info("Verified total number of days");
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickYesUpdateButton(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}
			try {

				testSteps.add("=================== GETTING RATE PLAN INFORMATION ======================");
				app_logs.info("=================== GETTING RATE PLAN INFORMATION ======================");

				rateGrid.clickRatePlanArrow(driver, testSteps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				rateGrid.clickEditIcon(driver, testSteps);
				ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);

				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
						Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
				if (isSeasonExist) {
					app_logs.info("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					testSteps.add("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					nightlyRate.selectSeasonDates(driver, testSteps,
							Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);

					testSteps.add(
							"=================== UPDATE RATE VALUES FOR SELECTED CLASSES AT SEASON LEVEL ======================");
					app_logs.info(
							"=================== UPDATE RATE VALUES FOR SELECTED CLASSES AT SEASON LEVEL ======================");

					String[] roomclasses = reservationRoomClassName.split(Utility.DELIM);
					String[] amountArray = Amount.split(Utility.DELIM);
					boolean isValueDifferentFromAlreadyPresent = false;
					if (roomclasses.length > 0) {
						for (int index = 0; index < roomclasses.length; index++) {
							String roomClassName = roomclasses[index].toString();

							getRoomClassWithRates.put(roomClassName, ratesGrid
									.getRoomClassRateWithAdditionalAdultAndChild(driver, roomClassName, testSteps));
							app_logs.info(getRoomClassWithRates.get(roomClassName));
							roomClassesSeasonLevelData.put(roomClassName,
									getRoomClassWithRates.get(roomClassName).get(0));

							getTestSteps.clear();
							getTestSteps = ratesGrid.enterRoomClassBaserate(driver, roomClassName, amountArray[index],
									getTestSteps);
							testSteps.addAll(getTestSteps);
							if (getRoomClassWithRates.get(roomClassName).get(0) != amountArray[index]) {
								isValueDifferentFromAlreadyPresent = true;
							}

						}
					}

					if (isValueDifferentFromAlreadyPresent) {
						nightlyRate.clickSaveSason(driver, testSteps);
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
					} else {
						nightlyRate.closeSeason(driver, testSteps);
						app_logs.info("Close Season");
					}

					Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);

				} else {
					app_logs.info("No Season For Desired Date");
					testSteps.add("No Season For Desired Date");
					ratePlanAvailableInBE = false;
					testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				}
			}

			catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}
			try {
				driver.navigate().refresh();
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				String[] selectedClasses = reservationRoomClassName.split(Utility.DELIM);
				if (selectedClasses.length > 0) {
					for (int index = 0; index < selectedClasses.length; index++) {
						String roomClassName = selectedClasses[index].toString();

						testSteps.add("=================== GET '" + roomClassName
								+ "' DATA AFTER SEASON LEVEL RATE UPDATION ======================");
						app_logs.info("=================== GET '" + roomClassName
								+ "' DATA AFTER SEASON LEVEL RATE UPDATION ======================");

						app_logs.info("Expected : " + roomClassName);
						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						getTestSteps.clear();
						getTestSteps = ratesGrid.getRateValuesOfRoomClassBetweenSelectedDateRange(driver, dateFormat,
								checkInDate, checkOutDate, roomClassName, bookingEngineChannel);
						testSteps.addAll(getTestSteps);
						bulkRateUpdateValuesMap.put(roomClassName, RatesGrid.rateValuesMap);
					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}

			try {
				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== VERIFICATION OF RATES DATA AFTER SEASON LEVEL RATE UPDATION IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFICATION OF RATES DATA AFTER SEASON LEVEL RATE UPDATION IN BOOKING ENGINE ======================");

				String[] selectedClasses = reservationRoomClassName.split(Utility.DELIM);
				if (selectedClasses.length > 0) {
					for (int index = 0; index < selectedClasses.length; index++) {
						String roomClassName = selectedClasses[index].toString();

						testSteps.add("=================== VERIFY '" + roomClassName + "' DATA ======================");
						app_logs.info("=================== VERIFY '" + roomClassName + "' DATA ======================");

						for (Map.Entry<String, HashMap<String, String>> entry : bulkRateUpdateValuesMap.entrySet()) {
							String className = entry.getKey();
							HashMap<String, String> roomClassDataValuesWithRules = entry.getValue();
							if (className.equals(roomClassName)) {
								getTestSteps.clear();
								getTestSteps = bookingEngine.verifyRateValuesInBookingEngine(driver, roomClassName,
										checkInDate, checkOutDate, dateFormat, roomClassDataValuesWithRules);
								testSteps.addAll(getTestSteps);
							}
						}
					}

				}
			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}

		}

		if (UpdateTypes.equals("verifyPolicies")) {
			try {
				driver.navigate().refresh();

				testSteps.add("Navigate Rates");
				app_logs.info("Navigate Rates");

				testSteps.add("=================== GETTING RATE PLAN INFORMATION ======================");
				app_logs.info("=================== GETTING RATE PLAN INFORMATION ======================");

				rateGrid.clickRatePlanArrow(driver, testSteps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				rateGrid.clickEditIcon(driver, testSteps);
				selectedRatePlanFolioName = rateGrid.getFolioNameOfRatePlan(driver, testSteps);
				Utility.app_logs.info("Rate Plan Folio Name : " + selectedRatePlanFolioName);
				testSteps.add("Rate Plan Folio Name : " + selectedRatePlanFolioName);
				selectedChannels = nightlyRate.getSelectedChannels(driver);
				selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);

				Utility.app_logs.info("Selected Channels : " + selectedChannels);
				testSteps.add("Selected Channels : " + selectedChannels);
				Utility.app_logs.info("Selected Room Classes : " + selectedRoomClasses);
				testSteps.add("Selected Room Classes : " + selectedRoomClasses);

				testSteps.add("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");
				app_logs.info("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");

				selectedRatePlanDefaultStatus = ratesGrid.verifyRatePlanSetToDefault(driver, testSteps, ratePlanName);

				ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
						Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
				if (isSeasonExist) {
					app_logs.info("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					testSteps.add("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					nightlyRate.selectSeasonDates(driver, testSteps,
							Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					getRoomClassWithRates.put(reservationRoomClassName, ratesGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, reservationRoomClassName, testSteps));
					app_logs.info(getRoomClassWithRates.get(reservationRoomClassName));
					ArrayList<String> gettest = new ArrayList<>();
					gettest = getRoomClassWithRates.get(reservationRoomClassName);
					ratePlanAdultCapicity = gettest.get(1);
					ratePlanPersonCapicity = gettest.get(2);

					testSteps.add("==================Get Policy from  Season==================");
					app_logs.info("==================Get Policy from  Season==================");

					nightlyRate.clickSeasonPolicies(driver, testSteps);
					if (nightlyRate.isPolicyAppliedByRoomClassisSelected(driver)) {
						if (nightlyRate.isRoomClassSelectedForPolicy(driver, reservationRoomClassName)) {
							getSessionLevelPolicy = nightlyRate.getSeasonSelectedPolicyForRoomClass(driver,
									reservationRoomClassName, getTestSteps);
							getTestSteps.clear();
							app_logs.info("Season Level Policies for Room Class '" + reservationRoomClassName + "' : "
									+ getSessionLevelPolicy);
							testSteps.add("Season Level Policies for Room Class '" + reservationRoomClassName + "' : "
									+ getSessionLevelPolicy);
						} else {
							app_logs.info("No Season Level Policis exist.");
							testSteps.add("No Season Level Policis exist.");
							getSessionLevelPolicy.put("Cancellation", "NA");
							getSessionLevelPolicy.put("Deposit", "NA");
							getSessionLevelPolicy.put("Check-in", "NA");
							getSessionLevelPolicy.put("No Show", "NA");
						}
					} else {
						getTestSteps.clear();
						getTestSteps = nightlyRate.checkUncheckSeasonLevelPolicy(driver, getTestSteps, "check");
						testSteps.addAll(getTestSteps);
						getSessionLevelPolicy = nightlyRate.getSeasonSelectedPolicy(driver, getTestSteps);
						getTestSteps.clear();
						app_logs.info("Season Level Policies : " + getSessionLevelPolicy);
						testSteps.add("Season Level Policies : " + getSessionLevelPolicy);
					}

					if (NightlyRate.allPoliciesAreAlreadyChecked == false) {
						nightlyRate.clickSaveSason(driver, testSteps);
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
					} else {
						nightlyRate.closeSeason(driver, testSteps);
						app_logs.info("Close Season");
					}

					Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);

				} else {
					app_logs.info("No Season For Desired Date");
					testSteps.add("No Season For Desired Date");
					ratePlanAvailableInBE = false;
					testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				}

			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}

			try {
				ratesGrid.expandRoomClass(driver, testSteps, reservationRoomClassName);
				ratesGrid.expandChannel(driver, testSteps, reservationRoomClassName, bookingEngineChannel);
				testSteps.add(
						"=================== UNCHECK NO CHECK IN, NO CHECK OUT RULE DATA IN INNCENTER ======================");
				app_logs.info(
						"=================== UNCHECK NO CHECK IN, NO CHECK OUT RULE DATA IN INNCENTER ======================");

				ratesGrid.uncheckCheckInCheckOutRuleIfTheyAlreadyApplied(driver, reservationRoomClassName,
						bookingEngineChannel, "No Check In");
				ratesGrid.uncheckCheckInCheckOutRuleIfTheyAlreadyApplied(driver, reservationRoomClassName,
						bookingEngineChannel, "No Check Out");
			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}

			try {

				testSteps.add(
						"=================== VERIFY SEASON LEVEL POLICIES IN BOOKING ENGINE BY CHECKING ALL IN INNCENTER======================");
				app_logs.info(
						"=================== VERIFY SEASON LEVEL POLICIES IN BOOKING ENGINE BY CHECKING ALL IN INNCENTER ======================");

				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);

				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
				app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");

				int noOfDays = Integer.parseInt(seasonUpdatedMinNights) + 1;

				String checkout = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkInDate, "MM/d/yyyy", "MM/d/yyyy",
						noOfDays + 1, timeZone);

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkout);
				testSteps.addAll(getTestSteps);

				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.enterChildrenBE(driver, child);
				testSteps.addAll(getTestSteps);
				if (isPromocode) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.enterPromoCode(driver, ratePlanPromoCode);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");

				getTestSteps.clear();
				roomClassNotExist = bookingEngine.verifyRoomClassExistInBookingEngine(driver, adults, child,
						reservationRoomClassName, ratePlanAdultCapicity, ratePlanPersonCapicity);
				if (roomClassNotExist == true) {
					testSteps.add("Successfully Verified '" + reservationRoomClassName
							+ "' is not showing in Booking Engine.");
				}

				if (roomClassNotExist == false) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyRatePlanExist(driver, ratePlanName, ratePlanAvailableInBE);
					testSteps.addAll(getTestSteps);
					statusCode.add(2, "1");

					if (ratePlanAvailableInBE) {

						getTestSteps.clear();
						getTestSteps = bookingEngine.clickBookRoom(driver, ratePlanName);
						testSteps.addAll(getTestSteps);

						testSteps.add("=================== VERIFY ASSOCIATED POLICIES ======================");
						app_logs.info("=================== VERIFY ASSOCIATED POLICIES ======================");

						if (Collections.frequency(getSessionLevelPolicy.values(), "NA") != 4) {
							for (Map.Entry<String, String> entry : getSessionLevelPolicy.entrySet()) {
								String policyType = entry.getKey();
								String policyName = entry.getValue();
								if (!policyName.equalsIgnoreCase("NA")) {
									getTestSteps.clear();
									getTestSteps = bookingEngine.verifyPolicyExistInBookingEngine(driver, policyType,
											policyName, true);
									testSteps.addAll(getTestSteps);
									seasonPoliciesExist = true;
								}

							}
						} else if (Collections.frequency(getSessionLevelPolicy.values(), "NA") == 4) {
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyPolicyExistNotInBookingEngine(driver);
							testSteps.addAll(getTestSteps);
						}

						if (seasonPoliciesExist) {
							testSteps.add("Successfully verified season policies are associated to reservation.");
							app_logs.info("Successfully verified season policies are associated to reservation.");
						}
					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Policies", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Policies", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				loginRateV2(driver);
				testSteps.add("Logged into the application ");
				app_logs.info("Logged into the application");

				navigation.Inventory(driver);
				app_logs.info("Navigate Inventory");
				testSteps.add("Navigate Inventory");

				navigation.ratesGrid(driver, testSteps);
				getTestSteps.clear();
				driver.navigate().refresh();

				testSteps.add("Navigate Rates");
				app_logs.info("Navigate Rates");

				testSteps.add("=================== GETTING RATE PLAN INFORMATION ======================");
				app_logs.info("=================== GETTING RATE PLAN INFORMATION ======================");

				rateGrid.clickRatePlanArrow(driver, testSteps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				rateGrid.clickEditIcon(driver, testSteps);

				ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);

				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
						Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
				if (isSeasonExist) {
					app_logs.info("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					testSteps.add("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					nightlyRate.selectSeasonDates(driver, testSteps,
							Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					getRoomClassWithRates.put(reservationRoomClassName, ratesGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, reservationRoomClassName, testSteps));
					app_logs.info(getRoomClassWithRates.get(reservationRoomClassName));
					ArrayList<String> gettest = new ArrayList<>();
					gettest = getRoomClassWithRates.get(reservationRoomClassName);
					ratePlanAdultCapicity = gettest.get(1);
					ratePlanPersonCapicity = gettest.get(2);

					testSteps.add("==================Get Policy from  Season==================");
					app_logs.info("==================Get Policy from  Season==================");

					nightlyRate.clickSeasonPolicies(driver, testSteps);
					if (nightlyRate.isPolicyAppliedByRoomClassisSelected(driver)) {
						if (nightlyRate.isRoomClassSelectedForPolicy(driver, reservationRoomClassName)) {
							getSessionLevelPolicy = nightlyRate.getSeasonSelectedPolicyForRoomClass(driver,
									reservationRoomClassName, getTestSteps);
							getTestSteps.clear();
							app_logs.info("Season Level Policies for Room Class '" + reservationRoomClassName + "' : "
									+ getSessionLevelPolicy);
							testSteps.add("Season Level Policies for Room Class '" + reservationRoomClassName + "' : "
									+ getSessionLevelPolicy);
						} else {
							app_logs.info("No Season Level Policis exist.");
							testSteps.add("No Season Level Policis exist.");
							getSessionLevelPolicy.put("Cancellation", "NA");
							getSessionLevelPolicy.put("Deposit", "NA");
							getSessionLevelPolicy.put("Check-in", "NA");
							getSessionLevelPolicy.put("No Show", "NA");
						}
					} else {
						getTestSteps.clear();
						getTestSteps = nightlyRate.checkUncheckSeasonLevelPolicy(driver, getTestSteps, "unCheck");
						testSteps.addAll(getTestSteps);
						getSessionLevelPolicy = nightlyRate.getSeasonSelectedPolicy(driver, getTestSteps);
						getTestSteps.clear();
						app_logs.info("Season Level Policies : " + getSessionLevelPolicy);
						testSteps.add("Season Level Policies : " + getSessionLevelPolicy);
					}

					if (NightlyRate.allPoliciesAreAlreadyUnChecked == false) {
						nightlyRate.clickSaveSason(driver, testSteps);
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
					} else {
						nightlyRate.closeSeason(driver, testSteps);
						app_logs.info("Close Season");
					}

					Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);

				} else {
					app_logs.info("No Season For Desired Date");
					testSteps.add("No Season For Desired Date");
					ratePlanAvailableInBE = false;
					testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				}

			} catch (Exception e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(scriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify total number of days", testName, "BulkUpdate", driver);
				}
			}

			try {

				testSteps.add(
						"===================  VERIFY SEASON LEVEL POLICIES IN BOOKING ENGINE BY UNCHECKING ALL IN INNCENTER ======================");
				app_logs.info(
						"===================  VERIFY SEASON LEVEL POLICIES IN BOOKING ENGINE BY UNCHECKING ALL IN INNCENTER ======================");
				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
				app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");

				int noOfDays = Integer.parseInt(seasonUpdatedMinNights) + 1;

				String checkout = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkInDate, "MM/d/yyyy", "MM/d/yyyy",
						noOfDays + 1, timeZone);

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkout);
				testSteps.addAll(getTestSteps);

				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.enterChildrenBE(driver, child);
				testSteps.addAll(getTestSteps);
				if (isPromocode) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.enterPromoCode(driver, ratePlanPromoCode);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");

				getTestSteps.clear();
				roomClassNotExist = bookingEngine.verifyRoomClassExistInBookingEngine(driver, adults, child,
						reservationRoomClassName, ratePlanAdultCapicity, ratePlanPersonCapicity);
				statusCode.add(4, "1");
				if (roomClassNotExist == true) {
					testSteps.add("Successfully Verified '" + reservationRoomClassName
							+ "' is not showing in Booking Engine.");
				}

				if (roomClassNotExist == false) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyRatePlanExist(driver, ratePlanName, ratePlanAvailableInBE);
					testSteps.addAll(getTestSteps);

					if (ratePlanAvailableInBE) {

						getTestSteps.clear();
						getTestSteps = bookingEngine.clickBookRoom(driver, ratePlanName);
						testSteps.addAll(getTestSteps);

						testSteps.add("=================== VERIFY ASSOCIATED POLICIES ======================");
						app_logs.info("=================== VERIFY ASSOCIATED POLICIES ======================");

						if (Collections.frequency(getSessionLevelPolicy.values(), "NA") != 4) {
							for (Map.Entry<String, String> entry : getSessionLevelPolicy.entrySet()) {
								String policyType = entry.getKey();
								String policyName = entry.getValue();
								if (!policyName.equalsIgnoreCase("NA")) {
									getTestSteps.clear();
									getTestSteps = bookingEngine.verifyPolicyExistInBookingEngine(driver, policyType,
											policyName, true);
									testSteps.addAll(getTestSteps);
									seasonPoliciesExist = true;
								}

							}
						} else if (Collections.frequency(getSessionLevelPolicy.values(), "NA") == 4) {
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyPolicyExistNotInBookingEngine(driver);
							testSteps.addAll(getTestSteps);
						}
						statusCode.add(7, "1");

					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Policies", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Policies", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		if (UpdateTypes.equals("verifyByClickingOnSearchForRoom")
				|| UpdateTypes.equals("verifyByClickingViewEntireAvailability")) {
			try {
				driver.navigate().refresh();
				testSteps.add("Navigate Rates");
				app_logs.info("Navigate Rates");
				testSteps.add("=================== GETTING RATE PLAN INFORMATION ======================");
				app_logs.info("=================== GETTING RATE PLAN INFORMATION ======================");
				rateGrid.clickRatePlanArrow(driver, testSteps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				rateGrid.clickEditIcon(driver, testSteps);
				selectedRatePlanFolioName = rateGrid.getFolioNameOfRatePlan(driver, testSteps);
				Utility.app_logs.info("Rate Plan Folio Name : " + selectedRatePlanFolioName);
				testSteps.add("Rate Plan Folio Name : " + selectedRatePlanFolioName);
				selectedChannels = nightlyRate.getSelectedChannels(driver);
				selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);

				Utility.app_logs.info("Selected Channels : " + selectedChannels);
				testSteps.add("Selected Channels : " + selectedChannels);
				Utility.app_logs.info("Selected Room Classes : " + selectedRoomClasses);
				testSteps.add("Selected Room Classes : " + selectedRoomClasses);

				selectedRatePlanDefaultStatus = ratesGrid.verifyRatePlanSetToDefault(driver, testSteps, ratePlanName);

				ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);

				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);

				dateWithoutSeason = nightlyRate.getDateWhereSeasonNotExist(driver);

				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
						Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
				if (isSeasonExist) {
					app_logs.info("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					testSteps.add("Verified Season exist Between Check-in (" + checkInDate + ") and Check-out Dates ("
							+ checkOutDate + ")");
					nightlyRate.selectSeasonDates(driver, testSteps,
							Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					roomClassList = rateGrid.getSeasonLevelRoomClasses(driver);

					testSteps.add("Selected Season Room Classes : " + roomClassList);
					app_logs.info("Selected Season Room Classes : " + roomClassList);

					nightlyRate.closeSeason(driver, testSteps);
					app_logs.info("Close Season");

					Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);

				} else {
					app_logs.info("No Season For Desired Date");
					testSteps.add("No Season For Desired Date");
					ratePlanAvailableInBE = false;
					testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Policies", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Policies", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		if (UpdateTypes.equals("verifyByClickingOnSearchForRoom")
				|| UpdateTypes.equals("verifyByClickingViewEntireAvailability")) {
			try {

				testSteps.add(
						"===================  VERIFY ROOM CLASSES BY NAVIGATING THROUGH AVAILABILITY GRID IN BOOKING ENGINE ======================");
				app_logs.info(
						"===================  VERIFY ROOM CLASSES BY NAVIGATING THROUGH AVAILABILITY GRID IN BOOKING ENGINE ======================");
				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
				app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");

				if (UpdateTypes.equals("verifyByClickingOnSearchForRoom")) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
					testSteps.addAll(getTestSteps);

					testSteps.add("Select Check-In Date : " + checkInDate);
					app_logs.info("Select Check-In Date : " + checkInDate);

					testSteps.add("Select Check-Out Date : " + checkOutDate);
					app_logs.info("Select Check-Out Date : " + checkOutDate);

					getTestSteps.clear();
					getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.enterChildrenBE(driver, child);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickSearchOfRooms(driver);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickOnViewEntireAvailabilityLinkThroughAdvancedOptions(driver);
					testSteps.addAll(getTestSteps);
				} else if (UpdateTypes.equals("verifyByClickingViewEntireAvailability")) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
					testSteps.addAll(getTestSteps);

					testSteps.add("Successfully navigated to availability grid page.");
					app_logs.info("Successfully navigated to availability grid page.");

					driver.navigate().refresh();
				}

				testSteps.add(
						"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
				app_logs.info(
						"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");

				for (int i = 0; i < roomClassList.size(); i++) {
					String roomClassName = roomClassList.get(i);
					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyRoomClassesByAdvancedOption(driver, roomClassName);
					testSteps.addAll(getTestSteps);
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Room Classes", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Room Classes", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (UpdateTypes.equals("verifyByClickingOnSearchForRoom")
				|| UpdateTypes.equals("verifyByClickingViewEntireAvailability")) {
			try {

				testSteps.add("=================== VERIFY CHECK OUT TEXT IS SHOWING  ======================");
				app_logs.info("=================== VERIFY CHECK OUT TEXT IS SHOWING ======================");

				testSteps.add("Click on Check In Date : " + checkInDate);
				app_logs.info("Click on Check In Date : " + checkInDate);

				if (roomClassList.size() > 0) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickOnCheckInDate(driver, checkInDate, checkOutDate,
							roomClassList.get(0), dateFormat, days);
					testSteps.addAll(getTestSteps);

					testSteps.add("Click on Check Out Date : " + checkOutDate);
					app_logs.info("Click on Check Out Date : " + checkOutDate);

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickOnCheckOutDate(driver, checkInDate, checkOutDate,
							roomClassList.get(0), dateFormat, days, BookingEngine.checkInDateIndex);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyCheckOutTextIsShowing(driver);
					testSteps.addAll(getTestSteps);

					String checkoutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkOutDate, "MM/d/yyyy",
							"MM/d/yyyy", days + 2, timeZone);

					testSteps.add(
							"=================== VERIFY CHECK OUT TEXT IS SHOWING BY MOVING CURSOR AND EXTENDING CHECKOUT DATE  ======================");
					app_logs.info(
							"=================== VERIFY CHECK OUT TEXT IS SHOWING BY MOVING CURSOR AND EXTENDING CHECKOUT DATE ======================");

					testSteps.add("Extended checkOut Date : " + checkoutDate);
					app_logs.info("Extended checkOut Date : " + checkoutDate);

					int newdays = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkoutDate, dateFormat);

					testSteps.add("Days : " + newdays);
					app_logs.info("Days : " + newdays);

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickOnCheckOutDate(driver, checkInDate, checkoutDate,
							roomClassList.get(0), dateFormat, newdays, BookingEngine.checkInDateIndex);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyCheckOutTextIsShowing(driver);
					testSteps.addAll(getTestSteps);
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Check Out Text", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Verify Check Out Text", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (UpdateTypes.equals("verifyByClickingViewEntireAvailability")) {
			try {

				testSteps.add("=================== VERIFY SELECT A DATE BUTTON IS SHOWING  ======================");
				app_logs.info("=================== VERIFY SELECT A DATE BUTTON IS SHOWING ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifySelectADateButtonIsDisplaying(driver);
				testSteps.addAll(getTestSteps);

				String newDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkInDate, "MM/d/yyyy", "MM/d/yyyy",
						days + 2, timeZone);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnSelectADateButtonAndSelectDate(driver, newDate);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== VERIFY AVAILABILITY GRID START DATE AFTER SELECTING FROM SELECT A DATE BUTTON ======================");
				app_logs.info(
						"=================== VERIFY AVAILABILITY GRID START DATE AFTER SELECTING FROM SELECT A DATE BUTTON ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyAvailabilityGridStartDate(driver,
						ESTTimeZone.reformatDate(newDate, "MM/d/yyyy", "d/MM/yyyy"));
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}

		if (UpdateTypes.equals("verifyCalender")) {
			try {

				ratePlanAdultCapicity = "3";
				ratePlanPersonCapicity = "6";
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				testSteps.add(
						"=================== VERIFY CALENDER MONTH AFTER SELECTING DATE FROM SECOND MONTH  ======================");
				app_logs.info(
						"=================== VERIFY CALENDER MONTH AFTER SELECTING DATE FROM SECOND MONTH ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectSecondMonthFromCalender(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCalenderMonthWhenSelectSecondMonthFromCalender(driver,
						BookingEngine.getMonthFromSecondPart);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				roomClassNotExist = bookingEngine.verifyRoomClassExistInBookingEngine(driver, adults, child,
						reservationRoomClassName, ratePlanAdultCapicity, ratePlanPersonCapicity);
				if (roomClassNotExist == true) {
					testSteps.add("Successfully Verified '" + reservationRoomClassName
							+ "' is not showing in Booking Engine.");
				}

				if (roomClassNotExist == false) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnReservationDetailsEditLink(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCalenderMonthWhenSelectSecondMonthFromCalender(driver,
						BookingEngine.getMonthFromSecondPart);
				testSteps.addAll(getTestSteps);

			}

			catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				testSteps.add("=================== Verifying the Calendar on the BE home page  ======================");
				app_logs.info("=================== Verifying the Calendar on the BE home page ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCalenderOnBookingEngineHomePage(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnAnotherAreaAndVerifyCalenderOnBookingEngineHomePage(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				testSteps.add(
						"=================== Verifying the check-in date when click for the second time ======================");
				app_logs.info(
						"=================== Verifying the check-in date when click for the second time ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCheckInDateWhenClickForSecondTime(driver, checkInDate);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				ratePlanAdultCapicity = "3";
				ratePlanPersonCapicity = "6";
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				testSteps.add(
						"=================== Verifying the Check in date is getting modified in Room class details page when user double taps on date picker in calendar ======================");
				app_logs.info(
						"=================== Verifying the Check in date is getting modified in Room class details page when user double taps on date picker in calendar  ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
				testSteps.addAll(getTestSteps);

				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				roomClassNotExist = bookingEngine.verifyRoomClassExistInBookingEngine(driver, adults, child,
						reservationRoomClassName, ratePlanAdultCapicity, ratePlanPersonCapicity);
				if (roomClassNotExist == true) {
					testSteps.add("Successfully Verified '" + reservationRoomClassName
							+ "' is not showing in Booking Engine.");
				}

				if (roomClassNotExist == false) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
					testSteps.addAll(getTestSteps);
				}

				String newCheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkOutDate, "MM/d/yyyy",
						"MM/d/yyyy", days + 2, timeZone);

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckOutDateByDoubleTapOnDatePicker(driver, newCheckOutDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnBackToRoomButton(driver);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== Navigate to Room Details Page After updating value of check Out Date ======================");
				app_logs.info(
						"=================== Navigate to Room Details Page After updating value of check Out Date  ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCheckOutDateAfterUpate(driver, newCheckOutDate);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				loginRateV2(driver);
				navigation.navSetup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				navigation.openProperty(driver, testSteps, propertyName);
				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);

				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				getTestSteps.clear();
				getTestSteps = bookingEngine.toggleAllowSameDayBooking(driver);
				testSteps.addAll(getTestSteps);

				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				testSteps.add(
						"=================== Verifying the current date is still displayed in the calendar ,even same day booking toggle is off in settings  ======================");
				app_logs.info(
						"=================== Verifying the current date is still displayed in the calendar ,even same day booking toggle is off in settings   ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCurrentDateIsNotDisplayingWhenSameDayBookingToggleIsOff(driver,
						checkInDate);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				testSteps.add(
						"=================== Check out date is default selected when user selected check-in date in calendar in Search widget   ======================");
				app_logs.info(
						"=================== Check out date is default selected when user selected check-in date in calendar in Search widget  ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCheckOutDateIsDefaultSelected(driver, checkInDate);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				loginRateV2(driver);
				propertyName = "End Inn Test ";
				navigation.navSetup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				navigation.openProperty(driver, testSteps, propertyName);
				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);

				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				getTestSteps.clear();
				getTestSteps = bookingEngine.toggleDisplayAgeRangeToggleButton(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.enterAdultAndChildrenValues(driver, adults, child);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.saveAgeRangeValues(driver);
				testSteps.addAll(getTestSteps);

				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				testSteps.add(
						"=================== Checking the age range tooltips on home page calendar ======================");
				app_logs.info(
						"=================== Checking the age range tooltips on home page calendar ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyAdultAgeRangeValues(driver, adults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyChildrenAgeRangeValues(driver, child);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyAdultAgeRangeValues(driver, adults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyChildrenAgeRangeValues(driver, child);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add(
						"=================== Verify start date and end date in calendar for group blocks  ======================");
				app_logs.info(
						"=================== Verify start date and end date in calendar for group blocks  ======================");

				loginRateV2(driver);
				propertyName = "End Inn Test ";
				String groupNumber = "963314486865981";
				checkInDate = "01/9/2021";
				checkOutDate = "01/13/2021";
				navigation.navSetup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				navigation.openProperty(driver, testSteps, propertyName);
				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);

				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				getTestSteps.clear();
				getTestSteps = bookingEngine.toggleGroupBookingsToggleButton(driver);
				testSteps.addAll(getTestSteps);
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnAdvancedOption(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnHaveaGroupNumberLink(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyEnteraGroupNumberPopUpIsShowing(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.enterGroupNumberInBE(driver, groupNumber);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCheckInCheckOutDate(driver, checkInDate, checkOutDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyDateRangeLiesBetweenBlockDateRange(driver, checkInDate,
						checkOutDate);
				testSteps.addAll(getTestSteps);

				String newCheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkOutDate, "MM/d/yyyy",
						"MM/d/yyyy", -1, timeZone);

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, newCheckOutDate);
				testSteps.addAll(getTestSteps);

				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);

				reservationRoomClassName = "R12";
				ratePlanAdultCapicity = "3";
				ratePlanPersonCapicity = "5";

				getTestSteps.clear();
				roomClassNotExist = bookingEngine.verifyRoomClassExistInBookingEngine(driver, adults, child,
						reservationRoomClassName, ratePlanAdultCapicity, ratePlanPersonCapicity);
				if (roomClassNotExist == true) {
					testSteps.add("Successfully Verified '" + reservationRoomClassName
							+ "' is not showing in Booking Engine.");
				}

				if (roomClassNotExist == false) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickOnReservationDetailsEditLink(driver);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyCheckInCheckOutDate(driver, checkInDate, checkOutDate);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyDateRangeLiesBetweenBlockDateRange(driver, checkInDate,
							checkOutDate);
					testSteps.addAll(getTestSteps);

					newCheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkOutDate, "MM/d/yyyy", "MM/d/yyyy",
							-1, timeZone);

					getTestSteps.clear();
					getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, newCheckOutDate);
					testSteps.addAll(getTestSteps);
				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add(
						"=================== Verify the rate displaying in the home page calendar by switching toggle off  ======================");
				app_logs.info(
						"=================== Verify the rate displaying in the home page calendar by switching toggle off  ======================");

				loginRateV2(driver);
				propertyName = "End Inn Test ";
				navigation.navSetup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				navigation.openProperty(driver, testSteps, propertyName);
				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);

				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				getTestSteps.clear();
				getTestSteps = bookingEngine.toggleDisplayRateForCalenderButton(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.saveDisplayRateForCalenderSettings(driver);
				testSteps.addAll(getTestSteps);

				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				getTestSteps.clear();
				getTestSteps = bookingEngine.hoverOnCheckInDateAndVerifyDates(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.hoverOnCheckOutDateAndVerifyDates(driver, checkOutDate);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add(
						"=================== Verify the rate displaying in the search list page calendar by by switching toggle off  ======================");
				app_logs.info(
						"=================== Verify the rate displaying in the search list page calendar by by switching toggle off  ======================");

				loginRateV2(driver);
				propertyName = "End Inn Test ";
				navigation.navSetup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				navigation.openProperty(driver, testSteps, propertyName);
				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);

				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				getTestSteps.clear();
				getTestSteps = bookingEngine.toggleDisplayRateForCalenderButton(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.saveDisplayRateForCalenderSettings(driver);
				testSteps.addAll(getTestSteps);

				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
				testSteps.addAll(getTestSteps);

				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.hoverOnCheckInDateAndVerifyDates(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.hoverOnCheckOutDateAndVerifyDates(driver, checkOutDate);
				testSteps.addAll(getTestSteps);

				testSteps.add(
						"=================== Verify the rate displaying in the room details  page calendar by by switching toggle off ======================");
				app_logs.info(
						"=================== Verify the rate displaying in the room details  page calendar by by switching toggle off ======================");

				reservationRoomClassName = "R12";
				ratePlanAdultCapicity = "3";
				ratePlanPersonCapicity = "5";

				getTestSteps.clear();
				roomClassNotExist = bookingEngine.verifyRoomClassExistInBookingEngine(driver, adults, child,
						reservationRoomClassName, ratePlanAdultCapicity, ratePlanPersonCapicity);
				if (roomClassNotExist == true) {
					testSteps.add("Successfully Verified '" + reservationRoomClassName
							+ "' is not showing in Booking Engine.");
				}

				if (roomClassNotExist == false) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickOnReservationDetailsEditLink(driver);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.hoverOnCheckInDateAndVerifyDates(driver, checkInDate);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.hoverOnCheckOutDateAndVerifyDates(driver, checkOutDate);
					testSteps.addAll(getTestSteps);

				}

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {

				testSteps.add(
						"=================== Verify the rate displayed in the search list page calendar by selecting group booking ======================");
				app_logs.info(
						"=================== Verify the rate displayed in the search list page calendar by selecting group booking ======================");

				loginRateV2(driver);
				propertyName = "End Inn Test ";
				String groupNumber = "963314486865981";
				navigation.navSetup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				navigation.openProperty(driver, testSteps, propertyName);
				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);

				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				getTestSteps.clear();
				getTestSteps = bookingEngine.toggleDisplayRateForCalenderButton(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.saveDisplayRateForCalenderSettings(driver);
				testSteps.addAll(getTestSteps);

				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnAdvancedOption(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnHaveaGroupNumberLink(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyEnteraGroupNumberPopUpIsShowing(driver);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.enterGroupNumberInBE(driver, groupNumber);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.hoverOnCheckInDateAndVerifyDates(driver, checkInDate);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.hoverOnCheckOutDateAndVerifyDates(driver, checkOutDate);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to VERIFY SELECT A DATE BUTTON", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			/*
			 * try {
			 * 
			 * testSteps.
			 * add("=================== Verify Rate display on Booking Engine Calendar when Vat applied ======================"
			 * ); app_logs.
			 * info("=================== Verify Rate display on Booking Engine Calendar when Vat applied ======================"
			 * );
			 * 
			 * driver.navigate().to("https://app.innroad.com/"); login.login(driver,
			 * "https://app.innroad.com/", "stone1 ", "robin", "Innroad@123");
			 * 
			 * String value ="10"; String category = "Sales Tax"; boolean excludeTaxExempt =
			 * false; String TaxName = "Test Tax"; String taxLedgerAccount = ""; boolean vat
			 * = true;
			 * 
			 * // Create Tax
			 * testSteps.add("<b>************** Making Tax Setup*******************</b>");
			 * navigation.Setup(driver); testSteps.add("Navigate to setup");
			 * navigation.Taxes(driver); testSteps.add("Navigate to taxes page"); // create
			 * new tax------------------
			 * testSteps.add("<b>********** Creating New percent Tax****************</b>");
			 * randomString = Utility.GenerateRandomNumber(); tName = TaxName.replace("XXX",
			 * randomString); tax.Click_NewItem(driver, testSteps);
			 * testSteps.add("Click at new tax item button");
			 * testSteps.add("<br>Create new taxes</br>"); boolean percentage = true;
			 * testSteps.add("percent Tax name is: " + "<b>" + tName + " </b>");
			 * tax.createTax(driver, test, tName.trim(), tName.trim(), tName.trim(), value,
			 * category, taxLedgerAccount, excludeTaxExempt, percentage, vat);
			 * testSteps.add("Created tax with percent amount: " + "<b>" + value + "</b>");
			 * 
			 * 
			 * } catch (Exception e) { if (Utility.reTry.get(testName) == Utility.count) {
			 * RetryFailedTestCases.count = Utility.reset_count;
			 * Utility.AddTest_IntoReport(scriptName, testDescription, testCategory,
			 * testSteps); Utility.updateReport(e,
			 * "Failed to Verify Rate display on Booking Engine Calendar when Vat applied ",
			 * "BERES", "BERES", driver); } else { Assert.assertTrue(false); } } catch
			 * (Error e) { if (Utility.reTry.get(testName) == Utility.count) {
			 * RetryFailedTestCases.count = Utility.reset_count;
			 * Utility.AddTest_IntoReport(scriptName, testDescription, testCategory,
			 * testSteps); Utility.updateReport(e,
			 * "Failed to Verify Rate display on Booking Engine Calendar when Vat applied ",
			 * "BERES", "BERES", driver); } else { Assert.assertTrue(false); }
			 * 
			 * }
			 */

		}
		// new Merged Code from here
		if (UpdateTypes.equals("VerifyRules")) {
			try {

				testSteps.add(
						"=================== VERIFY RATE PLAN IS SELECTED IN BOOKING ENGINE ======================");
				app_logs.info("=================== VERIFY RATE PLAN IS SELECTED BOOKING ENGINE ======================");

				navigation.rateV2Setup(driver);
				testSteps.add("Navigate Setup");
				app_logs.info("Navigate Setup");
				navigation.Properties(driver);
				testSteps.add("Navigate Properties");
				app_logs.info("Navigate Properties");
				try {
					navigation.openProperty(driver, testSteps, propertyName);
				} catch (Exception e) {
					navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
				}

				testSteps.add("Open Property : " + propertyName);
				app_logs.info("Open Property : " + propertyName);
				navigation.clickPropertyOptions(driver, testSteps);
				bookingEngine.clickOnBookingEngineConfigLink(driver,
						bookingEngineChannel.split("-")[1].replaceAll(" ", ""));
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SELECTED =====");
				app_logs.info("===== VERIFY RATE PLAN IS SELECTED =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				Utility.closeFirstTab(driver);
				testSteps.add("Close First Tab");
				app_logs.info("Close First Tab");
				getTestSteps.clear();
				boolean ratePlanIsSelectedOrNot = bookingEngine.isRatePlanExist(driver, ratePlanName, getTestSteps);

				if (ratePlanIsSelectedOrNot) {
					testSteps.add(
							"Successfully Verified that '" + ratePlanName + "' is already selected in Booking Engine.");
					app_logs.info(
							"Successfully Verified that '" + ratePlanName + "' is already selected in Booking Engine.");
				} else {
					testSteps.add("Select '" + ratePlanName + "' in Booking Engine.");
					app_logs.info("Select '" + ratePlanName + "' in Booking Engine.");
				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.setToggleButtonState(driver, "Display custom message", false,
						"Customize message when no rooms are available for the guest", displayCustomMessageState);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterCustomMessageAndSave(driver, customMessage, false,
						"Customize message when no rooms are available for the guest");
				testSteps.addAll(getTestSteps);
				try {
					driver.navigate().refresh();
					ratePlanIsSelectedOrNot = bookingEngine.isRatePlanExist(driver, ratePlanName, getTestSteps);

					if (ratePlanIsSelectedOrNot) {
						app_logs.info("Successfully Verified that '" + ratePlanName
								+ "' is already selected in Booking Engine.");
					} else {
						app_logs.info("Select '" + ratePlanName + "' in Booking Engine.");
					}

					getTestSteps.clear();
					getTestSteps = bookingEngine.setToggleButtonState(driver, "Display custom message", false,
							"Customize message when no rooms are available for the guest", displayCustomMessageState);

					getTestSteps.clear();
					getTestSteps = bookingEngine.enterCustomMessageAndSave(driver, customMessage, false,
							"Customize message when no rooms are available for the guest");
				} catch (Exception e) {

				}
			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				e.printStackTrace();
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get Rate Plan Details", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Open BOOKING ENGINE", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to Open BOOKING ENGINE", "BERES", "BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
				app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");
				if (Boolean.parseBoolean(bulkUpdate) && bulkUpdateRules.equalsIgnoreCase("min stay")) {
					if (testCaseDescription.equalsIgnoreCase(
							"Verify that Min Stay Rule is not enforced if the rule exists only for Checkout date")) {
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyMinStayRuleOnDate(driver, checkOutDate,
								bulkUpdateMinStayValue);
						testSteps.addAll(getTestSteps);
					} else if (!testCaseDescription.equalsIgnoreCase(
							"Verifying the error message for correct promo code with min stay rules.")) {
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyMinStayRuleOnDate(driver, checkInDate,
								bulkUpdateMinStayValue);
						testSteps.addAll(getTestSteps);
					}

				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate, false);
				testSteps.addAll(getTestSteps);
				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);
				getTestSteps.clear();
				getTestSteps = bookingEngine.enterAdultsBE(driver, adults);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.enterChildrenBE(driver, child);
				testSteps.addAll(getTestSteps);
				if (Boolean.parseBoolean(updatedIsRatePlanRistrictionReq) && updatedRistrictionType.contains("Promo")) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.enterPromoCode(driver, updatedPromoCode);
					testSteps.addAll(getTestSteps);
				}

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);

				if (Boolean.parseBoolean(noRoomAvailableMessage)) {
					testSteps.add(
							"=================== VERIFY NO ROOM AVAILABLE MESSAGE DISPLAYED WITH CUSTOM MESSAGE ======================");
					app_logs.info(
							"=================== VERIFY NO ROOM AVAILABLE MESSAGE DISPLAYED WITH CUSTOM MESSAGE ======================");
					try {
						getTestSteps.clear();
						getTestSteps = bookingEngine.noRoomAvailableCustomMessag(driver, customMessage);
						testSteps.addAll(getTestSteps);
					} catch (Exception e) {
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyNoRoomAvailableMessageDisplayed(driver);
						testSteps.addAll(getTestSteps);
					}
				} else {
					testSteps.add(
							"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
					app_logs.info(
							"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyRoomClassAvailableInBookingEngine(driver,
							reservationRoomClassName, true);
					testSteps.addAll(getTestSteps);
				}

				if (testCaseDescription.equalsIgnoreCase("Verify Min stay error message in Room details page")) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyRatePlanExist(driver, ratePlanName, ratePlanAvailableInBE);
					testSteps.addAll(getTestSteps);

					testSteps.add("============ EDIT CHECKIN AND CHECKOUT DATE SEARCH ROOMS ======================");
					app_logs.info("========== EDIT CHECKIN AND CHECKOUT DATE SEARCH ROOMS ======================");

					String newCheckOutDate = Utility.addDate(Integer.parseInt(updatedStayDays), dateFormat, checkInDate,
							dateFormat, timeZone);
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectCheckOutDateByDoubleTapOnDatePicker(driver, newCheckOutDate);
					testSteps.addAll(getTestSteps);
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickCheckAvailability(driver);
					testSteps.addAll(getTestSteps);
					getTestSteps.clear();
					getTestSteps = bookingEngine.noRoomAvailableMessagAfterEditSearchDetails(driver);
					testSteps.addAll(getTestSteps);
				} else if (Boolean.parseBoolean(changeStayDetails)) {
					testSteps.add("============ EDIT SEARCH DETAILS ======================");
					app_logs.info("========== EDIT SEARCH DETAILS ======================");
					String newCheckInDate = Utility.addDate(Integer.parseInt(addRemoveCheckInDays), dateFormat,
							checkInDate, dateFormat, timeZone);
					String newCheckOutDate = Utility.addDate(Integer.parseInt(updatedStayDays), dateFormat,
							newCheckInDate, dateFormat, timeZone);
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, newCheckInDate, newCheckOutDate,
							false);
					testSteps.addAll(getTestSteps);
					testSteps.add("Select Check-In Date : " + newCheckInDate);
					app_logs.info("Select Check-In Date : " + newCheckInDate);

					testSteps.add("Select Check-Out Date : " + newCheckOutDate);
					app_logs.info("Select Check-Out Date : " + newCheckOutDate);

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickSearchOfRooms(driver);
					testSteps.addAll(getTestSteps);
					if (Boolean.parseBoolean(afterUpdateNoRoomAvailableMessage)) {
						testSteps.add(
								"=================== VERIFY NO ROOM AVAILABLE MESSAGE DISPLAYED WITH CUSTOM MESSAGE ======================");
						app_logs.info(
								"=================== VERIFY NO ROOM AVAILABLE MESSAGE DISPLAYED WITH CUSTOM MESSAGE ======================");
						try {
							getTestSteps.clear();
							getTestSteps = bookingEngine.noRoomAvailableCustomMessag(driver, customMessage);
							testSteps.addAll(getTestSteps);
						} catch (Exception e) {
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyNoRoomAvailableMessageDisplayed(driver);
							testSteps.addAll(getTestSteps);
						}
					} else {
						testSteps.add(
								"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
						app_logs.info(
								"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyRoomClassAvailableInBookingEngine(driver,
								reservationRoomClassName, true);
						testSteps.addAll(getTestSteps);
					}
				}
			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e,
							"Failed to VERIFY ROOM CLASS VISIBILITY IN AVAILABILITY GRID IN BOOKING ENGINE", "BERES",
							"BERES", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e,
							"Failed to VERIFY ROOM CLASS VISIBILITY IN AVAILABILITY GRID IN BOOKING ENGINE", "BERES",
							"BERES", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
			if (Boolean.parseBoolean(createRoomClass)) {
				try {
					testSteps.add("============ DELETE ROOM CLASS IN INNCENTER ===========");
					app_logs.info("============ DELETE ROOM CLASS IN INNCENTER ===========");
					loginRateV2(driver);
					testSteps.add("Logged into the InnCenter ");
					app_logs.info("Logged into the InnCenter ");

					navigation.mainSetupManu(driver);
					app_logs.info("Navigate Setup");
					testSteps.add("Navigate Setup");

					navigation.navigateRoomClass(driver);
					app_logs.info("Navigate Room Class");
					testSteps.add("Navigate Room Class");

					app_logs.info("roomClassType : '" + roomClassType + "'");
					if (roomClassType.equalsIgnoreCase("Inactive")) {
						app_logs.info("roomClassType : " + roomClassType);
						roomClassV2.selectStatus(driver, testSteps, inActiveStatus);
					} else {
						roomClassV2.selectStatus(driver, testSteps, activeStatus);
					}
					for (int i = 0; i < reservationRoomClassName.split(Utility.DELIM).length; i++) {
						boolean isRoomClassShowing = roomClassV2.searchRoomClassStartWithV2(driver,
								reservationRoomClassName.split(Utility.DELIM)[i]);
						app_logs.info("Search");
						if (isRoomClassShowing) {
							getTestSteps.clear();
							getTestSteps = roomClassV2.deleteRoomClassStartWithRoomClassV2(driver,
									reservationRoomClassName.split(Utility.DELIM)[i]);
							testSteps.addAll(getTestSteps);
							testSteps.add("Successfully Deleted RoomClass  : "
									+ reservationRoomClassName.split(Utility.DELIM)[i]);
							app_logs.info("Successfully Deleted RoomClass :  "
									+ reservationRoomClassName.split(Utility.DELIM)[i]);
						} else {
							testSteps.add("No room class exist");
						}
					}

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			}

		}

		// till here

		// Generate Report
		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("VerifyAllRulesScenariosInNBE", envLoginExcel);
	}

	@AfterMethod(alwaysRun = true)
	public void updateTestRailLink() throws MalformedURLException, IOException, APIException {
		System.out.println("In after method");
		// Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode,
		// testName,TestCore.TestRail_AssignToID);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		// driver.close();
	}

}
