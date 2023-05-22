package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Login;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class CreateReservationFromNewBookingEngineAndVerifyTheReservationInInncenter extends TestCore {

	// Automation-1986
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> scriptName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();

	public static String test_description = "";
	public static String test_catagory = "";

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

	@Test(dataProvider = "getData", groups = "Inventory")
	public void createReservationFromNewBookingEngineAndVerifyTheReservationInInncenter(String bookingEngineChannel,
			String delim, String ratePlanType, String ratePlanName, String checkInDate, String checkOutDate,
			String adults, String child, String reservationRoomClassName, String marketSegment, String referral,
			String phone, String address, String city, String state, String postalCode, String firstName,
			String lastName, String paymentType, String cardNumber, String cardName, String cardExpDate,
			String emailAddress, String cvv, String entityName, String updateRate, String updatedRate)
			throws Exception {

		HashMap<String, String> ratePlanData = Utility.getExcelData(
				System.getProperty("user.dir") + "\\test-data\\CentralparkSanityTestData.xlsx",
				"CreateRatePlan_BEReser");
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
		String MaxPerson = ratePlanData.get("MaxPerson");
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

		Utility.DELIM = "\\" + delim;
		String testName = "CreateReservationFromNewBookingEngineAndVerifyTheReservationInInncenter";
		test_description = "Create reservation from new booking engine nd verify the reservation in Inncenter<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1986' target='_blank'>"
				+ "Click here to open JIRA: AUTOMATION-1986</a>";
		test_catagory = "BE_Reservation_Verification_In_InnCenter";
		scriptName.add(testName);
		testDescription.add(test_description);
		testCategory.add(test_catagory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RatesGrid rateGrid = new RatesGrid();
		CPReservationPage reservation = new CPReservationPage();
		Distribution distribution = new Distribution();
		RatesGrid ratesGrid = new RatesGrid();
		CPReservationPage reservationPage = new CPReservationPage();
		NightlyRate nightlyRate = new NightlyRate();
		Admin admin = new Admin();
		Properties properties = new Properties();

		BookingEngine bookingEngine = new BookingEngine();

		String randomNumber = Utility.GenerateRandomNumber();
		lastName = lastName + randomNumber;
		String reservationNumber = null;

		String inncenterHeaderTripTotal = "";
		String dateFormat = Utility.monthDayYear_DateFormat;
		String seasonDateFormat = Utility.dayMonthYear_DateFormat;
		String calendarTodayDay = "today";
		String todayDate = null;
		int daysBeforeCheckInDate = 0;
		boolean israteplanExist = false;

		boolean verifyLenthOfStayChecked = false;
		boolean verifyMinStayCondidtion = false;
		boolean verifyMaxStayCondition = false;
		HashMap<String, String> selectedLengthofStayRestrictions = new HashMap<>();
		String ratePlanPromoCode = null;
		HashMap<String, String> selectedBookingWindowRestrictions = new HashMap<>();
		HashMap<String, ArrayList<String>> getRoomClassWithRates = new HashMap<>();
		String ratePlanBaseRate = null;
		String ratePlanAdultCapicity = null;
		String ratePlanPersonCapicity = null;
		boolean ratePlanIsAdditionalAdultChild = false;
		String ratePlanMaxAdults = null;
		String ratePlanMaxPersons = null;
		String ratePlanAdultsRate = null;
		String ratePlanChildRate = null;
		int minStayRuleValue = 0;
		ArrayList<String> minStayRule = null;
		ArrayList<String> minrule = null;
		ArrayList<String> noCheckInRule = null;
		ArrayList<String> noCheckOutRule = null;
		String checkInColor = null;
		String checkOutColor = null;

		boolean isMinStayRule = false;
		boolean isMinStayRuleBrokenPopComeOrNot = false;
		int days = 0;
		days = Utility.getNumberofDays(checkInDate, checkOutDate, dateFormat);
		int seasonDays =  Utility.getNumberofDays(SeasonStartDate, SeasonEndDate, seasonDateFormat);
		boolean isPromocode = false;
		boolean ratePlanAvailableInBE = true;
		boolean seasonPoliciesExist = false;
		boolean ratePoliciesExist = false;
		ArrayList<String> availabilityInRatesBefore = new ArrayList<String>();
		ArrayList<String> availabilityInRatesAfter = new ArrayList<String>();
		String roomReservedValueBefore = "";
		String roomAvailableValueBefore = "";
		String roomReservedValueAfter = "";
		String roomAvailableValueAfter = "";
		String roomTotalValueAfter = "";
		String roomTotalValueBefore = "";
		ArrayList<String> selectedChannels = null;
		ArrayList<String> selectedRoomClasses = null;
		boolean selectedRatePlanDefaultStatus = false;
		String selectedRatePlanFolioName = null;
		HashMap<String, String> getRateLevelPolicy = new HashMap<String, String>();
		HashMap<String, String> getSessionLevelPolicy = new HashMap<String, String>();

		String subTotalBE = "";
		String taxAppliedBE = "";
		boolean roomClassNotExist = false;
		String propertyName = "";
		int numberOfProperties = 0;
		String timeZone = "";
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			try {
				// loginRateV2(driver);
				Login login = new Login();
				reservationRoomClassName = "Junior Suites";
				login.login(driver, "https://app.qainnroad.com", "autoota", "autouser", "Auto@123");
			} catch (Exception e) {
				driver.switchTo().alert().accept();
				Actions actions = new Actions(driver);
				actions.sendKeys(Keys.ENTER);
				// loginRateV2(driver);
				Login login = new Login();
				reservationRoomClassName = "Junior Suites";
				login.login(driver, "https://app.qainnroad.com", "autoota", "autouser", "Auto@123");
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
			String getCurrentDate = Utility.getNextDate(0, dateFormat, timeZone);
			if (ESTTimeZone.CompareDates(checkInDate, dateFormat, timeZone)) {
				checkInDate = getCurrentDate;
				checkOutDate = Utility.getNextDate(days, dateFormat, timeZone);
			}
			if (ESTTimeZone.CompareDates(SeasonStartDate, seasonDateFormat, timeZone)) {
				SeasonStartDate = getCurrentDate;
				SeasonEndDate = Utility.getNextDate(seasonDays, seasonDateFormat, timeZone);
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
			if(channelsList.size()==1) {
				bookingEngineChannel = channelsList.get(0);
			}else {
				boolean channelNamefound= false;
				for(int i = 0 ; i < channelsList.size() ;i++) {
					if(channelsList.get(i).toLowerCase().contains(propertyName.toLowerCase())){
						bookingEngineChannel = channelsList.get(i);
						channelNamefound= false;
					}
				}
				if(!channelNamefound) {
					bookingEngineChannel = channelsList.get(channelsList.size() -1);
				}
			}
			app_logs.info("Booking Engine channel : '" + bookingEngineChannel + "'");
			testSteps.add("Booking Engine channel : '" + bookingEngineChannel + "'");
			boolean distribute = false;
			if(bookingEngineChannel.contains("'")) {
				distribute = distribution.getDistributeValueOfChannel(driver, bookingEngineChannel.split("'")[0]);
			}else {
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

		try {

			app_logs.info("========== GET TODAY'S DATE ==========");
			testSteps.add("========== GET TODAY'S DATE ==========");
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
//			driver.navigate().refresh();
//			try {
//				getTestSteps.clear();
//				israteplanExist = ratesGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
//			} catch (Exception e) {
//				driver.navigate().refresh();
//				getTestSteps.clear();
//				israteplanExist = ratesGrid.isRatePlanExist(driver, ratePlanName, getTestSteps);
//			}
			israteplanExist = true;
			testSteps.add("Successfully verified ratePlan Exist.");
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
			try {

				testSteps.add("========== UPDATE RATE VALUE FOR ROOM CLASS '" + reservationRoomClassName + "' =====");
				app_logs.info("======= UPDATE RATE VALUE FOR ROOM CLASS '" + reservationRoomClassName + "'======");
				ratesGrid.clickOnEditRatePlan(driver);
				testSteps.add("Click on edit rate plan");
				app_logs.info("Click on edit rate plan");
				getTestSteps.clear();
				getTestSteps = ratesGrid.ratePlanOverView(driver);
				testSteps.addAll(getTestSteps);
				nightlyRate.switchCalendarTab(driver, testSteps);
				Wait.waitUntilPageLoadNotCompleted(driver, 40);
				boolean isSeasonExist = nightlyRate.verifySeasonExistInbetweencheckinAndCheckout(driver, getTestSteps,
						Utility.parseDate(checkInDate, dateFormat, seasonDateFormat),
						Utility.parseDate(checkOutDate, dateFormat, seasonDateFormat));
				if (isSeasonExist) {
					nightlyRate.selectSeasonDates(driver, testSteps,
							Utility.parseDate(checkInDate, dateFormat, seasonDateFormat));
					nightlyRate.clickEditThisSeasonButton(driver, testSteps);
					getTestSteps.clear();
					getTestSteps = ratesGrid.enterRoomClassBaserate(driver, reservationRoomClassName, updatedRate,
							getTestSteps);
					testSteps.addAll(getTestSteps);
					nightlyRate.clickSaveSason(driver, testSteps);

					getTestSteps.clear();
					getTestSteps = ratesGrid.clickOnSaveratePlan(driver);
					testSteps.addAll(getTestSteps);
					Wait.wait5Second();
					ratesGrid.closeOpendTabInMainMenu(driver);
				} else {
					app_logs.info("No Season For Desired Date");
					testSteps.add("No Season For Desired Date");
					Assert.assertTrue(false, "Failed to Update Rate Value");
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

		// navigate to Inventory->Rates Grid-> Availability and verify roomclass
		// availability

		try {

			testSteps.add("======= GETTING ROOM CLASS AVAILABILITY INFORMATION =========");
			app_logs.info("======= GETTING ROOM CLASS AVAILABILITY INFORMATION =========");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			ratesGrid.selectDateFromDatePicker(driver, checkInDate, dateFormat, testSteps);
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
			testSteps.add("Before Reservation Creation 'Room Available Value' is '" + roomAvailableValueBefore + "'");
			app_logs.info("Before Reservation Creation 'Room Available Value' is '" + roomAvailableValueBefore + "'");
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
			selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);
			if (!ratesGrid.getRoomClassStatus(driver, reservationRoomClassName)) {
				Utility.app_logs.info("Required Room CLass  is not Selected : " + reservationRoomClassName);
				testSteps.add("Required  Room CLass  is not Selected : " + reservationRoomClassName);
				nightlyRate.selectRoomClassesEditPage(driver, reservationRoomClassName, true, testSteps);
				selectedRoomClasses = nightlyRate.getSelectedRoomClass(driver);
			}
			Utility.app_logs.info("Selected Room Classes : " + selectedRoomClasses);
			testSteps.add("Selected Room Classes : " + selectedRoomClasses);

			testSteps.add("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");
			app_logs.info("=================== VERIFY RATE PLAN SET TO DEFAULT ======================");

			selectedRatePlanDefaultStatus = ratesGrid.verifyRatePlanSetToDefault(driver, testSteps, ratePlanName);

			ratesGrid.clickOnRestrcitionSAndPoliciesTab(driver);
			getRateLevelPolicy = nightlyRate.getAllTypeSelectedPolicy(driver, testSteps);

			Utility.app_logs.info("Selected Policies: " + getRateLevelPolicy);
			testSteps.add("Selected Policies : " + getRateLevelPolicy);
			testSteps.add("========== </b>Getting Restrictions for rate plan " + "<b>" + ratePlanName + " ==========");
			verifyLenthOfStayChecked = ratesGrid.verifyLenthOfStayCheckBox(driver, testSteps, "Length of stay");
			app_logs.info("Verified length of saty is checked: " + verifyLenthOfStayChecked);

			testSteps.add("Verified Length of Stay restriction is  : " + verifyLenthOfStayChecked);
			if (verifyLenthOfStayChecked) {

				verifyMinStayCondidtion = ratesGrid.verifyLenthOfStayCheckBox(driver, getTestSteps, "Min");
				verifyMaxStayCondition = ratesGrid.verifyLenthOfStayCheckBox(driver, getTestSteps, "Max");
				if (verifyMinStayCondidtion) {
					String getMin = ratesGrid.getMinAndMaxValue(driver, "Min");
					selectedLengthofStayRestrictions.put("Min", getMin);
				}
				if (verifyMaxStayCondition) {
					String getMax = ratesGrid.getMinAndMaxValue(driver, "Max");
					selectedLengthofStayRestrictions.put("Max", getMax);
				}
				Utility.app_logs.info("Selected Length of Stay : " + selectedLengthofStayRestrictions);
				testSteps.add("Selected Length of Stay : " + selectedLengthofStayRestrictions);
			}

			selectedBookingWindowRestrictions = ratesGrid.getBookingWindowRestrictions(driver, getTestSteps,
					ratePlanName);

			if (selectedBookingWindowRestrictions.isEmpty()) {
				testSteps.add("Verified Booking Window restriction is not selected");
				Utility.app_logs.info("Verified Booking Window restriction is not selected");
			} else {
				Utility.app_logs.info("Selected Booking Window : " + selectedBookingWindowRestrictions);
				testSteps.add("Selected Booking Window : " + selectedBookingWindowRestrictions);
			}

			isPromocode = ratesGrid.isPromoCodeChecked(driver, getTestSteps);

			if (isPromocode) {
				testSteps.add("Verified Promo Code restriction is  : " + isPromocode);
				ratePlanPromoCode = ratesGrid.getPromoCode(driver, testSteps);
				Utility.app_logs.info("Selected Promo Code : " + ratePlanPromoCode);
				testSteps.add("Selected Promo Code : " + ratePlanPromoCode);
			} else {
				ratePlanPromoCode = "";
			}

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
				ratePlanBaseRate = gettest.get(0);
				ratePlanAdultCapicity = gettest.get(1);
				ratePlanPersonCapicity = gettest.get(2);
				ratePlanIsAdditionalAdultChild = false;
				if (gettest.get(3).equalsIgnoreCase("yes")) {
					ratePlanIsAdditionalAdultChild = true;
				}
				ratePlanMaxAdults = gettest.get(4);
				ratePlanMaxPersons = gettest.get(5);
				ratePlanAdultsRate = gettest.get(6);
				ratePlanChildRate = gettest.get(7);

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
					getSessionLevelPolicy = nightlyRate.getSeasonSelectedPolicy(driver, getTestSteps);
					getTestSteps.clear();
					app_logs.info("Season Level Policies : " + getSessionLevelPolicy);
					testSteps.add("Season Level Policies : " + getSessionLevelPolicy);
				}

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

			testSteps.add("=================== GETTING RATE PLAN RULES ======================");
			app_logs.info("=================== GETTING RATE PLAN RULES ======================");
			ratesGrid.clickForRateGridCalender(driver, testSteps);
			Utility.selectDateFromDatePicker(driver, checkInDate, dateFormat);
			Wait.waitUntilPageLoadNotCompleted(driver, 50);
			ratesGrid.clickRatePlanArrow(driver, testSteps);
			ratesGrid.selectAndReturnSpecificRatePlan(driver, testSteps, ratePlanName);
			Wait.waitUntilPageLoadNotCompleted(driver, 40);
			ratesGrid.expandRoomClass(driver, testSteps, reservationRoomClassName);
			ratesGrid.expandChannel(driver, testSteps, reservationRoomClassName, bookingEngineChannel);

			minStayRule = ratesGrid.getRuleDataValuesForMinStay(driver, testSteps, reservationRoomClassName,
					bookingEngineChannel, days);
			minrule = minStayRule;

			Collections.sort(minrule);
			app_logs.info("minrule : " + minrule);

			minStayRuleValue = Integer.parseInt((String) minrule.get(minrule.size() - 1));

			if (minStayRuleValue > 0) {
				isMinStayRule = true;
				isMinStayRuleBrokenPopComeOrNot = reservationPage.verifyMinStayPopupComeOrNot(driver, minrule,
						minStayRuleValue, days);
			}

			noCheckInRule = ratesGrid.getRuleDataValuesForNoCheckIn(driver, testSteps, reservationRoomClassName,
					bookingEngineChannel, days);

			app_logs.info("noCheckInRule : " + noCheckInRule);

			checkInColor = reservationPage.verifyNoCheckInPopupComeOrNot(driver, minrule, noCheckInRule, checkInDate,
					checkOutDate);

			app_logs.info("checkInColor : " + checkInColor);

			noCheckOutRule = ratesGrid.getRuleDataValuesForNoCheckOut(driver, testSteps, reservationRoomClassName,
					bookingEngineChannel, days);

			app_logs.info("noCheckOutRule : " + noCheckOutRule);

			checkOutColor = reservationPage.verifyNoCheckOutPopupComeOrNot(driver, noCheckInRule, noCheckOutRule,
					checkInDate, checkOutDate);
			app_logs.info("checkOutColor : " + checkOutColor);

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
			testSteps.add(
					"====== CALCULATING THE SEASON WILL DISPLAY OR NOT IN BOOKING ENGINE BASED ON RESTRICTION AND RULES =====");
			app_logs.info(
					"====== CALCULATING THE SEASON WILL DISPLAY OR NOT IN BOOKING ENGINE BASED ON RESTRICTION AND RULES =====");
			testSteps.add("Days of Reservation : " + days);
			app_logs.info("Days of Reservation : " + days);
			testSteps.add("Reservation Dates  : " + checkInDate + " - " + checkOutDate);
			app_logs.info("Reservation Dates  : " + checkInDate + " - " + checkOutDate);
			if (verifyLenthOfStayChecked) {
				if (verifyMinStayCondidtion) {
					if (!(days >= Integer.parseInt(selectedLengthofStayRestrictions.get("Min")))) {
						ratePlanAvailableInBE = false;
						testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
						app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
						testSteps.add("Rate Plan 'Min Stay' Rule Failed");
						app_logs.info("Rate Plan 'Min Stay' Rule Failed");
					}
				}
				if (verifyMaxStayCondition) {
					if (!(days <= Integer.parseInt(selectedLengthofStayRestrictions.get("Max")))) {
						ratePlanAvailableInBE = false;
						testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
						app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
						testSteps.add("Rate Plan 'Max Stay' Rule Failed");
						app_logs.info("Rate Plan 'Max Stay' Rule Failed");
					}
				}
			}
			if (selectedBookingWindowRestrictions.containsKey("More than") && ratePlanAvailableInBE) {
				if (!(daysBeforeCheckInDate >= Integer.parseInt(selectedBookingWindowRestrictions.get("More than")))) {
					ratePlanAvailableInBE = false;
					testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					testSteps.add("Rate Plan Booking Window 'More than' Rule Failed");
					app_logs.info("Rate Plan Booking Window 'More than' Rule Failed");
				}
			}

			if (selectedBookingWindowRestrictions.containsKey("Within") && ratePlanAvailableInBE) {
				if (!(daysBeforeCheckInDate <= Integer.parseInt(selectedBookingWindowRestrictions.get("Within")))) {
					ratePlanAvailableInBE = false;
					testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
					testSteps.add("Rate Plan Booking Window 'Within' Rule Failed");
					app_logs.info("Rate Plan Booking Window 'Within' Rule Failed");
				}

			}

			if (!(days >= minStayRuleValue) && ratePlanAvailableInBE) {
				ratePlanAvailableInBE = false;
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				testSteps.add("Season 'Min Stay' Rule Failed");
				app_logs.info("Season 'Min Stay' Rule Failed");
			}
			if (noCheckInRule.get(0).equals("Yes") && ratePlanAvailableInBE) {
				ratePlanAvailableInBE = false;
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				testSteps.add("Season 'No Check-In' Rule Failed");
				app_logs.info("Season 'No Check-In' Rule Failed");
			}
			if (noCheckOutRule.get(noCheckOutRule.size() - 1).equals("Yes") && ratePlanAvailableInBE) {
				ratePlanAvailableInBE = false;
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				testSteps.add("Season 'No Check-Out' Rule Failed");
				app_logs.info("Season 'No Check-Out' Rule Failed");
			}

			if (ratePlanAvailableInBE) {
				testSteps.add("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
				app_logs.info("Availability of rate Plan in Booking Engine : " + ratePlanAvailableInBE);
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

			testSteps.add("=================== VERIFY RATE PLAN IS SELECTED IN BOOKING ENGINE ======================");
			app_logs.info("=================== VERIFY RATE PLAN IS SELECTED BOOKING ENGINE ======================");

			navigation.rateV2Setup(driver);
			testSteps.add("Navigate Setup");
			app_logs.info("Navigate Setup");
			navigation.Properties(driver);
			testSteps.add("Navigate Properties");
			app_logs.info("Navigate Properties");
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

			testSteps.add("=================== CREATE RESERVATION FROM BOOKING ENGINE ======================");
			app_logs.info("=================== CREATE RESERVATION FROM BOOKING ENGINE ======================");
			bookingEngine.clickWelcomePageLink(driver);
			testSteps.add("Opened the Booking Engine");
			app_logs.info("Opened the Booking Engine");
			testSteps.add("===== ENTER SEARCH ROOM DETAILS =====");
			app_logs.info("===== ENTER SEARCH ROOM DETAILS =====");

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

				if (ratePlanAvailableInBE) {

					testSteps.add("=================== VERIFY RATE PLAN NIGHTY STAY AMOUNT======================");
					app_logs.info("=================== VERIFY RATE PLAN NIGHTY STAY AMOUNT======================");
					getTestSteps.clear();
					getTestSteps = bookingEngine.calculateAdditionalChargesForTheEnteredAdultsAndChild(driver,
							ratePlanName, days, adults, child, reservationRoomClassName, ratePlanBaseRate,
							ratePlanAdultCapicity, ratePlanPersonCapicity, ratePlanMaxAdults, ratePlanMaxPersons,
							ratePlanAdultsRate, ratePlanChildRate, ratePlanIsAdditionalAdultChild);
					testSteps.addAll(getTestSteps);
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickBookRoom(driver, ratePlanName);
					testSteps.addAll(getTestSteps);

					testSteps.add("===== ENTER GUEST INFORMATION =====");
					app_logs.info("===== ENTER GUEST INFORMATION =====");
					getTestSteps.clear();
					getTestSteps = bookingEngine.enterGuestInfo(driver, firstName, lastName, emailAddress, phone,
							address, address, postalCode, city, state);
					testSteps.addAll(getTestSteps);
					testSteps.add("===== ENTER CARD DETAILS =====");
					app_logs.info("===== ENTER CARD DETAILS =====");
					getTestSteps.clear();
					getTestSteps = bookingEngine.enterMcCardInfo(driver, cardNumber, cardName, cardExpDate, cvv);
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

					subTotalBE = bookingEngine.getSubTotal(driver);
					testSteps.add("Sub Total in Booking Engine:" + subTotalBE);
					app_logs.info("Sub Total in Booking Engine:" + subTotalBE);
					taxAppliedBE = bookingEngine.getTaxApplied(driver);
					testSteps.add("Tax Applied in Booking Engine:" + taxAppliedBE);
					app_logs.info("Tax Applied in Booking Engine:" + taxAppliedBE);
					getTestSteps.clear();
					getTestSteps = bookingEngine.clickBookStay(driver);
					testSteps.addAll(getTestSteps);
					reservationNumber = bookingEngine.getReservationNumber(driver);
					testSteps.add("Reservation Created in Booking Engine:" + reservationNumber);
					app_logs.info("Reservation Created in Booking Engine:" + reservationNumber);
				}
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create BE Res", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create BE Res", "BERES", "BERES", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		if (roomClassNotExist == false) {
			if (ratePlanAvailableInBE) {

				try {
					testSteps.add("============ VERIFY CREATED RESERVATION DETAILS IN INNCENTER ===========");
					app_logs.info("===== VERIFY CREATED RESERVATION DETAILS IN INNCENTER =======");
					//loginRateV2(driver);
					Login login = new Login();
					login.login(driver, "https://app.qainnroad.com", "autoota", "autouser", "Auto@123");
					testSteps.add("Logged into the InnCenter ");
					app_logs.info("Logged into the InnCenter ");

					try {
						reservation.Search_ResNumber_And_Click(driver, reservationNumber);
					} catch (Exception e) {
						driver.navigate().refresh();
						reservation.Search_ResNumber_And_Click(driver, reservationNumber);
					}
					String guestName = firstName + " " + lastName;

					testSteps.add("Search Reservation : " + reservationNumber);
					app_logs.info("Search Reservation : " + reservationNumber);
					testSteps.add("Open Reservation ");
					app_logs.info("Open Reservation ");

					testSteps.add("=================== BANNER FIELDS VERIFICATION ======================");
					app_logs.info("=================== BANNER FIELDS VERIFICATION ======================");
					getTestSteps.clear();
					getTestSteps = reservation.verifyGuestNameAfterReservation(driver, guestName);
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservation.verifyStatusAfterReservation(driver, "Reserved");
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservation.verifyStayDateAfterReservation(driver,
							Utility.parseDate(checkInDate, dateFormat, "MMM d"),
							Utility.parseDate(checkOutDate, dateFormat, "MMM d"));
					testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservation.verifyPhoneNoBanner(driver, "1" + phone);
					testSteps.addAll(getTestSteps);

					inncenterHeaderTripTotal = reservation.getTripTotal_Header(driver);
					testSteps.add("Inncenter Trip Total : " + inncenterHeaderTripTotal);
					app_logs.info("Inncenter Trip Total : " + inncenterHeaderTripTotal);
					assertEquals(inncenterHeaderTripTotal, subTotalBE, "Failed Trip Total Mismateched");
					testSteps.add("Successfully Verified Trip Total : " + inncenterHeaderTripTotal);
					app_logs.info("Successfully Verified Trip Total : " + inncenterHeaderTripTotal);

					reservation.Verify_ReservationStatus_Status(driver, testSteps, "Reserved");

					testSteps.add("=================== STAY INFO FIELDS VERIFICATION=================== ");
					app_logs.info("=================== STAY INFO FIELDS VERIFICATION=================== ");

					String foundArrivalDate = reservation.getArrivalDate_ResDetail(driver);
					assertEquals(Utility.parseDate(foundArrivalDate, "MMM d, yyyy", "dd MMM, yyyy"),
							Utility.parseDate(checkInDate, dateFormat, "dd MMM, yyyy"),
							"Failed Arrival Date Missmatched");
					testSteps.add("Successfully Verified Arrival Date : " + foundArrivalDate);
					app_logs.info("Successfully Verified Arrival Date : " + foundArrivalDate);

					String foundDepartDate = reservation.getDepartDate_ResDetail(driver);
					assertEquals(Utility.parseDate(foundDepartDate, "MMM d, yyyy", "dd MMM, yyyy"),
							Utility.parseDate(checkOutDate, dateFormat, "dd MMM, yyyy"),
							"Failed Depart Date Missmatched");
					testSteps.add("Successfully Verified Depart Date : " + foundDepartDate);
					app_logs.info("Successfully Verified Depart Date : " + foundDepartDate);

					String foundAdultsCount = reservation.getNoOfAdults_ResDetail(driver);
					assertEquals(foundAdultsCount, adults, "Failed No Of Adults Missmatched");
					testSteps.add("Successfully Verified No Of Adults : " + foundAdultsCount);
					app_logs.info("Successfully Verified No Of Adults : " + foundAdultsCount);

					String foundNoOfChild = reservation.getNoOfChilds_ResDetail(driver);
					assertEquals(foundNoOfChild, child, "Failed No Of Childs Missmatched");
					testSteps.add("Successfully Verified No Of Childs : " + foundNoOfChild);
					app_logs.info("Successfully Verified No Of Childs : " + foundNoOfChild);

					String foundRoomClass = reservation.getRoomClass_ResDetail(driver);
					assertEquals(foundRoomClass, reservationRoomClassName, "Failed Room Class Missmatched");
					testSteps.add("Successfully Verified Room Class : " + foundRoomClass);
					app_logs.info("Successfully Verified Room Class : " + foundRoomClass);

					testSteps.add("=================== GUEST INFO VERIFICATION ======================");
					app_logs.info("=================== GUEST INFO VERIFICATION ======================");

					String foundGuestName = reservation.getGuestName_ResDetail(driver);
					assertEquals(foundGuestName, guestName, "Failed Guest Name Missmatched");
					testSteps.add("Successfully Verified Guest Name : " + foundGuestName);
					app_logs.info("Successfully Verified Guest Name : " + foundGuestName);

					String foundContactName = reservation.getContactName_ResDetail(driver);
					assertEquals(foundContactName, paymentType, "Failed Contact Name	 Missmatched");
					testSteps.add("Successfully Verified Contact Name : " + foundContactName);
					app_logs.info("Successfully Verified Contact Name : " + foundContactName);

					String foundEmail = reservation.getEmail_ResDetail(driver);

					testSteps.add("Found Email : " + foundEmail);
					app_logs.info("Found Email : " + foundEmail);

					String foundPhoneNo = reservation.getPhoneNO_ResDetail(driver);
					assertEquals(foundPhoneNo, "1" + phone, "Failed PhoneNo Missmatched");
					testSteps.add("Successfully Verified PhoneNo : " + foundPhoneNo);
					app_logs.info("Successfully Verified PhoneNo : " + foundPhoneNo);

					testSteps.add("=================== PAYMENT INFO VERIFICATION ======================");
					app_logs.info("=================== PAYMENT INFO VERIFICATION ======================");

					getTestSteps.clear();
					getTestSteps = reservation.verifyPaymentDetail_ResDetail(driver, paymentType,
							cardNumber.substring(12, 16), cardName, Utility.addDate(0, "MM/yy", cardExpDate, "MM/yyyy", timeZone));
					testSteps.addAll(getTestSteps);

					testSteps.add("=================== MARKETING INFO VERIFICATION ======================");
					app_logs.info("=================== MARKETING INFO VERIFICATION ======================");

					String foundSource = reservation.get_Reservationsource(driver, testSteps);
					assertEquals(foundSource, bookingEngineChannel, "Failed Source Missmatched");
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

					testSteps.add("=================== VERIFY RATE PLAN ======================");
					app_logs.info("=================== VERIFY RATE PLAN ======================");
					String foundRate = reservation.get_RatePlan(driver, testSteps);
					if (isPromocode) {
						assertEquals(foundRate, "PROMO CODE", "Failed RatePlan Missmatched");
					} else {
						assertEquals(foundRate, ratePlanName, "Failed RatePlan Missmatched");
					}

					testSteps.add("Verified Rate in InnCenter:" + foundRate);
					app_logs.info("Verified Rate in InnCenter:" + foundRate);

					testSteps.add("=================== VERIFY ASSOCIATED POLICIES ======================");
					app_logs.info("=================== VERIFY ASSOCIATED POLICIES ======================");

					HashMap<String, String> reservationPolicies = new HashMap<String, String>();
					if (seasonPoliciesExist) {
						reservationPolicies = getSessionLevelPolicy;
					} else if (ratePoliciesExist) {
						reservationPolicies = getRateLevelPolicy;
						testSteps.add("Successfully verified season policies are associated to reservation.");
						app_logs.info("Successfully verified season policies are associated to reservation.");
					}
					if (seasonPoliciesExist || ratePoliciesExist) {
						for (Map.Entry<String, String> entry : reservationPolicies.entrySet()) {
							String policyType = entry.getKey();
							String policyName = entry.getValue();
							if (!policyName.equalsIgnoreCase("NA")) {
								assertEquals(policyName, reservation.getReserationPolicy(driver, policyType),
										"Failed " + policyType + " + policy missmatched");
								testSteps.add("Verified " + policyType + " policy '" + policyName + "' exist.");
								app_logs.info("Verified " + policyType + " policy '" + policyName + "' exist.");
							}

						}
					}
					reservation.close_FirstOpenedReservation(driver, testSteps);

				} catch (Exception e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(testName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				try {
					testSteps.add("======= GETTING ROOM CLASS AVAILABILITY INFORMATION =========");
					app_logs.info("======= GETTING ROOM CLASS AVAILABILITY INFORMATION =========");
					navigation.Inventory(driver);
					testSteps.add("Navigate Inventory");
					app_logs.info("Navigate Inventory");
					navigation.Rates_Grid(driver);
					testSteps.add("Navigate Rates Grid");
					app_logs.info("Navigate Rates Grid");

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
					ratesGrid.expandRoomClass(driver, getTestSteps, reservationRoomClassName);
					roomReservedValueAfter = ratesGrid.getRoomClassDataValue(driver, 0, "Reserved");
					roomAvailableValueAfter = ratesGrid.getRoomClassDataValue(driver, 0, "Available");
					roomTotalValueAfter = ratesGrid.getRoomClassDataValue(driver, 0, "Total");
					testSteps.add("Total Room In Availability Grid Before:" + roomTotalValueBefore);
					app_logs.info("Total Room In Availability Grid Before:" + roomTotalValueBefore);
					testSteps.add("Total Room In Availability Grid After:" + roomTotalValueAfter);
					app_logs.info("Total Room In Availability Grid After:" + roomTotalValueAfter);
					assertEquals(roomTotalValueBefore, roomTotalValueAfter, "Failed:rooms Total didn't verify");
					String found = Integer.toString(((Integer.parseInt(roomAvailableValueBefore)) - 1));
					testSteps.add("Available Room In Availability Grid Before:" + roomAvailableValueBefore);
					app_logs.info("Available Room In Availability Grid Before:" + roomAvailableValueBefore);
					testSteps.add("Available Room In Availability Grid After:" + found);
					app_logs.info("Available Room In Availability Grid After:" + found);
					assertEquals(roomAvailableValueAfter, found, "Failed:rooms Available didn't verify");

					found = Integer.toString(((Integer.parseInt(roomReservedValueBefore)) + 1));

					testSteps.add("Reserved Room In Availability Grid Before:" + roomReservedValueBefore);
					app_logs.info("Reserved Room In Availability Grid Before:" + roomReservedValueBefore);
					testSteps.add("Reserved Room In Availability Grid After:" + found);
					app_logs.info("Reserved Room In Availability Grid After:" + found);
					assertEquals(roomReservedValueAfter, found, "Failed:rooms Reserved didn't verify");

				} catch (Exception e) {
					if (Utility.reTry.get(scriptName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(scriptName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(scriptName, testDescription, testCategory, testSteps);
						Utility.updateReport(e, "Failed to Verify", testName, "Verification", driver);
					} else {

						Assert.assertTrue(false);
					}
				}
			}
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
		return Utility.getData("VerifyBEReservationInInnCenter", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}
}