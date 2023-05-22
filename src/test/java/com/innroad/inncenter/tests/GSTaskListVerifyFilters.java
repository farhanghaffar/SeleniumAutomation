package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.By;
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

public class GSTaskListVerifyFilters extends TestCore {

	private WebDriver driver = null;
	public static String test_name = "";
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> TestName = new ArrayList<>();
	ArrayList<String> TestCategory = new ArrayList<>();
	ArrayList<String> TestDescription = new ArrayList<>();
	public static String test_description = null;
	public static String test_catagory = null;
	ArrayList<String> checkInDates = new ArrayList<>();
	ArrayList<String> checkOutDates = new ArrayList<>();
	Navigation navigation = new Navigation();
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	public static String roomClassNames = null;
	ArrayList<String> roomClassNamesList = new ArrayList<>();
	ArrayList<String> roomNumbers = new ArrayList<>();
	ArrayList<String> roomClassN = new ArrayList<>();
	CPReservationPage res = new CPReservationPage();
	RoomStatus roomStatus = new RoomStatus();
	Account account = new Account();
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
	public void gSTaskListVerifyFilters(String roomClassName, String maxAdults, String maxPersopns, String roomQuantity,
			String zone, String ratePlanName, String rate, String checkInDate, String checkOutDate, String salutation,
			String guestFirstName, String guestLastName, String paymentType,
			String cardNumber, String nameOnCard, String marketSegment, String referral, String taskFor,
			String taskCategory, String taskType, String taskDetail, String taskRemark, String taskAssignee
			) throws ParseException {
		String testCaseID="846658";
		if(Utility.getResultForCase(driver, testCaseID)) {
		Utility.initializeTestCase("846658", Utility.testId, Utility.statusCode, Utility.comments, "");
		test_name = "gSTaskListVerifyFilters";
		testDescription = "GS Task List Verification <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/846658' target='_blank'>"
				+ "Click here to open TestRail: 846658</a><br>";
		testCategory = "Verification";
		String testName = test_name;
		String createTestName = null, searchTestCase = null, filterTestCase = null, seasonStartDate = null,
				seasonEndDate = null;
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		TestName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String testCase = null;
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
			Utility.catchException(driver, e, test_description, test_catagory, "Get Dates", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Get Dates", testName, test_description,
					test_catagory, test_steps);
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
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description,
					test_catagory, test_steps);

		}		
		// Create Room Class
		try {
			roomNumbers.clear();
			roomClassNamesList.clear();
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
				roomClassNamesList.add(roomClassNames);
			app_logs.info(roomClassNamesList);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Create Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, test_steps);

		}
		try {
			String roomClassUpdated = roomClassNamesList.get(0);
			test_steps.add("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, ratePlanName, datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassUpdated, rate, "", "", "", "", true);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, test_steps);
		}
		// Create Reservation
		try {
			app_logs.info("======CP Reservation Creation=======");
			test_steps.add("======CP Reservation Creation=======");
			guestFirstName = guestFirstName + Utility.threeDigitgenerateRandomString();
			guestLastName = guestLastName + Utility.threeDigitgenerateRandomString();
			reservationNumber = res.createBasicReservation(driver, checkInDate, checkOutDate, maxAdults, "0",
					ratePlanName, salutation, guestFirstName, guestLastName, "No", paymentType, cardNumber, nameOnCard,
					marketSegment, referral, roomClassNamesList.get(0), true, true);
			test_steps.add("Reservation Created: <b>" + reservationNumber + "</>");
			app_logs.info("Reservation Created: " + reservationNumber);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName,
					test_description, test_catagory, test_steps);
		}
		try {
			navigation.navigateGuestservice(driver);
			app_logs.info("Navigate To Guest Services");
			test_steps.add("Navigate To Guest Services ");
			roomStatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomStatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			navigation.Nav_TaskList(driver);
			app_logs.info("Navigate To Task_List");
			test_steps.add("Navigate To Task List");
			Wait.wait10Second();
			taskFors = taskFor.split("\\|");
			taskCategories = taskCategory.split("\\|");
			taskAssignees = taskAssignee.split("\\|");
			taskTypes = taskType.split("\\|");
			String searchText = reservationNumber + "|" + Utility.RoomNo + "|" + zone;
			String[] search = searchText.split("\\|");
			for (int i = 0; i < taskFors.length; i++) {
				createTestName = "Create " + taskFors[i] + " Task";
				if (!Utility.insertTestName.containsKey(createTestName)) {
					Utility.insertTestName.put(createTestName, createTestName);
					Utility.reTry.put(createTestName, 0);
				} else {
					Utility.reTry.replace(createTestName, 1);
				}
				app_logs.info("======Create " + taskFors[i] + " Task=======");
				test_steps.add("======Create " + taskFors[i] + " Task=======");
				getTest_Steps.clear();
				getTest_Steps = tasklist.clickAddTaskButton(driver);
				getTest_Steps = tasklist.CreateNewTask(driver, taskFors[i], taskCategories[i], taskTypes[i], taskDetail,
						taskRemark, taskAssignees[i], search[i], getTest_Steps);
				test_steps.addAll(getTest_Steps);
				test_steps.add("Successfully Added Task with " + taskFors[i]);
				app_logs.info("Successfully Added  Task with " + taskFors[i]);
			}
		} catch (Exception e) {
			Utility.catchException(driver, e, "Create Task", "Task", "Task", createTestName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Task", "Task", "Task", createTestName, test_description,
					test_catagory, test_steps);
		}
		// Filter task
		try {
			filterTestCase = "Filters By Task Category and Verify Task";
			if (!Utility.insertTestName.containsKey(filterTestCase)) {
				Utility.insertTestName.put(filterTestCase, filterTestCase);
				Utility.reTry.put(filterTestCase, 0);
			} else {
				Utility.reTry.replace(filterTestCase, 1);
			}
			tasklist.SearchTask(driver, "");
			tasklist.clickAllStatsBar(driver, getTest_Steps);
			app_logs.info("======Filters By Task Category and Verify Task=======");
			test_steps.add("======Filters By Task Category and Verify Task=======");
			tasklist.ClickFilter(driver);
			test_steps.add("Click on Task Filters Icon");
			app_logs.info("Click on Task Filters Icon");
			tasklist.clickFiltersOptions(driver, test_steps, "Task Category");
			HashMap<String, String> filter = tasklist.clickSubFiltersOptionsOn(driver, test_steps, taskCategories[0]);
			tasklist.filterON(driver, test_steps, filter.get("Filter"));
			tasklist.ClearEnableWhenFilterON(driver, test_steps, "Task Category");
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, filter.get("SubFilterCount"));
			tasklist.clickSubFilterOptionOff(driver, test_steps, taskCategories[0]);
			tasklist.clickFiltersOptions(driver, test_steps, "Task Category");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(filterTestCase, testDescription, testCategory, test_steps);
			filterTestCase = "Filters By Task Type and Verify Task";
			if (!Utility.insertTestName.containsKey(filterTestCase)) {
				Utility.insertTestName.put(filterTestCase, filterTestCase);
				Utility.reTry.put(filterTestCase, 0);
			} else {
				Utility.reTry.replace(filterTestCase, 1);
			}
			app_logs.info("======Filters By Task Type and Verify Task=======");
			test_steps.add("======Filters By Task Type and Verify Task=======");
			Wait.wait5Second();
			tasklist.clickFiltersOptions(driver, test_steps, "Task Type");
			HashMap<String, String> filter1 = tasklist.clickSubFiltersOptionsOn(driver, test_steps, taskTypes[0]);
			tasklist.filterON(driver, test_steps, filter1.get("Filter"));
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, filter1.get("SubFilterCount"));
			tasklist.clickSubFilterOptionOff(driver, test_steps, taskTypes[0]);
			tasklist.clickFiltersOptions(driver, test_steps, "Task Type");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(filterTestCase, testDescription, testCategory, test_steps);
			filterTestCase = "Filters By Zone and Verify Task";
			if (!Utility.insertTestName.containsKey(filterTestCase)) {
				Utility.insertTestName.put(filterTestCase, filterTestCase);
				Utility.reTry.put(filterTestCase, 0);
			} else {
				Utility.reTry.replace(filterTestCase, 1);
			}
			app_logs.info("======Filters By Zone and Verify Task=======");
			test_steps.add("======Filters By Zone and Verify Task=======");
			Wait.wait5Second();
			tasklist.clickFiltersOptions(driver, test_steps, "Zone");
			HashMap<String, String> filter2 = tasklist.clickSubFiltersOptionsOn(driver, test_steps, zone);
			tasklist.filterON(driver, test_steps, filter2.get("Filter"));
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, filter2.get("SubFilterCount"));
			tasklist.clickSubFilterOptionOff(driver, test_steps, zone);
			tasklist.clickFiltersOptions(driver, test_steps, "Zone");
			filterTestCase = "Filters By Assignee and Verify Task";
			if (!Utility.insertTestName.containsKey(filterTestCase)) {
				Utility.insertTestName.put(filterTestCase, filterTestCase);
				Utility.reTry.put(filterTestCase, 0);
			} else {
				Utility.reTry.replace(filterTestCase, 1);
			}
			app_logs.info("======Filters By Assignee and Verify Task=======");
			test_steps.add("======Filters By Assignee and Verify Task=======");
			Wait.wait5Second();
			tasklist.clickFiltersOptions(driver, test_steps, "Assigned");
			HashMap<String, String> filter3 = tasklist.clickSubFiltersOptionsOn(driver, test_steps,
					taskAssignees[0].toLowerCase());
			tasklist.filterON(driver, test_steps, filter3.get("Filter"));
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, filter3.get("SubFilterCount"));
			tasklist.clickSubFilterOptionOff(driver, test_steps, taskAssignees[0].toLowerCase());
			tasklist.clickFiltersOptions(driver, test_steps, "Assigned");
			tasklist.clickFilterIcon(driver, test_steps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Search Verification", "Task", "Task", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Search Verification", "Task", "Task", testName, test_description,
					test_catagory, test_steps);
		}

		try {
			testCase = "Verify Filter- Clear/Clear All and verify User can select a date from calendar picker for Due on";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}
			tasklist.ClickFilter(driver);
			test_steps.add("Click on Task Filters Icon");
			app_logs.info("Click on Task Filters Icon");
			Wait.wait5Second();
			tasklist.clickFiltersOptions(driver, test_steps, "Task Category");
			tasklist.clickSubFiltersOptionsOn(driver, test_steps, taskCategories[0]);
			Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath("//div[contains(@class,'task-filter')]//ul//li/div//div//following-sibling::li[1]")));
			tasklist.clickClearLink(driver, test_steps, "Task Category");
			tasklist.clickFiltersOptions(driver, test_steps, "Task Category");
			tasklist.clickSubFiltersOptionsOn(driver, test_steps, taskCategories[0]);
			Utility.ScrollToViewElementINMiddle(driver, driver.findElement(By.xpath("//div[contains(@class,'task-filter')]//ul//li/div//div//following-sibling::li[1]")));
			tasklist.clickClearAllLink(driver, test_steps);
			tasklist.clickFilterIcon(driver, test_steps);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
			for (int i = 0; i < Utility.testId.size(); i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify GS task list verify filters");
			}
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Verify Filter Clear", "Task", "Task", testCase, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Filter Clear", "Task", "Task", testCase, test_description,
					test_catagory, test_steps);
		}
		// Delete Task
		try {
			app_logs.info("======Delete  Task=======");
			test_steps.add("======Delete Task=======");
			tasklist.SearchTask(driver, guestFirstName);
			tasklist.deleteTask(driver, taskTypes[0], test_steps);
			tasklist.SearchTask(driver,Utility.RoomNo);
			tasklist.deleteTask(driver, taskTypes[1], test_steps);
			tasklist.SearchTask(driver, zone);
			tasklist.deleteTask(driver, taskTypes[2], test_steps);
		} catch (Exception e) {
			Utility.catchException(driver, e, "Delete Task", "Task", "Task", testName, test_description, test_catagory,
					test_steps);
		} catch (Error e) {
		Utility.catchError(driver, e, "Delete Task", "Task", "Task", testName, test_description, test_catagory,test_steps);
		}
		// delete room Class
		try {
			test_steps.add("<b>======Delete Room Class======</b>");
			navigation.navigateToSetupfromTaskManagement(driver);
			navigation.RoomClass(driver);
			for (int i = 0; i < roomClassNamesList.size(); i++) {
				newRoomClass.searchRoomClassV2(driver, roomClassNamesList.get(i));
				app_logs.info("Click on Search Button");
				newRoomClass.deleteRoomClassV2(driver, roomClassNamesList.get(i));
				test_steps.add("All Room Class Deleted Successfully With Name: <b>" + roomClassNamesList.get(i) + " </b>");
				app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassNamesList.get(i));
			}
		} catch (Exception e) {
			Utility.catchException(driver, e, "Delete Room Class", "Room Class", "Room Class", testName,
					test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Room Class", "Room Class", "Room Class", testName, test_description,
					test_catagory, test_steps);
		}
		}
	}

	@DataProvider
	public Object[][] getData() {
		// return test data from the sheet name provided
		return Utility.getData("GSTaskListVerifyFilters", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
