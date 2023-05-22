package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightAudit;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.properties.OR;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyRoomStatusOnCheckInOut extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_nameOne = "";
	public static String test_description = "";
	public static String test_catagory = "";
	public static String roomClassName;
	public static String roomClassAbbrivation;
	String reservation = null, houseKeepingNoBeforeCheckIN = null, houseKeepingNoAfterCheckIN = null,
			houseKeepingNoAfterCheckOut = null, checkInDate = null, checkOutDate = null, guestFirstName = null,
			guestLastName = null, status = null, yearDate = null, rateName = null, rateDisplayName = null,seasonStartDate=null,seasonEndDate=null;
	boolean click = true;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation nav = new Navigation();
	RoomClass roomClass = new RoomClass();
	Rate rate = new Rate();
	NewRoomClassesV2 newRoomclass = new NewRoomClassesV2();
	TaskManagement task_mang = new TaskManagement();
	CPReservationPage res = new CPReservationPage();
	RoomStatus roomstatus = new RoomStatus();
	NightAudit naudit = new NightAudit();
	ReservationSearch revSearch = new ReservationSearch();
	String loading = "(//div[@class='ir-loader-in'])";
	NightlyRate nightlyRate= new NightlyRate();
	ArrayList<String> comments = new ArrayList<>();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "Reservation")
	public void verifyRoomStatusOnCheckInOut(String RoomClass,String MaxAdults, String MaxPersopns, String RoomQuantity,
			String ratePlanName,String rates, String CheckInDate,String CheckOutDate, 
			String Salutation,String GuestFirstName,String GuestLastName, String PaymentType, String CardNumber,
			String NameOnCard, String MarketSegment, String Referral,String children, String DirtyStatus, 
			String CleanStatus,String VacantStatus, String OccupiedStatus,String WithCheckINCheckOutToggle) throws ParseException {
		String testCaseID="850178|848211|848177|848210";
		if(Utility.getResultForCase(driver, testCaseID)) {
		Utility.initializeTestCase("850178|848211|848177|848210", Utility.testId, Utility.statusCode, Utility.comments, "");
		String testName = "";
		if (WithCheckINCheckOutToggle.equalsIgnoreCase("Yes")) {
			test_name = "VerifyRoomStatusOnCheck-In/Check-OutIfToggleEnabled ";
			test_description = "Verify RoomStatus On Enabled Toggle -Set Rooms To Dirty On Check-In/Check-Out<br>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848211' target='_blank'>"
					+ "Click here to open TestRail: 848211</a>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850178' target='_blank'>"
					+ "Click here to open TestRail: 850178</a>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848211' target='_blank'>"
					+ "Click here to open TestRail: 848211</a>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848177' target='_blank'>"
					+ "Click here to open TestRail: 848177</a>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848210' target='_blank'>"
					+ "Click here to open TestRail: 848210</a>";
			test_catagory = "CPReservation_CheckIN";
			testName = test_name;
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");
			test_steps.add(
					"**Verify Night Audit <b> HouseKeeping No </b> After Enable Toggle <b>Set Rooms To Dirty On Check-In/Check-Out</b>**");
		} else if (WithCheckINCheckOutToggle.equalsIgnoreCase("No")) {
			test_name = "VerifyRoomStatusOnCheck-In/Check-OutIfToggleDisabled";
			test_description = "Verify RoomStatus On Disabled Toggle -Set Rooms To Dirty On Check-In/Check-Out<br>"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682451' target='_blank'>"
					+ "Click here to open TestRail: C682451</a>";
			test_catagory = "CPReservation_CheckIN";
			testName = test_name;
			app_logs.info("##################################################################################");
			app_logs.info("EXECUTING: " + testName + " TEST.");
			app_logs.info("##################################################################################");
			test_steps.add(
					"**Verify Night Audit <b> HouseKeeping No </b>After Disable Toggle <b>Set Rooms To Dirty On Check-In/Check-Out</b>**");

		}
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> roomNos= new ArrayList<String>();
		// Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			driver = getDriver();
			login_CP(driver);
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

		// Get checkIN and Checkout Date
		try {
			if (!(Utility.validateInput(CheckInDate))) {
				for (int i = 0; i < GuestFirstName.toString().split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(CheckInDate.toString());
				checkOutDates = Utility.splitInputData(CheckOutDate.toString());
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			seasonStartDate=checkInDates.get(0);
			seasonEndDate=sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			datesRangeList = Utility.getAllDatesStartAndEndDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);

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
		// Create Room Class
		try {
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			app_logs.info("<b>======Start Creating New Room Class======</b>");
			roomClassName = RoomClass + Utility.generateRandomNumber();
			roomClassAbbrivation = RoomClass+ Utility.generateRandomNumber();
			test_steps.add("<b>****Start Creating New Room Class****</b>");
			nav.Setup(driver);
			test_steps.add("Navigated to Setup");
			nav.RoomClass(driver);
			test_steps.add("Navigated to Room Class");
			newRoomclass.deleteAllRoomClassV2(driver, RoomClass);
			roomNos=newRoomclass.createRoomClassV2(driver, test_steps, roomClassName, roomClassAbbrivation,MaxAdults, MaxPersopns,RoomQuantity);
			test_steps.add(" Room Class Created: <b>" + roomClassName + "</b>");
			app_logs.info(" Room Class Created: " + roomClassName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Room Class", testName, "Room class", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Room Class", testName, "Room class", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Rates and Associate Room Class
		try {
			test_steps.add("<b>======Start Updating Rate Plan ======</b>");
			app_logs.info("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, ratePlanName,datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassName, rates, "", 
					"", "", "", true);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Rates and Associate Room Class", testName, "Room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create Rates and Associate Room Class", testName, "Room class",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Enable Toggle Set Rooms To Dirty On Check-In/Check-Out
		try {
			test_steps.add("<b>****Set Rooms To Dirty On Check-In/Check-Out****</b>");
			nav.navigateToSetupfromRateGrid(driver);
			test_steps.add("Navigated to Setup");
			nav.TaskManagement_TabExist(driver);
			test_steps.add("Task Management Tab Exist");
			nav.TaskManagement(driver);
			test_steps.add("Click on Task Management");
			app_logs.info("Click on Task Management");
			task_mang.getToggleStatus_CheckInCheckOut(driver);
			if (WithCheckINCheckOutToggle.equalsIgnoreCase("Yes")) {
				if (Utility.toggle == true) {
					test_steps.add("Set Rooms To Dirty On Check-In/Check-Out Toggle Already Enabled");
					app_logs.info("Set Rooms To Dirty On Check-In/Check-Out Toggle Already Enabled");

				} else if (Utility.toggle == false) {
					task_mang.SetRoomsToDirtyFlag(driver, true);
					test_steps.add("Successfully Enabled Toggle Set Rooms To Dirty On Check-In/Check-Out ");
					app_logs.info("Successfully Enabled Toggle Set Rooms To Dirty On Check-In/Check-Out");
				}
		
   			} else if (WithCheckINCheckOutToggle.equalsIgnoreCase("No")) {
				if (Utility.toggle == true) {
					task_mang.SetRoomsToDirtyFlag(driver, false);
					test_steps.add("Successfully Disabled Toggle Set Rooms To Dirty On Check-In/Check-Out ");
					app_logs.info("Successfully Disabled Toggle Set Rooms To Dirty On Check-In/Check-Out");

				} else if (Utility.toggle == false) {
					test_steps.add("Set Rooms To Dirty On Check-In/Check-Out Toggle Already Disabled");
					app_logs.info("Set Rooms To Dirty On Check-In/Check-Out Toggle Already Disabled");

				}
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Enabled Toggle Set Rooms To Dirty On Check-In/Check-Out", testName,
						"Task Management", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Enabled Toggle Set Rooms To Dirty On Check-In/Check-Out", testName,
						"Task Management", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Start Creating Reservation

		try {
			guestFirstName = GuestFirstName + Utility.threeDigitgenerateRandomString();
			guestLastName = GuestLastName + Utility.threeDigitgenerateRandomString();
			app_logs.info(guestFirstName + guestLastName);
			reservation = res.createBasicReservation(driver, checkInDate, checkOutDate, MaxAdults, children,
					ratePlanName, Salutation, guestFirstName, guestLastName, "No", PaymentType, CardNumber,
					NameOnCard, MarketSegment, Referral, roomClassName, true, true);
			test_steps.add("Reservation Created: <b>" + reservation + "</>");
			app_logs.info("Reservation Created: " + reservation);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create New reservation and Check In", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to  Create New reservation and Check In ", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Get Night Audit HouseKeeping Number
		try {
			test_steps.add("<b>****Get Night Audit HouseKeeping Number****</b>");
			nav.Reports(driver);
			nav.NightAuditIcon(driver);
			test_steps.add("Navigated to Night Audit");
			naudit.EnterAuditDate(driver, checkInDate);
			test_steps.add("Click Audit Date Picker");
			app_logs.info("Click Audit Date Picker");
			naudit.GoButtonClick(driver);
			test_steps.add("Click Go Button");
			app_logs.info("Click Go Button");
			Wait.explicit_wait_xpath(OR.Period_status, driver);
			naudit.Get_NightAuditDate(driver, test_steps);
			houseKeepingNoBeforeCheckIN = null;
			houseKeepingNoBeforeCheckIN = naudit.GetHouseKeepingCount(driver);
			System.out.println("Night Audit House Keeping No Is: " + houseKeepingNoBeforeCheckIN);
			test_steps.add("Night Audit House Keeping No Is: <b>" + houseKeepingNoBeforeCheckIN + " </b>");
			app_logs.info("Night Audit House Keeping No Is: " + houseKeepingNoBeforeCheckIN);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get Night Audit HouseKeeping Number", testName, "Night Audit",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get Night Audit HouseKeeping Number", testName, "Night Audit",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Check IN
		try {
			nav.Reservation_Backward(driver);
			test_steps.add("<b>****Search Reservation****</b>");
			revSearch.basicSearch_WithResNumber(driver, reservation);
			test_steps.add("Search Reservation By Reservation Number <b>" + reservation + "<b>");
			app_logs.info("Search Reservation By Reservation Number " + reservation);
			test_steps.add("<b>****Start CheckIn Reservation****</b>");
			res.clickCheckInButton(driver, test_steps);
			res.generatGuestReportToggle(driver, test_steps, "No");
			List<String> Rooms = new ArrayList<String>();
			Rooms.add(Utility.RoomNo);
			res.completeCheckInProcessSingleRev(driver, test_steps);
			Wait.wait10Second();
			res.verifyCheckOutButton(driver, test_steps);
			res.closeReservationTab(driver);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Create New reservation and Check In", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to  Create New reservation and Check In ", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Verified Room Status After Check-IN
		try {
			test_steps.add("<b>****Start Verifying Room Status ON GS After Check-IN****</b>");
			nav.navigateGuestservice(driver);
			test_steps.add("Navigated to Guestservices");
			app_logs.info("Navigated to Guestservices");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			roomstatus.searchByRoomHashVerifyRoomStatus(driver, Utility.RoomNo, roomClassName,
					DirtyStatus, CleanStatus,VacantStatus, OccupiedStatus, test_steps);		
			
			test_steps.add("Set Rooms To Dirty On Check-In/Check-Out Toggle Enabled"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848177' target='_blank'>"
					+ "Click here to open TestRail: 848177</a><br>");

			Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Set Up--Verify the Set rooms to dirty on check-in/check-out");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
			
			comments.add("Set Rooms To Dirty On Check-In/Check-Out Toggle Enabled");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verified Room Status After Check-IN	 ", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to  Verified Room Status After Check-IN	 ", testName, "Reservation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verified Night Audit HouseKeeping Number
		try {
			test_steps.add("<b>****Verify Night Audit HouseKeeping Number After Check-IN****</b>");
			nav.Reports(driver);
			nav.NightAuditIcon(driver);
			test_steps.add("Navigated to Night Audit");
			naudit.EnterAuditDate(driver, checkInDate);
			test_steps.add("Click Audit Date Picker");
			app_logs.info("Click Audit Date Picker");
			naudit.GoButtonClick(driver);
			test_steps.add("Click Go Button");
			app_logs.info("Click Go Button");
			Wait.explicit_wait_xpath(OR.Period_status, driver);
			naudit.Get_NightAuditDate(driver, test_steps);
			houseKeepingNoAfterCheckIN = naudit.GetHouseKeepingCount(driver);
			System.out.println("After CheckIn HouseKeeping No Is: " + houseKeepingNoAfterCheckIN);
			test_steps.add(
					"Night Audit House Keeping No Is Before Check In: <b>" + houseKeepingNoBeforeCheckIN + " </b>");
			app_logs.info("Night Audit House Keeping No Is Before Check In: " + houseKeepingNoBeforeCheckIN);
			naudit.Verified_HouseKeeping_No_Increase(driver, test_steps, Integer.parseInt(houseKeepingNoBeforeCheckIN),
					roomstatus.OccupiedNo, houseKeepingNoAfterCheckIN);
			
			test_steps.add("Verify housekeeping count"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848211' target='_blank'>"
					+ "Click here to open TestRail: 848211</a><br>");
		
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify housekeeping count");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			
			test_steps.add("Verify housekeeping"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848210' target='_blank'>"
					+ "Click here to open TestRail: 848210</a><br>");
			Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Verify housekeeping");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);
			
			comments.add("Verify housekeeping count");
			comments.add("Verify housekeeping ");
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verified Night Audit HouseKeeping Number  ", testName, "Night Audit",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to  Verified Night Audit HouseKeeping Number  ", testName,
						"Night Audit", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Change Room Status
		try
		{
			test_steps.add("<b>****Start Update Room Status ON GS After Check-IN****</b>");
			nav.Guestservices_1(driver);
			Wait.waitforPageLoad(50, driver);
			test_steps.add("Navigated to Guestservices");
			app_logs.info("Navigated to Guestservices");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			if (WithCheckINCheckOutToggle.equalsIgnoreCase("Yes")) {
				app_logs.info("Yes");
				roomstatus.searchByRoomHashAndChangeRoomStatus(driver, Utility.RoomNo, roomClassName,
						CleanStatus, test_steps);
			} else if (WithCheckINCheckOutToggle.equalsIgnoreCase("No")) {
				app_logs.info("No");
				roomstatus.searchByRoomHashAndChangeRoomStatus(driver, Utility.RoomNo, roomClassName,
						DirtyStatus, test_steps);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verified Night Audit HouseKeeping Number  ", testName, "Night Audit",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to  Verified Night Audit HouseKeeping Number  ", testName,
						"Night Audit", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// CheckOut Reservation
		try {

			nav.navigateToReservationFromGuestServices(driver, test_steps);
			app_logs.info("Navigate to Reservation");
			revSearch.basicSearch_WithResNumber(driver, reservation);
			test_steps.add("<b>****Start CheckOut Reservation****</b>");
			res.clickCheckOutButton(driver, test_steps);
			res.generatGuestReportToggle(driver, test_steps,"No");
			boolean isPaymentWindow = res.getPaymentWindow(driver);
			if (isPaymentWindow) {
				res.proceedToCheckOutPayment(driver, test_steps);
				res.verifySpinerLoading(driver);
				res.clickLogORPayAuthorizedButton(driver, test_steps);
				res.clickCloseButtonOfCheckoutSuccessfully(driver, test_steps);
			}
			test_steps.add("<b>****Start Verifying Roll Back Button****</b>");
			res.verifyRollBackButton(driver, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to CheckOut  Reservation", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to CheckOut  Reservation ", testName, "Reservation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verified Room Status After Check-Out
		try {
			test_steps.add("<b>****Start Verifying Room Status ON GS After Check-Out****</b>");
			nav.navigateGuestservice(driver);
			test_steps.add("Navigated to Guestservices");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			roomstatus.searchByRoomHashVerifyRoomStatus(driver, Utility.RoomNo, roomClassName,
					DirtyStatus, CleanStatus,VacantStatus, OccupiedStatus, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verified Room Status After Check-Out	 ", testName, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to  Verified Room Status After Check-Out	 ", testName, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verified Night Audit HouseKeeping Number
		try {
			test_steps.add("<b>****Verify Night Audit HouseKeeping Number After Check-Out****</b>");
			nav.Reports(driver);
			nav.NightAuditIcon(driver);
			test_steps.add("Navigated to Night Audit");
			naudit.EnterAuditDate(driver, checkInDate);
			test_steps.add("Click Audit Date Picker");
			app_logs.info("Click Audit Date Picker");
			naudit.GoButtonClick(driver);
			test_steps.add("Click Go Button");
			app_logs.info("Click Go Button");
			Wait.explicit_wait_xpath(OR.Period_status, driver);
			naudit.Get_NightAuditDate(driver, test_steps);
			houseKeepingNoAfterCheckOut = null;
			houseKeepingNoAfterCheckOut = naudit.GetHouseKeepingCount(driver);
			System.out.println("After Check-Out HouseKeeping No Is: " + houseKeepingNoAfterCheckOut);
			test_steps.add(
					"Night Audit House Keeping No Is Before Check-Out: <b>" + houseKeepingNoAfterCheckIN + " </b>");
			app_logs.info("Night Audit House Keeping No Is Before Check-Out: " + houseKeepingNoAfterCheckIN);
			naudit.Verified_HouseKeeping_No_Decrease(driver, test_steps, Integer.parseInt(houseKeepingNoAfterCheckIN),
					roomstatus.VacantNo, houseKeepingNoAfterCheckOut);

			 test_steps.add("Verify House Keeping Status count in Night Audit after Check-In/Check-out" + 
				        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/850178' target='_blank'>"
								+ "Click here to open TestRail: 850178</a>");    		 
			 Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify House Keeping Status count in Night Audit after Check-In/Check-out");
			 Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
			comments.add("Verify House Keeping Status count in Night Audit after Check-In/Check-out");
			 for(int i=0;i<Utility.testId.size();i++) {
				 Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify House Keeping Status count in Night Audit after Check-In/Check-out");
			 }
			
			} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verified Night Audit HouseKeeping Number  ", testName, "Night Audit",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to  Verified Night Audit HouseKeeping Number  ", testName,
						"Night Audit", driver);
			} else {
				Assert.assertTrue(false);
			}
		}	
		// Delete Room Class
		try {
			test_steps.add("<b>****Delete Room Class****</b>");
			nav.navigateToSetupfromRoomMaintenance(driver);
			test_steps.add("Navigated to Setup");
			nav.RoomClass(driver);
			newRoomclass.searchRoomClassV2(driver, roomClassName);
			test_steps.add("Click on Search Button");
			app_logs.info("Click on Search Button");
			newRoomclass.deleteAllRoomClassV2(driver, roomClassName);
			test_steps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassName + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassName);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Delete Room Class ", testName, "Night Audit", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Delete Room Class ", testName, "Night Audit", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		}
	}
	@DataProvider
	public Object[][] getData() {
		return Utility.getData("VerifyRoomStatusOnCheckInOut", gsexcel);
	}
	
	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);

	}
}
