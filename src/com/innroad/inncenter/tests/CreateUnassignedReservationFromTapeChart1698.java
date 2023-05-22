package com.innroad.inncenter.tests;


import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CreateUnassignedReservationFromTapeChart1698 extends TestCore {

	// Automation-1449
	private WebDriver driver = null;

	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	public static String testDescription = "";
	public static String testCategory = "";

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "TapeChart")
	public void createUnassignedReservationFromTapeChart1698(String ratePlanName, String baseAmount, String addtionalAdult,
			String additionalChild, String folioName, String associateSession, String ratePolicy,
			String rateDescription, String roomClassAbb, String roomClassName, String bedsCount, String maxAdults,
			String maxPersons, String roomQuantity, String startRoomNumber, String ratePlan, String rateType,
			String rateAttributes, String interval, String source, String adults, String child, String marketSegment,
			String referral, String firstName, String lastName, String groupPhn, String groupAddress,
			String groupcity, String groupcountry, String groupstate, String groupPostalcode, String blockName,
			String roomPerNight,String salutation, 
			String channels,String SeasonName, String seasonDateFormat, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay, String specificDate, String dateFormat,String calendarTodayDay,
			String isAdditionalChargesForChildrenAdults, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight,String reason, String	subject) throws InterruptedException, IOException {

		String testName = "CreateUnassignedReservationFromTapeChart1698";
		testDescription = "Create Unassigned Reservation -> Tape Chart<br>";

		testCategory = "TapeChart";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		Tapechart tapeChart = new Tapechart();
		CPReservationPage reservationPage = new CPReservationPage();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		RoomClass roomClass = new RoomClass();
		ReservationSearch reservationSearch = new ReservationSearch();
		
		String tempraryRoomClassName = roomClassName;
		String tempraryRateName = ratePlanName;
		String randomNumber = Utility.GenerateRandomNumber(99);
		folioName = ratePlanName;
		String reservationNumber = null;
		String AfterUnassignedReservations = "0";
		String BeforeUnassignedReservations = "0";
		String AfterUnassignedReservationsRC = "0";
		String BeforeUnassignedReservationsRC = "0";
		lastName = lastName + randomNumber;
		String ReservationName = firstName + " " + lastName;
		String paymentType = "Cash";
		SeasonName = SeasonName + randomNumber;
		String calendarTodayDate = "July/17/2020";
		roomClassName = roomClassName + randomNumber;
		roomClassAbb = roomClassAbb + randomNumber;
		ratePlanName = ratePlanName + randomNumber;
		folioName = ratePlanName;
		
		String beforeReservedCount = null;
		String afterReservedCount = null;
		String beforeAvailableRooms = null;
		String afterAvailableRooms = null;
		String beforeAvailableRoomsRatesGrid = null;
		String afterAvailableRoomsRatesGrid = null;

		String afterReduceAvailableRoomsRatesGrid = null;
		String afterExtendAvailableRoomsRatesGrid = null;
		String afterReduceReservedCount = null;
		String afterReduceAvailableRooms = null;
		String afterExtendAvailableRooms = null;
		String afterExtendReservedCount = null;
		int totalRoomsExtend = 2;

		
		String timeZone = "America/New_York";
		app_logs.info("Time Zone " + timeZone);
		app_logs.info("Curret Time " + Utility.getCurrentDate("MMM dd,yyy: H:m:s", timeZone));

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);

			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_CP(driver);
			testSteps.add("Entered appication URL : " + TestCore.envURL);
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Room Class
		try {

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.navigateRoomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to room class", testName, "NavigateRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to navigate to room class", testName, "NavigateRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("==========CREATE A NEW ROOM CLASS==========");
			testSteps.add("==========CREATE A NEW ROOM CLASS==========");

			navigation.clickOnNewRoomClass(driver);
			getTestSteps.clear();
			getTestSteps = roomClass.createRoomClass(driver, roomClassName, roomClassAbb, bedsCount, MaxAdults,
					maxPersons, roomQuantity, test, getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Sccessfully Created New RoomClass " + roomClassName + " Abb : " + roomClassAbb);
			app_logs.info("Sccessfully Created New RoomClass" + roomClassName + " Abb : " + roomClassAbb);

			roomClass.closeRoomClassTab(driver);
			testSteps.add("Close created room class tab");

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Create New Rate and Attach RoomClass

		try {
			navigation.Inventory(driver);
			testSteps.add("Navigate Inventory");
			app_logs.info("Navigate Inventory");
			navigation.Rates_Grid(driver);
			testSteps.add("Navigate Rates Grid");
			app_logs.info("Navigate Rates Grid");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);
			calendarTodayDate = ratesGrid.getCalendarDate(driver, calendarTodayDay, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.closeCalendar(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// Create New Rate and Attach RoomClass
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
			nightlyRate.enterRateFolioDisplayName(driver, folioName, testSteps);
			nightlyRate.enterRatePlanDescription(driver, rateDescription, testSteps);

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
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to associate room class in newly created rateplan", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to associate room class in newly created rateplan", testName, "CreateNewRate", driver);
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
					Utility.addDate(4, dateFormat, calendarTodayDate, seasonDateFormat, timeZone));
			nightlyRate.enterSeasonName(driver, testSteps, SeasonName);
			nightlyRate.selectSeasonDays(driver, testSteps, isMonDay, isTueDay, isWednesDay, isThursDay, isFriday,
					isSaturDay, isSunDay);
			nightlyRate.clickCreateSeason(driver, testSteps);
			nightlyRate.selectSeasonColor(driver, testSteps);
			nightlyRate.selectAdditionalChargesForChildrenAdults(driver, testSteps,
					isAdditionalChargesForChildrenAdults);
			nightlyRate.enterRate(driver, testSteps, baseAmount, isAdditionalChargesForChildrenAdults, MaxAdults,
					MaxPersons, AdditionalAdultsPerNight, AdditionalChildPerNight);
			nightlyRate.addExtraRoomClassInSeason(driver, testSteps, isAddRoomClassInSeason, ExtraRoomClassesInSeason,
					isAdditionalChargesForChildrenAdults, baseAmount, ExtraRoomClassRatePerNight,
					ExtraRoomClassMaxAdults, ExtraRoomClassMaxPersons, ExtraRoomClassAdditionalAdultsPerNight,
					ExtraRoomClassAdditionalChildPerNight);
		
			nightlyRate.clickSaveSason(driver, testSteps);
			nightlyRate.clickCompleteChanges(driver, testSteps);
			nightlyRate.clickSaveAsActive(driver, testSteps);

			testSteps.add("RATE PLAN CREATED");
			app_logs.info("RATE PLAN CREATED");
		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// navigate to Inventory->Rates Grid
		try {
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
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid",testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {

				Assert.assertTrue(false);
			}
		}


		// navigate to Inventory->Rates Grid
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

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify available rooms in rate grid", testName, "VerifyAvailabelRooms", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify available rooms in rate grid", testName, "VerifyAvailabelRooms", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		// verify
		try {
			app_logs.info("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' BEFORE CREATING RESERVATION ========");
			testSteps.add("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' BEFORE CREATING RESERVATION ========");

			getTestSteps.clear();
			getTestSteps = ratesGrid.clickCalendar(driver);
			testSteps.addAll(getTestSteps);

			ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

			getTestSteps.clear();
			getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone, getTestSteps);
			testSteps.addAll(getTestSteps);

			int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
			dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
			int roomIndex;
			try {
				roomIndex = ratesGrid.getRoomClassIndex(driver, roomClassName);
			} catch (Exception f) {
				testSteps.add("Availability room Classes are not loading ");
				app_logs.info("Availability room Classes are not loading ");
				driver.navigate().refresh();
				testSteps.add("Refresh Page");
				app_logs.info("Refresh Page");
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
			beforeReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
			testSteps.add("Before Reserved Count : " + beforeReservedCount);
			app_logs.info("Before Reserved Count : " + beforeReservedCount);
			beforeAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
			testSteps.add("Before Available Rooms : " + beforeAvailableRooms);
			app_logs.info("Before Available Rooms : " + beforeAvailableRooms);

			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Reduce Room Class");
			app_logs.info("Reduce Room Class");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get reserved and available room count", testName, "GetRoomCount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to get reserved and available room count", testName, "GetRoomCount", driver);
			} else {

				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========GET UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			testSteps.add("==========GET UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			
			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

			navigation.reservation(driver);
			testSteps.add("Navigate Reservation");
			app_logs.info("Navigate Reservation");


		// Observe parking lots(Unassigned Reservations) in TapeChart
			navigation.navTapeChart(driver, test);
			testSteps.add("Navigate TapeChart");
			app_logs.info("Navigate TapeChart");
			getTestSteps.clear();
			getTestSteps = tapeChart.tapeChartSearch(driver, Utility.getCurrentDate("MM/dd/yyyy"),
					Utility.GetNextDate(1, "MM/dd/yyyy"), adults, child, ratePlanName);
			testSteps.addAll(getTestSteps);
			tapeChart.ClickUnassigned(driver);
			app_logs.info("Click Unassigned button");
			testSteps.add("Click Unassigned button");
			BeforeUnassignedReservations = tapeChart.GetandVerifyUnassignedReservationNumber(driver, true);
			app_logs.info("Number of  Unassigned Reservation : " + BeforeUnassignedReservations);
			testSteps.add("Number of  Unassigned Reservations : " + BeforeUnassignedReservations);
			app_logs.info(
					"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
			testSteps.add(
					"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
			app_logs.info("==========GET UNASSIGNED RESERVATIONS OF ROOM CLASS '" + roomClassAbb + "'==========");
			testSteps.add("==========GET UNASSIGNED RESERVATIONS OF ROOM CLASS '" + roomClassAbb + "'==========");

			BeforeUnassignedReservationsRC = tapeChart.GetUnassignedRoomClassCount(driver, roomClassAbb);
			app_logs.info("Number of Unassigned Reservations of Room Class '" + roomClassAbb + "' are '" + BeforeUnassignedReservationsRC + "'");
			testSteps.add("Number of Unassigned Reservations of Room Class '" + roomClassAbb + "' are '" + BeforeUnassignedReservationsRC + "'");
		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Unassigned Reservation From Tape Chart
		try {
			app_logs.info("==========CREATE UNASSIGNED RESERVATION FROM TAPECHART==========");
			testSteps.add("==========CREATE UNASSIGNED RESERVATION FROM TAPECHART==========");
			tapeChart.ClickUnassignedRoomClass(driver, roomClassAbb, testSteps);
			String roomnumber = reservationPage.GETRoomNumber(driver, testSteps);
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			testSteps.add("Verified Room Number is Unassigned");
			reservationPage.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, testSteps, salutation, firstName, lastName,
					"no");
			reservationPage.enter_MarketSegmentDetails(driver, testSteps, "", marketSegment, referral);
			reservationPage.enter_PaymentDetails(driver, testSteps, paymentType, "", "", "");
			reservationPage.clickBookNow(driver, testSteps);
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
			Object ReservationStatus = reservationPage.get_ReservationStatus(driver, testSteps);
			Assert.assertEquals(ReservationStatus, "Reserved", "Failed: Reservation Status missmatched");
			reservationPage.clickCloseReservationSavePopup(driver, testSteps);
			roomnumber = reservationPage.get_RoomNumber(driver, testSteps,"No");
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			testSteps.add("Verified Room Number is Unassigned");
			String TripTotal = reservationPage.get_TripSummaryTripTotalChargesWithoutCurrency(driver, getTestSteps);
			Utility.app_logs.info("Trip Total '" + TripTotal + "'");
			
			
		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create unassigned reservation from Tape Chart", testName, "CreateUnassignedReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testCategory, testSteps);
				Utility.updateReport(e, "Failed to Create unassigned reservation from Tape Chart", testName, "CreateUnassignedReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Observe parking lots(Unassigned Reservations) in TapeChart
		try {
			app_logs.info("==========VERIFY UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			testSteps.add("==========VERIFY UNASSIGNED RESERVATIONS IN UNASSIGNED COLUMN(PARKING LOT)==========");
			navigation.navTapeChart(driver, test);
			testSteps.add("Navigate TapeChart");
			app_logs.info("Navigate TapeChart");
			tapeChart.ClickUnassigned(driver);
			app_logs.info("Click Unassigned button");
			testSteps.add("Click Unassigned button");
			AfterUnassignedReservations = tapeChart.GetandVerifyUnassignedReservationNumber(driver, true);
			app_logs.info("Number of Unassigned Reservations : " + AfterUnassignedReservations);
			testSteps.add("Number of Unassigned Reservations : " + AfterUnassignedReservations);
			app_logs.info(
					"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
			testSteps.add(
					"Successfully verified number of Unassigned Reservation from 'Unassigned Button', 'Unassigned List Header' and Unassigned List of Reservations");
			Assert.assertTrue(
					Integer.parseInt(AfterUnassignedReservations) > Integer.parseInt(BeforeUnassignedReservations),
					"Failed: Unassigned Count not increase");
			app_logs.info("Verified Number of Unassigned Reservations has been incremented");
			testSteps.add("Verified Number of Unassigned Reservations has been incremented");
			tapeChart.VerifyUnassignedReservationColor(driver, ReservationName, Utility.getCurrentDate("E, MMM dd"),
					Utility.GetNextDate(1, "E, MMM dd"), roomClassAbb,testSteps);
			app_logs.info(
					"Successfully verified Reservation '" + ReservationName + "' Exist in Unassigned Reservations");
			testSteps.add(
					"Successfully verified Reservation '" + ReservationName + "' Exist in Unassigned Reservations");
			tapeChart.VerifyUnassignedReservation(driver, ReservationName, Utility.getCurrentDate("E, MMM dd"),
					Utility.GetNextDate(1, "E, MMM dd"), roomClassAbb);
			app_logs.info("==========VERIFY UNASSIGNED RESERVATION DETAILS==========");
			testSteps.add("==========VERIFY UNASSIGNED RESERVATION DETAILS==========");
			testSteps.add("Reservation Name : " + ReservationName);
			testSteps.add("Arrival Date : " + Utility.getCurrentDate("E, MMM dd"));
			testSteps.add("Departure Date : " + Utility.GetNextDate(1, "E, MMM dd"));
			testSteps.add("Room Class Abbreviation : " + roomClassAbb);
			app_logs.info("==========VERIFY UNASSIGNED RESERVATIONS OF ROOM CLASS '" + roomClassAbb + "'==========");
			testSteps.add("==========VERIFY UNASSIGNED RESERVATIONS OF ROOM CLASS '" + roomClassAbb + "'==========");

			AfterUnassignedReservationsRC = tapeChart.GetUnassignedRoomClassCount(driver, roomClassAbb);
			app_logs.info("Number of Unassigned Reservations of Room Class '" + roomClassAbb + "' are '" + AfterUnassignedReservationsRC + "'");
			testSteps.add("Number of Unassigned Reservations of Room Class '" + roomClassAbb + "' are '" + AfterUnassignedReservationsRC + "'");
			Assert.assertTrue(
					Integer.parseInt(AfterUnassignedReservationsRC) > Integer.parseInt(BeforeUnassignedReservationsRC),
					"Failed: Unassigned Count not increase");
			app_logs.info("Verified Room Class '" + roomClassAbb + "' Unassigned Reservations has been incremented");
			testSteps.add("Verified Room Class '" + roomClassAbb + "' Unassigned Reservations has been incremented");
		} catch (Exception e) {

			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Observe parking lots(Unassigned Reservations) in TapeChart",
						testName, "TapeChart", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		//Search Reservation and verify Room Class
		try {
			
			app_logs.info("==========SEARCH RESERVATION==========");
			testSteps.add("==========SEARCH RESERVATION==========");
			navigation.navigateToReservations(driver);
			testSteps.add("Navigate Reservation ");
			app_logs.info("Navigate Reservation ");
			driver.navigate().refresh();
			reservationSearch.basicSearch_WithResNumber(driver, reservationNumber,false);
			testSteps.add("Reservation Successfully Searched : " + reservationNumber);
			app_logs.info("Reservation Successfully Searched : " + reservationNumber);
			app_logs.info("==========VERIFY ROOM==========");
			testSteps.add("==========VERIFY ROOM==========");
			reservationSearch.verifyReservationRoom(driver, reservationNumber,roomClassName, testSteps);
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close opened reservation ");
			app_logs.info("Close opened reservation ");
			
			
		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to search reservation and verify Room Class", testName, "ReservationSearch",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to search reservation and verify Room Class", testName, "ReservationSearch",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// verify
		try {
			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER CREATING RESERVATION ========");
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
				testSteps.add("Availability room Classes are not loading ");
				app_logs.info("Availability room Classes are not loading ");
				driver.navigate().refresh();
				testSteps.add("Refresh Page");
				app_logs.info("Refresh Page");
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
			afterReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
			testSteps.add("After Reserved Count : " + afterReservedCount);
			app_logs.info("After Reserved Count : " + afterReservedCount);
			assertEquals(afterReservedCount, Integer.toString(Integer.parseInt(beforeReservedCount) + 1),
					"Failed : Reserved count is not increased after creating reservation");

			afterAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
			testSteps.add("After Available Rooms : " + afterAvailableRooms);
			app_logs.info("After Available Rooms : " + afterAvailableRooms);
			assertEquals(afterAvailableRooms, Integer.toString(Integer.parseInt(beforeAvailableRooms) - 1),
					"Failed : Available Rooms is not decreased after creating reservation");
			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Reduce Room Class");
			app_logs.info("Reduce Room Class");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify", testName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify", testName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		
		try {

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to add steps intot report", testName, "AddStepsIntoReport", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("TapChartUnassignedRes1698", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
