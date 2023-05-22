package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

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

public class GS_TaskList_Verification extends TestCore {

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
	public static String roomClassNames=null;
	ArrayList<String> roomClassNamesList = new ArrayList<>();
	//public static String roomClassAbbrivations=null;
	ArrayList<String> roomNumbers = new ArrayList<>();
	ArrayList<String> roomClassN = new ArrayList<>();
	CPReservationPage res = new CPReservationPage();
	RoomStatus roomStatus = new RoomStatus();
	Account account = new Account();
	TaskList tasklist = new TaskList();
	TaskManagement taskmang = new TaskManagement();
	String reservationNumber=null,accountNumber=null,randomNO=null;
	String [] taskFors=null,taskAssignees=null, taskCategories=null, taskTypes=null, zones=null, taskStatus=null;
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	NightlyRate nightlyRate = new NightlyRate();
	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gSTaskListVerification(String roomClassName,String maxAdults,
			String maxPersopns, String roomQuantity, String zone,String ratePlanName, String rate,String checkInDate, String checkOutDate,String ratePlan,
			String roomClass,String salutation, String guestFirstName, String guestLastName,
			String paymentType, String cardNumber, String nameOnCard,String marketSegment,String referral, 
			String accountType,String accountName,String phoneNumber, String altPhoneNumber, String email,
			String address1, String address2, String address3,String  city, String state, String poBox,
			String taskFor,String taskCategory, String taskType, String taskDetail,
			String taskRemark, String taskAssignee, String status) throws ParseException {
		String total="34";
		caseId.clear();
		statusCode.clear();
		comments.clear();
		/*Utility.initializeTestCase("787091"+"|"+"787236"+"|"+"787237"+"|"+"787242"+"|"+"787244"
			+"|"+"787243"+"|"+"787246"+"|"+"787247"+"|"+"787248"+"|"+"787249"+"|"+"787250"+
				"|"+"787149"+"|"+"787257"+"|"+"787258"+"|"+"787259"+"|"+"787260"
			+"|"+"787262"+"|"+"787263"+"|"+"787267"+"|"+"787272"+"|"+"787273"
			+"|"+"787275"+"|"+"787279"+"|"+"787285"+"|"+"787286"+"|"+"787288"
			+"|"+"787289"+"|"+"787290"+"|"+"787090"+"|"+"787122"+"|"+"787133"
			+"|"+"787140"+"|"+"787143"+"|"+"787147"+"|"+"787148"+"|"+"787239"+"|"+"787240"+"|"+"787241"
			+"|"+"787261"+"|"+"787274"+"|"+"787116"+"|"+"787245"+"|"+"787127", caseId, statusCode,comments,"");*/
		Utility.initializeTestCase("848197", Utility.testId, Utility.statusCode, Utility.comments, "");
		test_name = "GS_TaskList_Verification";
		testDescription = "GS Task List Verification <br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/51108' target='_blank'>"
				+ "Click here to open TestRail: C51108</a><br>";
		testCategory = "Verification";
		String testName = test_name;
		String createTestName=null, searchTestCase=null, filterTestCase=null,seasonStartDate = null, seasonEndDate = null;
		ArrayList<String> sessionEndDate = new ArrayList<>();
		ArrayList<String> datesRangeList = new ArrayList<String>();
		TestName.add(testName);
		TestDescription.add(testDescription);
		TestCategory.add(testCategory);
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		String testCase=null;
		// Get checkIN and Checkout Date
		try {
			if (!(Utility.validateInput(checkInDate))&& !(Utility.validateInput(checkOutDate))) {
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
			seasonStartDate =checkInDate;
			seasonEndDate = sessionEndDate.get(0);
			app_logs.info(seasonStartDate);
			app_logs.info(seasonEndDate);
			datesRangeList = Utility.getAllDatesBetweenCheckInOutDates(seasonStartDate, seasonEndDate);
			app_logs.info(datesRangeList);
		} catch (Exception e) {
			Utility.catchException(driver, e, test_description, test_catagory, "Get Dates", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Get Dates", testName, test_description, test_catagory, test_steps);
				}
		//Login
		try {
			if (!Utility.insertTestName.containsKey(testName)) {
				Utility.insertTestName.put(testName, testName);
				Utility.reTry.put(testName, 0);
			} else {
				Utility.reTry.replace(testName, 1);
			}					 
			driver = getDriver();
			login_GS(driver);			
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");	
		
			
		} catch (Exception e) {			
			Utility.catchException(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to login", "Login", "Login", testName, test_description, test_catagory, test_steps);
			
		}
	//enable toggle	
		try {
			app_logs.info("======Enabling Toggle from Task Management=======");
			test_steps.add("======Enabling Toggle from Task Management=======");
			navigation.setup(driver);
			navigation.taskManagement_TabExist(driver);
			navigation.taskManagement(driver);
			app_logs.info("Click on Task Management");
			taskmang.setInspectionCleaningToggle(driver, true);
			app_logs.info("Successfully Enabled Toggle for Inspection");
			taskmang.setInspectionCleaningToggleFlagStatus(driver);
			app_logs.info("Successfully Set Toggle Condition Value");

		}catch (Exception e) {			
			Utility.catchException(driver, e, "Enable Toggle", "Task Management", "Task Management", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Enable Toggle", "Task Management", "Task Management", testName, test_description, test_catagory, test_steps);
			
		}
		//Create Room Class
		try {
			
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			navigation.navigateToSetupfromTaskManagement(driver);
			app_logs.info("Navigated to Setup");
			navigation.roomClass(driver);
			app_logs.info("Navigated to Room Class");
			zones=zone.split("\\|");
			for(int j=0;j<zones.length;j++) {
			randomNO=Utility.threeDigitgenerateRandomString();
			roomClassNames = roomClassName + randomNO;
			newRoomClass.createRoomClassWithZoneV2(driver, roomClassNames, roomClassNames, maxAdults, maxPersopns, roomQuantity, zones[j], test, getTest_Steps);
			roomClassN.add(roomClassNames);
			String quantity = roomQuantity;
			String roomNumber = Utility.RoomNo;
			roomNumbers.add(roomNumber);
			for (int i = 1; i < Integer.parseInt(quantity); i++) {
				roomNumber = String.valueOf(Integer.parseInt(roomNumber) + 1);
				roomNumbers.add(roomNumber);
			}
			app_logs.info("Room NO Are: " + roomNumbers);
			newRoomClass.closeRoomClassTabV2(driver, roomClassNames);
			test_steps.add("Room Class created successfully " + roomClassNames);
			roomClassNamesList.add(roomClassNames);
			}			
			app_logs.info(roomClassNamesList);
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Failed to Create Room Class", "Room Class", "Room Class", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Create Room Class", "Room Class", "Room Class", testName, test_description, test_catagory, test_steps);
			
		}
		try {
			String roomClassUpdated=roomClassNamesList.get(0)+"|"+roomClassNamesList.get(1);
			test_steps.add("<b>======Start Updating Rate Plan ======</b>");
			nightlyRate.createSeasonForExistingRatePlan(driver, test_steps, ratePlanName, datesRangeList,
					seasonStartDate, seasonEndDate, "", roomClassUpdated, rate, "", "", "", "", true);
		}catch (Exception e) {
			Utility.catchException(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Update Season", "Rate Plan", "Rate Plan", testName, test_description,
					test_catagory, test_steps);
		}
		//Create Reservation
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
			
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Create Reservation", "Reservation", "Reservation", testName, test_description, test_catagory, test_steps);
			
		}
		
		try {	
			app_logs.info("======Create Account=======");
			test_steps.add("======Create Account=======");
			navigation.navigateToAccounts(driver, getTest_Steps);
			accountNumber=account.createAccount(driver, getTest_Steps, test, accountType, accountName, guestFirstName, guestLastName, 
					phoneNumber, altPhoneNumber, email, marketSegment, referral, address1, address2, address3, city, state, poBox);
			account.closeAccountTab(driver);
			test_steps.add("Created  Account " + accountNumber);
			app_logs.info("Created  Account " + accountNumber);
		}
		catch (Exception e) {			
			Utility.catchException(driver, e, "Failed to Create Account", "Account", "Account", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Failed to Create Account", "Account", "Account", testName, test_description, test_catagory, test_steps);
			
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
			taskFors=taskFor.split("\\|");
			taskCategories=taskCategory.split("\\|");
			taskAssignees=taskAssignee.split("\\|");
			taskTypes=taskType.split("\\|");
			String searchText=reservationNumber+"|"+accountNumber+"|"+roomNumbers.get(0)+"|"+zones[1];				
			String[] search=searchText.split("\\|");
			for(int i=0;i<taskFors.length;i++) {
			createTestName="Create "+taskFors[i]+" Task";
			if (!Utility.insertTestName.containsKey(createTestName)) {
				Utility.insertTestName.put(createTestName, createTestName);
				Utility.reTry.put(createTestName, 0);
			} else {
				Utility.reTry.replace(createTestName, 1);
			}	
			app_logs.info("======Create "+taskFors[i]+" Task=======");
			test_steps.add("======Create "+taskFors[i]+" Task=======");
			getTest_Steps.clear();
			getTest_Steps = tasklist.clickAddTaskButton(driver);
			getTest_Steps = tasklist.CreateNewTask(driver, taskFors[i], taskCategories[i], taskTypes[i], taskDetail, taskRemark,
					taskAssignees[i], search[i], getTest_Steps);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Added Task with " +taskFors[i]);
			app_logs.info("Successfully Added  Task with " +taskFors[i]);												
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(createTestName, testDescription, testCategory, test_steps);	
			}
			
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Create Task", "Task", "Task", createTestName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Create Task", "Task", "Task", createTestName, test_description, test_catagory, test_steps);
			
		}
		//Search Verification
		try {
			
			searchTestCase="Verify Searching with different task";
			if (!Utility.insertTestName.containsKey(searchTestCase)) {
				Utility.insertTestName.put(searchTestCase, searchTestCase);
				Utility.reTry.put(searchTestCase, 0);
			} else {
				Utility.reTry.replace(searchTestCase, 1);
			}	
			
			app_logs.info("======Search and Verified By Room No=======");
			test_steps.add("======Search and Verified By Room No=======");
			tasklist.SearchTask(driver, roomNumbers.get(0));
			tasklist.verifyTask(driver, roomNumbers.get(0), test_steps);
			app_logs.info("======Search and Verified By Guest Name=======");
			test_steps.add("======Search and Verified By Guest Name=======");
			tasklist.SearchTask(driver, guestFirstName);
			tasklist.verifyTask(driver, guestFirstName, test_steps);
			app_logs.info("======Search and Verified By Zone=======");
			test_steps.add("======Search and Verified By Zone=======");
			tasklist.SearchTask(driver, zones[1]);
			tasklist.verifyTask(driver, zones[1], test_steps);
			app_logs.info("======Search and Verified By Account=======");
			test_steps.add("======Search and Verified By Account=======");
			tasklist.SearchTask(driver, accountName);
			tasklist.verifyTask(driver, accountName, test_steps);
			app_logs.info("======Search and Verified By Assignee=======");
			test_steps.add("======Search and Verified By Assignee=======");
			tasklist.SearchTask(driver, taskAssignees[0]);
			tasklist.verifyTaskAssignee(driver, taskAssignees[0].toLowerCase().trim(), test_steps);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(searchTestCase, testDescription, testCategory, test_steps);
			
			
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Search Verification", "Task", "Task", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Search Verification", "Task", "Task", testName, test_description, test_catagory, test_steps);
			
		}
		//Filter task
		try {
			filterTestCase="Filters By Task Category and Verify Task";
			if (!Utility.insertTestName.containsKey(filterTestCase)) {
				Utility.insertTestName.put(filterTestCase, filterTestCase);
				Utility.reTry.put(filterTestCase, 0);
			} else {
				Utility.reTry.replace(filterTestCase, 1);
			}	
			tasklist.SearchTask(driver,"");
			tasklist.clickAllStatsBar(driver, getTest_Steps);
			app_logs.info("======Filters By Task Category and Verify Task=======");
			test_steps.add("======Filters By Task Category and Verify Task=======");
			tasklist.ClickFilter(driver);
			test_steps.add("Click on Task Filters Icon");
			app_logs.info("Click on Task Filters Icon");
			/*Utility.testCasePass(statusCode, 27,comments,"Successfully Verified User should be able to click on the filter icon to expand / collapse filter menu");
			test_steps.add("Successfully Verified User should be able to click on the filter icon to expand / collapse filter menu"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787290' target='_blank'>"
					+ "Click here to open TestRail: C787290</a><br>");*/
			tasklist.clickFiltersOptions(driver, test_steps, "Task Category");
			HashMap<String, String> filter=tasklist.clickSubFiltersOptionsOn(driver, test_steps, taskCategories[0]);
			/*Utility.testCasePass(statusCode, 17,comments,"Successfully Verified  select Task Category from the drop down");
			test_steps.add("Successfully Verified  select Task Category from the drop down"
			+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787263' target='_blank'>"
			+ "Click here to open TestRail: C787263</a><br>");*/
			tasklist.filterON(driver, test_steps, filter.get("Filter"));
			/*Utility.testCasePass(statusCode, 15,comments,"Successfully Verified Count for the No.of Filters applied should reflect on the Filter icon");
			test_steps.add("Successfully Verified  select Task Category from the drop down"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787260' target='_blank'>"
				+ "Click here to open TestRail: C787260</a><br>");
			Utility.testCasePass(statusCode, 22,comments,"Successfully Verified Verify the count of the results on the quick access filter");
			test_steps.add("Successfully Verified Verify the count of the results on the quick access filter"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787279' target='_blank'>"
				+ "Click here to open TestRail: C787279</a><br>");*/
			tasklist.ClearEnableWhenFilterON(driver, test_steps, "Task Category");
			/*Utility.testCasePass(statusCode, 14,comments,"Successfully Verified clear the filters applied to each section");
			test_steps.add("Successfully Verified clear the filters applied to each section"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787259' target='_blank'>"
				+ "Click here to open TestRail: C787259</a><br>");*/
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, filter.get("SubFilterCount"));
			/*Utility.testCasePass(statusCode, 16,comments,"Task count should be updated based on the Filters applied");
			test_steps.add("Task count should be updated based on the Filters applied"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787262' target='_blank'>"
					+ "Click here to open TestRail: C787262</a><br>");
			Utility.testCasePass(statusCode, 31,comments,"Verified Result After Filter");
			test_steps.add("Verified Result After Filter"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787140' target='_blank'>"
					+ "Click here to open TestRail: C787140</a><br>");*/
			tasklist.clickSubFilterOptionOff(driver, test_steps, taskCategories[0]);
			tasklist.clickFiltersOptions(driver, test_steps, "Task Category");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(filterTestCase, testDescription, testCategory, test_steps);
			
			
			filterTestCase="Filters By Task Type and Verify Task";
			if (!Utility.insertTestName.containsKey(filterTestCase)) {
				Utility.insertTestName.put(filterTestCase, filterTestCase);
				Utility.reTry.put(filterTestCase, 0);
			} else {
				Utility.reTry.replace(filterTestCase, 1);
			}	
			app_logs.info("======Filters By Task Type and Verify Task=======");
			test_steps.add("======Filters By Task Type and Verify Task=======");
			
			tasklist.clickFiltersOptions(driver, test_steps, "Task Type");
			HashMap<String, String> filter1=tasklist.clickSubFiltersOptionsOn(driver, test_steps, taskTypes[0]);
			tasklist.filterON(driver, test_steps, filter1.get("Filter"));
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, filter1.get("SubFilterCount"));
			tasklist.clickSubFilterOptionOff(driver, test_steps, taskTypes[0]);
			tasklist.clickFiltersOptions(driver, test_steps, "Task Type");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(filterTestCase, testDescription, testCategory, test_steps);
			
			filterTestCase="Filters By Zone and Verify Task";
			if (!Utility.insertTestName.containsKey(filterTestCase)) {
				Utility.insertTestName.put(filterTestCase, filterTestCase);
				Utility.reTry.put(filterTestCase, 0);
			} else {
				Utility.reTry.replace(filterTestCase, 1);
			}	
			
			app_logs.info("======Filters By Zone and Verify Task=======");
			test_steps.add("======Filters By Zone and Verify Task=======");
			
			tasklist.clickFiltersOptions(driver, test_steps, "Zone");
			HashMap<String, String> filter2=tasklist.clickSubFiltersOptionsOn(driver, test_steps, zones[0]);
			tasklist.filterON(driver, test_steps, filter2.get("Filter"));
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, filter2.get("SubFilterCount"));
			tasklist.clickSubFilterOptionOff(driver, test_steps, zones[0]);
			tasklist.clickFiltersOptions(driver, test_steps, "Zone");
			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(filterTestCase, testDescription, testCategory, test_steps);
			
