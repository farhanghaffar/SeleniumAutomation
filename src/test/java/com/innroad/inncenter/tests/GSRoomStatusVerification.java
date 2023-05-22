package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.util.Strings;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.Rate;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;
import com.innroad.inncenter.webelements.WebElements_RoomStatus;

public class GSRoomStatusVerification extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> testStepsOne = new ArrayList<>();
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	ArrayList<String> sessionEndDate = new ArrayList<>();
	ArrayList<String> roomNumbers = new ArrayList<>();
	ArrayList<String> roomMaintance = new ArrayList<String>();
	RoomClass roomClass = new RoomClass();
	public static String roomClassNames;
	public static String roomClassAbbrivations;
	Navigation navigation = new Navigation();
	CPReservationPage reservation = new CPReservationPage();
	ReservationSearch reservationSearch = new ReservationSearch();
	TaskList tasklist = new TaskList();
	RoomMaintenance room_maintenance = new RoomMaintenance();
	RatesGrid rateGrid= new RatesGrid();
	TaskManagement taskmang = new TaskManagement();
	NewRoomClassesV2 newRoomClass= new NewRoomClassesV2();
	NightlyRate nightlyRates = new NightlyRate();
	RoomStatus roomstatus = new RoomStatus();
	String testName = null,   zoneName = null, yearDate = null,
			guestFirstOne = null, guestSecondOne = null, reservationNo = null,seasonStartDate=null,seasonEndDate=null;
	Rate rate = new Rate();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {		
		String testName = this.getClass().getSimpleName().trim();
		Utility.ReportsName=testName;
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	private void createReservation(String checkInDate, String checkOutDate, String adults, String children,
			String ratePlanName, String salutation, String guestFirstName, String guestLastName, String isGuesProfile,
			String paymentType, String cardNumber, String nameOnCard, String marketSegment, String referral, String roomClassNames)
			throws InterruptedException, ParseException {
		navigation.Reservation_Backward_2(driver);
		reservation.click_NewReservation(driver, test_steps);
		reservation.select_CheckInDate(driver, test_steps, checkInDate);
		reservation.select_CheckoutDate(driver, test_steps, checkOutDate);
		reservation.enter_Adults(driver, test_steps, adults);
		reservation.enter_Children(driver, test_steps, children);
		reservation.select_Rateplan(driver, test_steps, ratePlanName, "");
		reservation.clickOnFindRooms(driver, test_steps);
		ArrayList<String> rooms = reservation.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassNames);
		reservation.selectRoomToReserve(driver, test_steps, roomClassNames, rooms.get(0));
		reservation.clickNext(driver, test_steps);
		yearDate = Utility.getFutureMonthAndYearForMasterCard();
		Random random = new Random();
		int x = random.nextInt(900);
		guestFirstOne = guestFirstName + x;
		guestSecondOne = guestLastName.toString() + x;
		reservation.enter_MailingAddress_OnUnchecked_CreateGuestProfile(driver, test_steps, salutation, guestFirstOne,
				guestSecondOne, isGuesProfile);
		reservation.enter_PaymentDetails(driver, test_steps, paymentType, cardNumber, nameOnCard, yearDate);
		reservation.enter_MarketSegmentDetails(driver, test_steps, "", marketSegment, referral);
		reservation.clickBookNow(driver, test_steps);
	}

	private void checkinAndSearchByOccupied(String notes) throws Exception {
		reservation.clickCheckInButton(driver, test_steps);
		reservation.generatGuestReportToggle(driver, test_steps, "No");
		reservation.completeCheckInProcess(driver, test_steps);
		boolean isPaymentWindow = reservation.getPaymentWindow(driver);
		if (isPaymentWindow) {
			reservation.addNotesAndClickLogORPayORAuthorizedButton(driver, test_steps, notes);
			reservation.clickCloseButtonOfSuccessModelPopupAfterCheckIn(driver, test_steps);
		}
		reservation.verifyCheckOutButton(driver, test_steps);
		test_steps.add("<b>======Start Searching By Occuiped======</b>");
		navigation.navigateGuestservice(driver);
		test_steps.add("Navigate to Guest Services");
		app_logs.info("Navigate to Guest Services");
		roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
		roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
		roomstatus.searchByVacantOccupied(driver, "Occupied", test_steps);
		test_steps.add("Successfull Searched By Occuiped: <b>" + "Occupied" + "</b>");
		app_logs.info("Successfull Search By Occupied");
	}

	@Test(dataProvider = "getFinalData", groups = "GuestServices")
	public void gSRoomStatusVerification(String roomClassName, String roomClassAbbrivation, String maxAdults, String maxPersopns,
			String roomQuantity, String checkInDate, String checkOutDate, String adults, String children,
			String salutation, String guestFirstName, String guestLastName, 
			String paymentType, String cardNumber, String nameOnCard, String marketSegment, String referral,
			String notes, String taskName, String category, String categoryType, String details, String remarks,
			String assignee, String subject,String delim,String planType,String ratePlanName,String folioDisplayName, String description, String channels, 
			String isRatePlanRistrictionReq,String ristrictionType, String seasonName, 
			String isMonDay, String isTueDay,String isWednesDay, String isThursDay, String isFriday, String isSaturDay, String isSunDay, 
			String isAdditionalChargesForChildrenAdults,String ratePerNight,
			String isAddRoomClassInSeason, String isSerasonLevelRules, String seasonPolicyType, String seasonPolicyValues,String isSeasonPolicies)
			throws InterruptedException, ParseException {
		String testCaseID="852347|850169|850170|850173|850177|850179|850183|850168|850180";
		if(Utility.getResultForCase(driver, testCaseID)) {
		Utility.initializeTestCase("852347|850169|850170|850173|850177|850179|850183|850168|850180", Utility.testId, Utility.statusCode,Utility.comments,"");
		test_name = "GSRoomStatusVerification";
		test_description = "GSRoomStatusVerification<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/852347' target='_blank'>"
				+ "Click here to open TestRail: 852347</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850169' target='_blank'>"
				+ "Click here to open TestRail: 850169</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850170' target='_blank'>"
				+ "Click here to open TestRail: 850170</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850173' target='_blank'>"
				+ "Click here to open TestRail: 850173</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850177' target='_blank'>"
				+ "Click here to open TestRail: 850177</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850183' target='_blank'>"
				+ "Click here to open TestRail: 850183</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850168' target='_blank'>"
				+ "Click here to open TestRail: 850168</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/850180' target='_blank'>"
				+ "Click here to open TestRail: 850180</a><br>";
		test_catagory = "Verification";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String testCase=null;
		
		// Get checkIN and Checkout Date
		try {
			if (!(Utility.validateInput(checkInDate))) {
				for (int i = 0; i < guestFirstName.split("\\|").length; i++) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
					sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(6), "MM/dd/yyyy", "dd/MM/yyyy"));
				}
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			seasonStartDate=checkInDate;
			seasonEndDate=sessionEndDate.get(0);

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
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}					
			driver = getDriver();
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

		// Enable toggle for Inspection
		try {
			test_steps.add("<b>======Enabling Toggle for Inspection======</b>");
			navigation.Setup(driver);
			test_steps.add("Navigated to Setup");
			navigation.TaskManagement_TabExist(driver);
			test_steps.add("Task Management Tab Exist");
			navigation.TaskManagement(driver);
			test_steps.add("Click on Task Management");
			app_logs.info("Click on Task Management");
			taskmang.setInspectionCleaningToggle(driver, true);
			test_steps.add("Successfully Enabled Toggle for Inspection ");
			app_logs.info("Successfully Enabled Toggle for Inspection");
			taskmang.setInspectionCleaningToggleFlagStatus(driver);
			test_steps.add("Successfully Set Toggle Condition Value ");
			app_logs.info("Successfully Set Toggle Condition Value");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Enable toggle for Inspection", "Task Management", "RoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Enable toggle for Inspection", "Task Management", "RoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create Room Class
		try {
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			navigation.navigateToSetupfromTaskManagement(driver);
			navigation.RoomClass(driver);
			test_steps.add("Navigated to Room Class");
			roomClassNames = roomClassName + Utility.getTimeStamp();
			roomClassAbbrivations = roomClassAbbrivation + Utility.getTimeStamp();
			newRoomClass.createRoomClassV2(driver, test_steps, roomClassNames, roomClassAbbrivations, maxAdults, maxPersopns, roomQuantity);			
			 Utility.RoomClassName=roomClassNames;
			 Utility.RoomClassabv=roomClassAbbrivations;
			 app_logs.info(Utility.RoomClassName);
			 app_logs.info(Utility.RoomClassabv);
			test_steps.add("Room Class Created: <b>" + Utility.RoomClassName + "</>");
			app_logs.info("Room Class Created: " + Utility.RoomClassabv);
        
			String quantity = roomQuantity;
			String roomNumber = Utility.RoomNo;

			roomNumbers.add(roomNumber);
			for (int i = 1; i < Integer.parseInt(quantity); i++) {
				roomNumber = String.valueOf(Integer.parseInt(roomNumber) + 1);
				roomNumbers.add(roomNumber);
			}
			app_logs.info("Room No Are: " + roomNumbers);

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

		// Get Zone
		try {
			test_steps.add("<b>======Getting Zone======</b>");
			zoneName=newRoomClass.getRoomClassZone(driver);
			test_steps.add("Zone : <b>" + zoneName + "</b>");
			app_logs.info("Zone: " + zoneName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get Zone", testName, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Get Zone", testName, "GS", driver);
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
			navigation.ratesGrid(driver,test_steps);	
			test_steps.add("<b>============Create new Rate Plan============</b>");				
			Utility.DELIM = "\\"+delim;
			nightlyRates.createBasicNightlyRatePlan(driver, planType, ratePlanName, folioDisplayName, testStepsOne, description, channels, 
					roomClassNames, isRatePlanRistrictionReq, ristrictionType, "", 
					"", "", "","","","","","", seasonName,seasonStartDate,seasonEndDate ,isMonDay, isTueDay, isWednesDay, isThursDay, isFriday, isSaturDay, isSunDay, 
					isAdditionalChargesForChildrenAdults,ratePerNight, "", "","","", isAddRoomClassInSeason, "", "",
					"", "", "","", isSerasonLevelRules,"", "", 
					"", "", "","","","", "", "","",seasonPolicyType, seasonPolicyValues,isSeasonPolicies);
			Wait.waitUntilPageLoadNotCompleted(driver, 60);
			test_steps.add("Rate Plan Created Successfully : <b>"+Utility.rateplanName+" </b>");
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

	

		// Search By Room #
		try {
			
			testCase="GS Search By Room # and Verify Room #";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			test_steps.add("<b>======Search By Room # and Verify Room #======</b>");
			navigation.navigateGuestservicesAfterrateGrid(driver);
			test_steps.add("Navigated to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);			
			/*test_steps.add("Verify Housekeeping Status page for new GS Screens"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850179' target='_blank'>"
					+ "Click here to open TestRail: 850179</a><br>");
			Utility.testCasePass(Utility.statusCode, 5, Utility.comments, "Verify Housekeeping Status page for new GS Screens");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
			*/roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			roomstatus.searchByRoomHash(driver, Utility.RoomNo, roomClassNames, test_steps);
			test_steps.add("Successfull Searched By Room#: <b>" + Utility.RoomNo + "</b>");
			app_logs.info("Successfull Search By Room# " + Utility.RoomNo);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Room #", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Room #", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Search By Room Class
		try {
			
			testCase="GS Search By Searching By RoomClass and Verify Room Class";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			test_steps.add("<b>======Start Searching By RoomClass and Verify Room Class======</b>");
			roomstatus.searchByRoom(driver, roomClassNames, test_steps);
			test_steps.add("Successfull Searched By RoomClassName: <b>" + roomClassNames + "</b>");
			app_logs.info("Successfull Search By RoomClassName: " + roomClassNames);
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Room #", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Room #", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Search By Zone
		try {
			
			testCase="GS Search By Searching By Zone and Verify Zone";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			test_steps.add("<b>======Start Searching By Zone and Verify Zone======</b>");
			roomstatus.searchByZone(driver, zoneName, test_steps);
			test_steps.add("Successfull Searched By Zone: <b>" + zoneName + "</b>");
			app_logs.info("Successfull Search By Zone");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Zone", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Zone", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Search By Vacant
		try {
			
			testCase="GS Search By  Vacant and Verify Vacant";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			test_steps.add("<b>======Start Searching By Vacant and Verify Vacant======</b>");
			roomstatus.searchByVacantOccupied(driver, "Vacant", test_steps);
			test_steps.add("Successfull Searched By  <b>" + "Vacant" + "</b>");
			app_logs.info("Successfull Search By Vacant");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Vacant", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Vacant", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Search By Arrival Due
		try {
			
			testCase="GS Search By  Search By Arrival Due and Verify";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			test_steps.add("<b>======Search By Arrival Due======</b>");
			boolean isExist = roomstatus.verifiedByArrivalDue(driver, "Arrival Due", test_steps);
			if (!isExist) {
				test_steps.add("<b>======Arrival Due Doesn't Exist======</b>");
				test_steps.add("<b>======Start Creating Reservation======</b>");
				createReservation(checkInDate, checkOutDate, adults, children, Utility.rateplanName, salutation, guestFirstName,
						guestLastName, config.getProperty("flagOff"), paymentType, cardNumber, nameOnCard, marketSegment, referral,Utility.RoomClassName);
				reservationNo = reservation.get_ReservationConfirmationNumber(driver, test_steps);
				reservation.clickCloseReservationSavePopup(driver, test_steps);
				test_steps.add("<b>======Start Searching By Arrival Due======</b>");
				reservation.closeReservationTab(driver);
				navigation.navigateGuestservice(driver);
				test_steps.add("Navigate to Guest Services");
				app_logs.info("Navigate to Guest Services");
				roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
				roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
				roomstatus.searchByArrivalDue(driver, "Arrival Due", test_steps);
				test_steps.add("Successfull Searched By Arrival Due: <b>" + "Arrival Due" + "</b>");
				app_logs.info("Successfull Search By Arrival Due");
				/*test_steps.add("Search Room Stats for Arrival Due and All"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850173' target='_blank'>"
						+ "Click here to open TestRail: 850173</a><br>");
				
				Utility.testCasePass(Utility.statusCode, 3, Utility.comments, "Search Room Stats for Arrival Due and All");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);
				test_steps.add("Verify Arrival Due label on the Room Status tile ->Reservation"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850180' target='_blank'>"
						+ "Click here to open TestRail: 850180</a><br>");
				
				Utility.testCasePass(Utility.statusCode, 8, Utility.comments, "Verify Arrival Due label on the Room Status tile ->Reservation");
				Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(8), Utility.statusCode.get(8), Utility.comments.get(8), TestCore.TestRail_AssignToID);
		*/
				
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
			}
		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Arrival Due", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Arrival Due", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Search By Occupied
		try {
			
			testCase="GS Search By  Search By Occupied and Verify";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			test_steps.add("<b>======Search By Occupied======</b>");
			boolean isExist = roomstatus.verifyByOccupied(driver, "Occupied", test_steps);
			boolean isNUll = Strings.isNullOrEmpty(reservationNo);
			app_logs.info("isRoomStatus Value: " + isExist);
			app_logs.info("isNull Value: " + isNUll);
			if ((!isExist && !isNUll)) {
				test_steps.add("<b>======Occupied Doesn't Exist======</b>");
				test_steps.add("<b>======Start Searching Created Reservation======</b>");
				navigation.Reservation_Backward_2(driver);
				reservationSearch.basicSearchWithResNumber(driver, reservationNo, test_steps);
				checkinAndSearchByOccupied(notes);
			} else if (!isExist && isNUll) {
				test_steps.add("<b>======Occupied Doesn't Exist======</b>");
				test_steps.add("<b>======Start Creating Reservation======</b>");
				createReservation(checkInDate, checkOutDate, adults, children, Utility.rateplanName, salutation, guestFirstName,
						guestLastName, config.getProperty("flagOff"), paymentType, cardNumber, nameOnCard, marketSegment, referral,Utility.RoomClassName);
				reservation.clickCloseReservationSavePopup(driver, test_steps);
				checkinAndSearchByOccupied(notes);
			}
			
			/*test_steps.add("Verify that the status (Occupancy) of the room should get changed after the reservation is CHECKIN."
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850170' target='_blank'>"
					+ "Click here to open TestRail: 850170</a><br>");
			
    		Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "Verify that the status (Occupancy) of the room should get changed after the reservation is CHECKIN.");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
*/
				
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Occupied", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Search By Occupied", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Verified Dirty Stat
		try {
			testCase="GS Verifying Dirty Stats";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			
			navigation.Reservation_Backward_2(driver);
			navigation.navigateGuestservice(driver);
			test_steps.add("Navigate to Guest Services");
			app_logs.info("Navigate to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			test_steps.add("<b>======Start Verifying Dirty Stats======</b>");
			roomstatus.clickQuickStatsAndVerifyColor(driver, "Dirty", test_steps);
			WebElements_RoomStatus roomStatusElement = new WebElements_RoomStatus(driver);
			roomstatus.verifyTotalRoomCard(driver, test_steps, "Dirty",
					roomStatusElement.RoomStatus_DirtyStatTotalRoomCardStatus,
					roomStatusElement.RoomStatus_DirtyStatSize);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Dirty quick stat", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Dirty quick stat", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Verified All Stat
		try {
			
			testCase="GS Verifying All Stats";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			
			test_steps.add("<b>======Start Verifying All Stats======</b>");
			roomstatus.verifyAllStat(driver, test_steps);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify All quick stat", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify All quick stat", testCase, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verified Inspection Quick Stat
		try {
			
			testCase="GS Verifying Inspection Stats";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			
			boolean isExist = roomstatus.getQuickStatSize(driver, "Inspection");
			if (!isExist) {
				test_steps.add(
						"<b>======Inspection Count is Zero Creating New Task and Set Task Status as Inspection======</b>");
				navigation.Task_List(driver);
				test_steps.add("Navigate to Task List Tab");
				app_logs.info("Navigate to Task List Tab");
				tasklist.Click_AddTask(driver);
				test_steps.add("Click on Add Task Button");
				app_logs.info("Click on Add Task Button");
				app_logs.info("Room NO:" + Utility.RoomNo);
				tasklist.createNewTaskWithRoomOfThatRoomClass(driver, taskName, category, categoryType, details,
						remarks, assignee, Utility.RoomNo, Utility.RoomClassName);
				test_steps.add("Add Task :<b>" + taskName + "</b>");
				app_logs.info("Add Task: " + taskName);
				tasklist.searchTaskByRoomNo(driver, Utility.RoomNo);
				test_steps.add("Search task By Room No : <b>" + Utility.RoomNo + "</b>");
				app_logs.info("Search task By Room No :" + Utility.RoomNo);
				tasklist.changeTaskStatus(driver, "To Do", "Inspection", test_steps);
				navigation.RoomStatus(driver);
			}
			test_steps.add("<b>======Start Verifying Inspection Stats======</b>");
			roomstatus.clickQuickStatsAndVerifyColor(driver, "Inspection", test_steps);
			WebElements_RoomStatus roomStatusElement = new WebElements_RoomStatus(driver);
			roomstatus.verifyTotalRoomCard(driver, test_steps, "Inspection",
					roomStatusElement.RoomStatus_InspectionRoomCardS, roomStatusElement.RoomStatus_InspectionStatSize);
			test_steps.add("Clicking on Inspection in Quick stats displays the inspection rooms"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/787196' target='_blank'>"
					+ "Click here to open TestRail: C787196</a><br>");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Inspection quick stat", "GS_RoomClass", "RoomClass", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Inspection quick stat", "GS_RoomClass", "RoomClass", driver);

			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Clean Quick Stat
		try {
			
			testCase="GS Verifying Clean Stats";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			boolean isExist = roomstatus.getQuickStatSize(driver, "Clean");
			if (!isExist) {
				test_steps.add("<b======Clean Count is Zero Creating New Task and Set Task Status as Clean======</b>");
				navigation.Task_List(driver);
				test_steps.add("Navigate to Task List Tab");
				app_logs.info("Navigate to Task List Tab");
				tasklist.clickQuickStatesAndUpdateTaskStatus(driver, "Inspection", "Done", Utility.RoomNo, test_steps);
				navigation.RoomStatus(driver);
				test_steps.add("Navigate to  Room Status Tab");
				app_logs.info("Navigate to Room Status Tab");
			}
			test_steps.add("<b>======Start Verifying Clean  Stats======</b>");
			roomstatus.clickQuickStatsAndVerifyColor(driver, "Clean", test_steps);
			WebElements_RoomStatus roomStatusElement = new WebElements_RoomStatus(driver);
			roomstatus.verifyTotalRoomCard(driver, test_steps, "Clean", roomStatusElement.RoomStatus_CleanRoomCardS,
					roomStatusElement.RoomStatus_CleanStatSize);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Clean quick stat", "GS_RoomClass", "RoomClass", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Clean quick stat", "GS_RoomClass", "RoomClass", driver);

			} else {
				Assert.assertTrue(false);
			}
		}
		// Verified Out of Order
		try {
			
			testCase="GS Verifying Out of Order Stats";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			boolean isExist = roomstatus.getQuickStatSize(driver, "Out of Order");
			if (!isExist) {
				test_steps.add(
						"<b>======Out Of Order Count is Zero Start Creating Room Maintenance With Out Of Order======</b>");
				navigation.RoomMaintenance(driver);
				test_steps.add("Navigated to Room Maintenance");
				roomMaintance = room_maintenance.createNewRoomOutOfOrder(driver, subject + Utility.getTimeStamp(),
						roomNumbers.get(1), roomClassNames, test_steps, details);
				navigation.clickRoomStatusTab(driver);
				app_logs.info("Navigate to Guest Services");
				test_steps.add("Navigated to Guestservices");
				roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
				roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			}
			WebElements_RoomStatus roomStatusElement = new WebElements_RoomStatus(driver);
			test_steps.add("<b>====== Start Verfying Out OF Order Stats======</b>");
			roomstatus.clickQuickStatsAndVerifyColor(driver, "Out of Order", test_steps);
			test_steps.add("Verify the Room Stats Grid and Tile color for OOO Rooms"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/787230' target='_blank'>"
					+ "Click here to open TestRail: C787230</a><br>");
			roomstatus.verifyTotalRoomCard(driver, test_steps, "Out of Order",
					roomStatusElement.RoomStatus_OORRoomCardS, roomStatusElement.RoomStatus_OutOfOrderStatSize);
			test_steps.add("Successfully Verified Out OF Order Room Card ");
			roomstatus.verifyOutOFOrderDisabledUnableToUpdate(driver, test_steps);
			/*test_steps.add("Verify the quick stats in room status page and task listÂ"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/852347' target='_blank'>"
						+ "Click here to open TestRail: 852347</a><br>");

     		Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify the quick stats in room status page and task listÂ");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
		
			test_steps.add("Verify that able to view the different status of the rooms in the Guest Services tab."+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/850169' target='_blank'>"
					+ "Click here to open TestRail: 850169</a><br>");
			
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify that able to view the different status of the rooms in the Guest Services tab.");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);

			test_steps.add("User should not be able to change status of ooo rooms"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/850177' target='_blank'>"
					+ "Click here to open TestRail: 850177</a><br>");
			
			Utility.testCasePass(Utility.statusCode, 4, Utility.comments, "User should not be able to change status of ooo rooms");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4), Utility.comments.get(4), TestCore.TestRail_AssignToID);
			
			test_steps.add("Verify whether the respective Rooms are displayed upon clicking on the Dirty/Inspected/Clean/OOO/Al"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/850183' target='_blank'>"
					+ "Click here to open TestRail: 850183</a><br>");
			 
			Utility.testCasePass(Utility.statusCode, 6, Utility.comments, "Verify whether the respective Rooms are displayed upon clicking on the Dirty/Inspected/Clean/OOO/Al");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(6), Utility.statusCode.get(6), Utility.comments.get(6), TestCore.TestRail_AssignToID);
	
			test_steps.add("Verify the UI of the Room Status tile"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/850168' target='_blank'>"
					+ "Click here to open TestRail: 850168</a><br>");
			 
			Utility.testCasePass(Utility.statusCode, 7, Utility.comments, "Verify the UI of the Room Status tile");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(7), Utility.statusCode.get(7), Utility.comments.get(7), TestCore.TestRail_AssignToID);
				*/
				
			for(int i=0;i<Utility.statusCode.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Guest Services");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Out of Order", "GS_RoomClass", "RoomClass", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Out of Order", "GS_RoomClass", "RoomClass", driver);

			} else {
				Assert.assertTrue(false);
			}
		}
       //Verified Sorting Option
		try
		{
			testCase="GS Verifying Sorting Options";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			roomstatus.verifySortFuntionality1(driver);
			test_steps.add("Verified Sorting Options <b> <br> Room#, <br>Zone  <br> Arrival Due </b>");
			app_logs.info("Verified Sorting Options Room#, Zone And Arrival Due");
			
			
			
		}catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Out of Order", "GS_RoomClass", "RoomClass", driver);

			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Out of Order", "GS_RoomClass", "RoomClass", driver);

			} else {
				Assert.assertTrue(false);
			}
		}
		// Delete Rate
		try {
			navigation.Inventory_Backward_2(driver);
			test_steps.add("Navigated to Inventory");
			navigation.ratesGrid(driver,test_steps);	
			ArrayList<String> ratePlanNames = new ArrayList<>();
			test_steps.add("<b>======Delete Rate Plan========</b>");
			 rateGrid.clickDeleteIcon(driver, test_steps);
			 rateGrid.verifyDeletedMsg(driver, test_steps, ratesConfig.getProperty("deleteRatePlanMsg"));
			 rateGrid.clickDeleteButton(driver, test_steps);
			 Wait.waitUntilPageLoadNotCompleted(driver, 50);
			 rateGrid.clickRatePlanArrow(driver, test_steps);
			 ratePlanNames=rateGrid.getRatePlanNames(driver);
			 rateGrid.verifyDeletedRatePlan(driver, test_steps, Utility.rateplanName, ratePlanNames);
			 

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Delete Rates ", testName, "Rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.updateReport(e, "Failed to Delete Rates", testName, "Rate", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Delete Room Class
		try {
			test_steps.add("<b>======Delete Room Class======</b>");
			navigation.navigateToSetupfromRateGrid(driver);
			test_steps.add("Navigated to Setup");
			navigation.RoomClass(driver);
			newRoomClass.searchRoomClassV2(driver, Utility.RoomClassName);
			test_steps.add("Click on Search Button");
			app_logs.info("Click on Search Button");
			newRoomClass.deleteRoomClassV2(driver, Utility.RoomClassName);
			test_steps.add("All Room Class Deleted Successfully With Name: <b>" + Utility.RoomClassName + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + Utility.RoomClassName);

			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);

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
	public Object[][] getDataOne() {
		return Utility.getData("createRatePlan", gsexcel);

	}

	public Object[][] getData() {

		return Utility.getData("GSRoomStatusVerification", gsexcel);

	}

		@DataProvider
		public Object[][] getFinalData()
		{
			 return Utility.combine(getData(),  getDataOne());
		} 
		
		
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}
}
