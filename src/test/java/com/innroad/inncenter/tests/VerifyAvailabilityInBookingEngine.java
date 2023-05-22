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
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Admin;
import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.Distribution;
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

public class VerifyAvailabilityInBookingEngine extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	String testName = "";

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

	@Test(dataProvider = "getData")
	public void verifyAvailabilityInBookingEngine(String propertyName, String channelName,
			String delim, String ratePlanName,String checkInDate ,String checkOutDate, String adults, String child, 
			String reservationRoomClassName, String updateRate, String roomClassesForUpdate,
			String isdaysCheck,String isTotalOccupancyOn, String totalOccupancyType, String totalOccupancyValue,
			String UpdateTypes,String SubTypeOfBulk,String UpdateRoomClasses,String Amount,String CurrencyType,
			String additionalAdults, String additionalChild,String minNights,String option,String testCaseName,
			 String cases)
			throws Exception {

		
		HashMap<String,String>ratePlanData=Utility.getExcelData(System.getProperty("user.dir")+"\\test-data\\CentralparkSanityTestData.xlsx","CreateNightlyRatePlanV2");
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
		testName = testCaseName;
		ArrayList<String> testSteps = new ArrayList<>();
		ArrayList<String> getTestSteps = new ArrayList<>();
		ArrayList<String> scriptName = new ArrayList<>();
		ArrayList<String> testCategory = new ArrayList<>();
		ArrayList<String> testDescription = new ArrayList<>();

		String test_description = "";
		String test_catagory = "";

		test_description = "Verify vailability in booking engine . <br>";
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
		Admin admin = new Admin();
		Properties properties = new Properties();

		BookingEngine bookingEngine = new BookingEngine();
		String dateFormat = Utility.monthDayYear_DateFormat;
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
		boolean ratePlanIsAdditionalAdultChild = false;
		String ratePlanMaxAdults = null;
		String ratePlanMaxPersons = null;
		String ratePlanAdultsRate = null;
		String ratePlanChildRate = null;
		String randomString = "";
		Tax tax = new Tax();
		String tName = "";

		
		try {
			String[] casesList = cases.split(",");
			for(int i = 0; i < casesList.length; i++) {
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
			loginRateV2(driver);
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
			app_logs.info("====== VERIFY PRE-CONDITIONS '" + channelName + "' IS SELECTED BT DEFAULT ==");
			testSteps.add("====== VERIFY PRE-CONDITIONS '" + channelName + "' IS SELECTED BT DEFAULT ======");
			navigation.Inventory(driver);
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

			app_logs.info("========== GET TODAY'S DATE ==========");
			testSteps.add("========== GET TODAY'S DATE ==========");
			navigation.Inventory(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");
			
			navigation.ratesGrid(driver,testSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, dateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
			app_logs.info("Today date : " + todayDate);
			daysBeforeCheckInDate = Utility.compareDates(checkInDate,todayDate, dateFormat);

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
		
		try {
			testSteps.add("=================== Verify Rate plan Exist or Not ======================");
			app_logs.info("=================== Verify Rate plan Exist or Not ======================");
			driver.navigate().refresh();
			try{
				getTestSteps.clear();
				israteplanExist = rateGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
			} catch(Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				israteplanExist = rateGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
			}
			testSteps.addAll(getTestSteps);
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
		
		if (updateRate.equalsIgnoreCase("yes")) {
			// Update rate
			//String updateRate, String roomClassesForUpdate, String updatedRate
			try {


				testSteps.add("========== UPDATE RATE VALUE FOR ROOM CLASS '"+roomClassesForUpdate +"' =====");
				app_logs.info("======= UPDATE RATE VALUE FOR ROOM CLASS '"+roomClassesForUpdate +"'======");
				ratesGrid.clickOnEditRatePlan(driver);
				testSteps.add("Click on edit rate plan");
				app_logs.info("Click on edit rate plan");
				getTestSteps.clear();
				getTestSteps = ratesGrid.ratePlanOverView(driver);
				testSteps.addAll(getTestSteps);
				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				
				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat,seasonDateFormat),Utility.parseDate(checkOutDate, dateFormat,seasonDateFormat));
				if (isSeasonExist) {
						nightlyRate.selectSeasonDates(driver, testSteps, Utility.parseDate(checkInDate, dateFormat,seasonDateFormat));
						nightlyRate.clickEditThisSeasonButton(driver, testSteps);
						getTestSteps.clear();
						getTestSteps = ratesGrid.enterRoomClassBaserate(driver, roomClassesForUpdate, updateRate, getTestSteps);
						testSteps.addAll(getTestSteps);
						nightlyRate.clickSaveSason(driver, testSteps);
						
						getTestSteps.clear();
						getTestSteps =  ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
						Wait.wait5Second();
						ratesGrid.closeOpendTabInMainMenu(driver);
				} else {
					app_logs.info("No Season For Desired Date");
					testSteps.add("No Season For Desired Date");
					Assert.assertTrue(false,"Failed to Update Rate Value");
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
		
		if (UpdateTypes.equals("Override")) {
		
		// navigate to Inventory->Rates Grid-> Availability and verify roomclass
		// availability
		
		try {
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, checkInDate, dateFormat, testSteps);

			ratesGrid.searchRateAndSelectRate(driver, testSteps, ratePlanName);

			availabilityInRatesBefore = getDataOfHash(
					ratesGrid.getAvailabilityOfRoomClass(driver, dateFormat, checkInDate, checkOutDate,
							reservationRoomClassName));

			testSteps.add(
					"======= GETTING ROOM CLASS AVAILABILITY INFORMATION =========");
			app_logs.info(
					"======= GETTING ROOM CLASS AVAILABILITY INFORMATION =========");
			
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
			
			testSteps.add("Before Reservation Creation 'Room Reserved Value' is '" + roomReservedValueBefore+ "'");
			app_logs.info("Before Reservation Creation 'Room Reserved Value' is '" + roomReservedValueBefore+ "'");
			testSteps.add("Before Reservation Creation 'Room Available Value' is '" + roomAvailableValueBefore+ "'");
			app_logs.info("Before Reservation Creation 'Room Available Value' is '" + roomAvailableValueBefore+ "'");
			testSteps.add("Before Reservation Creation 'Room Total Value' is '" + roomTotalValueBefore+ "'");
			app_logs.info("Before Reservation Creation 'Room Total Value' is '" + roomTotalValueBefore+ "'");

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
			testSteps.add(
					"=================== GETTING RATE PLAN INFORMATION ======================");
			app_logs.info(
					"=================== GETTING RATE PLAN INFORMATION ======================");
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
			
			selectedRatePlanDefaultStatus =  ratesGrid.verifyRatePlanSetToDefault(driver,testSteps,ratePlanName);
			
			
			ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
			
			nightlyRate.switchCalendarTab(driver, testSteps);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			
			dateWithoutSeason = nightlyRate.getDateWhereSeasonNotExist(driver);
			
			boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
					Utility.parseDate(checkInDate, dateFormat,seasonDateFormat),Utility.parseDate(checkOutDate, dateFormat,seasonDateFormat));
			if (isSeasonExist) {
				app_logs.info("Verified Season exist Between Check-in ("+checkInDate + ") and Check-out Dates ("+checkOutDate + ")");
				testSteps.add("Verified Season exist Between Check-in ("+checkInDate + ") and Check-out Dates ("+checkOutDate + ")");
					nightlyRate.selectSeasonDates(driver, testSteps, Utility.parseDate(checkInDate, dateFormat,seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					roomClassList = rateGrid.getSeasonLevelRoomClasses(driver);
					getRoomClassWithRates.put(roomClassesForUpdate, ratesGrid
							.getRoomClassRateWithAdditionalAdultAndChild(driver, roomClassesForUpdate, testSteps));
					app_logs.info(getRoomClassWithRates.get(roomClassesForUpdate));
					ArrayList<String> gettest = new ArrayList<>();
					gettest = getRoomClassWithRates.get(roomClassesForUpdate);
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
				ratePlanAvailableInBE= false;
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
				if (roomClassList.size() > 0) {
					for (int i = 0; i < roomClassList.size(); i++) {
						String roomClassName = roomClassList.get(i);

						testSteps.add("=================== GET '" + roomClassName + "' DATA ======================");
						app_logs.info("=================== GET '" + roomClassName + "' DATA ======================");

						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						ArrayList<String>[] arrayOfRoomClassesWithRules = new ArrayList[4];
						ArrayList<String> selectedRoomClassDataValues = ratesGrid.getChannelDataValues(driver,
								roomClassName, channelName);
						arrayOfRoomClassesWithRules[0] = selectedRoomClassDataValues;
						ratesGrid.expandChannel(driver, testSteps, roomClassName, channelName);

						testSteps.add(
								"=================== UPDATE MIN STAY RULE DATA IN INNCENTER ======================");
						app_logs.info(
								"=================== UPDATE MIN STAY RULE DATA IN INNCENTER ======================");

						ratesGrid.sendDataIfMinStayNightsDataNotExist(driver, roomClassName, channelName, "Min Stay",
								checkInDate, checkOutDate, dateFormat, minNights);

						testSteps.addAll((ratesGrid.getMinStayValuesOfRoomClassBetweenSelectedDateRange(driver,
								dateFormat, checkInDate, checkOutDate, roomClassName, channelName, "Min Stay")));

						ArrayList<String> minStayRuleValues = ratesGrid.getRuleDataValuesForMinStay(driver,
								roomClassName, channelName, "Min Stay");
						arrayOfRoomClassesWithRules[1] = minStayRuleValues;

						testSteps.add(
								"=================== UNCHECK NO CHECK IN, NO CHECK OUT RULE DATA IN INNCENTER ======================");
						app_logs.info(
								"=================== UNCHECK NO CHECK IN, NO CHECK OUT RULE DATA IN INNCENTER ======================");

						ratesGrid.uncheckCheckInCheckOutRuleIfTheyAlreadyApplied(driver, roomClassName, channelName,
								"No Check In");
						ratesGrid.uncheckCheckInCheckOutRuleIfTheyAlreadyApplied(driver, roomClassName, channelName,
								"No Check Out");

						testSteps.add(
								"=================== GET NO CHECK IN, NO CHECK OUT RULE DATA AFTER UPDATION IN INNCENTER ======================");
						app_logs.info(
								"=================== GET NO CHECK IN, NO CHECK OUT RULE DATA AFTER UPDATION IN INNCENTER ======================");

						testSteps.addAll(
								(ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver, dateFormat,
										checkInDate, checkOutDate, roomClassName, channelName, "No Check In", false)));

						testSteps.addAll(
								(ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver, dateFormat,
										checkInDate, checkOutDate, roomClassName, channelName, "No Check Out", false)));

						ArrayList<String> checkInRuleValues = ratesGrid.getRuleDataValueForCheckInCheckOut(driver,
								roomClassName, channelName, "No Check In");
						arrayOfRoomClassesWithRules[2] = checkInRuleValues;
						ArrayList<String> checkOutRuleValues = ratesGrid.getRuleDataValueForCheckInCheckOut(driver,
								roomClassName, channelName, "No Check Out");
						arrayOfRoomClassesWithRules[3] = checkOutRuleValues;
						roomClassDataValuesForBookingEngineChannel.put(roomClassName, arrayOfRoomClassesWithRules);
					}
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
				bookingEngine.clickOnBookingEngineConfigLink(driver);
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SELECTED =====");
				app_logs.info("===== VERIFY RATE PLAN IS SELECTED =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
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
										checkInDate, checkOutDate, adults, child,
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
										noOfDays, checkInDate, checkoutDate, adults, child,
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
								roomClassName, channelName);
						arrayOfRoomClassesWithRules[0] = selectedRoomClassDataValues;
						ratesGrid.expandChannel(driver, testSteps, roomClassName, channelName);
						ArrayList<String> minStayRuleValues = ratesGrid.getRuleDataValuesForMinStay(driver,
								roomClassName, channelName, "Min Stay");
						arrayOfRoomClassesWithRules[1] = minStayRuleValues;

						testSteps.add(
								"=================== APPLY NO CHECK IN, NO CHECK OUT RULES IN INNCENTER ======================");
						app_logs.info(
								"=================== APPLY NO CHECK IN, NO CHECK OUT RULES IN INNCENTER ======================");

						ratesGrid.applyCheckInCheckOutRuleValueIfTheyAlreadyNotApplied(driver, roomClassName,
								channelName, "No Check In", checkInDate, checkOutDate, dateFormat);
						ratesGrid.applyCheckInCheckOutRuleValueIfTheyAlreadyNotApplied(driver, roomClassName,
								channelName, "No Check Out", checkInDate, checkOutDate, dateFormat);

						testSteps.add(
								"=================== GET NO CHECK IN, NO CHECK OUT RULES DATA IN INNCENTER AFTER UPDATION ======================");
						app_logs.info(
								"=================== GET NO CHECK IN, NO CHECK OUT RULES DATA IN INNCENTER AFTER UPDATION ======================");

						testSteps.addAll(
								(ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver, dateFormat,
										checkInDate, checkOutDate, roomClassName, channelName, "No Check In", true)));

						testSteps.addAll(
								(ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver, dateFormat,
										checkInDate, checkOutDate, roomClassName, channelName, "No Check Out", true)));

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
			bookingEngine.clickOnBookingEngineConfigLink(driver);
			testSteps.add("Opened the Booking Engine Configration");
			app_logs.info("Opened the Booking Engine Configration");

			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
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
			getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver,checkInDate);
			testSteps.addAll(getTestSteps);
			
			testSteps.add("=================== VERIFICATION OF MIN NIGHTS, NO CHECK IN, NO CHECK OUT RULES UPDATION IN BOOKING ENGINE ======================"); 
			app_logs.info("=================== VERIFICATION OF MIN NIGHTS, NO CHECK IN, NO CHECK OUT RULES UPDATION IN BOOKING ENGINE ======================"); 
			
			if (roomClassList.size() > 0) {
				for (int index = 0; index < roomClassList.size(); index++) {
					String roomClassName = roomClassList.get(index).toString();
					app_logs.info("Expected : " + roomClassName);
					for (Map.Entry<String, ArrayList<String>[]> entry : roomClassDataValuesForBookingEngineChannel
							.entrySet()) {
						String className = entry.getKey();
						if (roomClassName.equalsIgnoreCase(className)) {
							
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyMinNightsDataInBookingEngine(driver, roomClassName,checkInDate,checkOutDate,dateFormat,RatesGrid.minStayDates);
							testSteps.addAll(getTestSteps);
							
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyCheckInDataInBookingEngine(driver, roomClassName,checkInDate,checkOutDate,dateFormat,RatesGrid.noCheckInDates);
							testSteps.addAll(getTestSteps);
							
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyCheckOutDataInBookingEngine(driver, roomClassName,checkInDate,checkOutDate,dateFormat,RatesGrid.noCheckOutDates);
							testSteps.addAll(getTestSteps);
							
						}
					}
				}
				
			}
			statusCode.add(0,"1");
			statusCode.add(6,"1");
			statusCode.add(8,"1");
			statusCode.add(9,"1");
			statusCode.add(10,"1");
			statusCode.add(1,"1");
			
			
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
				
				navigation.ratesGrid(driver,testSteps);
				
				ratesGrid.clickForRateGridCalender(driver, testSteps);
				Utility.selectDateFromDatePicker(driver, checkInDate, dateFormat);
				Wait.waitUntilPageLoadNotCompleted(driver, 50);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
				ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				
				testSteps.add("========== UPDATE RATE VALUE FOR ROOM CLASS '"+roomClassesForUpdate +"' =====");
				app_logs.info("======= UPDATE RATE VALUE FOR ROOM CLASS '"+roomClassesForUpdate +"'======");
				
				if (roomClassList.size() > 0) {
					for (int i = 0; i < roomClassList.size(); i++) {
						String roomClassName = roomClassList.get(i);
						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						ratesGrid.updateRateValuesForSelectedClasses(driver,roomClassName,channelName,updateRate,checkInDate,checkOutDate,dateFormat);
						ArrayList<String> selectedRoomClassDataValues = ratesGrid.getChannelDataValues(driver,
								roomClassName, channelName);
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
			bookingEngine.clickOnBookingEngineConfigLink(driver);
			testSteps.add("Opened the Booking Engine Configration");
			app_logs.info("Opened the Booking Engine Configration");

			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(1));
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
			getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver,checkInDate);
			testSteps.addAll(getTestSteps);
			
			
			testSteps.add("=================== VERIFICATION OF RATE VALUE UPDATION IN BOOKING ENGINE ======================"); 
			app_logs.info("=================== VERIFICATION OF RATE VALUE UPDATION IN BOOKING ENGINE ======================"); 
			
			if (roomClassList.size() > 0) {
				for (int index = 0; index < roomClassList.size(); index++) {
							String roomClassName = roomClassList.get(index).toString();
							getTestSteps.clear();
							getTestSteps = bookingEngine.verifyRateValuesInBookingEngine(driver, roomClassName,checkInDate,checkOutDate,dateFormat,RatesGrid.rateValuesMap);
							testSteps.addAll(getTestSteps);
							
				}
				
			}
			
			
			testSteps.add("=================== VERIFICATION OF DIFFERENT COMBINATIONS OF ADULTS AND CHILDS IN BOOKING ENGINE ======================"); 
			app_logs.info("=================== VERIFICATION OF DIFFERENT COMBINATIONS OF ADULTS AND CHILDS IN BOOKING ENGINE ======================"); 
			
			getTestSteps.clear();
			getTestSteps = bookingEngine.verifyDifferentAdultsAndchildrenCombinations(driver, roomClassesForUpdate, days, ratePlanAdultCapicity, ratePlanPersonCapicity,adults,child);
			testSteps.addAll(getTestSteps);
			statusCode.add(3,"1");
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
		
		try
		{
		
			bookingEngine.clickWelcomePageLink(driver);
			testSteps.add("Opened the Booking Engine");
			app_logs.info("Opened the Booking Engine");
			testSteps.add("===== VERIFY AVAILABILITY DETAILS =====");
			app_logs.info("===== VERIFY AVAILABILITY DETAILS =====");

			
			testSteps.add("=================== VERIFICATION OF $~~ IS SHOWING IN BOOKING ENGINE ======================"); 
			app_logs.info("=================== VERIFICATION OF $~~ IS SHOWING IN BOOKING ENGINE ======================"); 
			
			getTestSteps.clear();
			getTestSteps = bookingEngine.verifyAmountIsNotShowingWithoutRatePlan(driver,dateWithoutSeason);
			testSteps.addAll(getTestSteps);
		}
		catch (Exception e) {
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
		
		String[] nightlyRateArray = Amount.split( Utility.DELIM);
		String[] additionalAdultArray = additionalAdults.split(Utility.DELIM);
		String[] additionalChildArray = additionalChild.split(Utility.DELIM);
		days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);

		if (UpdateTypes.equals("BulkRates") || UpdateTypes.equals("BulkRules") || UpdateTypes.equals("BulkAvailability")) {
			try {

				app_logs.info("Update by : "+ UpdateTypes);
				testSteps.add("Update by : "+ UpdateTypes);

				getOldAvailability = ratesGrid.getRuleOnBaseofDays(driver, checkInDate, checkOutDate, listOfDay, timeZone,
						getTestSteps);
				
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
				getTestSteps =  ratesGrid.selectDate(driver, checkInDate, true);
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
		if(UpdateTypes.equals("BulkRates") || UpdateTypes.equals("BulkRules")) {
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

		if(UpdateTypes.equals("BulkRates") || UpdateTypes.equals("BulkRules"))
		{
			try {
				app_logs.info("==========SELECTING SOURCE==========");
				testSteps.add("==========SELECTING SOURCE==========");

				for (String str : channelArray) {
					if (str.equalsIgnoreCase("All sources")) {
						str = str + " (" + activeChannelsList.size() + ")";
					}
					getTestSteps.clear();
					getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", channelName);
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

		if(UpdateTypes.equals("BulkRates")) {
			try {
				app_logs.info("==========UPDATE RATES==========");
				testSteps.add("==========UPDATE RATES==========");
				Utility.clientCurrencySymbol = "$";

				// Checks Rate Update Type
				if (SubTypeOfBulk.equalsIgnoreCase("EnterNewRate")) {

					app_logs.info("Update Rate by checking : "+SubTypeOfBulk +" radio button.");
					testSteps.add("Update Rate by checking : "+SubTypeOfBulk +" radio button.");
					
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

					app_logs.info("Update Rate by selecting : "+SubTypeOfBulk +" from dropdown.");
					testSteps.add("Update Rate by selecting : "+SubTypeOfBulk +" from dropdown.");
					
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
					//assertEquals(totalDays, expectedDays, "Failed to match total days");
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
			
			try
			{
				String[] selectedClasses = reservationRoomClassName.split(Utility.DELIM);
				if(selectedClasses.length > 0)
				{
					for(int index=0;index<selectedClasses.length;index++)
					{
						String roomClassName = selectedClasses[index].toString();
						app_logs.info("Expected : " + roomClassName);
						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						getTestSteps.clear();
						getTestSteps = ratesGrid.getRateValuesOfRoomClassBetweenSelectedDateRange(driver,dateFormat,checkInDate,checkOutDate,roomClassName,channelName);
						testSteps.addAll(getTestSteps);
						bulkRateUpdateValuesMap.put(roomClassName, RatesGrid.rateValuesMap);
					}
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
			try
			{
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

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver,checkInDate);
				testSteps.addAll(getTestSteps);
				
				
				testSteps.add("=================== VERIFICATION OF BULK RATE VALUE UPDATION IN BOOKING ENGINE ======================"); 
				app_logs.info("=================== VERIFICATION OF BULK RATE VALUE UPDATION IN BOOKING ENGINE ======================"); 
				
				
				for (Map.Entry<String, HashMap<String, String>> entry : bulkRateUpdateValuesMap
						.entrySet()) {
					String className = entry.getKey();
					HashMap<String, String> roomClassDataValuesWithRules = entry.getValue();
					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyRateValuesInBookingEngine(driver, className,checkInDate,checkOutDate,dateFormat,roomClassDataValuesWithRules);
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
				
				String[] channelArr = channelName.split(Utility.DELIM);
				
				app_logs.info("Update availability by checking : "+SubTypeOfBulk +" radio button.");
				testSteps.add("Update Rate by checking : "+SubTypeOfBulk +" radio button.");
				
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

				days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);
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
			
			try
			{
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

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver,checkInDate);
				testSteps.addAll(getTestSteps);
				
				testSteps.add("=================== VERIFICATION OF BULK AVAILABILITY VALUES UPDATION IN BOOKING ENGINE ======================"); 
				app_logs.info("=================== VERIFICATION OF BULK AVAILABILITY VALUES UPDATION IN BOOKING ENGINE ======================"); 
				
				
				for (Map.Entry<String, HashMap<String, String>> entry : bulkAvailabilityUpdateValuesMap.entrySet()) {
					String className = entry.getKey();
					HashMap<String, String> roomClassDataValuesWithRules = entry.getValue();
					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyAvailabilityDataInBookingEngine(driver, className,
							checkInDate, checkOutDate, dateFormat, roomClassDataValuesWithRules);
					testSteps.addAll(getTestSteps);
				}
				
				
				
			}
			catch (Exception e) {
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
			
		if(UpdateTypes.equals("BulkRules"))
		{
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
					getTestSteps = ratesGrid.enterMinimumStayValue(driver, minNights);
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
				if(expectedDays.equals(totalDays))
				{
					testSteps.add("Failed : Days are mismatching.");
					app_logs.info("Failed : Days are mismatching.");
				}
				// assertEquals(totalDays, expectedDays, "Failed to match total days");
				else
				{
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

				String[] splitRoomClass = reservationRoomClassName.split(Utility.DELIM);

				if (splitRoomClass.length > 0) {
					for (int index = 0; index < splitRoomClass.length; index++) {
						String roomClassName = splitRoomClass[index].toString();
						app_logs.info("Expected : " + roomClassName);
						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						ratesGrid.expandChannel(driver, testSteps, roomClassName, channelName);

						if (isMinimumStayOn.equals("yes")) {
							testSteps.addAll((ratesGrid.getMinStayValuesOfRoomClassBetweenSelectedDateRange(driver,
									dateFormat, checkInDate, checkOutDate, roomClassName, channelName,
									"Min Stay")));
							bulkRateMinStayValuesMap.put(roomClassName, RatesGrid.minStayDates);
							
						}
						if (isCheckInOn.equals("yes")) {
							testSteps
									.addAll((ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver,
											dateFormat, checkInDate, checkOutDate, roomClassName,
											channelName, "No Check In", false)));
							bulkCheckInUpdateValuesMap.put(roomClassName, RatesGrid.noCheckInDates);
						}
						if (isCheckOutOn.equals("yes")) {
							testSteps
									.addAll((ratesGrid.getNoCheckInRuleValuesOfRoomClassBetweenSelectedDateRange(driver,
											dateFormat, checkInDate, checkOutDate, reservationRoomClassName,
											channelName, "No Check Out", false)));
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
				bookingEngine.clickOnBookingEngineConfigLink(driver);
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickViewOurEntireAvailability(driver,checkInDate);
				testSteps.addAll(getTestSteps);
				
				
				if (isMinimumStayOn.equals("yes")) {
					
					app_logs.info("==========VERIFYING MIN STAY RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");
					testSteps.add("==========VERIFYING MIN STAY RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");
					
					for (Map.Entry<String, HashMap<String, String>> entry : bulkRateMinStayValuesMap.entrySet()) {
						String className = entry.getKey();
						HashMap<String, String> roomClassDataValuesWithRules = entry.getValue();
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyMinNightsDataInBookingEngine(driver, className,
								checkInDate, checkOutDate, dateFormat, roomClassDataValuesWithRules);
						testSteps.addAll(getTestSteps);
						statusCode.add(5,"1");
					}
				}

				if (isCheckInOn.equals("yes")) {
					
					app_logs.info("==========VERIFYING NO CHECK IN RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");
					testSteps.add("==========VERIFYING NO CHECK IN RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE=========");
					
					for (Map.Entry<String, HashMap<String, String>> entry : bulkCheckInUpdateValuesMap.entrySet()) {
						String className = entry.getKey();
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyCheckInDataInBookingEngine(driver, className,
								checkInDate, checkOutDate, dateFormat, RatesGrid.noCheckInDates);
						testSteps.addAll(getTestSteps);
					}
				}

				if (isCheckOutOn.equals("yes")) {
					
					app_logs.info("==========VERIFYING NO CHECK OUT RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");
					testSteps.add("==========VERIFYING NO CHECK OUT RULE DATA AFTER BULK UPDATE IN BOOKING ENGINE==========");
					
					for (Map.Entry<String, HashMap<String, String>> entry : bulkCheckOutUpdateValuesMap.entrySet()) {
						String className = entry.getKey();
						getTestSteps.clear();
						getTestSteps = bookingEngine.verifyCheckOutDataInBookingEngine(driver, className,
								checkInDate, checkOutDate, dateFormat, RatesGrid.noCheckOutDates);
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
		if(UpdateTypes.equals("Update"))
		{

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
					getTestSteps = ratesGrid.selectItemsFromDropDowns(driver, "Source", channelName);
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
					
					testSteps.add("=================== UPDATE RATE VALUES FOR SELECTED CLASSES AT SEASON LEVEL ======================");
					app_logs.info("=================== UPDATE RATE VALUES FOR SELECTED CLASSES AT SEASON LEVEL ======================");
					
					
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
							getTestSteps = ratesGrid.enterRoomClassBaserate(driver, roomClassName, amountArray[index], getTestSteps);
							testSteps.addAll(getTestSteps);
							if(getRoomClassWithRates.get(roomClassName).get(0) != amountArray[index])
							{
								isValueDifferentFromAlreadyPresent = true;
							}
														
						}
					}

					if(isValueDifferentFromAlreadyPresent)
					{
						nightlyRate.clickSaveSason(driver, testSteps);
						getTestSteps.clear();
						getTestSteps =  ratesGrid.clickOnSaveratePlan(driver);
						testSteps.addAll(getTestSteps);
					}
					else
					{
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
			try
			{
				String[] selectedClasses = reservationRoomClassName.split(Utility.DELIM);
				if(selectedClasses.length > 0)
				{
					for(int index=0;index<selectedClasses.length;index++)
					{
						String roomClassName = selectedClasses[index].toString();
						
						testSteps.add("=================== GET '"+roomClassName+"' DATA AFTER SEASON LEVEL RATE UPDATION ======================");
						app_logs.info("=================== GET '"+roomClassName+"' DATA AFTER SEASON LEVEL RATE UPDATION ======================");
						
						app_logs.info("Expected : " + roomClassName);
						ratesGrid.expandRoomClass(driver, testSteps, roomClassName);
						getTestSteps.clear();
						getTestSteps = ratesGrid.getRateValuesOfRoomClassBetweenSelectedDateRange(driver,dateFormat,checkInDate,checkOutDate,roomClassName,channelName);
						testSteps.addAll(getTestSteps);
						bulkRateUpdateValuesMap.put(roomClassName, RatesGrid.rateValuesMap);
					}
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
			
			try
			{
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

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
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
						getTestSteps = nightlyRate.checkUncheckSeasonLevelPolicy(driver, getTestSteps,"check");
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
					}
					else
					{
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
			
			try
			{
				ratesGrid.expandRoomClass(driver, testSteps, reservationRoomClassName);
				ratesGrid.expandChannel(driver, testSteps, reservationRoomClassName, channelName);
				testSteps.add("=================== UNCHECK NO CHECK IN, NO CHECK OUT RULE DATA IN INNCENTER ======================");
				app_logs.info("=================== UNCHECK NO CHECK IN, NO CHECK OUT RULE DATA IN INNCENTER ======================");

				ratesGrid.uncheckCheckInCheckOutRuleIfTheyAlreadyApplied(driver, reservationRoomClassName, channelName,
						"No Check In");
				ratesGrid.uncheckCheckInCheckOutRuleIfTheyAlreadyApplied(driver, reservationRoomClassName, channelName,
						"No Check Out");
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

				testSteps.add("=================== VERIFY SEASON LEVEL POLICIES IN BOOKING ENGINE BY CHECKING ALL IN INNCENTER======================");
				app_logs.info("=================== VERIFY SEASON LEVEL POLICIES IN BOOKING ENGINE BY CHECKING ALL IN INNCENTER ======================");
				
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

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
				app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");

				int noOfDays = Integer.parseInt(minNights) + 1;

				String checkout = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkInDate,
					"MM/d/yyyy", "MM/d/yyyy", noOfDays + 1, timeZone);
				
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

				testSteps.add("=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
				app_logs.info("=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");

				getTestSteps.clear();
				roomClassNotExist = bookingEngine.verifyRoomClassExistInBookingEngine(driver, adults, child,
						reservationRoomClassName, ratePlanAdultCapicity, ratePlanPersonCapicity);
				if (roomClassNotExist == true) {
					testSteps.add(
							"Successfully Verified '" + reservationRoomClassName + "' is not showing in Booking Engine.");
				}

				if (roomClassNotExist == false) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyRatePlanExist(driver, ratePlanName, ratePlanAvailableInBE);
					testSteps.addAll(getTestSteps);
					statusCode.add(2,"1");
					
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

				testSteps.add("===================  VERIFY SEASON LEVEL POLICIES IN BOOKING ENGINE BY UNCHECKING ALL IN INNCENTER ======================");
				app_logs.info("===================  VERIFY SEASON LEVEL POLICIES IN BOOKING ENGINE BY UNCHECKING ALL IN INNCENTER ======================");
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

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
				testSteps.add("============== OPEN BOOKING ENGINE ======================");
				app_logs.info("============== OPEN BOOKING ENGINE ======================");
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");
				testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
				app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");

				int noOfDays = Integer.parseInt(minNights) + 1;

				String checkout = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkInDate,
					"MM/d/yyyy", "MM/d/yyyy", noOfDays + 1, timeZone);
			
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

				testSteps.add("=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");
				app_logs.info("=================== VERIFY ROOM CLASS IS SHOWING IN BOOKING ENGINE ======================");

				getTestSteps.clear();
				roomClassNotExist = bookingEngine.verifyRoomClassExistInBookingEngine(driver, adults, child,
						reservationRoomClassName, ratePlanAdultCapicity, ratePlanPersonCapicity);
				statusCode.add(4,"1");
				if (roomClassNotExist == true) {
					testSteps.add(
							"Successfully Verified '" + reservationRoomClassName + "' is not showing in Booking Engine.");
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
						statusCode.add(7,"1");
						
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
				bookingEngine.clickOnBookingEngineConfigLink(driver);
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));
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
				
				testSteps.add("Click on Check In Date : "+ checkInDate);
				app_logs.info("Click on Check In Date : "+ checkInDate);

				if (roomClassList.size() > 0) {
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickOnCheckInDate(driver, checkInDate, checkOutDate,
							roomClassList.get(0), dateFormat, days);
					testSteps.addAll(getTestSteps);
					
					testSteps.add("Click on Check Out Date : "+ checkOutDate);
					app_logs.info("Click on Check Out Date : "+ checkOutDate);

					getTestSteps.clear();
					getTestSteps = bookingEngine.clickOnCheckOutDate(driver, checkInDate, checkOutDate,
							roomClassList.get(0), dateFormat, days, BookingEngine.checkInDateIndex);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyCheckOutTextIsShowing(driver);
					testSteps.addAll(getTestSteps);

					String checkoutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkOutDate, "MM/d/yyyy",
							"MM/d/yyyy", days + 2, timeZone);
					
					testSteps.add("=================== VERIFY CHECK OUT TEXT IS SHOWING BY MOVING CURSOR AND EXTENDING CHECKOUT DATE  ======================");
					app_logs.info("=================== VERIFY CHECK OUT TEXT IS SHOWING BY MOVING CURSOR AND EXTENDING CHECKOUT DATE ======================");
					
					testSteps.add("Extended checkOut Date : "+ checkoutDate);
					app_logs.info("Extended checkOut Date : "+ checkoutDate);

					int newdays = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkoutDate);
					
					testSteps.add("Days : "+ newdays);
					app_logs.info("Days : "+ newdays);

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
				getTestSteps = bookingEngine.verifyAvailabilityGridStartDate(driver, ESTTimeZone.reformatDate(newDate, "MM/d/yyyy", "d/MM/yyyy"));
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

				testSteps.add("=================== Verifying the check-in date when click for the second time ======================");
				app_logs.info("=================== Verifying the check-in date when click for the second time ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCheckInDateWhenClickForSecondTime(driver,checkInDate);
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
				ratePlanPersonCapicity="6";
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				testSteps.add("=================== Verifying the Check in date is getting modified in Room class details page when user double taps on date picker in calendar ======================");
				app_logs.info("=================== Verifying the Check in date is getting modified in Room class details page when user double taps on date picker in calendar  ======================");

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
				
				
				String newCheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkOutDate, "MM/d/yyyy", "MM/d/yyyy",
						days + 2, timeZone);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckOutDateByDoubleTapOnDatePicker(driver,newCheckOutDate);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickOnBackToRoomButton(driver);
				testSteps.addAll(getTestSteps);
				
				testSteps.add("=================== Navigate to Room Details Page After updating value of check Out Date ======================");
				app_logs.info("=================== Navigate to Room Details Page After updating value of check Out Date  ======================");
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.selectRoomClass(driver, reservationRoomClassName);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCheckOutDateAfterUpate(driver,newCheckOutDate);
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

				bookingEngine.clickOnBookingEngineConfigLink(driver);
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));

				getTestSteps.clear();
				getTestSteps = bookingEngine.toggleAllowSameDayBooking(driver);
				testSteps.addAll(getTestSteps);
				
				bookingEngine.clickWelcomePageLink(driver);
				testSteps.add("Opened the Booking Engine");
				app_logs.info("Opened the Booking Engine");

				testSteps.add("=================== Verifying the current date is still displayed in the calendar ,even same day booking toggle is off in settings  ======================");
				app_logs.info("=================== Verifying the current date is still displayed in the calendar ,even same day booking toggle is off in settings   ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCurrentDateIsNotDisplayingWhenSameDayBookingToggleIsOff(driver,checkInDate);
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

				testSteps.add("=================== Check out date is default selected when user selected check-in date in calendar in Search widget   ======================");
				app_logs.info("=================== Check out date is default selected when user selected check-in date in calendar in Search widget  ======================");

				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCheckOutDateIsDefaultSelected(driver,checkInDate);
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
				
				propertyName="End Inn Test ";
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

				bookingEngine.clickOnBookingEngineConfigLink(driver);
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));

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
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.clickSearchOfRooms(driver);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyAdultAgeRangeValues(driver,adults);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyChildrenAgeRangeValues(driver,child);
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
				
			
			testSteps.add("=================== Verify start date and end date in calendar for group blocks  ======================");
			app_logs.info("=================== Verify start date and end date in calendar for group blocks  ======================");
			

				loginRateV2(driver);
				propertyName="End Inn Test ";
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

				bookingEngine.clickOnBookingEngineConfigLink(driver);
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));

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
				getTestSteps = bookingEngine.enterGroupNumberInBE(driver,groupNumber);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyCheckInCheckOutDate(driver,checkInDate,checkOutDate);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.verifyDateRangeLiesBetweenBlockDateRange(driver,checkInDate,checkOutDate);
				testSteps.addAll(getTestSteps);
				
				String newCheckOutDate = ESTTimeZone.getNextDateBaseOnPreviouseDate(checkOutDate, "MM/d/yyyy", "MM/d/yyyy",
						-1, timeZone);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, newCheckOutDate);
				testSteps.addAll(getTestSteps);

				testSteps.add("Select Check-In Date : " + checkInDate);
				app_logs.info("Select Check-In Date : " + checkInDate);

				testSteps.add("Select Check-Out Date : " + checkOutDate);
				app_logs.info("Select Check-Out Date : " + checkOutDate);
				
				
				reservationRoomClassName= "R12";
				ratePlanAdultCapicity = "3";
				ratePlanPersonCapicity="5";
				
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
					getTestSteps = bookingEngine.verifyCheckInCheckOutDate(driver,checkInDate,checkOutDate);
					testSteps.addAll(getTestSteps);
					
					getTestSteps.clear();
					getTestSteps = bookingEngine.verifyDateRangeLiesBetweenBlockDateRange(driver,checkInDate,checkOutDate);
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
				
			testSteps.add("=================== Verify the rate displaying in the home page calendar by switching toggle off  ======================");
			app_logs.info("=================== Verify the rate displaying in the home page calendar by switching toggle off  ======================");
			
				loginRateV2(driver);
				propertyName="End Inn Test ";
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

				bookingEngine.clickOnBookingEngineConfigLink(driver);
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));

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
			
			testSteps.add("=================== Verify the rate displaying in the search list page calendar by by switching toggle off  ======================");
			app_logs.info("=================== Verify the rate displaying in the search list page calendar by by switching toggle off  ======================");
			
				loginRateV2(driver);
				propertyName="End Inn Test ";
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

				bookingEngine.clickOnBookingEngineConfigLink(driver);
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));

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
				getTestSteps = bookingEngine.hoverOnCheckInDateAndVerifyDates(driver,checkInDate);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.hoverOnCheckOutDateAndVerifyDates(driver,checkOutDate);
				testSteps.addAll(getTestSteps);
				
				
				testSteps.add("=================== Verify the rate displaying in the room details  page calendar by by switching toggle off ======================");
				app_logs.info("=================== Verify the rate displaying in the room details  page calendar by by switching toggle off ======================");
				
				reservationRoomClassName= "R12";
				ratePlanAdultCapicity = "3";
				ratePlanPersonCapicity="5";
				
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
					getTestSteps = bookingEngine.hoverOnCheckInDateAndVerifyDates(driver,checkInDate);
					testSteps.addAll(getTestSteps);
					
					getTestSteps.clear();
					getTestSteps = bookingEngine.hoverOnCheckOutDateAndVerifyDates(driver,checkOutDate);
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
			
			testSteps.add("=================== Verify the rate displayed in the search list page calendar by selecting group booking ======================");
			app_logs.info("=================== Verify the rate displayed in the search list page calendar by selecting group booking ======================");
			
				loginRateV2(driver);
				propertyName="End Inn Test ";
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

				bookingEngine.clickOnBookingEngineConfigLink(driver);
				testSteps.add("Opened the Booking Engine Configration");
				app_logs.info("Opened the Booking Engine Configration");
				testSteps.add("===== VERIFY RATE PLAN IS SHOWING =====");
				app_logs.info("===== VERIFY RATE PLAN IS SHOWING =====");

				ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
				driver.switchTo().window(tabs2.get(1));

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
				getTestSteps = bookingEngine.enterGroupNumberInBE(driver,groupNumber);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.hoverOnCheckInDateAndVerifyDates(driver,checkInDate);
				testSteps.addAll(getTestSteps);
				
				getTestSteps.clear();
				getTestSteps = bookingEngine.hoverOnCheckOutDateAndVerifyDates(driver,checkOutDate);
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
		/*try {
			
			testSteps.add("=================== Verify Rate display on Booking Engine Calendar when Vat applied ======================");
			app_logs.info("=================== Verify Rate display on Booking Engine Calendar when Vat applied ======================");
			
			driver.navigate().to("https://app.innroad.com/");
			login.login(driver, "https://app.innroad.com/", "stone1 ", "robin", "Innroad@123");
			
			 String value ="10";
			 String category = "Sales Tax";
			 boolean excludeTaxExempt = false;
			 String TaxName = "Test Tax";
			 String taxLedgerAccount = "";
			 boolean vat = true;
				
			// Create Tax
			testSteps.add("<b>************** Making Tax Setup*******************</b>");
			navigation.Setup(driver);
			testSteps.add("Navigate to setup");
			navigation.Taxes(driver);
			testSteps.add("Navigate to taxes page");
				// create new tax------------------
			testSteps.add("<b>********** Creating New percent Tax****************</b>");
				randomString = Utility.GenerateRandomNumber();
				tName = TaxName.replace("XXX", randomString);
				tax.Click_NewItem(driver, testSteps);
				testSteps.add("Click at new tax item button");
				testSteps.add("<br>Create new taxes</br>");
						boolean percentage = true;
						testSteps.add("percent Tax name is: " + "<b>" + tName + " </b>");
						tax.createTax(driver, test, tName.trim(), tName.trim(), tName.trim(), value, category, taxLedgerAccount,
								excludeTaxExempt, percentage, vat);
						testSteps.add("Created tax with percent amount: " + "<b>" + value + "</b>");


		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Rate display on Booking Engine Calendar when Vat applied ", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Rate display on Booking Engine Calendar when Vat applied ", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		
		}*/
			
		}
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
		return Utility.getData("VerifyAvailabilityInBookingEngi", envLoginExcel);
	}

	@AfterMethod(alwaysRun = true)
	public void updateTestRailLink()  throws MalformedURLException, IOException, APIException {
		System.out.println("In after method");
		//Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, testName,TestCore.TestRail_AssignToID);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver()  throws MalformedURLException, IOException, APIException {
		//driver.close();	
	}

}
