package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class GSVerifyReservationTask  extends TestCore{

	
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
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gSVerifyReservationTask(String checkInDate, String checkOutDate, String adult,String child,
			String roomClassName, String salutation, String guestFirstName, String guestLastName, String paymentType, 
			String cardNumber,String nameOnCard,String marketSegment, String referral,
			String taskCategory, String taskType,
			String updatedTaskCategoryName,String updatedTaskTypeName, String updatedTaskStatusName) throws ParseException {
		String testCaseID="848278|852510";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "GS: Verify Reservation Task";
		test_description = "VerifyDirtyStatusMRBReservation<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848278' target='_blank'>"
				+ "Click here to open TestRail: 848278</a><br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/852510' target='_blank'>"
				+ "Click here to open TestRail: 852510</a><br>";

		test_catagory = "GS Dirty Status";
		testName = test_name;
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		Utility.initializeTestCase("848278|852510", Utility.testId, Utility.statusCode, Utility.comments, "");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> roomNumber = new ArrayList<String>();
		String confirmationNo=null, testCase=null;
		try {
			if (!(Utility.validateInput(checkInDate))) {
					checkInDates.add(Utility.getCurrentDate("dd/MM/yyyy"));
					checkOutDates.add(Utility.parseDate(Utility.getDatePast_FutureDate(1), "MM/dd/yyyy", "dd/MM/yyyy"));	
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
		
		try
		{
			test_description = "Verify that user is able to add a new task ";
			testCase="Verify that user is able to add a new task";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			testSteps.add("<b>======Start Creating New Reservation======</b>");
			app_logs.info("<b>======Start Creating New Reservation======</b>");
			String randomNumber=Utility.generateRandomNumber();
			guestFirstName=guestFirstName+randomNumber;
			guestLastName=guestLastName+randomNumber;
			confirmationNo=reservation.createBasicReservation(driver, checkInDate, checkOutDate, adult, child, "All", salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber, nameOnCard, marketSegment, referral, roomClassName, false, false);
			testSteps.add("<b>======Start Adding  Task in Reservation======</b>");
			app_logs.info("<b>======Start Adding  Task in Reservation======</b>");
			reservation.AddTask(driver, testSteps, taskCategory, taskType, "", "", checkInDate, "", "");
			reservation.verifySpinerLoading(driver);
			reservation.closeReservationTab(driver);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Reservation", "Reservation", "Reservation", testCase, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Reservation", "Reservation", "Reservation", testCase, test_description,
					test_catagory, testSteps);
		}
		
		try {
			testSteps.add("<b>======Start Verifying Task in Task List======</b>");
			app_logs.info("<b>======Start Verifying Task in Task List======</b>");
			navigation.navigateGuestservice(driver);					
			testSteps.add("Navigate to Guest Services");
			app_logs.info("Navigate to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, testSteps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, testSteps);			
			navigation.Nav_TaskList(driver);
			app_logs.info("Navigate To Task_List");
			testSteps.add("Navigate To Task List");
			app_logs.info("======Search and Verified By Guest Name=======");
			testSteps.add("======Search and Verified By Guest Name=======");
			tasklist.verifyTaskWithTypeAndGuestName(driver, taskType, guestFirstName, testSteps);
			/*testSteps.add("Verify that user is able to add a new task"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848278' target='_blank'>"
					+ "Click here to open TestRail: 848278</a><br>");
			Utility.testCasePass(Utility.testId, 0,Utility.comments,"Verify Reservation Task in GS Task List");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);

		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Task In Reservation", "Reservation", "Reservation", testCase, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Task In Reservation", "Reservation", "Reservation", testCase, test_description,
					test_catagory, testSteps);
		}
		try {
			
			testSteps.add("<b>======Updated Task in Reservation======</b>");
			app_logs.info("<b>======Updated Task in Reservation======</b>");
			test_description = "GS: Verify Updated Task";
			testCase="Verify that user is able to edit a task";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			
			navigation.navigateToReservationFromGuestServices(driver, testSteps);
			reservation.verifySpinerLoading(driver);
			app_logs.info(confirmationNo);
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);	
			reservation.updateTaskFromReservation(driver, taskType, updatedTaskCategoryName, updatedTaskTypeName, "", "", "", "", updatedTaskStatusName, testSteps);
			reservation.closeReservationTab(driver);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Update Task in Reservation", "Reservation", "Reservation", testCase, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Task in Reservation", "Reservation", "Reservation", testCase, test_description,
					test_catagory, testSteps);
		}
		try {
			testSteps.add("<b>======Verify Task in Task List from GS======</b>");
			app_logs.info("<b>======Verify Task in Task List from GS======</b>");
			navigation.navigateGuestservice(driver);					
			testSteps.add("Navigate to Guest Services");
			app_logs.info("Navigate to Guest Services");
			roomstatus.verifyRoomStatusTabEnabled(driver, testSteps);
			roomstatus.verifiedGuestServicesDataLoadedOrNot(driver, testSteps);			
			navigation.Nav_TaskList(driver);
			app_logs.info("Navigate To Task_List");
			testSteps.add("Navigate To Task List");
			app_logs.info("======Search and Verify Task=======");
			testSteps.add("======Search and Verify Task=======");
			tasklist.clickAllStatsBar(driver, testSteps);
			tasklist.verifyTaskWithTypeAndGuestName(driver, updatedTaskTypeName, guestFirstName, testSteps);
			tasklist.VerifyTasksStatus(driver, updatedTaskStatusName);
			/*testSteps.add("Verify that user is able to edit a task"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/852510' target='_blank'>"
					+ "Click here to open TestRail: 852510</a><br>");
			Utility.testCasePass(Utility.testId, 1,Utility.comments,"Verify Reservation Task in GS Task List");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1), Utility.comments.get(1), TestCore.TestRail_AssignToID);*/
			for(int i=0;i<Utility.statusCode.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "GS Verify Task");
			}
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, testSteps);

		}catch (Exception e) {
			Utility.catchException(driver, e, "Update Task in Reservation", "Reservation", "Reservation", testCase, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Task in Reservation", "Reservation", "Reservation", testCase, test_description,
					test_catagory, testSteps);
		}
	}
	}	
	
	@DataProvider
	public Object[][] getData() {

		return Utility.getData("GSVerifyReservationTask", gsexcel);

	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		
	}

}
