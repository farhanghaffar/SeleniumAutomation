package com.innroad.inncenter.tests;


import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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


public class GSRoomMaintenanceVerifyTapeChartAndRoomStatus extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> test_steps = new ArrayList<>();
	RoomClass roomClass = new RoomClass();
	public static String roomClassName;
	Navigation nav = new Navigation();
	CPReservationPage res = new CPReservationPage();
	RoomMaintenance room_maintenance = new RoomMaintenance();
	RoomStatus roomstatus = new RoomStatus();
	OverView overview = new OverView();
	Tapechart tapechart = new Tapechart();
	NewRoomClassesV2 newRoomClass= new NewRoomClassesV2();
	RatesGrid rateGrid= new RatesGrid();
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
			rateDisplayName = null,seasonStartDate=null,seasonEndDate=null;;
	String nights = null, startDate = null, endDate = null;
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	
	private void createReservation(String checkInDate, String checkOutDate, String adult, String  children, String ratePlanName) throws InterruptedException {
		res.click_NewReservation(driver, test_steps);
		res.select_CheckInDate(driver, test_steps, checkInDate);
		res.select_CheckoutDate(driver, test_steps, checkOutDate);
		res.enter_Adults(driver, test_steps, adult);
		res.enter_Children(driver, test_steps, children);
		res.select_Rateplan(driver, test_steps, ratePlanName, "");
		res.clickOnFindRooms(driver, test_steps);
		res.verifySpinerLoading(driver);
		
	}
	
	@Test(dataProvider = "getData", groups = "GuestServices")
		public void gsRoomMaintenanceVerifyTapeChartAndRoomStatus(String checkInDate, String checkOutDate,String RoomClassName, String MaxAdults,
			String MaxPersopns, String RoomQuantity,String ratePlanName,String updatedRate, String Subject,String Detail, String children) throws ParseException {
		String testCaseID="848232|848235|848195|848203|848230|848236|848231";
		if(Utility.getResultForCase(driver, testCaseID)) {
		caseId.clear();
		statusCode.clear();
		comments.clear();
		
		
		test_name = "GSRoomMaintenanceVerifyTapeChartAndRoomStatusAndInventory";
		test_description = "GS-Room Maintenance-Making a Room Out Of Order And Verify TapeChart , RoomStatus and Inventory<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682447' target='_blank'>"
				+ "Click here to open TestRail: C682447</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848195' target='_blank'>"
				+ "Click here to open TestRail: 848195</a><br>";
				
		test_catagory = "Verification";
		testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		Utility.initializeTestCase("848232|848235|848195|848203|848230|848236|848231", Utility.testId, Utility.statusCode,Utility.comments,"");
		//Utility.updateTestCaseStatusBeforeExecution(driver, Utility.testId, Utility.statusCode, TestCore.TestRail_AssignToID);
		
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> seasonEndDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> roomNumbers = new ArrayList<String>();
		String testCase=null;
		// Get checkIN and Checkout Date
		try {
			
			
			if (!(Utility.validateInput(checkInDate))) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));	
					seasonEndDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			seasonStartDate=checkInDate;
			seasonEndDate=seasonEndDates.get(0);
			datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDate, checkOutDate);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
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

