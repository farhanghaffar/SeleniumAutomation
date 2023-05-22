package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Distribution;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.Reports;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class RatesV2AvailabilityTabInncenterScenarios extends TestCore {

	// Automation-1698
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> testName = new ArrayList<>();
	ArrayList<String> testCategory = new ArrayList<>();
	ArrayList<String> testDescription = new ArrayList<>();
	String scriptName = "RatesV2AvailabilityTabInncenterScenarios";
	public static String test_description = "";
	public static String test_category = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Inventory")
	public void ratesV2AvailabilityTabInncenterScenarios(String day, String totalOccupancy, String paceVsLastYear,
			String addRatePlan, String bulkUpdate, String rates, String availability, String rules,
			String nightlyRatePlan, String drivedRatePlan, String pakageRatePlan, String intervalRatePlan,
			String bulkUpdateRateTitle, String bulkUpdateAvailabilityTitle, String bulkUpdateRulesTitle,
			String weekendColor, String otherweekDayColor, String nextDays, String totalDaysDisplayed,
			String maximumRoomClassesToExpand, String source, String specificDate, String dateFormat,
			String calendarTodayDay, String blockedStatus, String greenStatus, String multipleSpecificdates,
			String singleDate, String ratePlan, String adults, String children, String rateName, String baseAmount,
			String addtionalAdult, String additionalChild, String displayName, String associateSeason,
			String rateRPolicy, String rateDescription, String roomClassAbb, String roomClassName, String bedsCount,
			String maxAdults, String maxPersons, String roomQuantity, String outOfOrderRoomNumber, String marketSegment,
			String groupReferral, String groupFirstName, String groupLastName, String groupPhone, String groupAddress,
			String groupCity, String groupCountry, String groupState, String groupPostalcode, String blockName,
			String roomPerNight, String firstName, String lastName, String updatedBlockedCount, String roomBlockCount,
			String delim, String ratePlanName, String folioDisplayName, String description, String channels,
			String seasonName, String seasonDateFormat, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String RatePerNight, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight, String reason, String subject,String inActiveRoomClassName,String  inActiveRoomClassAbb,String  inActiveStatus,String activeStatus)
			throws InterruptedException, IOException {

		test_description = "Rates V2 - Availability tab Inncenter scenarios<br>"
				+ "<a href='https://innroad.atlassian.net/browse/AUTOMATION-1698' target='_blank'>"
				+ "Click here to open Jira: AUTOMATION-1698</a>";

		test_category = "Rates Grid";
		testName.add(scriptName);
		testDescription.add(test_description);
		testCategory.add(test_category);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + scriptName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		RoomMaintenance roomMaintenance = new RoomMaintenance();
		Reports reports = new Reports();
		Distribution distribution = new Distribution();
		NightlyRate nightlyRate = new NightlyRate();

		Tapechart tapechart = new Tapechart();

		CPReservationPage cpReservation = new CPReservationPage();
		RatesGrid ratesGrid = new RatesGrid();
		RoomClass roomClass = new RoomClass();
		Groups group = new Groups();
		AdvGroups advanceGroup = new AdvGroups();
		ArrayList<String> getRoomClasses = new ArrayList<>();
		ArrayList<String> activeChannelsList = new ArrayList<String>();
		String timeZone = "America/New_York";
		app_logs.info("Time Zone " + timeZone);
		app_logs.info("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", timeZone));

		String randomNumber = Utility.GenerateRandomNumber(99);
		// randomNumber = "23";
		// startRoomNumber = "147";
		// roomClassName = roomClassName + randomNumber;
		// roomClassAbb = roomClassAbb + randomNumber;
		maximumRoomClassesToExpand = "1";
		rateName = rateName + randomNumber;
		seasonName = seasonName + randomNumber;
		displayName = rateName;
		String accountNumber = Utility.GenerateRandomString15Digit();
		ratePlanName = ratePlanName + randomNumber;
		folioDisplayName = folioDisplayName + randomNumber;
		groupLastName = groupLastName + randomNumber;
		String accountName = groupFirstName + groupLastName;
		subject = subject + randomNumber;
		//String propertyName = Utility.WestPointInnProperty;
		String roomTotalValue = null;
		String roomReservedValue = null;
		String occupancyPercentageValue = null;
		String paceVsLastYearValue = null;
		String calendarTodayDate = null;
		String fileName = null;
		String lines[] = null;
		String beforeTotalRooms = null;
		String beforeRoomsSold = null;
		String beforeRoomsOutOfInventory = null;
		String beforeRoomsAvailable = null;
		String beforeOccupancy = null;
		String afterOccupancy = null;
		String afterTotalRooms = null;
		String afterRoomsSold = null;
		String afterRoomsOutOfInventory = null;
		String afterRoomsAvailable = null;
		String beforeOOOCount = null;
		String afterOOOCount = null;
		String beforeAvailableRooms = null;
		String afterAvailableRooms = null;

		String beforeAvailableRoomsRatesGrid = null;
		String afterAvailableRoomsRatesGrid = null;
		String labelTotalRooms = "Total Rooms";
		String labelRoomsSold = "Rooms Sold";
		String labelRoomsOutOfInventory = "Out of Inventory";
		String labelRoomsAvailable = "Rooms Available";
		String labelOccupancy = "Occupancy";

		// calendarTodayDate = "July/21/2020";

		try {
			if (!Utility.insertTestName.containsKey(scriptName)) {
				Utility.insertTestName.put(scriptName, scriptName);
				Utility.reTry.put(scriptName, 0);

			} else {
				Utility.reTry.replace(scriptName, 1);
			}
			driver = getDriver();
			// loginWPI(driver);
			login_CP(driver);
			testSteps.add("Entered appication URL : " + TestCore.envURL);
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Get Time Zone
		try {
			//
			//
			// testSteps.add("===================GET PROPERTY TIME
			// ZONE======================");
			// app_logs.info("===================GET PROPERTY TIME
			// ZONE======================");
			// navigation.Setup(driver);
			// testSteps.add("Navigate Setup");
			// app_logs.info("Navigate Setup");
			// navigation.Properties(driver);
			// testSteps.add("Navigat Properties");
			// app_logs.info("Navigat Properties");
			// navigation.open_Property(driver, testSteps, propertyName);
			// testSteps.add("Open Property : " + propertyName);
			// app_logs.info("Open Property : " + propertyName);
			// navigation.click_PropertyOptions(driver, testSteps);
			// timeZone = navigation.get_Property_TimeZone(driver);
			// navigation.Reservation_Backward(driver);
			// testSteps.add("Time Zone " + timeZone);
			// app_logs.info("Time Zone " + timeZone);
			// app_logs.info("Curret Time " + Utility.getCurrentDate("MMM
			// dd,yyy: H:m:s", timeZone));

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Time Zone", scriptName, "getTimeZone", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to get Time Zone", scriptName, "getTimeZone", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("==========GET ALL ACTIVE CHANNEL=========");
			app_logs.info("==========GET ALL ACTIVE CHANNEL==========");

			navigation.Inventory(driver);
			testSteps.add("Navigate Setup");
			navigation.Distribution(driver);
			testSteps.add("Navigate Distribution");
			activeChannelsList = distribution.getAllActiveChannelDetails(driver);
			System.out.println(activeChannelsList);
			testSteps.addAll(activeChannelsList);
			System.out.println(" size is:" + activeChannelsList.size());

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to store room class in arraylist", scriptName, "StoreRoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to store room class in arraylist", scriptName, "StoreRoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

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

			boolean isRoomClassShowing = roomClass.searchClass(driver, roomClassName);
			app_logs.info("Search");
			if (isRoomClassShowing) {

				roomClass.deleteRoomClass(driver, roomClassName);
				testSteps.add("Delete room class successfully");
				app_logs.info("Delete room class successfully");
			} else {
				testSteps.add("No room class exist");
			}
			roomClass.selectRoomClassSearchStatus(driver, inActiveStatus);
			isRoomClassShowing = roomClass.searchClass(driver, inActiveRoomClassName);
			app_logs.info("Search");
			if (isRoomClassShowing) {

				roomClass.deleteRoomClass(driver, inActiveRoomClassName);
				testSteps.add("Delete room class successfully");
				app_logs.info("Delete room class successfully");
			} else {
				testSteps.add("No room class exist");
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", scriptName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to delete room class", scriptName, "RoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
			inActiveRoomClassName = inActiveRoomClassName + randomNumber;
			inActiveRoomClassAbb = inActiveRoomClassAbb + randomNumber;

			app_logs.info("==========CREATE INACTIVE ROOM CLASS==========");
			testSteps.add("==========CREATE INACTIVE ROOM CLASS==========");

			navigation.clickOnNewRoomClass(driver);
			testSteps.add("Click on new room class button");
			getTestSteps = roomClass.createRoomClassWithStatus(driver, inActiveRoomClassName, inActiveRoomClassAbb, bedsCount, maxAdults,
					maxPersons, roomQuantity, test,inActiveStatus, getTestSteps);

			roomClass.CloseOpenedRoomClass(driver, roomClassName, testSteps);

			roomClass.selectRoomClassSearchStatus(driver, inActiveStatus);
			
			boolean isRoomClassShowing = roomClass.searchClass(driver, inActiveRoomClassName);
			app_logs.info("Search");
			if (isRoomClassShowing) {
				testSteps.add("In Activate Room Class(" + inActiveRoomClassName +") successfully created");
				app_logs.info("In Activate Room Class(" + inActiveRoomClassName +") successfully created");
			} else {
				Assert.assertTrue(false,"No room class created");
			}
			roomClassName = roomClassName + randomNumber;
			roomClassAbb = roomClassAbb + randomNumber;

			app_logs.info("==========CREATE ROOM CLASS==========");
			testSteps.add("==========CREATE ROOM CLASS==========");

			navigation.clickOnNewRoomClass(driver);
			testSteps.add("Click on new room class button");
			getTestSteps = roomClass.createRoomClass(driver, roomClassName, roomClassAbb, bedsCount, maxAdults,
					maxPersons, roomQuantity, test, getTestSteps);
			
			outOfOrderRoomNumber = Utility.RoomNo;
			roomClass.CloseOpenedRoomClass(driver, roomClassName, testSteps);
			
			app_logs.info("==========GET ALL ACIVE ROOM CLASS==========");
			testSteps.add("==========GET ALL ACIVE ROOM CLASS==========");
			roomClass.selectRoomClassSearchStatus(driver, activeStatus);
			roomClass.searchButtonClick(driver);
			getRoomClasses = roomClass.getAllRoomClasses(driver);
			testSteps.add("Active RoomClasses:" + getRoomClasses);
			app_logs.info("Active RoomClasses:" + getRoomClasses);
			// testSteps.addAll(getRoomClasses);
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", scriptName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", scriptName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// navigate to Inventory->Rates Grid
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
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			calendarTodayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.closeCalendar(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", scriptName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", scriptName, "Navigation", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== CREATE NEW NIGHTLY RATE PLAN ======================");
			app_logs.info("=================== CREATE NEW NIGHTLY RATE PLAN ======================");

			ratesGrid.clickRateGridAddRatePlan(driver);
			ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan type", "Nightly rate plan", testSteps);

			testSteps.add(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");
			app_logs.info(
					"=================== ENTER RATE PLAN NAME, FOLIO DISPLAY NAME AND DESCRIPTION ======================");

			nightlyRate.enterRatePlanName(driver, ratePlanName, testSteps);
			nightlyRate.enterRateFolioDisplayName(driver, folioDisplayName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, description, testSteps);

			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Rate plan name", ratePlanName, testSteps);

			testSteps.add("=================== SELECT DISTRIBUTED CHANNELS ======================");
			app_logs.info("=================== SELECT DISTRIBUTED CHANNELS ======================");

			nightlyRate.selectChannels(driver, channels, true, testSteps);
			String summaryChannels = nightlyRate.generateTitleSummaryValueForChannels(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Channel", summaryChannels, testSteps);

			testSteps.add("=================== SELECT ROOM CLASSES ======================");
			app_logs.info("=================== SELECT ROOM CLASSES ======================");
			nightlyRate.selectRoomClasses(driver, roomClassName, true, testSteps);
			String summaryRoomClasses = nightlyRate.generateTitleSummaryValueForRoomClass(driver);
			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.verifyTitleSummaryValue(driver, "Room class", summaryRoomClasses, testSteps);

			nightlyRate.clickNextButton(driver, testSteps);

			nightlyRate.clickNextButton(driver, testSteps);
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed", scriptName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed", scriptName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			testSteps.add("=================== CREATE SEASON ======================");
			app_logs.info("=================== CREATE SEASON ======================");

			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.selectSeasonDates(driver, testSteps,
					Utility.parseDate(calendarTodayDate, dateFormat, seasonDateFormat),
					Utility.addDate(1, dateFormat, calendarTodayDate, seasonDateFormat, timeZone));
			nightlyRate.enterSeasonName(driver, testSteps, seasonName);
			nightlyRate.selectSeasonDays(driver, testSteps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
					isSaturDay, isSunDay);
			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.selectSeasonColor(driver, testSteps);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, testSteps,
					isAdditionalChargesForChildrenAdults);
			nightlyRate.enterRate(driver, testSteps, RatePerNight, isAdditionalChargesForChildrenAdults, MaxAdults,
					MaxPersons, AdditionalAdultsPerNight, AdditionalChildPerNight);
			nightlyRate.addExtraRoomClassInSeason(driver, testSteps, isAddRoomClassInSeason, ExtraRoomClassesInSeason,
					isAdditionalChargesForChildrenAdults, RatePerNight, ExtraRoomClassRatePerNight,
					ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
					ExtraRoomClassAdditionalChildPerNight);
			nightlyRate.clickSaveSason(driver, testSteps);
			nightlyRate.clickCompleteChanges(driver, testSteps);
			nightlyRate.clickSaveAsActive(driver, testSteps);

			testSteps.add("=================== RATE PLAN CREATED ======================");
			app_logs.info("=================== RATE PLAN CREATED ======================");
			navigation.Rates_Grid(driver);
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed", scriptName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed", scriptName, "RatesV2", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// verify
		try {
			app_logs.info("========== VERIFY INACTIVE ROOM CLASS NOT EXIST IN RATES IN AVAILABILITY TAB ==========");
			testSteps.add("========== VERIFY INACTIVE ROOM CLASS NOT EXIST IN RATES IN AVAILABILITY TAB ==========");
			boolean foundInactiveRoomClass = false;
			List<String> ratesGridRoomClasses = ratesGrid.getRoomClasses(driver);
			for (int i = 0; i < ratesGridRoomClasses.size(); i++) {
				if(ratesGridRoomClasses.get(i).equals(inActiveRoomClassName)){
					foundInactiveRoomClass = true;
					break;
				}
			}
			Assert.assertFalse(foundInactiveRoomClass, "Failed : InActive Room Class found");
			app_logs.info("Successfully verified Inactive Room Class not found in availability tab");
			testSteps.add("Successfully verified Inactive Room Class not found in availability tab");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}


		// verify
		try {
			app_logs.info(
					"=========== VERIFY TOGGLING TO MAKE A ROOM AS BLACKOUT THEN AVAILABLE(WHICH WAS BLACKOUT EARLIER) AND VIEW IT IN AVAILABILITY TAB FOR ALL THE CHANNELS(SOURCES) ==========");
			testSteps.add(
					"=========== VERIFY TOGGLING TO MAKE A ROOM AS BLACKOUT THEN AVAILABLE(WHICH WAS BLACKOUT EARLIER) AND VIEW IT IN AVAILABILITY TAB FOR ALL THE CHANNELS(SOURCES) ===========");

			String datesList[] = multipleSpecificdates.split("\\|");
			for (int dateIndex = 0; dateIndex < datesList.length; dateIndex++) {
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				testSteps.add("========= SELECT SPECIFIC DATE : '" + datesList[dateIndex] + "' ===========");
				app_logs.info("========= SELECT SPECIFIC DATE : '" + datesList[dateIndex] + "' ===========");
				ratesGrid.clickForRateGridCalender(driver, testSteps);
				ratesGrid.selectDateFromDatePicker(driver, datesList[dateIndex], dateFormat, testSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, datesList[dateIndex], dateFormat, timeZone,
						getTestSteps);
				testSteps.addAll(getTestSteps);

				for (int i = 0; i < getRoomClasses.size(); i++) {
					if (i == Integer.parseInt(maximumRoomClassesToExpand)) {
						break;
					}
					testSteps.add("========= Room Class : '" + getRoomClasses.get(i) + "' ===========");
					app_logs.info("========= Room Class : '" + getRoomClasses.get(i) + "' ===========");
					try {
						ratesGrid.expandRoomClass(driver, i);
						testSteps.add("Expand Room Class");
						app_logs.info("Expand Room Class");
					} catch (Exception e) {
						testSteps.add("Failed to load availability grid. Please refresh. ");
						app_logs.info("Failed to load availability grid. Please refresh. ");
						driver.navigate().refresh();
						testSteps.add("Page Refreshed ");
						app_logs.info("Page Refreshed ");

						getTestSteps.clear();
						getTestSteps = ratesGrid.clickOnAvailability(driver);
						testSteps.addAll(getTestSteps);
						getTestSteps.clear();
						getTestSteps = ratesGrid.clickCalendar(driver);
						testSteps.addAll(getTestSteps);
						ratesGrid.clickForRateGridCalender(driver, testSteps);
						ratesGrid.selectDateFromDatePicker(driver, datesList[dateIndex], dateFormat, testSteps);

						getTestSteps.clear();
						getTestSteps = ratesGrid.verifyHeadingDates(driver, datesList[dateIndex], dateFormat, timeZone,
								getTestSteps);
						testSteps.addAll(getTestSteps);
						ratesGrid.expandRoomClass(driver, i);
						testSteps.add("Expand Room Class");
						app_logs.info("Expand Room Class");
					}

					for (int j = 0; j < activeChannelsList.size(); j++) {
						int roomIndex = ratesGrid.getRoomClassIndex(driver, getRoomClasses.get(i));
						getTestSteps.clear();
						getTestSteps = ratesGrid.verifyChannels(driver, getRoomClasses.get(roomIndex),
								activeChannelsList, getTestSteps);
						testSteps.addAll(getTestSteps);
						app_logs.info("=========== MAKE CHANNEL(" + activeChannelsList.get(j)
								+ ") BLACKOUT AND VERIFY ==========");
						testSteps.add("=========== FOR A SPECIFIC DATE MAKE CHANNEL(" + activeChannelsList.get(j)
								+ ") BLACKOUT AND VERIFY ===========");

						ratesGrid.activeOrBlackoutChannel(driver, datesList[dateIndex], dateFormat,
								getRoomClasses.get(roomIndex), activeChannelsList.get(j), blockedStatus);
						app_logs.info("Blackout room for Date '" + datesList[dateIndex] + "' and Source '"
								+ activeChannelsList.get(j) + "'");
						testSteps.add("Blackout room for Date '" + datesList[dateIndex] + "' and Source '"
								+ activeChannelsList.get(j) + "'");
						assertEquals(
								ratesGrid.getRoomStatus(driver, datesList[dateIndex], dateFormat,
										activeChannelsList.get(j), getRoomClasses.get(roomIndex)),
								blockedStatus, "Failed : Room Status is not Blacked Out");
						app_logs.info(
								"Successfully verified blackout room and 'B' is displaying under room percentage value");
						testSteps.add(
								"Successfully verified blackout room and 'B' is displaying under room percentage value");
						app_logs.info("=========== MAKE BLACKOUT CHANNEL(" + activeChannelsList.get(j)
								+ ") AVAILABLE AND VERIFY ==========");
						testSteps.add("=========== MAKE BLACKOUT CHANNEL(" + activeChannelsList.get(j)
								+ ") AVAILABLE AND VERIFY ==========");
						ratesGrid.activeOrBlackoutChannel(driver, datesList[dateIndex], dateFormat,
								getRoomClasses.get(roomIndex), activeChannelsList.get(j), greenStatus);
						app_logs.info("Make room Available  for Date '" + datesList[dateIndex] + "' and Source '"
								+ activeChannelsList.get(j) + "'");
						testSteps.add("Make room Available  for Date '" + datesList[dateIndex] + "' and Source '"
								+ activeChannelsList.get(j) + "'");
						assertEquals(
								ratesGrid.getRoomStatus(driver, datesList[dateIndex], dateFormat,
										activeChannelsList.get(j), getRoomClasses.get(roomIndex)),
								greenStatus, "Failed : Room Status is not Available");
						app_logs.info("Successfully verified Green Dot Appeared showing room as available");
						testSteps.add("Successfully verified Green Dot Appeared showing room as available");
					}

					ratesGrid.expandRoomClass(driver, i);
					testSteps.add("Reduce Room Class");
					app_logs.info("Reduce Room Class");
				}
			}

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// verify
		try {
			app_logs.info("========== "
					+ "Total Occupancy (total occupied rooms/total physical rooms (for all room classes) for that date) and Pace vs Last Year validation(Not Applicable) of data displayed"
							.toUpperCase()
					+ " ==========");
			testSteps.add("========== "
					+ "Total Occupancy (total occupied rooms/total physical rooms (for all room classes) for that date) and Pace vs Last Year validation(Not Applicable) of data displayed"
							.toUpperCase()
					+ " ==========");
			testSteps.add("========= SELECT SPECIFIC DATE : '" + singleDate + "' ===========");
			app_logs.info("========= SELECT SPECIFIC DATE : '" + singleDate + "' ===========");
			ratesGrid.clickForRateGridCalender(driver, testSteps);
			ratesGrid.selectDateFromDatePicker(driver, singleDate, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, singleDate, dateFormat, timeZone, getTestSteps);
			int dateIndex = ratesGrid.getHeadingDateIndex(driver, singleDate, dateFormat);
			int roomReserved = 0;
			int roomTotal = 0;
			List<String> ratesGridRoomClasses = ratesGrid.getRoomClasses(driver);
			for (int i = 0; i < ratesGridRoomClasses.size(); i++) {
				app_logs.info("Room Number : " + i + " , " + ratesGridRoomClasses.get(i));
				ratesGrid.expandRoomClass(driver, i);
				// testSteps.add("Expand Room Class");
				app_logs.info("Expand Room Class");

				roomTotalValue = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Total");
				roomReservedValue = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				roomReserved = roomReserved + Integer.parseInt(roomReservedValue);
				roomTotal = roomTotal + Integer.parseInt(roomTotalValue);

				ratesGrid.expandRoomClass(driver, i);
				// testSteps.add("Reduce Room Class");
				app_logs.info("Reduce Room Class");
			}
			testSteps.add("Total Calculated ROOMS : " + roomTotal);
			app_logs.info("Total Calculated ROOMS : " + roomTotal);
			testSteps.add("Total Reserved ROOMS : " + roomReserved);
			app_logs.info("Total Reserved ROOMS : " + roomReserved);
			float calculatedOccupancy = roomReserved / roomTotal;

			testSteps.add("Calculated Total Occupancy : " + calculatedOccupancy);
			app_logs.info("Calculated Total Occupancy : " + calculatedOccupancy);
			occupancyPercentageValue = ratesGrid.getOccupancyORPaceDataValue(driver, dateIndex, "OccupancyBet");
			paceVsLastYearValue = ratesGrid.getOccupancyORPaceDataValue(driver, dateIndex, "pace");
			testSteps.add("Actual Total Occupancy : " + occupancyPercentageValue);
			app_logs.info("Actual Total Occupancy : " + occupancyPercentageValue);
			testSteps.add("Pace vs Last Year : " + paceVsLastYearValue);
			app_logs.info("Pace vs Last Year : " + paceVsLastYearValue);
			assertEquals(occupancyPercentageValue, Integer.toString((roomReserved / roomTotal) * 100) + "%",
					"Failed:Total Occupancy value not matched");
			testSteps.add("Successfully verified Total Occupancy for a specific date");
			app_logs.info("Successfully verified Total Occupancy for a specific date");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// verify
		try {
			app_logs.info("========== VERIFY AVAILABLE ROOMS IN RATES TAB ==========");
			testSteps.add("========== VERIFY AVAILABLE ROOMS IN RATES TAB ==========");

			navigation.Rates_Grid(driver);

			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
			testSteps.addAll(getTestSteps);

			int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
			try{
				ratesGrid.clickRatePlanArrow(driver, testSteps);
			}catch(Exception f){

				testSteps.add("Rate Plan arrow not clicked ");
				app_logs.info("Rate Plan arrow not clicked");
				driver.navigate().refresh();
				testSteps.add("Refresh Page");
				app_logs.info("Refresh Page");
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
				testSteps.addAll(getTestSteps);

				dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
			}
			ratesGrid.searchRateAndSelectRate(driver, testSteps, ratePlanName);
			ratesGrid.clickExpandIconOfRatePlan(driver, testSteps);
			beforeAvailableRoomsRatesGrid = ratesGrid.getRoomClassAvailableValueInRatesGridTab(driver, dateIndex,
					roomClassName);
			testSteps.add("Rates Grid Before Available Rooms : " + beforeAvailableRoomsRatesGrid);
			app_logs.info("Rates Grid Before Available Rooms : " + beforeAvailableRoomsRatesGrid);

			app_logs.info("========== VERIFY IN AVAILABILITY TAB ==========");
			testSteps.add("========== VERIFY IN AVAILABILITY TAB ==========");
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

			app_logs.info("========= GET OOO COUNT OF ROOM CLASS '" + roomClassName
					+ "' BEFORE MAKING ROOM  OUT OF ORDER ========");
			testSteps.add("========= GET OOO COUNT OF ROOM CLASS '" + roomClassName
					+ "' BEFORE MAKING ROOM  OUT OF ORDER ========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);
			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
			testSteps.addAll(getTestSteps);

			dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
			int roomIndex = ratesGrid.getRoomClassIndex(driver, roomClassName);
			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Expand Room Class");
			app_logs.info("Expand Room Class");
			beforeOOOCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "OOO");
			testSteps.add("Before Out Of Order Count : " + beforeOOOCount);
			app_logs.info("Before Out Of Order Count : " + beforeOOOCount);
			beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
			testSteps.add("Before Available Rooms : " + beforeAvailableRooms);
			app_logs.info("Before Available Rooms : " + beforeAvailableRooms);

			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Reduce Room Class");
			app_logs.info("Reduce Room Class");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("========== GET DATA FROM DAILY FLASH REPORT ==========");
			testSteps.add("========== GET DATA FROM DAILY FLASH REPORT ==========");

			navigation.Inventory_Backward_1(driver);
			navigation.Reports_Backward(driver);
			testSteps.add("Navigate to Reports");
			app_logs.info("Navigate to Reports");

			reports.clickDailyFlash(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = reports.dailyFlashReport(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			reports.selectAnyDateFromCalender(driver, testSteps,
					Utility.parseDate(calendarTodayDate, dateFormat, "dd/MM/yyyy"), "dd/MM/yyyy");
			
			try {
				getTestSteps.clear();
				getTestSteps = reports.downloadDailyflashReport(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception f) {
				driver.navigate().refresh();
				reports.selectAnyDateFromCalender(driver, testSteps,
						Utility.parseDate(calendarTodayDate, dateFormat, "dd/MM/yyyy"),dateFormat);
				getTestSteps.clear();
				getTestSteps = reports.downloadDailyflashReport(driver);
				testSteps.addAll(getTestSteps);
			}
			fileName = Utility.download_status(driver);
			lines = Utility.checkPDFArray(fileName);
			System.out.println("line size : " + lines.length);
			int i = 0;
			for (String line : lines) {
				Utility.app_logs.info("line : " + i + " " + line);
				i++;
			}
			String rawData = reports.getLineHavingData(driver, lines, labelTotalRooms);
			String data[] = rawData.split(labelTotalRooms);
			for (int j = 0; j < data.length; j++) {
				app_logs.info("index : " + j + " , Data : " + data[j]);
			}
			beforeTotalRooms = rawData.split(labelTotalRooms)[1].trim();
			testSteps.add("Before Out Of Order Total Rooms : " + beforeTotalRooms);
			app_logs.info("Before Out Of Order Total Rooms : " + beforeTotalRooms);

			rawData = reports.getLineHavingData(driver, lines, labelRoomsSold);
			String rsdata[] = rawData.split(labelRoomsSold);
			for (int j = 0; j < rsdata.length; j++) {
				app_logs.info("index : " + j + " , Data : " + rsdata[j]);
			}
			beforeRoomsSold = rawData.split(labelRoomsSold)[1].trim();
			testSteps.add("Before Out Of Order Rooms Sold : " + beforeRoomsSold);
			app_logs.info("Before Out Of Order Rooms Sold : " + beforeRoomsSold);

			rawData = reports.getLineHavingData(driver, lines, labelRoomsOutOfInventory);
			String ooidata[] = rawData.split(labelRoomsOutOfInventory);
			for (int j = 0; j < ooidata.length; j++) {
				app_logs.info("index : " + j + " , Data : " + ooidata[j]);
			}
			beforeRoomsOutOfInventory = rawData.split(labelRoomsOutOfInventory)[1].trim();
			testSteps.add("Before Out Of Order Out Of Inventory : " + beforeRoomsOutOfInventory);
			app_logs.info("Before Out Of Order Out Of Inventory : " + beforeRoomsOutOfInventory);

			rawData = reports.getLineHavingData(driver, lines, labelRoomsAvailable);
			String avadata[] = rawData.split(labelRoomsAvailable);
			for (int j = 0; j < avadata.length; j++) {
				app_logs.info("index : " + j + " , Data : " + avadata[j]);
			}
			beforeRoomsAvailable = rawData.split(labelRoomsAvailable)[1].trim();
			testSteps.add("Before Out Of Order Rooms Available : " + beforeRoomsAvailable);
			app_logs.info("Before Out Of Order Rooms Available : " + beforeRoomsAvailable);

			rawData = reports.getLineHavingData(driver, lines, labelOccupancy);
			String occdata[] = rawData.split(" ");
			for (int j = 0; j < occdata.length; j++) {
				app_logs.info("index : " + j + " , Data : " + occdata[j]);
			}
			beforeOccupancy = rawData.split(" ")[2].trim();
			testSteps.add("Before Out Of Order Occupancy : " + beforeOccupancy);
			app_logs.info("Before Out Of Order Occupancy : " + beforeOccupancy);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("========== "
					+ "Verify Out of order counts from different pages in Inncenter(Daily flash Report (Rooms Available Occupancy, Out of Inventory count), Tape chart, Reservation find room by room class) and verify in Availability tab. "
							.toUpperCase()
					+ " ==========");
			testSteps.add("========== "
					+ "Verify Out of order counts from different pages in Inncenter(Daily flash Report (Rooms Available Occupancy, Out of Inventory count), Tape chart, Reservation find room by room class) and verify in Availability tab. "
							.toUpperCase()
					+ " ==========");
			app_logs.info("========== CREATE ROOM MAINTENANCE ITEM (OUT OF ORDER) ==========");
			testSteps.add("========== CREATE ROOM MAINTENANCE ITEM (OUT OF ORDER)  ==========");

			navigation.Guestservices(driver);
			testSteps.add("Navigated to Guestservices");
			navigation.RoomMaintenance(driver);
			testSteps.add("Navigated to RoomMaintenance");

			roomMaintenance.createOutOfOrderRoomItem(driver, calendarTodayDate,
					Utility.addDate(1, dateFormat, calendarTodayDate, dateFormat, timeZone), dateFormat, subject,
					outOfOrderRoomNumber, roomClassName, testSteps, subject);
			roomMaintenance.SearchRooms(driver, reason, outOfOrderRoomNumber);
			testSteps.add("Successfully Search RoomMain_Item");
			app_logs.info("Successfully Search RoomMain_Item");

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// verify
		try {
			app_logs.info("========== VERIFY OUT OF ORDER ROOM IS NOT SEEN IN TAPE CHART ==========");
			testSteps.add("========== VERIFY OUT OF ORDER ROOM IS NOT SEEN IN TAPE CHART ==========");
			navigation.reservation(driver);
			app_logs.info("Navigate Reservation");
			testSteps.add("Navigate Reservation");
			navigation.TapeChart(driver);
			app_logs.info("Navigate Tape Chart");
			testSteps.add("Navigate Tape Chart");
			app_logs.info("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
			testSteps.add("==========ENTER CHECK IN DETAILS AND SEARCH ROOMS==========");
			getTestSteps.clear();
			getTestSteps = tapechart.tapeChartSearch(driver,
					Utility.parseDate(calendarTodayDate, dateFormat, "MM/dd/yyyy"),
					Utility.addDate(1, dateFormat, calendarTodayDate, "MM/dd/yyyy", timeZone), adults, children,
					ratePlanName);
			testSteps.addAll(getTestSteps);
			// TO DO
			tapechart.Verify_RoomClass_AfterRoomMainModification(driver, outOfOrderRoomNumber, roomClassAbb, true);
			testSteps.add("Successfully Verified Out of Order Room in TapeChart");
			app_logs.info("Successfully Verified Out of Order Room in TapeChart");
			app_logs.info("========== VERIFY OUT OF ORDER ROOM IN RESERVATION ==========");
			testSteps.add("========== VERIFY OUT OF ORDER ROOM IN RESERVATION ==========");
			navigation.reservation(driver);
			app_logs.info("Navigate Reservation");
			testSteps.add("Navigate Reservation");

			cpReservation.click_NewReservation(driver, testSteps);
			String CheckInDate = Utility.parseDate(calendarTodayDate, dateFormat, "dd/MM/yyyy");
			String CheckoutDate = Utility.addDate(1, dateFormat, calendarTodayDate, "dd/MM/yyyy", timeZone);

			app_logs.info("CheckOut Date : " + CheckoutDate);
			cpReservation.select_CheckInDate(driver, testSteps, CheckInDate);
			cpReservation.select_CheckoutDate(driver, testSteps, CheckoutDate);
			cpReservation.enter_Adults(driver, testSteps, adults);
			cpReservation.enter_Children(driver, testSteps, children);
			cpReservation.select_Rateplan(driver, testSteps, ratePlanName, "");
			cpReservation.clickOnFindRooms(driver, testSteps);

			cpReservation.verifyRoomNumberAvailable(driver, testSteps, roomClassName, outOfOrderRoomNumber, false);
			testSteps.add(
					"Successfully Verified Out of Order Room is not available for reservation in Reservation Find Rooms");
			app_logs.info(
					"Successfully Verified Out of Order Room is not available for reservation in Reservation Find Rooms");
			cpReservation.closeFirstOpenedReservationUnSavedData(driver, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// verify
		try {

			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			app_logs.info("========== VERIFY AVAILABLE ROOMS IN RATES TAB ==========");
			testSteps.add("========== VERIFY AVAILABLE ROOMS IN RATES TAB ==========");
			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
			testSteps.addAll(getTestSteps);

			int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
			try{
				ratesGrid.clickRatePlanArrow(driver, testSteps);
			}catch(Exception f){

				testSteps.add("Rate Plan arrow not clicked ");
				app_logs.info("Rate Plan arrow not clicked");
				driver.navigate().refresh();
				testSteps.add("Refresh Page");
				app_logs.info("Refresh Page");
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
				testSteps.addAll(getTestSteps);

				dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
				ratesGrid.clickRatePlanArrow(driver, testSteps);
			}
			ratesGrid.searchRateAndSelectRate(driver, testSteps, ratePlanName);
			ratesGrid.clickExpandIconOfRatePlan(driver, testSteps);
			afterAvailableRoomsRatesGrid = ratesGrid.getRoomClassAvailableValueInRatesGridTab(driver, dateIndex,
					roomClassName);
			testSteps.add("Rates Grid After Available Rooms : " + afterAvailableRoomsRatesGrid);
			app_logs.info("Rates Grid After Available Rooms : " + afterAvailableRoomsRatesGrid);
			assertEquals(afterAvailableRoomsRatesGrid,
					Integer.toString(Integer.parseInt(beforeAvailableRoomsRatesGrid) - 1),
					"Failed : Available Rooms is not decreased after making room out of order in Rates Grid tab");
			app_logs.info("========== VERIFY OUT OF ORDER ROOM IN AVAILABILITY TAB ==========");
			testSteps.add("========== VERIFY OUT OF ORDER ROOM IN AVAILABILITY TAB ==========");
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

			app_logs.info("========= GET OOO COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER MAKING ROOM  OUT OF ORDER ========");
			testSteps.add("========= GET OOO COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER MAKING ROOM  OUT OF ORDER ========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
			testSteps.addAll(getTestSteps);

			dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
			int roomIndex;
			try {
				roomIndex = ratesGrid.getRoomClassIndex(driver, roomClassName);
			} catch (Exception f) {
				testSteps.add("Failed to load availability grid. Please refresh. ");
				app_logs.info("Failed to load availability grid. Please refresh. ");
				driver.navigate().refresh();
				testSteps.add("Page Refreshed ");
				app_logs.info("Page Refreshed ");
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickOnAvailability(driver);
				testSteps.addAll(getTestSteps);
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
						getTestSteps);
				testSteps.addAll(getTestSteps);

				dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
				roomIndex = ratesGrid.getRoomClassIndex(driver, roomClassName);
			}
			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Expand Room Class");
			app_logs.info("Expand Room Class");
			afterOOOCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "OOO");
			testSteps.add("After Out Of Order Count : " + afterOOOCount);
			app_logs.info("After Out Of Order Count : " + afterOOOCount);
			assertEquals(afterOOOCount, Integer.toString(Integer.parseInt(beforeOOOCount) + 1),
					"Failed : OOO count is not increased after making room out of order");

			afterAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
			testSteps.add("After Available Rooms : " + afterAvailableRooms);
			app_logs.info("After Available Rooms : " + afterAvailableRooms);
			assertEquals(afterAvailableRooms, Integer.toString(Integer.parseInt(beforeAvailableRooms) - 1),
					"Failed : Available Rooms is not decreased after making room out of order");
			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Reduce Room Class");
			app_logs.info("Reduce Room Class");
		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("========== GET DATA FROM DAILY FLASH REPORT ==========");
			testSteps.add("========== GET DATA FROM DAILY FLASH REPORT ==========");

			navigation.Inventory_Backward_1(driver);
			navigation.Reports_Backward(driver);
			testSteps.add("Navigate to Reports");
			app_logs.info("Navigate to Reports");

			reports.clickDailyFlash(driver, testSteps);

			getTestSteps.clear();
			getTestSteps = reports.dailyFlashReport(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			reports.selectAnyDateFromCalender(driver, testSteps,
					Utility.parseDate(calendarTodayDate, dateFormat, "dd/MM/yyyy"), "dd/MM/yyyy");
			try {
				getTestSteps.clear();
				getTestSteps = reports.downloadDailyflashReport(driver);
				testSteps.addAll(getTestSteps);
			} catch (Exception f) {
				driver.navigate().refresh();
				reports.selectAnyDateFromCalender(driver, testSteps,
						Utility.parseDate(calendarTodayDate, dateFormat, "dd/MM/yyyy"), dateFormat);
				getTestSteps.clear();
				getTestSteps = reports.downloadDailyflashReport(driver);
				testSteps.addAll(getTestSteps);
			}
			fileName = Utility.download_status(driver);
			lines = Utility.checkPDFArray(fileName);
			System.out.println("line size : " + lines.length);
			int i = 0;
			for (String line : lines) {
				Utility.app_logs.info("line : " + i + " " + line);
				i++;
			}
			String rawData = reports.getLineHavingData(driver, lines, labelTotalRooms);
			String data[] = rawData.split(labelTotalRooms);
			for (int j = 0; j < data.length; j++) {
				app_logs.info("index : " + j + " , Data : " + data[j]);
			}
			afterTotalRooms = rawData.split(labelTotalRooms)[1].trim();
			testSteps.add("After Out Of Order Total Rooms : " + afterTotalRooms);
			app_logs.info("After Out Of Order Total Rooms : " + afterTotalRooms);

			rawData = reports.getLineHavingData(driver, lines, labelRoomsSold);
			String rsdata[] = rawData.split(labelRoomsSold);
			for (int j = 0; j < rsdata.length; j++) {
				app_logs.info("index : " + j + " , Data : " + rsdata[j]);
			}
			afterRoomsSold = rawData.split(labelRoomsSold)[1].trim();
			testSteps.add("After Out Of Order Rooms Sold : " + afterRoomsSold);
			app_logs.info("After Out Of Order Rooms Sold : " + afterRoomsSold);

			rawData = reports.getLineHavingData(driver, lines, labelRoomsOutOfInventory);
			String ooidata[] = rawData.split(labelRoomsOutOfInventory);
			for (int j = 0; j < ooidata.length; j++) {
				app_logs.info("index : " + j + " , Data : " + ooidata[j]);
			}
			afterRoomsOutOfInventory = rawData.split(labelRoomsOutOfInventory)[1].trim();
			testSteps.add("After Out Of Order Out Of Inventory : " + afterRoomsOutOfInventory);
			app_logs.info("After Out Of Order Out Of Inventory : " + afterRoomsOutOfInventory);
			
				assertEquals(afterRoomsOutOfInventory, Integer.toString(Integer.parseInt(beforeRoomsOutOfInventory) + 1),
						"Failed : Out of Inventory count is not increased after making room out of order");
			
			rawData = reports.getLineHavingData(driver, lines, labelRoomsAvailable);
			String avadata[] = rawData.split(labelRoomsAvailable);
			for (int j = 0; j < avadata.length; j++) {
				app_logs.info("index : " + j + " , Data : " + avadata[j]);
			}
			afterRoomsAvailable = rawData.split(labelRoomsAvailable)[1].trim();
			testSteps.add("After Out Of Order Rooms Available : " + afterRoomsAvailable);
			app_logs.info("After Out Of Order Rooms Available : " + afterRoomsAvailable);
			app_logs.info("Expected Out Of Order Rooms Available : " + Integer.toString(Integer.parseInt(beforeRoomsAvailable) - 1));

			app_logs.info("Expected Out Of Order Rooms Available : " + (Integer.parseInt(afterRoomsAvailable)<(Integer.parseInt(beforeRoomsAvailable) - 1)));
			
				assertEquals(afterRoomsAvailable, Integer.toString(Integer.parseInt(beforeRoomsAvailable) - 1),
			"Failed : Rooms Available count is not decreased after making room out of order");
			

			rawData = reports.getLineHavingData(driver, lines, labelOccupancy);
			String occdata[] = rawData.split(" ");
			for (int j = 0; j < occdata.length; j++) {
				app_logs.info("index : " + j + " , Data : " + occdata[j]);
			}
			afterOccupancy = rawData.split(" ")[2].trim();
			testSteps.add("After Out Of Order Occupancy : " + afterOccupancy);
			app_logs.info("After Out Of Order Occupancy : " + afterOccupancy);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to verify", scriptName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// Clicking on Groups
		try {
			app_logs.info("========== VERIFY OUT OF ORDER ROOM IS NOT SEEN IN GROUP BLOCK CREATION ==========");
			testSteps.add("========== VERIFY OUT OF ORDER ROOM IS NOT SEEN IN GROUP BLOCK CREATION ==========");
			navigation.navigateToReservations(driver);
			testSteps.add("Navigate Reservations");
			app_logs.info("Navigate Reservations");

			navigation.secondaryGroupsManu(driver);
			testSteps.add("Navigate Groups");
			app_logs.info(" Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", scriptName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", scriptName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Click New Account and Enter Account Name
		try {
			app_logs.info("==========CREATE NEW GROUP ACCOUNT==========");
			testSteps.add("==========CREATE NEW GROUP ACCOUNT==========");
			getTestSteps.clear();
			getTestSteps = group.enterrGroupName(driver, accountName);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to click on new account button", scriptName, "EnterAccountName",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to click new account and enter account name", scriptName,
						"EnterAccountName", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Number
		try {
			getTestSteps.clear();
			getTestSteps = group.enterAccountNo(driver, accountNumber);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", scriptName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", scriptName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Attributes
		try {
			getTestSteps.clear();
			getTestSteps = group.selectAccountAttributes(driver, marketSegment, groupReferral);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", scriptName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", scriptName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Mailing Address
		try {
			getTestSteps.clear();
			getTestSteps = group.enterMailingAddress(driver, groupFirstName, groupLastName, groupPhone, groupAddress,
					groupCity, groupState, groupCountry, groupPostalcode);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", scriptName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", scriptName,
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
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", scriptName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", scriptName, "CheckMailingInfoCheckBox",
						driver);
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
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Save  Account", scriptName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Save Account", scriptName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

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
					Utility.addDate(0, dateFormat, calendarTodayDate, "MMM dd, yyyy", timeZone),
					Utility.addDate(1, dateFormat, calendarTodayDate, "MMM dd, yyyy", timeZone));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectRatePlan(driver, ratePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectAdults(driver, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectChilds(driver, children);
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
			app_logs.info("==========VERIFY OUT OF ORDER ROOM==========");
			testSteps.add("==========VERIFY OUT OF ORDER ROOM==========");
			advanceGroup.verifyRoomNumberExistInPickupFromRoomingList(driver, roomClassName, outOfOrderRoomNumber,
					false);
			app_logs.info("Successfully verified Out Of Order Room # : " + outOfOrderRoomNumber
					+ " is not available for reservation");
			testSteps.add("Successfully verified Out Of Order Room # : " + outOfOrderRoomNumber
					+ " is not available for reservation");
			navigation.reservation(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", scriptName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", scriptName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		
		// Navigate room maintenance

		try {
			navigation.Guestservices(driver);
			testSteps.add("Navigate to Guest Service");
			app_logs.info("Navigate to Guest Service");
			navigation.RoomMaintenance(driver);
			testSteps.add("Navigate to Room Maintenance");
			app_logs.info("Navigate to Room Maintenance");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Navigate Guest Service", scriptName, "NavigateGuestService", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Navigate Guest Service", scriptName, "NavigateGuestService", driver);
			} else {
				Assert.assertTrue(false);
			}

		}

		// Delete out of order room
		try {
			roomMaintenance.SearchRooms(driver, reason, outOfOrderRoomNumber);
			testSteps.add("Successfully Search RoomMain_Item");
			app_logs.info("Successfully Search RoomMain_Item");
			roomMaintenance.DeleteRoomOutOfOrder(driver, subject);
			testSteps.add("Delete room out of order successfully");
			app_logs.info("Delete room out of order successfully");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to delete room out of order", scriptName, "RoomMaintenance", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to delete room out of order", scriptName, "RoomMaintenance", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Generate Report
		try {
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", scriptName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(scriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(scriptName, test_description, test_category, testSteps);
				Utility.updateReport(e, "Failed to Generate Report", scriptName, "GenerateReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("RatesV2AvailabilityTabInncenter", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		//driver.quit();
	}
}
