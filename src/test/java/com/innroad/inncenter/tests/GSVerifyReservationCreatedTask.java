package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import com.innroad.inncenter.pageobjects.Account;
import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GSVerifyReservationCreatedTask extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	/*public static String testDescription = "";
	public static String testCategory = "";*/
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	/*ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();*/
	public static String testDescription = null;
	public static String testCategory = null;
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	public static String roomClassNames = null;
	CPReservationPage res = new CPReservationPage();
	RoomStatus roomStatus = new RoomStatus();
	TaskList tasklist = new TaskList();
	TaskManagement taskmang = new TaskManagement();
	String reservationNumber = null, accountNumber = null, randomNO = null;
	String[] taskFors = null, taskAssignees = null, taskCategories = null, taskTypes = null, zones = null,
			taskStatus = null;
	NightlyRate nightlyRate = new NightlyRate();

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gSVerifyReservationCreatedTask(String roomClassName, String maxAdults, String maxPersopns, String roomQuantity,
			String zone, String ratePlanName, String rate, String checkInDate, String checkOutDate, String salutation,
			String guestFirstName, String guestLastName, String paymentType,
			String cardNumber, String nameOnCard, String marketSegment, String referral,
			String taskCategory, String taskType, String taskDetail, String taskRemark, String taskAssignee
			) throws ParseException {
		String testCaseID="846655";
		if(Utility.getResultForCase(driver, testCaseID)) {
		Utility.initializeTestCase("846655", Utility.testId, Utility.statusCode, Utility.comments, "");
		test_name = "gSVerifyReservationCreatedTask";
		testDescription = "GS Task List Verification <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/846655' target='_blank'>"
				+ "Click here to open TestRail: 846655</a><br>";
		testCategory="GS";
		String testName = test_name;
		String seasonStartDate = null,
				seasonEndDate = null;
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		// Get checkIN and Checkout Date
		try {
			if (!(Utility.validateInput(checkInDate)) && !(Utility.validateInput(checkOutDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));
				sessionEndDate.add(Utility.parseDate(Utility.getDatePast_FutureDate(4), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);
			seasonStartDate = checkInDate;
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);
		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testCategory, "Get Dates", testName, testDescription,
					testCategory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testCategory, "Get Dates", testName, testDescription,
					testCategory, test_steps);
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
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, testDescription,
					testCategory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, testDescription,
					testCategory, test_steps);

		}
			// Create Room Class
		try {
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			navigation.setup(driver);
			app_logs.info("Navigated to Setup");
			navigation.roomClass(driver);
			app_logs.info("Navigated to Room Class");
				randomNO = Utility.threeDigitgenerateRandomString();
				roomClassNames = roomClassName + randomNO;
				newRoomClass.createRoomClassWithZoneV2(driver, roomClassNames, roomClassNames, maxAdults, maxPersopns,
						roomQuantity, zone, test, getTest_Steps);
				newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
				test_steps.add("Room Class created successfully " + roomClassNames);
			} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Room Class", "Room Class", "Room Class", testName,
					testDescription, testCategory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Create Room Class", "Room Class", "Room Class", testName,
					testDescription, testCategory, test_steps);

		}
		try {
			test_steps.add("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, ratePlanName, datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", true);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, testDescription,
					testCategory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, testDescription,
					testCategory, test_steps);
		}
		// Create Reservation
		try {
			app_logs.info("======CP Reservation Creation=======");
			test_steps.add("======CP Reservation Creation=======");
			guestFirstName = guestFirstName + Utility.threeDigitgenerateRandomString();
			guestLastName = guestLastName + Utility.threeDigitgenerateRandomString();
			reservationNumber=res.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, "0", 
					ratePlanName, salutation, guestFirstName, guestLastName, "No",
					paymentType, cardNumber, nameOnCard, marketSegment, referral, roomClassNames, true,false);	
			test_steps.add("Reservation Created: <b>" + reservationNumber + "</>");
			app_logs.info("Reservation Created: " + reservationNumber);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName,
					testDescription, testCategory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName,
					testDescription, testCategory, test_steps);
		}

		
		try {
			test_steps.add("<b>======Add Task in Reservation======</b>");
			app_logs.info("======Add Task in Reservation======");			
			res.AddTask(driver, test_steps, taskCategory, taskType, taskDetail, taskRemark, "", taskAssignee, "");
			res.closeReservationTab(driver);
			test_steps.add("<b>======Verify Task in Task List======</b>");
			app_logs.info("======Verify Task in Task List======");		
			navigation.navigateGuestservice(driver);
			app_logs.info("Navigate To Guest Services");
			test_steps.add("Navigate To Guest Services ");
			roomStatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomStatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			navigation.Nav_TaskList(driver);
			app_logs.info("Navigate To Task_List");
			test_steps.add("Navigate To Task List");
			Wait.wait10Second();
			tasklist.clickAllStatsBar(driver, test_steps);
			tasklist.verifyTaskWithTypeAndGuestName(driver, taskType, guestFirstName, test_steps);
			//tasklist.VerifyTasksStatus(driver, "To Do");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, test_steps);
			for (int i = 0; i < Utility.testId.size(); i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify GS task");
			}
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Add Task in Reservation", "Reservation", "Reservation", testName,
					testDescription, testCategory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Add Task in Reservation", "Reservation", "Reservation", testName, testDescription,
					testCategory, test_steps);
		}
		}
		/*// delete room Class
		try {
			test_steps.add("<b>======Delete Room Class======</b>");
			navigation.navigateToSetupfromTaskManagement(driver);
			navigation.RoomClass(driver);			
			newRoomClass.searchRoomClassV2(driver, roomClassNames);
			app_logs.info("Click on Search Button");
			newRoomClass.deleteRoomClassV2(driver, roomClassNames);
			test_steps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassNames + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassNames);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Delete Room Class", "Room Class", "Room Class", testName,
					testDescription, testCategory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Room Class", "Room Class", "Room Class", testName, testDescription,
					testCategory, test_steps);
		}*/
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("GSVerifyReservationCreatedTask", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
