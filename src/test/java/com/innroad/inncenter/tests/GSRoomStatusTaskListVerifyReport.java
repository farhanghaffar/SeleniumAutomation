package com.innroad.inncenter.tests;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.innroad.inncenter.pageobjects.Navigation;
import com.innroad.inncenter.pageobjects.NewRoomClassesV2;
import com.innroad.inncenter.pageobjects.Properties;
import com.innroad.inncenter.pageobjects.RoomClass;
import com.innroad.inncenter.pageobjects.RoomStatus;
import com.innroad.inncenter.pageobjects.TaskList;
import com.innroad.inncenter.pageobjects.TaskManagement;
import com.innroad.inncenter.testcore.TestCore;
import com.innroad.inncenter.utils.RetryFailedTestCases;
import com.innroad.inncenter.utils.Utility;

public class GSRoomStatusTaskListVerifyReport extends TestCore {

	private WebDriver driver = null;
	public static String testDescription = "";
	public static String testCategory = "";
	ArrayList<String> test_steps = new ArrayList<>();
	ArrayList<String> getTest_Steps = new ArrayList<>();
	String testName = this.getClass().getSimpleName().trim();
	Navigation navigation = new Navigation();
	RoomClass roomClass = new RoomClass();
	RoomStatus roomStatus = new RoomStatus();
	TaskManagement taskmang = new TaskManagement();
	public static String roomClassNames;
	public static String roomClassAbbrivations;
	NewRoomClassesV2 newRoomClass = new NewRoomClassesV2();
	ArrayList<String> roomNumbers = new ArrayList<>();
	Properties properties = new Properties();
	TaskList tasklist = new TaskList();
	HashMap<String, String> assignee = new HashMap<String, String>();
	String[] taskFors = null, taskAssignees = null, taskStatuss = null;
	String toDoCount = null, doneCount = null, inspectionCount = null, allCount = null, propertyName = null,
			zoneName = null;

	@BeforeTest(alwaysRun = true)
	public void checkRunMode() {

		app_logs.info("Verifying Test case " + testName.toUpperCase() + " is Skipped or not");
		if (!Utility.isExecutable(testName, gsexcel))
			throw new SkipException("Skipping the test - " + testName);
	}

