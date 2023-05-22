package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.time.LocalDate;
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
import com.innroad.inncenter.pageobjects.OverView;
import com.innroad.inncenter.pageobjects.RatesGrid;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomMaintenance;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.Tapechart;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GSVerifyAutoTaskWhileScheduleIsInactiveOrDelete extends TestCore{

	
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
	ArrayList<String> test_stepsOne = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	TaskManagement taskmang = new TaskManagement();
	CPReservationPage reservation = new CPReservationPage();
	ReservationSearch revSearch = new ReservationSearch();
	TaskList tasklist = new TaskList();
	String testName = null;
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	
	private void deleteCategories(WebDriver driver ,String categoryName, ArrayList<String> test_steps) throws InterruptedException {
		navigation.clickSetup(driver);
		navigation.taskManagement_TabExist(driver);
		test_steps.add("Task Management Tab Exist");
		navigation.taskManagement(driver);
		test_steps.add("Click on Task Management");
		app_logs.info("Click on Task Management");
		taskmang.deleteAllCategories(driver, categoryName, test_steps);
	}
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gSVerifyAutoTaskWhileScheduleIsInactiveOrDelete(String checkInDate, String checkOutDate, String adult,
			String child,String roomClassName,String ratePlanName,String categoryName,String taskName,String salutation, String guestFirstName, 
			String guestLastName, String paymentType,
			String cardNumber, String nameOnCard, String marketSegment, String referral) throws ParseException {
		String testCaseID="850194|848200";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "Verify the location of already created auto tasks and the schedule is Inactive/Deleted";
		test_description = "gSVerifyAutoTaskWhileScheduleIsInactiveOrDelete<br>";

		test_catagory = "GS Verify Task Status";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("850194|848200", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		HashMap<String, String> taskNames = new HashMap<String, String>();
		String taskNameActual = null, dueTime = null,confirmationNo = null,date=null;
		List<String> roomNumber = new ArrayList<String>();
		try {

			if (!(Utility.validateInput(checkInDate))) {
				checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
				checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(2), "MM/dd/yyyy", "dd/MM/yyyy"));
			} else {
				checkInDates = Utility.splitInputData(checkInDate);
				checkOutDates = Utility.splitInputData(checkOutDate);
			}
			checkInDate = checkInDates.get(0);
			checkOutDate = checkOutDates.get(0);
			app_logs.info(checkInDate);
			app_logs.info(checkOutDate);

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
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, test_steps);
		}
		
		try {
			test_steps.add("========== Delete Category ==========");
			app_logs.info("========== Delete Category ==========");
			deleteCategories(driver,categoryName,test_steps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Delete Category", "Task Management", "Task Management", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Category", "Task Management", "Task Management", testName,
					test_description, test_catagory, test_steps);
		}
		
		try {
			test_steps.add("========== Create Category and Task ==========");
			app_logs.info("========== Create Category and Task==========");
			taskNames = taskmang.createTask(driver, true, categoryName, taskName, true, "Every Day", true, test_steps);
			for (Map.Entry<String, String> entry : taskNames.entrySet()) {
				taskNameActual = entry.getKey();
				dueTime = entry.getValue();
			}
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Category and Task", "Task Management", "Task Management",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Category and Task", "Task Management", "Task Management", testName,
					test_description, test_catagory, test_steps);
		}
		
		try {
			test_steps.add("<b>==========Create Reservation ==========</b>");
			app_logs.info("==========Create Reservation ==========");
			guestFirstName = guestFirstName + Utility.fourDigitgenerateRandomString();
			guestLastName = guestLastName.toString() + Utility.fourDigitgenerateRandomString();
			confirmationNo = reservation.createBasicReservation(driver, checkInDate, checkOutDate, adult, child,
					ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber, nameOnCard,
					marketSegment, referral, roomClassName, true, false);
			test_steps.add(" Reservation Created Successsfully " + confirmationNo);
			app_logs.info(" Reservation Created Successsfully " + confirmationNo);
			roomNumber = reservation.getStayInfoRoomNo(driver, test_steps);
			test_steps.add("<b>==========Check In Reservation ==========</b>");
			app_logs.info("==========Check In Reservation ==========");
			reservation.clickCheckInButton(driver, test_steps);
			reservation.generatGuestReportToggle(driver, test_steps, "No");
			reservation.completeCheckInProcessSingleRev(driver, test_steps);
			Wait.wait5Second();
			revSearch.closeReservation(driver);
			app_logs.info("Close the Reservation");
			Wait.wait60Second();
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);
			driver.navigate().refresh();
			Wait.wait60Second();
			test_steps.add("<b>==========Verify Reservation Task==========</b>");
			for (int i = 1; i < datesRangeList.size(); i++) {
				date = Utility.parseDate(datesRangeList.get(i), "dd/MM/yyyy", "MM/dd/ yy");
				app_logs.info(date);
				String finalCheckInDateTime = date + " " + dueTime;
				app_logs.info(finalCheckInDateTime);
				reservation.verifyReservationTask(driver, taskNameActual, "Automatically generated at checkin",
						finalCheckInDateTime, "To Do", test_steps, "");
			}
			reservation.closeReservationTab(driver);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation and Verify Auto Task", "Reservation", "Reservation",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation and Verify Auto Task", "Reservation", "Reservation",
					testName, test_description, test_catagory, test_steps);
		}
		
		try {
			
			test_steps.add("<b>==========Delete Scheduled Task from Task Management ==========</b>");
			app_logs.info("==========Delete Scheduled Task from Task Management ==========");
			deleteCategories(driver,categoryName,test_steps);		
			test_steps.add("<b>==========Verify Task in Reservation After Delete Scheduled Task ==========</b>");
			app_logs.info("==========Verify Task in Reservation After Delete Scheduled Task ==========");
			navigation.navigateToReservationFromGuestServices(driver, test_steps);
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);
			driver.navigate().refresh();
			Wait.wait60Second();
			for (int i = 1; i < datesRangeList.size(); i++) {
				date = Utility.parseDate(datesRangeList.get(i), "dd/MM/yyyy", "MM/dd/ yy");
				app_logs.info(date);
				String finalCheckInDateTime = date + " " + dueTime;
				app_logs.info(finalCheckInDateTime);
				reservation.verifyReservationTask(driver, taskNameActual, "Automatically generated at checkin",
						finalCheckInDateTime, "To Do", test_steps, "");
			}
			reservation.closeReservationTab(driver);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Delete Sceduled Task and verify in reservation", "Task Management", "Task Management",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Sceduled Task and verify in reservation", "Task Management", "Task Management",
					testName, test_description, test_catagory, test_steps);
		}
		
		try {
			test_steps.add("<b>==========Verify Task in Task List After Delete Scheduled Task ==========</b>");
			app_logs.info("==========Verify Task in Task List After Delete Scheduled Task ==========");
			navigation.navigateGuestservice(driver);
			test_steps.add("Navigate to Guest Services");
			app_logs.info("Navigate to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, test_steps);
			//roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			navigation.Nav_TaskList(driver);
			app_logs.info("Navigate To Task_List");
			test_steps.add("Navigate To Task List");
			tasklist.clickTaskForDropDown(driver, test_steps);
			tasklist.selectTaskFor(driver, test_steps, "This Week");
			tasklist.clickAllStatsBar(driver, test_steps);
			Wait.wait5Second();
			tasklist.verifyTaskWithTypeAndGuestName(driver, taskName, guestFirstName, test_steps);
			for(int i=0;i<Utility.testId.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify auto task for schedule inactive and delete");
			}
			
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify task in Task List after Delete Sceduled Task", "Task List", "Task List",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify task in Task List after Delete Sceduled Task", "Task List", "Task List",
					testName, test_description, test_catagory, test_steps);
		}
		
		try {
			test_steps.add("========== Delete Category ==========");
			app_logs.info("========== Delete Category ==========");
			navigation.navigateToReservationFromGuestServices(driver, test_steps);
			deleteCategories(driver,categoryName,test_steps);
			
		/*	test_steps.add("Verify the location of already created auto tasks and the schedule is Inactive/Deleted"+
					"<a href='https://innroad.testrail.io/index.php?/cases/view/850194' target='_blank'>"
					+ "Click here to open TestRail: 850194</a><br>");
			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify Task in Task List and Reservation After Delete Scheduled Task ");	
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
*/
			
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, test_description, test_catagory, test_steps);
	
		}catch (Exception e) {
			Utility.catchException(driver, e, "Delete Category", "Task Management", "Task Management", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Category", "Task Management", "Task Management", testName,
					test_description, test_catagory, test_steps);
		}
		}
	}
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("GSAutoTaskScheduleInactiveOrDel", gsexcel);

	}
	
	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		
	}
}
