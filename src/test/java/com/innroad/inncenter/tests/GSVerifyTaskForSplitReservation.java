package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.BeforeTest;
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
import com.innroad.inncenter.utils.Utility;

public class GSVerifyTaskForSplitReservation extends TestCore{
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
		if (!Utility.isExecutable(testName, envLoginExcel))
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
	public void gSVerifyTaskForSplitReservation(String checkInDate, String checkOutDate, String adult,
			String child,String roomClassName,String ratePlanName,String categoryName,String taskName,String salutation, String guestFirstName, 
			String guestLastName, String paymentType,
			String cardNumber, String nameOnCard, String marketSegment, String referral) throws ParseException {
		
		test_name = "gSVerifyTaskForSplitReservation";
		/*test_description = "gSVerifyAutoTaskWhileScheduleIsInactiveOrDelete<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/75817630' target='_blank'>"
				+ "Click here to open TestRail: 75817630</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/tests/view/75817613' target='_blank'>"
				+ "Click here to open TestRail: 75817613</a><br>";*/

		test_catagory = "GS Verify Task Status";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String testCase=null;
		Utility.initializeTestCase("850184|728314", Utility.testId, Utility.statusCode, Utility.comments, "");
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
				Utility.reTry.replace(testName, 1);
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
			
			test_description = "GSVerifyTaskForSplitReservation<br>"
					+ "<a href='https://innroad.testrail.io/index.php?/tests/view/75817630' target='_blank'>"
					+ "Click here to open TestRail: 75817630</a><br>";
			testCase="Verify no error message for auto/manually created task when change task type from reservation";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			test_steps.add("========== Create Category and Task ==========");
			app_logs.info("========== Create Category and Task==========");
			taskNames = taskmang.createTask(driver, true, categoryName, taskName, true, "Every Day", true, test_steps);
			for (Map.Entry<String, String> entry : taskNames.entrySet()) {
				taskNameActual = entry.getKey();
				dueTime = entry.getValue();
			}
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Category and Task", "Task Management", "Task Management",
					testCase, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Category and Task", "Task Management", "Task Management", testCase,
					test_description, test_catagory, test_steps);
		}
		
		try {
			
			test_steps.add("<b>==========Create Split Reservation ==========</b>");
			app_logs.info("==========Create Split Reservation ==========");
		
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Split Reservation and Verify Auto Task", "Reservation", "Reservation",
					testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Split Reservation and Verify Auto Task", "Reservation", "Reservation",
					testName, test_description, test_catagory, test_steps);
		}
		
	}

}
