package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.poifs.storage.BlockList;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.BookingEngine;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.GuestHistory;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Policies;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reservation;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tax;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.APIException;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.snowtide.pdf.ac;

public class VerifyGuestProfileInBookingEngine extends TestCore {

	private WebDriver driver = null;

	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	String testName = "";
	Login login = new Login();
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	HashMap<String, String> groupDetails=new HashMap<>();
	Navigation navigation = new Navigation();
	BookingEngine bookingEngine = new BookingEngine();
	GuestHistory guestHistory = new GuestHistory();
	String accountNumber = "";
	String blockName = "";
	String accountName = "";
	String roomClassName = "";
	String maxAdults = "";
	String maxPersons = "";
	String timeZone = "";

	// String
	// cases="36661043,36661440,36661016,36661629,36661625,36661102,36661097,36660715,36660903,36660904,36660905";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, BEExcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData")
	public void verifyGuestProfileInBookingEngine(String delim, String ratePlanName,
			String checkInDate, String checkOutDate,String firstName, String lastName,String emailAddress, String adults, String child, String reservationRoomClassName, 
			String action,String toggleStateOfGuestProfileBookingsButton,
			String testCaseName, String cases) throws Exception {
		
		int numberOfProperties = 0;
		if(action.equalsIgnoreCase("verifyNewProfile")) {
		firstName = firstName + Utility.generateRandomStringWithGivenLength(5);
		lastName = lastName + Utility.generateRandomStringWithGivenLength(5);
		}
		String phone = "1213445465";
		String address = "Add#1";
		String postalCode = "12345";
		String country="Australia";
		String city = "New South Wales";
		String state = "New South Wales";
		String paymentType = "MC";
		String cardNumber = "5454545454545454";
		String cardName = "testName";
		String cardExpDate = "12/23";
		String cvv = "123";
		int noOfBlocks = 2;
//		String channelName = "";

		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\BookingEngineTestData.xlsx",
				"CreateNightlyRatePlanV2");
		String FolioDisplayName = ratePlanData.get("FolioDisplayName");
		String Description = ratePlanData.get("Description");
		String Channels = ratePlanData.get("Channels");
		String RoomClasses = ratePlanData.get("RoomClasses");
		String isRatePlanRistrictionReq = ratePlanData.get("isRatePlanRistrictionReq");
		String RistrictionType = ratePlanData.get("RistrictionType");
		String isMinStay = ratePlanData.get("isMinStay");
		String MinNights = ratePlanData.get("MinNights");
		String isMaxStay = ratePlanData.get("isMaxStay");
		String MaxNights = ratePlanData.get("MaxNights");
		String isMoreThanDaysReq = ratePlanData.get("isMoreThanDaysReq");
		String MoreThanDaysCount = ratePlanData.get("MoreThanDaysCount");
		String isWithInDaysReq = ratePlanData.get("isWithInDaysReq");
		String WithInDaysCount = ratePlanData.get("WithInDaysCount");
		String PromoCode = ratePlanData.get("PromoCode");
		String isPolicesReq = ratePlanData.get("isPolicesReq");
		String PoliciesType = ratePlanData.get("PoliciesType");
		String PoliciesName = ratePlanData.get("PoliciesName");
		String SeasonName = ratePlanData.get("SeasonName");
		String SeasonStartDate = ratePlanData.get("SeasonStartDate");
		String SeasonEndDate = ratePlanData.get("SeasonEndDate");
		String isMonDay = ratePlanData.get("isMonDay");
		String isTueDay = ratePlanData.get("isTueDay");
		String isWednesDay = ratePlanData.get("isWednesDay");
		String isThursDay = ratePlanData.get("isThursDay");
		String isFriday = ratePlanData.get("isFriday");
		String isSaturDay = ratePlanData.get("isSaturDay");
		String isSunDay = ratePlanData.get("isSunDay");
		String isAdditionalChargesForChildrenAdults = ratePlanData.get("isAdditionalChargesForChildrenAdults");
		String RatePerNight = ratePlanData.get("RatePerNight");
		String MaxAdults = ratePlanData.get("MaxAdults");
		String AdditionalAdultsPerNight = ratePlanData.get("AdditionalAdultsPerNight");
		String AdditionalChildPerNight = ratePlanData.get("AdditionalChildPerNight");
		String isAddRoomClassInSeason = ratePlanData.get("isAddRoomClassInSeason");
		String ExtraRoomClassesInSeason = ratePlanData.get("ExtraRoomClassesInSeason");
		String ExtraRoomClassRatePerNight = ratePlanData.get("ExtraRoomClassRatePerNight");
		String ExtraRoomClassMaxAdults = ratePlanData.get("ExtraRoomClassMaxAdults");
		String ExtraRoomClassMaxPersons = ratePlanData.get("ExtraRoomClassMaxPersons");
		String ExtraRoomClassAdditionalAdultsPerNight = ratePlanData.get("ExtraRoomClassAdditionalAdultsPerNight");
		String ExtraRoomClassAdditionalChildPerNight = ratePlanData.get("ExtraRoomClassAdditionalChildPerNight");
		String isSerasonLevelRules = ratePlanData.get("isSerasonLevelRules");
		String isAssignRulesByRoomClass = ratePlanData.get("isAssignRulesByRoomClass");
		String SeasonRuleSpecificRoomClasses = ratePlanData.get("SeasonRuleSpecificRoomClasses");
		String SeasonRuleType = ratePlanData.get("SeasonRuleType");
		String SeasonRuleMinStayValue = ratePlanData.get("SeasonRuleMinStayValue");
		String isSeasonRuleOnMonday = ratePlanData.get("isSeasonRuleOnMonday");
		String isSeasonRuleOnTuesday = ratePlanData.get("isSeasonRuleOnTuesday");
		String isSeasonRuleOnWednesday = ratePlanData.get("isSeasonRuleOnWednesday");
		String isSeasonRuleOnThursday = ratePlanData.get("isSeasonRuleOnThursday");
		String isSeasonRuleOnFriday = ratePlanData.get("isSeasonRuleOnFriday");
		String isSeasonRuleOnSaturday = ratePlanData.get("isSeasonRuleOnSaturday");
		String isSeasonRuleOnSunday = ratePlanData.get("isSeasonRuleOnSunday");
		String isSeasonPolicies = ratePlanData.get("isSeasonPolicies");
		String SeasonPolicyType = ratePlanData.get("SeasonPolicyType");
		String SeasonPolicyValues = ratePlanData.get("SeasonPolicyValues");
		String MaxPersons = ratePlanData.get("MaxPersons");
		testSteps = new ArrayList<>();
		getTestSteps = new ArrayList<>();
		scriptName = new ArrayList<>();
		testCategory = new ArrayList<>();
		testDescription = new ArrayList<>();
		
