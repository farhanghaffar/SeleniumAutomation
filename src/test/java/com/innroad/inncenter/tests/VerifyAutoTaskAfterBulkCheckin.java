package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.CPReservationPage;
import com.innroad.inncenter.pageobjects.Create_Reservation;
import com.innroad.inncenter.pageobjects.Groups;
import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.NightlyRate;
import com.innroad.inncenter.pageobjects.ReservationSearch;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class VerifyAutoTaskAfterBulkCheckin extends TestCore {
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
	CPReservationPage reservationPage = new CPReservationPage();
	ReservationSearch rsvSearch = new ReservationSearch();
	TaskManagement taskmang = new TaskManagement();
	ReservationSearch revSearch = new ReservationSearch();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	 NightlyRate nightlyRate= new NightlyRate();
	 Create_Reservation reservation= new Create_Reservation();
	Groups group = new Groups();
	String roomClassNames = null, confirmationNo = null,seasonStartDate=null,seasonEndDate=null;
	ArrayList<String> comments = new ArrayList<>();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getFinalData", groups = "GuestServices")
	public void verifyAutoTaskAfterBulkCheckin(String checkInDate, String checkOutDate, String roomClassName,
			String maxAdults, String maxPersopns, String roomQuantity, String ratePlanName, String rate,String salutation,
			String guestFirstName, String guestLastName, String paymentType,String cardNumber, String nameOnCard, String marketSegment, String referral,
			 String child,String categoryName, String taskName) throws ParseException {
		String testCaseID="850191";
		if(Utility.getResultForCase(driver, testCaseID)) {
		test_name = "Verify Activity log for auto generated tasks upon bulk checkin";
		test_catagory = "Verification";
		testName = test_name;
		test_description = "verifyAutoTaskAfterBulkCheckin<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/850191' target='_blank'>"
				+ "Click here to open TestRail: 850191</a><br>";
		Utility.initializeTestCase("850191", Utility.testId, Utility.statusCode, Utility.comments, "");
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		ArrayList<String> checkInDates = new ArrayList<>();
		ArrayList<String> checkOutDates = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		ArrayList<String> sessionEndDate = new ArrayList<>();
		HashMap<String, String> getDueTimeOfTask = new HashMap<String, String>();
		String dueDate=null;
		// Login
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
			seasonStartDate=checkInDate;
			seasonEndDate=sessionEndDate.get(0);
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
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(checkInDate, checkOutDate);
			app_logs.info(datesRangeList);
			dueDate=datesRangeList.get(1);
			app_logs.info(dueDate);
			
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

		try
		{
			testSteps.add("========== Delete Category ==========");
			app_logs.info("========== Delete Category ==========");			
			navigation.taskManagement_TabExist(driver);
			testSteps.add("Task Management Tab Exist");
			navigation.taskManagement(driver);
			testSteps.add("Click on Task Management");
			app_logs.info("Click on Task Management");
			taskmang.deleteAllCategories(driver, categoryName,testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Delete Category", "Task Management ", "Task Management", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Category", "Task Management", "Task Management", testName, test_description,
					test_catagory, testSteps);
		}
		
		try
		{
			testSteps.add("========== Create Category ==========");
			app_logs.info("========== Create Category ==========");		
			getDueTimeOfTask=taskmang.createTask(driver, true, categoryName, taskName, true, "Every Day", true, testSteps);			
			app_logs.info(getDueTimeOfTask);
			
		}catch (Exception e) {
			Utility.catchException(driver, e, "Create Category and Task", "Task Management ", "Task Management", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Category and Task", "Task Management ", "Task Management", testName, test_description,
					test_catagory, testSteps);
		}
		
		// Create Season
		try {
			testSteps.add("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, testSteps, ratePlanName, datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassNames, rate, "", "", "", "", true);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, testSteps);
		}
		

		// Create Reservation
		try {
			testSteps.add("<b>==========Start Creating Reservation==========</b>");
			app_logs.info("==========Start Creating Reservation==========");
			confirmationNo=reservationPage.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, child, 
					ratePlanName, salutation, guestFirstName, guestLastName, "No",
					paymentType, cardNumber, nameOnCard, marketSegment, referral, roomClassName, true,true);		
			} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		//Bulk Checkin
		try {
			rsvSearch.basicSearchResNumber(driver, confirmationNo);
			getTest_Steps=revSearch.Bulkcheckin(driver, "", getTest_Steps, true);
			testSteps.addAll(getTest_Steps);
			Wait.wait60Second();
		}catch (Exception e) {
			Utility.catchException(driver, e, "Bulk Check In On  Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "FBulk Check In On  Reservation", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}
		try {
			revSearch.basicSearch_WithResNumber(driver, confirmationNo);	
			ArrayList<String> taskNames= Utility.splitInputData(taskName);
			for(Map.Entry<String, String> set: getDueTimeOfTask.entrySet()){
				dueDate=Utility.parseDate(dueDate, "dd/MM/yyyy", "MM/dd/ yy");
				String finaldueDateTime=dueDate+" "+getDueTimeOfTask.get(set.getKey());
				app_logs.info(finaldueDateTime);
				reservationPage.verifyReservationTask(driver, set.getKey(), "Automatically generated at checkin",finaldueDateTime,"To Do", testSteps,"");				
			}
			revSearch.closeReservation(driver);	
			app_logs.info("Close the Reservation");		
			
			/*testSteps.add("Verify Activity log for auto generated tasks upon bulk checkin" + 
				        "<br><a href='https://innroad.testrail.io/index.php?/cases/view/850191' target='_blank'>"
								+ "Click here to open TestRail: 850191</a>");    		 
			 Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Verify Activity log for auto generated tasks upon bulk checkin");
			 Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);*/
			comments.add("Verify Activity log for auto generated tasks upon bulk checkin");
			 for(int i=0;i<Utility.testId.size();i++) {
					Utility.testCasePass(Utility.statusCode, i, Utility.comments, comments.get(i));
				}
	
		}catch (Exception e) {
			Utility.catchException(driver, e, "Verify Auto Task ON Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Auto Task On  Reservation", "Reservation", "Reservation", testName, test_description,
					test_catagory, testSteps);
		}		
		
	
		try
		{
			testSteps.add("========== Delete Category ==========");	
			navigation.clickSetup(driver);
			testSteps.add("Navigated to Setup");
			navigation.taskManagement_TabExist(driver);
			testSteps.add("Task Management Tab Exist");
			navigation.taskManagement(driver);
			testSteps.add("Click on Task Management");
			app_logs.info("Click on Task Management");
			taskmang.deleteAllCategories(driver, categoryName,testSteps);
			navigation.navigateToSetupfromTaskManagement(driver);
			testSteps.add("========== Delete Room Class ==========");	
			navigation.RoomClass(driver);
			newRoomClass.searchRoomClassV2(driver, roomClassName);
			testSteps.add("Click on Search Button");
			app_logs.info("Click on Search Button");
			newRoomClass.deleteAllRoomClassV2(driver, roomClassName);
			testSteps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassName + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassName);
			 RetryFailedTestCases.count = Utility.reset_count;
		     Utility.AddTest_IntoReport(testName, test_description, test_catagory, testSteps);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Delete Category", "Task Management", "Task Management", testName,
					test_description, test_catagory, testSteps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Category", "Task Management", "Task Management", testName, test_description,
					test_catagory, testSteps);
		}
		}
	}

	@DataProvider
	public Object[][] getData() {

		return Utility.getData("VerifyAutoTaskAfterBulkCheckin", gsexcel);

	}
	
	@DataProvider
	public Object[][] getDataOne() {
		return Utility.getData("GSVerifyArrivalDueScenarios", gsexcel);

	}
	@DataProvider
		public Object[][] getFinalData()
		{
			 return Utility.combine(getDataOne(),getData());
		} 
		
		

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
		Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
				Utility.comments, TestCore.TestRail_AssignToID);
	}

}