			filterTestCase="Filters By Assignee and Verify Task";
			if (!Utility.insertTestName.containsKey(filterTestCase)) {
				Utility.insertTestName.put(filterTestCase, filterTestCase);
				Utility.reTry.put(filterTestCase, 0);
			} else {
				Utility.reTry.replace(filterTestCase, 1);
			}	
			
			app_logs.info("======Filters By Assignee and Verify Task=======");
			test_steps.add("======Filters By Assignee and Verify Task=======");
			
			tasklist.clickFiltersOptions(driver, test_steps, "Assigned");
			HashMap<String, String> filter3=tasklist.clickSubFiltersOptionsOn(driver, test_steps, taskAssignees[0].toLowerCase());
			tasklist.filterON(driver, test_steps, filter3.get("Filter"));
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, filter3.get("SubFilterCount"));
			tasklist.clickSubFilterOptionOff(driver, test_steps, taskAssignees[0].toLowerCase());
			tasklist.clickFiltersOptions(driver, test_steps, "Assigned");
			tasklist.clickFilterIcon(driver,test_steps);
			/*Utility.testCasePass(statusCode, 12,comments,"Successfully Verified Filter options should be based on Search Criteria");
			test_steps.add("Successfully Verified Filter options should be based on Search Criteria"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787257' target='_blank'>"
					+ "Click here to open TestRail: C787257</a><br>");
			Utility.testCasePass(statusCode, 13,comments,"Successfully Verify the toggle functionality of Tasklist filter");		
			test_steps.add("Successfully Verify the toggle functionality of Tasklist filter"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787258' target='_blank'>"
					+ "Click here to open TestRail: C787258</a><br>");
			Utility.testCasePass(statusCode, 33,comments,"Verify tasks for all given ranges and filter functionality");
			test_steps.add("Verify tasks for all given ranges and filter functionality"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787147' target='_blank'>"
					+ "Click here to open TestRail: C787147</a><br>");*/
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(filterTestCase, testDescription, testCategory, test_steps);
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Search Verification", "Task", "Task", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Search Verification", "Task", "Task", testName, test_description, test_catagory, test_steps);			
		}
		
	   //Change Status
		try {
			 testCase="Update Status of Task";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}	
			app_logs.info("====== Update Status from task list=======");
			test_steps.add("====== Update Status from task list=======");	
			taskStatus=status.split("\\|");			
			tasklist.clickQuickStatesAndUpdateTaskStatus(driver, taskStatus[0], taskStatus[2], guestFirstName, test_steps);
			tasklist.clickAllStatsBar(driver, test_steps);
			tasklist.SearchTask(driver, guestFirstName);
			tasklist.verifyUpdatedTask(driver, taskStatus[2], test_steps);
		/*	Utility.testCasePass(statusCode, 28,comments,"Verify the Tasks after status change from tasklist");
			test_steps.add("Verify the Tasks after status change from tasklist"
			+"<a href='https://innroad.testrail.io/index.php?/cases/view/787090' target='_blank'>"
					+ "Click here to open TestRail: C787090</a><br>");*/
			app_logs.info("Update Status of task "+guestFirstName+"from" +taskStatus[0]+ "to" +taskStatus[2]);
			test_steps.add("Update Status of task "+guestFirstName+"from" +taskStatus[0]+ "to" +taskStatus[2]);	
			app_logs.info("====== Update Status from Bulk Action=======");
			test_steps.add("====== Update Status from Bulk Action=======");	
			tasklist.SearchTask(driver, accountName);
			Wait.waitforPageLoad(10, driver);
			tasklist.selectTask(driver, taskTypes[1],test_steps);
			tasklist.verifyEnableBulkActionButton(driver, test_steps);
			/*Utility.testCasePass(statusCode, 29, comments, "If a task is selected, the bulk action button");
			test_steps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/787122' target='_blank'>"
				+ "Click here to open TestRail: C787122</a><br>");
			tasklist.verifyDeleteTaskButton(driver);
			Utility.testCasePass(statusCode, 32, comments, "Delete task tabs should get enabled.");
			test_steps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/787143' target='_blank'>"
				+ "Click here to open TestRail: C787143</a><br>");
			*/
			tasklist.ClickBulkAction(driver);
			test_steps.add("Click Bulk Action");
			getTest_Steps=	tasklist.ChangeStatus(driver, taskStatus[1], getTest_Steps);
			Wait.waitforPageLoad(30, driver);
			/*Utility.testCasePass(statusCode, 18,comments,"Successfully Verified Change Status for multiple Tasks using Bulk status option");
			test_steps.add("Successfully Verified Change Status for multiple Tasks using Bulk status option"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787267' target='_blank'>"
					+ "Click here to open TestRail: C787267</a><br>");*/
			tasklist.SearchTask(driver, accountName);
			tasklist.verifyUpdatedTask(driver, taskStatus[1], test_steps);
			app_logs.info("Update Status of task "+accountName+"from" +taskStatus[0]+ "to" +taskStatus[1]);
			test_steps.add("Update Status of task "+accountName+"from" +taskStatus[0]+ "to" +taskStatus[1]);				
			/*Utility.testCasePass(statusCode,0,comments,"Verify  changed  status of the tasks from To-Do to inspection");
			test_steps.add("Verify  changed  status of the tasks from To-Do to inspection"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787091' target='_blank'>"
				+ "Click here to open TestRail: C787091</a><br>");
			test_steps.add("Verify the functionality of bulk action"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787148' target='_blank'>"
				+ "Click here to open TestRail: C787148</a><br>");
			Utility.testCasePass(statusCode,34,comments,"Verify the functionality of bulk action");
			test_steps.add("Verify the functionality of bulk action"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787148' target='_blank'>"
				+ "Click here to open TestRail: C787148</a><br>");*/
			Utility.testCasePass(Utility.statusCode,0,Utility.comments,"GS-Task List-Verify the functionality of bulk action");
			test_steps.add("GS-Task List-Verify the functionality of bulk action"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848197' target='_blank'>"
				+ "Click here to open TestRail: 848197</a><br>");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0), Utility.comments.get(0), TestCore.TestRail_AssignToID);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Change Status", "Task", "Task", testCase, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Change Status", "Task", "Task", testCase, test_description, test_catagory, test_steps);			
		}
		//Verify Quick Stats	
	
		try
		{
			testCase="Verify Task List Quick Stats";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}	
			app_logs.info("====== Verify To Do Task=======");
			test_steps.add("====== Verify To Do Task=======");		
			tasklist.SearchTask(driver, " ");
			tasklist.clickToStatsBar(driver, test_steps);
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, tasklist.getToDoCount(driver));
			/*Utility.testCasePass(statusCode, 7,comments,"Successfully Verified To Do Task");*/
			app_logs.info("====== Verify Inspection Task=======");
			test_steps.add("====== Verify Inspection Task=======");		
			tasklist.clickInspectionStatsBar(driver, test_steps);
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, tasklist.getInspectionCount(driver));
			/*Utility.testCasePass(statusCode, 8,comments,"Successfully Verified All fields should be sorted using the Inspection from quick stats");
			test_steps.add("Successfully Verified All fields should be sorted using the Inspection from quick stats"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787248' target='_blank'>"
					+ "Click here to open TestRail: C787248</a><br>");*/
			app_logs.info("====== Verify Done Task=======");
			test_steps.add("====== Verify Done Task=======");		
			tasklist.clickDoneStatsBar(driver, test_steps);
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, tasklist.getDoneCount(driver));
			/*Utility.testCasePass(statusCode, 9,comments,"Successfully Verified All fields should be sorted using the Done from quick stats");
			test_steps.add("Successfully Verified All fields should be sorted using the Done from quick stats"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787249' target='_blank'>"
					+ "Click here to open TestRail: C787249</a><br>");		*/	
			app_logs.info("====== Verify All Task=======");
			test_steps.add("====== Verify All Task=======");	
			tasklist.clickAllStatsBar(driver, test_steps);
			tasklist.verifytaskCountAsPerFilter(driver, test_steps, tasklist.getAllCount(driver));
			/*Utility.testCasePass(statusCode, 10,comments,"Successfully Verified All fields can sorted using the ALL");
			test_steps.add("Verify the functionality of bulk action"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787250' target='_blank'>"
					+ "Click here to open TestRail: C787250</a><br>");
			Utility.testCasePass(statusCode, 6,comments,"Successfully Verified functionality of Quick Stats in Tasklist Page");
			test_steps.add("Successfully Verified functionality of Quick Stats in Tasklist Page"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787246' target='_blank'>"
					+ "Click here to open TestRail: C787246</a><br>");*/
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
			
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Verify Quick Stats", "Task", "Task", testCase, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Quick Stats", "Task", "Task", testCase, test_description, test_catagory, test_steps);			
		}
		
	
		try {
			testCase="Verify auto suggest value";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}	
			
			getTest_Steps.clear();
			getTest_Steps = tasklist.clickAddTaskButton(driver);
			test_steps.addAll(getTest_Steps);
		    tasklist.verifyAutoSuggestNewTask(driver, taskFors[0], guestLastName, test_steps);
	      /* Utility.testCasePass(statusCode, 35,comments,"Verify auto suggest by Guest Last name");
			test_steps.add("Verify auto suggest by Guest Last name"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787239' target='_blank'>"
					+ "Click here to open TestRail: C787239</a><br>");*/
			
			tasklist.verifyAutoSuggestNewTask(driver, taskFors[0], guestFirstName, test_steps);
		   /* Utility.testCasePass(statusCode, 38,comments,"Verify auto suggest by Guest First name");
				test_steps.add("Verify auto suggest by Guest First name"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787116' target='_blank'>"
						+ "Click here to open TestRail: C787116</a><br>");*/
				
		    tasklist.verifyAutoSuggestNewTask(driver, taskFors[1], accountNumber, test_steps);
		    /*Utility.testCasePass(statusCode, 36,comments,"Verify auto suggest by Account number");
			test_steps.add("Verify auto suggest by Account number"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787241' target='_blank'>"
					+ "Click here to open TestRail: C787241</a><br>");*/
		    tasklist.verifyAutoSuggestNewTask(driver, taskFors[1], accountName, test_steps);
		   /* Utility.testCasePass(statusCode, 37,comments,"Verify auto suggest by Account name");
			test_steps.add("Verify auto suggest by Account name"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787240' target='_blank'>"
					+ "Click here to open TestRail: C787240</a><br>");*/
			
			tasklist.clickTaskDueOn(driver, test_steps);
			tasklist.selectTaskDueDate(driver, test_steps, checkOutDate);
			String dueDate=tasklist.getTaskDueOnDate(driver);
			dueDate=Utility.parseDate(dueDate, "MM/dd/yyyy", "dd/MM/yyyy");
			Utility.verifyText(checkOutDate, dueDate, "Failed to verify due date ", getTest_Steps, app_logs);
			
			/*Utility.testCasePass(statusCode, 39,comments,"User can select a date from calendar picker for Due on");
			test_steps.add("User can select a date from calendar picker for Due on"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787274' target='_blank'>"
						+ "Click here to open TestRail: C787274</a><br>");	*/	
			
			
			tasklist.addTaskPopup_CloseButton(driver);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Verify Quick Stats", "Task", "Task", testCase, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Quick Stats", "Task", "Task", testCase, test_description, test_catagory, test_steps);			
		}
		
		
		try {
			testCase="Verify Filter- Clear/Clear All and verify User can select a date from calendar picker for Due on";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}	
			tasklist.ClickFilter(driver);
			test_steps.add("Click on Task Filters Icon");
			app_logs.info("Click on Task Filters Icon");
			tasklist.clickFiltersOptions(driver, test_steps, "Task Category");
		    tasklist.clickSubFiltersOptionsOn(driver, test_steps, taskCategories[0]);
			tasklist.clickClearLink(driver, test_steps, "Task Category");
			//tasklist.filterON(driver, getTest_Steps, "");
			tasklist.clickFiltersOptions(driver, test_steps, "Task Category");
			tasklist.clickSubFiltersOptionsOn(driver, test_steps, taskCategories[0]);
			tasklist.clickClearAllLink(driver, test_steps);
			tasklist.clickFilterIcon(driver,test_steps);
			/*Utility.testCasePass(statusCode, 38,comments,"Verify Filter section should be cleared only after clicking on Clear/Clear All");
			test_steps.add("Verify Filter section should be cleared only after clicking on Clear/Clear All"
						+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787261' target='_blank'>"
						+ "Click here to open TestRail: C787261</a><br>");				*/
			
				
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, testDescription, testCategory, test_steps);
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Verify Filter Clear", "Task", "Task", testCase, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Verify Filter Clear", "Task", "Task", testCase, test_description, test_catagory, test_steps);			
		}
	//Delete Task	
		try {
			
			app_logs.info("======Delete  Task=======");
			test_steps.add("======Delete Task=======");
			tasklist.SearchTask(driver, guestFirstName);
			tasklist.deleteTask(driver, taskTypes[0], test_steps);
			/*Utility.testCasePass(statusCode, 11,comments,"Successfully deleted task");
			test_steps.add("Verify the functionality of deleting  a task"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787149' target='_blank'>"
					+ "Click here to open TestRail: C787149</a><br>");*/
			tasklist.SearchTask(driver, accountName);
			tasklist.deleteTask(driver, taskTypes[1], test_steps);
			tasklist.SearchTask(driver, roomNumbers.get(0));
			tasklist.deleteTask(driver, taskTypes[2], test_steps);
			tasklist.SearchTask(driver, zones[1]);
			tasklist.deleteTask(driver, taskTypes[3], test_steps);
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Delete Task", "Task", "Task", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Task", "Task", "Task", testName, test_description, test_catagory, test_steps);			
		}
		//delete room Class
		try {
			test_steps.add("<b>======Delete Room Class======</b>");
			navigation.navigateToSetupfromTaskManagement(driver);			
			navigation.RoomClass(driver);
			for(int i=0;i<roomClassNamesList.size();i++) {
				newRoomClass.searchRoomClassV2(driver, roomClassNamesList.get(i));
				app_logs.info("Click on Search Button");
				newRoomClass.deleteRoomClassV2(driver, roomClassNamesList.get(i));
				test_steps.add("All Room Class Deleted Successfully With Name: <b>" +roomClassNamesList.get(i) + " </b>");
				app_logs.info("All Room Class Deleted Successfully With Name: " + roomClassNamesList.get(i));
			}
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Delete Room Class", "Room Class", "Room Class", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Room Class", "Room Class", "Room Class", testName, test_description, test_catagory, test_steps);			
		}
		
	//delete account
		try {			
			test_steps.add("<b>======Delete Account======</b>");
			navigation.navigateToSetupfromRateGrid(driver);
			navigation.accounts(driver);
			account.searchForAnAccount(driver, getTest_Steps, accountType, accountName);
			account.deleteAllAccount(driver, accountName, getTest_Steps);
			test_steps.add("Delete Account: <b>" +accountName + " </b>");
			app_logs.info("Delete Account: " + accountName);
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testCategory, test_steps);
		}catch (Exception e) {			
			Utility.catchException(driver, e, "Delete Account", "Account", "Account", testName, test_description, test_catagory, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, "Delete Account", "Account", "Account", testName, test_description, test_catagory, test_steps);			
		}
		
		}
	@DataProvider
	public Object[][] getData() {

		// return test data from the sheet name provided

		return Utility.getData("GS_TaskList_Verification", envLoginExcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		 driver.quit();
	//	Utility.updateTestCaseAndCloseDriver(driver, caseId, statusCode, comments,TestCore.TestRail_AssignToID);
	//	Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);
	}

}