	@Test(dataProvider = "getData", groups = "GuestServices")
	public void gsRoomStatusTaskListVerifyReport(String roomClassName, String roomClassAbbrivation, String maxAdults,
			String maxPersopns, String roomQuantity, String taskFor, String taskCategory, String taskType,
			String taskDetail, String taskRemark, String taskAssignee, String taskStatus) throws ParseException {
		String testCaseID="848229|848202|848769|848192|848228";
		if(Utility.getResultForCase(driver, testCaseID)) {
		String total = "5";
		
		Utility.initializeTestCase("848229|848202|848769|848192|848228", Utility.testId, Utility.statusCode, Utility.comments,
				"");
		testDescription = "GS-Room Status/Task List- Verify Reports<br>"
				+ "<a href='https://innroad.testrail.io/index.php?/cases/view/682445' target='_blank'>"
				+ "Click here to open TestRail: C682445</a><br>";
		testCategory = "Verification";
		app_logs.info("##################################################################################");
		app_logs.info("EXECUTING: " + testName + " TEST.");
		app_logs.info("##################################################################################");

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
			// login_CP(driver);
			login_GS(driver);
			test_steps.add("Logged into the application");
			app_logs.info("Logged into the application");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
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
		//
		try {
			propertyName = properties.getRateV2Property(driver, test_steps);
			app_logs.info(propertyName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
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
		// Enable toggle for Inspection
		try {
			app_logs.info("======Enabling Toggle from Task Management=======");
			test_steps.add("======Enabling Toggle from Task Management=======");
			navigation.Setup(driver);
			test_steps.add("Navigated to Setup");
			navigation.TaskManagement_TabExist(driver);
			test_steps.add("Task Management Tab Exist");
			navigation.TaskManagement(driver);
			test_steps.add("Click on Task Management");
			app_logs.info("Click on Task Management");
			taskmang.setInspectionCleaningToggle(driver, true);
			test_steps.add("Successfully Enabled Toggle for Inspection ");
			app_logs.info("Successfully Enabled Toggle for Inspection");
			taskmang.setInspectionCleaningToggleFlagStatus(driver);
			test_steps.add("Successfully Set Toggle Condition Value ");
			app_logs.info("Successfully Set Toggle Condition Value");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
				Utility.updateReport(e, "Failed to Enable toggle for Inspection", "Task Management", "RoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
				Utility.updateReport(e, "Failed to Enable toggle for Inspection", "Task Management", "RoomClass",
						driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Create RoomClass
		try {
			test_steps.add("<b>======Start Creating New Room Class======</b>");
			navigation.navigateToSetupfromTaskManagement(driver);
			navigation.roomClass(driver);
			test_steps.add("Navigated to Room Class");
			String randomNO = Utility.generateRandomNumber();
			roomClassNames = roomClassName + randomNO;
			roomClassAbbrivations = roomClassAbbrivation + randomNO;
			newRoomClass.createRoomClassV2(driver, test_steps, roomClassNames, roomClassAbbrivations, maxAdults,
					maxPersopns, roomQuantity);
			Utility.RoomClassName = roomClassNames;
			Utility.RoomClassabv = roomClassAbbrivations;
			String quantity = roomQuantity;
//			====== roomclass creations ====
			String roomNumber = Utility.RoomNo;
			roomNumbers.add(roomNumber);
			for (int i = 1; i < Integer.parseInt(quantity); i++) {
				roomNumber = String.valueOf(Integer.parseInt(roomNumber) + 1);
				roomNumbers.add(roomNumber);
			}
			app_logs.info("Room NO Are: " + roomNumbers);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
				Utility.updateReport(e, "Failed to Create Room Class", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
				Utility.updateReport(e, "Failed to Create Room Class", testName, "Verification", driver);
			} else {
				Assert.assertTrue(false);
			}
		}

		// Get Zone
		try {
			test_steps.add("<b>======Getting Zone======</b>");
			zoneName = newRoomClass.getRoomClassZone(driver);
			test_steps.add("Zone : <b>" + zoneName + "</b>");
			app_logs.info("Zone: " + zoneName);
		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
				Utility.updateReport(e, "Failed to Get Zone", testName, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
				Utility.updateReport(e, "Failed to Get Zone", testName, "GS", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Add New Task
		try {
			app_logs.info("======Create Task In GuestServices & Assign Room=======");
			test_steps.add("======Create Task In GuestServices & Assign Room=======");
			navigation.navigateGuestservicesAfterrateGrid(driver);
			app_logs.info("Navigate To Guest Services");
			test_steps.add("Navigate To Guest Services ");
			roomStatus.verifyRoomStatusTabEnabled(driver, test_steps);
			roomStatus.verifiedGuestServicesDataLoadedOrNot(driver, test_steps);
			navigation.Nav_TaskList(driver);
			app_logs.info("Navigate To Task_List");
			test_steps.add("Navigate To Task List");
			
			roomStatus.verifyTaskPageAvailable(driver, test_steps);
			/*test_steps.add("Verify Task List tab"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848228' target='_blank'>"
					+ "Click here to open TestRail: C848228</a><br>");

			Utility.testCasePass(Utility.statusCode, 4, Utility.comments,
					"Verify Task List tab");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(4), Utility.statusCode.get(4),
					Utility.comments.get(4), TestCore.TestRail_AssignToID);
			*/
			taskFors = taskFor.split("\\|");
			taskStatuss = taskStatus.split("\\|");
			taskAssignees = taskAssignee.split("\\|");
			String searchText = roomNumbers.get(0) + "|" + zoneName;
			String[] search = searchText.split("\\|");
			for (int i = 0; i < taskFors.length; i++) {
				getTest_Steps.clear();
				getTest_Steps = tasklist.clickAddTaskButton(driver);
				test_steps.addAll(getTest_Steps);
				getTest_Steps = tasklist.CreateNewTask(driver, taskFors[i], taskCategory, taskType, taskDetail,
						taskRemark, taskAssignees[i], search[i], getTest_Steps);
				test_steps.addAll(getTest_Steps);
			}
			test_steps.add("Successfully Added New Task");
			app_logs.info("Successfully Added New Task");

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
				Utility.updateReport(e, "Failed to Add New Task", testName, "TaskAdd", driver);
			} else {
				Assert.assertTrue(false);
			}
		} catch (Error e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
				Utility.updateReport(e, "Failed to Add New Task", testName, "TaskAdd", driver);
			} else {
				Assert.assertTrue(false);
			}
		}
		// Verify Report on Task List
		try {
			tasklist.clickAllStatsBar(driver, test_steps);
			toDoCount = tasklist.getToDoCount(driver);
			doneCount = tasklist.getDoneCount(driver);
			inspectionCount = tasklist.getInspectionCount(driver);
			allCount = tasklist.getAllCount(driver);
			assignee = tasklist.getAssigneeCount(driver, taskAssignees[0]);
			app_logs.info(toDoCount);
			app_logs.info(doneCount);
			app_logs.info(inspectionCount);
			app_logs.info(allCount);
			app_logs.info(assignee);
			app_logs.info("======Verify All Assignee In Report=======");
			test_steps.add("======Verify All Assignee In Report=======");
			tasklist.verifyReportAllAssignee(driver, test_steps, toDoCount, inspectionCount, doneCount, allCount,
					propertyName, taskType, taskDetail, taskStatus, taskRemark, zoneName);
					tasklist.verifyTaskReportHeader(driver, "Assigned");
					tasklist.closeAndSwitchToParentWindow(driver);
			app_logs.info("======Verify By Assignee In Report=======");
			test_steps.add("======Verify By Assignee In Report=======");
			tasklist.verifyByAssignee(driver, test_steps, assignee.get("Assignees"), taskAssignees[0],
					assignee.get("UnAssignees"));
			tasklist.verifyTaskReportHeader(driver, "Assigned");
			 tasklist.closeAndSwitchToParentWindow(driver);
			/*test_steps.add("Task List report"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848229' target='_blank'>"
					+ "Click here to open TestRail: 848229</a><br>");

			Utility.testCasePass(Utility.statusCode, 0, Utility.comments, "Task List report");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(0), Utility.statusCode.get(0),
					Utility.comments.get(0), TestCore.TestRail_AssignToID);

			test_steps.add(
					"Navigate to task list tab and click on reports and select All Assignees/BY Assignees and check if you are navigated to a seperate tab."
							+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848769' target='_blank'>"
							+ "Click here to open TestRail: 848769</a><br>");

			Utility.testCasePass(Utility.statusCode, 2, Utility.comments,
					"Navigate to task list tab and click on reports and select All Assignees/BY Assignees and check if you are navigated to a seperate tab.");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(2), Utility.statusCode.get(2),
					Utility.comments.get(2), TestCore.TestRail_AssignToID);*/
		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testDescription, "Task List", testName, testDescription,
					testDescription, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testDescription, "Task List", testName, testDescription,
					testDescription, test_steps);
		}

		// Verify Report on Room Status
		try {
			app_logs.info("======Verify Report in GS=======");
			test_steps.add("======Verify Report in GS=======");
			navigation.roomStatus(driver);
			roomStatus.searchByRoom(driver, roomClassNames, test_steps);
			roomStatus.Reports(driver);
			roomStatus.reportRoomStatusWithTask(driver);
			roomStatus.switchToNextTab(driver);
			roomStatus.verifytaskOnReport(driver, test_steps, taskType, taskDetail, taskRemark, taskStatus);

			/*test_steps.add("GS-Room Status/Task List- Verify Reports"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848202' target='_blank'>"
					+ "Click here to open TestRail: 848202</a><br>");
			Utility.testCasePass(Utility.statusCode, 1, Utility.comments, "GS-Room Status/Task List- Verify Reports");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(1), Utility.statusCode.get(1),
					Utility.comments.get(1), TestCore.TestRail_AssignToID);

			test_steps.add("Verify the Reports filter in Room Status page and Task list page"
					+ "<a href='https://innroad.testrail.io/index.php?/cases/view/848192' target='_blank'>"
					+ "Click here to open TestRail: 848192</a><br>");

			Utility.testCasePass(Utility.statusCode, 3, Utility.comments,
					"Verify the Reports filter in Room Status page and Task list page");
			Utility.updateSingleTestCaseStatus(driver, Utility.testId.get(3), Utility.statusCode.get(3),
					Utility.comments.get(3), TestCore.TestRail_AssignToID);*/
			
			for(int i=0;i<Utility.statusCode.size();i++) {
				Utility.testCasePass(Utility.statusCode, i, Utility.comments, "Verify Roomm Status Task list");
			}
			Utility.updateTestCaseAndCloseDriverWithMultipleComments(driver, Utility.testId, Utility.statusCode,
					Utility.comments, TestCore.TestRail_AssignToID);
		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testDescription, "Task List", testName, testDescription,
					testDescription, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testDescription, "Task List", testName, testDescription,
					testDescription, test_steps);
		}

		try {
			app_logs.info("======Delete Task=======");
			test_steps.add("======Delete Task=======");
			navigation.Task_List(driver);
			app_logs.info("Navigate To Task_List");
			test_steps.add("Navigate To Task_List");
			tasklist.SearchTask(driver, roomNumbers.get(0));
			tasklist.deleteTask(driver, taskType, test_steps);
			tasklist.SearchTask(driver, zoneName);
			tasklist.deleteTask(driver, taskType, test_steps);

			
		} catch (Exception e) {
			Utility.catchException(driver, e, testDescription, testDescription, "Task List", testName, testDescription,
					testDescription, test_steps);
		} catch (Error e) {
			Utility.catchError(driver, e, testDescription, testDescription, "Task List", testName, testDescription,
					testDescription, test_steps);
		}
		// Delete Room Class
		try {
			test_steps.add("<b>======Delete Room Class======</b>");
			navigation.navigateToSetupfromTaskManagement(driver);
			test_steps.add("Navigated to Setup");
			navigation.RoomClass(driver);
			newRoomClass.searchRoomClassV2(driver, Utility.RoomClassName);
			test_steps.add("Click on Search Button");
			app_logs.info("Click on Search Button");
			newRoomClass.deleteRoomClassV2(driver, Utility.RoomClassName);
			test_steps.add("All Room Class Deleted Successfully With Name: <b>" + Utility.RoomClassName + " </b>");
			app_logs.info("All Room Class Deleted Successfully With Name: " + Utility.RoomClassName);

			
			RetryFailedTestCases.count = Utility.reset_count;
			Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);

		} catch (Exception e) {
			if (Utility.reTry.get(testName) == Utility.count) {
				RetryFailedTestCases.count = Utility.reset_count;
				Utility.AddTest_IntoReport(testName, testDescription, testDescription, test_steps);
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
	public Object[][] getData() {

		// return test data from the sheet name provided

		return Utility.getData("GSRoomStatusTaskListVerifyRepor", gsexcel);
	}

	@AfterClass(alwaysRun = true)
	public void closeDriver() {
		driver.quit();
	}

}
