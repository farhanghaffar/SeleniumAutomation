package com.innroad.inncenter.tests;


import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.DerivedRate;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

import bsh.util.Util;

public class AvailabilityVerificationInNBE extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String testName = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
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
	String BookingEngineTab="",InncenterTab="";

	@Test(dataProvider = "getData", groups = "BookingEngine")
	public void verifyAvailabilityInBookingEngine(String action,String propertyName, String channelName,
			String delim, String ratePlanName,String checkInDate ,String checkOutDate, String adults, String child, 
			String reservationRoomClassName,String isdaysCheck,String UpdateCategory,
			String UpdateTypes,String UpdateRatesType,String isUpdateRateByRoomClass,String nightlyRate_RatesUpdate,
			String additionalAdults_RatesUpdate,String additionalChild_RatesUpdate,String BaseType_RulesUpdate,String BaseRuleMinStayValue_RulesUpdate,String cases)
			throws Exception {
		HashMap<String,String>ratePlanData=Utility.getExcelData(System.getProperty("user.dir")+"\\test-data\\BookingEngineTestData.xlsx","CreateNightlyRatePlanV2");
		String FolioDisplayName=ratePlanData.get("FolioDisplayName");
		String Description=ratePlanData.get("Description");
		String Channels=ratePlanData.get("Channels");
		String RoomClasses=ratePlanData.get("RoomClasses");
		String isRatePlanRistrictionReq=ratePlanData.get("isRatePlanRistrictionReq");
		String RistrictionType=ratePlanData.get("RistrictionType");
		String isMinStay=ratePlanData.get("isMinStay");
		String MinNights=ratePlanData.get("MinNights"); 
		String isMaxStay=ratePlanData.get("isMaxStay"); 
		String MaxNights=ratePlanData.get("MaxNights"); 
		String isMoreThanDaysReq=ratePlanData.get("isMoreThanDaysReq"); 
		String MoreThanDaysCount=ratePlanData.get("MoreThanDaysCount"); 
		String isWithInDaysReq=ratePlanData.get("isWithInDaysReq"); 
		String WithInDaysCount=ratePlanData.get("WithInDaysCount"); 
		String PromoCode=ratePlanData.get("PromoCode"); 
		String isPolicesReq=ratePlanData.get("isPolicesReq"); 
		String PoliciesType=ratePlanData.get("PoliciesType"); 
		String PoliciesName=ratePlanData.get("PoliciesName");
		String SeasonName=ratePlanData.get("SeasonName");
		String SeasonStartDate=ratePlanData.get("SeasonStartDate"); 
		String SeasonEndDate=ratePlanData.get("SeasonEndDate");
		String isMonDay=ratePlanData.get("isMonDay");
		String isTueDay=ratePlanData.get("isTueDay");
		String isWednesDay=ratePlanData.get("isWednesDay");
		String isThursDay=ratePlanData.get("isThursDay");
		String isFriday=ratePlanData.get("isFriday");
		String isSaturDay=ratePlanData.get("isSaturDay");
		String isSunDay=ratePlanData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults=ratePlanData.get("isAdditionalChargesForChildrenAdults");
		String RatePerNight=ratePlanData.get("RatePerNight");
		String MaxAdults=ratePlanData.get("MaxAdults");
		String AdditionalAdultsPerNight=ratePlanData.get("AdditionalAdultsPerNight");
		String AdditionalChildPerNight=ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason=ratePlanData.get("isAddRoomClassInSeason");
		String ExtraRoomClassesInSeason=ratePlanData.get("ExtraRoomClassesInSeason");
		String ExtraRoomClassRatePerNight=ratePlanData.get("ExtraRoomClassRatePerNight");
		String ExtraRoomClassMaxAdults=ratePlanData.get("ExtraRoomClassMaxAdults");
		String ExtraRoomClassMaxPersons=ratePlanData.get("ExtraRoomClassMaxPersons");
		String ExtraRoomClassAdditionalAdultsPerNight=ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String ExtraRoomClassAdditionalChildPerNight=ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		String isSerasonLevelRules=ratePlanData.get("isSerasonLevelRules");
		String isAssignRulesByRoomClass=ratePlanData.get("isAssignRulesByRoomClass");
		String SeasonRuleSpecificRoomClasses=ratePlanData.get("SeasonRuleSpecificRoomClasses");
		String SeasonRuleType=ratePlanData.get("SeasonRuleType");
		String SeasonRuleMinStayValue=ratePlanData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnMonday=ratePlanData.get("isSeasonRuleOnMonday");
		String isSeasonRuleOnTuesday=ratePlanData.get("isSeasonRuleOnTuesday");
		String isSeasonRuleOnWednesday=ratePlanData.get("isSeasonRuleOnWednesday");
		String isSeasonRuleOnThursday=ratePlanData.get("isSeasonRuleOnThursday");
		String isSeasonRuleOnFriday=ratePlanData.get("isSeasonRuleOnFriday");
		String isSeasonRuleOnSaturday=ratePlanData.get("isSeasonRuleOnSaturday");
		String isSeasonRuleOnSunday=ratePlanData.get("isSeasonRuleOnSunday");
		String isSeasonPolicies=ratePlanData.get("isSeasonPolicies");
		String SeasonPolicyType=ratePlanData.get("SeasonPolicyType");
		String SeasonPolicyValues=ratePlanData.get("SeasonPolicyValues");
		String MaxPersons=ratePlanData.get("MaxPersons");
		
		
		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);
		app_logs.info("days: " + days);
		Utility.DELIM = "\\"+delim;
		testName = action;
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> scriptName = new ArrayList<>();
		ArrayList<String> testCategory = new ArrayList<>();
		ArrayList<String> testDescription = new ArrayList<>();

		String test_description = "";
		String test_catagory = "";

		test_description = "Verify ailability in booking engine . <br>";
		test_catagory = "Availability_Verification_In_InnCenter";
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
		DerivedRate derivedRate = new DerivedRate();
		Admin admin = new Admin();
		Login login = new Login();
		Properties properties = new Properties();

		BookingEngine bookingEngine = new BookingEngine();
		String dateFormat = Utility.dayMonthYear_DateFormat;
		String seasonDateFormat = Utility.dayMonthYear_DateFormat;
		String entityName = "ConfigureBookingEngine";
		String calendarTodayDay = "today";
		String todayDate = null;
		String timeZone = "";
		int numberOfProperties = 0;
		int daysBeforeCheckInDate = 0;
		boolean israteplanExist = false;
		String ratePlanPromoCode = null;
		
		boolean isPromocode = false;
		boolean ratePlanAvailableInBE = true;
		ArrayList<String> availabilityInRatesBefore = new ArrayList<String>();
		String roomReservedValueBefore = "";
		String roomAvailableValueBefore = "";
		String roomTotalValueBefore = "";
		ArrayList<String> selectedChannels = null;
		ArrayList<String> selectedRoomClasses = null;
		boolean selectedRatePlanDefaultStatus = false;
		String selectedRatePlanFolioName = null;
		String dateWithoutSeason = "";
		
		ArrayList<String> roomClassList = new ArrayList<>();
		HashMap<String, ArrayList<String>[]> roomClassDataValuesForBookingEngineChannel = new HashMap<String, ArrayList<String>[]>();
	
		String[] splitDays = isdaysCheck.split( Utility.DELIM);
		String isSundayCheck = splitDays[0];
		String isMondayCheck = splitDays[1];
		String isTuesdayChecked = splitDays[2];
		String isWednesdayChecked = splitDays[3];
		String isThursdayChecked = splitDays[4];
		String isFridayChecked = splitDays[5];
		String isSaturdayChecked = splitDays[6];
		String[] channelArray = channelName.split(Utility.DELIM);
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
		
		try {
			caseId.clear();
			statusCode.clear();
			String[] casesList = cases.split(",");
			for (int i = 0; i < casesList.length; i++) {
				caseId.add(casesList[i]);
				statusCode.add("4");
			}
			driver = getDriver();
			login_Group(driver);
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
			String getCurrentDate = Utility.getNextDate(0, dateFormat, timeZone);
			if (ESTTimeZone.CompareDates(checkInDate, dateFormat, timeZone)) {
				checkInDate = getCurrentDate;
				checkOutDate = Utility.getNextDate(days, dateFormat, timeZone);

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
				Utility.updateReport(e, "Failed to get Default Currency", testName,
						"Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Default Currency", testName,
						"Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			app_logs.info("====== VERIFY PRE-CONDITIONS '" + channelName + "' IS SELECTED BT DEFAULT ==");
			testSteps.add("====== VERIFY PRE-CONDITIONS '" + channelName + "' IS SELECTED BT DEFAULT ======");
			//navigation.Inventory(driver);
			navigation.Inventory_BackWard_Admin(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
			navigation.Distribution(driver);
			app_logs.info("Navigate Distribution");
			testSteps.add("Navigate Distribution");
		
			app_logs.info("Get Booking Engine Channel Complete name");
			testSteps.add("Get Booking Engine Channel Complete name");
			ArrayList<String> channelsList = distribution.getAllChannelsContainingName(driver, channelName);
			if(channelsList.size()==1) {
				channelName = channelsList.get(0);
			}else {
				for(int i = 0 ; i < channelsList.size() ;i++) {
					if(channelsList.get(i).toLowerCase().contains(propertyName.toLowerCase())){
						channelName = channelsList.get(i);
					}
				}
			}
			app_logs.info("Booking Engine channel : '" + channelName + "'");
			testSteps.add("Booking Engine channel : '" + channelName + "'");
			boolean distribute = distribution.getChannelDistributeValue(driver, channelName);
			if (distribute) {
				app_logs.info("'" + channelName + "' Distribute is selected by default");
				testSteps.add("'" + channelName + "' Distribute is selected by default");
			} else {
		
				app_logs.info("'" + channelName + "' Distribute is not selected by default");
				testSteps.add("'" + channelName + "' Distribute is not selected by default");
			}
			Assert.assertTrue(distribute,
					"Failed '" + channelName + "' Distribute is not selected by default");
		
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify Precondition '" + channelName + "' IS SELECTED BT DEFAULT ",
						testName, "Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e,
						"Failed to verify Preconditions '" + channelName + "' IS SELECTED BT DEFAULT ",
						testName, "Preconditions", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			testSteps.add("=================== Verify Rate plan Exist or Not ======================");
			app_logs.info("=================== Verify Rate plan Exist or Not ======================");
			navigation.Inventory(driver);
			navigation.ratesGrid(driver,testSteps);
			driver.navigate().refresh();
			try{
				getTestSteps.clear();
				israteplanExist = rateGrid.searchForRatePlanExistingOrNot(driver, getTestSteps,ratePlanName);
			} catch(Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				israteplanExist = rateGrid.searchForRatePlanExistingOrNot(driver, getTestSteps,ratePlanName);
			}
			testSteps.addAll(getTestSteps);
			app_logs.info("israteplanExist : " + israteplanExist);
		} catch (Exception e) {
			e.printStackTrace();
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
			try {

				testSteps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
				app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

				rateGrid.clickRateGridAddRatePlan(driver);
				rateGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", testSteps);

				testSteps.add(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
				app_logs.info(
						"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

				ratePlanName = ratePlanName + Utility.generateRandomString();
				FolioDisplayName = FolioDisplayName + Utility.generateRandomString();

				nightlyRate.enterRatePlanName(driver, ratePlanName, testSteps);
				nightlyRate.enterRateFolioDisplayName(driver, FolioDisplayName, testSteps);
				nightlyRate.enterRatePlanDescription(driver, Description, testSteps);

				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", ratePlanName, testSteps);

				testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
				app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

				nightlyRate.selectChannels(driver, Channels, true, testSteps);
				String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

				testSteps.add("=================== SELECT ROOM CLASSES ======================");
				app_logs.info("=================== SELECT ROOM CLASSES ======================");
				nightlyRate.selectRoomClasses(driver, RoomClasses, true, testSteps);
				String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

				nightlyRate.selectRestrictions(driver, Boolean.parseBoolean(isRatePlanRistrictionReq), RistrictionType,
						Boolean.parseBoolean(isMinStay), MinNights, Boolean.parseBoolean(isMaxStay), MaxNights,
						Boolean.parseBoolean(isMoreThanDaysReq), MoreThanDaysCount,
						Boolean.parseBoolean(isWithInDaysReq), WithInDaysCount, PromoCode, testSteps);

				String restrictionsSummary = nightlyRate.getRestrictionsToQualifyRate(driver, testSteps);
				nightlyRate.clickNextButton(driver, testSteps);

				nightlyRate.verifyTitleSummaryValue(driver, "Restrictions", restrictionsSummary, testSteps);

				nightlyRate.selectPolicy(driver, PoliciesType, PoliciesName, Boolean.parseBoolean(isPolicesReq),
						testSteps);

				HashMap<String, String> allPolicyDesc = nightlyRate.getAllPolicyDescriptions(driver, PoliciesName,
						Boolean.parseBoolean(isPolicesReq), testSteps);
				nightlyRate.clickNextButton(driver, testSteps);
				nightlyRate.verifyPolicyTitleSummaryValue(driver, PoliciesName, allPolicyDesc,
						Boolean.parseBoolean(isPolicesReq), testSteps);
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

			try {
				testSteps.add("=================== CREATE SEASON ======================");
				app_logs.info("=================== CREATE SEASON ======================");

				nightlyRate.clickCreateSeason(driver, testSteps);

				nightlyRate.createSeason(driver, testSteps, SeasonStartDate, SeasonEndDate, SeasonName, isMonDay,
						isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay,
						isAdditionalChargesForChildrenAdults, RatePerNight, MaxAdults, MaxPersons,
						AdditionalAdultsPerNight, AdditionalChildPerNight, isAddRoomClassInSeason,
						ExtraRoomClassesInSeason, ExtraRoomClassRatePerNight, ExtraRoomClassMaxAdults,
						ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
						ExtraRoomClassAdditionalChildPerNight, isAssignRulesByRoomClass, isSerasonLevelRules,
						SeasonRuleSpecificRoomClasses, SeasonRuleType, SeasonRuleMinStayValue, isSeasonRuleOnMonday,
						isSeasonRuleOnTuesday, isSeasonRuleOnWednesday, isSeasonRuleOnThursday, isSeasonRuleOnFriday,
						isSeasonRuleOnSaturday, isSeasonRuleOnSunday, SeasonPolicyType, SeasonPolicyValues,
						isSeasonPolicies);

				nightlyRate.clickCompleteChanges(driver, testSteps);
				nightlyRate.clickSaveAsActive(driver, testSteps);
				Wait.wait30Second();
				testSteps.add("=================== RATE PLAN CREATED ======================");
				app_logs.info("=================== RATE PLAN CREATED ======================");
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
	if(UpdateCategory.equalsIgnoreCase("VerifyAgeRangeToolTip")) {
		testSteps.add("=================== Checking the age range tooltips on home page calendar ======================");
		app_logs.info("=================== Checking the age range tooltips on home page calendar ======================");
		navigation.navigate_To_NBE(driver, testSteps, propertyName);
		

		getTestSteps.clear();
		getTestSteps = bookingEngine.toggleDisplayAgeRangeToggleButton(driver);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		getTestSteps = bookingEngine.enterAdultAndChildrenValues(driver,adults,child);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		getTestSteps = bookingEngine.saveAgeRangeValues(driver);
		testSteps.addAll(getTestSteps);
		
		bookingEngine.clickWelcomePageLink(driver);
		testSteps.add("Opened the Booking Engine");
		app_logs.info("Opened the Booking Engine");

		testSteps.add("=================== Checking the age range tooltips on home page calendar ======================");
		app_logs.info("=================== Checking the age range tooltips on home page calendar ======================");

		getTestSteps.clear();
		getTestSteps = bookingEngine.verifyAdultAgeRangeValues(driver,adults);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		getTestSteps = bookingEngine.verifyChildrenAgeRangeValues(driver,child);
		testSteps.addAll(getTestSteps);
		

	}
	if(UpdateCategory.equalsIgnoreCase("RatesDisplayToggleOff")) {
		testSteps.add("=================== Verify the rate displaying in the home page calendar by switching toggle off  ======================");
		app_logs.info("=================== Verify the rate displaying in the home page calendar by switching toggle off  ======================");
			
			navigation.navigate_To_NBE(driver, testSteps, propertyName);
			
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
			getTestSteps = bookingEngine.hoverOnCheckInDateAndVerifyDates(driver,checkInDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = bookingEngine.hoverOnCheckOutDateAndVerifyDates(driver,checkOutDate);
			testSteps.addAll(getTestSteps);

	}
		
	if(UpdateCategory.equalsIgnoreCase("VerifyRoomClasses")) {
		testSteps.add(
				"===================  VERIFY ROOM CLASSES BY NAVIGATING THROUGH AVAILABILITY GRID IN BOOKING ENGINE ======================");
		app_logs.info(
				"===================  VERIFY ROOM CLASSES BY NAVIGATING THROUGH AVAILABILITY GRID IN BOOKING ENGINE ======================");
		navigation.navigate_To_NBE(driver, testSteps, propertyName);
		
		testSteps.add("============== OPEN BOOKING ENGINE ======================");
		app_logs.info("============== OPEN BOOKING ENGINE ======================");
		bookingEngine.clickWelcomePageLink(driver);
		testSteps.add("Opened the Booking Engine");
		app_logs.info("Opened the Booking Engine");

		getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver, checkInDate);
		testSteps.addAll(getTestSteps);

		testSteps.add("Successfully navigated to availability grid page.");
		app_logs.info("Successfully navigated to availability grid page.");
			
		

		testSteps.add(
				"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
		app_logs.info(
				"=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
		String[] RoomClass=reservationRoomClassName.split(Utility.DELIM);

		if (RoomClass.length > 0) {
			for (int index = 0; index < RoomClass.length; index++) {
				String roomClassName = RoomClass[index].toString();
				app_logs.info("Expected : " + roomClassName);
				getTestSteps = bookingEngine.verifyRoomClassesByAdvancedOption(driver, roomClassName);
				testSteps.addAll(getTestSteps);
			}
		}
		testSteps.add("=================== VERIFY CHECK OUT TEXT IS SHOWING  ======================");
		app_logs.info("=================== VERIFY CHECK OUT TEXT IS SHOWING ======================");
		
		testSteps.add("Click on Check In Date : "+ checkInDate);
		app_logs.info("Click on Check In Date : "+ checkInDate);

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickOnCheckInDate(driver, checkInDate, checkOutDate,
					RoomClass[0], dateFormat, days);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("Click on Check Out Date : "+ checkOutDate);
			app_logs.info("Click on Check Out Date : "+ checkOutDate);

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickOnCheckOutDate(driver, checkInDate, checkOutDate,
					RoomClass[0], dateFormat, days, BookingEngine.checkInDateIndex);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = bookingEngine.verifyCheckOutTextIsShowing(driver);
			testSteps.addAll(getTestSteps);

			String checkoutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkOutDate, "dd/MM/yyyy",
					"dd/MM/yyyy", days + 2, timeZone);
			
			testSteps.add("=================== VERIFY CHECK OUT TEXT IS SHOWING BY MOVING CURSOR AND EXTENDING CHECKOUT DATE  ======================");
			app_logs.info("=================== VERIFY CHECK OUT TEXT IS SHOWING BY MOVING CURSOR AND EXTENDING CHECKOUT DATE ======================");
			
			testSteps.add("Extended checkOut Date : "+ checkoutDate);
			app_logs.info("Extended checkOut Date : "+ checkoutDate);

			int newdays = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkoutDate);
			
			testSteps.add("Days : "+ newdays);
			app_logs.info("Days : "+ newdays);

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickOnCheckOutDate(driver, checkInDate, checkoutDate,
					RoomClass[0], dateFormat, newdays, BookingEngine.checkInDateIndex);
			testSteps.addAll(getTestSteps);

			
			getTestSteps.clear();
			getTestSteps = bookingEngine.verifyCheckOutTextIsShowing(driver);
			testSteps.addAll(getTestSteps);	
	
	}
	if(UpdateCategory.equalsIgnoreCase("Availability")) {
		navigation.Inventory(driver, testSteps);
		navigation.ratesGrid(driver);
		String[] splitRoomClass = reservationRoomClassName.split(Utility.DELIM);
		
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
						+ " in Availability tab for " + channelName + "==========");
				testSteps.add("==========Getting the availability for the room class " + splitRoomClass[index]
						+ " in Availability tab for " + channelName + "==========");
				

				availabilityDataValuesMap = ratesGrid.getAvailabilityForGivenDays(driver, splitRoomClass[index],
						channelName, checkInDate, checkOutDate, listOfDay, testSteps);
				bulkAvailabilityUpdateValuesMap.put(roomClassName, availabilityDataValuesMap);

			}
		}
		navigation.navigate_To_NBE(driver, testSteps, propertyName);
		
		testSteps.add("============== OPEN BOOKING ENGINE ======================");
		app_logs.info("============== OPEN BOOKING ENGINE ======================");
		bookingEngine.clickWelcomePageLink(driver);
		testSteps.add("Opened the Booking Engine");
		app_logs.info("Opened the Booking Engine");
		
		getTestSteps.clear();
		getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver,checkInDate);
		testSteps.addAll(getTestSteps);
		
		testSteps.add("=================== VERIFICATION OF AVAILABILITY VALUES IN BOOKING ENGINE ======================"); 
		app_logs.info("=================== VERIFICATION OF AVAILABILITY IN BOOKING ENGINE ======================"); 
		
		
		for (Map.Entry<String, HashMap<String, String>> entry : bulkAvailabilityUpdateValuesMap.entrySet()) {
			String className = entry.getKey();
			HashMap<String, String> roomClassDataValuesWithRules = entry.getValue();
			getTestSteps.clear();
			getTestSteps = bookingEngine.verifyAvailabilityDataInBookingEngine(driver, className,
					checkInDate, checkOutDate, dateFormat, roomClassDataValuesWithRules);
			testSteps.addAll(getTestSteps);
		}

	}
	if(UpdateCategory.equalsIgnoreCase("No Rate"))
	{
		
			navigation.Inventory(driver, testSteps);
			navigation.ratesGrid(driver);
			testSteps.add("Navigated to rateGrid");
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
			
			navigation.navigate_To_NBE(driver, testSteps, propertyName);
			
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
			testSteps.add("Opened the Booking Engine Configration");
			app_logs.info("Opened the Booking Engine Configration");
			testSteps.add("============== OPEN BOOKING ENGINE ======================");
			app_logs.info("============== OPEN BOOKING ENGINE ======================");
			bookingEngine.clickWelcomePageLink(driver);
			testSteps.add("Opened the Booking Engine");
			app_logs.info("Opened the Booking Engine");
	
			testSteps.add("=================== VERIFICATION OF $~~ IS SHOWING IN BOOKING ENGINE ======================"); 
			app_logs.info("=================== VERIFICATION OF $~~ IS SHOWING IN BOOKING ENGINE ======================"); 
			
			getTestSteps.clear();
			getTestSteps = bookingEngine.verifyAmountIsNotShowingWithoutRatePlan(driver,dateWithoutSeason);
			testSteps.addAll(getTestSteps);

	}
	if(UpdateCategory.equalsIgnoreCase("Rates")) {
		if(UpdateTypes.equalsIgnoreCase("BulkUpdate")||UpdateTypes.equalsIgnoreCase("Overide")) {
			testSteps.add("=================== BULK UPDATE OR OVERRIDE RATES IN INNCENTER=================== ");
			app_logs.info("=================== BULK UPDATE OR OVERRIDE RATES IN INNCENTER=================== ");
			
			  navigation.Inventory(driver, testSteps);
			  navigation.ratesGrid(driver);
			  testSteps.add("Navigated to rateGrid");
			  rateGrid.bulkUpdateOverideRates(driver, delim, UpdateTypes, checkInDate,
			  checkOutDate, isSunDay, isMonDay, isTueDay,
			  isWednesDay, isThursDay, isFriday,
			  isSaturDay,ratePlanName, reservationRoomClassName, channelName,
			  UpdateRatesType, isUpdateRateByRoomClass, nightlyRate_RatesUpdate,
			  additionalAdults_RatesUpdate, additionalChild_RatesUpdate, testSteps);
			
		}else if(UpdateTypes.equalsIgnoreCase("Season Level")) {
			testSteps.add("=================== SEASON LEVEL RATES CHANGE=================== ");
			app_logs.info("=================== SEASON LEVEL RATES CHANGE=================== ");
			navigation.Inventory(driver, testSteps);
			navigation.ratesGrid(driver);

			rateGrid.clickRatePlanArrow(driver, testSteps);
			rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			derivedRate.expandReduceDerivedratePlans(driver, false, testSteps);
			rateGrid.clickEditIcon(driver, testSteps);

			nightlyRate.switchCalendarTab(driver, testSteps);
			
			ArrayList<String> allSeasonsDates = nightlyRate.getAllSeasonsDates(driver);
			Utility.app_logs.info("All Season Dates : " + allSeasonsDates);
			testSteps.add("All Season Dates : " + allSeasonsDates);

			nightlyRate.clickSaveRatePlanButton(driver, testSteps);

			String startSeasonDate = allSeasonsDates.get(0);
			String endSeasonDate = allSeasonsDates.get(allSeasonsDates.size() - 1);

			ArrayList<String> allDatesBW = Utility.getAllDatesBetweenCheckInOutDates(startSeasonDate, endSeasonDate);
			
			nightlyRate.selectSeasonDates(driver, testSteps, allDatesBW);
			nightlyRate.clickEditThisSeasonButton(driver, testSteps);
//
//		nightlyRate.selectAdditionalChargesForChildrenAdults(driver, test_steps,
//				ModifyisAdditionalChargesForChildrenAdults);

			nightlyRate.enterRoomClassSeasonRate(driver, testSteps, reservationRoomClassName,
					nightlyRate_RatesUpdate, "NA", "4",
					"6", additionalAdults_RatesUpdate, additionalChild_RatesUpdate);

			nightlyRate.clickSaveSason(driver, testSteps);

			nightlyRate.clickSaveRatePlanButton(driver, testSteps);
		}
	
	}
	if(UpdateCategory.equalsIgnoreCase("Rules")) {
		
		if(UpdateTypes.equalsIgnoreCase("BulkUpdate")||UpdateTypes.equalsIgnoreCase("Overide")) {
			
			testSteps.add("===================GETTING RULES FROM INNCENTER AFTER UPDATE=================== ");
			app_logs.info("===================GETTING RULES FROM INNCENTER AFTER UPDATE=================== ");
				
				navigation.Inventory(driver, testSteps);
				navigation.ratesGrid(driver);
				rateGrid.bulkUpdateOverideRules(driver, delim, UpdateTypes, checkInDate, checkOutDate, 
						isSunDay, isMonDay, isTueDay,isWednesDay, isThursDay, isFriday,isSaturDay, 
						ratePlanName, reservationRoomClassName, channelName, 
						BaseType_RulesUpdate, BaseRuleMinStayValue_RulesUpdate, testSteps);
				
		}else if(UpdateTypes.equalsIgnoreCase("Season Level")) {
			testSteps.add("=================== SEASON LEVEL RULES CHANGE=================== ");
			app_logs.info("=================== SEASON LEVEL RULES CHANGE=================== ");
		
				navigation.Inventory(driver, testSteps);
				navigation.ratesGrid(driver);

				rateGrid.clickRatePlanArrow(driver, testSteps);
				rateGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				derivedRate.expandReduceDerivedratePlans(driver, false, testSteps);
				rateGrid.clickEditIcon(driver, testSteps);

				nightlyRate.switchCalendarTab(driver, testSteps);
				
				ArrayList<String> allSeasonsDates = nightlyRate.getAllSeasonsDates(driver);
				Utility.app_logs.info("All Season Dates : " + allSeasonsDates);
				testSteps.add("All Season Dates : " + allSeasonsDates);

				nightlyRate.clickSaveRatePlanButton(driver, testSteps);

				String startSeasonDate = allSeasonsDates.get(0);
				String endSeasonDate = allSeasonsDates.get(allSeasonsDates.size() - 1);

				ArrayList<String> allDatesBW = Utility.getAllDatesBetweenCheckInOutDates(startSeasonDate, endSeasonDate);
				
				nightlyRate.selectSeasonDates(driver, testSteps, allDatesBW);
				nightlyRate.clickEditThisSeasonButton(driver, testSteps);


				nightlyRate.clickRulesRestrictionOnSeason(driver, testSteps);

				nightlyRate.clickAssignRulesByRoomClass(driver, testSteps, "Yes");

				nightlyRate.enterSeasonLevelRules(driver, testSteps, "Yes",
						"Yes", reservationRoomClassName, BaseType_RulesUpdate,
						BaseRuleMinStayValue_RulesUpdate, isMonDay, isTueDay,isWednesDay, isThursDay, isFriday,isSaturDay, isSunDay);

				nightlyRate.clickSaveSason(driver, testSteps);

				nightlyRate.clickSaveRatePlanButton(driver, testSteps);
	
		}
	}
	
	if(action.contains("Rates")||action.contains("Rules")) {
		
		testSteps.add(
				"=================== GET RATES ,NO CHECK IN, NO CHECK OUT AND MIN STAY DATA RULE IN INNCENTER ======================");
		app_logs.info(
				"=================== GET RATES, NO CHECK IN, NO CHECK OUT AND MIN STAY DATA IN INNCENTER ======================");
	
				navigation.Inventory(driver, testSteps);
				navigation.ratesGrid(driver);
				testSteps.add("=================== GET '" + reservationRoomClassName + "' DATA ======================");
				app_logs.info("=================== GET '" + reservationRoomClassName + "' DATA ======================");
				
				ratesGrid.expandRoomClass(driver, testSteps, reservationRoomClassName);
				ArrayList<String>[] arrayOfRoomClassesWithRules = new ArrayList[4];
				ArrayList<String> selectedRoomClassDataValues = ratesGrid.getChannelRatesForTheGivenDates(driver,checkInDate, checkOutDate,
						reservationRoomClassName, channelName);
				arrayOfRoomClassesWithRules[0] = selectedRoomClassDataValues;
				ratesGrid.expandChannel(driver, testSteps, reservationRoomClassName, channelName);
				ArrayList<String> minStayRuleValues=ratesGrid.getMinStayValuesOfRoomClassBetweenSelectedDateRange(driver,
						dateFormat, checkInDate, checkOutDate, reservationRoomClassName, channelName, "Min Stay");
				arrayOfRoomClassesWithRules[1] = minStayRuleValues;

				ArrayList<String> checkInRuleValues = ratesGrid.getRuleDataValueForCheckInCheckOut1(driver,checkInDate, checkOutDate,
						reservationRoomClassName, channelName, "No Check In");
				arrayOfRoomClassesWithRules[2] = checkInRuleValues;
				
				ArrayList<String> checkOutRuleValues = ratesGrid.getRuleDataValueForCheckInCheckOut1(driver,checkInDate, checkOutDate,
						reservationRoomClassName, channelName, "No Check Out");
				arrayOfRoomClassesWithRules[3] = checkOutRuleValues;
				
				roomClassDataValuesForBookingEngineChannel.put(reservationRoomClassName, arrayOfRoomClassesWithRules);
				
		
		/*	try {

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
									+ " in Availability tab for " + channelName + "==========");
							testSteps.add("==========Getting the availability for the room class " + splitRoomClass[index]
									+ " in Availability tab for " + channelName + "==========");

							availabilityDataValuesMap = ratesGrid.getAvailabilityOnBaseofDays(driver, splitRoomClass[index],
									channelName, checkInDate, checkOutDate, listOfDay, timeZone, isAvailability[index],
									true, testSteps);
							bulkAvailabilityUpdateValuesMap.put(roomClassName, availabilityDataValuesMap);

						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(scriptName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to click yes update button", testName, "BulkUpdate", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					e.printStackTrace();
					if (Utility.reTry.get(scriptName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to click yes update button", testName, "BulkUpdate", driver);
					}
				}
				

			
		*/

		
	
			navigation.navigate_To_NBE(driver, testSteps, propertyName);
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
			testSteps.add("Opened the Booking Engine Configration");
			app_logs.info("Opened the Booking Engine Configration");
			testSteps.add("============== OPEN BOOKING ENGINE ======================");
			app_logs.info("============== OPEN BOOKING ENGINE ======================");
			bookingEngine.clickWelcomePageLink(driver);
			testSteps.add("Opened the Booking Engine");
			app_logs.info("Opened the Booking Engine");
			testSteps.add("===== VERIFY AVAILABILITY DETAILS =====");
			app_logs.info("===== VERIFY AVAILABILITY DETAILS =====");
			
		//	bookingEngine.selectCheckInCheckOutDateAndGetCheckInRateValue(driver, checkInDate, checkOutDate, testSteps);
			for (Map.Entry<String, ArrayList<String>[]> entry : roomClassDataValuesForBookingEngineChannel
					.entrySet()) {
				String className = entry.getKey();
				ArrayList<String>[] roomClassDataValuesWithRules = entry.getValue();
			bookingEngine.getRatesRulesFromCalendar(driver, checkInDate, checkOutDate, testSteps,roomClassDataValuesWithRules[0],roomClassDataValuesWithRules[1],roomClassDataValuesWithRules[2]);
			getTestSteps.clear();
			getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate,false);
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
		
					app_logs.info("Expected : " + reservationRoomClassName);
					/*for (Map.Entry<String, ArrayList<String>[]> entry : roomClassDataValuesForBookingEngineChannel
							.entrySet()) {
						String className = entry.getKey();
						ArrayList<String>[] roomClassDataValuesWithRules = entry.getValue();*/
						if (reservationRoomClassName.equalsIgnoreCase(className)) {
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyRoomClassAndItsDataInBookingEngine1(driver,
									reservationRoomClassName, roomClassDataValuesWithRules[0], true,
									roomClassDataValuesWithRules[1], roomClassDataValuesWithRules[2],
									roomClassDataValuesWithRules[3]);
							testSteps.addAll(getTestSteps);
							getTestSteps.clear();

						}		

				testSteps.add(
						"=================== CLICKED ON NEXT BUTTON AND VERIFY ROOM CLASSES ======================");
				app_logs.info(
						"=================== CLICKED ON NEXT BUTTON AND VERIFY ROOM CLASSES ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnNextButton(driver, roomClassList);
				testSteps.addAll(getTestSteps);
				}
			if(action.contains("Rules")){
				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(0));
				navigation.Inventory_BackWard_Admin(driver);
				navigation.RatesGrid(driver);
				
				ratesGrid.clearRulesFrombulkUpdate(driver, delim, "BulkUpdate", checkInDate, checkOutDate, dateFormat,
					isSundayCheck, isMondayCheck, isTuesdayChecked, isWednesdayChecked, isThursdayChecked,
					isFridayChecked, isSaturdayChecked, ratePlanName, reservationRoomClassName, channelName,
					testSteps);
			}
	}

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", testName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	
		

			try {
				String[] testcase = cases.split(",");
				for (int i = 0; i < testcase.length; i++) {
					statusCode.add(i, "1");
				}
				
				Utility.updateTestCase(driver, caseId, statusCode,testName,TestCore.TestRail_AssignToID);
				Utility.closeTabsExcept(driver, 1);
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
		return Utility.getData("VerifyAvailabilityInBE", BEExcel);
	}
	/*@AfterMethod(alwaysRun = true)
	public void closeTab() {
		Utility.closeTabsExcept(driver, 1);
	}*/
/*	@AfterMethod(alwaysRun = true)
	public void updateTestRailLink() throws MalformedURLException, IOException, APIException {
		System.out.println("In after method");
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode,testName,TestCore.TestRail_AssignToID);

	}*/

	@AfterSuite(alwaysRun = true)
	public void closeDriver()  throws MalformedURLException, IOException, APIException {
		//driver.close();	
	}

}