		timeZone = "";
		String propertyName = "";
		String dateFormat = "dd/MM/yyyy";
		int days = ESTTimeZone.numberOfDaysBetweenDates(checkInDate, checkOutDate);
		app_logs.info("days: " + days);
		Policies policy = new Policies();

		Utility.DELIM = "\\" + delim;
		testName="";
		testName = testCaseName;

		String test_description = "";
		String test_catagory = "";

		test_description = "Verify Guests Profile in booking engine . <br>";
		test_catagory = "GuestProfile_Verification_In_BookingEngine";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		RatesGrid rateGrid = new RatesGrid();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();

		Groups group = new Groups();
		AdvGroups advgroup = new AdvGroups();
		CPReservationPage reservation = new CPReservationPage();
		Properties properties = new Properties();

		String calendarTodayDay = "today";
		String todayDate = null;
		int daysBeforeCheckInDate = 0;
		boolean israteplanExist = false;

		boolean selectedRatePlanDefaultStatus = false;

		boolean roomClassNotExist = false;
		String randomString = "";
		Tax tax = new Tax();
		String tName = "";
		String feeName = "testFee";
		String randomNumber = Utility.GenerateRandomNumber(99);
		roomClassName = reservationRoomClassName;

		String marketSegment = "Internet";
		String referral = "Other";
		String PhoneNumber = "8790321567";
		String Address1 = "test1";
	
		String PostalCode = "12345";

		String TaxName = "TestTax";
		HashMap<String, String> groupAssociatedPolicies = new HashMap<>();
		ArrayList<String> taxesList = new ArrayList<>();
		String value = "10";
		String depositPolicy = "";
		String cancellationPolicy = "";
		String checkInPolicy = "";
		String noShowPolicy = "";
		ArrayList<String> policies = new ArrayList<>();
		policies.add("Cancellation");
		policies.add("Deposit");
		policies.add("Check-in");
		policies.add("No Show");
		ArrayList<String> roomClassList = new ArrayList<>();
		HashMap<String, ArrayList<String>> getRoomClassWithRates = new HashMap<>();
		ArrayList<String> roomBlocks = new ArrayList<>();
		int accountsSearchCount;

