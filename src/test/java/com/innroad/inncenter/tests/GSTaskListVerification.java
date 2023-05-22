package com.innroad.inncenter.tests;

import static org.junit.Assert.assertThat;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.util.ArrayList;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;
import com.innroad.inncenter.waits.Wait;

public class GSTaskListVerification extends TestCore{
	private WebDriver driver = null;
	public static String test_name = "";
	public static String test_description = "";
	public static String test_catagory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	ArrayList<String> caseId=new ArrayList<String>();
	ArrayList<String> statusCode=new ArrayList<String>();
	ArrayList<String> comments=new ArrayList<String>();
	TaskList tasklist = new TaskList();
	TaskManagement taskmang = new TaskManagement();
	Navigation nav = new Navigation();
	RoomStatus roomStatus = new RoomStatus();
	Navigation navigation = new Navigation();
	String [] taskCategory=null, taskType=null;
	@BeforeTest(alwaysRun=true)
	public void checkRunMode() {
		String testName = this.getClass().getSimpleName().trim();
		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, envLoginExcel))
			throw new SkipException("Skipping the test - " + testName);
	}
	
	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gsRoomStatusReport(String taskFors, String taskCategories,String  taskTypes,String taskDetail, String taskRemark,
			String taskAssignees,String status) throws ParseException {
		caseId.clear();
		statusCode.clear();
		comments.clear();
		Utility.initializeTestCase("787268"+"|"+"787280"+"|"+"787287"+"|"+"787284"+"|"+"787269"+"|"+"787255", caseId, statusCode,comments,"");
		test_name = "GSTaskListVerification";
		test_description = "GSTaskListVerification ";
		test_catagory = "GS";
		String testName = test_name;

		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");
		
		try {
			driver = getDriver();
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");
		}catch(Exception e)
		{
			Utility.catchException(driver, e, test_description, test_catagory, "Login", testName, test_description, test_catagory, test_steps);
		}catch(Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Login", testName, test_description, test_catagory, test_steps);
		}
		String testCase=null;
		String sizeOfRoomTile=null;
		try {
			testCase="Validation message to be displayed when Saving a NEW Task without filling mandatory fields";
			if (!Utility.insertTestName.containsKey(testCase)) {
				Utility.insertTestName.put(testCase, testCase);
				Utility.reTry.put(testCase, 0);
			} else {
				Utility.reTry.replace(testCase, 1);
			}	
			
			navigation.navigateGuestservice(driver);
			test_steps.add("Navigate to Guest Services");
			app_logs.info("Navigate to Guest Services");
			roomStatus.verifyRoomStatusTabEnabled(driver,test_steps);
			roomStatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			navigation.Nav_TaskList(driver);
			app_logs.info("Navigate To Task_List");
			test_steps.add("Navigate To Task List");
			Wait.waitforPageLoad(50, driver);
			
			getTest_Steps.clear();
			getTest_Steps = tasklist.clickAddTaskButton(driver);
			test_steps.addAll(getTest_Steps);
			tasklist.clickSaveTaskButton(driver, test_steps);
			tasklist.verifyMandatoryFieldForTask(driver, test_steps);
			tasklist.addTaskPopup_CloseButton(driver);
			Utility.testCasePass(statusCode, 0, comments, "Validation message to be displayed when Saving a NEW Task without filling mandatory fields");
			test_steps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/787268' target='_blank'>"
				+ "Click here to open TestRail: C787268</a><br>");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase, test_description, test_catagory, test_steps);	
        }catch(Exception e)
		{
			Utility.catchException(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}catch(Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}
		
		String testCase1=null;
		
		try {
			testCase1="Verify the count in the quick access after creating the task";
			if (!Utility.insertTestName.containsKey(testCase1)) {
				Utility.insertTestName.put(testCase1, testCase1);
				Utility.reTry.put(testCase1, 0);
			} else {
				Utility.reTry.replace(testCase1, 1);
			}	
			sizeOfRoomTile=roomStatus.getQuickStatCount(driver,"All");
			app_logs.info(sizeOfRoomTile);
			taskCategory=taskCategories.split("\\|");
			taskType=taskTypes.split("\\|");
			getTest_Steps.clear();
			getTest_Steps = tasklist.clickAddTaskButton(driver);
			getTest_Steps = tasklist.CreateNewTask(driver, taskFors, taskCategory[0], taskType[0], taskDetail, taskRemark,
					taskAssignees, "50", getTest_Steps);
			test_steps.addAll(getTest_Steps);
			test_steps.add("Successfully Added Task with " +taskFors);
			app_logs.info("Successfully Added  Task with " +taskFors);		
			String sizeOfRoomTile1=roomStatus.getQuickStatCount(driver,"All");
			app_logs.info(sizeOfRoomTile1);
			if(Integer.parseInt(sizeOfRoomTile1)>Integer.parseInt(sizeOfRoomTile)) {
				test_steps.add("Successfully verify the count in the quick access after creating the task " +sizeOfRoomTile1);
				app_logs.info("Successfully verify the count in the quick access after creating the task " +sizeOfRoomTile1);		
				Utility.testCasePass(statusCode, 1, comments, "verify the count in the quick access after creating the task");
				test_steps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/787280' target='_blank'>"
					+ "Click here to open TestRail: C787280</a><br>");
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase1, test_description, test_catagory, test_steps);	
			}
		
			
		}catch(Exception e)
		{
			Utility.catchException(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}catch(Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}
		String testCase2=null;
		try {
			testCase2="User cannot search for task using invalid name in basic search box (no results)";
			if (!Utility.insertTestName.containsKey(testCase2)) {
				Utility.insertTestName.put(testCase2, testCase2);
				Utility.reTry.put(testCase2, 0);
			} else {
				Utility.reTry.replace(testCase2, 1);
			}	
			
			app_logs.info("======Search and Verified No Task=======");
			test_steps.add("======Search and Verified No Task=======");
			tasklist.SearchTask(driver, "ddrerevghyut");
			tasklist.verifyNoTaskFound(driver, test_steps, "No tasks found for the selected criteria and property.");
			Utility.testCasePass(statusCode, 2,comments,"Verified User can search for task using FOR reservation name in basic search box");
			test_steps.add("Verified User can search for task using FOR reservation name"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787287' target='_blank'>"
				+ "Click here to open TestRail: C787287</a><br>");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase2, test_description, test_catagory, test_steps);	
		}catch(Exception e)
		{
			Utility.catchException(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}catch(Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}
		
		String testCase3=null;
		try {
			testCase3="User can search for task using task category 'front desk' in basic search box";
			if (!Utility.insertTestName.containsKey(testCase3)) {
				Utility.insertTestName.put(testCase3, testCase3);
				Utility.reTry.put(testCase3, 0);
			} else {
				Utility.reTry.replace(testCase3, 1);
			}	
			app_logs.info("======Search and Verified Front Desk=======");
			test_steps.add("======Search and Verified Front Desk=======");
			tasklist.SearchTask(driver, "Front Desk");
			tasklist.verifyTaskType(driver, taskType[0], test_steps);
			Utility.testCasePass(statusCode, 3,comments,"User can search for task using task category 'front desk' in basic search box");
			test_steps.add("User can search for task using task category 'front desk' in basic search box"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787284' target='_blank'>"
				+ "Click here to open TestRail: C787284</a><br>");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase3, test_description, test_catagory, test_steps);	
			
		}catch(Exception e)
		{
			Utility.catchException(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}catch(Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}
		
		String testCase4=null;
		try {
			testCase4="Validation message to be displayed when Saving an EXISTING Task without filling mandatory fields";
			if (!Utility.insertTestName.containsKey(testCase4)) {
				Utility.insertTestName.put(testCase4, testCase4);
				Utility.reTry.put(testCase4, 0);
			} else {
				Utility.reTry.replace(testCase4, 1);
			}	
			
			tasklist.editFirstTask(driver);
			getTest_Steps = tasklist.clearEditTask(driver, taskCategory[1], taskType[1], getTest_Steps);
			test_steps.addAll(getTest_Steps);
			Wait.waitforPageLoad(5, driver);
			tasklist.verifyMandatoryFieldForTask(driver, test_steps);
			tasklist.TaskPopup_CloseButton(driver);
			Utility.testCasePass(statusCode, 4,comments,"Validation message to be displayed when Saving an EXISTING Task without filling mandatory fields");
			test_steps.add("Validation message to be displayed when Saving an EXISTING Task without filling mandatory fields"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/787269' target='_blank'>"
				+ "Click here to open TestRail: C7787269</a><br>");
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testCase4, test_description, test_catagory, test_steps);	
		}catch(Exception e)
		{
			Utility.catchException(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}catch(Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}
		String testCase5=null;
		try {
			testCase5="Observe the Quick Stats after deleting the Tasks";
			if (!Utility.insertTestName.containsKey(testCase5)) {
				Utility.insertTestName.put(testCase5, testCase5);
				Utility.reTry.put(testCase5, 0);
			} else {
				Utility.reTry.replace(testCase5, 1);
			}	
			
			String sizeOfRoomTile1=roomStatus.getQuickStatCount(driver,"All");
			app_logs.info(sizeOfRoomTile1);
			tasklist.SearchTask(driver, "Front Desk");
			tasklist.deleteTask(driver, taskType[0], test_steps);
			String sizeOfRoomTile2=roomStatus.getQuickStatCount(driver,"All");
			app_logs.info(sizeOfRoomTile2);
			
			if(Integer.parseInt(sizeOfRoomTile1)>Integer.parseInt(sizeOfRoomTile2)) {
				test_steps.add("Successfully verify Observe the Quick Stats after deleting the Tasks " +sizeOfRoomTile2);
				app_logs.info("Successfully verify Observe the Quick Stats after deleting the Tasks " +sizeOfRoomTile2);		
				Utility.testCasePass(statusCode, 1, comments, "erify Observe the Quick Stats after deleting the Tasks");
				test_steps.add("<a href='https://innroad.testrail.io/index.php?/cases/view/787255' target='_blank'>"
					+ "Click here to open TestRail: C787255</a><br>");
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testCase5, test_description, test_catagory, test_steps);	
			}
			
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, caseId, statusCode, comments, TestCore.TestRail_AssignToID);

		}
		catch(Exception e)
		{
			Utility.catchException(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}catch(Error e) {
			Utility.catchError(driver, e, test_description, test_catagory, "Task", testName, test_description, test_catagory, test_steps);
		}
	}
        
	@DataProvider
	public Object[][] getData() {

		// return test data from the sheetname provided

		return Utility.getData("GSTaskListVerification", envLoginExcel);
	}

	@AfterClass(alwaysRun=true)
	public void closeDriver() {
		 driver.quit();
	}

}
