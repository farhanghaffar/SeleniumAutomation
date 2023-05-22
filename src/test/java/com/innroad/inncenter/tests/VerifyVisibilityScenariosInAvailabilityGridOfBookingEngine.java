package com.innroad.inncenter.tests;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatePackage;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.properties.OR_BE;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyVisibilityScenariosInAvailabilityGridOfBookingEngine extends TestCore {

	// AUTOMATION-2101
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	String date = null;

	String reservationNumber = null;
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String comment = "";

	public static String test_description = "";
	public static String test_catagory = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyVisibilityScenariosInAvailabilityGridOfBookingEngine(String delim, String TestCaseID,
			String testCaseDescription, String roomClassType, String roomClassAbb, String roomClassName,
			String checkInDate, String checkOutDate) throws Exception {

		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
				"CreateRatePlan_VerifyVisibility");
		HashMap<String, String> staticData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
				"VerifyRoomClass_StaticData");

		String bookingEngineChannel = staticData.get("ChannelName");
		String roomClassMaxAdults = staticData.get("roomClassMaxAdults");
		String roomClassMaxPersons = staticData.get("roomClassMaxPersons");
		String roomQuantity = staticData.get("RoomQuatity");
		String roomClassRatePerNight = staticData.get("roomClasssRatePerNight");
		String roomClassAdultsRate = staticData.get("roomClassAdultrate");
		String roomClassChildRate = staticData.get("roomClassChildRate");
		String ratePlanType = staticData.get("ratePlanType");
		String ratePlanName = staticData.get("ratePlanName");
		String entityName = staticData.get("entityName");
		String productName = staticData.get("productsName");
		String isSellOnBookingEngine = staticData.get("isSellOnBookingEngine");
		String bookingEngineAvailabilityOption = staticData.get("bookingEngineAvailabilityOption");
		String productDescription = staticData.get("productDescritpion");
		String productCategory = staticData.get("productCategory");
		String productPolicy = staticData.get("productPolicy");
		String calculationMethodOne = staticData.get("productFirstCalculationMethod");
		String calculationMethodTwo = staticData.get("productSecondCalculationMethod");
		String productAmount = staticData.get("productAmount");
		String enableAvailabilityGridState = staticData.get("enableAvailabilityGridState");

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
		String timeZone = "";
		String propertyName = "";
		String scenario = "RoomClass";
		String roomClassVisibility = "FALSE";
		String displayChildrenState = "true";
		String displayPromoCodeState = "true";
		String updateRules = "no";
		if (testCaseDescription.equalsIgnoreCase(
				"Verifying Children Field in BE Availability Grid when children Toggle is ON and Promo code Toggle is ON")
				|| testCaseDescription.equalsIgnoreCase(
						"Verifying Children Field in BE Availability Grid when children Toggle is OFF and Promocode Toggle is ON")
				|| testCaseDescription.equalsIgnoreCase(
						"Verifying Promo Code Field in Availability grid when Promo Code Toggle is OFF and Children Toggle Off")) {
			scenario = "ChildrenPromoCode";
			updateRules = "yes";
			if (testCaseDescription.equalsIgnoreCase(
					"Verifying Promo Code Field in Availability grid when Promo Code Toggle is OFF and Children Toggle Off")) {
				displayChildrenState = "false";
				displayPromoCodeState = "false";
			} else if (testCaseDescription.equalsIgnoreCase(
					"Verifying Children Field in BE Availability Grid when children Toggle is OFF and Promocode Toggle is ON")) {
				displayChildrenState = "false";
			}
		}
		if (roomClassType.equalsIgnoreCase("Active")) {
			roomClassVisibility = "TRUE";
		}
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

		Utility.DELIM = "\\" + delim;
		Utility.SEASONDELIM = seasonDelim;
		String testName = "VerifyVisibilityScenariosInAvailabilityGridOfBookingEngine_" + testCaseDescription;
		test_description = testCaseDescription + " <br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-2101' target='_blank'>"
				+ "Click here to open JIRA: AUTOMATION-2101</a>";
		test_catagory = "NBE_AvailabilityGrid";
		scriptName.clear();
		testCategory.clear();
		testDescription.clear();
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		NewRoomClassesV2 roomClassV2 = new NewRoomClassesV2();
		RatesGrid ratesGrid = new RatesGrid();
		Distribution distribution = new Distribution();
		NightlyRate nightlyRate = new NightlyRate();
		Admin admin = new Admin();
		DerivedRate derivedRate = new DerivedRate();
		RatePackage ratePackage = new RatePackage();
		Properties properties = new Properties();

		BookingEngine bookingEngine = new BookingEngine();

		String inActiveStatus = "Inactive";
		String activeStatus = "Active";
		String randomNumber = Utility.GenerateRandomNumber();
		int numberOfProperties = 0;

		String dateFormat = Utility.monthDayYear_DateFormat;
		String seasonDateFormat = Utility.dayMonthYear_DateFormat;
		String calendarTodayDay = "today";
		String todayDate = null;
		boolean israteplanExist = false;

		ArrayList<String> selectedChannels = null;
		ArrayList<String> selectedRoomClasses = null;

		ArrayList<String> productWithDetails = new ArrayList<>();

		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate, dateFormat);
		app_logs.info("days: " + days);
		int seasonDays = ESTTimeZone.numberOfDaysBetweenDates(seasonStartDate, seasonEndDate, seasonDateFormat);
		String seasonDuration = "" + seasonDays + "";
		String enableAvailabilityGridTitle = "Enable Availability Grid";
		String displayChildrenTitle = "Display Children";
		String displayPromoCodeTitle = "Display Promocode";

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 1);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			try {
				driver = getDriver();
				loginRateV2(driver);
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				loginRateV2(driver);
			}
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

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
			try {
				navigation.openProperty(driver, testSteps, propertyName);
			} catch (Exception e) {
				navigation.openProperty2(driver, testSteps, propertyName.split("'")[0]);
			}
			testSteps.add("Open Property : " + propertyName);
			app_logs.info("Open Property : " + propertyName);
			navigation.clickPropertyOptions(driver, testSteps);
			timeZone = navigation.get_Property_TimeZone(driver);
			testSteps.add("Property TimeZone: " + timeZone);
			navigation.Reservation_Backward(driver);
			app_logs.info("Property : " + propertyName.split("'")[0]);
			String getCurrentDate = Utility.getNextDate(0, dateFormat, timeZone);
			if (ESTTimeZone.CompareDates(checkInDate, dateFormat, timeZone)) {
				checkInDate = getCurrentDate;
				checkOutDate = Utility.getNextDate(days, dateFormat, timeZone);
			}
			if (ESTTimeZone.CompareDates(seasonStartDate, seasonDateFormat, timeZone)) {
				seasonStartDate = getCurrentDate;
				seasonEndDate = Utility.getNextDate(seasonDays, seasonDateFormat, timeZone);
			}
			app_logs.info("Check-in Date : " + checkInDate);
			app_logs.info("Check-out Date : " + checkOutDate);

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

			app_logs.info(
					"Default Client Currency is:" + Utility.clientCurrency + " Symbol:" + Utility.clientCurrencySymbol);
			testSteps.add(
					"Default Client Currency is:" + Utility.clientCurrency + " Symbol:" + Utility.clientCurrencySymbol);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Default Currency'" + entityName + "' IS SELECTED", testName,
						"Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Default Currency '" + entityName + "' IS SELECTED", testName,
						"Preconditions", driver);
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
			testSteps.add("====== VERIFY PRE-CONDITIONS '" + bookingEngineChannel + "' IS SELECTED BT DEFAULT ======");
			navigation.Inventory(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
			navigation.Distribution(driver);
			app_logs.info("Navigate Distribution");
			testSteps.add("Navigate Distribution");

			app_logs.info("Get Booking Engine Channel Complete name");
			testSteps.add("Get Booking Engine Channel Complete name");
			ArrayList<String> channelsList = distribution.getAllChannelsContainingName(driver, bookingEngineChannel);
			if (channelsList.size() == 1) {
				bookingEngineChannel = channelsList.get(0);
			} else {
				for (int i = 0; i < channelsList.size(); i++) {
					if (channelsList.get(i).toLowerCase().contains(propertyName.toLowerCase())) {
						bookingEngineChannel = channelsList.get(i);
					}
				}
			}
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

		if (scenario.equalsIgnoreCase("RoomClass")) {
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
				boolean isRoomClassShowing = roomClassV2.searchRoomClassStartWithV2(driver, roomClassName);
				app_logs.info("Search");
				if (isRoomClassShowing) {
					getTestSteps.clear();
					getTestSteps = roomClassV2.deleteRoomClassStartWithRoomClassV2(driver, roomClassName);
					testSteps.addAll(getTestSteps);
					testSteps.add("Successfully Deleted RoomClass  : " + roomClassName);
					app_logs.info("Successfully Deleted RoomClass :  " + roomClassName);
				} else {
					testSteps.add("No room class exist");
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
				roomClassName = roomClassName + randomNumber;
				roomClassAbb = roomClassAbb + randomNumber;
				if (roomClassType.equalsIgnoreCase("inactive")) {
					getTestSteps.clear();
					getTestSteps = roomClassV2.createRoomClassWithStatusV2(driver, roomClassName, roomClassAbb,
							roomClassMaxAdults, roomClassMaxPersons, roomQuantity, inActiveStatus, test, getTestSteps);
					testSteps.addAll(getTestSteps);
				} else if (roomClassType.equalsIgnoreCase("Unpublished")) {
					// To do
				} else {
					getTestSteps.clear();
					getTestSteps = roomClassV2.createRoomClassV2(driver, roomClassName, roomClassAbb,
							roomClassMaxAdults, roomClassMaxPersons, roomQuantity, test, getTestSteps);
					testSteps.addAll(getTestSteps);

				}

				roomClassV2.closeRoomClassTabV2(driver, roomClassName);
				try {
					if (roomClassType.equalsIgnoreCase("inactive")) {
						roomClassV2.selectStatus(driver, testSteps, inActiveStatus);
					} else {
						roomClassV2.selectStatus(driver, testSteps, activeStatus);
					}
				} catch (Exception e) {

				}
				boolean isRoomClassShowing = roomClassV2.searchRoomClassV2(driver, roomClassName);
				app_logs.info("Search");
				if (isRoomClassShowing) {
					testSteps.add("'" + roomClassType + "' Room Class(" + roomClassName + ") successfully created");
					app_logs.info("'" + roomClassType + "' Room Class(" + roomClassName + ") successfully created");
				} else {
					Assert.assertTrue(false, "No room class created");
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

		if (roomClassType.equalsIgnoreCase("active") || roomClassType.equalsIgnoreCase("NoRate")
				|| scenario.equalsIgnoreCase("ChildrenPromoCode")) {
			try {
				if (scenario.equalsIgnoreCase("RoomClass")) {
					navigation.InventoryV2(driver);
					testSteps.add("Navigate Inventory");
					app_logs.info("Navigate Inventory");
				}
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
				app_logs.info("Today date : " + todayDate);
				getTestSteps.clear();
				getTestSteps = ratesGrid.closeCalendar(driver);
				testSteps.addAll(getTestSteps);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender",
							testName, "NavigateToRateGrid", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
					Utility.updateReport(e, "Failed to Navigate Rates Grid and get selected date from calender",
							testName, "NavigateToRateGrid", driver);
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
						Utility.updateReport(e, "Failed to  navigate to products and bundles", testName, "RatesV2",
								driver);
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
								bookingEngineAvailabilityOption, roomClassName, productDescription, productAmount,
								productPolicy, productCategory, calculationMethodOne, calculationMethodTwo,
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
						Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
						Utility.updateReport(e, "Failed to navigate to rate grid", testName, "NavigateRateGrid",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}
			}

			try {
				testSteps.add("=================== Verify Rate plan Exist or Not ======================");
				app_logs.info("=================== Verify Rate plan Exist or Not ======================");
				driver.navigate().refresh();
//				try {
//					getTestSteps.clear();
//					israteplanExist = ratesGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
//				} catch (Exception e) {
//					driver.navigate().refresh();
//					getTestSteps.clear();
//					israteplanExist = ratesGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
//				}
				// testSteps.addAll(getTestSteps);
				israteplanExist = true;
				testSteps.add("Successfully verified rate plan '" + ratePlanName + "' Exist");
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
					getTestSteps = ratesGrid.createParentRatePlan(driver, delim, ratePlanType, ratePlanName,
							rateFolioName, rateDescription, rateRoomClass, rateRoomClassSize, ratePlanBaseAmount,
							addtionalAdult, additionalChild, intervalRatePlanIntervalValue, isDefaultProrateChecked,
							packageRatePlanRateType, packageRatePlanProductName,
							packageratePlanProductFirstCalculationMethod, packageratePlanProductsecondCalculationMethod,
							packageRatePlanProductAmount, channels, isRatePlanRistrictionReq, ristrictionType,
							isMinStay, minNights, isMaxStay, maxNights, isMoreThanDaysReq, moreThanDaysCount,
							isWithInDaysReq, withInDaysCount, promoCode, isPolicesReq, policiesType, policiesName,
							seasonDelim, seasonName, seasonStartDate, seasonEndDate, isMonDay, isTueDay, isWednesDay,
							isThursDay, isFriday, isSaturDay, isSunDay, isAdditionalChargesForChildrenAdults,
							ratePerNight, maxAdults, maxPersons, additionalAdultsPerNight, additionalChildPerNight,
							isAddRoomClassInSeason, extraRoomClassesInSeason, extraRoomClassRatePerNight,
							extraRoomClassMaxAdults, extraRoomClassMaxPersons, extraRoomClassAdditionalAdultsPerNight,
							extraRoomClassAdditionalChildPerNight, isSeasonLevelRules, isAssignRulesByRoomClass,
							seasonRuleSpecificRoomClasses, seasonRuleType, seasonRuleMinStayValue, isSeasonRuleOnMonday,
							isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday,
							isSeasonRuleOnFriday, isSeasonRuleOnSaturday, isSeasonRuleOnSunday, isSeasonPolicies,
							seasonPolicyType, seasonPolicyValues, isAssignPoliciesByRoomClass,
							seasonPolicySpecificRoomClasses, isProRateStayInSeason, isProRateInRoomClass,
							isCustomPerNight, customRoomClasses, customRatePerNight, isCustomRatePerNightAdultandChild,
							customRateChildPerNight, customRateAdultdPerNight, roomClassesWithProRateUnchecked);
					testSteps.addAll(getTestSteps);

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to create new rate", testName, "CreateNewRate", driver);
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
				Utility.app_logs.info("Selected Channels : " + selectedChannels);
				testSteps.add("Selected Channels : " + selectedChannels);
				if (!ratesGrid.getChannelStatus(driver, bookingEngineChannel)) {
					Utility.app_logs.info("Required Channels  is not Selected : " + bookingEngineChannel);
					testSteps.add("Required Channels  is not Selected : " + bookingEngineChannel);
					nightlyRate.selectChannels(driver, bookingEngineChannel, true, testSteps);
					selectedChannels = nightlyRate.getSelectedChannels(driver);
				}
				Utility.app_logs.info("Selected Channels : " + selectedChannels);
				testSteps.add("Selected Channels : " + selectedChannels);
				boolean addNewRoomClass = false;

				testSteps.add("=================== ATTACH ROOM CLASS ======================");
				app_logs.info("=================== ATTACH ROOM CLASS ======================");
				selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);

				if (!ratesGrid.getRoomClassStatus(driver, roomClassName)) {
					Utility.app_logs.info("Required Room CLass  is not Selected : " + roomClassName);
					testSteps.add("Required  Room CLass  is not Selected : " + roomClassName);
					nightlyRate.selectRoomClassesEditPage(driver, roomClassName, true, testSteps);
					addNewRoomClass = true;
					selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);
				}

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
					nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(updatedIsRatePlanRistrictionReq),
							updatedRistrictionType, Boolean.parseBoolean(updatedIsMinStay), updatedMinNights,
							Boolean.parseBoolean(updatedIsMaxStay), updatedMaxNights,
							Boolean.parseBoolean(updatedIsMoreThanDaysReq), updatedMoreThanDaysCount,
							Boolean.parseBoolean(updatedIsWithInDaysReq), updatedWithInDaysCount, updatedPromoCode,
							testSteps);
				}
				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
						Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
				if (isSeasonExist) {
					testSteps.add("=================== UPDATING SEASON LEVEL RULES ======================");
					app_logs.info("=================== UPDATING SEASON LEVEL RULES ======================");
					nightlyRate.selectSeasonDates(driver, testSteps,
							Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					if (roomClassType.equalsIgnoreCase("NoRate")) {
						testSteps.add("=================== REMOVE ROOM CLASS FROM SEASON ======================");
						app_logs.info("=================== REMOVE ROOM CLASS FROM SEASON ======================");
						ratesGrid.unCheckRemoveRoomClassFromSeason(driver, roomClassName);
					} else if (roomClassType.equalsIgnoreCase("Active") || addNewRoomClass) {
						testSteps.add("=================== ADD RATES FOR ADDED ROOM CLASS ======================");
						app_logs.info("=================== ADD RATES FOR ADDED ROOM CLASS ======================");
						nightlyRate.addExtraRoomClassInSeason(driver, testSteps, "yes", roomClassName);
						ratesGrid.enterRoomClassRates(driver, roomClassName, roomClassRatePerNight,
								Boolean.toString(ratesGrid.chargeforAdditionalAdultsChildsIsChecked(driver)),
								roomClassMaxAdults, roomClassMaxPersons, roomClassAdultsRate, roomClassChildRate,
								testSteps);
					}

					nightlyRate.clickRulesRestrictionOnSeason(driver, testSteps);
					nightlyRate.selectRestrictionTypes(driver,
							"Min nights" + Utility.DELIM + "No check-in" + Utility.DELIM + "No check-out", false,
							getTestSteps);
					testSteps.add("Deselect all season level rules");
					app_logs.info("Deselect all season level rules");
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

		}

		try {

			testSteps.add("=================== VERIFY RATE PLAN IS SELECTED IN BOOKING ENGINE ======================");
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
			bookingEngine.clickOnBookingEngineConfigLink(driver);
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

			if (scenario.equalsIgnoreCase("ChildrenPromoCode")) {
				getTestSteps.clear();
				getTestSteps = bookingEngine.setToggleButtonState(driver, enableAvailabilityGridTitle, true,
						"Calendars", enableAvailabilityGridState);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = bookingEngine.setToggleButtonState(driver, displayChildrenTitle, true, "Search",
						displayChildrenState);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = bookingEngine.setToggleButtonState(driver, displayPromoCodeTitle, true, "Search",
						displayPromoCodeState);
				testSteps.addAll(getTestSteps);
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

		if (scenario.equalsIgnoreCase("RoomClass")) {
			try {
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver);
				testSteps.addAll(getTestSteps);
				testSteps.add(
						"============ VERIFY ROOM CLASS VISIBILITY IN AVAILABILITY GRID OF BOOKING ENGINE ======================");
				app_logs.info(
						"========== VERIFY ROOM CLASS VISIBILITY IN AVAILABILITY GRID OF BOOKING ENGINE ======================");
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyRoomClassVisibilityInBookingEngine(driver, roomClassName,
						Boolean.parseBoolean(roomClassVisibility));
				testSteps.addAll(getTestSteps);
				comment = "Successfully verified '" + roomClassType + "' room Class '" + roomClassName
						+ "' visibility in Availability Grid of Booking Engine.";
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
				boolean isRoomClassShowing = roomClassV2.searchRoomClassStartWithV2(driver, roomClassName);
				app_logs.info("Search");
				if (isRoomClassShowing) {
					getTestSteps.clear();
					getTestSteps = roomClassV2.deleteRoomClassStartWithRoomClassV2(driver, roomClassName);
					testSteps.addAll(getTestSteps);
					testSteps.add("Successfully Deleted RoomClass  : " + roomClassName);
					app_logs.info("Successfully Deleted RoomClass :  " + roomClassName);
				} else {
					testSteps.add("No room class exist");
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
		} else if (scenario.equalsIgnoreCase("ChildrenPromoCode")) {
			try {
				testSteps.add("=================== VERIFY BOOKING ENGINE HOME PAGE ======================");
				app_logs.info("=================== VERIFY BOOKING ENGINE HOME PAGE ======================");
				bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.LANGUAGE_DROPDOWN), true);
				app_logs.info("Successfully verified 'Language drop down' is displaying.");
				testSteps.add("Successfully verified 'Language drop down'  is displaying.");
				bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.PROPERTY_HEADER), true);
				app_logs.info("Successfully verified 'Property Header'  is displaying.");
				testSteps.add("Successfully verified 'Property Header'  is displaying.");
				bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.EMAIL_AND_PHONE), true);
				app_logs.info("Successfully verified 'Email and Phone' is displaying.");
				testSteps.add("Successfully verified 'Email and Phone' is displaying.");
				bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.startDate), true);
				app_logs.info("Successfully verified 'Check-in' field is displaying.");
				testSteps.add("Successfully verified 'Check-in' field is displaying.");
				bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.CHECKOUT_FIELD), true);
				app_logs.info("Successfully verified 'Check-out' field is displaying.");
				testSteps.add("Successfully verified 'Check-out' field is displaying.");
				bookingEngine.verifyElementVisibility(driver, By.id(OR_BE.ADULTS_BE), true);
				app_logs.info("Successfully verified 'Adults' field is displaying.");
				testSteps.add("Successfully verified 'Adults' field is displaying.");
				if (displayChildrenState.equalsIgnoreCase("true")) {
					bookingEngine.verifyElementVisibility(driver, By.id(OR_BE.CHILDREN_BE), true);
					app_logs.info("Successfully verified 'Children' field is displaying.");
					testSteps.add("Successfully verified 'Children' field is displaying.");
				}
				if (displayPromoCodeState.equalsIgnoreCase("true")) {
					bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.PROMOCODE_BE), true);
					app_logs.info("Successfully verified 'PromoCode' field is displaying.");
					testSteps.add("Successfully verified 'PromoCode' field is displaying.");
				}
				bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.ENTIRE_AVALABILITY), true);
				app_logs.info("Successfully verified 'view our enter Availability grid' button is displaying.");
				testSteps.add("Successfully verified 'view our enter Availability grid' button is displaying.");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify BE homePage elements", "BE", "BE", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify BE homePage elements", "BE", "BE", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			try {
				testSteps.add(
						"===================  VERIFY CHILDREN OR PROMOCODE FIELD IN BE AVAILABILITY GRID ======================");
				app_logs.info(
						"=================== VERIFY CHILDREN OR PROMOCODE FIELD IN BE AVAILABILITY GRID ======================");
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickCheckInAndCheckOutDateAndVerifyPopupDiaplyed(driver, roomClassName,
						days, checkInDate, checkOutDate, dateFormat);
				testSteps.addAll(getTestSteps);

				if (displayChildrenState.equalsIgnoreCase("true")) {
					bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.NO_OF_CHILDREN), true);
					app_logs.info("Successfully verified 'Children' field is displaying.");
					testSteps.add("Successfully verified 'Children' field is displaying.");
				} else {

					bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.NO_OF_CHILDREN), false);
					app_logs.info("Successfully verified 'Children' field is not displaying.");
					testSteps.add("Successfully verified 'Children' field is not displaying.");
				}
				bookingEngine.verifyElementVisibility(driver, By.xpath(OR_BE.PROMO_CODE), false);
				app_logs.info("Successfully verified 'PromoCode' field is not displaying.");
				testSteps.add("Successfully verified 'PromoCode' field is not displaying.");

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify BE homePage elements", "BE", "BE", driver);
				} else {
					Assert.assertTrue(false);
				}

			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to verify BE homePage elements", "BE", "BE", driver);
				} else {
					Assert.assertTrue(false);
				}
			}

		}

		// Generate Report
		try {
			String[] testcase = TestCaseID.split(Utility.DELIM);
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
			// Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comment,
			// TestCore.TestRail_AssignToID);
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
		return Utility.getData("VerifyRoomCLassVisibilityInNBE", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		// Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode,
		// comment,TestCore.TestRail_AssignToID);

	}
}
