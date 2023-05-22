package com.innroad.inncenter.tests;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.OverView;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyGSUpdatesInTapeChart extends TestCore {
	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	RoomClass roomClass = new RoomClass();
	public static String roomClassName;
	public static String roomClassAbbrivation;
	Navigation nav = new Navigation();
	CPReservationPage res = new CPReservationPage();
	RoomMaintenance room_maintenance = new RoomMaintenance();
	RoomStatus roomstatus = new RoomStatus();
	OverView overview = new OverView();
	Tapechart tapechart = new Tapechart();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	RatesGrid rateGrid = new RatesGrid();
	Navigation navigation = new Navigation();
	NightlyRate nightlyRates = new NightlyRate();
	ArrayList<String> roomCost = new ArrayList<String>();
	ArrayList<String> rooms = new ArrayList<String>();
	List<LocalDate> datesList = new ArrayList<LocalDate>();
	ArrayList<String> roomMaintance = new ArrayList<String>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	List<String> date = new ArrayList<String>();
	List<String> day = new ArrayList<String>();
	Rate rate = new Rate();
	String guestFirstName = null, guestLastName = null, reservation = null, testName = null, rateName = null,
			rateDisplayName = null, seasonStartDate = null, seasonEndDate = null;;
	String nights = null, startDate = null, endDate = null;
	ArrayList<String> caseId = new ArrayList<String>();
	ArrayList<String> statusCode = new ArrayList<String>();
	ArrayList<String> comments = new ArrayList<String>();
//	String comments=null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	@Test(dataProvider = "getData")
	public void verifyGSUpdatesInTapeChart(String TestCaseID, String delim, String planType,
			String ratePlanName, String folioDisplayName, String description, String channels,
			String isRatePlanRistrictionReq, String ristrictionType, String seasonName, String isMonDay,
			String isTueDay, String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay,
			String isAdditionalChargesForChildrenAdults, String ratePerNight, String isAddRoomClassInSeason,
			String isSerasonLevelRules, String seasonPolicyType, String seasonPolicyValues, String isSeasonPolicies,
			String maxAdults, String maxPersons, String roomQuantity) {
		if (Utility.isAllDigit(TestCaseID)) {
			caseId.add(TestCaseID);
			statusCode.add("5");
			comments.add("Failed");
		} else {
			String[] testcase = TestCaseID.split("\\|");
			for (int i = 0; i < testcase.length; i++) {
				caseId.add(testcase[i]);
				statusCode.add("5");
			}
		}
		String Subject = "subject"+ Utility.generateRandomStringWithGivenLength(4);
		String detail = "detail"+ Utility.generateRandomStringWithGivenLength(4);
		test_name = "GSRoomMaintenanceVerifyTapeChartAndRoomStatusAndInventory";
		test_description = "GS-Room Maintenance-Making a Room Out Of Order And Verify TapeChart , RoomStatus and Inventory<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682447' target='_blank'>"
				+ "Click here to open TestRail: C682447</a><br>";

		test_catagory = "Verification";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

		ArrayList<String> seasonStartDates = new ArrayList<>();
		ArrayList<String> seasonEndDates = new ArrayList<>();
		// Get checkIN and Checkout Date
		try {
			seasonStartDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
			seasonEndDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(6), "MM/dd/yyyy", "dd/MM/yyyy"));

			seasonStartDate = seasonStartDates.get(0);
			seasonEndDate = seasonEndDates.get(0);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);

			

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get CheckIN CheckOutDate", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}

			driver = getDriver();
			// login_CP(driver);
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to login", "NONGS_Login", "Login", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

// Create Room Class		
		try {
			test_steps.add("=================== CREATE NEW ROOM CLASS ======================");
			app_logs.info("=================== CREATE NEW ROOM CLASS ======================");
			test_steps.add("<b> ************Create Room Class</b>****************");
			nav.navSetup(driver);
			test_steps.add("Navigate to Setup");
			nav.RoomClass(driver);
			test_steps.add("Navigate to RoomClasses tab");
			roomClassName = "TestRoomClass " + Utility.generateRandomString();
			newRoomClass.createRoomClassV2(driver, roomClassName, roomClassName, maxAdults, maxPersons, roomQuantity,
					test, test_steps);

		} catch (Exception e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create room class", "Room Class", "Failed to Create room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			e.printStackTrace();
			if (Utility.reTry.get(test_name) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(test_name, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create room class", "Room Class", "Failed to Create room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Rates and Associate Room Class
		try {
			test_steps.add("<b>======Start Create New Rates and Associate Room Class with Rates======</b>");
			navigation.inventoryV2(driver);
			test_steps.add("Navigate to Inventory");
			app_logs.info("Navigate to Inventory");
			navigation.ratesGrid(driver, test_steps);
			test_steps.add("<b>============Create new Rate Plan============</b>");
			Utility.DELIM = "\\" + delim;
			nightlyRates.createNightlyRatePlan(driver, planType, ratePlanName, folioDisplayName, testStepsOne,
					description, channels, roomClassName, isRatePlanRistrictionReq, ristrictionType, "", "", "", "", "",
					"", "", "", "", seasonName, seasonStartDate, seasonEndDate, isMonDay, isTueDay, isWednesDay,
					isThursDay, isFriday, isSaturDay, isSunDay, isAdditionalChargesForChildrenAdults, ratePerNight, "",
					"", "", "", isAddRoomClassInSeason, "", "", "", "", "", "", isSerasonLevelRules, "", "", "", "", "",
					"", "", "", "", "", "", seasonPolicyType, seasonPolicyValues, isSeasonPolicies);
			Wait.waitUntilPageLoadNotCompleted(driver, 60);
			test_steps.add("Rate Plan Created Successfully : <b>" + Utility.rateplanName + " </b>");
			app_logs.info("Successfull Created Rate: " + Utility.rateplanName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search Rates and Associate Room Class", testName, "Room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search Rates and Associate Room Class", testName, "Room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		try {
			test_steps.add("<b>============Start Creating Room Maintenance============</b>");
			navigation.navigateGuestservicesAfterrateGrid(driver);
			test_steps.add("Navigated to Guestservices");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			nav.RoomMaintenance(driver);
			test_steps.add("Navigated to RoomMaintenance");
			roomMaintance = room_maintenance.createNewRoomOutOfOrder(driver, Subject,
					Utility.RoomNo, roomClassName, test_steps, detail);
			startDate = roomMaintance.get(0);
			endDate = roomMaintance.get(1);
			nights = roomMaintance.get(2);
			test_steps.add("Successfully Created New Out of Order Room");
			app_logs.info("Successfully Created New Out of Order Room");
			datesList = room_maintenance.getDatesAsPerNights(driver, test_steps, startDate, endDate, "MMM dd, yyyy");
			date = room_maintenance.getDateOnlyAsperNights(driver, test_steps, datesList, "MMM dd, yyyy", "dd");
			day = room_maintenance.getDayAsperNights(driver, test_steps, datesList, "MMM dd, yyyy", "E");
	
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create New Out of Order Room", testName, "RoomCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create New Out of Order Room", testName, "RoomCreation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
	}
	@DataProvider
	public Object[][] getData() {
		// return test data from the sheetname provided
		return Utility.getData("VerifyGSUpdatesInTapeChart", envLoginExcel);
	}
	@AfterMethod(alwaysRun = true)
	public void closeDriver() {
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments,
				TestCore.TestRail_AssignToID);
	}

}
