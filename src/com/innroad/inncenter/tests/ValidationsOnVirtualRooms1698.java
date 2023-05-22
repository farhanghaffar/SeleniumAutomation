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
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class ValidationsOnVirtualRooms1698 extends TestCore {
	private WebDriver driver = null;
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> testSteps = new ArrayList<>();
	ArrayList<String> getTestSteps = new ArrayList<>();
	ArrayList<String> ScriptName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, excel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void validationsOnVirtualRooms1698(String adult, String children, String promoCode, String salutation,
			String guestFirstName, String guestLastName, String phoneNumber, String alternativePhone, String email,
			String marketSegment, String referral, String dateFormat, String calendarTodayDay,
			String virtualRoomClassName, String virtualRoomClassAbb, String virtualRoomRatePlan,
			String virtualRoomSeason, String virtualRoomName, String reason, String subject)
			throws InterruptedException, IOException {

		String testName = "ValidationsOnVirtualRooms1698";
		testDescription = "Reservation Creation<br>";
		testCategory = "ReservationCreation";
		ScriptName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		CPReservationPage reservationPage = new CPReservationPage();

		RoomMaintenance roomMaintenance = new RoomMaintenance();
		Navigation navigation = new Navigation();
		RatesGrid ratesGrid = new RatesGrid();
		RoomClass roomClass = new RoomClass();

		String reservationNumber = null;
		String status = null;
		String calendarTodayDate = null;
		String randomNumber = Utility.GenerateRandomNumber();
		// randomNumber = "153";
		guestFirstName = guestFirstName + randomNumber;
		guestLastName = guestLastName + randomNumber;
		String timeZone = "America/New_York";

		String beforeReservedCount = null;
		String afterPhysicalRoomReservationReservedCount = null;
		String afterVirtualRoomReservationReservedCount = null;
		String beforeAvailableRooms = null;
		String afterVirtualRoomReservationAvailableRooms = null;

		String afterPhysicalRoomReservationAvailableRooms = null;
		String beforeAvailableRoomsRatesGrid = null;
		String afterPhysicalRoomReservationAvailableRoomsRatesGrid = null;

		String afterVirtualRoomReservationAvailableRoomsRatesGrid = null;

		boolean virtualRoomExist = false;
		boolean isRoomClassExist = false;
		String beforeOOOCount = null;
		String afterOOOCount = null;
		String roomNumber = null;
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}
			driver = getDriver();
			login_CP(driver);
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

		// Room Class
		try {

			app_logs.info("==========SEARCH ROOM CLASS==========");
			testSteps.add("==========SEARCH ROOM CLASS==========");

			navigation.mainSetupManu(driver);
			app_logs.info("Navigate Setup");
			testSteps.add("Navigate Setup");

			navigation.navigateRoomClass(driver);
			app_logs.info("Navigate Room Class");
			testSteps.add("Navigate Room Class");
			
			isRoomClassExist =roomClass.VerifyRoomClassExist(driver, virtualRoomClassName);
			if (!isRoomClassExist) {
				testSteps.add("Virtual room Class not Exist!!!!!!!!!");
				app_logs.info("Virtual room Class not exist!!!!!!!!!");
			}
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
		if (isRoomClassExist) {

			try {
				navigation.Inventory(driver);
				testSteps.add("Navigate Inventory");
				app_logs.info("Navigate Inventory");
				navigation.Rates_Grid(driver);
				testSteps.add("Navigate Rates Grid");
				app_logs.info("Navigate Rates Grid");
				try {
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickCalendar(driver);
					testSteps.addAll(getTestSteps);
				} catch (Exception f) {

					navigation.Rates_Grid(driver);
					getTestSteps.clear();
					getTestSteps = ratesGrid.clickCalendar(driver);
					testSteps.addAll(getTestSteps);
				}

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

			// navigate to Inventory->Rates Grid
			try {
				app_logs.info("========== VERIFY AVAILABLE ROOMS IN RATES TAB ==========");
				testSteps.add("========== VERIFY AVAILABLE ROOMS IN RATES TAB ==========");
				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);
				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
						getTestSteps);
				testSteps.addAll(getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
				try {
					ratesGrid.clickRatePlanArrow(driver, testSteps);
				} catch (Exception f) {

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
					getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
							getTestSteps);
					testSteps.addAll(getTestSteps);

					dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
					ratesGrid.clickRatePlanArrow(driver, testSteps);
				}
				ratesGrid.searchRateAndSelectRate(driver, testSteps, virtualRoomRatePlan);
				ratesGrid.clickExpandIconOfRatePlan(driver, testSteps);
				beforeAvailableRoomsRatesGrid = ratesGrid.getRoomClassAvailableValueInRatesGridTab(driver, dateIndex,
						virtualRoomClassName);
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
				getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
						getTestSteps);
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

			// verify
			try {
				app_logs.info("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + virtualRoomClassName
						+ "' BEFORE CREATING RESERVATION ========");
				testSteps.add("========= GET RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '" + virtualRoomClassName
						+ "' BEFORE CREATING RESERVATION ========");

				getTestSteps.clear();
				getTestSteps = ratesGrid.clickCalendar(driver);
				testSteps.addAll(getTestSteps);

				ratesGrid.selectDateFromDatePicker(driver, calendarTodayDate, dateFormat, testSteps);

				getTestSteps.clear();
				getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
						getTestSteps);
				testSteps.addAll(getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
				dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
				int roomIndex;
				try {
					roomIndex = ratesGrid.getRoomClassIndex(driver, virtualRoomClassName);
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
					roomIndex = ratesGrid.getRoomClassIndex(driver, virtualRoomClassName);
				}
				ratesGrid.expandRoomClass(driver, roomIndex);
				testSteps.add("Expand Room Class");
				app_logs.info("Expand Room Class");
				app_logs.info("========= GET OOO COUNT OF ROOM CLASS '" + virtualRoomClassName
						+ "' BEFORE MAKING ROOM  OUT OF ORDER ========");
				testSteps.add("========= GET OOO COUNT OF ROOM CLASS '" + virtualRoomClassName
						+ "' BEFORE MAKING ROOM  OUT OF ORDER ========");
				beforeOOOCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "OOO");
				testSteps.add("Before Out Of Order Count : " + beforeOOOCount);
				app_logs.info("Before Out Of Order Count : " + beforeOOOCount);
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

				app_logs.info("========== CREATE ROOM MAINTENANCE ITEM (OUT OF ORDER) ==========");
				testSteps.add("========== CREATE ROOM MAINTENANCE ITEM (OUT OF ORDER)  ==========");
				navigation.Inventory_Backward_1(driver);
				navigation.Guestservices(driver);
				testSteps.add("Navigated to Guestservices");
				navigation.RoomMaintenance(driver);
				testSteps.add("Navigated to RoomMaintenance");

				roomNumber = roomMaintenance.createOutOfOrderRoomItem(driver, calendarTodayDate,
						Utility.addDate(1, dateFormat, calendarTodayDate, dateFormat, timeZone), dateFormat, subject,
						"", virtualRoomClassName, testSteps, subject);
				roomMaintenance.SearchRooms(driver, reason, roomNumber);
				testSteps.add("Successfully Search RoomMain_Item");
				app_logs.info("Successfully Search RoomMain_Item");

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

			// Reservation
			try {
				navigation.reservation(driver);
				testSteps.add("Navigate to Reservations");
				app_logs.info("Navigate to Reservations");
				app_logs.info("==========CREATE RESERVATION FOR <b>PHYSICAL ROOM</b>==========");
				testSteps.add("==========CREATE RESERVATION FOR <b>PHYSICAL ROOM</b>==========");
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
				getTestSteps = reservationPage.selectRateplan(driver, virtualRoomRatePlan, promoCode, 1);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.selectRoomNumber(driver, getTestSteps, virtualRoomClassName,
						virtualRoomName, false);
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
				getTestSteps = reservationPage.selectReferral(driver, referral);
				testSteps.addAll(getTestSteps);

				reservationPage.clickBookNow(driver, testSteps);
				reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
				reservationPage.get_ReservationStatus(driver, testSteps);
				reservationPage.clickCloseReservation(driver, testSteps);
				reservationPage.closeReservationTab(driver);
				testSteps.add("Close Reservation");
				app_logs.info("Close Reservation");

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			// verify
			try {
				app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '"
						+ virtualRoomClassName + "' AFTER CREATING PHYSICAL ROOM RESERVATION========");
				testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '"
						+ virtualRoomClassName + "' AFTER CREATING PHYSICAL ROOM  RESERVATION ========");
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
				getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
						getTestSteps);
				testSteps.addAll(getTestSteps);

				int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
				try {
					ratesGrid.clickRatePlanArrow(driver, testSteps);
				} catch (Exception f) {

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
					getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
							getTestSteps);
					testSteps.addAll(getTestSteps);

					dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
					ratesGrid.clickRatePlanArrow(driver, testSteps);
				}
				ratesGrid.searchRateAndSelectRate(driver, testSteps, virtualRoomRatePlan);
				ratesGrid.clickExpandIconOfRatePlan(driver, testSteps);
				afterPhysicalRoomReservationAvailableRoomsRatesGrid = ratesGrid
						.getRoomClassAvailableValueInRatesGridTab(driver, dateIndex, virtualRoomClassName);
				testSteps.add("Rates Grid Available Rooms after Creating Physical Room Reservation : "
						+ afterPhysicalRoomReservationAvailableRoomsRatesGrid);
				app_logs.info("Rates Grid After Available Rooms after Creating Physical Room Reservation : "
						+ afterPhysicalRoomReservationAvailableRoomsRatesGrid);

				assertEquals(afterPhysicalRoomReservationAvailableRoomsRatesGrid,
						Integer.toString(Integer.parseInt(beforeAvailableRoomsRatesGrid) - 2),
						"Failed : Available Rooms is not decreased after Creating  Physical Room Reservation in Rates Grid tab");

				app_logs.info("Verified Room Class Available rooms decreased after Physical Room Reservation creation");
				testSteps.add("Verified Room Class Available rooms decreased after Physical Room Reservation creation");
				testSteps.add("========== VERIFY IN AVAILABILITY TAB ==========");
				app_logs.info("========== VERIFY IN AVAILABILITY TAB ==========");
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
				getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
						getTestSteps);
				testSteps.addAll(getTestSteps);

				dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
				int roomIndex;
				try {
					roomIndex = ratesGrid.getRoomClassIndex(driver, virtualRoomClassName);
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
					roomIndex = ratesGrid.getRoomClassIndex(driver, virtualRoomClassName);
				}
				ratesGrid.expandRoomClass(driver, roomIndex);
				testSteps.add("Expand Room Class");
				app_logs.info("Expand Room Class");
				app_logs.info("========= GET OOO COUNT OF ROOM CLASS '" + virtualRoomClassName
						+ "' AFTER MAKING ROOM  OUT OF ORDER ========");
				testSteps.add("========= GET OOO COUNT OF ROOM CLASS '" + virtualRoomClassName
						+ "' AFTER MAKING ROOM  OUT OF ORDER ========");
				afterOOOCount = ratesGrid.getRoomClassDataValue(driver, dateIndex, "OOO");
				testSteps.add("After Out Of Order Count : " + afterOOOCount);
				app_logs.info("After Out Of Order Count : " + afterOOOCount);

				assertEquals(afterOOOCount, Integer.toString(Integer.parseInt(beforeOOOCount) + 1),
						"Failed : OOO count is not increased after making room out of order");

				testSteps.add("Verified Out Of Order Count increased");
				app_logs.info("Verified Out Of Order Count increased");
				afterPhysicalRoomReservationReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex,
						"Reserved");
				testSteps.add("After Reserved Count : " + afterPhysicalRoomReservationReservedCount);
				app_logs.info("After Reserved Count : " + afterPhysicalRoomReservationReservedCount);

				assertEquals(afterPhysicalRoomReservationReservedCount,
						Integer.toString(Integer.parseInt(beforeReservedCount) + 1),
						"Failed : Reserved count is not increased after creating physical reservation");

				app_logs.info("Verified Room Class Reserved rooms increased after Physical Room Reservation creation");
				testSteps.add("Verified Room Class Reserved rooms increased after Physical Room Reservation creation");

				afterPhysicalRoomReservationAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex,
						"Available");
				testSteps.add("After Available Rooms : " + afterPhysicalRoomReservationAvailableRooms);
				app_logs.info("After Available Rooms : " + afterPhysicalRoomReservationAvailableRooms);

				assertEquals(afterPhysicalRoomReservationAvailableRooms,
						Integer.toString(Integer.parseInt(beforeAvailableRooms) - 2),
						"Failed : Available Rooms is not decreased after creating physical reservation");

				app_logs.info("Verified Room Class Available rooms decreased after Physical Room Reservation creation");
				testSteps.add("Verified Room Class Available rooms decreased after Physical Room Reservation creation");
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

			// Reservation
			try {
				navigation.reservationBackward3(driver);
				testSteps.add("Navigate to Reservations");
				app_logs.info("Navigate to Reservations");
				app_logs.info("==========CREATE RESERVATION FOR <b>VIRTUAL ROOM</b>==========");
				testSteps.add("==========CREATE RESERVATION FOR <b>VIRTUAL ROOM</b>==========");
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
				getTestSteps = reservationPage.selectRateplan(driver, virtualRoomRatePlan, promoCode, 1);
				testSteps.addAll(getTestSteps);

				getTestSteps.clear();
				getTestSteps = reservationPage.clickOnFindRooms(driver, getTestSteps);
				testSteps.addAll(getTestSteps);

				virtualRoomExist = reservationPage.getRoomNumberAvailability(driver, testSteps, virtualRoomClassName,
						virtualRoomName);
				if (!virtualRoomExist) {
					testSteps.add("Virtual room not available for selected dates!!!!!!!!!");
					app_logs.info("Virtual room not available for selected dates!!!!!!!!!");
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			} catch (Error e) {
				if (Utility.reTry.get(ScriptName) == Utility.count) {
					RetryFailedTestCases.count = Utility.reset_count;
					Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
					Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation",
							driver);
				} else {
					Assert.assertTrue(false);
				}
			}

			if (virtualRoomExist) {
				try {
					reservationPage.clickOnFindRooms(driver, getTestSteps);
					getTestSteps.clear();
					getTestSteps = reservationPage.selectRoomNumber(driver, getTestSteps, virtualRoomClassName,
							virtualRoomName, true);
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

					// getTestSteps.clear();
					// getTestSteps = reservationPage.enterEmail(driver,
					// getTestSteps, email);
					// testSteps.addAll(getTestSteps);

					getTestSteps.clear();
					getTestSteps = reservationPage.selectReferral(driver, referral);
					testSteps.addAll(getTestSteps);

					reservationPage.clickBookNow(driver, testSteps);
					reservationNumber = reservationPage.get_ReservationConfirmationNumber(driver, testSteps);
					reservationPage.get_ReservationStatus(driver, testSteps);
					reservationPage.clickCloseReservation(driver, testSteps);
					reservationPage.closeReservationTab(driver);
					testSteps.add("Close Reservation");
					app_logs.info("Close Reservation");

				} catch (Exception e) {
					e.printStackTrace();
					if (Utility.reTry.get(ScriptName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				} catch (Error e) {
					if (Utility.reTry.get(ScriptName) == Utility.count) {
						RetryFailedTestCases.count = Utility.reset_count;
						Utility.AddTest_IntoReport(ScriptName, TestDescription, TestCategory, testSteps);
						Utility.updateReport(e, "Failed to create new reservation", testName, "ReservationCreation",
								driver);
					} else {
						Assert.assertTrue(false);
					}
				}

				// verify
				try {
					app_logs.info("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '"
							+ virtualRoomClassName + "' AFTER CREATING VIRTUAL ROOM RESERVATION========");
					testSteps.add("========= VERIFY RESERVED AND AVAILABLE ROOMS COUNT OF ROOM CLASS '"
							+ virtualRoomClassName + "' AFTER CREATING VIRTUAL ROOM  RESERVATION ========");
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
					getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
							getTestSteps);
					testSteps.addAll(getTestSteps);

					int dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
					try {
						ratesGrid.clickRatePlanArrow(driver, testSteps);
					} catch (Exception f) {

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
						getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
								getTestSteps);
						testSteps.addAll(getTestSteps);

						dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
						ratesGrid.clickRatePlanArrow(driver, testSteps);
					}
					ratesGrid.searchRateAndSelectRate(driver, testSteps, virtualRoomRatePlan);
					ratesGrid.clickExpandIconOfRatePlan(driver, testSteps);
					afterVirtualRoomReservationAvailableRoomsRatesGrid = ratesGrid
							.getRoomClassAvailableValueInRatesGridTab(driver, dateIndex, virtualRoomClassName);
					testSteps.add("Rates Grid Available Rooms after Creating Virtual Room Reservation : "
							+ afterVirtualRoomReservationAvailableRoomsRatesGrid);
					app_logs.info("Rates Grid After Available Rooms after Creating Virtual Room Reservation : "
							+ afterVirtualRoomReservationAvailableRoomsRatesGrid);

					assertEquals(afterVirtualRoomReservationAvailableRoomsRatesGrid,
							afterPhysicalRoomReservationAvailableRoomsRatesGrid,
							"Failed : Available Rooms missmatched");

					app_logs.info(
							"Verified Room Class Available rooms has no effect after Virtual Room Reservation creation");
					testSteps.add(
							"Verified Room Class Available rooms has no effect after Virtual Room Reservation creation");
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
					getTestSteps = ratesGrid.verifyHeadingDates(driver, calendarTodayDate, dateFormat, timeZone,
							getTestSteps);
					testSteps.addAll(getTestSteps);

					dateIndex = ratesGrid.getHeadingDateIndex(driver, calendarTodayDate, dateFormat);
					int roomIndex;
					try {
						roomIndex = ratesGrid.getRoomClassIndex(driver, virtualRoomClassName);
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
						roomIndex = ratesGrid.getRoomClassIndex(driver, virtualRoomClassName);
					}
					ratesGrid.expandRoomClass(driver, roomIndex);
					testSteps.add("Expand Room Class");
					app_logs.info("Expand Room Class");
					afterVirtualRoomReservationReservedCount = ratesGrid.getRoomClassDataValue(driver, dateIndex,
							"Reserved");
					testSteps.add("After Reserved Count : " + afterVirtualRoomReservationReservedCount);
					app_logs.info("After Reserved Count : " + afterVirtualRoomReservationReservedCount);

					assertEquals(afterVirtualRoomReservationReservedCount, afterPhysicalRoomReservationReservedCount,
							"Failed : Reserved count missmatched");

					app_logs.info(
							"Verified Room Class Reserved rooms has no effect after Virtual Room Reservation creation");
					testSteps.add(
							"Verified Room Class Reserved rooms has no effect after Virtual Room Reservation creation");

					afterVirtualRoomReservationAvailableRooms = ratesGrid.getRoomClassDataValue(driver, dateIndex,
							"Available");
					testSteps.add("After Available Rooms : " + afterVirtualRoomReservationAvailableRooms);
					app_logs.info("After Available Rooms : " + afterVirtualRoomReservationAvailableRooms);

					assertEquals(afterVirtualRoomReservationAvailableRooms, afterPhysicalRoomReservationAvailableRooms,
							"Failed : Available Rooms missmatched");

					app_logs.info(
							"Verified Room Class Available rooms has no effect after Virtual Room Reservation creation");
					testSteps.add(
							"Verified Room Class Available rooms has no effect after Virtual Room Reservation creation");
					ratesGrid.expandRoomClass(driver, roomIndex);
					testSteps.add("Reduce Room Class");
					app_logs.info("Reduce Room Class");

					navigation.Inventory_Backward_1(driver);

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
			}
			
			

			// Delete out of order room
			try {
				app_logs.info("========== DELETE ROOM MAINTENANCE ITEM (OUT OF ORDER) ==========");
				testSteps.add("========== DELETE ROOM MAINTENANCE ITEM (OUT OF ORDER)  ==========");
				navigation.Guestservices(driver);
				testSteps.add("Navigate to Guest Service");
				app_logs.info("Navigate to Guest Service");
				navigation.RoomMaintenance(driver);
				testSteps.add("Navigate to Room Maintenance");
				app_logs.info("Navigate to Room Maintenance");
				roomMaintenance.SearchRooms(driver, reason, roomNumber);
				testSteps.add("Successfully Search RoomMain_Item");
				app_logs.info("Successfully Search RoomMain_Item");
				roomMaintenance.DeleteRoomOutOfOrder(driver, subject);
				testSteps.add("Delete room out of order successfully");
				app_logs.info("Delete room out of order successfully");

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
		return Utility.getData("ValidationsOnVirtualRooms1698", excel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		// driver.quit();
	}

}