		try {
			String[] casesList = cases.split(",");
			for (int i = 0; i < casesList.length; i++) {
				caseId.add(casesList[i]);
				statusCode.add("4");
			}
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login.login(driver,envURL, "autonbe", "autouser", "Auto@123");

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

		/*try {
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

		if (Utility.countForGroupVerificationScript == 1) {
			try {
				testSteps.add("=================== GET TIME ZONE FROM PROPERTY ======================");
				app_logs.info("=================== GET TIME ZONE FROM PROPERTY ======================");
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

				timeZone = navigation.get_Property_TimeZone(driver);
				testSteps.add("Property TimeZone: " + timeZone);

			} catch (Exception e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed to get time zone from property", "Property", "Property", driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(testName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
					Utility.updateReport(e, "Failed get time zone from property", "Property", "Property", driver);
				} else {
					Assert.assertTrue(false);
				}
			}
		}
		
		try {
			String getCurrentDate = Utility.getNextDate(0, dateFormat, timeZone);
			if (ESTTimeZone.CompareDates(checkInDate, dateFormat, timeZone)) {
				checkInDate = getCurrentDate;
				checkOutDate = Utility.getNextDate(days, dateFormat, timeZone);
			}
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

			app_logs.info("========== GET TODAY'S DATE ==========");
			testSteps.add("========== GET TODAY'S DATE ==========");

			navigation.navInventoryFromGroupBlock(driver);
			app_logs.info("Navigate Inventory");
			testSteps.add("Navigate Inventory");

			navigation.ratesGrid(driver, testSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			todayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, dateFormat, getTestSteps);
			testSteps.addAll(getTestSteps);
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

		try {
			testSteps.add("=================== Verify Rate plan Exist or Not ======================");
			app_logs.info("=================== Verify Rate plan Exist or Not ======================");
			driver.navigate().refresh();
			try {
				getTestSteps.clear();
				israteplanExist = rateGrid.searchForRatePlanExistingOrNot(driver, getTestSteps, ratePlanName);
			} catch (Exception e) {
				driver.navigate().refresh();
				getTestSteps.clear();
				israteplanExist = rateGrid.searchForRatePlanExistingOrNot(driver, getTestSteps, ratePlanName);
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
		*/
		testSteps.add("================= SEARCH CREATED ACCOUNT ON ACCOUNT SEARCH PAGE ======================");
		app_logs.info("================= SEARCH CREATED ACCOUNT ON ACCOUNT SEARCH PAGE ======================");
			
		testSteps.add(
					"Search with AccountFirstName and AccountLastName" + firstName + ", " + lastName);
		
		try {
		navigation.GuestHistory(driver);
		accountsSearchCount=guestHistory.searchAccountAndGetCount(driver, firstName, lastName);
		if(accountsSearchCount>0) {
		boolean isGuest=guestHistory.OpenSearchedGuestHistroyAccountAndVerifyEmail(driver,emailAddress);
		}
		
		testSteps.add("================= OPEN NBE AND CREATE RESERVATION ======================");
		app_logs.info("================= OPEN NBE AND CREATE RESERVATION ======================");

		logInBookingEngineConfigLink(driver, propertyName);

		getTestSteps.clear();
		getTestSteps = bookingEngine.toggleGuestProfileToggleButton(driver, toggleStateOfGuestProfileBookingsButton);
		testSteps.addAll(getTestSteps);
		
		bookingEngine.clickWelcomePageLink(driver);

		//navigation.navigateToBookingEngine(driver);
		testSteps.add("Opened the Booking Engine");
		app_logs.info("Opened the Booking Engine");

		//getTestSteps = bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate);
		bookingEngine.selectCheckInCheckOutDate(driver, checkInDate, checkOutDate, false);
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
		
		bookingEngine.selectRoomClass(driver, reservationRoomClassName);

		getTestSteps.clear();
		getTestSteps = bookingEngine.clickBookRoom(driver, ratePlanName);
		testSteps.addAll(getTestSteps);

		testSteps.add("===== ENTER GUEST INFORMATION =====");
		app_logs.info("===== ENTER GUEST INFORMATION =====");

		getTestSteps.clear();
		getTestSteps = bookingEngine.enterGuestInfo1(driver, firstName, lastName, emailAddress, phone,
				address, address, postalCode, country,city, state);
		testSteps.addAll(getTestSteps);
		testSteps.add("===== ENTER CARD DETAILS =====");
		app_logs.info("===== ENTER CARD DETAILS =====");
		getTestSteps.clear();
		getTestSteps = bookingEngine.enterMcCardInfo(driver, cardNumber, cardName, cardExpDate, cvv);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = bookingEngine.clickBookStay(driver);
		testSteps.addAll(getTestSteps);

		String reservationNumber = bookingEngine.getReservationNumber(driver);

		testSteps.add("Reservation Created in Booking Engine:" + reservationNumber);
		app_logs.info("Reservation Created in Booking Engine:" + reservationNumber);

		testSteps.add("============ VERIFY CREATED RESERVATION DETAILS IN INNCENTER ===========");
		app_logs.info("===== VERIFY CREATED RESERVATION DETAILS IN INNCENTER =======");
		
		//loginRateV2(driver)
		login.login(driver,envURL, "autonbe", "autouser", "Auto@123");
		testSteps.add("Logged into the InnCenter ");
		app_logs.info("Logged into the InnCenter ");

		reservation.Search_ResNumber_And_Click(driver, reservationNumber);
		String guestName = firstName + " " + lastName;

		testSteps.add("Search Reservation : " + reservationNumber);
		app_logs.info("Search Reservation : " + reservationNumber);
		testSteps.add("Open Reservation ");
		app_logs.info("Open Reservation ");

		testSteps.add("=================== BANNER FIELDS VERIFICATION ======================");
		app_logs.info("=================== BANNER FIELDS VERIFICATION ======================");
		
		testSteps.add("========== Verify reservation details page is showing ============");
		app_logs.info("========== Verify reservation details page is showing ============");

		getTestSteps.clear();
		getTestSteps = ratesGrid.verifyReservationDetailsPageIsShowing(driver);
		testSteps.addAll(getTestSteps);
		
		getTestSteps.clear();
		getTestSteps = reservation.verifyGuestNameAfterReservation(driver, guestName);
		testSteps.addAll(getTestSteps);

		getTestSteps.clear();
		getTestSteps = reservation.verifyStatusAfterReservation(driver, "Reserved");
		testSteps.addAll(getTestSteps);

		Reservation resold=new Reservation();
		
		resold.verifyCountryState_BillingInfo(driver, city, state, country);

		testSteps.add("=================== MARKETING INFO VERIFICATION ======================");
		app_logs.info("=================== MARKETING INFO VERIFICATION ======================");

		String foundSource = reservation.get_Reservationsource(driver, testSteps);
//		assertEquals(foundSource, channelName, "Failed Source Missmatched");
		testSteps.add("Successfully Verified Source : " + foundSource);
		app_logs.info("Successfully Verified Source : " + foundSource);

		String foundMarketSegment = reservation.get_MarketSegemnt(driver, testSteps);
		assertEquals(foundMarketSegment, marketSegment, "Failed Market Segment Missmatched");
		testSteps.add("Successfully Verified Market Segment : " + foundMarketSegment);
		app_logs.info("Successfully Verified Market Segment : " + foundMarketSegment);

		String foundReferal = reservation.get_Refferal(driver, testSteps);
		assertEquals(foundReferal, referral, "Failed Referal Missmatched");
		testSteps.add("Successfully Verified Referal : " + foundReferal);
		app_logs.info("Successfully Verified Referal : " + foundReferal);
		
		reservation.closeAllOpenedReservations(driver);
		
		testSteps
				.add("================= SEARCH  ACCOUNT BEFORE CREATION ON ACCOUNT SEARCH PAGE ======================");
		app_logs.info("================= SEARCH  ACCOUNT BEFORE CREATION ON ACCOUNT SEARCH PAGE ======================");
		
		testSteps.add(
				"Search with AccountFirstName and AccountLastName" + firstName + ", " + lastName);
		navigation.GuestHistory(driver);
		int accountsSearchCountAfterRes=guestHistory.searchAccountAndGetCount(driver, firstName, lastName);
		if(accountsSearchCountAfterRes>0) {
		boolean isGuestAfterRes=guestHistory.OpenSearchedGuestHistroyAccountAndVerifyEmail(driver,emailAddress);
		}
		
		if(toggleStateOfGuestProfileBookingsButton.equalsIgnoreCase("Off")) {
			Assert.assertEquals(accountsSearchCount+1, accountsSearchCountAfterRes);
			app_logs.info("Sucessfully verified new Guest Profile is created when toggle is off in NBE");
			testSteps.add("Sucessfully verified new Guest Profile is created when toggle is off in NBE");
			testSteps.add("========= VERIFYING CREATED RESERVATION IN GUEST HISTORY ==========");
			guestHistory.searchAccountAndGetCount(driver, firstName, lastName);
			guestHistory.OpenSearchedGuestHistroyAccount(driver);
			guestHistory.OpenReservtionTab_GuestHistoryAccount(driver);
			guestHistory.searchReservation(driver, reservationNumber);
			guestHistory.verifyReservationExistInSearch(driver, reservationNumber, true, testSteps);
			guestHistory.closeAccount1(driver);			
		}
		if(toggleStateOfGuestProfileBookingsButton.equalsIgnoreCase("On") && (action.equalsIgnoreCase("verifyNewProfile"))) {
			Assert.assertEquals(accountsSearchCount+1, accountsSearchCountAfterRes);
			app_logs.info("Sucessfully verified new Guest Profile is created with new names when toggle is On in NBE");
			testSteps.add("Sucessfully verified new Guest Profile is created with new names when toggle is On in NBE");
			testSteps.add("========= VERIFYING CREATED RESERVATION IN GUEST HISTORY ==========");
			guestHistory.searchAccountAndGetCount(driver, firstName, lastName);
			guestHistory.OpenSearchedGuestHistroyAccount(driver);
			guestHistory.OpenReservtionTab_GuestHistoryAccount(driver);
			guestHistory.searchReservation(driver, reservationNumber);
			guestHistory.verifyReservationExistInSearch(driver, reservationNumber, true, testSteps);
			guestHistory.closeAccount1(driver);			
		}
		if(action.equalsIgnoreCase("verifyNewProfileForEmail")) {
			Assert.assertEquals(accountsSearchCount+1, accountsSearchCountAfterRes);
			app_logs.info("Sucessfully verified new Guest Profile is created with same names but with different email when toggle is On in NBE");
			testSteps.add("Sucessfully verified new Guest Profile is created with same names but with different email when toggle is On in NBE");
			testSteps.add("========= VERIFYING CREATED RESERVATION IN GUEST HISTORY ==========");
			guestHistory.OpenSearchedAllGuestHistroyAccountAndVerifyReservationExists(driver, firstName, 
					lastName, emailAddress, reservationNumber, testSteps);
		}
		if(action.equalsIgnoreCase("verifyNoNewProfile")) {
			Assert.assertEquals(accountsSearchCount, accountsSearchCountAfterRes);
			app_logs.info("Sucessfully verified no new Guest Profile is created with same names when toggle is On in NBE");
			testSteps.add("Sucessfully verified no new Guest Profile is created with same names when toggle is On in NBE");
			testSteps.add("========= VERIFYING CREATED RESERVATION IN GUEST HISTORY ==========");
			guestHistory.searchAccountAndGetCount(driver, firstName, lastName);
			guestHistory.OpenSearchedGuestHistroyAccount(driver);
			guestHistory.OpenReservtionTab_GuestHistoryAccount(driver);
			guestHistory.searchReservation(driver, reservationNumber);
			guestHistory.verifyReservationExistInSearch(driver, reservationNumber, true, testSteps);
			guestHistory.closeAccount1(driver);
		}
		
		
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
							

					
		
		// Generate Report
		try {
			String[] testcase = cases.split(",");
			for (int i = 0; i < testcase.length; i++) {
				statusCode.add(i, "1");
			}
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

	public String logInBookingEngineConfigLink(WebDriver driver, String propertyName) throws InterruptedException {
		
		String getUrl = "";
		try {
			
			login_Group(driver);

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

			getUrl = bookingEngine.clickOnBookingEngineConfigLink(driver);
			testSteps.add("Opened the Booking Engine Configration");
			app_logs.info("Opened the Booking Engine Configration");

			ArrayList<String> tabs2 = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs2.get(0));
			driver.close();
			driver.switchTo().window(tabs2.get(1));

			getTestSteps.clear();
			getTestSteps = bookingEngine.clickOnSettingsLink(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", testName, "RoomClass", driver);
			}
		}
		
		return getUrl;
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("verifyGuestProfileBookingEngine", BEExcel);
	}

	@AfterMethod(alwaysRun = true)
	public void updateTestRailLink() throws MalformedURLException, IOException, APIException {
		System.out.println("In after method");
		 Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, testName,TestCore.TestRail_AssignToID);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() throws MalformedURLException, IOException, APIException {
		//driver.quit();
	}

}
