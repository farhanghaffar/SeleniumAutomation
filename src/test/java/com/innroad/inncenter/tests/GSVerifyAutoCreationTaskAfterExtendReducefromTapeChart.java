package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GSVerifyAutoCreationTaskAfterExtendReducefromTapeChart extends TestCore {

	private WebDriver driver = null;
	public static String test_name = null;
	public static String test_nameOne = null;
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> testSteps = new ArrayList<>();
	String testName = null;
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	RoomStatus roomstatus = new RoomStatus();
	CPReservationPage reservation = new CPReservationPage();
	TaskList tasklist = new TaskList();
	ReservationSearch revSearch = new ReservationSearch();
	NightlyRate nightlyRate = new NightlyRate();
	TaskManagement taskmang = new TaskManagement();
	Tapechart tapechart = new Tapechart();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "GuestServices")
	public void verifyAutoTaskAfterExtendReduceFromTapeChart(String checkInDate, String checkOutDate,
			String roomClassName, String maxAdults, String maxPersopns, String roomQuantity, String ratePlanName,
			String rate, String salutation, String guestFirstName, String guestLastName, String paymentType,
			String cardNumber, String nameOnCard, String marketSegment, String referral, String child,
			String categoryName, String taskName) throws ParseException {
		String testCaseID="850195|848635";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "GSVerifyAutoCreationTaskAfterExtendReducefromTapeChart";
		test_description = "GSVerifyAutoCreationTaskAfterExtendReducefromTapeChart<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850195' target='_blank'>"
				+ "Click here to open TestRail: 850195</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848635' target='_blank'>"
				+ "Click here to open TestRail: 848635</a><br>";
		test_catagory = "GS Verify Task Status";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("850195|848635", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		List<String> roomNumber = new ArrayList<String>();
		String roomClassNames = null, seasonStartDate = null, seasonEndDate = null, confirmationNo = null,
				checkInDateTime = null, checkOutDateTime = null, checkInDueTime = null, checkOutDueTime = null,
				taskNameActual = null, taskNameDueTime = null;
		HashMap<String, String> taskNames = new HashMap<String, String>();
		// Get checkIN and Checkout Date
		try {

			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			seasonStartDate = checkInDates.get(0);
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);

			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				//Utility.reTry.replace(testName, 1);
				Utility.reTry.replace(testName, Utility.reTry.get(testName)+1);
			}
			datesRangeList = Utility.getAllDatesStartAndEndDates(checkInDate, checkOutDate);
			app_logs.info(datesRangeList);
			driver = getDriver();
			login_GS(driver);
			testSteps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, testSteps);
		}
		// Create Room Class
		try {
			testSteps.add("<b>======Start Creating New Room Class======</b>");
			navigation.clickSetup(driver);
			navigation.RoomClass(driver);
			testSteps.add("Navigated to Room Class");
			roomClassNames = roomClassName + Utility.fourDigitgenerateRandomString();
			newRoomClass.createRoomClassV2(driver, testSteps, roomClassNames, roomClassNames, maxAdults, maxPersopns,
					roomQuantity);
			newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
			testSteps.add("Room Class Created: <b>" + roomClassNames + "</>");
			app_logs.info("Room Class Created: " + roomClassNames);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Room Class", "Room Class", "Room Class", testName, test_description,
					test_catagory, testSteps);
		}

		try {
			testSteps.add("========== Delete Category ==========");
			app_logs.info("========== Delete Category ==========");
			navigation.taskManagement_TabExist(driver);
			testSteps.add("Task Management Tab Exist");
			navigation.taskManagement(driver);
			testSteps.add("Click on Task Management");
			app_logs.info("Click on Task Management");
			taskmang.deleteAllCategories(driver, categoryName, testSteps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Delete Category", "Task Management", "Task Management", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Category", "Task Management", "Task Management", testName,
					test_description, test_catagory, testSteps);
		}
		try {
			testSteps.add("========== Create Category and Task ==========");
			app_logs.info("========== Create Category and Task==========");
			taskNames = taskmang.createTask(driver, true, categoryName, taskName, true, "Every Day", true, testSteps);
			for (Map.Entry<String, String> entry : taskNames.entrySet()) {
				taskNameActual = entry.getKey();
				taskNameDueTime = entry.getValue();

			}
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Category and Task", "Task Management", "Task Management",
					testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Category and Task", "Task Management", "Task Management", testName,
					test_description, test_catagory, testSteps);
		}
		// Create Season
		try {
			testSteps.add("<b>======Start Updating Rate Plan ======</b>");
			ArrayList<String> ratePlanNames = Utility.splitInputData(ratePlanName);
			nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanNames.get(0), datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", true);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>==========Create Reservation ==========</b>");
			app_logs.info("==========Create Reservation ==========");
			guestFirstName = guestFirstName + Utility.fourDigitgenerateRandomString();
			guestLastName = guestLastName.toString() + Utility.fourDigitgenerateRandomString();
			confirmationNo = reservation.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, child,
					ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber, nameOnCard,
					marketSegment, referral, roomClassNames, true, false);
			testSteps.add(" Reservation Created Successsfully " + confirmationNo);
			app_logs.info(" Reservation Created Successsfully " + confirmationNo);
			roomNumber = reservation.getStayInfoRoomNo(driver, testSteps);
			testSteps.add("<b>==========Check In Reservation ==========</b>");
			app_logs.info("==========Check In Reservation ==========");
			reservation.clickCheckInButton(driver, testSteps);
			reservation.generatGuestReportToggle(driver, testSteps, "No");
			//reservation.completeCheckInProcessSingleRev(driver, testSteps);
			reservation.clickOnConfirmCheckInButton(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			Wait.wait5Second();
			revSearch.closeReservation(driver);
			app_logs.info("Close the Reservation");
			Wait.wait60Second();
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);
			driver.navigate().refresh();
			Wait.wait60Second();
			testSteps.add("<b>==========Verify Reservation Task==========</b>");

			for (int i = 1; i < datesRangeList.size(); i++) {
				checkInDateTime = Utility.parseDate(datesRangeList.get(i), "dd/MM/yyyy", "MM/dd/ yy");
				app_logs.info(checkInDateTime);
				String finalCheckInDateTime = checkInDateTime + " " + taskNameDueTime;
				app_logs.info(finalCheckInDateTime);
				reservation.verifyReservationTask(driver, taskNameActual, "Automatically generated at checkin",
						finalCheckInDateTime, "To Do", testSteps, "");
			}
			reservation.closeReservationTab(driver);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation and Verify Auto Task", "Reservation", "Reservation",
					testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation and Verify Auto Task", "Reservation", "Reservation",
					testName, test_description, test_catagory, testSteps);
		}

		try {
			testSteps.add(
					"<b>==========Extend Stay Date from Tape Chart ==========</b>");
			app_logs.info(
					"==========Extend Stay Date from Tape Chart==========");
			navigation.navTapeChart(driver, test);
			testSteps.add("Successfully Navigate to TapeChart");
			app_logs.info("Successfully Navigate to TapeChart");
			tapechart.searchInTapechart(driver, testSteps, checkInDate, checkOutDate, maxAdults, child, ratePlanName,
					"");
			tapechart.extendReservation(driver, roomNumber.get(0), roomClassNames, guestFirstName, 1, checkOutDate,
					seasonEndDate, testSteps);

		} catch (Exception e) {
			Utility.catchException(driver, e,
					"Extend and Reduce Stay Date from Tape Chart and verify Task In Reservation", "Reservation",
					"Reservation", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Extend and Reduce Stay Date from Tape Chart and verify Task In Reservation",
					"Reservation", "Reservation", testName, test_description, test_catagory, testSteps);
		}

		try {
			
			testSteps.add(
					"<b>==========Verify task in reservation after Extend stay date from Tape Chart==========</b>");
			app_logs.info(
					"==========Verify task in reservation after Extend stay date from Tape Chart==========");
			
			ArrayList<String >datesRangeListSecond = Utility.getAllDatesStartAndEndDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeListSecond);
			navigation.navigateToReservations(driver);
			testSteps.add("Navigate to Reservation");
			app_logs.info("Navigate to Reservation");
			reservation.verifySpinerLoading(driver);
			Wait.wait60Second();
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);
			driver.navigate().refresh();
			Wait.wait60Second();
			for (int i = 1; i < datesRangeListSecond.size(); i++) {
				checkInDateTime = Utility.parseDate(datesRangeListSecond.get(i), "dd/MM/yyyy", "MM/dd/ yy");
				app_logs.info(checkInDateTime);
				String finalCheckInDateTime = checkInDateTime + " " + taskNameDueTime;
				app_logs.info(finalCheckInDateTime);
				reservation.verifyReservationTask(driver, taskNameActual, "Automatically generated at checkin",
						finalCheckInDateTime, "To Do", testSteps, "");
			}
			
			reservation.closeReservationTab(driver);
			
		} catch (Exception e) {
			Utility.catchException(driver, e, "verify Task In Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "verify Task In Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		}
		try {
			
			testSteps.add(
					"<b>==========Reduce Stay Date from Tape Chart ==========</b>");
			app_logs.info(
					"==========Reduce Stay Date from Tape Chart==========");
			navigation.navTapeChart(driver, test);
			testSteps.add("Successfully Navigate to TapeChart");
			app_logs.info("Successfully Navigate to TapeChart");
			tapechart.searchInTapechart(driver, testSteps, checkInDate, checkOutDate, maxAdults, child, ratePlanName,
					"");
			tapechart.reduceReservation(driver, roomClassNames, roomNumber.get(0), guestFirstName, 1, seasonEndDate, checkOutDate, testSteps);
			
			/*testSteps.add("Verify the extended or shortened reservations view limit from tape chart"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848635' target='_blank'>"
					+ "Click here to open TestRail: 848635</a><br>");
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "Verify the extended or shortened reservations view limit from tape chart");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);
			*/
			testSteps.add(
					"<b>==========Verify task in reservation after Extend stay date from Tape Chart==========</b>");
			app_logs.info(
					"==========Verify task in reservation after Extend stay date from Tape Chart==========");
			navigation.navigateToReservations(driver);
			testSteps.add("Navigate to Reservation");
			app_logs.info("Navigate to Reservation");
			reservation.verifySpinerLoading(driver);
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);
			for (int i = 1; i < datesRangeList.size()-1; i++) {
				checkInDateTime = Utility.parseDate(datesRangeList.get(i), "dd/MM/yyyy", "MM/dd/ yy");
				app_logs.info(checkInDateTime);
				String finalCheckInDateTime = checkInDateTime + " " + taskNameDueTime;
				app_logs.info(finalCheckInDateTime);
				reservation.verifyReservationTask(driver, taskNameActual, "Automatically generated at checkin",
						finalCheckInDateTime, "To Do", testSteps, "");
			}			
			reservation.closeReservationTab(driver);
			/*
			testSteps.add("Verify the location of auto created Tasks when the reservation stay dates are extended/reduced from Tapechart"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850195' target='_blank'>"
					+ "Click here to open TestRail: 850195</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify the location of auto created Tasks when the reservation stay dates are extended/reduced from Tapechart");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
			*/
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode,i,Utility.comments,"Verify Drive Rate on Vrbo");
			}
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e,
					"Reduce Stay Date from Tape Chart and verify Task In Reservation", "Reservation",
					"Reservation", testName, test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Reduce Stay Date from Tape Chart and verify Task In Reservation",
					"Reservation", "Reservation", testName, test_description, test_catagory, testSteps);
		}
	}
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("GSAutoTaskExtendReduceStayDate", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
