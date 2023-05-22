package com.innroad.inncenter.tests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.AdvGroups;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Folio;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.ESTTimeZone;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class VerifyAbleToCreateGroupReservationWithYellowBookIcon1698 extends TestCore {

	// Automation-1464
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

	@Test(dataProvider = "getData", groups = "Inventory")
	public void verifyAbleToCreateGroupReservationWithYellowBookIcon1698(String ratePlanName, String baseAmount,
			String addtionalAdult, String additionalChild, String folioName, String associateSession,
			String ratePolicy, String rateDescription, String roomClassAbbreviation, String roomClassName,
			String bedsCount, String maxAdults, String maxPersons, String roomQuantity, String startRoomNumber,
			String ratePlan, String rateType, String rateAttributes, String interval, String source, String adults,
			String child, String marketSegment, String groupReferral, String groupFirstName, String groupLastName,
			String groupPhone, String groupAddress, String groupCity, String groupCountry, String groupState,
			String groupPostalcode, String blockName, String roomPerNight, String firstName, String lastName,
			String updatedBlockedCount, String roomBlockCount, String lineItemDescription, String roomCharge,
			String postedState, String itemRow, String spanTag, String guestFolio, String pendingState,
			String blueBookClass, String yellowBookClass, String yelloFirstName, String yellowLastName,
			String isChecked, String channels,String SeasonName, String seasonDateFormat, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay, String specificDate, String dateFormat,String calendarTodayDay,
			String isAdditionalChargesForChildrenAdults, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight,String reason, String	subject) throws InterruptedException, IOException {

		String testName = "VerifyAbleToCreateGroupReservationWithYellowBookIcon1698";
		testDescription = "Desktop : Advanced group : Verify able to create group reservation with Yellow Book icon.<br>";
		testCategory = "Groups";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		Navigation navigation = new Navigation();
		CPReservationPage reservationPage = new CPReservationPage();
		Folio folio = new Folio();
		RoomClass roomClass = new RoomClass();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		ReservationSearch reservationSearch = new ReservationSearch();
		String tempraryRoomClassName = roomClassName;
		String tempraryRateName = ratePlanName;
		String randomNumber = Utility.GenerateRandomNumber();
		roomClassName = roomClassName + randomNumber;
		roomClassAbbreviation = roomClassAbbreviation + randomNumber;
		ratePlanName = ratePlanName + randomNumber;
		folioName = ratePlanName;
		groupLastName = groupLastName + randomNumber;
		String accountName = groupFirstName + groupLastName;
		lastName = lastName + randomNumber;
		String blueReservationFirstName = "Blue" + firstName;
		String accountNumber = null;
		String saluation = "Mr.";
		String reservationNumber = null;
		String reservationNumber1 = null;
		Groups group = new Groups();
		AdvGroups advanceGroup = new AdvGroups();
		blockName = blockName + randomNumber;
		String tax = null;
		String expectedRevenue = null;
		yellowLastName = yellowLastName + randomNumber;
		
		String beforeAvailableRoom = null;
		String beforePickupValue = null;
		SeasonName = SeasonName + randomNumber;
		String calendarTodayDate = "July/17/2020";
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
			testSteps.add("Logged into the application ");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
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
			getTestSteps = roomClass.createRoomClass(driver, roomClassName, roomClassAbbreviation, bedsCount, maxAdults,
					maxPersons, roomQuantity, test, getTestSteps);
			testSteps.addAll(getTestSteps);

			testSteps.add("Sccessfully Created New RoomClass " + roomClassName + " Abb : " + roomClassAbbreviation);
			app_logs.info("Sccessfully Created New RoomClass" + roomClassName + " Abb : " + roomClassAbbreviation);

			roomClass.closeRoomClassTab(driver);
			testSteps.add("Close created room class tab");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create room class successfully", testName, "NewRoomClass", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to associate room class in newly created rateplan", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
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
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "CreateNewRate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new season", testName, "CreateNewRate", driver);
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

		// Reservation
		try {
			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

			navigation.secondaryGroupsManu(driver);
			testSteps.add("Navigate Groups");
			app_logs.info(" Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", testName, "NavigateAdvGroup", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate AdvGroup", testName, "NavigateAdvGroup", driver);
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
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click on new account button", testName, "EnterAccountName", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to click new account and enter account name", testName,
						"EnterAccountName", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enter Account Number
		try {
			accountNumber = Utility.GenerateRandomString15Digit();
			getTestSteps.clear();
			getTestSteps = group.enterAccountNo(driver, accountNumber);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Number", testName, "EnterAccountNumber", driver);
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
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to select account attributes", testName, "AccountAttributes", driver);
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
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
						"EnterAccountMailingAddress", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Enter Account Mailing Address", testName,
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
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Check Mailing Info CheckBox", testName, "CheckMailingInfoCheckBox",
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
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Save  Account", testName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Save Account", testName, "SaveAccount", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Navigate to Reservation and check count
		try {
			app_logs.info("==========GET RESERVATION COUNT==========");
			testSteps.add("==========GET RESERVATION COUNT==========");
			getTestSteps.clear();
			group.clickOnGroupsReservationTab(driver);
			testSteps.addAll(getTestSteps);

			String initialResCount = group.getReservationCount(driver);
			testSteps.add("Initial Reservation Count : " + initialResCount);
			Utility.app_logs.info("Initial Reservation Count : " + initialResCount);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Folio", testName, "Group", driver);
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
			getTestSteps = group.SelectArrivalDepartureDates(driver, Utility.getCurrentDate("MMM dd, yyyy"),
					Utility.GetNextDate(1, "MMM dd, yyyy"));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectRatePlan(driver, ratePlanName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectAdults(driver, adults);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.SelectChilds(driver, child);
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

			app_logs.info("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
			testSteps.add("==========GET AND VERIFY ROOM BLOCKS TAB DETAILS==========");
			
			String RoomBlocked = group.getRoomBlockedInRoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail Room Blocked  : " + RoomBlocked);
			testSteps.add("Room Block Detail Room Blocked  : " + RoomBlocked);
			assertEquals(RoomBlocked, roomPerNight, "Failed Room Blocked Not Matched");

			String totalRoomNight = group.getTotalRoomNightsInRoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Total Room Nights  : " + totalRoomNight);
			testSteps.add("Room Block Detail Total Room Nights  : " + totalRoomNight);
			assertEquals(totalRoomNight, roomPerNight, "Failed Room Blocked Not Matched");

			String expectedRevenueDetail = group.getExpectedRevenueInRoomBlockDetail(driver);
			Utility.app_logs.info("Room Block Detail Expected Revenue  : " + expectedRevenueDetail);
			testSteps.add("Room Block Detail ExpectedRevenue  : " + expectedRevenueDetail);

			String expectedRevenueInfo = group.getExpectedRevenueInGroupInfo(driver);
			Utility.app_logs.info("Group Info Expected Revenue  : " + expectedRevenueInfo);
			testSteps.add("Group Info ExpectedRevenue  : " + expectedRevenueInfo);
			assertEquals(expectedRevenueDetail, expectedRevenueInfo, "Failed Expected Revenue Not Matched");

			expectedRevenue = expectedRevenueInfo;
			String pickUpPercentage = group.getPickUpPercentageInRoomBlockDetatil(driver);
			Utility.app_logs.info("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			testSteps.add("Room Block Detail PickUp Percentage  : " + pickUpPercentage);
			assertEquals(pickUpPercentage, "0", "Failed Pickup Percentage missmatched");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Create Block and Verify RoomClass", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// end here
		try {
			getTestSteps.clear();
			getTestSteps = group.bookIconClick(driver, roomClassName);
			testSteps.addAll(getTestSteps);
			Utility.app_logs.info("Click <b>Blue Book Icon</b> of Room Class  : " + roomClassName);
			testSteps.add("Click <b>Blue Book Icon</b> of Room Class  : " + roomClassName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		try {
			app_logs.info("==========ENTER RESERVATION DETAILS CREATED FROM <b>BLUE BOOK ICON</b>==========");
			testSteps.add("==========ENTER RESERVATION DETAILS CREATED FROM <b>BLUE BOOK ICON</b>==========");

			String roomnumber = reservationPage.getRoomNumber(driver, testSteps);
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			testSteps.add("Verified Room Number is Unassigned");

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyAssociatedAccount_ResHeader(driver, accountName);
			testSteps.addAll(getTestSteps);
			

			getTestSteps.clear();
			getTestSteps = reservationPage.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean(isChecked));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enter_GuestName(driver, getTestSteps, saluation, blueReservationFirstName, lastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, groupReferral);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps= reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			reservationNumber1 = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
			String ReservationStatus = reservationPage.get_ReservationStatus(driver, getTestSteps);
			Assert.assertEquals(ReservationStatus, "Reserved", "Failed: Reservation Status missmatched");
			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);
			

			testSteps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
			getTestSteps.clear();
			getTestSteps = reservationPage.verifyAssociatedAccount(driver, accountName);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		
		try {
			navigation.secondaryGroupsManu(driver);
			app_logs.info("Navigate Groups");
			testSteps.add("Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========SEARCH CREATED GROUP ACCOUNT==========");
			testSteps.add("==========SEARCH CREATED GROUP ACCOUNT==========");
			getTestSteps.clear();
			getTestSteps = group.searchGroupAccount(driver, accountName, accountNumber, true, true);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			getTestSteps.clear();
			getTestSteps = group.navigateRoomBlock(driver);
			testSteps.addAll(getTestSteps);

			String beforeBookIconClass = null;

			app_logs.info("==========ROOMING LIST DETAILS==========");
			testSteps.add("==========ROOMING LIST DETAILS==========");
			Utility.app_logs.info("Book Room From RoomClass : " + roomClassName);
			testSteps.add("Book Room From RoomClass : " + roomClassName);

			beforePickupValue = group.getPickUpValue(driver, roomClassName);
			Utility.app_logs.info("Pickup Value : " + beforePickupValue);
			testSteps.add("Pickup Value : " + beforePickupValue);

			beforeAvailableRoom = group.getAvailableRooms(driver, roomClassName);
			Utility.app_logs.info("Available Rooms : " + beforeAvailableRoom);
			testSteps.add("Available Rooms : " + beforeAvailableRoom);

			beforeBookIconClass = group.getBookIconClass(driver, roomClassName);
			testSteps.add("BookIcon class : " + beforeBookIconClass);
			assertEquals(beforeBookIconClass, yellowBookClass, "Failed Book Icon is not Blue");

			String blockedCount = group.getBlocked(driver, roomClassName);
			testSteps.add("Blocked count  : " + blockedCount);
			assertEquals(blockedCount, roomBlockCount, "Failed Room Blocked Not Matched");

			group.bookIconClick(driver, roomClassName);
			Utility.app_logs.info("Click <b>Yellow Book Icon</b> of Room Class  : " + roomClassName);
			testSteps.add("Click <b>Yellow Book Icon</b> of Room Class  : " + roomClassName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Process Book Icon", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Reservation
		try {
			app_logs.info("==========ENTER RESERVATION DETAILS CREATED FROM <b>YELLOW BOOK ICON</b>==========");
			testSteps.add("==========ENTER RESERVATION DETAILS CREATED FROM <b>YELLOW BOOK ICON</b>==========");

			String roomnumber = reservationPage.getRoomNumber(driver, testSteps);
			Assert.assertEquals(roomnumber, "Unassigned", "Failed: Reservation is not unassigned");
			testSteps.add("Verified Room Number is Unassigned");

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyAssociatedAccount_ResHeader(driver, accountName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.VerifyGuestProfileCheckox(driver, Boolean.parseBoolean(isChecked));
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enter_GuestName(driver, getTestSteps, saluation, yelloFirstName, yellowLastName);
			testSteps.addAll(getTestSteps);

			reservationPage.selectReferral(driver, groupReferral);

			getTestSteps.clear();
			getTestSteps= reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
			String ReservationStatus = reservationPage.get_ReservationStatus(driver, getTestSteps);
			Assert.assertEquals(ReservationStatus, "Reserved", "Failed: Reservation Status missmatched");
			reservationPage.clickCloseReservationSavePopup(driver, getTestSteps);

			testSteps.add("Successfully Associated Account to  Reservation");
			app_logs.info("Successfully Associated Account to Reservation");
			getTestSteps.clear();
			getTestSteps = reservationPage.verifyAssociatedAccount(driver, accountName);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========VERIFY RESERVATION HISTORY==========");
			testSteps.add("==========VERIFY RESERVATION HISTORY==========");
			getTestSteps.clear();
			getTestSteps = reservationPage.ClickOnHistory(driver);
			testSteps.addAll(getTestSteps);
			getTestSteps.clear();
			getTestSteps = reservationPage.verifyResInHistory(driver, reservationNumber);
			testSteps.addAll(getTestSteps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Associate Account to Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			folio.FolioTab(driver);
			Utility.app_logs.info("Click Folio Tab");
			testSteps.add("Click Folio Tab");

			tax = folio.getTax(driver, roomCharge);

			testSteps.add("Room Charges Tax : " + tax);
			app_logs.info("Room Charges Tax : " + tax);

			getTestSteps.clear();
			getTestSteps = folio.FolioExist(driver, guestFolio, accountName, true);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Folio Options", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Folio Options", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("==========VERIFY LINE ITEM DETAIL IN GUEST FOLIO==========");
			testSteps.add("==========VERIFY LINE ITEM DETAIL IN GUEST FOLIO==========");

			getTestSteps.clear();
			getTestSteps = folio.changeFolioOption(driver, guestFolio);
			testSteps.addAll(getTestSteps);

			folio.VerifyLineItems_State(driver, roomCharge, pendingState, 1);
			testSteps.add("Verify line itme in pending state after added");

			getTestSteps.clear();
			getTestSteps = folio.LineItemDate(driver, roomCharge, 0, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = folio.LineItemCategory(driver, roomCharge, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = folio.getDescroption(driver, roomCharge, lineItemDescription, false, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = folio.LineItemQuentity(driver, roomCharge, "1", 1);
			testSteps.addAll(getTestSteps);

			String getTax = folio.getTax(driver, roomCharge);
			String totalAmountWithTax = folio.AddValue(baseAmount, getTax);
			totalAmountWithTax = "$ " + totalAmountWithTax;

			String getAmount = folio.getAmount(driver, roomCharge, 1);
			getAmount = "$ " + getAmount;
			testSteps.add("Expected amount after added tax: " + totalAmountWithTax);
			testSteps.add("Found : " + getAmount);
			assertEquals(totalAmountWithTax, totalAmountWithTax, "Failed: Amount is mismatching!");
			testSteps.add("Verified amount after included tax");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Folio Options", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Verify Folio Options", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========VERIFY LINE ITEM DETAIL IN ACCOUNT FOLIO==========");
			testSteps.add("==========VERIFY LINE ITEM DETAIL IN ACCOUNT FOLIO==========");

			getTestSteps.clear();
			getTestSteps = folio.changeFolioOption(driver, accountName);
			testSteps.addAll(getTestSteps);

			int rowsSize = folio.getLineItemRows(driver);
			app_logs.info("BeforeRowsSize: " + rowsSize);
			assertEquals(rowsSize, 0, "Failed: Line item is showing after selected account folio");
			testSteps.add("Verified no lien item is displaying in folio line item after selected " + accountName);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify folio", testName, "FolioVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify folio", testName, "FolioVerification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Start verify item in item details pop up
		String getAmount = "";
		try {
			app_logs.info("==========VERIFY 'ITEM DETAIL' POPUP LINE ITEMS==========");
			testSteps.add("==========VERIFY 'ITEM DETAIL' POPUP LINE ITEMS==========");

			getTestSteps.clear();
			getTestSteps = folio.changeFolioOption(driver, guestFolio);
			testSteps.addAll(getTestSteps);

			getTestSteps = folio.getDescroption(driver, roomCharge, lineItemDescription, true);
			testSteps.add("Click Line Item Description : " + lineItemDescription);
			app_logs.info("Click Line Item Description : " + lineItemDescription);
			folio.ItemDetails_Category_State(driver, roomCharge, postedState, 1);
			testSteps.add("Verify line itme in pending state");
			app_logs.info("Verify line itme in pending state");

			getTestSteps.clear();
			getTestSteps = folio.DateItemDetails(driver, roomCharge, 1, itemRow);

			getTestSteps.clear();
			getTestSteps = folio.ItemDetailsCategory(driver, roomCharge, 1, itemRow);

			getTestSteps.clear();
			getTestSteps = folio.itemDetails_Descroption(driver, roomCharge, lineItemDescription, false, 1, itemRow,
					spanTag);

			testSteps.add("Successfully Verified Line item exist");
			app_logs.info("Successfully Verified Line item exist");
			getAmount = folio.getAmount_ItemDetails(driver, roomCharge, 1);
			testSteps.add("Expected amount: $ " + baseAmount + ".00");
			app_logs.info("Expected amount: $ " + baseAmount + ".00");
			testSteps.add("Found: " + getAmount);
			app_logs.info("Found: " + getAmount);
			assertEquals(getAmount, "$ " + baseAmount + ".00",
					"Failed: manual override rate value is mismatching in item row");

			testSteps.add("Line Item 'Date : " + Utility.getCurrentDate("E, dd-MMM-yyyy") + "', 'Category : "
					+ roomCharge + "', Description : " + lineItemDescription + "' and Amount : " + baseAmount + "'");
			app_logs.info("Line Item 'Date : " + Utility.getCurrentDate("E, dd-MMM-yyyy") + "', 'Category : "
					+ roomCharge + "', Description : " + lineItemDescription + "' and Amount : " + baseAmount + "'");

			app_logs.info("==========VERIFY 'ITEM DETAIL' POPUP ROOM CHARGES==========");
			testSteps.add("==========VERIFY 'ITEM DETAIL' POPUP ROOM CHARGES==========");

			String itemDetails_RoomChares = folio.Itemdetails_RoomChares(driver);
			testSteps.add("Expected room chares: $ " + baseAmount + ".00");
			app_logs.info("Expected room chares: $ " + baseAmount + ".00");
			testSteps.add("Found: " + itemDetails_RoomChares);
			app_logs.info("Found: " + itemDetails_RoomChares);
			assertEquals(itemDetails_RoomChares, "$ " + baseAmount + ".00",
					"Failed: room charges are mismatching in item details popup");
			testSteps.add("Verified room charges in item detail popup");
			app_logs.info("Verified room charges in item detail popup");

			getTestSteps.clear();
			getTestSteps = folio.CancelPopupButton(driver, true, false);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify item details popup", testName, "Folio", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify item details popup", testName, "Folio", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Navigate to Groups
		try {
			navigation.secondaryGroupsManu(driver);
			app_logs.info("Navigate Groups");
			testSteps.add("Navigate Groups");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			app_logs.info("==========SEARCH CREATED GROUP ACCOUNT==========");
			testSteps.add("==========SEARCH CREATED GROUP ACCOUNT==========");
			getTestSteps.clear();
			getTestSteps = group.searchGroupAccount(driver, accountName, accountNumber, true, true);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Search Account ", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// navigate Group reservation tab
		try {
			app_logs.info("==========VERIFY RESERVATION CREATED GROUP==========");
			testSteps.add("==========VERIFY RESERVATION CREATED GROUP==========");
			getTestSteps.clear();
			advanceGroup.clickOnGroupsReservationTab(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = group.verifyReservationCount(driver, 2);
			testSteps.addAll(getTestSteps);

			String text = group.getReservationDetails(driver, reservationNumber, 1);
			assertEquals(text, yelloFirstName + " " + yellowLastName, "Failed: Guest name Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 2);
			assertEquals(text, blockName, "Failed: Block name Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 3);
			assertEquals(text, adults, "Failed: Adults Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 4);
			assertEquals(text, child, "Failed: Children Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 5);
			assertEquals(text, "Reserved", "Failed: Res Status Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 6);
			assertEquals(text, roomClassName, "Failed: Room Class Name Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 7);
			assertEquals(text, Utility.getCurrentDate("MMM d, yyyy"), "Failed: Arrival Date Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 8);
			assertEquals(text, Utility.GetNextDate(1, "MMM d, yyyy"), "Failed: Departure Date Missmatched");
			text = group.getReservationDetails(driver, reservationNumber, 9);
			assertEquals(text, roomPerNight, "Failed: Nights Missmatched");

			testSteps.add("Successfully Verified Reservation Exist");
			app_logs.info("Successfully Verified Line item is added");

			testSteps.add("'Reservation Number : " + reservationNumber + "', 'Guest Name : " + yelloFirstName + " "
					+ yellowLastName + "', 'BlockName : " + blockName + "', 'Room Class : " + roomClassName + "'");

			app_logs.info("'Reservation Number : " + reservationNumber + "', 'Guest Name : " + blueReservationFirstName + " "
					+ lastName + "', 'BlockName : " + blockName + "', 'Room Class : " + roomClassName + "'");

			testSteps.add("'Adults : " + adults + "', 'Children : " + child + "', 'Arrival Date : "
					+ Utility.getCurrentDate("MMM d, yyyy") + "', 'Departure Date : "
					+ Utility.GetNextDate(1, "MMM d, yyyy") + "'");
			app_logs.info("'Adults : " + adults + "', 'Children : " + child + "', 'Arrival Date : "
					+ Utility.getCurrentDate("MMM d, yyyy") + "', 'Departure Date : "
					+ Utility.GetNextDate(1, "MMM d, yyyy") + "'");

			group.ClickReservationName_VerifyPopup(driver, reservationNumber, testSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify item details popup", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify item details popup", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// here to verify block verification

		try {
			app_logs.info("==========VERIFY BLOCK DETAILS AFTER CREATED RESERVATION==========");
			testSteps.add("==========VERIFY BLOCK DETAILS AFTER CREATED RESERVATION==========");

			getTestSteps.clear();
			getTestSteps = group.navigateRoomBlock(driver);
			testSteps.addAll(getTestSteps);

			String todayDate = ESTTimeZone.DateFormateForLineItem(0);
			todayDate = Utility.parseDate(todayDate, "MM/dd/yyyy", "MMM d, yyyy");
			String getRelaseDate = group.getReleaseDateInRoomBlockDetatil(driver);
			testSteps.add("Expected realse date: " + todayDate);
			testSteps.add("Found: " + getRelaseDate);
			assertEquals(getRelaseDate, todayDate, "Failed: Realse date is mismatching");

			String totalRoomNight = group.getTotalRoomNightsInRoomBlockDetail(driver);
			testSteps.add("Expecetd total room nights: " + roomPerNight);
			testSteps.add("Found: " + totalRoomNight);
			assertEquals(totalRoomNight, roomPerNight, "Failed: per night is mismathching");
			testSteps.add("Verified room nights");

			expectedRevenue = folio.AddValue(baseAmount, tax);
			expectedRevenue = folio.AddValue(expectedRevenue, expectedRevenue);
			String expectedRevenueDetail = group.getExpectedRevenueInRoomBlockDetail(driver);
			testSteps.add("Expected revenue: $" + expectedRevenue);
			testSteps.add("Found: $" + expectedRevenueDetail);
			assertEquals(expectedRevenueDetail, expectedRevenue, "Faied: Expected revenue is mismatching");
			testSteps.add("Verified expected revenue");

			String getPickedupRevenue = group.getPickedupRevenueInRoomBlockDetail(driver);
			testSteps.add("Pickedup revenue: $" + expectedRevenue);
			testSteps.add("Found: $" + getPickedupRevenue);
			assertEquals(getPickedupRevenue, expectedRevenue, "Faied: Pickedup revenue is mismatching");
			testSteps.add("Verified pickedup revenue");

			String afterExpectedRevenue = group.getExpectedRevenueInGroupInfo(driver);
			Utility.app_logs.info("Expected revenue in block info: " + getRelaseDate);
			testSteps.add("Expected revenue  : " + getRelaseDate);
			testSteps.add("Found: $" + afterExpectedRevenue);
			assertEquals(afterExpectedRevenue, expectedRevenue, "Failed Expected revenue is mismatching in block info");

			String pickUpPercentage = group.getPickUpPercentageInRoomBlockDetatil(driver);
			testSteps.add("Expected pickup percentage: 200");
			testSteps.add("Found: " + pickUpPercentage);
			assertEquals(pickUpPercentage, "200", "Failed Pickup Percentage missmatched");
			testSteps.add("Verified pickup percentage");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room block details", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify room block details", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {

			app_logs.info("==========ROOMING LIST DETAILS AFTER CREATED NEW RESERVATION==========");
			testSteps.add("==========ROOMING LIST DETAILS AFTER CREATED NEW RESERVATION==========");

			String afterPickupValue = group.getPickUpValue(driver, roomClassName);
			testSteps.add("Expected pickup: 2");
			testSteps.add("Found: " + afterPickupValue);
			assertEquals(afterPickupValue, "2", "Failed: Pickup value is mismatching");
			testSteps.add("Verified pickup value");

			String afterAvailableRoom = group.getAvailableRooms(driver, roomClassName);
			roomQuantity = folio.MinseValue(roomQuantity, "2");
			testSteps.add("Expected available rooms: " + roomQuantity);
			testSteps.add("Found : " + afterAvailableRoom);
			assertEquals(afterAvailableRoom, roomQuantity, "Failed: Available rooms is mismatching");
			testSteps.add("Verified available rooms");

			String bookClass = group.getBookIconClass(driver, roomClassName);
			assertNotEquals(bookClass, yellowBookClass, "Failed Room book Icon is still blue");
			testSteps.add("Verified room book icon color changed from yellow to other");

			String blockedCount = group.getBlocked(driver, roomClassName);
			testSteps.add("Expected block count: " + roomBlockCount);
			testSteps.add("Found: " + blockedCount);
			assertEquals(blockedCount, roomBlockCount, "Failed Room Blocked Not Matched");
			testSteps.add("Verified block count");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rooming list", testName, "Group", driver);
			} else {
				Assert.assertTrue(false);
			}

		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify rooming list", testName, "Group", driver);
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
		app_logs.info("beforeAvailableRoomsRatesGrid : " + beforeAvailableRoomsRatesGrid);
		assertEquals(afterAvailableRoomsRatesGrid,
				Integer.toString(Integer.parseInt(beforeAvailableRoomsRatesGrid) - 2),
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
		app_logs.info("beforeReservedCount : "  +beforeReservedCount);
		assertEquals(afterReservedCount, Integer.toString(Integer.parseInt(beforeReservedCount) + 2),
				"Failed : Reserved count is not increased after creating reservation");
	
		afterAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
		testSteps.add("After Available Rooms : " + afterAvailableRooms);
		app_logs.info("After Available Rooms : " + afterAvailableRooms);
		app_logs.info("beforeAvailableRooms : "  +beforeAvailableRooms);
		assertEquals(afterAvailableRooms, Integer.toString(Integer.parseInt(beforeAvailableRooms) - 2),
				"Failed : Available Rooms is not decreased after creating reservation");
		ratesGrid.expandRoomClass(driver, roomIndex);
		testSteps.add("Reduce Room Class");
		app_logs.info("Reduce Room Class");
	
	} catch (Exception e) {
		e.printStackTrace();
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
			app_logs.info("========= EXTEND FIRST RESERVATION ========");
			testSteps.add("========= EXTEND FIRST RESERVATION ========");
			
			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

			reservationPage.closeReservationTab(driver);
			testSteps.add("Closed opened reservation");
			app_logs.info("Closed opened reservation");
			
			String CheckInDate = Utility.addDate(0, dateFormat, calendarTodayDate, "MM/dd/yyyy", timeZone);
			String CheckOutDate = Utility.addDate(totalRoomsExtend, dateFormat, calendarTodayDate, "MM/dd/yyyy", timeZone);
			app_logs.info("CheckInDate : " + CheckInDate);
			app_logs.info("CheckOutDate : " + CheckOutDate);
			app_logs.info("calendarTodayDate : " + calendarTodayDate);
			reservationSearch.basicSearchWithResNumber(driver, reservationNumber1, true);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, CheckInDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, CheckOutDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);
		//add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to extend reservation", testName, "ExtendReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to extend reservation", testName, "ExtendReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			app_logs.info("========= EXTEND RESERVATION ========");
			testSteps.add("========= EXTEND RESERVATION ========");
			
			String CheckInDate = Utility.addDate(0, dateFormat, calendarTodayDate, "MM/dd/yyyy", timeZone);
			String CheckOutDate = Utility.addDate(totalRoomsExtend, dateFormat, calendarTodayDate, "MM/dd/yyyy", timeZone);
			app_logs.info("CheckInDate : " + CheckInDate);
			app_logs.info("CheckOutDate : " + CheckOutDate);
			app_logs.info("calendarTodayDate : " + calendarTodayDate);
			reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, CheckInDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, CheckOutDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);
		//add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to extend reservation", testName, "ExtendReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to extend reservation", testName, "ExtendReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// verify
		try {
			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER EXTEND RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER EXTEND RESERVATION ========");
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
			int dateIndex ;
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
			String tempDate = null;
			for(int i = 0 ; i < totalRoomsExtend ; i++){
				tempDate = Utility.addDate(i, dateFormat, calendarTodayDate, dateFormat, timeZone);
				dateIndex = ratesGrid.getHeadingDateIndex(driver, tempDate, dateFormat);
				app_logs.info(dateIndex);
				app_logs.info(totalRoomsExtend);
				app_logs.info(beforeAvailableRoomsRatesGrid);
				app_logs.info(afterAvailableRoomsRatesGrid);
				
				afterExtendAvailableRoomsRatesGrid = ratesGrid.getRoomClassAvailableValueInRatesGridTab(driver, dateIndex,
						roomClassName);
				testSteps.add("Rates Grid Available Rooms for Date(" + tempDate + ") : " + afterExtendAvailableRoomsRatesGrid);
				app_logs.info("Rates Grid Available Rooms for Date(" + tempDate + ") : " + afterExtendAvailableRoomsRatesGrid);
				app_logs.info("afterAvailableRoomsRatesGrid : " + afterAvailableRoomsRatesGrid);
			//	assertEquals(afterAvailableRoomsRatesGrid,
				//		Integer.toString(Integer.parseInt(beforeAvailableRoomsRatesGrid) - 2),
					//	"Failed : Available Rooms missmatched");
			}	
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
			for(int i = 0 ; i < totalRoomsExtend ; i++){
				tempDate = Utility.addDate(i, dateFormat, calendarTodayDate, dateFormat, timeZone);
				dateIndex = ratesGrid.getHeadingDateIndex(driver, tempDate, dateFormat);
				afterExtendReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				testSteps.add("After Extend Reserved Count for Date(" + tempDate + ") : " + afterExtendReservedCount);
				app_logs.info("After Extend Reserved Count for Date(" + tempDate + ") : " + afterExtendReservedCount);
				assertEquals(afterExtendReservedCount, Integer.toString(Integer.parseInt(beforeReservedCount) + 2),
						"Failed : Reserved count missmatched");

				afterExtendAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				testSteps.add("After Extend Available Rooms for Date(" + tempDate + ") : " + afterExtendAvailableRooms);
				app_logs.info("After Extend Available Rooms for Date(" + tempDate + ") : " + afterExtendAvailableRooms);
				assertEquals(afterExtendAvailableRooms, Integer.toString(Integer.parseInt(beforeAvailableRooms) - 2),
						"Failed : Available Rooms missmatched");
			}

			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Reduce Room Class");
			app_logs.info("Reduce Room Class");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify", testName, "verify", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to verify", testName, "verify", driver);
			} else {

				Assert.assertTrue(false);
			}
		}
		
		try {
			app_logs.info("========= REDUCE FIRST RESERVATION ========");
			testSteps.add("========= REDUCE FIRST RESERVATION ========");
			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");
			totalRoomsExtend = 1;
			String CheckInDate = Utility.addDate(0, dateFormat, calendarTodayDate, "MM/dd/yyyy", timeZone);
			String CheckOutDate = Utility.addDate(totalRoomsExtend, dateFormat, calendarTodayDate, "MM/dd/yyyy", timeZone);
			app_logs.info("CheckInDate : " + CheckInDate);
			app_logs.info("CheckOutDate : " + CheckOutDate);
			app_logs.info("calendarTodayDate : " + calendarTodayDate);
			
			reservationSearch.basicSearchWithResNumber(driver, reservationNumber1, true);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, CheckInDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, CheckOutDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);
		//add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to reduce reservation", testName, "ReduceReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to reduce reservation", testName, "ReduceReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			app_logs.info("========= REDUCE SECOND RESERVATION ========");
			testSteps.add("========= REDUCE SECOND RESERVATION ========");
			totalRoomsExtend = 1;
			String CheckInDate = Utility.addDate(0, dateFormat, calendarTodayDate, "MM/dd/yyyy", timeZone);
			String CheckOutDate = Utility.addDate(totalRoomsExtend, dateFormat, calendarTodayDate, "MM/dd/yyyy", timeZone);
			app_logs.info("CheckInDate : " + CheckInDate);
			app_logs.info("CheckOutDate : " + CheckOutDate);
			app_logs.info("calendarTodayDate : " + calendarTodayDate);
			
			reservationSearch.basicSearchWithResNumber(driver, reservationNumber, true);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickEditReservation(driver);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickChangeStayDetails(driver);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckInDate(driver, CheckInDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterCheckOutDate(driver, CheckOutDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickFindRooms(driver);
			testSteps.addAll(getTestSteps);
		//add select method to do
			getTestSteps.clear();
			getTestSteps = reservationPage.clickSaveAfterEditStayInfo(driver);
			testSteps.addAll(getTestSteps);

			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to reduce reservation", testName, "ReduceReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to reduce reservation", testName, "ReduceReservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// verify
		try {
			app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER REDUCE RESERVATION========");
			testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + roomClassName
					+ "' AFTER REDUCE RESERVATION ========");
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
			int dateIndex ;
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
			String tempDate = null;
			for(int i = 1 ; i <= totalRoomsExtend ; i++){
				tempDate = Utility.addDate(i, dateFormat, calendarTodayDate, dateFormat, timeZone);
				dateIndex = ratesGrid.getHeadingDateIndex(driver, tempDate, dateFormat);
				afterReduceAvailableRoomsRatesGrid = ratesGrid.getRoomClassAvailableValueInRatesGridTab(driver, dateIndex,
						roomClassName);
				testSteps.add("Rates Grid Available Rooms for Date(" + tempDate + ") : " + afterReduceAvailableRoomsRatesGrid);
				app_logs.info("Rates Grid Available Rooms for Date(" + tempDate + ") : " + afterReduceAvailableRoomsRatesGrid);
				assertEquals(afterReduceAvailableRoomsRatesGrid,beforeAvailableRoomsRatesGrid,
						"Failed : Available Rooms missmatched");
			}	
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
			for(int i = 1 ; i <= totalRoomsExtend ; i++){
				tempDate = Utility.addDate(i, dateFormat, calendarTodayDate, dateFormat, timeZone);
				dateIndex = ratesGrid.getHeadingDateIndex(driver, tempDate, dateFormat);
				afterReduceReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				testSteps.add("After Reduce Reserved Count for Date(" + tempDate + ") : " + afterReduceReservedCount);
				app_logs.info("After Reduce Reserved Count for Date(" + tempDate + ") : " + afterReduceReservedCount);
				assertEquals(afterReduceReservedCount, "0",
						"Failed : Reserved count missmatched");

				afterReduceAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				testSteps.add("After Reduce Available Rooms for Date(" + tempDate + ") : " + afterReduceAvailableRooms);
				app_logs.info("After Reduce Available Rooms for Date(" + tempDate + ") : " + afterReduceAvailableRooms);
				assertEquals(afterReduceAvailableRooms, beforeAvailableRooms,
						"Failed : Available Rooms is missmatched");
			}

			ratesGrid.expandRoomClass(driver, roomIndex);
			testSteps.add("Reduce Room Class");
			app_logs.info("Reduce Room Class");

		} catch (Exception e) {
			e.printStackTrace();
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
		return Utility.getData("CreateResFromYellowIcon1698", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}
}