// Create Room Class		
		try {
			
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			nav.Setup(driver);
			test_steps.add("Navigated to Setup");
			nav.RoomClass(driver);
			test_steps.add("Navigated to Room Class");
			roomClassName = RoomClassName + Utility.fourDigitgenerateRandomString();
			roomNumbers=newRoomClass.createRoomClassV2(driver, test_steps, roomClassName, roomClassName, MaxAdults, MaxPersopns, RoomQuantity);			
			test_steps.add("Room Class Created: <b>"+roomClassName+"</>");
			app_logs.info(" Room Class Created: "+roomClassName);
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

		// Create Season
		try {
			test_steps.add("<b>======Start Updating Rate Plan ======</b>");
			nightlyRates.createSeasonForExistingRatePlan(driver, test_steps, ratePlanName,datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassName, updatedRate, "", 
					"", "", "", true);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, test_steps);
		}

		// Create New Out of Order Room
		try {
			test_steps.add("<b>============Start Creating Room Maintenance============</b>");
			navigation.navigateGuestservicesAfterrateGrid(driver);
			test_steps.add("Navigated to Guestservices");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			nav.RoomMaintenance(driver);
			test_steps.add("Navigated to RoomMaintenance");
			Subject=Subject + Utility.fourDigitgenerateRandomString();
			roomMaintance = room_maintenance.createNewRoomOutOfOrder(driver, Subject,
					roomNumbers.get(0), roomClassName, test_steps, Detail);
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

		// Verified Room Status
		try {
			testCase="Verifying Out OF Order Room in Room Status";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			test_steps.add("<b>============Start Verfying Room Status============</b>");
			roomstatus.clickRoomStatusTab(driver);
			roomstatus.searchByRoomHash(driver, roomNumbers.get(0), roomClassName, test_steps);
			roomstatus.verifyRoomStatusWithSpecificRoomNo(driver, roomNumbers.get(0), roomClassName, "Out of Order",
					test_steps);
			
			/*test_steps.add("Verification of out of order in House keeping tab"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848236' target='_blank'>"
					+ "Click here to open TestRail: C848236</a><br>");
		
			Utility.testCasePass( Utility.statusCode, 5,Utility.comments,"Successfully Verified Out of Order Room in room status tab");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(5), Utility.statusCode.get(5), Utility.comments.get(5), TestCore.TestRail_AssignToID);
*/
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Room Status", testCase, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Room Status", testCase, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Navigate to TapeChart
		try {

			testCase="Verifying Out OF Order Room in Tape Chart";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			test_steps.add("<b>============Start Verifying Out OF Order Room in Tape Chart============</b>");
			nav.Reservation_Backward_2(driver);
			test_steps.add("Navigate to Reservation");
			app_logs.info("Navigate to Reservation");
			nav.navTapeChart(driver, test);
			test_steps.add("Successfully Navigate to TapeChart");
			app_logs.info("Successfully Navigate to TapeChart");
			tapechart.verifyOutOfOrder(driver, test_steps, roomClassName,roomNumbers.get(0));
			test_steps.add("Successfully Verified Out of Order Room in Tape Chart");
			app_logs.info("Successfully Verified Out of Order Room in Tape Chart");
			
			/*test_steps.add("GS-Room Maintenance-Making a room out of order and verify in Tape chart/Room status page"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848203' target='_blank'>"
					+ "Click here to open TestRail: C848203</a><br>");
			
			Utility.testCasePass(Utility.statusCode, 3,Utility.comments,"GS-Room Maintenance-Making a room out of order and verify in Tape chart/Room status page");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3), Utility.comments.get(3), TestCore.TestRail_AssignToID);
		
			test_steps.add("Verify status of rooms in tape chart when rooms are added under maintenance from guest service"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848230' target='_blank'>"
					+ "Click here to open TestRail: C848230</a><br>");
			
			Utility.testCasePass(Utility.statusCode, 4,Utility.comments,"Verify status of rooms in tape chart when rooms are added under maintenance from guest service");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4), Utility.comments.get(6), TestCore.TestRail_AssignToID);
			*/RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate to TapeChart", testCase, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Navigate to TapeChart", testCase, "Navigation", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Verify Out of Order On Inventory
		try {
			testCase="Verifying Out OF Order Room in Inventory";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			List<String> roomNoSize = new ArrayList<String>();
			roomNoSize.add(roomNumbers.get(0));
			int size = roomNoSize.size();
			test_steps.add("<b>============Start Verifying Out OF Order Room in Inventory============</b>");
			nav.Inventory(driver);
			test_steps.add("Navigated to Inventory");
			nav.Inventory_Ratesgrid_Tab(driver, test_steps);
			nav.AvailabilityTab(driver);
			test_steps.add("Click on Availability Tab");
			List<String> dateList = date.subList(1, date.size());
			app_logs.info(dateList);
			List<String> dayList = day.subList(1, day.size());
			app_logs.info(dayList);
			overview.verifyOutOfOrder(driver, test_steps, roomClassName, "OOO", String.valueOf(size), dateList, dayList,
					Integer.parseInt(nights));
			test_steps.add("Successfully Verified Out of Order Room in Inventory");
			app_logs.info("Successfully Verified Out of Order Room in Inventory");
				/*	test_steps.add("Verify room maintenance item in overview"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848231' target='_blank'>"
					+ "Click here to open TestRail: 848231</a><br>");					
					Utility.testCasePass(Utility.statusCode, 6, Utility.comments, "Verify room maintenance item in overview");
					Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(6), Utility.statusCode.get(6), Utility.comments.get(6), TestCore.TestRail_AssignToID);
			*/RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		} catch (Exception e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Out of Order Room In Inventory", testCase, "RoomCreation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testCase) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
				Utility.updateReport(e, "Failed to Verify Out of Order Room In Inventory", testCase, "RoomCreation",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		try {
			
			test_steps.add("<b>============Start Verifying Out OF Order Room in Reservation============</b>");
			app_logs.info("============Start Verifying Out OF Order Room in Reservation============");
			testCase="Verify Creating reservation in a room for which room maintenance item is created";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			
			test_steps.add("Created Out of Order " + Subject);
			app_logs.info("Created Out of Order " + Subject);
			
			nav.inventoryToReservation(driver);
			createReservation( checkInDate,  checkOutDate,  MaxAdults,   children,  ratePlanName);
			ArrayList<String> rooms = res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassName);
			assertFalse(rooms.contains(roomNumbers.get(0)), "Failed to verify arraylist");
			test_steps.add("Verified Out Order Room should not come for Reservation" + roomNumbers.get(0));
			app_logs.info("Verified Out Order Room should not come for Reservation" + roomNumbers.get(0));
			/*test_steps.add("Verify Creating reservation in a room for which room maintenance item is created"
			+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848232' target='_blank'>"
			+ "Click here to open TestRail: 848232</a><br>");
			
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify Creating reservation in a room for which room maintenance item is created");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
	*/
			
			res.closeReservationTab(driver);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "verify OOT on Reservation Creation", "Reservation", "Reservation", testCase,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "verify OOT on Reservation Creation", "Reservation", "Reservation", testCase, test_description,
					test_catagory, test_steps);
		}
		
		try {
			test_steps.add("<b>============Start Verifying Able to Create Reservation After delete Out of Order============</b>");
			app_logs.info("============Start Verifying Able to Create Reservation After delete Out of Order============");
			testCase="Delete a Room Maintenance Item and verify in Room Assignment picker";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			
			nav.clickOnGuestServices(driver, test_steps);
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			nav.RoomMaintenance(driver);
			test_steps.add("Navigated to RoomMaintenance");
			String startDate=Utility.parseDate(checkInDate, "dd/MM/yyyy", "MMM dd, yyyy");
			String endDate=Utility.parseDate(checkOutDate, "dd/MM/yyyy", "MMM dd, yyyy");
			room_maintenance.searchByFromAndToDate(driver, test_steps, startDate, endDate, "dd/MM/yyyy", "Out of Order");
			room_maintenance.DeleteRoomOutOfOrder(driver, Subject);
			nav.guestservices_Click(driver, test_steps);
			nav.navigateToReservationFromGuestServices(driver, test_steps);
			createReservation( checkInDate,  checkOutDate,  MaxAdults,   children,  ratePlanName);
			ArrayList<String> rooms = res.getAllRoomNumbersFromRoomClassDropDown(driver, test_steps, roomClassName);
			assertTrue(rooms.contains(roomNumbers.get(0)), "Failed to verify arraylist");
			test_steps.add("Verified  Room should  come for Reservation After removed from Out of Order" + roomNumbers.get(0));
			app_logs.info("Verified  Room should  come for Reservation After removed from Out of Order" + roomNumbers.get(0));
			/*test_steps.add("Delete a Room Maintenance Item and verify in Room Assignment picker"
			+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848235' target='_blank'>"
			+ "Click here to open TestRail: 848235</a><br>");
			res.closeReservationTab(driver);			
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Delete a Room Maintenance Item and verify in Room Assignment picker");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
		
			test_steps.add("To Verify making a room out of order is reflecting in tape chart Guest servicess-room status pageÃ‚Â Ã‚Â "
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848195' target='_blank'>"
					+ "Click here to open TestRail: C848195</a><br>");
			
			Utility.testCasePass(Utility.statusCode, 2, Utility.comments, "To Verify making a room out of order is reflecting in tape chart Guest servicess-room status pageÃ‚Â Ã‚Â ");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2), Utility.comments.get(2), TestCore.TestRail_AssignToID);
		*/	RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);

		
		}catch (Exception e) {
			Utility.catchException(driver, e, "verify  Reservation Creation after delete OOD", "Reservation", "Reservation", testCase,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "verify  Reservation Creation after delete OOD", "Reservation", "Reservation", testCase, test_description,
					test_catagory, test_steps);
		}
	
		
		// Delete Room Class
				try {
					test_steps.add("<b>======Delete Room Class======</b>");
					navigation.setup(driver);
					test_steps.add("Navigated to Setup");
					navigation.RoomClass(driver);
					newRoomClass.searchRoomClassV2(driver, roomClassName);
					test_steps.add("Click on Search Button");
					app_logs.info("Click on Search Button");
					newRoomClass.deleteRoomClassV2(driver, roomClassName);
					test_steps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassName + " </b>");
					app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassName);

					for(int i=0;i<Utility.statusCode.size();i++) {
						Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Room Maintainance cases");
					}
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
	public Object[][] getDataOne() {
		return Utility.getData("createRatePlan", gsexcel);

	}
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyTapeChartRoomStatus", gsexcel);

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
