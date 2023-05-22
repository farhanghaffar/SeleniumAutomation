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
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class CreateReservation1698 extends TestCore {
	private WebDriver driver = null;
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();

	//@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
//dataProvider = "getData", groups = "Reservation"
	@Test()
	public void createReservation1698() /*(String url, String clientCode, String username, String password,
			String checkInDate, String checkOutDate, String adult, String children, String promoCode,
			String isAssign, String isDepositOverride, String depositOverrideAmount,
			String salutation, String guestFirstName, String guestLastName, String phoneNumber, String alternativePhone,
			String email, String account, String accountType, String address1, String address2, String address3,
			String city, String country, String state, String postalCode, String isGuesProfile, String paymentType,
			String cardNumber, String nameOnCard, String cardExpDate, String isChangeInPayAmount,
			String changedAmountValue, String travelAgent, String marketSegment, String referral, String isAddNotes,
			String noteType, String noteSubject, String description, String isTask, String taskCategory, String taskType,
			String taskDetails, String taskRemarks, String taskDueon, String taskAssignee, String taskStatus,
			String channels,String SeasonName, String seasonDateFormat, String isMonDay, String isTueDay, String isWednesDay,
			String isThursDay, String isFriday, String isSaturDay, String isSunDay, String specificDate, String dateFormat,String calendarTodayDay,
			String isAdditionalChargesForChildrenAdults, String MaxAdults, String MaxPersons,
			String AdditionalAdultsPerNight, String AdditionalChildPerNight, String isAddRoomClassInSeason,
			String ExtraRoomClassesInSeason, String ExtraRoomClassRatePerNight, String ExtraRoomClassMaxAdults,
			String ExtraRoomClassMaxPersons, String ExtraRoomClassAdditionalAdultsPerNight,
			String ExtraRoomClassAdditionalChildPerNight,String reason, String	subject,
			String ratePlanName, String baseAmount,
			String addtionalAdult, String additionalChild, String folioName, String associateSession,
			String ratePolicy, String rateDescription, String roomClassAbbreviation, String roomClassName,
			String bedsCount, String maxAdults, String maxPersons, String roomQuantity, String startRoomNumber)*/
			throws InterruptedException, IOException {

		String testName = "CreateReservation1698";
		testDescription = "Reservation Creation<br>";
		testCategory = "ReservationCreation";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();
		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		NightlyRate nightlyRate = new NightlyRate();
		RoomClass roomClass = new RoomClass();
		ReservationSearch reservationSearch = new ReservationSearch();
		
		String reservationNumber = null;
		String status = null;
		String calendarTodayDate = null;
/*		String tempraryRoomClassName = roomClassName;
		String tempraryRateName = ratePlanName;
		String randomNumber = Utility.GenerateRandomNumber();
	//	randomNumber = "153";
		roomClassName = roomClassName + randomNumber;
		roomClassAbbreviation = roomClassAbbreviation + randomNumber;
		ratePlanName = ratePlanName + randomNumber;
		folioName = ratePlanName;
		guestFirstName = guestFirstName + randomNumber;
		guestLastName = guestLastName + randomNumber;
		String timeZone = "America/New_York";
*/
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

		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			//login_CP(driver);
			testSteps.add("Logged into the application");
			testSteps.add("Logged into the application");
			testSteps.add("Logged into the application");
			testSteps.add("Logged into the application");
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		/*
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
			app_logs.info("calendarTodayDate : " + calendarTodayDate);
			getTestSteps.clear();
			getTestSteps = ratesGrid.closeCalendar(driver);
			testSteps.addAll(getTestSteps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to Navigate Rates Grid", testName, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
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
			testSteps.add("Click Add rate Plan");
			app_logs.info("Click Add rate Plan");
			ratesGrid.clickRateGridAddRatePlanOption(driver, "Nightly rate plan");
			testSteps.add("Click Nightly Rate Plan");
			app_logs.info("Click Nightly rate Plan");

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
					Utility.addDate(2, dateFormat, calendarTodayDate, seasonDateFormat, timeZone));
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
			app_logs.info("==========CREATE NEW RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
			testSteps.add("==========CREATE NEW RESERVATION FROM <b>NEW RESERVATION</b> BUTTON==========");
			reservationPage.click_NewReservation(driver, testSteps);

			String CheckInDate = Utility.parseDate(calendarTodayDate, dateFormat, "dd/MM/yyyy");
			String CheckoutDate = Utility.addDate(1, dateFormat, calendarTodayDate, "dd/MM/yyyy", timeZone);

			app_logs.info("CheckOut Date : " + CheckoutDate);
			reservationPage.select_CheckInDate(driver, testSteps, CheckInDate);
			reservationPage.select_CheckoutDate(driver, testSteps, CheckoutDate);
			
			
			getTestSteps.clear();
			getTestSteps = reservationPage.enterAdult(driver, adult);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterChildren(driver, children);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRateplan(driver, ratePlanName, promoCode, 1);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectRoom(driver, getTestSteps, roomClassName, "Yes", "");
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.clickNext(driver, getTestSteps);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.verifyGuestProfileCheckoxChecked(driver, false);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterGuestName(driver, getTestSteps, salutation, guestFirstName,
					guestLastName);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enterEmail(driver, getTestSteps, email);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.selectReferral(driver, referral);
			testSteps.addAll(getTestSteps);

			getTestSteps.clear();
			getTestSteps = reservationPage.enter_PaymentDetails(driver, getTestSteps, paymentType, cardNumber, nameOnCard, cardExpDate);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickBookNow(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			reservationPage.get_ReservationStatus(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			getTestSteps.clear();
			getTestSteps = reservationPage.clickCloseReservation(driver, getTestSteps);
			testSteps.addAll(getTestSteps);
			
			reservationPage.closeReservationTab(driver);
			testSteps.add("Close Reservation");
			app_logs.info("Close Reservation");

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(ScriptName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
				Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation", driver);
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
			app_logs.info("========= EXTEND RESERVATION ========");
			testSteps.add("========= EXTEND RESERVATION ========");
			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");

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
				afterExtendAvailableRoomsRatesGrid = ratesGrid.getRoomClassAvailableValueInRatesGridTab(driver, dateIndex,
						roomClassName);
				testSteps.add("Rates Grid Available Rooms for Date(" + tempDate + ") : " + afterExtendAvailableRoomsRatesGrid);
				app_logs.info("Rates Grid Available Rooms for Date(" + tempDate + ") : " + afterExtendAvailableRoomsRatesGrid);
				assertEquals(afterAvailableRoomsRatesGrid,
						Integer.toString(Integer.parseInt(beforeAvailableRoomsRatesGrid) - 1),
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
			for(int i = 0 ; i < totalRoomsExtend ; i++){
				tempDate = Utility.addDate(i, dateFormat, calendarTodayDate, dateFormat, timeZone);
				dateIndex = ratesGrid.getHeadingDateIndex(driver, tempDate, dateFormat);
				afterExtendReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Reserved");
				testSteps.add("After Extend Reserved Count for Date(" + tempDate + ") : " + afterExtendReservedCount);
				app_logs.info("After Extend Reserved Count for Date(" + tempDate + ") : " + afterExtendReservedCount);
				assertEquals(afterExtendReservedCount, Integer.toString(Integer.parseInt(beforeReservedCount) + 1),
						"Failed : Reserved count missmatched");

				afterExtendAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex, "Available");
				testSteps.add("After Extend Available Rooms for Date(" + tempDate + ") : " + afterExtendAvailableRooms);
				app_logs.info("After Extend Available Rooms for Date(" + tempDate + ") : " + afterExtendAvailableRooms);
				assertEquals(afterExtendAvailableRooms, Integer.toString(Integer.parseInt(beforeAvailableRooms) - 1),
						"Failed : Available Rooms missmatched");
			}

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
			app_logs.info("========= REDUCE RESERVATION ========");
			testSteps.add("========= REDUCE RESERVATION ========");
			navigation.reservationBackward3(driver);
			testSteps.add("Navigate to Reservations");
			app_logs.info("Navigate to Reservations");
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
		*/
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
		return Utility.getData("CreateReservation1698", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	}

}
